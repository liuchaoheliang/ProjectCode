package com.froad;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.froad.logback.LogCvt;
import com.froad.po.ActiveOrderDetail;
import com.froad.po.ReturnSubOrderProductRes;
import com.froad.util.Arith;

public class Test {

	public static void main(String[] args) throws Exception{
		
//		f1();
		
//		f2();
		
//		f3();
		
//		String couponCode = "aaaabbbbcccc";
//		System.out.println(couponCode.substring(0,4)+" - "+couponCode.substring(4,8)+" - "+couponCode.substring(8,12));
		
//		Double sumMoney = 0.0;
//		Double d = 0.0;
//		if(d<=0){
//			d=null;
//		}
//		sumMoney = Arith.sub(sumMoney, d);
//		System.out.println(sumMoney);
		
		f4(11);
	}
	
	public static void f4(int num){
		String url = "jdbc:mysql://10.43.2.7:3306/froad_cbank_4?useUnicode=true&characterEncoding=utf8";  
	    String name = "com.mysql.jdbc.Driver";  
	    String user = "root";  
	    String password = "root";  
	    java.sql.Connection conn = null; 
	    java.sql.PreparedStatement pst1 = null;
	    java.sql.PreparedStatement pst2 = null;
	    java.sql.ResultSet result = null;
		int index = 0, count = num, cf = 0, add = 0;
		String ticket = null;
		long s = System.currentTimeMillis();
		try{
			Class.forName(name);//指定连接类型  
			conn = java.sql.DriverManager.getConnection(url, user, password);//获取连接  
			pst1 = conn.prepareStatement("select * from vvv where v_id = ?");//准备执行语句  
			pst2 = conn.prepareStatement("insert into vvv(v_id,money) values(?,?)");//准备执行语句  
			while( index < count ){
				ticket = generate1();
				pst1.setString(1, ticket);
            	result = pst1.executeQuery();
            	if( result.next() ){
            		System.out.println("重复了 "+(++cf)+" 个");
            	}else{
            		pst2.setString(1, ticket);
                    pst2.setInt(2, 50);
                    add = pst2.executeUpdate();
                    if( add == 1 ){
                    	index++;
                    	System.out.println("成功了 "+(index)+" 个");
                    }else{
                    	System.out.println("第 "+(index)+" 个失败");
                    }
            	}
			}
			long e = System.currentTimeMillis();
			System.out.println("耗时秒数 "+((e-s)/1000));
			System.out.println("失败个数 "+(cf));
		} catch (Exception e) {  
			e.printStackTrace();  
		} finally { 
			if(null != conn)
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}  
		} 
	}
	private static java.util.Random random = new java.util.Random();;
	public static String generate1(){
		int first = -1,	second = -1, third = -1;
		String ticket = null, t_first = null, t_second = null, t_third = null;
		first = random.nextInt(100000);
		second = random.nextInt(100000);
		third = random.nextInt(100);
		t_first = fill(first);
		t_second = fill(second);
		t_third = ""+(third<10?("0"+third):(third));
		ticket = t_first+t_second+t_third;
		return ticket;
	}
	private static String fill(int num){
		String str = null;
		if( num < 10 ){
			str = "0000"+num;
		}else if( num < 100 ){
			str = "000"+num;
		}else if( num < 1000 ){
			str = "00"+num;
		}else if( num < 10000 ){
			str = "0"+num;
		}else{
			str = ""+num;
		}
		return str;
	}

	public static void f3(){
		
		List<ReturnSubOrderProductRes> returnSubOrderProductResList = new ArrayList<ReturnSubOrderProductRes>();
		ReturnSubOrderProductRes returnSubOrderProductRes = new ReturnSubOrderProductRes();
		returnSubOrderProductRes.setProductId("65FG78KLJ001");
		returnSubOrderProductRes.setPrice(5.5);
		returnSubOrderProductRes.setVipPrice(4.4);
		returnSubOrderProductRes.setNormalCount(3);
		returnSubOrderProductRes.setVipCount(7);
		returnSubOrderProductResList.add(returnSubOrderProductRes);
		System.out.println(JSON.toJSONString(returnSubOrderProductResList));
		returnSubOrderProductRes = returnSubOrderProductResList.get(0);
		returnSubOrderProductRes.setVouPrice(3.3);
		returnSubOrderProductRes.setVipVouPrice(2.2);
//		returnSubOrderProductResList.add(returnSubOrderProductRes);
		System.out.println(JSON.toJSONString(returnSubOrderProductResList));
		for( ReturnSubOrderProductRes p : returnSubOrderProductResList ){
			System.out.println(JSON.toJSONString(p));
		}
	}
	
	public static void f2(){
		List<Map<String, Object>> productInfoList = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> product = new HashMap<String, Object>();
		product.put("product_id", "12JH87S8701");
		product.put("product_name", "娃哈哈童鞋");
		product.put("product_price", "76000");
		product.put("vou_money", "12000"); // 总价 - 平摊的代金券/红包金额
		product.put("quantity", "1");
		product.put("product_vip_price", "52000");
		product.put("vip_vou_money", "7800"); // 总价 - 平摊的代金券/红包金额
		product.put("vip_quantity", "1");
		productInfoList.add(product);
		product = new HashMap<String, Object>();
		product.put("product_id", "34JL9812H01");
		product.put("product_name", "PUMA T恤");
		product.put("product_price", "76000");
		product.put("vou_money", "12000"); // 总价 - 平摊的代金券/红包金额
		product.put("quantity", "1");
		product.put("product_vip_price", "52000");
		product.put("vip_vou_money", "7800"); // 总价 - 平摊的代金券/红包金额
		product.put("vip_quantity", "1");
		productInfoList.add(product);

		String detail = JSON.toJSONString(productInfoList);
		System.out.println(detail);
		
		productInfoList = (List<Map<String, Object>>)JSON.parse(detail);
		for( Map<String, Object> p : productInfoList ){
			System.out.println(p);
		}
		
	}
	
	public static void f1(){
		
		Long now = System.currentTimeMillis();
		long end = new Date(now+(3600*24*1000*0)+(3600*1000*8)+(60*1000*12)+(1000*12)).getTime(); 
		Long gap = end - now;
		System.out.println(gap);
		long t = gap / ( 3600 * 24 * 1000 );
		System.out.print("差距 "+t+"天");
		if( gap % ( 3600 * 24 * 1000 ) > 0 ){
			gap = gap - ( 3600 * 24 * 1000 ) * t;
			long s = gap / ( 3600 * 1000 );
			System.out.print(" "+s+"时 ");
			if( gap % ( 3600 * 1000 ) > 0 ){
				gap = gap - ( 3600 * 1000 ) * s;
				long f = gap / ( 60 * 1000 );
				System.out.print(" "+f+"分 ");
			}
		}
		
	}
}
