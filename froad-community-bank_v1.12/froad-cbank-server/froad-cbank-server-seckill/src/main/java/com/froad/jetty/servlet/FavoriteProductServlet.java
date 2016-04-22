package com.froad.jetty.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.froad.enums.H5ResultCode;
import com.froad.jetty.service.FavoriteService;
import com.froad.jetty.service.SupportService;
import com.froad.jetty.service.impl.FavoriteServiceImpl;
import com.froad.jetty.service.impl.SupportServiceImpl;
import com.froad.jetty.vo.FavoriteRequestVo;
import com.froad.jetty.vo.ResponseVo;
import com.froad.logback.LogCvt;
import com.froad.util.JSonUtil;
import com.froad.util.ServletResultUtil;

/**
 * 秒杀收藏相关接口
 * 
 * @author wangzhangxu
 * @date 2015年4月27日 上午10:53:39
 * @version v1.0
 */
public class FavoriteProductServlet extends AbstractHttpServlet {
	
	private static final long serialVersionUID = 3960850575987569161L;
	
	private FavoriteService favoriteService;
	
	private SupportService supportService;
	
	/**
	 * 初始化
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		favoriteService = new FavoriteServiceImpl();
		supportService = new SupportServiceImpl();
	}
	
	/**
	 * 是否已收藏接口
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getRequestURL().toString();
		LogCvt.debug("是否已收藏接口[path=" + path + "]");
		ResponseVo responseVo = null;
		if (StringUtils.isEmpty(path) || !path.endsWith("/iscollected")) {
			responseVo = new ResponseVo(H5ResultCode.failed);
			ServletResultUtil.renderWithCache(responseVo, request, response);
			return;
		}
		
		FavoriteRequestVo reqVo = new FavoriteRequestVo();
		responseVo = initIscollectedParameters(reqVo, request);
		if (!responseVo.success()) {
			ServletResultUtil.renderWithCache(responseVo, request, response);
			return;
		}
		
		Boolean collected = favoriteService.isCollected(reqVo);
		if (collected == null) {
			responseVo = new ResponseVo(H5ResultCode.thriftException);
			ServletResultUtil.renderWithCache(responseVo, request, response);
		} else {
			ServletResultUtil.renderWithCache(collected.toString(), request, response);
		}
	}
	
	/**
	 * 收藏接口
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getRequestURL().toString();
		LogCvt.debug("收藏接口[path=" + path + "]");
		ResponseVo responseVo = null;
		if (StringUtils.isEmpty(path) || !path.endsWith("/add")) {
			responseVo = new ResponseVo(H5ResultCode.failed);
			ServletResultUtil.renderWithCache(responseVo, request, response);
			return;
		}
		
		FavoriteRequestVo reqVo = initAddParameters(request);
		responseVo = validAddParameters(reqVo);
		if (!responseVo.success()) {
			ServletResultUtil.renderWithCache(responseVo, request, response);
			return;
		}
		
		// 验证用户登录
		responseVo = supportService.checkToken(reqVo.getMemberCode(), reqVo.getToken());
		if (!responseVo.success()) {
			ServletResultUtil.render(responseVo, request, response);
			return;
		}
		
		responseVo = favoriteService.collect(reqVo);
		ServletResultUtil.renderWithCache(responseVo, request, response);
	}
	
	/**
	 * 取消收藏接口
	 */
	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getRequestURL().toString();
		LogCvt.debug("取消收藏接口[path=" + path + "]");
		
		ResponseVo responseVo = null;
		if (StringUtils.isEmpty(path) || !path.endsWith("/delete")) {
			responseVo = new ResponseVo(H5ResultCode.failed);
			ServletResultUtil.renderWithCache(responseVo, request, response);
			return;
		}
		
		FavoriteRequestVo reqVo = initDeleteParameters(request);
		responseVo = validDeleteParameters(reqVo);
		if (!responseVo.success()) {
			ServletResultUtil.renderWithCache(responseVo, request, response);
			return;
		}
		
		// 验证用户登录
		responseVo = supportService.checkToken(reqVo.getMemberCode(), reqVo.getToken());
		if (!responseVo.success()) {
			ServletResultUtil.render(responseVo, request, response);
			return;
		}
		
		Boolean deleted = favoriteService.delete(reqVo);
		if (deleted == null) {
			responseVo = new ResponseVo(H5ResultCode.thriftException);
			ServletResultUtil.renderWithCache(responseVo, request, response);
		} else {
			ServletResultUtil.renderWithCache(deleted.toString(), request, response);
		}
	}
	
	public FavoriteRequestVo initAddParameters(HttpServletRequest request){
		FavoriteRequestVo reqVo = new FavoriteRequestVo();
		try {
			String json = IOUtils.toString(request.getInputStream());
			reqVo = JSONObject.parseObject(json, new TypeReference<FavoriteRequestVo>(){});
		} catch (IOException e) {
			LogCvt.error("添加收藏接口解析请求数据异常", e);
		}
		
		Long memberCode = null;
		try{
			memberCode = Long.parseLong(request.getHeader("memberCode"));
		}catch(Exception e){}
		reqVo.setMemberCode(memberCode);
		reqVo.setToken(request.getHeader("token"));
		reqVo.setClientId((String)request.getAttribute("clientId"));
		
		LogCvt.debug("添加收藏接口请求参数：" + JSonUtil.toJSonString(reqVo));
		
		return reqVo;
	} 
	
	public ResponseVo validAddParameters(FavoriteRequestVo reqVo){
		boolean pass = true;
		// required
		if (reqVo.getMemberCode() == null 
				|| StringUtils.isEmpty(reqVo.getToken()) 
				|| StringUtils.isEmpty(reqVo.getClientId()) 
				|| StringUtils.isEmpty(reqVo.getProductId())) {
			pass = false;
		}
		
		ResponseVo resVo = null;
		if (pass) {
			resVo = new ResponseVo(H5ResultCode.success.getCode(), H5ResultCode.success.getMsg());
		} else {
			LogCvt.debug(H5ResultCode.missingParam.getMsg());
			resVo = new ResponseVo(H5ResultCode.missingParam.getCode(), H5ResultCode.missingParam.getMsg());
		}
		return resVo;
	} 
	
	public FavoriteRequestVo initDeleteParameters(HttpServletRequest request){
		FavoriteRequestVo reqVo = new FavoriteRequestVo();
		try {
			String json = IOUtils.toString(request.getInputStream());
			reqVo = JSONObject.parseObject(json, new TypeReference<FavoriteRequestVo>(){});
		} catch (IOException e) {
			LogCvt.error("取消收藏接口解析请求数据异常", e);
		}
		
		Long memberCode = null;
		try{
			memberCode = Long.parseLong(request.getHeader("memberCode"));
		}catch(Exception e){}
		reqVo.setMemberCode(memberCode);
		reqVo.setToken(request.getHeader("token"));
		
		reqVo.setClientId((String)request.getAttribute("clientId"));
		
		LogCvt.debug("取消收藏接口请求参数：" + JSonUtil.toJSonString(reqVo));
		
		return reqVo;
	} 
	
	public ResponseVo validDeleteParameters(FavoriteRequestVo reqVo){
		
		boolean pass = true;
		// required
		if (reqVo.getMemberCode() == null 
				|| StringUtils.isEmpty(reqVo.getToken()) 
				|| StringUtils.isEmpty(reqVo.getClientId()) 
				|| StringUtils.isEmpty(reqVo.getProductId())) {
			pass = false;
		}
		
		ResponseVo resVo = null;
		if (pass) {
			resVo = new ResponseVo(H5ResultCode.success.getCode(), H5ResultCode.success.getMsg());
		} else {
			LogCvt.debug(H5ResultCode.missingParam.getMsg());
			resVo = new ResponseVo(H5ResultCode.missingParam.getCode(), H5ResultCode.missingParam.getMsg());
		}
		return resVo;
	} 
	
	public ResponseVo initIscollectedParameters(FavoriteRequestVo reqVo, HttpServletRequest request){
		Long memberCode = null;
		try{
			memberCode = Long.parseLong(request.getHeader("memberCode"));
		}catch(Exception e){}
		reqVo.setMemberCode(memberCode);
		
		reqVo.setClientId((String)request.getAttribute("clientId"));
		reqVo.setProductId(request.getParameter("productId"));
		// setters end 
		
		LogCvt.debug("是否已收藏接口请求参数：" + JSonUtil.toJSonString(reqVo));
		
		boolean pass = true;
		// required
		if (reqVo.getMemberCode() == null 
				|| StringUtils.isEmpty(reqVo.getClientId()) 
				|| StringUtils.isEmpty(reqVo.getProductId())) {
			pass = false;
		}
		
		ResponseVo resVo = null;
		if (pass) {
			resVo = new ResponseVo(H5ResultCode.success.getCode(), H5ResultCode.success.getMsg());
		} else {
			LogCvt.debug(H5ResultCode.missingParam.getMsg());
			resVo = new ResponseVo(H5ResultCode.missingParam.getCode(), H5ResultCode.missingParam.getMsg());
		}
		return resVo;
	} 
	
}
