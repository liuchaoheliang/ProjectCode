/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */

/**  
 * @Title: Test.java
 * @Package com.froad.thrift
 * @Description: TODO
 * @author vania
 * @date 2015年3月18日
 */

package com.froad.thrift;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.redis.impl.RedisManager;
import com.froad.po.MerchantAccount;
import com.froad.po.mongo.Location;
import com.froad.po.mongo.OutletDetailSimpleInfo;
import com.froad.thrift.vo.MerchantAccountVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PlatType;
import com.froad.util.BeanUtil;
import com.froad.util.GeoUtil;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.froad.util.PropertiesUtil;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * <p>
 * Title: Test.java
 * </p>
 * <p>
 * Description: 描述
 * </p>
 * 
 * @author vania
 * @version 1.0
 * @created 2015年3月18日 上午9:51:05
 */

public class Test {
	List<String> list = new ArrayList<String>();

	/**
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @Title: main
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param args
	 * @return void 返回类型
	 * @throws
	 */
	public static void main(String[] args) throws Exception {
		String outletName ="%";
		if(StringUtils.isNotBlank(outletName) 
				
				&& outletName.indexOf('%')>0) {
			System.out.println(outletName.replaceAll(outletName, "\\\\%"));
		}
		System.out.println(outletName.replaceAll(outletName, "\\\\%"));
		System.exit(0);
		
		String parentTreePath = "0,1111,55555";
		
		System.out.println(parentTreePath.startsWith("0,"));
		System.out.println(parentTreePath.substring(parentTreePath.indexOf("0,")+2));
		
		OutletDetailSimpleInfo info = new OutletDetailSimpleInfo();
		info.setId("111111111111111111l");
		info.setOutletName("aa a a ");
		Location location = new Location();
		location.setLatitude(31.521563);
		location.setLongitude(116.999999);
		info.setLocation(location);
		
		List<OutletDetailSimpleInfo> list = new ArrayList<OutletDetailSimpleInfo>();
		list.add(info);
		System.out.println("设置之前:"+JSON.toJSONString(list,true));
		
		setDistance(116.365545, 31.321563, list);
		System.out.println("设置之后:"+JSON.toJSONString(list,true));
		
		Boolean a = false;
		Boolean b = true;
		System.out.println(a==b);
		
		System.exit(0);
		System.out.println(getMethodName0());
		
//		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
//		for (int i = 0; i < ste.length; i++) {			
//			System.out.println(ste[i].getMethodName());
//		}
		
		
		System.exit(1);
//		PropertiesUtil.load();
		OriginVo originVo = new OriginVo();
		originVo.setPlatType(PlatType.bank);
		System.out.println(originVo.getPlatType() == PlatType.bank);
		
		String lat = "31.8416583524";
		if(!NumberUtils.isNumber(lat) || -90 > Double.parseDouble(lat) || Double.parseDouble(lat) > 90) {
			throw new Exception("纬度不合法!");
		}
		
		
		Calendar ca = Calendar.getInstance();
//		ca.set(Calendar.YEAR, 0); // 设置为0000年
		System.out.println(ca.getTimeInMillis());
		ca.set(Calendar.YEAR, 2016); // 设置为0000年
		System.out.println(ca.getTimeInMillis());
		Test t = new Test();
		t.printAnyThree();
	}

	private static List<OutletDetailSimpleInfo> setDistance(double lng, double lat, List<OutletDetailSimpleInfo> outletDetailList) {
		for (OutletDetailSimpleInfo outletDetailSimpleInfo : outletDetailList) {
			
			Location location = outletDetailSimpleInfo.getLocation();
			if (null != location) {
				Double latitude = location.getLatitude();
				Double longitude = location.getLongitude();
				if (latitude != null && longitude != null) {
					double dis = GeoUtil.getDistanceOfMeter(lat, lng, latitude, longitude); // 计算距离
					System.out.println("dis======>"+dis);
					outletDetailSimpleInfo.setDis(dis);
				}
			}
			
		}
		return outletDetailList;
	}
	
	
	public static String getMethodName0(){
		return getMethodName1();
	}
	public static String getMethodName1(){
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		for (int i = 0; i < ste.length; i++) {			
			System.out.println(ste[i].getClassName() + "\t" + ste[i].getMethodName());
		}
		return "";
//		return  Thread.currentThread().getStackTrace()[1].getMethodName(); 
	}

	public void printAnyThree() throws Exception {
		int[] arr = { 1, 3, 5, 7, 9, 11, 13, 15 };
		print(combine(arr, 3));
	}

	public List combine(int[] a, int m) throws Exception {
		int n = a.length;
		if (m > n) {
			throw new Exception("错误！数组a中只有" + n + "个元素。" + m + "大于" + 2
					+ "!!!");
		}

		List result = new ArrayList();

		int[] bs = new int[n];
		for (int i = 0; i < n; i++) {
			bs[i] = 0;
		}
		// 初始化
		for (int i = 0; i < m; i++) {
			bs[i] = 1;
		}
		boolean flag = true;
		boolean tempFlag = false;
		int pos = 0;
		int sum = 0;
		// 首先找到第一个10组合，然后变成01，同时将左边所有的1移动到数组的最左边
		do {
			sum = 0;
			pos = 0;
			tempFlag = true;
			result.add(print(bs, a, m));

			for (int i = 0; i < n - 1; i++) {
				if (bs[i] == 1 && bs[i + 1] == 0) {
					bs[i] = 0;
					bs[i + 1] = 1;
					pos = i;
					break;
				}
			}
			// 将左边的1全部移动到数组的最左边

			for (int i = 0; i < pos; i++) {
				if (bs[i] == 1) {
					sum++;
				}
			}
			for (int i = 0; i < pos; i++) {
				if (i < sum) {
					bs[i] = 1;
				} else {
					bs[i] = 0;
				}
			}

			// 检查是否所有的1都移动到了最右边
			for (int i = n - m; i < n; i++) {
				if (bs[i] == 0) {
					tempFlag = false;
					break;
				}
			}
			if (tempFlag == false) {
				flag = true;
			} else {
				flag = false;
			}

		} while (flag);
		result.add(print(bs, a, m));

		return result;
	}

	private int[] print(int[] bs, int[] a, int m) {
		int[] result = new int[m];
		int pos = 0;
		for (int i = 0; i < bs.length; i++) {
			if (bs[i] == 1) {
				result[pos] = a[i];
				pos++;
			}
		}
		return result;
	}

	private void print(List l) {
		
		int n = 0;
		for (int i = 0; i < l.size(); i++) {
			int[] a = (int[]) l.get(i);
			for (int j = 0; j < a.length; j++) {
				n += a[j];
				
				System.out.print(a[j] + " ");
			}
			System.out.println("n==>"+n);
			System.out.println();
			n=0;
		}
	}

}
