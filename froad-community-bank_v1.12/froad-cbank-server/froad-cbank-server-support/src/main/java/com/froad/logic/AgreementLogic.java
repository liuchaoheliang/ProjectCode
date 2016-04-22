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
 * @Title: AgreementLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.po.Agreement;

/**
 * 
 * <p>@Title: AgreementLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface AgreementLogic {


    /**
     * 增加 Agreement
     * @param agreement
     * @return Long    主键ID
     */
	public ResultBean addAgreement(Agreement agreement);



    /**
     * 删除 Agreement
     * @param agreement
     * @return Boolean    是否成功
     */
	public ResultBean deleteAgreement(Agreement agreement);



    /**
     * 修改 Agreement
     * @param agreement
     * @return Boolean    是否成功
     */
	public ResultBean updateAgreement(Agreement agreement);



    /**
     * 查询 Agreement
     * @param agreement
     * @return List<Agreement>    结果集合 
     */
	public List<Agreement> findAgreement(Agreement agreement);



    /**
     * 分页查询 Agreement
     * @param page
     * @param agreement
     * @return Page<Agreement>    结果集合 
     */
	public Page<Agreement> findAgreementByPage(Page<Agreement> page, Agreement agreement);



}