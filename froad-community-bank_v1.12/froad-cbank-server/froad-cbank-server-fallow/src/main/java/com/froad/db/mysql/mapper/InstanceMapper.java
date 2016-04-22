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
 * @Title: InstanceMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.Instance;

/**
 * 
 * <p>@Title: InstanceMapper.java</p>
 * <p>Description: 封装mybatis对MySQL映射的实体Instance的CURD操作Mapper </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface InstanceMapper {


    /**
     * 增加 Instance
     * @param instance
     * @return Long    受影响行数
     */
	public Long addInstance(Instance instance);



    /**
     * 批量增加 Instance
     * @param instanceList
     * @return Boolean    是否成功
     */
	public Boolean addInstanceByBatch(@Param("instanceList")List<Instance> instanceList);



    /**
     * 删除 Instance
     * @param instance
     * @return Integer    受影响行数
     */
	public Integer deleteInstance(Instance instance);



    /**
     * 根据id删除一个 Instance
     * @param id
     * @return Integer    受影响行数
     */
	public Integer deleteInstanceById(Long id);



    /**
     * 根据instanceId删除一个 Instance
     * @param instanceId
     * @return Integer    受影响行数
     */
	public Integer deleteInstanceByInstanceId(String instanceId);



    /**
     * 修改 Instance
     * @param instance
     * @return Integer    受影响行数
     */
	public Integer updateInstance(Instance instance);



    /**
     * 根据id修改一个 Instance
     * @param instance
     * @return Integer    受影响行数
     */
	public Integer updateInstanceById(Instance instance);



    /**
     * 根据instanceId修改一个 Instance
     * @param instance
     * @return Integer    受影响行数
     */
	public Integer updateInstanceByInstanceId(Instance instance);



    /**
     * 根据id查询一个 Instance
     * @param id
     * @return Instance    返回结果
     */
	public Instance findInstanceById(Long id);



    /**
     * 根据instanceId查询一个 Instance
     * @param instanceId
     * @return Instance    返回结果
     */
	public Instance findInstanceByInstanceId(String instanceId);



    /**
     * 查询一个 Instance
     * @param instance
     * @return Instance    返回结果集
     */
	public Instance findOneInstance(Instance instance);



    /**
     * 统计 Instance
     * @param instance
     * @return Integer    返回记录数
     */
	public Integer countInstance(Instance instance);



    /**
     * 查询 Instance
     * @param instance
     * @return List<Instance>    返回结果集
     */
	public List<Instance> findInstance(@Param("instance")Instance instance, @Param("order")String order);



    /**
     * 分页查询 Instance
     * @param page 
     * @param instance
     * @return List<Instance>    返回分页查询结果集
     */
	public List<Instance> findByPage(Page page, @Param("instance")Instance instance);



}