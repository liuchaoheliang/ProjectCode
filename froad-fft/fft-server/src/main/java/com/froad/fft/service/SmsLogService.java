package com.froad.fft.service;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.SmsLog;

public interface SmsLogService
{

    /**
     * *******************************************************
     * <p> 描述: *-- <b>保存SmsLog并返回数据主键（如果主键为null则插入失败）</b> --* </p>
     * <p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
     * <p> 时间: 2014年1月23日 下午2:35:38 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Long saveSmsLog(SmsLog smsLog);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>更新短信日志发送结果状态</b> --* </p>
     * <p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
     * <p> 时间: 2014年1月23日 下午3:33:46 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Boolean updateSmsLogStatusById(Long id);

    /**
     * 方法描述：根据ID查询对象
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 根据id获取短信
     * @time: 2014年4月11日 下午5:50:57
     */
    public SmsLog selectSmsLogById(Long id);

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    public Page findSmsLogByPage(Page page);
}
