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
import com.froad.cbank.coremodule.normal.boss.enums.Platform;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.BossUser;
import com.froad.cbank.coremodule.normal.boss.pojo.system.OrgVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.system.OrgVolistReq;
import com.froad.cbank.coremodule.normal.boss.support.system.OrgSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;
import com.froad.thrift.vo.OriginVo;

/**
 * 组织机构管理
 * @author yfy
 * @date: 2016年01月04日 下午14:31:01
 */
@Controller
@RequestMapping(value="org")
public class OrgController {

	@Resource
	private OrgSupport orgSupport;
	
	/**
	 * 当前用户组织权限查询
	 * @author yfy
	 * @date: 2016年01月15日 下午11:37:04
	 * @param model
	 * @param request
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public void list(ModelMap model, HttpServletRequest request) {
		try {
			model.clear();
			BossUser user = (BossUser) request.getAttribute(Constants.BOSS_USER);
			model.putAll(orgSupport.list(user.getId()));
		} catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("当前用户组织权限查询请求异常"+e.getMessage(), e);
			new RespError(model, "当前用户组织权限查询失败!!!");
		}
	}
	
	/**
	 * 组织机构列表分页查询
	 * @author yfy
	 * @date: 2016年01月04日 下午14:58:04
	 * @param model
	 * @param request
	 * @param listReq
	 */
	@RequestMapping(value = "listPage", method = RequestMethod.GET)
	public void listByPage(ModelMap model, HttpServletRequest request,OrgVolistReq voReq) {
		LogCvt.info("组织机构列表分页查询条件:" + JSON.toJSONString(voReq));
		try {
			if(!StringUtil.isNotBlank(voReq.getId())){
				throw new BossException("主键ID不能为空!");
			}
			model.clear();
			model.putAll(orgSupport.listByPage(voReq));
		} catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("组织机构列表分页查询请求异常"+e.getMessage(), e);
			new RespError(model, "组织机构列表分页查询失败!!!");
		}
	}

	/**
	 * 当前用户下的一级和二级组织信息树列表
	 * @author yfy
	 * @date: 2016年01月04日 下午15:10:13
	 * @param model
	 * @param request
	 * @param userId
	 */
	@RequestMapping(value = "twoOrgList", method = RequestMethod.GET)
	public void twoOrgList(ModelMap model, HttpServletRequest request) {
		try {
			model.clear();
			BossUser user = (BossUser) request.getAttribute(Constants.BOSS_USER);
			model.putAll(orgSupport.twoOrgList(user.getId()));
		} catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("当前用户下的一级和二级组织信息树列表查询请求异常"+e.getMessage(), e);
			new RespError(model, "当前用户下的一级和二级组织信息树列表查询失败!!!");
		}
	}
	
	/**
	 * 根据组织ID查询当前组织子节点
	 * @author yfy
	 * @date: 2016年01月04日 下午15:10:13
	 * @param model
	 * @param request
	 * @param userId
	 */
	@RequestMapping(value = "childOrgList", method = RequestMethod.GET)
	public void childOrgList(ModelMap model, HttpServletRequest request,String orgId) {
		LogCvt.info("当前组织子节点查询条件:orgId:" + orgId);
		try {
			model.clear();
			BossUser user = (BossUser) request.getAttribute(Constants.BOSS_USER);
			model.putAll(orgSupport.childOrgList(user.getId(),orgId));
		} catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("当前组织子节点查询请求异常"+e.getMessage(), e);
			new RespError(model, "当前组织子节点查询失败!!!");
		}
	}
	
	/**
	 * 组织数据权限查询
	 * @author yfy
	 * @date: 2016年01月05日 上午10:09:01
	 * @param model
	 * @param request
	 * @param orgId
	 */
	@RequestMapping(value = "orgReList", method = RequestMethod.GET)
	public void orgReList(ModelMap model, HttpServletRequest request,String orgId) {
		LogCvt.info("组织数据权限查询条件:orgId:" + orgId);
		try {
			if(!StringUtil.isNotBlank(orgId)){
				throw new BossException("组织ID不能为空!");
			}
			model.clear();
			model.putAll(orgSupport.orgReList(orgId));
		} catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("组织数据权限查询请求异常"+e.getMessage(), e);
			new RespError(model, "组织数据权限查询失败!!!");
		}
	}
	
	/**
	 * 根据组织ID查询组织角色
	 * @author yfy
	 * @date: 2016年01月05日 上午10:09:01
	 * @param model
	 * @param request
	 * @param orgId
	 */
	@RequestMapping(value = "orgRoleList", method = RequestMethod.GET)
	public void orgRoleList(ModelMap model, HttpServletRequest request,String orgId) {
		LogCvt.info("根据组织ID查询组织角色条件:orgId:" + orgId);
		try {
			if(!StringUtil.isNotBlank(orgId)){
				throw new BossException("组织ID不能为空!");
			}
			model.clear();
			model.putAll(orgSupport.orgRoleList(orgId));
		} catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("根据组织ID查询组织角色请求异常"+e.getMessage(), e);
			new RespError(model, "根据组织ID查询组织角色失败!!!");
		}
	}
	
	/**
	 * 根据组织ID集合数组查询组织角色
	 * @author yfy
	 * @date: 2016年01月05日 上午10:09:01
	 * @param model
	 * @param request
	 * @param orgIds
	 */
	@RequestMapping(value = "orgIdsRoleList", method = RequestMethod.GET)
	public void orgRoleList(ModelMap model, HttpServletRequest request,String[] orgIds) {
		LogCvt.info("根据组织ID集合数组查询组织角色条件:" + JSON.toJSONString(orgIds));
		try {
			if(orgIds.length ==0 ){
				throw new BossException("组织ID不能为空!");
			}
			model.clear();
			model.putAll(orgSupport.orgRoleList(orgIds));
		} catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("根据组织ID集合数组查询组织角色请求异常"+e.getMessage(), e);
			new RespError(model, "根据组织ID集合数组查询组织角色失败!!!");
		}
	}
	
	/**
	 * 组织机构管理新增
	 * @author yfy
	 * @date: 2016年01月05日 上午10:24:50
	 * @param model
	 * @param request
	 * @param voReq
	 */
	@Auth(keys={"boss_org_menu"})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public void add(ModelMap model, HttpServletRequest request,@RequestBody OrgVoReq voReq) {
		LogCvt.info("组织机构管理新增参数:" + JSON.toJSONString(voReq));
		try {
			checkEmpty(model,voReq);//数据校验
			model.clear();
			OriginVo org = (OriginVo) request.getAttribute(Constants.ORIGIN);
			model.putAll(orgSupport.add(voReq,org));
		} catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("组织机构管理新增请求异常"+e.getMessage(), e);
			new RespError(model, "组织机构管理新增失败!!!");
		}
	}
	
	/**
	 * 组织机构管理修改
	 * @author yfy
	 * @date: 2016年01月05日 上午11:08:23
	 * @param model
	 * @param request
	 * @param voReq
	 */
	@Auth(keys={"boss_org_menu"})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public void update(ModelMap model, HttpServletRequest request,@RequestBody OrgVoReq voReq) {
		LogCvt.info("组织机构管理修改参数:" + JSON.toJSONString(voReq));
		try {
			if(!StringUtil.isNotBlank(voReq.getId())){
				throw new BossException("主键ID不能为空!");
			}
			checkEmpty(model,voReq);//数据校验
			model.clear();
			OriginVo originVo = (OriginVo) request.getAttribute(Constants.ORIGIN);
			model.putAll(orgSupport.update(voReq,originVo));
		} catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("组织机构管理修改请求异常"+e.getMessage(), e);
			new RespError(model, "组织机构管理修改失败!!!");
		}
	}
	
	/**
	 * 组织机构管理详情
	 * @author yfy
	 * @date: 2016年01月05日 上午11:38:04
	 * @param model
	 * @param request
	 * @param id
	 */
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public void detail(ModelMap model, HttpServletRequest request,Long id,String orgId) {
		LogCvt.info("组织机构管理详情查询条件:id:" + id +",orgId:"+orgId);
		try {
			if(!StringUtil.isNotBlank(id) && StringUtil.isBlank(orgId)){
				throw new BossException("主键ID或组织ID不能为空!");
			}
			model.clear();
			model.putAll(orgSupport.detail(id,orgId));
		} catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("组织机构管理详情查询请求异常"+e.getMessage(), e);
			new RespError(model, "组织机构管理详情查询失败!!!");
		}
	}
	
	/**
	 * 组织机构管理删除
	 * @author yfy
	 * @date: 2016年01月05日 下午13:16:12
	 * @param model
	 * @param request
	 * @param id
	 */
	@Auth(keys={"boss_org_menu"})
	@RequestMapping(value = "delete", method = RequestMethod.DELETE)
	public void delete(ModelMap model, HttpServletRequest request,Long id) {
		LogCvt.info("组织机构管理删除条件:" + JSON.toJSONString(id));
		try {
			if(!StringUtil.isNotBlank(id)){
				throw new BossException("主键ID不能为空!");
			}
			model.clear();
			OriginVo originVo = (OriginVo) request.getAttribute(Constants.ORIGIN);
			model.putAll(orgSupport.delete(id,originVo));
		} catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("组织机构管理删除请求异常"+e.getMessage(), e);
			new RespError(model, "组织机构管理删除失败!!!");
		}
	}
	
	/**
	 * 组织级别类型查询
	 * @author yfy
	 * @date: 2016年01月06日 上午09:11:50
	 * @param model
	 * @param request
	 * @param dicCode
	 * @param clientId
	 */
	@RequestMapping(value = "level", method = RequestMethod.GET)
	public void selectLevel(ModelMap model, HttpServletRequest request,String dicCode,String clientId) {
		LogCvt.info("组织级别裂类型查询参数:dicCode："+dicCode+",clientId:" + clientId);
		try {
			model.clear();
			model.putAll(orgSupport.selectLevel(dicCode,clientId));
		} catch (Exception e) {
			LogCvt.error("组织级别裂类型查询请求异常"+e.getMessage(), e);
			new RespError(model, "组织级别裂类型查询失败!!!");
		}
	}
	
	/**
	 * 校验信息
	 * @param model
	 * @param voReq
	 * @throws BossException 
	 */
	public void checkEmpty(ModelMap model, OrgVoReq voReq) throws BossException{
		
		if(voReq.getPlatform().equals(Platform.bank.getCode())){
			if(StringUtil.isBlank(voReq.getClientId())){
				throw new BossException("客户端ID不能为空!");
			}
			if(StringUtil.isBlank(voReq.getCode())){
				throw new BossException("组织编码不能为空!");
			}
			if(!StringUtil.isNotBlank(voReq.getAreaId())){
				throw new BossException("地区不能为空!");
			}
			if(StringUtil.isBlank(voReq.getAddress())){
				throw new BossException("地址信息不能为空!");
			}
			if(voReq.getLevel().equals(2)){
				if(StringUtil.isBlank(voReq.getProCode())){
					throw new BossException("一级组织编码不能为空!");
				}
			}else if(voReq.getLevel().equals(3)){
				if(StringUtil.isBlank(voReq.getProCode())){
					throw new BossException("一级组织编码不能为空!");
				}
				if(StringUtil.isBlank(voReq.getCityCode())){
					throw new BossException("二级组织编码不能为空!");
				}
			}
		}
		
	}
}
