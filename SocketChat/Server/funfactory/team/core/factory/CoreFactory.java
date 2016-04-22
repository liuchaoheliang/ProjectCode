package team.core.factory;

import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;

import team.core.beans.conveying.UtilConveyingMsg;
import team.core.constant.CodeFunInsinuate;

public class CoreFactory {

	private static Logger log = Logger.getLogger(CoreFactory.class);
	
	public static UtilConveyingMsg core(UtilConveyingMsg utilConveyingMsg){
		if(utilConveyingMsg == null){
			//服务端发送报文错误
			log.error("客户端发送数据为空");
			return new UtilConveyingMsg("500", null);
		}
		String insinuateInfo = CodeFunInsinuate.insinuate.get(utilConveyingMsg.getCode());
		if(insinuateInfo == null){
			//服务端发送的请求代码错误
			log.error("客户端端发送的请求代码错误 code:" + utilConveyingMsg.getCode());
			return new UtilConveyingMsg("404", null);
		}
		String[] mappingFunction = insinuateInfo.split("\\|");
		try {
			Class<?> clazz = Class.forName(mappingFunction[0]);
			Object objcet = clazz.newInstance();
			insinuateInfo = null;
			return (UtilConveyingMsg)clazz.getMethod(mappingFunction[1],Object.class).invoke(objcet, utilConveyingMsg.getObject());
		} catch (ClassNotFoundException e) {
			log.error("CoreFactory异常",e);
		} catch (InstantiationException e) {
			log.error("CoreFactory异常",e);
		} catch (IllegalAccessException e) {
			log.error("CoreFactory异常",e);
		} catch (IllegalArgumentException e) {
			log.error("CoreFactory异常",e);
		} catch (SecurityException e) {
			log.error("CoreFactory异常",e);
		} catch (InvocationTargetException e) {
			log.error("CoreFactory异常",e);
		} catch (NoSuchMethodException e) {
			log.error("CoreFactory异常",e);
		}
		return new UtilConveyingMsg("500", null);
	}
}
