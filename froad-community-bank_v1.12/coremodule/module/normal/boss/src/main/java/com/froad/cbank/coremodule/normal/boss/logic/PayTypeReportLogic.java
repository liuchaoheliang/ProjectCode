package com.froad.cbank.coremodule.normal.boss.logic;

import java.util.List;

import com.froad.cbank.coremodule.normal.boss.common.db.mysql.bean.Page;
import com.froad.cbank.coremodule.normal.boss.entity.BaseReportEntity;
import com.froad.cbank.coremodule.normal.boss.entity.OrderEntity;
/**
 * 支付方式分析 logic
 * @author liaopeixin
 *	@date 2015年11月3日 上午11:42:15
 */
public interface PayTypeReportLogic {
	/**
	 * 按日分页查询
	 * @param page
	 * @param base
	 * @return
	 * @throws Exception
	 * @author liaopeixin
	 *	@date 2015年11月4日 上午9:57:17
	 */
	public List<OrderEntity> getListByPage(Page<OrderEntity> page,BaseReportEntity base) throws Exception;
}
