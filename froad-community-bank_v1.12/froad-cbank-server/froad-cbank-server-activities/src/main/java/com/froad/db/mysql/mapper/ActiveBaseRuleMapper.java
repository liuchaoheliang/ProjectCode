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
 * @Title: ActiveBaseRuleMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ActiveBaseRule;

/**
 * 
 * <p>@Title: ActiveBaseRuleMapper.java</p>
 * <p>Description: 封装mybatis对MySQL映射的实体ActiveBaseRule的CURD操作Mapper </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ActiveBaseRuleMapper {


    /**
     * 增加 ActiveBaseRule
     * @param activeBaseRule
     * @return Long    受影响行数
     */
	public Long addActiveBaseRule(ActiveBaseRule activeBaseRule);



    /**
     * 批量增加 ActiveBaseRule
     * @param activeBaseRuleList
     * @return Boolean    是否成功
     */
	public Boolean addActiveBaseRuleByBatch(List<ActiveBaseRule> activeBaseRuleList);



    /**
     * 删除 ActiveBaseRule
     * @param activeBaseRule
     * @return Integer    受影响行数
     */
	public Integer deleteActiveBaseRule(ActiveBaseRule activeBaseRule);



    /**
     * 根据id删除一个 ActiveBaseRule
     * @param id
     * @return Integer    受影响行数
     */
	public Integer deleteActiveBaseRuleById(Long id);

	
    /**
     * 根据id删除一个 ActiveBaseRule
     * @param id
     * @return Integer    受影响行数
     */
	public Integer deleteActiveBaseRuleByActiveId(@Param("activeId") String activeId);

    /**
     * 根据id禁用一个 ActiveBaseRule
     * @param id
     * @return Integer    受影响行数
     */
	public Integer disableActiveBaseRuleByActiveId(@Param("clientId") String clientId, @Param("activeId") String activeId, @Param("operator") String operator, @Param("updateTime") Date updateTime);

	

    /**
     * 修改 ActiveBaseRule
     * @param activeBaseRule
     * @return Integer    受影响行数
     */
	public Integer updateActiveBaseRule(ActiveBaseRule activeBaseRule);

	
    /**
     * 修改 ActiveBaseRule
     * @param activeBaseRule
     * @return Integer    受影响行数
     */	
	public Integer updateActiveBaseRuleById(ActiveBaseRule activeBaseRule);



    /**
     * 根据id修改一个 ActiveBaseRule
     * @param activeBaseRule
     * @return Integer    受影响行数
     */
	public Integer updateActiveBaseRulebyActiveId(ActiveBaseRule activeBaseRule);



    /**
     * 根据id查询一个 ActiveBaseRule
     * @param id
     * @return ActiveBaseRule    返回结果
     */
	public ActiveBaseRule findActiveBaseRuleById(Long id);
	
    /**
     * 根据id查询一个 ActiveBaseRule
     * @param activeId
     * @return ActiveBaseRule    返回结果
     */	
	public ActiveBaseRule findActiveBaseRuleByActiveId(@Param("activeId") String activeId);

	public ActiveBaseRule findActiveBaseRuleByActiveNameAndClientId(ActiveBaseRule activeBaseRule);


    /**
     * 查询一个 ActiveBaseRule
     * @param activeBaseRule
     * @return ActiveBaseRule    返回结果集
     */
	public ActiveBaseRule findOneActiveBaseRule(ActiveBaseRule activeBaseRule);

	

    /**
     * 统计 ActiveBaseRule
     * @param activeBaseRule
     * @return Integer    返回记录数
     */
	public Integer countActiveBaseRule(ActiveBaseRule activeBaseRule);



    /**
     * 查询 ActiveBaseRule
     * @param activeBaseRule
     * @return List<ActiveBaseRule>    返回结果集
     */
	public List<ActiveBaseRule> findActiveBaseRule(@Param("activeBaseRule")ActiveBaseRule activeBaseRule, @Param("order")String order);



    /**
     * 分页查询 ActiveBaseRule
     * @param page 
     * @param activeBaseRule
     * @return List<ActiveBaseRule>    返回分页查询结果集
     */
	public List<ActiveBaseRule> findByPage(Page page, @Param("activeBaseRule")ActiveBaseRule activeBaseRule);
	
	 /**
	  * @Title: findSustainRuleInfoByPage
	  * @Description: 返回所属红包支持的活动信息
	  * @author: shenshaocheng 2015年12月9日
	  * @modify: shenshaocheng 2015年12月9日
	  * @param page 分页信息
	  * @param activeBaseRule 查询条件
	  * @return
	 */	
	public List<ActiveBaseRule> findSustainRuleInfoByPage(Page page, @Param("activeBaseRule")ActiveBaseRule activeBaseRule);
	
	 /**
	  * @Title: findSuntainActiveBaseRuleListPage
	  * @Description: 返回所有活动信息（红包支持）
	  * @author: shenshaocheng 2015年12月9日
	  * @modify: shenshaocheng 2015年12月9日
	  * @param page 分页信息
	  * @param activeBaseRule 查询条件
	  * @return
	 */	
	public List<ActiveBaseRule> findSuntainActiveBaseRuleListByPage(Page page, @Param("activeBaseRule")ActiveBaseRule activeBaseRule);
	
	
	/**
	 * @Title: getMaxActiveByClientId
	 * @Description: 获取最大的活动序号
	 * @author: yefeifei 2015年11月20日
	 * @modify: yefeifei 2015年11月20日
	 * @param clientId
	 * @return
	*/
	public String getMaxActiveByClientId(@Param("clientId") String clientId,@Param("type")String type);


	 /**
	  * @Title: findSuntainActiveBaseRuleList
	  * @Description: 获取支持的活动（满额、打折）
	  * @author: shenshaocheng 2015年12月7日
	  * @modify: shenshaocheng 2015年12月7日
	  * @param activeBaseRule  查询条件
	  * @return
	 */	
	public List<ActiveBaseRule> findSuntainActiveBaseRuleList(ActiveBaseRule activeBaseRule);

}