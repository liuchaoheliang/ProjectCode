package com.froad.fft.persistent.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.ClientChannelMapper;
import com.froad.fft.persistent.entity.ClientChannel;

public class ClientChannelMapperImpl implements ClientChannelMapper{

	@Resource
	private ClientChannelMapper clientChannelMapper;
	
	@Override
	public void save(ClientChannel clientChannel) {
		clientChannelMapper.save(clientChannel);
	}

	@Override
	public void saveBatch(List<ClientChannel> list) {
		clientChannelMapper.saveBatch(list);
	}

}
