/**
 * 文件名称:TransState.java
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
public enum TransState
{
    processing("20", "交易处理中"),
    success("30", "交易成功"),
    fail("40", "交易失败"),
    close("50", "交易关闭"),
	exception("60","交易异常");

    private String code;
    private String describe;

    private TransState(String code, String describe)
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
