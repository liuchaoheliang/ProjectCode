/**
 * <p>Project: ubank</p>
 * <p>module: coremouule</p>
 * <p>@version: Copyright © 2015 F-Road All Rights Reserved</p>
 */
package com.froad.cbank.coremodule.normal.boss.controller.ad;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.valid.ValidBeanField;
import com.froad.cbank.coremodule.framework.common.valid.ValidException;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.ad.AdPositionAddReq;
import com.froad.cbank.coremodule.normal.boss.pojo.ad.AdPositionDetailReq;
import com.froad.cbank.coremodule.normal.boss.pojo.ad.AdPositionDisReq;
import com.froad.cbank.coremodule.normal.boss.pojo.ad.AdPositionListReq;
import com.froad.cbank.coremodule.normal.boss.pojo.ad.AdPositionRes;
import com.froad.cbank.coremodule.normal.boss.pojo.ad.AdPositionUpdReq;
import com.froad.cbank.coremodule.normal.boss.support.ad.AdPositionSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;
import com.froad.thrift.vo.OriginVo;

/**
 * <p>标题: —— 标题</p>
 * <p>说明: —— 简要描述职责、应用范围、使用注意事项等</p>
 * <p>创建时间：2015-5-11下午2:05:02</p>
 * <p>作者: 高峰</p>
 * <p>版本: 1.0</p>
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
@Controller
@RequestMapping(value = "/adPosition")
public class AdPositionController {

	@Resource
	private AdPositionSupport adPositionSupport;
	
	/**
	 * <p>功能简述：—— 查询广告位列表</p>
	 * <p>使用说明：—— 方法功能、应用场景、使用注意事项等</p>
	 * <p>创建时间：2015-5-11下午2:06:10</p>
	 * <p>作者: 高峰</p>
	 */
	@RequestMapping(method = RequestMethod.GET)
	public void index(ModelMap model, AdPositionRes adRes) {
		LogCvt.info("广告位列表请求参数:", JSON.toJSONString(adRes));
		try {
			  model.clear();
			  model.putAll(adPositionSupport.list(adRes));
		} catch (Exception e) {
			LogCvt.error("查询广告位列表请求异常" + e.getMessage(), e);
			new RespError(model, "查询广告位列表广告失败!!!");
		}
	}
	
	/**
	 * <p>功能简述：—— 根据clientId获取所有广告位</p>
	 * <p>使用说明：—— 说明方法功能、使用注意事项等</p>
	 * <p>创建时间：2015-5-22下午02:10:04</p>
	 * <p>作者: 吴菲</p>
	 * @param model
	 * @param clientId
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(ModelMap model, String clientId) {
		LogCvt.info("根据clientId查询广告位：", JSON.toJSONString(clientId));
		try {
			model.clear();
			model.putAll(adPositionSupport.listAll(clientId));
		} catch (Exception e) {
			LogCvt.error("根据clientId查询广告位请求异常" + e.getMessage(), e);
			new RespError(model, "根据clientId查询广告位失败!!!");
		}
	}
	
	/**
	 * <p>功能简述：—— 查询广告位详情</p>
	 * <p>使用说明：—— 方法功能、应用场景、使用注意事项等</p>
	 * <p>创建时间：2015-5-11下午2:06:10</p>
	 * <p>作者: 高峰</p>
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public void detail(ModelMap model, @PathVariable long id) {
		LogCvt.info("广告位详情查询编号:", JSON.toJSONString(id));
		try {
			model.clear();
			model.putAll(adPositionSupport.detail(id));
		} catch (Exception e) {
			LogCvt.error("查询广告位详情请求异常"+e.getMessage(), e);
			new RespError(model, "查询广告位详情广告失败!!!");
		}
	}
	
	/**
	 * <p>功能简述：—— 添加广告位</p>
	 * <p>使用说明：—— 方法功能、应用场景、使用注意事项等</p>
	 * <p>创建时间：2015-5-11下午2:11:31</p>
	 * <p>作者: 高峰</p>
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void add(ModelMap model, @RequestBody AdPositionRes adRes) {
		LogCvt.info("添加广告位请求参数:", JSON.toJSONString(adRes));
		try {
			model.clear();
			model.putAll(adPositionSupport.add(adRes));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("新增广告位请求异常" + e.getMessage(), e);
			new RespError(model, "新增广告位失败!!!");
		}
	}
	
	/**
	 * <p>功能简述：—— 修改广告位</p>
	 * <p>使用说明：—— 方法功能、应用场景、使用注意事项等</p>
	 * <p>创建时间：2015-5-11下午2:12:57</p>
	 * <p>作者: 高峰</p>
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public void update(ModelMap model, @RequestBody AdPositionRes adRes) {
		LogCvt.info("修改广告位请求参数:", JSON.toJSONString(adRes));
		try {
			model.clear();
			model.putAll(adPositionSupport.update(adRes));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("修改广告位请求异常" + e.getMessage(), e);
			new RespError(model, "修改广告位失败!!!");
		}
	}
	
	/**
	 * <p>功能简述：—— 删除广告位</p>
	 * <p>使用说明：—— 方法功能、应用场景、使用注意事项等</p>
	 * <p>创建时间：2015-5-11下午2:14:02</p>
	 * <p>作者: 高峰</p>
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void delete(ModelMap model, @PathVariable long id) {
		LogCvt.info("删除广告位编号:", JSON.toJSONString(id));
		try {
			model.clear();
			model.putAll(adPositionSupport.delete(id));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("删除广告位请求异常" + e.getMessage(), e);
			new RespError(model, "删除广告位失败!!!");
		}
	}
	
	/**
	 * 获取广告位列表
	 * @path /adPosition/lt
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月21日 下午2:42:42
	 * @param model
	 * @param pojo
	 */
	@Auth(keys={"boss_adp_menu"})
	@RequestMapping(value = "/lt", method = RequestMethod.GET)
	public void getAdPositionList(ModelMap model, AdPositionListReq pojo) {
		try {
			ValidBeanField.valid(pojo);
			if(LogCvt.isDebugEnabled()) {
				LogCvt.debug("获取广告位列表请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			model.putAll(adPositionSupport.getAdPositionList(pojo));
		} catch (ValidException e) {
			new RespError(model, e.getMessage());
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 获取广告位详情
	 * @path /adPosition/detail
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月21日 下午2:54:53
	 * @param model
	 * @param pojo
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public void getAdPositionDetail(ModelMap model, AdPositionDetailReq pojo) {
		try {
			if(pojo.getId() == 0) {
				throw new BossException("广告位ID为空");
			}
			if(LogCvt.isDebugEnabled()) {
				LogCvt.debug("获取广告位详情请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			model.putAll(adPositionSupport.getAdPositionDetail(pojo));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 新增广告位
	 * @path /adPosition/add
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月21日 下午3:39:01
	 * @param model
	 * @param pojo
	 */
	@Auth(keys={"boss_adp_add"})
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addAdPosition(ModelMap model, HttpServletRequest req, @RequestBody AdPositionAddReq pojo) {
		try {
			if(pojo.getSizeLimit() > 9999 || pojo.getSizeLimit() < 0) {
				throw new BossException("大小限制为1~9999");
			}
			if(pojo.getWidth() > 9999 || pojo.getWidth() < 0) {
				throw new BossException("宽度限制为1~9999");
			}
			if(pojo.getHeight() > 9999 || pojo.getHeight() < 0) {
				throw new BossException("高度限制为1~9999");
			}
			OriginVo org = (OriginVo) req.getAttribute(Constants.ORIGIN);
			if(LogCvt.isDebugEnabled()) {
				LogCvt.debug("新增广告位请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			model.putAll(adPositionSupport.addAdPosition(pojo, org));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 修改广告位
	 * @path /adPosition/update
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月21日 下午3:39:01
	 * @param model
	 * @param pojo
	 */
	@Auth(keys={"boss_adp_modify"})
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void updateAdPosition(ModelMap model, HttpServletRequest req, @RequestBody AdPositionUpdReq pojo) {
		try {
			if(pojo.getId() == 0) {
				throw new BossException("广告位ID为空");
			}
			if(pojo.getSizeLimit() > 9999 || pojo.getSizeLimit() < 0) {
				throw new BossException("大小限制为1~9999");
			}
			if(pojo.getWidth() > 9999 || pojo.getWidth() < 0) {
				throw new BossException("宽度限制为1~9999");
			}
			if(pojo.getHeight() > 9999 || pojo.getHeight() < 0) {
				throw new BossException("高度限制为1~9999");
			}
			OriginVo org = (OriginVo) req.getAttribute(Constants.ORIGIN);
			if(LogCvt.isDebugEnabled()) {
				LogCvt.debug("修改广告位请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			model.putAll(adPositionSupport.updateAdPosition(pojo, org));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 禁用广告位
	 * @path /adPosition/disable
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月21日 下午3:39:01
	 * @param model
	 * @param pojo
	 */
	@Auth(keys={"boss_adp_disable"})
	@RequestMapping(value = "/disable", method = RequestMethod.POST)
	public void disableAdPosition(ModelMap model, HttpServletRequest req, @RequestBody AdPositionDisReq pojo) {
		try {
			if(pojo.getId() == 0) {
				throw new BossException("广告位ID为空");
			}
			OriginVo org = (OriginVo) req.getAttribute(Constants.ORIGIN);
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("禁用广告位请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			model.putAll(adPositionSupport.disableAdPosition(pojo, org));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 获取所有广告位列表
	 * @path /adPosition/disable
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月21日 下午3:39:01
	 * @param model
	 * @param pojo
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public void getAllAdPosition(ModelMap model, AdPositionListReq pojo) {
		try {
			if(LogCvt.isDebugEnabled()) {
				LogCvt.debug("查询全部广告位请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			model.putAll(adPositionSupport.getAllAdPosition(pojo));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
}