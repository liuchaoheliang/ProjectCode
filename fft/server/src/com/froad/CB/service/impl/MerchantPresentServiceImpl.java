package com.froad.CB.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.common.Command;
import com.froad.CB.dao.merchant.MerchantDAO;
import com.froad.CB.dao.merchant.MerchantPresentDAO;
import com.froad.CB.po.merchant.Merchant;
import com.froad.CB.po.merchant.MerchantPresent;
import com.froad.CB.service.MerchantPresentService;
import com.froad.util.Assert;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-7  
 * @version 1.0
 */
@WebService(endpointInterface="com.froad.CB.service.MerchantPresentService")
public class MerchantPresentServiceImpl implements MerchantPresentService {
	private static final Logger logger = Logger.getLogger(MerchantPresentServiceImpl.class);
	private MerchantPresentDAO merchantPresentDao;
	private MerchantDAO merchantDao;
	
	@Override
	public Integer addMerchantPresent(MerchantPresent merchantPresent) {
		if(merchantPresent==null){
			logger.info("增加商家介绍参数merchantPresent不能为空！");
			return null;
		}
		return merchantPresentDao.insert(merchantPresent);
	}

	@Override
	public boolean updMerchantPresent(MerchantPresent merchantPresent) {
		if(merchantPresent==null || Assert.empty(merchantPresent.getId())){
			logger.info("更新商家介绍参数merchantPresent.id不能为空！");
			return false;
		}
		Integer num = merchantPresentDao.updateByPrimaryKeySelective(merchantPresent);
		return num==null||num==0||num==-1?false:true;
	}

	@Override
	public MerchantPresent getMerchantPresentByMerchantId(String merchantId) {
		if(Assert.empty(merchantId)){
			logger.info("查询商家介绍参数merchantId不能为空！");
			return null;
		}
		return merchantPresentDao.getMerchantPresent(merchantId);
	}

	@Override
	public MerchantPresent getMerchantPresentByUserId(String userId) {
		if(userId==null||"".equals(userId)){
			logger.info("查询商家介绍参数userId不能为空！");
			return null;
		}
		Merchant queryCon=new Merchant();
		queryCon.setUserId(userId);
		List<Merchant> results=null;
		try {
			results = merchantDao.select(queryCon);
		} catch (SQLException e) {
			logger.info("userId查询商家优惠userId查询商户信息出错！");
			e.printStackTrace();
		}
		Merchant merchant=results!=null?results.get(0):new Merchant();
		return merchantPresentDao.getMerchantPresent(merchant.getId()+"");
	}

	@Override
	public MerchantPresent updateByMerchantId(MerchantPresent merchantPresent) throws Exception{
		Integer num = null;
		if(merchantPresent==null){
			return null;
		}
		try{
			MerchantPresent mp = merchantPresentDao.getMerchantPresent(merchantPresent.getMerchantId());
			if(mp != null){
				num = merchantPresentDao.updateByMerchantId(merchantPresent);				
			}else{
				merchantPresent.setState(Command.STATE_START);
				num = merchantPresentDao.insert(merchantPresent);
			}
		}catch(Exception e){
			logger.error("更新商户介绍信息失败。", e);
			throw new Exception("系统更新商户介绍信息失败!");
		}
		MerchantPresent newMerchantPresent=null;
		try{
			if(num != null && num > 0){
				newMerchantPresent=merchantPresentDao.selectByMerchantId(merchantPresent.getMerchantId());				
			}
		}catch(Exception e){
			logger.error("查询更新后的商户介绍信息失败。", e);
			throw new Exception("系统查询更新后的商户介绍信息失败!");
		}
		return newMerchantPresent;
	}
	
	public MerchantPresentDAO getMerchantPresentDao() {
		return merchantPresentDao;
	}

	public void setMerchantPresentDao(MerchantPresentDAO merchantPresentDao) {
		this.merchantPresentDao = merchantPresentDao;
	}

	public MerchantDAO getMerchantDao() {
		return merchantDao;
	}

	public void setMerchantDao(MerchantDAO merchantDao) {
		this.merchantDao = merchantDao;
	}
	
}
