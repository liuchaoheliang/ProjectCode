package com.froad.thrift;

import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSONObject;
import com.froad.logback.LogCvt;
import com.froad.thrift.service.AgreementService;
import com.froad.thrift.vo.AgreementPageVoRes;
import com.froad.thrift.vo.AgreementVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;

public class AgreementClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			OriginVo originVo = new OriginVo();
			TSocket transport = new TSocket("localhost", 8899);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			TMultiplexedProtocol mp2 = new TMultiplexedProtocol(protocol,"AgreementService");
			AgreementService.Client service2 = new AgreementService.Client(mp2);
			transport.open();

			
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
