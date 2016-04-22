package com.froad.fft.persistent.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.GivePointRuleMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.GivePointRule;

public class GivePointRuleMapperImpl implements GivePointRuleMapper
{

    @Resource
    private GivePointRuleMapper givePointRuleMapper;

    @Override
    public Long saveGivePointRule(GivePointRule temp)
    {
        givePointRuleMapper.saveGivePointRule(temp);
        return temp.getId();
    }

    @Override
    public Boolean updateGivePointRuleById(GivePointRule temp)
    {
        return givePointRuleMapper.updateGivePointRuleById(temp);
    }

    @Override
    public GivePointRule selectGivePointRuleById(Long id)
    {
        return givePointRuleMapper.selectGivePointRuleById(id);
    }

    @Override
    public List<GivePointRule> selectGivePointRuleByPage(Page page)
    {
        return givePointRuleMapper.selectGivePointRuleByPage(page);
    }

    @Override
    public Integer selectGivePointRuleByPageCount(Page page)
    {
        return givePointRuleMapper.selectGivePointRuleByPageCount(page);
    }

}
