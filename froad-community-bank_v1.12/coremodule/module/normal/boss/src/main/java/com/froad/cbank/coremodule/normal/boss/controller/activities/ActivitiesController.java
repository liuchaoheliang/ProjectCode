package com.froad.cbank.coremodule.normal.boss.controller.activities;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.ActivityListVo;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.ActivityVoReq;
import com.froad.cbank.coremodule.normal.boss.support.activities.ActivitySupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 
 * <p>标题: 商品活动</p>
 * <p>说明: 商品活动相关的业务</p>
 * <p>创建时间：2015年5月13日下午2:06:51</p>
 * <p>作者: 陈明灿</p>
 * <p>版本: 1.0</p>
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
@Controller
@RequestMapping(value = "activity")
public class ActivitiesController{

	@Resource
	private ActivitySupport activitySupport;
	
	/**
	 * 活动列表查询
	 * @tilte list
	 * @author zxl
	 * @date 2015年6月11日 上午10:42:54
	 * @param model
	 * @param request
	 * @param activityVoReq
	 */
	@Auth(keys={"boss_actitvity_point_menu"})
	@RequestMapping(method = RequestMethod.GET)
	public void list(ModelMap model, HttpServletRequest request,ActivityListVo activityVoReq) {
		LogCvt.info("查询活动列表条件:"+JSON.toJSONString(activityVoReq));
		try {
			model.clear();
			if(StringUtils.isBlank(activityVoReq.getClientId())){
				throw new BossException("银行渠道不能为空!");
			}
			model.putAll(activitySupport.list(activityVoReq));
		} catch(BossException e){
			new RespError(model,e);
		}catch (Exception e) {
			LogCvt.error("查询活动列表请求异常"+e.getMessage(), e);
			new RespError(model, "查询活动列表失败!!!");
		}
	}
	
	/**
	 * 
	 * <p>功能简述：查询活动详情</p>
	 * <p>使用说明：根据主键id查询活动</p>
	 * <p>创建时间：2015年5月16日下午4:58:20</p>
	 * <p>作者: 陈明灿</p>
	 * 
	 * @param model
	 * @param id
	 */
	@Auth(keys={"boss_actitvity_point_menu"})
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public void queryById(ModelMap model, @PathVariable("id") String id) {
		LogCvt.info("查询活动详情请求参数:id:"+JSON.toJSONString(id));
		try {
			model.clear();
			if (StringUtil.isBlank(id)) {
				new RespError(model,"活动id不能为空!!!");
				return;
			}
			model.putAll(activitySupport.queryById(id));
		} catch (Exception e) {
			new RespError(model,"查询活动详情失败!!!");
			LogCvt.error("查询活动详情请求异常"+ e.getMessage(),e);
		}
	}
	
	/**
	 * 
	 * <p>功能简述：新增活动</p> 
	 * <p>使用说明：新增活动</p> 
	 * <p>创建时间：2015年5月16日下午5:14:50</p>
	 * <p>作者: 陈明灿</p>
	 * @param model
	 * @param activityVoReq
	 */
	@Auth(keys={"boss_actitvity_point_add"})
	@RequestMapping(method = RequestMethod.POST)
	public void add(ModelMap model, @RequestBody ActivityVoReq activityVoReq) {
		LogCvt.info("新增活动请求参数:id:"+JSON.toJSONString(activityVoReq));
		try {
			model.clear();
			// /** 默认未启用 */
			activityVoReq.setStatus(0);
			/**活动类型*/
			activityVoReq.setActivitiesType("1");
			if (null == activityVoReq.getBeginTime()) {
				new RespError(model,"请设置活动开始时间!!!");
				return;
			}
			if (null == activityVoReq.getEndTime()) {
				new RespError(model,"请设置活动结束时间!!!");
				return;
			}
			model.putAll(activitySupport.saveOrUpdate(activityVoReq));
		}catch(BossException e){
			new RespError(model, e);
		}catch (Exception e) {
			new RespError(model,"新增活动失败!!!");
			LogCvt.error("新增活动请求异常"+ e.getMessage(),e);
		}
	}
	
	/**
	 * 
	 * <p>功能简述：修改活动</p> 
	 * <p>使用说明：修改活动</p> 
	 * <p>创建时间：2015年5月16日下午5:15:09</p>
	 * <p>作者: 陈明灿</p>
	 * @param model
	 * @param activityVoReq
	 */
	@Auth(keys={"boss_actitvity_point_modify","boss_actitvity_point_enable","boss_actitvity_point_disable"})
	@RequestMapping(method = RequestMethod.PUT)
	public void update(ModelMap model, @RequestBody ActivityVoReq activityVoReq) {
		LogCvt.info("修改活动请求参数:id:"+JSON.toJSONString(activityVoReq));
		try {
			model.clear();
			// /** 默认未启用 */
			// activityVoReq.setIsEnable(false);
			/**活动类型*/
			activityVoReq.setActivitiesType("1");
			if (null == activityVoReq.getId()) {
				new RespError(model,"活动id不能为空!!!");
				return;
			}
			if (null == activityVoReq.getBeginTime()) {
				new RespError(model,"请设置活动开始时间!!!");
				return;
			}
			if (null == activityVoReq.getEndTime()) {
				new RespError(model,"请设置活动结束时间!!!");
				return;
			}
			if (activityVoReq.getStatus()!=null && activityVoReq.getStatus() == 2) {
				// 作废活动
				model.putAll(activitySupport.discard(activityVoReq));
			}
			// 启用或者修改活动
			model.putAll(activitySupport.saveOrUpdate(activityVoReq));
		} catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model,"修改活动失败!!!");
			LogCvt.error("修改活动请求异常"+e.getMessage(),e);
		}
	}
	/**
	 * 
	 * <p>功能简述：删除活动</p> 
	 * <p>使用说明：根据主键id删除活动</p> 
	 * <p>创建时间：2015年5月16日下午4:25:45</p>
	 * <p>作者: 陈明灿</p>
	 * @param model
	 * @param id
	 */
	@Auth(keys={"boss_actitvity_point_delete"})
	@RequestMapping(value = "{id}/{clientId}", method = RequestMethod.DELETE)
	public void deleteById(ModelMap model, @PathVariable("id") Long id,@PathVariable("clientId")String clientId) {
		try {
			model.clear();
			model.putAll(activitySupport.deleteById(id,clientId));
		} catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model,"删除活动失败!!!");
			LogCvt.error("删除活动请求异常"+e.getMessage(),e);
		}
	}
	
}
