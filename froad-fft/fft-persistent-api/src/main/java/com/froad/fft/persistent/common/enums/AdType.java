/**
 * 文件名称:AdType.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-10
 * 历史修改:  
 */
package com.froad.fft.persistent.common.enums;

/**
 * todo:
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public enum AdType
{
    /**
     * 文本
     */
    text("0", "文本"),

    /**
     * 图片
     */
    image("1", "图片"),

    /**
     * flash
     */
    flash("2", "FLASH");//flash

    private String code;
    private String describe;

    private AdType(String code, String describe)
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
