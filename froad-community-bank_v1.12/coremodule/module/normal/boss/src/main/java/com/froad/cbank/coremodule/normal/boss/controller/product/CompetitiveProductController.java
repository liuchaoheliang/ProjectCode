package com.froad.cbank.coremodule.normal.boss.controller.product;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.froad.cbank.coremodule.normal.boss.pojo.product.CompetiviProUpVo;
import com.froad.cbank.coremodule.normal.boss.pojo.product.CompetiviteProAddReq;
import com.froad.cbank.coremodule.normal.boss.pojo.product.ProductListVoReq;
import com.froad.cbank.coremodule.normal.boss.support.product.CompetiviteProSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 
 * @ClassName: CompetitiveProductController
 * @author chenzhangwei@f-road.com.cn
 * @createTime 2015年11月24日 下午1:30:55 
 * @desc <p>精品商城商品管理</p>
 */

@RequestMapping(value="/competitiveProduct")
@Controller
public class CompetitiveProductController {
	
	//注入精品商城support
	@Resource
	private CompetiviteProSupport competiviteProSupport;
	/**
	 * 
	 * <p>Title:精品商城商品列表查询 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: 精品商城商品列表查询
	 * @createTime 2015年11月24日 下午1:33:40 
	 * @param model
	 */
	@Auth(keys={"boss_vipmall_goods_menu"})
	@RequestMapping(value = "/lt", method = RequestMethod.GET)
	public void list(ModelMap model,ProductListVoReq req){
		LogCvt.info("精品商品列表查询条件:" + JSON.toJSONString(req));
		try {
			model.clear();
			model.putAll(competiviteProSupport.list(req));
		} catch (Exception e) {
			LogCvt.error("精品商品列表查询请求异常"+e.getMessage(), e);
			new RespError(model, "精品商品列表查询失败!!!");
		}
	}
	
	/**
	 * 
	 * <p>Title:精品商城商品详情查询 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2015年11月24日 下午1:36:46 
	 * @param model
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public void detail(ModelMap model,String proId){
		LogCvt.info("精品商城商品详情查询条件:" + JSON.toJSONString("商品id: "+proId));
		try {
			model.clear();
			model.putAll(competiviteProSupport.detail(proId));
		} catch (Exception e) {
			LogCvt.error("精品商品详情查询请求异常"+e.getMessage(), e);
			new RespError(model, "精品商品详情查询失败!!!");
		}
	}
	
	/**
	 * 
	 * <p>Title:精品商城商品新增 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: 精品商城商品新增
	 * @createTime 2015年11月24日 下午1:46:29 
	 * @param model
	 */
	@Auth(keys={"boss_vipmall_goods_add"})
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(ModelMap model,HttpServletRequest request,@RequestBody CompetiviteProAddReq req){

		LogCvt.info("精品商城商品新增条件:" + JSON.toJSONString(req));
		try {
			//如果批量发货为是时，需要校验预计发货时间不能为空
			if(req.getIsBatchDelivery()==true){
				if(req.getDeliveryTime()==null || req.getDeliveryTime()==""){
					throw new BossException("预计发货时间不能为空！");
				}
			}
			if(req.getImages().size()<0 || req.getImages().size()>6){
				throw new BossException("商品图片列表最多只能显示5张图片");
			}
		/*	if(req.getPrice()<0 || req.getPrice()>999999999.99){
				throw new BossException("商品价格必须大于0小于999999999.99");
			}
			if(req.getVipPrice()<0 || req.getVipPrice()>999999999.99){
				throw new BossException("vip价格必须大于0小于999999999.99");
			}
			if(req.getMarketPrice()<0|| req.getMarketPrice()>999999999.99){
				throw new BossException("市场价格必须大于0小于999999999.99");
			}*/
			model.clear();
			model.putAll(competiviteProSupport.add(request, req));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("精品商品新增请求异常"+e.getMessage(), e);
			new RespError(model, "精品商品新增失败!!!");
		}
	}
	
	/**
	 * 
	 * <p>Title:精品商城商品修改 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: 精品商城商品修改
	 * @createTime 2015年11月24日 下午1:47:00 
	 * @param model
	 */
	@Auth(keys={"boss_vipmall_goods_modify"})
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(ModelMap model,HttpServletRequest request,@RequestBody CompetiviteProAddReq req){
		LogCvt.info("精品商城商品修改条件:" + JSON.toJSONString(req));
		try {
			if(StringUtil.isBlank(req.getProductId())){
				throw new BossException("修改商品的id不能为空！");
			}
			//如果批量发货为是时，需要校验预计发货时间不能为空
			if(req.getIsBatchDelivery()==true){
				if(req.getDeliveryTime()==null || req.getDeliveryTime()==""){
					throw new BossException("预计发货时间不能为空！");
				}
			}
			model.clear();
			model.putAll(competiviteProSupport.update(request, req));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("精品商品修改请求异常"+e.getMessage(), e);
			new RespError(model, "精品商品修改失败!!!");
		}
	}
	
	/**
	 * 
	 * <p>Title:精品商城商品上架 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: 精品商城商品上架
	 * @createTime 2015年11月24日 下午1:47:26 
	 * @param model
	 */
	@Auth(keys={"boss_vipmall_goods_up"})
	@RequestMapping(value = "/up", method = RequestMethod.POST)
	public void up(ModelMap model,HttpServletRequest request,@RequestBody CompetiviProUpVo req){
		LogCvt.info("精品商城商品上架条件:" + JSON.toJSONString(req));
		try {
			model.clear();
			model.putAll(competiviteProSupport.updateGoodsStatusOn(request, req.getId()));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("精品商品上架请求异常"+e.getMessage(), e);
			new RespError(model, "精品商品上架失败!!!");
		}
	}
	
	/**
	 * 
	 * <p>Title:精品商城商品下架 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: 精品商城商品下架
	 * @createTime 2015年11月24日 下午1:47:53 
	 * @param model
	 */
	@Auth(keys={"boss_vipmall_goods_down"})
	@RequestMapping(value = "/down", method = RequestMethod.POST)
	public void down(ModelMap model,HttpServletRequest request, @RequestBody CompetiviProUpVo req){
		LogCvt.info("精品商城商品下架条件:" + JSON.toJSONString(req));
		try {
			model.clear();
			model.putAll(competiviteProSupport.updateGoodsStatusOff(request, req.getId()));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("精品商品下架请求异常"+e.getMessage(), e);
			new RespError(model, "精品商品下架失败!!!");
		}
	}
	
	
	/**
	 * 
	 * <p>Title:查询所有精品商品的一级分类 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2015年12月1日 下午1:40:11 
	 * @param model
	 * @param request
	 * @param clientId
	 * 
	 */
	
	@RequestMapping(value = "/findAllOneProCate", method = RequestMethod.GET)
	public void findAllOneProCategory(ModelMap model,HttpServletRequest request,String clientId,Boolean enable){
		LogCvt.info("精品商城商品一级分类查询条件:" + JSON.toJSONString(" cilentid: "+clientId));
		try {
			if(!StringUtils.isNotBlank(clientId)){
				clientId="";
			}
			model.clear();
			model.putAll(competiviteProSupport.findAllProOneCategory(clientId, request,enable));
		} catch (Exception e) {
			LogCvt.error("精品商城商品一级分类查询请求异常"+e.getMessage(), e);
			new RespError(model, "精品商城商品一级分类查询失败!!!");
		}
	}
	
	
	
}
