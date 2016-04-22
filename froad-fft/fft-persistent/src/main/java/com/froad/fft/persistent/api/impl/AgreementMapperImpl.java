package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.AgreementMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Agreement;

import java.util.List;

public class AgreementMapperImpl implements AgreementMapper
{

    @Resource
    private AgreementMapper agreementMapper;

    @Override
    public Long saveAgreement(Agreement agreement)
    {
        agreementMapper.saveAgreement(agreement);
        return agreement.getId();
    }

    @Override
    public Boolean updateAgreementById(Agreement agreement)
    {
        return agreementMapper.updateAgreementById(agreement);
    }

    @Override
    public Agreement selectAgreementById(Long id)
    {
        return agreementMapper.selectAgreementById(id);
    }

    public List<Agreement> selectAgreementByPage(Page page)
    {
        return agreementMapper.selectAgreementByPage(page);
    }

    public Integer selectAgreementByPageCount(Page page)
    {
        return agreementMapper.selectAgreementByPageCount(page);
    }


}
