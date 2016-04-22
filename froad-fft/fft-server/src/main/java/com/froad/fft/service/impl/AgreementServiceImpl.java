package com.froad.fft.service.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.bean.page.Page;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.froad.fft.persistent.api.AgreementMapper;
import com.froad.fft.persistent.entity.Agreement;
import com.froad.fft.service.AgreementService;

@Service("agreementServiceImpl")
public class AgreementServiceImpl implements AgreementService
{

    private static Logger logger = Logger.getLogger(AgreementServiceImpl.class);

    @Resource
    private AgreementMapper agreementMapper;

    @Override
    public Long saveAgreement(Agreement agreement)
    {
        return agreementMapper.saveAgreement(agreement);
    }

    @Override
    public Boolean updateAgreementById(Agreement agreement)
    {
        if (agreement.getId() == null)
        {
            logger.error("更新对象缺少必要Id字段值");
            return false;
        }
        return agreementMapper.updateAgreementById(agreement);
    }

    @Override
    @Cacheable("agreement")
    public Agreement selectAgreementById(Long id)
    {
        if (id == null)
        {
            logger.error("查询数据id为空");
            return null;
        }
        return agreementMapper.selectAgreementById(id);
    }

    public Page selectAgreementByPage(Page page)
    {
        if (page == null)
        {
            page = new Page();
        }
        page.setResultsContent(agreementMapper.selectAgreementByPage(page));
        page.setTotalCount(agreementMapper.selectAgreementByPageCount(page));
        return page;
    }
}
