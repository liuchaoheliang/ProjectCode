package com.froad.service;

import java.util.List;

import com.froad.logback.LogCvt;

/**
 *  消息处理抽象类
 * @author zjz
 *
 */
public abstract class AbstractProcessService implements ProcessService {

	@Override
	public void processMsg(List<String> msgs) {
		// TODO Auto-generated method stub
		int index = 1;
		for(String msg : msgs){
			if( index % 2 == 0)
				processMsg(msg);
			index ++;
		}
	}

}
