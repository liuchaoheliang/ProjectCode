package com.froad.logic.res;

import com.froad.po.BankOperator;
import com.froad.po.Result;

/**
 * 
 * @ClassName: BankOperatorCheckRes 
 * @Description: 银行用户token检验Res
 * @author: ll
 * @date: 2015年5月14日
 */
public class BankOperatorCheckRes {
	private Result result;//结果集
	private BankOperator bankOperator;//银行用户对象
	
	
	public BankOperatorCheckRes() {
		
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public BankOperator getBankOperator() {
		return bankOperator;
	}
	public void setBankOperator(BankOperator bankOperator) {
		this.bankOperator = bankOperator;
	}
	
	
	
	
}
