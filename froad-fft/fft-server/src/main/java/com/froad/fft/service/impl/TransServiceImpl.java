package com.froad.fft.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.froad.fft.bean.Result;
import com.froad.fft.dto.SmsDto;
import com.froad.fft.persistent.api.PayMapper;
import com.froad.fft.persistent.api.PresellDeliveryMapper;
import com.froad.fft.persistent.api.ProductMapper;
import com.froad.fft.persistent.api.ProductPresellMapper;
import com.froad.fft.persistent.api.SmsContentMapper;
import com.froad.fft.persistent.api.TransDetailsMapper;
import com.froad.fft.persistent.api.TransMapper;
import com.froad.fft.persistent.api.TransSecurityTicketMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.common.enums.ClusterState;
import com.froad.fft.persistent.common.enums.PayTypeDetails;
import com.froad.fft.persistent.common.enums.SmsType;
import com.froad.fft.persistent.common.enums.TransPayState;
import com.froad.fft.persistent.entity.Pay;
import com.froad.fft.persistent.entity.PresellDelivery;
import com.froad.fft.persistent.entity.Product;
import com.froad.fft.persistent.entity.ProductPresell;
import com.froad.fft.persistent.entity.ProductPresell.ClusterType;
import com.froad.fft.persistent.entity.SysClient;
import com.froad.fft.persistent.entity.Trans;
import com.froad.fft.persistent.entity.TransDetails;
import com.froad.fft.persistent.entity.TransSecurityTicket;
import com.froad.fft.service.SysClientService;
import com.froad.fft.service.TransCoreService;
import com.froad.fft.service.TransService;
import com.froad.fft.thirdparty.dto.request.sms.SmsBean;
import com.froad.fft.thirdparty.request.sms.impl.SMSMessageFuncImpl;
import com.froad.fft.util.SerialNumberUtil;

@Service("transServiceImpl")
public class TransServiceImpl implements TransService {

	private static Logger logger = Logger.getLogger(TransServiceImpl.class);
	
	private static final SimpleDateFormat displayFormat = new SimpleDateFormat(
	"MM月dd日 HH时mm分");

	@Resource
	private TransMapper transMapper;

	@Resource
	private TransDetailsMapper transDetailsMapper;
	
	@Resource
	private PayMapper payMapper;
	
	@Resource
	private SysClientService sysClientService;

	@Resource
	private ProductMapper productMapper;

	@Resource
	private ProductPresellMapper productPresellMapper;
	
	@Resource
	private TransSecurityTicketMapper transSecurityTicketMapper;
	
	@Resource
	private PresellDeliveryMapper presellDeliveryMapper;
	
	@Resource
	private SmsContentMapper smsContentMapper;
	
	@Resource
	private SMSMessageFuncImpl sMSMessageFuncImpl;
	
	@Resource
	private TransCoreService transCoreService; 

	@Override
	public Trans findTransBySn(String sn) {
		return transMapper.selectBySn(sn);
	}

	public Trans findTransById(Long id) {
		return transMapper.selectTransById(id);
	}

	@Override
	public Page findTransByPage(Page page) {
		if (page == null) {
			page = new Page();
		}
		page.setResultsContent(transMapper.selectTransByPage(page));
		page.setTotalCount(transMapper.selectTransByPageCount(page));
		return page;
	}
	
	
	/**
	  * 方法描述：精品预售成团成功后的交易处理
	  * @param: List<Trans> 精品预售类型、处理中的已支付交易
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 12, 2014 5:14:19 PM
	  */
	private void doCluster(List<Trans> transList){
		if(transList==null||transList.size()==0){
			logger.info("没有已支付完成的精品预售交易记录");
			return;
		}
		List<Long> sendList=new ArrayList<Long>();
		for (int i = 0; i < transList.size(); i++) {
			this.sendPresellMessage(transList.get(i));
			sendList.add(transList.get(i).getId());
		}
		transMapper.updateStateToSuccessByIds(sendList);
	}
	
	

	/**
	 * 方法描述：生成并发送精品预售提货短信,同时添加短信日志
	 * 
	 * @param: Trans
	 * @return: boolean 发送成功|发送失败
	 * @version: 1.0
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Mar 12, 2014 4:03:01 PM
	 */
	private Result sendPresellMessage(Trans trans){
		List<TransDetails> details=trans.getDetailsList();
		if(details==null||details.size()==0){
			logger.error("交易详情为空，交易号："+trans.getId());
			return new Result(Result.FAIL,"交易的商品明细为空");
		}
		TransDetails td=details.get(0);
		ProductPresell presell=productPresellMapper.selectByProductId(td.getProductId());
		String beginTime=null;
		String endTime=null;
		beginTime=displayFormat.format(presell.getDeliveryStartTime());
		endTime=displayFormat.format(presell.getDeliveryEndTime());
		
		SysClient client=sysClientService.findSysClientById(trans.getClientId());
		TransSecurityTicket ticketBean=new TransSecurityTicket();
		ticketBean.setExpireTime(presell.getDeliveryEndTime());
		ticketBean.setIsConsume(false);
		ticketBean.setMemberCode(trans.getMemberCode());
		String securityNo=SerialNumberUtil.buildSecuritiesNo(client.getNumber());
		ticketBean.setSecuritiesNo(securityNo);
		ticketBean.setSmsNumber(1);
		ticketBean.setSmsTime(new Date());
		ticketBean.setSysUserId(0l);
		ticketBean.setTransId(trans.getId());
		ticketBean.setTransType(trans.getType());
		ticketBean.setMerchantId(td.getSupplyMerchantId());
		try {
			transSecurityTicketMapper.saveTransSecurityTicket(ticketBean);
		} catch (Exception e) {
			logger.error("添加精品预售消费券时出现异常",e);
			return new Result(Result.FAIL,"添加精品预售消费券时出现异常");
		}
		PresellDelivery delivery=presellDeliveryMapper.selectPresellDeliveryById(trans.getDeliveryId());
		
		String productName=td.getProductName();
		String quantity=td.getQuantity()+"";
		String[] msg={productName,quantity,securityNo,beginTime,endTime,delivery.getAddress()};
		SmsBean smsBean = new SmsBean(SmsType.presellDelivery, client.getId(), trans.getPhone(), msg, null,"",false);
		SmsDto smsDto = sMSMessageFuncImpl.sendSMSMessage(smsBean);
		String code=smsDto.isFlag()?Result.SUCCESS:Result.FAIL;
		return new Result(code,smsDto.getMsg());
	}

	/**
	 * 方法描述：生成并发送精品预售成团失败短信,同时添加短信日志
	 * 
	 * @param: Trans
	 * @return: boolean 发送成功|发送失败
	 * @version: 1.0
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Mar 12, 2014 4:03:01 PM
	 */
	private boolean sendPresellFailedMessage(Trans trans) {
		List<TransDetails> details = trans.getDetailsList();
		if(details==null||details.size()==0){
			logger.error("交易详情为空，交易号："+trans.getId());
			return false;
		}
		String productName=details.get(0).getProductName();
		String quantity=details.get(0).getQuantity()+"";
		String[] msg = {productName,quantity};
		SysClient client=sysClientService.findSysClientById(trans.getClientId());
		SmsBean smsBean = new SmsBean(SmsType.presellClusterFail, 
				client.getId(), trans.getPhone(), msg, null,"",false);
		SmsDto smsDto = sMSMessageFuncImpl.sendSMSMessage(smsBean);
		return smsDto.isFlag();
	}

	@Override
	public void cluster() {
		List<Trans> list=transMapper.selectPresellTrans();
		this.doCluster(list);
	}

	@Override
	public Result clusterByManager(Long productId) {
		Product product=productMapper.selectProductById(productId);
		if(product==null){
			return new Result(Result.FAIL,"商品不存在，商品编号："+productId);
		}
		ProductPresell presell=productPresellMapper.selectProductPresellById(product.getProductPresellId());
		if(presell==null){
			return new Result(Result.FAIL,"商品的售预属性不存在，商品编号："+productId);
		}
		if(ClusterState.success.equals(presell.getClusterState())){
			return new Result(Result.FAIL,"该商品已经成团");
		}
		if(ClusterState.fail.equals(presell.getClusterState())){
			return new Result(Result.FAIL,"该商品已经成团失败，不能进行此项操作");
		}
		ProductPresell tmpPresell=new ProductPresell();
		tmpPresell.setId(presell.getId());
		if(presell.getTrueBuyerNumber()<presell.getClusteringNumber()){
			int virtualNumber=this.makeVirtualNumber(presell.getTrueBuyerNumber(), presell.getClusteringNumber());
			tmpPresell.setVirtualBuyerNumber(presell.getVirtualBuyerNumber()+virtualNumber);
		}
		tmpPresell.setClusterState(ClusterState.success);
		tmpPresell.setClusterType(ClusterType.manual);
		productPresellMapper.updateProductPresellById(tmpPresell);//更新预售商品信息
		if(presell.getEndTime().compareTo(new Date())<=0){
			List<Trans> transList=transMapper.selectPresellTransByProductId(productId);
			this.doCluster(transList);//成团之后的处理
		}
		return new Result(Result.SUCCESS,"预售商品成功成团，操作成功");
	
	}

	@Override
	public Result clusterFailedByManager(Long productId) {
		Product product=productMapper.selectProductById(productId);
		if(product==null){
			return new Result(Result.FAIL,"商品不存在，商品编号："+productId);
		}
		ProductPresell presell=productPresellMapper.selectProductPresellById(product.getProductPresellId());
		if(presell==null){
			return new Result(Result.FAIL,"商品的预售属性为空，商品编号："+productId);
		}
		if(ClusterState.fail.equals(presell.getClusterState())){
			return new Result(Result.FAIL,"该商品已经成团失败，请不要重复操作");
		}
		if(ClusterState.success.equals(presell.getClusterState())){
			return new Result(Result.FAIL,"该商品已经成功成团，不能进行此项操作");
		}
		Date now =new Date();
		if(now.before(presell.getEndTime())){
			return new Result(Result.FAIL,"预售还没有到达结束时间");
		}
		ProductPresell tmpPresell=new ProductPresell();
		tmpPresell.setId(presell.getId());
		presell.setClusterState(ClusterState.fail);
		presell.setClusterType(ClusterType.manual);
		productPresellMapper.updateProductPresellById(presell);
		List<Trans> transList=transMapper.selectPresellTransByProductId(productId);
		if(transList==null||transList.size()==0){
			return new Result(Result.SUCCESS,"操作成功");
		}
		String refundReason="预售商品成团失败，商品编号["+productId+"]，发起退款。";
		Trans trans = null;
		for (int i = 0; i < transList.size(); i++) {
			trans=transList.get(i);
			transCoreService.doRefund(trans, refundReason);
			//给成功付款或付积分的用户发短信
			this.sendPresellFailedMessage(trans);
		}
		return new Result(Result.SUCCESS,"操作成功");
	}
	
	/**
	  * 方法描述：生成虚拟参团人数
	  * @param: realSaleNumber 实际销售数量
	  * @param: clusterNumber 最低成团数量
	  * @param: max 最大人数
	  * @return: 虚拟参团人数
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 12, 2014 5:45:06 PM
	  */
	private Integer makeVirtualNumber(Integer realSaleNumber,Integer clusterNumber){
		Random random=new Random();
		int min=clusterNumber-realSaleNumber;
		if(min<1){//已成团
			return 0;
		}
		int randNum=random.nextInt(clusterNumber*10);
		if(randNum>=min){
			return randNum;
		}
		return this.makeVirtualNumber(realSaleNumber, clusterNumber);
	}

	@Override
	public void doExceptionProductTrans() {
		// TODO 处理购买商品的异常交易 退分、退款
		
	}

	@Override
	public String queryPresellState(String sn) {
		Trans trans=transMapper.selectBySn(sn);
		TransDetails details=trans.getDetailsList().get(0);
		ProductPresell presell=productPresellMapper.selectByProductId(details.getProductId());
		ClusterState cluster=presell.getClusterState();
		Date beginTime=presell.getDeliveryStartTime();
		Date endTime=presell.getDeliveryEndTime();
		Date now=new Date();
		TransPayState payState=trans.getPayState();
		if(ClusterState.wait.equals(cluster)){
			if(TransPayState.unpaid.equals(payState)||
					TransPayState.partPayment.equals(payState)){
				return "未付款";
			}else if(TransPayState.paid.equals(payState)){
				return "已付款，等待成团";
			}else if(TransPayState.refunding.equals(payState)){
				return "已申请退款，退款中";
			}else if(TransPayState.refunded.equals(payState)){
				return "已退款";
			}
		}else if(ClusterState.success.equals(cluster)){
			if(now.before(beginTime)){
				return "已成团，正在备货";
			}else{
				if(TransPayState.refunding.equals(payState)){
					return "已申请退货，退款中";
				}else if(TransPayState.refunded.equals(payState)){
					return "已退款";
				}
				List<TransSecurityTicket> ticketList=transSecurityTicketMapper.selectByTransId(trans.getId());
				if(ticketList!=null&&ticketList.size()>0){
					TransSecurityTicket ticket=ticketList.get(0);
					if(ticket.getIsConsume()){
						return "提货已完成";
					}
				}
				if(now.after(endTime)){
					return "提货已过期";
				}
				return "已成团，提货中";
			}
		}else if(ClusterState.fail.equals(cluster)){
			if(TransPayState.paid.equals(payState)||
					TransPayState.refunding.equals(payState)){
				return "未成团，退款中";
			}
			if(TransPayState.refunded.equals(payState)){
				return "未成团，已退款";
			}
		}
		logger.error("不做计算的状态，交易序号："+sn);
		return "--";
	}

	@Override
	public void closeTimeoutTrans() {
		transMapper.updateTimeoutTransToClose();
		List<Trans> list=transMapper.selectTimeoutTrans();
		if(list==null||list.size()==0){
			logger.info("===========没有需要关闭的超时交易===========");
			return;
		}
		Trans trans=null;
		List<Pay> payList=null;
		Pay pay=null;
		List<Long> closeIdList=new ArrayList<Long>();
		for (int i = 0; i < list.size(); i++) {
			trans=list.get(i);
			payList=trans.getPayList();
			for (int j = 0; j < payList.size(); j++) {
				pay=payList.get(j);
				if(PayTypeDetails.PAY_FFT_POINTS.equals(pay.getPayTypeDetails())||
						PayTypeDetails.PAY_BANK_POINTS.equals(pay.getPayTypeDetails())){
					boolean isOk=transCoreService.refundPoints(pay, trans.getClientId());
					if(isOk){
						closeIdList.add(trans.getId());
					}
					break;
				}
			}
		}
		if(closeIdList.size()>0){
			logger.info("共需要关闭["+list.size()+"]笔超时交易，成功关闭["+closeIdList.size()+"]笔交易");
			transMapper.updateStateToCloseByIds(closeIdList);
		}
	}
	
	@Override
	public List<Trans> selectGroupAndPresellByMemberCode(Long memberCode) {
		return transMapper.selectGroupAndPresellByMemberCode(memberCode);
	}

}
