package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.RegisterGivePointRuleMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.RegisterGivePointRule;

import java.util.List;

public class RegisterGivePointRuleMapperImpl implements RegisterGivePointRuleMapper
{

    @Resource
    private RegisterGivePointRuleMapper registerGivePointRuleMapper;

    @Override
    public Long saveRegisterGivePointRule(RegisterGivePointRule registerGivePointRule)
    {
        registerGivePointRuleMapper.saveRegisterGivePointRule(registerGivePointRule);
        return registerGivePointRule.getId();
    }

    @Override
    public Boolean updateRegisterGivePointRuleById(RegisterGivePointRule registerGivePointRule)
    {
        return registerGivePointRuleMapper.updateRegisterGivePointRuleById(registerGivePointRule);
    }

    @Override
    public RegisterGivePointRule selectRegisterGivePointRuleById(Long id)
    {
        return registerGivePointRuleMapper.selectRegisterGivePointRuleById(id);
    }

    public RegisterGivePointRule selectRegisterGivePointRuleByClientId(Long id)
    {
        return registerGivePointRuleMapper.selectRegisterGivePointRuleByClientId(id);
    }

    public List<RegisterGivePointRule> selectRegisterGivePointRuleByPage(Page page)
    {
        return registerGivePointRuleMapper.selectRegisterGivePointRuleByPage(page);
    }

    public Integer selectRegisterGivePointRuleByPageCount(Page page)
    {
        return registerGivePointRuleMapper.selectRegisterGivePointRuleByPageCount(page);
    }

}
