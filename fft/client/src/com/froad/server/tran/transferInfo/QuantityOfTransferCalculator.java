package com.froad.server.tran.transferInfo;

import com.froad.client.pay.Pay;
import com.froad.client.trans.Trans;
import com.froad.client.transRule.RuleDetail;

public interface QuantityOfTransferCalculator {
	/**
	  * 方法描述：根据卖家规则明细计算该交易流转信息的流转数量
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	 * @throws Exception 
	  * @time: 2012-12-20 上午11:52:07
	 */
	public double calculateQuantityOfTransfer(Trans tran, RuleDetail ruleDetail) throws Exception;
}
