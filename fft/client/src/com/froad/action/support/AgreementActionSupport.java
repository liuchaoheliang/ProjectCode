package com.froad.action.support;

import java.util.List;

import org.apache.log4j.Logger;

import com.froad.client.agreement.Agreement;
import com.froad.client.agreement.AgreementService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 协议 client service  ActionSupport
 */
public class AgreementActionSupport {
	private static Logger logger = Logger.getLogger(AgreementActionSupport.class);
	private AgreementService agreementService;
	
	/**
	 * 根据 ID 查找 协议
	 * @param id
	 * @return
	 */
	public Agreement getAgreementById(Integer id){
		return agreementService.getAgreementById(id);
	}
	
	/**
	 * 条件查询
	 * @param agreement
	 * @return
	 */
	public List<Agreement> getAgreement(Agreement agreement){
		return agreementService.getAgreement(agreement);
	}
	
	public AgreementService getAgreementService() {
		return agreementService;
	}
	public void setAgreementService(AgreementService agreementService) {
		this.agreementService = agreementService;
	}
	
}
