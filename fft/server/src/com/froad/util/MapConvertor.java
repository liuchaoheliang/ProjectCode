package com.froad.util;
import java.util.ArrayList;

import java.util.List;

import java.util.Map;

 

import javax.xml.bind.annotation.XmlAccessType;

import javax.xml.bind.annotation.XmlAccessorType;

import javax.xml.bind.annotation.XmlType;

 




	/**
	 * 类描述：map适配器
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: meicy meiwenxiang@f-road.com.cn
	 * @time: 2012-3-6 下午1:48:46 
	 */
@XmlType(name = "MapConvertor")  
@XmlAccessorType(XmlAccessType.FIELD)  
public class MapConvertor {  
      
    private List<MapEntry> entries = new ArrayList<MapEntry>();  
      
    public void addEntry(MapEntry entry){  
        entries.add(entry);  
    }  
      
    public static class MapEntry{  
        public MapEntry() {  
            super();  
        }  
        public MapEntry(Map.Entry<String,Object> entry) {  
            super();  
            this.key = entry.getKey();  
            this.value = entry.getValue();  
        }  
        public MapEntry(String key,Object value) {  
            super();  
            this.key = key;  
            this.value = value;  
        }  
        private String key;  
        private Object value;  
        public String getKey() {  
            return key;  
        }  
        public void setKey(String key) {  
            this.key = key;  
        }  
        public Object getValue() {  
            return value;  
        }  
        public void setValue(Object value) {  
            this.value = value;  
        }  
    }  
  
    public List<MapEntry> getEntries() {  
        return entries;  
    }  
}  