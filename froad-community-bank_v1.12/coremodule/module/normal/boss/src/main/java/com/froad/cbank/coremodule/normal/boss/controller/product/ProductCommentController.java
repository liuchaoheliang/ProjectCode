package com.froad.cbank.coremodule.normal.boss.controller.product;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.product.ProductCommentVoReq;
import com.froad.cbank.coremodule.normal.boss.support.product.ProductCommentSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;


	/**
	 * 类描述：商品评论相关接口
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2015 
	 * @author: 刘超 liuchao@f-road.com.cn
	 * @time: 2015年4月27日 下午3:00:06 
	 */

@Controller
@RequestMapping(value ="/productComment")
public class ProductCommentController{
	
	@Resource
	private ProductCommentSupport productCommentSupport;
	/**
	  * 方法描述：商品评论列表
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月27日 下午1:42:15
	  */
	@Auth(keys={"boss_product_co_menu"})
	@RequestMapping(value = "lt", method = RequestMethod.GET)
	public void list(ModelMap model, HttpServletRequest request,ProductCommentVoReq listReq) {
		LogCvt.info("商品评价列表查询条件:" + JSON.toJSONString(listReq));
		try {
			model.clear();
			if(StringUtils.isBlank(listReq.getClientId())){
				throw new BossException("银行渠道不能为空!");
			}
			model.putAll(productCommentSupport.list(listReq));
		} catch(BossException e){
			new RespError(model,e);
		}catch (Exception e) {
			LogCvt.error("商品评价列表查询请求异常"+e.getMessage(), e);
			new RespError(model, "商品评价列表查询失败!!!");
		}
	}
	
	
	
	
	/**
	  * 方法描述：商品评论详情
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月27日 下午1:44:01
	  */
	@RequestMapping(value="detail", method=RequestMethod.GET)
	public void detail(ModelMap model, HttpRequest request){
		
		//	暂时该需求
	}

}
