/**
 * 文件名称:PayTypeDetails.java
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
public enum PayTypeDetails
{

    PAY_AMOUNT("000", "金额支付"),
    PAY_FFT_POINTS("001", "分分通积分支付"),
    PAY_BANK_POINTS("002", "银行积分支付"),

    BUY_POINTS_AMOUNT("100", "购买积分的金额"),
    BUY_POINTS_FEE("101", "购买积分的手续费"),
    REBATE_POINTS("102", "给用户返积分");

    private String code;
    private String describe;

    private PayTypeDetails(String code, String describe)
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
