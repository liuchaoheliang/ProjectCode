/**
 * 文件名称:TreeDto.java
 * 文件描述: 树状接口dto
 * 产品标识: fft
 * 单元描述: fft-api
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-10
 * 历史修改:  
 */
package com.froad.fft.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public abstract class TreeDto implements Serializable
{

    private Long id;

    private Date createTime;

    private String treePath;// 树路径

    private Long parentId;//上级地区ID

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTreePath()
    {
        return treePath;
    }

    public void setTreePath(String treePath)
    {
        this.treePath = treePath;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
}
