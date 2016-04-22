/**
 * @Title: HandselInfoMapper.java
 * @Package com.froad.db.mysql.mapper
 * @Description: TODO
 * Copyright:2015 F-Road All Rights Reserved   
 * Company:f-road.com.cn
 * 
 * @creater froad-Joker 2015年12月8日
 * @version V1.0
**/

package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.HandselInfo;

 /**
 * @ClassName: HandselInfoMapper
 * @Description: TODO
 * @author froad-Joker 2015年12月8日
 * @modify froad-Joker 2015年12月8日
 */

public interface HandselInfoMapper {
    /**
     * 增加 HandselInfo
     * @param handselInfo
     * @return Long    受影响行数
     */
	public Long addHandselInfo(HandselInfo handselInfo);
	
	/**
	 * 批量增加 HandselInfo
	 * 
	 * @author: lf 2016年02月25日
	 * */
	public Boolean addHandselInfoByBatch(@Param("handselInfoList") List<HandselInfo> handselInfoList);

}
