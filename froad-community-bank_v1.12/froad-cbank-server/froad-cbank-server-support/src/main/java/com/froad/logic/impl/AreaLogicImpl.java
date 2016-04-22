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
 * @Title: AreaLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.alibaba.fastjson.JSON;
import com.froad.common.enums.CityName;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.db.mysql.mapper.AreaMapper;
import com.froad.db.redis.SupportsRedis;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.AreaLogic;
import com.froad.po.Area;

/**
 * 
 * <p>@Title: AreaLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class AreaLogicImpl implements AreaLogic {


    /**
     * 增加 Area
     * @param area
     * @return Long    主键ID
     */
	@Override
	public ResultBean addArea(Area area) {
		ResultBean resultBean=new ResultBean(ResultCode.success,"添加地区信息成功");
		Long result = 0l; 
		SqlSession sqlSession = null;
		AreaMapper areaMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			areaMapper = sqlSession.getMapper(AreaMapper.class);

			if (areaMapper.addArea(area) > -1) { 
				result = area.getId(); 
				sqlSession.commit(true); 
			    resultBean= new ResultBean(ResultCode.success,"添加地区信息成功",result);
			}
			
			if(result > 0){
				/**********************操作Redis缓存**********************/
				Boolean redisR = SupportsRedis.set_cbbank_area_client_id_area_id(area);
				LogCvt.debug("addArea Redis Operating:"+redisR);
			}
			
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			LogCvt.error("添加Area失败，原因:" + e.getMessage(), e); 
			resultBean= new ResultBean(ResultCode.failed,"添加地区信息失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 

	}


    /**
     * 删除 Area
     * @param area
     * @return Boolean    是否成功
     */
	@Override
	public ResultBean deleteArea(Area area) {
		ResultBean resultBean=new ResultBean(ResultCode.success,"删除地区信息成功");
		Boolean result = null; 
		SqlSession sqlSession = null;
		AreaMapper areaMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			areaMapper = sqlSession.getMapper(AreaMapper.class);
			if( area.getId() == null || area.getId() <= 0 ){
				resultBean= new ResultBean(ResultCode.failed,"删除区域信息失败 - id 为空");
				return resultBean;
			}
			result = areaMapper.deleteArea(area); 
		
			if(result) {
				
				sqlSession.commit(true);
				/**********************操作Redis缓存**********************/
				Boolean redisR = SupportsRedis.set_cbbank_area_client_id_area_id(area.getId(),"is_enable", "0");
				LogCvt.debug("delArea Redis Operating:"+redisR);
			}
			
			
			
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			result = false; 
			LogCvt.error("删除Area失败，原因:" + e.getMessage(), e); 
			resultBean= new ResultBean(ResultCode.failed,"删除地区信息失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 

	}


    /**
     * 修改 Area
     * @param area
     * @return Boolean    是否成功
     */
	@Override
	public ResultBean updateArea(Area area) {
		ResultBean resultBean=new ResultBean(ResultCode.success,"修改地区信息成功");
		Boolean result = null; 
		SqlSession sqlSession = null;
		AreaMapper areaMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			areaMapper = sqlSession.getMapper(AreaMapper.class);
			if( area.getId() == null || area.getId() <= 0 ){
				resultBean= new ResultBean(ResultCode.failed,"修改区域信息失败 - id 为空");
				return resultBean;
			}
			result = areaMapper.updateArea(area);
			if(result) {
				sqlSession.commit(true);
				/**********************操作Redis缓存**********************/
				Boolean redisR = SupportsRedis.set_cbbank_area_client_id_area_id(area);
				LogCvt.debug("updArea Redis Operating:"+redisR);
			}
			
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			result = false; 
			LogCvt.error("修改Area失败，原因:" + e.getMessage(), e); 
			resultBean= new ResultBean(ResultCode.failed,"修改地区信息失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 

	}


    /**
     * 查询 Area
     * @param area
     * @return List<Area>    结果集合 
     */
	@Override
	public List<Area> findArea(Area area) {
		
		List<Area> result = null; 
		SqlSession sqlSession = null;
		AreaMapper areaMapper = null;
		try { 
			/**********************操作Redis缓存**********************/
			
			// 现在的代理方式不支持 待二期完善
//			SupportRedis.getAll_cbbank_area_area_id(area);
//			Map<String,String> operatorRedis = SupportRedis.getAll_cbbank_area_client_id_area_id(area.getId());
//			operatorRedis=null;
//			if(operatorRedis!=null){
//				Area are = new Area();
//				are.setAreaCode(operatorRedis.get("area_code"));
//				are.setIsEnable(BooleanUtils.toBooleanObject(operatorRedis.get("is_enabled")));
//				are.setName(operatorRedis.get("name"));
//				are.setParentId(Long.parseLong(operatorRedis.get("parent_id")));
//				are.setTreePath(operatorRedis.get("tree_path"));
//				are.setTreePathName(operatorRedis.get("tree_path_name"));
//				are.setVagueLetter(operatorRedis.get("vague_letter"));
//				result.add(are);
//			}else{
				/**********************操作MySQL数据库**********************/
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				areaMapper = sqlSession.getMapper(AreaMapper.class);
				result = areaMapper.findArea(area); 
				if( result == null ){
					result = new ArrayList<Area>();
				}
//				for(Area a : result){
//					SupportRedis.set_cbbank_area_client_id_area_id(a);
//				}
//			}

			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询Area失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 Area
     * @param page
     * @param area
     * @return Page<Area>    结果集合 
     */
	@Override
	public Page<Area> findAreaByPage(Page<Area> page, Area area) {

		List<Area> result = new ArrayList<Area>(); 
		SqlSession sqlSession = null;
		AreaMapper areaMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			areaMapper = sqlSession.getMapper(AreaMapper.class);

			result = areaMapper.findByPage(page, area); 
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("分页查询Area失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}
	
	/** 
	 * 根据id获取地区信息
	* @Title: findAreaById 
	* @Description: 
	* @author longyunbo
	* @param @param id
	* @param @return
	* @return 
	* @throws 
	*/
	@Override
	public Area findAreaById(Long id){
		SqlSession sqlSession = null;
		AreaMapper areaMapper = null;
		Area area = null;
		try {
			Map<String, String> redisR = SupportsRedis.get_cbbank_area_area_id(id);
			if( redisR != null && !redisR.isEmpty() ){
				LogCvt.debug("在缓存中查询到Area信息 id:"+id);
				area = changeMapToArea(redisR);
				area.setId(id);
				return area;
			}
			LogCvt.debug("在缓存中没有查询到Area信息 id:"+id);
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			areaMapper = sqlSession.getMapper(AreaMapper.class);
			area = areaMapper.findAreaById(id);
			if( area == null ){
				area = new Area();// 确保service中copy时不出错
			}else{
				Boolean tr = SupportsRedis.set_cbbank_area_client_id_area_id(area);
				LogCvt.debug("Again addArea Redis Operating:"+tr);
			}
		} catch (Exception e) {
			// TODO: handle exception
			LogCvt.error("获取地区信息失败:"+e.getMessage(), e);
		}finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return area;
	}
	
	/**
     * 根据 areaCode 获取地区
     * @return AreaVo
     * 
     * @param areaCode
     */
	@Override
	public Area findAreaByAreaCode(String areaCode) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		AreaMapper areaMapper = null;
		Area area = null;
		try{
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			areaMapper = sqlSession.getMapper(AreaMapper.class);
			area = areaMapper.findAreaByAreaCode(areaCode);
			if( area == null ){
				area = new Area();// 确保service中copy时不出错
			}
		} catch (Exception e) {
			// TODO: handle exception
			LogCvt.error("获取地区信息失败:"+e.getMessage(), e);
		}finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return area;
	}
	
	/**
     * 根据 areaCode 和 clientId 获取地区 
     * @return AreaVo
     */
	@Override
	public Area findAreaByAreaCodeAndClientId(String areaCode, String clientId) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		AreaMapper areaMapper = null;
		Area area = null;
		try{
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			areaMapper = sqlSession.getMapper(AreaMapper.class);
			area = new Area();
			area.setAreaCode(areaCode);
			area.setClientId(clientId);
			List<Area> al = areaMapper.findArea(area);
			if( al != null && al.size() > 0 ){
				area = al.get(0);
			}else{
				area = new Area();// 确保service中copy时不出错
			}
		} catch (Exception e) {
			// TODO: handle exception
			LogCvt.error("获取地区信息失败:"+e.getMessage(), e);
		}finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return area;
	}


	// 把Map中的值传成Area对象
	private Area changeMapToArea(Map<String, String> redisR){
		Area area = new Area();
		String name = redisR.get("name");
		if( name != null ){
			area.setName(name);
		}
		String clientId=redisR.get("client_id");
		if(clientId!=null){
			area.setClientId(clientId);
		}
		String vague_letter = redisR.get("vague_letter");
		if( vague_letter != null ){
			area.setVagueLetter(vague_letter);
		}
		String tree_path = redisR.get("tree_path");
		if( tree_path != null ){
			area.setTreePath(tree_path);
		}
		String parent_id = redisR.get("parent_id");
		if( parent_id != null ){
			area.setParentId(Long.parseLong(parent_id));
		}
		String is_enable = redisR.get("is_enable");
		if( is_enable != null ){
			area.setIsEnable("1".equals(is_enable));
		}
		String tree_path_name = redisR.get("tree_path_name");
		if( tree_path_name != null ){
			area.setTreePathName(tree_path_name);
		}
		String area_code = redisR.get("area_code");
		if( area_code != null ){
			area.setAreaCode(area_code);
		}
		return area;
	}
	
	/** 
	 * 根据id获取子集地区
	* @Title: findChildrenInfoById 
	* @Description: 
	* @author longyunbo
	* @param  id
	* @param  areaCode
	* @return 
	* @throws 
	*/
	@Override
	public List<Area> findChildrenInfoById(Long id, String areaCode) {
		SqlSession sqlSession = null;
		AreaMapper areaMapper = null;
		List<Area> result = new ArrayList<Area>(); 
		try {
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			areaMapper = sqlSession.getMapper(AreaMapper.class);
			if( id != null && id > 0 ){
				result = areaMapper.findChildrenInfoById(id);
			}else if( areaCode != null && !"".equals(areaCode) ){
				Area area = new Area();
				area.setAreaCode(areaCode);
				List<Area> tal = areaMapper.findArea(area);
				if( tal != null && tal.size() > 0 ){
					area = tal.get(0);
					result = areaMapper.findChildrenInfoById(area.getId());
				}
			}else if( id == 0 ){
				result = areaMapper.findChildrenInfoById(id);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			LogCvt.error("获取地区失败:"+e.getMessage(), e);
		} finally{
			if(null != sqlSession)  
				sqlSession.close();  
		}
		return result;
	}

	@Override
	public List<Area> findChildrenInfo(Long id, String areaCode, String clientId) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		AreaMapper areaMapper = null;
		List<Area> result = new ArrayList<Area>(); 
		try {
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			areaMapper = sqlSession.getMapper(AreaMapper.class);
			if( id != null && id > 0 ){
				result = areaMapper.findChildrenInfoByIdAndClientId(id, clientId);
			}else if( areaCode != null && !"".equals(areaCode) ){
				Area area = new Area();
				area.setAreaCode(areaCode);
				List<Area> tal = areaMapper.findArea(area);
				if( tal != null && tal.size() > 0 ){
					area = tal.get(0);
					result = areaMapper.findChildrenInfoByIdAndClientId(area.getId(), clientId);
				}
			}else if( id == 0 ){
				result = areaMapper.findChildrenInfoByIdAndClientId(id, clientId);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			LogCvt.error("获取地区失败:"+e.getMessage(), e);
		} finally{
			if(null != sqlSession)  
				sqlSession.close();  
		}
		return result;
	}


	/**
     * 判断areaCode是否属于clientId的范围内
     * @return boolean
     * 
     * @param areaCode
     * @param clientId
     */
	@Override
	public boolean isAreaCodeScopeOfClientId(String areaCode, String clientId) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		AreaMapper areaMapper = null;
		List<Area> al = new ArrayList<Area>(); 
		try{
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			areaMapper = sqlSession.getMapper(AreaMapper.class);
			Area area = new Area();
			area.setClientId(clientId);
			area.setAreaCode(areaCode);
			al = areaMapper.findArea(area); 
			if( al != null && al.size() > 0 ){
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			LogCvt.error("判断areaCode是否属于clientId的范围内 失败:"+e.getMessage(), e);
		} finally{
			if(null != sqlSession)  
				sqlSession.close();  
		}
		return false;
	}

	/**
     * 根据clientId获取省级地区
     * @return List<Area>
     * 
     * @param clientId
     */
	@Override
	public List<Area> findProvinceAreaByClientId(String clientId) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		AreaMapper areaMapper = null;
		List<Area> areas = null;
		List<Area> cityareas=null;
		List<Area> totalareas=new ArrayList<Area>();
		try{
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			areaMapper = sqlSession.getMapper(AreaMapper.class);
			areas = areaMapper.findProvinceAreaByClientId(clientId);
			//newareas = new ArrayList<Area>();// 确保service中copy时不出错
			/**
			 * 1.如果不是直辖市,直接返回
			 * 2.如果是直辖市,则需调用查询市的接口查询一次,然后再将返回市的结果组装返回
			 * 效果：如果是安徽省,点击就到市,如果是上海市,点击就到浦东新区
			 */
			if( areas != null ){
				for(int i=0;i<areas.size();i++){
					Area area=areas.get(i);
//					if(area.getName().equals(CityName.BJING.getCityName())
//							||area.getName().equals(CityName.TJIAN.getCityName())
//							||area.getName().equals(CityName.CQING.getCityName())
//							||area.getName().equals(CityName.SHAI.getCityName()))
//						
//				    { 
					cityareas=areaMapper.findChildrenInfoByIdAndClientId(area.getId(),clientId);
					if(cityareas!=null&&cityareas.size()>0){
						for(int k=0;k<cityareas.size();k++){
						totalareas.add(cityareas.get(k));
						}					
							
						//return newareas;
					}
//					}
//					else{
//					newareas.add(area);
//					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		//	newareas = new ArrayList<Area>();
			LogCvt.error("根据clientId获取省级地区失败:"+e.getMessage(), e);
		}finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return totalareas;
	}

	
	

}