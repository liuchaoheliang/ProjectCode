package com.froad.cbank.coremodule.normal.boss.common.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * 连接Thrift服务的客户端
 * 
 * @author TH
 *
 */
public class ThriftClient {

	
	private TTransport transport = null;
	private TProtocol protocol = null;
	private TMultiplexedProtocol multiProtocol = null;
	
	/**
	 * 打开服务连接
	 * 
	 * @param ip 服务ip
	 * @param port 服务端口
	 * @param srvName 服务名
	 * @throws TTransportException
	 */
	public void open(String ip, int port, String srvName, int timeout) throws TTransportException {

        transport = new TSocket(ip, port,timeout); 
        transport.open(); 
        
        // 设置传输协议为 TBinaryProtocol 
        protocol = new TBinaryProtocol(transport); 
        multiProtocol = new TMultiplexedProtocol(protocol, srvName);
        
//        TProtocol protocol = new TCompactProtocol(transport);
	}
	

	/**
	 * 关闭服务连接
	 */
	public void close() {
		if (transport != null) {
			transport.close();
			
			transport = null;
			protocol = null;
			multiProtocol = null;
		}
	}
	
	/**
	 * 获取连接协议
	 * @return 连接协议
	 */
	public TProtocol getProtocol() {
		if (multiProtocol != null)
			return multiProtocol;
		else
			return protocol;
	}
	
}
