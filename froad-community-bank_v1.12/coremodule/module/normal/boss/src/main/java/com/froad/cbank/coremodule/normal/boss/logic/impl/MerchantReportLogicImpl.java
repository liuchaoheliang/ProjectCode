package com.froad.cbank.coremodule.normal.boss.logic.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.froad.cbank.coremodule.normal.boss.common.db.mysql.bean.Page;
import com.froad.cbank.coremodule.normal.boss.entity.BaseReportEntity;
import com.froad.cbank.coremodule.normal.boss.entity.OrderEntity;
import com.froad.cbank.coremodule.normal.boss.logic.MerchantReportLogic;
import com.froad.cbank.coremodule.normal.boss.mapper.OrderMapper;
/**
 * 商户销售统计
 * @author liaopeixin
 *	@date 2015年11月5日 上午10:15:45
 */
@Repository
public class MerchantReportLogicImpl implements MerchantReportLogic {

	@Resource
	private OrderMapper orderMapper;
	/**
	 * 分页查询列表
	 */
	@Override
	public List<OrderEntity> getListByPage(Page<OrderEntity> page,
			BaseReportEntity base) throws Exception {
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
