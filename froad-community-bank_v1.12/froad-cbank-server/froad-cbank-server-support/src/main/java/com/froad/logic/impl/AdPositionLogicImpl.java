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
 * @Title: AdPositionLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.db.mysql.mapper.AdPositionMapper;
import com.froad.db.redis.SupportsRedis;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.AdPositionLogic;
import com.froad.po.AdPosition;

/**
 * 
 * <p>@Title: AdPositionLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class AdPositionLogicImpl implements AdPositionLogic {


    /**
     * 增加 AdPosition
     * @param adPosition
     * @return Long    主键ID
     */
	@Override
	public ResultBean addAdPosition(AdPosition adPosition) {
		ResultBean resultBean=new ResultBean(ResultCode.success,"添加广告位信息成功");
		Long result = null; 
		SqlSession sqlSession = null;
		AdPositionMapper adPositionMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			adPositionMapper = sqlSession.getMapper(AdPositionMapper.class);

			if (adPositionMapper.addAdPosition(adPosition) > -1) { 
				result = adPosition.getId(); 
			}
			
			/**********************操作Redis缓存**********************/
			if(result > 0)
			SupportsRedis.set_cbbank_adpos_client_id_ad_position_id(adPosition);
			sqlSession.commit(true); 
			resultBean= new ResultBean(ResultCode.success,"添加广告位信息成功",result);
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			LogCvt.error("添加AdPosition失败，原因:" + e.getMessage(), e); 
			resultBean= new ResultBean(ResultCode.failed,"添加广告位信息失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		 return resultBean;

	}


    /**
     * 删除 AdPosition
     * @param adPosition
     * @return Boolean    是否成功
     */
	@Override
	public ResultBean deleteAdPosition(AdPosition adPosition) {
		ResultBean resultBean=new ResultBean(ResultCode.success,"删除广告位信息成功");
		Boolean result = null; 
		SqlSession sqlSession = null;
		AdPositionMapper adPositionMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			adPositionMapper = sqlSession.getMapper(AdPositionMapper.class);
			if( adPosition.getId() == null || adPosition.getId() <= 0 ){
				resultBean= new ResultBean(ResultCode.failed,"删除广告位信息失败 - id 为空");
				return resultBean;
			}
			result = adPositionMapper.deleteAdPosition(adPosition);
			if(result) {
				/**********************操作Redis缓存**********************/
				SupportsRedis.set_cbbank_adpos_client_id_ad_position_id(adPosition.getClientId(),adPosition.getId(),"is_enable", "false");
			}
			sqlSession.commit(true);
			
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			result = false; 
			LogCvt.error("删除AdPosition失败，原因:" + e.getMessage(), e); 
			resultBean= new ResultBean(ResultCode.failed,"删除广告位信息失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 

	}


    /**
     * 修改 AdPosition
     * @param adPosition
     * @return Boolean    是否成功
     */
	@Override
	public ResultBean updateAdPosition(AdPosition adPosition) {
		ResultBean resultBean=new ResultBean(ResultCode.success,"修改广告位信息成功");
		Boolean result = null; 
		SqlSession sqlSession = null;
		AdPositionMapper adPositionMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			adPositionMapper = sqlSession.getMapper(AdPositionMapper.class);
			if( adPosition.getId() == null || adPosition.getId() <= 0 ){
				resultBean= new ResultBean(ResultCode.failed,"删除广告位信息失败 - id 为空");
				return resultBean;
			}
			result = adPositionMapper.updateAdPosition(adPosition); 
			
			if(result)
			/**********************操作Redis缓存**********************/
			SupportsRedis.set_cbbank_adpos_client_id_ad_position_id(adPosition);
			sqlSession.commit(true);
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			result = false; 
			LogCvt.error("修改AdPosition失败，原因:" + e.getMessage(), e); 
			resultBean= new ResultBean(ResultCode.failed,"修改广告位信息失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 

	}


    /**
     * 查询 AdPosition
     * @param adPosition
     * @return List<AdPosition>    结果集合 
     */
	@Override
	public List<AdPosition> findAdPosition(AdPosition adPosition) {

		List<AdPosition> result = new ArrayList<AdPosition>(); 
//		AdPosition adp = new AdPosition();
		SqlSession sqlSession = null;
		AdPositionMapper adPositionMapper = null;
		try { 
			/**********************操作Redis缓存**********************/
//			Map<String,String> operatorRedis = SupportRedis.getAll_cbbank_adpos_client_id_ad_position_id(adPosition.getClientId(), adPosition.getId());
//			if(operatorRedis!=null){
//				adp.setDescription(operatorRedis.get("description"));
//				adp.setHeight(Integer.parseInt(operatorRedis.get("height")));
//				adp.setIsEnable(BooleanUtils.toBooleanObject(operatorRedis.get("is_enable")));
//				adp.setName(operatorRedis.get("name"));
//				adp.setPositionPage(operatorRedis.get("position_page"));
//				adp.setPositionPoint(Integer.parseInt(operatorRedis.get("position_point")));
//				adp.setPositionStyle(operatorRedis.get("position_style"));
//				adp.setWidth(Integer.parseInt(operatorRedis.get("width")));
//				result.add(adp);
//			}else{
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				adPositionMapper = sqlSession.getMapper(AdPositionMapper.class);
				result = adPositionMapper.findAdPosition(adPosition); 
				for(AdPosition ado: result){
					SupportsRedis.set_cbbank_adpos_client_id_ad_position_id(ado);
				}
//			}
			
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询AdPosition失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 AdPosition
     * @param page
     * @param adPosition
     * @return Page<AdPosition>    结果集合 
     */
	@Override
	public Page<AdPosition> findAdPositionByPage(Page<AdPosition> page, AdPosition adPosition) {

		List<AdPosition> result = new ArrayList<AdPosition>(); 
		SqlSession sqlSession = null;
		AdPositionMapper adPositionMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			adPositionMapper = sqlSession.getMapper(AdPositionMapper.class);

			result = adPositionMapper.findByPage(page, adPosition); 
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("分页查询AdPosition失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}

	/**
     * 查询 AdPosition
     * @return AdPositionVo
     * 
     * @param clientId
     * @param id
     */
	@Override
	public AdPosition getAdPositionById(String clientId, long id) {
		// TODO Auto-generated method stub
		
		AdPosition adPosition = null;
		SqlSession sqlSession = null;
		AdPositionMapper adPositionMapper = null;
		
		try{
			
			if( clientId != null && !"".equals(clientId) ){
				Map<String,String> operatorRedis = SupportsRedis.getAll_cbbank_adpos_client_id_ad_position_id(clientId, id);
				LogCvt.debug("查询AdPosition 缓存:"+operatorRedis);
				if(operatorRedis!=null){
					adPosition = new AdPosition();
					adPosition.setId(id);
					adPosition.setClientId(clientId);
					adPosition.setDescription(operatorRedis.get("description"));
					adPosition.setHeight(Integer.parseInt(operatorRedis.get("height")));
					adPosition.setIsEnable(BooleanUtils.toBooleanObject(operatorRedis.get("is_enable")));
					adPosition.setName(operatorRedis.get("name"));
					adPosition.setPositionPage(operatorRedis.get("position_page"));
					adPosition.setPositionPoint(Integer.parseInt(operatorRedis.get("position_point")));
					adPosition.setPositionStyle(operatorRedis.get("position_style"));
					adPosition.setWidth(Integer.parseInt(operatorRedis.get("width")));
					return adPosition;
				}
			}
			
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			adPositionMapper = sqlSession.getMapper(AdPositionMapper.class);
			adPosition = adPositionMapper.findAdPositionById(id);
			if( adPosition == null ){
				adPosition = new AdPosition();
			}else{
				SupportsRedis.set_cbbank_adpos_client_id_ad_position_id(adPosition);
			}
			
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询AdPosition失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return adPosition;
	}


}