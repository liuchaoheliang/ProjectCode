
	 /**
  * 文件名：ReturnSaleDetailMapperImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年4月1日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.persistent.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.ReturnSaleDetailMapper;
import com.froad.fft.persistent.entity.ReturnSaleDetail;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月1日 下午5:54:34 
 */
public class ReturnSaleDetailMapperImpl implements ReturnSaleDetailMapper {

	@Resource
	private ReturnSaleDetailMapper returnSaleDetailMapper;
	
	@Override
	public Long saveReturnSaleDetail(ReturnSaleDetail returnSaleDetail) {		
		returnSaleDetailMapper.saveReturnSaleDetail(returnSaleDetail);
		return returnSaleDetail.getId();
	}

	@Override
	public ReturnSaleDetail getReturnSaleDetailById(Long id) {
		return returnSaleDetailMapper.getReturnSaleDetailById(id);
	}

	@Override
	public ReturnSaleDetail getReturnSaleDetailByRsId(Long rsid) {
		return returnSaleDetailMapper.getReturnSaleDetailByRsId(rsid);
	}

	
	@Override
	public List<ReturnSaleDetail> selectByConditions(
			ReturnSaleDetail returnSaleDetail) {

		return returnSaleDetailMapper.selectByConditions(returnSaleDetail);
	}

}
