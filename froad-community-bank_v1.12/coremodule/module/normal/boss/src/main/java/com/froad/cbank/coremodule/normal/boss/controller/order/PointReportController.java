package com.froad.cbank.coremodule.normal.boss.controller.order;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.order.PointReportReq;
import com.froad.cbank.coremodule.normal.boss.support.order.PointReportSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 积分报表--controller
 * @author liaopeixin
 *	@date 2016年1月21日 下午6:14:39
 */
@Controller
@RequestMapping("/pointReport")
public class PointReportController {
	
	@Resource
	private PointReportSupport pointReportSupport;
	/**
	 * 汇总信息查询
	 * @param model
	 * @param req
	 * @author liaopeixin
	 *	@date 2016年1月21日 下午6:29:43
	 */
	@Auth(keys={"boss_report_integral_menu"})
	@RequestMapping(value = "/pointCount", method = RequestMethod.GET)
	public void pointCount(ModelMap model,PointReportReq req){
		LogCvt.info("积分报表汇总信息查询：" + JSON.toJSONString(req));
		try {
			if(StringUtils.isBlank(req.getClientId())){
				throw new BossException("客户端ID不能为空");
			}
			if(StringUtils.isBlank(req.getStartTime())){
				throw new BossException("开始时间不能为空");
			}
			if(StringUtils.isBlank(req.getEndTime())){
				throw new BossException("结束时间不能为空");
			}
			model.clear();
			model.putAll(pointReportSupport.pointCount(req));
		}catch(BossException e){
			new RespError(model, e);
		}catch (Exception e) {
			LogCvt.error("积分报表汇总信息查询请求异常" + e.getMessage(), e);
			new RespError(model, "积分报表汇总信息查询查询失败!!!");
		}
	}
	/**
	 * 购物订单/面对面惠付订单 汇总与明细信息查询
	 * @param model
	 * @param req
	 * @author liaopeixin
	 *	@date 2016年1月21日 下午6:29:54
	 */
	@Auth(keys={"boss_report_integral_menu"})
	@RequestMapping(value = "/pointOrder", method = RequestMethod.GET)
	public void pointOrder(ModelMap model,PointReportReq req){
		LogCvt.info("购物订单/面对面惠付订单 汇总与明细信息：" + JSON.toJSONString(req));
		try {
			if(StringUtils.isBlank(req.getClientId())){
				throw new BossException("客户端ID不能为空");
			}
			if(StringUtils.isBlank(req.getIsQrcode())){
				throw new BossException("是否面对面不能为空");				
			}
			if(StringUtils.isBlank(req.getStartTime())){
				throw new BossException("开始时间不能为空");
			}
			if(StringUtils.isBlank(req.getEndTime())){
				throw new BossException("结束时间不能为空");
			}
			model.clear();
			model.putAll(pointReportSupport.pointOrder(req));
		}catch(BossException e){
			new RespError(model, e);
		}catch (Exception e) {
			LogCvt.error("购物订单/面对面惠付订单 汇总与明细信息查询请求异常" + e.getMessage(), e);
			new RespError(model, "购物订单/面对面惠付订单 汇总与明细信息查询失败!!!");
		}
	}
	/**
	 * 商户汇总信息
	 * @param model
	 * @param req
	 * @author liaopeixin
	 *	@date 2016年1月21日 下午6:30:02
	 */
	@Auth(keys={"boss_report_integral_menu"})
	@RequestMapping(value = "/pointMerchantCount", method = RequestMethod.GET)
	public void pointMerchantCount(ModelMap model,PointReportReq req){
		LogCvt.info("商户汇总信息查询请求条件：" + JSON.toJSONString(req));
		try {
			if(StringUtils.isBlank(req.getClientId())){
				throw new BossException("客户端ID不能为空");
			}
			if(StringUtils.isBlank(req.getStartTime())){
				throw new BossException("开始时间不能为空");
			}
			if(StringUtils.isBlank(req.getEndTime())){
				throw new BossException("结束时间不能为空");
			}
			model.clear();
			model.putAll(pointReportSupport.pointMerchantCount(req));
		}catch(BossException e){
			new RespError(model, e);
		}catch (Exception e) {
			LogCvt.error("商户汇总信息查询请求异常" + e.getMessage(), e);
			new RespError(model, "商户汇总信息查询失败!!!");
		}
	}
	/**
	 * 汇总信息导出
	 * @param model
	 * @param request
	 * @param req
	 * @author liaopeixin
	 *	@date 2016年1月22日 上午10:00:31
	 */
	@Auth(keys={"boss_report_integral_export"})
	@RequestMapping(value = "export", method = RequestMethod.GET)
	public void download(ModelMap model, HttpServletRequest request,PointReportReq req) {
		LogCvt.info("积分报表导出请求条件：" + JSON.toJSONString(req));
		try {
			if(StringUtils.isBlank(req.getClientId())){
				throw new BossException("客户端ID不能为空");
			}
			if(StringUtils.isBlank(req.getStartTime())){
				throw new BossException("开始时间不能为空");
			}
			if(StringUtils.isBlank(req.getEndTime())){
				throw new BossException("结束时间不能为空");
			}
			model.clear();
			model.putAll(pointReportSupport.export(req));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("积分报表导出请求异常"+e.getMessage(), e);
			new RespError(model, "积分报表导出失败!!!");
		}
	}
}
