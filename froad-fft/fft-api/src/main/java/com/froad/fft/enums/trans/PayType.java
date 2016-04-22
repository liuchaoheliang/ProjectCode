/**
 * 文件名称:PayType.java
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
public enum PayType
{
    /**
	 * 资金
	 */
	cash("10","资金支付"),

	/**
	 * 积分
	 */
	points("20","积分支付");

    private String code;
    private String describe;

    private PayType(String code, String describe)
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
