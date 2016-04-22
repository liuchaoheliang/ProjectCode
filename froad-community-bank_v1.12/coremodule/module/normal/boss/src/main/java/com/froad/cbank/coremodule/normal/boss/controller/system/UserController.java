package com.froad.cbank.coremodule.normal.boss.controller.system;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.BossUser;
import com.froad.cbank.coremodule.normal.boss.pojo.system.AddUserVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.system.UpdatePwdVo;
import com.froad.cbank.coremodule.normal.boss.pojo.system.UserListVoReq;
import com.froad.cbank.coremodule.normal.boss.support.system.UserSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 
 * @ClassName: UserController
 * @author chenzhangwei@f-road.com.cn
 * @createTime 2016年1月6日 下午3:48:15 
 * @desc <p>权限管理用户模块相关服务</p>
 */
@Controller
@RequestMapping(value="/user")
public class UserController {
	
	@Resource
	UserSupport userSupport;
	
	/**
	 * 
	 * <p>Title:boss 权限用户列表查询 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月6日 下午4:01:09 
	 * @param model
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public void findAlluser(ModelMap model,UserListVoReq req){
		LogCvt.info("权限用户列表查询条件："+JSON.toJSONString(req));
		try {
			model.clear();
			model.putAll(userSupport.list(req));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**'
	 * 
	 * <p>Title:添加用户 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月6日 下午4:39:44 
	 * @param model
	 * @param req
	 */
	@Auth(keys={"boss_org_menu"})
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public void add(ModelMap model,@RequestBody AddUserVoReq req,HttpServletRequest request){
		if(StringUtil.isBlank(req.getPassword())){
			new RespError(model,"添加用户密码不能为空");
		}
		LogCvt.info("添加用户条件："+JSON.toJSONString(req));
		try {
			model.clear();
			model.putAll(userSupport.add(request, req));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 
	 * <p>Title: 用户删除</p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月6日 下午4:42:02 
	 * @param model
	 * @param id
	 */
	@Auth(keys={"boss_org_menu"})
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public void delete(ModelMap model,String id,HttpServletRequest request){
		if(StringUtil.isBlank(id)){
			new RespError(model,"用户删除id不能为空");
		}
		LogCvt.info("删除用户条件：用户id:     "+id);
		try {
			model.clear();
			model.putAll(userSupport.delete(id, request));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	@Auth(keys={"boss_org_menu"})
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public void update(ModelMap model,@RequestBody AddUserVoReq req,HttpServletRequest request){
		LogCvt.info("修改用户条件："+JSON.toJSONString(req));
		if(StringUtil.isBlank(req.getUserId())){
			new RespError(model,"修改用户id不能为空");
		}
		try {
			model.clear();
			model.putAll(userSupport.update(request, req));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
		
	}
	
	/**
	 * 
	 * <p>Title:根据组织机构id查询用户相关的组织机构 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月6日 下午5:04:20 
	 * @param model
	 * @param userId
	 */
	@RequestMapping(value="/findOrgByUser",method=RequestMethod.GET)
	public void findOrgByUser(ModelMap model,List<String> orgIds){
		LogCvt.info("查询用户组织条件，用户id   ："+JSON.toJSONString(orgIds));
		if(ArrayUtil.empty(orgIds)){
			new RespError(model,"查询组织失败,组织id不能为空");
		}
		try {
			model.clear();
			model.putAll(userSupport.findOrgsByUser(orgIds));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 
	 * <p>Title:根据用户查询用户的角色 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月6日 下午5:07:24 
	 * @param model
	 * @param userId
	 */
	@RequestMapping(value="/findRoleByUser",method=RequestMethod.GET)
	public void findRoleByUser(ModelMap model,HttpServletRequest request){
		try {
			model.clear();
			BossUser user = (BossUser) request.getAttribute(Constants.BOSS_USER);
			LogCvt.info("查询用户角色条件，用户id   ："+JSON.toJSONString(user.getId()));
			model.putAll(userSupport.findRolesByUser(user.getId()));
		}catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 
	 * <p>Title: 用户重置密码</p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月11日 上午11:15:31 
	 * @param userId
	 */
	@Auth(keys={"boss_org_menu"})
	@RequestMapping(value="/resetPwd",method=RequestMethod.GET)
	public void resetPwd(Long userId,HttpServletRequest request,ModelMap model){
		if(userId==null || userId ==0){
			new RespError(model,"重置密码用户id不能为空");
		}
		try {
			model.clear();
			LogCvt.info("用户密码重置条件，用户id   ："+JSON.toJSONString(userId));
			model.putAll(userSupport.resetPwd(userId, request));
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 
	 * <p>Title:用户详情查询 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月11日 上午11:34:51 
	 * @param userId
	 */
	@RequestMapping(value="/detail",method=RequestMethod.GET)
	public void detail(Long userId,ModelMap model){
		if(userId==null || userId ==0){
			new RespError(model,"用户详情查询id不能为空");
		}
		try {
			model.clear();
			LogCvt.info("用户详情查询条件，用户id   ："+JSON.toJSONString(userId));
			model.putAll(userSupport.detail(userId));
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
		
	}
	
	
	/**
	 * 
	 * <p>Title:查询可分配的用户角色，再点击用户的时候需要调用改接口 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月20日 上午11:16:27 
	 * @param model
	 * @param userId
	 * @param request
	 */
	@RequestMapping(value="/findRolesByUserId",method=RequestMethod.GET)
	public void findRoleByUser(ModelMap model,Long userId,HttpServletRequest request){
		if(userId == null || userId ==0){
			new RespError(model,"查询用户角色，用户id不能为空");
		}
		try {
			model.clear();
			LogCvt.info("查询用户角色   ："+JSON.toJSONString(userId));
			BossUser user = (BossUser) request.getAttribute(Constants.BOSS_USER);
			model.putAll(userSupport.findRolesById(user.getId(), userId));
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 
	 * <p>Title: 用户修改密码</p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2015年11月25日 下午5:15:50 
	 * @param model
	 * @param id
	 * @param pwd
	 * @param request
	 */
	@RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
	public void updatePwd(ModelMap model,HttpServletRequest request,@RequestBody UpdatePwdVo req) {
		try{
			LogCvt.info("用户修改密码条件: " + JSON.toJSONString(req));
			BossUser user = (BossUser) request.getAttribute(Constants.BOSS_USER);
			model.putAll(userSupport.updatePwd(user,req.getPwd(),request,req.getOldPwd()));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("用户修改密码请求异常"+e.getMessage(), e);
			new RespError(model,e.getMessage());
		}
	}
	
	
	
}
