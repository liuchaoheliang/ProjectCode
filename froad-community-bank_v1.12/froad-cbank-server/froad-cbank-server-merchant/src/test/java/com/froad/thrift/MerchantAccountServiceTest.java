package com.froad.thrift;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSON;
import com.froad.thrift.service.MerchantAccountService;
import com.froad.thrift.service.MerchantOutletPhotoService;
import com.froad.thrift.vo.MerchantAccountVo;
import com.froad.thrift.vo.MerchantOutletPhotoVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PlatType;

public class MerchantAccountServiceTest {



	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String host= "";
			int port = 0;
//			host= "10.43.2.3";
//			port = 15201;
			host = "127.0.0.1";
			port = 15201;
//			host = "10.43.1.9";
//			port = 15201;
			TSocket transport = new TSocket(host, port);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"MerchantAccountService");
			MerchantAccountService.Client service = new MerchantAccountService.Client(mp);

			transport.open();

			MerchantAccountVo   vo = new MerchantAccountVo();
			vo.setId(6);
			vo.setClientId("anhui");
			vo.setCreateTime(Calendar.getInstance().getTimeInMillis());
			vo.setMerchantId("0154F9410013");
			vo.setOutletId("0");
			vo.setAcctName("测试账号");
			vo.setAcctNumber("62269009323532220");
			vo.setAcctType("1");
			vo.setOpeningBank("340000");
			vo.setIsDelete(false);
			
			OriginVo originVo = new OriginVo();
			originVo.setPlatType(PlatType.bank);
			originVo.setOperatorId(100000000);
			originVo.setOperatorIp("192.168.19.105");
			originVo.setDescription("");
			
//			System.out.println(service.getMerchantAccount(vo).get(0).getAcctName());
//			service.updateMerchantAccount(vo);
//			service.deleteMerchantAccount(vo);
//			service.addMerchantAccount(vo);
//			service.updateMerchantAccount(originVo, vo);
//			service.deleteMerchantAccount(originVo, vo);
//			System.out.println(service.addMerchantAccount(originVo, vo));

			
			vo = new MerchantAccountVo();
			vo.setId(100000202);
			vo.setMerchantId("04485FC78000");
			vo.setAcctName("罗帆5");
			vo.setAcctNumber("666666666666666");
			System.out.println(service.updateMerchantAccount(originVo, vo));
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}

