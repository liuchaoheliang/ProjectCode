/**
 * 文件名称:ClusterState.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-8
 * 历史修改:  
 */
package com.froad.fft.persistent.common.enums;

/**
 * 预售成团状态
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public enum ClusterState
{

	wait("0", "未成团"),
    success("1", "成团成功"),
    fail("2", "成团失败");

    private String code;
    private String describe;

    private ClusterState(String code, String describe)
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
