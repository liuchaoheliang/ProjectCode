package com.froad.cbank.coremodule.module.normal.bank.enums;
/**
 * 
 * ClassName: CountTypeEnum
 * Function: 统计类型
 * date: 2015-12-4 下午01:38:08
 *
 * @author wufei
 * @version
 */
public enum CountTypeEnum {

	DAY("day","天"),
	WEEK("week","周"),
	MONTH("month","月"),
	YEAR("year","年"),
	DIY("diy","自定义")
	;
	
    private String code;
    private String description;
    
	
	private CountTypeEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String getCode() {
		return code;
	}

    
    
}
