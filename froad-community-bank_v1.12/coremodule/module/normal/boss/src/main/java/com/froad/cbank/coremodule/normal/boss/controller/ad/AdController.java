package com.froad.cbank.coremodule.normal.boss.controller.ad;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.froad.cbank.coremodule.normal.boss.pojo.ad.AdAddReq;
import com.froad.cbank.coremodule.normal.boss.pojo.ad.AdDetailReq;
import com.froad.cbank.coremodule.normal.boss.pojo.ad.AdListReq;
import com.froad.cbank.coremodule.normal.boss.pojo.ad.AdRes;
import com.froad.cbank.coremodule.normal.boss.pojo.ad.AdUpdReq;
import com.froad.cbank.coremodule.normal.boss.support.LoginBossSupport;
import com.froad.cbank.coremodule.normal.boss.support.ad.AdSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.ErrorEnums;
import com.froad.cbank.coremodule.normal.boss.utils.ImgUtil;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;
import com.froad.thrift.vo.OriginVo;

/**
 * <p>标题: —— 标题</p>
 * <p>说明: —— 简要描述职责、应用范围、使用注意事项等</p>
 * <p>创建时间：2015-5-11下午2:18:43</p>
 * <p>作者: 高峰</p>
 * <p>版本: 1.0</p>
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
@Controller
public class AdController {
	
	@Resource
	LoginBossSupport loginBossSupport;
	
	@Resource
	private AdSupport adSupport;

	/**
	 * <p>功能简述：—— 获取广告列表</p>
	 * <p>使用说明：—— 方法功能、应用场景、使用注意事项等</p>
	 * <p>创建时间：2015-5-11下午2:19:00</p>
	 * <p>作者: 高峰</p>
	 * @param model
	 */
	@RequestMapping(value = "/ad", method = RequestMethod.GET)
	public void list(ModelMap model, AdRes adRes) {
		LogCvt.info("广告列表请求参数:", JSON.toJSONString(adRes));
		try {
			model.clear();
			model.putAll(adSupport.list(adRes));
		} catch (Exception e) {
			LogCvt.error("查询广告列表请求异常" + e.getMessage(), e);
			new RespError(model, "查询广告列表失败!!!");
		}
	}
	
	/**
	 * <p>功能简述：—— 获取广告详情</p>
	 * <p>使用说明：—— 方法功能、应用场景、使用注意事项等</p>
	 * <p>创建时间：2015-5-11下午2:19:05</p>
	 * <p>作者: 高峰</p>
	 * @param model
	 */
	@RequestMapping(value = "/ad/{id}", method = RequestMethod.GET)
	public void detail(ModelMap model, @PathVariable long id) {
		LogCvt.info("广告详情查询编号:", JSON.toJSONString(id));
		try {
			model.clear();
			model.putAll(adSupport.detail(id));
		} catch (Exception e) {
			LogCvt.error("查询广告详情请求异常" + e.getMessage(), e);
			new RespError(model, "查询广告详情失败!!!");
		}
	}
	
	/**	
	 * <p>功能简述：—— 添加广告</p>
	 * <p>使用说明：—— 方法功能、应用场景、使用注意事项等</p>
	 * <p>创建时间：2015-5-11下午2:19:08</p>
	 * <p>作者: 高峰</p>
	 * @param model
	 */
	@RequestMapping(value = "/ad", method = RequestMethod.POST)
	public void add(ModelMap model, @RequestBody AdRes adRes) {
		LogCvt.info("添加广告请求参数:", JSON.toJSONString(adRes));
		try {
			model.clear();
			model.putAll(adSupport.add(adRes));
		} catch (ParseException e) {
			LogCvt.error("数据转换请求异常" + e.getMessage(), e);
			new RespError(model, "数据转换失败!!!");
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("添加广告请求异常" + e.getMessage(), e);
			new RespError(model, "添加广告失败!!!");
		}
	}
	
	/**
	 * <p>功能简述：—— 修改广告</p>
	 * <p>使用说明：—— 方法功能、应用场景、使用注意事项等</p>
	 * <p>创建时间：2015-5-11下午2:19:08</p>
	 * <p>作者: 高峰</p>
	 * @param model
	 */
	@RequestMapping(value = "/ad", method = RequestMethod.PUT)
	public void update(ModelMap model, @RequestBody AdRes adRes) {
		LogCvt.info("修改广告请求参数:", JSON.toJSONString(adRes));
		try {
			model.clear();
			model.putAll(adSupport.update(adRes));
		} catch (ParseException e) {
			LogCvt.error("数据转换请求异常" + e.getMessage(), e);
			new RespError(model, "数据转换失败!!!");
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("修改广告请求异常" + e.getMessage(), e);
			new RespError(model, "修改广告失败!!!");
		}
	}
	
	/**
	 * <p>功能简述：—— 删除广告</p>
	 * <p>使用说明：—— 方法功能、应用场景、使用注意事项等</p>
	 * <p>创建时间：2015-5-11下午2:19:08</p>
	 * <p>作者: 高峰</p>
	 * @param model
	 */
	@RequestMapping(value = "/ad/{id}", method = RequestMethod.DELETE)
	public void delete(ModelMap model, @PathVariable long id) {
		LogCvt.info("删除广告编号:", JSON.toJSONString(id));
		try {
			model.clear();
			model.putAll(adSupport.delete(id));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("删除广告请求异常" + e.getMessage(), e);
			new RespError(model, "删除广告失败!!!");
		}
	}
	
	/**
	 * 获取广告列表
	 * @path /ad/list
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月21日 下午4:17:40
	 * @param model
	 * @param pojo
	 */
	@Auth(keys={"boss_ad_menu"})
	@RequestMapping(value = "/ad/list", method = RequestMethod.GET)
	public void getAdList(ModelMap model, HttpServletRequest req, AdListReq pojo) {
		try {
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("获取广告列表请求参数：" + JSON.toJSONString(pojo));
			}
			OriginVo org = (OriginVo) req.getAttribute(Constants.ORIGIN);
			if(StringUtils.isBlank(pojo.getClientId())){
				throw new BossException("银行渠道不能为空!");
			}
			model.clear();
			model.putAll(adSupport.getAdList(pojo, org));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 获取广告详情
	 * @path /ad/detail
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月21日 下午4:17:40
	 * @param model
	 * @param pojo
	 */
	@RequestMapping(value = "/ad/detail", method = RequestMethod.GET)
	public void getAdDetail(ModelMap model, HttpServletRequest req, AdDetailReq pojo) {
		try {
			if(pojo.getId() == 0) {
				throw new BossException("广告ID为空");
			}
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("广告详情获取请求参数：" + JSON.toJSONString(pojo));
			}
			OriginVo org = (OriginVo) req.getAttribute(Constants.ORIGIN);
			model.clear();
			model.putAll(adSupport.getAdDetail(pojo, org));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 新增广告
	 * @path /ad/add
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月21日 下午4:17:40
	 * @param model
	 * @param pojo
	 */
	@Auth(keys={"boss_ad_add"})
	@RequestMapping(value = "/ad/add", method = RequestMethod.POST)
	public void addAd(ModelMap model, HttpServletRequest req, @RequestBody AdAddReq pojo) {
		try {
			if(pojo.getAdLocationId() == 0) {
				throw new BossException("广告位ID为空");
			}
			if("1".equals(pojo.getType()) && StringUtil.isBlank(pojo.getPath())) {//图片类型广告的广告图不可为空
				throw new BossException("请先上传广告图片");
			}
			OriginVo org = (OriginVo) req.getAttribute(Constants.ORIGIN);
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("广告新增请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			model.putAll(adSupport.addAd(pojo, org));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 修改广告
	 * @path /ad/update
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月21日 下午4:17:40
	 * @param model
	 * @param pojo
	 */
	@Auth(keys={"boss_ad_modify"})
	@RequestMapping(value = "/ad/update", method = RequestMethod.POST)
	public void updateAd(ModelMap model, HttpServletRequest req, @RequestBody AdUpdReq pojo) {
		try {
			if(pojo.getId() == 0) {
				throw new BossException("广告ID为空");
			}
			if(pojo.getAdLocationId() == 0) {
				throw new BossException("广告位ID为空");
			}
			if("1".equals(pojo.getType()) && StringUtil.isBlank(pojo.getPath())) {//图片类型广告的广告图不可为空
				throw new BossException("请先上传广告图片");
			}
			OriginVo org = (OriginVo) req.getAttribute(Constants.ORIGIN);
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("广告修改请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			model.putAll(adSupport.updateAd(pojo, org));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 广告图上传接口
	 * @path /ad/upload
	 * @author luwanquan@f-road.com.cn
	 * @throws IOException 
	 * @date 2015年10月23日 下午4:18:29
	 */
	@ImpExp
	@RequestMapping(value = "/ad/upload", method = RequestMethod.POST)
	public void upload(HttpServletRequest req, HttpServletResponse res, @RequestParam(value = "file") MultipartFile file) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String json = "";
		try {
			String adLocationId = req.getParameter("adLocationId");
			if(StringUtil.isBlank(adLocationId)) {
				throw new BossException("广告图片压缩的宽高比例按广告位的宽高值来计算，请先选择广告位");
			}
			
			//图片格式检查
			ImgUtil.checkImage(file);
			
			if(file.getSize() > 2097152) {
				throw new BossException("广告图片大小限2M以内");
			}
			res.setCharacterEncoding("UTF-8");
			res.setContentType("text/html");
			HashMap<String, String> resMap = adSupport.upload(file, adLocationId);
			map.put("path", resMap);
			json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("RESPONE: " + json);
		} catch (BossException e) {
			map.put("code", e.getCode());
			map.put("message", e.getMsg());
			res.setStatus(608);
			json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("imgUpload-RESPONE：" + json);
			LogCvt.error(e.getMessage(), e);
		} catch (Exception e) {
			map.put("code", ErrorEnums.img_upload_fail.getCode());
			map.put("message", ErrorEnums.img_upload_fail.getMsg());
			res.setStatus(608);
			json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("imgUpload-RESPONE：" + json);
			LogCvt.error(e.getMessage(), e);
		}
	}
}