package com.froad.logic.impl;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.froad.logic.QrCodeLogic;
import com.froad.thrift.service.QrCodeService;
import com.froad.thrift.vo.QrCodeRequestVo;
import com.froad.thrift.vo.QrCodeResponseVo;
import com.froad.util.PropertiesUtil;

public class QrCodeLogicImpl implements QrCodeLogic {

	/**
	 * thrift.properties
	 */
	private static final String THRIFT_PROPERTIES_FILE = "thrift";
	
	/**
	 * properties host key
	 */
	private static final String PROPERTIES_HOST_KEY = "thrift.qrcode.host";
	
	/**
	 * properties port key
	 */
	private static final String PROPERTIES_PORT_KEY = "thrift.qrcode.port";
	
	@Override
	public QrCodeResponseVo generateQrCode(QrCodeRequestVo requestVo) {
		TTransport transport = null;
		TProtocol protocol = null;
		TMultiplexedProtocol multiProtocol = null;
		QrCodeService.Client client = null;
		String host = null;
		int port = 0;
		QrCodeResponseVo responseVo = null;
		
        try {
    		host = PropertiesUtil.getProperty(THRIFT_PROPERTIES_FILE, PROPERTIES_HOST_KEY);
    		port = Integer.valueOf(PropertiesUtil.getProperty(THRIFT_PROPERTIES_FILE, PROPERTIES_PORT_KEY));
        	
        	// 设置调用的服务地址，端口
            transport = new TSocket(host, port);
			transport.open();
			
	        // 设置传输协议为 TBinaryProtocol 
	        protocol = new TBinaryProtocol(transport); 
	        multiProtocol = new TMultiplexedProtocol(protocol,"QrCodeService");
	        client = new QrCodeService.Client(multiProtocol);
	        
            // 调用服务的 retrieveQrCode 方法
	        responseVo = client.retrieveQrCode(requestVo);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			transport.close();
		}
		
		return responseVo;
	}

}
