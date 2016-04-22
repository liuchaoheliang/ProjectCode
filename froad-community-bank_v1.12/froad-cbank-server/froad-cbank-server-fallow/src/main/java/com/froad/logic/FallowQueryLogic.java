/**
 * Project Name:Froad Cbank Server Fallow
 * File Name:FallowExecuteLogic.java
 * Package Name:com.froad.logic
 * Date:2015年10月14日下午7:00:12
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.logic;


import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mysql.bean.Page;
import com.froad.po.QueryAlreadyAuitListReq;
import com.froad.po.QueryApplyAuitListReq;
import com.froad.po.QueryAttrReq;
import com.froad.po.QueryAttrRes;
import com.froad.po.QueryMongoPageRes;
import com.froad.po.QueryPendAuitCountReq;
import com.froad.po.QueryPendAuitCountRes;
import com.froad.po.QueryPendAuitListReq;
import com.froad.po.QueryTaskOverviewReq;
import com.froad.po.QueryTaskOverviewRes;
import com.froad.po.QueryTaskReq;
import com.froad.po.QueryTaskRes;

/**
 * ClassName:FallowExecuteLogic
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月14日 下午7:00:12
 * @author   vania
 * @version  
 * @see 	 
 */
public interface FallowQueryLogic {
	

    /**
     * 
     * getTaskOverview:(当前审核信息概要接口).
     *
     * @author wm
     * 2015年10月20日 上午10:40:31
     * @param req
     * @return
     *
     */
    public QueryTaskOverviewRes getTaskOverview(QueryTaskOverviewReq req);
    
    
    /**
     * 
     * getTaskList:查询审核任务信息  -- 审核任务单列表接口
     *
     * @author wm
     * 2015年10月22日 上午11:44:07
     * @param req
     * @return
     *
     */
    public QueryTaskRes getTaskList(QueryTaskReq req);
    
    /**
     * 
     * getInstanceIdByAttr:根据业务(商户、门店、商品)ID查询审核流水号
     *
     * @author mi
     * 2015年10月22日 下午5:04:35
     * @param req
     * @return
     *
     */
    public QueryAttrRes getInstanceIdByAttr(QueryAttrReq req);
    
    /**
     * 
     * getPendAuitList:待审核查询接口[待办箱]
     *
     * @author wm
     * 2015年10月22日 下午6:20:52
     * @param req
     * @param pageVo
     * @return
     * @throws org.apache.thrift.TException
     *
     */
    public QueryMongoPageRes getPendAuitListByPage(MongoPage page,QueryPendAuitListReq req);
    
    /**
     * 
     * getPendAuitCount:待审核数量查询接口
     *
     * @author vania
     * 2015年11月6日 下午3:05:00
     * @param req
     * @return
     *
     */
    public QueryPendAuitCountRes getPendAuitCount(QueryPendAuitCountReq req) ;
    
    /**
     * 
     * 申请审核任务查询接口[申请箱](getApplyAuitList)
     *
     * @author wm
     * 2015年10月28日 下午4:24:39
     * @param page
     * @param req
     * @return
     *
     */
    public QueryMongoPageRes getApplyAuitListByPage(MongoPage page,QueryApplyAuitListReq req);


    /**
     * 
     * 已办理审核信息查询接口[已办箱](getAlreadyAuitList)
     *
     * @author wm
     * 2015年10月28日 下午4:25:06
     * @param page
     * @param req
     * @return
     *
     */
    public QueryMongoPageRes getAlreadyAuitListByPage(MongoPage page,QueryAlreadyAuitListReq req);


}
