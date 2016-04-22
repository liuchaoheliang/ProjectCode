package com.froad.cbank.coremodule.normal.boss.logic.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.froad.cbank.coremodule.normal.boss.common.db.mysql.bean.Page;
import com.froad.cbank.coremodule.normal.boss.entity.BaseReportEntity;
import com.froad.cbank.coremodule.normal.boss.entity.OrderEntity;
import com.froad.cbank.coremodule.normal.boss.logic.ProductCategoryReportLogic;
import com.froad.cbank.coremodule.normal.boss.mapper.OrderMapper;
/**
 * 类目销售报表 logicImpl
 * @author liaopeixin
 *	@date 2015年11月3日 下午3:40:07
 */
@Repository
public class ProductCategoryReportLogicImpl implements
		ProductCategoryReportLogic {

	@Resource
	private OrderMapper orderMapper;
	
	@Override
	public List<OrderEntity> getListByPage(
			Page<OrderEntity> page, BaseReportEntity base)
			throws Exception {
		if(StringUtils.isNotBlank(base.getClientId()) && StringUtils.isBlank(base.getBusinessType())){
			if(StringUtils.isNotBlank(base.getOrgCode())){
				return orderMapper.getListByOrgCodeByPage(page, base);
			}
			//clientId true businessType false
			return orderMapper.getListByClientIdByPage(page, base);
		}else if(StringUtils.isNotBlank(base.getBusinessType()) && StringUtils.isBlank(base.getClientId())){
			//clientId false businessType true
			return orderMapper.getListByTypeByPage(page, base);
		}else if(StringUtils.isNotBlank(base.getBusinessType()) && StringUtils.isNotBlank(base.getClientId())){
			if(StringUtils.isNotBlank(base.getOrgCode())){
				return orderMapper.getListByOrgCodeAndTypeByPage(page, base);
			}
			//clientId true businessType true
			return orderMapper.getListByClientIdAndTypeByPage(page, base);
		}else{
			//clientId false businessType false
			return orderMapper.getListByPage(page, base);
		}
	}

}
