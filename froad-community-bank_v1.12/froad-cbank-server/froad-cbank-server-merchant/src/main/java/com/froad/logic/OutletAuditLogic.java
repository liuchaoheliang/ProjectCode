package com.froad.logic;

import com.froad.po.Outlet;
import com.froad.po.OutletTemp;
import com.froad.po.Result;
import com.froad.po.mq.AuditMQ;

public interface OutletAuditLogic {
	
	/**
	 * 录入审核处理
	 * @param auditMq 审核对象
	 * @param outlet  门店对象
	 * @return Result
	 * @throws Exception
	 */
	public Result auditOutlet(AuditMQ auditMq,Outlet outlet) throws Exception;
	
	
	/**
	 * 新增门店临时表.
	 * 
	 * @param outletTemp
	 * @return
	 * @throws Exception
	 */
	public String addOutletTemp(OutletTemp outletTemp)throws Exception;
	

	/**
	 * 门店编辑审核操作.
	 * 
	 * @param auditId
	 * @return
	 * @throws Exception
	 */
	public Result auditOutletEdit(AuditMQ auditMq,Outlet outlet) throws Exception ;
	
	/**
	 * 编辑审核查询编辑门店信息.
	 * 
	 * @param auditId
	 * @return
	 * @throws Exception
	 */
	public OutletTemp findAuditOutletByAuditId(String auditId) throws Exception;
	
	/**
	 * 获取编辑门店待审核门店临时对象.
	 * 
	 * @param outletTemp
	 * @return
	 * @throws Exception
	 */
	public OutletTemp findOutletTemp(OutletTemp outletTemp) throws Exception;
	
	/**
	 * 删除OutletTemp操作.
	 * 
	 * @param outletTemp
	 * @return
	 */
	public Boolean removeOutletTemp(OutletTemp outletTemp)throws Exception;
	
	
	
	/**
	 * 修改Out
	 * @param outletTemp
	 * @return
	 * @throws Exception
	 */
	public Boolean uddateOutletTemp(OutletTemp outletTemp)throws Exception;
	
	
	/**
	 * 通过门店Id及流水号查询门店信息.
	 * 
	 * @param outletId
	 * @param auditId
	 * @return
	 * @throws Exception
	 */
	public OutletTemp findOutletTempByOutletId(String outletId,String auditId)throws Exception;
	
	
	/**
	 * 更新审核门店流水号.
	 * @param outletTemp
	 * @return
	 * @throws Exception
	 */
	public Boolean updateOutletTempByAuditId(OutletTemp outletTemp) throws Exception;	
}