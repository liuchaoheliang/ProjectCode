package com.froad.cbank.coremodule.normal.boss.logic.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.froad.cbank.coremodule.normal.boss.common.db.mysql.bean.Page;
import com.froad.cbank.coremodule.normal.boss.entity.BaseReportEntity;
import com.froad.cbank.coremodule.normal.boss.entity.OrderEntity;
import com.froad.cbank.coremodule.normal.boss.logic.ProductSellReportLogic;
import com.froad.cbank.coremodule.normal.boss.mapper.OrderMapper;

@Repository
@Transactional(rollbackFor=Exception.class)
public class ProductSellReportLogicImpl implements ProductSellReportLogic{
	
	@Resource
	OrderMapper orderMapper;

	@Override
	public List<OrderEntity> getListByPage(Page<OrderEntity> page,BaseReportEntity base) throws Exception {
		return orderMapper.getProductSellListByPage(page, base);
	}

}
