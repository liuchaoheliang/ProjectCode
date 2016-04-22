package com.froad;

import java.io.File;

import com.froad.config.XmlService;
import com.froad.logback.LogCvt;
import com.froad.pool.ServicePool;
import com.froad.util.MemberUtil;
import com.froad.util.PropertiesUtil;
import com.froad.util.XStreamHandle;

/**
 * Hello world!
 *
 */
public class TransferMain {
    public static void main( String[] args ){
    	PropertiesUtil.load();
    	MemberUtil.load();
    	LogCvt.info("启动服务,加载配置文件");
    	File xml = new File(System.getProperty(Constants.CONFIG_PATH)+File.separatorChar+"transfer.xml");
        ServicePool.loadService(XStreamHandle.toBean(xml, XmlService.class));
    }
}
