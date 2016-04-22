package com.froad.action.support.trans;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.froad.action.trans.TransHelper;
import com.froad.client.buyerChannel.BuyerChannel;
import com.froad.client.buyerChannel.BuyerChannelService;
import com.froad.client.trans.Pay;
import com.froad.client.trans.TransDetails;
import com.froad.client.point.PointService;
import com.froad.client.point.Points;
import com.froad.client.point.PointsAccount;
import com.froad.client.sellerChannel.FundsChannel;
import com.froad.client.sellerChannel.SellerChannel;
import com.froad.client.sellerChannel.SellerChannelService;
import com.froad.client.sellers.Seller;
import com.froad.client.sellers.SellersService;
import com.froad.client.trans.Trans;
import com.froad.client.user.AppException_Exception;
import com.froad.client.user.MUserService;
import com.froad.client.user.User;
import com.froad.client.userCertification.UserCertification;
import com.froad.client.userCertification.UserCertificationService;
import com.froad.common.PayState;
import com.froad.common.TranCommand;
import com.froad.util.command.PayCommand;

/**
 * 类描述：用于生成各种交易pay记录的工具类
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2013
 * @author: 李金魁 lijinkui@f-road.com.cn
 * @time: Apr 3, 2013 12:58:01 AM
 */
public class PayActionSupport {

	private static final Logger logger = Logger
			.getLogger(PayActionSupport.class);

	private BuyerChannelService buyerChannelService;

	private SellerChannelService sellerChannelService;

	private SellersService sellersService;

	private PointService pointService;

	private MUserService userService;

	private UserCertificationService userCertificationService;

	/**
	 * 方法描述：校验必选的交易参数
	 * 
	 * @param: Trans
	 * @return: boolean
	 * @version: 1.0
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Apr 3, 2013 7:27:30 PM
	 */
	private boolean checkParam(Trans trans) {
		if (trans == null) {
			logger.error("交易对象为空");
			return false;
		}
		if (!TranCommand.TRAN_PROCESSING.equals(trans.getState())) {
			logger.error("未知的交易状态：" + trans.getState());
			return false;
		}
		if (!PayCommand.CLIENT_TYPES.contains(trans.getClientType())) {
			logger.error("未知的客户端类型：" + trans.getClientType());
			return false;
		}
		if (!TranCommand.TRANS_TYPES.contains(trans.getTransType())) {
			logger.error("未知交易类型：" + trans.getTransType());
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
		if (trans.getSellerId() == null || "".equals(trans.getSellerId())) {
			logger.error("卖家编号为空");
			return false;
		}
		if (trans.getSellerChannelId() == null
				|| "".equals(trans.getSellerChannelId())) {
			logger.error("卖家渠道编号为空");
			return false;
		}
		if (trans.getUserId() == null || "".equals(trans.getUserId())) {
			logger.error("用户编号为空");
			return false;
		}
		logger.info("========交易参数校验通过========");
		return true;
	}

	/**
	 * 方法描述：将生成的单一pay记录添加到list里
	 * 
	 * @param: 所有的pay
	 * @return: List<Pay> 入参的pay为null时不加入list,list的元素个数大于0时返回list,否则返回null
	 * @version: 1.0
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Apr 4, 2013 12:07:52 PM
	 */
	private List<Pay> addPayToList(Pay... pay) {
		List<Pay> payList = new ArrayList<Pay>();
		for (int i = 0; i < pay.length; i++) {
			if (pay[i] != null) {
				pay[i].setStep(String.valueOf(i));
				payList.add(pay[i]);
			}
		}
		if (payList.size() > 0) {
			return payList;
		}
		return null;
	}

	/**
	 * 方法描述：生成交易所需的pay记录
	 * 
	 * @param: Trans
	 * @return: List<Pay>
	 * @version: 1.0
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Apr 3, 2013 3:18:48 AM
	 */
	public List<Pay> makePay(Trans trans) {
		boolean isValid = this.checkParam(trans);
		if (!isValid) {
			return null;
		}
		String transType = trans.getTransType();
		String payMethod = trans.getPayMethod();
		String payChannel=TransHelper.makePayChannel(payMethod);
		trans.setPayChannel(payChannel);
		Pay pointsPay = null, collectPay = null, deductPay = null, feePay = null, lotteryPay = null, hfczPay = null;
		if (TranCommand.POINTS_EXCH_PRODUCT.equals(transType)
				|| TranCommand.GROUP.equals(transType)
				|| TranCommand.PRE_SELL.equals(transType)) {
			/** 积分兑换和团购(消费积分+代收)* */
			lotteryPay = lottery(trans);
			hfczPay = hfcz(trans);
			Pay presentPay = null;
			if (trans.getPresentPointsValue() != null) {
				presentPay = presentPoints(trans);
			}
			List<Pay> payList = null;
			if (TranCommand.POINTS_FFT.equals(payMethod)) {// 方付通积分支付
				pointsPay = payFftPoints(trans);
				payList = addPayToList(pointsPay, lotteryPay, hfczPay);
			} else if (TranCommand.POINTS_BANK.equals(payMethod)) {// 银行积分支付
				pointsPay = payBankPoints(trans);
				payList = addPayToList(pointsPay, lotteryPay, hfczPay);
			} else if (TranCommand.CASH.equals(payMethod)) {// 现金支付
				collectPay = collectCash(trans);
				payList = addPayToList(collectPay, lotteryPay, hfczPay);
			} else if (TranCommand.POINTS_FFT_CASH.equals(payMethod)
					|| TranCommand.POINTS_FFT_CASH_SCOPE.equals(payMethod)) {// 方付通积分+现金(范围)
				pointsPay = payFftPoints(trans);
				collectPay = collectCash(trans);
				payList = addPayToList(pointsPay, collectPay, lotteryPay,
						hfczPay);
			} else if (TranCommand.POINTS_BANK_CASH.equals(payMethod)
					|| TranCommand.POINTS_BANK_CASH_SCOPE.equals(payMethod)) {// 银行积分+现金(范围)
				pointsPay = payBankPoints(trans);
				collectPay = collectCash(trans);
				payList = addPayToList(pointsPay, collectPay, lotteryPay,
						hfczPay);
			} else if (TranCommand.ALPAY.equals(payMethod)) {
				collectPay = alipay(trans);
				payList = addPayToList(collectPay, lotteryPay, hfczPay);
			} else if (TranCommand.POINTS_FFT_ALPAY_SCOPE.equals(payMethod)) {
				pointsPay = payFftPoints(trans);
				collectPay = alipay(trans);
				payList = addPayToList(pointsPay, collectPay, lotteryPay,
						hfczPay);
			} else if (TranCommand.POINTS_BANK_ALPAY_SCOPE.equals(payMethod)) {
				pointsPay = payBankPoints(trans);
				collectPay = alipay(trans);
				payList = addPayToList(pointsPay, collectPay, lotteryPay,
						hfczPay);
			} else {
				logger.error("未知的支付方式：" + payMethod);
				return null;
			}
			if (presentPay != null && payList != null) {
				String step = payList.get(payList.size() - 1).getStep();
				presentPay.setStep((Integer.parseInt(step) + 1) + "");
				payList.add(presentPay);
			}
			return payList;
		} else if (TranCommand.POINTS_REBATE.equals(transType)) {
			/** 积分返利即收款和送积分(代收+代扣买积分的钱+代扣买积分的手续费+送积分)* */
			collectPay = collectCash(trans);
			deductPay = buyPointsCash(trans);
			feePay = buyPointsFee(trans);
			pointsPay = sendPoints(trans);
			return addPayToList(collectPay, deductPay, feePay, pointsPay);
		}

		// else if(TranCommand.POINTS_EXCH_CASH.equals(transType)){
		// /**积分提现(消费积分+转账+积分手续费)**/
		// pointsPay=deductPoints(trans);
		// transferPay=transferForBuyer(trans);
		// feePay=deductFee(trans);
		// return addPayToList(pointsPay,transferPay,feePay);
		// }

		else if (TranCommand.COLLECT.equals(transType)) {
			/** 纯收款(代收)* */
			collectPay = collectCash(trans);
			return addPayToList(collectPay);
		} else if (TranCommand.PRESENT_POINTS.equals(transType)) {
			/** 纯送积分(代扣买积分的钱+代扣买积分的手续费+送积分)* */
			deductPay = buyPointsCash(trans);
			feePay = buyPointsFee(trans);
			pointsPay = sendPoints(trans);
			return addPayToList(deductPay, feePay, pointsPay);

		} else {
			logger.error("未知的交易类型：" + transType);
			return null;
		}
	}

	/***************************************************************************
	 * 用于积分兑换、团购交易、预售交易、纯收款 资金支付
	 **************************************************************************/
	public Pay collectCash(Trans trans) {
		Pay pay = new Pay();
		pay.setState(PayState.PAY_WAIT);
		pay.setTypeDetail(TranCommand.COLLECT_CASH);
		pay.setType(TranCommand.TYPE_CASH);
		String buyerChannelId = trans.getBuyerChannelId();
		if (buyerChannelId == null || "".equals(buyerChannelId)) {
			logger.error("该笔交易的买家资金渠道编号为空，该买家的用户编号为： " + trans.getUserId());
			return null;
		}
		BuyerChannel buyerChannel = buyerChannelService.selectById(Integer
				.parseInt(buyerChannelId));
		if (buyerChannel == null) {
			logger.error("买家资金渠道不存在，该买家的用户编号为： " + trans.getUserId());
			return null;
		}
		Integer sellerChannelId = Integer.parseInt(trans.getSellerChannelId());
		SellerChannel sellerChannel = sellerChannelService
				.selectById(sellerChannelId);
		if (sellerChannel == null) {
			logger.error("该笔交易的卖家资金渠道不存在，资金渠道编号：" + sellerChannelId);
			return null;
		}
		pay.setFromAccountName(buyerChannel.getAccountName());
		pay.setFromAccountNo(buyerChannel.getAccountNumber());
		pay.setChannelId(buyerChannel.getFundsChannel().getId() + "");
		pay.setFromPhone(buyerChannel.getPhone());
		pay.setPayValue(trans.getCurrencyValueRealAll());
		pay.setToAccountName(sellerChannel.getAccountName());
		pay.setToAccountNo(sellerChannel.getAccountNumber());
		pay.setFromRole(PayCommand.TRADER_TYPE_BUYER);
		Seller seller = sellersService.selectById(Integer.parseInt(trans
				.getSellerId()));
		if (seller == null) {
			logger.error("该笔交易的卖家不存在，代收的pay记录生成失败，传入的卖家编号："
					+ trans.getSellerId());
			return null;
		}
		if (PayCommand.IS_INTERNAL_NO.equals(seller.getIsInternal())) {// 如果不是内部卖家
			pay.setToRole(PayCommand.TRADER_TYPE_MERCHANT);
		} else {
			pay.setToRole(PayCommand.TRADER_TYPE_FROAD);
		}
		pay.setRemark("贴膜卡支付记录");
		return pay;
	}


	/***************************************************************************
	 * 用于积分兑换、团购交易、预售交易 支付宝支付
	 **************************************************************************/
	public Pay alipay(Trans trans) {
		Pay pay = new Pay();
		pay.setState(PayState.PAY_WAIT);
		pay.setTypeDetail(TranCommand.COLLECT_CASH);
		pay.setType(TranCommand.TYPE_CASH);

		Integer sellerChannelId = Integer.parseInt(trans.getSellerChannelId());
		SellerChannel sellerChannel = sellerChannelService
				.selectById(sellerChannelId);
		if (sellerChannel == null) {
			logger.error("该笔交易的卖家资金渠道不存在，资金渠道编号：" + sellerChannelId);
			return null;
		}
		User user = null;
		try {
			user = userService.queryUserAllByUserID(trans.getUserId());
		} catch (AppException_Exception e1) {
			logger.error("查询用户信息时出现异常，用户编号：" + trans.getUserId(), e1);
			return null;
		}
		pay.setFromPhone(user.getMobilephone());
		pay.setPayValue(trans.getCurrencyValueRealAll());
		pay.setToAccountName(sellerChannel.getAccountName());
		pay.setToAccountNo(sellerChannel.getAccountNumber());
		pay.setFromRole(PayCommand.TRADER_TYPE_BUYER);
		pay.setRemark("支付宝支付记录");
		Seller seller = sellersService.selectById(Integer.parseInt(trans
				.getSellerId()));
		if (seller == null) {
			logger.error("该笔交易的卖家不存在，合并支付的pay记录生成失败，传入的卖家编号："
					+ trans.getSellerId());
			return null;
		}
		if (PayCommand.IS_INTERNAL_NO.equals(seller.getIsInternal())) {// 如果不是内部卖家
			pay.setToRole(PayCommand.TRADER_TYPE_MERCHANT);
		} else {
			pay.setToRole(PayCommand.TRADER_TYPE_FROAD);
		}
		return pay;
	}

	/**
	 * 收分分通积分-用分分通积分付款 *
	 */
	public Pay payFftPoints(Trans trans) {
		Pay pay = new Pay();
		pay.setState(PayState.PAY_WAIT);
		pay.setTypeDetail(TranCommand.PAY_FFT_POINTS);
		pay.setType(TranCommand.TYPE_POINTS);
		User user = null;
		try {
			user = userService.queryUserAllByUserID(trans.getUserId());
		} catch (AppException_Exception e1) {
			logger.error("查询用户信息时出现异常", e1);
			return null;
		}
		if (user == null || user.getUserID() == null) {
			logger.error("用户不存在，传入的用户编号为： " + trans.getUserId());
			return null;
		}
		Points points = new Points();
		points.setOrgNo(TranCommand.FFT_ORG_NO);
		points.setAccountMarked(user.getUsername());
		Points pointsRes = null;
		try {
			pointsRes = pointService.queryPoints(points);
		} catch (Exception e) {
			logger.error("积分账户查询出现异常，用户名为：" + user.getUsername(), e);
			return null;
		}
		if (pointsRes == null
				|| !PayCommand.RESPCODE_SUCCESS.equals(pointsRes
						.getResultCode())) {
			logger.error("积分账户查询失败，用户名为：" + user.getUsername());
			return null;
		}
		PointsAccount account = this.findPointsAccount(pointsRes
				.getPointsAccountList(), TranCommand.FFT_ORG_NO);
		if (account == null) {
			logger.error("该用户没有分分通积分账户号，用户名：" + user.getUsername());
			return null;
		}
		pay.setFromAccountNo(account.getAccountId());
		pay.setPayValue(trans.getFftPointsValueRealAll());
		pay.setFromUsername(user.getUsername());
		pay.setRemark("分分通积分支付记录");
		return pay;
	}

	/**
	 * 收银行积分-用银行积分付款 *
	 */
	public Pay payBankPoints(Trans trans) {
		Pay pay = new Pay();
		pay.setState(PayState.PAY_WAIT);
		pay.setTypeDetail(TranCommand.PAY_BANK_POINTS);
		pay.setType(TranCommand.TYPE_POINTS);
		User user = null;
		try {
			user = userService.queryUserAllByUserID(trans.getUserId());
		} catch (AppException_Exception e1) {
			logger.error("查询用户信息时出现异常", e1);
			return null;
		}
		if (user == null || user.getUserID() == null) {
			logger.error("用户不存在，传入的用户编号为： " + trans.getUserId());
			return null;
		}
		Points points = new Points();
		points.setOrgNo(TranCommand.BANK_ORG_NO);
		points.setAccountMarked(user.getUsername());
		Points pointsRes = null;
		try {
			pointsRes = pointService.queryPoints(points);
		} catch (Exception e) {
			logger.error("积分账户查询出现异常，用户名为：" + user.getUsername(), e);
			return null;
		}
		if (pointsRes == null
				|| !PayCommand.RESPCODE_SUCCESS.equals(pointsRes
						.getResultCode())) {
			logger.error("积分账户查询失败，用户名为：" + user.getUsername());
			return null;
		}
		PointsAccount account = this.findPointsAccount(pointsRes
				.getPointsAccountList(), TranCommand.BANK_ORG_NO);
		if (account == null) {
			logger.error("该用户没有银行积分账户号，用户名：" + user.getUsername());
			return null;
		}
		pay.setFromAccountNo(account.getAccountId());
		pay.setPayValue(trans.getBankPointsValueRealAll());
		pay.setFromUsername(user.getUsername());
		pay.setRemark("银行积分支付");
		return pay;
	}

	/** 积分返利:纯返积分* */
	/**
	 * 代扣购买积分的金额 *
	 */
	public Pay buyPointsCash(Trans trans) {
		Pay pay = new Pay();
		pay.setState(PayState.PAY_WAIT);
		pay.setTypeDetail(TranCommand.BUY_POINTS_CASH);
		pay.setType(TranCommand.TYPE_CASH);

		pay.setFromRole(PayCommand.TRADER_TYPE_MERCHANT);
		pay.setToRole(PayCommand.TRADER_TYPE_FROAD);
		Integer sellerChannelId = Integer.parseInt(trans.getSellerChannelId());
		SellerChannel sellerChannel = sellerChannelService
				.selectById(sellerChannelId);
		if (sellerChannel == null) {
			logger.error("该笔交易的卖家资金渠道不存在，卖家资金渠道编号：" + sellerChannelId);
			return null;
		}
		FundsChannel fundsChannel = sellerChannel.getFundsChannel();
		if (fundsChannel == null) {
			logger.error("该笔交易的卖家渠道对应的FundsChannel不存在，卖家资金渠道编号："
					+ sellerChannelId);
			return null;
		}
		pay.setFromAccountName(sellerChannel.getAccountName());
		pay.setFromAccountNo(sellerChannel.getAccountNumber());
		pay.setChannelId(fundsChannel.getId() + "");
		pay.setPayValue(trans.getPointsAmountRealValue());
		pay.setToAccountName(fundsChannel.getFftPointsAccountName());
		pay.setToAccountNo(fundsChannel.getFftPointsAccountNumber());
		pay.setRemark("代扣购买积分的金额");
		return pay;
	}

	/**
	 * 付购买积分的手续费 *
	 */
	public Pay buyPointsFee(Trans trans) {
		Pay pay = new Pay();
		pay.setState(PayState.PAY_WAIT);
		pay.setTypeDetail(TranCommand.BUY_POINTS_FEE);
		pay.setType(TranCommand.TYPE_CASH);
		pay.setFromRole(PayCommand.TRADER_TYPE_MERCHANT);
		pay.setToRole(PayCommand.TRADER_TYPE_FROAD);

		Integer sellerChannelId = Integer.parseInt(trans.getSellerChannelId());
		SellerChannel sellerChannel = sellerChannelService
				.selectById(sellerChannelId);
		if (sellerChannel == null) {
			logger.error("该笔交易的卖家资金渠道不存在，卖家资金渠道编号：" + sellerChannelId);
			return null;
		}
		FundsChannel fundsChannel = sellerChannel.getFundsChannel();
		if (fundsChannel == null) {
			logger.error("该笔交易的卖家渠道对应的FundsChannel不存在，卖家资金渠道编号："
					+ sellerChannelId);
			return null;
		}
		pay.setFromAccountName(sellerChannel.getAccountName());
		pay.setFromAccountNo(sellerChannel.getAccountNumber());
		pay.setChannelId(fundsChannel.getId() + "");
		pay.setPayValue(trans.getFftFactorage());
		pay.setToAccountName(fundsChannel.getFftPointsAccountName());
		pay.setToAccountNo(fundsChannel.getFftPointsAccountNumber());
		pay.setRemark("代扣购买积分的手续费");
		return pay;
	}

	/**
	 * 收银台收款或返积分 给买家返分分通积分 *
	 */
	public Pay sendPoints(Trans trans) {
		Pay pay = new Pay();
		pay.setState(PayState.PAY_WAIT);
		pay.setTypeDetail(TranCommand.SEND_POINTS);
		pay.setType(TranCommand.TYPE_POINTS);
		pay.setPayValue(trans.getFftPointsValueRealAll());
		Seller seller = sellersService.selectById(Integer.parseInt(trans
				.getSellerId()));
		if (seller == null) {
			logger.error("该笔交易的卖家不存在，卖家编号：" + trans.getSellerId());
			return null;
		}
		com.froad.client.sellers.User userOfSeller = seller.getUser();
		if (userOfSeller == null) {
			logger.error("该笔交易的卖家对应的用户不存在，卖家编号：" + trans.getSellerId());
			return null;
		}
		pay.setFromPhone(userOfSeller.getMobilephone());
		pay.setFromUsername(userOfSeller.getUsername());
		String userId = trans.getUserId();
		if (userId == null || "".equals(userId)) {
			logger.error("返积分的用户编号不存在，未指定积分送给哪个用户。");
			return null;
		}
		User user = null;
		try {
			user = userService.queryUserAllByUserID(userId);
		} catch (AppException_Exception e) {
			logger.error("查询用户出现异常", e);
		}
		if (user == null || user.getUserID() == null) {
			logger.error("返积分所指定的用户不存在，用户编号：" + userId);
			return null;
		}
		pay.setToPhone(user.getMobilephone());
		pay.setToUsername(user.getUsername());
		pay.setRemark("给用户返利分分通积分(用户购买商品时，商户返积分)");
		return pay;
	}

	/**
	 * 积分兑换商品送积分*
	 */
	public Pay presentPoints(Trans trans) {
		Pay pay = new Pay();
		pay.setState(PayState.PAY_WAIT);
		pay.setTypeDetail(TranCommand.SEND_POINTS);
		pay.setType(TranCommand.TYPE_POINTS);
		pay.setPayValue(trans.getPresentPointsValue());
		Seller seller = sellersService.selectById(Integer.parseInt(trans
				.getSellerId()));
		if (seller == null) {
			logger.error("该笔交易的卖家不存在，卖家编号：" + trans.getSellerId());
			return null;
		}
		com.froad.client.sellers.User userOfSeller = seller.getUser();
		if (userOfSeller == null) {
			logger.error("该笔交易的卖家对应的用户不存在，卖家编号：" + trans.getSellerId());
			return null;
		}
		pay.setFromPhone(userOfSeller.getMobilephone());
		pay.setFromUsername(userOfSeller.getUsername());
		String userId = trans.getUserId();
		if (userId == null || "".equals(userId)) {
			logger.error("返积分的用户编号不存在，未指定积分送给哪个用户。");
			return null;
		}
		User user = null;
		try {
			user = userService.queryUserAllByUserID(userId);
		} catch (AppException_Exception e) {
			logger.error("查询用户出现异常", e);
		}
		if (user == null || user.getUserID() == null) {
			logger.error("返积分所指定的用户不存在，用户编号：" + userId);
			return null;
		}
		pay.setToPhone(user.getMobilephone());
		pay.setToUsername(user.getUsername());
		pay.setRemark("给用户返利分分通积分(以购买积分兑换商品的形式返分)");
		return pay;
	}

	/** 用于积分提现* */
	/**
	 * 积分提现-扣分分通积分 同payFftPoints方法 部分值不一样 *
	 */
	public Pay deductPoints(Trans trans) {
		Pay pay = new Pay();
		pay.setState(PayState.PAY_WAIT);
		pay.setTypeDetail(TranCommand.DEDUCT_POINTS);
		pay.setType(TranCommand.TYPE_POINTS);
		User user = null;
		try {
			user = userService.queryUserAllByUserID(trans.getUserId());
		} catch (AppException_Exception e1) {
			logger.error("查询用户出现异常", e1);
		}
		if (user == null || user.getUserID() == null) {
			logger.error("该笔交易中买家对应的用户不存在，用户编号为：" + trans.getUserId());
			return null;
		}
		Points points = new Points();
		points.setOrgNo(TranCommand.FFT_ORG_NO);
		points.setAccountMarked(user.getUsername());
		Points pointsRes = null;
		try {
			pointsRes = pointService.queryPoints(points);
		} catch (Exception e) {
			logger.error("积分账户查询出现异常，用户名为：" + user.getUsername(), e);
			return null;
		}
		if (!PayCommand.RESPCODE_SUCCESS.equals(pointsRes.getResultCode())) {
			logger.error("积分账户查询失败,失败原因： " + pointsRes.getRespMsg());
			return null;
		}
		PointsAccount account = this.findPointsAccount(pointsRes
				.getPointsAccountList(), TranCommand.FFT_ORG_NO);
		if (account == null) {
			logger.error("该用户没有分分通积分账户号，用户名：" + user.getUsername());
			return null;
		}
		pay.setFromAccountNo(account.getAccountId());
		pay.setPayValue(trans.getFftPointsValueRealAll());
		pay.setFromUsername(user.getUsername());
		pay.setRemark("积分提现时，扣分分通积分");
		return pay;
	}

	/**
	 * 积分提现-给用户转账 *
	 */
	public Pay transferForBuyer(Trans trans) {
		Pay pay = new Pay();
		pay.setState(PayState.PAY_WAIT);
		pay.setTypeDetail(TranCommand.TRANSFER_FOR_BUYER);
		pay.setType(TranCommand.TYPE_CASH);
		String userId = trans.getUserId();
		UserCertification userCert = userCertificationService
				.getUserCertByUserId(userId);
		if (userCert == null) {
			logger.error("UserCertification记录不存在，没有进行提现认证，用户编号：" + userId);
			return null;
		}
		com.froad.client.userCertification.FundsChannel fc = userCert
				.getFundsChannel();
		if (fc == null) {
			logger.error("提现认证指定的资金渠道(FundsChannel)不存在");
			return null;
		}
		pay.setFromAccountName(fc.getPointsWithdrawAccountName());
		pay.setFromAccountNo(fc.getPointsWithdrawAccountNumber());
		pay.setPayValue(trans.getCurrencyValueAll());
		pay.setToAccountName(userCert.getAccountName());
		pay.setToAccountNo(userCert.getAccountNo());
		pay.setRemark("积分提现时，给用户转账的记录");
		return pay;
	}

	/**
	 * 积分提现-提现手续费 *
	 */
	public Pay deductFee(Trans trans) {
		Pay pay = new Pay();
		pay.setState(PayState.PAY_WAIT);
		pay.setTypeDetail(TranCommand.DEDUCT_FEE);
		pay.setType(TranCommand.TYPE_CASH);
		pay.setFromRole(PayCommand.TRADER_TYPE_BUYER);
		pay.setToRole(PayCommand.TRADER_TYPE_FROAD);

		String userId = trans.getUserId();
		UserCertification userCert = userCertificationService
				.getUserCertByUserId(userId);
		if (userCert == null) {
			logger.error("UserCertification记录不存在，没有进行提现认证，用户编号：" + userId);
			return null;
		}
		com.froad.client.userCertification.FundsChannel fc = userCert
				.getFundsChannel();
		if (fc == null) {
			logger.error("提现认证指定的资金渠道(FundsChannel)不存在");
			return null;
		}
		pay.setFromAccountName(userCert.getAccountName());
		pay.setFromAccountNo(userCert.getAccountNo());
		pay.setToAccountName(fc.getFftPointsAccountName());
		pay.setToAccountNo(fc.getFftPointsAccountNumber());

		pay.setPayValue(trans.getFftFactorage());
		pay.setRemark("积分提现手续费");
		return pay;
	}

	/**
	 * 方法描述：彩票的pay记录
	 * 
	 * @param: Trans(transDetails.goodsRackId)
	 * @return: Pay
	 * @version: 1.0
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Apr 8, 2013 11:11:10 PM
	 */
	public Pay lottery(Trans trans) {
		if (!TranCommand.POINTS_EXCH_PRODUCT.equals(trans.getTransType())) {
			return null;
		}
		List<TransDetails> detailsList = trans.getTransDetailsList();
		if (detailsList == null || detailsList.size() == 0) {
			return null;
		}
		boolean hasLottery = false;
		for (int i = 0; i < detailsList.size(); i++) {
			if (TranCommand.CATEGORY_LOTTORY.equals(detailsList.get(i)
					.getGoodsCategoryId())) {
				hasLottery = true;
				break;
			}
		}
		if (!hasLottery) {
			return null;
		}
		Pay pay = new Pay();
		pay.setState(PayState.PAY_WAIT);
		pay.setTypeDetail(TranCommand.CATEGORY_LOTTORY);
		pay.setType(TranCommand.TYPE_LOTTERY);
		pay.setPayValue(trans.getCurrencyValueAll());
		pay.setToPhone(trans.getPhone());
		String userId = trans.getUserId();
		User user = null;
		try {
			user = userService.queryUserAllByUserID(userId);
		} catch (AppException_Exception e) {
			logger.error("查询会员出现异常", e);
			return null;
		}
		if (user == null || user.getUserID() == null) {
			logger.error("彩票的pay记录组装失败，会员不存在");
			return null;
		}
		pay.setToUsername(user.getUsername());
		pay.setRemark("彩票");
		return pay;
	}

	public Pay hfcz(Trans trans) {
		if (!TranCommand.POINTS_EXCH_PRODUCT.equals(trans.getTransType())) {
			return null;
		}
		List<TransDetails> detailsList = trans.getTransDetailsList();
		if (detailsList == null || detailsList.size() == 0) {
			return null;
		}
		boolean hasHfcz = false;
		for (int i = 0; i < detailsList.size(); i++) {
			if (TranCommand.CATEGORY_HFCZ.equals(detailsList.get(i)
					.getGoodsCategoryId())) {
				hasHfcz = true;
				break;
			}
		}
		if (!hasHfcz) {
			return null;
		}
		Pay pay = new Pay();
		pay.setState(PayState.PAY_WAIT);
		pay.setTypeDetail(TranCommand.CATEGORY_HFCZ);
		pay.setType(TranCommand.TYPE_HFCZ);
		pay.setPayValue(trans.getCurrencyValueAll());
		pay.setToPhone(trans.getPhone());
		String userId = trans.getUserId();
		User user = null;
		try {
			user = userService.queryUserAllByUserID(userId);
		} catch (AppException_Exception e) {
			logger.error("查询会员出现异常", e);
			return null;
		}
		if (user == null || user.getUserID() == null) {
			logger.error("话费充值的pay记录组装失败，会员不存在");
			return null;
		}
		pay.setToUsername(user.getUsername());
		pay.setRemark("话费充值");
		return pay;
	}

	/**
	 * 方法描述：按积分机构号查找积分账户
	 * 
	 * @param: List<PointsAccount> 目标list
	 * @return: orgNo 积分机构号
	 * @version: 1.0
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Apr 8, 2013 6:47:04 PM
	 */
	private PointsAccount findPointsAccount(List<PointsAccount> acctList,
			String orgNo) {
		for (int i = 0; i < acctList.size(); i++) {
			if (orgNo.equals(acctList.get(i).getOrgNo())) {
				return acctList.get(i);
			}
		}
		return null;
	}

	public void setBuyerChannelService(BuyerChannelService buyerChannelService) {
		this.buyerChannelService = buyerChannelService;
	}

	public void setSellerChannelService(
			SellerChannelService sellerChannelService) {
		this.sellerChannelService = sellerChannelService;
	}

	public void setSellersService(SellersService sellersService) {
		this.sellersService = sellersService;
	}

	public void setPointService(PointService pointService) {
		this.pointService = pointService;
	}

	public void setUserService(MUserService userService) {
		this.userService = userService;
	}

	public void setUserCertificationService(
			UserCertificationService userCertificationService) {
		this.userCertificationService = userCertificationService;
	}

}
