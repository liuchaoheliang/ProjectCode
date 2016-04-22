package com.froad.thrift;

import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSONObject;
import com.froad.thrift.service.CashService;
import com.froad.thrift.vo.CashPageVoRes;
import com.froad.thrift.vo.CashVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;

public class CashClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			OriginVo originVo = new OriginVo();
			TSocket transport = new TSocket("localhost", 8899);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);

			TMultiplexedProtocol mp4 = new TMultiplexedProtocol(protocol,"CashService");
			CashService.Client service4 = new CashService.Client(mp4);
			transport.open();

			
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
