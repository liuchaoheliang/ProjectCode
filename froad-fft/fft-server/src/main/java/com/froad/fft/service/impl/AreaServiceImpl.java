package com.froad.fft.service.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.bean.page.Page;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.froad.fft.persistent.api.AreaMapper;
import com.froad.fft.persistent.entity.Area;
import com.froad.fft.service.AreaService;

@Service("areaServiceImpl")
public class AreaServiceImpl implements AreaService
{

    private static Logger logger = Logger.getLogger(AreaServiceImpl.class);

    @Resource
    private AreaMapper areaMapper;

    @Override
    public Long saveArea(Area area)
    {
        return areaMapper.saveArea(area);
    }

    @Override
    public Boolean updateAreaById(Area area)
    {
        if (area.getId() == null)
        {
            logger.error("更新对象缺少必要Id字段值");
            return false;
        }
        return areaMapper.updateAreaById(area);
    }

    @Override
    public Area selectAreaById(Long id)
    {
        if (id == null)
        {
            logger.error("查询数据id为空");
            return null;
        }
        return areaMapper.selectAreaById(id);
    }

    public Page findAreaByPage(Page page)
    {
        if (page == null)
        {
            page = new Page();
        }
        page.setResultsContent(areaMapper.selectAreaByPage(page));
        page.setTotalCount(areaMapper.selectAreaByPageCount(page));
        return page;
    }


}
