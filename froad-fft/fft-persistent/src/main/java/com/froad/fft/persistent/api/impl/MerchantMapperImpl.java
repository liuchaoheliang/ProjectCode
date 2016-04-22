package com.froad.fft.persistent.api.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.MerchantMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Merchant;

public class MerchantMapperImpl implements MerchantMapper
{

    @Resource
    private MerchantMapper merchantMapper;

    @Override
    public Long saveMerchant(Merchant merchant)
    {
        merchantMapper.saveMerchant(merchant);
        return merchant.getId();
    }

    @Override
    public Boolean updateMerchantById(Merchant merchant)
    {
        return merchantMapper.updateMerchantById(merchant);
    }

    @Override
    public Merchant selectMerchantById(Long id)
    {
        return merchantMapper.selectMerchantById(id);
    }

    @Override
    public List<Merchant> selectMerchantByPage(Page page)
    {
        return merchantMapper.selectMerchantByPage(page);
    }

    @Override
    public Integer selectMerchantByPageCount(Page page)
    {
        return merchantMapper.selectMerchantByPageCount(page);
    }

    @Override
    public List<Merchant> selectMerchantByClientId(Long clientId)
    {
        return merchantMapper.selectMerchantByClientId(clientId);
    }

    @Override
    public List<Merchant> selectAllOutletMerchant( Map<String,Object> paraMap)
    {
        return merchantMapper.selectAllOutletMerchant(paraMap);
    }
}
