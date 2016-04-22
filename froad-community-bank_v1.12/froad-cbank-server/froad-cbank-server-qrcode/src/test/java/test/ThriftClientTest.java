package test;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.froad.thrift.service.QrCodeService;
import com.froad.thrift.vo.QrCodeRequestVo;
import com.froad.util.QrCodeUtil;

public class ThriftClientTest {

	public static void main(String[] args) {
		QrCodeUtil util = null;
		
		util = new QrCodeUtil();
		for (int i = 0; i< 1000; i++) {
			System.out.println(i);
			util.generateCheckCode("8888");
		}
		
		
//		generateCode();
	}

	public static void generateCode() {
		TTransport transport = null;
		TProtocol protocol = null;
		TMultiplexedProtocol multiProtocol = null;
		QrCodeService.Client client = null;
		QrCodeRequestVo qrcodeRequestVo = null;
        String keyword = null;
        String clientId = null;
		
        try { 
            // 设置调用的服务地址为本地，端口为 8899 
            transport = new TSocket("10.43.1.9", 15301); 
            transport.open(); 
            
            // 设置传输协议为 TBinaryProtocol 
            protocol = new TBinaryProtocol(transport); 
            multiProtocol = new TMultiplexedProtocol(protocol,"QrCodeService");
            client = new QrCodeService.Client(multiProtocol);
            
            //transport.open();
            
            qrcodeRequestVo = new QrCodeRequestVo();
            keyword = "88888888";
            clientId = "1000";
            qrcodeRequestVo.setKeyword(keyword);
            qrcodeRequestVo.setClientId(clientId);
            
            // 调用服务的 retrieveQrCode 方法
            client.retrieveQrCode(qrcodeRequestVo);
            
//            System.out.println(client.generateWordImage("hello"));
            
            
			System.out.println(client.getName());
			System.out.println(client.getVersion());
			System.out.println(client.aliveSince());
			System.out.println(client.getServiceCount());
			System.out.println(client.getServiceBizMethods());
			System.out.println(client.getBizMethodInvokeInfo("retrieveQrCode"));
			System.out.println(client.getBizMethodsInvokeInfo());

            
            transport.close(); 
        } catch (TTransportException e) { 
            e.printStackTrace(); 
        } catch (TException e) { 
            e.printStackTrace(); 
        } 
	}
}
