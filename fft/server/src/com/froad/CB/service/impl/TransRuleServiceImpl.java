package com.froad.CB.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.AppException.RuleDetailTempletsNotExists;
import com.froad.CB.AppException.RuleStateIsNull;
import com.froad.CB.AppException.RuleStateNotExists;
import com.froad.CB.AppException.RuleTypeIsNull;
import com.froad.CB.AppException.RuleTypeNotExists;
import com.froad.CB.common.Command;
import com.froad.CB.common.CommonUtil;
import com.froad.CB.common.constant.DetailRuleFormulaFromType;
import com.froad.CB.common.constant.RuleType;
import com.froad.CB.dao.RuleDetailDao;
import com.froad.CB.dao.RuleDetailTempletDao;
import com.froad.CB.dao.TransRuleDao;
import com.froad.CB.po.RuleDetail;
import com.froad.CB.po.RuleDetailTemplet;
import com.froad.CB.po.TransRule;
import com.froad.CB.service.RuleDetailTempletService;
import com.froad.CB.service.TransRuleService;
import com.froad.util.Assert;

@WebService(endpointInterface="com.froad.CB.service.TransRuleService")
public class TransRuleServiceImpl implements TransRuleService {
	private RuleDetailDao ruleDetailDao;
	
	private TransRuleDao transRuleDao;
	private RuleDetailTempletService ruleDetailTempletService;
	private static final Logger log=Logger.getLogger(TransRuleServiceImpl.class);
	@Override
	public Integer addTranRuleDetail(RuleDetail ruleDetail) {
		// TODO Auto-generated method stub
		return ruleDetailDao.insert(ruleDetail);
	}
	
	private boolean checkTransRule(TransRule transRule)throws Exception{
		if(Assert.empty(transRule.getRuleType()))
			throw new RuleTypeIsNull("规则类型为空");
		if(RuleType.getRuleType(transRule.getRuleType())==null)
			throw new RuleTypeNotExists("系统不存在规则类型："+transRule.getRuleType());
		if(Assert.empty(transRule.getState()))
			throw new RuleStateIsNull("规则状态为空");
		if(!Command.STATES.contains(transRule.getState()))
			throw new RuleStateNotExists("系统不存在规则状态为："+transRule.getState());
		
		return true;
	}

	@Override
	public Integer addTransRule(TransRule transRule) throws Exception{
		// TODO Auto-generated method stub
		try {
			checkTransRule(transRule);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("交易规则验证无效，其规则信息为："+transRule, e);
			throw e;
		}
		RuleDetailTemplet queryCon=new RuleDetailTemplet();
		queryCon.setRuleType(transRule.getRuleType());
		queryCon.setState(Command.STATE_START);
		List<RuleDetailTemplet> ruleDetailTemplets=ruleDetailTempletService.getRuleDetailTemplets(queryCon);
		if(Assert.empty(ruleDetailTemplets)){
			log.error("规则类型为："+transRule.getRuleType()+"的规则明细模板不存在！");
			throw new RuleDetailTempletsNotExists("规则类型为："+transRule.getRuleType()+"的规则明细模板不存在！");
		}
		for(RuleDetailTemplet ruleDetailTemplet:ruleDetailTemplets){
			Map<String,String> formulaAndMinMax=new HashMap();
			
			try{
				formulaAndMinMax=getFormulaAndMinMax(transRule,ruleDetailTemplet);
			}catch(Exception e){
				log.error("获取规则明细的公式失败！", e);
				return null;
			}
			String formulaOfDetailRule=formulaAndMinMax.get("formula");
			if(Assert.empty(formulaOfDetailRule)){
				log.error("获取规则明细的公式为空！");
				return null;
			}
			RuleDetail ruleDetail=new RuleDetail();
			ruleDetail.setRuleType(transRule.getRuleType());
			ruleDetail.setRuleDetailType(ruleDetailTemplet.getRuleDetailType());
			ruleDetail.setRuleDetailTempletId(ruleDetailTemplet.getId());
			ruleDetail.setRuleId(transRule.getId());
			ruleDetail.setStartTime(transRule.getStartTime());
			ruleDetail.setEndTime(transRule.getEndTime());
			ruleDetail.setState(transRule.getState());
			ruleDetail.setDescription(ruleDetailTemplet.getDescription());
			ruleDetail.setFormula(formulaOfDetailRule);
			ruleDetail.setMax(formulaAndMinMax.get("max"));
			ruleDetail.setMin(formulaAndMinMax.get("min"));
			transRule.getRuleDetailList().add(ruleDetail);
		}
		Integer ruleId=null;
		try {
			ruleId = transRuleDao.insert(transRule);
			for(RuleDetail ruleDetailToSave:transRule.getRuleDetailList()){
				ruleDetailToSave.setRuleId(ruleId);
			}
			ruleDetailDao.insertBatch(transRule.getRuleDetailList());
		} catch (Exception e) {
			// TODO: handle exception
			log.error("在添加规则和规则明细时，产生数据库异常", e);
		}
		return ruleId;
	}
	
	private String getFormula(TransRule transRule,RuleDetailTemplet ruleDetailTemplet)throws Exception{
		String ruleValue=null;
		if(DetailRuleFormulaFromType.getDetailRuleFormulaFromType(ruleDetailTemplet.getDetailRuleFormulaFromType())==DetailRuleFormulaFromType.CONSTANT){
			ruleValue=ruleDetailTemplet.getDetailRuleFormulaFrom();
		}else{
			Method method=transRule.getClass().getMethod(ruleDetailTemplet.getDetailRuleFormulaFrom(), null);
			ruleValue=(String)method.invoke(transRule, null);
		}
		double ruleValueInt=0;
		ruleValueInt=Double.valueOf(ruleValue);
		double formula=0;
		formula=Double.valueOf(ruleDetailTemplet.getFormulaOfDetailRule());
		double result=ruleValueInt*formula;
		result=CommonUtil.scale2(result,2);
		return String.valueOf(result);
	}
	
	
	//Map key：vaue{formula:公式，min： 最小值，max：最大值}
	private Map getFormulaAndMinMax(TransRule transRule,RuleDetailTemplet ruleDetailTemplet)throws Exception{
		Map<String, String> result=new HashMap();
		String ruleValue=null;
		if(DetailRuleFormulaFromType.getDetailRuleFormulaFromType(ruleDetailTemplet.getDetailRuleFormulaFromType())==DetailRuleFormulaFromType.CONSTANT){
			ruleValue=ruleDetailTemplet.getDetailRuleFormulaFrom();
		}else{
			Method method=transRule.getClass().getMethod(ruleDetailTemplet.getDetailRuleFormulaFrom(), null);
			ruleValue=(String)method.invoke(transRule, null);//规则的值是形如：X【min,max】
		}
		String noMinMaxRuleParaStr=ruleValue;
		String minStr="";
		String maxStr="";
		int noMinMaxRuleParaEndIndex=ruleValue.indexOf("[");
		if(noMinMaxRuleParaEndIndex>0){
		 noMinMaxRuleParaStr=ruleValue.substring(0, noMinMaxRuleParaEndIndex);
		 int minEndIndex=ruleValue.indexOf(",");
		 if(minEndIndex>0){
			 minStr=ruleValue.substring(noMinMaxRuleParaEndIndex+1, minEndIndex);
			 int minMaxRuleParaEndIndex=ruleValue.indexOf("]");
			 if(minMaxRuleParaEndIndex>0)
				 maxStr=ruleValue.substring(minEndIndex+1, minMaxRuleParaEndIndex);
		 }
		}
		double ruleValueInt=0;
		ruleValueInt=Double.valueOf(noMinMaxRuleParaStr);
		double formula=0;
		formula=Double.valueOf(ruleDetailTemplet.getFormulaOfDetailRule());
		double comFormula=ruleValueInt*formula;
		comFormula=CommonUtil.scale2(comFormula,2);
		result.put("formula", String.valueOf(comFormula));
		if(!Assert.empty(minStr)&&!("-1".equals(minStr)))
			result.put("min", minStr);
		if(!Assert.empty(maxStr)&&!("-1".equals(maxStr)))
			result.put("max", maxStr);
		return result;
	}


	@Override
	public List<TransRule> getTransRules(TransRule queryCon) {
		// TODO Auto-generated method stub
		return transRuleDao.selectTransRules(queryCon);
	}

	@Override
	public boolean updateTranRuleDetail(RuleDetail ruleDetail) {
		// TODO Auto-generated method stub
		return ruleDetailDao.updateByPrimaryKeySelective(ruleDetail);
	}

	@Override
	public boolean updateTransRule(TransRule transRule) {
		// TODO Auto-generated method stub
		boolean result=false;
		if(transRule==null||transRule.getId()==null||Assert.empty(transRule.getState())){
			return result;
		}
		if(!Command.STATES.contains(transRule.getState()))
			return result;
		
		try {
			TransRule transRuleInfo = transRuleDao.selectByPrimaryKey(transRule
					.getId());
			if(transRuleInfo==null)
				return result;
			TransRule updateInfo = new TransRule();
			updateInfo.setId(transRule.getId());
			updateInfo.setState(transRule.getState());
			updateInfo.setRuleDesc(transRule.getRuleDesc());
			updateInfo.setRemark(transRule.getRemark());
			result = transRuleDao.updateByPrimaryKeySelective(updateInfo);
			if (!result)
				return result;
			RuleDetail ruleDetailUpdateInfo = new RuleDetail();
			ruleDetailUpdateInfo.setRuleId(transRule.getId());
			ruleDetailUpdateInfo.setRuleType(transRuleInfo.getRuleType());
			ruleDetailUpdateInfo.setState(transRule.getState());
			result = ruleDetailDao.updateRuleDetailByRule(ruleDetailUpdateInfo);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("更新规则时，数据库异常", e);
			result=false;
		}
		return result;
	}


	public RuleDetailDao getRuleDetailDao() {
		return ruleDetailDao;
	}

	public void setRuleDetailDao(RuleDetailDao ruleDetailDao) {
		this.ruleDetailDao = ruleDetailDao;
	}


	public TransRuleDao getTransRuleDao() {
		return transRuleDao;
	}

	public void setTransRuleDao(TransRuleDao transRuleDao) {
		this.transRuleDao = transRuleDao;
	}

	public RuleDetailTempletService getRuleDetailTempletService() {
		return ruleDetailTempletService;
	}

	public void setRuleDetailTempletService(
			RuleDetailTempletService ruleDetailTempletService) {
		this.ruleDetailTempletService = ruleDetailTempletService;
	}

}
