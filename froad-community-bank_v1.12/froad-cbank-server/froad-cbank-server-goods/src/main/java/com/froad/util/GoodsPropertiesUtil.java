package com.froad.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.froad.Constants;
import com.froad.logback.LogCvt;

public class GoodsPropertiesUtil {
    
    private static Properties goodsProperty;//
    private static Properties thriftProperty;
    private static Properties monitorProperty;
    
    static {
        FileReader fr = null;
		try {
			Properties props = new Properties();
			//goods的thrift.properties配置文件名称
			fr = new FileReader(new File(System.getProperty(Constants.CONFIG_PATH)+File.separatorChar+GoodsConstants.THRIFT_PROPERTIES));
            props.load(fr);
            setThriftProperty(props);
            
            //goods的monitor.properties配置文件名称
            fr = new FileReader(new File(System.getProperty(Constants.CONFIG_PATH)+File.separatorChar+GoodsConstants.MONITOR_PROPERTIES));
            props.load(fr);
            setMonitorProperty(props);
            
            //goods.properties配置文件名称
            fr = new FileReader(new File(System.getProperty(Constants.CONFIG_PATH)+File.separatorChar+GoodsConstants.GOODS_PROPERTIES));
            props.load(fr);
            setGoodsProperty(props);
            
            
		    
		} catch (IOException e) {
			LogCvt.error(e.getMessage(), e);
		}finally{
			try {
				if(fr != null){
					fr.close();
				}
			} catch (IOException e) {
			}
		}
      
    }
    
	public static Properties getGoodsProperty() {
        return goodsProperty;
    }

    public static void setGoodsProperty(Properties goodsProperty) {
        GoodsPropertiesUtil.goodsProperty = goodsProperty;
    }

    public static Properties getThriftProperty() {
        return thriftProperty;
    }

    public static void setThriftProperty(Properties thriftProperty) {
        GoodsPropertiesUtil.thriftProperty = thriftProperty;
    }

    public static Properties getMonitorProperty() {
        return monitorProperty;
    }

    public static void setMonitorProperty(Properties monitorProperty) {
        GoodsPropertiesUtil.monitorProperty = monitorProperty;
    }

}
