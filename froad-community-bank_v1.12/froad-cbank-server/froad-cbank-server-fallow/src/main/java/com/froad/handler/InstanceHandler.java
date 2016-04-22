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
 * @Title: InstanceHandler.java
 * @Package com.froad.handler
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.Instance;

/**
 * 
 * <p>@Title: InstanceHandler.java</p>
 * <p>Description: 封装对MySQL对应的实体Instance的CURD操作接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface InstanceHandler {


    /**
     * 增加 Instance
     * @param instance
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addInstance(Instance instance)  throws Exception;



    /**
     * 增加 Instance
     * @param sqlSession
     * @param instance
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addInstance(SqlSession sqlSession, Instance instance) throws Exception;



    /**
     * 删除 Instance
     * @param instance
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteInstance(Instance instance) throws Exception;



    /**
     * 删除 Instance
     * @param sqlSession
     * @param instance
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteInstance(SqlSession sqlSession, Instance instance) throws Exception;



    /**
     * 根据id删除 Instance
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteInstanceById(Long id) throws Exception;



    /**
     * 根据id删除 Instance
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteInstanceById(SqlSession sqlSession, Long id) throws Exception;



    /**
     * 根据instanceId删除 Instance
     * @param instanceId
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteInstanceByInstanceId(String instanceId) throws Exception;



    /**
     * 根据instanceId删除 Instance
     * @param instanceId
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteInstanceByInstanceId(SqlSession sqlSession, String instanceId) throws Exception;



    /**
     * 修改 Instance
     * @param instance
     * @return Boolean    受影响行数
     * @throws Exception
     */
	public Integer updateInstance(Instance instance) throws Exception;



    /**
     * 修改 Instance
     * @param sqlSession
     * @param instance
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateInstance(SqlSession sqlSession, Instance instance) throws Exception;



    /**
     * 根据id修改 Instance
     * @param Instance
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateInstanceById(Instance instance) throws Exception;



    /**
     * 根据id修改 Instance
     * @param sqlSession
     * @param instance
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateInstanceById(SqlSession sqlSession, Instance instance) throws Exception;



    /**
     * 根据instanceId修改 Instance
     * @param Instance
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateInstanceByInstanceId(Instance instance) throws Exception;



    /**
     * 根据instanceId修改 Instance
     * @param sqlSession
     * @param Instance
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateInstanceByInstanceId(SqlSession sqlSession, Instance instance) throws Exception;



    /**
     * 根据id查询单个 Instance
     * @param id
     * @return Instance    单个Instance
     * @throws Exception
     */
	public Instance findInstanceById(Long id) throws Exception;



    /**
     * 根据instanceId查询单个 Instance
     * @param instanceId
     * @return Instance    单个Instance
     * @throws Exception
     */
	public Instance findInstanceByInstanceId(String instanceId) throws Exception;



    /**
     * 查询一个 Instance
     * @param instance
     * @return Instance    单个Instance
     * @throws Exception
     */
	public Instance findOneInstance(Instance instance) throws Exception;



    /**
     * 查询统计 Instance
     * @param instance
     * @return Integer    返回记录数Instance
     * @throws Exception
     */
	public Integer countInstance(Instance instance) throws Exception;



    /**
     * 查询 Instance
     * @param instance
     * @return List<Instance>    Instance集合 
     * @throws Exception
     */
	public List<Instance> findInstance(Instance instance) throws Exception;



    /**
     * 查询 Instance
     * @param instance
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<Instance>    Instance集合 
     * @throws Exception
     */
	public List<Instance> findInstance(Instance instance, String order) throws Exception;



    /**
     * 分页查询 Instance
     * @param page
     * @param instance
     * @return Page<Instance>    Instance分页结果 
     * @throws Exception 
     */
	public Page<Instance> findInstanceByPage(Page<Instance> page, Instance instance) throws Exception;



}