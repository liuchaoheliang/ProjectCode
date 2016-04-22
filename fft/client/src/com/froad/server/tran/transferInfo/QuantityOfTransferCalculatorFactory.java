package com.froad.server.tran.transferInfo;

import com.froad.client.transRule.RuleDetail;

public interface QuantityOfTransferCalculatorFactory {
	public QuantityOfTransferCalculator getQuantityOfTransferCalculator(RuleDetail ruleDetail) throws Exception;
}
