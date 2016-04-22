package com.froad.cbank.coremodule.normal.boss.logic.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.froad.cbank.coremodule.normal.boss.common.db.mysql.bean.Page;
import com.froad.cbank.coremodule.normal.boss.entity.BaseReportEntity;
import com.froad.cbank.coremodule.normal.boss.entity.OrderEntity;
import com.froad.cbank.coremodule.normal.boss.logic.TakeOutletReportLogic;
import com.froad.cbank.coremodule.normal.boss.mapper.OrderMapper;

@Repository
public class TakeOutletReportLogicImpl implements TakeOutletReportLogic {

	@Resource
	private OrderMapper orderMapper;
	
	@Override
	public List<OrderEntity> getListByPage(Page<OrderEntity> page, BaseReportEntity base) throws Exception {
		if(StringUtils.isNotBlank(base.getClientId())){
			if(StringUtils.isNotBlank(base.getOrgCode())){
				return orderMapper.getListByOrgCodeByPage(page, base);
			}
			return orderMapper.getListByClientIdByPage(page, base);
		}else{
			return orderMapper.getListByPage(page, base);
		}
	}

}
