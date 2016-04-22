package com.froad.cbank.coremodule.module.normal.bank.enums;
/**
 * 
 * ClassName: ProcessIdEnum
 * Function: 审核流程ID
 * date: 2015-10-22 下午01:56:33
 *
 * @author wufei
 * @version
 */
public enum ProcessIdEnum {

	MERCHANT_ADD("100000000","商户新增审核"),
	MERCHANT_UPDATE("100000001","商户更新审核"),
	OUTLET_ADD("100000002","门店新增审核"),
	OUTLET_UPDATE("100000003","门店新增审核"),
	GROUPPRODUCT_ADD("100000004","团购商品新增审核"),
	PRESALEPRODUCT_ADD("100000005","预售商品新增审核");
	
	
	private String code;
	private String description;
	private ProcessIdEnum(String code,String description){
		this.code = code;
		this.description = description;
	}
	public String getCode() {
		return code;
	}
	public String getDescription() {
		return description;
	}
	
	
	
	
}
