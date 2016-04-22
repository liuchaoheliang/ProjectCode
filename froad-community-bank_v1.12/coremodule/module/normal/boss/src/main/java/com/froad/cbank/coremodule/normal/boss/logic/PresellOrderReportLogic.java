package com.froad.cbank.coremodule.normal.boss.logic;

import java.util.List;

import com.froad.cbank.coremodule.normal.boss.common.db.mysql.bean.Page;
import com.froad.cbank.coremodule.normal.boss.entity.BaseReportEntity;
import com.froad.cbank.coremodule.normal.boss.entity.OrderEntity;

public interface PresellOrderReportLogic {
	
	/**
	 * 按日列表分页查询
	 * @tilte getListByPage
	 * @author zxl
	 * @date 2015年11月3日 下午1:21:05
	 * @param page
	 * @param base
	 * @return
	 * @throws Exception
	 */
	List<OrderEntity> getListByPage(Page<OrderEntity> page,BaseReportEntity base) throws Exception;
	
}
