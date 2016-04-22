package com.froad.timer.task.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mappers.ProductMapper;
import com.froad.db.mysql.rp_mappers.ReportMerchantProductMapper;
import com.froad.db.mysql.rp_mappers.ReportOrderMapper;
import com.froad.db.mysql.rp_mappers.ReportOrderProductMapper;
import com.froad.enums.OrderStatus;
import com.froad.enums.OrderType;
import com.froad.enums.OrgLevelEnum;
import com.froad.enums.ProductAuditState;
import com.froad.enums.ProductStatus;
import com.froad.enums.ProductType;
import com.froad.enums.SubOrderRefundState;
import com.froad.logback.LogCvt;
import com.froad.po.BatchChunk;
import com.froad.po.Org;
import com.froad.po.ProductCategory;
import com.froad.po.ReportMerchantProduct;
import com.froad.po.ReportOrder;
import com.froad.po.ReportOrderProduct;
import com.froad.singleton.ProductCategorySingleton;
import com.froad.timer.bean.TaskBean;
import com.froad.timer.task.AbstractProTask;
import com.froad.util.Checker;
import com.froad.util.CollectionsUtil;
import com.froad.util.EmptyChecker;
import com.froad.util.OrgUtil;
import com.froad.util.TaskTimeUtil;

public class TaskReportMerchantProduct extends AbstractProTask {
	
	private ProductMapper productMapper = null;
	private ReportMerchantProductMapper reportMerchantProductMapper = null;
	private ReportOrderProductMapper reportOrderProductMapper = null;
	private ReportOrderMapper reportOrderMapper = null;
	
	private Map<String,String> categoryMap = null;
	private OrgUtil orgUtil = null;
	
	public TaskReportMerchantProduct(String name, TaskBean task) {
		super(name, task);
	}
	
	
	/**
	 * process product records by chunk
	 * 
	 * @param batchChunk
	 * @param categoryMap
	 */
	
	public void processByChunk(BatchChunk batchChunk){
		Date queryDate = null;
		List<ReportMerchantProduct> reportMerchantProductList = null;
		List<ReportMerchantProduct> merchantProductList = null;
		ReportMerchantProduct reportMerchantProduct = null;
		StringBuffer logMsg = null;
		Page<ReportMerchantProduct> page = null;
		String[] categoryTree = null;
		Org org = null;
		long startTime = 0;
		long endTime = 0;
		String category = null;
		long saleCount = 0;
		long saleAmount = 0;
		long refundAmount = 0;
		List<ReportOrderProduct> orderProductList = null;
		ReportOrderProduct orderProduct = null;
		List<ReportOrder> reportOrderList = null;
		ReportOrder reportOrder = null;
		ReportOrder orderQuery = null;
		String merchantId = null;
		ReportOrderProduct productQuery = null;
		
		// log message
		logMsg  = new StringBuffer();
		logMsg.append("Start process batch_id:batchDate:jobName:chunkId ");
		logMsg.append(batchChunk.getBatchId());
		logMsg.append(":");
		logMsg.append(batchChunk.getBatchDate());
		logMsg.append(":");
		logMsg.append(batchChunk.getJobName());
		logMsg.append(":");
		logMsg.append(batchChunk.getChunkId());
		LogCvt.info(logMsg.toString());
		
		page = new Page<ReportMerchantProduct>();
		page.setPageNumber(batchChunk.getChunkPage());
		page.setPageSize(batchChunk.getChunkSize());
		
		queryDate = batchChunk.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayEnd(batchChunk.getLastBatchDate()) : TaskTimeUtil.convertToDayBegin(batchChunk.getBatchDate());
		startTime = batchChunk.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayBegin(batchChunk.getLastBatchDate()).getTime() : 0;
		endTime = batchChunk.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayEnd(batchChunk.getLastBatchDate()).getTime() : 0;
		
		reportMerchantProductList = productMapper.makeReportMerchantProductDataByPage(queryDate, page);
		
		merchantProductList = new ArrayList<ReportMerchantProduct>();
		for(ReportMerchantProduct p  : reportMerchantProductList){
			org = orgUtil.getOrg(p.getClientId(), p.getOrgCode());
			if (org == null){
				LogCvt.warn(new StringBuffer("机构码不存在，org_code=")
						.append(p.getOrgCode()).append(" 商品ID，product_id=")
						.append(p.getProductId()).toString());
				continue;
			}
			if(Checker.isNotEmpty(p.getCategory())){
				categoryTree = p.getCategory().trim().split(" ");
				category = categoryTree[0].trim();
			}

			saleCount = 0;
			saleAmount = 0;
			refundAmount = 0;
			if (ProductType.presell.getCode().equals(p.getType())){
				merchantId = p.getOrgCode();
			} else {
				merchantId = p.getMerchantId();
			}
			
			productQuery = new ReportOrderProduct();
			productQuery.setClientId(p.getClientId());
			productQuery.setMerchantId(merchantId);
			productQuery.setCreateDate(batchChunk.getBatchDate());
			productQuery.setProductId(p.getProductId());
			orderProductList = reportOrderProductMapper.findByCondition(productQuery);
			if (EmptyChecker.isNotEmpty(orderProductList)){
				for (int i = 0; i < orderProductList.size(); i++){
					orderProduct = orderProductList.get(i);
					
					// 商品销售数量、金额，全额退款的不计算
					if (orderProduct.getOrderStatus().equals(OrderStatus.paysuccess.getCode())
							&& !orderProduct.getRefundState().equals(SubOrderRefundState.REFUND_SUCCESS.getCode())) {
						saleCount += orderProduct.getQuantity() + orderProduct.getVipQuantity();
						saleAmount += orderProduct.getMoney() * orderProduct.getQuantity()
								+ orderProduct.getVipMoney() * orderProduct.getVipQuantity();
					}
					
					if (orderProduct.getRefundState().equals(SubOrderRefundState.REFUND_SUCCESS.getCode())
							|| orderProduct.getRefundState().equals(SubOrderRefundState.REFUND_PART.getCode())) {
						refundAmount += orderProduct.getMoney() * orderProduct.getRefundQuantity()
								+ orderProduct.getVipMoney() * orderProduct.getVipRefundQuantity();
					}
				}
			}
			
			reportMerchantProduct = new ReportMerchantProduct();
			if (EmptyChecker.isNotEmpty(category)){
				reportMerchantProduct.setCategory(category);
				reportMerchantProduct.setCategoryName(categoryMap.get(p.getClientId() + category));
			}
			reportMerchantProduct.setClientId(p.getClientId());
			reportMerchantProduct.setCreateTime(TaskTimeUtil.convertToDay(batchChunk.getBatchDate()));
			reportMerchantProduct.setMerchantId(merchantId);
			reportMerchantProduct.setMerchantName(p.getMerchantName());
			if (p.getAuditState().equals(ProductAuditState.passAudit.getCode()) && startTime <= p.getAuditTime().getTime() && endTime >= p.getAuditTime().getTime()){
				reportMerchantProduct.setNewCount(p.getTotalProducts());
			} else {
				reportMerchantProduct.setNewCount(0l);
			}
			if (reportMerchantProduct.getNewCount() == 0 && refundAmount == 0 && saleAmount == 0 && saleCount == 0){
				//没有新增，没有动账的商品，不记录
				LogCvt.info(new StringBuffer("商品无新增和动账记录，product_id=").append(p.getProductId()).toString());
				continue;
			}
			reportMerchantProduct.setProductId(p.getProductId());
			reportMerchantProduct.setRefundAmount(refundAmount);
			reportMerchantProduct.setSaleAmount(saleAmount);
			reportMerchantProduct.setSaleCount(saleCount);
			if (p.getAuditState().equals(ProductAuditState.passAudit.getCode()) && p.getIsMarketable().equals(ProductStatus.onShelf.getCode())){
				reportMerchantProduct.setTotalProducts(p.getTotalProducts());
			} else {
				reportMerchantProduct.setTotalProducts(0l);
				reportMerchantProduct.setNewCount(0l);
			}
			reportMerchantProduct.setType(p.getType());
			
			reportMerchantProduct.setForgCode(org.getProvinceAgency());
			if (orgUtil.getOrg(org.getClientId(), org.getProvinceAgency()) != null && orgUtil.getOrg(org.getClientId(), org.getProvinceAgency()).getOrgName() != null){
				reportMerchantProduct.setForgName(orgUtil.getOrg(org.getClientId(), org.getProvinceAgency()).getOrgName());
			}
			reportMerchantProduct.setOrgCode(p.getOrgCode());
			if (orgUtil.getOrg(p.getClientId(), p.getOrgCode()) != null && orgUtil.getOrg(p.getClientId(), p.getOrgCode()).getOrgName() != null){
				reportMerchantProduct.setOrgName(orgUtil.getOrg(p.getClientId(), p.getOrgCode()).getOrgName());
			}
			reportMerchantProduct.setSorgCode(org.getCityAgency());
			if (orgUtil.getOrg(org.getClientId(), org.getCityAgency()) != null && orgUtil.getOrg(org.getClientId(), org.getCityAgency()).getOrgName() != null){
				reportMerchantProduct.setSorgName(orgUtil.getOrg(org.getClientId(), org.getCityAgency()).getOrgName());
			}
			reportMerchantProduct.setTorgCode(org.getCountyAgency());
			if (orgUtil.getOrg(org.getClientId(), org.getCountyAgency()) != null && orgUtil.getOrg(org.getClientId(), org.getCountyAgency()).getOrgName() != null){
				reportMerchantProduct.setTorgName(orgUtil.getOrg(org.getClientId(), org.getCountyAgency()).getOrgName());
			}
			if (org.getOrgLevel().equals(OrgLevelEnum.orgLevel_four.getLevel())){
				reportMerchantProduct.setLorgCode(org.getOrgCode());
				reportMerchantProduct.setLorgName(org.getOrgName());
			} else if (org.getOrgLevel().equals(OrgLevelEnum.orgLevel_three.getLevel())) {
				reportMerchantProduct.setTorgCode(org.getOrgCode());
				reportMerchantProduct.setTorgName(org.getOrgName());
			} else if (org.getOrgLevel().equals(OrgLevelEnum.orgLevel_two.getLevel())) {
				reportMerchantProduct.setSorgCode(org.getOrgCode());
				reportMerchantProduct.setSorgName(org.getOrgName());
			}
			
			merchantProductList.add(reportMerchantProduct);
		}
		
		// 最后的chunk处理面对面商品
		if (batchChunk.getChunkPage() == batchChunk.getTotalChunk()){
			LogCvt.info("开始处理面对面商品");
			
			orderQuery = new ReportOrder();
			orderQuery.setOrderType(OrderType.face2face.getCode());
			orderQuery.setCreateDate(batchChunk.getBatchDate());
			reportOrderList = reportOrderMapper.findByCondition(orderQuery);
			if (EmptyChecker.isNotEmpty(reportOrderList)){
				for (int i = 0; i < reportOrderList.size(); i++){
					reportOrder = reportOrderList.get(i);
					
					reportMerchantProduct = new ReportMerchantProduct();
//					reportMerchantProduct.setCategory(category);
//					reportMerchantProduct.setCategoryName(categoryMap.get(Long.valueOf(category)));
					reportMerchantProduct.setClientId(reportOrder.getClientId());
					reportMerchantProduct.setCreateTime(TaskTimeUtil.convertToDay(batchChunk.getBatchDate()));
					reportMerchantProduct.setForgCode(reportOrder.getForgCode());
					reportMerchantProduct.setForgName(reportOrder.getForgName());
					reportMerchantProduct.setLorgCode(reportOrder.getLorgCode());
					reportMerchantProduct.setLorgName(reportOrder.getLorgName());
					reportMerchantProduct.setMerchantId(reportOrder.getMerchantId());
					reportMerchantProduct.setMerchantName(reportOrder.getMemberName());
					reportMerchantProduct.setNewCount(1l);
					reportMerchantProduct.setOrgCode(reportOrder.getOrgCode());
					if (orgUtil.getOrg(reportOrder.getClientId(), reportOrder.getOrgCode()) != null && orgUtil.getOrg(reportOrder.getClientId(), reportOrder.getOrgCode()).getOrgName() != null){
						reportMerchantProduct.setOrgName(orgUtil.getOrg(reportOrder.getClientId(), reportOrder.getOrgCode()).getOrgName());
					}
					reportMerchantProduct.setProductId(reportOrder.getProductId());
					reportMerchantProduct.setRefundAmount(0l);
					reportMerchantProduct.setSaleAmount(reportOrder.getTotalPrice());
					reportMerchantProduct.setSaleCount(1l);
					reportMerchantProduct.setSorgCode(reportOrder.getSorgCode());
					reportMerchantProduct.setSorgName(reportOrder.getSorgName());
					reportMerchantProduct.setTorgCode(reportOrder.getTorgCode());
					reportMerchantProduct.setTorgName(reportOrder.getTorgName());
					reportMerchantProduct.setTotalProducts(1l);
					reportMerchantProduct.setType("0");//面对面虚拟商品
					merchantProductList.add(reportMerchantProduct);
				}
			}
			
			LogCvt.info("面对面商品处理完成");
		}
		
		if(Checker.isNotEmpty(merchantProductList)){
			List<List<ReportMerchantProduct>> totalProduceList = CollectionsUtil.splitList(merchantProductList, CollectionsUtil.MAX_INSERT_SIZE);
			for (List<ReportMerchantProduct> subList : totalProduceList){
				boolean result = reportMerchantProductMapper.addByBatch(subList);
				LogCvt.info("商户商品统计汇总表cb_report_merchant_product添加"+(result?"成功":"失败"));
			}
		}
	}
	
	@Override
	public void initialize() {
		reportMerchantProductMapper = rpSqlSession.getMapper(ReportMerchantProductMapper.class);
		reportOrderProductMapper = rpSqlSession.getMapper(ReportOrderProductMapper.class);
		reportOrderMapper = rpSqlSession.getMapper(ReportOrderMapper.class);
		productMapper = sqlSession.getMapper(ProductMapper.class);
		
		List<ProductCategory> productCategoryList = null;
		categoryMap = new HashMap<String,String>(); 
		productCategoryList = ProductCategorySingleton.getInstance(sqlSession);
		for(ProductCategory p : productCategoryList){
			categoryMap.put(p.getClientId() + p.getId(), p.getName());
		}
		
		orgUtil = new OrgUtil(sqlSession);
	}
	
}
