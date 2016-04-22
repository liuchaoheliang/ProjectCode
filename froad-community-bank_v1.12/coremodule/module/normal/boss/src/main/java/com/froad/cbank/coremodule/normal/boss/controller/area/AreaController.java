package com.froad.cbank.coremodule.normal.boss.controller.area;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.support.area.AreaSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 类描述：地区相关接口
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2015年5月12日 下午4:31:34 
 */
@Controller
@RequestMapping
public class AreaController {

	@Resource
	private AreaSupport areaSupport;
	
	/**
	  * 方法描述：区域列表查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年5月12日 下午4:33:02
	  */
	@RequestMapping(value = "/area", method = RequestMethod.GET )
	public void deliveryList(ModelMap model, HttpServletRequest request ,String clientId, Long parentId ) {
		LogCvt.info("区域列表查询请求参数：[clientId]:"+clientId+"[parentId] :"+parentId);
		try {
			model.putAll(areaSupport.getAreaList(clientId, parentId));
		} catch (Exception e) {
			 LogCvt.error("区域列表查询请求异常"+e.getMessage(), e);
			 new RespError(model, "区域列表查询失败!!!");
		}
	}
	
	/**
	 * 根据客户端ID联动获取地市级地区
	 * @path /area/list
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年10月10日 上午11:11:27
	 */
	@RequestMapping(value = "/area/list", method = RequestMethod.GET)
	public void getAreaListByClientId(ModelMap model, String clientId) {
		try {
			if(StringUtil.isBlank(clientId)) {
				throw new BossException("客户端ID为空");
			}
			model.clear();
			model.putAll(areaSupport.getAreaListByClientId(clientId));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
}
