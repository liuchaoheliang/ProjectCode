package com.froad.thrift;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.thrift.service.BossMerchantService;
import com.froad.thrift.service.BossProductService;
import com.froad.thrift.vo.BossAuditProcessVo;
import com.froad.thrift.vo.BossProductFilterVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.PlatType;


public class BossProductServiceTest {
	
	public static void main(String[] args) {
		try {
			
//			TSocket transport = new TSocket("10.43.1.9", 15201);
			TSocket transport = new TSocket("127.0.0.1", 16001);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, BossProductService.class.getSimpleName());
			BossProductService.Client service = new BossProductService.Client(mp);

			transport.open();
//			BossProductFilterVo productFilterVo=new BossProductFilterVo();
//			productFilterVo.setAuditStatus("1");
//			PageVo pageVo=new PageVo();
//			pageVo.setPageNumber(1);
//			pageVo.setPageSize(5);
//			System.out.println(service.findAuditProductsByPage(productFilterVo, pageVo));
	

	//		System.out.println(service.getVipProducts("anhui"));
			
			OriginVo originVo=new  OriginVo();
			PlatType platType = PlatType.findByValue(1);
			originVo.setPlatType(platType);
			BossAuditProcessVo processVo=new BossAuditProcessVo();
			processVo.setAuditRemark("111111111");
			processVo.setAuditState("1");
			processVo.setUserId("11111111");
			processVo.setRoleId("11111111");
			processVo.setAuditTime(11111111111111111l);
			processVo.setProductId("09B72EA40000");
			System.out.println(service.auditProduct(originVo, processVo));
			
			
			System.out.println(service.getBossProductDetail("0979AFE28000"));
			
			transport.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
