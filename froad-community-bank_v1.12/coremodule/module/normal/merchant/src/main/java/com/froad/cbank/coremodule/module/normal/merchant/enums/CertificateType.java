package com.froad.cbank.coremodule.module.normal.merchant.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 证件类型
 * @ClassName CertificateType
 * @author zxl
 * @date 2015年5月7日 下午12:54:11
 */
public enum CertificateType {
	
	IDCARD("1", "身份证"),
    PASSPORT("2", "护照"),
    MILITARYID("3", "军官证"),
    SOLDIERID("4", "士兵证"),
    HOUSEHOLDREGISTER("5", "户口本"),
    POLICEID("6", "警官证"),
    TAIWANGID("7", "台胞证");
    
    private String code;
    
    private String describe;
    
    private CertificateType(String code,String describe){
        this.code=code;
        this.describe=describe;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}
    
	public static String getDescribeByCode(String code){
		if(StringUtils.isBlank(code)){
			return code;
		}
		for(CertificateType c : CertificateType.values()){
			if(c.getCode().equals(code)){
				return c.getDescribe();
			}
		}
		return code;
	}
}
