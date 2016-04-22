package com.froad.thrift;

import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSON;
import com.froad.enums.ResultCode;
import com.froad.enums.TerminalType;
import com.froad.thrift.service.AdvertisingService;
import com.froad.thrift.vo.AdvertisingVo;
import com.froad.thrift.vo.FindAllAdvertisingParamVo;
import com.froad.thrift.vo.FindAllAdvertisingResultVo;

public class AdvertisingClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			TSocket transport = new TSocket("localhost", 15701);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);

			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"AdvertisingService");
			AdvertisingService.Client service = new AdvertisingService.Client(mp);
			
			transport.open();
			
			// 新增
			pageOptFind(service);
			
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	private static void pageOptFind(AdvertisingService.Client service) throws Exception{
		FindAllAdvertisingParamVo fvo = new FindAllAdvertisingParamVo();
	//	fvo.setTerminalType(TerminalType.ANDRIOD.getCode());
		fvo.setPositionPage("SPLM");
		fvo.setClientId("chongqing");
		fvo.setParamOneValue("2092");
		fvo.setParamTwoValue("100000055");
		FindAllAdvertisingResultVo rvo = service.pageOptFindAdvertisings(fvo);
		if( ResultCode.success.getCode().equals(rvo.getResultVo().getResultCode()) ){
			List<AdvertisingVo> al = rvo.getAdvertisingVoList();
			if( al != null && al.size() > 0 ){
				for( AdvertisingVo vo : al ){
					System.out.println(JSON.toJSONString(vo));
				}
			}else{
				System.out.println("查询没数据");
			}
		}else{
			System.out.println("查询出异常");
		}
	}
}
