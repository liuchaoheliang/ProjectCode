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
 * @Title: MerchantResourceLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.MerchantResourceMapper;
import com.froad.enums.MerchantResourceType;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.MerchantResourceLogic;
import com.froad.po.MerchantResource;
import com.froad.po.MerchantResourceAddRes;
import com.froad.po.Result;

/**
 * 
 * <p>@Title: MerchantResourceLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class MerchantResourceLogicImpl implements MerchantResourceLogic {


    /**
     * 增加 MerchantResource
     * @param merchantResource
     * @return MerchantResourceAddRes
     */
	@Override
	public MerchantResourceAddRes addMerchantResource(MerchantResource merchantResource) {

		MerchantResourceAddRes merchantResourceAddRes = new MerchantResourceAddRes(); 
		SqlSession sqlSession = null;
		MerchantResourceMapper merchantResourceMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantResourceMapper = sqlSession.getMapper(MerchantResourceMapper.class);

			StringBuilder treePath = new StringBuilder(""); // 获取treePath
			
			Long parentId = merchantResource.getParentId();
			if(parentId == null || parentId == 0) { // 如果上级ID为空或者0 则重置为0
				merchantResource.setParentId(0l);
			} else {
				MerchantResource parentMerchantResource = merchantResourceMapper.findMerchantResourceById(parentId);
				if(null == parentMerchantResource) {
					throw new FroadServerException("上级资源不存在!");
				}
				if(!parentMerchantResource.getIsEnabled()) {
					throw new FroadServerException("上级资源不可用!");
				}
				String parentTreePath = parentMerchantResource.getTreePath();
				if (parentTreePath.startsWith("0,")) {
					parentTreePath = parentTreePath.substring(parentTreePath.indexOf("0,") + 2);
				}
				treePath.append(parentTreePath).append(",");
			}
			
			merchantResource.setTreePath("");
			
			String type = merchantResource.getType();
			if(type.equals(MerchantResourceType.module.getCode()) || type.equals(MerchantResourceType.page.getCode()) ) { // 只有菜单才需要排序
				Integer orderValue = null;
	//			LogCvt.info("把[" + MerchantResourceType.page.getDescribe() + "]ID为: " + srcResourceId + "拖动到[" + MerchantResourceType.page.getDescribe() + "]ID为: " + destResourceId + "之后!!!");
				
				int maxDestOrderVallue = merchantResourceMapper.maxOrderVallueByTreePath(parentId);
				merchantResourceMapper.addOrderValue(maxDestOrderVallue + 1, null, 1); // 把目标资源之后资源的往后挪1
				
				orderValue = maxDestOrderVallue + 1;
				merchantResource.setOrderValue(orderValue); // 把当前资源的排序值设为maxDestOrderVallue+1
			}
			
			Long count = merchantResourceMapper.addMerchantResource(merchantResource) ;
			
			if (count > -1) { 
				MerchantResource upd = new MerchantResource();
				upd.setId(merchantResource.getId());
				upd.setTreePath(treePath.append(merchantResource.getId()).toString());				
				merchantResourceMapper.updateMerchantResource(upd);
				
				
				sqlSession.commit(true); 
				Result result = new Result(ResultCode.success);
				merchantResourceAddRes.setResult(result);
				merchantResourceAddRes.setId(merchantResource.getId());
			}else{
				sqlSession.rollback(true);
				Result result = new Result(ResultCode.add_merchant_resource_db_lapse);
				merchantResourceAddRes.setResult(result);
			}

			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
			
		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			LogCvt.error("添加MerchantResource失败，原因:" + e.getMessage(), e); 
			Result result = new Result(ResultCode.failed, e.getMessage());
			merchantResourceAddRes.setResult(result);
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			LogCvt.error("添加MerchantResource失败，原因:" + e.getMessage(), e); 
			Result result = new Result(ResultCode.add_merchant_resource_app_lapse);
			merchantResourceAddRes.setResult(result);
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return merchantResourceAddRes; 

	}


    /**
     * 删除 MerchantResource
     * @param merchantResource
     * @return Result
     */
	@Override
	public Result deleteMerchantResource(MerchantResource merchantResource) throws FroadServerException,Exception{

		Result result = null; 
		SqlSession sqlSession = null;
		MerchantResourceMapper merchantResourceMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantResourceMapper = sqlSession.getMapper(MerchantResourceMapper.class);

			if( merchantResourceMapper.deleteMerchantResource(merchantResource) ){
				sqlSession.commit(true);
				result = new Result(ResultCode.success.getCode(), ResultCode.success.getMsg());
			} else {
				sqlSession.rollback(true);
				result = new Result(ResultCode.del_merchant_resource_db_lapse.getCode(), ResultCode.del_merchant_resource_db_lapse.getMsg()); 
			}

			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);
			result = new Result(ResultCode.del_merchant_resource_app_lapse.getCode(), e.getMessage()); 
			LogCvt.error("删除MerchantResource失败，原因:" + e.getMessage(), e); 
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);
			result = new Result(ResultCode.del_merchant_resource_app_lapse); 
			LogCvt.error("删除MerchantResource失败，原因:" + e.getMessage(), e); 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 修改 MerchantResource
     * @param merchantResource
     * @return Result
     */
	@Override
	public Result updateMerchantResource(MerchantResource merchantResource) {

		Result result = null; 
		SqlSession sqlSession = null;
		MerchantResourceMapper merchantResourceMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantResourceMapper = sqlSession.getMapper(MerchantResourceMapper.class);

			if( merchantResourceMapper.updateMerchantResource(merchantResource) ){
				sqlSession.commit(true);
				result = new Result(ResultCode.success); 
			} else {
				sqlSession.rollback(true);
				result = new Result(ResultCode.upd_merchant_resource_db_lapse); 
			}

			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
			
		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			result = new Result(ResultCode.upd_merchant_resource_app_lapse.getCode(), e.getMessage()); 
			LogCvt.error("修改MerchantResource失败，原因:" + e.getMessage(), e); 
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			result = new Result(ResultCode.upd_merchant_resource_app_lapse); 
			LogCvt.error("修改MerchantResource失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}

	
	/**    
	 * 移动商户资源至某一个资源之前/之后
	 * @param srcResourceId
	 * @param destResourceId
	 * @return    
	 * @see com.froad.logic.MerchantResourceLogic#moveToBefore(long, long)    
	 */
	
	@Override
	public boolean moveMerchantResourceTo(long srcResourceId, long destResourceId, int action) throws FroadServerException,Exception{
		boolean result = false;
		SqlSession sqlSession = null;
		MerchantResourceMapper merchantResourceMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantResourceMapper = sqlSession.getMapper(MerchantResourceMapper.class);
			
			MerchantResource srcMerchantResource = merchantResourceMapper.findMerchantResourceById(srcResourceId);
			
			MerchantResource destMerchantResource = merchantResourceMapper.findMerchantResourceById(destResourceId);
			if (null == srcMerchantResource) {
				throw new FroadServerException("源资源不存在!");
			}
			if (null == destMerchantResource) {
				throw new FroadServerException("目标资源不存在!");
			}
			if (!srcMerchantResource.getParentId().equals(destMerchantResource.getParentId())) {
				throw new FroadServerException("不同模块之间的资源不能互相拖动!");
			}
			if (!srcMerchantResource.getType().equals(srcMerchantResource.getType())) {
				throw new FroadServerException("不同类型之间的资源不能互相拖动!");
			}
			
			Integer srcMerchantResourceOrerValue = srcMerchantResource.getOrderValue();
			Integer destMerchantResourceOrerValue = destMerchantResource.getOrderValue();
			
			if(null == srcMerchantResourceOrerValue || null == destMerchantResourceOrerValue){
				throw new FroadServerException("数据异常!");				
			}
//			Integer startOrderValue = null, endOrderValue = null;
//			
//			if (srcMerchantResourceOrerValue < destMerchantResourceOrerValue) {
//				startOrderValue = srcMerchantResourceOrerValue;
//				endOrderValue = destMerchantResourceOrerValue;
//			} else {
//				startOrderValue = destMerchantResourceOrerValue;
//				endOrderValue = srcMerchantResourceOrerValue;
//			}
			
			String type = srcMerchantResource.getType(); // 资源类型
			if (0 == action) { // 拖动到某某之前
				if (type.equals(MerchantResourceType.module.getCode())) { // 如果是模块之间的拖动
					LogCvt.info("把[" + MerchantResourceType.module.getDescribe() + "]ID为: " + srcResourceId + "拖动到[" + MerchantResourceType.module.getDescribe() + "]ID为: " + destResourceId + "之前!!!");
					
					int srcCountTreePath = merchantResourceMapper.countByTreePath(srcMerchantResource.getId());
					merchantResourceMapper.addOrderValue(destMerchantResourceOrerValue, null, srcCountTreePath + 1); // 把目标资源之后资源的往后挪srcCountTreePath+1
					
					int inc = 0;// 把源资源挪到当前位置
					List<Long> srcIDs = merchantResourceMapper.findMerchantResourceIdByTreePath(srcMerchantResource.getId());
					if (CollectionUtils.isNotEmpty(srcIDs)) {
						for (Long srcId : srcIDs) {
							inc++;
							MerchantResource merchantResource = new MerchantResource();
							merchantResource.setId(srcId);
							merchantResource.setOrderValue(destMerchantResourceOrerValue + inc);

							merchantResourceMapper.updateMerchantResource(merchantResource);
						}
					}
				} else if (type.equals(MerchantResourceType.page.getCode())) { // 如果是页面之间的拖动
					LogCvt.info("把[" + MerchantResourceType.page.getDescribe() + "]ID为: " + srcResourceId + "拖动到[" + MerchantResourceType.page.getDescribe() + "]ID为: " + destResourceId + "之前!!!");
					
					merchantResourceMapper.addOrderValue(destMerchantResourceOrerValue, null, + 1); // 把目标资源之后资源的往后挪1
					
					MerchantResource merchantResource = new MerchantResource();// 把源资源挪到当前位置
					merchantResource.setId(srcMerchantResource.getId());
					merchantResource.setOrderValue(destMerchantResource.getOrderValue());
					merchantResourceMapper.updateMerchantResource(merchantResource);
				}
				
			} else {// 拖动到某某之后
				if (type.equals(MerchantResourceType.module.getCode())) { // 如果是模块之间的拖动
					LogCvt.info("把[" + MerchantResourceType.module.getDescribe() + "]ID为: " + srcResourceId + "拖动到[" + MerchantResourceType.module.getDescribe() + "]ID为: " + destResourceId + "之后!!!");
					
					int srcCountTreePath = merchantResourceMapper.countByTreePath(srcMerchantResource.getId());
					int maxDestOrderVallue = merchantResourceMapper.maxOrderVallueByTreePath(destMerchantResource.getId());
					merchantResourceMapper.addOrderValue(maxDestOrderVallue + 1, null, srcCountTreePath); // 把目标资源之后资源的往后挪
					
					int inc = 0;// 把源资源挪到当前位置
					List<Long> srcIDs = merchantResourceMapper.findMerchantResourceIdByTreePath(srcMerchantResource.getId());
					if (CollectionUtils.isNotEmpty(srcIDs)) {
						for (Long srcId : srcIDs) {
							inc++;
							MerchantResource merchantResource = new MerchantResource();
							merchantResource.setId(srcId);
							merchantResource.setOrderValue(maxDestOrderVallue + inc);

							merchantResourceMapper.updateMerchantResource(merchantResource);
						}
					}

				} else if (type.equals(MerchantResourceType.page.getCode())) { // 如果是页面之间的拖动
					LogCvt.info("把[" + MerchantResourceType.page.getDescribe() + "]ID为: " + srcResourceId + "拖动到[" + MerchantResourceType.page.getDescribe() + "]ID为: " + destResourceId + "之后!!!");
					
					merchantResourceMapper.addOrderValue(destMerchantResourceOrerValue + 1, null, 1); // 把目标资源之后资源的往后挪1
					
					MerchantResource merchantResource = new MerchantResource();// 把源资源挪到当前位置
					merchantResource.setId(srcMerchantResource.getId());
					merchantResource.setOrderValue(destMerchantResource.getOrderValue() + 1);
					merchantResourceMapper.updateMerchantResource(merchantResource);
				}
			}
			
			
			/**********************操作Mongodb数据库**********************/
			
			/**********************操作Redis缓存**********************/
			
			sqlSession.commit(true); 
			result = true;
		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
//			LogCvt.error("移动商户资源至某一个资源之前 ，原因:" + e.getMessage(), e); 
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
//			LogCvt.error("移动商户资源至某一个资源之前 ，原因:" + e.getMessage(), e); 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}
	/**    
	 * 移动商户资源至某一个资源之前 
	 * @param srcResourceId
	 * @param destResourceId
	 * @return    
	 * @see com.froad.logic.MerchantResourceLogic#moveToBefore(long, long)    
	 */	
	public boolean moveMerchantResourceToBefore(long srcResourceId, long destResourceId) throws FroadServerException,Exception{
		// TODO Auto-generated method stub
		boolean result = false;
		SqlSession sqlSession = null;
		MerchantResourceMapper merchantResourceMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantResourceMapper = sqlSession.getMapper(MerchantResourceMapper.class);

			MerchantResource srcMerchantResource = merchantResourceMapper.findMerchantResourceById(srcResourceId);
			
			MerchantResource destMerchantResource = merchantResourceMapper.findMerchantResourceById(destResourceId);
			if (null == srcMerchantResource) {
				throw new FroadServerException("源资源不存在!");
			}
			if (null == destMerchantResource) {
				throw new FroadServerException("目标资源不存在!");
			}
			if (!srcMerchantResource.getParentId().equals(destMerchantResource.getParentId())) {
				throw new FroadServerException("不同模块之间的资源不能互相拖动!");
			}
			if (!srcMerchantResource.getType().equals(srcMerchantResource.getType())) {
				throw new FroadServerException("不同类型之间的资源不能互相拖动!");
			}
			
			Integer srcMerchantResourceOrerValue = srcMerchantResource.getOrderValue();
			Integer destMerchantResourceOrerValue = destMerchantResource.getOrderValue();
			
			String type = srcMerchantResource.getType(); // 资源类型
			if(type.equals(MerchantResourceType.module.getCode())) { // 如果是模块之间的拖动
				
			}
			

			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
			
			
		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
//			LogCvt.error("移动商户资源至某一个资源之前 ，原因:" + e.getMessage(), e); 
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
//			LogCvt.error("移动商户资源至某一个资源之前 ，原因:" + e.getMessage(), e); 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}


	
	/**    
	 * 移动商户资源至某一个资源之后 
	 * @param srcResourceId
	 * @param destResourceId
	 * @return    
	 * @see com.froad.logic.MerchantResourceLogic#moveToAfter(long, long)    
	 */	
	public boolean moveMerchantResourceToAfter(long srcResourceId, long destResourceId) throws FroadServerException,Exception{
		// TODO Auto-generated method stub
		return false;
	}

    /**
     * 查询 MerchantResource
     * @param merchantResource
     * @return List<MerchantResource>    结果集合 
     */
	@Override
	public List<MerchantResource> findMerchantResource(MerchantResource merchantResource) {

		List<MerchantResource> result = new ArrayList<MerchantResource>(); 
		SqlSession sqlSession = null;
		MerchantResourceMapper merchantResourceMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantResourceMapper = sqlSession.getMapper(MerchantResourceMapper.class);

			result = merchantResourceMapper.findMerchantResource(merchantResource); 
			// sqlSession.commit(true);
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true); 
			LogCvt.error("查询MerchantResource失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 MerchantResource
     * @param page
     * @param merchantResource
     * @return Page<MerchantResource>    结果集合 
     */
	@Override
	public Page<MerchantResource> findMerchantResourceByPage(Page<MerchantResource> page, MerchantResource merchantResource) {

		List<MerchantResource> result = new ArrayList<MerchantResource>(); 
		SqlSession sqlSession = null;
		MerchantResourceMapper merchantResourceMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantResourceMapper = sqlSession.getMapper(MerchantResourceMapper.class);

			result = merchantResourceMapper.findByPage(page, merchantResource); 
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true); 
			LogCvt.error("分页查询MerchantResource失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}


	
	


}