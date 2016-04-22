package com.froad.fft.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.MerchantMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Merchant;
import com.froad.fft.service.MerchantService;

import java.util.List;
import java.util.Map;

@Service("merchantServiceImpl")
public class MerchantServiceImpl implements MerchantService
{

    private static Logger logger = Logger.getLogger(MerchantServiceImpl.class);

    @Resource
    private MerchantMapper merchantMapper;

    @Override
    public Long saveMerchant(Merchant merchant)
    {
        if (merchant == null)
        {
            logger.info("插入参数为空");
            return null;
        }
        return merchantMapper.saveMerchant(merchant);
    }

    @Override
    public Boolean updateMerchantById(Merchant merchant)
    {
        if (merchant.getId() == null)
        {
            logger.error("更新对象缺少必要Id字段值");
            return false;
        }
        return merchantMapper.updateMerchantById(merchant);
    }

    @Override
    public Merchant selectMerchantById(Long id)
    {
        if (id == null)
        {
            logger.error("查询数据id为空");
            return null;
        }
        return merchantMapper.selectMerchantById(id);
    }

    @Override
    public Page findMerchantByPage(Page page)
    {
        if (page == null)
        {
            page = new Page();
        }
        page.setResultsContent(merchantMapper.selectMerchantByPage(page));
        page.setTotalCount(merchantMapper.selectMerchantByPageCount(page));
        return page;
    }

    public List<Merchant> findAllOutletMerchant(Map<String, Object> paraMap)
    {
        return merchantMapper.selectAllOutletMerchant(paraMap);
    }


}
