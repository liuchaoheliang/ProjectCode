package com.froad.cbank.coremodule.module.normal.user.support;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.common.Constants;
import com.froad.cbank.expand.redis.RedisManager;


	/**
	 * 类描述：基类
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2015 
	 * @author: 刘超 liuchao@f-road.com.cn
	 * @time: 2015年3月23日 下午6:03:53 
	 */
public class BaseSupport {
	

	/** 
	 * 返回值
	 * @ClassName: Results 
	 * @Description: TODO(这里用一句话描述这个类的作用) 
	 * @Create Author: hjz
	 * @Create Date: 2015年4月8日 下午5:39:07 
	 */ 
	public static class Results {
		/**
		 * 状态码
		 * @Title code
		 * @type Results
		 * @Create Author: hjz
		 * @date 2015年4月3日 下午2:51:31
		 * 含义 TODO
		 */
		public static final String code = Constants.Results.code;
		/**
		 * 返回信息
		 * @Title msg
		 * @type Results
		 * @Create Author: hjz
		 * @date 2015年4月3日 下午2:51:37
		 * 含义 TODO
		 */
		public static final String msg = Constants.Results.msg;
		
		/**
		 * 返回数据
		 * @Title result
		 * @type Results
		 * @Create Author: hjz
		 * @date 2015年4月3日 下午2:51:47
		 * 含义 TODO
		 */
		public static final String result = Constants.Results.result;
		
		
	}
//	private TSocket tSocket;
	
//	@Resource 
//	public ConnectionManager connectionManager;
	
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
	public TMultiplexedProtocol getProtocol(TSocket socket,Class clazz) {
		TMultiplexedProtocol protocol=null;
		try {		
			protocol = new TMultiplexedProtocol(new TBinaryProtocol(socket),clazz.getSimpleName());
//			LogCvt.info("打开传输连接");
//			tSocket.open();
			return protocol ;
		} catch (Exception e) {
			e.printStackTrace();
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
	public  TMultiplexedProtocol getProtocol(ProtocolType type,TSocket socket,Class clazz) {
		TMultiplexedProtocol protocol=null;
		try {	
			if(ProtocolType.TBinary.equals(type)){
				protocol = new TMultiplexedProtocol(new TBinaryProtocol(socket),clazz.getSimpleName());
			}else if(ProtocolType.TCompact.equals(type)){
				protocol = new TMultiplexedProtocol(new TCompactProtocol(socket),clazz.getSimpleName());
			}
//			LogCvt.info("打开传输连接");
//			tSocket.open();
			return protocol ;
		} catch (Exception e) {
			e.printStackTrace();
			return protocol;
		}
	}
	
	
	/**
	  * 方法描述：关闭连接资源
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年3月24日 下午4:47:03
	  */
	public void closeSource(TMultiplexedProtocol protocol){
		if(protocol!=null){
			LogCvt.info("关闭连接资源");
			protocol.getTransport().close();
		}
	}
	
}
