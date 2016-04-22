package com.froad.cbank.coremodule.normal.boss.controller.activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.annotation.ImpExp;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.BossUser;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.VouchersCouponCodeListReq;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.VouchersRuleListReq;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.VouchersRuleVoReq;
import com.froad.cbank.coremodule.normal.boss.support.activities.VouchersRuleSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.ErrorEnums;
import com.froad.cbank.coremodule.normal.boss.utils.PramasUtil;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;
import com.froad.thrift.vo.OriginVo;

/**
 * 红包规则
 * @author yfy
 * @date: 2015年11月24日 下午14:55:01
 */
@Controller
@RequestMapping(value="vouchersRule")
public class VouchersRuleController {

	@Resource
	private VouchersRuleSupport vouchersRuleSupport;
	
	/**
	 * 红包规则分页列表查询
	 * @author yfy
	 * @date: 2015年11月24日 下午15:01:31
	 * @param model
	 * @param request
	 * @param listReq
	 */
	@Auth(keys={"boss_packet_menu"})
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public void list(ModelMap model, HttpServletRequest request,VouchersRuleListReq listReq) {
		LogCvt.info("红包规则分页列表查询条件:" + JSON.toJSONString(listReq));
		try {
			model.clear();
			model.putAll(vouchersRuleSupport.list(listReq));
		} catch (Exception e) {
			LogCvt.error("红包规则分页列表查询请求异常"+e.getMessage(), e);
			new RespError(model, "红包规则分页列表查询失败!!!");
		}
	}
	
	/**
	 * 红包规则新增
	 * @author yfy
	 * @date: 2015年11月24日  下午15:06:12
	 * @param model
	 * @param request
	 * @param voReq
	 */
	@Auth(keys={"boss_packet_add"})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public void add(ModelMap model, HttpServletRequest request,@RequestBody VouchersRuleVoReq voReq) {
		LogCvt.info("红包规则新增参数:" + JSON.toJSONString(voReq));
		try {
			Long startTime = null;
			Long endTime = null;
			if(StringUtil.isNotBlank(voReq.getExpireStartTime())){
				startTime = PramasUtil.DateFormat(voReq.getExpireStartTime());
			}
			if(StringUtil.isNotBlank(voReq.getExpireEndTime())){
				endTime = PramasUtil.DateFormat(voReq.getExpireEndTime());
			}
			if(startTime != null && endTime != null){
				if(startTime < System.currentTimeMillis()){
					throw new BossException("活动期开始时间不能小于当前时间!");
				}
				if(endTime < System.currentTimeMillis()){
					throw new BossException("活动期结束时间不能小于当前时间!");
				}
				if(startTime.equals(endTime)){
					throw new BossException("活动期开始时间不能等于结束时间!");
				}
				if(startTime > endTime){
					throw new BossException("活动期开始时间不能大于结束时间!");
				}
			}
			this.checkEmpty(model, voReq);//校验数据范围
			OriginVo originVo = (OriginVo) request.getAttribute(Constants.ORIGIN);
			BossUser user = (BossUser) request.getAttribute(Constants.BOSS_USER);
			voReq.setOperator(user.getName());
			model.clear();
			model.putAll(vouchersRuleSupport.add(originVo,voReq));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("红包规则新增请求异常"+e.getMessage(), e);
			new RespError(model, "红包规则新增失败!!!");
		}
	}
	
	/**
	 * 红包规则修改
	 * @author yfy
	 * @date: 2015年11月24日 下午15:17:55
	 * @param model
	 * @param request
	 * @param voReq
	 */
	@Auth(keys={"boss_packet_modify"})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public void update(ModelMap model, HttpServletRequest request,@RequestBody VouchersRuleVoReq voReq) {
		LogCvt.info("红包规则修改参数:" + JSON.toJSONString(voReq));
		try {
			Long startTime = null;
			Long endTime = null;
			if(StringUtil.isNotBlank(voReq.getExpireStartTime())){
				startTime = PramasUtil.DateFormat(voReq.getExpireStartTime());
			}
			if(StringUtil.isNotBlank(voReq.getExpireEndTime())){
				endTime = PramasUtil.DateFormat(voReq.getExpireEndTime());
			}
			if(startTime != null && endTime != null){
				if(startTime.equals(endTime)){
					throw new BossException("活动期开始时间不能等于结束时间!");
				}
				if(startTime > endTime){
					throw new BossException("活动期开始时间不能大于结束时间!");
				}
			}
			this.checkEmpty(model, voReq);//校验数据范围
			OriginVo originVo = (OriginVo) request.getAttribute(Constants.ORIGIN);
			BossUser user = (BossUser) request.getAttribute(Constants.BOSS_USER);
			voReq.setOperator(user.getName());
			model.clear();
			model.putAll(vouchersRuleSupport.update(originVo,voReq));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("红包规则修改请求异常"+e.getMessage(), e);
			new RespError(model, "红包规则修改失败!!!");
		}
	}
	
	/**
	 * 红包规则详情
	 * @author yfy
	 * @date: 2015年11月24日 下午15:29:34
	 * @param model
	 * @param request
	 * @param clientId
	 * @param activeId
	 */
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public void detail(ModelMap model, HttpServletRequest request,String clientId,String activeId) {
		LogCvt.info("红包规则详情查询条件:clientId:" +clientId+",activeId:"+activeId);
		try {
			if(!StringUtil.isNotBlank(activeId)){
				throw new BossException("活动编号不能为空!");
			}
			if(!StringUtil.isNotBlank(clientId)){
				throw new BossException("客户端不能为空!");
			}
			model.clear();
			model.putAll(vouchersRuleSupport.detail(clientId,activeId));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("红包规则详情查询请求异常"+e.getMessage(), e);
			new RespError(model, "红包规则详情查询失败!!!");
		}
	}
	
	/**
	 * 红包规则禁用
	 * @author yfy
	 * @date: 2015年11月24日 下午15:42:14
	 * @param model
	 * @param request
	 * @param clientId
	 * @param activeId
	 */
	@Auth(keys={"boss_packet_disable"})
	@RequestMapping(value = "disable", method = RequestMethod.GET)
	public void disable(ModelMap model, HttpServletRequest request,String clientId,String activeId) {
		LogCvt.info("红包规则禁用参数:clientId:" +clientId+",activeId:"+activeId);
		try {
			if(!StringUtil.isNotBlank(activeId)){
				throw new BossException("活动编号不能为空!");
			}
			if(!StringUtil.isNotBlank(clientId)){
				throw new BossException("客户端不能为空!");
			}
			OriginVo originVo = (OriginVo) request.getAttribute(Constants.ORIGIN);
			BossUser user = (BossUser) request.getAttribute(Constants.BOSS_USER);
			model.clear();
			model.putAll(vouchersRuleSupport.disable(originVo,clientId,activeId,user.getName()));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("红包规则禁用请求异常"+e.getMessage(), e);
			new RespError(model, "红包规则禁用失败!!!");
		}
	}
	
	/**
	 * 活动分页列表查询
	 * @author yfy
	 * @date: 2015年11月25日 下午13:10:07
	 * @param model
	 * @param request
	 * @param listReq
	 */
	@RequestMapping(value = "pmtActive", method = RequestMethod.GET)
	public void activeList(ModelMap model, HttpServletRequest request,VouchersRuleListReq listReq) {
		LogCvt.info("活动分页列表查询条件:" + JSON.toJSONString(listReq));
		try {
			model.clear();
			model.putAll(vouchersRuleSupport.activeList(listReq));
		} catch (Exception e) {
			LogCvt.error("活动分页列表请求异常"+e.getMessage(), e);
			new RespError(model, "活动分页列表查询失败!!!");
		}
	}
	

	/**
	 * 新增或修改红包券码分页列表查询
	 * @author yfy
	 * @date: 2015年11月25日 下午13:58:12
	 * @param model
	 * @param request
	 * @param listReq
	 */
	@RequestMapping(value = "codeList", method = RequestMethod.GET)
	public void codeList(ModelMap model, HttpServletRequest request,VouchersCouponCodeListReq listReq) {
		LogCvt.info("红包券码回显查询条件:" + JSON.toJSONString(listReq));
		try {
			model.clear();
			model.putAll(vouchersRuleSupport.codeList(listReq));
		} catch (Exception e) {
			LogCvt.error("红包券码回显查询请求异常"+e.getMessage(), e);
			new RespError(model, "红包券码回显失败!!!");
		}
	}
	/**
	 * 详情页红包券码分页列表查询
	 * @author yfy
	 * @date: 2015年11月25日 下午13:58:12
	 * @param model
	 * @param request
	 * @param listReq
	 */
	@RequestMapping(value = "dlCodeList", method = RequestMethod.GET)
	public void dlCodeList(ModelMap model, HttpServletRequest request,VouchersCouponCodeListReq listReq) {
		LogCvt.info("详情红包券码回显查询条件:" + JSON.toJSONString(listReq));
		try {
			model.clear();
			model.putAll(vouchersRuleSupport.dlCodeList(listReq));
		} catch (Exception e) {
			LogCvt.error("详情红包券码回显请求异常"+e.getMessage(), e);
			new RespError(model, "详情红包券码回显失败!!!");
		}
	}
	
	
	
	/**
	 * 上传txt
	 * @param req
	 * @param res
	 * @param file
	 * @throws IOException
	 * @author liaopeixin
	 *	@date 2015年12月11日 上午11:17:39
	 */
	@ImpExp
	@RequestMapping(value="upload", method=RequestMethod.POST)
	public void uploadCsv(HttpServletRequest req, HttpServletResponse res, @RequestParam("file") MultipartFile file) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		LogCvt.info("红包规则上传红包券码条件:没有条件");
		String json = "";
		try {
			res.setCharacterEncoding("UTF-8");
			res.setContentType("text/html");
			if(file.getOriginalFilename().indexOf(".txt") == -1) {
				throw new BossException("文件格式有误，请上传txt文件"); 
			}
			InputStream is = file.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			int count = 0;
			String tmp = null;
			while((tmp = br.readLine()) != null) {
				if(StringUtil.isNotBlank(tmp)) {
					count++;
				}
			}
			if(count == 0) {
				throw new BossException("上传券码个数为空");
			}
			
			OriginVo originVo = (OriginVo) req.getAttribute(Constants.ORIGIN);
			
			
			map = vouchersRuleSupport.uploadCode(originVo, file);
			
			json = JSONArray.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("RESPONE：" + json);
		} catch (BossException e) {
			map.put("code", e.getCode());
			map.put("message", e.getMsg());
			res.setStatus(608);
			json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("RESPONE：" + json);
			LogCvt.error(e.getMessage(), e);
		} catch (Exception e) {
			map.put("code", ErrorEnums.thrift_err.getCode());
			map.put("message", ErrorEnums.thrift_err.getMsg());
			res.setStatus(608);
			json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("RESPONE：" + json);
			LogCvt.error(e.getMessage(), e);
		}
	}
	/**
	 * 红包规则下载交易记录
	 * @author yfy
	 * @date: 2015年11月24日 下午17:42:14
	 * @param model
	 * @param request
	 * @param clientId
	 * @param activeId
	 */
	@Auth(keys={"boss_packet_export"})
	@RequestMapping(value = "download", method = RequestMethod.GET)
	public void download(ModelMap model, HttpServletRequest request,String clientId,String activeId) {
		LogCvt.info("红包规则下载交易记录参数:clientId:" +clientId+",activeId:"+activeId);
		try {
			if(!StringUtil.isNotBlank(activeId)){
				throw new BossException("活动编号不能为空!");
			}
			if(!StringUtil.isNotBlank(clientId)){
				throw new BossException("客户端不能为空!");
			}
			model.clear();
			model.putAll(vouchersRuleSupport.download(clientId,activeId));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("红包规则下载交易记录请求异常"+e.getMessage(), e);
			new RespError(model, "红包规则下载交易记录失败!!!");
		}
	}
	
	/**
	 * 校验数据范围
	 * @param model
	 * @param voReq
	 * @throws BossException 
	 * @throws ParseException 
	 */
	public void checkEmpty(ModelMap model,VouchersRuleVoReq voReq) throws BossException, ParseException{
		
		if(voReq.getActiveName().length() >50){
			throw new BossException("只能输入0至50汉字或字符!!");
		}
		if(voReq.getDescription().length() >300){
			throw new BossException("只能输入0至300汉字或字符!");
		}
		if(!StringUtil.isNotBlank(voReq.getMinMoney())){
			throw new BossException("红包总金额不能为空!");
		}
		if(!StringUtil.isNotBlank(voReq.getTotalMoney())){
			throw new BossException("红包金额不能为空!");
		}
		Long totalMoney = new BigDecimal(voReq.getTotalMoney()).multiply(new BigDecimal(100)).longValue();
		Long minMoney = new BigDecimal(voReq.getMinMoney()).multiply(new BigDecimal(100)).longValue();
		if(minMoney > totalMoney){
			throw new BossException("红包金额不能大于红包总金额!");
		}
		
		if(NumberUtils.isNumber(voReq.getMerchantRate())//商户补贴
				&& NumberUtils.isNumber(voReq.getBankRate())//银行补贴
				&& NumberUtils.isNumber(voReq.getFftRate())){//方付通补贴
			
			BigDecimal merchantRate = new BigDecimal(voReq.getMerchantRate());
			if(merchantRate.intValue() < 0 || merchantRate.intValue() > 100){
				throw new BossException("只能输入0至100的数字!");
			}
			BigDecimal bankRate = new BigDecimal(voReq.getBankRate());
			if(bankRate.intValue() < 0 || bankRate.intValue() > 100){
				throw new BossException("只能输入0至100的数字!");
			}
			BigDecimal fftRate = new BigDecimal(voReq.getFftRate());
			if(fftRate.intValue() < 0 || fftRate.intValue() > 100){
				throw new BossException("只能输入0至100的数字!");
			}
			Integer sum = merchantRate.add(bankRate).add(fftRate).intValue();
			if(!sum.equals(100)){
				throw new BossException("商户补贴,方付通补贴,银行补贴总额必须等于100%");
			}
		}else{
			throw new BossException("只能输入0至100的数字!");
		}
	}
	
}
