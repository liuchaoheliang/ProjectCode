package com.froad.cbank.coremodule.normal.boss.controller.activities;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.BossUser;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.FullPromotionListReq;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.FullPromotionVoReq;
import com.froad.cbank.coremodule.normal.boss.support.activities.FullPromotionSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.NumberUtil;
import com.froad.cbank.coremodule.normal.boss.utils.PramasUtil;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;
import com.froad.thrift.vo.OriginVo;

/**
 * 满额促销
 * @author yfy
 * @date: 2015年11月6日 上午9:23:01
 */
@Controller
@RequestMapping(value="fullPromotion")
public class FullPromotionController {

	@Resource
	private FullPromotionSupport fullPromotionSupport;
	
	/**
	 * 满额促销分页列表查询
	 * @author yfy
	 * @date: 2015年11月6日 上午11:31:20
	 * @param model
	 * @param request
	 * @param listReq
	 */
	@Auth(keys={"boss_full_menu"})
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public void list(ModelMap model, HttpServletRequest request,FullPromotionListReq listReq) {
		LogCvt.info("满额促销分页列表查询条件:" + JSON.toJSONString(listReq));
		try {
			model.clear();
			model.putAll(fullPromotionSupport.list(listReq));
		} catch (Exception e) {
			LogCvt.error("满额促销分页列表查询请求异常"+e.getMessage(), e);
			new RespError(model, "满额促销分页列表查询失败!!!");
		}
	}
	
	/**
	 * 满额促销新增
	 * @author yfy
	 * @date: 2015年10月21日  下午16:48:15
	 * @param model
	 * @param request
	 * @param voReq
	 */
	@Auth(keys={"boss_full_add"})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public void add(ModelMap model, HttpServletRequest request,@RequestBody FullPromotionVoReq voReq) {
		LogCvt.info("满额促销新增参数:" + JSON.toJSONString(voReq));
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
			model.putAll(fullPromotionSupport.add(originVo,voReq));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("满额促销新增请求异常"+e.getMessage(), e);
			new RespError(model, "满额促销新增失败!!!");
		}
	}
	
	/**
	 * 满额促销修改
	 * @author yfy
	 * @date: 2015年10月21日 下午17:07:55
	 * @param model
	 * @param request
	 * @param voReq
	 */
	@Auth(keys={"boss_full_modify"})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public void update(ModelMap model, HttpServletRequest request,@RequestBody FullPromotionVoReq voReq) {
		LogCvt.info("满额促销修改参数:" + JSON.toJSONString(voReq));
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
			model.putAll(fullPromotionSupport.update(originVo,voReq));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("满额促销修改请求异常"+e.getMessage(), e);
			new RespError(model, "满额促销修改失败!!!");
		}
	}
	
	/**
	 * 满额促销详情
	 * @author yfy
	 * @date: 2015年10月21日 下午17:29:34
	 * @param model
	 * @param request
	 * @param clientId
	 * @param activeId
	 */
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public void detail(ModelMap model, HttpServletRequest request,String clientId,String activeId) {
		LogCvt.info("满额促销详情查询条件:clientId:" +clientId+",activeId:"+activeId);
		try {
			if(!StringUtil.isNotBlank(activeId)){
				throw new BossException("活动编号不能为空!");
			}
			if(!StringUtil.isNotBlank(clientId)){
				throw new BossException("客户端不能为空!");
			}
			model.clear();
			model.putAll(fullPromotionSupport.detail(clientId,activeId));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("满额促销详情查询请求异常"+e.getMessage(), e);
			new RespError(model, "满额促销详情查询失败!!!");
		}
	}
	
	/**
	 * 满额促销禁用
	 * @author yfy
	 * @date: 2015年10月21日 下午17:42:14
	 * @param model
	 * @param request
	 * @param clientId
	 * @param activeId
	 */
	@Auth(keys={"boss_full_disable"})
	@RequestMapping(value = "disable", method = RequestMethod.GET)
	public void disable(ModelMap model, HttpServletRequest request,String clientId,String activeId) {
		LogCvt.info("满额促销禁用参数:clientId:" +clientId+",activeId:"+activeId);
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
			model.putAll(fullPromotionSupport.disable(originVo,clientId,activeId,user.getName()));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("满额促销禁用请求异常"+e.getMessage(), e);
			new RespError(model, "满额促销禁用失败!!!");
		}
	}
	
	/**
	 * 满额促销下载交易记录
	 * @author yfy
	 * @date: 2015年10月21日 下午17:42:14
	 * @param model
	 * @param request
	 * @param clientId
	 * @param activeId
	 */
	@Auth(keys={"boss_full_export"})
	@RequestMapping(value = "download", method = RequestMethod.GET)
	public void download(ModelMap model, HttpServletRequest request,String clientId,String activeId,Boolean isPrePay) {
		LogCvt.info("满额促销下载交易记录参数:clientId:" +clientId+",activeId:"+activeId+",isPrePay:"+isPrePay);
		try {
			if(!StringUtil.isNotBlank(activeId)){
				throw new BossException("活动编号不能为空!");
			}
			if(!StringUtil.isNotBlank(clientId)){
				throw new BossException("客户端不能为空!");
			}
			OriginVo originVo = (OriginVo) request.getAttribute(Constants.ORIGIN);
			model.clear();
			model.putAll(fullPromotionSupport.download(originVo,clientId,activeId,isPrePay));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("满额促销下载交易记录请求异常"+e.getMessage(), e);
			new RespError(model, "满额促销下载交易记录失败!!!");
		}
	}
	
	/**
	 * 满赠红包个数查询
	 * @author yfy
	 * @date: 2015年10月21日 下午17:29:34
	 * @param model
	 * @param request
	 * @param clientId
	 * @param activeId
	 */
	@RequestMapping(value = "findPackage", method = RequestMethod.GET)
	public void findVouchers(ModelMap model, HttpServletRequest request,String clientId,String activeId) {
		LogCvt.info("满赠红包个数查询条件:clientId:" +clientId+",activeId:"+activeId);
		try {
			if(!StringUtil.isNotBlank(activeId)){
				throw new BossException("活动编号不能为空!");
			}
			if(!StringUtil.isNotBlank(clientId)){
				throw new BossException("客户端不能为空!");
			}
			model.clear();
			model.putAll(fullPromotionSupport.findVouchersCount(clientId,activeId));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("满赠红包个数查询请求异常"+e.getMessage(), e);
			new RespError(model, "满赠红包个数查询失败!!!");
		}
	}
	
	/**
	 * 校验数据范围
	 * @param model
	 * @param voReq
	 * @throws BossException 
	 * @throws ParseException 
	 */
	public void checkEmpty(ModelMap model,FullPromotionVoReq voReq) throws BossException, ParseException{
		
		if(voReq.getActiveName().length() >50){
			throw new BossException("只能输入0至50汉字或字符!!");
		}
		if(voReq.getDescription().length() >300){
			throw new BossException("只能输入0至300汉字或字符!");
		}
		if(voReq.getIsPrePay()){
			if(!StringUtil.isNotBlank(voReq.getMaxMoney())){
				throw new BossException("满减活动总额不能为空!");
			}
			if(!StringUtil.isNotBlank(voReq.getRetMoney())){
				throw new BossException("返限额不能为空!");
			}
			Long maxMoney = new BigDecimal(voReq.getMaxMoney()).multiply(new BigDecimal(100)).longValue();
			Long retMoney = new BigDecimal(voReq.getRetMoney()).multiply(new BigDecimal(100)).longValue();
			//金额下限
			Long minLimit = new BigDecimal(voReq.getMinLimit()).multiply(new BigDecimal(100)).longValue();
			if(retMoney > maxMoney){
				throw new BossException("返现额不能大于满减活动总额!");
			}
			if(minLimit<retMoney){
				throw new BossException("返现额不能大于金额下限!");
			}
		}
		if(StringUtil.isNotBlank(voReq.getPerDay())){
			if(voReq.getPerDay().toString().length() > 5){
				throw new BossException("每人限定的时间段长度不能超过5位数!");
			}
			if(!StringUtil.isNotBlank(voReq.getPerCount())){
				throw new BossException("每人限定的次数不能为空!");
			}
			if(voReq.getPerDay()==0){
				throw new BossException("每人限定的时间段不能为0!");
			}
			if(voReq.getPerCount()==0){
				throw new BossException("每人限定的次数不能为0!");
			}
		}
		if(StringUtil.isNotBlank(voReq.getPerCount())){
			if(voReq.getPerCount().toString().length() > 10){
				throw new BossException("每人时间段限定次数长度不能超过10位数!");
			}
			if(!StringUtil.isNotBlank(voReq.getPerDay())){
				throw new BossException("每人限定的时间段不能为空!");
			}
		}
		String startDate = voReq.getExpireStartTime();
		String endDate = voReq.getExpireEndTime();
		Long startTime = null;
		Long endTime = null;
		if(StringUtil.isNotBlank(voReq.getExpireStartTime())){
			startTime = PramasUtil.DateFormat(voReq.getExpireStartTime());
		}
		if(StringUtil.isNotBlank(voReq.getExpireEndTime())){
			endTime = PramasUtil.DateFormat(voReq.getExpireEndTime());
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date(startTime));
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date(endTime));
		Integer hh1 = Integer.valueOf(startDate.substring(11,13));
		Integer hh2 = Integer.valueOf(endDate.substring(11,13));
		Integer mm1 = cal1.get(Calendar.MINUTE);
		Integer mm2 = cal2.get(Calendar.MINUTE);
		Integer ss1 = cal1.get(Calendar.SECOND);
		Integer ss2 = cal2.get(Calendar.SECOND);
		
		if(StringUtil.isNotBlank(voReq.getTotalDay())){
			if(voReq.getTotalDay().toString().length() > 5){
				throw new BossException("整体限定的间隔量长度不能超过5位数!");
			}
			if(!StringUtil.isNotBlank(voReq.getTotalCount())){
				throw new BossException("整体限定的笔数不能为空!");
			}
			if(voReq.getTotalDay()==0){
				throw new BossException("整体限定的时间段不能为0!");
			}
			if(voReq.getTotalCount()==0){
				throw new BossException("整体限定的笔数不能为0!");
			}
			if(startTime != null && endTime != null){
				double recTime = 0;
				if(voReq.getIsTotalDay()){
					recTime = Math.ceil((double)(endTime - startTime)/1000/60/60/24);
				    Integer strat = hh1*3600+mm1*60+ss1;
				    Integer end = hh2*3600+mm2*60+ss2;
					if((strat-end)>=0){
						recTime = recTime + 1;
					}
				}else{
					Integer strat = mm1*60+ss1;
					Integer end = mm2*60+ss2;
					if((strat-end)>=0){
						recTime = Math.ceil((double)(endTime - startTime)/1000/60/60)+1;
					}else{
						recTime = Math.floor((double)(endTime - startTime)/1000/60/60)+1;
					}
				}
				if(voReq.getTotalDay() > recTime){
					throw new BossException("整体设定时间段超过范围值!");
				}
			}
		}
		if(StringUtil.isNotBlank(voReq.getTotalCount())){
			if(voReq.getTotalCount().toString().length() > 10){
				throw new BossException("整体时间段限定笔数长度不能超过10位数!");
			}
			if(!StringUtil.isNotBlank(voReq.getTotalDay())){
				throw new BossException("整体限定的时间段不能为空!");
			}
			double recTime = 0;
			if(voReq.getIsTotalDay()){//日
				recTime = Math.ceil((double)(endTime - startTime)/1000/60/60/24);
			    Integer strat = hh1*3600+mm1*60+ss1;
			    Integer end = hh2*3600+mm2*60+ss2;
				if((strat-end)>=0){
					recTime = recTime + 1;
				}
			}else{//小时
				Integer strat = mm1*60+ss1;
				Integer end = mm2*60+ss2;
				if((strat-end)>=0){
					recTime = Math.ceil((double)(endTime - startTime)/1000/60/60)+1;
				}else{
					recTime = Math.floor((double)(endTime - startTime)/1000/60/60)+1;
				}
			}
			if(voReq.getIsTotalDay().equals("0")){
				String totalNum = NumberUtil.subZeroAndDot(new BigDecimal(voReq.getMaxMoney()).divide(
						new BigDecimal(voReq.getRetMoney()),2,BigDecimal.ROUND_HALF_UP).toString());
				if(totalNum.indexOf(".")>0){
					throw new BossException("满减活动总额必须是返现额的整数倍");
				}
				if(voReq.getTotalCount() > Long.valueOf(totalNum)){
					throw new BossException("整体限定笔数不能大于整体笔数");
				}
				String totalCount = NumberUtil.subZeroAndDot(Math.floor(new BigDecimal(totalNum).divide(
						new BigDecimal(recTime),2,BigDecimal.ROUND_HALF_UP).multiply(
						new BigDecimal(voReq.getTotalDay())).doubleValue())+"");
				
				if(Long.valueOf(totalCount)==0){
					totalCount = Long.valueOf(totalCount) + 1+"";
				}
				if(voReq.getTotalCount() < Long.valueOf(totalCount)){
					throw new BossException("整体限定笔数不能低于"+Long.valueOf(totalCount)+"笔");
				}
			}
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
