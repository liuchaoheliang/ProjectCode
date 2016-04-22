/**
 * 文件名称:GivePointRuleType.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-11
 * 历史修改:  
 */
package com.froad.fft.enums;

/**
 * 送积分规则类型
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public enum GivePointRuleType
{
    /**
     * 固定
     */
    fixed("0", "固定"),

    /**
     * 比例
     */
    scale("1", "比例");//比例

    private String code;
    private String describe;

    private GivePointRuleType(String code, String describe)
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
