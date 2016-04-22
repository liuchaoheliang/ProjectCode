package com.froad.fft.service.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.bean.page.Page;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.RegisterGivePointRuleMapper;
import com.froad.fft.persistent.entity.RegisterGivePointRule;
import com.froad.fft.service.RegisterGivePointRuleService;

@Service("registerGivePointRuleServiceImpl")
public class RegisterGivePointRuleServiceImpl implements RegisterGivePointRuleService
{

    private static Logger logger = Logger.getLogger(RegisterGivePointRuleServiceImpl.class);

    @Resource
    private RegisterGivePointRuleMapper registerGivePointRuleMapper;

    @Override
    public Long saveRegisterGivePointRule(RegisterGivePointRule registerGivePointRule)
    {
        return registerGivePointRuleMapper.saveRegisterGivePointRule(registerGivePointRule);
    }

    @Override
    public Boolean updateRegisterGivePointRuleById(RegisterGivePointRule registerGivePointRule)
    {
        if (registerGivePointRule.getId() == null)
        {
            logger.error("更新对象缺少必要Id字段值");
            return false;
        }
        return registerGivePointRuleMapper.updateRegisterGivePointRuleById(registerGivePointRule);
    }

    @Override
    public RegisterGivePointRule selectRegisterGivePointRuleById(Long id)
    {
        if (id == null)
        {
            logger.error("查询数据id为空");
            return null;
        }
        return registerGivePointRuleMapper.selectRegisterGivePointRuleById(id);
    }

    @Override
    public RegisterGivePointRule selectRegisterGivePointRuleByClientId(Long id)
    {
        if (id == null)
        {
            logger.error("查询数据id为空");
            return null;
        }
        return registerGivePointRuleMapper.selectRegisterGivePointRuleByClientId(id);
    }

    public Page findRegisterGivePointRuleByPage(Page page)
    {
        if (page == null)
        {
            page = new Page();
        }
        page.setResultsContent(registerGivePointRuleMapper.selectRegisterGivePointRuleByPage(page));
        page.setTotalCount(registerGivePointRuleMapper.selectRegisterGivePointRuleByPageCount(page));
        return page;
    }
}
