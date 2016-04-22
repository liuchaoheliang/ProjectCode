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
 * @Title: BizInstanceAttrMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.BizInstanceAttr;

/**
 * 
 * <p>@Title: BizInstanceAttrMapper.java</p>
 * <p>Description: 封装mybatis对MySQL映射的实体BizInstanceAttr的CURD操作Mapper </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface BizInstanceAttrMapper {


    /**
     * 增加 BizInstanceAttr
     * @param bizInstanceAttr
     * @return Long    受影响行数
     */
	public Long addBizInstanceAttr(BizInstanceAttr bizInstanceAttr);



    /**
     * 批量增加 BizInstanceAttr
     * @param bizInstanceAttrList
     * @return Boolean    是否成功
     */
	public Boolean addBizInstanceAttrByBatch(@Param("bizInstanceAttrList") List<BizInstanceAttr> bizInstanceAttrList);



    /**
     * 删除 BizInstanceAttr
     * @param bizInstanceAttr
     * @return Integer    受影响行数
     */
	public Integer deleteBizInstanceAttr(BizInstanceAttr bizInstanceAttr);



    /**
     * 根据id删除一个 BizInstanceAttr
     * @param id
     * @return Integer    受影响行数
     */
	public Integer deleteBizInstanceAttrById(Long id);



    /**
     * 修改 BizInstanceAttr
     * @param bizInstanceAttr
     * @return Integer    受影响行数
     */
	public Integer updateBizInstanceAttr(BizInstanceAttr bizInstanceAttr);



    /**
     * 根据id修改一个 BizInstanceAttr
     * @param bizInstanceAttr
     * @return Integer    受影响行数
     */
	public Integer updateBizInstanceAttrById(BizInstanceAttr bizInstanceAttr);



    /**
     * 根据id查询一个 BizInstanceAttr
     * @param id
     * @return BizInstanceAttr    返回结果
     */
	public BizInstanceAttr findBizInstanceAttrById(Long id);



    /**
     * 查询一个 BizInstanceAttr
     * @param bizInstanceAttr
     * @return BizInstanceAttr    返回结果集
     */
	public BizInstanceAttr findOneBizInstanceAttr(BizInstanceAttr bizInstanceAttr);



    /**
     * 统计 BizInstanceAttr
     * @param bizInstanceAttr
     * @return Integer    返回记录数
     */
	public Integer countBizInstanceAttr(BizInstanceAttr bizInstanceAttr);



    /**
     * 查询 BizInstanceAttr
     * @param bizInstanceAttr
     * @return List<BizInstanceAttr>    返回结果集
     */
	public List<BizInstanceAttr> findBizInstanceAttr(@Param("bizInstanceAttr")BizInstanceAttr bizInstanceAttr, @Param("order")String order);



    /**
     * 分页查询 BizInstanceAttr
     * @param page 
     * @param bizInstanceAttr
     * @return List<BizInstanceAttr>    返回分页查询结果集
     */
	public List<BizInstanceAttr> findByPage(Page page, @Param("bizInstanceAttr")BizInstanceAttr bizInstanceAttr);



}