package com.froad.cbank.coremodule.normal.boss.logic;

import java.util.List;

import com.froad.cbank.coremodule.normal.boss.common.db.mysql.bean.Page;
import com.froad.cbank.coremodule.normal.boss.entity.BaseReportEntity;
import com.froad.cbank.coremodule.normal.boss.entity.OrderEntity;
/**
 * 类目销售统计报表 logic
 * @author liaopeixin
 *	@date 2015年11月3日 上午11:42:41
 */
public interface ProductCategoryReportLogic {

	/**
	 * 按日列表分页查询
	 * @param page
	 * @param base
	 * @return
	 * @throws Exception
	 * @author liaopeixin
	 *	@date 2015年11月3日 下午3:35:27
	 */
	public List<OrderEntity> getListByPage(Page<OrderEntity> page,BaseReportEntity base) throws Exception;

}
