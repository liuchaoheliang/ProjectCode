package com.froad.cbank.coremodule.module.normal.merchant.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.ImageDel_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Image_Info_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.MerchantUser;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.UploadCrop_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.UploadDel_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.UploadSaveCrop_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.UploadToken_Req;
import com.froad.cbank.coremodule.module.normal.merchant.service.Common_Service;
import com.froad.cbank.coremodule.module.normal.merchant.service.Image_Service;
import com.froad.cbank.coremodule.module.normal.merchant.service.Outlet_Service;
import com.froad.cbank.coremodule.module.normal.merchant.utils.Constants;
import com.froad.cbank.coremodule.module.normal.merchant.utils.EnumTypes;
import com.froad.cbank.coremodule.module.normal.merchant.utils.HttpServer;
import com.froad.cbank.coremodule.module.normal.merchant.utils.RespError;
import com.froad.thrift.vo.PlatType;

/**
 * 图片
 * @ClassName ImageController
 * @author zxl
 * @date 2015年4月8日 上午11:31:18
 */
@Controller
@RequestMapping(value = "/img")
public class ImageController {
	
	@Resource
	Common_Service common_Service;
	
	@Resource
	Outlet_Service outlet_Service;
	
	@Resource
	Image_Service image_Service;
	/**
	 * 图片上传
	 * @tilte upload
	 * @author zxl
	 * @date 2015年4月8日 上午11:35:25
	 * @param model
	 * @param f
	 * @throws IOException 
	 */
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public void upload(HttpServletResponse response, @RequestParam(value="file") MultipartFile[] file,HttpServletRequest request) throws IOException {
		String json = "";
		response.setCharacterEncoding("UTF-8");
		//兼容IE
		response.setContentType("text/html");
		try {
			//登录检查
			common_Service.verifyUser(request,request.getParameter("token"),request.getParameter("uid"));
			//图片格式检查
			common_Service.checkImage(file);
			//上传
			ArrayList<Image_Info_Res> al = common_Service.imgUpload(file);
			json = JSON.toJSONString(al);
		} catch (MerchantException e) {
			HashMap<String,String> m = new HashMap<String, String>();
			m.put("code", e.getCode());
			m.put("message", e.getMsg());
			response.setStatus(608);
			json = JSON.toJSONString(m);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			HashMap<String,String> m = new HashMap<String, String>();
			m.put("code", EnumTypes.syserr.getCode());
			m.put("message", EnumTypes.syserr.getMsg());
			response.setStatus(608);
			json = JSON.toJSONString(m);
		} finally{
			LogCvt.info("RESPONE: "+json);
			response.getWriter().write(json);
			response.flushBuffer();
		}
	}
	
	/**
	 * 单图片上传
	 * @tilte uploadSimple
	 * @author zxl
	 * @date 2015年4月13日 下午2:03:46
	 * @param model
	 * @param file
	 * @throws IOException 
	 */
	@RequestMapping(value = "uploadOne", method = RequestMethod.POST)
	public void uploadSimple(HttpServletResponse response, @RequestParam(value="file") MultipartFile file,HttpServletRequest request) throws IOException {
		String json = "";
		response.setCharacterEncoding("UTF-8");
		//兼容IE
		response.setContentType("text/html");
		try {
			//登录检查
			common_Service.verifyUser(request,request.getParameter("token"),request.getParameter("uid"));
			//图片格式检查
			common_Service.checkImage(file);
			//上传
			ArrayList<Image_Info_Res> al = common_Service.imgUploadSimple(file);
			json = JSON.toJSONString(al);
		} catch (MerchantException e) {
			HashMap<String,String> m = new HashMap<String, String>();
			m.put("code", e.getCode());
			m.put("message", e.getMsg());
			response.setStatus(608);
			json = JSON.toJSONString(m);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			HashMap<String,String> m = new HashMap<String, String>();
			m.put("code", EnumTypes.syserr.getCode());
			m.put("message", EnumTypes.syserr.getMsg());
			response.setStatus(608);
			json = JSON.toJSONString(m);
		} finally{
			LogCvt.info("RESPONE: "+json);
			response.getWriter().write(json);
			response.flushBuffer();
		}
	}

	
	@RequestMapping(value = "token", method = RequestMethod.GET)
	public void uploadImage(ModelMap model,HttpServletRequest request, UploadToken_Req req){
		try {
			model.clear();
			model.putAll(image_Service.uploadImage(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	
	@RequestMapping(value = "crop", method = RequestMethod.POST)
	public void crop(ModelMap model,HttpServletRequest request,@RequestBody UploadCrop_Req req){
		try {
			model.clear();
			model.putAll(image_Service.crop(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	
	
	@RequestMapping(value = "saveCrop", method = RequestMethod.POST)
	public void saveCrop(ModelMap model,HttpServletRequest request,@RequestBody UploadSaveCrop_Req req){
		try {
			model.clear();
			model.putAll(image_Service.saveCrop(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	@RequestMapping(value = "del", method = RequestMethod.POST)
	public void del(ModelMap model,HttpServletRequest request,@RequestBody UploadDel_Req req){
		try {
			model.clear();
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			req.setIp(request.getAttribute(Constants.CLIENT_IP).toString());
			req.setPlatType((PlatType)request.getAttribute(Constants.PLAT_TYPE));
			model.putAll(image_Service.del(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	
	@RequestMapping(value = "imgDel", method = RequestMethod.POST)
	public void imgDel(HttpServletRequest request,HttpServletResponse response){
		try {
	        JSONArray jsonArray = JSONArray.parseArray(HttpServer.receivePost(request));  
	        List<ImageDel_Req> req=new ArrayList<ImageDel_Req>();
	        for (int i = 0; i < jsonArray.size(); i++) {
	        	ImageDel_Req image=JSON.parseObject(jsonArray.get(i).toString().trim(), ImageDel_Req.class);
	        	req.add(image);
	        }
	        HttpServer.sendResponseStr(response,image_Service.imgDel(req));
		}catch (Exception e) {
			HttpServer.sendResponseStr(response,HttpServer.error("9080", "接受http异常"));
			LogCvt.error(e.getMessage(), e);
		}
	}

}
