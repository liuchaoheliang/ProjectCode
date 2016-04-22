package com.froad.fft.support;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.froad.fft.bean.Result;
import com.froad.fft.common.AppException;
import com.froad.fft.persistent.api.FundsChannelMapper;
import com.froad.fft.persistent.api.MerchantAccountMapper;
import com.froad.fft.persistent.api.MerchantMapper;
import com.froad.fft.persistent.common.enums.AccountType;
import com.froad.fft.persistent.common.enums.MerchantType;
import com.froad.fft.persistent.common.enums.PayChannel;
import com.froad.fft.persistent.common.enums.PayRole;
import com.froad.fft.persistent.common.enums.PayState;
import com.froad.fft.persistent.common.enums.PayType;
import com.froad.fft.persistent.common.enums.PayTypeDetails;
import com.froad.fft.persistent.entity.FundsChannel;
import com.froad.fft.persistent.entity.Merchant;
import com.froad.fft.persistent.entity.MerchantAccount;
import com.froad.fft.persistent.entity.Pay;
import com.froad.fft.persistent.entity.SysClient;
import com.froad.fft.persistent.entity.Trans;
import com.froad.fft.persistent.entity.TransDetails;
import com.froad.fft.service.SysClientService;
import com.froad.fft.thirdparty.common.PointsCommand;
import com.froad.fft.thirdparty.common.RespCodeCommand;
import com.froad.fft.thirdparty.dto.request.points.PointsAccount;
import com.froad.fft.thirdparty.dto.request.points.PointsReq;
import com.froad.fft.thirdparty.dto.request.points.PointsRes;
import com.froad.fft.thirdparty.request.points.PointsFunc;
import com.froad.fft.util.SerialNumberUtil;


	/**
	 * 类描述：组装交易的支付明细
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: 2014年3月28日 上午11:29:05 
	 */
@Component
public class PaySupport {
	
	private static final Logger log=Logger.getLogger(PaySupport.class);
	
	private BigDecimal zero=new BigDecimal(0);
	
	@Resource
	private MerchantAccountMapper merchantAccountMapper;
	
	@Resource
	private MerchantMapper merchantMapper;
	
	@Resource
	private FundsChannelMapper fundsChannelMapper;
	
	@Resource
	private SysClientService sysClientService;
	
	@Resource
	private PointsFunc pointsFunc;
	
	public Result makePayList(Trans trans){
		Result result=this.makeProductPayList(trans);
		if(Result.FAIL.equals(result.getCode())){
			return result;
		}
		result=this.makeMerchantPayList(trans);
		if(Result.FAIL.equals(result.getCode())){
			return result;
		}
		List<Pay> list=trans.getPayList();
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setStep(i);
			list.get(i).setSn(SerialNumberUtil.buildPaySn());
		}
		return result;
	}
	
	private Result makeProductPayList(Trans trans){
		if(!TransHelper.isProductTrans(trans.getType())){
			return new Result(Result.SUCCESS,"成功");
		}
		List<Pay> payList=new ArrayList<Pay>();
		try {
			if(!StringUtils.isEmpty(trans.getFftPoints())){
				BigDecimal fftDec=null;
				try {
					fftDec=new BigDecimal(trans.getFftPoints());
				} catch (Exception e) {
					log.error("积分格式错误",e);
					return new Result(Result.FAIL,"积分值只能是整数或小数");
				}
				if(fftDec.compareTo(zero)==1){
					payList.add(this.fftPoints(trans));
				}
			}
			if(!StringUtils.isEmpty(trans.getBankPoints())){
				BigDecimal bankDec=null;
				try {
					bankDec=new BigDecimal(trans.getBankPoints());
				} catch (Exception e) {
					log.error("积分格式错误",e);
					return new Result(Result.FAIL,"积分值只能是整数或小数");
				}
				if(bankDec.compareTo(zero)==1){
					payList.add(this.bankPoints(trans));
				}
			}
			BigDecimal realPrice=new BigDecimal(trans.getRealPrice());
			if(realPrice.compareTo(zero)==1){
				payList.add(this.combinePay(trans));
			}
			//TODO 积分兑换送积分的pay --
			
			trans.setPayList(payList);
			return new Result(Result.SUCCESS,"成功");
		} catch (AppException e) {
			log.error("组装支付参数出现异常",e);
			return new Result(Result.FAIL,e.getErrMsg());
		}
	}
	
	private Result makeMerchantPayList(Trans trans){
		if(!TransHelper.isMerchantTrans(trans.getType())){
			return new Result(Result.SUCCESS,"成功");
		}
		//TODO 组装参数--
		
		return new Result(Result.SUCCESS,"成功");
	}
	
	
	/** 
	 * 用于积分兑换、团购交易、精品预售 
	 * 贴膜卡、支付宝、网银
	 * **/
	public Pay combinePay(Trans trans) throws AppException{
		Pay pay = new Pay();
		pay.setPayState(PayState.pay_wait);
		pay.setPayTypeDetails(PayTypeDetails.PAY_AMOUNT);
		pay.setPayType(PayType.cash);
		pay.setPayValue(trans.getRealPrice());
		if(PayChannel.filmCard.equals(trans.getPayChannel())){
			pay.setFromPhone(trans.getFilmMobile());
		}else{
			pay.setFromPhone(trans.getMember().getMobile());
		}
		SysClient client=sysClientService.findSysClientByNumber(trans.getClientNumber());
		List<FundsChannel> channelList=fundsChannelMapper.selectByClientId(client.getId());
		String payOrg=TransHelper.findPayOrg(channelList, trans.getPayChannel());
		pay.setPayOrg(payOrg);
		TransDetails details=trans.getDetailsList().get(0);
		Long toMerchantId=details.getGatherMerchantId();
		Merchant merchant=merchantMapper.selectMerchantById(toMerchantId);
		if(merchant==null){
			log.error("商户编号为："+toMerchantId+"的商户不存在");
			throw new AppException("商户编号为："+toMerchantId+"的商户不存在");
		}
		MerchantAccount acct=this.findMerchantAccount(toMerchantId,trans.getClientId());
		if(acct==null){
			log.error("收款商户的账户号为空，商户编号："+details.getGatherMerchantId());
			throw new AppException("收款商户的账户号为空，商户编号："+details.getGatherMerchantId());
		}
		pay.setToAccountName(acct.getAcctName());
		pay.setToAccountNo(acct.getAcctNumber());
		pay.setFromRole(PayRole.member);
		if(MerchantType.froad.equals(merchant.getType())){
			pay.setToRole(PayRole.fft);
		}else{
			pay.setToRole(PayRole.merchant);
		}
		pay.setMerchantId(toMerchantId);//只做记录
		pay.setRemark("资金支付记录");
		return pay;
	}
	

	/**
	 * 分分通积分支付
	 */
	public Pay fftPoints(Trans trans) throws AppException{
		Pay pay = new Pay();
		pay.setPayState(PayState.pay_wait);
		pay.setPayType(PayType.points);
		pay.setPayTypeDetails(PayTypeDetails.PAY_FFT_POINTS);
		String username=trans.getMember().getUsername();
		SysClient client=sysClientService.findSysClientById(trans.getClientId());
		PointsAccount acct=this.findPointsAccount(PointsCommand.FROAD_ORG_NO, username,client.getPartnerId());
		if(acct==null){
			throw new AppException("没有分分通积分账户");
		}
		if(!this.validatePoints(trans.getFftPoints(), acct.getPoints())){
			throw new AppException("分分通积分不够");
		}
		pay.setFromAccountNo(acct.getAccountId());
		pay.setPayValue(trans.getFftPoints());
		pay.setFromUserName(username);
		pay.setRemark("分分通积分支付记录");
		return pay;
	}

	/**
	 * 银行积分支付
	 */
	public Pay bankPoints(Trans trans)throws AppException{
		Pay pay = new Pay();
		pay.setPayState(PayState.pay_wait);
		pay.setPayType(PayType.points);
		pay.setPayTypeDetails(PayTypeDetails.PAY_BANK_POINTS);
		
		String username=trans.getMember().getUsername();
		SysClient client=sysClientService.findSysClientById(trans.getClientId());
		if(StringUtils.isEmpty(client.getOrgNo())){
			throw new AppException("银行积分机构号为空");
		}
		PointsAccount acct=this.findPointsAccount(client.getOrgNo(), username,client.getPartnerId());
		if(acct==null){
			throw new AppException("没有银行积分账户");
		}
		if(!this.validatePoints(trans.getBankPoints(), acct.getPoints())){
			throw new AppException("银行积分不够");
		}
		pay.setFromAccountNo(acct.getAccountId());
		pay.setPayValue(trans.getBankPoints());
		pay.setFromUserName(username);
		pay.setRemark("银行积分支付");
		return pay;
	}

	/** 积分返利:纯返积分* */
	/**
	 * 代扣购买积分的金额 *
	 */
//	public Pay buyPointsCash(Trans trans) {
//		Pay pay = new Pay();
//		pay.setPayState(PayState.PAY_WAIT);
//		pay.setPayTypeDetails(TranCommand.BUY_POINTS_CASH);
//		pay.setPayType(TranCommand.TYPE_CASH);
//
//		pay.setFromRole(PayCommand.TRADER_TYPE_MERCHANT);
//		pay.setToRole(PayCommand.TRADER_TYPE_FROAD);
//		pay.setFromAccountName(sellerChannel.getAccountName());
//		pay.setFromAccountNo(sellerChannel.getAccountNumber());
//		pay.setChannelId(fundsChannel.getId() + "");
//		pay.setPayValue(trans.getPointsAmountRealValue());
//		pay.setToAccountName(fundsChannel.getFftPointsAccountName());
//		pay.setToAccountNo(fundsChannel.getFftPointsAccountNumber());
//		pay.setRemark("代扣购买积分的金额");
//		return pay;
//	}

	/**
	 * 付购买积分的手续费 *
	 */
//	public Pay buyPointsFee(Trans trans) {
//		Pay pay = new Pay();
//		pay.setPayState(PayState.PAY_WAIT);
//		pay.setPayTypeDetails(TranCommand.BUY_POINTS_FEE);
//		pay.setPayType(TranCommand.TYPE_CASH);
//		pay.setFromRole(PayCommand.TRADER_TYPE_MERCHANT);
//		pay.setToRole(PayCommand.TRADER_TYPE_FROAD);
//
//		pay.setFromAccountName(sellerChannel.getAccountName());
//		pay.setFromAccountNo(sellerChannel.getAccountNumber());
//		pay.setChannelId(fundsChannel.getId() + "");
//		pay.setPayValue(trans.getFftFactorage());
//		pay.setToAccountName(fundsChannel.getFftPointsAccountName());
//		pay.setToAccountNo(fundsChannel.getFftPointsAccountNumber());
//		pay.setRemark("代扣购买积分的手续费");
//		return pay;
//	}

	/**
	 * 收银台收款或返积分 给用户返分分通积分 *
	 */
//	public Pay rebatePoints(Trans trans) {
//		Pay pay = new Pay();
//		pay.setPayState(PayState.PAY_WAIT);
//		pay.setPayTypeDetails(TranCommand.SEND_POINTS);
//		pay.setPayType(TranCommand.TYPE_POINTS);
//		pay.setPayValue(trans.getFftPointsValueRealAll());
//		pay.setFromPhone(userOfSeller.getMobilephone());
//		pay.setFromUsername(userOfSeller.getUsername());
//		pay.setToPhone(user.getMobilephone());
//		pay.setToUsername(user.getUsername());
//		pay.setRemark("给用户返利分分通积分(用户购买商品时，商户返积分)");
//		return pay;
//	}

	/**
	 * 积分兑换商品送积分*
	 */
//	public Pay presentPoints(Trans trans) {
//		Pay pay = new Pay();
//		pay.setPayState(PayState.PAY_WAIT);
//		pay.setPayTypeDetails(TranCommand.SEND_POINTS);
//		pay.setPayType(TranCommand.TYPE_POINTS);
//		pay.setPayValue(trans.getPresentPointsValue());
//		pay.setFromPhone(userOfSeller.getMobilephone());
//		pay.setFromUsername(userOfSeller.getUsername());
//		pay.setToPhone(user.getMobilephone());
//		pay.setToUsername(user.getUsername());
//		pay.setRemark("给用户返利分分通积分(以购买积分兑换商品的形式返分)");
//		return pay;
//	}

	
	
	private MerchantAccount findMerchantAccount(Long merchantId,Long clientId){
		List<MerchantAccount> acctList=merchantAccountMapper.selectByMerchantId(merchantId);
		MerchantAccount acct=null;
		for (int i = 0; i < acctList.size(); i++) {
			acct=acctList.get(i);
			if(acct.getIsEnabled()&&AccountType.collect.equals(acct.getAcctType())){
				return acct;
			}
		}
		return null;
	}
	
	private PointsAccount findPointsAccount(String orgNo,String username,String partnerId)throws AppException{
		PointsReq pointsReq = new PointsReq(orgNo,username,PointsCommand.ACCOUNT_MARKED_TYPE_USERNAME,partnerId);
		PointsRes pointsRes = pointsFunc.queryParAccountPoints(pointsReq);
		if(RespCodeCommand.SUCCESS.equals(pointsRes.getResultCode())){
			List<PointsAccount> acctList=pointsRes.getAccountPointsInfoList();
			if(acctList==null||acctList.size()==0){
				log.warn("用户："+username+"没有积分账户");
				return null;
			}
			return acctList.get(0);
		}
		throw new AppException("积分账户信息查询异常，原因："+pointsRes.getResultDesc());
	}

	private boolean validatePoints(String points,String acctPoints){
		BigDecimal pointsDec=new BigDecimal(points);
		BigDecimal acctPointsDec=new BigDecimal(acctPoints);
		int result=acctPointsDec.compareTo(pointsDec);
		return result==1||result==0;
	}
}
