package com.froad.cbank.coremodule.normal.boss.logic;

import java.util.List;

import com.froad.cbank.coremodule.normal.boss.common.db.mysql.bean.Page;
import com.froad.cbank.coremodule.normal.boss.entity.BaseReportEntity;
import com.froad.cbank.coremodule.normal.boss.entity.OrderEntity;

/**
 * 商户销售统计报表
 * @author liaopeixin
 *	@date 2015年11月5日 上午10:13:41
 */
public interface MerchantReportLogic {

	public List<OrderEntity> getListByPage(Page<OrderEntity> page,BaseReportEntity base) throws Exception;
}
