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
 * @Title: AdImpl.java
 * @Package com.froad.thrift.service.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.thrift.TException;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.AdLogic;
import com.froad.logic.impl.AdLogicImpl;
import com.froad.po.Ad;
import com.froad.po.AdPosition;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.AdService;
import com.froad.thrift.vo.AdPageVoRes;
import com.froad.thrift.vo.AdPositionVo;
import com.froad.thrift.vo.AdVo;
import com.froad.thrift.vo.AddResultVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;


/**
 * 
 * <p>@Title: AdImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class AdServiceImpl extends BizMonitorBaseService implements AdService.Iface {
	private AdLogic adLogic = new AdLogicImpl();
	public AdServiceImpl() {}
	public AdServiceImpl(String name, String version) {
		super(name, version);
	}
	
	
	/** 非空验证
	* @Title: verification 
	* @Description: 
	* @author longyunbo
	* @param @param resultVo
	* @param @param addResultVo
	* @param @param adVo
	* @param @return
	* @return AddResultVo
	* @throws 
	*/
	public AddResultVo verification(ResultVo resultVo,AddResultVo addResultVo,Ad ad){
		if(ad.getClientId() == null || "".equals(ad.getClientId())){
			LogCvt.error("添加Ad失败,原因:客户端ClientId不能为空!");
			resultVo.setResultDesc("添加广告失败,原因:ClientId不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		if(ad.getTitle() == null || "".equals(ad.getTitle())){
			LogCvt.error("添加Ad失败,原因:标题Title不能为空!");
			resultVo.setResultDesc("添加广告失败,原因:标题不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		if(ad.getType() == null){
			LogCvt.error("添加Ad失败,原因:类型Type不能为空!");
			resultVo.setResultDesc("添加广告失败,原因:类型不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		if(ad.getBeginTime() == null){
			LogCvt.error("添加Ad失败,原因:开始时间BeginTime不能为空!");
			resultVo.setResultDesc("添加广告失败,原因:开始时间不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		if(ad.getEndTime() == null){
			LogCvt.error("添加Ad失败,原因:结束时间EndTime不能为空!");
			resultVo.setResultDesc("添加广告失败,原因:结束时间不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		if(ad.getAdPositionId() == null){
			LogCvt.error("添加Ad失败,原因:广告位IDAdPositionId不能为空!");
			resultVo.setResultDesc("添加广告失败,原因:广告位ID不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		resultVo.setResultCode(ResultCode.success.getCode());
		addResultVo.setResultVo(resultVo);
		return addResultVo;
	}

    /**
     * 增加 Ad
     * @param ad
     * @return long    主键ID
     */
	@Override
	public AddResultVo addAd(OriginVo originVo,AdVo adVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加Ad");
		AddResultVo addResultVo=new AddResultVo();
		ResultVo resultVo=new ResultVo();
		
		Ad ad = (Ad)BeanUtil.copyProperties(Ad.class, adVo);
		//非空验证
		addResultVo=verification(resultVo,addResultVo,ad);
		if(!StringUtils.equals(ResultCode.success.getCode(),addResultVo.getResultVo().getResultCode())){
			return addResultVo;
		}
		if(ad.getIsEnabled() == null){
			ad.setIsEnabled(true);
		}
		if((Integer)ad.getClickCount() ==null){
			ad.setClickCount(0);
		}
		ResultBean result = adLogic.addAd(ad);
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
			return addResultVo;
		}
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("添加广告信息成功");
		addResultVo.setResultVo(resultVo);
		addResultVo.setId((Long)result.getData());
		return addResultVo;
	}



    /**
     * 删除 Ad
     * @param ad
     * @return boolean    
     */
	@Override
	public ResultVo deleteAd(OriginVo originVo,AdVo adVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除Ad");
		Ad ad = (Ad)BeanUtil.copyProperties(Ad.class, adVo);
		ResultBean result = adLogic.deleteAd(ad);
		ResultVo resultVo=new ResultVo(ResultCode.success.getCode(),"删除广告成功");
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
		}
		
		return resultVo;
	}



    /**
     * 修改 Ad
     * @param ad
     * @return boolean    
     */
	@Override
	public ResultVo updateAd(OriginVo originVo,AdVo adVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改Ad");
		Ad ad = (Ad)BeanUtil.copyProperties(Ad.class, adVo);
		ResultBean result = adLogic.updateAd(ad);
		ResultVo resultVo=new ResultVo(ResultCode.success.getCode(),"修改广告成功");
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
		}
		
		return resultVo;
	}



    /**
     * 查询 Ad
     * @param ad
     * @return List<AdVo>
     */
	@Override
	public List<AdVo> getAd(AdVo adVo,AdPositionVo adPositionVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询Ad");
		Ad ad =null;
		AdPosition adposition=null;
			if(adVo!=null){
				ad = (Ad)BeanUtil.copyProperties(Ad.class, adVo);
			}
			if(adPositionVo!=null){
				adposition = (AdPosition)BeanUtil.copyProperties(AdPosition.class, adPositionVo);
			}
		List<Ad> adList = adLogic.findAd(ad,adposition);
		List<AdVo> adVoList = new ArrayList<AdVo>();
		for (Ad po : adList) {
			AdVo vo = (AdVo)BeanUtil.copyProperties(AdVo.class, po);
			adVoList.add(vo);
		}
		return adVoList;
	}



    /**
     * 分页查询 Ad
     * @param ad
     * @return AdPageVoRes
     */
	@Override
	public AdPageVoRes getAdByPage(PageVo pageVo, AdVo adVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("分页查询Ad");
		Page<Ad> page = (Page)BeanUtil.copyProperties(Page.class, pageVo);
		Ad ad = (Ad)BeanUtil.copyProperties(Ad.class, adVo);
		page = adLogic.findAdByPage(page, ad);

		AdPageVoRes adPageVoRes = new AdPageVoRes();
		List<AdVo> adVoList = new ArrayList<AdVo>();
		for (Ad po : page.getResultsContent()) {
			AdVo vo = (AdVo)BeanUtil.copyProperties(AdVo.class, po);
			adVoList.add(vo);
		}
		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		adPageVoRes.setPage(pageVo);
		adPageVoRes.setAdVoList(adVoList);
		
		return adPageVoRes;
	}
	
	/**
     * 查询 Ad
     * @return AdVo
     * 
     * @param id
     */
	@Override
	public AdVo getAdById(long id) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询AdById");
		Ad ad = adLogic.findAdById(id);
		AdVo vo = (AdVo)BeanUtil.copyProperties(AdVo.class, ad);
		return vo;
	}
	
	/**
     * 查询 Ad
     * @return AdVo
     * 
     * @param positionIds
     */
	@Override
	public Map<String, List<AdVo>> getAdByPositionIds(List<Long> positionIds)
			throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询AdByPositionIds");
		if(CollectionUtils.isEmpty(positionIds)) {
			LogCvt.error("getAdByPositionIds接口根据广告位id集合查询广告集合, 传入的参数为空");
			return new HashMap<String, List<AdVo>>();
		}
		Map<String, List<Ad>> map = adLogic.getAdByPositionIds(positionIds);
		LogCvt.debug("查询AdByPositionIds result = " + map);
		if(map != null && !map.isEmpty()) {
			Map<String, List<AdVo>> result = new HashMap<String, List<AdVo>>();
			Set<String> set = map.keySet();
			List<Ad> ads = null;
			List<AdVo> advs = null;
			AdVo adv = null;
			for( String key : set ){
				ads = map.get(key);
				if( CollectionUtils.isNotEmpty(ads) ){
					advs = new ArrayList<AdVo>();
					for( Ad ad : ads ){
						adv = (AdVo)BeanUtil.copyProperties(AdVo.class, ad);
						advs.add(adv);
					}
					result.put(key, advs);
				}else{
					result.put(key, new ArrayList<AdVo>());
				}
			}
			return result;
		}
		return new HashMap<String, List<AdVo>>();
	}


}
