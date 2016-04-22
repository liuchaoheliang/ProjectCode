package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.SellerRuleDao;
import com.froad.CB.po.SellerRule;
import com.froad.CB.service.SellerRuleService;

@WebService(endpointInterface="com.froad.CB.service.SellerRuleService")
public class SellerRuleServiceImpl implements SellerRuleService{
	
	private static Logger logger=Logger.getLogger(SellerRuleServiceImpl.class);
	
	private SellerRuleDao sellerRuleDao;

	@Override
	public Integer addSellerRule(SellerRule sellerRule) {
		if(sellerRule==null){
			logger.error("参数为空，添加失败");
			return null;
		}
		return sellerRuleDao.addSellerRule(sellerRule);
	}

	@Override
	public List<SellerRule> getBySelective(SellerRule sellerRule) {
		if(sellerRule==null){
			logger.error("参数为空，查询失败");
			return null;
		}
		return sellerRuleDao.getBySelective(sellerRule);
	}

	@Override
	public boolean updateById(SellerRule sellerRule) {
		if(sellerRule==null||sellerRule.getId()==null){
			logger.error("参数为空，更新失败");
			return false;
		}
		try {
			sellerRuleDao.updateById(sellerRule);
			return true;
		} catch (Exception e) {
			logger.error("添加规则出现异常",e);
			return false;
		}
	}

	public void setSellerRuleDao(SellerRuleDao sellerRuleDao) {
		this.sellerRuleDao = sellerRuleDao;
	}

}
