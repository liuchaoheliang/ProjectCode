package com.froad.singleton;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.mappers.MerchantTypeMapper;
import com.froad.po.MerchantType;

public class MerchantTypeSingleton {
	private static volatile List<MerchantType> instance = null;
	
	private MerchantTypeSingleton(){
	}
	
	/**
	 * get merchant type information
	 * 
	 * @param session
	 * @return
	 */
	public static List<MerchantType> getInstance(SqlSession session, String clientId){
		MerchantTypeMapper mapper = null;
		
		if (null == instance){
			synchronized (MerchantTypeSingleton.class){
				if (null == instance){
					mapper = session.getMapper(MerchantTypeMapper.class);
					instance = mapper.findAllTypeInfo(clientId);
				}
			}
		}
		return instance;
	}
	
	/**
	 * destroy instance
	 */
	public static void destroyInstance(){
		if (null != instance){
			instance.clear();
			instance = null;
		}
	}
}
