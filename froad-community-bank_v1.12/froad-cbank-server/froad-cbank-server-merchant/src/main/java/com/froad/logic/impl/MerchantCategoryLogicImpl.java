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
 * @Title: MerchantCategoryLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;
import com.froad.db.mongo.OutletDetailMongo;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.AreaMapper;
import com.froad.db.mysql.mapper.MerchantCategoryMapper;
import com.froad.db.redis.MerchantCategoryRedis;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.MerchantCategoryLogic;
import com.froad.po.Area;
import com.froad.po.MerchantCategory;

/**
 * 
 * <p>@Title: MerchantCategoryLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class MerchantCategoryLogicImpl implements MerchantCategoryLogic {


    /**
     * 增加 MerchantCategory
     * @param merchantCategory
     * @return Long    主键ID
     */
	@Override
	public Long addMerchantCategory(MerchantCategory merchantCategory) throws FroadServerException,Exception{

		Long result = -1l; 
		SqlSession sqlSession = null;
		MerchantCategoryMapper merchantCategoryMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantCategoryMapper = sqlSession.getMapper(MerchantCategoryMapper.class);

			StringBuilder treePath = new StringBuilder(""); // 获取treePath
			Long parentId = merchantCategory.getParentId();
			if(parentId == null){ // 如果上级ID为空 则重置为0
				merchantCategory.setParentId(0l);
//				merchantCategory.setTreePath("");
			} else {
				MerchantCategory parentMerchantCategory = merchantCategoryMapper.findMerchantCategoryById(parentId);
				if(null == parentMerchantCategory) {
					throw new FroadServerException("上级分类不存在!");
				}
				if(parentMerchantCategory.getIsDelete()) {
					throw new FroadServerException("上级分类不可用!");
				}
				treePath.append(parentMerchantCategory.getTreePath()).append(",");
			}
			
			merchantCategory.setTreePath("");
			result = merchantCategoryMapper.addMerchantCategory(merchantCategory);
			

//			String treePath = merchantCategory.getTreePath(); // 获取treePath
//			if(StringUtils.isBlank(treePath)){ // 如果上级ID为TreePath为自己
//				merchantCategory.setTreePath(String.valueOf(result));
//			} else {
//				merchantCategory.setTreePath(treePath + "," + String.valueOf(result));
//			}
			
			if(result > -1){
				
				MerchantCategory upd = new MerchantCategory(); 
				upd.setId(merchantCategory.getId());
				upd.setTreePath(treePath.append(merchantCategory.getId()).toString());
				merchantCategoryMapper.updateMerchantCategory(upd); 
				
				/**********************操作Mongodb数据库**********************/
	
				/**********************操作Redis缓存**********************/
				merchantCategory = merchantCategoryMapper.findMerchantCategoryById(merchantCategory.getId());
				if(merchantCategory == null){
					throw new FroadServerException("设置MerchantCategory缓存数据查询不到实体对象!");
				}
				/* 设置商户分类信息缓存 */
				boolean flag = MerchantCategoryRedis.set_cbbank_merchant_category_detail_client_id_category_id(merchantCategory);
				if(!flag){
					sqlSession.rollback(true); 
					throw new FroadServerException("设置MerchantCategory缓存数据失败!");
				}
				
				sqlSession.commit(true); 
			} else {
				sqlSession.rollback(true); 
			}
			result = merchantCategory.getId();
		} catch (FroadServerException e) {
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			result = null; 
//			LogCvt.error("添加MerchantCategory失败，原因:" + e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			result = null; 
//			LogCvt.error("添加MerchantCategory失败，原因:" + e.getMessage(), e);
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 删除 MerchantCategory
     * @param merchantCategory
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteMerchantCategory(MerchantCategory merchantCategory)throws FroadServerException,Exception{

		Boolean result = null; 
		SqlSession sqlSession = null;
		MerchantCategoryMapper merchantCategoryMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantCategoryMapper = sqlSession.getMapper(MerchantCategoryMapper.class);

			result = merchantCategoryMapper.deleteMerchantCategory(merchantCategory); 

			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
			if(result){
				if(StringUtils.isBlank(merchantCategory.getClientId())){
					throw new FroadServerException("删除MerchantCategory缓存数据时客户端ID不能为空!");
				}
				
				if(merchantCategory.getId() <= 0){
					throw new FroadServerException("删除MerchantCategory缓存数据时ID不能为空!");
				}
				/* 删除商户分类信息缓存 */
				result  = MerchantCategoryRedis.del_cbbank_merchant_category_detail_client_id_category_id(merchantCategory.getClientId(),merchantCategory.getId());
				if(!result){
					throw new FroadServerException("删除MerchantCategory缓存数据失败!");
				}
				
				sqlSession.commit(true); 
			} else {
				sqlSession.rollback(true); 
			}
		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			result = false; 
//			LogCvt.error("删除MerchantCategory失败，原因:" + e.getMessage(), e); 
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			result = false; 
//			LogCvt.error("删除MerchantCategory失败，原因:" + e.getMessage(), e); 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 修改 MerchantCategory
     * @param merchantCategory
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateMerchantCategory(MerchantCategory merchantCategory) throws FroadServerException,Exception{

		Boolean result = null; 
		SqlSession sqlSession = null;
		MerchantCategoryMapper merchantCategoryMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantCategoryMapper = sqlSession.getMapper(MerchantCategoryMapper.class);

			result = merchantCategoryMapper.updateMerchantCategory(merchantCategory); 

			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
			if(result){
				/* 设置商户分类信息缓存 */
				merchantCategory = merchantCategoryMapper.findMerchantCategoryById(merchantCategory.getId()); // 重新查一次
				if(null == merchantCategory){
					throw new FroadServerException("修改MerchantCategory缓存数据失败!查询不到具体实体对象!");
				}
				result = MerchantCategoryRedis.set_cbbank_merchant_category_detail_client_id_category_id(merchantCategory);
				if(!result){
					throw new FroadServerException("修改MerchantCategory缓存数据失败!");
				}
				
//			result = true; 
				
				sqlSession.commit(true); 
			} else {
				sqlSession.rollback(true); 
			}
		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			result = false; 
//			LogCvt.error("修改MerchantCategory失败，原因:" + e.getMessage(), e); 
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			result = false; 
//			LogCvt.error("修改MerchantCategory失败，原因:" + e.getMessage(), e); 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}

	/**
	 * 查询分类
	 * @Title: findMerchantCategoryById 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param id
	 * @return
	 * @return MerchantCategory    返回类型 
	 * @throws
	 */
	public MerchantCategory findMerchantCategoryById(String clientId, Long id) {
		MerchantCategory merchantCategory = null;
		Map<String,String> map = MerchantCategoryRedis.get_cbbank_merchant_category_detail_client_id_category_id(clientId, id);
		if(null != map && !map.isEmpty()) {
			LogCvt.debug("商户分类缓存不为空,直接查询缓存");
//			hash.put("name", ObjectUtils.toString(merchantCategory.getName(), ""));
//			hash.put("tree_path", ObjectUtils.toString(merchantCategory.getTreePath(),""));
//			hash.put("parent_id", ObjectUtils.toString(merchantCategory.getParentId(), ""));
			
			merchantCategory = new MerchantCategory();
			merchantCategory.setName(map.get("name"));
			merchantCategory.setTreePath("tree_path");
			if(NumberUtils.isNumber(map.get("parent_id")))
				merchantCategory.setParentId(Long.parseLong(map.get("parent_id")));
		} else {
			SqlSession sqlSession = null;
			MerchantCategoryMapper merchantCategoryMapper = null;
			try { 
				/**********************操作Redis缓存**********************/

				/**********************操作Mongodb数据库**********************/

				/**********************操作MySQL数据库**********************/
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				merchantCategoryMapper = sqlSession.getMapper(MerchantCategoryMapper.class);

				merchantCategory = merchantCategoryMapper.findMerchantCategoryById(id); 
				if(null != merchantCategory) {
					LogCvt.debug("商户分类缓存为空,重新设置缓存");
					MerchantCategoryRedis.set_cbbank_merchant_category_detail_client_id_category_id(merchantCategory);
				}
				// sqlSession.commit(true);
			} catch (Exception e) { 
//				if(null != sqlSession)  
//					sqlSession.rollback(true);
				LogCvt.error("查询MerchantCategory失败，原因:" + e.getMessage(), e); 
			} finally { 
				if(null != sqlSession)  
					sqlSession.close();  
			} 
		}
		return merchantCategory;
	}
	
    /**
     * 支持多银行客户端和boss查询
     * 查询 MerchantCategory
     * @param merchantCategory
     * @return List<MerchantCategory>    结果集合 
     */
	@Override
	public List<MerchantCategory> findMerchantCategory(MerchantCategory merchantCategory) {

		List<MerchantCategory> result = new ArrayList<MerchantCategory>(); 
		SqlSession sqlSession = null;
		MerchantCategoryMapper merchantCategoryMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantCategoryMapper = sqlSession.getMapper(MerchantCategoryMapper.class);

			result = merchantCategoryMapper.findMerchantCategory(merchantCategory); 
			// sqlSession.commit(true);
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true);
			LogCvt.error("查询MerchantCategory失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 支持多银行客户端和boss查询
     * 分页查询 MerchantCategory
     * @param page
     * @param merchantCategory
     * @return Page    结果集合 
     */
	@Override
	public Page<MerchantCategory> findMerchantCategoryByPage(Page<MerchantCategory> page, MerchantCategory merchantCategory) {

		List<MerchantCategory> result = new ArrayList<MerchantCategory>(); 
		SqlSession sqlSession = null;
		MerchantCategoryMapper merchantCategoryMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantCategoryMapper = sqlSession.getMapper(MerchantCategoryMapper.class);

			result = merchantCategoryMapper.findByPage(page, merchantCategory); 
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true);  
			LogCvt.error("分页查询MerchantCategory失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}


	@Override
	public List<MerchantCategory> findMerchantCategoryByCategoryIdList(String clientId, List<Long> categoryIdList) {

		List<MerchantCategory> result = new ArrayList<MerchantCategory>(); 
		SqlSession sqlSession = null;
		MerchantCategoryMapper merchantCategoryMapper = null;
		try { 


			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantCategoryMapper = sqlSession.getMapper(MerchantCategoryMapper.class);
			
			result = merchantCategoryMapper.findMerchantCategoryByCategoryIdList(clientId,categoryIdList);
			
//			for (long l : categoryIdList) {
//				result.add(merchantCategoryMapper.findMerchantCategoryByCategoryIdList(clientId,l));
//			}

			 
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true);
			LogCvt.error("查询MerchantCategory失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}

	
	@Override
	public List<MerchantCategory> findMerchantCategoryByH5(MerchantCategory merchantCategory) throws FroadServerException,Exception {
		List<MerchantCategory> returnResult = new ArrayList<MerchantCategory>();
		List<MerchantCategory> queryResult = new ArrayList<MerchantCategory>(); 
		SqlSession sqlSession = null;
		MerchantCategoryMapper merchantCategoryMapper = null;
		AreaMapper areaMapper = null;
		MerchantCategory firstMC = null;
		MerchantCategory mcTemp = null;
		List<MerchantCategory> resultTemp = null;
		sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
		merchantCategoryMapper = sqlSession.getMapper(MerchantCategoryMapper.class);
		areaMapper = sqlSession.getMapper(AreaMapper.class);
		OutletDetailMongo odm =new OutletDetailMongo();
		//市级
		Long areaParentId = 0L;
		try {
			boolean nextFlag = false;
			
			if(merchantCategory.getAreaId()==null){
				merchantCategory.setAreaId(0l);
			}else{
				Area area = areaMapper.findAreaById(merchantCategory.getAreaId());
				if(area!=null){
		              //最后的节点
		                int level = 0;
		                if(area.getTreePath()!=null){
		                    String[] areas = area.getTreePath().split(",");
		                    level = areas.length;
		                }
		                if(level<3){//三级代表区
		                	areaParentId = merchantCategory.getAreaId();
		                	
		                } 
		            }
			}
			
			//通过父级id查询分类列表
			merchantCategory.setIsDelete(false);
			queryResult = merchantCategoryMapper.findMerchantCategory(merchantCategory);
			//到mongo中查询是否存在门店
			for (int i = 0; i < queryResult.size(); i++) {
				firstMC = queryResult.get(i);
				firstMC.setAreaId(merchantCategory.getAreaId());
				mcTemp = new MerchantCategory();
				mcTemp.setClientId(merchantCategory.getClientId());
				mcTemp.setTreePath(String.valueOf(firstMC.getId()));
				mcTemp.setIsDelete(false);
				
				LogCvt.debug("序号=" + (i+1) + "   父id=" + firstMC.getParentId() + "   分类id=" + firstMC.getId());
				
				//判断是否有缓存，如果有则直接判断缓存内容。
				String hasFlag = MerchantCategoryRedis.get_cbbank_merchant_category_h5_client_id_category_id(merchantCategory.getClientId(), firstMC.getId(),merchantCategory.getAreaId());
				//当redis不存在值时，在进入mongo进行查询
				if(hasFlag==null){
					LogCvt.debug("分类id:"+firstMC.getId()+"【不存在】Redis!");
					//获取下级商户分类 
					resultTemp = merchantCategoryMapper.findMerchantCategory(mcTemp);
					
					if(resultTemp!=null){
						LogCvt.debug("分类id="+firstMC.getId()+"的下级分类统计："+resultTemp.size());
						//如果是一级分类，会将自己也查出来
						
						Long[] categoryIds = new Long[resultTemp.size()];
						
						for (int j = 0; j < resultTemp.size(); j++) {
							//去除一级分类重复问题
							if(firstMC.getId().longValue()==resultTemp.get(j).getId().longValue()){
								categoryIds[j] = 0l;
							}else{
								categoryIds[j] = resultTemp.get(j).getId();
							}	
						}
						
						//根据分类id获取门店数据统计
						if(areaParentId!=null && areaParentId>0){
							nextFlag = odm.getOutletDetailCategoryInfo(merchantCategory.getClientId(), 0l,areaParentId,categoryIds);
						}else{
							nextFlag = odm.getOutletDetailCategoryInfo(merchantCategory.getClientId(), merchantCategory.getAreaId(),0l,categoryIds);
						}
						
						firstMC.setNextFlag(nextFlag);
						MerchantCategoryRedis.set_cbbank_merchant_category_h5_client_id_category_id(firstMC, String.valueOf(nextFlag));
					}else{
						LogCvt.debug("分类id="+firstMC.getId()+"的下级分类统计：0");
						firstMC.setNextFlag(false);
					}
				}else{
					LogCvt.debug("分类id:"+firstMC.getId()+"存在Redis,直接获取!");
					firstMC.setNextFlag(Boolean.parseBoolean(hasFlag));
				}
				
				//非一级分类时，如果该分类没有门店则不返给前端！ nextFlag=true则有下级分类及门店
				if(!nextFlag && firstMC.getParentId().longValue()>0){
					//检查自身级别是否存在门店
					if(areaParentId!=null && areaParentId>0){
						if(odm.getOutletDetailCategoryInfo(firstMC.getClientId(), 0l,areaParentId,new Long[]{firstMC.getId()})){
							returnResult.add(firstMC);
						}
					}else{
						if(odm.getOutletDetailCategoryInfo(firstMC.getClientId(), firstMC.getAreaId(),0l,new Long[]{firstMC.getId()})){
							returnResult.add(firstMC);
						}
					}
					
				}else{
					returnResult.add(firstMC);
				}
			}
		} catch (Exception e) {
			throw e;
		}finally{
			if(null != sqlSession)  
				sqlSession.close();  
		}

		return returnResult;
	}
	
}