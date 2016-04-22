package com.froad.thrift.service.impl;

import org.apache.thrift.TException;

import com.froad.logback.LogCvt;
import com.froad.support.PointSettlementSupport;
import com.froad.support.impl.PointSettlementSupportImpl;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.PointSettlementService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.pointsettlement.PointSettlementMerchantResVo;
import com.froad.thrift.vo.pointsettlement.PointSettlementReqVo;
import com.froad.thrift.vo.pointsettlement.PointSettlementResVo;
import com.froad.util.JSonUtil;
public class PointSettlementServiceImpl  extends BizMonitorBaseService  implements PointSettlementService.Iface {

	PointSettlementSupport support = new PointSettlementSupportImpl();
	
	public PointSettlementServiceImpl(String name, String version) {
		super(name, version);
	}
	
	@Override
	public PointSettlementResVo queryPointCount(PointSettlementReqVo req) throws TException {
		LogCvt.info("积分结算汇总统计, 请求参数:" + JSonUtil.toJSonString(req));
		PointSettlementResVo responseVo = support.queryPointCount(req);
		LogCvt.info("积分结算汇总统计, 响应结果:" + JSonUtil.toJSonString(responseVo));
		return responseVo;
	}

	@Override
	public PointSettlementResVo queryPointDetail(PointSettlementReqVo req) throws TException {
		LogCvt.info("积分结算明细查询, 请求参数:" + JSonUtil.toJSonString(req));
		PointSettlementResVo responseVo = support.queryPointDetail(req);
		LogCvt.info("积分结算明细查询, 响应结果:" + JSonUtil.toJSonString(responseVo));
		return responseVo;
	}

	@Override
	public PointSettlementMerchantResVo queryMerchantPointCount(PointSettlementReqVo req) throws TException {
		LogCvt.info("积分结算商户汇总统计, 请求参数:" + JSonUtil.toJSonString(req));
		PointSettlementMerchantResVo responseVo = support.queryMerchantPointCount(req);
		LogCvt.info("积分结算商户汇总统计, 响应结果:" + JSonUtil.toJSonString(responseVo));
		return responseVo;
	}

	@Override
	public ExportResultRes exportPointSettlement(PointSettlementReqVo req) throws TException {
		LogCvt.info("积分结算报表导出, 请求参数:" + JSonUtil.toJSonString(req));
		ExportResultRes responseVo = support.exportPointSettlement(req);
		LogCvt.info("积分结算报表导出, 响应结果:" + JSonUtil.toJSonString(responseVo));
		return responseVo;
	}
}
