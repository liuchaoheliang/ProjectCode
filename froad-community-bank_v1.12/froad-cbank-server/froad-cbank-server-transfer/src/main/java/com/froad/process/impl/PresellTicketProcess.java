package com.froad.process.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.common.qrcode.QrCodeGenerateService;
import com.froad.common.qrcode.QrCodeRequestVo;
import com.froad.common.qrcode.QrCodeResponseVo;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.entity.ProductPresell;
import com.froad.db.ahui.entity.TransDto;
import com.froad.db.ahui.mappers.MerchantExMapper;
import com.froad.db.ahui.mappers.MerchantOutletMapper;
import com.froad.db.ahui.mappers.OutletBankMapper;
import com.froad.db.ahui.mappers.ProductMapper;
import com.froad.db.ahui.mappers.TransMapper;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.enums.ModuleID;
import com.froad.enums.QrCodeType;
import com.froad.enums.QrcodePrefix;
import com.froad.enums.ResultCode;
import com.froad.enums.SubOrderType;
import com.froad.enums.TicketStatus;
import com.froad.enums.TransferTypeEnum;
import com.froad.fft.persistent.entity.OutleBank;
import com.froad.logback.LogCvt;
import com.froad.po.Ticket;
import com.froad.process.AbstractProcess;
import com.froad.util.Arith;
import com.froad.util.Constans;
import com.froad.util.MemberUtil;
import com.froad.util.MongoTableName;
import com.froad.util.SimpleID;

/**
 *  生成待成团的卷数据迁移
  * @ClassName: TicketProcess
  * @Description: TODO
  * @author share 2015年5月2日
  * @modify share 2015年5月2日
 */
public class PresellTicketProcess extends AbstractProcess {

	private SimpleID simpleId = new SimpleID(ModuleID.ticket);
	
	public PresellTicketProcess(String name, ProcessServiceConfig config) {
		super(name, config);
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	public void process() {
		// TODO Auto-generated method stub
		TransMapper transMapper = ahSqlSession.getMapper(TransMapper.class);
		MerchantExMapper merchantMapper = ahSqlSession.getMapper(MerchantExMapper.class);
		TransferMapper tsfMapper = sqlSession.getMapper(TransferMapper.class);
		MerchantOutletMapper merchantOutletMapper = ahSqlSession.getMapper(MerchantOutletMapper.class);
		ProductMapper productMapper = ahSqlSession.getMapper(ProductMapper.class);
		OutletBankMapper outletBankMapper = ahSqlSession.getMapper(OutletBankMapper.class);
		
		// 初始化内存数据
		Map<String, String> merMap = new HashMap<String, String>();
		Map<String, String> proMap = new HashMap<String, String>();
		Map<String, String> orderMap = new HashMap<String, String>();
		Map<String, String> outletMap = new HashMap<String, String>();
		/**
		 *  初始化数据到内存信息
		 */
		List<Transfer> merList = tsfMapper.queryGroupList(TransferTypeEnum.merchant_id);
		if(merList != null && merList.size() >0) {
			for(Transfer tf : merList) {
				merMap.put(tf.getOldId()  , tf.getNewId());
			}
		}
		List<Transfer> proList = tsfMapper.queryGroupList(TransferTypeEnum.product_id);
		if(proList != null && proList.size() >0) {
			for(Transfer tf : proList) {
				proMap.put(tf.getOldId() , tf.getNewId());
			}
		}
		List<Transfer> outletList = tsfMapper.queryGroupList(TransferTypeEnum.outlet_id);
		if(outletList != null && outletList.size() >0) {
			for(Transfer tf : outletList) {
				outletMap.put(tf.getOldId() , tf.getNewId());
			}
		}
		List<Transfer> orderList = tsfMapper.queryGroupList(TransferTypeEnum.order_id);
		if(orderList != null && orderList.size() >0) {
			for(Transfer tf : orderList) {
				orderMap.put(tf.getOldId() , tf.getNewId());
			}
		}
		
		
		List<TransDto> transList = transMapper.queryForPresellTicke();
		/**
		 *  移植所有的未生成卷的订单信息
		 */
		for(TransDto tran : transList){
			// mongo卷对象
			Ticket ticket = new Ticket();
			// 创建时间
			long createTime = tran.getCreateTime().getTime();
			// 客户端ID
			String clientId = Constans.clientId;
			// 类型
			String transType = SubOrderType.presell_org.getCode();
			// 门店信息
			OutleBank outletBank = outletBankMapper.selectByTransId(tran.getId());
			String outletId = outletMap.get(outletBank.getOutleId()+"");
			String outletName = merchantOutletMapper.selectById(outletBank.getOutleId()).getName();
			// 处理卷的特殊信息字段
			handlePresellTicket(ticket, tran, transType,outletBank);
			// 订单ID
			String orderIds = orderMap.get(String.valueOf(tran.getId()));
			if(orderIds == null){
				LogCvt.error("退款订单ID无对应中间表记录:" +tran.getId());
				continue;
			}
			String orderId = orderIds.split(",")[0];
			// 子订单ID
			String subOrderId = orderIds.split(",")[1];
			// 会员号
			long memberCode = tran.getMemberCode();
			// 会员名
			String memberName = tran.getDeliveryName();
			// 卷号
			String ticketId = simpleId.nextId();
			// image卷二维码
			String image = generateQrcode(ticketId, clientId);
			// 卷提货手机号
			String smsNumber = tran.getPhone();
			// 商品ID
			long proId = tran.getProductId();
			String productId = proMap.get(proId+"");
			// 商品名称
			String productName = tran.getProductName();
			// 商品数量
			int quantity = tran.getQuantity();
			// 卷状态
			ProductPresell presell = productMapper.selectPresellProductById(proId);
			if(presell == null){
				LogCvt.error("预售商品信息不存在,ID="+proId);
				continue;
			}
			long expireTime = presell.getTicketExpireTime().getTime();
			// 待发卷
			String status = TicketStatus.init.getCode();
			
			// 商户ID
			String merchantId = merMap.get(tran.getMerchantId()+"");
			// 商户名称
			String merchantName = merchantMapper.selectById(tran.getMerchantId()).getName();
			// 卷退款信息
			
			// Ticket卷表添加到Mongo
			ticket.setClientId(clientId);
			ticket.setCreateTime(createTime);
			ticket.setExpireTime(expireTime);
			ticket.setImage(image);
			ticket.setMemberCode(memberCode+"");
			ticket.setMemberName(memberName);
			ticket.setMerchantId(merchantId);
			ticket.setMerchantName(merchantName);
			ticket.setMobile(tran.getPhone());
			ticket.setOrderId(orderId);
			ticket.setSubOrderId(subOrderId);
			ticket.setOutletId(outletId);
			ticket.setOutletName(outletName);
			ticket.setProductId(productId);
			ticket.setProductName(productName);
			ticket.setQuantity(quantity);
			ticket.setStatus(status);
			ticket.setTicketId(ticketId);
			ticket.setType(transType);
			ticket.setPrice(Arith.mul(Double.parseDouble(tran.getSingle()), 1000));
			mongo.add(ticket, MongoTableName.CB_TICKET);
			
		}
		
		
		
	}
	
	/**
	 *  预售卷的特殊处理
	  * @Title: handlePresellTicket
	  * @Description: TODO
	  * @author: share 2015年5月2日
	  * @modify: share 2015年5月2日
	  * @param @param securityTicket
	  * @param @param ticket
	  * @param @param transType    
	  * @return void    
	  * @throws
	 */
	private void handlePresellTicket(Ticket ticket, TransDto trans,String transType,OutleBank outletBank) {
		// 预售第一机构码
		String forgCode = "340000";
		// 第二级机构码
		String sorgCode = null;
		// 第三级机构码
		String torgCode = null;
		// 预售提货网点和名称
		String orgCode = null;
		String orgName = null;
		torgCode = getOrgCode(outletBank.getBankOrg());
		orgCode = torgCode;
		orgName = outletBank.getOrgName();
		sorgCode = getOrgCode(outletBank.getParentBankOrg());
		// 如果是预售的卷商品
		if(forgCode.equals(sorgCode)){
			sorgCode = torgCode;
			torgCode = null;
		}
		ticket.setForgCode(forgCode);
		ticket.setSorgCode(sorgCode);
		ticket.setTorgCode(torgCode);
		ticket.setOrgCode(orgCode);
		ticket.setOrgName(orgName);
	}
	
	
	/**
	 * 生成券二维码
	 * 
	 * @param qrCodeLogic
	 * @param ticketId
	 * @param clientId
	 */
	private String generateQrcode(String ticketId, String clientId){
		StringBuffer keyword = null;
		QrCodeRequestVo request = null;
		QrCodeResponseVo response = null;
		String imageUrl = null;
		
		try {
			keyword = new StringBuffer();
			keyword.append(QrCodeType.TICKET.getCode());
			keyword.append(ticketId);
			
			request = new QrCodeRequestVo();
			request.setClientId(clientId);
			request.setKeyword(keyword.toString());
			
			response = new QrCodeGenerateService().generateQrCode(request);
			
			if (response.getResultCode().equals(ResultCode.success.getCode())){
				imageUrl = response.getUrl();
				LogCvt.debug(new StringBuffer(keyword).append("二维码生成成功.").toString());
			} else {
				LogCvt.debug(new StringBuffer(keyword).append("二维码生成失败. ").append(response.getResultDesc()).toString());
			}
		} catch (Exception e){
			LogCvt.error("券号二维码", e);
		}
		
		return imageUrl;
	}
	
	
	private String getOrgCode(String sourceCode){
		if(sourceCode == null){
			return sourceCode;
		}
		return sourceCode.split("_")[1];
	}

}

