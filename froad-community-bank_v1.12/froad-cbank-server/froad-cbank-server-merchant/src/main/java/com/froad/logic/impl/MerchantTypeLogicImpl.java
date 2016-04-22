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
 * @Title: MerchantTypeLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.MerchantTypeMapper;
import com.froad.db.redis.MerchantTypeRedis;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.MerchantTypeLogic;
import com.froad.po.MerchantType;

/**
 * 
 * <p>@Title: MerchantTypeLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class MerchantTypeLogicImpl implements MerchantTypeLogic {


    /**
     * 增加 MerchantType
     * @param merchantType
     * @return Long    主键ID
     */
	@Override
	public Long addMerchantType(MerchantType merchantType) throws FroadServerException,Exception{

		Long result = -1l; 
		SqlSession sqlSession = null;
		MerchantTypeMapper merchantTypeMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantTypeMapper = sqlSession.getMapper(MerchantTypeMapper.class);
			
			if (merchantTypeMapper.addMerchantType(merchantType) > -1) {
				result = merchantType.getId();
			}

			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
			boolean flag =  false;
			if(result > 0){
				/* 设置缓存 */
				flag = MerchantTypeRedis.set_cbbank_merchant_type_merchant_type_id(merchantType);
				if(!flag){
					throw new FroadServerException("添加MerchantType缓存数据失败!");
				}
				
				
				sqlSession.commit(true); 
			} else {
				sqlSession.rollback(true); 
			} 
			result = merchantType.getId();
		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);
			result = null; 
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);
			result = null; 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 删除 MerchantType
     * @param merchantType
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteMerchantType(MerchantType merchantType) throws FroadServerException,Exception{

		Boolean result = null; 
		SqlSession sqlSession = null;
		MerchantTypeMapper merchantTypeMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantTypeMapper = sqlSession.getMapper(MerchantTypeMapper.class);

			result = merchantTypeMapper.deleteMerchantType(merchantType); 

			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
			if(result){
				if(merchantType.getId() <= 0){
					throw new FroadServerException("删除MerchantType分类ID不能为0!");
				}
				/* 删除缓存 */
				result = MerchantTypeRedis.del_cbbank_merchant_type_merchant_type_id(merchantType.getClientId());
				if(!result){
					throw new FroadServerException("删除MerchantType缓存数据失败!");
				}
				
				sqlSession.commit(true); 
			} else {
				sqlSession.rollback(true); 
			}
			
		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			result = false; 
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			result = false; 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 修改 MerchantType
     * @param merchantType
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateMerchantType(MerchantType merchantType) throws FroadServerException,Exception{

		Boolean result = null; 
		SqlSession sqlSession = null;
		MerchantTypeMapper merchantTypeMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantTypeMapper = sqlSession.getMapper(MerchantTypeMapper.class);

			result = merchantTypeMapper.updateMerchantType(merchantType); 

			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
			if(result){
				
				if(merchantType.getId() <= 0){
					throw new FroadServerException("修改MerchantType分类ID不能为0!");
				}
				merchantType = merchantTypeMapper.findMerchantTypeById(merchantType.getId(),merchantType.getClientId());
				/* 设置缓存 */
				result = MerchantTypeRedis.set_cbbank_merchant_type_merchant_type_id(merchantType);
				if(!result){
					throw new FroadServerException("修改MerchantType缓存数据失败!");
				}
				
				
				
				sqlSession.commit(true); 
			} else {
				sqlSession.rollback(true); 
			}
		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);
			result = null; 
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);
			result = null; 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}

	/**
	 * 根据id查询
	 * @param id
	 * @param clientId
	 * @return    
	 * @see com.froad.logic.MerchantTypeLogic#findMerchantTypeById(java.lang.Long)
	 */
	@Override
	public MerchantType findMerchantTypeById(Long id,String clientId) {
		MerchantType mt = null;
		String name = MerchantTypeRedis.get_cbbank_merchant_type_merchant_type_id(clientId,id.toString());
		if (StringUtils.isNotBlank(name)) {
			LogCvt.debug("商户类型缓存不为空,直接查询缓存");
			mt = JSON.parseObject(name,MerchantType.class);
		} else {
			SqlSession sqlSession = null;
			MerchantTypeMapper merchantTypeMapper = null;
			try {
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				merchantTypeMapper = sqlSession.getMapper(MerchantTypeMapper.class);

				mt = merchantTypeMapper.findMerchantTypeById(id,clientId);
				if (null != mt) {
					LogCvt.debug("商户类型缓存为空,重新设置缓存");
					MerchantTypeRedis.set_cbbank_merchant_type_merchant_type_id(mt);
				}
			} catch (Exception e) {
				LogCvt.error("查询MerchantType失败，原因:" + e.getMessage(), e);
			} finally {
				if (null != sqlSession)
					sqlSession.close();
			}
		}

		return mt;
	}

    /**
     * 查询 MerchantType
     * @param merchantType
     * @return List<MerchantType>    结果集合 
     */
	@Override
	public List<MerchantType> findMerchantType(MerchantType merchantType) {

		List<MerchantType> result = new ArrayList<MerchantType>(); 
		SqlSession sqlSession = null;
		MerchantTypeMapper merchantTypeMapper = null;
		try { 
			/**********************操作Redis缓存**********************/
			Map<String, String> map  = MerchantTypeRedis.getAll_cbbank_merchant_type_merchant_type_id(merchantType.getClientId());
			if(null != map && !map.isEmpty()) {
				LogCvt.debug("商户类型缓存不为空,直接查询缓存");
				Set<Map.Entry<String ,String >> set= map.entrySet();
				for (Map.Entry<String ,String > kv : set) {
					result.add(JSON.parseObject(kv.getValue(),MerchantType.class));
				}
			} else {
				LogCvt.debug("商户类型缓存为空,查询数据库");
				/**********************操作MySQL数据库**********************/
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				merchantTypeMapper = sqlSession.getMapper(MerchantTypeMapper.class);
	
				result = merchantTypeMapper.findMerchantType(merchantType); 
				if(result.size() > 0){
					/* 设置缓存 */
					LogCvt.debug("商户类型缓存为空,重新设置缓存");
					MerchantTypeRedis.set_cbbank_merchant_type_merchant_type_id(result.toArray(new MerchantType[]{}));
				}else{
					LogCvt.debug("商户类型缓存为空,查询result为空无需设置缓存");
				}
			}
			
			
		} catch (Exception e) { 
			LogCvt.error("查询MerchantType失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 MerchantType
     * @param page
     * @param merchantType
     * @return Page<MerchantType>    结果集合 
     */
	@Override
	public Page<MerchantType> findMerchantTypeByPage(Page<MerchantType> page, MerchantType merchantType) {

		List<MerchantType> result = new ArrayList<MerchantType>(); 
		SqlSession sqlSession = null;
		MerchantTypeMapper merchantTypeMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantTypeMapper = sqlSession.getMapper(MerchantTypeMapper.class);

			result = merchantTypeMapper.findByPage(page, merchantType); 
			page.setResultsContent(result);
		} catch (Exception e) { 
//			if(null != sqlSession)  
			LogCvt.error("分页查询MerchantType失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}


	@Override
	public List<MerchantType> findMerchantTypeByMerchantTypeIdList(String clientId,
			List<Long> merchantTypeIdList) {

		List<MerchantType> result = new ArrayList<MerchantType>(); 
		SqlSession sqlSession = null;
		MerchantTypeMapper merchantTypeMapper = null;
		try { 
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				merchantTypeMapper = sqlSession.getMapper(MerchantTypeMapper.class);
				result = merchantTypeMapper.findMerchantTypeByMerchantTypeIdList(clientId,merchantTypeIdList);
			
		} catch (Exception e) { 
			LogCvt.error("查询MerchantType失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


}
