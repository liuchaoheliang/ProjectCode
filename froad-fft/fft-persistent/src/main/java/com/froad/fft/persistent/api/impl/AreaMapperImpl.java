package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.AreaMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Area;

import java.util.List;

public class AreaMapperImpl implements AreaMapper
{

    @Resource
    private AreaMapper areaMapper;

    @Override
    public Long saveArea(Area area)
    {
        areaMapper.saveArea(area);
        return area.getId();
    }

    @Override
    public Boolean updateAreaById(Area area)
    {
        return areaMapper.updateAreaById(area);
    }

    @Override
    public Area selectAreaById(Long id)
    {
        return areaMapper.selectAreaById(id);
    }

    public List<Area> selectAreaByPage(Page page)
    {
        return areaMapper.selectAreaByPage(page);
    }

    public Integer selectAreaByPageCount(Page page)
    {
        return areaMapper.selectAreaByPageCount(page);
    }

}
