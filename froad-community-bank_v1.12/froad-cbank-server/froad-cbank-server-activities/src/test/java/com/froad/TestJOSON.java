package com.froad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class TestJOSON {
		public static void main(String[] args) {
			
			List<Map<String, Object>> productInfoList = new ArrayList<Map<String, Object>>();
				Map<String, Object> product = new HashMap<String, Object>();
				product.put("product_id", "1");
				product.put("money", "1"); // 总价 - 平摊的满减额
				product.put("quantity","1");
				product.put("vip_money", "1"); // 总价 - 平摊的满减额
				product.put("vip_quantity", "1");
				productInfoList.add(product);
				
				Map<String, Object> product2 = new HashMap<String, Object>();
				product2.put("product_id", "1");
				product2.put("money", "1"); // 总价 - 平摊的满减额
				product2.put("quantity","1");
				product2.put("vip_money", "1"); // 总价 - 平摊的满减额
				product2.put("vip_quantity", "1");
				productInfoList.add(product2);
				System.out.println(productInfoList);
				product2.put("product_id", "11111");
				System.out.println(productInfoList);
			
			
		}
		
		
		

}
