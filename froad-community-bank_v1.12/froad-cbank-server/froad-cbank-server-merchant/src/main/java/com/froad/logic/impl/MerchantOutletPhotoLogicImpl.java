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
 * @Title: MerchantOutletPhotoLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.froad.db.mongo.OutletDetailMongo;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.MerchantOutletPhotoMapper;
import com.froad.db.mysql.mapper.OutletMapper;
import com.froad.db.redis.MerchantOutletPhotoRedis;
import com.froad.enums.ProductAuditState;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.MerchantOutletPhotoLogic;
import com.froad.po.MerchantOutletPhoto;
import com.froad.po.Outlet;
import com.froad.po.Result;

/**
 * 
 * <p>@Title: MerchantOutletPhotoLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class MerchantOutletPhotoLogicImpl implements MerchantOutletPhotoLogic {


    /**
     * 增加 MerchantOutletPhoto
     * @param merchantOutletPhoto
     * @return Long    主键ID
     */
	@Override
	public Long addMerchantOutletPhoto(MerchantOutletPhoto merchantOutletPhoto) throws FroadServerException,Exception{

		Long result = -1l; 
		SqlSession sqlSession = null;
		MerchantOutletPhotoMapper merchantOutletPhotoMapper = null;
		OutletMapper outletMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantOutletPhotoMapper = sqlSession.getMapper(MerchantOutletPhotoMapper.class);
			
			if (merchantOutletPhotoMapper.addMerchantOutletPhoto(merchantOutletPhoto) > -1) { 
				result = merchantOutletPhoto.getId(); 
			}

			/**********************操作Mongodb数据库**********************/
			if(result > 0){ 
				if(merchantOutletPhoto.getIsDefault()) { // 如果是默认的图片才缓存
					LogCvt.debug("添加门店详情表的默认图片");
					OutletDetailMongo outletDetailMongo = new OutletDetailMongo();
					if(StringUtils.isBlank(merchantOutletPhoto.getOutletId())
							|| StringUtils.isBlank(merchantOutletPhoto.getMedium())){
						throw new FroadServerException("添加门店详情表的默认图片mongodb数据失败!outletId/medium不能为空!");
					}
					boolean flag = outletDetailMongo.updateOutletDetailDefaultImage(merchantOutletPhoto.getOutletId(), merchantOutletPhoto.getThumbnail()); // 默认图片存小图
					if(!flag){
						throw new FroadServerException("添加门店详情表的默认图片mongodb数据失败!");
					}
					
					
					/**********************操作Redis缓存**********************/
					/* 设置缓存 */
					// 如果是默认的图片才缓存
					LogCvt.debug("添加门店详情表的默认图片缓存");
					outletMapper = sqlSession.getMapper(OutletMapper.class);
					Outlet outlet = outletMapper.findOutletByOutletId(merchantOutletPhoto.getOutletId());
					if(outlet != null ){
						if(StringUtils.isBlank(outlet.getClientId())){
							throw new FroadServerException("添加门店详情表的默认图片redis数据失败!客户端ID不能为空");
						}
						boolean flag2 = MerchantOutletPhotoRedis.set_cbbank_merchant_outlet_image_client_id_merchant_id_outlet_id(outlet.getClientId(), merchantOutletPhoto);
						if(!flag2){
							throw new FroadServerException("添加门店详情表的默认图片redis数据失败!");
						}
					}
				}
				sqlSession.commit(true); 
			} else {
				sqlSession.rollback(true); 
			}
			result = merchantOutletPhoto.getId(); 
		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);
			result = null; 
//			LogCvt.error("添加MerchantOutletPhoto失败，原因:" + e.getMessage(), e); 
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);
			result = null; 
//			LogCvt.error("添加MerchantOutletPhoto失败，原因:" + e.getMessage(), e); 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}

	/**
     * 批量增加 MerchantOutletPhoto
     * @param list<merchantOutletPhoto>
     * @return list<Result>    结果集
     * 
     * @param merchantOutletPhotoList
     */
	@Override
	public List<Result> addMerchantOutletPhotoByBatch(
			List<MerchantOutletPhoto> merchantOutletPhotoList) throws FroadServerException,Exception{
		// TODO Auto-generated method stub
		List<Result> resultList = new ArrayList<Result>();
		Long id = null;
		for( MerchantOutletPhoto merchantOutletPhoto : merchantOutletPhotoList ){
			id = this.addMerchantOutletPhoto(merchantOutletPhoto);
			Result result = new Result();
			if( id != null ){
				result.setResultCode(ResultCode.success.getCode());
			}else{
				result.setResultCode(ResultCode.failed.getCode());
			}
			result.setResultDesc(merchantOutletPhoto.getTitle());
			resultList.add(result);
		}
		return resultList;
	}

	/**
	 * 以集合为准,保存门店相册
	 * @Title: saveMerchantOutletPhoto 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantOutletPhotoList
	 * @return
	 * @throws FroadServerException, Exception
	 * @return Boolean    返回类型 
	 * @throws
	 */
	public Boolean saveMerchantOutletPhoto(List<MerchantOutletPhoto> merchantOutletPhotoList) throws FroadServerException, Exception {

		Boolean result = null; 
		SqlSession sqlSession = null;
		MerchantOutletPhotoMapper merchantOutletPhotoMapper = null;
		OutletMapper outletMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantOutletPhotoMapper = sqlSession.getMapper(MerchantOutletPhotoMapper.class);
			
			outletMapper = sqlSession.getMapper(OutletMapper.class);
			
			MerchantOutletPhoto merchantOutletPhoto = merchantOutletPhotoList.get(0);
			
			Outlet outlet = outletMapper.findOutletByOutletId(merchantOutletPhoto.getOutletId());
			if(null == outlet) {
				throw new FroadServerException("门店不存在");
			}
			//原因：门店审核通过后、门店被禁用就不能修改相册.
			if(ProductAuditState.passAudit.getCode().equals(outlet.getAuditState()) && (!outlet.getIsEnable())) {
				throw new FroadServerException("门店已经被禁用");
			}
			String clientId = outlet.getClientId();
			String merchantId = outlet.getMerchantId();
			String outletId = merchantOutletPhoto.getOutletId();
			result = merchantOutletPhotoMapper.deleteMerchantOutletPhotoByOutletId(outletId); // 先清除门店下所有的图片
			
			
//			result = merchantOutletPhotoMapper.deleteMerchantOutletPhoto(merchantOutletPhoto); 
			
			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
			/* 设置缓存 */
			if( result) {// 如果是默认的图片才删除缓存
				if(StringUtils.isNotEmpty(clientId) && StringUtils.isNotEmpty(merchantId) && StringUtils.isNotEmpty(outletId))
					MerchantOutletPhotoRedis.del_cbbank_merchant_outlet_image_client_id_merchant_id_outlet_id(clientId, merchantId, outletId); // 删除默认图片缓存
			}
			
			for (MerchantOutletPhoto merchantOutletPhoto2 : merchantOutletPhotoList) {
				this.addMerchantOutletPhoto2(merchantOutletPhoto2,merchantOutletPhotoMapper,sqlSession);
			}
			sqlSession.commit(true); 
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
	
	
	public void addMerchantOutletPhoto2(MerchantOutletPhoto merchantOutletPhoto,MerchantOutletPhotoMapper merchantOutletPhotoMapper,SqlSession sqlSession) throws FroadServerException,Exception{

		Long result = -1l; 
		OutletMapper outletMapper = null;
		try { 
			if (merchantOutletPhotoMapper.addMerchantOutletPhoto(merchantOutletPhoto) > -1) { 
				result = merchantOutletPhoto.getId(); 
			}
			/**********************操作Mongodb数据库**********************/
			if(result > 0){ 
				if(merchantOutletPhoto.getIsDefault()) { // 如果是默认的图片才缓存
					LogCvt.debug("添加门店详情表的默认图片");
					OutletDetailMongo outletDetailMongo = new OutletDetailMongo();
					if(StringUtils.isBlank(merchantOutletPhoto.getOutletId())
							|| StringUtils.isBlank(merchantOutletPhoto.getMedium())){
						throw new FroadServerException("添加门店详情表的默认图片mongodb数据失败!outletId/medium不能为空!");
					}
					boolean flag = outletDetailMongo.updateOutletDetailDefaultImage(merchantOutletPhoto.getOutletId(), merchantOutletPhoto.getThumbnail()); // 默认图片存小图
					if(!flag){
						throw new FroadServerException("添加门店详情表的默认图片mongodb数据失败!");
					}
					
					
					/**********************操作Redis缓存**********************/
					/* 设置缓存 */
					// 如果是默认的图片才缓存
					LogCvt.debug("添加门店详情表的默认图片缓存");
					outletMapper = sqlSession.getMapper(OutletMapper.class);
					Outlet outlet = outletMapper.findOutletByOutletId(merchantOutletPhoto.getOutletId());
					if(outlet != null ){
						if(StringUtils.isBlank(outlet.getClientId())){
							throw new FroadServerException("添加门店详情表的默认图片redis数据失败!客户端ID不能为空");
						}
						boolean flag2 = MerchantOutletPhotoRedis.set_cbbank_merchant_outlet_image_client_id_merchant_id_outlet_id(outlet.getClientId(), merchantOutletPhoto);
						if(!flag2){
							throw new FroadServerException("添加门店详情表的默认图片redis数据失败!");
						}
					}
				}
			} 
		} catch (FroadServerException e) { 
			throw e;
		} catch (Exception e) { 
			throw e;
		} 
	}
	
	
    /**
     * 删除 MerchantOutletPhoto
     * @param merchantOutletPhoto
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteMerchantOutletPhoto(MerchantOutletPhoto merchantOutletPhoto)throws FroadServerException,Exception{

		Boolean result = null; 
		SqlSession sqlSession = null;
		MerchantOutletPhotoMapper merchantOutletPhotoMapper = null;
		OutletMapper outletMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantOutletPhotoMapper = sqlSession.getMapper(MerchantOutletPhotoMapper.class);
			
			outletMapper = sqlSession.getMapper(OutletMapper.class);
			Outlet outlet = outletMapper.findOutletByOutletId(merchantOutletPhoto.getOutletId());

			result = merchantOutletPhotoMapper.deleteMerchantOutletPhoto(merchantOutletPhoto); 
			
			

			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
			/* 设置缓存 */
			if( result) {// 如果是默认的图片才删除缓存
				if(merchantOutletPhoto.getIsDefault()) {
					if(StringUtils.isBlank(outlet.getClientId())){
						throw new FroadServerException("删除门店详情表的默认图片时客户端ID不能为空!");
					}
//					merchantOutletPhoto = merchantOutletPhotoMapper.findMerchantOutletPhotoById(merchantOutletPhoto.getId());
					Boolean redisResult = MerchantOutletPhotoRedis.del_cbbank_merchant_outlet_image_client_id_merchant_id_outlet_id(outlet.getClientId(), merchantOutletPhoto);
					if(!redisResult){
						//因裁剪图片需求能够删除历史图片的要求，改为 删除redis失败后记录日志不回滚流程 20151202
						LogCvt.error("删除门店详情表的默认图片redis数据失败!");
					}
				}
				sqlSession.commit(true); 
			} else {
				sqlSession.rollback(true); 
			}
		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			result = false; 
//			LogCvt.error("删除MerchantOutletPhoto失败，原因:" + e.getMessage(), e);
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			result = false; 
//			LogCvt.error("删除MerchantOutletPhoto失败，原因:" + e.getMessage(), e);
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 修改 MerchantOutletPhoto
     * @param merchantOutletPhoto
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateMerchantOutletPhoto(MerchantOutletPhoto merchantOutletPhoto) throws FroadServerException,Exception{

		Boolean result = null; 
		SqlSession sqlSession = null;
		MerchantOutletPhotoMapper merchantOutletPhotoMapper = null;
		OutletMapper outletMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantOutletPhotoMapper = sqlSession.getMapper(MerchantOutletPhotoMapper.class);
			
			result = merchantOutletPhotoMapper.updateMerchantOutletPhoto(merchantOutletPhoto); 
			
			merchantOutletPhoto = merchantOutletPhotoMapper.findMerchantOutletPhotoById(merchantOutletPhoto.getId());

			/**********************操作Mongodb数据库**********************/
			if (merchantOutletPhoto != null && merchantOutletPhoto.getIsDefault() && result) { // 如果是默认的图片才缓存
				LogCvt.debug("修改门店详情表的默认图片");
				OutletDetailMongo outletDetailMongo = new OutletDetailMongo();
				result = outletDetailMongo.updateOutletDetailDefaultImage(merchantOutletPhoto.getOutletId(), merchantOutletPhoto.getMedium());
			
				
				/**********************操作Redis缓存**********************/
				/* 设置缓存 */
				// 如果是默认的图片才删除缓存
				if(result){
					outletMapper = sqlSession.getMapper(OutletMapper.class);
					Outlet outlet = outletMapper.findOutletByOutletId(merchantOutletPhoto.getOutletId());
					if( outlet != null ){
						if(StringUtils.isBlank(outlet.getClientId())){
							throw new FroadServerException("修改门店详情表的默认图片时客户端ID不能为空!");
						}
						result = MerchantOutletPhotoRedis.set_cbbank_merchant_outlet_image_client_id_merchant_id_outlet_id(outlet.getClientId(), merchantOutletPhoto);
						if(!result){
							throw new FroadServerException("修改门店详情表的默认图片redis数据失败!");
						}
					}
				}
				sqlSession.commit(true); 
			} else {
				sqlSession.rollback(true); 
			}
		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			result = false; 
//			LogCvt.error("修改MerchantOutletPhoto失败，原因:" + e.getMessage(), e);
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			result = false; 
//			LogCvt.error("修改MerchantOutletPhoto失败，原因:" + e.getMessage(), e);
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}

	/**
	 * 支持多银行客户端和boss查询
	 * @Title: findDefaultMerchantOutletPhoto 
	 * @author vania
	 * @version 1.0
	 * @see: 返回门店默认的图片
	 * @param client_id
	 * @param merchant_id
	 * @param outlet_id
	 * @return
	 * @return MerchantOutletPhoto    返回类型 
	 * @throws
	 */
	public MerchantOutletPhoto findDefaultMerchantOutletPhoto(String client_id, String merchant_id, String outlet_id) {
		SqlSession sqlSession = null;
		MerchantOutletPhoto merchantOutletPhoto = null;
		try {
			/********************** 操作Redis缓存 **********************/
			Map<String, String> hash = MerchantOutletPhotoRedis.getALL_cbbank_merchant_outlet_image_client_id_merchant_id_outlet_id(client_id, merchant_id, outlet_id);
			if (null != hash) {
				merchantOutletPhoto = new MerchantOutletPhoto();
				
				merchantOutletPhoto.setTitle(hash.get("title"));
				merchantOutletPhoto.setSource(hash.get("soucrce"));
				merchantOutletPhoto.setLarge(hash.get("large"));
				merchantOutletPhoto.setMedium(hash.get("medium"));
				merchantOutletPhoto.setThumbnail(hash.get("thumbnail"));
			} else { // 缓存不存在则查询数据库
				/********************** 操作MySQL数据库 **********************/
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				MerchantOutletPhotoMapper merchantOutletPhotoMapper = sqlSession.getMapper(MerchantOutletPhotoMapper.class);

				merchantOutletPhoto = new MerchantOutletPhoto();
				merchantOutletPhoto.setIsDefault(true);
				
				List<MerchantOutletPhoto> ps = merchantOutletPhotoMapper.findMerchantOutletPhoto(merchantOutletPhoto);
				if (CollectionUtils.isNotEmpty(ps)) {
					merchantOutletPhoto = ps.get(0);
					/* 重新设置缓存 */
					MerchantOutletPhotoRedis.set_cbbank_merchant_outlet_image_client_id_merchant_id_outlet_id(client_id, merchantOutletPhoto);
				}
			}
		} catch (Exception e) {
//			if(null != sqlSession)  
//				sqlSession.rollback(true); 
			LogCvt.error("查询默认MerchantOutletPhoto失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return merchantOutletPhoto;
	}

    /**
     * 支持多银行客户端和boss查询
     * 查询 MerchantOutletPhoto
     * @param merchantOutletPhoto
     * @return List<MerchantOutletPhoto>    结果集合 
     */
	@Override
	public List<MerchantOutletPhoto> findMerchantOutletPhoto(MerchantOutletPhoto merchantOutletPhoto) {

		List<MerchantOutletPhoto> result = new ArrayList<MerchantOutletPhoto>(); 
		SqlSession sqlSession = null;
		MerchantOutletPhotoMapper merchantOutletPhotoMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantOutletPhotoMapper = sqlSession.getMapper(MerchantOutletPhotoMapper.class);

			result = merchantOutletPhotoMapper.findMerchantOutletPhoto(merchantOutletPhoto); 
			// sqlSession.commit(true);
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true); 
			LogCvt.error("查询MerchantOutletPhoto失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 支持多银行客户端和boss查询
     * 分页查询 MerchantOutletPhoto
     * @param page
     * @param merchantOutletPhoto
     * @return Page<MerchantOutletPhoto>    结果集合 
     */
	@Override
	public Page<MerchantOutletPhoto> findMerchantOutletPhotoByPage(Page<MerchantOutletPhoto> page, MerchantOutletPhoto merchantOutletPhoto) {

		List<MerchantOutletPhoto> result = new ArrayList<MerchantOutletPhoto>(); 
		SqlSession sqlSession = null;
		MerchantOutletPhotoMapper merchantOutletPhotoMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantOutletPhotoMapper = sqlSession.getMapper(MerchantOutletPhotoMapper.class);

			result = merchantOutletPhotoMapper.findByPage(page, merchantOutletPhoto); 
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true); 
			LogCvt.error("分页查询MerchantOutletPhoto失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}



}