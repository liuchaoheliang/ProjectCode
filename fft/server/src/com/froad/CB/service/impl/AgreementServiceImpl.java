package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.AgreementDao;
import com.froad.CB.po.Agreement;
import com.froad.CB.service.AgreementService;

/** 
 * @author FQ 
 * @date 2013-1-31 下午02:25:17
 * @version 1.0
 * 
 */
@WebService(endpointInterface="com.froad.CB.service.AgreementService")
public class AgreementServiceImpl implements AgreementService {

	private static final Logger logger=Logger.getLogger(AgreementServiceImpl.class);
	
	private AgreementDao agreementDao;
	

	@Override
	public Integer addAgreement(Agreement agreement) {
		if(agreement==null){
			logger.info("增加协议参数不能为空！");
			return null;
		}
		return agreementDao.addAgreement(agreement);
	}

	@Override
	public Agreement getAgreementById(Integer id) {
		if(id==null){
			logger.info("查询协议ID参数不能为空！");
			return null;
		}
		return agreementDao.getAgreementById(id);
	}

	@Override
	public boolean updateAgreement(Agreement agreement) {
		if(agreement==null){
			logger.info("条件 Agreement 更新协议参数不能为空！");
			return false;
		}
		return agreementDao.updateAgreement(agreement);
	}

	@Override
	public List<Agreement> getAgreement(Agreement agreement) {
		if(agreement==null){
			logger.info("条件 Agreement 查询协议参数不能为空！");
			return null;
		}
		return agreementDao.getAgreement(agreement);
	}
	
	public AgreementDao getAgreementDao() {
		return agreementDao;
	}

	public void setAgreementDao(AgreementDao agreementDao) {
		this.agreementDao = agreementDao;
	}

	
}
