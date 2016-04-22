package com.froad;

/**
 * @Title: TestVoucherRun.java
 * @Package com.froad
 * @Description: TODO
 * Copyright:2015 F-Road All Rights Reserved   
 * Company:f-road.com.cn
 * 
 * @creater froad-Joker 2015年11月26日
 * @version V1.0
**/



import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.thrift.service.VouchersRunService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.CheckVouchersReqVo;
import com.froad.thrift.vo.active.CheckVouchersResVo;
import com.froad.thrift.vo.active.FindVouchersOfSubmitReqVo;
import com.froad.thrift.vo.active.FindVouchersResVo;
import com.froad.thrift.vo.active.VouchersToRedPackReqVo;

 /**
 * @ClassName: TestVoucherRun
 * @Description: TODO
 * @author froad-Joker 2015年11月26日
 * @modify froad-Joker 2015年11月26日
 */

public class TestVoucherRun {
	public static void main(String[] args) throws TException {
		TSocket transport = new TSocket("localhost", 16501);
		TBinaryProtocol protocol = new TBinaryProtocol(transport);
		
		TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"VouchersRunService");
		VouchersRunService.Client service = new VouchersRunService.Client(mp);
		
		transport.open();
		
		//=====================available list ================

//		FindVouchersOfSubmitReqVo findVouchersOfSubmitReqVo = new FindVouchersOfSubmitReqVo();
//		findVouchersOfSubmitReqVo.setClientId("chongqing");
//		findVouchersOfSubmitReqVo.setIsAvailable(true);
//		findVouchersOfSubmitReqVo.setMemberCode(123456);
//		findVouchersOfSubmitReqVo.setOrderMoney(200);
//		List<String> sustainActiveIds = new ArrayList<String>();
//		sustainActiveIds.add("chongqing-MJ-2015-001");
//		//sustainActiveIds.add("chongqing-MJ-2015-002");
//		findVouchersOfSubmitReqVo.setSustainActiveIds(sustainActiveIds);
//		PageVo pageVo = new PageVo();
//		pageVo.setPageSize(2);
//		pageVo.setPageNumber(1);
//		findVouchersOfSubmitReqVo.setPage(pageVo);
//		
//		FindVouchersResVo result = service.findVouchersOfSubmit(findVouchersOfSubmitReqVo);
//		System.out.println(result.getVouchersInfoList().size());
		
		//==========================checkVouchers ================================================
//		CheckVouchersReqVo checkVouchersReq = new CheckVouchersReqVo();
//		List<String> sustainActiveIds = new ArrayList<String>();
//		sustainActiveIds.add("chongqing-MJ-2015-001");
//		checkVouchersReq.setClientId("chongqing");
//		checkVouchersReq.setMemberCode(123456);
//		checkVouchersReq.setVouchersId("82JDI2JDSDK2F83J");
//		checkVouchersReq.setOrderMoney(200);
//		checkVouchersReq.setSustainActiveIds(sustainActiveIds);
//		CheckVouchersResVo checkVouchersResVo = service.checkVouchers(checkVouchersReq);
//		System.out.println("money : " + checkVouchersResVo.getVouchersMonry());
		
		//==========================vouchersToRedPack ================================================
		
		VouchersToRedPackReqVo vouchersToRedPackReqVo = new VouchersToRedPackReqVo();
		vouchersToRedPackReqVo.setClientId("chongqing");
		vouchersToRedPackReqVo.setMemberCode(12345601L);
		vouchersToRedPackReqVo.setVouchersId("731635243471");
		ResultVo result = service.vouchersToRedPack(vouchersToRedPackReqVo);
		System.out.println(result);
		
	}
}
