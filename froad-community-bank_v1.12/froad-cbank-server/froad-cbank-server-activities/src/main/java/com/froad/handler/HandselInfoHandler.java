/**
 * @Title: HandselInfoHandler.java
 * @Package com.froad.handler
 * @Description: TODO
 * Copyright:2015 F-Road All Rights Reserved   
 * Company:f-road.com.cn
 * 
 * @creater froad-Joker 2015年12月8日
 * @version V1.0
**/

package com.froad.handler;

import java.util.List;

import com.froad.po.HandselInfo;

 /**
 * @ClassName: HandselInfoHandler
 * @Description: TODO
 * @author froad-Joker 2015年12月8日
 * @modify froad-Joker 2015年12月8日
 */

public interface HandselInfoHandler {
    /**
     * 增加 HandselInfo
     * @param activeBaseRule
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addHandselInfo(HandselInfo handselInfo)  throws Exception;
	
	/**
	 * 批量增加 HandselInfo
	 * 
	 * @author: lf 2016年02月25日
	 * */
	public Boolean addHandselInfoByBatch(List<HandselInfo> handselInfoList) throws Exception;
}
