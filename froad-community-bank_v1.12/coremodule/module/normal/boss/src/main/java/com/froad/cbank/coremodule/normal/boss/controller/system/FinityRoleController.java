package com.froad.cbank.coremodule.normal.boss.controller.system;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.BossUser;
import com.froad.cbank.coremodule.normal.boss.pojo.system.FinityRoleVoDeleteReq;
import com.froad.cbank.coremodule.normal.boss.pojo.system.FinityRoleVoReq;
import com.froad.cbank.coremodule.normal.boss.support.system.FinityRoleSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;
import com.froad.thrift.vo.OriginVo;

/**
 * 角色管理
 * @author liaopeixin
 *	@date 2016年1月4日 下午4:23:59
 */
@Controller
@RequestMapping(value="/finityRole")
public class FinityRoleController {

	@Resource
	private FinityRoleSupport finityRoleSupport;
	/**
	 * 角色列表查询
	 * 
	 * @author liaopeixin
	 *	@date 2016年1月5日 =上午9:31:29
	 */
	@Auth(keys={"boss_role_menu"})
	@RequestMapping(value="list",method=RequestMethod.GET)
	public void list(ModelMap model,HttpServletRequest request,String platform){
		try{
			LogCvt.info("角色列表请求：平台名称："+platform);
			model.clear();
			BossUser user=(BossUser) request.getAttribute(Constants.BOSS_USER);			
			model.putAll(finityRoleSupport.list(platform,user.getId()));
		}catch (Exception e){
			LogCvt.error(e.getMessage(),e);
			new RespError(model,"角色列表查询失败！");
		}
	}
	
	@RequestMapping(value="detail",method=RequestMethod.GET)
	public void list(ModelMap model,Long roleId){
		try{
			if(roleId==null){
				throw new BossException("角色ID 不能为空!!");
			}
			LogCvt.info("角色详情请求...roleId:"+roleId);
			model.clear();
			model.putAll(finityRoleSupport.detail(roleId));
		}catch(BossException e){
			new RespError(model, e);
		}catch (Exception e){
			LogCvt.error(e.getMessage(),e);
			new RespError(model,"角色详情查询失败！");
		}
	}
	
	/**
	 * 角色新增
	 * @param model
	 * @param req
	 * @param resourceIds
	 * @author liaopeixin
	 *	@date 2016年1月5日 下午4:11:45
	 */
	@Auth(keys={"boss_role_menu"})
	@RequestMapping(value="add",method=RequestMethod.POST)
	public void add(ModelMap model,HttpServletRequest request,@RequestBody FinityRoleVoReq req){
		try{
			LogCvt.info("角色新增条件："+JSONObject.toJSONString(req)+"--资源idList:"+req.getResourceIds());
			model.clear();
			OriginVo originVo = (OriginVo) request.getAttribute(Constants.ORIGIN);
			model.putAll(finityRoleSupport.add(req, originVo, req.getResourceIds()));
		}catch(BossException e){
			new RespError(model, e);
		}catch(Exception e){
			LogCvt.error(e.getMessage(),e);
			new RespError(model,"角色新增失败！");
		}
	}
	/**
	 * 角色修改
	 * @param model
	 * @param req
	 * @param resourceIds
	 * @author liaopeixin
	 *	@date 2016年1月5日 下午4:12:01
	 */
	@Auth(keys={"boss_role_menu"})
	@RequestMapping(value="update",method=RequestMethod.POST)
	public void update(ModelMap model,HttpServletRequest request,@RequestBody FinityRoleVoReq req){
		try{	
			if(req.getId()==null){
				throw new BossException("角色ID不能为空!");
			}
			LogCvt.info("角色修改条件："+JSONObject.toJSONString(req)+"--资源idList:"+req.getResourceIds());
			model.clear();
			OriginVo originVo = (OriginVo) request.getAttribute(Constants.ORIGIN);
			model.putAll(finityRoleSupport.update(req, originVo,req.getResourceIds()));
		}catch(BossException e){
			new RespError(model, e);
		}catch(Exception e){
			LogCvt.error(e.getMessage(),e);
			new RespError(model,"角色修改失败！");
		}
	}

	/**
	 * 角色删除
	 * @param model
	 * @param request
	 * @param roleId
	 * @param platform
	 * @author liaopeixin
	 *	@date 2016年1月5日 下午4:12:17
	 */
	@Auth(keys={"boss_role_menu"})
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public void delete(ModelMap model, HttpServletRequest request,@RequestBody FinityRoleVoDeleteReq req){
		try{
			LogCvt.info("角色删除：roleId："+req.getId()+"--平台名称"+req.getPlatform());
			model.clear();
			OriginVo originVo = (OriginVo) request.getAttribute(Constants.ORIGIN);
			model.putAll(finityRoleSupport.delete(originVo, req.getId(), req.getPlatform()));
		}catch(BossException e){
			new RespError(model, e);
		}catch(Exception e){
			LogCvt.error(e.getMessage(),e);
			new RespError(model,"角色删除失败！");
		}
	}
	
}
