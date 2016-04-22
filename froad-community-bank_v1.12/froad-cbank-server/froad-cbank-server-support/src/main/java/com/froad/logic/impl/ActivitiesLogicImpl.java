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
 * @Title: ActivitiesLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.db.mysql.mapper.ActivitiesMapper;
import com.froad.db.redis.SupportsRedis;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.ActivitiesLogic;
import com.froad.po.Activities;

/**
 * 
 * <p>@Title: ActivitiesLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class ActivitiesLogicImpl implements ActivitiesLogic {


    /**
     * 增加 Activities
     * @param activities
     * @return Long    主键ID
     */
	@Override
	public ResultBean addActivities(Activities activities) {
		
		ResultBean resultBean=new ResultBean(ResultCode.success,"添加活动信息成功");
		Long result = null; 
		SqlSession sqlSession = null;
		ActivitiesMapper activitiesMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			activities.setCreateTime(new Date());
			activities.setIsDelete(false);
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activitiesMapper = sqlSession.getMapper(ActivitiesMapper.class);
//			List<Activities> activitie= activitiesMapper.findActivitiesById(activities);
//		    if (EmptyChecker.isNotEmpty( activitie)) {
//		    	return new ResultBean(ResultCode.failed,"添加活动失败，活动已存在，不允许重复增加!");
//            }
		    if (activitiesMapper.addActivities(activities) > -1) { 
				result = activities.getId(); 
			}
			
			/**********************操作Redis缓存**********************/
			if(result > 0)
			SupportsRedis.set_cbbank_activities_client_id_activities_id(activities);
			sqlSession.commit(true); 
		    resultBean= new ResultBean(ResultCode.success,"添加活动信息成功",result);
		} catch (Exception e) { 
			sqlSession.rollback(true); 
			resultBean= new ResultBean(ResultCode.failed,"添加活动信息失败");
			LogCvt.error("添加Activities失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		  return resultBean;

	}


    /**
     * 删除 Activities
     * @param activities
     * @return Boolean    是否成功
     */
	@Override
	public ResultBean deleteActivities(String clientId, Long activitiesId) {
		ResultBean resultBean=new ResultBean(ResultCode.success,"删除活动信息成功");
		Boolean result = null; 
		SqlSession sqlSession = null;
		ActivitiesMapper activitiesMapper = null;
		try { 
			
			
			if( activitiesId == null || activitiesId <= 0 ){
				resultBean= new ResultBean(ResultCode.failed,"删除活动信息失败 - activities.id 为空");
				return resultBean;
			}
			if( clientId == null || "".equals(clientId) ){
				resultBean= new ResultBean(ResultCode.failed,"删除活动信息失败 - clientId 为空");
				return resultBean;
			}
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activitiesMapper = sqlSession.getMapper(ActivitiesMapper.class);

			result = activitiesMapper.deleteActivities(activitiesId); 
			if(result) {
				/**********************操作Redis缓存**********************/
				SupportsRedis.set_cbbank_activities_client_id_activities_id(clientId, activitiesId,"status", "2");
			}else{
				resultBean= new ResultBean(ResultCode.failed,"删除活动信息失败");
			}
			sqlSession.commit(true);
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			resultBean= new ResultBean(ResultCode.failed,"删除活动信息失败");
			LogCvt.error("删除Activities失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		 return resultBean;

	}


    /**
     * 修改 Activities
     * @param activities
     * @return Boolean    是否成功
     */
	@Override
	public ResultBean updateActivities(Activities activities) {
		
		ResultBean resultBean=new ResultBean(ResultCode.success,"修改活动信息成功");
		Boolean result = null; 
		SqlSession sqlSession = null;
		ActivitiesMapper activitiesMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activitiesMapper = sqlSession.getMapper(ActivitiesMapper.class);
//			List<Activities> activitie= activitiesMapper.findActivitiesById(activities);
//		    if (EmptyChecker.isNotEmpty(activitie)) {
//				result = activitiesMapper.updateActivities(activities); 
//				if(result) {
//					/**********************操作Redis缓存**********************/
//					SupportRedis.set_cbbank_activities_client_id_activities_id(activities);
//				
//				}
//				sqlSession.commit(true);
//		    }
			if( activitiesMapper.updateActivities(activities) ){
				sqlSession.commit(true);
				Activities t = new Activities();
				t.setId(activities.getId());
				List<Activities> ts = this.findActivities(t);
				SupportsRedis.set_cbbank_activities_client_id_activities_id(ts.get(0));
//				result = true;
			}else{
				resultBean = new ResultBean(ResultCode.failed,"修改活动信息失败");
			}
		} catch (Exception e) { 
			sqlSession.rollback(true); 
			result = false; 
			resultBean= new ResultBean(ResultCode.failed,"修改活动信息失败");
			LogCvt.error("修改Activities失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		 return resultBean;

	}


    /**
     * 查询 Activities
     * @param activities
     * @return List<Activities>    结果集合 
     */
	@Override
	public List<Activities> findActivities(Activities activities) {

		List<Activities> result = new ArrayList<Activities>(); 
		Activities act = new Activities();
		SqlSession sqlSession = null;
		ActivitiesMapper activitiesMapper = null;
		try { 
			/**********************操作Redis缓存**********************/
//			Map<String,String> operatorRedis = SupportRedis.getAll_cbbank_area_client_id_area_id(activities.getId());
//			if(operatorRedis!=null){
//				act.setActivitiesName(operatorRedis.get("activities_name"));
//				act.setActivitiesType(operatorRedis.get("activities_type"));
////				act.setBeginTime(operatorRedis.get("begin_time"));
////				act.setEndTime(operatorRedis.get("end_time"));  时间类型还没确定报错，先注释。。
//				act.setIsEnable(BooleanUtils.toBooleanObject(operatorRedis.get("is_enable"), "1", "0", ""));
//				act.setPoints(Integer.parseInt(operatorRedis.get("points")));
//				act.setVipPrice(operatorRedis.get("vip_price"));
//				result.add(act);
//			}else{
				/**********************操作Mongodb数据库**********************/
	
				/**********************操作MySQL数据库**********************/
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				activitiesMapper = sqlSession.getMapper(ActivitiesMapper.class);
				//MySql查询数据
				result = activitiesMapper.findActivities(activities);
				//重新设置缓存
//				for(Activities ats : result){
//					SupportRedis.set_cbbank_activities_client_id_activities_id(ats);
//				}
//			}
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询Activities失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 Activities
     * @param page
     * @param activities
     * @return Page<Activities>    结果集合 
     */
	@Override
	public Page<Activities> findActivitiesByPage(Page<Activities> page, Activities activities) {

		List<Activities> result = new ArrayList<Activities>(); 
		SqlSession sqlSession = null;
		ActivitiesMapper activitiesMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activitiesMapper = sqlSession.getMapper(ActivitiesMapper.class);

			result = activitiesMapper.findByPage(page, activities); 
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("分页查询Activities失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}

	/**
     * 查询 Activities
     * @return ActivitiesVo
     * 
     * @param clientId
     * @param id
     */
	@Override
	public Activities getActivitiesById(String clientId, long id) {
		// TODO Auto-generated method stub
		
		Activities activities = null;
		SqlSession sqlSession = null;
		ActivitiesMapper activitiesMapper = null;
		
		try{
			
			if( clientId != null && !"".equals(clientId) ){
				Map<String,String> operatorRedis = SupportsRedis.getAll_cbbank_activities_client_id_activities_id(clientId, id);
				LogCvt.debug("查询Activities 缓存:"+operatorRedis);
				if(operatorRedis!=null){
					activities = new Activities();
					activities.setId(id);
					activities.setClientId(clientId);
					String createTime = ObjectUtils.toString(operatorRedis.get("create_time"));
					if( createTime != null ){
						activities.setCreateTime(new Date(Long.parseLong(createTime)));
					}
					activities.setActivitiesName(ObjectUtils.toString(operatorRedis.get("activities_name")));
					String points = ObjectUtils.toString(operatorRedis.get("points"));
					if( points != null ){
						activities.setPoints(Integer.parseInt(points));
					} 
					String beginTime = ObjectUtils.toString(operatorRedis.get("begin_time"));
					if( beginTime != null ){
						activities.setBeginTime(new Date(Long.parseLong(beginTime)));
					}
					String endTime = ObjectUtils.toString(operatorRedis.get("end_time"));
					if( endTime != null ){
						activities.setEndTime(new Date(Long.parseLong(endTime)));
					}
					activities.setStatus(ObjectUtils.toString(operatorRedis.get("status")));
					String count = ObjectUtils.toString(operatorRedis.get("count"));
					if( count != null ){
						activities.setCount(Integer.parseInt(count));
					} 
					return activities;
				}
			}
			
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activitiesMapper = sqlSession.getMapper(ActivitiesMapper.class);
			activities = activitiesMapper.getActivitiesById(id);
			if( activities == null ){
				activities = new Activities();
			}else{
				SupportsRedis.set_cbbank_activities_client_id_activities_id(activities);
			}
			
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询Activities失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return activities;
	}


}