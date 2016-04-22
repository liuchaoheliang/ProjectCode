package com.froad.cbank.coremodule.normal.boss.support.activities;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.thrift.service.RegisteredRuleInfoService;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.ActiveBaseRuleVo;
import com.froad.thrift.vo.active.ActiveTagRelationVo;
import com.froad.thrift.vo.active.AddResultVo;
import com.froad.thrift.vo.active.FindPageRegisteredRuleInfoVoResultVo;
import com.froad.thrift.vo.active.FindRegisteredRuleInfoVoResultVo;
import com.froad.thrift.vo.active.RegisteredDetailRuleVo;
import com.froad.thrift.vo.active.RegisteredRuleInfoPageVoRes;
import com.froad.thrift.vo.active.RegisteredRuleInfoVo;

/**
 * @author luwanquan@f-road.com.cn
 * @date 2015年12月2日 上午10:11:17
 */
@Service
public class RegisteredSupport {
	
	@Resource
	private RegisteredRuleInfoService.Iface registeredRuleInfoService;
	
	/**
	 * 注册促销规则分页列表
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年12月3日 上午12:33:18
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, Object> queryRegisteredRuleList() throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Page page = new Page();
		//封装请求对象
		PageVo pageReq = new PageVo();
		RegisteredRuleInfoVo req = new RegisteredRuleInfoVo();
		ActiveTagRelationVo req1 = new ActiveTagRelationVo();
		ActiveBaseRuleVo req2 = new ActiveBaseRuleVo();
		RegisteredDetailRuleVo req3 = new RegisteredDetailRuleVo();
		
		req.setActiveTagRelation(req1);
		req.setActiveBaseRule(req2);
		req.setRegisteredDetailRule(req3);
		//调用SERVER端接口
		FindPageRegisteredRuleInfoVoResultVo resp = registeredRuleInfoService.getRegisteredRuleInfoByPage(pageReq, req);
		//封装返回结果对象
		ResultVo result = resp.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			RegisteredRuleInfoPageVoRes res = resp.getRegisteredRuleInfoPageVoRes();
			if(!ArrayUtil.empty(res.getRegisteredRuleInfoVoList())) {
				
			}
			map.put("registeredRuleList", null);
			map.put("page", page);
		} else {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 注册规则促销详情
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年12月3日 上午12:34:48
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, Object> queryRegisteredRuleDetail(String clientId, String activeId) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//调用SERVER端接口
		FindRegisteredRuleInfoVoResultVo resp = registeredRuleInfoService.getRegisteredRuleInfoById(clientId, activeId);
		//封装返回结果对象
		ResultVo result = resp.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			
		} else {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 添加注册促销规则
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年12月3日 上午12:21:59
	 * @param org
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, Object> addRegisteredRule(OriginVo org) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//封装请求对象
		RegisteredRuleInfoVo req = new RegisteredRuleInfoVo();
		ActiveTagRelationVo req1 = new ActiveTagRelationVo();
		ActiveBaseRuleVo req2 = new ActiveBaseRuleVo();
		req2.setType("6");
		RegisteredDetailRuleVo req3 = new RegisteredDetailRuleVo();
		
		req.setActiveTagRelation(req1);
		req.setActiveBaseRule(req2);
		req.setRegisteredDetailRule(req3);
		//调用SERVER端接口
		AddResultVo resp = registeredRuleInfoService.addRegisteredRuleInfo(org, req);
		//封装返回结果对象
		ResultVo result = resp.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			map.put("code", result.getResultCode());
			map.put("message", result.getResultDesc());
		} else {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 修改注册促销规则
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年12月3日 上午1:04:54
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, Object> updateRegisteredRule(OriginVo org) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//封装请求对象
		RegisteredRuleInfoVo req = new RegisteredRuleInfoVo();
		ActiveTagRelationVo req1 = new ActiveTagRelationVo();
		ActiveBaseRuleVo req2 = new ActiveBaseRuleVo();
		RegisteredDetailRuleVo req3 = new RegisteredDetailRuleVo();
		
		req.setActiveTagRelation(req1);
		req.setActiveBaseRule(req2);
		req.setRegisteredDetailRule(req3);
		//调用SERVER端接口
		ResultVo resp = registeredRuleInfoService.updateRegisteredRuleInfo(org, req);
		if(Constants.RESULT_SUCCESS.equals(resp.getResultCode())) {
			map.put("code", resp.getResultCode());
			map.put("message", resp.getResultDesc());
		} else {
			throw new BossException(resp.getResultCode(), resp.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 禁用注册促销规则
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年12月3日 上午1:11:29
	 * @param org
	 * @param clientId
	 * @param activeId
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, Object> disableRegisteredRule(OriginVo org, String clientId, String activeId) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//调用SERVER端接口
		ResultVo resp = registeredRuleInfoService.disableRegisteredRuleInfo(org, clientId, activeId, org.getOperatorUserName());
		if(Constants.RESULT_SUCCESS.equals(resp.getResultCode())) {
			map.put("code", resp.getResultCode());
			map.put("message", resp.getResultDesc());
		} else {
			throw new BossException(resp.getResultCode(), resp.getResultDesc());
		}
		return map;
	}
}
