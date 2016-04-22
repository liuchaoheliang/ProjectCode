/**
 * 文件名称:CertificateType.java
 * 文件描述: 证件类型枚举
 * 产品标识: fft
 * 单元描述: fft-server
 * 编写人: houguoquan_Aides
 * 编写时间: 14-5-9
 * 历史修改:  
 */
package com.froad.enums;

/**
 * 证件类型
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public enum CertificateType
{

    IDCARD("1", "身份证"),
    PASSPORT("2", "护照"),
    MILITARYID("3", "军官证"),
    SOLDIERID("4", "士兵证"),
    HOUSEHOLDREGISTER("5", "户口本"),
    POLICEID("6", "警官证"),
    TAIWANGID("7", "台胞证");

    private String code;
    private String describe;

    private CertificateType(String code, String describe)
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
