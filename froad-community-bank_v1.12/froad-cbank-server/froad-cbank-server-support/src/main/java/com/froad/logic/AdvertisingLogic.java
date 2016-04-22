package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.po.Advertising;
import com.froad.po.FindAdvertisingResult;
import com.froad.po.FindAllAdvertisingParam;
import com.froad.po.FindAllAdvertisingResult;
import com.froad.po.FindPageAdvertisingResult;
import com.froad.po.Result;
import com.froad.thrift.vo.FindAllAdvertisingParamVo;

/**
 * 
 * <p>@Title: AdvertisingLogic.java</p>
 * <p>Description: 描述 </p>
 * <p>广告 - 逻辑 - 接口</p>
 * @author lf
 * @version 1.0
 * @created 2015年9月22日
 */
public interface AdvertisingLogic {
	/**
     * 增加 Advertising
     * @param advertising
     * @return Result
     */
	public Result addAdvertising(Advertising advertising);
	
	/**
     * 删除 Advertising
     * @param id
     * @return Result  
     */
	public Result deleteAdvertising(Long id);
	
	/**
     * 修改 Advertising
     * @param advertising
     * @return Result
     */
	public Result updateAdvertising(Advertising advertising);

	/**
     * 查询 Advertising 列表
     * @param advertising
     * @return List<Advertising>    结果集合 
     */
	public FindAllAdvertisingResult findAdvertising(Advertising advertising);

    /**
     * 分页查询 Advertising
     * @param page
     * @param advertising
     * @return Page<Advertising>    结果集合 
     */
	public FindPageAdvertisingResult findAdvertisingByPage(Page<Advertising> page, Advertising advertising);

	/**
     * 查询 Advertising 单个
     * @return advertising
     * 
     * @param clientId
     * @param id
     */
	public FindAdvertisingResult getAdvertisingById(String clientId, long id);
	
	/**
     * 页面优化查询 Advertising 列表
     * @return List<Advertising>
     * 
     * @param findAllAdvertisingParam
     */
	public List<Advertising> pageOptFindAdvertisings(FindAllAdvertisingParam findAllAdvertisingParam);
}
