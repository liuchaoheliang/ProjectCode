package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.NavigationMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Navigation;

import java.util.List;

public class NavigationMapperImpl implements NavigationMapper
{

    @Resource
    private NavigationMapper navigationMapper;

    @Override
    public Long saveNavigation(Navigation navigation)
    {
        navigationMapper.saveNavigation(navigation);
        return navigation.getId();
    }

    @Override
    public Boolean updateNavigationById(Navigation navigation)
    {
        return navigationMapper.updateNavigationById(navigation);
    }

    @Override
    public Navigation selectNavigationById(Long id)
    {
        return navigationMapper.selectNavigationById(id);
    }

    public List<Navigation> selectNavigationByPage(Page page)
    {
        return navigationMapper.selectNavigationByPage(page);
    }

    public Integer selectNavigationByPageCount(Page page)
    {
        return navigationMapper.selectNavigationByPageCount(page);
    }


}
