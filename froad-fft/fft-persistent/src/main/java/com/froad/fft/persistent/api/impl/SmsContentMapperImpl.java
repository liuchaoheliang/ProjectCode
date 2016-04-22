package com.froad.fft.persistent.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.SmsContentMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.SmsContent;

public class SmsContentMapperImpl implements SmsContentMapper {
	
	@Resource
	private SmsContentMapper smsContentMapper;

	@Override
	public SmsContent selectSmsContentToSendMsg(SmsContent smsContent) {
		return smsContentMapper.selectSmsContentToSendMsg(smsContent);
	}

	@Override
	public List<SmsContent> findSmsContentByPage(Page page) {
		return smsContentMapper.findSmsContentByPage(page);
	}

	@Override
	public Integer findSmsContentByPageCount(Page page) {
		return smsContentMapper.findSmsContentByPageCount(page);
	}

	@Override
	public Long saveSmsContent(SmsContent smsContent) {
		return smsContentMapper.saveSmsContent(smsContent);
	}

	@Override
	public SmsContent selectSmsContentById(Long id) {
		return smsContentMapper.selectSmsContentById(id);
	}

	@Override
	public Boolean updateSmsContentById(SmsContent smsContent) {
		return smsContentMapper.updateSmsContentById(smsContent);
	}

}
