package com.froad.cbank.coremodule.normal.boss.controller.delivery;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.delivery.DeliverGoodsReq;
import com.froad.cbank.coremodule.normal.boss.support.delivery.DeliverGoodsSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 发货管理
 * @ClassName DeliverGoodsController
 * @author zxl
 * @date 2015年6月17日 下午2:31:07
 */
@Controller
@RequestMapping("/deliver/goods")
public class DeliverGoodsController {
	
	@Resource
	DeliverGoodsSupport deliverGoodsSupport;
	
	/**
	 * 发货列表查询
	 * @tilte detail
	 * @author zxl
	 * @date 2015年6月17日 下午2:35:09
	 * @param model
	 * @param request
	 * @param req
	 */
	@Auth(keys={"boss_presell_de_menu"})
	@RequestMapping(value="list",method = RequestMethod.GET)
	public void list(ModelMap model, HttpServletRequest request,DeliverGoodsReq req) {
		try {
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("请求参数:"+JSON.toJSONString(req));
			}
			model.clear();
			model.putAll(deliverGoodsSupport.list(req));
		}catch(BossException e){
			new RespError(model,e);
		}catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
}
