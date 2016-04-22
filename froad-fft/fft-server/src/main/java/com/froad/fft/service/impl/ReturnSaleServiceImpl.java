package com.froad.fft.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.froad.fft.persistent.api.ReturnSaleMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.ReturnSale;
import com.froad.fft.service.ReturnSaleService;
import com.froad.fft.util.SerialNumberUtil;

@Service("returnSaleServiceImpl")
public class ReturnSaleServiceImpl implements ReturnSaleService {

	private static Logger logger = Logger.getLogger(ReturnSaleServiceImpl.class);
	
	@Resource
	ReturnSaleMapper returnSaleMapper;
	@Override
	public Page<ReturnSale> findReturnSaleByPage(Page<ReturnSale> page) {
		if(page==null){
			logger.info("page对象为空,无法查询");
		}
		page.setResultsContent(returnSaleMapper.findReturnSaleByPage(page));
		page.setTotalCount(returnSaleMapper.findReturnSaleByPageCount(page));
		return page;
	}

	@Override
	public ReturnSale getReturnSaleById(Long id) {
		if(id==null){
			logger.info("id为空,无法查询");
			return null;
		}
		return returnSaleMapper.getReturnSaleById(id);
	}

	

	@Override
	public Long saveReturnSale(ReturnSale returnSale) {
		returnSale.setSn(SerialNumberUtil.buildReturnSn());
		return returnSaleMapper.saveReturnSale(returnSale);
	}

	
	@Override
	public List<ReturnSale> selectByConditions(ReturnSale returnSale) {
		return returnSaleMapper.selectByConditions(returnSale);
	}

}
