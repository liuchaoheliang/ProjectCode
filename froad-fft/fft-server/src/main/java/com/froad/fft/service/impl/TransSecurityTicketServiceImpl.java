package com.froad.fft.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.TransSecurityTicketMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.TransSecurityTicket;
import com.froad.fft.service.TransSecurityTicketService;

@Service("transSecurityTicketServiceImpl")
public class TransSecurityTicketServiceImpl implements
		TransSecurityTicketService {

	private static Logger logger = Logger.getLogger(TransSecurityTicketServiceImpl.class);
	
	@Resource
	private TransSecurityTicketMapper transSecurityTicketMapper;
	
	@Override
	public Long saveTransSecurityTicket(TransSecurityTicket transSecurityTicket) {
		return transSecurityTicketMapper.saveTransSecurityTicket(transSecurityTicket);
	}

	@Override
	public Boolean updateTransSecurityTicketById(
			TransSecurityTicket transSecurityTicket) {
		if (transSecurityTicket.getId() == null) {
			logger.error("更新对象缺少必要Id字段值");
			return false;
		}
		return transSecurityTicketMapper.updateTransSecurityTicketById(transSecurityTicket);
	}

	@Override
	public TransSecurityTicket selectTransSecurityTicketById(Long id) {
		if (id == null) {
			logger.error("查询数据id为空");
			return null;
		}
		return transSecurityTicketMapper.selectTransSecurityTicketById(id);
	}

	

	@Override
	public TransSecurityTicket selectBySecurityNo(String SecurityNo) {
		if ("".equals(SecurityNo)) {
			logger.error("查询券号为空");
			return null;
		}
		return transSecurityTicketMapper.selectBySecurityNo(SecurityNo);
	}

	

	@Override
	public Page findByPage(Page page) {
		if (page == null) {
			page = new Page();
		}
		page.setResultsContent(transSecurityTicketMapper.selectByPage(page));
		page.setTotalCount(transSecurityTicketMapper.selectByPageCount(page));
		return page;
	}

	@Override
	public List<TransSecurityTicket> selectByCondition(TransSecurityTicket ticket) {
		return transSecurityTicketMapper.selectByCondition(ticket);
	}

}
