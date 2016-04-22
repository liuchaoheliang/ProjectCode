package com.froad.thrift.service.impl;

import org.apache.thrift.TException;

import com.froad.db.mysql.bean.Page;
import com.froad.enums.ActiveType;
import com.froad.logic.VouchersRuleInfoLogic;
import com.froad.logic.impl.VouchersRuleInfoLogicImpl;
import com.froad.po.ActiveBaseRule;
import com.froad.po.VouchersRuleInfo;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.VouchersRuleInfoService.Iface;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.AddResultVo;
import com.froad.thrift.vo.active.ExportVouchersDetailInfo;
import com.froad.thrift.vo.active.FindAllVouchersRuleInfoVoResultVo;
import com.froad.thrift.vo.active.FindPageVouchersRuleInfoVoResultVo;
import com.froad.thrift.vo.active.FindVouchersRuleInfoVoResultVo;
import com.froad.thrift.vo.active.TemporaryVouchersDetailInfoVO;
import com.froad.thrift.vo.active.VouchersRuleInfoVo;
import com.froad.util.ActiveUtils;
import com.froad.util.BeanUtil;

/**
 * @ClassName: VouchersRuleInfoServiceImpl
 * @Description: 代金券规则信息服务
 * @author froad-shenshaocheng 2015年11月26日
 * @modify froad-shenshaocheng 2015年11月26日
 */
public class VouchersRuleInfoServiceImpl extends BizMonitorBaseService
		implements Iface {

	private VouchersRuleInfoLogic vouchersRuleInfoLogic = new VouchersRuleInfoLogicImpl();

	public VouchersRuleInfoServiceImpl(String n, String v) {
		super(n, v);
	}

	/**
	 * @Title: addVouchersRuleInfo
	 * @Description: 新增代金券规则
	 * @author: shenshaocheng 2015年11月26日
	 * @modify: shenshaocheng 2015年11月26日
	 * @param originVo
	 *            源对象信息(包含平台,操作ip,操作员id等...)
	 * @param vouchersRuleInfoVo
	 *            代金券详细信息
	 * @return 返回 新增结果
	 * @throws TException
	 * @see com.froad.thrift.service.VouchersRuleInfoService.Iface#addVouchersRuleInfo(com.froad.thrift.vo.OriginVo,
	 *      com.froad.thrift.vo.active.VouchersRuleInfoVo)
	 */
	@Override
	public AddResultVo addVouchersRuleInfo(OriginVo originVo,
			VouchersRuleInfoVo vouchersRuleInfoVo) throws TException {
		// 类型转换
		VouchersRuleInfo vouchersRuleInfo = (VouchersRuleInfo) BeanUtil
				.copyProperties(VouchersRuleInfo.class, vouchersRuleInfoVo);
		AddResultVo addResultVo = vouchersRuleInfoLogic.addVouchersRuleInfo(
				originVo, vouchersRuleInfo);
		return addResultVo;
	}

	/**
	 * @Title: disableVouchersRuleInfo
	 * @Description: 禁用 代金券规则
	 * @author: shenshaocheng 2015年11月26日
	 * @modify: shenshaocheng 2015年11月26日
	 * @param originVo
	 *            源对象信息(包含平台,操作ip,操作员id等...)
	 * @param clientId
	 *            客户端
	 * @param activeId
	 *            活动id
	 * @param operator
	 *            操作员
	 * @return 返回操作结果
	 * @throws TException
	 * @see com.froad.thrift.service.VouchersRuleInfoService.Iface#disableVouchersRuleInfo(com.froad.thrift.vo.OriginVo,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ResultVo disableVouchersRuleInfo(OriginVo originVo, String clientId,
			String activeId, String operator) throws TException {
		ResultVo resultVo = this.vouchersRuleInfoLogic.disableVouchersRuleInfo(
				originVo, clientId, activeId, operator);
		return resultVo;
	}

	/**
	 * @Title: getActiveRuleInfo
	 * @Description: 查询 代金券列表
	 * @author: shenshaocheng 2015年11月26日
	 * @modify: shenshaocheng 2015年11月26日
	 * @param vouchersRuleInfoVo
	 *            代金券查询条件集
	 * @return 返回代金券列表
	 * @throws TException
	 * @see com.froad.thrift.service.VouchersRuleInfoService.Iface#getActiveRuleInfo(com.froad.thrift.vo.active.VouchersRuleInfoVo)
	 */
	@Override
	public FindAllVouchersRuleInfoVoResultVo getActiveRuleInfo(
			VouchersRuleInfoVo vouchersRuleInfoVo) throws TException {
		VouchersRuleInfo vouchersRuleInfo = (VouchersRuleInfo) BeanUtil
				.copyProperties(VouchersRuleInfo.class, vouchersRuleInfoVo);
		ActiveBaseRule baseRule = new ActiveBaseRule();
		baseRule = ActiveUtils.processingNullData(
				vouchersRuleInfoVo.getActiveBaseRule(), vouchersRuleInfo.getActiveBaseRule());
		baseRule.setType(ActiveType.vouchers.getCode());
		vouchersRuleInfo.setActiveBaseRule(baseRule);
		FindAllVouchersRuleInfoVoResultVo vo = this.vouchersRuleInfoLogic
				.getActiveRuleInfo(vouchersRuleInfo);
		return vo;
	}

	/**
	 * @Title: getActiveRuleInfoById
	 * @Description: 查询 单个 代金券信息
	 * @author: shenshaocheng 2015年11月26日
	 * @modify: shenshaocheng 2015年11月26日
	 * @param clientId
	 *            客户端ID
	 * @param activeId
	 *            活动ID
	 * @return
	 * @throws TException
	 * @see com.froad.thrift.service.VouchersRuleInfoService.Iface#getActiveRuleInfoById(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public FindVouchersRuleInfoVoResultVo getActiveRuleInfoById(
			String clientId, String activeId) throws TException {
		FindVouchersRuleInfoVoResultVo vo = this.vouchersRuleInfoLogic
				.getActiveRuleInfoById(clientId, activeId);
		return vo;
	}

	/**
	 * @Title: getActiveRuleInfoByPage
	 * @Description: 分页查询代金券列表
	 * @author: shenshaocheng 2015年11月26日
	 * @modify: shenshaocheng 2015年11月26日
	 * @param pageVo
	 *            分页信息
	 * @param vouchersRuleInfoVo
	 *            代金券查询条件集
	 * @return 返回分页列表结果
	 * @throws TException
	 * @see com.froad.thrift.service.VouchersRuleInfoService.Iface#getActiveRuleInfoByPage(com.froad.thrift.vo.PageVo,
	 *      com.froad.thrift.vo.active.VouchersRuleInfoVo)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public FindPageVouchersRuleInfoVoResultVo getActiveRuleInfoByPage(
			PageVo pageVo, VouchersRuleInfoVo vouchersRuleInfoVo)
			throws TException {
		FindPageVouchersRuleInfoVoResultVo findPageVouchersRuleInfoVoResultVo = new FindPageVouchersRuleInfoVoResultVo();
		Page page = (Page) BeanUtil.copyProperties(Page.class, pageVo);
		VouchersRuleInfo vouchersRuleInfo = (VouchersRuleInfo) BeanUtil
				.copyProperties(VouchersRuleInfo.class, vouchersRuleInfoVo);
		ActiveBaseRule baseRule = new ActiveBaseRule();
		baseRule = ActiveUtils.processingNullData(
				vouchersRuleInfoVo.getActiveBaseRule(), vouchersRuleInfo.getActiveBaseRule());
		baseRule.setType(ActiveType.vouchers.getCode());
		vouchersRuleInfo.setActiveBaseRule(baseRule);
		findPageVouchersRuleInfoVoResultVo = this.vouchersRuleInfoLogic
				.getActiveRuleInfoByPage(page, vouchersRuleInfo);
		return findPageVouchersRuleInfoVoResultVo;
	}

	/**
	 * @Title: updateVouchersRuleInfo
	 * @Description: 更新代金券规则
	 * @author: shenshaocheng 2015年11月26日
	 * @modify: shenshaocheng 2015年11月26日
	 * @param originVo
	 *            源对象信息(包含平台,操作ip,操作员id等...)
	 * @param vouchersRuleInfoVo
	 *            代金券信息
	 * @return 返回更新结果
	 * @throws TException
	 * @see com.froad.thrift.service.VouchersRuleInfoService.Iface#updateVouchersRuleInfo(com.froad.thrift.vo.OriginVo,
	 *      com.froad.thrift.vo.active.VouchersRuleInfoVo)
	 */
	@Override
	public ResultVo updateVouchersRuleInfo(OriginVo originVo,
			VouchersRuleInfoVo vouchersRuleInfoVo) throws TException {
		VouchersRuleInfo po = (VouchersRuleInfo) BeanUtil.copyProperties(
				VouchersRuleInfo.class, vouchersRuleInfoVo);
		ResultVo resultVo = this.vouchersRuleInfoLogic.updateVouchersRuleInfo(
				originVo, po);
		return resultVo;
	}

	/**
	 * @Title: exportVouchersDetailInfo
	 * @Description: 下载红包劵码明细信息
	 * @author: shenshaocheng 2015年11月29日
	 * @modify: shenshaocheng 2015年11月29日
	 * @param clientId
	 *            客户端ID
	 * @param activeId
	 *            活动ID
	 * @return
	 * @throws TException
	 * @see com.froad.thrift.service.VouchersRuleInfoService.Iface#exportVouchersDetailInfo(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public ExportVouchersDetailInfo exportVouchersDetailInfo(String clientId,
			String activeId) throws TException {
		ExportVouchersDetailInfo vouchersDetailInfo = this.vouchersRuleInfoLogic
				.exportVouchersDetailInfo(clientId, activeId);
		return vouchersDetailInfo;
	}

	/**
	 * @Title: addTemporaryVouchersRuleInfo
	 * @Description: 保存红包券码临时信息
	 * @author: shenshaocheng 2015年12月1日
	 * @modify: shenshaocheng 2015年12月1日
	 * @param originVo
	 * @param temporaryVouchersDetailInfoVO
	 * @return
	 * @throws TException
	 * @see com.froad.thrift.service.VouchersRuleInfoService.Iface#addTemporaryVouchersRuleInfo(com.froad.thrift.vo.OriginVo,
	 *      com.froad.thrift.vo.active.TemporaryVouchersDetailInfoVO)
	 */
	@Override
	public AddResultVo addTemporaryVouchersRuleInfo(OriginVo originVo,
			TemporaryVouchersDetailInfoVO temporaryVouchersDetailInfoVO)
			throws TException {		
		AddResultVo addResultVo = vouchersRuleInfoLogic
				.addTemporaryVouchersRuleInfo(originVo, temporaryVouchersDetailInfoVO);
		return addResultVo;
	}
}
