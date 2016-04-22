package com.froad.CB.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.froad.CB.AppException.AppException;
import com.froad.CB.common.BusinessException;
import com.froad.CB.common.Command;
import com.froad.CB.common.GoodsCommand;
import com.froad.CB.common.KeyGenerator;
import com.froad.CB.common.MailCommand;
import com.froad.CB.common.RuleDetailType;
import com.froad.CB.common.SmsLogType;
import com.froad.CB.common.SysCommand;
import com.froad.CB.common.TicketType;
import com.froad.CB.common.constant.ExceptionTransType;
import com.froad.CB.common.constant.HFCZCommand;
import com.froad.CB.common.constant.PayCommand;
import com.froad.CB.common.constant.PayState;
import com.froad.CB.common.constant.PointCommand;
import com.froad.CB.common.constant.RefundReason;
import com.froad.CB.common.constant.TranCommand;
import com.froad.CB.dao.GoodsDao;
import com.froad.CB.dao.GoodsGatherRackDao;
import com.froad.CB.dao.GoodsPresellRackDao;
import com.froad.CB.dao.PayDao;
import com.froad.CB.dao.PresellDeliveryDao;
import com.froad.CB.dao.TransDao;
import com.froad.CB.dao.TransDetailsDao;
import com.froad.CB.dao.TransGoodsAttributeDao;
import com.froad.CB.dao.impl.CommonGoodsDaoImpl;
import com.froad.CB.dao.merchant.MerchantDAO;
import com.froad.CB.dao.user.UserDao;
import com.froad.CB.po.AuthTicket;
import com.froad.CB.po.ClientGoodsExchangeRack;
import com.froad.CB.po.ClientGoodsGroupRack;
import com.froad.CB.po.FundsChannel;
import com.froad.CB.po.Goods;
import com.froad.CB.po.GoodsExchangeRack;
import com.froad.CB.po.GoodsGatherRack;
import com.froad.CB.po.GoodsGroupRack;
import com.froad.CB.po.GoodsPresellRack;
import com.froad.CB.po.HFCZ;
import com.froad.CB.po.Pay;
import com.froad.CB.po.PresellDelivery;
import com.froad.CB.po.Seller;
import com.froad.CB.po.SmsLog;
import com.froad.CB.po.Trans;
import com.froad.CB.po.TransDetails;
import com.froad.CB.po.TransGoodsAttribute;
import com.froad.CB.po.UserCertification;
import com.froad.CB.po.bill.CombineRequest;
import com.froad.CB.po.bill.CombineResponse;
import com.froad.CB.po.bill.OrderDetail;
import com.froad.CB.po.bill.PayNotice;
import com.froad.CB.po.bill.PayRequest;
import com.froad.CB.po.bill.PayResponse;
import com.froad.CB.po.bill.RefundNotice;
import com.froad.CB.po.bill.RefundRequest;
import com.froad.CB.po.bill.RefundResponse;
import com.froad.CB.po.bill.Transfer;
import com.froad.CB.po.lottery.Lottery;
import com.froad.CB.po.merchant.Merchant;
import com.froad.CB.po.transaction.Points;
import com.froad.CB.po.transaction.Rebate;
import com.froad.CB.po.user.User;
import com.froad.CB.service.AuthTicketService;
import com.froad.CB.service.MessageService;
import com.froad.CB.service.TransService;
import com.froad.CB.service.UserCertificationService;
import com.froad.CB.thirdparty.BillFunc;
import com.froad.CB.thirdparty.HFCZFunc;
import com.froad.CB.thirdparty.LotteryFunc;
import com.froad.CB.thirdparty.PointsFunc;
import com.froad.util.DateUtil;
import com.froad.util.MessageSourceUtil;
import com.froad.util.Result;
import com.froad.util.Util;

@WebService(endpointInterface="com.froad.CB.service.TransService")
public class TransServiceImpl implements TransService{
	
	private static final Logger logger=Logger.getLogger(TransServiceImpl.class);
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
	"yyyy-MM-dd|HH:mm:ss");
	
	private static final SimpleDateFormat dateFormat1 = new SimpleDateFormat(
	"yyyy-MM-dd HH:mm:ss");
	
	private static final SimpleDateFormat displayFormat = new SimpleDateFormat(
	"yyyy年MM月dd号");
	
	
	private TransDao transDao;
	
	private TransDetailsDao transDetailsDao;
	
	private AuthTicketService authTicketService;
	
	private TransGoodsAttributeDao transGoodsAttributeDao;
	
	private CommonGoodsDaoImpl commonGoodsDaoImpl;
	
	private MerchantDAO merchantDAO;
	
	private PayDao payDao;
	
	private GoodsDao goodsDao;
	
	private GoodsGatherRackDao goodsGatherRackDao;
	
	private UserDao userDao;
	
	private MessageService messageService;
	
	private UserCertificationService userCertificationService;
	
	private GoodsPresellRackDao goodsPresellRackDao;
	
	private PresellDeliveryDao presellDeliveryDao;
	
	
	@Override
	public Trans addTrans(Trans trans)throws AppException{
		logger.info("========正在添加交易========");
		if(trans==null){
			logger.error("参数为空，交易添加失败");
			throw new AppException("传入参数为空");
		}
		String time=DateUtil.formatDate2Str(new Date());
		trans.setCreateTime(time);
		trans.setUpdateTime(time);
		Integer transId=null;
		try {
			transId = transDao.addTrans(trans);
		} catch (SQLException e) {
			logger.error("添加交易时出现sql异常",e);
			throw new AppException("添加交易时出现SQL异常");
		}
		logger.info("trans记录添加成功，交易号: "+transId);
		Integer goodsRackId=null;
		if(TranCommand.POINTS_REBATE.equals(trans.getTransType())||
				TranCommand.COLLECT.equals(trans.getTransType())||
				TranCommand.PRESENT_POINTS.equals(trans.getTransType())){
			GoodsGatherRack gather=trans.getGoodsGatherRack();
			if(gather!=null){
				Goods goods=gather.getGoods();
				if(goods!=null){
					goods.setIsAuto("1");
					Integer goodsId=goodsDao.addGoods(goods);
					gather.setGoodsId(goodsId+"");
					goodsRackId=goodsGatherRackDao.insert(gather);
				}
			}
		}
		List<TransDetails> detailsList=trans.getTransDetailsList();
		if(detailsList!=null&&detailsList.size()>0){
			for (int i = 0; i < detailsList.size(); i++) {
				detailsList.get(i).setTransId(transId);
				if(goodsRackId!=null){
					detailsList.get(i).setGoodsRackId(goodsRackId+"");
				}
			}
			transDetailsDao.batchInsert(detailsList);
			logger.info("transDetails记录添加成功，所属交易号："+transId);
		}
		List<TransGoodsAttribute> attrList=trans.getTransGoodsAttrList();
		if(attrList!=null&&attrList.size()>0){
			for (int i = 0; i <attrList.size(); i++) {
				attrList.get(i).setTransId(transId+"");
			}
			transGoodsAttributeDao.batchInsert(attrList);
			logger.info("transGoodsAttribute记录添加成功，所属交易号："+transId);
		}
		List<Pay> payList=trans.getPayList();
		if(payList!=null&&payList.size()>0){
			for (int i = 0; i <payList.size(); i++) {
				payList.get(i).setTransId(transId+"");
			}
			payDao.batchInsert(payList);
			logger.info("pay记录添加成功，所属交易号："+transId);
		}
		trans.setId(transId);
		logger.info("========交易添加完成========");
		return trans;
	}

	@Override
	public void deleteById(Integer id) {
		if(id==null){
			logger.error("编号为空，交易删除失败");
			return;
		}
		transDao.deleteById(id);
	}

	@Override
	public Trans getTransById(Integer id) {
		if(id==null){
			logger.error("编号为空，查询交易失败");
			return null;
		}
		Trans trans=transDao.getTransById(id);
		this.joinGoods(trans);
		trans.setUser(userDao.queryUserAllByUserID(trans.getUserId()));
		return trans;
	}

	@Override
	public void updateById(Trans trans) {
		if(trans==null||trans.getId()==null){
			logger.error("参数为空，交易更新失败");
			return;
		}
		transDao.updateById(trans);
	}

	@Override
	public void updateStateById(Integer id, String state) {
		if(id==null||state==null||"".equals(state)){
			logger.error("参数为空，更新状态失败");
			return;
		}
		transDao.updateStateById(id, state);
	}


	@Override
	public List<Trans> getTransByBuyerId(String buyerId) {
		if(buyerId==null||"".equals(buyerId)){
			logger.error("买家编号为空");
			return null;
		}
		List<Trans> list=transDao.getTransByBuyerId(buyerId);
		if(list!=null&&list.size()>0){
			for (int i = 0; i <list.size(); i++) {
				this.joinGoods(list.get(i));
			}
		}
		return list;
	}

	@Override
	public List<Trans> getTransBySellerId(String sellerId) {
		if(sellerId==null||"".equals(sellerId)){
			logger.error("卖家编号为空");
			return null;
		}
		List<Trans> list=transDao.getTransBySellerId(sellerId);
		if(list!=null&&list.size()>0){
			for (int i = 0; i <list.size(); i++) {
				this.joinGoods(list.get(i));
			}
		}
		return list;
	}

	@Override
	public Trans getTransByPager(Trans trans) {
		if(trans==null){
			logger.error("参数为空");
			return null;
		}		
		Trans tr=transDao.getTransByPager(trans);
		List<?> list=tr.getList();
		if(list!=null&&list.size()>0){
			for (int i = 0; i <list.size(); i++) {
				this.joinGoods((Trans)list.get(i));
			}
		}
		return tr;
	}
	
	@Override
	public Trans getGroupOrExchangeTransByPager(Trans trans) {
		if(trans==null){
			logger.error("参数为空");
			return null;
		}
		Trans tr=transDao.getGroupOrExchangeTransByPager(trans);
		List<?> list=tr.getList();
		if(list!=null&&list.size()>0){
			for (int i = 0; i <list.size(); i++) {
				this.joinGoods((Trans)list.get(i));
			}
		}
		return tr;
	}

	@Override
	public Result doPay(Integer transId){
		Trans trans=this.getTransById(transId);
		boolean isValid=check(trans);//验证参数
		if(!isValid){
			return new Result(Result.FAIL,"交易参数校验不通过");
		}
		List<Pay> payList=trans.getPayList();
		String payMethod=trans.getPayMethod();//支付方式
		logger.info("正在发起支付，payMethod: "+payMethod+"，transId: "+transId+"transType: "+trans.getTransType());
		if(payMethod.equals(TranCommand.POINTS_BANK)||payMethod.equals(TranCommand.POINTS_FFT)){
			/**********积分**********/
			Pay pointsPay=null,tmpPay=null,hfczPay=null,lotteryPay=null,presentPay=null;
			String detail=null;
			for (int i = 0; i <payList.size(); i++) {
				tmpPay=payList.get(i);
				detail=tmpPay.getTypeDetail();
				if(RuleDetailType.PAY_POINTS.getValue().equals(detail)||
						RuleDetailType.PAY_BANK_POINTS.getValue().equals(detail)){
					pointsPay=tmpPay;
				}else if(TranCommand.CATEGORY_HFCZ.equals(detail)){
					hfczPay=tmpPay;
				}else if(TranCommand.CATEGORY_LOTTORY.equals(detail)){
					lotteryPay=tmpPay;
				}else if(RuleDetailType.SEND_POINTS.getValue().equals(detail)){
					presentPay=tmpPay;
				}
			}
			if(pointsPay==null){
				logger.error("该交易的支付方式为：纯积分，但是积分的支付记录不存在，交易号为："+transId);
				return new Result(Result.FAIL,"积分支付失败");
			}
			//消费积分
			Points pointsRes=this.payPoints(pointsPay);
			if(pointsRes!=null&&PayCommand.RESPCODE_SUCCESS.equals(pointsRes.getResultCode())){
				String payPointsNo=pointsRes.getPayPointsNo();
				if(payPointsNo==null){
					payPointsNo=trans.getPointBillNo();
				}
				if(hfczPay!=null){//发起充话费请求
					boolean reqOk=this.hfcz(trans.getTransGoodsAttrList(), hfczPay);
					if(!reqOk){
						this.updateStateById(transId, TranCommand.TRAN_FAIL);
						this.refundPoints(pointsPay, payPointsNo);
						return new Result(Result.FAIL,"话费充值失败，积分将在24小时内退还");
					}
				}else if(lotteryPay!=null){//买彩票
					boolean reqOk=this.lottery(trans.getTransGoodsAttrList(), lotteryPay);
					if(!reqOk){
						this.updateStateById(transId, TranCommand.TRAN_FAIL);
						this.refundPoints(pointsPay, payPointsNo);
						return new Result(Result.FAIL,"彩票购买失败，积分将在24小时内退还");
					}
				}else{//实体商品
					this.afterPay(trans);
					if(presentPay!=null){
						this.presentPoints(presentPay);
					}
				}
				return new Result(Result.SUCCESS,"交易成功");
			}else{//积分支付失败
				this.updateStateById(transId, TranCommand.TRAN_FAIL);
				return new Result(Result.FAIL,"积分支付失败");
			}
		}else if(payMethod.equals(TranCommand.CASH)||payMethod.equals(TranCommand.ALIPAY)){
			/**********金额**********/
			Pay cashPay=null,tmpPay=null;
			for (int i = 0; i <payList.size(); i++) {
				tmpPay=payList.get(i);
				if(RuleDetailType.PAY_COLLECT.getValue().equals(tmpPay.getTypeDetail())){
					cashPay=tmpPay;
					break;
				}
			}
			if(cashPay==null){
				logger.error("该笔交易的支付方式为：金额，但是金额的支付记录不存在，交易号为："+transId);
				return new Result(Result.FAIL,"请确认是否为贴膜卡用户");
			}
			//发起支付请求
			Result result=this.cashPay(cashPay,trans);
			if(Result.SUCCESS.equals(result.getCode())){
				return result;
			}else{
				return new Result(Result.FAIL,result.getMsg());
			}
		}else{
			/********积分+金额********/
			Pay pointsPay=null,cashPay=null,tmpPay=null;
			String typeDetail=null;
			for (int i = 0; i <payList.size(); i++) {
				tmpPay=payList.get(i);
				typeDetail=tmpPay.getTypeDetail();
				if(RuleDetailType.PAY_POINTS.getValue().equals(typeDetail)||
						RuleDetailType.PAY_BANK_POINTS.getValue().equals(typeDetail)){
					pointsPay=tmpPay;
				}else if(RuleDetailType.PAY_COLLECT.getValue().equals(typeDetail)){
					cashPay=tmpPay;
				}
			}
			if(pointsPay==null){
				logger.error("当前的支付方式为:积分+金额，但是积分的支付记录不存在");
				return new Result(Result.FAIL,"积分支付失败");
			}
			if(cashPay==null){
				logger.error("当前的支付方式为:积分+金额，但是金额的支付记录不存在");
				return new Result(Result.FAIL,"请确认是否为贴膜卡用户");
			}
			//消费积分
			Points pointsRes=this.payPoints(pointsPay);
			if(pointsRes==null||!PayCommand.RESPCODE_SUCCESS.equals(pointsRes.getResultCode())){
				this.updateStateById(transId, TranCommand.TRAN_FAIL);
				return new Result(Result.FAIL,"积分支付失败");
			}
			//发起支付请求
			Result result=this.cashPay(cashPay,trans);
			if(Result.SUCCESS.equals(result.getCode())){
				return result;
			}else{
				//退积分
				String payPointsNo=pointsRes.getPayPointsNo();
				if(payPointsNo==null){
					payPointsNo=trans.getPointBillNo();
				}
				this.refundPoints(pointsPay, payPointsNo);
				return new Result(Result.FAIL,result.getMsg());
			}
		}
	}
	
	
	@Override
	public boolean doDeduct(Integer transId) throws BusinessException {
		Trans trans=this.getTransById(transId);
		boolean isValid=check(trans);//验证参数
		if(!isValid){
			throw new BusinessException(9999,"交易参数校验不通过");
		}
		List<Pay> payList=trans.getPayList();
		Pay deductPay=null,feePay=null,tmpPay=null;
		for (int i = 0; i < payList.size(); i++) {
			tmpPay=payList.get(i);
			if(RuleDetailType.BUY_POINTS.getValue().equals(tmpPay.getTypeDetail())){
				//购买积分的pay记录
				deductPay=tmpPay;
			}else if(RuleDetailType.POINTS_Factorage.getValue().equals(tmpPay.getTypeDetail())){
				//购买积分手续费的pay记录
				feePay=tmpPay;
			}
		}
		if(deductPay==null){
			logger.error("该笔交易的代扣记录不存在，交易号："+transId);
			throw new BusinessException(9999,"该笔交易的代扣记录不存在");
		}
		if(PayState.PAY_REQUEST_SUCCESS.equals(deductPay.getState())){
			logger.error("代扣请求已经发送成功，请不要重复操作。交易号："+transId);
			throw new BusinessException(9999,"代扣请求已经发送成功，请不要重复操作。");
		}
		/**************发起代扣请求**************/
		Result result=this.deduct(deductPay,feePay,trans);
		if(Result.SUCCESS.equals(result.getCode())){
			return true;
		}else{
			throw new BusinessException(9999,result.getMsg());
		}
	}
	
	
	@Override
	public boolean deductPoints(Integer transId) throws BusinessException {
		Trans trans=this.getTransById(transId);
		boolean isValid=check(trans);//验证参数
		if(!isValid){
			throw new BusinessException(9999,"交易参数校验不通过");
		}
		List<Pay> payList=trans.getPayList();
		Pay pointsPay=null,tmpPay=null;
		for (int i = 0; i < payList.size(); i++) {
			tmpPay=payList.get(i);
			if(RuleDetailType.DEDUCT_POINTS.getValue().equals(tmpPay.getTypeDetail())){
				//扣积分的pay记录
				pointsPay=tmpPay;
				break;
			}
		}
		if(pointsPay==null){
			logger.error("积分的pay记录不存在，提现失败，交易号："+transId);
			throw new BusinessException(9999,"积分抵扣记录不存在");
		}
		/*********扣除积分*********/
		Points pointsRes=this.payPoints(pointsPay);
		if(pointsRes==null||!PayCommand.RESPCODE_SUCCESS.equals(pointsRes.getResultCode())){
			this.updateStateById(transId, TranCommand.TRAN_FAIL);
			throw new BusinessException(9999,"积分支付失败");
		}else{
			//给用户发申请成功的短信
			User user=userDao.queryUserAllByUserID(trans.getUserId());
			String content=MessageSourceUtil.getSource().getMessage("withdraw_apply", null, null);
			SmsLog smsLog=new SmsLog();
			smsLog.setMobile(user.getMobilephone());
			smsLog.setMessage(content);
			smsLog.setType(SmsLogType.WITHDRAW_APPLY);
		//	messageService.sendMessage(smsLog);
			return true;
		}
		
	}
	
	
	@Override
	public boolean withdraw(Integer transId) throws BusinessException{
		Trans trans=this.getTransById(transId);
		boolean isValid=check(trans);//验证参数
		if(!isValid){
			return false;
		}
		List<Pay> payList=trans.getPayList();
		Pay cashPay=null,pointsPay=null,feePay=null,tmpPay=null;
		String typeDetail=null;
		for (int i = 0; i < payList.size(); i++) {
			tmpPay=payList.get(i);
			typeDetail=tmpPay.getTypeDetail();
			if(RuleDetailType.DEDUCT_POINTS.getValue().equals(typeDetail)){
				//扣积分的pay记录
				pointsPay=tmpPay;
			}else if(RuleDetailType.PAY_TO_Buyer.getValue().equals(typeDetail)){
				//转给提现用户的金额(未扣手续费)
				cashPay=tmpPay;
			}else if(RuleDetailType.PAY_TO_FFT.getValue().equals(typeDetail)){
				//手续费
				feePay=tmpPay;
			}
		}
		if(cashPay==null||pointsPay==null){
			logger.error("积分提现的pay数据不完整，提现失败，交易号："+transId);
			return false;
		}
		String total=cashPay.getPayValue();
		if(total==null||"".equals(total)){
			logger.error("提现金额为空");
			return false;
		}
		
		/*********给提现用户转账********/
		logger.info("======正在为提现用户转账======");
		String amount=null;//实际转账金额
		if(feePay==null||feePay.getPayValue()==null){
			amount=Util.formatMoney(total).toString();
		}else{
			BigDecimal real=new BigDecimal(total).subtract(new BigDecimal(feePay.getPayValue()));
			amount=Util.formatMoney(real).toString();
		}
		Transfer transfer=new Transfer();
		transfer.setTransferID("fbu"+cashPay.getTransId());
		transfer.setTransferAmount(amount);
		transfer.setPayerAccountName(cashPay.getFromAccountName());
		transfer.setPayerAccountNum(cashPay.getFromAccountNo());
		transfer.setPayeeAccountName(cashPay.getToAccountName());
		transfer.setPayeeAccountNum(cashPay.getToAccountNo());
		Transfer transferRes=null;//转账是否成功
		try {
			transferRes=BillFunc.transfer(transfer);
		} catch (Exception e) {
			logger.error("积分换现的转账过程中出现异常！交易编号："+cashPay.getTransId(),e);
		}
		/*********更新交易和支付明细*********/
		Trans tmpTrans=new Trans();
		tmpTrans.setId(Integer.parseInt(cashPay.getTransId()));
		tmpTrans.setUpdateTime(DateUtil.formatDate2Str(new Date()));
		boolean isOk=false;//标识提现是否成功
		if(transferRes!=null&&PayCommand.RESPCODE_SUCCESS.equals(transferRes.getResultCode())){//转账成功
			isOk=true;
			tmpTrans.setBillNo(transfer.getTransferID());
			tmpTrans.setState(TranCommand.TRAN_SUCCESS);
			
			cashPay.setState(PayState.PAY_SUCCESS);
			//给用户发提现成功的短信
			User user=userDao.queryUserAllByUserID(trans.getUserId());
			String[]msg={trans.getFftPointsValueRealAll(),trans.getFftFactorage(),trans.getCurrencyValueRealAll()};
			String content=MessageSourceUtil.getSource().getMessage("withdraw_success", msg, null);
			SmsLog smsLog=new SmsLog();
			smsLog.setMobile(user.getMobilephone());
			smsLog.setMessage(content);
			smsLog.setType(SmsLogType.WITHDRAW_SUCCESS);
			//messageService.sendMessage(smsLog);
		}else{
			logger.error("========转账失败，积分提现失败，交易号： "+cashPay.getTransId());
			tmpTrans.setState(TranCommand.TRAN_FAIL);
			
			cashPay.setState(PayState.PAY_FAIL);
			if(transferRes!=null){
				cashPay.setResultCode(transferRes.getResultCode());
				cashPay.setResultDesc(transferRes.getRemark());
			}
			//退积分
			this.refundPoints(pointsPay,trans.getPointBillNo());
		}
		this.updateById(tmpTrans);//更新trans记录
		payDao.updatePay(cashPay);//更新转账的pay记录
		if(feePay!=null){
			feePay.setState(cashPay.getState());
			feePay.setResultCode(cashPay.getResultCode());
			payDao.updatePay(feePay);//更新手续费的pay记录
		}
		logger.info("=======交易记录更新成功，提现完成=======");
		return isOk;
	}
	
	
	/**
	  * 方法描述：发券(团购或积分兑换)
	  * @param: Trans
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 20, 2013 3:38:09 PM
	  */
	private void sendTicket(Trans trans){
		String transType=trans.getTransType();//交易类型
		String clientType=trans.getClientType();//客户端类型
		String ticketType=null;//券的类型 见TicketType
		String smsLogType=null;//短信类型 
		String goodsName=null;//商品名
		String beginTime=null;//开始时间
		String expireTime=null;//过期时间
		String[] msg=null;//短信的动态内容
		String key=null;//message属性文件里的key值
		TransDetails transDetails=trans.getTransDetailsList().get(0);
		logger.info("============正在发券，交易号为："+trans.getId()+"，交易类型："+trans.getTransType()+"=============");
		if(PayCommand.CLIENT_PC.equals(clientType)){
			if(TranCommand.GROUP.equals(transType)){
				logger.info("============开始添加团购券=============");
				ticketType=TicketType.GROUP;
				smsLogType=SmsLogType.SMSLOG_GROUP_TICKET;
				GoodsGroupRack group=transDetails.getGoodsGroupRack();
				goodsName=group.getSeoTitle();
				try {
					beginTime=displayFormat.format(dateFormat.parse(group.getTicketBeginTime()));
					expireTime=displayFormat.format(dateFormat.parse(group.getTicketEndTime()));
				} catch (ParseException e) {
					logger.error("时间格式转换异常，添加券失败",e);
					return;
				}
				key="group";
			}else{
				logger.info("============开始添加积分兑换券=============");
				ticketType=TicketType.EXCHANGE;
				smsLogType=SmsLogType.SMSLOG_EXCHANGE_TICKET;
				GoodsExchangeRack exchange=transDetails.getGoodsExchangeRack();
				goodsName=exchange.getGoods().getGoodsName();
				Calendar cal=Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, exchange.getDays());
				expireTime=displayFormat.format(cal.getTime());
				key="exchange";
			}
		}else{
			if(TranCommand.GROUP.equals(transType)){
				logger.info("============开始添加团购券=============");
				ticketType=TicketType.GROUP;
				smsLogType=SmsLogType.SMSLOG_GROUP_TICKET;
				ClientGoodsGroupRack group=transDetails.getClientGoodsGroupRack();
				goodsName=group.getSeoTitle();
				try {
					beginTime=displayFormat.format(dateFormat.parse(group.getTicketBeginTime()));
					expireTime=displayFormat.format(dateFormat.parse(group.getTicketEndTime()));
				} catch (ParseException e) {
					logger.error("时间格式转换异常，添加券失败",e);
					return;
				}
				key="group";
			}else{
				logger.info("============开始添加积分兑换券=============");
				ticketType=TicketType.EXCHANGE;
				smsLogType=SmsLogType.SMSLOG_EXCHANGE_TICKET;
				ClientGoodsExchangeRack exchange=transDetails.getClientGoodsExchangeRack();
				goodsName=exchange.getGoods().getGoodsName();
				Calendar cal=Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, exchange.getDays());
				expireTime=displayFormat.format(cal.getTime());
				key="exchange";
			}
		}
		
		String userId=trans.getUserId();
		User user=userDao.queryUserAllByUserID(userId);
		if(user==null){
			logger.error("用户不存在，认证券添加失败，交易号："+trans.getId());
			return;
		}
		String userMobile=trans.getPhone()==null?user.getMobilephone():trans.getPhone();
		AuthTicket authTicket=new AuthTicket();
		authTicket.setUserId(userId);
		authTicket.setMerchantId(trans.getMerchantId()+"");
		authTicket.setTransId(trans.getId()+"");
		authTicket.setType(ticketType);
		authTicket.setExpireTime(expireTime);
		AuthTicket ticketRes=null;
		int goodsNumber=Integer.parseInt(transDetails.getGoodsNumber());
		for (int i = 0; i <goodsNumber; i++) {//按商品数量循环发送短信
			try {
				authTicket.setId(null);
				ticketRes=authTicketService.addAuthTicket(authTicket);
			} catch (AppException e) {
				logger.error("添加认证券时出现异常，添加券失败",e);
				return;
			}
			logger.info("=======团购或兑换券添加完成=======");
			if(TranCommand.GROUP.equals(transType)){
				msg=new String[]{goodsName,ticketRes.getSecuritiesNo(),beginTime,expireTime};
			}else{
				msg=new String[]{goodsName,ticketRes.getSecuritiesNo(),expireTime};
			}
			String content=MessageSourceUtil.getSource().getMessage(key, msg, null);
			SmsLog smsLog=new SmsLog();
			smsLog.setMobile(userMobile);
			smsLog.setMessage(content);
			smsLog.setType(smsLogType);
			messageService.sendMessage(smsLog);
			
		}
	}
	
	
	
	/**
	  * 方法描述：实体商品在线支付成功后的处理
	  * @param: Trans 交易对象
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 11, 2014 5:03:08 PM
	  */
	private void afterPay(Trans trans){
		String transType=trans.getTransType();
		if(TranCommand.PRESELL.equals(transType)){//如果是精品预售
			TransDetails details=trans.getTransDetailsList().get(0);
			String id=details.getGoodsRackId();
		    GoodsPresellRack presell=goodsPresellRackDao.getById(Integer.parseInt(id));
		    int goodsNum=Integer.parseInt(details.getGoodsNumber());
		    int trueBuyerNum=presell.getTrueBuyerNum()+goodsNum;
		    presell.setTrueBuyerNum(trueBuyerNum);
		    if(trueBuyerNum>=presell.getClusteringNum()){
		    	presell.setIsCluster("1");
		    }
		    goodsPresellRackDao.updateById(presell);
		    logger.info("========预售商品参团数量更新完成=======");
		}else if(TranCommand.POINTS_EXCH_PRODUCT.equals(transType)||
				TranCommand.GROUP.equals(transType)){//如果是积分兑换或者团购
			this.updateStateById(trans.getId(), TranCommand.TRAN_SUCCESS);//更新交易
			this.sendTicket(trans);//发券
			this.updateStore(trans);//更新库存和销量
		}
	}
	
	
	/**
	  * 方法描述：是否需要发起退款(用户对已经关闭的交易成功付款)
	  * @param: trans 待退款交易
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Aug 12, 2013 5:32:58 PM
	  */
	private boolean isRefund(Trans trans){
		if(!TranCommand.TRAN_CLOSE.equals(trans.getState())){//交易没有关闭
			return false;
		}
		Seller seller=trans.getSeller();
		if(seller==null||!"1".equals(seller.getIsInternal())){//不是方付通账户
			logger.info("卖家的账户不属于方付通账户，不处理");
			return false;
		}
		return true;
	}
	
	
	/**
	  * 方法描述：(代收|代扣)的通知后处理方法
	  * @param: PayNotice
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 23, 2013 7:26:27 PM
	  */
	public void process(PayNotice bill){
		Integer transId=null;//trans表主键
		Integer payId=null;//pay表主键
		String orderId=bill.getOrderID();
		if(orderId==null||!orderId.contains("x")){
			logger.error("无效的订单号(orderID): "+orderId);
			return;
		}
		String[] tmp=orderId.replace("fbu", "").split("x");
		transId=Integer.parseInt(tmp[0]);
		payId=Integer.parseInt(tmp[1]);
		Trans trans = this.getTransById(transId);
		boolean payOk=false;
		if (trans!=null){
			List<Pay> payList=trans.getPayList();
			Pay pay=this.findPay(payList, payId);
			if(pay==null){
				logger.error("支付明细(pay)不存在，交易号："+transId);
				return;
			}
			if(PayState.PAY_SUCCESS.equals(pay.getState())){
				logger.error("已经支付成功，请不要重复操作，交易号："+transId+"支付明细(pay)编号："+payId);
				return;
			}
			/*******************修改交易和支付记录******************/
			Trans tmpTrans = new Trans();
			tmpTrans.setId(transId);
			tmpTrans.setBillNo(bill.getFroadBillNo());
			tmpTrans.setUpdateTime(dateFormat.format(new Date()));
			if (PayCommand.STATE_CODE_PAYED.equals(bill.getStateCode())){//支付成功
				payOk=true;
				pay.setState(PayState.PAY_SUCCESS);
				logger.info("支付成功！交易号为：" +transId);
			} else {//支付失败
				pay.setState(PayState.PAY_FAIL);
				pay.setResultCode(bill.getResultCode());
				logger.error("支付失败！返回的结果码：" + bill.getResultCode()
						+ "，状态码：" + bill.getStateCode() + "。交易号为："
						+transId);
			}
			//修改支付明细状态
			payDao.updatePay(pay);
			//修改手续费的pay记录
			if(RuleDetailType.BUY_POINTS.getValue().equals(pay.getTypeDetail())){
				Pay feePay=new Pay();
				feePay.setTransId(transId+"");
				feePay.setType(PayCommand.TYPE_CASH);
				feePay.setTypeDetail(RuleDetailType.POINTS_Factorage.getValue());
				feePay.setState(pay.getState());
				feePay.setResultCode(pay.getResultCode());
				payDao.updatePay(feePay);
			}
			/******付款成功：发券|代扣|返积分  付款失败：如果扣了积分则退积分******/
			String transType=trans.getTransType();
			if(payOk){//支付成功
				if(this.isRefund(trans)){
					logger.info("该笔订单已关闭，系统自动发起退款，交易号："+transId);
					this.refund(pay, RefundReason.PAYED_CLOSED_ORDER);
					return;
				}
				if(TranCommand.POINTS_EXCH_PRODUCT.equals(transType)){
					//发券|充话费|买彩票
					Pay tmpPay=null,hfczPay=null,lotteryPay=null,pointsPay=null,presentPay=null;
					String detail=null;
					for (int i = 0; i <payList.size(); i++) {//查出各类pay
						tmpPay=payList.get(i);
						detail=tmpPay.getTypeDetail();
						if(TranCommand.CATEGORY_HFCZ.equals(detail)){
							hfczPay=tmpPay;
						}else if(TranCommand.CATEGORY_LOTTORY.equals(detail)){
							lotteryPay=tmpPay;
						}else if(RuleDetailType.PAY_BANK_POINTS.getValue().equals(detail)||
								RuleDetailType.PAY_POINTS.getValue().equals(detail)){
							pointsPay=tmpPay;
						}else if(RuleDetailType.SEND_POINTS.getValue().equals(detail)){
							presentPay=tmpPay;
						}
					}
					if(lotteryPay!=null){//买彩票
						boolean reqOk=this.lottery(trans.getTransGoodsAttrList(), lotteryPay);
						if(!reqOk){
							this.updateStateById(transId, TranCommand.TRAN_FAIL);
							this.refund(pay, "彩票购买失败");
							if(pointsPay!=null){//退积分
								this.refundPoints(pointsPay, trans.getPointBillNo());
							}
						}
					}else if(hfczPay!=null){//充话费
						boolean reqOk=this.hfcz(trans.getTransGoodsAttrList(), hfczPay);//发起充话费请求
						if(!reqOk){
							this.updateStateById(transId, TranCommand.TRAN_FAIL);
							this.refund(pay, "话费充值失败");//退款
							if(pointsPay!=null){//退积分
								this.refundPoints(pointsPay, trans.getPointBillNo());
							}
						}
					}else{//实体商品
						this.afterPay(trans);
						if(presentPay!=null){
							this.presentPoints(presentPay);
						}
					}
				}else if(TranCommand.GROUP.equals(transType)||
						TranCommand.PRESELL.equals(transType)){
					this.afterPay(trans);
				}else if(TranCommand.POINTS_REBATE.equals(transType)||
						TranCommand.COLLECT.equals(transType)||
						TranCommand.PRESENT_POINTS.equals(transType)){
					//如果当前的支付通知是属于收款的，则发起代扣，如果属于代扣，则返积分
					Pay deductPay=null,rebatePay=null,feePay=null,tmpPay=null;
					for (int i = 0; i <payList.size(); i++) {
						tmpPay=payList.get(i);
						if(RuleDetailType.SEND_POINTS.getValue().equals(tmpPay.getTypeDetail())){
							rebatePay=tmpPay;
						}else if(RuleDetailType.BUY_POINTS.getValue().equals(tmpPay.getTypeDetail())){
							deductPay=tmpPay;
						}else if(RuleDetailType.POINTS_Factorage.getValue().equals(tmpPay.getTypeDetail())){
							feePay=tmpPay;
						}
					}
					if(RuleDetailType.BUY_POINTS.getValue().equals(pay.getTypeDetail())){
						tmpTrans.setState(TranCommand.TRAN_SUCCESS);
						this.updateById(tmpTrans);//更新交易
						if(rebatePay!=null){//返积分
							this.rebatePoints(rebatePay);
						}
					}else{//收到支付通知的是收款的pay
						if(deductPay!=null){
							this.deduct(deductPay, feePay,trans);//返积分前的代扣
						}else{//属于纯收款
							tmpTrans.setState(TranCommand.TRAN_SUCCESS);
							this.updateById(tmpTrans);//更新交易
						}
					}
				}
			}else{
				//更新交易状态
				tmpTrans.setState(TranCommand.TRAN_FAIL);
				this.updateById(tmpTrans);
				//如果支付了积分,退积分
				Pay pointsPay=null,tmpPay=null;
				for (int i = 0; i <payList.size(); i++) {
					tmpPay=payList.get(i);
					if(RuleDetailType.PAY_POINTS.getValue().equals(tmpPay.getTypeDetail())||
							RuleDetailType.PAY_BANK_POINTS.getValue().equals(tmpPay.getTypeDetail())){
						pointsPay=tmpPay;
						break;
					}
				}
				if(pointsPay!=null&&PayState.POINT_PAY_SUCCESS.equals(pointsPay.getState())){
					this.refundPoints(pointsPay, trans.getPointBillNo());
				}
			}
		}else{
			logger.error("交易不存在！通知返回的交易编号：" + transId);
		}
	}
	
	
	/**
	  * 方法描述：退款的通知后处理方法
	  * @param: RefundNotice
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 23, 2013 7:34:27 PM
	  */
	public void processRefund(RefundNotice refund){
		String refundOrderId=refund.getRefundOrderID();
		Trans trans = transDao.getTransByRefundOrderId(refundOrderId);
		if (trans!=null){
			Integer transId=trans.getId();
			Pay pay=this.findCashPay(trans);
			if(pay==null){
				logger.error("退款的支付明细(pay)不存在，交易号："+transId);
				return;
			}
			if(PayState.REFUND_SUCCESS.equals(pay.getState())){
				logger.error("已经退款成功，请不要重复操作，交易号："+transId+"支付明细(pay)编号："+pay.getId());
				return;
			}
			/*******************修改交易和支付记录******************/
			if (PayCommand.STATE_CODE_PAYED.equals(refund.getStateCode())){//退款成功
				Trans tmpTrans = new Trans();
				tmpTrans.setId(transId);
				tmpTrans.setUpdateTime(dateFormat.format(new Date()));
				tmpTrans.setState(TranCommand.TRAN_CLOSE);
				pay.setState(PayState.REFUND_SUCCESS);
				logger.info("退款成功！交易号为：" +transId+"，退款订单号： "+refundOrderId);
				//关闭交易
				this.updateById(tmpTrans);
				//修改支付明细状态
				payDao.updatePay(pay);
			}else{//退款失败
				pay.setState(PayState.REFUND_FAIL);
				pay.setResultCode(refund.getResultCode());
				logger.error("退款失败！返回的结果码：" + refund.getResultCode()
						+ "，状态码：" + refund.getStateCode() + "。交易号为："
						+transId+" 退款订单号为： "+refundOrderId);
				//修改支付明细状态
				payDao.updatePay(pay);
				//邮件通知
				messageService.sendEmail("接收退款通知，退款失败", "OPEN-API通知报文：" + refund.getNoticeXML(), MailCommand.REFUND_MAIL_ADDR);
			}
		}else{
			logger.error("交易不存在！退款通知返回的退款订单号：" + refund.getRefundOrderID());
		}
	}
	
	private Pay findPay(List<Pay> payList,Integer payId){
		for (int i = 0; i <payList.size(); i++) {
			if(payList.get(i).getId().equals(payId)){
				return payList.get(i);
			}
		}
		return null;
	}
	
	private Pay findCashPay(Trans trans){
		List<Pay> payList=trans.getPayList();
		Pay pay=null;
		for (int i = 0; i < payList.size(); i++) {
			pay=payList.get(i);
			if(RuleDetailType.PAY_COLLECT.getValue().equals(pay.getTypeDetail())){
				return pay;
			}
		}
		return null;
	}
	
	/**
	  * 方法描述：验证支付交易的参数
	  * @param: Trans
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 7, 2013 11:26:28 PM
	  */
	private boolean check(Trans trans){
		if(trans==null){
			logger.error("交易记录不存在");
			return false;
		}
		logger.info("正在校验交易号为："+trans.getId()+"的交易参数");
		if(TranCommand.TRAN_SUCCESS.equals(trans.getState())){
			logger.error("交易已完成，请不要重复操作");
			return false;
		}else if(TranCommand.TRAN_CLOSE.equals(trans.getState())){
			logger.error("交易已关闭");
			return false;
		}
		String transType=trans.getTransType();
		if(!TranCommand.TRANS_TYPES.contains(transType)){
			logger.error("未知交易类型："+transType);
			return false;
		}
		if(trans.getSellerId()==null||"".equals(trans.getSellerId())){
			logger.error("卖家编号为空");
			return false;
		}
		if(trans.getUserId()==null||"".equals(trans.getUserId())){
			logger.error("用户编号为空");
			return false;
		}
		String payMethod=trans.getPayMethod();
		if(!TranCommand.PAY_METHODS.contains(payMethod)){
			logger.error("未知的支付方式(payMethod)："+payMethod);
			return false;
		}
		if(!TranCommand.POINTS_FFT.equals(payMethod)&&!TranCommand.POINTS_BANK.equals(payMethod)){
			if(!TranCommand.PAY_CHANNELS.contains(trans.getPayChannel())){
				logger.error("未知的支付渠道(payChannel)："+trans.getPayChannel());
				return false;
			}
		}
		if(trans.getPayList()==null||trans.getPayList().size()==0){
			logger.error("交易对应的支付信息为空，交易号： "+trans.getId());
			return false;
		}
		logger.info("========交易参数校验通过========");
		return true;
	}
	
	
	/**
	  * 方法描述：发起话费充值请求
	  * @param: List<TransGoodsAttribute> 交易品属性
	  * @param: Pay 话费充值的pay记录
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 25, 2013 7:26:51 PM
	  */
	private boolean hfcz(List<TransGoodsAttribute> attrList,Pay hfczPay){
		String transId=hfczPay.getTransId();
		TransGoodsAttribute tmp=null;
		String phoneNumber=null,money=null;
		for (int i = 0; i <attrList.size(); i++) {
			tmp=attrList.get(i);
			if(TranCommand.ID_PHONE_NO.equals(tmp.getGoodsRackAttributeId())){
				phoneNumber=tmp.getElement();
			}else if(TranCommand.ID_PRICE.equals(tmp.getGoodsRackAttributeId())){
				money=tmp.getElement();
			}
		}
		if(phoneNumber==null||money==null){
			logger.error("充值号码或金额为空，交易号： "+transId);
			hfczPay.setState(PayState.HFCZ_REQ_FAIL);
			hfczPay.setResultDesc("充值号码或金额为空");
			payDao.updatePay(hfczPay);
			return false;
		}
		HFCZ hfcz=new HFCZ();
		hfcz.setCZNo(phoneNumber);
		hfcz.setMoney(new BigDecimal(money));
		hfcz.setSPID("fbu"+transId+"x"+hfczPay.getId());
		hfcz.setTranDate(dateFormat1.format(new Date()));
		HFCZ hfczRes=HFCZFunc.onLine(hfcz);
		boolean reqOk=false;
		if(HFCZCommand.CODE_SUCCESS.equals(hfczRes.getRespCode())){
			reqOk=true;
			hfczPay.setState(PayState.HFCZ_REQ_SUCCESS);
			hfczPay.setResultDesc("发送话费充值请求成功，正在充值中");
		}else{
			hfczPay.setState(PayState.HFCZ_REQ_FAIL);
			hfczPay.setResultCode(hfczRes.getRespCode());
			hfczPay.setResultDesc(hfczRes.getRespMsg());
		}
		payDao.updatePay(hfczPay);
		return reqOk;
	}
	
	
	/**
	  * 方法描述：发起彩票购买请求
	  * @param: List<TransGoodsAttribute>
	  * @param: Pay
	  * @return: 
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 26, 2013 6:15:07 PM
	  */
	public boolean lottery(List<TransGoodsAttribute> attrList,Pay lotteryPay){
		String transId=lotteryPay.getTransId();
		Lottery lottery = new Lottery();
		lottery.setOrderID("fbu"+transId+"x"+lotteryPay.getId());
		TransGoodsAttribute attr=null;
		String goodsRackAttrId=null;
		for (int i = 0; i <attrList.size(); i++) {
			attr=attrList.get(i);
			goodsRackAttrId=attr.getGoodsRackAttributeId();
			if(TranCommand.ID_LOTTERY_NO.equals(goodsRackAttrId)){
				lottery.setLotteryNo(attr.getElement());
			}else if(TranCommand.ID_PLAY_TYPE.equals(goodsRackAttrId)){
				lottery.setPlayType(attr.getElement());
			}else if(TranCommand.ID_NUM_TYPE.equals(goodsRackAttrId)){
				lottery.setNumType(attr.getElement());
			}else if(TranCommand.ID_BUY_AMOUNT.equals(goodsRackAttrId)){
				lottery.setBuyamount(attr.getElement());
			}else if(TranCommand.ID_AMOUNT.equals(goodsRackAttrId)){
				lottery.setAmount(attr.getElement());
			}else if(TranCommand.ID_LOTTERY_PHONE.equals(goodsRackAttrId)){
				lottery.setMobilephone(attr.getElement());
			}else if(TranCommand.ID_PERIOAD.equals(goodsRackAttrId)){
				lottery.setPeriod(attr.getElement());//
			}else if(TranCommand.ID_NUMBER.equals(goodsRackAttrId)){
				lottery.setContent(attr.getElement());//
			}else if(TranCommand.ID_NUM_COUNT.equals(goodsRackAttrId)){
				lottery.setNumCount(attr.getElement());//
			}
		}
		Lottery lotteryRes=null;
		try {
			lotteryRes=LotteryFunc.createOrder(lottery);
		} catch (AppException e) {
			logger.error("购买彩票出现异常，交易号： "+lotteryPay.getTransId(),e);
		}
		if(lotteryRes!=null&&Command.RESP_CODE_SUCCESS.equals(lotteryRes.getRespCode())){
			lotteryPay.setState(PayState.LOTTERY_REQ_SUCCESS);
			lotteryPay.setResultDesc("彩票购买请求发送成功");
			payDao.updatePay(lotteryPay);
			transDao.updateLotteryBillNo(lotteryRes.getTranID(), Integer.parseInt(transId));
			return true;
		}else{
			lotteryPay.setState(PayState.LOTTERY_REQ_FAIL);
			lotteryPay.setResultDesc("彩票购买请求发送失败");
			payDao.updatePay(lotteryPay);
			return false;
		}
	}
	
	private Result cashPay(Pay cashPay,Trans trans){
		String payChannel=trans.getPayChannel();
		if(PayCommand.TRADER_TYPE_FROAD.equals(cashPay.getToRole())||
				TranCommand.PAY_CHANNEL_ALIPAY.equals(payChannel)){
			return this.combinePay(cashPay, trans);
		}else{
			return this.collect(cashPay, trans);
		}
	}
	
	
	/**
	  * 方法描述：发起代收请求
	  * @param: Pay
	  * @param: payChannel 支付渠道
	  * @return: Result
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 9, 2013 5:09:27 PM
	  */
	private Result collect(Pay cashPay,Trans trans){
		FundsChannel channel=cashPay.getFundsChannel();
		if(channel==null){
			logger.error("资金渠道为空，代收请求失败");
			return new Result(Result.FAIL,"资金渠道为空");
		}
		PayRequest request=new PayRequest();
		request.setOrderID("fbu"+cashPay.getTransId()+"x"+cashPay.getId());
		request.setFromAccountName(cashPay.getFromAccountName());
		request.setFromAccountNum(cashPay.getFromAccountNo());
		request.setToAccountName(cashPay.getToAccountName());
		request.setToAccountNum(cashPay.getToAccountNo());
		request.setFromPhone(cashPay.getFromPhone());
		request.setToPhone(cashPay.getToPhone());
		request.setPayerAmount(cashPay.getPayValue());
		request.setPayeeAmount(cashPay.getPayValue());
		request.setPayerType(cashPay.getFromRole());
		request.setPayerWay(trans.getPayChannel());
		request.setPayeeType(cashPay.getToRole());
		request.setPayeeWay(TranCommand.PAY_CHANNEL_CARD);
		request.setPayOrg(channel.getPayOrg());
		request.setRequestURL(SysCommand.AGENCY_FUND_URL);
		request.setNoticeUrl(SysCommand.BILL_NOTICE_URL);
		List<TransDetails> details=trans.getTransDetailsList();
		if(details!=null&&details.size()>0){
			Goods goods=details.get(0).getGoods();
			if(goods!=null){
				request.setOrderDisplay(goods.getGoodsName());
			}
		}
		PayResponse payRes=null;
		try {
			payRes=BillFunc.requestPay(request);
		} catch (Exception e) {
			logger.error("代收请求出现异常，交易号："+cashPay.getTransId(),e);
		}
		if(payRes!=null&&PayCommand.RESPCODE_SUCCESS.equals(payRes.getResultCode())){
			logger.info("=======代收请求发送成功，交易号："+cashPay.getTransId());
			cashPay.setState(PayState.PAY_REQUEST_SUCCESS);
			transDao.updateBillNo(payRes.getFroadBillNo(), Integer.parseInt(cashPay.getTransId()));
			payDao.updatePay(cashPay);
			return new Result(Result.SUCCESS,"支付请求发送成功");
		}else{
			logger.info("=======代收请求发送失败，交易号："+cashPay.getTransId());
			this.updateStateById(trans.getId(), TranCommand.TRAN_FAIL);
			cashPay.setState(PayState.PAY_REQUEST_FAIL);
			String msg="支付请求发送失败";
			if(payRes!=null){
				cashPay.setResultCode(payRes.getResultCode());
				if(payRes.getRemark()!=null){
					cashPay.setResultDesc(payRes.getRemark());
					msg=TranCommand.EXCEPTION_PREFIX+payRes.getRemark();
				}
			}
			payDao.updatePay(cashPay);
			return new Result(Result.FAIL,msg);
		}
	}
	
	
	/**
	  * 方法描述：发起代扣请求
	  * @param:Pay 扣款的pay记录
	  * @param:Pay 手续费的pay记录(无手续费时传null)
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 9, 2013 5:09:27 PM
	  */
	private Result deduct(Pay deductPay,Pay feePay,Trans trans){
		String total=deductPay.getPayValue();
		String amount=null;//实际扣款金额
		if(feePay==null||feePay.getPayValue()==null){
			amount=Util.formatMoney(total).toString();
		}else{
			BigDecimal real=new BigDecimal(total).add(new BigDecimal(feePay.getPayValue()));
			amount=Util.formatMoney(real).toString();
		}
		FundsChannel channel=deductPay.getFundsChannel();
		if(channel==null){
			logger.error("======资金渠道为空，代扣请求失败，交易号："+trans.getId());
			return new Result(Result.FAIL,"资金渠道为空");
		}
		Integer transId=Integer.parseInt(deductPay.getTransId());
		PayRequest request=new PayRequest();
		request.setOrderID("fbu"+transId+"x"+deductPay.getId());
		request.setFromAccountName(deductPay.getFromAccountName());
		request.setFromAccountNum(deductPay.getFromAccountNo());
		request.setToAccountName(deductPay.getToAccountName());
		request.setToAccountNum(deductPay.getToAccountNo());
		request.setFromPhone(deductPay.getFromPhone());
		request.setPayerAmount(amount);
		request.setPayeeAmount(amount);
		request.setPayerType(PayCommand.TRADER_TYPE_MERCHANT);
		request.setPayerWay(TranCommand.PAY_CHANNEL_CARD);
		request.setPayeeType(PayCommand.TRADER_TYPE_FROAD);
		request.setPayeeWay(TranCommand.PAY_CHANNEL_CARD);
		request.setPayOrg(channel.getPayOrg());
		request.setRequestURL(SysCommand.DEDUCT_URL);
		request.setNoticeUrl(SysCommand.BILL_NOTICE_URL);
		List<TransDetails> details=trans.getTransDetailsList();
		if(details!=null&&details.size()>0){
			Goods goods=details.get(0).getGoods();
			if(goods!=null){
				request.setOrderDisplay(goods.getGoodsName());
			}
		}
		PayResponse payRes=null;
		try {
			payRes=BillFunc.requestPay(request);
		} catch (Exception e) {
			logger.error("代扣请求出现异常，交易号："+transId,e);
		}
		String code="",msg="";
		if(payRes!=null&&PayCommand.RESPCODE_SUCCESS.equals(payRes.getResultCode())){
			logger.info("=======代扣请求发送成功，交易号："+transId+"=======");
			deductPay.setState(PayState.PAY_REQUEST_SUCCESS);
			transDao.updateBillNo(payRes.getFroadBillNo(),transId);
			code=Result.SUCCESS;
			msg="支付请求发送成功";
		}else{
			logger.info("=======代扣请求发送失败，交易号："+transId+"=======");
			transDao.updateStateById(transId, TranCommand.TRAN_FAIL);
			deductPay.setState(PayState.PAY_REQUEST_FAIL);
			code=Result.FAIL;
			msg="支付请求发送失败";
			if(payRes!=null){
				deductPay.setResultCode(payRes.getResultCode());
				if(payRes.getRemark()!=null){
					deductPay.setResultDesc(payRes.getRemark());
					msg=TranCommand.EXCEPTION_PREFIX+payRes.getRemark();
				}
			}
		}
		payDao.updatePay(deductPay);
		if(feePay!=null){
			feePay.setState(deductPay.getState());
			feePay.setResultCode(deductPay.getResultCode());
			payDao.updatePay(feePay);//更新手续费的pay记录
		}
		logger.info("=======代扣请求完成========");
		return new Result(code,msg);
	}
	
	private Result combinePay(Pay pay,Trans trans){
		CombineRequest reqBean = new CombineRequest();
		OrderDetail detail = new OrderDetail();
		detail.setOrderID(pay.getTransId()+"x"+pay.getId());
		detail.setOrderAmount(pay.getPayValue());
		detail.setOrderSupplier(TranCommand.PARTNER_ID);
		List<TransDetails> transDetails=trans.getTransDetailsList();
		if(transDetails!=null&&transDetails.size()>0){
			Goods goods=transDetails.get(0).getGoods();
			if(goods!=null){
				detail.setOrderDisplay(PayCommand.MERCHANTNAME+"|"+goods.getGoodsName());
			}
		}
		reqBean.getOrderDetailList().add(detail);
		// 订单数据
		reqBean.setOrderDisplay("分分通订单号"+pay.getTransId());
		reqBean.setOrderType(PayCommand.ORDER_TYPE_COMBINE);
		reqBean.setOrderRemark("分分通合并支付");
		if(TranCommand.PAY_CHANNEL_ALIPAY.equals(trans.getPayChannel())){
			reqBean.setPayType(PayCommand.PAY_TYPE_ALIPAY);
			reqBean.setPayOrg(TranCommand.ALIPAY_ORG_NO);
		}else{
			reqBean.setPayType(PayCommand.PAY_TYPE_FILM);
			FundsChannel channel=pay.getFundsChannel();
			if(channel==null){
				logger.error("资金渠道为空，代收请求失败");
				return new Result(Result.FAIL,"资金渠道为空");
			}
			reqBean.setPayOrg(channel.getPayOrg());
		}
		if(PayCommand.TRADER_TYPE_FROAD.equals(pay.getToRole())){
			reqBean.setPayDirect("10");
		}else{
			reqBean.setPayDirect("20");
		}
		reqBean.setClient(trans.getClientType());
		reqBean.setTotalAmount(pay.getPayValue());
		reqBean.setPayerMember(pay.getFromPhone());
		
		try {
			CombineResponse response=BillFunc.combineOrder(reqBean);
			if(PayCommand.RESPCODE_SUCCESS.equals(response.getResultCode())){
				payDao.updateStateById(pay.getId(), PayState.PAY_REQUEST_SUCCESS);
				transDao.updateBillNo(response.getFroadBillNo(), trans.getId());
				String msg=response.getPaymentURL();
				if(msg==null||"".equals(msg)){
					msg="账单下推成功，等待付款！";
				}
				return new Result(Result.SUCCESS,msg);
			}else{
				payDao.updateStateById(pay.getId(), PayState.PAY_REQUEST_FAIL);
				return new Result(Result.FAIL,"合并支付请求失败，结果码："+response.getResultCode());
			}
		} catch (AppException e) {
			logger.error("合并支付请求异常",e);
			payDao.updateStateById(pay.getId(), PayState.PAY_REQUEST_FAIL);
			return new Result(Result.FAIL,e.getMessage());
		}
	}
	
	
	/**
	  * 方法描述：发起退款
	  * @param: Pay 待退款的pay记录
	  * @param: refundReason 退款理由
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 25, 2013 11:42:01 AM
	  */
	public boolean refund(Pay cashPay,String refundReason){
		RefundRequest req=new RefundRequest();
		String refundOrderId=KeyGenerator.generate("fbu");
		req.setRefundOrderID(refundOrderId);
		req.setRefundAmount(cashPay.getPayValue());
		req.setOrderAmount(cashPay.getPayValue());
		req.setRefundReason(refundReason);
		req.setRefundType(PayCommand.REFUND_ALL);
		req.setCombinePay(PayCommand.TRADER_TYPE_FROAD.equals(cashPay.getToRole()));
		//TODO 合并支付combineOrder方法和这个地方的orderId应该统一有fbu前缀，等openapi放开orderId的限制
		if(req.getCombinePay()){
			req.setOrderSupplier(TranCommand.PARTNER_ID);
			req.setOrderId(cashPay.getTransId()+"x"+cashPay.getId());
		}else{
			req.setOrderId("fbu"+cashPay.getTransId()+"x"+cashPay.getId());
		}
		RefundResponse refundRes=null;
		try {
			refundRes=BillFunc.requestRefund(req);
		} catch (Exception e) {
			logger.error("发送退款请求出现异常，交易号： "+cashPay.getTransId(),e);
		}
		if(refundRes!=null&&PayCommand.RESPCODE_SUCCESS.equals(refundRes.getResultCode())){
			logger.info("======退款请求发送成功，交易号："+cashPay.getTransId());
			cashPay.setState(PayState.REFUND_REQ_SUCCESS);
		}else{
			logger.info("=======退款请求发送失败，交易号："+cashPay.getTransId());
			cashPay.setState(PayState.REFUND_REQ_FAIL);
			if(refundRes!=null){
				cashPay.setResultCode(refundRes.getResultCode());
			}
		}
		payDao.updatePay(cashPay);
		transDao.updateRefundOrderIdById(refundOrderId, Integer.parseInt(cashPay.getTransId()));
		return PayState.REFUND_REQ_SUCCESS.equals(cashPay.getState());
	}
	
	
	/**
	  * 方法描述：组装消费和扣除积分的Points参数
	  * @param: pointsPay 积分的pay表记录
	  * @return: Points
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 8, 2013 6:06:17 PM
	  */
	private Points getPoints(Pay pointsPay){
		Points points=new Points();
		points.setObjectNo(pointsPay.getTransId());
		points.setAccountId(pointsPay.getFromAccountNo());
		points.setPoints(pointsPay.getPayValue());
		points.setAccountMarked(pointsPay.getFromUsername());
		if(RuleDetailType.DEDUCT_POINTS.getValue().equals(pointsPay.getTypeDetail())){
			points.setObjectType(PointCommand.OBJECT_TYPE_WITHDRAW);
		}else{
			points.setObjectType(PointCommand.OBJECT_TYPE_CONSUME);
		}
		return points;
	}
	
	
	/**
	  * 方法描述：消费积分
	  * @param: pointsPay 积分的pay表记录
	  * @return: Points
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 8, 2013 6:09:04 PM
	  */
	private Points payPoints(Pay pointsPay){
		Points pointsRes=null;
		if(PayState.POINT_PAY_SUCCESS.equals(pointsPay.getState())){
			logger.info("积分已支付成功");
			pointsRes=new Points();
			pointsRes.setResultCode(PayCommand.RESPCODE_SUCCESS);
			return pointsRes;
		}
		Points points=getPoints(pointsPay);
		try {
			pointsRes = PointsFunc.payPoints(points);
		} catch (AppException e1) {
			logger.error("积分扣除时出现异常，交易号："+pointsPay.getTransId(),e1);
		}
		if(pointsRes!=null&&PayCommand.RESPCODE_SUCCESS.equals(pointsRes.getResultCode())){
			pointsPay.setState(PayState.POINT_PAY_SUCCESS);
			transDao.updatePointBillNo(pointsRes.getPayPointsNo(), Integer.parseInt(pointsPay.getTransId()));
		}else{
			logger.error("积分扣除失败，交易号："+pointsPay.getTransId());
			pointsPay.setState(PayState.POINT_PAY_FAIL);
			if(pointsRes!=null){
				pointsPay.setResultCode(pointsRes.getResultCode());
			}
		}
		payDao.updatePay(pointsPay);
		return pointsRes;
	}
	
	
	/**
	  * 方法描述：退还积分
	  * @param: pointsPay 积分的pay表记录
	  * @param: payPointsNo 积分消费编号
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 8, 2013 6:02:49 PM
	  */
	public boolean refundPoints(Pay pointsPay,String payPointsNo){
		Points points=getPoints(pointsPay);
		points.setPayPointsNo(payPointsNo);
		Points refundRes=null;
		try {
			refundRes = PointsFunc.refundPoints(points);
		} catch (AppException e) {
			logger.error("积分退还异常，交易号："+pointsPay.getTransId(),e);
		}
		if(refundRes!=null&&PayCommand.RESPCODE_SUCCESS.equals(refundRes.getResultCode())){
			pointsPay.setState(PayState.POINT_REFUND_SUCCESS);
		}else{
			logger.error("=========积分退还失败，交易号： "+pointsPay.getTransId());
			pointsPay.setState(PayState.POINT_REFUND_FAIL);
			if(refundRes!=null){
				pointsPay.setResultCode(refundRes.getResultCode());
			}
		}
		payDao.updatePay(pointsPay);//更新积分扣除的pay记录
		return PayState.POINT_REFUND_SUCCESS.equals(pointsPay.getState());
	}
	
	
	/**
	  * 方法描述：返利积分(用于收银台的赠送积分和收款后的返积分)
	  * @param: Pay 返利积分的pay记录
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 15, 2013 5:08:57 PM
	  */
	private boolean rebatePoints(Pay pointsPay){
		Rebate rebate=new Rebate();
		rebate.setPoints(pointsPay.getPayValue());
		rebate.setObjectNo(pointsPay.getTransId());
		rebate.setAccountMarkedFrom(pointsPay.getFromUsername());
		rebate.setAccountMarkedTo(pointsPay.getToUsername());
		rebate.setFromPhone(pointsPay.getFromPhone());
		rebate.setToPhone(pointsPay.getToPhone());
		Rebate rebateRes=null;
		try {
			rebateRes=PointsFunc.rebatePoints(rebate);
		} catch (Exception e) {
			logger.error("积分返利异常，交易号："+pointsPay.getTransId(),e);
		}
		if(rebateRes!=null&&PayCommand.RESPCODE_SUCCESS.equals(rebateRes.getResultCode())){
		
			Merchant m = merchantDAO.selectByPrimaryKey(transDao.getTransById(Integer.parseInt(pointsPay.getTransId())).getMerchantId());
			
			String merchantName = m.getMstoreShortName();
			
			
			pointsPay.setState(PayState.POINT_REBATE_SUCCESS);
			transDao.updatePointBillNo(rebateRes.getFillAndPresendInfoNo(), Integer.parseInt(pointsPay.getTransId()));
			//给用户发送返积分成功的短信
			User user=userDao.queryUserAllByUsername(pointsPay.getToUsername());
			String[]msg={merchantName,pointsPay.getPayValue()};
			String content=MessageSourceUtil.getSource().getMessage("rebate_points", msg, null);
			SmsLog smsLog=new SmsLog();
			smsLog.setMobile(user.getMobilephone());
			smsLog.setMessage(content);
			smsLog.setType(SmsLogType.REBATE_POINTS);
			messageService.sendMessage(smsLog);
		}else{
			logger.error("=======积分返利失败，交易号："+pointsPay.getTransId());
			pointsPay.setState(PayState.POINT_REBATE_FAIL);
			if(rebateRes!=null){
				pointsPay.setResultCode(rebateRes.getResultCode());
			}
		}
		payDao.updatePay(pointsPay);//更新积分返利的pay记录
		return PayState.POINT_REBATE_SUCCESS.equals(pointsPay.getState());
	}
	
	
	
	/**
	  * 方法描述：增送积分(商户在积分兑换商品上送积分)
	  * Points(orgNo,objectNo,objectDes,objectType,
	  *                point,accountMarked,accountMarkedType) 
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 27, 2014 4:31:51 PM
	  */
	private boolean presentPoints(Pay pointsPay){
		Points points=new Points();
		points.setOrgNo(TranCommand.FFT_ORG_NO);
		points.setObjectNo(pointsPay.getTransId());
		points.setObjectDes(pointsPay.getRemark());
		points.setPoints(pointsPay.getPayValue());
		points.setAccountMarked(pointsPay.getToUsername());
		points.setAccountMarkedType(PointCommand.ACCOUNT_MARKED_TYPE);
		Points pointsRes=null;
		try {
			pointsRes=PointsFunc.presentPoints(points);
		} catch (Exception e) {
			logger.error("给用户增送积分时出现异常，交易号："+pointsPay.getTransId(),e);
		}
		if(pointsRes!=null&&PayCommand.RESPCODE_SUCCESS.equals(pointsRes.getResultCode())){
			logger.info("积分增送成功，交易号："+pointsPay.getTransId());
			Merchant m = merchantDAO.selectByPrimaryKey(transDao.getTransById(Integer.parseInt(pointsPay.getTransId())).getMerchantId());
			
			String merchantName = m.getMstoreShortName();
			
			pointsPay.setState(PayState.POINT_PRESENT_SUCCESS);
			transDao.updatePointBillNo(pointsRes.getPresentPointsNo(), Integer.parseInt(pointsPay.getTransId()));
			//给用户发送返积分成功的短信
			User user=userDao.queryUserAllByUsername(pointsPay.getToUsername());
			String[]msg={merchantName,pointsPay.getPayValue()};
			String content=MessageSourceUtil.getSource().getMessage("rebate_points", msg, null);
			SmsLog smsLog=new SmsLog();
			smsLog.setMobile(user.getMobilephone());
			smsLog.setMessage(content);
			smsLog.setType(SmsLogType.REBATE_POINTS);
			messageService.sendMessage(smsLog);
		}else{
			logger.error("=======积分增送失败，交易号："+pointsPay.getTransId());
			pointsPay.setState(PayState.POINT_PRESENT_FAIL);
			if(pointsRes!=null){
				pointsPay.setResultCode(pointsRes.getResultCode());
			}
		}
		payDao.updatePay(pointsPay);//更新增送积分的pay记录
		return PayState.POINT_PRESENT_SUCCESS.equals(pointsPay.getState());
	}
	
	
	/**
	  * 方法描述：为查询出来的trans.transDetails关联出Goods**Rack和Goods
	  * @param: Trans
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 14, 2013 4:50:38 PM
	  */
	private void joinGoods(Trans trans){
		if(trans!=null){
			String key=trans.getClientType()+trans.getTransType();
			String classname=GoodsCommand.CLAZZ_NAMES.get(key);
			if(classname!=null){
				List<TransDetails> details=trans.getTransDetailsList();
				if(details!=null&&details.size()>0){
					TransDetails tmp=null;
					Object baseGoods=null;//上架商品对象
					String goodsRackId=null;
					Method method=null;
					StringBuffer clazzName=null;
					String methodName=null,totalName=null;
					for (int i = 0; i <details.size(); i++) {
						tmp=details.get(i);
						goodsRackId=tmp.getGoodsRackId();
						if(goodsRackId!=null&&!"".equals(goodsRackId)){
							baseGoods=commonGoodsDaoImpl.getCommonGoodsById(classname, Integer.parseInt(goodsRackId));
							clazzName=new StringBuffer();
							clazzName.append(classname.substring(0,1).toUpperCase());
							clazzName.append(classname.substring(1));
							methodName="set"+clazzName.toString();
							totalName=GoodsCommand.PREFIX+clazzName.toString();
							try {
								method=TransDetails.class.getMethod(methodName,Class.forName(totalName));
								if(baseGoods!=null){
									method.invoke(tmp, baseGoods);//为交易详情里的上架商品对象设值
									Method getGoods=baseGoods.getClass().getMethod("getGoods",new Class<?>[0]);
									Method setGoods=TransDetails.class.getMethod("setGoods", Goods.class);
									Goods goods=(Goods)getGoods.invoke(baseGoods,new Object[0]);
									setGoods.invoke(tmp, goods);//为交易详情里的商品对象设值
								}
							} catch (SecurityException e) {
								e.printStackTrace();
							} catch (NoSuchMethodException e) {
								e.printStackTrace();
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}


	@Override
	public Trans getTransByLotteryBillNo(String lotteryBillNo) {
		if(lotteryBillNo==null||"".equals(lotteryBillNo)){
			logger.error("彩票交易号为空，查询失败");
			return null;
		}
		return transDao.getTransByLotteryBillNo(lotteryBillNo);
	}

	@Override
	public List<Trans> getTransByMerchantId(String merchantId) {
		if(merchantId==null||"".equals(merchantId)){
			logger.error("商户编号为空");
			return null;
		}
		return transDao.getTransByMerchantId(merchantId);
	}

	@Override
	public Trans doTrans(Trans trans) throws BusinessException {
		Trans transRes=null;
		try {
			transRes = this.addTrans(trans);
		} catch (AppException e) {
			throw new BusinessException(9999,e.getMessage());
		}
		if(transRes==null){
			logger.error("======传入的trans为null，交易添加失败======");
			transRes=new Trans();
			transRes.setRespCode(TranCommand.RESP_CODE_ADD_FAIL);
			transRes.setRespMsg("传入的trans为null");
			return transRes;
		}
		String transType=transRes.getTransType();
		Integer transId=transRes.getId();
		boolean isOk=false;
		if(transType.equals(TranCommand.POINTS_EXCH_CASH)){
//			isOk=this.deductPoints(transId);
			isOk=this.applyWithdrawPoints(transId);
		}else if(transType.equals(TranCommand.POINTS_EXCH_PRODUCT)||
				transType.equals(TranCommand.GROUP)||
				transType.equals(TranCommand.PRESELL)){
			Result result=this.doPay(transId);
			isOk=Result.SUCCESS.equals(result.getCode());
			if(TranCommand.PAY_CHANNEL_ALIPAY.equals(trans.getPayChannel())){
				transRes.setPaymentUrl(result.getMsg());
			}
		}else if(transType.equals(TranCommand.PRESENT_POINTS)){
			isOk = this.doDeduct(transId);
		}else if(transType.equals(TranCommand.POINTS_REBATE)||
				transType.equals(TranCommand.COLLECT)){
			isOk = this.doCollect(transId);
		}
		if(isOk){
			logger.info("交易下推成功，交易号为： "+transId);
			transRes.setRespCode(TranCommand.RESP_CODE_PAY_REQ_OK);
			transRes.setRespMsg("交易下推成功");
			return transRes;
		}
		transRes.setRespCode(TranCommand.RESP_CODE_PAY_REQ_FAIL);
		transRes.setRespMsg("交易添加完成，支付失败");
		logger.error("=======交易添加完成，下推失败，交易号： "+transId);
		return transRes;
	}

	@Override
	public String getTransStateById(Integer transId) {
		if(transId==null){
			logger.error("传入的交易号为空");
			return null;
		}
		return transDao.getTransStateById(transId);
	}

	@Override
	public boolean refuse(Integer transId,String reason) throws BusinessException {
		Trans trans=this.getTransById(transId);
		boolean isValid=check(trans);//验证参数
		if(!isValid){
			logger.error("无效的交易参数，交易号："+transId);
			return true;
		}
		List<Pay> payList=trans.getPayList();
		Pay pointsPay=null,tmpPay=null;
		for (int i = 0; i < payList.size(); i++) {
			tmpPay=payList.get(i);
			if(RuleDetailType.DEDUCT_POINTS.getValue().equals(tmpPay.getTypeDetail())){
				//扣积分的pay记录
				pointsPay=tmpPay;
				break;
			}
		}
		if(pointsPay==null){
			logger.error("积分的pay记录不存在，交易号："+transId);
			return true;
		}
		if(!PayState.POINT_PAY_SUCCESS.equals(pointsPay.getState())&&!PayState.POINT_REFUND_FAIL.equals(pointsPay.getState())){
			logger.error("当前的积分状态为："+pointsPay.getState()+"，不需要退积分，交易号："+transId);
			return true;
		}
		/*********退积分并更新交易*********/
		boolean isOk=this.refundPoints(pointsPay, trans.getPointBillNo());
		if(isOk){
			Trans tmpTrans=new Trans();
			tmpTrans.setId(transId);
			tmpTrans.setState(TranCommand.TRAN_CLOSE);
			tmpTrans.setRemark(reason);
			this.updateById(tmpTrans);
		}else{
			logger.error("积分退还失败！交易号："+transId);
		}
		return isOk;
	}

	
	/**
	  * 方法描述：更新商品销量和库存
	  * @param: Trans 数据库查询出来的交易对象
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: May 9, 2013 2:08:22 PM
	  */
	public void updateStore(Trans trans){
		List<TransDetails> detailsList=trans.getTransDetailsList();
		TransDetails details=detailsList.get(0);
		int goodsNumber=Integer.parseInt(details.getGoodsNumber());//购买的商品数
		/********减去库存********/
		Goods goods=details.getGoods();
		int store=goodsDao.getStoreById(goods.getId());//商品库存
		if(store<goodsNumber){
			logger.error("=========库存不足========");
			return;
		}
		goodsDao.reduceStoreById(goodsNumber, goods.getId());
		/********增加销量********/
		String transType=trans.getTransType();
		String namespace=null;//sqlmap里的namespace
		if(TranCommand.GROUP.equals(transType)){
			namespace="goodsGroupRack";
		}else if(TranCommand.POINTS_EXCH_PRODUCT.equals(transType)){
			namespace="goodsExchangeRack";
		}else{
			logger.error("交易类型:"+transType+"，不支持更新销量和库存。交易号:"+trans.getId());
			return;
		}
		if(!PayCommand.CLIENT_PC.equals(trans.getClientType())){
			namespace="client"+namespace.substring(0,1).toUpperCase()+namespace.substring(1);
		}
		commonGoodsDaoImpl.addSaleNumberById(namespace, goodsNumber, Integer.parseInt(details.getGoodsRackId()));
	}

	@Override
	public boolean doCollect(Integer transId) throws BusinessException {
		Trans trans=this.getTransById(transId);
		boolean isValid=check(trans);//验证参数
		if(!isValid){
			throw new BusinessException(9999,"交易参数校验不通过");
		}
		List<Pay> payList=trans.getPayList();
		Pay collectPay=null,tmpPay=null;
		for (int i = 0; i < payList.size(); i++) {
			tmpPay=payList.get(i);
			if(RuleDetailType.PAY_COLLECT.getValue().equals(tmpPay.getTypeDetail())){
				collectPay=tmpPay;//收款
				break;
			}
		}
		
		if(PayState.PAY_REQUEST_SUCCESS.equals(collectPay.getState())){
			logger.error("收款请求已经发送成功，请不要重复操作。交易号："+transId);
			throw new BusinessException(9999,"收款请求已经发送成功，请不要重复操作。");
		}
		/**************发起代收请求**************/
		Result result=this.collect(collectPay, trans);
		if(Result.SUCCESS.equals(result.getCode())){
			return true;
		}else{
			throw new BusinessException(9999,result.getMsg());
		}
	}

	@Override
	public boolean rebatePoints(Integer transId) throws BusinessException {
		Trans trans=transDao.getTransById(transId);
		if(trans==null){
			logger.error("交易不存在，交易号为："+transId);
			throw new BusinessException(9999,"交易不存在");
		}
		List<Pay> payList=trans.getPayList();
		Pay rebatePay=null,tmpPay=null;
		for (int i = 0; i <payList.size(); i++) {
			tmpPay=payList.get(i);
			if(RuleDetailType.SEND_POINTS.getValue().equals(tmpPay.getTypeDetail())){
				rebatePay=tmpPay;
				break;
			}
		}
		if(rebatePay==null){
			logger.error("返积分的支付记录不存在，交易号："+transId);
			throw new BusinessException(9999,"返积分的支付记录不存在");
		}
		if(PayState.POINT_REBATE_SUCCESS.equals(rebatePay.getState())){
			logger.error("积分已经返成功，请不要重复操作");
			throw new BusinessException(9999,"积分已经返成功，请不要重复操作");
		}
		return this.rebatePoints(rebatePay);
	}

	@Override
	public boolean refund(Integer transId, String reason) throws BusinessException {
		Trans trans=transDao.getTransById(transId);
		if(trans==null){
			logger.error("交易不存在，交易号为："+transId);
			throw new BusinessException(9999,"交易不存在");
		}
		List<Pay> payList=trans.getPayList();
		Pay tmpPay=null,cashPay=null;
		for (int i = 0; i <payList.size(); i++) {
			tmpPay=payList.get(i);
			if(RuleDetailType.PAY_COLLECT.getValue().equals(tmpPay.getTypeDetail())){
				cashPay=tmpPay;
				break;
			}
		}
		if(cashPay==null){
			logger.error("金额支付的记录不存在，交易号："+transId);
			throw new BusinessException(9999,"金额支付的记录不存在");
		}
		String state=cashPay.getState();
		if(PayState.REFUND_SUCCESS.equals(state)){
			logger.error("已经退款，交易号："+transId);
			throw new BusinessException(9999,"已经退款");
		}
		if(PayState.REFUND_REQ_SUCCESS.equals(state)){
			logger.error("退款请求已经发送成功，请不要重复发起。交易号："+transId);
			throw new BusinessException(9999,"退款请求已经发起成功，重复的退款请求");
		}
		if(PayState.PAY_WAIT.equals(state)||PayState.PAY_REQUEST_SUCCESS.equals(state)
				||PayState.PAY_REQUEST_FAIL.equals(state)||
				PayState.PAY_FAIL.equals(state)){
			logger.error("该笔交易没有支付，不能发起退款。交易号："+transId+" 当前的支付状态："+state);
			throw new BusinessException(9999,"该笔交易没有支付，不能发起退款");
		}
		return this.refund(cashPay, reason);
	}

	@Override
	public boolean refundPoints(Integer transId) throws BusinessException {
		Trans trans=transDao.getTransById(transId);
		return this.refundPoints(trans);
	}
	
	private boolean refundPoints(Trans trans) throws BusinessException {
		if(trans==null){
			logger.error("交易不存在");
			throw new BusinessException(9999,"交易不存在");
		}
		Integer transId=trans.getId();
		List<Pay> payList=trans.getPayList();
		Pay tmpPay=null,pointsPay=null;
		String detail=null;
		for (int i = 0; i <payList.size(); i++) {//查出各类pay
			tmpPay=payList.get(i);
			detail=tmpPay.getTypeDetail();
			if(RuleDetailType.PAY_BANK_POINTS.getValue().equals(detail)||
					RuleDetailType.PAY_POINTS.getValue().equals(detail)||
					RuleDetailType.DEDUCT_POINTS.getValue().equals(detail)){
				pointsPay=tmpPay;
				break;
			}
		}
		if(pointsPay==null){
			logger.error("积分支付记录不存在，交易号："+transId);
			throw new BusinessException(9999,"积分支付记录不存在");
		}
		if(!PayState.POINT_PAY_SUCCESS.equals(pointsPay.getState())&&
				!PayState.POINT_REFUND_FAIL.equals(pointsPay.getState())){
			logger.error("当前的积分支付状态为："+pointsPay.getState()+"，不符合退分条件。交易号： "+transId);
			throw new BusinessException(9999,"积分没有支付成功或已经退还");
		}
		return this.refundPoints(pointsPay, trans.getPointBillNo());
	}

	
	
	/**
	  * 方法描述：退积分的业务方法(用于定时任务)
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Aug 12, 2013 3:37:37 PM
	  */
	public void refundPointsTask(){
		logger.info("开始查询所有需要退积分的交易");
		List<Trans> transList=this.getExceptionTrans(ExceptionTransType.ALL_REFUND_POINTS);	
		//退积分.如果成功,则关闭交易
		if(transList!=null&&transList.size()>0){
			List<Integer> dbIdList=transDao.getNoNeedCloseTransId();
			if(dbIdList!=null&&dbIdList.size()>0){
				for (int i = 0; i < transList.size(); i++) {
					if(dbIdList.contains(transList.get(i).getId())){
						transList.remove(i);
						i--;
					}
				}
			}
			logger.info("======共有"+transList.size()+"笔交易需要退积分=====");
			List<Integer> idList=new ArrayList<Integer>();
			for (int i = 0; i < transList.size(); i++) {
				try {
					boolean ok=this.refundPoints(transList.get(i));
					if(ok){
						logger.info("定时任务执行退积分成功,交易ID为："+transList.get(i).getId());
						idList.add(transList.get(i).getId());
					}
					Thread.sleep(500);
				} catch (BusinessException e) {
					logger.error("定时任务退积分异常",e);
					continue;
				} catch (InterruptedException e) {
					logger.error("定时任务退积分异常",e);
				} catch (Exception e){
					logger.error("定时任务退积分异常",e);
					continue;
				}
			}
			if(idList.size()>0){
				logger.info("======成功退还积分的交易数："+idList.size()+"，积分退还失败的交易数： "+(transList.size()-idList.size()));
				transDao.closeTransByIdList(idList);
			}
		}
	}
	

	@Override
	public List<Trans> getExceptionTrans(ExceptionTransType exceptionTransType) {
		switch (exceptionTransType) {
			case DEDUCT_REBATE:
				return transDao.getDeductTrans();
			case REBATE:
				return transDao.getRebateTrans();
			case REFUND:
				return transDao.getRefundTrans();
			case ALL_REFUND_POINTS:
				return transDao.getAllRefundPointsTrans();
			case REFUND_POINTS:
				return transDao.getRefundPointsTrans();
			case REFUND_TIMEOUT_POINTS:
				return transDao.getTimeoutPointsTrans();
			default:
				return null;
		}
	}

	@Override
	public Trans getDataToRepExcel(Trans trans) {
		Trans tr = new Trans();
		if(trans==null){
			logger.error("参数为空");
			return null;
		}
		if(TranCommand.POINTS_EXCH_PRODUCT.equals(trans.getTransType())
                || TranCommand.GROUP.equals(trans.getTransType())
                || TranCommand.PRESELL.equals(trans.getTransType()))
        {
			tr = transDao.getExcelDataToExpExchangeOrGroup(trans);
		}else{
			tr = transDao.getExcelDataToExpTrans(trans);
		}
		
		List<?> list=tr.getList();
		if(list!=null&&list.size()>0){
			for (int i = 0; i <list.size(); i++) {
				this.joinGoods((Trans)list.get(i));
			}
		}
		return tr;
	}

	@Override
	public Trans getTransByPagerFinance(Trans trans) {
		if(trans==null){
			logger.error("参数为空");
			return null;
		}
		Trans tr=transDao.getTransByPagerFinance(trans);
		List<?> list=tr.getList();
		if(list!=null&&list.size()>0){
			for (int i = 0; i <list.size(); i++) {
				this.joinGoods((Trans)list.get(i));
			}
		}
		return tr;
	}
	public Trans getGroupOrExchangeTransByPagerFinance(Trans trans) {
		if(trans==null){
			logger.error("参数为空");
			return null;
		}
		Trans tr=transDao.getGroupOrExchangeTransByPagerFinance(trans);
		List<?> list=tr.getList();
		if(list!=null&&list.size()>0){
			Goods temp = new Goods();
			Trans ttemp;
			for (int i = 0; i <list.size(); i++) {
				ttemp = (Trans)list.get(i);
				this.joinGoods(ttemp);
				
				temp.setTable(itemizeTableToGetGoods(ttemp.getTransType(),ttemp.getClientType()));
				temp.setGoodsRockId(ttemp.getTransDetailsList().get(0).getGoodsRackId());
				ttemp.setGoodsName(goodsDao.useGoodsRackIdAndTableGetGoodsName(temp));
			}
		}
		return tr;
	}
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public void setTransDetailsDao(TransDetailsDao transDetailsDao) {
		this.transDetailsDao = transDetailsDao;
	}

	public void setTransGoodsAttributeDao(
			TransGoodsAttributeDao transGoodsAttributeDao) {
		this.transGoodsAttributeDao = transGoodsAttributeDao;
	}

	public void setPayDao(PayDao payDao) {
		this.payDao = payDao;
	}
	
	public void setCommonGoodsDaoImpl(CommonGoodsDaoImpl commonGoodsDaoImpl) {
		this.commonGoodsDaoImpl = commonGoodsDaoImpl;
	}

	public void setTransDao(TransDao transDao) {
		this.transDao = transDao;
	}

	public void setAuthTicketService(AuthTicketService authTicketService) {
		this.authTicketService = authTicketService;
	}

	public void setGoodsDao(GoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}

	public void setGoodsGatherRackDao(GoodsGatherRackDao goodsGatherRackDao) {
		this.goodsGatherRackDao = goodsGatherRackDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public MerchantDAO getMerchantDAO() {
		return merchantDAO;
	}

	public void setMerchantDAO(MerchantDAO merchantDAO) {
		this.merchantDAO = merchantDAO;
	}

	public static String itemizeTableToGetGoods(String transType,String clickType){
		String tableName = null;
		if("01".equals(transType)){
			//积分兑换
			tableName = "goods_exchange_rack";
		}else if("02".equals(transType)){
			//团购交易
			tableName = "goods_group_rack";
		}else if("03".equals(transType)){
			//返利积分   
			tableName = "goods_gather_rack";
		}else if("04".equals(transType)){
			//积分提现交易
			tableName = "goods_carry_rack";
		}else if("06".equals(transType)){
			//送积分交易
			tableName = "goods_gather_rack";
		} else if("08".equals(transType)){
        			//送积分交易
        			tableName = "goods_presell_rack";
        		}
		if(!"100".equals(clickType==null?"":clickType.trim())){
			//如果不是电脑版发起的交易			
			if("01".equals(transType)){
				//积分兑换
				tableName = "client_goods_exchange_rack";
			}else if("02".equals(transType)){
				//团购交易
				tableName = "client_goods_group_rack";
			}else if("04".equals(transType)){
				//积分提现交易
				tableName = "client_goods_carry_rack";
			}			
		}
		return tableName;
	}

	@Override
	public Trans getTransByPagerMgmt(Trans trans) {
		if(trans==null){
			logger.error("参数为空");
			return null;
		}
//		trans.setBeginTime(trans.getBeginTime().substring(0, trans.getBeginTime().indexOf("|")));
//		trans.setEndTime(trans.getEndTime().substring(0,trans.getEndTime().indexOf("|")));
		//根据商户名获取符合调件的商户id集合
		List<Merchant> mlist=null;
		if(trans.getMerchant()!=null &&  trans.getMerchant().getCompanyShortName()!=null && !"".equals(trans.getMerchant().getCompanyShortName())){
			mlist=merchantDAO.getAllMerchant(trans.getMerchant());					
			if(mlist!=null && mlist.size()>0){
				List<String> l=new ArrayList<String>();
				for(Merchant mer:mlist){
					l.add(mer.getId().toString());				
				}
				trans.setMerchantIdList(l);
			}else{
				return new Trans();
			}
		}
		Trans tr=transDao.getTransByPager(trans);
		List<?> list=tr.getList();
		if(list!=null&&list.size()>0){
			for (int i = 0; i <list.size(); i++) {
				Trans t=(Trans)list.get(i);
				this.joinGoods(t);
				t.setUser(userDao.queryUserAllByUserID(t.getUserId()));
			}			
		}
		return tr;
	}

	@Override
	public boolean applyWithdrawPoints(Integer transId)
			throws BusinessException {
		Trans trans=this.getTransById(transId);
		if(trans==null){
			throw new BusinessException(9999,"交易不存在，交易号为："+transId);
		}
		User user=trans.getUser();
		if(user==null){
			throw new BusinessException(9999,"提现用户不存在，交易号为："+transId);
		}
		Points points = new Points();
		points.setOrgNo(TranCommand.FFT_ORG_NO);
		points.setAccountMarked(user.getUsername());
		points.setAccountMarkedType(PointCommand.ACCOUNT_MARKED_TYPE);
		UserCertification userCert=userCertificationService.getUserCertByUserId(user.getUserID());
		if(userCert==null){
			logger.error("还没有绑定提现银行卡，用户编号："+user.getUserID());
			throw new BusinessException(9999,"您还没有绑定提现银行卡信息");
		}
		if(StringUtils.isEmpty(userCert.getUserIdCard())){
			logger.error("还没有填写身份证信息，用户编号："+user.getUserID());
			throw new BusinessException(9999,"请完善身份证信息");
		}
		FundsChannel funds=userCert.getFundsChannel();
		if(funds==null){
			throw new BusinessException(9999,"提现银行卡对应的资金机构为空。");
		}
		points.setBankId(funds.getPayOrg());
		points.setBankName(funds.getChannelShortName());
		points.setRealName(userCert.getAccountName());
		points.setBankCard(userCert.getAccountNo());
		points.setCertNo(userCert.getUserIdCard());
		points.setPoints(trans.getFftPointsValueAll());
		points.setObjectNo("fbu"+transId);
		points.setBusinessType("分分通积分提现");
		try {
			Points pointsRes=PointsFunc.withdrawPoints(points);
			if(Command.RESP_CODE_SUCCESS.equals(pointsRes.getResultCode())){
				transDao.updatePointBillNo(pointsRes.getWithdrawPoints().getCashPointsNo(), transId);
				return true;
			}
			logger.info("积分提现失败，结果码："+pointsRes.getResultCode()+"原因："+pointsRes.getRespMsg());
		} catch (AppException e) {
			logger.error("积分提现异常",e);
		}
		return false;
	}

	public void setUserCertificationService(
			UserCertificationService userCertificationService) {
		this.userCertificationService = userCertificationService;
	}

	public void setGoodsPresellRackDao(GoodsPresellRackDao goodsPresellRackDao) {
		this.goodsPresellRackDao = goodsPresellRackDao;
	}
    /**
     * 根据交易商品号获取精品预售商品信息
     * @param goodsRackId 商品id号
     * @return
     */
    public GoodsPresellRack getGoodsPresellRackById(String goodsRackId)
    {
        return this.goodsPresellRackDao.getById(Integer.parseInt(goodsRackId));
    }
	
	
	/**
	  * 方法描述：生成并发送精品预售提货短信,同时添加短信日志
	  * @param: Trans 
	  * @return: boolean 发送成功|发送失败
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 12, 2014 4:03:01 PM
	  */
	private boolean sendPresellMessage(Trans trans){
		List<TransDetails> transDetails=trans.getTransDetailsList();
		if(transDetails==null||transDetails.size()==0){
			logger.error("交易详情为空，交易号："+trans.getId());
			return false;
		}
		TransDetails td=transDetails.get(0);
		GoodsPresellRack presell=goodsPresellRackDao.getById(Integer.parseInt(td.getGoodsRackId()));
		String beginTime=null;
		String endTime=null;
		try {
			beginTime=displayFormat.format(dateFormat1.parse(presell.getDeliveryStartTime()));
			endTime=displayFormat.format(dateFormat1.parse(presell.getDeliveryEndTime()));
		} catch (ParseException e1) {
			logger.error("预售开始时间和结束时间格式错误",e1);
			return false;
		}
		AuthTicket authTicket=new AuthTicket();
		authTicket.setUserId(trans.getUserId());
		authTicket.setMerchantId(trans.getMerchantId()+"");
		authTicket.setTransId(trans.getId()+"");
		authTicket.setType(TicketType.PRESELL);
		authTicket.setExpireTime(endTime);
		AuthTicket ticketRes=null;
		try {
			ticketRes=authTicketService.addAuthTicket(authTicket);
		} catch (AppException e) {
			logger.error("添加认证券时出现异常，添加券失败",e);
			return false;
		}
		PresellDelivery delivery=presellDeliveryDao.queryByTransId(trans.getId());
		if(delivery==null){
			logger.error("提货点信息为空，预售成团短信发送失败");
			return false;
		}
		String goodsName=presell.getGoods().getGoodsName();
		String[] msg={goodsName,ticketRes.getSecuritiesNo(),beginTime,endTime,delivery.getName(),delivery.getAddress(),delivery.getTelephone()};
		String content=MessageSourceUtil.getSource().getMessage("presell", msg, null);
		SmsLog smsLog=new SmsLog();
		smsLog.setMobile(trans.getPhone());
		smsLog.setMessage(content);
		smsLog.setType(SmsLogType.PRESELL_TAKE_PRODUCT);
		return messageService.sendMessage(smsLog);
	}
	
	
	/**
	  * 方法描述：生成并发送精品预售退款短信,同时添加短信日志
	  * @param: Trans 
	  * @return: boolean 发送成功|发送失败
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 12, 2014 4:03:01 PM
	  */
	private boolean sendPresellRefundMessage(Trans trans){
		List<TransDetails> transDetails=trans.getTransDetailsList();
		String goodsName="";
		if(transDetails!=null&&transDetails.size()>0){
			TransDetails td=transDetails.get(0);
			GoodsPresellRack presell=goodsPresellRackDao.getById(Integer.parseInt(td.getGoodsRackId()));
			goodsName=presell.getGoods().getGoodsName();
		}
		String[] msg={goodsName};
		String content=MessageSourceUtil.getSource().getMessage("presell_refund", msg, null);
		SmsLog smsLog=new SmsLog();
		smsLog.setMobile(trans.getPhone());
		smsLog.setMessage(content);
		smsLog.setType(SmsLogType.PRESELL_REFUND);
		return messageService.sendMessage(smsLog);
	}
	
	
	/**
	  * 方法描述：精品预售成团成功后的交易处理
	  * @param: List<Trans> 精品预售类型的交易
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 12, 2014 5:14:19 PM
	  */
	private void doCluster(List<Trans> transList){
		List<String> stateList=new ArrayList<String>();
		stateList.add(PayState.PAY_SUCCESS);
		stateList.add(PayState.POINT_PAY_SUCCESS);
		List<Trans> paidList=this.findPaidTrans(transList,stateList);
		if(paidList==null||paidList.size()==0){
			logger.info("没有已支付完成的精品预售交易记录");
			return;
		}
		List<Integer> sendList=new ArrayList<Integer>();
		for (int i = 0; i < paidList.size(); i++) {
			this.sendPresellMessage(paidList.get(i));
			sendList.add(paidList.get(i).getId());
		}
		transDao.batchSuccessStateById(sendList);
	}
	
	
	/**
	  * 方法描述：从交易列表中筛选出指定状态的交易列表
	  * @param: List<Trans>
	  * @return: List<Trans>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 12, 2014 3:05:55 PM
	  */
	private List<Trans> findPaidTrans(List<Trans> transList,List<String> stateList){
		if(transList==null||transList.size()==0){
			return null;
		}
		Trans trans = null;
		List<Pay> payList = null;
		Pay pay = null;
		List<Trans> results = new ArrayList<Trans>();
		String state = null;
		int count = 0;
		for (int i = 0; i < transList.size(); i++) {
			trans=transList.get(i);
			payList=trans.getPayList();
			if(payList.size()==1){//如果是纯积分或者纯金额支付
				state=payList.get(0).getState();
				if(stateList.contains(state)){
					results.add(trans);
				}
				continue;
			}
			count=0;
			for (int j = 0; j < payList.size(); j++) {
				pay=payList.get(j);
				state=pay.getState();
				if(stateList.contains(state)){
					count++;
				}
				if(count==2){
					results.add(trans);
				}
			}
		}
		return results;
	}

	@Override
	public void cluster() {
		List<Trans> transList=transDao.queryPresellTrans();
		this.doCluster(transList);
	}

	@Override
	public Result clusterByManager(Integer presellGoodsId) {
		if(presellGoodsId==null){
			return new Result(Result.FAIL,"传入的预售商品编号为空");
		}
		GoodsPresellRack presell=goodsPresellRackDao.getById(presellGoodsId);
		if(presell==null){
			return new Result(Result.FAIL,"预售商品不存在，传入编号："+presellGoodsId);
		}
		if("1".equals(presell.getIsCluster())){
			return new Result(Result.FAIL,"该预售商品已经成团");
		}
		if("2".equals(presell.getIsCluster())){
			return new Result(Result.FAIL,"该预售商品的状态为成团失败，不能进行此项操作");
		}
		int virtualNumber=this.makeVirtualNumber(presell.getTrueBuyerNum(), presell.getClusteringNum(), presell.getMaxSaleNum());
		presell.setVirtualBuyerNum(presell.getVirtualBuyerNum()+virtualNumber);
		presell.setIsCluster("1");
		presell.setClusterType("2");
		goodsPresellRackDao.updateById(presell);//更新预售商品信息
		Date endTime = null;
		try {
			endTime=dateFormat1.parse(presell.getEndTime());
		} catch (ParseException e) {
			logger.error("预售结束日期格式不正确",e);
			return new Result(Result.FAIL,"预售结束日期格式不正确");
		}
		if(endTime.compareTo(new Date())<=0){
			List<Trans> transList=transDao.queryPresellTransByGoodsRackId(presell.getId());
			this.doCluster(transList);//成团之后的处理
		}
		return new Result(Result.SUCCESS,"预售商品成功成团，操作成功");
	}
	
	
	/**
	  * 方法描述：生成虚拟参团人数
	  * @param: realSaleNumber 实际销售数量
	  * @param: clusterNumber 最低成团数量
	  * @param: maxPresell 允许的最大成团数量
	  * @param: max 最大人数
	  * @return: 虚拟参团人数
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 12, 2014 5:45:06 PM
	  */
	private Integer makeVirtualNumber(Integer realSaleNumber,Integer clusterNumber,Integer maxPresell){
		Random random=new Random();
		int min=clusterNumber-realSaleNumber;
		if(min<1){//已成团
			return 0;
		}
		int randNum=random.nextInt(clusterNumber*10);
		if(randNum>=min){
			int max=maxPresell-realSaleNumber;
			if(maxPresell<1){//没有最大数限制
				return randNum;
			}else{
				if(randNum<=max){
					return randNum;
				}
			}
		}
		return this.makeVirtualNumber(realSaleNumber, clusterNumber, maxPresell);
	}

	@Override
	public Result clusterFailedByManager(Integer presellGoodsId) {
		GoodsPresellRack presellRack=goodsPresellRackDao.getById(presellGoodsId);
		if(presellRack==null){
			return new Result(Result.FAIL,"预售商品不存在");
		}
		if("1".equals(presellRack.getIsCluster())){
			return new Result(Result.FAIL,"该预售商品的状态为成团成功，不能进行此项操作");
		}
		if("2".equals(presellRack.getIsCluster())){
			return new Result(Result.FAIL,"该预售商品的状态为成团失败，请不要重复操作");
		}
		try {
			Date endTime=dateFormat1.parse(presellRack.getEndTime());
			Date now =new Date();
			if(now.before(endTime)){
				return new Result(Result.FAIL,"预售还没有到达结束时间");
			}
		} catch (ParseException e) {
			logger.error("预售结束日期格式不正确",e);
			return new Result(Result.FAIL,"预售结束日期格式不正确");
		}
		GoodsPresellRack presell=new GoodsPresellRack();
		presell.setId(presellGoodsId);
		presell.setIsCluster("2");
		presell.setClusterType("2");
		goodsPresellRackDao.updateById(presell);
		List<Trans> transList=transDao.queryPresellTransByGoodsRackId(presellGoodsId);
		if(transList==null||transList.size()==0){
			return new Result(Result.SUCCESS,"操作成功");
		}
		String refundReason="预售商品成团失败，预售商品编号["+presellGoodsId+"]，发起退款。";
		List<Pay> payList = null;
		Pay pay = null;
		Trans trans = null;
		boolean isPaid=false;
		for (int i = 0; i < transList.size(); i++) {
			trans=transList.get(i);
			payList=trans.getPayList();
			isPaid=false;
			int size=payList.size();
			for (int j = 0; j < size; j++) {
				pay=payList.get(j);
				if(PayState.PAY_SUCCESS.equals(pay.getState())){
					isPaid=true;
					this.refund(pay, refundReason);//退款
				}else if(PayState.POINT_PAY_SUCCESS.equals(pay.getState())){
					isPaid=true;
					boolean isOk=this.refundPoints(pay, trans.getPointBillNo());//退积分
					if(isOk&&size==1){
						transDao.updateStateById(trans.getId(), TranCommand.TRAN_CLOSE);
					}
				}
			}
			if(isPaid){//给成功付款或付积分的用户发短信
				this.sendPresellRefundMessage(trans);
			}
		}
		return new Result(Result.SUCCESS,"操作成功");
	}

	@Override
	public List<Trans> getclusterFailedTrans(Integer rackId,
			List<String> payState) {
			if(rackId !=null){
				if(payState==null || payState.size()==0){
					payState=new ArrayList<String>();
					payState.add("2001");//积分支付成功
					payState.add("2004");//积分退还失败
					payState.add("2003");//积分退还成功
					
					payState.add("1004");//付款成功  --支付交易成功
					payState.add("3001");//发送退款请求成功
					payState.add("3002");//发送退款请求失败
					payState.add("3004");//退款失败
					payState.add("3003");//退款成功		
				}
				List<Trans> tlist=transDao.queryPresellTransByGoodsRackId(rackId);
				List<Trans> list=findPaidTrans(tlist, payState);
				if(list!=null&&list.size()>0){
					Trans trans;
					for (int i = 0; i <list.size(); i++) {
						trans = (Trans)list.get(i);
						this.joinGoods(trans);						
						String name=trans.getTransDetailsList().get(0).getGoodsPresellRack().getGoods().getGoodsName();
						trans.setGoodsName(name);
					}
				}
				return list;
			}else{
				logger.info("查询交易的商品Id为空");
				return null;				
			}
			
	}

	@Override
	public void doPresellTask() {
		//需要退分的trans记录
		logger.info("查询预售不成团需要退分的交易");
		List<Trans> pointList=transDao.getPresellRefundPontsTrans();
		if(pointList!=null && pointList.size()>0){
			logger.info("需退分的交易数量："+pointList.size());
			for(Trans list:pointList){
				try {
					logger.info("执行退分操作：transId:"+list.getId());
					this.refundPoints(list);
				} catch (BusinessException e) {
					logger.info("执行退积分异常",e);
					continue;
				}
			}			
		}
		//需要退款的trans记录
		logger.info("查询预售不成团需要退款的交易");
		List<Trans> cashList=transDao.getPresellRefundTrans();
		if(cashList!=null && cashList.size()>0){
			logger.info("需退款的交易数量："+cashList.size());
			for(Trans list:cashList){
				try {
					logger.info("执行退款操作：transId:"+list.getId());
					this.refund(list.getId(), "预售商品不成团退款");
				} catch (BusinessException e) {
					logger.info("执行退款异常",e);
					continue;
				}
			}	
		}
	}

	public void setPresellDeliveryDao(PresellDeliveryDao presellDeliveryDao) {
		this.presellDeliveryDao = presellDeliveryDao;
	}

	@Override
	public List<Trans> findPaidPresellTrans(Integer rackId) {
		List<String> stateList=new ArrayList<String>();
		stateList.add(PayState.PAY_SUCCESS);
		stateList.add(PayState.POINT_PAY_SUCCESS);
		List<Trans> tlist=transDao.getPresellByRackId(rackId.toString());
		List<Trans> list=this.findPaidTrans(tlist, stateList);
		return list;
	}

	@Override
	public void closePresellTransOutofTime() {
		

		logger.info("执行关闭超时预售交易定时任务");
		int n=transDao.closePresellOutOfTimeCash();	
		logger.info("执行关闭超时的纯现金、纯积分支付订单，关闭条数："+n);
		
		List<Trans> list=transDao.getPresellOutOfTimePionts();
		
		for(Trans trans:list){
			List<Pay> payList=trans.getPayList();
			Pay cashPay=null,pointPay=null;
			for(Pay payTemp:payList){
				if(PayCommand.TYPE_CASH.equals(payTemp.getType())){
					cashPay=payTemp;
				}else if(PayCommand.TYPE_POINTS.equals(payTemp.getType())){
					pointPay=payTemp;
				}				
			}
			//积分支付：支付成功     现金支付：等待发送支付请求、请求发送成功、请求发送失败
			if(PayState.POINT_PAY_SUCCESS.equals(pointPay.getState()) && 
					(PayState.PAY_WAIT.equals(cashPay.getState()) || PayState.PAY_REQUEST_FAIL.equals(cashPay.getState()) || PayState.PAY_REQUEST_SUCCESS.equals(cashPay.getState()) )){
				boolean flag;
				try {
					flag = this.refundPoints(trans.getId());
					logger.info("预售交易退还积分:"+flag+"，订单交易ID："+trans.getId());
					if(flag){
						trans.setState(TranCommand.TRAN_CLOSE);		
						transDao.updateById(trans);
					}
				} catch (BusinessException e) {
					e.printStackTrace();
				}
			}
			//积分支付：退分失败    现金支付：等待发送支付请求、请求发送成功、请求发送失败
			if(PayState.POINT_REFUND_FAIL.equals(pointPay.getState()) && 
					(PayState.PAY_WAIT.equals(cashPay.getState()) || PayState.PAY_REQUEST_FAIL.equals(cashPay.getState()) || PayState.PAY_REQUEST_SUCCESS.equals(cashPay.getState()) )){
				boolean flag;
				try {
					flag = this.refundPoints(trans.getId());
					logger.info("预售交易退还积分:"+flag+"，订单交易ID："+trans.getId());
					if(flag){
						trans.setState(TranCommand.TRAN_CLOSE);		
						transDao.updateById(trans);
					}
				} catch (BusinessException e) {
					e.printStackTrace();
				}
			}
			
			
		}
		logger.info("结束关闭超时预售交易定时任务");
	}

	@Override
	public Trans getTransByRefundOrderId(String refundOrderId) {
		if(refundOrderId==null||"".equals(refundOrderId)){
			logger.error("传入的退款订单号为空");
			return null;
		}
		return transDao.getTransByRefundOrderId(refundOrderId);
	}
}
