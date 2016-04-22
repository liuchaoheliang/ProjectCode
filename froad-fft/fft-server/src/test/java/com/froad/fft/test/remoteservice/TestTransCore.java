package com.froad.fft.test.remoteservice;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.fft.api.service.TransExportService;
import com.froad.fft.bean.Result;
import com.froad.fft.bean.trans.TransDto;
import com.froad.fft.dto.TransQueryDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.enums.trans.TransCreateSource;
import com.froad.fft.enums.trans.TransPayChannel;
import com.froad.fft.enums.trans.TransPayMethod;
import com.froad.fft.enums.trans.TransType;
import com.froad.fft.service.TransCoreService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:context/servlet/applicationContext-mapper-remote.xml",
		"classpath:context/servlet/applicationContext-service-remote.xml",
		"classpath:context/applicationContext.xml",
		"classpath:context/applicationContext-expand.xml"
		})
public class TestTransCore {

	@Resource
	private TransExportService transExportService;
	
	@Resource
	private TransCoreService transCoreService;
	
	@Test
	public void createTrans(){
		TransDto transDto=new TransDto();
		transDto.setClientAccessType(ClientAccessType.chongqing);
		transDto.setClientVersion(ClientVersion.version_1_0);
		transDto.setCreateSource(TransCreateSource.pc);
//		transDto.setFftPoints("1000.1");
//		transDto.setBankPoints("10");
		transDto.setFilmMobile("15512344321");
		transDto.setMemberCode(50000037167l);
		transDto.setMobile("15512344321");
		transDto.setPayChannel(TransPayChannel.internetBank);
		transDto.setPayMethod(TransPayMethod.internate_bank);
		Map<Long, Integer> productMap=new HashMap<Long, Integer>();
		productMap.put(26l, 3);
		transDto.setProductMap(productMap);
		transDto.setTransType(TransType.presell);
		transDto.setDeliveryId(16l);
		transDto.setDeliveryName("付龙");
		Result result=transExportService.createTrans(transDto);
		String sn=result.getMsg();
		System.out.println("创建订单结果："+sn);
		result=transExportService.doPay(ClientAccessType.chongqing, ClientVersion.version_1_0, sn);
		System.out.println("支付结果："+result.getMsg());
	}
	
	@Test
	public void doPay(){
		String sn="140429145248112";
		Result result=transExportService.doPay(ClientAccessType.chongqing, ClientVersion.version_1_0, sn);
		System.out.println("结果："+result.getMsg());
	}
	
	@Test
    public void selectById(){
    	Long transId=25l;
    	TransQueryDto trans=transExportService.findTransById(ClientAccessType.chongqing, ClientVersion.version_1_0, transId);
    	System.out.println(trans.getPayMethod());
    }
	
	@Test
	public void doRefund(){
		String sn="140418173747139";
		String reason="预售商品不成团，请求退款（测试）";
		Result result=transCoreService.doRefund(sn, reason);
		System.out.println("退款结果："+result.getMsg());
	}
}
