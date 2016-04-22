package com.froad.cbank.coremodule.normal.boss.logic.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.froad.cbank.coremodule.normal.boss.common.db.mysql.bean.Page;
import com.froad.cbank.coremodule.normal.boss.entity.BaseReportEntity;
import com.froad.cbank.coremodule.normal.boss.entity.OrderEntity;
import com.froad.cbank.coremodule.normal.boss.logic.PayTypeReportLogic;
import com.froad.cbank.coremodule.normal.boss.mapper.OrderMapper;
/**
 * 支付方式统计报表 logic 实现类
 * @author liaopeixin
 *	@date 2015年11月3日 上午11:44:25
 */
@Repository
public class PayTypeReportLogicImpl implements PayTypeReportLogic {

	@Resource
	private OrderMapper orderMapper;
	/**
	 * 分页查询
	 */
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
