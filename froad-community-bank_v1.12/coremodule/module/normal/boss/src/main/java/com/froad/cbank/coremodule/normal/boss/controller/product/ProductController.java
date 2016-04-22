package com.froad.cbank.coremodule.normal.boss.controller.product;

import java.util.Date;

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
import com.froad.cbank.coremodule.normal.boss.pojo.BossUser;
import com.froad.cbank.coremodule.normal.boss.pojo.product.AuditProductReq;
import com.froad.cbank.coremodule.normal.boss.pojo.product.GiftProductVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.product.ProductAddReq;
import com.froad.cbank.coremodule.normal.boss.pojo.product.ProductListVo;
import com.froad.cbank.coremodule.normal.boss.pojo.product.UpdownProductReq;
import com.froad.cbank.coremodule.normal.boss.support.product.ProductSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;
import com.froad.thrift.vo.OriginVo;

/**
 * 类描述：商品管理相关接口
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2015年4月27日 下午2:43:55 
 */
@Controller
@RequestMapping(value="/product")
public class ProductController{

	@Resource
	private ProductSupport productSupport;


	/**
	  * 方法描述：商品列表
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月27日 下午1:42:15
	  */
	@Auth(keys={"boss_product_menu"})
	@RequestMapping(value = "lt", method = RequestMethod.GET)
	public void list(ModelMap model, HttpServletRequest request,ProductListVo listReq) {
		LogCvt.info("商品列表查询条件:" + JSON.toJSONString(listReq));
		try {
			model.clear();
			if(StringUtils.isBlank(listReq.getClientId())){
				throw new BossException("银行渠道不能为空!");
			}
			model.putAll(productSupport.bossList(listReq));
		} catch(BossException e){
			new RespError(model,e);
		} catch (Exception e) {
			LogCvt.error("商品列表查询请求异常"+e.getMessage(), e);
			new RespError(model, "商品列表查询商品详情失败!!!");
		}
		
	}
	
	
	
	
	/**
	  * 方法描述：商品详情
	  * @author: yfy
	  * @time: 2015年7月27日 下午11:54:01
	  */
	@RequestMapping(value = "/bossDetail", method = RequestMethod.GET)
	public void bossDetail(ModelMap model, HttpServletRequest request,String productId) {
		 LogCvt.info("商品详情请求参数:productId：" + productId);
		try {
			if(!StringUtils.isNotBlank(productId)){
				new RespError(model, "商品ID不能为空!");
				return;
			}
			model.clear();
			model.putAll(productSupport.bossDetail(productId));
		} catch (Exception e) {
			LogCvt.error("查询商品详情请求异常"+e.getMessage(), e);
			new RespError(model, "查询商品详情失败!!!");
		}
	}
	
	/**
	 * 商品新增
	 * @tilte add
	 * @author zxl
	 * @date 2015年7月27日 上午9:48:04
	 * @param model
	 * @param request
	 * @param req
	 */
	@Auth(keys={"boss_product_add"})
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(ModelMap model, HttpServletRequest request,@RequestBody ProductAddReq req) {
		try {
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("保存商品请求参数:" + JSON.toJSONString(req));
			}
			if(req.getImgList()==null||req.getImgList().size()==0){
				new RespError(model, "图片不能为空!");
				return;
			}
			if(req.getImgList().size()>5){
				new RespError(model, "图片最多保存5张!");
				return;
			}
			//有vip规则
			if(StringUtils.isNotBlank(req.getVipId())){
				if(req.getVipPrice()==null||req.getMaxVip()==null){
					new RespError(model, "vip价格及限购数量不能为空!");
					return;
				}
			}
			//自提
			if("1".equals(req.getDeliveryOption())||"2".equals(req.getDeliveryOption())){
				if(req.getOrgCodes()==null||req.getOutletIds()==null){
					new RespError(model, "提货机构及网点不能为空!");
					return;
				}
			}
			
			model.clear();
			model.putAll(productSupport.add(req,request));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 商品修改
	 * @tilte mdy
	 * @author zxl
	 * @date 2015年7月27日 上午9:52:16
	 * @param model
	 * @param request
	 * @param req
	 */
	@Auth(keys={"boss_product_modify"})
	@RequestMapping(value = "/mdy", method = RequestMethod.POST)
	public void mdy(ModelMap model, HttpServletRequest request,@RequestBody ProductAddReq req) {
		try {
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("修改商品请求参数:" + JSON.toJSONString(req));
			}
			if(StringUtils.isBlank(req.getProductId())){
				new RespError(model, "商品ID不能为空!");
				return;
			}
			if(req.getImgList()==null||req.getImgList().size()==0){
				new RespError(model, "图片不能为空!");
				return;
			}
			if(req.getImgList().size()>5){
				new RespError(model, "图片最多保存5张!");
				return;
			}
			//有vip规则
			if(StringUtils.isNotBlank(req.getVipId())){
				if(req.getVipPrice()==null||req.getMaxVip()==null){
					new RespError(model, "vip价格及限购数量不能为空!");
					return;
				}
			}
			//自提
			if("1".equals(req.getDeliveryOption())||"2".equals(req.getDeliveryOption())){
				if(req.getOrgCodes()==null||req.getOutletIds()==null){
					new RespError(model, "提货机构及网点不能为空!");
					return;
				}
			}
			
			model.clear();
			model.putAll(productSupport.mdy(req,request));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 商品删除
	 * @tilte deleteByIds
	 * @author zxl
	 * @date 2015年7月27日 上午11:18:21
	 * @param model
	 * @param request
	 * @param id
	 */
	@Auth(keys={"boss_product_delete"})
	@RequestMapping(value = "/del", method = RequestMethod.DELETE)
	public void deleteByIds(ModelMap model, HttpServletRequest request,String productId) {
		try {
			if (StringUtils.isBlank(productId)) {
				new RespError(model, "请选择要删除的商品!!!");
				return;
			}
			model.putAll(productSupport.deleteByIds(request,productId));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("删除商品请求异常"+e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 查询提货网点
	 * @tilte outlet
	 * @author zxl
	 * @date 2015年7月28日 上午9:24:22
	 * @param model
	 * @param request
	 * @param clientId 客户端id
	 * @param orgCode 机构号
	 */
	@RequestMapping(value = "/outlet", method = RequestMethod.GET)
	public void outlet(ModelMap model, HttpServletRequest request,String clientId,String orgCode) {
		try {
			model.clear();
			if (StringUtils.isBlank(clientId)) {
				new RespError(model, "客户端Id不为空!");
				return;
			}
			model.putAll(productSupport.outlet(request,clientId,orgCode));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * vip规则
	 * @tilte viprule
	 * @author zxl
	 * @date 2015年7月30日 上午10:56:36
	 * @param model
	 * @param request
	 * @param clientId
	 * @param orgCode
	 */
	@RequestMapping(value = "/viprule", method = RequestMethod.GET)
	public void viprule(ModelMap model, HttpServletRequest request,String clientId) {
		try {
			model.clear();
			if (StringUtils.isBlank(clientId)) {
				new RespError(model, "客户端Id不为空!");
				return;
			}
			model.putAll(productSupport.viprule(request,clientId));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	/**
	 * 审核商品
	 * @path /product/audit
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年7月29日 下午2:08:22
	 * @param model
	 */
	@Auth(keys={"boss_product_audit"})
	@RequestMapping(value = "/audit", method = RequestMethod.POST)
	public void auditProduct(ModelMap model, HttpServletRequest req, @RequestBody AuditProductReq pojo) {
		try {
			//获取操作用户
			BossUser boss = (BossUser) req.getAttribute(Constants.BOSS_USER);
			OriginVo org = (OriginVo) req.getAttribute(Constants.ORIGIN);
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("商品审核请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			if("2".equals(pojo.getAuditState()) && (pojo.getAuditRemark().length() < 1 || pojo.getAuditRemark().length() > 200)) {
				if(StringUtils.isNotEmpty(pojo.getAuditRemark())) {
					if(pojo.getAuditRemark().length() > 200) {
						throw new BossException("审核不通过备注限200字内");
					}
				} else {
					throw new BossException("审核不通过备注为空");
				}
			}
			pojo.setAuditTime(new Date().getTime());
			pojo.setUserId(boss.getId() + "");
			model.putAll(productSupport.auditProduct(pojo, org));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 商品上下架
	 * @path /product/updown
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年7月29日 下午5:23:05
	 */
	@Auth(keys={"boss_product_up","boss_product_down"})
	@RequestMapping(value = "/updown", method = RequestMethod.POST)
	public void upDown(ModelMap model, HttpServletRequest req, @RequestBody UpdownProductReq pojo) {
		try {
			OriginVo org = (OriginVo) req.getAttribute(Constants.ORIGIN);
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("商品上下架请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			model.putAll(productSupport.upDown(pojo, org));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 
	 * <p>功能简述：积分礼品商品列表</p> 
	 * <p>使用说明：条件查询积分礼品商品列表</p> 
	 * <p>创建时间：2015年5月9日上午9:47:26</p>
	 * <p>作者: 陈明灿</p>
	 * @param model
	 * @param request
	 */
	@Auth(keys={"boss_gift_menu"})
	@RequestMapping(value = "/gift/lt", method = RequestMethod.GET)
	public void giftList(ModelMap model, HttpServletRequest request,ProductListVo listReq) {
		LogCvt.info("积分礼品列表查询条件:" + JSON.toJSONString(listReq));
		try {
			model.clear();
			/**  这是一个积分礼品列表查询 , 积分礼品的类型为4  */
			listReq.setType("4");
			model.putAll(productSupport.list(listReq));
		} catch (Exception e) {
			LogCvt.error("积分礼品列表查询请求异常"+e.getMessage(), e);
			new RespError(model, "积分礼品列表查询商品详情失败!!!");
		}

	}

	/**
	  * 方法描述：商品详情
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月27日 下午1:44:01
	  */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public void detail(ModelMap model, HttpServletRequest request,String productId) {
		 LogCvt.info("商品详情请求参数:productId：" + productId);
		try {
			if(!StringUtils.isNotBlank(productId)){
				new RespError(model, "商品ID不能为空!");
				return;
			}
			model.clear();
			model.putAll(productSupport.detail(productId));
		} catch (Exception e) {
			LogCvt.error("查询商品详情请求异常"+e.getMessage(), e);
			new RespError(model, "查询商品详情失败!!!");
		}
	}
	
	/**
	 * 
	 * <p>功能简述：保存商品</p> 
	 * <p>使用说明：新增或者修改商品</p> 
	 * <p>创建时间：2015年4月29日下午2:01:56</p>
	 * <p>作者: 陈明灿</p>
	 * @param map
	 * @param request
	 * @param req
	 */
	@Auth(keys={"boss_gift_add","boss_gift_modify"})
	@RequestMapping(value = "/au", method = RequestMethod.POST)
	public void saveOrUpdateProduct(ModelMap model, HttpServletRequest request,
			@RequestBody GiftProductVoReq req) {
		LogCvt.info("保存商品请求参数:" + JSON.toJSONString(req));
		try {
			model.clear();
			model.putAll(productSupport.saveOrUpdate(req));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("保存商品请求异常"+e.getMessage(), e);
			new RespError(model, "保存商品失败!!!");
		}

	}
	
	/**
	 * 
	 * <p>功能简述：删除商品</p> 
	 * <p>使用说明：根据ids删除商品</p> 
	 * <p>创建时间：2015年4月29日下午3:35:51</p>
	 * <p>作者: 陈明灿</p>
	 */
	@Auth(keys={"boss_gift_delete"})
	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public void deleteByIds(ModelMap model, HttpServletRequest request,String[] ids) {
		try {
			if (null == ids) {
				new RespError(model, "请选择要删除的商品!!!");
				return;
			}
			model.putAll(productSupport.deleteByIds(ids));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("删除商品请求异常"+e.getMessage(), e);
			new RespError(model, "删除商品失败!!!");
		}
	}
	
	/**
	 * 
	 * <p>功能简述：商品上下架</p> 
	 * <p>使用说明：根据ids上下架商品</p> 
	 * <p>创建时间：2015年4月29日下午4:25:50</p>
	 * <p>作者: 陈明灿</p>
	 * @param model
	 * @param request
	 * @param ids
	 */
	@Auth(keys={"boss_gift_up","boss_gift_down"})
	@RequestMapping(value = "/son", method = RequestMethod.GET)
	public void sellOrNot(ModelMap model, HttpServletRequest request,String[] ids, String flag) {
		LogCvt.info("上/下架商品请求参数ids: " + JSON.toJSONString(ids) + "flag: "
				+ JSON.toJSONString(flag));
		try {
			if (null == ids || ids.length == 0) {
				new RespError(model, "请选择要删除的商品!!!");
				return;
			}
			if (null == flag) {
				new RespError(model, "请求参数有误!!!");
				return;
			}
			model.putAll(productSupport.sellOrNot(ids, flag));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("商品上下架操作请求异常"+e.getMessage(), e);
			new RespError(model, "商品上下架操作失败!!!");
		}
	}
}
