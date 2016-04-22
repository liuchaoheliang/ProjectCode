package com.froad.process.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.ibatis.session.SqlSession;

import com.froad.common.qrcode.QrCodeGenerateService;
import com.froad.common.qrcode.QrCodeRequestVo;
import com.froad.common.qrcode.QrCodeResponseVo;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.entity.TransDto;
import com.froad.db.ahui.mappers.MerchantExMapper;
import com.froad.db.ahui.mappers.MerchantOutletMapper;
import com.froad.db.ahui.mappers.OutletBankMapper;
import com.froad.db.ahui.mappers.PayMapper;
import com.froad.db.ahui.mappers.SysUserMapper;
import com.froad.db.ahui.mappers.TicketMapper;
import com.froad.db.ahui.mappers.TransMapper;
import com.froad.db.ahui.mappers.TransRefundsMapper;
import com.froad.db.ahui.mysql.AhMyBatisManager;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.enums.QrCodeType;
import com.froad.enums.QrcodePrefix;
import com.froad.enums.ResultCode;
import com.froad.enums.SubOrderType;
import com.froad.enums.TicketStatus;
import com.froad.enums.TransferTypeEnum;
import com.froad.fft.persistent.common.enums.TransType;
import com.froad.fft.persistent.entity.MerchantOutlet;
import com.froad.fft.persistent.entity.OutleBank;
import com.froad.fft.persistent.entity.Pay;
import com.froad.fft.persistent.entity.TransRefunds;
import com.froad.fft.persistent.entity.TransSecurityTicket;
import com.froad.logback.LogCvt;
import com.froad.po.Ticket;
import com.froad.process.AbstractProcess;
import com.froad.util.Arith;
import com.froad.util.Constans;
import com.froad.util.DesUtils;
import com.froad.util.MemberUtil;
import com.froad.util.MongoTableName;

/**
 *  卷表数据迁移
  * @ClassName: TicketProcess
  * @Description: TODO
  * @author share 2015年5月2日
  * @modify share 2015年5月2日
 */
public class TicketProcess extends AbstractProcess {

	public TicketProcess(String name, ProcessServiceConfig config) {
		super(name, config);
		// TODO Auto-generated constructor stub
	}
	
	// 初始化内存数据
	Map<String, String> merMap = new HashMap<String, String>();
	Map<String, String> proMap = new HashMap<String, String>();
	Map<String, String> merUserMap = new HashMap<String, String>();
	Map<String, String> orderMap = new HashMap<String, String>();
	Map<String, String> outletMap = new HashMap<String, String>();
	Map<String, String> refundsMap = new HashMap<String, String>();

	@Override
	public void process() {
		// TODO Auto-generated method stub
		TicketMapper ticektMapper = ahSqlSession.getMapper(TicketMapper.class);
		TransferMapper tsfMapper = sqlSession.getMapper(TransferMapper.class);
		
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
		List<Transfer> merUserList = tsfMapper.queryGroupList(TransferTypeEnum.merchant_user_id);
		if(merUserList != null && merUserList.size() >0) {
			for(Transfer tf : merUserList) {
				merUserMap.put(tf.getOldId()  , tf.getNewId());
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
		List<Transfer> refundsList = tsfMapper.queryGroupList(TransferTypeEnum.refund_id);
		if(refundsList != null && refundsList.size() >0) {
			for(Transfer tf : refundsList) {
				refundsMap.put(tf.getOldId() , tf.getNewId());
			}
		}
		
		
		ExecutorService pool = Executors.newFixedThreadPool(20);
		
		/**
		 *  移植所有的卷信息
		 */
		List<TransSecurityTicket> ticketsList = ticektMapper.queryAll();
		for(final TransSecurityTicket securityTicket : ticketsList){
			
			pool.submit(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					createTicket(securityTicket);
				}
			});
		}
		pool.shutdown();
		LogCvt.info("卷信息转移线程处理已完成");
		
	}
	
	
	public void createTicket(TransSecurityTicket securityTicket ){
		SqlSession session = null;
		try {
			session = AhMyBatisManager.getSqlSessionFactory().openSession();
			TransMapper transMapper = session.getMapper(TransMapper.class);
			MerchantExMapper merchantMapper = session.getMapper(MerchantExMapper.class);
			SysUserMapper sysUserExMapper = session.getMapper(SysUserMapper.class);
			MerchantOutletMapper merchantOutletMapper = session.getMapper(MerchantOutletMapper.class);
			TransRefundsMapper transRefundsMapper = session.getMapper(TransRefundsMapper.class);
			OutletBankMapper outletBankMapper = session.getMapper(OutletBankMapper.class);
			PayMapper payMapper = session.getMapper(PayMapper.class);
			// mongo卷对象
			Ticket ticket = new Ticket();
			// 客户端ID
			String clientId = Constans.clientId;
			// 类型
			String transType = getTicketType(securityTicket.getTransType());
			if(transType == null){
				LogCvt.error("无效的卷类型："+securityTicket.getId());
				return;
			}
			// 门店ID
			String outletId = null;
			String outletName = null;
			// 订单信息
			TransDto trans = transMapper.queryById(securityTicket.getTransId());
			if(SubOrderType.presell_org.getCode().equals(transType)){
				OutleBank outletBank = outletBankMapper.selectByTransId(trans.getId());
				outletId = outletMap.get(outletBank.getOutleId()+"");
				outletName = merchantOutletMapper.selectById(outletBank.getOutleId()).getName();
				// 处理卷的特殊信息字段
				handlePresellTicket(ticket, trans, transType,outletBank);
				
			}else{
				outletId = outletMap.get(trans.getOutletId()+"");
				MerchantOutlet outlet = merchantOutletMapper.selectById(trans.getOutletId());
				if(outlet != null){
					outletName = outlet.getName();
				}else{
					LogCvt.error("暂无门店信息ID："+trans.getOutletId());
				}
			}
			// 创建时间
			long createTime = trans.getCreateTime().getTime() + getXxTime(securityTicket.getId());
			// 订单ID
			String orderIds = orderMap.get(String.valueOf(trans.getId()));
			if(orderIds == null){
				LogCvt.error("退款订单ID无对应中间表记录:" +trans.getId());
				return;
			}
			String orderId = orderIds.split(",")[0];
			// 子订单ID
			String subOrderId = orderIds.split(",")[1];
			// 会员号
			long memberCode = securityTicket.getMemberCode();
			// 会员名
			String memberName = trans.getDeliveryName();
			// 卷号
			String ticketId = decrypt(securityTicket.getSecuritiesNo());
			// image卷二维码
			String image = generateQrcode(ticketId, clientId);
			// 商品ID
			long proId = trans.getProductId();
			String productId = proMap.get(proId+"");
			// 商品名称
			String productName = trans.getProductName();
			// 商品数量
			int quantity = "1".equals(transType)?1:trans.getQuantity();
			// 卷状态
			long smsTime = securityTicket.getSmsTime().getTime();
			long expireTime = securityTicket.getExpireTime().getTime();
			boolean isConsume = securityTicket.getIsConsume();
			String state = securityTicket.getState();
			// 消费时间
			Long consumeTime = securityTicket.getConsumeTime()==null?null:securityTicket.getConsumeTime().getTime();
			String status = "";
			if("2".equals(state)){
				status = TicketStatus.refunded.getCode();
			}else if(isConsume && consumeTime != null){
				status = TicketStatus.consumed.getCode();
			}else if(expireTime < System.currentTimeMillis() && "0".equals(state)){
				status = TicketStatus.expired.getCode();
			}else if(smsTime < System.currentTimeMillis()){
				status = TicketStatus.sent.getCode();
			}else{
				LogCvt.info("其他状态设置为，已发送卷ID："+securityTicket.getId());
				status = TicketStatus.sent.getCode();
			}
			// 团购商户用户ID
			Long merchantUserId = null;
			String merchantUserName = null;
			if(SubOrderType.group_merchant.getCode().equals(transType) && TicketStatus.consumed.getCode().equals(status)){
				merchantUserId = Long.parseLong(merUserMap.get(securityTicket.getSysUserId()+""));
				merchantUserName = sysUserExMapper.selectSysUserById(securityTicket.getSysUserId()).getUsername();
			}
			// 商户ID
			String merchantId = merMap.get(securityTicket.getMerchantId()+"");
			// 商户名称
			String merchantName = merchantMapper.selectById(securityTicket.getMerchantId()).getName();
			// 卷退款信息
			String refundId = null;
			Long refundTime = null;
			if(TicketStatus.refunded.getCode().equals(status)){
				long id = securityTicket.getId();
				TransRefunds refund = transRefundsMapper.getTransRefundsByTickeId(id);
				if(refund != null){
					refundId = refundsMap.get(refund.getId()+"");
					refundTime = refund.getCreateTime().getTime();	
				}else{
					Pay pay = payMapper.queryLastOrderPayInfo(trans.getId());
					if(pay != null){
						refundId = refundsMap.get(pay.getId()+"");
						refundTime = pay.getUpdateTime().getTime();	
					}else{
						LogCvt.error("转移退款卷，获取退款ID异常,卷ID："+id);
					}
				}
			}
			
			// Ticket卷表添加到Mongo
			ticket.setClientId(clientId);
			ticket.setConsumeTime(consumeTime);
			ticket.setCreateTime(createTime);
			ticket.setExpireTime(expireTime);
			ticket.setImage(image);
			ticket.setMemberCode(memberCode+"");
			ticket.setMemberName(memberName);
			ticket.setMerchantId(merchantId);
			ticket.setMerchantName(merchantName);
			ticket.setMerchantUserId(merchantUserId);
			ticket.setMerchantUserName(merchantUserName);
			ticket.setMobile(trans.getPhone());
			ticket.setOrderId(orderId);
			ticket.setSubOrderId(subOrderId);
			ticket.setOutletId(outletId);
			ticket.setOutletName(outletName);
			ticket.setProductId(productId);
			ticket.setProductName(productName);
			ticket.setQuantity(quantity);
			ticket.setRefundId(refundId);
			ticket.setRefundTime(refundTime);
			ticket.setStatus(status);
			ticket.setTicketId(ticketId);
			ticket.setType(transType);
			ticket.setPrice(Arith.mul(Double.parseDouble(trans.getSingle()), 1000));
			mongo.add(ticket, MongoTableName.CB_TICKET);
		} catch (Exception e) {
			// TODO: handle exception
			LogCvt.info("卷信息转移异常",e);
		}finally{
			if(session != null){
				session.close();
			}
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
			keyword.append(QrCodeType.TICKET);
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
	
	private String getTicketType(TransType type){
		
		if("02".equals(type.getCode())){
			// 团购
			return SubOrderType.group_merchant.getCode();
		}else if("08".equals(type.getCode())){
			// 预售
			return SubOrderType.presell_org.getCode();
		}
		return null;
	}
	
	private String getOrgCode(String sourceCode){
		if(sourceCode == null){
			return sourceCode;
		}
		return sourceCode.split("_")[1];
	}
	
	private static String decrypt(String str){
        if (str == null || "".equals(str)){
            return null;
        }
        try
        {
            DesUtils desUtils = new DesUtils();
            String ticketNo = desUtils.decrypt(str);
            if(ticketNo.startsWith("ah")){
            	return ticketNo.substring(2);
            }
            return ticketNo;
        }
        catch(Exception e)
        {
           LogCvt.error("券号解密异常", e);
        }
        return null;
    }
	
	public int getXxTime(Long id){
		try {
			String str = id.toString();
			if(str.length() > 3){
				str = str.substring(str.length()-3);
			}
			return Integer.parseInt(str);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
		
	}
	
	public static void main(String[] args) throws ParseException {
//		System.out.println(decrypt("907e5e80b5f0a3b393d3bda94ed5dc55"));
		
		
	}

}

