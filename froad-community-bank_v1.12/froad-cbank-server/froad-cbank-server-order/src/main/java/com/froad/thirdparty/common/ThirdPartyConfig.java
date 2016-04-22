package com.froad.thirdparty.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.alibaba.fastjson.JSONObject;
import com.froad.Constants;
import com.froad.logback.LogCvt;


public class ThirdPartyConfig {
    
    /**
     * 积分兑换码超时时间
     */
    public static int EXCHANGE_CODE_EXPIRE_TIME = 300;
    private static JSONObject clientPEBankIDMapping = null;
    static{
            Properties props = new Properties();
            try
            {
                FileReader fr = new FileReader(new File(System.getProperty(Constants.CONFIG_PATH)+File.separatorChar+"thirdparty.properties"));
                
                props.load(fr);
            }
            catch (FileNotFoundException e)
            {
                LogCvt.error("加载配置文件信息错误", e);
            }
            catch (IOException e)
            {
                LogCvt.error("加载配置文件信息错误", e);
            }
            
            if(props.contains("EXCHANGE_CODE_EXPIRE_TIME")){
                EXCHANGE_CODE_EXPIRE_TIME = Integer.valueOf(props.getProperty("EXCHANGE_CODE_EXPIRE_TIME","300"));
            }
            
            if(props.contains("VIP_BANK_ID_MAPPING")){
            	String mappingJson = props.getProperty("VIP_BANK_ID_MAPPING");
            	clientPEBankIDMapping = (JSONObject) JSONObject.parse(mappingJson);
            }
    }
    
    public static Long getPEBankLabelIDByClientID(String clientID){
    	
    	if(clientPEBankIDMapping == null){
    		return null;
    	}
    	
    	Object value = clientPEBankIDMapping.get(clientID);
    	
    	if(value!=null){
    		String valueStr = value.toString();
    		if(valueStr.matches("\\d+")){
    			return Long.valueOf(valueStr);
    		}
    	}
    	
    	return null;
    }
}
