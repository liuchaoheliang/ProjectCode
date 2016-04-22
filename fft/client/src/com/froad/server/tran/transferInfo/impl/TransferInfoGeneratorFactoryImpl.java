package com.froad.server.tran.transferInfo.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;


import com.froad.client.transRule.RuleDetail;
import com.froad.exception.transaction.RuleDetailIsNull;
import com.froad.server.tran.transferInfo.TransferInfoGenerator;
import com.froad.server.tran.transferInfo.TransferInfoGeneratorFactory;
import com.froad.util.Assert;
import com.froad.util.command.Constants;

public class TransferInfoGeneratorFactoryImpl implements
		TransferInfoGeneratorFactory {
	private static final Logger log=Logger.getLogger(TransferInfoGeneratorFactoryImpl.class);
	private Map<String,TransferInfoGenerator> beanIdAndBean=new HashMap();

	public TransferInfoGenerator getTransferInfoGenerator(RuleDetail ruleDetail) throws Exception {
		// TODO Auto-generated method stub
		TransferInfoGenerator transferInfoGenerator=null;
		if(ruleDetail==null)
			throw new RuleDetailIsNull("规则明细为空");
		String beanId=Constants.rule_type_points_transfer.get(ruleDetail.getRuleDetailTemplet().getRuleDetailCategory());
		if(Assert.empty(beanId)){
			
			log.error("Constants中points_transfer的值为空,points_transfer的值应该为:该类中beanIdAndBean属性的key,其key对应的是积分转移信息生成器bean");
			return transferInfoGenerator;
		}
		transferInfoGenerator=beanIdAndBean.get(beanId);	
		return transferInfoGenerator;
	}

	public Map<String, TransferInfoGenerator> getBeanIdAndBean() {
		return beanIdAndBean;
	}

	public void setBeanIdAndBean(Map<String, TransferInfoGenerator> beanIdAndBean) {
		this.beanIdAndBean = beanIdAndBean;
	}

}
