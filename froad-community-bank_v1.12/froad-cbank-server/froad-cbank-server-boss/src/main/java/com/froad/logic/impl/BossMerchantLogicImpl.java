package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.OutletMapper;
import com.froad.logback.LogCvt;
import com.froad.logic.BossMerchantLogic;
import com.froad.po.Outlet;

public class BossMerchantLogicImpl implements BossMerchantLogic{

	@Override
	public List<Outlet> findSubBankOutlet(String clientId, String orgCode) {

		List<Outlet> result = new ArrayList<Outlet>(); 
		SqlSession sqlSession = null;
		OutletMapper outletMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			outletMapper = sqlSession.getMapper(OutletMapper.class);
			result = outletMapper.findSubBankOutlet(clientId,orgCode); 
		} catch (Exception e) { 
			LogCvt.error("查询提货网点(预售用到) 失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}

}
