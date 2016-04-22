package com.froad.thrift;

import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.froad.thrift.service.SmsContentService;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.SmsContentPageVoRes;
import com.froad.thrift.vo.SmsContentVo;
import com.froad.thrift.vo.PageVo;

public class SmsContentClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			OriginVo originVo = new OriginVo();
//			TSocket transport = new TSocket("localhost", 15701);
//			TSocket transport = new TSocket("10.43.2.3", 15701);
			TSocket transport = new TSocket("10.43.1.9", 15701);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);

			TMultiplexedProtocol mp4 = new TMultiplexedProtocol(protocol,"SmsContentService");
			SmsContentService.Client service4 = new SmsContentService.Client(mp4);
			transport.open();

//			SmsContentVo vo = service4.getSmsContentByClientIdAndType("anhui", 1108);
//			System.out.println(JSON.toJSONString(vo));
			
			SmsContentVo vo = new SmsContentVo();
			vo.setSmsType(1108);
			List<SmsContentVo> vol = service4.getSmsContent(vo);
			if( vol == null || vol.size() <= 0 ){
				System.out.println("null");
			}else{
				vo = vol.get(0);
				System.out.println(JSON.toJSONString(vo));
				vo.setContent("您的团购商品{0}数量{1}个，您可以进入社区e银行个人中心查看券码信息…");
				System.out.println(JSON.toJSONString(service4.updateSmsContent(null, vo)));
				
			}
			
			
//			vo.setContent("您的团购商品{0}数量{1}个,您可以进入社区e银行个人中心查看券码信息…");
//			System.out.println(JSON.toJSONString(service4.updateSmsContent(null, vo)));
			
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
