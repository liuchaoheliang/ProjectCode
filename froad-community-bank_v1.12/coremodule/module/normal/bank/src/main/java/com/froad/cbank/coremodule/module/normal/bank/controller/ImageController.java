package com.froad.cbank.coremodule.module.normal.bank.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.exception.BankException;
import com.froad.cbank.coremodule.module.normal.bank.service.PresaleProductService;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.vo.FileVo;
import com.froad.thrift.service.BankOperatorService;
import com.froad.thrift.service.OrgUserRoleService;
import com.froad.thrift.vo.BankOperatorCheckVoRes;

/**
 * 图片
 * 
 * @ClassName ImageController
 * @author zxl
 * @date 2015年4月8日 上午11:31:18
 */
@Controller
@RequestMapping(value = "/img")
public class ImageController {
	
	@Resource
	private PresaleProductService presaleProductService;
	@Resource
	BankOperatorService.Iface bankOperatorService;
	@Resource
	OrgUserRoleService.Iface orgUserRoleService;
	
	/**
	 * 图片上传
	 * 
	 * @tilte upload
	 * @author zxl
	 * @date 2015年4月8日 上午11:35:25
	 * @param model
	 * @param file
	 * @throws IOException
	 */	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public void upload(HttpServletRequest req, HttpServletResponse res,String userId,String token,@RequestParam(value="file") MultipartFile[] file) throws IOException {
		
		String json = "";
		try {

			res.setCharacterEncoding("UTF-8");
			res.setContentType("text/html");
			// 校验token
			String clientId = req.getAttribute(Constants.CLIENT_ID)+"";
			if(!StringUtil.isNotEmpty(userId) || !StringUtil.isNotEmpty(token)){
				throw new BankException(EnumTypes.timeout.getCode(),EnumTypes.timeout.getMessage());
			}
//			String bankUserLoginFlag = req.getAttribute(Constants.FLAG)+"";
			LogCvt.info("img/upload里面的checkToken参数：clientId: " + clientId
					+ " userId:" + userId + " token:" + token);
//			if(bankUserLoginFlag!=null && "1".equals(bankUserLoginFlag)){
//				String orgCode = req.getAttribute(Constants.CODE)+"";
//				String username = req.getAttribute(Constants.USERNAME)+"";
			// LogCvt.info("img/upload里面银行用户登录的checkToken参数：orgCode: " +
			// orgCode+" username:"+username);
//				OrgUserRoleCheckVoRes orgUserRoleCheckVoRes = orgUserRoleService.checkToken(clientId, orgCode, username, token);//
//				if(!orgUserRoleCheckVoRes.getResult().getResultCode().equals(EnumTypes.success.getCode())){
//					throw new BankException(EnumTypes.timeout.getCode(),orgUserRoleCheckVoRes.getResult().getResultDesc());
//				}
//			}else{
			LogCvt.info("img/upload里面银行管理平台登录的checkToken参数");
				BankOperatorCheckVoRes resVo = bankOperatorService.checkToken(clientId,Long.valueOf(userId), token);
				if(!resVo.getResult().getResultCode().equals(EnumTypes.success.getCode())){
					throw new BankException(EnumTypes.timeout.getCode(),resVo.getResult().getResultDesc());
				}				
//			}
			if(file != null){
				// 图片格式校验
				presaleProductService.checkImage(file);
				// 上传
				ArrayList<FileVo> map = presaleProductService.imgUpload(file);
				json = JSON.toJSONString(map);
				res.getWriter().write(json);
				LogCvt.info("RESPONE: " + json);
			}else{
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("code", EnumTypes.fail.getCode());
				map.put("message", "请先选择上传的图片");
				res.setStatus(608);
				json = JSON.toJSONString(map);
				res.getWriter().write(json);
				LogCvt.info("RESPONE: " + json);
			}
		} catch (BankException e) {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("code", e.getCode());
			map.put("message", e.getMessage());
			res.setStatus(608);
			json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("RESPONE: " + json);
		} catch (Exception e) {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("code", EnumTypes.thrift_err.getCode());
			map.put("message", EnumTypes.thrift_err.getMessage());
			res.setStatus(608);
			json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("RESPONE: " + json);
		}
		
	}
	
}
