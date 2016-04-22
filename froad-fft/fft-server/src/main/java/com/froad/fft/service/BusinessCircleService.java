/**
 * 文件名称:BusinessCircleService.java
 * 文件描述: 商圈接口
 * 产品标识: fft
 * 单元描述: fft-server
 * 编写人: gq.hou_Mimosa
 * 编写时间: 14-3-26
 * 历史修改:  
 */
package com.froad.fft.service;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.BusinessCircle;

import java.util.List;

/**
 * 商圈service
 *
 * @author Aides
 * @version 1.0
 */
public interface BusinessCircleService
{
    /**
     * *******************************************************
     * <p> 描述: *-- <b>保存GivePointRule</b> --* </p>
     * <p> 作者: 侯国权 </p>
     * <p> 时间: 2014年1月3日 下午4:29:27 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Long saveBusinessCircle(BusinessCircle temp);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键更新</b> --* </p>
     * <p> 作者: 侯国权 </p>
     * <p> 时间: 2014年1月6日 上午9:59:25 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Boolean updateBusinessCircleById(BusinessCircle temp);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键获取对象</b> --* </p>
     * <p> 作者: 侯国权 </p>
     * <p> 时间: 2014年1月6日 上午10:18:23 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public BusinessCircle selectBusinessCircleById(Long id);

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    public Page selectBusinessCircleByPage(Page page);



}
