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
 * @Title: OrgMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.chonggou.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.chonggou.entity.Transfer;
import com.froad.enums.TransferTypeEnum;

/**
 *  转移类型ID中间表
  * @ClassName: TransferMapper
  * @Description: TODO
  * @author share 2015年4月30日
  * @modify share 2015年4月30日
 */
public interface TransferMapper {

	/**
	 *  添加
	  * @Title: insert
	  * @Description: TODO
	  * @author: share 2015年4月30日
	  * @modify: share 2015年4月30日
	  * @param @param transfer
	  * @param @return    
	  * @return int    
	  * @throws
	 */
	public int insert(Transfer transfer);
	
	
    /**
     *  
      * @Title: queryNewId
      * @Description: TODO
      * @author: share 2015年4月30日
      * @modify: share 2015年4月30日
      * @param @param oldId
      * @param @param type
      * @param @return    
      * @return Transfer    
      * @throws
     */
	public Transfer queryNewId(@Param("oldId")String oldId,@Param("type")TransferTypeEnum type);
	/**
	 * 
	  * @Title: queryOId
	  * @Description: TODO
	  * @author: Yaren Liang 2015年5月2日
	  * @modify: Yaren Liang 2015年5月2日
	  * @param @param newId
	  * @param @param type
	  * @param @return    
	  * @return Transfer    
	  * @throws
	 */
	public Transfer queryOId(@Param("newId")String newId,@Param("type")TransferTypeEnum type);
	
	/**
	 * 获取某一类型关联数据集合
	 */
	public List<Transfer> queryGroupList(@Param("type")TransferTypeEnum type);
	
	/**
	 * 批量导入
	 */
	public void addTransferBatch(List<Transfer> list);
	

}