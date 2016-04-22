/*   
* Copyright © 2008 F-Road All Rights Reserved.
*  
* This software is the confidential and proprietary information of   
* Founder. You shall not disclose such Confidential Information   
* and shall use it only in accordance with the terms of the agreements   
* you entered into with Founder.   
*   
*/

/**
 * 
 * @Title: ResourceLogicImpl.java
 * @Package com.froad.logic.impl
 * @see:  
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.thrift.TException;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.AmountMapper;
import com.froad.db.mysql.mapper.DefineTaskMapper;
import com.froad.db.mysql.mapper.MemberMapper;
import com.froad.db.mysql.mapper.MerchantMapper;
import com.froad.db.mysql.mapper.ProductMapper;
import com.froad.enums.CountTypeEnum;
import com.froad.enums.LatitudeTupeEnum;
import com.froad.logback.LogCvt;
import com.froad.logic.ReportLogic;
import com.froad.po.req.DefineTaskReq;
import com.froad.po.req.ReqPo;
import com.froad.po.resp.AmountQuota;
import com.froad.po.resp.BaseResp;
import com.froad.po.resp.DefineTaskResp;
import com.froad.po.resp.MemberQuota;
import com.froad.po.resp.MerchantInfoResp;
import com.froad.po.resp.OrderQuota;
import com.froad.po.resp.ProductInfoResp;
import com.froad.po.resp.Resp;
import com.froad.thrift.vo.coremodule.MerchantInfoRespVo;
import com.froad.thrift.vo.coremodule.ProductInfoRespVo;
import com.froad.thrift.vo.coremodule.ReportAmountQuotaVo;
import com.froad.thrift.vo.coremodule.ReportMemberQuotaVo;
import com.froad.thrift.vo.coremodule.ReportOrderQuotaVo;
import com.froad.util.BeanUtil;
import com.froad.utils.DateSystemUtil;
import com.froad.utils.NumberUtils;

/**
 * 
 * <p>
 * 
 * @Title: ResourceLogic.java
 *         </p>
 *         <p>
 *         Description: 描述
 *         </p>
 * 
 * @author f-road
 * @version 1.0
 * @created 2015年3月17日
 */
public class ReportLogicImpl implements ReportLogic {

	public static Map<String, Long> totalAmountMap = new HashMap<String, Long>(); // 累积金额
	public static Map<String, Long> totalRefundAmountMap = new HashMap<String, Long>();// 累计退款金额
	public static Map<String, Long> totalOrderMap = new HashMap<String, Long>(); // 累积订单数
	public static Map<String, Long> totalRefundOrderMap = new HashMap<String, Long>(); // 累计退款订单

	public static Map<String, Long> totalMerchantMap = new HashMap<String, Long>();// 累计商户
	public static Map<String, Long> totalOutlerMap = new HashMap<String, Long>(); // 累计门店数
	public static Map<String, Long> totalProductMap = new HashMap<String, Long>(); // 累计商品数
	public static Map<String, Long> totalDownProductMap = new HashMap<String, Long>();// 累计下架商品数
	
	public static Map<String,Integer> keyMap=new HashMap<String,Integer>();

	@Override
	public Resp sumAmountOrOrder(ReqPo reqPo) {
		Resp r = null;
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			AmountMapper amountMapper = sqlSession.getMapper(AmountMapper.class);
			r = amountMapper.sumAmountOrOrder(reqPo);
		} catch (Exception e) {
			LogCvt.error("查询ReportLogicImpl-->sumAmountOrOrder失败，原因:" + e.getMessage(), e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return r;
	}
	
	@Override
	public Resp sumMerchantCount(ReqPo reqPo) {
		Resp r = null;
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			MerchantMapper merchantMapper = sqlSession.getMapper(MerchantMapper.class);
			r = merchantMapper.sumMerchantCount(reqPo);
		} catch (Exception e) {
			LogCvt.error("查询ReportLogicImpl-->sumMerchantCount失败，原因:" + e.getMessage(), e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return r;
	}
	
	@Override
	public Resp sumProductCount(ReqPo reqPo){
		Resp r = null;
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
			r = productMapper.sumProductCount(reqPo);
		} catch (Exception e) {
			LogCvt.error("查询ReportLogicImpl-->sumProductCount失败，原因:" + e.getMessage(), e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return r;
	}
	
	public Resp sumRefundAmountOrOrder(ReqPo reqPo){
		Resp r = null;
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			AmountMapper amountMapper = sqlSession.getMapper(AmountMapper.class);
			r = amountMapper.sumRefundAmountOrOrder(reqPo);
		} catch (Exception e) {
			LogCvt.error("查询ReportLogicImpl-->sumRefundAmountOrOrder失败，原因:" + e.getMessage(), e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return r;
	}

	public Page<Resp> findReportByPage(Page<Resp> page, ReqPo reqPo) {
		List<Resp> listRes = new ArrayList<Resp>();
		SqlSession sqlSession = null;
		int totalCount = 0;
		try {
			/********************** 操作MySQL数据库 **********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			/******************* 金额订单指标查询 **********************/
			if (reqPo.getLatitudeTupe().equals(LatitudeTupeEnum.AMOUNT.getDescription())
					|| reqPo.getLatitudeTupe().equals(LatitudeTupeEnum.ORDER.getDescription())) {

				AmountMapper amountMapper = sqlSession.getMapper(AmountMapper.class);
				listRes = amountMapper.findReportPageByAmount(reqPo);
				totalCount = amountMapper.findReportPageCountByAmount(reqPo);
				page.setTotalCount(totalCount);
			}
			/******************* 商户指标查询 **********************/
			if (reqPo.getLatitudeTupe().equals(LatitudeTupeEnum.MERCHANT.getDescription())) {
				MerchantMapper merchantMapper = sqlSession.getMapper(MerchantMapper.class);
				listRes = merchantMapper.findReportMerchantInfoByPage(page, reqPo);
				totalCount = merchantMapper.getMerchantCount(page, reqPo);
				page.setTotalCount(totalCount);
			}
			/******************* 商品指标查询 **********************/
			if (reqPo.getLatitudeTupe().equals(LatitudeTupeEnum.PRODUCT.getDescription())) {
				ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
				listRes = productMapper.findReportProductInfoBy(page, reqPo);
				totalCount = productMapper.getProductCount(page, reqPo);
				page.setTotalCount(totalCount);
			}
			/******************* 用户指标查询 **********************/
			if (reqPo.getLatitudeTupe().equals(LatitudeTupeEnum.MERMBER.getDescription())) {
				MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
				listRes = memberMapper.findReportPageByMember(reqPo);
				totalCount = memberMapper.findReportMemberByPageCount(reqPo);
				page.setTotalCount(totalCount);
			}
			page.setResultsContent(listRes);
		} catch (Exception e) {
			LogCvt.error("分页查询ReportLogicImpl-->findReportByPage失败，原因:" + e.getMessage(), e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return page;
	}

	@Override
	public Page<Resp> getReportByPage(Page<Resp> page, ReqPo req) {
		List<Resp> listRes = new ArrayList<Resp>();
		page.setResultsContent(listRes);
		return page;
	}

	/**
	 * 任务列表分页查询
	 */
	@Override
	public Page<DefineTaskResp> getDefineTaskByPage(Page<DefineTaskResp> page, DefineTaskReq req) {
		SqlSession sqlSession = null;
		sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
		DefineTaskMapper defineTaskMapper = sqlSession.getMapper(DefineTaskMapper.class);
		List<DefineTaskResp> list = defineTaskMapper.getDefineTaskRespByPage(page, req);
		page.setResultsContent(list);

		return page;
	}
	/**
	 * 查询 Resource
	 * 
	 * @param resource
	 * @return List<Resource> 结果集合
	 */
	/*
	 * public List<AmountResp> selectAmount(ReqPo req) { //
	 * LogCvt.info("--------开始查询金额信息---------"); List<AmountResp> result = new
	 * ArrayList<AmountResp>(); SqlSession sqlSession = null; AmountMapper
	 * amountMapper = null; try { sqlSession =
	 * MyBatisManager.getSqlSessionFactory().openSession(); amountMapper =
	 * sqlSession.getMapper(AmountMapper.class);
	 * result=amountMapper.selectAmount(req); // sqlSession.commit(true); }
	 * catch (Exception e) { result = null; e.printStackTrace();
	 * LogCvt.error("获取金额指标报表e失败，原因:" + e.getMessage(), e); } finally { if (null
	 * != sqlSession) sqlSession.close(); } return result; }
	 */

	/**
	 * excel表头封装
	 * 
	 * @param page
	 * @param req
	 * @return
	 */
	public List<String> toExcelHeader(ReqPo req) {
		List<String> header = new ArrayList<String>();// 表头
		if (req != null) {
			if (req.getCountType() != null) {// 统计类型：day：按天;week：按周;month：按月;diy：自定义
				if (CountTypeEnum.day.name().equalsIgnoreCase(req.getCountType())) {
					header.add("时间(天)");
				} else if (CountTypeEnum.week.name().equalsIgnoreCase(req.getCountType())) {
					header.add("时间(周)");
				} else if (CountTypeEnum.month.name().equalsIgnoreCase(req.getCountType())) {
					header.add("时间(月)");
				} else if (CountTypeEnum.year.name().equalsIgnoreCase(req.getCountType())) {
					header.add("时间(年)");
				}
			}
			// if(req.getClientId()!=null){
			// header.add("银行");
			// }
			// if(req.getPlatform()!=null){
			// header.add("业务平台");
			// }
			if (req.getOrgCode() != null) {
				// header.add("机构号");
				header.add("机构名称");
			}
			if (req.getOrderType() != null) {
				header.add("业务类型");
			}
			if (req.getPayType() != null) {
				header.add("支付方式");
			}
			if (req.getCheckedMerchant()) {
				header.add("商户");
			}
			if (req.getCheckedMerchantCategory()) {
				header.add("商户类目");
			}
			// if (LatitudeTupeEnum.AMOUNT.getDescription()
			// .equalsIgnoreCase(req.getLatitudeTupe())) {
			if (req.getCheckedAmount()) {
				header.add("金额(元)");
			}
			if (req.getCheckedAmountCumulation()) {
				header.add("累计金额(元)");
			}
			if (req.getCheckedAmountRefund()) {
				header.add("退款金额(元)");
			}
			if (req.getCheckedAmountTurnover()) {
				header.add("成交金额(元)");
			}
			if (req.getCheckedAmountCumulationTurnover()) {
				header.add("累计成交金额(元)");
			}
			// } else if (LatitudeTupeEnum.ORDER.getDescription()
			// .equalsIgnoreCase(req.getLatitudeTupe())) {
			if (req.getCheckedOrderCount()) {
				header.add("订单");
			}
			if (req.getCheckedOrderCumulation()) {
				header.add("累计订单");
			}
			if (req.getCheckedOrderRefund()) {
				header.add("退款订单");
			}
			if (req.getCheckedOrderTurnover()) {
				header.add("成交订单");
			}
			if (req.getCheckedOrderCumulationTurnover()) {
				header.add("累积成交订单");
			}
			// } else if (LatitudeTupeEnum.MERCHANT.getDescription()
			// .equalsIgnoreCase(req.getLatitudeTupe())) {
			if (req.getCheckedMerchantSum()) {
				header.add("商户数");
			}
			if (req.getCheckedMerchantCancelContract()) {
				header.add("解约商户数");
			}
			if (req.getCheckedMerchantCumulation()) {
				header.add("累积商户数");
			}
			if (req.getCheckedOutletCount()) {
				header.add("门店数");
			}
			if (req.getCheckedOutletCumulation()) {
				header.add("累计门店数");
			}
			// } else if (LatitudeTupeEnum.PRODUCT.getDescription()
			// .equalsIgnoreCase(req.getLatitudeTupe())) {
			if (req.getCheckedProductSum()) {
				header.add("商品数");
			}
			if (req.getCheckedProductCumulation()) {
				header.add("累计商品数");
			}
			if (req.getCheckedProductDownSum()) {
				header.add("下架商品数");
			}
			if (req.getCheckedProductDownComulation()) {
				header.add("累计下架商品数");
			}
			// } else if (LatitudeTupeEnum.MERMBER.getDescription()
			// .equalsIgnoreCase(req.getLatitudeTupe())) {
			if (req.getCheckedMemberCount()) {
				header.add("消费用户数");
			}
			if (req.getCheckedMemberComulationCount()) {
				header.add("累计消费用户数");
			}
			// }
		}
		return header;
	}

	/**
	 * 导出excel
	 * 
	 * @param page
	 * @param req
	 * @return
	 * 实现逻辑
	 */
	public List<List<String>> toExcel(Page<Resp> page, ReqPo req) {
		List<List<String>> data = new ArrayList<List<String>>();
		// page = this.findReportByPage(page, req);

		List<Resp> listRes = new ArrayList<Resp>();
		SqlSession sqlSession = null;

		/********************** 操作MySQL数据库 **********************/
		sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

		/******************* 金额订单指标查询 **********************/
		AmountMapper amountMapper = sqlSession.getMapper(AmountMapper.class);
		listRes = amountMapper.findReportPageBy(req);

		if (listRes == null) {
			return null;
		}

		String countType = null; 
		for (Resp resp : listRes) {
			List<String> record = new ArrayList<String>();
			BaseResp base = resp.getBase();
			
			req.setHasMyself(false);
			if((req.getOrgLevel()+"").equals(resp.getBase().getOrgLevel())){
				req.setHasMyself(true);
			}
			
			if (req.getCountType() != null) {
				countType = null;
				if ("day".equalsIgnoreCase(req.getCountType())) {
					countType = base.getDay();
				} else if ("week".equalsIgnoreCase(req.getCountType())) {
					countType = base.getWeek();
				} else if ("month".equalsIgnoreCase(req.getCountType())) {
					countType = base.getMonth();
				} else if ("year".equalsIgnoreCase(req.getCountType())) {
					countType = base.getYear();
				}
				record.add(countType);
			}
			if (req.getOrgCode() != null) {
				record.add(base.getOrgName());
			}
			if (req.getOrderType() != null) {
				record.add(base.getOrderTypeName());
			}
			if (req.getPayType() != null) {
				record.add(base.getPayTypeName());
			}
			if (req.getCheckedMerchant()) {
				record.add(base.getMerchantName());
			}
			if (req.getCheckedMerchantCategory()) {
				record.add(base.getMerchantCategoryName());
			}

			String key = "";
			if (req.getCheckedMerchant()) {
				key += base.getMerchantId();
			} else {
				if (req.getCheckedMerchantCategory()) {
					key += base.getMerchantCategoryId();
				} else {
					key += base.getOrgCode();
				}
			}
			
			if(keyMap.get("amount"+key)==null || keyMap.get("order"+key)==null || keyMap.get("merchant"+key)==null || keyMap.get("product"+key)==null)
				getFirstInfo(req, resp, base, key);
			  
			if (resp.getAmountLat() != null) {
				ReportAmountQuotaVo amountLat = (ReportAmountQuotaVo) BeanUtil.copyProperties(ReportAmountQuotaVo.class,
						resp.getAmountLat());
				if (req.getCheckedAmount()) {
					record.add(NumberUtils.changeAmountLong(amountLat.getOrderAmount(), 3));
				} 
				if ( keyMap.get("amount"+key)!=null && keyMap.get("amount"+key)!=1 && !"".equals(key) && (req.getCheckedAmountCumulationTurnover() || req.getCheckedAmountCumulation())) {
					totalAmountMap.put(key, amountLat.getOrderAmount()+ (totalAmountMap.get(key) == null ? 0 : totalAmountMap.get(key)));
				}
				if (req.getCheckedAmountCumulation()) {
					record.add(NumberUtils.changeAmountLong(totalAmountMap.get(key), 3));
				}
				if (req.getCheckedAmountRefund()) {
					record.add(NumberUtils.changeAmountLong(amountLat.getRefundAmount(), 3));
				}
				if (req.getCheckedAmountTurnover()) {
					record.add(NumberUtils.changeAmountLong(amountLat.getAmountTurnover(), 3));
				}
				if ( req.getCheckedAmountCumulationTurnover()) {
					if (keyMap.get("amount"+key)!=null && keyMap.get("amount"+key)!=1 && !"".equals(key)) {
						totalRefundAmountMap.put(key, amountLat.getRefundAmount()
								+ (totalRefundAmountMap.get(key) == null ? 0 : totalRefundAmountMap.get(key)));
					}
					record.add(
							NumberUtils.changeAmountLong(totalAmountMap.get(key) - totalRefundAmountMap.get(key), 3));
				}
				keyMap.put("amount"+key, (keyMap.get("amount"+key)==null?0:keyMap.get("amount"+key))+1);
			}
			if (resp.getOrderLat() != null) {
				ReportOrderQuotaVo orderLat = (ReportOrderQuotaVo) BeanUtil.copyProperties(ReportOrderQuotaVo.class,
						resp.getOrderLat());
				if (req.getCheckedOrderCount()) {
					record.add(orderLat.getOrderCount() + "");
				}
				if (keyMap.get("order"+key)!=null && keyMap.get("order"+key)!=1 && !"".equals(key) && (req.getCheckedOrderCumulation() || req.getCheckedOrderCumulationTurnover())) {
					totalOrderMap.put(key,
							orderLat.getOrderCount() + (totalOrderMap.get(key) == null ? 0 : totalOrderMap.get(key)));
				}
				if (req.getCheckedOrderCumulation()) {
					record.add(totalOrderMap.get(key) + "");
				}
				if (req.getCheckedOrderRefund()) {
					record.add(orderLat.getOrderRefund() + "");
				}
				if (req.getCheckedOrderTurnover()) {
					record.add(orderLat.getOrderTurnover() + "");
				}
				if (req.getCheckedOrderCumulationTurnover()) {
					if (keyMap.get("order"+key)!=null && keyMap.get("order"+key)!=1 && !"".equals(key)) {
						totalRefundOrderMap.put(key, orderLat.getOrderRefund()
								+ (totalRefundOrderMap.get(key) == null ? 0 : totalRefundOrderMap.get(key)));
					}
					record.add((totalOrderMap.get(key) - totalRefundOrderMap.get(key)) + "");
				}
				keyMap.put("order"+key, (keyMap.get("order"+key)==null?0:keyMap.get("order"+key))+1);
			}
			if (resp.getMerchantInfoResp() != null) {
				MerchantInfoRespVo merchantInfoRespVo = (MerchantInfoRespVo) BeanUtil
						.copyProperties(MerchantInfoRespVo.class, resp.getMerchantInfoResp());
				if (req.getCheckedMerchantSum()) {
					record.add(merchantInfoRespVo.getMerchantCount() + "");
				}
				if (req.getCheckedMerchantCancelContract()) {
					record.add(merchantInfoRespVo.getMerchantCancelContract() + "");
				}
				if (req.getCheckedMerchantCumulation()) {
					if (keyMap.get("merchant"+key)!=null && keyMap.get("merchant"+key)!=1 && !"".equals(key)) {
						totalMerchantMap.put(key, merchantInfoRespVo.getMerchantCount()
								+ (totalMerchantMap.get(key) == null ? 0 : totalMerchantMap.get(key)));
					}
					record.add(totalMerchantMap.get(key) + "");
				}
				if (req.getCheckedOutletCount()) {
					record.add(merchantInfoRespVo.getOutletSum() + "");
				}
				if ( req.getCheckedOutletCumulation()) {
					if (keyMap.get("merchant"+key)!=null && keyMap.get("merchant"+key)!=1  && !"".equals(key)) {
						totalOutlerMap.put(key, merchantInfoRespVo.getOutletSum()
								+ (totalOutlerMap.get(key) == null ? 0 : totalOutlerMap.get(key)));
					}
					record.add(totalOutlerMap.get(key) + "");
				}
				keyMap.put("merchant"+key, (keyMap.get("merchant"+key)==null?0:keyMap.get("merchant"+key))+1);
			}
			if (resp.getProductInfoResp() != null) {
				ProductInfoRespVo productInfoResp = (ProductInfoRespVo) BeanUtil.copyProperties(ProductInfoRespVo.class,
						resp.getProductInfoResp());
				if (req.getCheckedProductSum()) {
					record.add(productInfoResp.getProductCount() + "");
				}
				if (req.getCheckedProductCumulation()) {
					if (keyMap.get("product"+key)!=null && keyMap.get("product"+key)!=1 && !"".equals(key)) {
						totalProductMap.put(key, productInfoResp.getProductCount()
								+ (totalProductMap.get(key) == null ? 0 : totalProductMap.get(key)));
					}
					record.add(totalProductMap.get(key) + "");
				}
				if (req.getCheckedProductDownSum()) {
					record.add(productInfoResp.getProductDownSum() + "");
				}
				if ( req.getCheckedProductDownComulation()) {
					if (keyMap.get("product"+key)!=null && keyMap.get("product"+key)!=1  && !"".equals(key)) {
						totalDownProductMap.put(key, productInfoResp.getProductDownSum()
								+ (totalDownProductMap.get(key) == null ? 0 : totalDownProductMap.get(key)));
					}
					record.add(totalDownProductMap.get(key) + "");
				}
				keyMap.put("product"+key, (keyMap.get("product"+key)==null?0:keyMap.get("product"+key))+1);
			}
			if (resp.getMemberLat() != null) {
				MemberQuota memberLat = resp.getMemberLat();
				if (req.getCheckedMemberCount()) {
					record.add(memberLat.getConsumptionMemberCount() + "");
				}
				if (req.getCheckedMemberComulationCount()) {
					record.add(memberLat.getConsumptionMemberComulation() + "");
				}
			}
			data.add(record);
 		}
		return data;
	}
	
	
	/**
	 * 获取第一条记录
	 * @param req
	 * @param resp
	 * @param base
	 * @param key
	 */
	public void getFirstInfo(ReqPo req,Resp resp,BaseResp base,String key){
		Resp sumR=null;
		Resp sumF=null;
		Resp sumMerchant=null;
		Resp sumProduct=null;
		if(req.getCheckedAmountCumulationTurnover() || req.getCheckedAmountCumulation() || 
				req.getCheckedOrderCumulation() || req.getCheckedOrderCumulationTurnover() ||
				req.getCheckedAmountCumulationTurnover() || req.getCheckedOrderCumulationTurnover() || 
				req.getCheckedMerchantCumulation() || req.getCheckedOutletCumulation() || 
				req.getCheckedProductCumulation() || req.getCheckedProductDownComulation()){//需要计算累计信息 
			/*if(req.getCountType().equals(CountTypeEnum.day.name())){
				req.setEndDate(resp.getBase().getDay());
			}else if(req.getCountType().equals(CountTypeEnum.week.name())){
				req.setEndWeek(resp.getBase().getWeekTemp());//其实周不需要设置，controller传入的时候已经添加
			}else if(req.getCountType().equals(CountTypeEnum.month.name())){
				req.setEndMonth(resp.getBase().getMonthTemp());
			}else if(req.getCountType().equals(CountTypeEnum.year.name())){
				req.setEndYear(resp.getBase().getYear());
			}*/
			if(req.getCountType().equals(CountTypeEnum.day.name())){
				req.setEndDate(resp.getBase().getDay());
			}else if(req.getCountType().equals(CountTypeEnum.week.name())){
				req.setEndDate(DateSystemUtil.getWeekInfoLastDay(Integer.valueOf(resp.getBase().getYear()), Integer.valueOf(resp.getBase().getWeekTemp())));
			}else if(req.getCountType().equals(CountTypeEnum.month.name())){
				req.setEndDate(DateSystemUtil.getWeekInfoLastMonth(Integer.valueOf(resp.getBase().getYear()), Integer.valueOf(resp.getBase().getMonthTemp())));
			}else if(req.getCountType().equals(CountTypeEnum.year.name())){
//				req.setEndYear(resp.getBase().getYear());
			}
			
			if(base.getOrgCode()!=null){
				req.setOrgCode(resp.getBase().getOrgCode());
			}
			if(base.getMerchantId()!=null){
				req.setMerchantId(resp.getBase().getMerchantId());
			}
			if(base.getMerchantCategoryId()!=null){
				req.setMerchantCategoryId(resp.getBase().getMerchantCategoryId());
			}
						
			if((totalAmountMap.get(key)==null && totalOrderMap.get(key)==null && totalRefundAmountMap.get(key)==null && totalRefundOrderMap.get(key)==null )&& 
					( req.getCheckedAmountCumulationTurnover() || req.getCheckedAmountCumulation() || 
					req.getCheckedOrderCumulation() || req.getCheckedOrderCumulationTurnover() ||
					req.getCheckedAmountCumulationTurnover() || req.getCheckedOrderCumulationTurnover()) ){
				sumR=sumAmountOrOrder(req);
				if(sumR==null){
					sumR=new Resp();
					sumR.setOrderLat(new OrderQuota());
					sumR.setAmountLat(new AmountQuota());
				}
				if(sumR.getAmountLat().getOrderAmount()==null){
					sumR.getAmountLat().setOrderAmount(0l);
				}
				if(sumR.getOrderLat().getOrderCount()==null){
					sumR.getOrderLat().setOrderCount(0);
				} 
				if(req.getCheckedAmountCumulationTurnover() || req.getCheckedOrderCumulationTurnover() ){
					sumF=sumRefundAmountOrOrder(req);
					if(sumF==null){
						sumF=new Resp();
						sumF.setOrderLat(new OrderQuota());
						sumF.setAmountLat(new AmountQuota());
					}
					if(sumF.getAmountLat().getRefundAmount()==null){
						sumF.getAmountLat().setRefundAmount(0l);
					}
					if(sumF.getOrderLat().getOrderRefund()==null){
						sumF.getOrderLat().setOrderRefund(0);
					}
				}
				if (!"".equals(key) && (req.getCheckedAmountCumulationTurnover() || req.getCheckedAmountCumulation())) {
					totalAmountMap.put(key, sumR.getAmountLat().getOrderAmount());
				}
				if (!"".equals(key) && (req.getCheckedOrderCumulation() || req.getCheckedOrderCumulationTurnover())) {
					totalOrderMap.put(key,Long.valueOf(sumR.getOrderLat().getOrderCount()));
				}
				if (req.getCheckedAmountCumulationTurnover()) {
					totalRefundAmountMap.put(key,sumF.getAmountLat().getRefundAmount());
				}
				if (req.getCheckedOrderCumulationTurnover()) {
					totalRefundOrderMap.put(key,Long.valueOf(sumF.getOrderLat().getOrderRefund()));
				}
				keyMap.put("order"+key, 1);
				keyMap.put("amount"+key, 1);
			}
			
 			if(totalMerchantMap.get(key)==null && totalOutlerMap.get(key)==null && (req.getCheckedMerchantCumulation() || req.getCheckedOutletCumulation())){
				sumMerchant=sumMerchantCount(req);
				if(sumMerchant==null){
					sumMerchant=new Resp();
					sumMerchant.setMerchantInfoResp(new MerchantInfoResp());
					}
				if(sumMerchant.getMerchantInfoResp().getMerchantCumulation()==null){
					sumMerchant.getMerchantInfoResp().setMerchantCumulation(0);
				}
				if(sumMerchant.getMerchantInfoResp().getOutletCumulation()==null){
					sumMerchant.getMerchantInfoResp().setOutletCumulation(0);
				} 
				if (req.getCheckedMerchantCumulation() && !"".equals(key)) { 
					totalMerchantMap.put(key, Long.valueOf(sumMerchant.getMerchantInfoResp().getMerchantCumulation())); 
				}
				if (req.getCheckedOutletCumulation() && !"".equals(key)) { 
					totalOutlerMap.put(key,  Long.valueOf(sumMerchant.getMerchantInfoResp().getOutletCumulation())); 
				}
				keyMap.put("merchant"+key, 1);
			}
			 
			if(totalProductMap.get(key)==null && totalDownProductMap.get(key)==null && (req.getCheckedProductCumulation() || req.getCheckedProductDownComulation())){
				sumProduct=sumProductCount(req);
				if(sumProduct==null){
					sumProduct=new Resp();
					sumProduct.setProductInfoResp(new ProductInfoResp());
					}
				if(sumProduct.getProductInfoResp().getProductCumulation()==null){
					sumProduct.getProductInfoResp().setProductCumulation(0);
				}
				if(sumProduct.getProductInfoResp().getProductDownComulation()==null){
					sumProduct.getProductInfoResp().setProductDownComulation(0);
				} 
				if (req.getCheckedProductCumulation() && !"".equals(key)) { 
					totalProductMap.put(key, Long.valueOf(sumProduct.getProductInfoResp().getProductCumulation())); 
				}
				if (req.getCheckedProductDownComulation() && !"".equals(key)) { 
					totalDownProductMap.put(key, Long.valueOf(sumProduct.getProductInfoResp().getProductDownComulation())); 
				}
				keyMap.put("product"+key, 1);
			}
			
		} 
	}
	

	/**
	 * 组装excel数据data
	 * 
	 * @param pageVo
	 * @param reportReqVo
	 * @return
	 * @throws TException
	 * 
	 *             方法作废
	 */
	public List<List<String>> toExcelByPage(Page<Resp> page, ReqPo req) throws TException {
		List<List<String>> data = new ArrayList<List<String>>();
		page = this.findReportByPage(page, req);
		for (Resp resp : page.getResultsContent()) {
			List<String> record = new ArrayList<String>();
			BaseResp base = resp.getBase();
			// ReportBaseRespVo base = (ReportBaseRespVo)
			// BeanUtil.copyProperties(ReportBaseRespVo.class, resp.getBase());
			if (req.getCountType() != null) {
				if ("day".equalsIgnoreCase(req.getCountType())) {
					record.add(base.getDay());
				} else if ("week".equalsIgnoreCase(req.getCountType())) {
					record.add(base.getWeek());
				} else if ("month".equalsIgnoreCase(req.getCountType())) {
					record.add(base.getMonth());
				} else if ("year".equalsIgnoreCase(req.getCountType())) {
					record.add(base.getYear());
				}
			}
			// if(req.getClientId()!=null){
			// record.add(base.getClientId());
			// }
			// if(req.getPlatform()!=null){
			// record.add(base.getPlatform()==null?"":"社区银行");
			// }
			if (req.getOrgCode() != null) {
				// record.add(base.getOrgCode());
				record.add(base.getOrgName());
			}
			if (req.getOrderType() != null) {
				record.add(base.getOrderTypeName());
			}
			if (req.getPayType() != null) {
				record.add(base.getPayTypeName());
			}
			if (req.getCheckedMerchant()) {
				record.add(base.getMerchantName());
			}
			if (req.getCheckedMerchantCategory()) {
				record.add(base.getMerchantCategoryName());
			}
			if (LatitudeTupeEnum.AMOUNT.getDescription().equalsIgnoreCase(req.getLatitudeTupe())
					&& resp.getAmountLat() != null) {
				ReportAmountQuotaVo amountLat = (ReportAmountQuotaVo) BeanUtil.copyProperties(ReportAmountQuotaVo.class,
						resp.getAmountLat());
				if (req.getCheckedAmount()) {
					record.add(NumberUtils.changeAmountLong(amountLat.getOrderAmount(), 3));
				}
				if (req.getCheckedAmountCumulation()) {
					record.add(NumberUtils.changeAmountLong(amountLat.getOrderTotalPrice(), 3));
				}
				if (req.getCheckedAmountRefund()) {
					record.add(NumberUtils.changeAmountLong(amountLat.getRefundAmount(), 3));
				}
				if (req.getCheckedAmountTurnover()) {
					record.add(NumberUtils.changeAmountLong(amountLat.getAmountTurnover(), 3));
				}
				if (req.getCheckedAmountCumulationTurnover()) {
					record.add(NumberUtils.changeAmountLong(amountLat.getAmountCumulatiTurnover(), 3));
				}
			}
			if (LatitudeTupeEnum.ORDER.getDescription().equalsIgnoreCase(req.getLatitudeTupe())
					&& resp.getOrderLat() != null) {
				ReportOrderQuotaVo orderLat = (ReportOrderQuotaVo) BeanUtil.copyProperties(ReportOrderQuotaVo.class,
						resp.getOrderLat());
				if (req.getCheckedOrderCount()) {
					record.add(orderLat.getOrderCount() + "");
				}
				if (req.getCheckedOrderCumulation()) {
					record.add(orderLat.getOrderUmulation() + "");
				}
				if (req.getCheckedOrderRefund()) {
					record.add(orderLat.getOrderRefund() + "");
				}
				if (req.getCheckedOrderTurnover()) {
					record.add(orderLat.getOrderTurnover() + "");
				}
				if (req.getCheckedOrderCumulationTurnover()) {
					record.add(orderLat.getOrderCumulationTurnover() + "");
				}
			}
			if (LatitudeTupeEnum.MERCHANT.getDescription().equalsIgnoreCase(req.getLatitudeTupe())
					&& resp.getMerchantInfoResp() != null) {
				MerchantInfoRespVo merchantInfoRespVo = (MerchantInfoRespVo) BeanUtil
						.copyProperties(MerchantInfoRespVo.class, resp.getMerchantInfoResp());
				if (req.getCheckedMerchantSum()) {
					record.add(merchantInfoRespVo.getMerchantCount() + "");
				}
				if (req.getCheckedMerchantCancelContract()) {
					record.add(merchantInfoRespVo.getMerchantCancelContract() + "");
				}
				if (req.getCheckedMerchantCumulation()) {
					record.add(merchantInfoRespVo.getMerchantCumulation() + "");
				}
				if (req.getCheckedOutletCount()) {
					record.add(merchantInfoRespVo.getOutletSum() + "");
				}
				if (req.getCheckedOutletCumulation()) {
					record.add(merchantInfoRespVo.getOutletCumulation() + "");
				}
			}
			if (LatitudeTupeEnum.PRODUCT.getDescription().equalsIgnoreCase(req.getLatitudeTupe())
					&& resp.getProductInfoResp() != null) {
				ProductInfoRespVo productInfoResp = (ProductInfoRespVo) BeanUtil.copyProperties(ProductInfoRespVo.class,
						resp.getProductInfoResp());
				if (req.getCheckedProductSum()) {
					record.add(productInfoResp.getProductCount() + "");
				}
				if (req.getCheckedProductCumulation()) {
					record.add(productInfoResp.getProductCumulation() + "");
				}
				if (req.getCheckedProductDownSum()) {
					record.add(productInfoResp.getProductDownSum() + "");
				}
				if (req.getCheckedProductDownComulation()) {
					record.add(productInfoResp.getProductDownComulation() + "");
				}
			}
			if (LatitudeTupeEnum.MERMBER.getDescription().equalsIgnoreCase(req.getLatitudeTupe())
					&& resp.getMemberLat() != null) {
				ReportMemberQuotaVo memberLat = (ReportMemberQuotaVo) BeanUtil.copyProperties(MemberQuota.class,
						resp.getMemberLat());
				if (req.getCheckedMemberCount()) {
					record.add(memberLat.getConsumptionMemberCount() + "");
				}
				if (req.getCheckedMemberComulationCount()) {
					record.add(memberLat.getConsumptionMemberComulation() + "");
				}
			}
			data.add(record);
		}
		return data;
	}
}