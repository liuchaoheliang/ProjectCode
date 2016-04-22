package com.froad.cbank.coremodule.normal.boss.controller.product;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.annotation.ImpExp;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.product.ProductCategoryVoReq;
import com.froad.cbank.coremodule.normal.boss.support.product.ProductCategorySupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.ErrorEnums;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;
import com.froad.thrift.vo.OriginVo;

/**
 * 商品分类相关接口
 * @author liaopeixin
 * 2015-9-17
 */

@Controller
@RequestMapping(value="/productCategory")
public class ProductCategoryController{

	@Resource
	private ProductCategorySupport productCategorySupport;

	/**
	 * 	商品分类列表
	 * @author liaopeixin
	 * @param model
	 * @param clientId
	 */
	@Auth(keys={"boss_product_ca_menu","boss_vipmall_goods_ca_menu"})
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void findProCategoryList(ModelMap model,HttpServletRequest request,String clientId,boolean isMall){
		LogCvt.info("商品分类列表查询条件  clientId:" + clientId+" isMall:"+isMall);
		try {
			if(!StringUtils.isNotBlank(clientId)){
				clientId="";
			}
			model.clear();
			model.putAll(productCategorySupport.queryProCategoryList(clientId,(OriginVo)request.getAttribute(Constants.ORIGIN),isMall));
		} catch (Exception e) {
			LogCvt.error("商品分类列表查询请求异常"+e.getMessage(), e);
			new RespError(model, "商品分类列表查询失败!!!");
		}
	}
	/**
	 * 获取普通商品的商品分类
	 * <p>功能简述：根据父类Id查询商品分类</p> 
	 * <p>创建时间：2015年4月28日下午6:10:56</p>
	 * <p>作者: 陈明灿</p>
	 * @param model
	 * @param parentId
	 */
	@RequestMapping(value = "lt", method = RequestMethod.GET)
	public void queryCategoryById(ModelMap model, String parentId,String clientId) {
		 LogCvt.info("根据Id查询商品分类请求参数上级id: " + parentId + "客户端id: " + clientId);
		try {
			model.clear();
			if (StringUtil.isBlank(parentId)) {
				new RespError(model, "请求参数不能为空!!!");
				return;
			}
			model.putAll(productCategorySupport.queryCategoryById(Long.valueOf(parentId),
					clientId));
		} catch (Exception e) {
			LogCvt.error("根据父类id查询商品分类请求异常"+e.getMessage(), e);
			new RespError(model, "根据父类id查询商品分类失败!!!");
		}
	}
	
	/**
	 * 获取精品商品的商品分类
	 * @author songzichao
	 * @param model
	 * @param parentId
	 */
	@RequestMapping(value = "boutiquelt", method = RequestMethod.GET)
	public void queryBoutiqueGoodsCategoryById(ModelMap model, String parentId,String clientId) {
		 LogCvt.info("根据Id查询精品商品分类请求参数上级id: " + parentId + "客户端id: " + clientId);
		try {
			model.clear();
			if (StringUtil.isBlank(parentId)) {
				new RespError(model, "请求参数不能为空!!!");
				return;
			}
			model.putAll(productCategorySupport.queryBoutiqueGoodsCategoryById(Long.valueOf(parentId),
					clientId));
		} catch (Exception e) {
			LogCvt.error("根据父类id查询精品商品分类请求异常"+e.getMessage(), e);
			new RespError(model, "根据父类id查询精品商品分类失败!!!");
		}
	}
	
	
	/**
	 * @author liaopeixin
	 * 商品分类详情
	 */
	@RequestMapping(value ="/detail", method = RequestMethod.GET)
	public void proCategoryDetails(ModelMap model,HttpServletRequest request,String id,String clientId){
		LogCvt.info("商品分类详情查询条件:" + id+"---clientId:"+clientId);
		try {
			if(!StringUtils.isNotBlank(id)){
				new RespError(model, "商品分类ID不能为空!");
				return;
			}
			model.clear();
			model.put("proCategory",productCategorySupport.queryProCategoryDetails(id,clientId,(OriginVo)request.getAttribute(Constants.ORIGIN)));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("商品分类详情查询请求异常"+e.getMessage(), e);
			new RespError(model, "商品分类详情查询失败!!!");
		}
	}
	/**
	 * @author liaopeixin
	 * 商品分类新增
	 */
	@Auth(keys={"boss_product_ca_add","boss_vipmall_goods_ca_add"})
	@RequestMapping(value ="/add", method = RequestMethod.POST)
	public void addProCategory(ModelMap model,@RequestBody ProductCategoryVoReq categoryReq,HttpServletRequest request){
		LogCvt.info("商品分类新增条件:" + JSON.toJSONString(categoryReq));
		try{
			model.putAll(productCategorySupport.addOrUpdate(categoryReq,(OriginVo)request.getAttribute(Constants.ORIGIN)));
		}catch(BossException e){
			new RespError(model, e);
		}catch(Exception e){
			LogCvt.error("新增商品分类请求异常"+e.getMessage(), e);
			new RespError(model, "新增商品分类失败!!!");
		}
	}
	/**
	 * @author liaopeixin
	 * 商品分类编辑
	 */
	@Auth(keys={"boss_product_ca_modify","boss_vipmall_goods_ca_modify"})
	@RequestMapping(value ="/update", method = RequestMethod.POST)
	public void updateProCategory(ModelMap model,@RequestBody ProductCategoryVoReq categoryReq,HttpServletRequest request){
		LogCvt.info("商品分类修改条件:" + JSON.toJSONString(categoryReq));
		try{
			model.putAll(productCategorySupport.addOrUpdate(categoryReq,(OriginVo)request.getAttribute(Constants.ORIGIN)));
		}catch(BossException e){
			new RespError(model, e);
		}catch(Exception e){
			LogCvt.error("修改商品分类请求异常"+e.getMessage(), e);
			new RespError(model, "修改商品分类失败!!!");
		}
	}
	
	/**
	 * 获取所有商品分类(普通商品分类)
	 * @path /productCategory/
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年10月12日 下午6:04:40
	 * @param model
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public void getAllProCategory(ModelMap model, HttpServletRequest req, String clientId) {
		LogCvt.info("获取所有普通商品分类条件    clientId:" + clientId + "  isMall:false");
		try {
			if(StringUtil.isBlank(clientId)) {
				throw new BossException("客户端ID为空");
			}
			OriginVo org = (OriginVo) req.getAttribute(Constants.ORIGIN);
			model.clear();
			model.putAll(productCategorySupport.getAllProCategory(clientId, org, false));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 获取所有商品分类（精品商品分类）
	 * @path /productCategory/
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年10月12日 下午6:04:40
	 * @param model
	 */
	@RequestMapping(value = "/allMall", method = RequestMethod.GET)
	public void getAllMallProCategory(ModelMap model, HttpServletRequest req, String clientId) {
		LogCvt.info("获取所有商品分类条件    clientId:" + clientId + "  isMall:false");
		try {
			if(StringUtil.isBlank(clientId)) {
				throw new BossException("客户端ID为空");
			}
			OriginVo org = (OriginVo) req.getAttribute(Constants.ORIGIN);
			model.clear();
			model.putAll(productCategorySupport.getAllProCategory(clientId, org, true));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 商品分类导出接口
	 * @path /productCategory/export
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年10月28日 下午2:21:51
	 * @param model
	 */
	@Auth(keys={"boss_product_ca_export","boss_vipmall_goods_ca_export"})
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void exportProductCategory(ModelMap model,String exportType, HttpServletRequest req, String clientId, Long categoryId) {
		LogCvt.info("商品分类导出条件   exportType:"+exportType +" clientId:"+clientId +" categoryId:"+categoryId);
		try {
			if(StringUtil.isBlank(clientId)) {
				throw new BossException("客户端ID为空");
			}
			OriginVo org = (OriginVo)req.getAttribute(Constants.ORIGIN);
			model.clear();
			model.putAll(productCategorySupport.exportProductCategory(org,exportType,clientId, categoryId));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 商品分类的商品导入
	 * @path /productCategory/import
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年10月28日 下午4:31:22
	 * @param req
	 * @param res
	 * @param file
	 * @throws IOException
	 */
	@ImpExp
	@Auth(keys={"boss_product_ca_import","boss_vipmall_goods_ca_import"})
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public void importProductCategory(HttpServletRequest req, HttpServletResponse res, @RequestParam(value = "file") MultipartFile file,String exportType) throws IOException {
		LogCvt.info("商品分类导入条件的类型是  exportType: "+exportType);
		Map<String, Object> map = new HashMap<String, Object>();
		String json = "";
		try {
			OriginVo org = (OriginVo)req.getAttribute(Constants.ORIGIN);
			res.setCharacterEncoding("UTF-8");
			res.setContentType("text/html");
			map = productCategorySupport.importProductCategory(file,org,exportType);
			json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("RESPONE: " + json);
		} catch (BossException e) {
			map.put("code", e.getCode());
			map.put("message", e.getMsg());
			res.setStatus(608);
			json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("excelUpload-RESPONE：" + json);
			LogCvt.error(e.getMessage(), e);
		} catch (Exception e) {
			map.put("code", ErrorEnums.fail.getCode());
			map.put("message", ErrorEnums.fail.getMsg());
			res.setStatus(608);
			json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("excelUpload-RESPONE：" + json);
			LogCvt.error(e.getMessage(), e);
		}
	}
}
