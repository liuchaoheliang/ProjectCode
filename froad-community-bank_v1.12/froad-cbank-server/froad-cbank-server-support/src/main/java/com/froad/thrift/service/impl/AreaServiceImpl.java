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
 * @Title: AreaImpl.java
 * @Package com.froad.thrift.service.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.froad.util.BeanUtil;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.AreaLogic;
import com.froad.logic.impl.AreaLogicImpl;
import com.froad.po.Area;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.AreaService;
import com.froad.thrift.vo.AddResultVo;
import com.froad.thrift.vo.AreaPageVoRes;
import com.froad.thrift.vo.AreaVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;


/**
 * 
 * <p>@Title: AreaImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class AreaServiceImpl extends BizMonitorBaseService implements AreaService.Iface {
	private AreaLogic areaLogic = new AreaLogicImpl();
	public AreaServiceImpl() {}
	public AreaServiceImpl(String name, String version) {
		super(name, version);
	}

	
	public AddResultVo verification(ResultVo resultVo,AddResultVo addResultVo,Area area){
		
		if(area.getName()==null || "".equals(area.getName())){
			LogCvt.error("添加地区失败,原因:名称Name不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:名称不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		if(area.getTreePath() == null || "".equals(area.getTreePath())){
			LogCvt.error("添加地区失败,原因:树路径TreePath不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:树路径不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		if(area.getClientId() == null || "".equals(area.getClientId())){
			LogCvt.error("添加地区失败,原因:客户端Id clientId 不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:客户端Id clientId 不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		resultVo.setResultCode(ResultCode.success.getCode());
		addResultVo.setResultVo(resultVo);
		return addResultVo;
		
	}
	
    /**
     * 增加 Area
     * @param area
     * @return long    主键ID
     */
	@Override
	public AddResultVo addArea(OriginVo originVo,AreaVo areaVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加Area param:"+JSON.toJSONString(areaVo));
		AddResultVo addResultVo=new AddResultVo();
		ResultVo resultVo=new ResultVo();
		Area area= (Area)BeanUtil.copyProperties(Area.class, areaVo);
		//非空验证
		addResultVo=verification(resultVo,addResultVo,area);
		if(!StringUtils.equals(ResultCode.success.getCode(),addResultVo.getResultVo().getResultCode())){
			return addResultVo;
		}
		if(area.getIsEnable() == null){
			area.setIsEnable(true);
		}
		ResultBean result = areaLogic.addArea(area);
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("添加地区信息成功");
		addResultVo.setResultVo(resultVo);
		addResultVo.setId((Long)result.getData());
		return addResultVo;
	}



    /**
     * 删除 Area
     * @param area
     * @return boolean    
     */
	@Override
	public ResultVo deleteArea(OriginVo originVo,AreaVo areaVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除Area param:"+JSON.toJSONString(areaVo));
		Area area= (Area)BeanUtil.copyProperties(Area.class, areaVo);
		ResultBean result = areaLogic.deleteArea(area);
		ResultVo resultVo=new ResultVo(ResultCode.success.getCode(),"删除地区成功");
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
		}
		
		return resultVo;
	}



    /**
     * 修改 Area
     * @param area
     * @return boolean    
     */
	@Override
	public ResultVo updateArea(OriginVo originVo,AreaVo areaVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改Area param:"+JSON.toJSONString(areaVo));
		Area area= (Area)BeanUtil.copyProperties(Area.class, areaVo);
		ResultBean result = areaLogic.updateArea(area);
		ResultVo resultVo=new ResultVo(ResultCode.success.getCode(),"修改地区成功");
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
		}
		
		return resultVo;

	}



    /**
     * 查询 Area
     * @param area
     * @return List<AreaVo>
     */
	@Override
	public List<AreaVo> getArea(AreaVo areaVo) throws TException {
		// TODO Auto-generated method stub
		
		List<AreaVo> areaVoList =null;
		try {
			LogCvt.debug("查询Area param:"+JSON.toJSONString(areaVo));
			Area area= (Area)BeanUtil.copyProperties(Area.class, areaVo);
			LogCvt.debug("查询Area param:"+JSON.toJSONString(area));
			List<Area> areaList = areaLogic.findArea(area);
			areaVoList = new ArrayList<AreaVo>();
			for (Area po : areaList) {
				AreaVo vo= (AreaVo)BeanUtil.copyProperties(AreaVo.class, po);
				areaVoList.add(vo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			LogCvt.error("查询getArea()失败"+e.getMessage());
		}
		return areaVoList;
	}



    /**
     * 分页查询 Area
     * @param area
     * @return AreaPageVoRes
     */
	@Override
	public AreaPageVoRes getAreaByPage(PageVo pageVo, AreaVo areaVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("分页查询Area param:"+JSON.toJSONString(areaVo)+ "page:"+JSON.toJSONString(pageVo));
		Page<Area> page = (Page)BeanUtil.copyProperties(Page.class, pageVo);
		Area area = (Area)BeanUtil.copyProperties(Area.class, areaVo);
		page = areaLogic.findAreaByPage(page, area);

		AreaPageVoRes areaPageVoRes = new AreaPageVoRes();
		List<AreaVo> areaVoList = new ArrayList<AreaVo>();
		for (Area po : page.getResultsContent()) {
			AreaVo vo = (AreaVo)BeanUtil.copyProperties(AreaVo.class, po);
			areaVoList.add(vo);
		}
		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		areaPageVoRes.setPage(pageVo);
		areaPageVoRes.setAreaVoList(areaVoList);
		return areaPageVoRes;
	}

	/**  根据id获取地区信息
	* @Title: findAreaById 
	* @Description: 
	* @author longyunbo
	* @param  id
	* @param 
	* @return AreaVo
	* @throws 
	*/

	@Override
	public AreaVo findAreaById(long id) throws TException {
		// TODO Auto-generated method stub
			LogCvt.info("查询Area单个");
			AreaVo areaVo =null;
			try {
				Area area = areaLogic.findAreaById(id);
				areaVo = (AreaVo)BeanUtil.copyProperties(AreaVo.class, area);
				
			} catch (Exception e) {
				areaVo=null;
				LogCvt.error("查询Area失败"+e.getMessage());
			}
			
			return areaVo;
	}

	/**
     * 根据 areaCode 获取地区
     * @return AreaVo
     * 
     * @param areaCode
     */
	@Override
	public AreaVo findAreaByAreaCode(String areaCode) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询Area单个");
		AreaVo areaVo =null;
		try {
			Area area = areaLogic.findAreaByAreaCode(areaCode);
			areaVo = (AreaVo)BeanUtil.copyProperties(AreaVo.class, area);
			
		} catch (Exception e) {
			areaVo=new AreaVo();
			LogCvt.error("查询Area失败"+e.getMessage());
		}
		
		return areaVo;
	}
	
	/**
     * 根据 areaCode 和 clientId 获取地区 
     * @return AreaVo
     */
	@Override
	public AreaVo findAreaByAreaCodeAndClientId(String areaCode, String clientId)
			throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询Area单个");
		AreaVo areaVo =null;
		try {
			Area area = areaLogic.findAreaByAreaCodeAndClientId(areaCode, clientId);
			areaVo = (AreaVo)BeanUtil.copyProperties(AreaVo.class, area);
			
		} catch (Exception e) {
			areaVo=new AreaVo();
			LogCvt.error("查询Area失败"+e.getMessage());
		}
		
		return areaVo;
	}
	/** 根据id获取子集地区
	* @Title: findChildrenInfoById 
	* @Description: 
	* @author longyunbo
	* @param  id
	* @param  areaCode
	* @param  InvocationTargetException
	* @return List<AreaVo>
	* @throws 
	*/
	@Override
	public List<AreaVo> findChildrenInfoById(long id, String areaCode) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询Area");
		List<AreaVo> areaVoList = new ArrayList<AreaVo>();
		List<Area> areaList = null;
		try {
			areaList = areaLogic.findChildrenInfoById(id, areaCode); 
			if(areaList !=null && !areaList.isEmpty()){
				for (Area po : areaList) {
					AreaVo vo = (AreaVo)BeanUtil.copyProperties(AreaVo.class, po);
					areaVoList.add(vo);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			LogCvt.error("查询地区失败:"+e);
		}
		
		return areaVoList;
	}
	
	/** 根据id获取子集地区
	* @Title: findChildrenInfo 
	* @Description: 
	* @param  id
	* @param  areaCode
	* @param  clientId
	* @param  InvocationTargetException
	* @return List<AreaVo>
	* @throws 
	*/
	@Override
	public List<AreaVo> findChildrenInfo(long id, String areaCode,
			String clientId) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询Area");
		List<AreaVo> areaVoList = new ArrayList<AreaVo>();
		List<Area> areaList = null;
		try {
			areaList = areaLogic.findChildrenInfo(id, areaCode, clientId); 
			if(areaList !=null && !areaList.isEmpty()){
				for (Area po : areaList) {
					AreaVo vo = (AreaVo)BeanUtil.copyProperties(AreaVo.class, po);
					areaVoList.add(vo);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			LogCvt.error("查询地区失败:"+e);
		}
		
		return areaVoList;
	}
	/**
     * 判断areaCode是否属于clientId的范围内
     * @return boolean
     * 
     * @param areaCode
     * @param clientId
     */
	@Override
	public boolean isAreaCodeScopeOfClientId(String areaCode, String clientId)
			throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("判断areaCode是否属于clientId的范围内");
		boolean result = false;
		try{
			result = areaLogic.isAreaCodeScopeOfClientId(areaCode, clientId);
		} catch (Exception e) {
			// TODO: handle exception
			LogCvt.error("判断areaCode是否属于clientId的范围内 失败:"+e);
		}
		return result;
	}
	
	/**
     * 根据clientId获取省级地区
     * @return List<AreaVo>
     * 
     * @param clientId
     */
	@Override
	public List<AreaVo> findProvinceAreaByClientId(String clientId) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("根据clientId获取省级地区");
		List<AreaVo> areaVos = new ArrayList<AreaVo>();
		try {
			List<Area> areas = areaLogic.findProvinceAreaByClientId(clientId);
			if( areas != null && areas.size() > 0 ){
				for( Area area : areas ){
					areaVos.add((AreaVo)BeanUtil.copyProperties(AreaVo.class, area));
				}
			}
			
		} catch (Exception e) {
			areaVos = new ArrayList<AreaVo>();
			LogCvt.error("获取省级地区失败"+e.getMessage());
		}
		
		return areaVos;
	}
	
}
