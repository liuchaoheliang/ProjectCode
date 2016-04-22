package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.SmsLogMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.SmsLog;

import java.util.List;

public class SmsLogMapperImpl implements SmsLogMapper {

	@Resource
	private SmsLogMapper smsLogMapper;
	
	@Override
	public Long saveSmsLog(SmsLog smsLog) {
		smsLogMapper.saveSmsLog(smsLog);
		return smsLog.getId();
	}

	@Override
	public Boolean updateSmsLog(SmsLog smsLog) {
		return smsLogMapper.updateSmsLog(smsLog);
	}

    public SmsLog selectSmsLogById(Long id)
    {
        return smsLogMapper.selectSmsLogById(id);
    }

    public List<SmsLog> selectSmsLogByPage(Page page)
    {
        return smsLogMapper.selectSmsLogByPage(page);
    }


    public Integer selectSmsLogByPageCount(Page page)
    {
       return smsLogMapper.selectSmsLogByPageCount(page);
    }
}
