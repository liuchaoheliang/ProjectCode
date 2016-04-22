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
 * @Title: AgreementMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.Agreement;

/**
 * 
 * <p>@Title: AgreementMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface AgreementMapper {


    /**
     * 增加 Agreement
     * @param agreement
     * @return Long    主键ID
     */
	public Long addAgreement(Agreement agreement);



    /**
     * 批量增加 Agreement
     * @param List<Agreement>
     * @return Boolean    是否成功
     */
	public Boolean addAgreementByBatch(List<Agreement> agreementList);



    /**
     * 删除 Agreement
     * @param agreement
     * @return Boolean    是否成功
     */
	public Boolean deleteAgreement(Agreement agreement);



    /**
     * 修改 Agreement
     * @param agreement
     * @return Boolean    是否成功
     */
	public Boolean updateAgreement(Agreement agreement);



    /**
     * 查询一个 Agreement
     * @param agreement
     * @return Agreement    返回结果
     */
	public Agreement findAgreementById(Long id);

    /**
     * 查询 Agreement
     * @param agreement
     * @return List<Agreement>    返回结果集
     */
	public List<Agreement> findAgreement(Agreement agreement);



    /**
     * 分页查询 Agreement
     * @param page 
     * @param agreement
     * @return List<Agreement>    返回分页查询结果集
     */
	public List<Agreement> findByPage(Page page, @Param("agreement")Agreement agreement);



}