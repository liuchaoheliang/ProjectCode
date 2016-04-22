package com.froad.thrift;

import java.util.Calendar;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSON;
import com.froad.po.Org;
import com.froad.thrift.service.MerchantAccountService;
import com.froad.thrift.service.OrgService;
import com.froad.thrift.vo.MerchantAccountVo;
import com.froad.thrift.vo.OrgVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PlatType;

public class OrgServerTest {
	public static void main(String[] args) {
		OrgServerTest orgServer = new OrgServerTest();
		orgServer.getOrgInfoByOrgNameTest();
	}
	
	
	/**
	 * 机构信息服务根据客户端id及机构名称查询返回机构信息列表测试.
	 */
	public void getOrgInfoByOrgNameTest(){
		try {
			String host= "";
			int port = 0;
			host = "127.0.0.1";
			port = 15101;
			TSocket transport = new TSocket(host, port);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"OrgService");
			OrgService.Client service = new OrgService.Client(mp);

			transport.open();

			OrgVo   vo = new OrgVo();
			vo.setClientId("chongqing");
			vo.setOrgName("行");
			List<OrgVo> orgList = (List<OrgVo>) service.getOrgInfoByOrgName(vo,10,"000000");
			System.out.println("size="+orgList.size());
			System.out.print(JSON.toJSONString(orgList));
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}