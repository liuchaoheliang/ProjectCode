package com.froad.logic.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.enums.ActiveIdCode;
import com.froad.enums.ActiveStatus;
import com.froad.enums.ActiveType;
import com.froad.enums.ResultCode;
import com.froad.ftp.ExcelWriter;
import com.froad.handler.VouchersInfoHandler;
import com.froad.handler.VouchersRuleInfoHandler;
import com.froad.handler.impl.VouchersInfoHandlerImpl;
import com.froad.handler.impl.VouchersRuleInfoHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.ActiveBaseRuleLogic;
import com.froad.logic.ActiveSustainRelationLogic;
import com.froad.logic.ActiveTagRelationLogic;
import com.froad.logic.VouchersRuleInfoLogic;
import com.froad.po.ActiveBaseRule;
import com.froad.po.ActiveSustainRelation;
import com.froad.po.ActiveTagRelation;
import com.froad.po.Vouchers;
import com.froad.po.VouchersCouponCode;
import com.froad.po.VouchersDetailRule;
import com.froad.po.VouchersInfo;
import com.froad.po.VouchersRuleInfo;
import com.froad.po.VouchersUseDetails;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.AddResultVo;
import com.froad.thrift.vo.active.ExportVouchersDetailInfo;
import com.froad.thrift.vo.active.FindAllVouchersRuleInfoVoResultVo;
import com.froad.thrift.vo.active.FindPageVouchersRuleInfoVoResultVo;
import com.froad.thrift.vo.active.FindVouchersRuleInfoVoResultVo;
import com.froad.thrift.vo.active.TemporaryVouchersDetailInfoVO;
import com.froad.thrift.vo.active.VouchersCouponCodeVO;
import com.froad.thrift.vo.active.VouchersRuleInfoPageVoRes;
import com.froad.thrift.vo.active.VouchersRuleInfoVo;
import com.froad.util.ActiveDownUtils;
import com.froad.util.BeanUtil;
import com.froad.util.CreateRandomCode;

public class VouchersRuleInfoLogicImpl implements VouchersRuleInfoLogic {

	private VouchersRuleInfoHandler handler = new VouchersRuleInfoHandlerImpl();

	private VouchersInfoHandler vouchersInfoHandler = new VouchersInfoHandlerImpl();

	private ActiveBaseRuleLogic baseRuleLogic = new ActiveBaseRuleLogicImpl();

	private ActiveTagRelationLogic tagRelationLogic = new ActiveTagRelationLogicImpl();

	private ActiveSustainRelationLogic sustainRelationLogic = new ActiveSustainRelationLogicImpl();

	/**
	 * @Title: addVouchersRuleInfo
	 * @Description: 新增代金券规则
	 * @author: shenshaocheng 2015年11月26日
	 * @modify: shenshaocheng 2015年11月26日
	 * @param originVo
	 *            源对象信息(包含平台,操作ip,操作员id等...)
	 * @param vouchersRuleInfo
	 *            代金券信息集
	 * @return 返回 新增结果
	 * @see com.froad.logic.VouchersRuleInfoLogic#addVouchersRuleInfo(com.froad.thrift.vo.OriginVo,
	 *      com.froad.po.VouchersRuleInfo)
	 */
	@Override
	public AddResultVo addVouchersRuleInfo(OriginVo originVo,
			VouchersRuleInfo vouchersRuleInfo) {
		AddResultVo addResultVo = new AddResultVo();
		ResultVo resultVo = new ResultVo();
		//Date systemDate = this.getSystemDate();
		// 检查前端新增基准表信息是否完整
		resultVo = this.baseRuleLogic.verification(resultVo,
				vouchersRuleInfo.getActiveBaseRule());
		if (resultVo.getResultCode().equals(ResultCode.failed.getCode())) {
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		// 检查标签关系表
		resultVo = tagRelationLogic.verification(resultVo,
				vouchersRuleInfo.getActiveTagRelation());
		if (resultVo.getResultCode().equals(ResultCode.failed.getCode())) {
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}

		if (vouchersRuleInfo != null
				&& vouchersRuleInfo.getActiveBaseRule() != null
				&& vouchersRuleInfo.getVouchersDetailRule() != null) {
			String activeId = this.createActiveId(vouchersRuleInfo
					.getActiveBaseRule().getClientId());
			vouchersRuleInfo.getActiveBaseRule().setStatus(
					ActiveStatus.launch.getCode());
			vouchersRuleInfo.getActiveBaseRule().setActiveId(activeId);
			List<VouchersInfo> vouchersInfoList = new ArrayList<VouchersInfo>();
			List<ActiveSustainRelation> activeSustainRelationList = new ArrayList<ActiveSustainRelation>();
			if (vouchersRuleInfo.getVouchersDetailRule() != null
					&& vouchersRuleInfo.getVouchersDetailRule().getVouchersTotal() > 0) {				
				// 判断上传个数是否正确，如果存在余数，表示红包个数与总金额不匹配。
				double totalCount = vouchersRuleInfo.getVouchersDetailRule()
						.getTotalMoney()
						/ vouchersRuleInfo.getVouchersDetailRule()
								.getMinMoney();
				
				if (totalCount != vouchersRuleInfo.getVouchersDetailRule().getVouchersTotal()) {
					resultVo.setResultCode(ResultCode.failed.getCode());
					resultVo.setResultDesc("上传券码个数不对。");
					addResultVo.setResultVo(resultVo);
					LogCvt.debug("上传券码个数不对.");
					return addResultVo;
				}
				if (vouchersRuleInfo.getVouchersDetailRule() != null
						&& vouchersRuleInfo.getVouchersDetailRule()
								.getActiveSustainRelationList().size() > 0) {
					activeSustainRelationList = vouchersRuleInfo
							.getVouchersDetailRule()
							.getActiveSustainRelationList();
				}

				vouchersRuleInfo
						.setActiveSustainRelationList(activeSustainRelationList);
				vouchersRuleInfo.setVouchersInfoList(vouchersInfoList);
				// 初始化限购次数
				if(vouchersRuleInfo.getVouchersDetailRule().getPerDay() < 1) {
					vouchersRuleInfo.getVouchersDetailRule().setPerDay(1);
				}
				
				long resultCode = handler.addVouchersRuleInfo(originVo,
						vouchersRuleInfo);
				if (resultCode > 2) {
					resultVo.setResultCode(ResultCode.success.getCode());
					String mesString = "新增红包规则成功";
					resultVo.setResultDesc(mesString);
					LogCvt.debug("新增红包规则成功。");
				} else {
					resultVo.setResultCode(ResultCode.failed.getCode());
					String mesString = "新增红包规则失败。";
					resultVo.setResultDesc(mesString);
					LogCvt.debug("新增红包规则失败。更新数目为 " + resultCode);
				}

			} else {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("红包规则明细参数VouchersDetailRule不能为空");
				LogCvt.debug("红包规则明细参数VouchersDetailRule不能为空");
			}
		} else {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("红包基础规则参数vouchersRuleInfo不能为空");
			LogCvt.debug("红包基础规则参数vouchersRuleInfo不能为空");
		}

		addResultVo.setResultVo(resultVo);
		return addResultVo;
	}

	@Override
	public AddResultVo addTemporaryVouchersRuleInfo(OriginVo originVo,
			TemporaryVouchersDetailInfoVO temporaryVouchersDetailInfoVO) {
		LogCvt.info("-----上传券码开始--------");
		AddResultVo addResultVo = new AddResultVo();
		ResultVo resultVo = new ResultVo();
		// 生成随机字符串，作为临时数据唯一标识返回前端
		String activeId = "";		
		if(temporaryVouchersDetailInfoVO.getActiveId() > 0) {
			activeId = temporaryVouchersDetailInfoVO.getActiveId() + "";
		} else {
			activeId = CreateRandomCode.getRandomString(16);
		}
	    
		List<VouchersInfo> vouchersInfoList = new ArrayList<VouchersInfo>();
		for (VouchersCouponCodeVO vo : temporaryVouchersDetailInfoVO
				.getVouchersCouponCodelist()) {
			VouchersInfo po = new VouchersInfo();
			po.setActiveId(activeId);
			po.setVouchersId(vo.getVouchersIds());
			po.setClientId("Temporary");
			po.setIsUpload(true);
			vouchersInfoList.add(po);
		}
		if (vouchersInfoList != null && vouchersInfoList.size() > 0) {
			long resultCode = handler.addTemporaryVouchersRuleInfo(originVo,
					vouchersInfoList);
			resultVo.setResultCode(resultCode > 0 ? ResultCode.success
					.getCode() : ResultCode.failed.getCode());
			String mesString = resultCode > 0 ? "新增临时红包券码成功" : "新增临时红包券码 数据失败";
			resultVo.setResultDesc(mesString);
			LogCvt.info("新增临时红包券码 ：" + mesString);
		} else {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("红包券码不能为空");
			LogCvt.info("红包券码不能为空");
		}
		
		addResultVo.setId(Long.parseLong(activeId));
		addResultVo.setResultVo(resultVo);
		return addResultVo;
	}

	/**
	 * @Title: disableVouchersRuleInfo
	 * @Description: 禁用红包规则
	 * @author: shenshaocheng 2015年11月28日
	 * @modify: shenshaocheng 2015年11月28日
	 * @param originVo
	 *            源标识
	 * @param clientId
	 *            客户端id
	 * @param activeId
	 *            活动ID
	 * @param operator
	 *            操作员
	 * @return 返回更新结果
	 * @see com.froad.logic.VouchersRuleInfoLogic#disableVouchersRuleInfo(com.froad.thrift.vo.OriginVo,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ResultVo disableVouchersRuleInfo(OriginVo originVo, String clientId,
			String activeId, String operator) {
		int result = handler.disableVouchersRuleInfo(originVo, clientId,
				activeId, operator);
		ResultVo resultVo = new ResultVo();
		if (result > 0) {
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc("禁用红包规则成功。");
		} else {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("禁用红包规则失败。");
		}

		return resultVo;
	}

	@Override
	public FindAllVouchersRuleInfoVoResultVo getActiveRuleInfo(
			VouchersRuleInfo vouchersRuleInfo) {
		FindAllVouchersRuleInfoVoResultVo allVouchersRulevo = new FindAllVouchersRuleInfoVoResultVo();
		List<Vouchers> vouchersList = this.handler
				.getActiveRuleInfo(vouchersRuleInfo);
		List<VouchersRuleInfo> vouchersRuleInfoList = this
				.assembleVouchersInfosList(vouchersList);
		allVouchersRulevo.vouchersRuleInfoList = new ArrayList<VouchersRuleInfoVo>();
		for (VouchersRuleInfo po : vouchersRuleInfoList) {
			VouchersRuleInfoVo vo = (VouchersRuleInfoVo) BeanUtil
					.copyProperties(VouchersRuleInfoVo.class, po);
			allVouchersRulevo.vouchersRuleInfoList.add(vo);
		}

		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("共获取 " + vouchersRuleInfoList.size() + "条数据");
		allVouchersRulevo.resultVo = resultVo;
		return allVouchersRulevo;
	}

	@Override
	public FindVouchersRuleInfoVoResultVo getActiveRuleInfoById(
			String clientId, String activeId) {
		VouchersRuleInfo vouchersRuleInfo = new VouchersRuleInfo();
		ActiveBaseRule baseRule = new ActiveBaseRule();
		baseRule.setClientId(clientId);
		baseRule.setActiveId(activeId);
		baseRule.setType(ActiveType.vouchers.getCode());
		vouchersRuleInfo.setActiveBaseRule(baseRule);
		FindVouchersRuleInfoVoResultVo vouchersRulevo = new FindVouchersRuleInfoVoResultVo();
		List<Vouchers> vouchersList = this.handler
				.getActiveRuleInfo(vouchersRuleInfo);
		// 把支持的活动也查处装入详情中
		ActiveSustainRelation activeSustainRelation = new ActiveSustainRelation();
		activeSustainRelation.setActiveId(activeId);
		activeSustainRelation.setClientId(clientId);
		List<ActiveSustainRelation> activeSustainRelationList = this.sustainRelationLogic
				.findActiveSustainRelation(activeSustainRelation);
		LogCvt.debug("获取到红包支持的活动 " + activeSustainRelationList.size() + "条。");
		List<VouchersRuleInfo> vouchersRuleInfoList = this
				.assembleVouchersInfosList(vouchersList);
		LogCvt.debug("获取到红包规则 " + vouchersRuleInfoList.size() + "条。");
		if (vouchersRuleInfoList != null && vouchersRuleInfoList.size() > 0) {
			VouchersRuleInfoVo vo = new VouchersRuleInfoVo();
			vouchersRuleInfo = vouchersRuleInfoList.get(0);
			vouchersRuleInfo
					.setActiveSustainRelationList(activeSustainRelationList);
			vouchersRuleInfo.getVouchersDetailRule()
					.setActiveSustainRelationList(activeSustainRelationList);
			vo = (VouchersRuleInfoVo) BeanUtil.copyProperties(
					VouchersRuleInfoVo.class, vouchersRuleInfo);
			vouchersRulevo.vouchersRuleInfo = vo;
		}

		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("共获取 " + vouchersRuleInfoList.size() + "条数据");
		vouchersRulevo.setResultVo(resultVo);
		return vouchersRulevo;
	}

	@Override
	public FindPageVouchersRuleInfoVoResultVo getActiveRuleInfoByPage(
			Page<VouchersRuleInfo> page, VouchersRuleInfo vouchersRuleInfo) {
		LogCvt.debug("-------获取分页数据开始------");
		FindPageVouchersRuleInfoVoResultVo allVouchersRulevo = new FindPageVouchersRuleInfoVoResultVo();
		VouchersRuleInfoPageVoRes ruleInfoPageVoRes = new VouchersRuleInfoPageVoRes();
		page = this.handler.getActiveRuleInfoByPage(page, vouchersRuleInfo);
		List<VouchersRuleInfo> vouchersRuleInfoList = page.getResultsContent();
		ruleInfoPageVoRes.vouchersRuleInfoList = new ArrayList<VouchersRuleInfoVo>();
		for (VouchersRuleInfo po : vouchersRuleInfoList) {
			VouchersRuleInfoVo vo = (VouchersRuleInfoVo) BeanUtil
					.copyProperties(VouchersRuleInfoVo.class, po);
			ruleInfoPageVoRes.vouchersRuleInfoList.add(vo);
		}

		PageVo pVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);
		page.setResultsContent(vouchersRuleInfoList);
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("共获取 " + vouchersRuleInfoList.size() + "条数据");
		allVouchersRulevo.resultVo = resultVo;
		VouchersRuleInfoPageVoRes res = new VouchersRuleInfoPageVoRes();
		res.setPage(pVo);
		allVouchersRulevo.vouchersRuleInfoPageRes = res;
		allVouchersRulevo.vouchersRuleInfoPageRes.vouchersRuleInfoList = ruleInfoPageVoRes.vouchersRuleInfoList;
		return allVouchersRulevo;
	}

	@Override
	public ResultVo updateVouchersRuleInfo(OriginVo originVo,
			VouchersRuleInfo vouchersRuleInfo) {
		ResultVo resultVo = new ResultVo();
		// 检查前端更新信息是否完整
		resultVo = this.baseRuleLogic.verification(resultVo,
				vouchersRuleInfo.getActiveBaseRule());
		if (resultVo.getResultCode().equals(ResultCode.failed.getCode())) {
			return resultVo;
		}
		// 检查标签关系表
		resultVo = tagRelationLogic.verification(resultVo,
				vouchersRuleInfo.getActiveTagRelation());
		if (resultVo.getResultCode().equals(ResultCode.failed.getCode())) {
			return resultVo;
		}
		if (vouchersRuleInfo != null
				&& vouchersRuleInfo.getActiveBaseRule() != null
				&& vouchersRuleInfo.getVouchersDetailRule() != null) {
			List<VouchersInfo> vouchersInfoList = new ArrayList<VouchersInfo>();
			List<ActiveSustainRelation> activeSustainRelationList = new ArrayList<ActiveSustainRelation>();
			List<String> vouchersIds = new ArrayList<String>();
			if (vouchersRuleInfo.getVouchersDetailRule() != null
					&& vouchersRuleInfo.getVouchersDetailRule()
							.getVouchersCouponCodelist() != null
					&& vouchersRuleInfo.getVouchersDetailRule()
							.getVouchersCouponCodelist().size() > 0) {
				for (VouchersCouponCode po : vouchersRuleInfo
						.getVouchersDetailRule().getVouchersCouponCodelist()) {
					vouchersIds.add(po.getVouchersIds());
				}
			}

			if (vouchersRuleInfo.getVouchersDetailRule() != null) {
				activeSustainRelationList = vouchersRuleInfo
						.getVouchersDetailRule().getActiveSustainRelationList();
				vouchersRuleInfo
						.setActiveSustainRelationList(activeSustainRelationList);
				vouchersRuleInfo.setVouchersInfoList(vouchersInfoList);
				// 初始化限购次数
				if(vouchersRuleInfo.getVouchersDetailRule().getPerDay() < 1) {
					vouchersRuleInfo.getVouchersDetailRule().setPerDay(1);
				}
				
				ResultVo resultCode = handler.updateVouchersRuleInfo(originVo,
						vouchersRuleInfo);
				resultVo = resultCode;
				LogCvt.debug("更新红包规则 ：" + resultCode.getResultDesc());
			} else {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("红包规则明细参数VouchersDetailRule不能为空");
				LogCvt.debug("红包规则明细参数VouchersDetailRule不能为空");
			}
		} else {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("红包基础规则参数vouchersRuleInfo不能为空");
			LogCvt.debug("红包基础规则参数vouchersRuleInfo不能为空");
		}

		return resultVo;
	}

	@Override
	public ExportVouchersDetailInfo exportVouchersDetailInfo(String clientId,
			String activeId) {
		ExportVouchersDetailInfo vouchersDetailInfo = new ExportVouchersDetailInfo();
		List<VouchersUseDetails> vouchersUseDetailsList = new ArrayList<VouchersUseDetails>();
		VouchersInfo vouchersInfo = new VouchersInfo();
		vouchersInfo.setActiveId(activeId);
		vouchersInfo.setClientId(clientId);
		try {
			// 每个sheet显示条数
			int everySheetNum = 50000;
			String url = null;
			ActiveDownUtils down = new ActiveDownUtils();
			// 第一步：根据条件查询要导出的所有数据
			ExcelWriter excelWriter = new ExcelWriter(',',"GBK");
			
			vouchersUseDetailsList = this.vouchersInfoHandler
					.findVouchersUseDetails(vouchersInfo);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			String excelFileName = clientId + "_" + ActiveIdCode.HB.getCode()
					+ "_" + formatter.format(this.getSystemDate());
			// 如果查出数据为空，则到出一个空excel
			if (vouchersUseDetailsList.size() == 0) {
				// 第二步：将excel列头放入List<String> 将数据放入List<List<String>>
				// 生成一个sheet
				long startTime1 = System.currentTimeMillis();
				excelWriter.writeCsv(down.downVoucherUseDetaiHeader(),
						down.downVoucherUseDetaiData(null),
						excelFileName);
				long endTime1 = System.currentTimeMillis();
				LogCvt.debug("下载耗时(空excel):" + (endTime1 - startTime1)
						+ "--startTime1:" + startTime1 + "---endTime1:"
						+ endTime1);
				url = excelWriter.getUrl();
				vouchersDetailInfo.setExportUrl(url);
				LogCvt.debug("下载券码明细URL " + url);
			} else {
				// 第三步：调用导出共通方法，返回url。url就是生成的Excel在文件服务器上的相对路径。
				long startTime2 = System.currentTimeMillis();
				excelWriter.writeCsv(down.downVoucherUseDetaiHeader(),
						down.downVoucherUseDetaiData(vouchersUseDetailsList),
						excelFileName);
				url = excelWriter.getUrl();
				long endTime2 = System.currentTimeMillis();
				LogCvt.debug("下载耗时:" + (endTime2 - startTime2)
						+ "--startTime2:" + startTime2 + "---endTime2:"
						+ endTime2);
				vouchersDetailInfo.setExportUrl(url);
				LogCvt.debug("下载券码明细URL " + url);
			}

		} catch (Exception e) {
			LogCvt.error("查询需要下载的券码明细异常" + e.getMessage(), e);
			e.printStackTrace();
		}

		return vouchersDetailInfo;
	}

	/**
	 * @Title: getSystemDate
	 * @Description: 获取系统时间
	 * @author: shenshaocheng 2015年11月26日
	 * @modify: shenshaocheng 2015年11月26日
	 * @return
	 */
	private Date getSystemDate() {
		long currentTime = System.currentTimeMillis();
		Date date = new Date(currentTime);
		return date;
	}

	/**
	 * @Title: createActiveId
	 * @Description: 生成活动ID
	 * @author: shenshaocheng 2015年11月27日
	 * @modify: shenshaocheng 2015年11月27日
	 * @return
	 */
	private String createActiveId(String clientId) {
		String activeId = this.handler.findMaxActiveId(clientId);
		long currentTime = System.currentTimeMillis();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		Date date = new Date(currentTime);
		if (activeId != null && !"".equals(activeId)) {
			int maxCount = Integer.parseInt(activeId.split("-")[activeId
					.split("-").length - 1]) + 1;
			String numberCode = maxCount + "";
			if (maxCount < 10) {
				numberCode = "00" + maxCount;
			} else if (maxCount < 100) {
				numberCode = "0" + maxCount;
			}
			activeId = clientId + "-" + ActiveIdCode.HB.getCode() + "-"
					+ formatter.format(date) + "-" + numberCode;
		} else {
			activeId = clientId + "-" + ActiveIdCode.HB.getCode() + "-"
					+ formatter.format(date) + "-" + "001";
		}

		return activeId;
	}

	/**
	 * @Title: findRepeatVoucherList
	 * @Description: 查询重复新增券码是否已经存在，返回已经重复的券码
	 * @author: shenshaocheng 2015年11月27日
	 * @modify: shenshaocheng 2015年11月27日
	 * @return
	 */
/*	private List<String> findRepeatVoucherList(List<String> vouchersList) {
		List<String> repeatVouchersList = new ArrayList<String>();
		List<VouchersInfo> vouchersInfoList = this.vouchersInfoHandler
				.findRepeatVoucherList(vouchersList);
		for (VouchersInfo vouchersInfo : vouchersInfoList) {
			repeatVouchersList.add(vouchersInfo.getVouchersId());
		}
		return repeatVouchersList;
	}*/

	/**
	 * @Title: assembleVouchersInfo
	 * @Description: 组装券码信息
	 * @author: shenshaocheng 2015年11月27日
	 * @modify: shenshaocheng 2015年11月27日
	 * @param vouchersRuleInfo
	 *            红包规则信息
	 * @param systemDate
	 *            系统时间
	 * @param vouchersInfoList
	 *            红包券码列表
	 */
/*	private void assembleVouchersInfo(VouchersRuleInfo vouchersRuleInfo,
			Date systemDate, List<VouchersInfo> vouchersInfoList) {
		if (vouchersRuleInfo.getVouchersDetailRule() != null) {
			for (VouchersCouponCode vouchersId : vouchersRuleInfo
					.getVouchersDetailRule().getVouchersCouponCodelist()) {
				VouchersInfo vouchersInfo = new VouchersInfo();
				vouchersInfo.setActiveId(vouchersRuleInfo
						.getVouchersDetailRule().getActiveId());
				vouchersInfo.setClientId(vouchersRuleInfo
						.getVouchersDetailRule().getClientId());
				vouchersInfo.setCreateTime(systemDate);
				vouchersInfo.setMemberCode((long) 0);
				vouchersInfo.setStatus("0");
				vouchersInfo.setUpdateTime(systemDate);
				vouchersInfo.setUserMemberCode((long) 0);
				vouchersInfo.setSendActiveId(null);
				vouchersInfo.setSendTime(null);
				vouchersInfo.setUseTime(null);
				vouchersInfo.setVouchersId(vouchersId.getVouchersIds());
				vouchersInfo.setVouchersMoney(vouchersRuleInfo
						.getVouchersDetailRule().getMinMoney());
				vouchersInfo.setVouchersResMoney(vouchersRuleInfo
						.getVouchersDetailRule().getMinMoney());
				vouchersInfo.setVouchersUseMoney((double) 0);
				vouchersInfoList.add(vouchersInfo);
			}
		}
	}*/

	/**
	 * @Title: assembleVouchersInfosList
	 * @Description: 组装返回Po
	 * @author: shenshaocheng 2015年12月4日
	 * @modify: shenshaocheng 2015年12月4日
	 * @return
	 */
	private List<VouchersRuleInfo> assembleVouchersInfosList(
			List<Vouchers> vouchersRuleInfoList) {
		List<VouchersRuleInfo> vouchersInfosList = new ArrayList<VouchersRuleInfo>();
		for (Vouchers vouchers : vouchersRuleInfoList) {
			ActiveBaseRule baseRule = new ActiveBaseRule();
			ActiveTagRelation tagRelation = new ActiveTagRelation();
			VouchersDetailRule detailRule = new VouchersDetailRule();
			VouchersRuleInfo vouchersRuleInfo = new VouchersRuleInfo();
			baseRule.setActiveId(vouchers.getActiveId());
			baseRule.setActiveName(vouchers.getActiveName());
			baseRule.setActiveLogo(vouchers.getActiveLogo());
			baseRule.setBankRate(vouchers.getBankRate());
			baseRule.setClientId(vouchers.getClientId());
			baseRule.setCreateTime(vouchers.getCreateTime());
			baseRule.setDescription(vouchers.getDescription());
			baseRule.setExpireEndTime(vouchers.getExpireEndTime());
			baseRule.setExpireStartTime(vouchers.getExpireStartTime());
			baseRule.setFftRate(vouchers.getFftRate());
			baseRule.setLimitType(vouchers.getLimitType());
			baseRule.setMerchantRate(vouchers.getMerchantRate());
			baseRule.setOperator(vouchers.getOperator());
			baseRule.setSettleType(vouchers.getSettleType());
			baseRule.setStatus(vouchers.getStatus());
			baseRule.setType(vouchers.getType());
			baseRule.setUpdateTime(vouchers.getUpdateTime());

			tagRelation.setActiveId(vouchers.getActiveId());
			tagRelation.setClientId(vouchers.getClientId());
			tagRelation.setCreateTime(vouchers.getCreateTime());
			tagRelation.setItemId(vouchers.getItemId());
			tagRelation.setItemType(vouchers.getItemType());
			tagRelation.setUpdateTime(vouchers.getUpdateTime());

			detailRule.setActiveId(vouchers.getActiveId());
			detailRule.setClientId(vouchers.getClientId());
			detailRule.setIsFtof(vouchers.getIsFtof());
			detailRule.setIsOtherActive(vouchers.getIsOtherActive());
			detailRule.setIsPerDay(vouchers.getIsPerDay());
			detailRule.setIsPrePay(vouchers.getIsPrePay());
			detailRule.setIsRepeat(vouchers.getIsRepeat());
			detailRule.setIsTotalDay(vouchers.getIsTotalDay());
			detailRule.setMaxMoney(vouchers.getMaxMoney());
			detailRule.setMinMoney(vouchers.getMinMoney());
			detailRule.setOrderMinMoney(vouchers.getOrderMinMoney());
			detailRule.setPayMethod(vouchers.getPayMethod());
			detailRule.setPerCount(vouchers.getPerCount());
			detailRule.setPerDay(vouchers.getPerDay());
			detailRule.setTotalCount(vouchers.getTotalCount());
			detailRule.setTotalDay(vouchers.getTotalDay());
			detailRule.setTotalMoney(vouchers.getTotalMoney());
			detailRule.setIsOnlyNewUsers(vouchers.getIsOnlyNewUsers());

			vouchersRuleInfo.setActiveBaseRule(baseRule);
			vouchersRuleInfo.setActiveTagRelation(tagRelation);
			vouchersRuleInfo.setVouchersDetailRule(detailRule);

			vouchersInfosList.add(vouchersRuleInfo);
		}

		return vouchersInfosList;
	}
}
