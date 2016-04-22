package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.froad.db.mysql.bean.Page;
import com.froad.enums.AdEnableStatus;
import com.froad.enums.AdLocationPositionPage;
import com.froad.enums.AdParamType;
import com.froad.enums.ResultCode;
import com.froad.enums.TerminalType;
import com.froad.logback.LogCvt;
import com.froad.logic.AdLocationLogic;
import com.froad.logic.impl.AdLocationLogicImpl;
import com.froad.po.AdLocation;
import com.froad.po.FindAdLocationResult;
import com.froad.po.FindAllAdLocationResult;
import com.froad.po.FindPageAdLocationResult;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.AdLocationService.Iface;
import com.froad.thrift.vo.AdLocationPageVoRes;
import com.froad.thrift.vo.AdLocationVo;
import com.froad.thrift.vo.AddResultVo;
import com.froad.thrift.vo.FindAdLocationResultVo;
import com.froad.thrift.vo.FindAllAdLocationResultVo;
import com.froad.thrift.vo.FindPageAdLocationResultVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;

/**
 * 
 * <p>@Title: AdLocationServiceImpl.java</p>
 * <p>Description: 广告位服务实现 </p> 
 * @author luo fan
 * @version 1.0
 * @created 2015年9月18日
 */
public class AdLocationServiceImpl extends BizMonitorBaseService implements Iface {

	private AdLocationLogic adLocationLogic = new AdLocationLogicImpl();
	
	public AdLocationServiceImpl() {}
	public AdLocationServiceImpl(String name, String version) {
		super(name, version);
	}
	
	/**
     * 增加 AdLocation
     * @param adLocation
     * @return AddResultVo
     * 
     * @param originVo
     * @param adLocationVo
     */
	@Override
	public AddResultVo addAdLocation(OriginVo originVo, AdLocationVo adLocationVo) throws TException {
		
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加 AdLocation");
		AddResultVo addResultVo = new AddResultVo();
		ResultVo resultVo = new ResultVo(ResultCode.success.getCode(),"");
		addResultVo.setResultVo(resultVo);
		
		AdLocation adLocation = (AdLocation)BeanUtil.copyProperties(AdLocation.class, adLocationVo);
		
		// 新增验证
		addVerification(resultVo, addResultVo, adLocation);
		if(!StringUtils.equals(ResultCode.success.getCode(), addResultVo.getResultVo().getResultCode())){
			return addResultVo;
		}
				
		// 启用类型如果为空 - 赋予初始值(启用)
		String enableStatus = adLocation.getEnableStatus();
		if( enableStatus == null || EMPTY.equals(enableStatus) ){
			adLocation.setEnableStatus(AdEnableStatus.enable.getCode());
		}
		
		// 调logic逻辑方法进行保存
		Result result = adLocationLogic.addAdLocation(adLocation);
		
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
			return addResultVo;
		}
		
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("添加广告位信息成功");
		addResultVo.setResultVo(resultVo);
		addResultVo.setId(Long.parseLong(result.getResultDesc()));
		return addResultVo;
	}
	private static final String EMPTY = "";
	//新增的验证
	private void addVerification(ResultVo resultVo, AddResultVo addResultVo, AdLocation adLocation){
		
		String name = adLocation.getName();
		if( name == null || EMPTY.equals(name) ){
			LogCvt.error("添加adPosition失败,原因:名称Name不能为空!");
			resultVo.setResultDesc("添加广告位失败,原因:名称不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return ;
		}
		String terminalType = adLocation.getTerminalType();
		if( terminalType == null || EMPTY.equals(terminalType) ){
			LogCvt.error("添加adPosition失败,原因:名称TerminalType不能为空!");
			resultVo.setResultDesc("添加广告位失败,原因:终端类型不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return ;
		}else{
			if( TerminalType.getType(terminalType) == null ){
				LogCvt.error("添加adPosition失败,原因:TerminalType不在有效值范围内!");
				resultVo.setResultDesc("添加广告位失败,原因:终端类型的值不在有效值范围内!");
				resultVo.setResultCode(ResultCode.failed.getCode());
				addResultVo.setResultVo(resultVo);
				return ;
			}
		}
		String positionPage = adLocation.getPositionPage();
		if( positionPage == null || EMPTY.equals(positionPage) || positionPage.length() > 16 ){
			LogCvt.error("添加adPosition失败,原因:PositionPage不能为空!");
			resultVo.setResultDesc("添加广告位失败,原因:位置(页面标识)不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return ;
		}else{
			if( AdLocationPositionPage.getType(positionPage) == null ){
				LogCvt.error("添加adPosition失败,原因:PositionPage不在有效值范围内!");
				resultVo.setResultDesc("添加广告位失败,原因:页面标识的值不在有效值范围内!");
				resultVo.setResultCode(ResultCode.failed.getCode());
				addResultVo.setResultVo(resultVo);
				return ;
			}
		}
		Integer sizeLimit = adLocation.getSizeLimit();
		if( sizeLimit == null || sizeLimit < 0 ){
			LogCvt.error("添加adPosition失败,原因:SizeLimit不能为空!");
			resultVo.setResultDesc("添加广告位失败,原因:大小限制不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return ;
		}
		Integer width = adLocation.getWidth();
		if( width == null || width < 0 ){
			LogCvt.error("添加adPosition失败,原因:Width不能为空!");
			resultVo.setResultDesc("添加广告位失败,原因:宽度不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return ;
		}
		Integer height = adLocation.getHeight();
		if( height == null || height < 0 ){
			LogCvt.error("添加adPosition失败,原因:Height不能为空!");
			resultVo.setResultDesc("添加广告位失败,原因:高度不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return ;
		}
		String enableStatus = adLocation.getEnableStatus();
		if( enableStatus != null && !EMPTY.equals(enableStatus) ){
			if( AdEnableStatus.getType(enableStatus) == null ){
				LogCvt.error("添加adPosition失败,原因:EnableStatus不在有效值范围内!");
				resultVo.setResultDesc("添加广告位失败,原因:启用类型的值不在有效值范围内!");
				resultVo.setResultCode(ResultCode.failed.getCode());
				addResultVo.setResultVo(resultVo);
				return ;
			}
		}
		String paramOneType = adLocation.getParamOneType();
		if( paramOneType != null && !EMPTY.equals(paramOneType) ){
			if( AdParamType.getType(paramOneType) == null ){
				LogCvt.error("添加adPosition失败,原因:ParamOneType不在有效值范围内!");
				resultVo.setResultDesc("添加广告位失败,原因:第一附加参数类型的值不在有效值范围内!");
				resultVo.setResultCode(ResultCode.failed.getCode());
				addResultVo.setResultVo(resultVo);
				return ;
			}
		}
		String paramTwoType = adLocation.getParamTwoType();
		if( paramTwoType != null && !EMPTY.equals(paramTwoType) ){
			if( AdParamType.getType(paramTwoType) == null ){
				LogCvt.error("添加adPosition失败,原因:ParamTwoType不在有效值范围内!");
				resultVo.setResultDesc("添加广告位失败,原因:第二附加参数类型的值不在有效值范围内!");
				resultVo.setResultCode(ResultCode.failed.getCode());
				addResultVo.setResultVo(resultVo);
				return ;
			}
		}
		String paramThreeType = adLocation.getParamThreeType();
		if( paramThreeType != null && !EMPTY.equals(paramThreeType) ){
			if( AdParamType.getType(paramThreeType) == null ){
				LogCvt.error("添加adPosition失败,原因:ParamThreeType不在有效值范围内!");
				resultVo.setResultDesc("添加广告位失败,原因:第三附加参数类型的值不在有效值范围内!");
				resultVo.setResultCode(ResultCode.failed.getCode());
				addResultVo.setResultVo(resultVo);
				return ;
			}
		}
	}
	
	@Override
	public ResultVo deleteAdLocation(OriginVo originVo, long id) throws TException {
		
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("暂不支持删除 AdLocation");
		return new ResultVo(ResultCode.failed.getCode(), "暂不支持删除 AdLocation");
	}

	/**
     * 编辑更新 AdLocation
     * @param adLocation
     * @return ResultVo
     * 
     * @param originVo
     * @param adLocationVo
     */
	@Override
	public ResultVo updateAdLocation(OriginVo originVo, AdLocationVo adLocationVo) throws TException {
		
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("编辑更新 AdLocation");
		ResultVo resultVo = null;
		
		AdLocation adLocation = (AdLocation)BeanUtil.copyProperties(AdLocation.class, adLocationVo);
		
		Long id = adLocation.getId();
		if( id == null || id <= 0 ){
			resultVo = new ResultVo(ResultCode.failed.getCode(), "编辑更新 AdLocation id不能为空");
			return resultVo;
		}
		
		// 调logic逻辑方法进行编辑更新
		Result result = adLocationLogic.updateAdLocation(adLocation);
				
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo = new ResultVo(result.getResultCode(), result.getResultDesc());
			return resultVo;
		}
		
		resultVo = new ResultVo(ResultCode.success.getCode(), "编辑更新广告位信息成功");
		
		return resultVo;
	}
	
	@Override
	public FindAllAdLocationResultVo getAdLocation(AdLocationVo adLocationVo) throws TException {
		
		LogCvt.info("查询 AdLocation 列表");
		
		FindAllAdLocationResultVo findAllAdLocationResultVo = new FindAllAdLocationResultVo();
		AdLocation adLocation = (AdLocation)BeanUtil.copyProperties(AdLocation.class, adLocationVo);
		
		// 调logic逻辑方法进行查询
		FindAllAdLocationResult findAllAdLocationResult = adLocationLogic.findAdLocation(adLocation);
		List<AdLocation> adLocationList = findAllAdLocationResult.getAdLocationList();
		
		List<AdLocationVo> adLocationVoList = new ArrayList<AdLocationVo>();
		if( adLocationList != null && adLocationList.size() > 0 ){
			for( AdLocation po : adLocationList ){
				AdLocationVo vo = (AdLocationVo)BeanUtil.copyProperties(AdLocationVo.class, po);
				adLocationVoList.add(vo);
			}
		}else{
			LogCvt.info("查询 AdLocation 列表 结果为空");
		}
		
		ResultVo resultVo = (ResultVo)BeanUtil.copyProperties(ResultVo.class, findAllAdLocationResult.getResult());
		findAllAdLocationResultVo.setResultVo(resultVo);
		findAllAdLocationResultVo.setAdLocationVoList(adLocationVoList);
		return findAllAdLocationResultVo;
	}

	@Override
	public FindPageAdLocationResultVo getAdLocationByPage(PageVo pageVo, AdLocationVo adLocationVo) throws TException {
		
		LogCvt.info("查询 AdLocation 分页");
		
		Page<AdLocation> page = (Page)BeanUtil.copyProperties(Page.class, pageVo);
		AdLocation adLocation = (AdLocation)BeanUtil.copyProperties(AdLocation.class, adLocationVo);
		
		FindPageAdLocationResultVo findPageAdLocationResultVo = new FindPageAdLocationResultVo();
		AdLocationPageVoRes adLocationPageVoRes = new AdLocationPageVoRes();
		
		// 调logic逻辑方法进行查询
		FindPageAdLocationResult findPageAdLocationResult = adLocationLogic.findAdLocationByPage(page, adLocation);
		page = findPageAdLocationResult.getPage();
		
		List<AdLocationVo> adLocationVoList = new ArrayList<AdLocationVo>();
		for( AdLocation po : page.getResultsContent() ){
			AdLocationVo vo = (AdLocationVo)BeanUtil.copyProperties(AdLocationVo.class, po);
			adLocationVoList.add(vo);
		}
		
		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		
		adLocationPageVoRes.setPage(pageVo);
		adLocationPageVoRes.setAdLocationVoList(adLocationVoList);
		
		ResultVo resultVo = (ResultVo)BeanUtil.copyProperties(ResultVo.class, findPageAdLocationResult.getResult());
		findPageAdLocationResultVo.setResultVo(resultVo);
		findPageAdLocationResultVo.setAdLocationPageVoRes(adLocationPageVoRes);
		
		return findPageAdLocationResultVo;
	}

	@Override
	public FindAdLocationResultVo getAdLocationById(long id) throws TException {
		
		LogCvt.info("查询 AdLocation 单个");
		
		FindAdLocationResultVo findAdLocationResultVo = new FindAdLocationResultVo();
		FindAdLocationResult findAdLocationResult = adLocationLogic.getAdLocationById(id);
		
		ResultVo resultVo = (ResultVo)BeanUtil.copyProperties(ResultVo.class, findAdLocationResult.getResult());
		AdLocationVo adLocationVo = (AdLocationVo)BeanUtil.copyProperties(AdLocationVo.class, findAdLocationResult.getAdLocation());
		findAdLocationResultVo.setResultVo(resultVo);
		findAdLocationResultVo.setAdLocationVo(adLocationVo);
		
		return findAdLocationResultVo;
	}

	@Override
	public ResultVo disabledAdLocation(OriginVo originVo, long id) throws TException {
		
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("禁用 AdLocation");
		
		Result result = adLocationLogic.disabledAdLocation(id);
		ResultVo resultVo = (ResultVo)BeanUtil.copyProperties(ResultVo.class, result);
		
		return resultVo;
	}

}
