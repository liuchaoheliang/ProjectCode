package com.froad.cbank.coremodule.normal.boss.controller.system;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.BossUser;
import com.froad.cbank.coremodule.normal.boss.pojo.system.FinityResourceVoMoveReq;
import com.froad.cbank.coremodule.normal.boss.pojo.system.FinityResourceVoReq;
import com.froad.cbank.coremodule.normal.boss.support.system.ResourceSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;
import com.froad.thrift.vo.OriginVo;

@Controller
@RequestMapping(value ="/resource")
public class ResourceControler {

	@Resource
	private ResourceSupport resourceSupport;
	
	/**
	 * 资源查询
	 * @tilte list
	 * @author dhq
	 * @date 2016年1月4日 上午16:01:04
	 * @param model
	 * @param request
	 */
	@Auth(keys={"boss_res_menu"})
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(ModelMap model,HttpServletRequest request){
		LogCvt.info("资源查询请求");
		try{
			model.clear();
			model.putAll(resourceSupport.queryList());
		}catch(BossException e){
			new RespError(model, e);
		}catch(Exception e){
			LogCvt.info("资源查询请求异常"+e.getMessage(),e);
			new RespError(model,e.getMessage());
		}
	}
	
	/**
	 * 资源查询(详情)
	 * @tilte detail
	 * @author dhq
	 * @date 2016年1月4日 上午16:01:04
	 * @param model
	 * @param request
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public void detail(ModelMap model,HttpServletRequest request,Long id){
		LogCvt.info("资源查询(详情)请求参数:id="+id);
		try{
			if(id==null){
				throw new BossException("资源ID不能为空!");
			}
			model.clear();
			model.putAll(resourceSupport.detail(id));
		}catch(BossException e){
			new RespError(model, e);
		}catch(Exception e){
			LogCvt.info("资源查询(详情)请求异常"+e.getMessage(),e);
			new RespError(model,e.getMessage());
		}
	}
	
	
	/**
	 * 资源查询(用户)
	 * @tilte list
	 * @author dhq
	 * @date 2016年1月4日 上午16:01:04
	 * @param model
	 * @param request
	 * @param userId
	 */
	@RequestMapping(value = "/list_user", method = RequestMethod.GET)
	public void listByUser(ModelMap model,HttpServletRequest request){
		LogCvt.info("资源查询(用户)请求");
		try{
			BossUser user = (BossUser) request.getAttribute(Constants.BOSS_USER);
			model.clear();
			model.putAll(resourceSupport.queryListByUser(user.getId()));
		}catch(BossException e){
			new RespError(model, e);
		}catch(Exception e){
			LogCvt.info("资源查询(用户)请求异常"+e.getMessage(),e);
			new RespError(model,e.getMessage());
		}
	}
	
	/**
	 * 资源查询(角色 )
	 * @tilte lt
	 * @author dhq
	 * @date 2016年1月4日 上午16:01:04
	 * @param model
	 * @param request
	 * @param resourceReq
	 * @param roles
	 */
	@RequestMapping(value = "/list_role", method = RequestMethod.GET)
	public void listByRoles(ModelMap model,HttpServletRequest request,String roles,String platform){
		LogCvt.info("资源查询(角色)请求参数:roles="+roles+",platform="+platform);
		try{
			if(!StringUtil.isNotBlank(roles)){
				throw new BossException("角色ID不能为空!");
			}
			model.clear();
			model.putAll(resourceSupport.queryListByRole(roles,platform));
		}catch(BossException e){
			new RespError(model, e);
		}catch(Exception e){
			LogCvt.info("资源查询(角色)请求异常"+e.getMessage(),e);
			new RespError(model,e.getMessage());
		}
	}
	
	/**
	 * 新增资源
	 * @tilte add
	 * @author dhq
	 * @date 2016年1月4日 上午16:01:04
	 * @param model
	 * @param request
	 * @param resourceReq
	 */
	@Auth(keys={"boss_res_menu"})
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(ModelMap model,HttpServletRequest request,@RequestBody FinityResourceVoReq resourceReq){
		LogCvt.info("资源添加请求参数"+JSON.toJSONString(resourceReq));
		try{
			if(StringUtil.isBlank(resourceReq.getResource_key())){
				throw new BossException("资源编号不能为空!");
			}
			if(StringUtil.isBlank(resourceReq.getResourceName())){
				throw new BossException("资源名称不能为空!");
			}
			model.clear();
			OriginVo originVo = (OriginVo) request.getAttribute(Constants.ORIGIN);
			model.putAll(resourceSupport.save(originVo,resourceReq));
		}catch(BossException e){
			new RespError(model, e);
		}catch(Exception e){
			LogCvt.info("资源添加请求异常"+e.getMessage(),e);
			new RespError(model,e.getMessage());
		}
	}
	
	/**
	 * 更新资源
	 * @tilte update
	 * @author dhq
	 * @date 2016年1月4日 上午16:01:04
	 * @param model
	 * @param request
	 * @param resourceReq
	 */
	@Auth(keys={"boss_res_menu"})
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public void update(ModelMap model,HttpServletRequest request,@RequestBody FinityResourceVoReq resourceReq){
		LogCvt.info("资源更新请求参数"+JSON.toJSONString(resourceReq));
		try{
			if(resourceReq.getId()==null){
				throw new BossException("资源ID不能为空!");
			}

			model.clear();
			OriginVo originVo = (OriginVo) request.getAttribute(Constants.ORIGIN);
			model.putAll(resourceSupport.update(originVo,resourceReq));
		}catch(BossException e){
			new RespError(model, e);
		}catch(Exception e){
			LogCvt.info("资源查询更新请求异常"+e.getMessage(),e);
			new RespError(model,e.getMessage());
		}
	}
	
	/**
	 * 删除资源
	 * @tilte delete
	 * @author dhq
	 * @date 2016年1月4日 上午16:01:04
	 * @param model
	 * @param request
	 * @param platform
	 * @param resourceId
	 */
	@Auth(keys={"boss_res_menu"})
	@RequestMapping(value="/delete",method = RequestMethod.POST)
	public void delete(ModelMap model,HttpServletRequest request,@RequestBody FinityResourceVoReq resourceReq){
		LogCvt.info("资源删除请求参数"+JSON.toJSONString(resourceReq));
		try{
			if(!StringUtil.isNotBlank(resourceReq.getId())){
				throw new BossException("资源ID不能为空!");
			}
			if(!StringUtil.isNotBlank(resourceReq.getPlatform())){
				throw new BossException("平台不能为空!");
			}
			model.clear();
			OriginVo originVo = (OriginVo) request.getAttribute(Constants.ORIGIN);
			model.putAll(resourceSupport.delete(originVo, resourceReq.getPlatform(), resourceReq.getId()));
		}catch(BossException e){
			new RespError(model, e);
		}catch(Exception e){
			LogCvt.info("资源删除请求异常"+e.getMessage(),e);
			new RespError(model,e.getMessage());
		}
	}
	
	/**
	 * 调整资源顺序
	 * @tilte move
	 * @author dhq
	 * @date 2016年1月4日 上午16:01:04
	 * @param model
	 * @param request
	 * @param moveFlag
	 * @param resourceId
	 */
	@Auth(keys={"boss_res_menu"})
	@RequestMapping(value="/move",method = RequestMethod.POST)
	public void move(ModelMap model,HttpServletRequest request,@RequestBody FinityResourceVoMoveReq resourceReq){
		LogCvt.info("调整资源顺序请求参数:="+JSON.toJSONString(resourceReq));
		try{
			model.clear();
			OriginVo originVo = (OriginVo) request.getAttribute(Constants.ORIGIN);
			model.putAll(resourceSupport.move(originVo,resourceReq.getIds(),resourceReq.getOrderValues()));
		}catch(BossException e){
			new RespError(model, e);
		}catch(Exception e){
			LogCvt.info("调整资源顺序请求异常"+e.getMessage(),e);
			new RespError(model,e.getMessage());
		}
	}
}