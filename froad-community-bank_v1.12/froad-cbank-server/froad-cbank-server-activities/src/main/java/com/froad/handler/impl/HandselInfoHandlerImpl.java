/**
 * @Title: HandselInfoHandlerImpl.java
 * @Package com.froad.handler.impl
 * @Description: TODO
 * Copyright:2015 F-Road All Rights Reserved   
 * Company:f-road.com.cn
 * 
 * @creater froad-Joker 2015年12月8日
 * @version V1.0
**/

package com.froad.handler.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.HandselInfoMapper;
import com.froad.handler.HandselInfoHandler;
import com.froad.logback.LogCvt;
import com.froad.po.HandselInfo;

 /**
 * @ClassName: HandselInfoHandlerImpl
 * @Description: TODO
 * @author froad-Joker 2015年12月8日
 * @modify froad-Joker 2015年12月8日
 */

public class HandselInfoHandlerImpl implements HandselInfoHandler {

	/**
	 * @Title: addHandselInfo
	 * @Description: TODO
	 * @author: Joker 2015年12月8日
	 * @modify: Joker 2015年12月8日
	 * @param handselInfo
	 * @return
	 * @throws Exception
	 * @see com.froad.handler.HandselInfoHandler#addHandselInfo(com.froad.po.HandselInfo)
	 */

	@Override
	public Long addHandselInfo(HandselInfo handselInfo)
			throws Exception {
		Long result = null; 
		SqlSession sqlSession = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			HandselInfoMapper handselInfoMapper = sqlSession.getMapper(HandselInfoMapper.class);
			if (handselInfoMapper.addHandselInfo(handselInfo) > 0) { // 添加成功
				result = handselInfo.getId(); 
				sqlSession.commit(true); 
			} else { // 添加失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = null; 
			LogCvt.info("添加[addHandselInfo]失败"+e.getMessage());
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			 throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}

	/**
	 * 批量增加 HandselInfo
	 * 
	 * @author: lf 2016年02月25日
	 * */
	@Override
	public Boolean addHandselInfoByBatch(List<HandselInfo> handselInfoList)
			throws Exception {
		
		Boolean result = null; 
		SqlSession sqlSession = null;
		
		try{
			
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			HandselInfoMapper handselInfoMapper = sqlSession.getMapper(HandselInfoMapper.class);
			
			result = handselInfoMapper.addHandselInfoByBatch(handselInfoList);

		} catch (Exception e) { 
			result = false; 
			LogCvt.info("添加[addHandselInfo]失败"+e.getMessage());
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			 throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result;
	}

}
