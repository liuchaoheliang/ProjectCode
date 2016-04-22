package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.*;

@WebService
public interface PresellDeliveryService
{
    /**
     * 方法描述：添加提货点信息
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014-2-27 下午01:50:45
     */
    public Integer add(PresellDelivery presellDelivery);

    /**
     * 方法描述：根据Id更新数据
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014-2-27 下午02:07:23
     */
    public Integer updateById(PresellDelivery presellDelivery);

    /**
     * 方法描述：根据Id查询数据
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014-2-27 下午02:07:26
     */
    public PresellDelivery getById(Integer id);

    /**
     * 方法描述：根据条件查询数据
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014-2-27 下午02:07:29
     */
    public List<PresellDelivery> getByConditions(PresellDelivery presellDelivery);

    /**
     * 方法描述：提货点分页接口
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014-2-28 下午04:14:27
     */
    public PresellDelivery getByPager(PresellDelivery presellDelivery);

    /**
     * 方法描述：根据商品编号获取提货点列表
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 侯国权  houguoquan@f-road.com.cn
     * @time: 2014-3-9 下午02:07:29
     */
    public List<PresellDelivery> getByRackId(Integer rackId);


}
