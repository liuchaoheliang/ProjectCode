package com.froad.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import com.froad.logback.LogCvt;

public class GoodsSortConstants {

	public static final Map<String, String> sqlSortOrdersMap = new HashMap<String, String>();//mysql查询默认排序的字段
    public static final Map<String, String> mongoSortOrdersMap = new HashMap<String, String>();//mongo查询默认排序的字段
    
    public static final Map<String, String> sqlOrderFiledsMap = new HashMap<String, String>();//mysql查询可以排序的字段
    public static final Map<String, String> mongoOrderFiledsMap = new HashMap<String, String>();//mongo查询可以排序的字段
    static{
        sqlSortOrdersMap.put("0", "{\"1\":\"createTime-desc\"}");//不传类型的排序
        sqlSortOrdersMap.put("1", "{\"1\":\"createTime-desc\"}");
        sqlSortOrdersMap.put("2", "{\"1\":\"createTime-desc\"}");
        sqlSortOrdersMap.put("3", "{\"1\":\"createTime-desc\"}");
        sqlSortOrdersMap.put("4", "{\"1\":\"createTime-desc\"}");
        sqlSortOrdersMap.put("5", "{\"1\":\"createTime-desc\"}");
        
        mongoSortOrdersMap.put("1", "{\"2\":\"sellCount-desc\",\"1\":\"price-asc\"}");
        mongoSortOrdersMap.put("2", "{\"3\":\"startTime-desc\",\"2\":\"sellCount-desc\",\"1\":\"price-asc\"}");
        mongoSortOrdersMap.put("3", "{\"2\":\"sellCount-desc\",\"1\":\"price-asc\"}");
        mongoSortOrdersMap.put("4", "{\"3\":\"startTime-desc\",\"2\":\"sellCount-desc\",\"1\":\"price-asc\"}");
        mongoSortOrdersMap.put("5", "{\"3\":\"startTime-desc\",\"2\":\"sellCount-desc\",\"1\":\"price-asc\"}");
        
        sqlOrderFiledsMap.put("sellCount", "sell_count");
        sqlOrderFiledsMap.put("startTime", "start_time");
        sqlOrderFiledsMap.put("createTime", "create_time");
        sqlOrderFiledsMap.put("price", "price");
        sqlOrderFiledsMap.put("rackTime", "rack_time");
        sqlOrderFiledsMap.put("point", "point");
        sqlOrderFiledsMap.put("endTime", "end_time");
        sqlOrderFiledsMap.put("auditTime", "audit_time");
        sqlOrderFiledsMap.put("downTime", "down_time");
        
        mongoOrderFiledsMap.put("sellCount", "sell_count");
        mongoOrderFiledsMap.put("startTime", "start_time");
        mongoOrderFiledsMap.put("price", "price");
        mongoOrderFiledsMap.put("endTime", "end_time");
        mongoOrderFiledsMap.put("storeCount", "store_count");
        mongoOrderFiledsMap.put("rackTime", "rack_time");
    }
    
    public static LinkedHashMap<String, String> getSortOrderFileds(String orderFiledStr,Map<String,String> orderFiledsMap){
        LinkedHashMap<String, String> orderFileds = new LinkedHashMap<String, String>();
        try{
            if(orderFiledStr!=null && !"".equals(orderFiledStr)){
                //orderField:排序字段
                //权重:字段-排序方式（权重越高排序越优先,排序方式为desc或asc）
                //排序字段
                //key是需要排序列权重(权重越高排序的优先级越高),value是字段-排序方式;
                @SuppressWarnings("unchecked")
				Map<String, String> map = (Map<String, String>) JSonUtil.toObject(orderFiledStr, Map.class);
                if(map!=null && map.size()>0){
                    Map<String, String> orders = new TreeMap<String, String>(new Comparator<String>() { // 按照key(列权重)降序排列
                        public int compare(String obj1, String obj2) {
                            // 降序排序
                            return obj2.compareTo(obj1);
                        }
                    });
                    orders.putAll(map);
                    
                    String column = null;
                    String order = null;
                    for(String column_order : orders.values()){
                        String[] column_order_arr = column_order.split("-");
                        if(column_order_arr.length == 2) {
                            order = column_order_arr[1].trim();
                            column = orderFiledsMap.get(column_order_arr[0].trim());
                            if(column!=null && !"".equals(column)){
                                orderFileds.put(column, !order.equalsIgnoreCase("desc") ? "asc" : order); // 默认asc
                            }
                        }
                    }
                    return orderFileds;
                }
            }
        } catch (Exception e) {
            LogCvt.error("getSortOrderFileds失败,orderFiledStr:"+orderFiledStr+",原因:" + e.getMessage()); 
        }
        return orderFileds;
    }
    
}
