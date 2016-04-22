package com.froad.util.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {
	public static final String trans_type_transaction="transaction";
	public static final String trans_type_transPoints="transPoints";
	public static final String trans_type_transAllowance="transAllowance";
	public static final String trans_type_transPointsExchCurrency="transPointsExchCurrency";
	
	//用于 转移信息生成器工厂 
	public static final String points_transfer="pointsTransferInfoGenerator";
	public static final String currency_transfer="currencyTransferInfoGenerator";
	public static final String vProduct_transfer="vProductTransferInfoGenerator";
	public static final Map<String, String> rule_type_points_transfer=new HashMap();
	static {
		rule_type_points_transfer.put(RuleDetailCategory.Points.getValue(), points_transfer);
		rule_type_points_transfer.put(RuleDetailCategory.Currency.getValue(), currency_transfer);
		rule_type_points_transfer.put(RuleDetailCategory.VProduct.getValue(), vProduct_transfer);
	}
	
	
	//规则计算类型（通过公式计算，类计算）
	public static final String countType_Formula="00";
	public static final String countType_Class="01";
	public static final String beanId_Formula="quantityOfTransferCalculatorByFormula";
	public static final String beanId_Class="";
	public static final Map<String, String> countType_BeanId=new HashMap();
	static {
		countType_BeanId.put(countType_Formula, beanId_Formula);
		countType_BeanId.put(countType_Class, beanId_Class);
	}
	
//	public static final String TranType_TransPoints="transPoints";
//	public static final String TranType_transAllowance="transAllowance";
//	public static final String TranType_trans="transaction";
	
}
