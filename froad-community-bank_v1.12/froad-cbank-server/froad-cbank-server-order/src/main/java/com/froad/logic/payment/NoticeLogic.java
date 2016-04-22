package com.froad.logic.payment;

import com.froad.common.beans.ResultBean;


/**
 * 接收通知处理（只会处理现金类型交易通知）
* <p>Function: NoticeLogic</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2015-5-21 下午3:29:38
* @version 1.0
 */
public interface NoticeLogic {

	/**
	 * 现金消费通知
	* <p>Function: consumeNotic</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-25 下午5:32:20
	* @version 1.0
	* @param noticeXML
	* @return
	 */
	ResultBean consumeNotic(String noticeXML);
	
	/**
	 * 现金退款通知
	* <p>Function: refundNotic</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-28 上午11:01:25
	* @version 1.0
	* @param noticeXML
	* @return
	 */
	ResultBean refundNotic(String noticeXML);
}
