package com.froad.timer.task.support;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.froad.cons.AccountCheckingConst;
import com.froad.db.mongo.PaymentMongoService;
import com.froad.db.mongo.impl.PaymentMongoServiceImpl;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.MerchantAccountMapper;
import com.froad.enums.AccountTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.po.MerchantAccount;
import com.froad.po.mongo.PaymentMongo;
import com.froad.util.Arith;
import com.froad.util.IOTXTAppend;

/**
 * 现金对账处理
* <p>Function: AccountCheckingOfCash</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2015年7月30日 上午11:48:18
* @version 1.0
 */
public class AccountCheckingOfCash {

	private PaymentMongoService paymentMongoService = new  PaymentMongoServiceImpl();
	
	/**
	 * 查询商户帐号信息
	* <p>Function: queryMerchantAccount</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015年8月4日 下午5:24:39
	* @version 1.0
	* @param merchantAccount
	* @return
	 */
	private MerchantAccount queryMerchantAccount(MerchantAccount merchantAccount){
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			MerchantAccountMapper mapper = sqlSession.getMapper(MerchantAccountMapper.class);
			return mapper.queryByCondition(merchantAccount);
		} catch (Exception e){
			LogCvt.error("执行查询商户帐号信息异常", e);
		} finally {
			if(sqlSession != null)
				sqlSession.close();
		}
		return null;
	} 
	
	public void doCheck(PaymentMongo pm,String uri){
		
		//交易编码
		String tranCode = "";
		String[] sbArray = new String[15];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHssmm");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		
		String fromAccName = null,fromAccNo = null,toAccName = null,toAccNo = null;
		
		//现金支付：贴膜卡支付
		if(pm.getPaymentType() == 2 && pm.getPaymentReason().equals("2") && pm.getPaymentTypeDetails()==2){
			tranCode = pm.getFromUserMemberCode().toString();//AccountCheckingConst.FOILCARD_PAY;
			fromAccNo = paymentMongoService.queryByOrderId(pm.getOrderId()).getMemberCode().toString();
			fromAccName = "";
			toAccName = AccountCheckingConst.FROAD_ACC_NAME;
			toAccNo = AccountCheckingConst.FROAD_ACC_NO;
		}
		//现金支付：快捷支付
		else if(pm.getPaymentType() == 2 && pm.getPaymentReason().equals("2") && pm.getPaymentTypeDetails()==51){
			tranCode = AccountCheckingConst.SHORTCUT_PAY;
			
			fromAccNo = paymentMongoService.queryByOrderId(pm.getOrderId()).getMemberCode().toString();
			fromAccName = "";
			toAccName = AccountCheckingConst.FROAD_ACC_NAME;
			toAccNo = AccountCheckingConst.FROAD_ACC_NO;
		}
		//现金支付：现金退款
		else if(pm.getPaymentType() == 2 && pm.getPaymentReason().equals("1")){
			tranCode = AccountCheckingConst.CASHRETURN_PAY;
			
			fromAccNo = AccountCheckingConst.FROAD_ACC_NO;
			fromAccName = AccountCheckingConst.FROAD_ACC_NAME;
			toAccNo = paymentMongoService.queryByOrderId(pm.getOrderId()).getMemberCode().toString();
			toAccName = "";
		}
		//现金支付：即时支付
		else if(pm.getPaymentType() == 2 && pm.getPaymentReason().equals("0") && pm.getPaymentTypeDetails()==55){
			tranCode = AccountCheckingConst.IMMEDIATE_PAY;
//			String[] accountInfo = pm.getSettleMerchantAndOutletId().split("\\|");
//			String merchantId = accountInfo[0]; // 商户id
//			String outletId = accountInfo[1];
//			
//			MerchantAccount merchantAccount = new MerchantAccount();
//			merchantAccount.setMerchantId(merchantId);
//			merchantAccount.setOutletId(outletId);
//			merchantAccount.setIsDelete(false);
//			merchantAccount.setAcctType(AccountTypeEnum.SQ.getCode());
//			merchantAccount = queryMerchantAccount(merchantAccount);
			fromAccNo = AccountCheckingConst.FROAD_ACC_NO;
			fromAccName = AccountCheckingConst.FROAD_ACC_NAME;
//			if(merchantAccount != null){
//				toAccNo = merchantAccount.getAcctNumber();
//				toAccName = merchantAccount.getAcctName();
//			}else{
//				toAccNo = "";
//				toAccName = "";
//			}
			toAccNo = pm.getSettleToAccountNo();
			toAccName = pm.getSettleToAccountName();
		}
		
		//------支付|退款|结算
		//需要采集的字段
		//(请求流水)payment_id,(账单号)bill_no,(交易额)payment_value,(交易时间)create_time,(交易类型),
		//(结果码)result_code,对账日期,(银行组号)payment_org_no,借方名称，借方账号，贷方名称，贷方账号,银行名称,商户编号,前端平台名称
		sbArray[0] = pm.getPaymentId();
		//如果为退款，返回payment_id
		if(pm.getPaymentType() == 2 && pm.getPaymentReason().equals("1") || pm.getPaymentReason().equals("5")){
			sbArray[1] = pm.getPaymentId();
		}else{
			sbArray[1] = pm.getBillNo();
		}
		sbArray[2] = String.valueOf(Arith.div(Double.parseDouble(pm.getPaymentValue().toString()), 1000));
		sbArray[3] = sdf.format(pm.getCreateTime());
		sbArray[4] = tranCode;
		//处理code
		if(4 == pm.getStep()){
			if("5".equals(pm.getPaymentStatus())){
				sbArray[5] = "9999";
			}else{
				sbArray[5] = "0000";
			}
		}else{
			sbArray[5] = "0001";
		}
		sbArray[6] = sdf2.format(new Date());
		sbArray[7] = pm.getPaymentOrgNo();
		sbArray[8] = fromAccName;
		sbArray[9] = fromAccNo;
		sbArray[10] = toAccName;
		sbArray[11] = toAccNo;
		Map<String, String> client = AccountCheckingConst.getClientInfo(pm.getClientId());
		sbArray[12] = client.get("bank_name");
		sbArray[13] = client.get("openapi_partner_no");
		sbArray[14] = client.get("bank_name");
		IOTXTAppend.appendData(uri, sbArray);
	}
}