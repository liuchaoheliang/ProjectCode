package com.froad.server.tran.transferInfo.impl;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.froad.client.pay.Pay;
import com.froad.client.trans.Trans;
import com.froad.client.transRule.RuleDetail;
import com.froad.exception.transaction.CalculatQuantityException;
import com.froad.exception.transaction.FormulaHasError;
import com.froad.exception.transaction.FormulaParaException;
import com.froad.server.tran.transferInfo.QuantityOfTransferCalculator;
import com.froad.util.Assert;
import com.froad.util.CommonUtil;
import com.froad.util.command.ParamOfFormulaFrom;

public class QuantityOfTransferCalculatorByFormula implements
		QuantityOfTransferCalculator {
	private static final Logger log=Logger.getLogger(QuantityOfTransferCalculatorByFormula.class);
	
	//获取公式的参数值
	private String getParaOfFormula(Trans tran,RuleDetail ruleDetail){
		String paraString="";
		if(ParamOfFormulaFrom.ParamOfFormulaFrom_TranObj.equals(ruleDetail.getRuleDetailTemplet().getParamOfFormulaFrom())){
		
			String methodName=ruleDetail.getRuleDetailTemplet().getParamOfFormula();//获取公式参数的方法名
			try{
				Method method=tran.getClass().getMethod(methodName, null);
				paraString=(String)method.invoke(tran, null);
			}catch(Exception e){
				log.error("在交易使用卖家规则计算流转数量时，获取该规则的公式的参数时，产生异常。" +
						"其卖家规则明细信息为："+ruleDetail, e);
				throw new FormulaParaException("在交易使用卖家规则计算流转数量时，获取该规则的公式的参数时，产生异常。");
			}
		}
		if(ParamOfFormulaFrom.ParamOfFormulaFrom_Class.equals(ruleDetail.getRuleDetailTemplet().getParamOfFormulaFrom())){
			String className=ruleDetail.getRuleDetailTemplet().getParamOfFormula();//获取公式参数的方法名
			try{
				Object obj=Class.forName(className).newInstance();
				String methodName="getParaFormula";
				Class[] paraClazz={com.froad.client.trans.Trans.class};
				Object[] paraObj={tran};			
				Method method=obj.getClass().getMethod(methodName,paraClazz );
				paraString=(String)method.invoke(obj,paraObj);
			}catch(Exception e){
				log.error("在交易使用卖家规则计算流转数量时，获取该规则的公式的参数时，产生异常。" +
						"其卖家规则明细信息为："+ruleDetail, e);
				throw new FormulaParaException("在交易使用卖家规则计算流转数量时，获取该规则的公式的参数时，产生异常。");
			}
		}
			
		return paraString;
	}
	
	public double calculateQuantityOfTransfer(Trans tran,
			RuleDetail ruleDetail) throws Exception {
		// TODO Auto-generated method stub
		String paraString=getParaOfFormula(tran,ruleDetail);
		if(Assert.empty(paraString)){
			log.error("在交易使用卖家规则计算流转数量时，公式的参数为空");
			throw new FormulaParaException("在交易使用卖家规则计算流转数量时，公式的参数为空");
		}
		double param=0;
		
		try{
			param=Double.valueOf(paraString);
		}catch(Exception e){
			log.error("在交易使用卖家规则计算流转数量时，获取该规则的公式的参数时，产生异常。" +
					"其卖家规则明细信息为："+ruleDetail, e);
			throw new FormulaParaException("在交易使用卖家规则计算流转数量时，获取该规则的公式的参数时，产生异常。");
		}
		String formula = ruleDetail.getFormula();//公式，公式中的另一个操作数为总交易金额，也就是说公式是对总交易金额乘以一个系数
		if(Assert.empty(formula)){
			log.error("在交易使用卖家规则计算流转数量时，计算产生异常。因formula不是数字,formula值为：空");
			throw new FormulaHasError("在交易使用卖家规则计算流转数量时，计算产生异常。因formula不是数字,formula值为：空");
		}
		double ratio=0;
		try{
			ratio=Double.valueOf(formula);
		}catch(Exception e){
			log.error("在交易使用卖家规则计算流转数量时，计算产生异常。因formula不是数字,formula值为："+formula +"，其卖家规则明细信息为："+ruleDetail, e);
			throw new FormulaHasError("在交易使用卖家规则计算流转数量时，计算产生异常。因formula不是数字,formula值为："+formula );
		}
		double quantity=param*ratio;
		if(quantity<0){
			log.error("系统用该卖家规则计算的流转资金有误");
			throw new CalculatQuantityException("系统用该卖家规则计算的流转资金有误");
		}
		quantity=CommonUtil.scale2(quantity,2);
		if(!Assert.empty(ruleDetail.getMax())){
			try {
				double max = Double.parseDouble(ruleDetail.getMax());
				if(quantity>max)
					quantity=max;
			} catch (Exception e) {
				// TODO: handle exception
				log.error("规则明细计算流转信息的流转量时，流转的最大值转型异常", e);
			}
		}
		if(!Assert.empty(ruleDetail.getMin())){
			try {
				double min = Double.parseDouble(ruleDetail.getMin());
				if(quantity<min)
					quantity=min;
			} catch (Exception e) {
				// TODO: handle exception
				log.error("规则明细计算流转信息的流转量时，流转的最大值转型异常", e);
			}
		}
		return quantity;
	}

}
