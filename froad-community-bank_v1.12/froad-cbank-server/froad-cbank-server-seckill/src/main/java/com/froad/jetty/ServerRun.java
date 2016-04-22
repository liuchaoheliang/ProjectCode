package com.froad.jetty;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;

import com.froad.util.PropertiesUtil;

/**
 * 多接口服务
 * @ClassName: ServerRun
 * @Description: Jetty服务主进程
 * @author wangzhangxu
 * @date 2015年4月15日 上午10:14:35
 * @version v1.0
 *
 */
public class ServerRun extends BaseJettyServer{

	private static final int SERVER_PORT = PropertiesUtil.port;
	
	public void run(){
		try {
			Server server = new Server();
			String jettyHome = "./";
			
			// 加载jetty.xml配置文件(jetty.xml来自Jetty官方，不做任何修改)
			InputStream fileStream = new FileInputStream(new File(jettyHome + "config/etc/jetty.xml"));
			XmlConfiguration config = new XmlConfiguration(fileStream);
			// 无侵入式修改jetty.xml中默认的端口
			config.getProperties().put("jetty.port", String.valueOf(SERVER_PORT));
		    config.configure(server);
		    
			WebAppContext context = new WebAppContext();
			context.setDefaultsDescriptor(jettyHome + "config/etc/webdefault.xml");
		    
			context.setContextPath("/");
		    context.setDescriptor(jettyHome + "webapp/WEB-INF/web.xml");
		    context.setResourceBase(jettyHome + "webapp");
		    context.setParentLoaderPriority(true);
		    server.setHandler(context);
		    
		    server.start();
		    System.out.println("the server is started and is listening at " + SERVER_PORT + "...");
		    server.join();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void reinitialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
}
