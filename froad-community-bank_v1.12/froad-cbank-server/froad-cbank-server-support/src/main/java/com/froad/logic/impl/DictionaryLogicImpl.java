package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.thrift.TException;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.DictionaryItemMapper;
import com.froad.logback.LogCvt;
import com.froad.logic.DictionaryLogic;
import com.froad.po.Dictionaryitem;

public class DictionaryLogicImpl implements DictionaryLogic{

	
	@Override
	public List<Dictionaryitem> findDictionaryitem(String DicCode,
			String clientId) throws TException {
		SqlSession sqlSession = null;
		DictionaryItemMapper dictionaryitemMapper = null;
		List<Dictionaryitem> items=null;
		try { 

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			dictionaryitemMapper = sqlSession.getMapper(DictionaryItemMapper.class);
		    items=dictionaryitemMapper.findDictionaryitemByDicCode(DicCode,clientId); 
		    if(items==null){
		    	return new ArrayList<Dictionaryitem>(); 
		    }
		} catch (Exception e) { 
			LogCvt.error("查询Dictionaryitem列表失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return items; 
	}


	@Override
	public Dictionaryitem getDictionaryitemById(Long id) throws TException {
		SqlSession sqlSession = null;
		DictionaryItemMapper dictionaryitemMapper = null;
		Dictionaryitem item=null;
		try { 

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			dictionaryitemMapper = sqlSession.getMapper(DictionaryItemMapper.class);
		    item=dictionaryitemMapper.getDictionaryitemById(id); 
		    if(item==null){
		    	return new Dictionaryitem(); 
		    }
		} catch (Exception e) { 
			LogCvt.error("查询Dictionaryitem失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return item; 
	}


}
