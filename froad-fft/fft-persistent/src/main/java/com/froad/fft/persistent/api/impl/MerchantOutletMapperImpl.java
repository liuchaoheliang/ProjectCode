package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.MerchantOutletMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.MerchantOutlet;
import com.froad.fft.persistent.bean.OutletStatistic;

import java.util.List;
import java.util.Map;

public class MerchantOutletMapperImpl implements MerchantOutletMapper
{

    @Resource
    private MerchantOutletMapper merchantOutletMapper;

    @Override
    public Long saveMerchantOutlet(MerchantOutlet merchantOutlet)
    {
        merchantOutletMapper.saveMerchantOutlet(merchantOutlet);
        return merchantOutlet.getId();
    }

    @Override
    public Boolean updateMerchantOutletById(MerchantOutlet merchantOutlet)
    {
        return merchantOutletMapper.updateMerchantOutletById(merchantOutlet);
    }

    @Override
    public MerchantOutlet selectMerchantOutletById(Long id)
    {
        return merchantOutletMapper.selectMerchantOutletById(id);
    }

    @Override
    public List<MerchantOutlet> selectMerchantOutletByPage(Page page)
    {
        return merchantOutletMapper.selectMerchantOutletByPage(page);
    }

    @Override
    public Integer selectMerchantOutletByPageCount(Page page)
    {
        return merchantOutletMapper.selectMerchantOutletByPageCount(page);
    }

    public List<MerchantOutlet> selectAllUnboundtoPresellDeliveryOutlet()
    {
        return merchantOutletMapper.selectAllUnboundtoPresellDeliveryOutlet();
    }

    @Override
    public List<MerchantOutlet> selectByConditions(MerchantOutlet merchantOutlet)
    {
        return merchantOutletMapper.selectByConditions(merchantOutlet);
    }


}
