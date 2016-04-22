package com.froad.db.mongo.page;

import java.util.LinkedHashMap;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Sort {  
  
    /** key为排序的名称, value为顺序 */  
    private Map<String, OrderBy> field = new LinkedHashMap<String, OrderBy>();  
  
    public Sort(String key, OrderBy order) {  
        field.put(key, order);  
    }  
  
    public Sort on(String key, OrderBy order) {  
        field.put(key, order);  
        return this;  
    }  
  
    public DBObject getSortObject() {  
        DBObject dbo = new BasicDBObject();  
        for (String k : field.keySet()) {  
            dbo.put(k, (field.get(k).equals(OrderBy.ASC) ? 1 : -1));  
        }  
        return dbo;  
    }  
} 