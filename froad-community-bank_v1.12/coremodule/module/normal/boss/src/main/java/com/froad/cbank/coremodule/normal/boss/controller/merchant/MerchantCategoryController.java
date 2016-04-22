package com.froad.cbank.coremodule.normal.boss.controller.merchant;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
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
import com.froad.cbank.coremodule.normal.boss.pojo.merchant.AddMerchantCategoryVo;
import com.froad.cbank.coremodule.normal.boss.pojo.merchant.DeleteMerchantReq;
import com.froad.cbank.coremodule.normal.boss.pojo.merchant.MerchantCategoryReq;
import com.froad.cbank.coremodule.normal.boss.pojo.merchant.MerchantCategoryRes;
import com.froad.cbank.coremodule.normal.boss.support.merchant.MerchantCategorySupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.ErrorEnums;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;
import com.froad.thrift.vo.OriginVo;



/**
 * 
 * 类描述:商户分类接口
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author "chenzhangwei"
 * @time 2015年9月17日 下午2:45:14
 * @email "chenzhangwei@f-road.com.cn"
 */
@Controller
@RequestMapping(value="/merchantCategory")
public class MerchantCategoryController{
	@Resource
	private MerchantCategorySupport merchantCategorySupport;
	
	/**
	 * 
	 * 方法描述:商户列表查询
	 * @author "chenzhangwei"
	 * @email "chenzhangwei@f-road.com.cn"
	 * @time 2015年9月17日 上午10:55:51
	 * @param model
	 * @param request
	 * @param voReq
	 */
	@Auth(keys={"boss_merchant_ca_menu"})
	@RequestMapping(value="lt", method=RequestMethod.GET)
	public void list(ModelMap model,HttpServletRequest request,MerchantCategoryReq voReq){
		if(voReq.getClientId()==null||voReq.getClientId()==""){
			//默认不查询
			voReq.setClientId("");
		}
		LogCvt.info("商户分类列表查询条件："+JSON.toJSONString(voReq));
		OriginVo originVo=(OriginVo) request.getAttribute(Constants.ORIGIN);
		try {
			model.clear();
			model.putAll(merchantCategorySupport.queryList(voReq, originVo));
		} catch (Exception e) {
			LogCvt.error("商户分类列表查询请求异常"+e.getMessage(), e);
			new RespError(model, "商户分类列表查询失败!!!");
		}			
	}
	
	
		
	/**
	 * 
	 * 方法描述:保存商户分类(添加或者修改)
	 * @author "chenzhangwei"
	 * @email "chenzhangwei@f-road.com.cn"
	 * @time 2015年9月17日 上午11:03:48
	 * @param voReq
	 * @param request
	 * @param model
	 */
	@Auth(keys={"boss_merchant_ca_add","boss_merchant_ca_modify"})
	@RequestMapping(value="ad", method=RequestMethod.POST)
	public void save(@RequestBody AddMerchantCategoryVo voReq ,HttpServletRequest request,ModelMap model){
		LogCvt.info("商户分类增加更新："+JSON.toJSONString(voReq));
		OriginVo originVo=(OriginVo) request.getAttribute(Constants.ORIGIN);
		try {
			model.clear();
			model.putAll(merchantCategorySupport.save(voReq,originVo));
		}catch(BossException e){
			new RespError(model, e);
		}catch (Exception e) {
			LogCvt.error("商户分类新增请求异常"+e.getMessage(), e);
			new RespError(model, "商户分类新增失败!!!");
		}
	}
	/**
	 * 
	 * 方法描述:删除商户分类
	 * @author "chenzhangwei"
	 * @email "chenzhangwei@f-road.com.cn"
	 * @time 2015年9月17日 下午4:01:31
	 * @param model
	 * @param request
	 * @param req
	 */
	@RequestMapping(value="delete", method=RequestMethod.GET)
	public void delete(ModelMap model,HttpServletRequest request,DeleteMerchantReq req){
		LogCvt.info("商户分类删除："+JSON.toJSONString(req));
		try{
			model.clear();
			model.putAll(merchantCategorySupport.delete(req));
		}catch(Exception e){
			LogCvt.error("商户分类删除请求异常"+e.getMessage(), e);
			new RespError(model, "商户分类删除失败!!!");
		}
	}
	
	/**
	 * 
	 * 方法描述:单个分类详细信息查询
	 * @author "chenzhangwei"
	 * @email "chenzhangwei@f-road.com.cn"
	 * @time 2015年9月22日 下午1:42:15
	 * @param model
	 * @param request
	 * @param id
	 * @param clientId
	 */
	@RequestMapping(value="details",method=RequestMethod.GET)
	public void details(ModelMap model,HttpServletRequest request,String id,String clientId){
		LogCvt.info("商户分类详细信息查询:"+id+"clientId:"+clientId);
		if(id==null||id==""){
			new RespError(model, "商户分类id不能为空");
		}
		if(clientId==""||clientId==null){
			new RespError(model, "客户端id不能为空");
		}
		MerchantCategoryRes res=new MerchantCategoryRes();
		try{
			model.clear();
			res=findMerchantCategoryById(request,id,clientId);
			model.put("merchant", res);
		}catch(Exception e){
			LogCvt.error("商户分类详细信息查询请求异常"+e.getMessage(), e);
			new RespError(model, "商户分类详细信息查询失败!!!");
		}
	}
	
	/**
	 * 
	 * 方法描述:查询单个商户分类信息
	 * @author "chenzhangwei"
	 * @email "chenzhangwei@f-road.com.cn"
	 * @time 2015年9月18日 下午1:29:06
	 * @param request
	 * @param id
	 * @return
	 * @throws TException 
	 * @throws NumberFormatException 
	 */
	private MerchantCategoryRes findMerchantCategoryById(HttpServletRequest request,String id,String clientId) throws Exception{
			return merchantCategorySupport.findMerchantCategoryById((OriginVo) request.getAttribute(Constants.ORIGIN), id,clientId);
	}
	
	/**
	 * 获取所有启用商户分类
	 * @path /merchantCategory/all
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年10月12日 下午5:47:22
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public void getAllMerchantCategory(ModelMap model, HttpServletRequest req, String clientId) {
		try {
			if(StringUtil.isBlank(clientId)) {
				throw new BossException("客户端ID为空");
			}
			OriginVo org = (OriginVo) req.getAttribute(Constants.ORIGIN);
			model.clear();
			model.putAll(merchantCategorySupport.getAllMerchantCategory(clientId, org));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 商户分类导出接口
	 * @path /merchantCategory/export
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年10月28日 下午2:21:51
	 * @param model
	 */
	@Auth(keys={"boss_merchant_ca_export"})
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void exportProductCategory(ModelMap model, HttpServletRequest req, String clientId, Long categoryId) {
		try {
			if(StringUtil.isBlank(clientId)) {
				throw new BossException("客户端ID为空");
			}
			OriginVo org = (OriginVo)req.getAttribute(Constants.ORIGIN);
			model.clear();
			model.putAll(merchantCategorySupport.exportMerchantCategory(org, clientId, categoryId));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 商户分类的商户导入
	 * @path /merchantCategory/import
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年10月28日 下午4:31:22
	 * @param req
	 * @param res
	 * @param file
	 * @throws IOException
	 */
	@ImpExp
	@Auth(keys={"boss_merchant_ca_import"})
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public void importProductCategory(HttpServletRequest req, HttpServletResponse res, @RequestParam(value = "file") MultipartFile file) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String json = "";
		try {
			res.setCharacterEncoding("UTF-8");
			res.setContentType("text/html");
			OriginVo org = (OriginVo)req.getAttribute(Constants.ORIGIN);
			map = merchantCategorySupport.importMerchantCategory(file, org);
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
	
	/**
	 * 获取父级分类列表
	 * @path /merchantCategory/parents
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年11月5日 下午7:44:51
	 */
	@RequestMapping(value = "/parents")
	public void getParentCategoryList(ModelMap model, String clientId, String categoryId, String type) {
		try {
			if(StringUtil.isBlank(clientId)) {
				throw new BossException("客户端ID为空");
			}
			if(StringUtil.isBlank(categoryId)) {
				throw new BossException("分类ID为空");
			}
			if(StringUtil.isBlank(type)) {//1.商户分类、2.商品分类
				throw new BossException("类型值为空");
			}
			model.clear();
			model.putAll(merchantCategorySupport.getParentCategoryList(clientId, categoryId, type));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
}
