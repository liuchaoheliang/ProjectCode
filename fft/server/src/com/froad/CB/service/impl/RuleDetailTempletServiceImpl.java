package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.AppException.RuleDetailTypeIsNull;
import com.froad.CB.AppException.RuleTypeIsNull;
import com.froad.CB.AppException.RuleTypeNotExists;
import com.froad.CB.AppException.StateIsNull;
import com.froad.CB.AppException.StateNotExists;
import com.froad.CB.common.Command;
import com.froad.CB.common.constant.RuleType;
import com.froad.CB.dao.RuleDetailTempletDao;
import com.froad.CB.po.RuleDetailTemplet;
import com.froad.CB.service.RuleDetailTempletService;
import com.froad.util.Assert;

@WebService(endpointInterface="com.froad.CB.service.RuleDetailTempletService")
public class RuleDetailTempletServiceImpl implements RuleDetailTempletService {
	private RuleDetailTempletDao ruleDetailTempletDao;
	private static final Logger log=Logger.getLogger(RuleDetailTempletServiceImpl.class);
	private boolean checkTransRule(RuleDetailTemplet ruleDetailTemplet)throws Exception{
		if(Assert.empty(ruleDetailTemplet.getRuleType()))
			throw new RuleTypeIsNull("规则类型为空");
		if(RuleType.getRuleType(ruleDetailTemplet.getRuleType())==null)
			throw new RuleTypeNotExists("系统不存在规则类型："+ruleDetailTemplet.getRuleType());
		if(Assert.empty(ruleDetailTemplet.getRuleDetailType()))
			throw new RuleDetailTypeIsNull("规则明细类型为空");
		if(Assert.empty(ruleDetailTemplet.getState()))
			throw new StateIsNull("规则明细模板状态为空");
		if(!Command.STATES.contains(ruleDetailTemplet.getState()))
			throw new StateNotExists("系统不存在规则明细模板的状态为："+ruleDetailTemplet.getState());
		
		return true;
	}
	@Override
	public Integer addRuleDetailTemplet(RuleDetailTemplet ruleDetailTemplet)throws Exception {
		Integer ruleDetailTempletId=null;
		try {
			// TODO Auto-generated method stub
			checkTransRule(ruleDetailTemplet);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("验证规则明细模板失败！其规则明细模板信息为："+ruleDetailTemplet, e);
			throw e;
		}
		try {
			ruleDetailTempletId = ruleDetailTempletDao.insert(ruleDetailTemplet);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("添加规则明细模板时，数据库异常：", e);
		}
		return ruleDetailTempletId;
	}
	

	@Override
	public boolean updateRuleDetailTemplet(
			RuleDetailTemplet ruleDetailTemplet) {
		// TODO Auto-generated method stub
		boolean result=false;
		if(ruleDetailTemplet==null||ruleDetailTemplet.getId()==null||Assert.empty(ruleDetailTemplet.getState())){
			return result;
		}
		if(!Command.STATES.contains(ruleDetailTemplet.getState()))
			return result;
		RuleDetailTemplet updateInfo = new RuleDetailTemplet();
		updateInfo.setId(ruleDetailTemplet.getId());
		updateInfo.setState(ruleDetailTemplet.getState());
		updateInfo.setDescription(ruleDetailTemplet.getDescription());
		updateInfo.setRemark(ruleDetailTemplet.getRemark());
		try {
			result = ruleDetailTempletDao
					.updateByPrimaryKeySelective(updateInfo);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("更新规则明细模板时，数据库异常：", e);
		}
		return result;
	}

	public RuleDetailTempletDao getRuleDetailTempletDao() {
		return ruleDetailTempletDao;
	}

	public void setRuleDetailTempletDao(RuleDetailTempletDao ruleDetailTempletDao) {
		this.ruleDetailTempletDao = ruleDetailTempletDao;
	}

	@Override
	public List<RuleDetailTemplet> getRuleDetailTemplets(
			RuleDetailTemplet ruleDetailTemplet) {
		// TODO Auto-generated method stub
		return ruleDetailTempletDao.selectRuleDetailTemplets(ruleDetailTemplet);
	}
}
