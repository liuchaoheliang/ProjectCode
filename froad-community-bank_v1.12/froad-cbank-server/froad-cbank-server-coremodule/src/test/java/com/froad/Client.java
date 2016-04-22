package com.froad;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.enums.LatitudeTupeEnum;
import com.froad.thrift.service.ReportService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.coremodule.ReportReqVo;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			TSocket transport = new TSocket("localhost", 16601);
			// TSocket transport = new TSocket("10.43.2.238",15101);

			TBinaryProtocol protocol = new TBinaryProtocol(transport);

			TMultiplexedProtocol mp1 = new TMultiplexedProtocol(protocol,
					"ReportService");
			ReportService.Client service1 = new ReportService.Client(mp1);

			transport.open();

			PageVo pageVo = new PageVo();
			pageVo.setPageSize(10000);
			pageVo.setPageNumber(1);
			ReportReqVo req = new ReportReqVo();
			req.setClientId("chongqing");
			req.setOrgCode("000000");
			req.setOrgLevel(1);

			req.setLatitudeTupe(LatitudeTupeEnum.MERCHANT.getDescription());
			req.setCountType("day");

			// req.setCheckedMemberComulationCount(true);
			 req.setCheckedMerchant(true);
			// req.setCheckedMerchantCancelContract(true);
			// req.setCheckedMerchantCategory(true);
			// req.setCheckedMerchantSum(true);
			// req.setCheckedOutletCount(true);
			// req.setCheckedOutletCumulation(true);
//			req.setCheckedMerchantCategory(true);
//			req.setCheckedProductSum(true);
//			req.setCheckedProductDownSum(true);
//			req.setCheckedProductCumulation(true);
//			req.setCheckedProductDownComulation(true);
			// req.setCheckedAmountCumulationTurnover(true);
			// req.setCheckedAmountCumulation(true);
			// req.setCheckedAmount(true);
			// req.setCheckedOrderCount(true);
//			ReportRespPageVo page = service1.getRespPage(pageVo, req);
//			List<ReportRespVo> list = page.getReportResp();
//			for (ReportRespVo reportRespVo : list) {
//				System.out.println(reportRespVo);
//			}
			// System.out.println(page.getReportRespList().size());
			// ExportResultRes res = service1.getReportByCondition(req);
			// System.out.println(res.getUrl());
			// req.setCheckedAmountCumulation(true);
			 req.setCheckedAmount(true);
			 req.setCheckedOrderCount(true);
			 req.setCheckedAmountCumulation(true);
			// ReportRespPageVo page = service1.getRespPage(pageVo, req);
			// System.out.println(page.getReportRespList().size());
			// System.out.println(res.getUrl());
			// req.setCheckedAmountCumulation(true);
			// req.setCheckedAmount(true);
			// req.setCheckedOrderCount(true);
			// ReportRespPageVo page = service1.getRespPage(pageVo, req);
			// System.out.println(page.getReportResp().size());
			 ExportResultRes res=service1.getReportByCondition(req);
			 System.out.println(res.getUrl());
//			DefineTaskReqVo  defineTaskReqVo = new DefineTaskReqVo();
// 			defineTaskReqVo.setClientId("chongqing");
// 			//defineTaskReqVo.setLoginId(1);
// 			DefineTaskRespPageVo res1=service1.getDefineTaskPageVo(pageVo, defineTaskReqVo);
//			System.out.println(res1.getDefineTaskRespVo().size());
			
			transport.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
