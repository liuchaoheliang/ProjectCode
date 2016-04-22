package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSession;

import com.froad.db.mongo.BankUserResourceMongo;
import com.froad.db.mongo.bean.BankUserResource;
import com.froad.db.mongo.bean.ResourcesInfo;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.BankResourceMapper;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.BankResourceLogic;
import com.froad.po.BankResource;

/**
 * 
 * <p>@Title: BankResourceLogic.java</p>
 * <p>Description: 描述 </p> 银行资源表Logic实现类
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年3月17日
 */
public class BankResourceLogicImpl implements BankResourceLogic {


    /**
     * 增加 BankResource
     * @param bankResource
     * @return Long    主键ID
     */
	@Override
	public Long addBankResource(BankResource bankResource)  throws FroadServerException, Exception{

		SqlSession sqlSession = null;
		BankResourceMapper bankResourceMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bankResourceMapper = sqlSession.getMapper(BankResourceMapper.class);

			//设置是否有效
			bankResource.setStatus(true);
			//设置是否删除
			bankResource.setIsDelete(false);
			bankResourceMapper.addBankResource(bankResource); 
			sqlSession.commit(true);
			
		}catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return bankResource.getId(); 

	}


    /**
     * 删除 BankResource
     * @param bankResource
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteBankResource(long id)  throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		BankResourceMapper bankResourceMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bankResourceMapper = sqlSession.getMapper(BankResourceMapper.class);

			result = bankResourceMapper.deleteBankResource(id);
			// 删除子资源信息
			result = bankResourceMapper.deleteSubResource(id);
			
			/**********************操作Mongo**********************/
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("resources.resource_id", id);
			List<BankUserResource> bankUserResources = BankUserResourceMongo.findByCondition(map);
			if(CollectionUtils.isNotEmpty(bankUserResources)){
				for(BankUserResource b : bankUserResources){
					String[] _ids = b.getId().split("_");
					result=BankUserResourceMongo.removeResource(_ids[0], Long.parseLong(_ids[1]), id);
				}
			}
			
			// 删除子资源信息
			Map<String, Object> subMap = new HashMap<String, Object>();
			map.put("resources.parent_resource_id", id);
			List<BankUserResource> subBankUserResource = BankUserResourceMongo.findByCondition(subMap);
			if(CollectionUtils.isNotEmpty(subBankUserResource)){
				for(BankUserResource b : subBankUserResource){
					String[] _ids = b.getId().split("_");
					List<ResourcesInfo> subResources = b.getResources();
					if(CollectionUtils.isNotEmpty(subResources)){
						for(ResourcesInfo subR : subResources){
							if(subR.getParentResourceId() == id){
								result=BankUserResourceMongo.removeResource(_ids[0], Long.parseLong(_ids[1]), subR.getResourceId());
							}
						}
					}
				}
			}
			
			
			if(result){
				sqlSession.commit(true);
			}
			
		}catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} catch (Exception e) { 
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
     * 修改 BankResource
     * @param bankResource
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateBankResource(BankResource bankResource)  throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		BankResourceMapper bankResourceMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bankResourceMapper = sqlSession.getMapper(BankResourceMapper.class);

			result = bankResourceMapper.updateBankResource(bankResource); 
			
			//操作成功，先提交mysq数据，后续可查最新数据set到缓存中
			if(result){
				sqlSession.commit(true);
			}
			
			/**********************操作Mongo**********************/
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("resources.resource_id", bankResource.getId());
			List<BankUserResource> bankUserResources = BankUserResourceMongo.findByCondition(map);
			if(CollectionUtils.isNotEmpty(bankUserResources)){
				for(BankUserResource b : bankUserResources){
					String[] _ids = b.getId().split("_");
					result=BankUserResourceMongo.updateResource(_ids[0], Long.parseLong(_ids[1]), bankResource);
				}
			}
			
			
		}catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} catch (Exception e) { 
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
     * 查询 BankResource
     * @param bankResource
     * @return List<BankResource>    结果集合 
     */
	@Override
	public List<BankResource> findBankResource(BankResource bankResource) {

		List<BankResource> result = new ArrayList<BankResource>(); 
		SqlSession sqlSession = null;
		BankResourceMapper bankResourceMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bankResourceMapper = sqlSession.getMapper(BankResourceMapper.class);

			result = bankResourceMapper.findBankResource(bankResource); 
		} catch (Exception e) { 
			LogCvt.error("查询BankResource失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 BankResource
     * @param page
     * @param bankResource
     * @return List<BankResource>    结果集合 
     */
	@Override
	public Page<BankResource> findBankResourceByPage(Page<BankResource> page, BankResource bankResource) {

		List<BankResource> result = new ArrayList<BankResource>(); 
		SqlSession sqlSession = null;
		BankResourceMapper bankResourceMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bankResourceMapper = sqlSession.getMapper(BankResourceMapper.class);

			result = bankResourceMapper.findByPage(page, bankResource); 
			page.setResultsContent(result);
		} catch (Exception e) { 
			LogCvt.error("分页查询BankResource失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}
	
}