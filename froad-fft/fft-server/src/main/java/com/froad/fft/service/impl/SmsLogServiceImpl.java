package com.froad.fft.service.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.bean.page.Page;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.SmsLogMapper;
import com.froad.fft.persistent.entity.SmsLog;
import com.froad.fft.service.SmsLogService;

@Service("smsLogServiceImpl")
public class SmsLogServiceImpl implements SmsLogService
{

    private static Logger logger = Logger.getLogger(SmsLogServiceImpl.class);

    @Resource
    private SmsLogMapper smsLogMapper;

    @Override
    public Long saveSmsLog(SmsLog smsLog)
    {
        return smsLogMapper.saveSmsLog(smsLog);
    }

    @Override
    public Boolean updateSmsLogStatusById(Long id)
    {
        SmsLog smsLog = new SmsLog(true);
        smsLog.setId(id);
        return smsLogMapper.updateSmsLog(smsLog);
    }

    public SmsLog selectSmsLogById(Long id)
    {
        return smsLogMapper.selectSmsLogById(id);
    }

    public Page findSmsLogByPage(Page page)
    {
        if (page == null)
        {
            page = new Page();
        }
        page.setResultsContent(smsLogMapper.selectSmsLogByPage(page));
        page.setTotalCount(smsLogMapper.selectSmsLogByPageCount(page));
        return page;
    }
}
