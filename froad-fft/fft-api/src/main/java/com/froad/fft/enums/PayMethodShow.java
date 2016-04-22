/**
 * 文件名称:PayMethodShow.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-3
 * 历史修改:  
 */
package com.froad.fft.enums;

/**
 * todo:
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public enum PayMethodShow
{

    /**
     * 贴膜卡支付
     */
    film_card("film_card", " 贴膜卡支付"),

    /**
     * 方付通积分
     */
    points_fft("points_fft", "积分支付"),

    /**
     * 银行积分
     */
    points_bank("points_bank", "银行积分"),

    /**
     * 方付通积分+贴膜卡支付
     */
    points_fft_film_card("points_fft_film_card", "贴膜卡+积分支付"),

    /**
     * 银行积分+贴膜卡支付
     */
    points_bank_film_card("points_bank_film_card", "贴膜卡+银行积分支付"),

    /**
     * 支付宝支付
     */
    alipay("alipay", "支付宝支付"),

    /**
     * 支付宝+联盟积分
     */
    alipay_fft_points("alipay_fft_points", "支付宝+积分支付"),

    /**
     * 支付宝+银行积分
     */
    alipay_bank_points("alipay_bank_points", "支付宝+银行积分支付"),

    /**
     * 网银支付
     */
    internate_bank("internate_bank","网银支付"),

    /**
     * 网银支付+分分通积分
     */
    internate_fft_points("internate_fft_points","网银+积分支付"),

    /**
     * 网银支付+银行积分
     */
    internate_bank_points("internate_bank_points","网银+银行积分支付")
    ;

    private String code;

    private String describe;

    private PayMethodShow(String code, String describe)
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
