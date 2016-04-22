package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.handler.VouchersInfoHandler;
import com.froad.handler.impl.VouchersInfoHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.VouchersJobLogic;
import com.froad.po.FindVouchersOfCenterReq;
import com.froad.po.FindVouchersRes;
import com.froad.po.VouchersInfo;

public class VouchersJobLogicImpl implements VouchersJobLogic {

	// 券信息操作
	private VouchersInfoHandler vouchersInfoHandler = new VouchersInfoHandlerImpl();
	
	// 1未过期2已过期3已使用
	private static final String STATUS_UN_EXPIRED = "1";
	private static final String STATUS_EXPIRED = "2";
	private static final String STATUS_USE = "3";
	
	/**
	 * 查询红包详情 - 会员中心
	 * */
	@Override
	public FindVouchersRes findVouchersOfCenter(FindVouchersOfCenterReq findVouchersOfCenterReq) {
		
		FindVouchersRes findVouchersRes = new FindVouchersRes();
		
		String reqId = findVouchersOfCenterReq.getReqId();
		String clientId = findVouchersOfCenterReq.getClientId();
		Long memberCode = findVouchersOfCenterReq.getMemberCode();
		String status = findVouchersOfCenterReq.getStatus();
		Page page = findVouchersOfCenterReq.getPage();
		
		findVouchersRes.setReqId(reqId);
		findVouchersRes.setClientId(clientId);
		findVouchersRes.setMemberCode(memberCode);
		findVouchersRes.setPage(page);
		
		try{
			
			List<VouchersInfo> vouchersInfoList = new ArrayList<VouchersInfo>();;
			
			// 未过期 - 0初始化 - 关联的活动规则未过期
			if( STATUS_UN_EXPIRED.equals(status) ){
				
				vouchersInfoList = vouchersInfoHandler.findVouchersInfoUnExpiredByPage(page, memberCode);
				// 处理 预警天数
				processDaysWarn(vouchersInfoList);
				
			// 过期 - 0初始化 - 关联的活动规则已过期
			}else if( STATUS_EXPIRED.equals(status) ){
				
				vouchersInfoList = vouchersInfoHandler.findVouchersInfoExpiredByPage(page, memberCode);
				
			// 已使用 - 1支付中/2支付成功
			}else if( STATUS_USE.equals(status) ){
				
				vouchersInfoList = vouchersInfoHandler.findVouchersInfoUseByPage(page, memberCode);
			}
			
			for(VouchersInfo info : vouchersInfoList) {
				info.setVouchersMoney(info.getVouchersMoney()/1000);
				info.setVouchersResMoney(info.getVouchersResMoney()/1000);
				info.setVouchersUseMoney(info.getVouchersUseMoney()/1000);
			}
			
			findVouchersRes.setVouchersInfoList(vouchersInfoList);
			
		}catch(Exception e){
			LogCvt.error("查询 "+memberCode+" 所属 "+status+" 红包 异常", e);
			findVouchersRes.setVouchersInfoList(null);
		}
		
		return findVouchersRes;
	}

	// 处理 预警天数
	private static void processDaysWarn(List<VouchersInfo> vouchersInfoList){
		if( vouchersInfoList != null && vouchersInfoList.size() > 0 ){
			Long now = new Date().getTime();
			for( VouchersInfo vouchersInfo : vouchersInfoList ){
				Long gap = vouchersInfo.getExpireEndTime().getTime() - now;
				long t = gap / ( 3600 * 24 * 1000 );
				StringBuffer sb = new StringBuffer().append(t).append("天");
				if( gap % ( 3600 * 24 * 1000 ) > 0 ){
					gap = gap - ( 3600 * 24 * 1000 ) * t;
					long s = gap / ( 3600 * 1000 );
					sb.append(s).append("时");
					if( gap % ( 3600 * 1000 ) > 0 ){
						gap = gap - ( 3600 * 1000 ) * s;
						long f = gap / ( 60 * 1000 );
						sb.append(f).append("分");
					}
				}
				vouchersInfo.setDaysWarn(sb.toString());
			}
		}
	}
	
}
