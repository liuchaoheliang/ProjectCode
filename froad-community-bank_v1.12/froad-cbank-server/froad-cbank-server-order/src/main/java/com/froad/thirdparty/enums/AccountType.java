package com.froad.thirdparty.enums;

/**
 * 类描述：商户的账户类型
 *
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014
 * @author: 李金魁 lijinkui@f-road.com.cn
 * @time: 2014年3月25日 下午2:58:39
 */
public enum AccountType
{
    /**
     * 收款账户 *
     */
    collect("1", "收款账户"),

    /**
     * 扣款账户 *
     */
    deduct("2", "扣款账户");

    private String code;
    private String describe;

    private AccountType(String code, String describe)
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
