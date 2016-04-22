package com.froad.server.tran.transferInfo;

import com.froad.client.transRule.RuleDetail;

public interface TransferInfoGeneratorFactory {
	public TransferInfoGenerator getTransferInfoGenerator(RuleDetail ruleDetail) throws Exception;
}
