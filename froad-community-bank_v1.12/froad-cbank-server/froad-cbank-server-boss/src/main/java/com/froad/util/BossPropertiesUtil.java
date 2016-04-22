package com.froad.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.froad.Constants;
import com.froad.logback.LogCvt;

/**
 * boss管理平台用到读取配置文件的工具类
 * @author wangyan
 *
 */
public class BossPropertiesUtil {
    
    private static Properties goodsProperty;//boss管理平台
    private static Properties thriftProperty;
    
    static {
        FileReader fr = null;
		try {
			Properties props = new Properties();
			
			//thrift.properties配置文件名称
			fr = new FileReader(new File(System.getProperty(Constants.CONFIG_PATH)+File.separatorChar+BossConstants.THRIFT_PROPERTIES));
            props.load(fr);
            setThriftProperty(props);
            
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
        BossPropertiesUtil.goodsProperty = goodsProperty;
    }

    public static Properties getThriftProperty() {
        return thriftProperty;
    }

    public static void setThriftProperty(Properties thriftProperty) {
        BossPropertiesUtil.thriftProperty = thriftProperty;
    }

}
