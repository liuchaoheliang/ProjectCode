package com.froad.enums;
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

	day("天"),
	week("周"),
	month("月"),
	year("年")
	;
	
    
    private String description;
    
	private CountTypeEnum(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
    
}
