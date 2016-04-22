package com.froad.cbank.coremodule.normal.boss.controller.provider;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.support.provider.ProviderSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 
 * @ClassName: ProViderController
 * @author chenzhangwei@f-road.com.cn
 * @createTime 2015年12月2日 下午5:17:08 
 * @desc <p>供应商controller</p>
 */
@Controller
@RequestMapping(value="/provider")
public class ProViderController {
	
	@Resource
	ProviderSupport providerSupport;
	
	/**
	 * 
	 * <p>Title:供应商列表查询 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2015年12月2日 下午5:24:28 
	 * @param model
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void findAllProvider(ModelMap model){
		LogCvt.info("供应商列表查询.......");
		try {
			model.clear();
			model.putAll(providerSupport.findAllProvider());
		} catch (Exception e) {
			LogCvt.error("供应商列表查询请求异常"+e.getMessage(), e);
			new RespError(model, "供应商列表查询失败!!!");
		}
	}
}
