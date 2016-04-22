package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
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
import com.froad.logic.AdvertisingLogic;
import com.froad.po.AdLocation;
import com.froad.po.Advertising;
import com.froad.po.FindAdvertisingResult;
import com.froad.po.FindAllAdvertisingParam;
import com.froad.po.FindAllAdvertisingResult;
import com.froad.po.FindPageAdvertisingResult;
import com.froad.po.Result;

/**
 * 
 * <p>@Title: AdvertisingLogic.java</p>
 * <p>Description: 描述 </p>
 * <p>广告 - 逻辑 - 实现</p>
 * @author lf
 * @version 1.0
 * @created 2015年9月22日
 */
public class AdvertisingLogicImpl implements AdvertisingLogic {

	/**
     * 增加 Advertising
     * @param advertising
     * @return Result
     */
	@Override
	public Result addAdvertising(Advertising advertising) {
		
		Result result = null;
		Long id = null;
		
		SqlSession sqlSession = null;
		AdvertisingMapper advertisingMapper = null;
		
		try{
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			advertisingMapper = sqlSession.getMapper(AdvertisingMapper.class);
			
			// 同一客户端、同一标题，只能有一个广告
			
			List<Advertising> advertisingList = advertisingMapper.findAdvertisingListByClientIdAndTitle(advertising.getClientId(), advertising.getTitle());
			if( advertisingList != null && advertisingList.size() > 0 ){
				result= new Result(ResultCode.failed,"添加广告信息失败-同一客户端、同一种标题，只能有一个广告");
				return result;
			}
			
			if( advertisingMapper.addAdvertising(advertising) > -1 ){
				id = advertising.getId();
			}
			
			/**********************操作Redis缓存**********************/
			if( id > 0 ){
				SupportsRedis.set_cbbank_advertising_client_id_advertising_id(advertising);
			}
			
			sqlSession.commit(true); 
			
			result = new Result(ResultCode.success.getCode(), ""+id);
			
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			LogCvt.error("添加Advertising失败，原因:" + e.getMessage(), e); 
			result= new Result(ResultCode.failed,"添加广告信息失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return result;
	}

	/**
     * 删除 Advertising
     * @param id
     * @return Result  
     */
	@Override
	public Result deleteAdvertising(Long id) {
		return null;
	}

	/**
     * 修改 Advertising
     * @param advertising
     * @return Result
     */
	@Override
	public Result updateAdvertising(Advertising advertising) {
		
		Result result = null;
		SqlSession sqlSession = null;
		AdvertisingMapper advertisingMapper = null;
		
		try{
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			advertisingMapper = sqlSession.getMapper(AdvertisingMapper.class);
			
			if( advertisingMapper.updateAdvertising(advertising) ) { 
				
				/**********************操作Redis缓存**********************/
				SupportsRedis.set_cbbank_advertising_client_id_advertising_id(advertising);
				sqlSession.commit(true);
				result = new Result(ResultCode.success.getCode(), "编辑更新 Advertising 成功");
			
			}else{
				
				result = new Result(ResultCode.failed.getCode(), "编辑更新 Advertising 失败");
			}
			
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			LogCvt.error("编辑更新Advertising失败，原因:" + e.getMessage(), e); 
			result= new Result(ResultCode.failed,"编辑更新广告信息失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return result;
		
	}

	/**
     * 查询 Advertising 列表
     * @param advertising
     * @return List<Advertising>    结果集合 
     */
	@Override
	public FindAllAdvertisingResult findAdvertising(Advertising advertising) {
		
		FindAllAdvertisingResult findAllAdvertisingResult = new FindAllAdvertisingResult();
		List<Advertising> advertisingList = new ArrayList<Advertising>();
		
		SqlSession sqlSession = null;
		AdvertisingMapper advertisingMapper = null;
		
		try{
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			advertisingMapper = sqlSession.getMapper(AdvertisingMapper.class);
			
			advertisingList = advertisingMapper.findAdvertisingList(advertising);
			if( advertisingList != null && advertisingList.size() > 0 ){
				for( Advertising po : advertisingList ){
					SupportsRedis.set_cbbank_advertising_client_id_advertising_id(po);
				}
			}
			findAllAdvertisingResult.setResult(new Result(ResultCode.success));
			findAllAdvertisingResult.setAdvertisingList(advertisingList);
			
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询Advertising列表失败，原因:" + e.getMessage(), e); 
			findAllAdvertisingResult.setResult(new Result(ResultCode.failed, "-查询Advertising列表失败"));
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return findAllAdvertisingResult;
	}

	/**
     * 分页查询 Advertising
     * @param page
     * @param advertising
     * @return Page<Advertising>    结果集合 
     */
	@Override
	public FindPageAdvertisingResult findAdvertisingByPage(Page<Advertising> page, Advertising advertising) {
		
		FindPageAdvertisingResult findPageAdvertisingResult = new FindPageAdvertisingResult();
		List<Advertising> advertisingList = new ArrayList<Advertising>();
		
		SqlSession sqlSession = null;
		AdvertisingMapper advertisingMapper = null;
		
		try{
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			advertisingMapper = sqlSession.getMapper(AdvertisingMapper.class);
			
			advertisingList = advertisingMapper.findByPage(page, advertising);
			page.setResultsContent(advertisingList);
			
			findPageAdvertisingResult.setResult(new Result(ResultCode.success));
			findPageAdvertisingResult.setPage(page);
			
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询Advertising分页失败，原因:" + e.getMessage(), e); 
			findPageAdvertisingResult.setResult(new Result(ResultCode.failed, "-查询Advertising分页失败"));
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return findPageAdvertisingResult;
	}

	/**
     * 查询 Advertising 单个
     * @return advertising
     * 
     * @param clientId
     * @param id
     */
	@Override
	public FindAdvertisingResult getAdvertisingById(String clientId, long id) {
		
		FindAdvertisingResult findAdvertisingResult = new FindAdvertisingResult();
		Advertising advertising = null;
		
		SqlSession sqlSession = null;
		AdvertisingMapper advertisingMapper = null;
		
		try{
			
			if( clientId != null && !"".equals(clientId) ){
				Map<String,String> operatorRedis = SupportsRedis.getAll_cbbank_advertising_client_id_advertising_id(clientId, id);
				if(operatorRedis!=null){
					LogCvt.debug("查询 Advertising 缓存存在:"+clientId+"-"+id);
					advertising = new Advertising();
					advertising.setId(id);
					advertising.setClientId(clientId);
					advertising.setTitle(operatorRedis.get("title"));
					advertising.setAdLocationId(Long.parseLong(operatorRedis.get("ad_location_id")));
					advertising.setType(operatorRedis.get("type"));
					advertising.setOrderSn(Integer.getInteger(operatorRedis.get("order_sn")));
					advertising.setBeginTime(new Date(Long.parseLong(operatorRedis.get("begin_time"))));
					advertising.setEndTime(new Date(Long.parseLong(operatorRedis.get("end_time"))));
					advertising.setParamOneValue(operatorRedis.get("param_one_value"));
					advertising.setParamTwoValue(operatorRedis.get("param_two_value"));
					advertising.setParamThreeValue(operatorRedis.get("param_three_value"));
					advertising.setContent(operatorRedis.get("content"));
					advertising.setLink(operatorRedis.get("link"));
					advertising.setPath(operatorRedis.get("path"));
					advertising.setIsBlankTarge(Boolean.parseBoolean(operatorRedis.get("is_blank_targe")));
					advertising.setEnableStatus(operatorRedis.get("enable_status"));
					advertising.setClickCount(Integer.getInteger(operatorRedis.get("click_count")));
					advertising.setDescription(operatorRedis.get("description"));
					advertising.setTerminalType(operatorRedis.get("terminal_type"));
					advertising.setPositionPage(operatorRedis.get("position_page"));
				}
			}

			if( advertising == null ){
				
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				advertisingMapper = sqlSession.getMapper(AdvertisingMapper.class);
				
				advertising = advertisingMapper.findAdvertisingById(id);
				if( advertising == null ){
					advertising = new Advertising();
				}else{
					SupportsRedis.set_cbbank_advertising_client_id_advertising_id(advertising);
				}
			}
			findAdvertisingResult.setResult(new Result(ResultCode.success));
			findAdvertisingResult.setAdvertising(advertising);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询AdLocation单个失败，原因:" + e.getMessage(), e);
			findAdvertisingResult.setResult(new Result(ResultCode.failed, "查询Advertising单个失败"));
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return findAdvertisingResult;
	}
  
	/**
     * 页面优化查询 Advertising 列表
     * @return List<Advertising>
     * 
     * @param findAllAdvertisingParam
     */
	@Override
	public List<Advertising> pageOptFindAdvertisings(FindAllAdvertisingParam findAllAdvertisingParam) {
		
		List<Advertising> advertisingList = new ArrayList<Advertising>();
		
		SqlSession sqlSession = null;
		AdLocationMapper adLocationMapper = null;
		AdvertisingMapper advertisingMapper = null;
		
		try{
		
			String terminalType = findAllAdvertisingParam.getTerminalType();
			String positionPage = findAllAdvertisingParam.getPositionPage();
		
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			
			// 先根据终端类型、页码标识确定唯一的广告位
			adLocationMapper = sqlSession.getMapper(AdLocationMapper.class);
			List<AdLocation> adLocationList = adLocationMapper.findAdLocationListByTerminalAndPosition(terminalType, positionPage);
			if( adLocationList != null && adLocationList.size() <= 0 ){
				LogCvt.debug("页面优化查询Advertising列表失败，原因:查询参数 terminalType:"+terminalType+"-positionPage:"+positionPage+"没有广告位信息");
				return advertisingList;
			}
			AdLocation adLocation = adLocationList.get(0);
			
			advertisingMapper = sqlSession.getMapper(AdvertisingMapper.class);
			
			String paramOneValue = findAllAdvertisingParam.getParamOneValue();
			String paramTwoValue = findAllAdvertisingParam.getParamTwoValue();
			String paramThreeValue = findAllAdvertisingParam.getParamThreeValue();
			// 组装参数进行查询
			Advertising advertising = new Advertising();
			advertising.setAdLocationId(adLocation.getId());
			advertising.setClientId(findAllAdvertisingParam.getClientId());
			if( adLocation.getParamOneType() != null ){
				if( paramOneValue==null||"".equals(paramOneValue) ){
					LogCvt.debug("页面优化查询Advertising列表失败，原因:根据广告位信息 paramOneValue不能为空");
					return advertisingList;
				}
				advertising.setParamOneValue(paramOneValue);
			}
			if( adLocation.getParamTwoType() != null ){
				if( paramTwoValue==null||"".equals(paramTwoValue) ){
					LogCvt.debug("页面优化查询Advertising列表失败，原因:根据广告位信息 paramTwoValue不能为空");
					return advertisingList;
				}
				advertising.setParamTwoValue(paramTwoValue);
			}
			if( adLocation.getParamThreeType() != null ){
				if( paramThreeValue==null||"".equals(paramThreeValue) ){
					LogCvt.debug("页面优化查询Advertising列表失败，原因:根据广告位信息 paramThreeValue不能为空");
					return advertisingList;
				}
				advertising.setParamThreeValue(paramThreeValue);
			}
			advertisingList = advertisingMapper.pageOptFindAdvertisingsByThreeParam(advertising);
			if( advertisingList == null || advertisingList.size() <= 0 ){
				advertisingList = advertisingMapper.pageOptFindAdvertisingsByTwoParam(advertising);
				if( advertisingList == null || advertisingList.size() <= 0 ){
					advertisingList = advertisingMapper.pageOptFindAdvertisingsByOneParam(advertising);
					if( advertisingList == null || advertisingList.size() <= 0 ){
						advertisingList = advertisingMapper.pageOptFindAdvertisingsParamNull(advertising);
					}
				}
			}
			// ---------------------------------------------
//			advertisingList = advertisingMapper.pageOptFindAdvertisingsAutoMatch(advertising);
//			if( advertisingList == null || advertisingList.size() <= 0 ){
//				advertisingList = advertisingMapper.pageOptFindAdvertisingsParamNull(advertising);
//			}
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("页面优化查询Advertising列表失败，原因:" + e.getMessage(), e);
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return advertisingList;
	}
	

}
