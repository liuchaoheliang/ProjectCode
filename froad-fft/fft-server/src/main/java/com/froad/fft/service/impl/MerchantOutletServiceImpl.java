package com.froad.fft.service.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.common.enums.TransType;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.OutletStatistic;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.froad.fft.persistent.api.MerchantOutletMapper;
import com.froad.fft.persistent.entity.MerchantOutlet;
import com.froad.fft.service.MerchantOutletService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("merchantOutletServiceImpl")
public class MerchantOutletServiceImpl implements MerchantOutletService
{

    private static Logger logger = Logger.getLogger(MerchantOutletServiceImpl.class);

    @Resource
    private MerchantOutletMapper merchantOutletMapper;

    @Override
    public Long saveMerchantOutlet(MerchantOutlet merchantOutlet)
    {
        return merchantOutletMapper.saveMerchantOutlet(merchantOutlet);
    }

    @Override
    public Boolean updateMerchantOutletById(MerchantOutlet merchantOutlet)
    {
        if (merchantOutlet.getId() == null)
        {
            logger.error("更新对象缺少必要Id字段值");
            return false;
        }
        return merchantOutletMapper.updateMerchantOutletById(merchantOutlet);
    }

    @Override
    public MerchantOutlet selectMerchantOutletById(Long id)
    {
        if (id == null)
        {
            logger.error("查询数据id为空");
            return null;
        }
        return merchantOutletMapper.selectMerchantOutletById(id);
    }

    public Page findMerchantOutletByPage(Page page)
    {
        if (page == null)
        {
            page = new Page();
        }
        page.setResultsContent(merchantOutletMapper.selectMerchantOutletByPage(page));
        page.setTotalCount(merchantOutletMapper.selectMerchantOutletByPageCount(page));
        return page;
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
