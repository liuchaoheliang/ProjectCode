package com.froad.util.froadMap;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ProvinceMap {

	public static final Map<String, String> PostCityMap;
	static {
		PostCityMap = new TreeMap<String, String>();
		
		PostCityMap.put("0110", "北京市");
		PostCityMap.put("0210", "天津市");
		PostCityMap.put("0315", "重庆市");
		PostCityMap.put("0405", "上海市");
		PostCityMap.put("0512", "河北省");
		PostCityMap.put("0612", "山西省");
		PostCityMap.put("0712", "辽宁省");
		PostCityMap.put("0815", "吉林省");
		PostCityMap.put("0915", "黑龙江省");
		PostCityMap.put("1005", "江苏省");
		PostCityMap.put("1105", "浙江省");
		PostCityMap.put("1110", "安徽省");
		PostCityMap.put("1310", "福建省");
		PostCityMap.put("1412", "江西省");
		PostCityMap.put("1510", "山东省");
		PostCityMap.put("1612", "河南省");
		PostCityMap.put("1712", "湖北省");
		PostCityMap.put("1812", "湖南省");
		PostCityMap.put("1910", "广东省");
		PostCityMap.put("2012", "海南省");
		PostCityMap.put("2112", "四川省");
		PostCityMap.put("2215", "贵州省");
		PostCityMap.put("2315", "云南省");
		PostCityMap.put("2415", "陕西省");
		PostCityMap.put("2515", "甘肃省");
		PostCityMap.put("2620", "青海省");
		PostCityMap.put("2700", "台湾省");
		PostCityMap.put("2815", "内蒙古自治区");
		PostCityMap.put("2912", "广西壮族自治区");
		PostCityMap.put("3015", "宁夏回族自治区");
		PostCityMap.put("3120", "新疆维吾尔自治区");
		PostCityMap.put("3220", "西藏自治区");
		PostCityMap.put("3300", "香港特别行政区");
		PostCityMap.put("3400", "澳门特别行政区");
		PostCityMap.put("3520", "其他城市");
	}
	public static void main(String args[]) {
		Set<Map.Entry<String, String>> set = PostCityMap.entrySet();
		int i = 0;
        for (Iterator<Map.Entry<String, String>> it = set.iterator(); it.hasNext();) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            //System.out.println(entry.getKey() + "--->" + entry.getValue());
            System.out.println("insert into logistics_price(ID,logisticsCompanyID,providerID,city,price) values('"+ ++i +"','100','10000','"+ 
            		entry.getValue()+"','"+ entry.getKey().substring(2, 4) +"');");
        }

		
	}

}
