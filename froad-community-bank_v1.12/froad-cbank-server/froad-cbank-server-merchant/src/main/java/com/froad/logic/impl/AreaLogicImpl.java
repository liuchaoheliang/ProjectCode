/*   
 * Copyright © 2008 F-Road All Rights Reserved.
 *  
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */

/**
 * 
 * @Title: AreaLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.AreaMapper;
import com.froad.logback.LogCvt;
import com.froad.logic.AreaLogic;
import com.froad.po.Area;

/**
 * 
 * <p>
 * @Title: AreaLogic.java
 * </p>
 * <p>
 * Description: 描述
 * </p>
 * 
 * @author f-road
 * @version 1.0
 * @created 2015年3月17日
 */
public class AreaLogicImpl implements AreaLogic {

	/**
	 * 根据id获取地区信息
	 * 
	 * @Title: findAreaById
	 * @Description:
	 * @author longyunbo
	 * @param @param id
	 * @param @return
	 * @return
	 * @throws
	 */
	@Override
	public Area findAreaById(Long id) {
		SqlSession sqlSession = null;
		AreaMapper areaMapper = null;
		Area area = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			areaMapper = sqlSession.getMapper(AreaMapper.class);
			area = areaMapper.findAreaById(id);
		} catch (Exception e) {
			// TODO: handle exception
			LogCvt.error("获取地区信息失败:" + e.getMessage(),e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return area;
	}

}