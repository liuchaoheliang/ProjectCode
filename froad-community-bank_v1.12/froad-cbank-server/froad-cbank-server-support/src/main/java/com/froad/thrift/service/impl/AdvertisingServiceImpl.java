package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.thrift.TException;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.froad.db.mysql.bean.Page;
import com.froad.enums.AdEnableStatus;
import com.froad.enums.AdvertisingType;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.AdvertisingLogic;
import com.froad.logic.impl.AdvertisingLogicImpl;
import com.froad.po.Advertising;
import com.froad.po.FindAdvertisingResult;
import com.froad.po.FindAllAdvertisingParam;
import com.froad.po.FindAllAdvertisingResult;
import com.froad.po.FindPageAdvertisingResult;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.AdvertisingService.Iface;
import com.froad.thrift.vo.AddResultVo;
import com.froad.thrift.vo.AdvertisingPageVoRes;
import com.froad.thrift.vo.AdvertisingVo;
import com.froad.thrift.vo.FindAdvertisingResultVo;
import com.froad.thrift.vo.FindAllAdvertisingParamVo;
import com.froad.thrift.vo.FindAllAdvertisingResultVo;
import com.froad.thrift.vo.FindPageAdvertisingResultVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;

/**
 * 
 * <p>@Title: AdLocationServiceImpl.java</p>
 * <p>Description: 广告服务实现 </p> 
 * @author luo fan
 * @version 1.0
 * @created 2015年9月22日
 */
public class AdvertisingServiceImpl extends BizMonitorBaseService implements Iface {

	private AdvertisingLogic advertisingLogic = new AdvertisingLogicImpl(); 
	
	public AdvertisingServiceImpl() {}
	public AdvertisingServiceImpl(String name, String version) {
		super(name, version);
	}
	
	/**
     * 增加 AdvertisingVo
     * @param advertisingVo
     * @return AddResultVo
     * 
     * @param originVo
     * @param advertisingVo
     */
	@Override
	public AddResultVo addAdvertising(OriginVo originVo, AdvertisingVo advertisingVo) throws TException {
		
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加 Advertising");
		AddResultVo addResultVo = new AddResultVo();
		ResultVo resultVo = new ResultVo(ResultCode.success.getCode(),"");
		addResultVo.setResultVo(resultVo);
		
		Advertising advertising = (Advertising)BeanUtil.copyProperties(Advertising.class, advertisingVo);
		
		// 新增验证
		verification(resultVo, addResultVo, advertising, true);
		if(!StringUtils.equals(ResultCode.success.getCode(), addResultVo.getResultVo().getResultCode())){
			return addResultVo;
		}
		
		// 点击次数如果为空 - 赋予初始值(0)
		Integer clickCount = advertising.getClickCount();
		if( clickCount == null || clickCount < 0 ){
			advertising.setClickCount(0);
		}
		
		// 调logic逻辑方法进行保存
		Result result = advertisingLogic.addAdvertising(advertising);
		
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
			return addResultVo;
		}
		
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("添加广告信息成功");
		addResultVo.setResultVo(resultVo);
		addResultVo.setId(Long.parseLong(result.getResultDesc()));
		return addResultVo;
	}
	private static final String EMPTY = "";
	//验证
	private void verification(ResultVo resultVo, AddResultVo addResultVo, Advertising advertising, boolean isAdd){
		String clientId = advertising.getClientId();
		if( clientId == null || EMPTY.equals(clientId) ){
			LogCvt.error(getOperName(isAdd)+"advertising失败,原因:客户端ClientId不能为空!");
			resultVo.setResultDesc(getOperName(isAdd)+"广告失败,原因:ClientId不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return ;
		}
		String title = advertising.getTitle();
		if( title == null || EMPTY.equals(title) ){
			LogCvt.error(getOperName(isAdd)+"advertising失败,原因:标题Title不能为空!");
			resultVo.setResultDesc(getOperName(isAdd)+"广告失败,原因:标题不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return ;
		}
		Long adLocationId = advertising.getAdLocationId();
		if( adLocationId == null || adLocationId <= 0 ){
			LogCvt.error(getOperName(isAdd)+"advertising失败,原因:广告位ID-AdLocationId不能为空!");
			resultVo.setResultDesc(getOperName(isAdd)+"广告失败,原因:广告位ID不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return ;
		}
		String type = advertising.getType();
		if( type == null || EMPTY.equals(type) ){
			LogCvt.error(getOperName(isAdd)+"advertising失败,原因:类型Type不能为空!");
			resultVo.setResultDesc(getOperName(isAdd)+"广告失败,原因:类型不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return ;
		}else{
			if( AdvertisingType.getType(type) == null ){
				LogCvt.error(getOperName(isAdd)+"advertising失败,原因:Type不在有效值范围内!");
				resultVo.setResultDesc(getOperName(isAdd)+"广告失败,原因:类型的值不在有效值范围内!");
				resultVo.setResultCode(ResultCode.failed.getCode());
				addResultVo.setResultVo(resultVo);
				return ;
			}
		}
		Integer orderSn = advertising.getOrderSn();
		if( orderSn == null || orderSn < 0 ){
			LogCvt.error(getOperName(isAdd)+"advertising失败,原因:序号OrderSn不能为空!");
			resultVo.setResultDesc(getOperName(isAdd)+"广告失败,原因:序号不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return ;
		}
		Date beginTime = advertising.getBeginTime();
		if( beginTime == null || beginTime.getTime() <= 0 ){
			LogCvt.error(getOperName(isAdd)+"advertising失败,原因:开始时间BeginTime不能为空!");
			resultVo.setResultDesc(getOperName(isAdd)+"广告失败,原因:开始时间不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return ;
		}
		Date endTime = advertising.getEndTime();
		if( endTime == null || endTime.getTime() <= 0 ){
			LogCvt.error(getOperName(isAdd)+"advertising失败,原因:结束时间EndTime不能为空!");
			resultVo.setResultDesc(getOperName(isAdd)+"广告失败,原因:结束时间不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return ;
		}
		if( beginTime.getTime() >= endTime.getTime() ){
			LogCvt.error(getOperName(isAdd)+"advertising失败,原因:结束时间EndTime必须大于开始时间BeginTime!");
			resultVo.setResultDesc(getOperName(isAdd)+"广告失败,原因:结束时间必须大于开始时间!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return ;
		}
		Boolean isBlankTarge = advertising.getIsBlankTarge();
		if( isBlankTarge == null ){
			LogCvt.error(getOperName(isAdd)+"advertising失败,原因:是否新窗口打开IsBlankTarge不能为空!");
			resultVo.setResultDesc(getOperName(isAdd)+"广告失败,原因:是否新窗口打开不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return ;
		}
		String enableStatus = advertising.getEnableStatus();
		if( enableStatus == null || EMPTY.equals(enableStatus) ){
			LogCvt.error(getOperName(isAdd)+"advertising失败,原因:启用状态EnableStatus不能为空!");
			resultVo.setResultDesc(getOperName(isAdd)+"广告失败,原因:启用状态不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return ;
		}else{
			if( AdEnableStatus.getType(enableStatus) == null ){
				LogCvt.error(getOperName(isAdd)+"advertising失败,原因:EnableStatus不在有效值范围内!");
				resultVo.setResultDesc(getOperName(isAdd)+"广告失败,原因:启用类型的值不在有效值范围内!");
				resultVo.setResultCode(ResultCode.failed.getCode());
				addResultVo.setResultVo(resultVo);
				return ;
			}
		}
	}
	private static final String ADD = "添加";
	private static final String UPDATE = "编辑更新";
	private static String getOperName(boolean isAdd){
		return isAdd?ADD:UPDATE;
	}

	/**
     * 删除 AdvertisingVo
     * @param id
     * @return ResultVo
     * 
     * @param originVo
     * @param id
     */
	@Override
	public ResultVo deleteAdvertising(OriginVo originVo, long id) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("暂不支持删除 Advertising");
		return new ResultVo(ResultCode.failed.getCode(), "暂不支持删除 Advertising");
	}

	/**
     * 修改 AdvertisingVo
     * @param advertisingVo
     * @return ResultVo
     * 
     * @param originVo
     * @param advertisingVo
     */
	@Override
	public ResultVo updateAdvertising(OriginVo originVo, AdvertisingVo advertisingVo) throws TException {
		
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("编辑更新 Advertising");
		ResultVo resultVo = new ResultVo(ResultCode.success.getCode(),"");
		
		Advertising advertising = (Advertising)BeanUtil.copyProperties(Advertising.class, advertisingVo);
		
		Long id = advertising.getId();
		if( id == null || id <= 0 ){
			resultVo = new ResultVo(ResultCode.failed.getCode(), "编辑更新 Advertising id不能为空");
			return resultVo;
		}
		
		// 验证
		AddResultVo addResultVo = new AddResultVo();
		addResultVo.setResultVo(resultVo);
		verification(resultVo, addResultVo, advertising, false);
		if(!StringUtils.equals(ResultCode.success.getCode(), addResultVo.getResultVo().getResultCode())){
			return resultVo;
		}
		
		// 调logic逻辑方法进行编辑更新
		Result result = advertisingLogic.updateAdvertising(advertising);
		
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo = new ResultVo(result.getResultCode(), result.getResultDesc());
			return resultVo;
		}
		
		resultVo = new ResultVo(ResultCode.success.getCode(), "编辑更新广告信息成功");
		
		return resultVo;
	}

	/**
     * 查询 AdvertisingVo 列表
     * @param advertisingVo
     * @return List<AdvertisingVo>
     * 
     * @param advertisingVo
     */
	@Override
	public FindAllAdvertisingResultVo getAdvertising(AdvertisingVo advertisingVo) throws TException {
		
		LogCvt.info("查询 Advertising 列表");
		
		FindAllAdvertisingResultVo findAllAdvertisingResultVo = new FindAllAdvertisingResultVo();
		Advertising advertising = (Advertising)BeanUtil.copyProperties(Advertising.class, advertisingVo);
		
		FindAllAdvertisingResult findAllAdvertisingResult = advertisingLogic.findAdvertising(advertising);
		List<Advertising> advertisingList = findAllAdvertisingResult.getAdvertisingList();
		
		List<AdvertisingVo> advertisingVoList = new ArrayList<AdvertisingVo>();
		if( advertisingList != null && advertisingList.size() > 0 ){
			for( Advertising po : advertisingList ){
				AdvertisingVo vo = (AdvertisingVo)BeanUtil.copyProperties(AdvertisingVo.class, po);
				advertisingVoList.add(vo);
			}
		}else{
			LogCvt.info("查询 Advertising 列表 结果为空");
		}
		
		ResultVo resultVo = (ResultVo)BeanUtil.copyProperties(ResultVo.class, findAllAdvertisingResult.getResult());
		findAllAdvertisingResultVo.setResultVo(resultVo);
		findAllAdvertisingResultVo.setAdvertisingVoList(advertisingVoList);
		return findAllAdvertisingResultVo;
	}

	/**
     * 分页查询 AdvertisingVo
     * @param advertisingVo
     * @return AdvertisingPageVoRes
     * 
     * @param page
     * @param advertisingVo
     */
	@Override
	public FindPageAdvertisingResultVo getAdvertisingByPage(PageVo pageVo, AdvertisingVo advertisingVo) throws TException {
		
		LogCvt.info("查询 Advertising 分页");
		
		Page<Advertising> page = (Page)BeanUtil.copyProperties(Page.class, pageVo);
		Advertising advertising = (Advertising)BeanUtil.copyProperties(Advertising.class, advertisingVo);
		
		FindPageAdvertisingResultVo findPageAdvertisingResultVo = new FindPageAdvertisingResultVo();
		AdvertisingPageVoRes advertisingPageVoRes = new AdvertisingPageVoRes();
		
		FindPageAdvertisingResult findPageAdvertisingResult = advertisingLogic.findAdvertisingByPage(page, advertising);
		page = findPageAdvertisingResult.getPage();
		
		List<AdvertisingVo> advertisingVoList = new ArrayList<AdvertisingVo>();
		for( Advertising po : page.getResultsContent() ){
			AdvertisingVo vo = (AdvertisingVo)BeanUtil.copyProperties(AdvertisingVo.class, po);
			advertisingVoList.add(vo);
		}
		
		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		
		advertisingPageVoRes.setPage(pageVo);
		advertisingPageVoRes.setAdvertisingVoList(advertisingVoList);
		
		ResultVo resultVo = (ResultVo)BeanUtil.copyProperties(ResultVo.class, findPageAdvertisingResult.getResult());
		findPageAdvertisingResultVo.setResultVo(resultVo);
		findPageAdvertisingResultVo.setAdvertisingPageVoRes(advertisingPageVoRes);
		
		return findPageAdvertisingResultVo;
	}

	/**
     * 查询 AdvertisingVo 单个
     * @return AdvertisingVo
     * 
     * @param id
     */
	@Override
	public FindAdvertisingResultVo getAdvertisingById(String clientId, long id) throws TException {
		
		LogCvt.info("查询 Advertising 单个");
		FindAdvertisingResultVo findAdvertisingResultVo = new FindAdvertisingResultVo();
		FindAdvertisingResult findAdvertisingResult = advertisingLogic.getAdvertisingById(clientId, id);
		
		ResultVo resultVo = (ResultVo)BeanUtil.copyProperties(ResultVo.class, findAdvertisingResult.getResult());
		AdvertisingVo advertisingVo = (AdvertisingVo)BeanUtil.copyProperties(AdvertisingVo.class, findAdvertisingResult.getAdvertising());
		
		findAdvertisingResultVo.setResultVo(resultVo);
		findAdvertisingResultVo.setAdvertisingVo(advertisingVo);
		
		return findAdvertisingResultVo;
	}
	
	/**
     * 页面优化查询 AdvertisingVo 列表
     * @return FindAllAdvertisingResultVo
     * 
     * @param findAllAdvertisingParamVo
     */
	@Override
	public FindAllAdvertisingResultVo pageOptFindAdvertisings(FindAllAdvertisingParamVo findAllAdvertisingParamVo) throws TException {
		
		LogCvt.info("页面优化查询 Advertising 列表");
		FindAllAdvertisingResultVo findAllAdvertisingResultVo = new FindAllAdvertisingResultVo();
		
		// 校验
		String terminalType = findAllAdvertisingParamVo.getTerminalType();
		String positionPage = findAllAdvertisingParamVo.getPositionPage();
		String clientId = findAllAdvertisingParamVo.getClientId();
		if( terminalType == null || EMPTY.equals(terminalType) || positionPage == null || EMPTY.equals(positionPage) ){
			findAllAdvertisingResultVo.setResultVo(new ResultVo(ResultCode.failed.getCode(), "终端类型(terminalType)和页面标识(positionPage)都不能为空"));
			return findAllAdvertisingResultVo;
		}
		if( clientId == null || EMPTY.equals(clientId) ){
			findAllAdvertisingResultVo.setResultVo(new ResultVo(ResultCode.failed.getCode(), "客户端(clientId)不能为空"));
			return findAllAdvertisingResultVo;
		}
		
		FindAllAdvertisingParam findAllAdvertisingParam = (FindAllAdvertisingParam)BeanUtil.copyProperties(FindAllAdvertisingParam.class, findAllAdvertisingParamVo);
		List<Advertising> advertisingList = advertisingLogic.pageOptFindAdvertisings(findAllAdvertisingParam);
		
		List<AdvertisingVo> advertisingVoList = new ArrayList<AdvertisingVo>();
		if( advertisingList != null && advertisingList.size() > 0 ){
			for( Advertising po : advertisingList ){
				AdvertisingVo vo = (AdvertisingVo)BeanUtil.copyProperties(AdvertisingVo.class, po);
				advertisingVoList.add(vo);
			}
			findAllAdvertisingResultVo.setResultVo(new ResultVo(ResultCode.success.getCode(), ResultCode.success.getMsg()));
			findAllAdvertisingResultVo.setAdvertisingVoList(advertisingVoList);
		}else{
			LogCvt.info("页面优化查询 Advertising 列表 结果为空");
			findAllAdvertisingResultVo.setResultVo(new ResultVo(ResultCode.failed.getCode(), "页面优化查询 Advertising 列表 结果为空"));
		}
		
		return findAllAdvertisingResultVo;
	}


}
