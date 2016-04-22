/**
 * 文件名称:TransPayState.java
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
public enum TransPayState
{
    unpaid("10", "未支付"),
    partPayment("20", "部分支付"),
    paid("30", "已支付"),
    refunding("40", "退款中"),
    refunded("50", "全额退款");

    private String code;
    private String describe;

    private TransPayState(String code, String describe)
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
