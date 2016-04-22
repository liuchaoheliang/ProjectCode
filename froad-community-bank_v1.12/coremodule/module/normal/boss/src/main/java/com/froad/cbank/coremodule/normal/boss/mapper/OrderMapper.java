package com.froad.cbank.coremodule.normal.boss.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.cbank.coremodule.normal.boss.common.db.mysql.bean.Page;
import com.froad.cbank.coremodule.normal.boss.entity.BaseReportEntity;
import com.froad.cbank.coremodule.normal.boss.entity.OrderEntity;

public interface OrderMapper {
	
	/**
	 * 查询列表 (无条件)
	 * @tilte getListByPage
	 * @author zxl
	 * @date 2015年11月4日 下午4:45:56
	 * @param page
	 * @param base
	 * @return
	 */
	public List<OrderEntity> getListByPage(Page<OrderEntity> page,@Param("base")BaseReportEntity base);
	
	/**
	 * 根据clientId查询
	 * @tilte getOrderListByClientIdByPage
	 * @author zxl
	 * @date 2015年11月5日 上午10:40:29
	 * @param page
	 * @param base
	 * @return
	 */
	public List<OrderEntity> getListByClientIdByPage(Page<OrderEntity> page,@Param("base")BaseReportEntity base);
	
	/**
	 * 根据type 查询
	 * @tilte getListByTypeByPage
	 * @author zxl
	 * @date 2015年11月6日 上午11:24:21
	 * @param page
	 * @param base
	 * @return
	 */
	public List<OrderEntity> getListByTypeByPage(Page<OrderEntity> page,@Param("base")BaseReportEntity base);
	
	
	/**
	 * 根据clientid and type 查询
	 * @tilte getListByClientIdAndTypeByPage
	 * @author zxl
	 * @date 2015年11月6日 上午11:22:28
	 * @param page
	 * @param base
	 * @return
	 */
	public List<OrderEntity> getListByClientIdAndTypeByPage(Page<OrderEntity> page,@Param("base")BaseReportEntity base);
	
	/**
	 *  根据clientid orgcode查询
	 * @tilte getListByOrgCodeByPage
	 * @author zxl
	 * @date 2015年11月18日 下午1:25:45
	 * @param page
	 * @param base
	 * @return
	 */
	public List<OrderEntity> getListByOrgCodeByPage(Page<OrderEntity> page,@Param("base")BaseReportEntity base);
	
	
	/**
	 * 根据clientid orgcode type查询
	 * @tilte getListByOrgCodeAndTypeByPage
	 * @author zxl
	 * @date 2015年11月18日 下午1:26:05
	 * @param page
	 * @param base
	 * @return
	 */
	public List<OrderEntity> getListByOrgCodeAndTypeByPage(Page<OrderEntity> page,@Param("base")BaseReportEntity base);
	
	
	/**
	 * 商品销售列表
	 * @tilte getProductSellListByPage
	 * @author zxl
	 * @date 2015年11月5日 下午3:14:03
	 * @param page
	 * @param base
	 * @return
	 */
	public List<OrderEntity> getProductSellListByPage(Page<OrderEntity> page,@Param("base")BaseReportEntity base);
	
}
