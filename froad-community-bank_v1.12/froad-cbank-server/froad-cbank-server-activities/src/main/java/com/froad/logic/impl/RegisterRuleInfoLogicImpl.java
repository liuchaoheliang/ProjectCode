/**
 * @Title: RegisterRuleInfoLogicImpl.java
 * @Package com.froad.logic.impl
 * @Description: TODO
 * Copyright:2015 F-Road All Rights Reserved   
 * Company:f-road.com.cn
 * 
 * @creater froad-Joker 2015年12月1日
 * @version V1.0
 **/

package com.froad.logic.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.db.redis.SupportsRedis;
import com.froad.enums.ActiveAwardType;
import com.froad.enums.ActiveIdCode;
import com.froad.enums.ActiveItemType;
import com.froad.enums.ActiveStatus;
import com.froad.enums.ActiveType;
import com.froad.enums.ResultCode;
import com.froad.ftp.ExcelWriter;
import com.froad.handler.ActiveBaseRuleHandler;
import com.froad.handler.ActiveMainReportHandler;
import com.froad.handler.ActiveTagRelationHandler;
import com.froad.handler.RegisterRuleInfoHandler;
import com.froad.handler.impl.ActiveBaseRuleHandlerImpl;
import com.froad.handler.impl.ActiveMainReportHandlerImpl;
import com.froad.handler.impl.ActiveTagRelationHandlerImpl;
import com.froad.handler.impl.RegisterRuleInfoHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.ActiveBaseRuleLogic;
import com.froad.logic.ActiveTagRelationLogic;
import com.froad.logic.RegisterRuleInfoLogic;
import com.froad.logic.RegisteredDetailRuleLogic;
import com.froad.po.ActiveBaseRule;
import com.froad.po.ActiveTagRelation;
import com.froad.po.RegisteredExportDetail;
import com.froad.po.RegisteredRuleInfo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.AddResultVo;
import com.froad.thrift.vo.active.ExportRegisteredRuleInfoInfoRes;
import com.froad.thrift.vo.active.FindPageRegisteredRuleInfoVoResultVo;
import com.froad.thrift.vo.active.RegisteredRuleInfoPageVoRes;
import com.froad.thrift.vo.active.RegisteredRuleInfoVo;
import com.froad.util.ActiveDownUtils;
import com.froad.util.BeanUtil;
import com.froad.util.RedisKeyUtil;
import com.froad.util.Validator;

/**
 * @ClassName: RegisterRuleInfoLogicImpl
 * @Description: TODO
 * @author froad-Joker 2015年12月1日
 * @modify froad-Joker 2015年12月1日
 */

public class RegisterRuleInfoLogicImpl implements RegisterRuleInfoLogic {

	private ActiveBaseRuleLogic activeBaseRuleLogic = new ActiveBaseRuleLogicImpl();
	private ActiveTagRelationLogic activeTagRelationLogic = new ActiveTagRelationLogicImpl();
	private RegisteredDetailRuleLogic registeredDetailRuleLogic = new RegisteredDetailRuleLogicImpl();
	private RegisterRuleInfoHandler registerRuleInfoHandler = new RegisterRuleInfoHandlerImpl();

	private ActiveBaseRuleHandler activeBaseRuleHandler = new ActiveBaseRuleHandlerImpl();
	private ActiveTagRelationHandler activeTagRelationHander = new ActiveTagRelationHandlerImpl();
	
	private ActiveMainReportHandler activeMainReportHandler = new ActiveMainReportHandlerImpl();

	/**
	 * @Title: addRegisteredRuleInfo
	 * @Description: TODO
	 * @author: Joker 2015年12月1日
	 * @modify: Joker 2015年12月1日
	 * @param originVo
	 * @param registeredRuleInfo
	 * @return
	 * @see com.froad.logic.RegisterRuleInfoLogic#addRegisteredRuleInfo(com.froad.thrift.vo.OriginVo,
	 *      com.froad.po.RegisteredRuleInfo)
	 */

	@Override
	public AddResultVo addRegisteredRuleInfo(OriginVo originVo,
			RegisteredRuleInfo registeredRuleInfo) {
		AddResultVo addResultVo = new AddResultVo();
		ResultVo resultVo = new ResultVo();	
		addResultVo.setResultVo(activeBaseRuleLogic.verification(resultVo,
				registeredRuleInfo.getActiveBaseRule()));
		if (!StringUtils.equals(ResultCode.success.getCode(), addResultVo
				.getResultVo().getResultCode())) {
			return addResultVo;
		}
		addResultVo.setResultVo(registeredDetailRuleLogic.verification(resultVo,
				registeredRuleInfo.getRegisteredDetailRule()));
		if (!StringUtils.equals(ResultCode.success.getCode(), addResultVo
				.getResultVo().getResultCode())) {
			return addResultVo;
		}
		
		if(registeredRuleInfo.getRegisteredDetailRule().getTriggerType()) {
			addResultVo.setResultVo(activeTagRelationLogic.verification(resultVo,
					registeredRuleInfo.getActiveTagRelation()));
			if (!StringUtils.equals(ResultCode.success.getCode(), addResultVo
					.getResultVo().getResultCode())) {
				return addResultVo;
			}
		}		
		
		//首单满减商品绑定活动判断--start
		String itemType = registeredRuleInfo.getActiveTagRelation()
				.getItemType();
		String clientId = registeredRuleInfo.getActiveBaseRule().getClientId();
		String itemId = registeredRuleInfo.getActiveTagRelation().getItemId();
		if(registeredRuleInfo.getRegisteredDetailRule().getTriggerType()) {
			if (null != isExistProductActive(clientId, //判断有没没过期的全场活动
					ActiveItemType.unLimint.getCode(), "" , null, ActiveType.registerGive.getCode())) {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("添加活动失败,全场商品绑定了一个全场活动!");
				addResultVo.setResultVo(resultVo);
				return addResultVo;
			} 
		}
		
		if(ActiveItemType.goods.getCode().equals(itemType))
		{
			if (null != isExistProductActive(clientId, //判断输入的商品标签是否已经参加活动
					itemType, itemId, null, ActiveType.registerGive.getCode())) {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("添加活动失败,该商品标签已经绑定了活动!");
				addResultVo.setResultVo(resultVo);
				return addResultVo;
			}
		}
		if(registeredRuleInfo.getRegisteredDetailRule().getTriggerType() 
				&& activeTagRelationHander.countLimitProductActivityTag(clientId, ActiveType.registerGive.getCode()) > 0)
		{
			if (ActiveItemType.unLimint.getCode().equals(itemType)) {//已经存在一个限定商品活动，则无法在创建全场活动
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("添加活动失败,有商品绑定了活动，无法全场绑定!");
				addResultVo.setResultVo(resultVo);
				return addResultVo;
			}
		}
		//首单满减商品绑定活动判断--end
		resultVo = registerRuleInfoHandler.addRegisteredRuleInfo(originVo,
				registeredRuleInfo);

		addResultVo.setResultVo(resultVo);
		return addResultVo;
	}

	/**
	 * @Title: disableActiveRuleInfo
	 * @Description: TODO
	 * @author: Joker 2015年12月1日
	 * @modify: Joker 2015年12月1日
	 * @param clientId
	 * @param activeId
	 * @param operator
	 * @return
	 * @see com.froad.logic.RegisterRuleInfoLogic#disableActiveRuleInfo(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */

	@Override
	public ResultBean disableActiveRuleInfo(String clientId, String activeId,
			String operator) {
		ResultBean resultBean = null;
		try {
			resultBean = activeBaseRuleHandler.disableActiveBaseRuleByActiveId(
					clientId, activeId, operator);
			
			//delete redis
			String key = RedisKeyUtil.cbbank_full_regist_active_client_id_active_id(clientId, activeId);
			SupportsRedis.deleteRedisKey(key);
			SupportsRedis.del_cbbank_active_product_info(activeId);
		} catch (Exception e) {
			LogCvt.error("禁用注册促销活动异常：" + e.getMessage() , e);
		}
		return resultBean;
	}

	/**
	 * @Title: getRegisteredRuleInfoById
	 * @Description: TODO
	 * @author: Joker 2015年12月2日
	 * @modify: Joker 2015年12月2日
	 * @param clientId
	 * @param activeId
	 * @return
	 * @see com.froad.logic.RegisterRuleInfoLogic#getRegisteredRuleInfoById(java.lang.String,
	 *      java.lang.String)
	 */

	@Override
	public RegisteredRuleInfo getRegisteredRuleInfoById(String clientId,
			String activeId) {

		return registerRuleInfoHandler.getRegisteredRuleInfoById(clientId,
				activeId);
	}

	@Override
	public ResultVo updateRegisteredRuleInfo(OriginVo originVo,
			RegisteredRuleInfo registeredRuleInfo) {
		ResultVo resultVo = new ResultVo();
		// 检查前端更新信息是否完整
		resultVo = this.verification(resultVo, registeredRuleInfo.getActiveBaseRule());
		if(resultVo.getResultCode().equals(ResultCode.failed.getCode())) {
			return resultVo;
		}
		// 检查标签关系表(首单)
		if(registeredRuleInfo.getRegisteredDetailRule() != null
				&& registeredRuleInfo.getRegisteredDetailRule().getTriggerType()) {
			resultVo = this.activeTagRelationLogic.verification(resultVo, registeredRuleInfo.getActiveTagRelation());
		}
		
		if(resultVo.getResultCode().equals(ResultCode.failed.getCode())) {
			return resultVo;
		} 
		
		if(registeredRuleInfo.getRegisteredDetailRule() != null 
				&& registeredRuleInfo.getRegisteredDetailRule().getAwardType().equals(
						ActiveAwardType.unionIntegral.getCode())) {
			registeredRuleInfo.getRegisteredDetailRule().setVouchersActiveId(null);
		}
		resultVo = this.registerRuleInfoHandler.updateRegisteredRuleInfo(
				originVo, registeredRuleInfo);
		return resultVo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public FindPageRegisteredRuleInfoVoResultVo getRegisteredRuleInfoByPage(
			PageVo pageVo, RegisteredRuleInfo registeredRuleInfo) {
		FindPageRegisteredRuleInfoVoResultVo registeredRuleInfoVoResultVo = new FindPageRegisteredRuleInfoVoResultVo();
		Page<RegisteredRuleInfo> page = (Page<RegisteredRuleInfo>) BeanUtil
				.copyProperties(Page.class, pageVo);
		List<RegisteredRuleInfo> registeredRuleInfosList = this.registerRuleInfoHandler
				.getRegisteredRuleInfoByPage(page, registeredRuleInfo);
		List<RegisteredRuleInfoVo> voList = new ArrayList<RegisteredRuleInfoVo>();
		for (RegisteredRuleInfo po : registeredRuleInfosList) {
			RegisteredRuleInfoVo vo = (RegisteredRuleInfoVo) BeanUtil
					.copyProperties(RegisteredRuleInfoVo.class, po);
			voList.add(vo);
		}
		page.setResultsContent(registeredRuleInfosList);
		pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);
		RegisteredRuleInfoPageVoRes res = new RegisteredRuleInfoPageVoRes();
		res.setPage(pageVo);
		res.setRegisteredRuleInfoVoList(voList);
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("分页查询完毕");
		registeredRuleInfoVoResultVo.setResultVo(resultVo);
		registeredRuleInfoVoResultVo.setRegisteredRuleInfoPageVoRes(res);
		return registeredRuleInfoVoResultVo;
	}

	@Override
	public ExportRegisteredRuleInfoInfoRes exportRegisteredRuleInfoInfoResUrl(
			RegisteredRuleInfo registeredRuleInfo) {
		String url = null;
		ExportRegisteredRuleInfoInfoRes res = new ExportRegisteredRuleInfoInfoRes();
		ActiveDownUtils down = new ActiveDownUtils();
		List<RegisteredExportDetail> exportDetailList = new ArrayList<RegisteredExportDetail>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			long startTime = System.currentTimeMillis();
			// 每页最大条数
			int everySheetNum = 50000;
			ExcelWriter excelWriter = new ExcelWriter(',',"GBK");
			String excelFileName = registeredRuleInfo.getActiveBaseRule()
					.getClientId()
					+ "_"
					+ ActiveIdCode.ZC.getCode()
					+ "_"
					+ formatter.format(new Date(System.currentTimeMillis()));
			if(registeredRuleInfo.getRegisteredDetailRule() != null &&
					!registeredRuleInfo.getRegisteredDetailRule().getTriggerType()) {
				LogCvt.debug("导出注册送明细---" 
					+ registeredRuleInfo.getRegisteredDetailRule().getTriggerType());
				exportDetailList = this.registerRuleInfoHandler
						.getRegisteredExportDetail(registeredRuleInfo);
				if (exportDetailList.size() > 0) {
					excelWriter.writeCsv(down.downRegisteredDetaiHeader(),
							down.downRegisteredDetaiData(exportDetailList),
							excelFileName);
				} else {
					excelWriter.writeCsv(down.downRegisteredDetaiHeader(),
							down.downRegisteredDetaiData(null), excelFileName);
				}

				url = excelWriter.getUrl();
			} else if (registeredRuleInfo.getRegisteredDetailRule() != null &&
					registeredRuleInfo.getRegisteredDetailRule().getTriggerType()) {
				LogCvt.debug("导出首单满减明细---" + registeredRuleInfo.getRegisteredDetailRule().getTriggerType());
				url = this.activeMainReportHandler.getActiveMainReportURL(
						registeredRuleInfo.getActiveBaseRule().getActiveId(), 
						registeredRuleInfo.getActiveBaseRule().getClientId(),
						"1",
						ActiveIdCode.ZC.getCode());
			}
			
			LogCvt.debug("导出注册活动交易明细url:" + url);
			res.setExportUrl(url);
			long endTime = System.currentTimeMillis();
			LogCvt.debug("下载耗时:" + (endTime - startTime) + "--startTime:"
					+ startTime + "---endTime:" + endTime);
		} catch (Exception e) {
			LogCvt.error("导出注册交易明细异常：" + e.getMessage(), e);
		}

		return res;
	}
	
	public ActiveTagRelation isExistProductActive(String clientId, String itemType, String itemId, String activeId, String type) {
		
		return activeTagRelationHander.getAvailableActive(clientId, itemType, itemId, activeId, type);
		
	}
	
	/**
	 * 校验注册基础信息
	 * @param resultVo
	 * @param activeBaseRule
	 * @return
	 */
	private ResultVo verification(ResultVo resultVo, //AddResultVo addResultVo,
			ActiveBaseRule activeBaseRule) {		
		if(null == activeBaseRule)
		{
			LogCvt.error("添加activeRuleInfo失败,原因:activeBaseRule不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:活动基础信息内容不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}
		else if(null != activeBaseRuleHandler.findOneActiveBaseRuleByActiveNameAndClientId(activeBaseRule))
		{
			LogCvt.error("该营销活动名称已经存在!");
			resultVo.setResultDesc("该营销活动名称已经存在!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}
		else if(Validator.isEmptyStr(activeBaseRule.getClientId())){
			LogCvt.error("添加activeBaseRule失败,原因:ClientId不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:客户端不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}
		else if(Validator.isEmptyStr(activeBaseRule.getSettleType()))
		{
			LogCvt.error("添加activeBaseRule失败,原因:SettleType不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:结算方式不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}
		else if(null == ActiveStatus.getType(activeBaseRule.getSettleType()))
		{
			LogCvt.error("添加activeBaseRule失败,原因:SettleType不在范围内!");
			resultVo.setResultDesc("添加活动失败,原因:结算方式不在范围内!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}
		else if(Validator.isEmptyStr(activeBaseRule.getType()))
		{
			LogCvt.error("添加activeBaseRule失败,原因:Type不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:活动类型不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}
		else if(Validator.isEmptyStr(activeBaseRule.getActiveName()))
		{
			LogCvt.error("添加activeBaseRule失败,原因:ActiveName不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:活动名称不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}	
		else if(activeBaseRule.getExpireEndTime().before(activeBaseRule.getExpireStartTime()))
		{
			LogCvt.error("添加activeBaseRule失败,原因:活动结束时间不能早于活动开始时间!");
			resultVo.setResultDesc("添加活动失败,原因:活动结束时间不能早于活动开始时间!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}			
		else if((activeBaseRule.getMerchantRate())
				+(activeBaseRule.getBankRate())
				+(activeBaseRule.getFftRate())!=100000)
		{
			LogCvt.error("添加activeBaseRule失败,原因:补贴比例加起来不是100%!");
			resultVo.setResultDesc("添加活动失败,原因:补贴比例加起来不是100%!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}
			
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("验证营销基础信息正确");
		return resultVo;
	}

}
