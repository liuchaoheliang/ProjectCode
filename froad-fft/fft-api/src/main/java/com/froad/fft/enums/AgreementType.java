/**
 * 文件名称:AgreementType.java
 * 文件描述: 协议类型
 * 产品标识: fft
 * 单元描述: fft-api
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-9
 * 历史修改:  
 */
package com.froad.fft.enums;

/**
 * 协议类型
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public enum AgreementType
{
    register("0", "注册");//注册

    private String code;
    private String describe;

    private AgreementType(String code, String describe)
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
