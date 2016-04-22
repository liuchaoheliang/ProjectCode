/**
 * 文件名称:DataState.java
 * 文件描述: 数据状态枚举
 * 产品标识: 分分通
 * 单元描述: fft-api
 * 编写人: houguoquan_Aides
 * 编写时间: 14-3-27
 * 历史修改:  
 */
package com.froad.thirdparty.enums;

/**
 * 数据状态枚举
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public enum DataState
{
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
