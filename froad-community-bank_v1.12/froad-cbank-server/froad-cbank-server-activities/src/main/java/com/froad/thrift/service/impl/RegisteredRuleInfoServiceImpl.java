/**
 * @Title: RegisteredRuleInfoServiceImpl.java
 * @Package com.froad.thrift.service.impl
 * @Description: TODO
 * Copyright:2015 F-Road All Rights Reserved   
 * Company:f-road.com.cn
 * 
 * @creater froad-Joker 2015年12月1日
 * @version V1.0
 **/

package com.froad.thrift.service.impl;

import org.apache.thrift.TException;

import com.alibaba.druid.util.StringUtils;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.enums.ActiveType;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.RegisterRuleInfoLogic;
import com.froad.logic.impl.RegisterRuleInfoLogicImpl;
import com.froad.po.ActiveBaseRule;
import com.froad.po.RegisteredRuleInfo;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.RegisteredRuleInfoService.Iface;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.AddResultVo;
import com.froad.thrift.vo.active.ExportRegisteredRuleInfoInfoRes;
import com.froad.thrift.vo.active.FindAllRegisteredRuleInfoVoResultVo;
import com.froad.thrift.vo.active.FindPageRegisteredRuleInfoVoResultVo;
import com.froad.thrift.vo.active.FindRegisteredRuleInfoVoResultVo;
import com.froad.thrift.vo.active.RegisteredRuleInfoVo;
import com.froad.util.ActiveUtils;
import com.froad.util.BeanUtil;

/**
 * @ClassName: RegisteredRuleInfoServiceImpl
 * @Description: TODO
 * @author froad-Joker 2015年12月1日
 * @modify froad-Joker 2015年12月1日
 */

public class RegisteredRuleInfoServiceImpl extends BizMonitorBaseService
		implements Iface {

	public RegisteredRuleInfoServiceImpl() {
	}

	public RegisteredRuleInfoServiceImpl(String n, String v) {
		super(n, v);
	}

	private RegisterRuleInfoLogic registerRuleInfoLogic = new RegisterRuleInfoLogicImpl();

	/**
	 * @Title: addRegisteredRuleInfo
	 * @Description: TODO
	 * @author: Joker 2015年12月1日
	 * @modify: Joker 2015年12月1日
	 * @param arg0
	 * @param arg1
	 * @return
	 * @throws TException
	 * @see com.froad.thrift.service.RegisteredRuleInfoService.Iface#addRegisteredRuleInfo(com.froad.thrift.vo.OriginVo,
	 *      com.froad.thrift.vo.active.RegisteredRuleInfoVo)
	 */

	@Override
	public AddResultVo addRegisteredRuleInfo(OriginVo originVo,
			RegisteredRuleInfoVo registeredRuleInfoVo) throws TException {
		LogCvt.info("添加[RegisteredRuleInfo]方法开始");
		RegisteredRuleInfo registeredRuleInfo = (RegisteredRuleInfo) BeanUtil
				.copyProperties(RegisteredRuleInfo.class, registeredRuleInfoVo);
		AddResultVo addResultVo = registerRuleInfoLogic.addRegisteredRuleInfo(
				originVo, registeredRuleInfo);
		LogCvt.info("添加[RegisteredRuleInfo]方法结束");
		return addResultVo;
	}

	/**
	 * @Title: disableRegisteredRuleInfo
	 * @Description: TODO
	 * @author: Joker 2015年12月1日
	 * @modify: Joker 2015年12月1日
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @return
	 * @throws TException
	 * @see com.froad.thrift.service.RegisteredRuleInfoService.Iface#disableRegisteredRuleInfo(com.froad.thrift.vo.OriginVo,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */

	@Override
	public ResultVo disableRegisteredRuleInfo(OriginVo originVo,
			String clientId, String activeId, String operator)
			throws TException {

		LogCvt.info("禁止[RegisteredRuleInfo]方法开始");
		ResultBean result = registerRuleInfoLogic.disableActiveRuleInfo(
				clientId, activeId, operator);
		ResultVo resultVo = new ResultVo(ResultCode.success.getCode(), "禁止活动成功");
		if (!StringUtils.equals(ResultCode.success.getCode(),
				result.getResultCode())) {
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
		}
		LogCvt.info("禁止[RegisteredRuleInfo]方法结束");
		return resultVo;
	}

	/**
	 * @Title: getRegisteredRuleInfo
	 * @Description: 查询 RegisteredRuleInfo 列表
	 * @author: Joker 2015年12月1日
	 * @modify: Joker 2015年12月1日
	 * @param arg0
	 * @return
	 * @throws TException
	 * @see com.froad.thrift.service.RegisteredRuleInfoService.Iface#getRegisteredRuleInfo(com.froad.thrift.vo.active.RegisteredRuleInfoVo)
	 */
	@Override
	public FindAllRegisteredRuleInfoVoResultVo getRegisteredRuleInfo(
			RegisteredRuleInfoVo registeredRuleInfoVo) throws TException {

		return null;
	}

	/**
	 * @Title: getRegisteredRuleInfoById
	 * @Description: TODO
	 * @author: Joker 2015年12月1日
	 * @modify: Joker 2015年12月1日
	 * @param arg0
	 * @param arg1
	 * @return
	 * @throws TException
	 * @see com.froad.thrift.service.RegisteredRuleInfoService.Iface#getRegisteredRuleInfoById(java.lang.String,
	 *      java.lang.String)
	 */

	@Override
	public FindRegisteredRuleInfoVoResultVo getRegisteredRuleInfoById(
			String clientId, String activeId) throws TException {
		LogCvt.info("查询[RegisteredRuleInfoById]方法开始");
		FindRegisteredRuleInfoVoResultVo findRegisteredRuleInfoVoResultVo = new FindRegisteredRuleInfoVoResultVo();
		ResultVo resultVo = new ResultVo(ResultCode.success.getCode(),
				"查询注册送活动成功");
		RegisteredRuleInfo RegisteredRuleInfo = registerRuleInfoLogic
				.getRegisteredRuleInfoById(clientId, activeId);
		if (null == RegisteredRuleInfo) {
			resultVo = new ResultVo(ResultCode.failed.getCode(),
					"查询注册送活动失败,没有这个注册送活动");
		}
		findRegisteredRuleInfoVoResultVo.setResultVo(resultVo);

		RegisteredRuleInfoVo registeredRuleInfoVo = (RegisteredRuleInfoVo) BeanUtil
				.copyProperties(RegisteredRuleInfoVo.class, RegisteredRuleInfo);

		findRegisteredRuleInfoVoResultVo
				.setRegisteredRuleInfoVo(registeredRuleInfoVo);
		LogCvt.info("查询[RegisteredRuleInfoById]方法结束");
		return findRegisteredRuleInfoVoResultVo;
	}

	/**
	 * @Title: getRegisteredRuleInfoByPage
	 * @Description: TODO
	 * @author: Joker 2015年12月1日
	 * @modify: Joker 2015年12月1日
	 * @param arg0
	 * @param arg1
	 * @return
	 * @throws TException
	 * @see com.froad.thrift.service.RegisteredRuleInfoService.Iface#getRegisteredRuleInfoByPage(com.froad.thrift.vo.PageVo,
	 *      com.froad.thrift.vo.active.RegisteredRuleInfoVo)
	 */

	@Override
	public FindPageRegisteredRuleInfoVoResultVo getRegisteredRuleInfoByPage(
			PageVo pageVo, RegisteredRuleInfoVo registeredRuleInfoVo)
			throws TException {
		FindPageRegisteredRuleInfoVoResultVo registeredRuleInfoVoResultVo = new FindPageRegisteredRuleInfoVoResultVo();
		RegisteredRuleInfo registeredRuleInfo = (RegisteredRuleInfo) BeanUtil
				.copyProperties(RegisteredRuleInfo.class, registeredRuleInfoVo);
		/*registeredRuleInfo.getActiveBaseRule().setType(
				ActiveType.registerGive.getCode());*/
		ActiveBaseRule baseRule = ActiveUtils.processingNullData(
				registeredRuleInfoVo.getActiveBaseRule(),
				registeredRuleInfo.getActiveBaseRule());
		registeredRuleInfo.setActiveBaseRule(baseRule);
		registeredRuleInfoVoResultVo = this.registerRuleInfoLogic
				.getRegisteredRuleInfoByPage(pageVo, registeredRuleInfo);
		return registeredRuleInfoVoResultVo;
	}

	/**
	 * @Title: updateRegisteredRuleInfo
	 * @Description: 更新注册活动规则
	 * @author: Joker 2015年12月1日
	 * @modify: Joker 2015年12月1日
	 * @param originVo
	 *            源对象信息(包含平台,操作ip,操作员id等...)
	 * @param registeredRuleInfoVo
	 *            注册活动信息
	 * @return
	 * @throws TException
	 * @see com.froad.thrift.service.RegisteredRuleInfoService.Iface#updateRegisteredRuleInfo(com.froad.thrift.vo.OriginVo,
	 *      com.froad.thrift.vo.active.RegisteredRuleInfoVo)
	 */
	@Override
	public ResultVo updateRegisteredRuleInfo(OriginVo originVo,
			RegisteredRuleInfoVo registeredRuleInfoVo) throws TException {
		RegisteredRuleInfo registeredRuleInfo = (RegisteredRuleInfo) BeanUtil
				.copyProperties(RegisteredRuleInfo.class, registeredRuleInfoVo);
		ResultVo resultVo = this.registerRuleInfoLogic
				.updateRegisteredRuleInfo(originVo, registeredRuleInfo);
		return resultVo;
	}

	@Override
	public ExportRegisteredRuleInfoInfoRes exportRegisteredRuleInfoInfoResUrl(
			RegisteredRuleInfoVo registeredRuleInfoVo) throws TException {
		RegisteredRuleInfo registeredRuleInfo = (RegisteredRuleInfo) BeanUtil
				.copyProperties(RegisteredRuleInfo.class, registeredRuleInfoVo);
		ExportRegisteredRuleInfoInfoRes res = this.registerRuleInfoLogic
				.exportRegisteredRuleInfoInfoResUrl(registeredRuleInfo);
		return res;
	}

}
