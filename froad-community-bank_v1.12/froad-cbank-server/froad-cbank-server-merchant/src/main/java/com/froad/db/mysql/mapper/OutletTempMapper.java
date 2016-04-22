package com.froad.db.mysql.mapper;

import org.apache.ibatis.annotations.Param;

import com.froad.po.OutletTemp;

/**
 * 门店临时表mapper.
 * 
 * @author user
 *
 */
public interface OutletTempMapper {

	/**
	 * 门店审核通过在审核.
	 * 
	 * @param outletTemp
	 * @return
	 */
	public Boolean addOutletTemp(OutletTemp outletTemp) ;
	
	
	/**
	 * 通过审核Id查找门店审核信息.
	 * 
	 * @param auditId
	 * @return
	 */
	public OutletTemp findOutletTempByAuditId(String auditId);
	
	
	/**
	 * 通过门店临时对象获取门店临时对象.
	 *  
	 * @param outletTemp
	 * @return
	 */
	public OutletTemp findOutletTemp(OutletTemp outletTemp);
	
	
	/**
	 * 删除OutletTemp操作.
	 * 
	 * @param outletTemp
	 * @return
	 */
	public Boolean removeOutletTemp(OutletTemp outletTemp);
	
	
	/**
	 * 通过门店Id获取门店临时信息.
	 * 
	 * @param outletId
	 * @return
	 */
	public OutletTemp findOutletTempByOutletId(@Param("outletId")String outletId,@Param("auditId")String auditId);
	
	/**
	 * 修改门店编辑信息.
	 * @param outletTemp
	 * @return
	 */
	public Boolean uddateOutletTemp(OutletTemp outletTemp);
	
	/**
	 * 修改门店临时审核流水号.
	 * @param outletTemp
	 * @return
	 */
	public Boolean updateOutletTempByAuditId(OutletTemp outletTemp);
}