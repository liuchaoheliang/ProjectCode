//package com.froad.cbank.coremodule.normal.boss.controller.activities;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.alibaba.fastjson.JSON;
//import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
//import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
//import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
//import com.froad.cbank.coremodule.normal.boss.exception.BossException;
//import com.froad.cbank.coremodule.normal.boss.pojo.LimitActivityInfoVoReq;
//import com.froad.cbank.coremodule.normal.boss.support.LimitActivitySupport;
//import com.froad.cbank.coremodule.normal.boss.utils.Constants;
//import com.froad.cbank.coremodule.normal.boss.utils.RespError;
//
///**
// * 类描述：限购活动相关业务类
// * @version: 1.0
// * @Copyright www.f-road.com.cn Corporation 2015 
// * @author: f-road.com.cn
// * @time: 2015-5-23下午1:09:44 
// */
//
//@Controller
//@RequestMapping(value = "/limitActive")
//public class LimitActivityController extends BasicSpringController{
//	@Resource
//	private LimitActivitySupport limitActivitySupport;
//	
//	
//	@RequestMapping(method = RequestMethod.GET)
//	public void list(ModelMap model, HttpServletRequest request,LimitActivityInfoVoReq req) {
//		LogCvt.info("查询限购活动列表请求参数："+JSON.toJSONString(req));
//		try {
//			model.clear();
//			model.putAll(limitActivitySupport.list(req));
//		} catch (Exception e) {
//			LogCvt.error("限购活动列表查询请求异常"+e.getMessage(), e);
//			new RespError(model, "限购活动列表查询失败!!!");
//		}
//	}
//	
//	/**
//	 * 
//	 * <p>功能简述：查询限购活动详情</p>
//	 * <p>使用说明：根据主键id查询限购活动</p>
//	 * <p>创建时间：2015年5月23日下午1:58:20</p> 
//	 * @param model
//	 * @param id
//	 */
//	@RequestMapping(value = "{id}", method = RequestMethod.GET)
//	public void queryById(ModelMap model, @PathVariable("id") String id) {
//		LogCvt.info("查询限购活动详情请求参数:"+JSON.toJSONString(id));
//		try {
//			model.clear();
//			if (StringUtil.isBlank(id)) {
//				new RespError(model,"活动id不能为空!!!");
//				return;
//			}
//			model.putAll(limitActivitySupport.queryById(id));
//		} catch (Exception e) {
//			LogCvt.error("查询限购活动详情请求异常"+e.getMessage(), e);
//			new RespError(model, "查询限购活动详情失败!!!");
//		}
//	}
//	
//	/**
//	 * 
//	 * <p>功能简述：新增限购活动</p> 
//	 * <p>使用说明：新增限购活动</p> 
//	 * <p>创建时间：2015年5月23日下午1:14:50</p>
//	 * @param model
//	 * @param activityVoReq
//	 */
//	@RequestMapping(method = RequestMethod.POST)
//	public void add(ModelMap model, @RequestBody LimitActivityInfoVoReq activityVoReq) {
//		LogCvt.info("新增限购活动请求参数:"+JSON.toJSONString(activityVoReq));
//		try {
//			model.clear();
//			/** 默认未启用 */
//			activityVoReq.setActStatus("0");
//			/**限购活动*/
//			activityVoReq.setActivitiesType("2");
////			if (null == activityVoReq.getBeginTime()) {
////				model.put("code", Constants.RESULT_FAIL);
////				model.put("message", "请设置限购活动开始时间!!!");
////				return;
////			}
////			if (null == activityVoReq.getEndTime()) {
////				model.put("code", Constants.RESULT_FAIL);
////				model.put("message", "请设置限购活动结束时间!!!");
////				return;
////			}
//			model.putAll(limitActivitySupport.saveOrUpdate(activityVoReq));
//		}catch(BossException e){
//			new RespError(model, e);
//		} catch (Exception e) {
//			LogCvt.error("新增限购活动详情请求异常"+e.getMessage(), e);
//			new RespError(model, "新增限购活动详情失败!!!");
//		}
//	}
//	
//	/**
//	 * 
//	 * <p>功能简述：修限购活动</p> 
//	 * <p>使用说明：修改限购活动</p> 
//	 * <p>创建时间：2015年5月23日下午1:15:09</p>
//	 * @param model
//	 * @param activityVoReq
//	 */
//	@RequestMapping(method = RequestMethod.PUT)
//	public void update(ModelMap model, @RequestBody LimitActivityInfoVoReq activityVoReq) {
//		LogCvt.info("修改限购活动请求参数:", JSON.toJSONString(activityVoReq));
//		try {
//			model.clear();
//			/** 默认未启用 */
//			//activityVoReq.setActStatus("1");
//			/**活动类型*/
//			activityVoReq.setActivitiesType("2");
//			if (null == activityVoReq.getId()) {
//				model.put("code", Constants.RESULT_FAIL);
//				model.put("message", "限购活动id不能为空!!!");
//				return;
//			}
////			if (null == activityVoReq.getBeginTime()) {
////				model.put("code", Constants.RESULT_FAIL);
////				model.put("message", "请设置限购活动开始时间!!!");
////				return;
////			}
////			if (null == activityVoReq.getEndTime()) {
////				model.put("code", Constants.RESULT_FAIL);
////				model.put("message", "请设置活动结束时间!!!");
////				return;
////			}
//			if ("-1".equals(activityVoReq.getActStatus())) {
//				// 作废活动
//				model.putAll(limitActivitySupport.discard(activityVoReq));
//			}
//			model.putAll(limitActivitySupport.saveOrUpdate(activityVoReq));
//		}catch(BossException e){
//			new RespError(model, e);
//		} catch (Exception e) {
//			LogCvt.error("修改限购活动详情请求异常"+e.getMessage(), e);
//			new RespError(model, "修改限购活动详情失败!!!");
//		}
//	}
//	/**
//	 * 
//	 * <p>功能简述：删除限购活动</p> 
//	 * <p>使用说明：根据主键id删除活动</p> 
//	 * <p>创建时间：2015年5月23日下午1:25:45</p>
//	 * @param model
//	 * @param id
//	 */
//	@RequestMapping(value = "{id}/{clientId}", method = RequestMethod.DELETE)
//	public void deleteById(ModelMap model, @PathVariable("id") Long id,@PathVariable("clientId")String clientId) {
//		LogCvt.info("删除限购活动请求参数:"+JSON.toJSONString(id));
//		try {
//			model.clear();
//			model.putAll(limitActivitySupport.deleteById(id,clientId));
//		}catch(BossException e){
//			new RespError(model, e);
//		} catch (Exception e) {
//			LogCvt.error("删除限购活动详情请求异常"+e.getMessage(), e);
//			new RespError(model, "删除限购活动详情失败!!!");
//		}
//	}
//}
