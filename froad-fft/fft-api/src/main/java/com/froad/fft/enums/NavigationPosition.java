/**
 * 文件名称:NavigationPosition.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-14
 * 历史修改:  
 */
package com.froad.fft.enums;

/**
 * 导航位置
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public enum NavigationPosition
{
    /**
     * 顶部
     */
    top("0", "顶部"),

    /**
     * 中部
     */
    middle("1", "中间"),

    /**
     * 比例
     */
    bottom("2", "底部");//比例

    private String code;
    private String describe;

    private NavigationPosition(String code, String describe)
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
