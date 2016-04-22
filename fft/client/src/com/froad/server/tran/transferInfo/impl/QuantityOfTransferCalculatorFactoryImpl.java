package com.froad.server.tran.transferInfo.impl;

import java.util.Map;

import com.froad.client.transRule.RuleDetail;
import com.froad.exception.transaction.CountTypeOfSellRuleDetailIsNull;
import com.froad.exception.transaction.RuleDetailIsNull;
import com.froad.server.tran.transferInfo.QuantityOfTransferCalculator;
import com.froad.server.tran.transferInfo.QuantityOfTransferCalculatorFactory;
import com.froad.util.Assert;
import com.froad.util.command.Constants;

public class QuantityOfTransferCalculatorFactoryImpl implements
		QuantityOfTransferCalculatorFactory {

	private Map<String,QuantityOfTransferCalculator> beanIdAndBean;
	public QuantityOfTransferCalculator getQuantityOfTransferCalculator(
			RuleDetail ruleDetail) throws Exception {
		// TODO Auto-generated method stub
		QuantityOfTransferCalculator quantityOfTransferCalculator=null;
		if(ruleDetail==null)
			throw new RuleDetailIsNull("卖家规则明细为空");
		String countType=ruleDetail.getRuleDetailTemplet().getCalculateType();
		if(Assert.empty(countType))
			throw new CountTypeOfSellRuleDetailIsNull("卖家规则明细的计算类型为空，系统支持的卖家规则明细的计算类型为：公式");
		String beanId=Constants.countType_BeanId.get(ruleDetail.getRuleDetailTemplet().getCalculateType());
		quantityOfTransferCalculator=beanIdAndBean.get(beanId);
		return quantityOfTransferCalculator;
	}
	public Map<String, QuantityOfTransferCalculator> getBeanIdAndBean() {
		return beanIdAndBean;
	}
	public void setBeanIdAndBean(
			Map<String, QuantityOfTransferCalculator> beanIdAndBean) {
		this.beanIdAndBean = beanIdAndBean;
	}
	

}
