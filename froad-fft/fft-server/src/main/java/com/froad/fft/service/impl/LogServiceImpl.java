package com.froad.fft.service.impl;

import java.net.MalformedURLException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.caucho.hessian.client.HessianProxyFactory;
import com.froad.fft.api.service.CacheExportService;
import com.froad.fft.api.service.FileExportService;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.persistent.api.LogMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Log;
import com.froad.fft.service.LogService;

@Service("logServiceImpl")
public class LogServiceImpl implements LogService {

	private static Logger logger = Logger.getLogger(LogServiceImpl.class);
	
	@Resource
	private LogMapper logMapper;
	
	@Override
	public Long saveLog(Log log) {
		return logMapper.saveLog(log);
	}

	@Override
	public Boolean updateLogById(Log log) {
		if(log.getId() == null){
			logger.error("更新对象缺少必要Id字段值");
			return false;
		}
		return logMapper.updateLogById(log);
	}

	@Override
	public Log selectLogById(Long id) {
		if(id == null){
			logger.error("查询数据id为空");
			return null;
		}
		return logMapper.selectLogById(id);
	}

	@Override
	public Page findLogByPage(Page page) {
		if(page == null){
			page=new Page();
		}
		page.setResultsContent(logMapper.findLogByPage(page));
		page.setTotalCount(logMapper.findLogByPageCount(page));
		return page;
	}

}
