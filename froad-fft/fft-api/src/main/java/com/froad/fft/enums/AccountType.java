/**
 * 文件名称:AccountType.java
 * 文件描述: 商户账户类型
 * 产品标识: fft
 * 单元描述: fft-api
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-1
 * 历史修改:  
 */
package com.froad.fft.enums;

/**
 * 账户类型
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public enum AccountType
{

    /**
     * 收款账户 *
     */
    collect("1", "收款账户"),

    /**
     * 扣款账户 *
     */
    deduct("2", "扣款账户");

    private String code;
    private String describe;

    private AccountType(String code, String describe)
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
