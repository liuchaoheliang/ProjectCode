package com.froad.fft.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.persistent.bean.page.Page;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.AdMapper;
import com.froad.fft.persistent.entity.Ad;
import com.froad.fft.service.AdService;

@Service("adServiceImpl")
public class AdServiceImpl implements AdService
{

    private static Logger logger = Logger.getLogger(AdServiceImpl.class);

    @Resource
    private AdMapper adMapper;

    @Override
    public Long saveAd(Ad ad)
    {
        return adMapper.saveAd(ad);
    }

    @Override
    public Boolean updateAdById(Ad ad)
    {
        if (ad.getId() == null)
        {
            logger.error("更新对象缺少必要Id字段值");
            return false;
        }
        return adMapper.updateAdById(ad);
    }

    @Override
    public Ad selectAdById(Long id)
    {
        if (id == null)
        {
            logger.error("查询数据id为空");
            return null;
        }
        return adMapper.selectAdById(id);
    }

    public Page findAdByPage(Page page)
    {
        if (page == null)
        {
            page = new Page();
        }
        page.setResultsContent(adMapper.selectAdByPage(page));
        page.setTotalCount(adMapper.selectAdByPageCount(page));
        return page;
    }

	@Override
	public List<Ad> findEnableAdByAdPositionId(Long adPositionId) {
		
		return adMapper.selectEnableAdByAdPositionId(adPositionId);
	}

}
