/**
 * 文件名称:TagType.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-15
 * 历史修改:  
 */
package com.froad.fft.enums;

/**
 * todo:
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public enum TagType
{
    /**
     * 商户
     */
    merchant("0", "商户");

    private String code;
    private String describe;

    private TagType(String code, String describe)
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
