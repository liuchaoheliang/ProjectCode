package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.AdLocation;

/**
 * 
 * <p>@Title: AdLocationMapper.java</p>
 * <p>Description: 描述 </p> 
 * <p>广告位 - 数据操作 - 接口</p> 
 * @author lf 
 * @version 1.0
 * @created 2015年9月18日
 */
public interface AdLocationMapper {

	/**
     * 增加 AdLocation
     * @param adLocation
     * @return Long    主键ID
     */
	public Long addAdLocation(AdLocation adLocation);
	
	/**
	 * 删除 AdLocation
	 * @param id主键
	 * @return Boolean
	 * */
	public Boolean deleteAdLocation(Long id);
	
	/**
	 * 更新 AdLocation
	 * @param adLocation
	 * @return Boolean
	 * */
	public Boolean updateAdLocation(AdLocation adLocation);
	
	/**
	 * 查询 AdLocation 列表
	 * @param adLocation
	 * @return List<AdLocation>
	 * */
	public List<AdLocation> findAdLocationList(AdLocation adLocation);
	
	/**
     * 分页查询 AdLocation
     * @param page 
     * @param adLocation
     * @return List<AdLocation>    返回分页查询结果集
     */
	public List<AdLocation> findByPage(@Param("page")Page page, @Param("adLocation")AdLocation adLocation);
	
	/**
	 * 查询 AdLocation 单个
	 * @param id主键
	 * @return AdLocation
	 * */
	public AdLocation findAdLocationById(Long id);
	
	/**
     * 禁用 AdPosition
     * @param id
     * @return Boolean    是否成功
     */
	public Boolean disabledAdLocation(Long id);
	
	/**
	 * 查询 AdLocation 列表 - 根据 terminal 和 position 
	 * @param adLocation
	 * @return List<AdLocation>
	 * */
	public List<AdLocation> findAdLocationListByTerminalAndPosition(@Param("terminalType")String terminalType, @Param("positionPage")String positionPage);
	/**
	 * 
	 * @Title: selectAdLocationById 
	 * @Description: TODO
	 * @author: Yaolong Liang 2015年9月25日
	 * @modify: Yaolong Liang 2015年9月25日
	 * @param id
	 * @return
	 * @return AdLocation
	 * @throws
	 */
	public  AdLocation selectAdLocationById(Long id);
}
