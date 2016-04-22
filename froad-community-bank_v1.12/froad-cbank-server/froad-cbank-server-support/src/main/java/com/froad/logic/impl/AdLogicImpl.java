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
 * @Title: AdLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.db.mysql.mapper.AdMapper;
import com.froad.db.mysql.mapper.AdPositionMapper;
import com.froad.db.redis.SupportsRedis;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.AdLogic;
import com.froad.po.Ad;
import com.froad.po.AdPosition;

/**
 * 
 * <p>@Title: AdLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class AdLogicImpl implements AdLogic {


    /**
     * 增加 Ad
     * @param ad
     * @return Long    主键ID
     */
	@Override
	public ResultBean addAd(Ad ad) {
		ResultBean resultBean=new ResultBean(ResultCode.success,"添加广告信息成功");
		Long result = null; 
		SqlSession sqlSession = null;
		AdMapper adMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			adMapper = sqlSession.getMapper(AdMapper.class);

			if (adMapper.addAd(ad) > -1) { 
				result = ad.getId(); 
			}
			sqlSession.commit(true); 
			resultBean= new ResultBean(ResultCode.success,"添加广告信息成功",result);
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			LogCvt.error("添加Ad失败，原因:" + e.getMessage(), e); 
			resultBean= new ResultBean(ResultCode.failed,"添加广告信息失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 

	}


    /**
     * 删除 Ad
     * @param ad
     * @return Boolean    是否成功
     */
	@Override
	public ResultBean deleteAd(Ad ad) {
		ResultBean resultBean=new ResultBean(ResultCode.success,"删除广告信息成功");
		SqlSession sqlSession = null;
		AdMapper adMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			adMapper = sqlSession.getMapper(AdMapper.class);
			if( ad.getId() == null || ad.getId() <= 0 ){
				resultBean= new ResultBean(ResultCode.failed,"删除广告信息失败 - id 为空");
				return resultBean;
			}
			adMapper.deleteAd(ad); 
			sqlSession.commit(true);
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			LogCvt.error("删除Ad失败，原因:" + e.getMessage(), e);
			resultBean= new ResultBean(ResultCode.failed,"删除广告信息失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 

	}


    /**
     * 修改 Ad
     * @param ad
     * @return Boolean    是否成功
     */
	@Override
	public ResultBean updateAd(Ad ad) {
		ResultBean resultBean=new ResultBean(ResultCode.success,"修改广告信息成功");
		SqlSession sqlSession = null;
		AdMapper adMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			adMapper = sqlSession.getMapper(AdMapper.class);
			if( ad.getId() == null || ad.getId() <= 0 ){
				resultBean= new ResultBean(ResultCode.failed,"修改广告信息失败 - id 为空");
				return resultBean;
			}
			adMapper.updateAd(ad); 
			sqlSession.commit(true);
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			LogCvt.error("修改Ad失败，原因:" + e.getMessage(), e); 
			resultBean= new ResultBean(ResultCode.failed,"修改广告信息失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 

	}


    /**
     * 查询 Ad
     * @param ad
     * @return List<Ad>    结果集合 
     */
	@Override
	public List<Ad> findAd(Ad ad,AdPosition adPosition) {

		List<Ad> result = new ArrayList<Ad>(); 
		SqlSession sqlSession = null;
		AdMapper adMapper = null;
		AdPositionMapper adPositionMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			adMapper = sqlSession.getMapper(AdMapper.class);
			
			//根据客户端ID+页面标识，查询出其下对应的所有广告
			if(adPosition!=null){
				adPositionMapper = sqlSession.getMapper(AdPositionMapper.class);
				//如果传入的adPosition不为空，就给adPosition的client_id,position_page赋值，调用广告位的基础查询接口获得集合
				List<AdPosition> adPositionList =  adPositionMapper.findAdPosition(adPosition);
				if( adPositionList != null && adPositionList.size() > 0 ){
					List<Ad> tal = new ArrayList<Ad>();
					for( AdPosition adp : adPositionList ){
						ad.setAdPositionId(adp.getId());
						tal = adMapper.findAd(ad);
						result.addAll(tal);
					}
				}else{
					result = adMapper.findAd(ad); 
				}
			}else{
				result = adMapper.findAd(ad); 
			}
			
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询Ad失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 Ad
     * @param page
     * @param ad
     * @return Page<Ad>    结果集合 
     */
	@Override
	public Page<Ad> findAdByPage(Page<Ad> page, Ad ad) {

		List<Ad> result = new ArrayList<Ad>(); 
		SqlSession sqlSession = null;
		AdMapper adMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			adMapper = sqlSession.getMapper(AdMapper.class);

			result = adMapper.findByPage(page, ad); 
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("分页查询Ad失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}

	/**
     * 查询 Ad
     * @return AdVo
     * 
     * @param id
     */
	@Override
	public Ad findAdById(long id) {
		// TODO Auto-generated method stub
		
		Ad ad = null;
		SqlSession sqlSession = null;
		AdMapper adMapper = null;
				
		try{
						
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			adMapper = sqlSession.getMapper(AdMapper.class);
			ad = adMapper.findAdById(id);
			if( ad == null ){
				ad = new Ad();
			}
			
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询Ad失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return ad;
	}

	/**
     * 查询 Ad
     * @return Ad
     * 
     * @param positionIds
     */
	@Override
	public Map<String, List<Ad>> getAdByPositionIds(List<Long> positionIds) {
		// TODO Auto-generated method stub
		
		SqlSession sqlSession = null;
		AdMapper adMapper = null;
		Map<String, List<Ad>> map = new HashMap<String, List<Ad>>();
				
		try{
						
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			adMapper = sqlSession.getMapper(AdMapper.class);
			
			Ad ad = new Ad();
			List<Ad> ads = null;
			Date now = new Date();
			for( long positionId : positionIds ){
				ad.setAdPositionId(positionId);
				ad.setIsEnabled(true);
				ad.setBeginTime(now);
				ad.setEndTime(now);
				ads = adMapper.findAd(ad);
				map.put(""+positionId, ads);
			}
			
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询Ad失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return map;
	}

	
}