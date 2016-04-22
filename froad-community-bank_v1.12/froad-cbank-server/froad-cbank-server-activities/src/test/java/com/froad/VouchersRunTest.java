package com.froad;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSON;
import com.froad.thrift.service.ActiveRunService;
import com.froad.thrift.service.VouchersRunService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.active.CheckOrderReqVo;
import com.froad.thrift.vo.active.CheckOrderResVo;
import com.froad.thrift.vo.active.CreateMarketOrderReqVo;
import com.froad.thrift.vo.active.CreateResultVo;
import com.froad.thrift.vo.active.FindVouchersOfCenterReqVo;
import com.froad.thrift.vo.active.FindVouchersResVo;
import com.froad.thrift.vo.active.MarketSubOrderProductVo;
import com.froad.thrift.vo.active.MarketSubOrderVo;
import com.froad.thrift.vo.active.OrderProductVo;
import com.froad.thrift.vo.active.ShoppingCartReqProductVo;
import com.froad.thrift.vo.active.ShoppingCartReqVo;
import com.froad.thrift.vo.active.ShoppingCartResVo;

public class VouchersRunTest {

	public static void main(String[] args){
		try{
			TSocket transport = null;
//			transport = new TSocket("10.24.248.187", 16501);
//			transport = new TSocket("127.0.0.1", 16501);
//			transport = new TSocket("10.24.248.188", 16501);
//			transport = new TSocket("10.43.1.133", 16501);
//			transport = new TSocket("10.43.1.131", 9102);
//			transport = new TSocket("10.43.1.123", 16501);
			transport = new TSocket("10.24.240.251", 16501);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"VouchersRunService");
			VouchersRunService.Client service = new VouchersRunService.Client(mp);
			
			transport.open();
			
//			ResultVo resultVo = service.updateMarketOrder(null);
//			System.out.println(JSON.toJSONString(resultVo));
			
			// 获取红包 - 会员中心
			findVouchersOfCenter(service);
			
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void findVouchersOfCenter(VouchersRunService.Client service) throws Exception{
		
		FindVouchersOfCenterReqVo findVouchersOfCenterReqVo = new FindVouchersOfCenterReqVo();
		findVouchersOfCenterReqVo.setReqId("01DF8723005");
		findVouchersOfCenterReqVo.setClientId("chongqing");
		findVouchersOfCenterReqVo.setMemberCode(50001965303l);
		// 1未过期2已过期3已使用
		findVouchersOfCenterReqVo.setStatus("1");
		PageVo page = new PageVo();
		page.setPageSize(2);
		page.setPageNumber(2);
		findVouchersOfCenterReqVo.setPage(page);
		FindVouchersResVo findVouchersResVo = service.findVouchersOfCenter(findVouchersOfCenterReqVo);
		System.out.println(JSON.toJSONString(findVouchersResVo.getVouchersInfoList()));
	}
}
