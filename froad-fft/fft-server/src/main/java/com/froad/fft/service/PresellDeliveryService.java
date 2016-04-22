/**
 * 文件名称:PresellDeliveryService.java
 * 文件描述: 预售自提点service
 * 产品标识: fft
 * 单元描述: fft-server
 * 编写人: houguoquan_Aides
 * 编写时间: 14-3-27
 * 历史修改:  
 */
package com.froad.fft.service;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.PresellDelivery;

import java.util.List;

/**
 * 预售自提点service
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public interface PresellDeliveryService
{
    /**
     * *******************************************************
     * <p> 描述: *-- <b>保存PresellDelivery</b> --* </p>
     * <p> 作者: 侯国权 </p>
     * <p> 时间: 2014年1月3日 下午4:29:27 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Long savePresellDelivery(PresellDelivery temp);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键更新</b> --* </p>
     * <p> 作者: 侯国权 </p>
     * <p> 时间: 2014年1月6日 上午9:59:25 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Boolean updatePresellDeliveryById(PresellDelivery temp);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键获取对象</b> --* </p>
     * <p> 作者: 侯国权 </p>
     * <p> 时间: 2014年1月6日 上午10:18:23 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public PresellDelivery selectPresellDeliveryById(Long id);

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    public Page findPresellDeliveryByPage(Page page);

}
