package com.froad.fft.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.froad.fft.persistent.api.ReturnSaleDetailMapper;
import com.froad.fft.persistent.entity.ReturnSaleDetail;
import com.froad.fft.service.ReturnSaleDetailService;

@Service("returnSaleDetailServiceImpl")
public class ReturnSaleDetailServiceImpl implements ReturnSaleDetailService {
	
	private static Logger logger = Logger.getLogger(ReturnSaleDetailServiceImpl.class);
	
	@Resource
	ReturnSaleDetailMapper returnSaleDetailMapper;
	@Override
	public ReturnSaleDetail getReturnSaleDetailById(Long id) {
		if(id == null){
			logger.info("id为空无法查询");
			return null;
		}
		return returnSaleDetailMapper.getReturnSaleDetailById(id);
	}
	@Override
	public ReturnSaleDetail getReturnSaleDetailByRsId(Long rsid) {
		if(rsid == null){
			logger.info("rsid为空无法查询");
			return null;
		}
		return returnSaleDetailMapper.getReturnSaleDetailByRsId(rsid);
	}
	

	@Override
	public Long saveReturnSaleDetail(ReturnSaleDetail returnSaleDetail) {
		if(returnSaleDetail == null){
			logger.info("传入参数为空");
			return null;
		}
		return returnSaleDetailMapper.saveReturnSaleDetail(returnSaleDetail);
	}
	
	
	@Override
	public List<ReturnSaleDetail> selectByConditions(
			ReturnSaleDetail returnSaleDetail) {
		return returnSaleDetailMapper.selectByConditions(returnSaleDetail);
	}

}
