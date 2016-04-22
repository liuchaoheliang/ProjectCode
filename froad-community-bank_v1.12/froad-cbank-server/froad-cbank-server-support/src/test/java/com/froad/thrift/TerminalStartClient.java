package com.froad.thrift;


import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.thrift.service.TerminalStartService;
import com.froad.thrift.vo.TerminalStartVo;

public class TerminalStartClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			TSocket transport = new TSocket("localhost", 15701);
			
			TBinaryProtocol protocol = new TBinaryProtocol(transport);

			TMultiplexedProtocol mp1 = new TMultiplexedProtocol(protocol,"TerminalStartService");
			TerminalStartService.Client service1 = new TerminalStartService.Client(mp1);
			
			transport.open();

			TerminalStartVo vo = service1.getTerminalStart("anhui", "1", "2");
			System.err.println(vo.getImageId());
			System.err.println(vo.getImagePath());
			
			
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
