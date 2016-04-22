package com.froad.db.ahui.mappers;

import java.util.List;

import com.froad.fft.persistent.entity.Receiver;

public interface ReceiverMapper {

	public List<Receiver> findReceiverByCondition(Receiver receiver);
	
	
	public List<Receiver> queryAllList();
}

