package com.froad.cbank.coremodule.normal.boss.controller.product;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.product.SeckillProductSetVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.product.SeckillProductVoReq;
import com.froad.cbank.coremodule.normal.boss.support.product.ProductSeckillSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 秒杀商品管理
 * 
 * @author 
 * @date 2015-4-21 下午03:29:06
 */
@Controller
@RequestMapping(value = "/seckillProduct")
public class SeckillProductController{

	@Resource
	private ProductSeckillSupport productSeckillSupport;

	/**
	 * @Title: 查询非秒杀商品列表
	 * @author chenhao
	 * @date 2015-5-13 下午01:15:58
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/nslt", method = RequestMethod.GET)
	public void QueryProductNotSeckill(ModelMap model, HttpServletRequest req,SeckillProductVoReq voReq) {
		LogCvt.info("非秒杀商品列表查询条件请求参数:", JSON.toJSONString(voReq));
		try {
			model.clear();
			model.putAll(productSeckillSupport.nslist(voReq));
		} catch(BossException e){
			new RespError(model,e);
		} catch (Exception e) {
			LogCvt.error("非秒杀商品信息列表条件查询请求异常"+e.getMessage(), e);
			new RespError(model, "非秒杀商品信息列表条件查询失败!!!");
		}
	}
	
	/**
	 * @Title: 查询秒杀商品列表
	 * @author chenhao
	 * @date 2015-5-13 下午04:15:58
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/lt", method = RequestMethod.GET)
	public void QuerySeckillProduct(ModelMap model, HttpServletRequest req,SeckillProductVoReq voReq) {
		LogCvt.info("秒杀商品列表查询条件请求参数:", JSON.toJSONString(voReq));
		try {
			model.clear();
			model.putAll(productSeckillSupport.list(voReq));
		} catch(BossException e){
			new RespError(model,e);
		} catch (Exception e) {
			LogCvt.error("秒杀商品信息列表条件查询请求异常"+e.getMessage(), e);
			new RespError(model, "秒杀商品信息列表条件查询失败!!!");
		}

	}

	/**
	 * @Title: 秒杀商品批量设置
	 * @author chenhao
	 * @date 2015-5-13 下午02:45:58
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/ad", method = RequestMethod.POST)
	public void seckillProductBatchSet(ModelMap model, HttpServletRequest req,
			@RequestBody SeckillProductSetVoReq voReq) {
		LogCvt.info("新增秒杀商品请求参数:" + JSON.toJSONString(voReq));
		try {
			model.clear();
			productSeckillSupport.verify(voReq);
			model.putAll(productSeckillSupport.add(voReq));
		} catch(BossException e){
			new RespError(model,e);
		} catch (Exception e) {
			LogCvt.error("秒杀商品批量设置请求异常"+e.getMessage(), e);
			new RespError(model, "秒杀商品批量设置失败!!!");
		}

	}
	
	/**
	 * @Title: 秒杀商品修改
	 * @author chenhao
	 * @date 2015-5-13 上午09:51:58
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/ue", method = RequestMethod.PUT)
	public void seckillProductUpdate(ModelMap model, HttpServletRequest req, @RequestBody SeckillProductVoReq voReq) {
		LogCvt.info("修改秒杀商品请求参数:" + JSON.toJSONString(voReq));
		try {
			model.clear();
			productSeckillSupport.verify(voReq);
			model.putAll(productSeckillSupport.update(voReq));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("修改秒杀商品请求异常"+e.getMessage(), e);
			new RespError(model, "修改秒杀商品失败!!!");
		}

	}

	/**
	 * @Title:秒杀商品上/下架
	 * @author chenhao
	 * @date 2015-5-13 上午11:42:23
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/it", method = RequestMethod.PUT)
	public void seckillProduckUpDown(ModelMap model, HttpServletRequest req,@RequestBody SeckillProductVoReq voReq) {
		LogCvt.info("上/下架秒杀商品请求参数: " +JSON.toJSONString(voReq));
		String str = "1".equals(voReq.getIsMarketable()) ? "上" : "下";
		try {
			model.clear();
			if (StringUtil.isNotEmpty(voReq.getProductId())
					&& StringUtil.isNotEmpty(voReq.getIsMarketable()) 
					&& ("1".equals(voReq.getIsMarketable()) || "2".equals(voReq.getIsMarketable()))) {
				model.putAll(productSeckillSupport.updateStatus(voReq.getProductId(), voReq.getIsMarketable(),voReq.getClientId()));
			}
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("秒杀商品"+str+"架请求异常"+e.getMessage(), e);
			new RespError(model, "秒杀商品"+str+"架失败!!!");
		}
	}

	/**
	 * @Title:秒杀商品详情
	 * @author chenhao
	 * @date 2015-5-13 下午4:28:32
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/dl", method = RequestMethod.GET)
	public void seckillProductDetil(ModelMap model, HttpServletRequest req,String productId) {
		LogCvt.info("秒杀商品详情请求参数:productId：" + productId);
		try {
			if(StringUtil.isNotEmpty(productId)){
				model.putAll(productSeckillSupport.detail(productId));
			}else{
				new RespError(model, "秒杀商品信息productId不能为空!!!");
			}
		} catch(BossException e){
			new RespError(model,e);
		} catch (Exception e) {
			LogCvt.error("秒杀商品信息详请求异常"+e.getMessage(), e);
			new RespError(model, "秒杀商品信息请求失败!!!");
		}
	}

	/**
	 * @Title:秒杀商品删除
	 * @author chenhao
	 * @date 2015-5-13 下午5:33:25
	 * @param model
	 * @param req
	 * @param productId
	 */
	@RequestMapping(value = "/de", method = RequestMethod.DELETE)
	public void seckillProductDelete(ModelMap model, HttpServletRequest req,String productId, String clientId) {
		LogCvt.info("删除秒杀商品请求参数:productId:" + productId + ",clientId:" + clientId);
		try {
			if(StringUtil.isNotEmpty(productId) || StringUtil.isNotEmpty(clientId)){
				model.putAll(productSeckillSupport.delete(productId, clientId));
			}else{
				new RespError(model, "删除秒杀商品productId或clientId不能为空!!!");
			}
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("删除秒杀商品请求异常"+e.getMessage(), e);
			new RespError(model, "删除秒杀商品失败!!!");
		}
	}

}
