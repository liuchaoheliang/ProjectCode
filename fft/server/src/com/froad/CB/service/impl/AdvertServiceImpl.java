package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.AppException.AdvertTypeIsNull;
import com.froad.CB.AppException.StateIsNull;
import com.froad.CB.dao.AdvertDao;
import com.froad.CB.po.Advert;
import com.froad.CB.po.Agreement;
import com.froad.CB.service.AdvertService;
import com.froad.CB.service.AgreementService;
import com.froad.util.Assert;
@WebService(endpointInterface="com.froad.CB.service.AdvertService")
public class AdvertServiceImpl implements AdvertService {
	private AdvertDao advertDao; 

	private static final Logger log=Logger.getLogger(AdvertServiceImpl.class);
	private boolean check(Advert advert) throws Exception{
		if(Assert.empty(advert.getState()))
			throw new StateIsNull("广告状态为空");
		if(Assert.empty(advert.getType()))
			throw new AdvertTypeIsNull("广告类型为空");
		return true;
	}
	
	
	
	@Override
	public Integer addAdvert(Advert advert) throws Exception {
		// TODO Auto-generated method stub
		Integer advertId=null;
		try {
			check(advert);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("广告验证失败！", e);
			throw e;
		}
		try {
			advertId = advertDao.insert(advert);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("添加广告时，数据库异常！", e);
		}
		return advertId;
	}

	@Override
	public List<Advert> getAdverts(Advert queryCon) {
		// TODO Auto-generated method stub
		List<Advert> adverts=null;
		try {
			adverts = advertDao.selectAdverts(queryCon);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询广告时，数据库异常！", e);
		}
		return adverts;
	}
	
	

	@Override
	public boolean stopAdvert(Advert advert) {
		// TODO Auto-generated method stub
		boolean result=false;
		if(advert.getId()==null||Assert.empty(advert.getState()))
			return result;
		Advert stopInfo=new Advert();
		stopInfo.setId(advert.getId());
		stopInfo.setState(advert.getState());
		try {
			result = advertDao.updateByPrimaryKeySelective(stopInfo);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("删除广告时，数据库异常！", e);
		}
		return result;
	}

	@Override
	public boolean updateAdvert(Advert advert) {
		// TODO Auto-generated method stub
		boolean result=false;
		if(advert.getId()==null)
			return result;
		try {
			result = advertDao.updateByPrimaryKeySelective(advert);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("更新广告时，数据库异常！", e);
		}
		return result;
	}

	@Override
	public Advert getAdvertByPager(Advert pager) {
		
		if(pager==null){
			log.error("参数为空，分页查询失败！");
			return null;
		}
		return advertDao.getAdvertByPager(pager);
	}

	public AdvertDao getAdvertDao() {
		return advertDao;
	}

	public void setAdvertDao(AdvertDao advertDao) {
		this.advertDao = advertDao;
	}

}
