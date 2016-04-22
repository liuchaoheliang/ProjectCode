package com.froad.fft.persistent.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.LogMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Log;

public class LogMapperImpl implements LogMapper {
	
	@Resource
	private LogMapper logMapper;

	@Override
	public Long saveLog(Log log) {
		logMapper.saveLog(log);
		return log.getId();
	}

	@Override
	public Boolean updateLogById(Log log) {
		return logMapper.updateLogById(log);
	}

	@Override
	public Log selectLogById(Long id) {
		return logMapper.selectLogById(id);
	}

	@Override
	public List<Log> findLogByPage(Page page) {
		return logMapper.findLogByPage(page);
	}

	@Override
	public Integer findLogByPageCount(Page page) {
		return logMapper.findLogByPageCount(page);
	}

}
