package com.froad.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.safehaus.uuid.UUID;
import org.safehaus.uuid.UUIDGenerator;

public class Util {

	private static Logger logger = Logger.getLogger(Util.class);
	
	/**
	  * 方法描述：获取本机IP地址
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: meicy meiwenxiang@f-road.com.cn
	  * @time: 2011-12-8 下午4:20:51
	  */
	public static String getIp() {
		String localip = null;// 本地IP，如果没有配置外网IP则返回它
		String netip = null;// 外网IP
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface
					.getNetworkInterfaces();
			InetAddress ip = null;
			boolean finded = false;// 是否找到外网IP
			while (netInterfaces.hasMoreElements() && !finded) {
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> address = ni.getInetAddresses();
				while (address.hasMoreElements()) {
					ip = address.nextElement();
					 System.out.println(ni.getName() +";"+ip.getHostAddress()
					 +";ip.isSiteLocalAddress()="+ip.isSiteLocalAddress()+";ip.isLoopbackAddress()="+ip.isLoopbackAddress());
					if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
							&& ip.getHostAddress().indexOf(":") == -1) {// 外网IP
						netip = ip.getHostAddress();
						
						finded = true;
						break;
					} else if (ip.isSiteLocalAddress()
							&& !ip.isLoopbackAddress()
							&& ip.getHostAddress().indexOf(":") == -1) {// 内网IP
						localip = ip.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			logger.error("异常", e);
		}
		
		logger.info("外网IP地址为:"+netip);
		if (netip != null && !"".equals(netip)) {
			return netip;
		} else {
			return localip;
		}
	}

	/**
	 * 方法描述：生成UUID
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 勾君伟 goujunwei@f-road.com.cn
	 * @version: 2011-3-11 上午11:24:37
	 */
	public static String getGUID() {
		UUIDGenerator g = UUIDGenerator.getInstance();
		UUID uuid = g.generateRandomBasedUUID();
		return uuid.toString();
	}

	/**
	 * 方法描述：短信注册时 对激活码的操作
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2011-6-1 下午05:09:35
	 */
	public static String createCode(String code) {

		code = code.substring(4, 10);

		return code;
	}

//	/**
//	 * 方法描述：金额处理 保留两位小数（第三位有则进一处理）
//	 * 
//	 * @param:
//	 * @return:
//	 * @version: 1.0
//	 * @author: 何庆均 heqingjun@f-road.com.cn
//	 * @update:
//	 * @time: 2011-9-5 下午12:52:24
//	 */
//	public static BigDecimal priceManage(BigDecimal price) {
//		return price.add(new BigDecimal("0.004")).setScale(2,
//				BigDecimal.ROUND_HALF_UP);
//	}
	
	/**
	  * 方法描述： 保留两位小数（第三位有则进一处理）
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2012-6-8 下午4:16:48
	  */
	public static String roundUp(String value){
		return new BigDecimal(value==null?"":value).setScale(2, BigDecimal.ROUND_UP).toString();
	}
	
	/**
	 * 方法描述：保留两位小数（第三位有则截断处理）
	 * @param: 
	 * @return: 
	 * @version: 1.0
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2012-6-8 下午4:16:48
	 */
	public static String roundDown(String value){
		return new BigDecimal(value==null?"":value).setScale(2, BigDecimal.ROUND_DOWN).toString();
	}
	/**
	 * 方法描述：金额处理 保留两位小数（第三位有则四舍五入处理）
	 * 
	 * @param:	
	 * @return:
	 * @version: 1.0
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2011-9-5 下午12:52:24
	 */
	public static String priceScale2(String price) {
		return (new BigDecimal(price==null||"".equals(price)?"0":price)).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
	}
	
	
	
	/**
	  * 方法描述：截掉小数后3位  0.003 --》 0.00
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2013-4-9 下午9:59:25
	  */
	public static BigDecimal formatMoney(BigDecimal  val) {
		return val.setScale(2, RoundingMode.DOWN);
	}
	public static BigDecimal formatMoney(String  val) {
		return new BigDecimal(val).setScale(2, RoundingMode.DOWN);
	}
	/**
	 * 保留到整数
	 * @param price
	 * @return
	 */
	public static String priceScale0(String price) {
		return (new BigDecimal(price==null||"".equals(price)?"0":price)).setScale(0,BigDecimal.ROUND_HALF_UP).toString();
	}
	
	/**
	 * 方法描述：计算时间间隔(天数)
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: meicy meiwenxiang@f-road.com.cn
	 * @time: 2011-12-7 下午6:18:08
	 */
	public static int IntervalDays(Date stratTime, Date endTime) {
		   int result=0;
			Long between = (endTime.getTime() - stratTime.getTime()) / 1000;// 除以1000是为了转换成秒
			Long day = between / (24 * 3600);
			result=Integer.parseInt(day.toString());        
		return result;
	}

	
	/**
	  * 方法描述：获取json数据
	  * @param: JSONObject jb,String key
	  * @return: 
	  * @version: 1.0
	  * @author: 黄庆伟 huangqingwei@f-road.com.cn
	  * @time: Dec 8, 2011 3:04:35 PM
	  */
	public static String getJsonValue(JSONObject jb,String key){
		String value="";
		try {
			value=jb.getString(key);
		} catch (JSONException e) {
			// TODO: handle exception
			//logger.error("异常", e);
			value="";
		}
		return value;
	}
	
	
	/**
	  * 方法描述：获取当前时间
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: meicy meiwenxiang@f-road.com.cn
	  * @time: 2011-12-10 下午2:25:13
	  */
	public static  String getCurrentTime ()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String Time = df.format(new Date());
		return Time;
	}
	/**
	  * 方法描述：原始时间转换为显示时间
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: chenxiangde chenxiangde@f-road.com.cn
	  * @time: 2011-12-11 下午2:25:13
	  */
	public static  String getDisTime(String orgTime)
	{
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    SimpleDateFormat df2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
	    String disTime=null;
		try {
			disTime = df2.format(df1.parse(orgTime));
		} catch (ParseException e) {
			logger.error("异常", e);
		}
		return disTime;
	}
	
	public static void main(String[] args) throws ParseException {
//		String stratTime = "2008-02-28 12:12:12";
//		String endTime = "2009-02-28 12:12:12";
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date begin;
//			begin = df.parse(stratTime);
//			Date end = df.parse(endTime);
//		System.out.println(IntervalDays(begin,end));
		getIp();
		
	}
}
