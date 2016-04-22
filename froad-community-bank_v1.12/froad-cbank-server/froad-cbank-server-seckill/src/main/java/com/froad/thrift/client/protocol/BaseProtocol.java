package com.froad.thrift.client.protocol;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

public class BaseProtocol {
	
	
	/**
	 * 类描述：传输协议类型
	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2015 
	 * @author: 刘超 liuchao@f-road.com.cn
	 * @time: 2015年3月24日 下午1:20:17 
	 */
	public enum ProtocolType{
		TBinary, //TBinaryProtocol 使用传输协议
		TCompact //TCompactProtocol 使用传输协议
	}

	/**
	  * 方法描述：获取连接协议（默认TBinaryProtocol）
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年3月24日 上午10:35:32
	  */
	public static TMultiplexedProtocol getProtocol(TSocket tSocket,Class<?> clazz) {
		TMultiplexedProtocol protocol=null;
		try {		
			protocol = new TMultiplexedProtocol(new TBinaryProtocol(tSocket),clazz.getSimpleName());
			return protocol ;
		} catch (Exception e) {
			e.printStackTrace();
			return protocol;
		}
	}
	
	public static TMultiplexedProtocol getProtocol(TSocket tSocket,String serverName){
		TMultiplexedProtocol protocol=null;
		try {
			protocol = new TMultiplexedProtocol(new TBinaryProtocol(tSocket),serverName);
			return protocol ;
		} catch (Exception e) {
			return protocol;
		}
	}


	/**
	  * 方法描述：获取连接协议（支持TBinaryProtocol和TCompactProtocol）
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年3月24日 下午4:45:54
	  */
	public static TMultiplexedProtocol getProtocol(ProtocolType type,TSocket tSocket,Class<?> clazz) {
		TMultiplexedProtocol protocol=null;
		try {	
			if(ProtocolType.TBinary.equals(type)){
				protocol = new TMultiplexedProtocol(new TBinaryProtocol(tSocket),clazz.getSimpleName());
			}else if(ProtocolType.TCompact.equals(type)){
				protocol = new TMultiplexedProtocol(new TCompactProtocol(tSocket),clazz.getSimpleName());
			}
			return protocol ;
		} catch (Exception e) {
			return protocol;
		}
	}
}
