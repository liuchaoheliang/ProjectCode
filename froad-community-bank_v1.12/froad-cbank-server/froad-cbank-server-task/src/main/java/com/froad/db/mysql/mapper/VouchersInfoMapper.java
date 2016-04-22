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
 * @Title: VouchersInfoMapper.java
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
import com.froad.po.VouchersInfo;
import com.froad.po.VouchersUseDetails;

/**
 * 
 * <p>
 * @Title: VouchersInfoMapper.java
 * </p>
 * <p>
 * Description: 封装mybatis对MySQL映射的实体VouchersInfo的CURD操作Mapper
 * </p>
 * 
 * @author f-road
 * @version 1.0
 * @created 2015年3月17日
 */
public interface VouchersInfoMapper {
    
	/**
	 * 
	 * @Title: getVouchersInfoFromHalfYear 
	 * @Description: TODO
	 * @author: Yaolong Liang 2016年1月8日
	 * @modify: Yaolong Liang 2016年1月8日
	 * @param date
	 * @return
	 * @return List<VouchersInfo>
	 * @throws
	 */
	public List<VouchersInfo> getVouchersInfoFromHalfYear(Date date);
	
	/**
	 * 
	 * @Title: addVouchersUseInfoByBatch 
	 * @Description: TODO
	 * @author: Yaolong Liang 2015年12月31日
	 * @modify: Yaolong Liang 2015年12月31日
	 * @param vouchersInfoList
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean addVouchersUseInfoByBatch(
			@Param("vouchersInfoList") List<VouchersInfo> vouchersInfoList); 
	/**
	 * 
	 * @Title: deleteVouchersInfoFromHalfYear 
	 * @Description: TODO
	 * @author: Yaolong Liang 2016年1月4日
	 * @modify: Yaolong Liang 2016年1月4日
	 * @return
	 * @return Integer
	 * @throws
	 */
	public Integer deleteVouchersInfoFromHalfYear(Date date);
	/**
	 * 
	 * @Title: addVouchersUseInfo 
	 * @Description: TODO
	 * @author: Yaolong Liang 2016年1月8日
	 * @modify: Yaolong Liang 2016年1月8日
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean addVouchersUseInfo(Date date); 
	/**
	 * 
	 * @Title: getVouchersInfoOutOfDateFromHalfYear 
	 * @Description: TODO
	 * @author: Yaolong Liang 2016年1月4日
	 * @modify: Yaolong Liang 2016年1月4日
	 * @return
	 * @return List<VouchersInfo>
	 * @throws
	 */
	
	public List<VouchersInfo> getVouchersInfoOutOfDateFromHalfYear(Date date);
	
	/**
	 * 
	 * @Title: addVouchersOutOfDateInfoByBatch 
	 * @Description: TODO
	 * @author: Yaolong Liang 2016年1月4日
	 * @modify: Yaolong Liang 2016年1月4日
	 * @param vouchersInfoList
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean addVouchersOutOfDateInfoByBatch(
			@Param("vouchersInfoList") List<VouchersInfo> vouchersInfoList); 
	/**
	 * 
	 * @Title: deleteVouchersInfoOutOfDateFromHalfYear 
	 * @Description: TODO
	 * @author: Yaolong Liang 2016年1月4日
	 * @modify: Yaolong Liang 2016年1月4日
	 * @return
	 * @return Integer
	 * @throws
	 */
	public Integer deleteVouchersInfoOutOfDateFromHalfYear(@Param("vouchersInfoList") List<VouchersInfo> vouchersInfoList);	
	/**
	 * 
	 * @Title: addVouchersOutOfDateInfoByBatch 
	 * @Description: TODO
	 * @author: Yaolong Liang 2016年1月8日
	 * @modify: Yaolong Liang 2016年1月8日
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean addVouchersOutOfDateInfo(Date date); 
	/**
	 * 
	 * @Title: findVouchersInfoIds 
	 * @Description: TODO
	 * @author: Yaolong Liang 2016年1月5日
	 * @modify: Yaolong Liang 2016年1月5日
	 * @return
	 * @return List<String>
	 * @throws
	 */
	public List<VouchersInfo> findVouchersInfoIds();
	/**
	 * 
	 * @Title: deleteVouchersTemporary 
	 * @Description: TODO
	 * @author: Yaolong Liang 2016年1月4日
	 * @modify: Yaolong Liang 2016年1月4日
	 * @return
	 * @return Integer
	 * @throws
	 */
	public Integer deleteVouchersTemporary();
}