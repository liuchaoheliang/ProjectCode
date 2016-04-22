package com.froad.CB.service.impl;

import java.sql.SQLException;
import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import com.froad.CB.dao.merchant.MerchantDAO;
import com.froad.CB.dao.merchant.MerchantPreferentialDAO;
import com.froad.CB.po.merchant.Merchant;
import com.froad.CB.po.merchant.MerchantPreferential;
import com.froad.CB.service.MerchantPreferentialService;
import com.froad.util.Assert;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-5  
 * @version 1.0
 */
@WebService(endpointInterface="com.froad.CB.service.MerchantPreferentialService")
public class MerchantPreferentialServiceImpl implements
		MerchantPreferentialService {
	private static final Logger logger=Logger.getLogger(MerchantPreferentialServiceImpl.class);
	private MerchantPreferentialDAO merchantPreferentialDao;
	private MerchantDAO merchantDao;
	
	@Override
	public Integer addMerchantPreferential(
			MerchantPreferential merchantPreferential) {
		if(merchantPreferential==null){
			logger.info("增加商家优惠参数merchantPreferential不能为空！");
			return null;
		}
		return merchantPreferentialDao.insert(merchantPreferential);
	}

	@Override
	public MerchantPreferential getMerchantPreferentialById(Integer id) {
		if(Assert.empty(id)){
			logger.info("查询商家优惠参数id不能为空！");
			return null;
		}
		return merchantPreferentialDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean updMerchantPreferential(
			MerchantPreferential merchantPreferential) {
		if(merchantPreferential==null){
			logger.info("增加商家优惠参数merchantPreferential不能为空！");
			return false;
		}
		Integer num = merchantPreferentialDao.updateByPrimaryKeySelective(merchantPreferential);
		return num==null||num==0||num==-1?false:true;
	}

	@Override
	public Integer deletePreferentialById(MerchantPreferential merchantPreferential) {
		if(merchantPreferential == null || Assert.empty(merchantPreferential.getId())){
			logger.info("删除商家优惠参数id不能为空！");
			return null;
		}
		return merchantPreferentialDao.deleteStateByPrimaryKey(merchantPreferential.getId());
	}

	@Override
	public List<MerchantPreferential> getMerchantPreferentialInfoByMerchantId(
			String merchantId) {
		if(Assert.empty(merchantId)){
			logger.info("查询商家优惠参数merchantId不能为空！");
			return null;
		}
		return merchantPreferentialDao.getMerchantPreferential(merchantId);
	}

	@Override
	public List<MerchantPreferential> getMerchantPreferentialInfoByUserId(
			String userId) {
		if(userId==null||"".equals(userId)){
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
		return merchantPreferentialDao.getMerchantPreferential(String.valueOf(merchant.getId()));
	}

	@Override
	public MerchantPreferential getMerchantPreferentialByPager(
			MerchantPreferential pager) {
		if(pager==null){
			logger.info("分页查询商家优惠参数merchantPreferential不能为空！");
			return null;
		}
		return merchantPreferentialDao.getMerchantPreferentialByPager(pager);
	}

	@Override
	public MerchantPreferential updMerchantPreferentialInfo(
			MerchantPreferential merchantPreferential) throws Exception{
		if(merchantPreferential==null){
			return null;
		}
		try{
			merchantPreferentialDao.updateByPrimaryKeySelective(merchantPreferential);
		}catch(Exception e){
			logger.error("更新商户优惠信息失败。", e);
			throw new Exception("系统更新商户优惠信息失败!");
		}
		MerchantPreferential newMerchantPreferential=null;
		try{
			newMerchantPreferential=merchantPreferentialDao.selectByPrimaryKey(merchantPreferential.getId());
		}catch(Exception e){
			logger.error("查询更新后的商户优惠信息失败。", e);
			throw new Exception("系统查询更新后的商户优惠信息失败!");
		}
		return newMerchantPreferential;
	}
	
	@Override
	public List<MerchantPreferential> getMerchantPreferentialListByMerchantId(MerchantPreferential merchantPreferential) {
		if(merchantPreferential==null||Assert.empty(merchantPreferential.getMerchantId()))
			return null;
		
		List<MerchantPreferential> results=merchantPreferentialDao.selectMerchantPreferentials(merchantPreferential);
		return results;
	}
	
	public MerchantPreferentialDAO getMerchantPreferentialDao() {
		return merchantPreferentialDao;
	}

	public void setMerchantPreferentialDao(
			MerchantPreferentialDAO merchantPreferentialDao) {
		this.merchantPreferentialDao = merchantPreferentialDao;
	}

	public MerchantDAO getMerchantDao() {
		return merchantDao;
	}

	public void setMerchantDao(MerchantDAO merchantDao) {
		this.merchantDao = merchantDao;
	}
	
}
