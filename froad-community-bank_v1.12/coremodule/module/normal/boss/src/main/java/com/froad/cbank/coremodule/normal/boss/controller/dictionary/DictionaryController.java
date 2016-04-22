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
import com.froad.cbank.coremodule.normal.boss.pojo.dictionary.DictionaryAddReq;
import com.froad.cbank.coremodule.normal.boss.pojo.dictionary.DictionaryReq;
import com.froad.cbank.coremodule.normal.boss.support.dictionary.DictionarySupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 字典
 * @ClassName DictionaryController
 * @author zxl
 * @date 2015年11月26日 下午1:59:41
 */
@Controller
@RequestMapping("/dic")
public class DictionaryController {
	
	@Resource
	DictionarySupport dictionarySupport;
	
	/**
	 * 列表查询
	 * @tilte list
	 * @author zxl
	 * @date 2015年11月26日 下午2:51:27
	 * @param model
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public void list(ModelMap model,DictionaryReq req) {
		try {
			model.clear();
			model.putAll(dictionarySupport.list(req));
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
	@Auth(keys={"boss_dic_add"})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public void add(ModelMap model, HttpServletRequest request,@RequestBody DictionaryAddReq req) {
		try {
			model.clear();
			model.putAll(dictionarySupport.add(request,req));
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
	 * @date 2015年12月7日 上午11:42:46
	 * @param model
	 * @param request
	 * @param req
	 */
	@Auth(keys={"boss_dic_modify"})
	@RequestMapping(value = "mdy", method = RequestMethod.POST)
	public void mdy(ModelMap model, HttpServletRequest request,@RequestBody DictionaryAddReq req) {
		try {
			model.clear();
			if(req.getId() == null){
				new RespError(model, "id不能为空");
				return;
			}
			model.putAll(dictionarySupport.mdy(request,req));
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
	@Auth(keys={"boss_dic_delete"})
	@RequestMapping(value = "del", method = RequestMethod.POST)
	public void del(ModelMap model, HttpServletRequest request,@RequestBody Map<Object,Object> map) {
		try {
			if(StringUtils.isBlank((String)map.get("id"))){
				new RespError(model, "id不能为空");
				return;
			}
			model.clear();
			model.putAll(dictionarySupport.del(request,(String)map.get("id")));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
}
