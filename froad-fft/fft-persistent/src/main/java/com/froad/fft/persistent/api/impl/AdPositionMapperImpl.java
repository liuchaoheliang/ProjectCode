package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.AdPositionMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.AdPosition;

import java.util.List;

public class AdPositionMapperImpl implements AdPositionMapper
{

    @Resource
    private AdPositionMapper adPositionMapper;

    @Override
    public Long saveAdPosition(AdPosition adPosition)
    {
        adPositionMapper.saveAdPosition(adPosition);
        return adPosition.getId();
    }

    @Override
    public Boolean updateAdPositionById(AdPosition adPosition)
    {
        return adPositionMapper.updateAdPositionById(adPosition);
    }

    @Override
    public AdPosition selectAdPositionById(Long id)
    {
        return adPositionMapper.selectAdPositionById(id);
    }

    public List<AdPosition> selectAdPositionByPage(Page page)
    {
        return adPositionMapper.selectAdPositionByPage(page);
    }

    public Integer selectAdPositionByPageCount(Page page)
    {
        return adPositionMapper.selectAdPositionByPageCount(page);
    }

}
