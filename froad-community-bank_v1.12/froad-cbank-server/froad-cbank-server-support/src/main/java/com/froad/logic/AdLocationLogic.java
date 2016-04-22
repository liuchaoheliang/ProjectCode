package com.froad.logic;

import com.froad.db.mysql.bean.Page;
import com.froad.po.AdLocation;
import com.froad.po.FindAdLocationResult;
import com.froad.po.FindAllAdLocationResult;
import com.froad.po.FindPageAdLocationResult;
import com.froad.po.Result;

/**
 * 
 * <p>@Title: AdLocationLogic.java</p>
 * <p>Description: 描述 </p>
 * <p>广告位 - 逻辑 - 接口</p>
 * @author lf
 * @version 1.0
 * @created 2015年9月18日
 */
public interface AdLocationLogic {
	/**
     * 增加 AdLocation
     * @param adLocation
     * @return Result
     */
	public Result addAdLocation(AdLocation adLocation);
	
	/**
     * 删除 AdLocation
     * @param id
     * @return Result  
     */
	public Result deleteAdLocation(Long id);
	
	/**
     * 修改 AdLocation
     * @param adLocation
     * @return Result
     */
	public Result updateAdLocation(AdLocation adLocation);

	/**
     * 查询 AdLocation 列表
     * @param adLocation
     * @return List<AdLocation>    结果集合 
     */
	public FindAllAdLocationResult findAdLocation(AdLocation adLocation);

    /**
     * 分页查询 AdLocation
     * @param page
     * @param adLocation
     * @return Page<AdLocation>    结果集合 
     */
	public FindPageAdLocationResult findAdLocationByPage(Page<AdLocation> page, AdLocation adLocation);

	/**
     * 查询 AdLocation 单个
     * @return adLocation
     * 
     * @param clientId
     * @param id
     */
	public FindAdLocationResult getAdLocationById(long id);
	
	/**
     * 禁用 AdLocation
     * @param id
     * @return Result  
     */
	public Result disabledAdLocation(Long id);
}
