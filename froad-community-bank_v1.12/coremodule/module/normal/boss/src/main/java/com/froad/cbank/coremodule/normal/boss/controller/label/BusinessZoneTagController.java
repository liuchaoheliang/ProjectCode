package com.froad.cbank.coremodule.normal.boss.controller.label;

import javax.annotation.Resource;

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
import com.froad.cbank.coremodule.normal.boss.pojo.label.BusinessZoneTagListVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.BusinessZoneTagReq;
import com.froad.cbank.coremodule.normal.boss.support.label.BusinessZoneTagSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 商圈标签管理controller
 * 
 * @author liaopeixin
 * @date 2015年10月23日 上午9:39:04
 */
@Controller
@RequestMapping("/businessZoneTag")
public class BusinessZoneTagController {

	@Resource
	private BusinessZoneTagSupport businessZoneTagSupport;

	/**
	 * 列表请求
	 * 
	 * @param model
	 * @param req
	 * @author liaopeixin
	 * @date 2015年10月23日 上午9:43:04
	 */
	@Auth(keys={"boss_label_tag_menu"})
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(ModelMap model, BusinessZoneTagListVoReq req) {
		try {
			LogCvt.info("商圈标签管理列表请求条件："+JSON.toJSONString(req));
			model.clear();
			model.putAll(businessZoneTagSupport.list(req));
		} catch (Exception e) {
			LogCvt.error("商圈标签列表查询失败"+e.getMessage(),e);
			new RespError(model, "商圈标签列表查询失败!!!");
		}
	}
	/**
	 * 商圈标签详情
	 * @param model
	 * @param id
	 * @author liaopeixin
	 *	@date 2015年10月26日 下午5:06:40
	 */
	@Auth(keys={"boss_label_tag_modify"})
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public void detail(ModelMap model, String id) {
		try{
			if(StringUtil.isNotBlank(id)){
				new RespError(model,"商圈标签ID不能为空！");
			}
			LogCvt.info("商圈标签管理详情请求条件：id="+id);
			model.clear();
			model.putAll(businessZoneTagSupport.details(id));
		}catch(BossException e){
			new RespError(model,e);
		}catch(Exception e){
			LogCvt.error("商圈标签管理详情查询失败"+e.getMessage(),e);
			new RespError(model,"商圈标签管理详情查询失败!!!");
		}
	}
	/**
	 * 商圈标签新增
	 * @param model
	 * @param req
	 * @author liaopeixin
	 *	@date 2015年10月26日 上午9:24:45
	 */
	@Auth(keys={"boss_label_tag_add"})
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(ModelMap model,@RequestBody BusinessZoneTagReq req) {
		try {
			LogCvt.info("商圈标签新增请求条件" + JSON.toJSONString(req));
			model.clear();
			model.putAll(businessZoneTagSupport.save(req));
		}catch(BossException e){ 
			new RespError(model, e);
		}catch (Exception e) {
			LogCvt.error("新增商圈标签失败" + e.getMessage(), e);
			new RespError(model, "新增商圈标签失败!!!");
		}
	}
	/**
	 * 商圈标签修改
	 * @param model
	 * @param req
	 * @author liaopeixin
	 *	@date 2015年10月26日 下午5:06:25
	 */
	@Auth(keys={"boss_label_tag_modify"})
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(ModelMap model,@RequestBody BusinessZoneTagReq req) {
		try {
			LogCvt.info("商圈标签修改请求条件" + JSON.toJSONString(req));
			model.clear();
			model.putAll(businessZoneTagSupport.save(req));
		}catch(BossException e){ 
			new RespError(model, e); 
		}catch (Exception e) {
			LogCvt.error("修改商圈标签失败" + e.getMessage(), e);
			new RespError(model, "修改商圈标签失败!!!");
		}
	}
	/**
	 * 商圈标签禁用
	 * @param model
	 * @param id
	 * @author liaopeixin
	 *	@date 2015年10月26日 下午5:06:14
	 */
	@Auth(keys={"boss_label_tag_diable"})
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public void delete(ModelMap model, String id) {
		try{
			if(StringUtil.isNotBlank(id)){
				new RespError(model,"商圈标签ID不能为空！");
			}
			LogCvt.info("商圈标签禁用请求条件：id="+id);
			model.clear();
			model.putAll(businessZoneTagSupport.delete(id));
		}catch(BossException e){
			new RespError(model,e);
		}catch(Exception e){
			LogCvt.error("商圈标签禁用失败"+e.getMessage(),e);
			new RespError(model,"商圈标签禁用失败!!!");
		}
	}

	/**
	 * 商圈标签商户导出
	 * @param model
	 * @param id
	 * @author liaopeixin
	 *	@date 2015年10月26日 上午9:40:54
	 */
	@Auth(keys={"boss_label_tag_export"})
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void export(ModelMap model, String id) {
		try{
			if(StringUtil.isNotBlank(id)){
				new RespError(model,"商圈标签ID不能为空！");
			}
			LogCvt.info("商圈标签导出请求条件：id="+id);
			model.clear();
			model.putAll(businessZoneTagSupport.export(id));
		}catch(BossException e){
			new RespError(model,e);
		}catch(Exception e){
			LogCvt.error("商圈标签导出失败"+e.getMessage(),e);
			new RespError(model,"商圈标签导出失败!!!");
		}
	}
}
