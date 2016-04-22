package com.froad.thrift;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.thrift.service.MerchantCategoryService;
import com.froad.thrift.service.MerchantTypeService;
import com.froad.thrift.vo.MerchantCategoryVo;
import com.froad.thrift.vo.MerchantTypeVo;

public class MerchantTypeServiceTest {
	




	public static void main(String[] args) {
		try {
//			TSocket transport = new TSocket("10.43.1.122", 15201);
			TSocket transport = new TSocket("127.0.0.1", 15201);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"MerchantTypeService");
			MerchantTypeService.Client service = new MerchantTypeService.Client(mp);

			transport.open();
			
			MerchantTypeVo vo = new MerchantTypeVo();
			vo.setClientId("zzz");
			service.getMerchantType(vo);

//			vo.setName("沙县");
//			vo.setTreePath("");
			
//			service.addMerchantCategory(vo);
//			service.updateMerchantCategory(vo);
//			System.out.println(service.getMerchantCategory(vo));
//			List<Long> listId = new ArrayList<Long>();
//			listId.add(100000000l);
//			listId.add(100000001l);
////			listId.add(100000003l);
//			service.getMerchantTypeVoByMerchantTypeIdList(listId);

		
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




}

