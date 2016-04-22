package com.froad.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;

import com.froad.thrift.service.BankAccessService;
import com.froad.thrift.vo.bankaccess.BankAccessClientListReq;
import com.froad.thrift.vo.bankaccess.BankAccessClientListRes;
import com.froad.thrift.vo.bankaccess.BankAccessClientVo;
import com.froad.util.Checker;

public class BossBankAccessTest {

	public static void main(String[] args) throws TException {
		//本地测试
		//TSocket transprot = new TSocket("127.0.0.1",16001);
		//test2测试
		TSocket transprot = new TSocket("10.43.1.132",16001);
		transprot.open();
		TBinaryProtocol protocol = new TBinaryProtocol(transprot);
		TMultiplexedProtocol multiplexedProtocol = new TMultiplexedProtocol(protocol, BankAccessService.class.getSimpleName());
		BankAccessService.Iface bankAccessService = new BankAccessService.Client(multiplexedProtocol);
		getClientList(bankAccessService);
	}
	
	/**获取客户端集合信息
	 * @throws TException */
	private static void getClientList(BankAccessService.Iface bankAccessService) throws TException {
		BankAccessClientListReq req = new BankAccessClientListReq();
		String type = "2";
		req.setType(type);
		
		BankAccessClientListRes res = bankAccessService.getClientList(req);
		
		if (Checker.isNotEmpty(res)) {
			System.out.println(res.getResultVo().getResultDesc());
		}
		if (res.getClientList() != null && res.getClientList().size() > 0) {
			for (BankAccessClientVo vo : res.getClientList()) {
				System.out.println("BankName" + vo.getBankName() + "   ClientId" + vo.getClientId() +
						"   ClientName" + vo.getClientName());
			}
		}

	}

}
