package com.froad.thrift;

import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSON;
import com.froad.thrift.service.AreaService;
import com.froad.thrift.vo.AreaPageVoRes;
import com.froad.thrift.vo.AreaVo;
import com.froad.thrift.vo.PageVo;


public class AreaClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			TSocket transport = null;
			transport = new TSocket("localhost", 15701);
//			transport = new TSocket("10.43.2.3", 15701);
//			transport = new TSocket("10.43.1.9", 15701);
			
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			transport.open();
			
			TMultiplexedProtocol mp6= new TMultiplexedProtocol(protocol,"AreaService");
			AreaService.Client service = new AreaService.Client(mp6);
			
			select1(service);
			
			
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void select1(AreaService.Client service) throws Exception{
		
		List<AreaVo> al = service.findProvinceAreaByClientId("chongqing");
		if( al != null && al.size() > 0 ){
			for( AreaVo vo : al ){
				System.out.println(JSON.toJSONString(vo));
			}
		}else{
			System.out.println("没数据");
		}
	}

}
