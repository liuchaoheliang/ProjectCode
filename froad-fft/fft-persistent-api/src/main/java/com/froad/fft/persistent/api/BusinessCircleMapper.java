/**
 * 文件名称:BusinessCircleMapper.java
 * 文件描述: 商圈mappler接口定义
 * 产品标识: fft
 * 单元描述: fft-president-api
 * 编写人: gq.hou_Mimosa
 * 编写时间: 14-3-24
 * 历史修改: 2014-3-24 by gq.hou_Mimosa
 */
package com.froad.fft.persistent.api;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.BusinessCircle;

import java.util.List;

public interface BusinessCircleMapper
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
     * /**
     * 方法描述：分页查询
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 侯国权
     * @time: 2014年3月28日 上午10:32:48
     */
    public List<BusinessCircle> selectBusinessCircleByPage(Page page);

    /**
     * 方法描述：分页总条数
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author:侯国权
     * @time: 2014年3月28日 上午10:32:50
     */
    public Integer selectBusinessCircleByPageCount(Page page);
}
