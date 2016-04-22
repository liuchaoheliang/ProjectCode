package com.froad.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * json相关处理
 * 
 * @author FQ
 * 
 */
public class JsonUtil {
	/**
	 * 将java对象转换成json字符串
	 * 
	 * @param javaObj
	 * @return
	 */
	public static String getJsonString4JavaPOJO(Object javaObj) {

		JSONObject json;
		json = JSONObject.fromObject(javaObj);
		return json.toString();

	}

	/**
	 * 从一个JSON 对象字符格式中得到一个java对象
	 * 
	 * @param jsonString
	 * @param pojoCalss
	 * @return
	 */
	public static Object getObject4JsonString(String jsonString, Class pojoCalss) {
		Object pojo;
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		pojo = JSONObject.toBean(jsonObject, pojoCalss);
		return pojo;
	}

	/**
	 * 从json数组中得到相应java数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Object[] getObjectArray4Json(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		return jsonArray.toArray();

	}

	/**
	 * 从json HASH表达式中获取一个map，改map支持嵌套功能
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Map getMap4Json(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator keyIter = jsonObject.keys();
		String key;
		Object value;
		Map valueMap = new HashMap();

		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			value = jsonObject.get(key);
			valueMap.put(key, value);
		}

		return valueMap;
	}

	/**
	 * 从json对象集合表达式中得到一个java对象列表
	 * 
	 * @param jsonString
	 * @param pojoClass
	 * @return
	 */
	public static List getList4Json(String jsonString, Class pojoClass) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		Object pojoValue;

		List list = new ArrayList();
		for (int i = 0; i < jsonArray.size(); i++) {

			jsonObject = jsonArray.getJSONObject(i);
			pojoValue = JSONObject.toBean(jsonObject, pojoClass);
			list.add(pojoValue);

		}
		return list;

	}
	
	/**
	 * List 转成 JSONArray串
	 * @param list
	 * @return
	 */
	public static String getJsonString4List(List<?> list){
		JSONArray jsonArray = JSONArray.fromObject(list);
		return jsonArray.toString();
	}
	
	/**
	 *Map 转成 json串
	 * @param map
	 * @return
	 */
	public static String getJsonString4Map(Map<?,?> map){
		JSONObject jsonObject = JSONObject.fromObject(map);
		return jsonObject.toString();
	}
	 
	
	/**
	  * 方法描述：获取选填值
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2012-11-8 上午10:31:20
	  */
	public static Object getOptionalValue(JSONObject object,String key){
		if(object.containsKey(key))
			return object.get(key);
		else
			return "";
	}
	public static Object getOptionalMoneyValue(JSONObject object,String key){
		if(object.containsKey(key)){
			if("".equals(object.get(key)))
				return "0";
			else if(new BigDecimal((String)object.get(key)).compareTo(new BigDecimal("0"))==0){
				return "0";
			//如果有小数，判断小数是否为0
			}else if(((String)object.get(key)).indexOf(".")!=-1){
				String [] price =((String)object.get(key)).split("\\.");
				BigDecimal a= new BigDecimal(price[1]);
				if(a.compareTo(new BigDecimal("0"))==0)
					  return price[0];
				else 
					return object.get(key);
			}else
				return object.get(key);
		}
			 
		else
			return "0";
	} 
	public static void main(String[] args) {
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("name", "北京");
		map1.put("value", "111111");
		list.add(map1);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("st", "0");
		map.put("msg", "非法调用");
		map.put("rt", list);
		
		System.out.println(JsonUtil.getJsonString4List(list));
		System.out.println(JSONObject.fromObject(map));
		
		
		List<Map<String, String>> lowestCodeList = new ArrayList<Map<String, String>>();
		for(int i=0;i<5;i++){
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("code",i+++"");
			lowestCodeList.add(map2);
		}
		
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("st", "1");
		map3.put("rt", lowestCodeList);
		
		System.out.println(JsonUtil.getJsonString4Map(map3));
		String str="{\"status_code\":200,\"message\":\"请求失败\",\"ekey\":\"错误Key\", \"emsg\":\"错误原因描述\"}";
		JSONObject js = JSONObject.fromObject(str);
		
		System.out.println(js.containsKey("ekey"));
		
		String strTest="{\"rt\":[{\"name\":\"农牧业\",\"pcode\":null,\"code\":\"201\",\"type\":\"1\",\"tag\":[\"农业\",\"牧业\"]}," +
		"			 {\"name\":\"农业\",\"pcode\":\"201\",\"code\":\"201101\",\"type\":\"2\",\"tag\":[\"农\"]}," +
		"			 {\"name\":\"农夫\",\"pcode\":\"201101\",\"code\":null,\"type\":\"3\",\"tag\":[\"打鱼者\"]}," +
		"			 {\"name\":\"农具商\",\"pcode\":\"201101\",\"code\":null,\"type\":\"3\",\"tag\":[]}," +
		"			{\"name\":\"渔业\",\"pcode\":null,\"code\":\"202\",\"type\":\"1\",\"tag\":[]}],\"st\":\"1\"}";

		List<Map<String,String>> arr=new ArrayList<Map<String, String>>();
		JSONObject jsob = JSONObject.fromObject(strTest);
		System.out.println(jsob.getJSONArray("rt"));
		System.out.println(jsob.getJSONArray("rt").size());
		
		Map<String, String> map4 = new HashMap<String, String>();
		map4.put("1", "测试");
		
		Iterator it = map4.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry m = (Map.Entry) it.next();
			System.out.println("email-" + m.getKey() + ":" + m.getValue());

		}

	}
	
}
