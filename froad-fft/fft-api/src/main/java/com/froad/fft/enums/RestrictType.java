/**
 * 文件名称:RestrictType.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-21
 * 历史修改:  
 */
package com.froad.fft.enums;

/**
 * 限购类型
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public enum RestrictType
{

    /**
     * 单用户总购买数量
     */
    singleUser_total("0", "单用户总购买数量");

    private String code;
    private String describe;

    private RestrictType(String code, String describe)
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
