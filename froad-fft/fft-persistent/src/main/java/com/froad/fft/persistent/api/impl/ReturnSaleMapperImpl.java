
	 /**
  * 文件名：ReturnSaleMapperImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年4月1日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.persistent.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.ReturnSaleMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.ReturnSale;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月1日 下午5:52:41 
 */
public class ReturnSaleMapperImpl implements ReturnSaleMapper {
	
	@Resource
	private ReturnSaleMapper returnSaleMapper;
	@Override
	public Long saveReturnSale(ReturnSale returnSale) {
		returnSaleMapper.saveReturnSale(returnSale);
		return returnSale.getId();
	}
	@Override
	public List<ReturnSale> findReturnSaleByPage(Page<ReturnSale> page) {
		return returnSaleMapper.findReturnSaleByPage(page);
	}
	@Override
	public ReturnSale getReturnSaleById(Long id) {
		return returnSaleMapper.getReturnSaleById(id);
	}
	@Override
	public Integer findReturnSaleByPageCount(Page<ReturnSale> page) {
		return returnSaleMapper.findReturnSaleByPageCount(page);
	}
	
	@Override
	public List<ReturnSale> selectByConditions(ReturnSale returnSale) {
		return returnSaleMapper.selectByConditions(returnSale);
	}

}
