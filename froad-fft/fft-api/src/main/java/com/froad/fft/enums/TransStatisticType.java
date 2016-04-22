/**
 * 文件名称:TransStatisticType.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-5-5
 * 历史修改:  
 */
package com.froad.fft.enums;

/**
 * todo:
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public enum TransStatisticType
{
    TOTALPRICE("0", "总金额排行"),
    ORDERTIME("1", "订单时间排行");

    private String code;
    private String describe;

    private TransStatisticType(String code, String describe)
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
