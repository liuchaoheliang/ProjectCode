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
 * @Title: BossSenseWordsLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.po.BossSenseWords;

/**
 * 
 * <p>@Title: BossSenseWordsLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface BossSenseWordsLogic {


    /**
     * 增加 BossSenseWords
     * @param bossSenseWords
     * @return Long    主键ID
     */
	public Long addBossSenseWords(BossSenseWords bossSenseWords);



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
     * 查询 BossSenseWords
     * @param bossSenseWords
     * @return List<BossSenseWords>    结果集合 
     */
	public List<BossSenseWords> findBossSenseWords(BossSenseWords bossSenseWords);



    /**
     * 分页查询 BossSenseWords
     * @param page
     * @param bossSenseWords
     * @return Page<BossSenseWords>    结果集合 
     */
	public Page<BossSenseWords> findBossSenseWordsByPage(Page<BossSenseWords> page, BossSenseWords bossSenseWords);



}