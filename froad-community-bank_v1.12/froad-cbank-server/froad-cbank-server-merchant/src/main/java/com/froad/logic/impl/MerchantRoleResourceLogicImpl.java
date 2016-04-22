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
 * @Title: MerchantRoleResourceLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;



import java.util.List;

import com.froad.db.mongo.MerchantRoleResourceMongo;
//import com.froad.db.mongo.impl.MongoManager;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.MerchantRoleResourceLogic;
import com.froad.po.MerchantRoleResourceAddRes;
import com.froad.po.Result;
import com.froad.po.mongo.MerchantRoleResource;

/**
 * 
 * <p>@Title: MerchantRoleResourceLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class MerchantRoleResourceLogicImpl implements MerchantRoleResourceLogic {
	private MerchantRoleResourceMongo mongo = new MerchantRoleResourceMongo();
	
    /**
     * 增加 MerchantRoleResource
     * @param merchantRoleResource
     * @return MerchantRoleResourceAddRes
     */
	@Override
	public MerchantRoleResourceAddRes addMerchantRoleResource(MerchantRoleResource merchantRoleResource) {

		MerchantRoleResourceAddRes merchantRoleResourceAddRes = new MerchantRoleResourceAddRes();
//		SqlSession sqlSession = null;
//		MerchantRoleResourceMapper merchantRoleResourceMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
//			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
//			merchantRoleResourceMapper = sqlSession.getMapper(MerchantRoleResourceMapper.class);
//
//			if (merchantRoleResourceMapper.addMerchantRoleResource(merchantRoleResource) > -1) { 
//				result = merchantRoleResource.getMerchantRoleId(); 
//			}
//			sqlSession.commit(true); 

			

			/**********************操作Redis缓存**********************/
			
			/**********************操作Mongodb数据库**********************/
			if(mongo.addMerchantRoleResource(merchantRoleResource) > -1 ){
				Result result = new Result(ResultCode.success.getCode(), ResultCode.success.getMsg());
				merchantRoleResourceAddRes.setResult(result);
				merchantRoleResourceAddRes.setId(merchantRoleResource.get_id());
			}else{
				Result result = new Result(ResultCode.add_merchant_role_resource_mongo_lapse.getCode(), ResultCode.add_merchant_role_resource_mongo_lapse.getMsg());
				merchantRoleResourceAddRes.setResult(result);
			}
//			DBObject dbObject = changeToDBObjectOfAdd(merchantRoleResource);
//			manager.add(dbObject, CB_MERCHANT_ROLE_RESOURCE);
			
					
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true);  
			LogCvt.error("添加MerchantRoleResource失败，原因:" + e.getMessage(), e); 
			Result result = new Result(ResultCode.add_merchant_role_resource_app_lapse);
			merchantRoleResourceAddRes.setResult(result);
		} finally { 
//			if(null != sqlSession)  
//				sqlSession.close();  
		} 
		return merchantRoleResourceAddRes; 

	}


    /**
     * 删除 MerchantRoleResource
     * @param _id (client_id_role_id)
     * @return Result
     */
	@Override
	public Result deleteMerchantRoleResource(String _id) throws FroadServerException,Exception{

		Result result = null; 
//		SqlSession sqlSession = null;
//		MerchantRoleResourceMapper merchantRoleResourceMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
//			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
//			merchantRoleResourceMapper = sqlSession.getMapper(MerchantRoleResourceMapper.class);
//
//			result = merchantRoleResourceMapper.deleteMerchantRoleResource(merchantRoleResource); 
//			sqlSession.commit(true);

			

			/**********************操作Redis缓存**********************/
			
			/**********************操作Mongodb数据库**********************/
			
			int rint = mongo.removeMerchantRoleResource(_id);
			LogCvt.info("受影响行数:" + rint);
//			result = rint > 0 ? true : false;
			if( rint > 0 ){
				result = new Result(ResultCode.success.getCode(), ResultCode.success.getMsg());
			}else{
				result = new Result(ResultCode.del_merchant_role_resource_mongo_lapse.getCode(), ResultCode.del_merchant_role_resource_mongo_lapse.getMsg());
			}
		} catch (FroadServerException e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true); 
//			LogCvt.error("删除MerchantRoleResource失败，原因:" + e.getMessage(), e); 
			result = new Result(ResultCode.del_merchant_role_resource_app_lapse.getCode(), ResultCode.del_merchant_role_resource_app_lapse.getMsg());
			throw e;
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true); 
//			LogCvt.error("删除MerchantRoleResource失败，原因:" + e.getMessage(), e); 
			result = new Result(ResultCode.del_merchant_role_resource_app_lapse.getCode(), ResultCode.del_merchant_role_resource_app_lapse.getMsg());
			throw e;
		} finally { 
//			if(null != sqlSession)  
//				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 修改 MerchantRoleResource
     * @param merchantRoleResource
     * @return Result
     */
	@Override
	public Result updateMerchantRoleResource(MerchantRoleResource merchantRoleResource) throws FroadServerException,Exception{

		Result result = null; 
//		SqlSession sqlSession = null;
//		MerchantRoleResourceMapper merchantRoleResourceMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
//			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
//			merchantRoleResourceMapper = sqlSession.getMapper(MerchantRoleResourceMapper.class);
//
//			result = merchantRoleResourceMapper.updateMerchantRoleResource(merchantRoleResource); 
//			sqlSession.commit(true);

			

			/**********************操作Redis缓存**********************/
			
			/**********************操作Mongodb数据库**********************/
			
			int rint = mongo.updateMerchantRoleResource(merchantRoleResource.get_id(), merchantRoleResource);
			LogCvt.info("受影响行数:" + rint);
//			result = rint > 0 ? true : false;
			if( rint > 0 ){
				result = new Result(ResultCode.success.getCode(), ResultCode.success.getMsg());
			}else{
				result = new Result(ResultCode.upd_merchant_role_resource_mongo_lapse.getCode(), ResultCode.upd_merchant_role_resource_mongo_lapse.getMsg());
			}
		} catch (FroadServerException e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true);
//			LogCvt.error("修改MerchantRoleResource失败，原因:" + e.getMessage(), e); 
			result = new Result(ResultCode.upd_merchant_role_resource_app_lapse.getCode(), ResultCode.upd_merchant_role_resource_app_lapse.getMsg()+e.getMessage());
			throw e;
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true);
//			LogCvt.error("修改MerchantRoleResource失败，原因:" + e.getMessage(), e); 
			result = new Result(ResultCode.upd_merchant_role_resource_app_lapse.getCode(), ResultCode.upd_merchant_role_resource_app_lapse.getMsg()+e.getMessage());
			throw e;
		} finally { 
//			if(null != sqlSession)  
//				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 查询 MerchantRoleResource
     * @param _id (client_id_role_id)
     * @return MerchantRoleResource
     */
	@Override
	public MerchantRoleResource findMerchantRoleResource(String _id) {

		MerchantRoleResource result = null; 
//		SqlSession sqlSession = null;
//		MerchantRoleResourceMapper merchantRoleResourceMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/
			result = mongo.findMerchantRoleResource(_id);
			/**********************操作MySQL数据库**********************/
//			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
//			merchantRoleResourceMapper = sqlSession.getMapper(MerchantRoleResourceMapper.class);
//
//			result = merchantRoleResourceMapper.findMerchantRoleResource(merchantRoleResource); 
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// if(null != sqlSession)  
//					sqlSession.rollback(true);  
			LogCvt.error("查询MerchantRoleResource失败，原因:" + e.getMessage(), e); 
		} finally { 
//			if(null != sqlSession)  
//				sqlSession.close();  
		} 
		return result; 

	}


	@Override
	public List<MerchantRoleResource> getMerchantRoleResourceListByClientId(
			String client_id) {
		// TODO Auto-generated method stub
		List<MerchantRoleResource> result = null; 
		try{
//			1、完全匹配 Pattern.compile("^name$", Pattern.CASE_INSENSITIVE);
//			2、右匹配 Pattern.compile("^.*name$", Pattern.CASE_INSENSITIVE);
//			3、左匹配 Pattern.compile("^name.*$", Pattern.CASE_INSENSITIVE);
//			4、模糊匹配 Pattern.compile("^.*name.*$", Pattern.CASE_INSENSITIVE);
			/**********************操作Mongodb数据库**********************/
			result = mongo.findMerchantRoleResourceListByClientId(client_id);
			
		} catch (Exception e) { 
			LogCvt.error("查询MerchantRoleResource列表失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result;
	}

	/**
     * 查询 List<MerchantRoleResource> (全部)
     * @return List<MerchantRoleResourceVo>
     */
	@Override
	public List<MerchantRoleResource> getMerchantRoleResourceList() {
		// TODO Auto-generated method stub
		List<MerchantRoleResource> result = null; 
		try{
			
			/**********************操作Mongodb数据库**********************/
			result = mongo.findMerchantRoleResource();
			
		} catch (Exception e) { 
			LogCvt.error("查询MerchantRoleResource列表失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result;
	}



//	// 把 MerchantRoleResource 转换成 DBObject 供 update 使用的 set
//	private DBObject changeToDBObjectOfUpdateSet(MerchantRoleResource merchantRoleResource){
//		
//		DBObject dbS = new BasicDBObject();
//		
//		return dbS;
//	}
}