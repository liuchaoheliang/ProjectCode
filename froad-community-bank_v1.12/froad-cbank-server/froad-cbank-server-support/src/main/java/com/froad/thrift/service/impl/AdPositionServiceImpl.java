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
 * @Title: AdPositionImpl.java
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
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.AdPositionLogic;
import com.froad.logic.impl.AdPositionLogicImpl;
import com.froad.po.AdPosition;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.AdPositionService;
import com.froad.thrift.vo.ActivitiesVo;
import com.froad.thrift.vo.AdPositionPageVoRes;
import com.froad.thrift.vo.AdPositionVo;
import com.froad.thrift.vo.AddResultVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;


/**
 * 
 * <p>@Title: AdPositionImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class AdPositionServiceImpl extends BizMonitorBaseService implements AdPositionService.Iface {
	private AdPositionLogic adPositionLogic = new AdPositionLogicImpl();
	public AdPositionServiceImpl() {}
	public AdPositionServiceImpl(String name, String version) {
		super(name, version);
	}
	
	/** 非空验证
	* @Title: verification 
	* @Description: 
	* @author longyunbo
	* @param @param resultVo
	* @param @param addResultVo
	* @param @param adPositionVo
	* @param @return
	* @return AddResultVo
	* @throws 
	*/
	public AddResultVo verification(ResultVo resultVo,AddResultVo addResultVo,AdPosition adPosition){

		if(adPosition.getClientId() == null || "".equals(adPosition.getClientId())){
			LogCvt.error("添加adPosition失败,原因:客户端ClientId不能为空!");
			resultVo.setResultDesc("添加广告位失败,原因:ClientId不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		if(adPosition.getName() == null || "".equals(adPosition.getName())){
			LogCvt.error("添加adPosition失败,原因:名称Name不能为空!");
			resultVo.setResultDesc("添加广告位失败,原因:名称不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		if(adPosition.getWidth()==null){
			LogCvt.error("添加adPosition失败,原因:宽度Width不能为空!");
			resultVo.setResultDesc("添加广告位失败,原因:宽度不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		if(adPosition.getHeight() ==null){
			LogCvt.error("添加adPosition失败,原因:高度Height不能为空!");
			resultVo.setResultDesc("添加广告位失败,原因:高度不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		if(adPosition.getPositionPage() == null || "".equals(adPosition.getPositionPage())){
			LogCvt.error("添加adPosition失败,原因:⻚面标识PositionPage不能为空!");
			resultVo.setResultDesc("添加广告位失败,原因:页面标识不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		if(adPosition.getPositionPoint() ==null){
			LogCvt.error("添加adPosition失败,原因:⻚面原点PositionPoint不能为空!");
			resultVo.setResultDesc("添加广告位失败,原因:页面原点不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		resultVo.setResultCode(ResultCode.success.getCode());
		addResultVo.setResultVo(resultVo);
		return addResultVo;
	}

    /**
     * 增加 AdPosition
     * @param adPosition
     * @return long    主键ID
     */
	@Override
	public AddResultVo addAdPosition(OriginVo originVo,AdPositionVo adPositionVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加AdPosition");
		AddResultVo addResultVo=new AddResultVo();
		ResultVo resultVo=new ResultVo();
		
	
		AdPosition adPosition = (AdPosition)BeanUtil.copyProperties(AdPosition.class, adPositionVo);
		//非空验证
		addResultVo=verification(resultVo,addResultVo,adPosition);
		if(!StringUtils.equals(ResultCode.success.getCode(),addResultVo.getResultVo().getResultCode())){
			return addResultVo;
		}
		if(adPosition.getIsEnable()==null){
			adPosition.setIsEnable(true);
		}
		ResultBean result = adPositionLogic.addAdPosition(adPosition);
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
			return addResultVo;
		}
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("添加广告位信息成功");
		addResultVo.setResultVo(resultVo);
		addResultVo.setId((Long)result.getData());
		return addResultVo;
	}



    /**
     * 删除 AdPosition
     * @param adPosition
     * @return boolean    
     */
	@Override
	public ResultVo deleteAdPosition(OriginVo originVo,AdPositionVo adPositionVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除AdPosition");
		AdPosition adPosition = (AdPosition)BeanUtil.copyProperties(AdPosition.class, adPositionVo);
		ResultBean result = adPositionLogic.deleteAdPosition(adPosition);
		ResultVo resultVo=new ResultVo(ResultCode.success.getCode(),"删除广告位成功");
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
		}
		
		return resultVo;
	}



    /**
     * 修改 AdPosition
     * @param adPosition
     * @return boolean    
     */
	@Override
	public ResultVo updateAdPosition(OriginVo originVo,AdPositionVo adPositionVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改AdPosition");
		AdPosition adPosition = (AdPosition)BeanUtil.copyProperties(AdPosition.class, adPositionVo); 
		ResultBean result = adPositionLogic.updateAdPosition(adPosition);
		ResultVo resultVo=new ResultVo(ResultCode.success.getCode(),"修改广告位成功");
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
		}
		
		return resultVo;
	}



    /**
     * 查询 AdPosition
     * @param adPosition
     * @return List<AdPositionVo>
     */
	@Override
	public List<AdPositionVo> getAdPosition(AdPositionVo adPositionVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询AdPosition");
		AdPosition adPosition = (AdPosition)BeanUtil.copyProperties(AdPosition.class, adPositionVo);
		List<AdPosition> adPositionList = adPositionLogic.findAdPosition(adPosition);
		List<AdPositionVo> adPositionVoList = new ArrayList<AdPositionVo>();
		for (AdPosition po : adPositionList) {
			AdPositionVo vo = (AdPositionVo)BeanUtil.copyProperties(AdPositionVo.class, po);
			adPositionVoList.add(vo);
		}
		return adPositionVoList;
	}



    /**
     * 分页查询 AdPosition
     * @param adPosition
     * @return AdPositionPageVoRes
     */
	@Override
	public AdPositionPageVoRes getAdPositionByPage(PageVo pageVo, AdPositionVo adPositionVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("分页查询AdPosition");
		Page<AdPosition> page = (Page)BeanUtil.copyProperties(Page.class, pageVo);
		AdPosition adPosition = (AdPosition)BeanUtil.copyProperties(AdPosition.class, adPositionVo);
		page = adPositionLogic.findAdPositionByPage(page, adPosition);

		AdPositionPageVoRes adPositionPageVoRes = new AdPositionPageVoRes();
		List<AdPositionVo> adPositionVoList = new ArrayList<AdPositionVo>();
		for (AdPosition po : page.getResultsContent()) {
			AdPositionVo vo = (AdPositionVo)BeanUtil.copyProperties(AdPositionVo.class, po);
			adPositionVoList.add(vo);
		}
		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		adPositionPageVoRes.setPage(pageVo);
		adPositionPageVoRes.setAdPositionVoList(adPositionVoList);
		return adPositionPageVoRes;

	}
	
	/**
     * 查询 AdPosition
     * @return AdPositionVo
     * 
     * @param clientId
     * @param id
     */
	@Override
	public AdPositionVo getAdPositionById(String clientId, long id) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询AdPosition");
		
		AdPosition adPosition = adPositionLogic.getAdPositionById(clientId, id);
		AdPositionVo result = (AdPositionVo)BeanUtil.copyProperties(AdPositionVo.class, adPosition);
		return result;
	}


}
