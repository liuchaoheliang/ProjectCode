package com.froad.fft.service.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.bean.page.Page;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.FundsChannelMapper;
import com.froad.fft.persistent.entity.FundsChannel;
import com.froad.fft.service.FundsChannelService;

@Service("fundsChannelServiceImpl")
public class FundsChannelServiceImpl implements FundsChannelService
{

    private static Logger logger = Logger.getLogger(FundsChannelServiceImpl.class);

    @Resource
    private FundsChannelMapper fundsChannelMapper;

    @Override
    public FundsChannel selectFundsChannelById(Long id)
    {
        if (id == null)
        {
            logger.error("查询数据id为空");
            return null;
        }
        return fundsChannelMapper.selectFundsChannelById(id);
    }

    @Override
    public Long saveFundsChannel(FundsChannel merchantOutlet)
    {
        return fundsChannelMapper.saveFundsChannel(merchantOutlet);
    }

    @Override
    public Boolean updateFundsChannelById(FundsChannel merchantOutlet)
    {
        if (merchantOutlet.getId() == null)
        {
            logger.error("更新对象缺少必要Id字段值");
            return false;
        }
        return fundsChannelMapper.updateFundsChannelById(merchantOutlet);
    }

    public Page findFundsChannelByPage(Page page)
    {
        if (page == null)
        {
            page = new Page();
        }
        page.setResultsContent(fundsChannelMapper.selectFundsChannelByPage(page));
        page.setTotalCount(fundsChannelMapper.selectFundsChannelByPageCount(page));
        return page;
    }


}
