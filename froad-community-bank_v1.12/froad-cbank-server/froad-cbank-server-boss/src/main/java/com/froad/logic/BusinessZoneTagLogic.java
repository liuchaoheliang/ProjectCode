/**
 * Project Name:froad-cbank-server-boss
 * File Name:BusinessZoneTagService.java
 * Package Name:com.froad.logic
 * Date:2015年10月23日上午10:42:57
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.logic;

import com.froad.thrift.vo.businesszonetag.BusinessZoneTagAddReq;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagAddRes;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagDeleteReq;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagDeleteRes;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagDetailReq;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagDetailRes;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagExportOutletReq;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagExportOutletRes;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagListReq;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagListRes;
import com.froad.thrift.vo.businesszonetag.GenerateActivityNoReq;
import com.froad.thrift.vo.businesszonetag.GenerateActivityNoRes;

/**
 * ClassName:BusinessZoneTagService
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月23日 上午10:42:57
 * @author   kevin
 * @version  
 * @see 	 
 */
public interface BusinessZoneTagLogic {

	/**
	 * 获取商圈列表信息
	 * getBusinessZoneTagList:(这里用一句话描述这个方法的作用).
	 *
	 * @author zhouzhiwei
	 * 2015年10月23日 上午10:48:20
	 * @param req
	 * @return
	 *
	 */
	public BusinessZoneTagListRes getBusinessZoneTagList(BusinessZoneTagListReq req); 
	
	
	/**
	 * 编辑商圈信息
	 * editBusinessZoneTag:(这里用一句话描述这个方法的作用).
	 *
	 * @author zhouzhiwei
	 * 2015年10月23日 上午10:55:16
	 * @param req
	 * @return
	 *
	 */
	public BusinessZoneTagAddRes editBusinessZoneTag(BusinessZoneTagAddReq req);
	
	
	/**
	 * 新增商圈信息
	 * addBusinessZoneTag:(这里用一句话描述这个方法的作用).
	 *
	 * @author zhouzhiwei
	 * 2015年10月23日 上午11:00:21
	 * @param req
	 * @return
	 *
	 */
	public BusinessZoneTagAddRes addBusinessZoneTag(BusinessZoneTagAddReq req);
	
	
	/**
	 * 导出商圈门店列表
	 * exportBusinessZoneTag:(这里用一句话描述这个方法的作用).
	 *
	 * @author zhouzhiwei
	 * 2015年10月23日 上午11:01:25
	 * @param req
	 * @return
	 *
	 */
	public BusinessZoneTagExportOutletRes exportBusinessZoneTag(BusinessZoneTagExportOutletReq req);
	
	/**
	 * 获取商圈详情
	 * getBusinessZoneTag:(这里用一句话描述这个方法的作用).
	 *
	 * @author zhouzhiwei
	 * 2015年10月26日 上午11:47:05
	 * @param req
	 * @return
	 *
	 */
	public BusinessZoneTagDetailRes getBusinessZoneTag(BusinessZoneTagDetailReq req);
	
	/**
	 * 禁用商圈标签
	 * deleteBusinessZoneTag:(这里用一句话描述这个方法的作用).
	 *
	 * @author zhouzhiwei
	 * 2015年10月26日 上午11:48:04
	 * @param req
	 * @return
	 *
	 */
	public BusinessZoneTagDeleteRes deleteBusinessZoneTag(BusinessZoneTagDeleteReq req);
	
	/**
	 * 生成活动编号
	 * generateActivityNo:(这里用一句话描述这个方法的作用).
	 *
	 * @author zhouzhiwei
	 * 2015年10月26日 下午4:14:30
	 * @param req
	 * @return
	 *
	 */
	public GenerateActivityNoRes generateActivityNo(GenerateActivityNoReq req);
}
