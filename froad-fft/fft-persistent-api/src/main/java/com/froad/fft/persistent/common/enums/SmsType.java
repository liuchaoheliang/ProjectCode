package com.froad.fft.persistent.common.enums;

/**
 * *******************************************************
 * <p> 工程: fft-server </p>
 * <p> 类名: SmsType.java </p>
 * <p> 描述: *-- <b>短信类型枚举</b> --* </p>
 * <p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
 * <p> 时间: 2014年1月26日 上午10:24:11 </p>
 * *******************************************************
 */
public enum SmsType
{

    authcodeRegister("1001", "用户注册短信验证码"),
    authcodeResetPwd("1002", "用户重置密码短信验证码"),
    authcodeModifiedMobile("1003", "用户修改手机号码短信验证码"),
    presellDelivery("1004", "精品预售提货短信"),
    presellRefund("1005", "精品预售退款短信"),
    presellClusterFail("1006", "精品预售不成团短信"),
    presellReturnSale("1007", "精品预售申请退货");

    private String code;
    private String describe;

    private SmsType(String code, String describe)
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
