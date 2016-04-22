package com.froad.fft.persistent.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.PresellDeliveryMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.PresellDelivery;

public class PresellDeliveryMapperImpl implements PresellDeliveryMapper
{

    @Resource
    private PresellDeliveryMapper presellDeliveryMapper;

    @Override
    public Long savePresellDelivery(PresellDelivery temp)
    {
        presellDeliveryMapper.savePresellDelivery(temp);
        return temp.getId();
    }

    @Override
    public Boolean updatePresellDeliveryById(PresellDelivery temp)
    {
        return presellDeliveryMapper.updatePresellDeliveryById(temp);
    }

    @Override
    public PresellDelivery selectPresellDeliveryById(Long id)
    {
        return presellDeliveryMapper.selectPresellDeliveryById(id);
    }

    @Override
    public List<PresellDelivery> selectPresellDeliveryByPage(Page page)
    {
        return presellDeliveryMapper.selectPresellDeliveryByPage(page);
    }

    @Override
    public Integer selectPresellDeliveryByPageCount(Page page)
    {
        return presellDeliveryMapper.selectPresellDeliveryByPageCount(page);
    }

}
