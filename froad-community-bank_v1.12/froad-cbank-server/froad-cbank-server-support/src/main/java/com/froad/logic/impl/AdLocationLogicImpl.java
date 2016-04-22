package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.AdLocationMapper;
import com.froad.db.mysql.mapper.AdvertisingMapper;
import com.froad.db.redis.SupportsRedis;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.AdLocationLogic;
import com.froad.logic.AdvertisingLogic;
import com.froad.po.AdLocation;
import com.froad.po.Advertising;
import com.froad.po.FindAdLocationResult;
import com.froad.po.FindAllAdLocationResult;
import com.froad.po.FindPageAdLocationResult;
import com.froad.po.Result;

/**
 * 
 * <p>@Title: AdLocationLogicImpl.java</p>
 * <p>Description: 描述 </p>
 * <p>广告位 - 逻辑 - 实现类</p>
 * @author lf
 * @version 1.0
 * @created 2015年9月18日
 */
public class AdLocationLogicImpl implements AdLocationLogic {

	/**
     * 增加 AdLocation
     * @param adLocation
     * @return Result
     */
	@Override
	public Result addAdLocation(AdLocation adLocation) {
		
		Result result = null;
		Long id = null;
		SqlSession sqlSession = null;
		AdLocationMapper adLocationMapper = null;
		
		try{
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			adLocationMapper = sqlSession.getMapper(AdLocationMapper.class);
			
			// 同一位置、同一种终端类型，只能有一个广告位
			List<AdLocation> adLocationList = adLocationMapper.findAdLocationListByTerminalAndPosition(adLocation.getTerminalType(), adLocation.getPositionPage());
			if( adLocationList != null && adLocationList.size() > 0 ){
				result= new Result(ResultCode.failed,"添加广告位信息失败:同一位置、同一种终端类型，只能有一个广告位。");
				return result;
			}
			
			if( adLocationMapper.addAdLocation(adLocation) > -1 ) { 
				id = adLocation.getId(); 
			}
			
			/**********************操作Redis缓存**********************/
			if( id > 0 ){
				SupportsRedis.set_cbbank_adLocation_ad_location_id(adLocation);
			}
			
			sqlSession.commit(true); 
			
			result = new Result(ResultCode.success.getCode(), ""+id);
			
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			LogCvt.error("添加AdLocation失败，原因:" + e.getMessage(), e); 
			result= new Result(ResultCode.failed,"添加广告位信息失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return result;
	}

	/**
     * 删除 AdLocation
     * @param adLocation
     * @return Result  
     */
	@Override
	public Result deleteAdLocation(Long id) {
		
		return null;
	}

	/**
     * 修改 AdLocation
     * @param adLocation
     * @return Result
     */
	@Override
	public Result updateAdLocation(AdLocation adLocation) {
		
		Result result = null;
		SqlSession sqlSession = null;
		AdLocationMapper adLocationMapper = null;
		
		try{
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			adLocationMapper = sqlSession.getMapper(AdLocationMapper.class);
			AdLocation  adLocationOld= adLocationMapper.findAdLocationById(adLocation.getId());
			setAdLocation(adLocation,adLocationOld);
			System.out.println(adLocationOld.toString());
			if( adLocationMapper.updateAdLocation(adLocation) ) { 
				
				/**********************操作Redis缓存**********************/
				SupportsRedis.set_cbbank_adLocation_ad_location_id(adLocationOld);
				sqlSession.commit(true);
				result = new Result(ResultCode.success.getCode(), "编辑更新 AdLocation 成功");
			
			}else{
				
				result = new Result(ResultCode.failed.getCode(), "编辑更新 AdLocation 失败");
			}
			
			
			
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			LogCvt.error("编辑更新AdLocation失败，原因:" + e.getMessage(), e); 
			result= new Result(ResultCode.failed,"编辑更新广告位信息失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return result;
	}
	
	public void setAdLocation(AdLocation newOne,AdLocation oldOne){
		 if(newOne.getDescription()!=null&&!"".equals(newOne.getDescription())){
			 oldOne.setDescription(newOne.getDescription());
		 }
		 if(newOne.getEnableStatus()!=null&&!"".equals(newOne.getEnableStatus())){
			 oldOne.setEnableStatus(newOne.getDescription());
		 }
		 if(newOne.getHeight()!=null){
			 oldOne.setHeight(newOne.getHeight());
		}
		if(newOne.getName()!=null&&!"".equals(newOne.getName())){
			oldOne.setName(newOne.getName());
		}
		if(newOne.getParamOneType()!=null&&!"".equals(newOne.getParamOneType())){
			oldOne.setParamOneType(newOne.getParamOneType());
		}
		if(newOne.getParamTwoType()!=null&&!"".equals(newOne.getParamTwoType())){
			oldOne.setParamTwoType(newOne.getParamTwoType());
		}
		if(newOne.getParamThreeType()!=null&&!"".equals(newOne.getParamThreeType())){
			oldOne.setParamThreeType(newOne.getParamThreeType());
		}
		if(newOne.getPositionPage()!=null&&!"".equals(newOne.getPositionPage())){
			oldOne.setPositionPage(newOne.getPositionPage());
		}
		if(newOne.getSizeLimit()!=null){
			oldOne.setSizeLimit(newOne.getSizeLimit());
		}
		if(newOne.getWidth()!=null){
			oldOne.setWidth(newOne.getWidth());
		}
		if(newOne.getTerminalType()!=null&&!"".equals(newOne.getTerminalType())){
			oldOne.setTerminalType(newOne.getTerminalType());
		}
	}

	/**
     * 查询 AdLocation 列表
     * @param adLocation
     * @return List<AdLocation>    结果集合 
     */
	@Override
	public FindAllAdLocationResult findAdLocation(AdLocation adLocation) {
		
		FindAllAdLocationResult findAllAdLocationResult = new FindAllAdLocationResult();
		List<AdLocation> adLocationList = new ArrayList<AdLocation>();
		
		SqlSession sqlSession = null;
		AdLocationMapper adLocationMapper = null;
		
		try {
			
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			adLocationMapper = sqlSession.getMapper(AdLocationMapper.class);
			
			adLocationList = adLocationMapper.findAdLocationList(adLocation);
			if( adLocationList != null && adLocationList.size() > 0 ){
				for( AdLocation po : adLocationList ){
					SupportsRedis.set_cbbank_adLocation_ad_location_id(po);
				}
			}
			findAllAdLocationResult.setResult(new Result(ResultCode.success));
			findAllAdLocationResult.setAdLocationList(adLocationList);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询AdLocation列表失败，原因:" + e.getMessage(), e); 
			findAllAdLocationResult.setResult(new Result(ResultCode.failed, "-查询AdLocation列表失败"));
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return findAllAdLocationResult;
	}

	/**
     * 分页查询 AdLocation
     * @param page
     * @param adLocation
     * @return Page<AdLocation>    结果集合 
     */
	@Override
	public FindPageAdLocationResult findAdLocationByPage(Page<AdLocation> page, AdLocation adLocation) {
		
		FindPageAdLocationResult findPageAdLocationResult = new FindPageAdLocationResult();
		List<AdLocation> adLocationList = new ArrayList<AdLocation>();
		
		SqlSession sqlSession = null;
		AdLocationMapper adLocationMapper = null;
		
		try{
			
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			adLocationMapper = sqlSession.getMapper(AdLocationMapper.class);
			
			adLocationList = adLocationMapper.findByPage(page, adLocation);
			page.setResultsContent(adLocationList);
			
			findPageAdLocationResult.setResult(new Result(ResultCode.success));
			findPageAdLocationResult.setPage(page);
			
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询AdLocation分页失败，原因:" + e.getMessage(), e);
			findPageAdLocationResult.setResult(new Result(ResultCode.failed, "查询AdLocation分页失败"));
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return findPageAdLocationResult;
	}

	/**
     * 查询 AdLocation 单个
     * @return adLocation
     * 
     * @param clientId
     * @param id
     */
	@Override
	public FindAdLocationResult getAdLocationById(long id) {
		
		FindAdLocationResult findAdLocationResult = new FindAdLocationResult();
		AdLocation adLocation = null;
		
		SqlSession sqlSession = null;
		AdLocationMapper adLocationMapper = null;
		
		try{
			
			Map<String,String> operatorRedis = SupportsRedis.getAll_cbbank_adLocation_ad_location_id(id);
			if(operatorRedis!=null){
				LogCvt.debug("查询 AdLocation 缓存存在:"+id);
				adLocation = new AdLocation();
				adLocation.setId(id);
				adLocation.setName(operatorRedis.get("name"));
				adLocation.setTerminalType(operatorRedis.get("terminal_type"));
				adLocation.setPositionPage(operatorRedis.get("position_page"));
				adLocation.setSizeLimit(Integer.parseInt(operatorRedis.get("size_limit")));
				adLocation.setWidth(Integer.parseInt(operatorRedis.get("width")));
				adLocation.setHeight(Integer.parseInt(operatorRedis.get("height")));
				adLocation.setParamOneType(operatorRedis.get("param_one_type"));
				adLocation.setParamTwoType(operatorRedis.get("param_two_type"));
				adLocation.setParamThreeType(operatorRedis.get("param_three_type"));
				adLocation.setDescription(operatorRedis.get("description"));
				adLocation.setEnableStatus(operatorRedis.get("enable_status"));
				
			}
			
			if( adLocation == null ){
			
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				adLocationMapper = sqlSession.getMapper(AdLocationMapper.class);
				
				adLocation = adLocationMapper.findAdLocationById(id);
				if( adLocation == null ){
					adLocation = new AdLocation();
				}else{
					SupportsRedis.set_cbbank_adLocation_ad_location_id(adLocation);
				}
			}
			findAdLocationResult.setResult(new Result(ResultCode.success));
			findAdLocationResult.setAdLocation(adLocation);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询AdLocation单个失败，原因:" + e.getMessage(), e);
			findAdLocationResult.setResult(new Result(ResultCode.failed, "查询AdLocation单个失败"));
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return findAdLocationResult;
	}

	/**
     * 禁用 AdLocation
     * @param id
     * @return Result  
     */
	@Override
	public Result disabledAdLocation(Long id) {
		
		Result result = null;
		SqlSession sqlSession = null;
		AdLocationMapper adLocationMapper = null;
		AdvertisingMapper advertisingMapper = null;
		AdvertisingLogic advertisingLogic = null;
		
		try{
			
			if( id == null || id <= 0 ){
				result = new Result(ResultCode.failed.getCode(), "禁用AdLocation失败 id不能为空");
				return result;
			}
			
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			adLocationMapper = sqlSession.getMapper(AdLocationMapper.class);
			
			Boolean br = adLocationMapper.disabledAdLocation(id);
			if( br ){
				AdLocation adLocation = adLocationMapper.selectAdLocationById(id);
				SupportsRedis.set_cbbank_adLocation_ad_location_id(adLocation.getId(), "enable_status", "1");
				LogCvt.debug("禁用 AdLocation成功 id="+id);
				sqlSession.commit(true);
				result = new Result(ResultCode.success.getCode(), "禁用AdLocation成功");
				try{
					advertisingMapper = sqlSession.getMapper(AdvertisingMapper.class);
					Advertising advertising = new Advertising();
					advertising.setAdLocationId(id);
					// 查询出关联的Advertising
					List<Advertising> advertisingList = advertisingMapper.findAdvertisingList(advertising);
					if( advertisingList != null && advertisingList.size() > 0 ){
						advertisingLogic = new AdvertisingLogicImpl();
						for( Advertising po : advertisingList ){
							po.setEnableStatus("1"); // 禁用
							Result tr = advertisingLogic.updateAdvertising(po);
							LogCvt.debug("关联的Advertising "+po.getId()+" 禁用:"+(ResultCode.success.getCode().equals(tr.getResultCode())?"成功":"失败")+(ResultCode.success.getCode().equals(tr.getResultCode())?"":" "+tr.getResultDesc()));
							sqlSession.commit(true);
						}
					}else{
						LogCvt.debug("没有关联的Advertising");
					}
					
				}catch(Exception e){
					LogCvt.error("禁用关联Advertising失败，原因:" + e.getMessage(), e); 
				}
			}else{
				result = new Result(ResultCode.failed.getCode(), "禁用AdLocation失败");
			}
			
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("禁用AdLocation失败，原因:" + e.getMessage(), e); 
			result = new Result(ResultCode.failed.getCode(), "禁用AdLocation失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return result;
	}

}
