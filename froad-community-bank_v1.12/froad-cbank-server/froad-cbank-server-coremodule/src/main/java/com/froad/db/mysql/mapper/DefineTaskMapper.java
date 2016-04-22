/**
 * Project Name:froad-cbank-server-coremodule
 * File Name:DefineTaskMapper.java
 * Package Name:com.froad.db.mysql.mapper
 * Date:2015-12-5下午04:20:19
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.req.DefineTaskReq;
import com.froad.po.resp.DefineTaskResp;

/**
 * ClassName:DefineTaskMapper
 * Reason:	 任务列表.
 * Date:     2015-12-5 下午04:20:19
 * @author   wufei
 * @version  
 * @see 	 
 */
public interface DefineTaskMapper {

	public List<DefineTaskResp> getDefineTaskRespByPage(Page page,@Param("defineTaskReq") DefineTaskReq defineTaskReq);
}
