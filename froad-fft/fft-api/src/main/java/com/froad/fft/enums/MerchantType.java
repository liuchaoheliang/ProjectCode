package com.froad.fft.enums;

/**
 * 商户类型
 *
 * @author FQ
 */
public enum MerchantType
{

    /**
     * 方付通商户
     */
    froad("10", "方付通商户"),

    /**
     * 积分返利
     */
    points_rebate("20", "积分返利商户"),

    /**
     * 直接优惠
     */
    direct_preferential("30", "直接优惠商户"),

    /**
     * 银行商户
     */
    bank("40", "银行商户");

    private String code;
    private String describe;

    private MerchantType(String code, String describe)
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
