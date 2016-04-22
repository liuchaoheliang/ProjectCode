package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.AdLocation;
import com.froad.po.Advertising;

/**
 * 
 * <p>@Title: AdvertisingMapper.java</p>
 * <p>Description: 描述 </p> 
 * <p>广告 - 数据操作 - 接口</p> 
 * @author lf 
 * @version 1.0
 * @created 2015年9月22日
 */
public interface AdvertisingMapper {

	/**
     * 增加 Advertising
     * @param advertising
     * @return Long    主键ID
     */
	public Long addAdvertising(Advertising advertising);
	
	/**
	 * 删除 Advertising
	 * @param id主键
	 * @return Boolean
	 * */
	public Boolean deleteAdvertising(Long id);
	
	/**
	 * 更新 Advertising
	 * @param advertising
	 * @return Boolean
	 * */
	public Boolean updateAdvertising(Advertising advertising);
	
	/**
	 * 查询 Advertising 列表
	 * @param advertising
	 * @return List<Advertising>
	 * */
	public List<Advertising> findAdvertisingList(Advertising advertising);
	
	/**
     * 分页查询 Advertising
     * @param page 
     * @param advertising
     * @return List<Advertising>    返回分页查询结果集
     */
	public List<Advertising> findByPage(@Param("page")Page page, @Param("advertising")Advertising advertising);
	
	/**
	 * 查询 Advertising 单个
	 * @param id主键
	 * @return Advertising
	 * */
	public Advertising findAdvertisingById(Long id);
	
	/**
	 * 优化查询 Advertising 列表 使用三个参赛
	 * @param advertising
	 * @return List<Advertising>
	 * */
	public List<Advertising> pageOptFindAdvertisingsByThreeParam(Advertising advertising);
	
	/**
	 * 优化查询 Advertising 列表 使用两个参赛
	 * @param advertising
	 * @return List<Advertising>
	 * */
	public List<Advertising> pageOptFindAdvertisingsByTwoParam(Advertising advertising);
	
	/**
	 * 优化查询 Advertising 列表 使用一个参赛
	 * @param advertising
	 * @return List<Advertising>
	 * */
	public List<Advertising> pageOptFindAdvertisingsByOneParam(Advertising advertising);
	
	/**
	 * 优化查询 Advertising 列表 不使用参赛
	 * @param advertising
	 * @return List<Advertising>
	 * */
	public List<Advertising> pageOptFindAdvertisings(Advertising advertising);
	
	/**
	 * 优化查询 Advertising 列表 自动匹配
	 * @param advertising
	 * @return List<Advertising>
	 * */
	public List<Advertising> pageOptFindAdvertisingsAutoMatch(Advertising advertising);
	
	/**
	 * 优化查询 Advertising 列表 参赛为null
	 * @param advertising
	 * @return List<Advertising>
	 * */
	public List<Advertising> pageOptFindAdvertisingsParamNull(Advertising advertising);
	
	/**
	 * 查询 Advertising 列表 根据 客户端id 和 标题 
	 * @param clientId title
	 * @return List<Advertising>
	 * */
	public List<Advertising> findAdvertisingListByClientIdAndTitle(@Param("clientId")String clientId, @Param("title")String title);

}
