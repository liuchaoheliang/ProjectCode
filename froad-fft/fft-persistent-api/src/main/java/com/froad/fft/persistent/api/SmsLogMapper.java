package com.froad.fft.persistent.api;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.SmsLog;

import java.util.List;

public interface SmsLogMapper {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>保存SmsLog</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月23日 下午2:34:06 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Long saveSmsLog(SmsLog smsLog);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键更新SmsLog</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月23日 下午3:38:10 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Boolean updateSmsLog(SmsLog smsLog);


    /**
     * 方法描述：根据ID查询对象
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: liuchao liuchao@f-road.com.cn
     * @time: 2014年2月20日 下午5:50:57
     */
    public SmsLog selectSmsLogById(Long id);

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    public List<SmsLog> selectSmsLogByPage(Page page);

    /**
     * 分页统计
     *
     * @param page
     * @return
     */
    public Integer selectSmsLogByPageCount(Page page);


}
