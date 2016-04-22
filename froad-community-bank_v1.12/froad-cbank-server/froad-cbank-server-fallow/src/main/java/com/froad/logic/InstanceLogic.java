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
 * @Title: InstanceLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.Instance;

/**
 * 
 * <p>@Title: InstanceLogic.java</p>
 * <p>Description: 封装对Instance所有业务逻辑处理的接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface InstanceLogic {


    /**
     * 增加 Instance
     * @param instance
     * @return Long    主键ID
     */
	public Long addInstance(Instance instance) ;



    /**
     * 删除 Instance
     * @param instance
     * @return Boolean    是否成功
     */
	public Boolean deleteInstance(Instance instance) ;



    /**
     * 根据id删除 Instance
     * @param id
     * @return Boolean    是否成功
     */
	public Boolean deleteInstanceById(Long id) ;



    /**
     * 根据instanceId删除 Instance
     * @param instanceId
     * @return Boolean    是否成功
     */
	public Boolean deleteInstanceByInstanceId(String instanceId) ;



    /**
     * 修改 Instance
     * @param instance
     * @return Boolean    是否成功
     */
	public Boolean updateInstance(Instance instance) ;



    /**
     * 根据id修改 Instance
     * @param instance
     * @return Boolean    是否成功
     */
	public Boolean updateInstanceById(Instance instance) ;



    /**
     * 根据instanceId修改 Instance
     * @param instance
     * @return Boolean    是否成功
     */
	public Boolean updateInstanceByInstanceId(Instance instance) ;



    /**
     * 根据id查询单个 Instance
     * @param id
     * @return Instance    单个 Instance
     */
	public Instance findInstanceById(Long id) ;



    /**
     * 根据instanceId查询单个 Instance
     * @param instanceId
     * @return Instance    单个 Instance
     */
	public Instance findInstanceByInstanceId(String instanceId) ;



    /**
     * 查询一个 Instance
     * @param instance
     * @return Instance    单个 Instance
     */
	public Instance findOneInstance(Instance instance) ;



    /**
     * 统计 Instance
     * @param instance
     * @return Integer    返回记录数 Instance
     */
	public Integer countInstance(Instance instance) ;



    /**
     * 查询 Instance
     * @param instance
     * @return List<Instance>    Instance集合 
     */
	public List<Instance> findInstance(Instance instance) ;



    /**
     * 分页查询 Instance
     * @param page
     * @param instance
     * @return Page<Instance>    Instance分页结果 
     */
	public Page<Instance> findInstanceByPage(Page<Instance> page, Instance instance) ;



}