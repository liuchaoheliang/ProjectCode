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
 * @Title: BossSenseWordsMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.BossSenseWords;

/**
 * 
 * <p>@Title: BossSenseWordsMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface BossSenseWordsMapper {


    /**
     * 增加 BossSenseWords
     * @param bossSenseWords
     * @return Long    主键ID
     */
	public Long addBossSenseWords(BossSenseWords bossSenseWords);



    /**
     * 批量增加 BossSenseWords
     * @param List<BossSenseWords>
     * @return Boolean    是否成功
     */
	public Boolean addBossSenseWordsByBatch(List<BossSenseWords> bossSenseWordsList);



    /**
     * 删除 BossSenseWords
     * @param bossSenseWords
     * @return Boolean    是否成功
     */
	public Boolean deleteBossSenseWords(BossSenseWords bossSenseWords);



    /**
     * 修改 BossSenseWords
     * @param bossSenseWords
     * @return Boolean    是否成功
     */
	public Boolean updateBossSenseWords(BossSenseWords bossSenseWords);



    /**
     * 查询一个 BossSenseWords
     * @param bossSenseWords
     * @return BossSenseWords    返回结果
     */
	public BossSenseWords findBossSenseWordsById(Long id);



    /**
     * 查询 BossSenseWords
     * @param bossSenseWords
     * @return List<BossSenseWords>    返回结果集
     */
	public List<BossSenseWords> findBossSenseWords(BossSenseWords bossSenseWords);



    /**
     * 分页查询 BossSenseWords
     * @param page 
     * @param bossSenseWords
     * @return List<BossSenseWords>    返回分页查询结果集
     */
	public List<BossSenseWords> findByPage(Page page, @Param("bossSenseWords")BossSenseWords bossSenseWords);



}