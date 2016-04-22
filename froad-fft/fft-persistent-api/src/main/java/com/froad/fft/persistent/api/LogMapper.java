package com.froad.fft.persistent.api;

import java.util.List;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Log;

public interface LogMapper {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>保存Log</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月6日 下午7:09:22 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Long saveLog(Log log);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键更新数据</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月6日 下午7:20:51 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Boolean updateLogById(Log log);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键查询对象</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月6日 下午7:30:43 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Log selectLogById(Long id);

	/**
	 * @author larry
	 * @param page
	 * <p>分页查询日志</p>
	 * @return
	 */
	public List<Log> findLogByPage(Page page);
	public Integer findLogByPageCount(Page page);
}
