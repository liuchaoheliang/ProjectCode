package com.froad.fft.persistent.common.enums;

/**
 * 数据状态
 * @author FQ
 *
 */
public enum DataState {
	
	create("10","创建"),//创建
	entering("20","录入"),//录入
    enable("30","启用"),//启用
	disable("40","停用"),//停用
	delete("50","删除") ;//删除
	
	
    private String code;
    private String describe;
    private DataState(String code, String describe)
    {
        this.code = code;
        this.describe = describe;
    }

    public String getCode()
    {
        return code;
    }

    public String getDescribe()
    {
        return describe;
    }

    @Override
    public String toString()
    {
        return this.code;
    }
    
}
