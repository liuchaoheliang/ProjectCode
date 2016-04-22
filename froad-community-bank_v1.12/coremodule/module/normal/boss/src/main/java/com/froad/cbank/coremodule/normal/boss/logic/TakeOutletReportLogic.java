package com.froad.cbank.coremodule.normal.boss.logic;

import java.util.List;

import com.froad.cbank.coremodule.normal.boss.common.db.mysql.bean.Page;
import com.froad.cbank.coremodule.normal.boss.entity.BaseReportEntity;
import com.froad.cbank.coremodule.normal.boss.entity.OrderEntity;
/**
 * 提货网点分析报表 logic
 * @author liaopeixin
 *	@date 2015年11月3日 上午11:43:04
 */
public interface TakeOutletReportLogic {
	/**
	 * 按日分页显示
	 * @param page
	 * @param base
	 * @return
	 * @throws Exception
	 * @author liaopeixin
	 *	@date 2015年11月5日 上午10:00:54
	 */
	public List<OrderEntity> getListByPage(Page<OrderEntity> page,BaseReportEntity base) throws Exception;
	
}
