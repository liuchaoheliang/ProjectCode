package com.froad.fft.service.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.bean.page.Page;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.NavigationMapper;
import com.froad.fft.persistent.entity.Navigation;
import com.froad.fft.service.NavigationService;

@Service("navigationServiceImpl")
public class NavigationServiceImpl implements NavigationService
{

    private static Logger logger = Logger.getLogger(NavigationServiceImpl.class);

    @Resource
    private NavigationMapper navigationMapper;

    @Override
    public Long saveNavigation(Navigation navigation)
    {
        return navigationMapper.saveNavigation(navigation);
    }

    @Override
    public Boolean updateNavigationById(Navigation navigation)
    {
        if (navigation.getId() == null)
        {
            logger.error("更新对象缺少必要Id字段值");
            return false;
        }
        return navigationMapper.updateNavigationById(navigation);
    }

    @Override
    public Navigation selectNavigationById(Long id)
    {
        if (id == null)
        {
            logger.error("查询数据id为空");
            return null;
        }
        return navigationMapper.selectNavigationById(id);
    }

    public Page findNavigationByPage(Page page)
    {

        if (page == null)
        {
            page = new Page();
        }
        page.setResultsContent(navigationMapper.selectNavigationByPage(page));
        page.setTotalCount(navigationMapper.selectNavigationByPageCount(page));
        return page;
    }

}
