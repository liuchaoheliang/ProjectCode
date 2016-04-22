package com.froad.fft.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.froad.fft.persistent.api.SmsContentMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.SmsContent;
import com.froad.fft.service.SmsContentService;

@Service("smsContentServiceImpl")
public class SmsContentServiceImpl implements SmsContentService {

	private static Logger logger = Logger.getLogger(SmsContentServiceImpl.class);
	
	@Resource
	private SmsContentMapper smsContentMapper;
	
	@Override
	public Long saveSmsContent(SmsContent smsContent) {
		return smsContentMapper.saveSmsContent(smsContent);
	}

	
	@Override
	@Cacheable("smsContent")
	public SmsContent selectSmsContentToSendMsg(SmsContent smsContent) {
		return smsContentMapper.selectSmsContentToSendMsg(smsContent);
	}

	@Override
	public Page findSmsContentByPage(Page page) {
		if(page == null){
			page=new Page();
		}
		page.setResultsContent(smsContentMapper.findSmsContentByPage(page));
		page.setTotalCount(smsContentMapper.findSmsContentByPageCount(page));
		return page;
	}


	@Override
	public SmsContent selectSmsContentById(Long id) {
		if(id == null){
			logger.error("查询数据id为空");
			return null;
		}
		return smsContentMapper.selectSmsContentById(id);
	}


	@Override
	public Boolean updateSmsContentById(SmsContent smsContent) {
		if(smsContent.getId() == null){
			logger.error("更新对象缺少必要Id字段值");
			return false;
		}
		return smsContentMapper.updateSmsContentById(smsContent);
	}
	
}
