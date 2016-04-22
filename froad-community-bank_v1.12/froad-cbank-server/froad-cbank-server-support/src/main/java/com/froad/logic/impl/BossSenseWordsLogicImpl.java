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
 * @Title: BossSenseWordsLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.BossSenseWordsMapper;
import com.froad.db.redis.impl.RedisManager;
import com.froad.logback.LogCvt;
import com.froad.logic.BossSenseWordsLogic;
import com.froad.po.BossSenseWords;
import com.froad.thrift.service.AdPositionService;
import com.froad.thrift.vo.PageVo;
import com.froad.util.RedisKeyUtil;

import com.froad.logic.ProductSenseService;

/**
 * 
 * <p>@Title: BossSenseWordsLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class BossSenseWordsLogicImpl implements BossSenseWordsLogic {


    /**
     * 增加 BossSenseWords
     * @param bossSenseWords
     * @return Long    主键ID
     */
	@Override
	public Long addBossSenseWords(BossSenseWords bossSenseWords) {

		Long result = null; 
		SqlSession sqlSession = null;
		BossSenseWordsMapper bossSenseWordsMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossSenseWordsMapper = sqlSession.getMapper(BossSenseWordsMapper.class);

			if (bossSenseWordsMapper.addBossSenseWords(bossSenseWords) > -1) { 
				result = bossSenseWords.getId(); 
			}
			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
			sqlSession.commit(true); 

			/**********************通知商品模块**********************/
			if(bossSenseWords.getIsEnable()){
			    RedisManager redis = new RedisManager();
			    Set<String> goodHosts = redis.getSet(RedisKeyUtil.cbank_good_ip_port());
			
			    for(String str : goodHosts){
			    	String strArr[] = str.split(":");
			        TSocket transport = new TSocket(strArr[0], Integer.parseInt(strArr[1]));
			        TBinaryProtocol protocol = new TBinaryProtocol(transport);
			        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"ProductService");
			
			        ProductSenseService.Client service = new ProductSenseService.Client(mp);
			        transport.open();
			
			        service.modifySenseWord(1, bossSenseWords.getWord());
			        transport.close();
			    }
			}
		} catch (Exception e) { 
			sqlSession.rollback(true);  
		result = null; 
			LogCvt.error("添加BossSenseWords失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 删除 BossSenseWords
     * @param bossSenseWords
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteBossSenseWords(BossSenseWords bossSenseWords) {

		Boolean result = null; 
		SqlSession sqlSession = null;
		BossSenseWordsMapper bossSenseWordsMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossSenseWordsMapper = sqlSession.getMapper(BossSenseWordsMapper.class);

			bossSenseWordsMapper.deleteBossSenseWords(bossSenseWords); 
			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
			sqlSession.commit(true);

			result = true;

		} catch (Exception e) { 
			sqlSession.rollback(true);  
			result = false; 
			LogCvt.error("删除BossSenseWords失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 修改 BossSenseWords
     * @param bossSenseWords
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateBossSenseWords(BossSenseWords bossSenseWords) {

		Boolean result = true; 
		SqlSession sqlSession = null;
		BossSenseWordsMapper bossSenseWordsMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossSenseWordsMapper = sqlSession.getMapper(BossSenseWordsMapper.class);

			bossSenseWordsMapper.updateBossSenseWords(bossSenseWords); 
			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
			sqlSession.commit(true);

			result = true;

		} catch (Exception e) { 
			sqlSession.rollback(true);  
			result = false; 
			LogCvt.error("删除BossSenseWords失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 查询 BossSenseWords
     * @param bossSenseWords
     * @return List<BossSenseWords>    结果集合 
     */
	@Override
	public List<BossSenseWords> findBossSenseWords(BossSenseWords bossSenseWords) {

		List<BossSenseWords> result = new ArrayList<BossSenseWords>(); 
		SqlSession sqlSession = null;
		BossSenseWordsMapper bossSenseWordsMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossSenseWordsMapper = sqlSession.getMapper(BossSenseWordsMapper.class);

			result = bossSenseWordsMapper.findBossSenseWords(bossSenseWords); 
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
		result = null; 
			LogCvt.error("查询BossSenseWords失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 BossSenseWords
     * @param page
     * @param bossSenseWords
     * @return Page<BossSenseWords>    结果集合 
     */
	@Override
	public Page<BossSenseWords> findBossSenseWordsByPage(Page<BossSenseWords> page, BossSenseWords bossSenseWords) {

		List<BossSenseWords> result = new ArrayList<BossSenseWords>(); 
		SqlSession sqlSession = null;
		BossSenseWordsMapper bossSenseWordsMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossSenseWordsMapper = sqlSession.getMapper(BossSenseWordsMapper.class);

			result = bossSenseWordsMapper.findByPage(page, bossSenseWords); 
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("分页查询BossSenseWords失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}


}