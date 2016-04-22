/**
 * 文件名称:PayRole.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-3
 * 历史修改:  
 */
package com.froad.fft.enums.trans;

/**
 * todo:
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public enum PayRole
{

    member("1", "用户"),
    merchant("2", "商户"),
    bank("3", "银行或者银行积分池"),
    fft("4", "方付通或者方付通积分池");

    private String code;
    private String describe;

    private PayRole(String code, String describe)
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
