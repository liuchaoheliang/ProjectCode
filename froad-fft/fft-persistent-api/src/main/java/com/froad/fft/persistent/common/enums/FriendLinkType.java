/**
 * 文件名称:FriendLinkType.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-15
 * 历史修改:  
 */
package com.froad.fft.persistent.common.enums;

/**
 * 友情链接类型
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public enum FriendLinkType
{
    /**
     * 文本
     */
    text("0", "文本"),

    /**
     * 图片
     */
    image("1", "图片");

    private String code;
    private String describe;

    private FriendLinkType(String code, String describe)
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
