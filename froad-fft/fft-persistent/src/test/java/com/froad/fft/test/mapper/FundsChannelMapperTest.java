package com.froad.fft.test.mapper;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.fft.persistent.api.impl.FundsChannelMapperImpl;
import com.froad.fft.persistent.common.enums.PayChannel;
import com.froad.fft.persistent.entity.FundsChannel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/applicationContext.xml","classpath:context/applicationContext-remote.xml"}) 
public class FundsChannelMapperTest {
	
	@Resource
	private FundsChannelMapperImpl fundsChannelMapperImpl;

	@Test
	public void save(){
		FundsChannel fundsChannel=new FundsChannel();
		fundsChannel.setChannelType(PayChannel.alipay);
		fundsChannel.setPayOrg("700");
		fundsChannel.setFullName("支付宝");
		fundsChannel.setShortName("支付宝");
		fundsChannel.setUpdateTime(new Date());
		fundsChannel.setCreateTime(new Date());
		fundsChannelMapperImpl.saveFundsChannel(fundsChannel);
	}
}
