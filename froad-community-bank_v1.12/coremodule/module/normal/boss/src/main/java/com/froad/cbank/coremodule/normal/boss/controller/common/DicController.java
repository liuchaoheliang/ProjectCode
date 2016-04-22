package com.froad.cbank.coremodule.normal.boss.controller.common;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.support.common.DicSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;


/**
 * 字典查询
 * @ClassName DicController
 * @author zxl
 * @date 2016年1月14日 上午10:39:51
 */
@Controller
public class DicController {
	
	@Resource
	DicSupport dicSupport;
	
	@RequestMapping(value = "/dic/item", method = RequestMethod.GET)
	public void list(ModelMap model,String dicCode, String clientId) {
		try {
			model.clear();
			model.put("list", dicSupport.getItem(dicCode, clientId));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
}
