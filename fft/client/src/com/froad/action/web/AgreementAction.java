package com.froad.action.web;

import java.util.List;

import net.sf.json.JSONObject;

import com.froad.action.support.AgreementActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.agreement.Agreement;
import com.froad.util.Command;

/** 
 * @author FQ 
 * @date 2013-2-19 下午04:39:00
 * @version 1.0
 * 协议
 */
public class AgreementAction extends BaseActionSupport {
	
	private AgreementActionSupport agreementActionSupport;
	
	private Agreement agreement;

	//查询协议
	public String agreement(){
		log.info("协议内容");
		
		agreement.setState(Command.FBU_USERED_STATE);
		
		log.info("协议查询条件："+JSONObject.fromObject(agreement));
		List<Agreement>  list=agreementActionSupport.getAgreement(agreement);
		
		if(list!=null && !list.isEmpty()){
			agreement=list.get(0);
			return ajaxHtml(agreement.getContent());
		}
		else{
			return ajaxHtml("");
		}
	}
	
	public AgreementActionSupport getAgreementActionSupport() {
		return agreementActionSupport;
	}

	public void setAgreementActionSupport(
			AgreementActionSupport agreementActionSupport) {
		this.agreementActionSupport = agreementActionSupport;
	}
	
	public Agreement getAgreement() {
		return agreement;
	}

	public void setAgreement(Agreement agreement) {
		this.agreement = agreement;
	}
}
