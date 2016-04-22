package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.FundsChannelMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.FundsChannel;

import java.util.List;

public class FundsChannelMapperImpl implements FundsChannelMapper
{

    @Resource
    private FundsChannelMapper fundsChannelMapper;

    @Override
    public Long saveFundsChannel(FundsChannel fundsChannel)
    {
        fundsChannelMapper.saveFundsChannel(fundsChannel);
        return fundsChannel.getId();
    }

    @Override
    public Boolean updateFundsChannelById(FundsChannel fundsChannel)
    {
        return fundsChannelMapper.updateFundsChannelById(fundsChannel);
    }

    @Override
    public FundsChannel selectFundsChannelById(Long id)
    {
        return fundsChannelMapper.selectFundsChannelById(id);
    }

    @Override
    public List<FundsChannel> selectFundsChannelByPage(Page page)
    {
        return fundsChannelMapper.selectFundsChannelByPage(page);
    }

    @Override
    public Integer selectFundsChannelByPageCount(Page page)
    {
        return fundsChannelMapper.selectFundsChannelByPageCount(page);
    }

	@Override
	public List<FundsChannel> selectByClientId(Long clientId) {
		return fundsChannelMapper.selectByClientId(clientId);
	}

}
