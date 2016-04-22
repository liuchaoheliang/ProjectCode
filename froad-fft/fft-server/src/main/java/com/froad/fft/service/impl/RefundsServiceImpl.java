
	 /**
  * 文件名：RefundsServiceImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年3月27日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.froad.fft.persistent.api.RefundsMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Refunds;
import com.froad.fft.service.RefundsService;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月27日 下午5:56:02 
 */
@Service("refundsServiceImpl")
public class RefundsServiceImpl implements RefundsService {
	
	private static Logger logger = Logger.getLogger(RefundsServiceImpl.class);

	@Resource
	private RefundsMapper refundsMapper;
	
	@Override
	public Long saveRefunds(Refunds refunds) {
		if(refunds==null){
			logger.info("传入参数为空");
			return null;
		}
		return refundsMapper.saveRefunds(refunds);
	}

	@Override
	public Boolean updateRefundsById(Refunds refunds) {
		if(refunds==null || refunds.getId()==null){
			logger.info("传入参数有误");
			return null;
		}
		return refundsMapper.updateRefundsById(refunds);
	}

	@Override
	public Refunds findRefundsById(Long id) {
		if(id==null){
			logger.info("传入参数为空");
			return null;
		}
		return refundsMapper.selectRefundsById(id);
	}

	@Override
	public Page findRefundsByPage(Page page) {
		if(page == null){
			page=new Page();
		}
		page.setResultsContent(refundsMapper.selectRefundsByPage(page));
		page.setTotalCount(refundsMapper.selectRefundsByPageCount(page));		
		return page;
	}

}
