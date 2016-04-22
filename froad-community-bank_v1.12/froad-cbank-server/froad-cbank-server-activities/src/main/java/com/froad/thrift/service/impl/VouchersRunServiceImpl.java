package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.alibaba.fastjson.JSON;
import com.froad.db.mysql.bean.Page;
import com.froad.logback.LogCvt;
import com.froad.logic.VouchersJobLogic;
import com.froad.logic.VouchersRunLogic;
import com.froad.logic.impl.VouchersJobLogicImpl;
import com.froad.logic.impl.VouchersRunLogicImpl;
import com.froad.po.CheckVouchersReq;
import com.froad.po.CheckVouchersRes;
import com.froad.po.FindVouchersOfCenterReq;
import com.froad.po.FindVouchersOfSubmitReq;
import com.froad.po.FindVouchersRes;
import com.froad.po.PayResultNoticeReq;
import com.froad.po.VouchersInfo;
import com.froad.po.VouchersToRedPackReq;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.VouchersRunService.Iface;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.CheckVouchersReqVo;
import com.froad.thrift.vo.active.CheckVouchersResVo;
import com.froad.thrift.vo.active.CloseVouchersOrderReqVo;
import com.froad.thrift.vo.active.CreateVouchersOrderFailureGoBackReqVo;
import com.froad.thrift.vo.active.FindVouchersOfCenterReqVo;
import com.froad.thrift.vo.active.FindVouchersOfSubmitReqVo;
import com.froad.thrift.vo.active.FindVouchersResVo;
import com.froad.thrift.vo.active.PayResultNoticeReqVo;
import com.froad.thrift.vo.active.VouchersInfoVo;
import com.froad.thrift.vo.active.VouchersToRedPackReqVo;
import com.froad.util.BeanUtil;

public class VouchersRunServiceImpl extends BizMonitorBaseService implements Iface {
	
	
	public VouchersRunServiceImpl() {}

	public VouchersRunServiceImpl(String name, String version) {
		super(name, version);
	}
	
	private VouchersJobLogic vouchersJobLogic = new VouchersJobLogicImpl();
	
	private VouchersRunLogic vouchersRunLogic = new VouchersRunLogicImpl();
	
	@Override
	public FindVouchersResVo findVouchersOfSubmit(
			FindVouchersOfSubmitReqVo paramFindVouchersOfSubmitReqVo)
			throws TException {
		LogCvt.info("查询[findVouchersOfSubmit]方法开始");
		FindVouchersResVo resVo = new FindVouchersResVo();
		Page<VouchersInfo> page = (Page<VouchersInfo>)BeanUtil.copyProperties(Page.class, paramFindVouchersOfSubmitReqVo.getPage());
				
		// 入参转换
		FindVouchersOfSubmitReq findVouchersOfSubmitReq = (FindVouchersOfSubmitReq)BeanUtil.copyProperties(FindVouchersOfSubmitReq.class, paramFindVouchersOfSubmitReqVo);
		
		page = vouchersRunLogic.findVouchersOfSubmit(page, findVouchersOfSubmitReq);
		
		List<VouchersInfo> infoList = page.getResultsContent();
		
		List<VouchersInfoVo> vouchersInfoList = new ArrayList<VouchersInfoVo>();
		for(VouchersInfo info : infoList)
		{
			VouchersInfoVo vo = (VouchersInfoVo)BeanUtil.copyProperties(VouchersInfoVo.class, info);
			vouchersInfoList.add(vo);
		}
		
		PageVo pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		resVo.setPage(pageVo);
		resVo.setVouchersInfoList(vouchersInfoList);
		resVo.setClientId(paramFindVouchersOfSubmitReqVo.getClientId());
		resVo.setMemberCode(paramFindVouchersOfSubmitReqVo.getMemberCode());
		LogCvt.info("查询[findVouchersOfSubmit]方法结束");
		return resVo;
	}

	/**
	 * 查询红包详情 - 会员中心
	 * */
	@Override
	public FindVouchersResVo findVouchersOfCenter(FindVouchersOfCenterReqVo findVouchersOfCenterReqVo) throws TException {

		LogCvt.info("查询红包详情 - 会员中心 findVouchersOfCenter 参数:");
		LogCvt.info(JSON.toJSONString(findVouchersOfCenterReqVo));
		
		FindVouchersOfCenterReq findVouchersOfCenterReq = (FindVouchersOfCenterReq)BeanUtil.copyProperties(FindVouchersOfCenterReq.class, findVouchersOfCenterReqVo);
		
		FindVouchersRes findVouchersRes = vouchersJobLogic.findVouchersOfCenter(findVouchersOfCenterReq);
		
		FindVouchersResVo findVouchersResVo = (FindVouchersResVo)BeanUtil.copyProperties(FindVouchersResVo.class, findVouchersRes);
		
		return findVouchersResVo;
	}

	@Override
	public CheckVouchersResVo checkVouchers(
			CheckVouchersReqVo paramCheckVouchersReqVo) throws TException {
		LogCvt.info("验证券码[checkVouchers]方法开始");
		
		// 入参转换
		CheckVouchersReq checkVouchersReq = (CheckVouchersReq)BeanUtil.copyProperties(CheckVouchersReq.class, paramCheckVouchersReqVo);
		 
		CheckVouchersRes checkVouchersRes = vouchersRunLogic.checkVoucherVaild(checkVouchersReq);
		checkVouchersRes.setClientId(checkVouchersReq.getClientId());
		checkVouchersRes.setMemberCode(checkVouchersReq.getMemberCode());
		CheckVouchersResVo resVo = (CheckVouchersResVo)BeanUtil.copyProperties(CheckVouchersResVo.class, checkVouchersRes);
		LogCvt.info("验证券码[checkVouchers]方法结束");
		return resVo;
	}

	@Override
	public ResultVo createVouchersOrderFailureGoBack(
			CreateVouchersOrderFailureGoBackReqVo paramCreateVouchersOrderFailureGoBackReqVo)
			throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultVo payResultNotice(
			PayResultNoticeReqVo paramPayResultNoticeReqVo) throws TException {
		
		LogCvt.info("券码通知[payResultNotice]方法开始");
		// 入参转换
		PayResultNoticeReq payResultNoticeReq = (PayResultNoticeReq)BeanUtil.copyProperties(PayResultNoticeReq.class, paramPayResultNoticeReqVo);
		
		ResultVo resultVo = vouchersRunLogic.payResultNotice(payResultNoticeReq);
		
		LogCvt.info("券码通知[payResultNotice]方法结束");
		return resultVo;
	}

	@Override
	public ResultVo closeVouchersOrder(
			CloseVouchersOrderReqVo paramCloseVouchersOrderReqVo)
			throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultVo vouchersToRedPack(VouchersToRedPackReqVo vouchersToRedPackReqVo)
			throws TException {
		
		LogCvt.info("券码转换[vouchersToRedPack]方法开始");
		// 入参转换
		VouchersToRedPackReq vouchersToRedPackReq = (VouchersToRedPackReq)BeanUtil.copyProperties(VouchersToRedPackReq.class, vouchersToRedPackReqVo);
		
		ResultVo resultVo = vouchersRunLogic.vouchersToRedPack(vouchersToRedPackReq);
		
		LogCvt.info("券码转换[vouchersToRedPack]方法结束");
		return resultVo;
	}

}
