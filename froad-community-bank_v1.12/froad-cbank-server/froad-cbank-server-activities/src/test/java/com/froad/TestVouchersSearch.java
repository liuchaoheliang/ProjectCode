package com.froad;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;

import com.froad.thrift.service.VouchersRuleInfoService;
import com.froad.thrift.service.VouchersSearchService;

public class TestVouchersSearch {
	public static void main(String[] args) throws TTransportException {
		/***********************************************/
		TSocket transport = new TSocket("localhost", 16501);
		TBinaryProtocol protocol = new TBinaryProtocol(transport);

		TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,
				"VouchersRuleInfoService");
		VouchersRuleInfoService.Client service = new VouchersRuleInfoService.Client(
				mp);

		TMultiplexedProtocol checkmp = new TMultiplexedProtocol(protocol,
				"VouchersSearchService");
		VouchersSearchService.Client serClient = new VouchersSearchService.Client(
				checkmp);
	}
}
