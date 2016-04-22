package com.froad.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.froad.Constants;
import com.froad.logback.LogCvt;

 /**
  * @ClassName: ActivePropertiesUtil
  * @Description: 配置文件读取
  * @author froad-shenshaocheng 2015年11月11日
  * @modify froad-shenshaocheng 2015年11月11日
 */
public class ActivePropertiesUtil {
    
    private static Properties activeProperty;//
    private static Properties thriftProperty;
    private static Properties monitorProperty;
    private static Properties activeProperties;

	static {
        FileReader fr = null;
		try {
			Properties props = new Properties();
			// thrift.properties配置文件名称
			fr = new FileReader(new File(System.getProperty(Constants.CONFIG_PATH)+File.separatorChar+ActiveConstants.THRIFT_PROPERTIES));
            props.load(fr);
            setActiveProperties(props);
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
        return activeProperty;
    }

    public static void setGoodsProperty(Properties goodsProperty) {
        ActivePropertiesUtil.activeProperty = goodsProperty;
    }

    public static Properties getThriftProperty() {
        return thriftProperty;
    }

    public static void setThriftProperty(Properties thriftProperty) {
        ActivePropertiesUtil.thriftProperty = thriftProperty;
    }

    public static Properties getMonitorProperty() {
        return monitorProperty;
    }

    public static void setMonitorProperty(Properties monitorProperty) {
        ActivePropertiesUtil.monitorProperty = monitorProperty;
    }
    
    public static Properties getActiveProperties() {
		return activeProperties;
	}

	public static void setActiveProperties(Properties activeProperties) {
		ActivePropertiesUtil.activeProperties = activeProperties;
	}

}
