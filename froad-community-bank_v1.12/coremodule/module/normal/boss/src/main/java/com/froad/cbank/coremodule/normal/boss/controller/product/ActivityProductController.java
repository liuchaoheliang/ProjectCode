package com.froad.cbank.coremodule.normal.boss.controller.product;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.product.ActivityProductDetailReq;
import com.froad.cbank.coremodule.normal.boss.pojo.product.ActivityProductVoListReq;
import com.froad.cbank.coremodule.normal.boss.pojo.product.OperateActivityProductVo;
import com.froad.cbank.coremodule.normal.boss.support.product.ActivityProductSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 
 * <p>标题: 活动与商品相关操作的类</p>
 * <p>说明: 活动与商品相关操作的类</p>
 * <p>创建时间：2015年5月25日下午8:07:21</p>
 * <p>作者: 陈明灿</p>
 * <p>版本: 1.0</p>
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
@Controller
@RequestMapping(value = "activityProduct")
public class ActivityProductController{

	@Resource
	private ActivityProductSupport activityProductSupport;
	/**
	 * 
	 * <p>功能简述：查询与活动相关的商品列表</p> 
	 * <p>使用说明：0-查询已经绑定的商品列表;1-查询可以绑定的商品列表</p> 
	 * <p>创建时间：2015年5月26日上午11:27:41</p>
	 * <p>作者: 陈明灿</p>
	 * @param model
	 * @param request
	 * @param listReq
	 */
	@RequestMapping(method = RequestMethod.GET)
	public void list(ModelMap model, HttpServletRequest request,ActivityProductVoListReq listReq) {
		LogCvt.info("与活动相关的商品列表查询条件："+JSON.toJSONString(listReq));
		try {
			model.clear();
			// 查询已经绑定的商品列表
			if ("0".equals(listReq.getFlag())) {
				model.putAll(activityProductSupport.haveList(listReq));
			}
			// 查询可以绑定的商品列表
			if ("1".equals(listReq.getFlag())) {
				model.putAll(activityProductSupport.newList(listReq));
			}
		} catch (Exception e) {
			LogCvt.error("与活动相关的商品列表查询请求异常"+e.getMessage(), e);
			new RespError(model, "与活动相关的商品列表查询失败!!!");
		}
	}
	
	/**
	 * 
	 * <p>功能简述：绑定活动商品</p>
	 * <p>使用说明：绑定活动商品</p>
	 * <p>创建时间：2015年5月25日下午8:43:48</p>
	 * <p>作者: 陈明灿</p>
	 * @param model
	 * @param request
	 * @param activityProductVo
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void addProduct(ModelMap model, HttpServletRequest request,@RequestBody OperateActivityProductVo activityProductVo) {
		LogCvt.info("绑定商品请求参数:", JSON.toJSONString(activityProductVo));
		try {
			model.clear();
			List<String> ids = activityProductVo.getProductIds();
			if(ids.isEmpty()){
				new RespError(model, "商品id不能为空!!!");
				return;
			}
			model.putAll(activityProductSupport.addProduct(activityProductVo));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("绑定活动商品请求异常"+e.getMessage(), e);
			new RespError(model, "绑定活动商品失败!!!");
		}
	}

	/**
	 * 
	 * <p>功能简述：解绑活动商品</p>
	 * <p>使用说明：解绑活动商品</p>
	 * <p>创建时间：2015年5月25日下午8:43:48</p>
	 * <p>作者: 陈明灿</p>
	 * 
	 * @param model
	 * @param request
	 * @param activityProductVo
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public void removeProduct(ModelMap model, HttpServletRequest request,@RequestBody OperateActivityProductVo activityProductVo) {
		LogCvt.info("解绑商品请求参数:", JSON.toJSONString(activityProductVo));
		try {
			model.clear();
			model.putAll(activityProductSupport.removeProduct(activityProductVo));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("解绑活动商品请求异常"+e.getMessage(), e);
			new RespError(model, "解绑活动商品失败!!!");
		}
	}
	
	/**
	 * 商品送积分明细
	 * @tilte detail
	 * @author zxl
	 * @date 2015年6月15日 下午1:32:14
	 * @param model
	 * @param request
	 * @param listReq
	 */
	@Auth(keys={"boss_actitvity_p_dl_menu"})
	@RequestMapping(value="dl",method = RequestMethod.GET)
	public void detail(ModelMap model, HttpServletRequest request,ActivityProductDetailReq req) {
		try {
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("请求参数:"+JSON.toJSONString(req));
			}
			model.clear();
			if(StringUtils.isBlank(req.getClientId())){
				throw new BossException("银行渠道不能为空!");
			}
			model.putAll(activityProductSupport.detail(req));
		}catch(BossException e){
			new RespError(model,e);
		}catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}


}
