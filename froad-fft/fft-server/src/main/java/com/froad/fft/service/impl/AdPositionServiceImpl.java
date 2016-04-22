package com.froad.fft.service.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.bean.page.Page;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.AdPositionMapper;
import com.froad.fft.persistent.entity.AdPosition;
import com.froad.fft.service.AdPositionService;

@Service("adPositionServiceImpl")
public class AdPositionServiceImpl implements AdPositionService
{

    private static Logger logger = Logger.getLogger(AdPositionServiceImpl.class);

    @Resource
    private AdPositionMapper adPositionMapper;

    @Override
    public Long saveAdPosition(AdPosition adPosition)
    {
        return adPositionMapper.saveAdPosition(adPosition);
    }

    @Override
    public Boolean updateAdPositionById(AdPosition adPosition)
    {
        if (adPosition.getId() == null)
        {
            logger.error("更新对象缺少必要Id字段值");
            return false;
        }
        return adPositionMapper.updateAdPositionById(adPosition);
    }

    @Override
    public AdPosition selectAdPositionById(Long id)
    {
        if (id == null)
        {
            logger.error("查询数据id为空");
            return null;
        }
        return adPositionMapper.selectAdPositionById(id);
    }

    public Page findAdPositionByPage(Page page)
    {
        if (page == null)
        {
            page = new Page();
        }
        page.setResultsContent(adPositionMapper.selectAdPositionByPage(page));
        page.setTotalCount(adPositionMapper.selectAdPositionByPageCount(page));
        return page;
    }
}
