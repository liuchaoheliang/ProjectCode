package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.TransGoodsAttributeDao;
import com.froad.CB.po.TransGoodsAttribute;
import com.froad.CB.service.TransGoodsAttributeService;


@WebService(endpointInterface="com.froad.CB.service.TransGoodsAttributeService")
public class TransGoodsAttributeServiceImpl implements TransGoodsAttributeService{

	private static final Logger logger=Logger.getLogger(TransGoodsAttributeServiceImpl.class);
	
	private TransGoodsAttributeDao transGoodsAttributeDao;
	
	@Override
	public boolean add(TransGoodsAttribute attr) {
		if(attr==null){
			logger.error("参数为空，添加失败");
			return false;
		}
		try {
			transGoodsAttributeDao.add(attr);
			return true;
		} catch (Exception e) {
			logger.error("添加操作异常",e);
			return false;
		}
	}

	@Override
	public List<TransGoodsAttribute> getByTransId(String transId) {
		if(transId==null||"".equals(transId)){
			logger.error("交易编号为空，查询失败");
			return null;
		}
		return transGoodsAttributeDao.getByTransId(transId);
	}

	public void setTransGoodsAttributeDao(
			TransGoodsAttributeDao transGoodsAttributeDao) {
		this.transGoodsAttributeDao = transGoodsAttributeDao;
	}

}
