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
 * @Title: BizInstanceAttrValueMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.BizInstanceAttrValue;

/**
 * 
 * <p>@Title: BizInstanceAttrValueMapper.java</p>
 * <p>Description: 封装mybatis对MySQL映射的实体BizInstanceAttrValue的CURD操作Mapper </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface BizInstanceAttrValueMapper {


    /**
     * 增加 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return Long    受影响行数
     */
	public Long addBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue);



    /**
     * 批量增加 BizInstanceAttrValue
     * @param bizInstanceAttrValueList
     * @return Boolean    是否成功
     */
	public Boolean addBizInstanceAttrValueByBatch(@Param("bizInstanceAttrValueList") List<BizInstanceAttrValue> bizInstanceAttrValueList);



    /**
     * 删除 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return Integer    受影响行数
     */
	public Integer deleteBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue);



    /**
     * 根据instanceId删除一个 BizInstanceAttrValue
     * @param instanceId
     * @return Integer    受影响行数
     */
	public Integer deleteBizInstanceAttrValueByInstanceId(String instanceId);



    /**
     * 修改 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return Integer    受影响行数
     */
	public Integer updateBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue);



    /**
     * 根据instanceId修改一个 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return Integer    受影响行数
     */
	public Integer updateBizInstanceAttrValueByInstanceId(BizInstanceAttrValue bizInstanceAttrValue);



    /**
     * 根据instanceId查询一个 BizInstanceAttrValue
     * @param instanceId
     * @return BizInstanceAttrValue    返回结果
     */
	public BizInstanceAttrValue findBizInstanceAttrValueByInstanceId(String instanceId);



    /**
     * 查询一个 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return BizInstanceAttrValue    返回结果集
     */
	public BizInstanceAttrValue findOneBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue);



    /**
     * 统计 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return Integer    返回记录数
     */
	public Integer countBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue);



    /**
     * 查询 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return List<BizInstanceAttrValue>    返回结果集
     */
	public List<BizInstanceAttrValue> findBizInstanceAttrValue(@Param("bizInstanceAttrValue")BizInstanceAttrValue bizInstanceAttrValue, @Param("order")String order);



    /**
     * 分页查询 BizInstanceAttrValue
     * @param page 
     * @param bizInstanceAttrValue
     * @return List<BizInstanceAttrValue>    返回分页查询结果集
     */
	public List<BizInstanceAttrValue> findByPage(Page page, @Param("bizInstanceAttrValue")BizInstanceAttrValue bizInstanceAttrValue);



}