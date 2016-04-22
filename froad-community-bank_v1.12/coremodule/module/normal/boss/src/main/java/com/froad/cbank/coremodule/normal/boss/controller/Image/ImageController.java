package com.froad.cbank.coremodule.normal.boss.controller.Image;

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
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.FileVo;
import com.froad.cbank.coremodule.normal.boss.support.ImageSupport;
import com.froad.cbank.coremodule.normal.boss.utils.ErrorEnums;
import com.froad.cbank.coremodule.normal.boss.utils.ImgUtil;

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
	private ImageSupport imageSupport;
	
	/**
	 * 图片上传
	 * @tilte upload
	 * @author zxl
	 * @date 2015年4月8日 上午11:35:25
	 * @param model
	 * @param file
	 * @throws IOException 
	 */	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public void upload(HttpServletRequest req, HttpServletResponse res,@RequestParam(value="file") MultipartFile[] file) throws IOException {
		
		String json = "";
		try {
			res.setCharacterEncoding("UTF-8");
			res.setContentType("text/html");
			
			//图片格式检查
			for(MultipartFile f : file){
				ImgUtil.checkImage(f);
			}
			
			ArrayList<FileVo> map = imageSupport.imgUpload(file);
			json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("RESPONE: " + json);
		} catch (BossException e) {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("code", e.getCode());
			map.put("message", e.getMsg());
			res.setStatus(608);
			json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("RESPONE: " + json);
		} catch (Exception e) {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("code", ErrorEnums.thrift_err.getCode());
			map.put("message", ErrorEnums.thrift_err.getMsg());
			res.setStatus(608);
			json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("RESPONE: " + json);
		}
		
	}
	
}
