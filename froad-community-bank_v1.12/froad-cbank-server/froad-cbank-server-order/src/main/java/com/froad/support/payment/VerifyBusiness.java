package com.froad.support.payment;

import com.froad.common.beans.ResultBean;
import com.froad.po.Payment;

public interface VerifyBusiness {

	
	/**
	 * 验证现金结算类型流水
	* <p>Function: typeOfCashForSettle</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-5 下午3:07:13
	* @version 1.0
	* @param payment
	* @return
	 */
	ResultBean typeOfCashForSettle(Payment payment);
	
	/**
	 * 验证现金用户退款类型流水
	* <p>Function: typeOfCashForUserRefund</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-5 下午3:10:03
	* @version 1.0
	* @param payment
	* @return
	 */
	ResultBean typeOfCashForUserRefund(Payment payment);
	
	/**
	 * 验证现金用户支付类型流水
	* <p>Function: typeOfCashForUserPay</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-5 下午3:11:05
	* @version 1.0
	* @param payment
	* @return
	 */
	ResultBean typeOfCashForUserPay(Payment payment);
	
	/**
	 * 验证用户积分支付
	* <p>Function: typeOfPointForUserPay</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-7 下午12:18:13
	* @version 1.0
	* @param payment
	* @return
	 */
	ResultBean typeOfPointForUserPay(Payment payment);
	
	/**
	 * 验证用户积分退款
	* <p>Function: typeOfPointForUserRefund</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-7 下午12:18:25
	* @version 1.0
	* @param payment
	* @return
	 */
	ResultBean typeOfPointForUserRefund(Payment payment);
}
