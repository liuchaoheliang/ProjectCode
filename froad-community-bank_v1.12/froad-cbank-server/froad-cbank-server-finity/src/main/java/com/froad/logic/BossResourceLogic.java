
/**
 * 
 * @Title: ResourceLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.exceptions.FroadServerException;
import com.froad.po.Resource;

/**
 * 
 * <p>@Title: ResourceLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface BossResourceLogic {

	public Long insertSelective(Resource resource)  throws FroadServerException, Exception;
	

    /**
     * 增加 Resource
     * @param resource
     * @return Long    主键ID
     */
	public Long addResource(Resource resource)  throws FroadServerException, Exception;



    /**
     * 删除 Resource
     * @param resource
     * @return Boolean    是否成功
     */
	public Boolean deleteResource(Resource resource)  throws FroadServerException, Exception;



    /**
     * 修改 Resource
     * @param resource
     * @return Boolean    是否成功
     */
	public Boolean updateResource(Resource resource)  throws FroadServerException, Exception;



    /**
     * 查询 Resource
     * @param resource
     * @return List<Resource>    结果集合 
     */
	public List<Resource> findResource(Resource resource)  throws Exception;





	/**
	 * 根据角色获取资源信息
	 * @param roleId
	 * @return
	 */
	public List<Resource> findResourceByRole(Resource resource,Long roleId) throws Exception;

	
	/**
	 * 根据角色获取资源信息
	 * @param roleId
	 * @return
	 */
	public List<Resource> findResourceByRoles(Resource resource,List<Long> roles) throws Exception;
	
	/**
	 * 根据用户获取资源信息
	 * 
	 * @param userId
	 * @return
	 */
	public List<Resource> findResourceByUser(Resource resource,  Long userId)   throws FroadServerException, Exception;
	
	
	/**
	 * 菜单调整顺序
	 * @param resourceVoList
	 * @return
	 */
	public Boolean moveMenu(List<Resource> resourceList)  throws FroadServerException, Exception;



}