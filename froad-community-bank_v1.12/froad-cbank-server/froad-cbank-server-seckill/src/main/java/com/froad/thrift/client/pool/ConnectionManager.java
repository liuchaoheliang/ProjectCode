package com.froad.thrift.client.pool;

import org.apache.thrift.transport.TSocket;

/**
 * 连接池管理
 * @ClassName ConnectionManager
 * @author zxl
 * @date 2015年3月23日 下午7:53:02
 */
//@Aspect
public class ConnectionManager  {
	
	/** 连接提供池 */
	public ConnectionProvider connectionProvider;
	
	/** 保存local对象 
	ThreadLocal<TSocket> socketThreadSafe = new ThreadLocal<TSocket>();
	
	@Before("execution(* com.froad.cbank.coremodule.module..*.*(..)) && @annotation(com.froad.cbank.coremodule.framework.expand.thrift.annotation.Thrift)")
	public void before(){
		socketThreadSafe.set(connectionProvider.getConnection());
	}
	
	@After("execution(* com.froad.cbank.coremodule.module..*.*(..)) && @annotation(com.froad.cbank.coremodule.framework.expand.thrift.annotation.Thrift)")
	public void after(){
		connectionProvider.returnCon(socketThreadSafe.get());
		socketThreadSafe.remove();
	}
	
	public TSocket getSocket(){
		return socketThreadSafe.get();
	}
	*/
	public ConnectionProvider getConnectionProvider() {
		return connectionProvider;
	}

	public void setConnectionProvider(ConnectionProvider connectionProvider) {
		this.connectionProvider = connectionProvider;
	}
	
	public TSocket getTSocket(){
		return connectionProvider.getConnection();
	}
	
	public void returnConn(TSocket t){
		connectionProvider.returnCon(t);
	}
	
	public String getConString(){
		return connectionProvider.getConnectionPoolInfo();
	}
}
