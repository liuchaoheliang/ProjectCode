package com.froad.fft.persistent.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.AdMapper;
import com.froad.fft.persistent.api.SysUserMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Ad;

public class AdMapperImpl implements AdMapper
{

    @Resource
    private AdMapper adMapper;

    @Override
    public Long saveAd(Ad ad)
    {
        adMapper.saveAd(ad);
        return ad.getId();
    }

    @Override
    public Boolean updateAdById(Ad ad)
    {
        return adMapper.updateAdById(ad);
    }

    @Override
    public Ad selectAdById(Long id)
    {
        return adMapper.selectAdById(id);
    }

    public List<Ad> selectAdByPage(Page page)
    {
        return adMapper.selectAdByPage(page);
    }

    public Integer selectAdByPageCount(Page page)
    {
        return adMapper.selectAdByPageCount(page);
    }

	@Override
	public List<Ad> selectEnableAdByAdPositionId(Long adPositionId) {
		// TODO Auto-generated method stub
		return adMapper.selectEnableAdByAdPositionId(adPositionId);
	}

}
