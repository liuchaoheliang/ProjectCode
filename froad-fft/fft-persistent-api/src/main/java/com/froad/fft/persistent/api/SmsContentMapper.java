package com.froad.fft.persistent.api;

import java.util.List;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.SmsContent;

public interface SmsContentMapper {
	
	public Long saveSmsContent(SmsContent smsContent);

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过客户端号，短信类型查询短信模版用于发送短信</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月23日 下午5:26:35 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public SmsContent selectSmsContentToSendMsg(SmsContent smsContent);

	/**
	 * @author larry
	 * @param page
	 * <p>分页查询短信模板</p>
	 * @return
	 */
	public List<SmsContent> findSmsContentByPage(Page page);
	/**
	 * @author larry
	 * 分页查询记录条数
	 * @param page
	 * @return
	 */
	public Integer findSmsContentByPageCount(Page page);

	/**
	 * 根据ID查询短信模板
	 * @param id
	 * @return
	 */
	public SmsContent selectSmsContentById(Long id);

	/**
	 * @author larry
	 * @param smsContent
	 * <p>更新短信模板</p>
	 * @return
	 */
	public Boolean updateSmsContentById(SmsContent smsContent);
}
