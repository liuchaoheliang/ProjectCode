package com.froad.cbank.coremodule.framework.common.util.type;

import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.TypeReference;


/************************************************
 * Copyright (c)  by soap
 * All right reserved.
 * Create Date: 2009-8-15
 * Create Author: hujz
 * File Name:  josn工具
 * Last version:  1.0
 * Function:这里写注释
 * Last Update Date:
 * Change Log:
**************************************************/ 
 
public class JsonUtil {
    private ObjectMapper mapper;
    public ObjectMapper getMapper() {
        return mapper;
    }
    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }
    @SuppressWarnings("deprecation")
    public JsonUtil(Inclusion inclusion){
        mapper =new ObjectMapper();
        mapper.getSerializationConfig().setSerializationInclusion(inclusion);
        mapper.getDeserializationConfig().set(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        setDateFormat(TimeUtil.theTimeFormat);
    }
    /**
     * 创建输出全部属性
     * @return
     */
    public static JsonUtil buildNormalBinder(){
        return new JsonUtil(Inclusion.ALWAYS);
    }
    /**
     * 创建只输出非空属性的
     * @return
     */
    public static JsonUtil buildNonNullBinder(){
        return new JsonUtil(Inclusion.NON_NULL);
    }
    /**
     * 创建只输出初始值被改变的属性
     * @return
     */
    public static JsonUtil buildNonDefaultBinder(){
        return new JsonUtil(Inclusion.NON_DEFAULT);
    }
    /**
     * 把json字符串转成对象
     * @param json
     * @param clazz
     * @return
     */
    public <T> T getJsonToObject(String json,Class<T> clazz){
        T object=null;
        if(StringUtil.isNotBlank(json)) {
            try {
                object=getMapper().readValue(json, clazz);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return object;
    }
    /**
     * 把JSON转成list
     * @param json
     * @param typeReference
     * @return
     */
    @SuppressWarnings({ "rawtypes", "deprecation" })
    public Object getJsonToList(String json,TypeReference typeReference){
        Object object=null;
        if(StringUtil.isNotBlank(json)) {
            try {
                object=getMapper().readValue(json, TypeFactory.fromTypeReference(typeReference));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return object;
    }
    /**
     * 把JSON转成list
     * @param json
     * @param clazz
     * @return
     */
    @SuppressWarnings({ "rawtypes", "deprecation" })
    public Object getJsonToList(String json,Class clazz){
        Object object=null;
        if(StringUtil.isNotBlank(json)) {
           try {
                object=getMapper().readValue(json,TypeFactory.instance.constructParametricType(List.class, clazz));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return object;
    }
    /**
     * 把JSON转成Map
     * @param json
     * @param keyclazz
     * @param valueclazz
     * @return
     */
    @SuppressWarnings({ "rawtypes", "deprecation" })
    public Object getJsonToMap(String json,Class keyclazz,Class valueclazz){
        Object object=null;
        if(StringUtil.isNotBlank(json)) {
            try {
                object=getMapper().readValue(json,TypeFactory.instance.constructParametricType(Map.class, keyclazz,valueclazz));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return object;
    }

    /**
     * 把json格式数据装成map
     * @param str
     * @return
     */
    public static Map<String,String> getJsonToMap(String str){
        Map<String,String> map = new HashMap<String, String>();
        if(StringUtil.isNotBlank(str)){
            String[] s=str.split(",");
            if(s.length>0){
                for (int i = 0; i < s.length; i++) {
                    String con=s[i];
                    int s1=con.indexOf(":");
                    if(s1>0){
                        map.put(con.substring(0,s1).trim().replace("\"", ""), con.substring(s1+1).replace("\"", ""));
                    }else{
                        map.put(con.trim().replace("\"", ""), "");
                    }
                }
            }
        }
        return map;
    }

    /**
     * 把map转成combo数据格式的json格式
     * @return String (json)
     */
    public String getMapToJson(Map<String,String> map) {
        List<String[]> list =new ArrayList<String[]>();
        if (null != map && !map.isEmpty()) {
            for (String key : map.keySet()) {
                String[] strS = new String[2];
                strS[0] = key;
                strS[1] = map.get(key);
                list.add(strS);
            }
        }
        return jsonObject(list);
    }

    /**
     * 把对象转成json格式
     * @param obj 需要转的对象
     * @return String
     */
    public String jsonObject(@SuppressWarnings("rawtypes") List list) {
        StringWriter sw = new StringWriter();
        JsonGenerator gen;
        try {
            gen = new JsonFactory().createJsonGenerator(sw);
            getMapper().writeValue(gen, list);
            gen.close();
        } catch (Exception e) {
            
        }
        return sw.toString();
    }
    
    /**
     * 把JSON转成Object
     * @param json
     * @param keyclazz
     * @param valueclazz
     * @return
     */
    @SuppressWarnings({ "rawtypes", "deprecation" })
    public Object getJsonToObject(String json,Class objclazz,Class ...pclazz){
        Object object=null;
        if(StringUtil.isNotBlank(json)) {
            try {
                object=getMapper().readValue(json,TypeFactory.instance.constructParametricType(objclazz, pclazz));
            } catch (Exception e) {
            }
        }
        return object;
    }
    /**
     * 把对象转成字符串
     * @param object
     * @return
     */
    public String toJson(Object object){
        String json=null;
        try {
            json=getMapper().writeValueAsString(object);
        }  catch (Exception e) {
        }
        return json;
    }
    /**
     * 设置日期格式
     * @param pattern
     */
    @SuppressWarnings("deprecation")
    public void setDateFormat(String pattern){
        if(StringUtil.isNotBlank(pattern)){
            DateFormat df=new SimpleDateFormat(pattern);
            getMapper().getSerializationConfig().setDateFormat(df);
            getMapper().getDeserializationConfig().setDateFormat(df);
        }
    }
    
    /**
     *  将JSONObjec对象转换成Map-List集合
     * @param json
     * @return
     */
     @SuppressWarnings("unchecked")
	public static Map<String, Object> JsonToMap(JSONObject json){
         Map<String,Object> columnValMap = new HashMap<String,Object>();
         Set<Object> jsonKeys = json.keySet();
         for (Object key : jsonKeys) {
             Object JsonValObj = json.get(key);
             if(JsonValObj instanceof JSONArray){
                columnValMap.put((String)key,  JsonToList((JSONArray) JsonValObj));
             }else if(key instanceof JSONObject){
                columnValMap.put((String)key,  JsonToMap((JSONObject) JsonValObj));
             }else{
                 columnValMap.put((String)key,JsonValObj);
             }
        }
         return columnValMap;
     }
     /**
      * 将JSONArray对象转换成Map-List集合
      * @param jsonArr
      * @return
      */
     public static List<Object> JsonToList(JSONArray jsonArr){
         List<Object> jsonObjList = new ArrayList<Object> ();
         for(Object obj : jsonArr){
             if(obj instanceof JSONArray){
                 jsonObjList.add(JsonToList((JSONArray) obj));
             } else if(obj instanceof JSONObject){
                 jsonObjList.add(JsonToMap((JSONObject) obj));
             }else{
                 jsonObjList.add(obj);
             }
         }
         return jsonObjList;
     }
    
}