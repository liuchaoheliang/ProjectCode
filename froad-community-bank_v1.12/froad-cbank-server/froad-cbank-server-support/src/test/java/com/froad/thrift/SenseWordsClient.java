package com.froad.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.thrift.service.SenseWordsService;
import com.froad.thrift.vo.ResultVo;

public class SenseWordsClient {

	 public static void main(String argv[]){
	        try{
	        	TSocket transport = null;
			transport = new TSocket("localhost", 15701);
//				transport = new TSocket("10.43.2.3", 15701);
//                transport = new TSocket("10.43.1.9", 15701);
				
				TBinaryProtocol protocol = new TBinaryProtocol(transport);
				transport.open();
				
				TMultiplexedProtocol mp6= new TMultiplexedProtocol(protocol,"SenseWordsService");
				SenseWordsService.Client service = new SenseWordsService.Client(mp6);
				String str="东至诸葛烤鱼火锅店是一家经营烤鱼系列为主的餐厅,同时还提供精品系列为辅的菜系产品,烤鱼采用先烤"
						+ "后炖的独特烹饪方法,边吃边以小火持续炖煮,出品的烤鱼肉质鲜美,口感极佳，香味浓郁,汤红色辣,辣而不燥！店内所有鱼均为现做,新鲜有保障";
				String str1="安徽农金社区e银行用户进店消费,凭手机支付菜金享受9.5折优惠;21:30以后进店消费,菜金可享受8.8折优惠";
				String str2="活动时间：2015年6月29日至2015年12月31日";
				ResultVo result = service.isContaintSensitiveWord(str);
				System.out.println(result);
				
	        } catch (Exception e) {
				e.printStackTrace();
			}
	    }
}
