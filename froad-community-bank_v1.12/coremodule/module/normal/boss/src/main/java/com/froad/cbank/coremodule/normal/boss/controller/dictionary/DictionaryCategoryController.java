package com.froad.cbank.coremodule.normal.boss.controller.dictionary;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.dictionary.DictionaryCategoryAddReq;
import com.froad.cbank.coremodule.normal.boss.support.dictionary.DictionaryCategorySupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 字典分类
 * @ClassName DictionaryCategoryController
 * @author zxl
 * @date 2015年11月26日 下午1:59:41
 */
@Controller
@RequestMapping("/dicCategory")
public class DictionaryCategoryController {
	
	@Resource
	DictionaryCategorySupport dictionaryCategorySupport;
	
	/**
	 * 列表查询
	 * @tilte list
	 * @author zxl
	 * @date 2015年11月26日 下午2:51:27
	 * @param model
	 */
	@Auth(keys={"boss_dic_menu"})
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public void list(ModelMap model) {
		try {
			model.clear();
			model.putAll(dictionaryCategorySupport.list());
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 新增
	 * @tilte add
	 * @author zxl
	 * @date 2015年11月26日 下午2:51:39
	 * @param model
	 * @param request
	 * @param req
	 */
	@Auth(keys={"boss_dic_ca_add"})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public void add(ModelMap model, HttpServletRequest request,@RequestBody DictionaryCategoryAddReq req) {
		try {
			model.clear();
			model.putAll(dictionaryCategorySupport.add(request,req));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 修改
	 * @tilte mdy
	 * @author zxl
	 * @date 2015年12月2日 下午3:46:54
	 * @param model
	 * @param request
	 * @param req
	 */
	@Auth(keys={"boss_dic_ca_modify"})
	@RequestMapping(value = "mdy", method = RequestMethod.POST)
	public void mdy(ModelMap model, HttpServletRequest request,@RequestBody DictionaryCategoryAddReq req) {
		try {
			model.clear();
			if(req.getId() == null){
				new RespError(model, "id不能为空");
				return;
			}
			model.putAll(dictionaryCategorySupport.mdy(request,req));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 删除
	 * @tilte del
	 * @author zxl
	 * @date 2015年11月26日 下午2:51:44
	 * @param model
	 * @param request
	 * @param map
	 */
	@Auth(keys={"boss_dic_ca_delete"})
	@RequestMapping(value = "del", method = RequestMethod.POST)
	public void del(ModelMap model, HttpServletRequest request,@RequestBody Map<Object,Object> map) {
		try {
			if(StringUtils.isBlank((String)map.get("id"))){
				new RespError(model, "id不能为空");
				return;
			}
			model.clear();
			model.putAll(dictionaryCategorySupport.del(request,Long.parseLong((String)map.get("id"))));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 导出
	 * @tilte export
	 * @author zxl
	 * @date 2015年12月1日 下午4:54:27
	 * @param model
	 * @param request
	 * @param id
	 */
	@Auth(keys={"boss_dic_ca_export"})
	@RequestMapping(value = "export", method = RequestMethod.GET)
	public void export(ModelMap model, HttpServletRequest request,String id,String type) {
		try {
			if(StringUtils.isBlank(id)){
				new RespError(model, "id不能为空");
				return;
			}
			if(StringUtils.isBlank(type)){
				new RespError(model, "导出类型不能为空");
				return;
			}
			model.clear();
			model.putAll(dictionaryCategorySupport.export(id,Integer.parseInt(type)));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
}
