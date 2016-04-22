package com.froad.fft.service;

import com.froad.fft.bean.page.PageDto;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Log;

public interface LogService {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>保存Log并返回数据主键（如果主键为null则插入失败）</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月6日 下午7:11:04 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Long saveLog(Log log);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键更新数据</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月6日 下午7:22:17 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Boolean updateLogById(Log log);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键查询对象</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月6日 下午7:31:24 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Log selectLogById(Long id);
	
	/**
	 * @author larry
	 * <p>
	 * 分页查询日志
	 * </p>
	 * @param page
	 * @return
	 */
	public Page findLogByPage(Page page);
}
