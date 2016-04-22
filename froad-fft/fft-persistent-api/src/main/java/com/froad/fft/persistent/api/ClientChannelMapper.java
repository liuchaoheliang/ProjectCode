package com.froad.fft.persistent.api;

import java.util.List;

import com.froad.fft.persistent.entity.ClientChannel;


	/**
	 * 类描述：客户端和资金渠道的中间mapper
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: 2014年4月29日 下午4:43:53 
	 */
public interface ClientChannelMapper {

	public void save(ClientChannel clientChannel);
	
	public void saveBatch(List<ClientChannel> list);
}
