package com.froad.fft.persistent.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.BusinessCircleMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.BusinessCircle;

public class BusinessCircleMapperImpl implements BusinessCircleMapper
{

    @Resource
    private BusinessCircleMapper businessCircleMapper;

    @Override
    public Long saveBusinessCircle(BusinessCircle temp)
    {
         businessCircleMapper.saveBusinessCircle(temp);
        return temp.getId();
    }

    @Override
    public Boolean updateBusinessCircleById(BusinessCircle temp)
    {
        return businessCircleMapper.updateBusinessCircleById(temp);
    }

    @Override
    public BusinessCircle selectBusinessCircleById(Long id)
    {
        return businessCircleMapper.selectBusinessCircleById(id);
    }

    public List<BusinessCircle> selectBusinessCircleByPage(Page page)
    {
        return businessCircleMapper.selectBusinessCircleByPage(page);
    }

    public Integer selectBusinessCircleByPageCount(Page page)
    {
        return businessCircleMapper.selectBusinessCircleByPageCount(page);
    }
}
