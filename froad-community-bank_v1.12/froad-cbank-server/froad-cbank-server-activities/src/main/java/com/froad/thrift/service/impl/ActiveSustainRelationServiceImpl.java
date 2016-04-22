package com.froad.thrift.service.impl;

import org.apache.thrift.TException;

import com.froad.db.mysql.bean.Page;
import com.froad.logic.ActiveBaseRuleLogic;
import com.froad.logic.VouchersInfoLogic;
import com.froad.logic.impl.ActiveBaseRuleLogicImpl;
import com.froad.logic.impl.VouchersInfoLogicImpl;
import com.froad.po.ActiveBaseRule;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.ActiveSustainRelationService.Iface;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.active.ActiveBaseRuleVo;
import com.froad.thrift.vo.active.FindAllPromotionActiveVO;
import com.froad.thrift.vo.active.FindPromotionActiveByPageVO;
import com.froad.util.ActiveUtils;
import com.froad.util.BeanUtil;

public class ActiveSustainRelationServiceImpl extends BizMonitorBaseService
		implements Iface {
	ActiveBaseRuleLogic activeBaseRuleLogic = new ActiveBaseRuleLogicImpl();
	
	VouchersInfoLogic vouchersInfoLogic = new VouchersInfoLogicImpl();
	
	public ActiveSustainRelationServiceImpl() {
	}

	public ActiveSustainRelationServiceImpl(String n, String v) {
		super(n, v);
	}

	/**
	 * @Title: findAllPromotionActive
	 * @Description: 促销活动列表接口实现
	 * @author: shenshaocheng 2015年11月27日
	 * @modify: shenshaocheng 2015年11月27日
	 * @param activeBaseRuleVo
	 *            活动基础信息
	 * @return
	 * @throws TException
	 * @see com.froad.thrift.service.ActiveSustainRelationService.Iface#findAllPromotionActive(com.froad.thrift.vo.active.ActiveBaseRuleVo)
	 */
	@Override
	public FindAllPromotionActiveVO findAllPromotionActive(
			ActiveBaseRuleVo activeBaseRuleVo) throws TException {
		ActiveBaseRule activeBaseRule = (ActiveBaseRule) BeanUtil
				.copyProperties(ActiveBaseRule.class, activeBaseRuleVo);
		activeBaseRule = ActiveUtils.processingNullData(activeBaseRuleVo, activeBaseRule);
		FindAllPromotionActiveVO promotionActiveVO = this.activeBaseRuleLogic
				.findAllPromotionActive(activeBaseRule);
		return promotionActiveVO;
	}

	/**
	 * @Title: findPromotionActiveByPage
	 * @Description: 详情页查询活动支持列表分页接口(分页)
	 * @author: shenshaocheng 2015年11月27日
	 * @modify: shenshaocheng 2015年11月27日
	 * @param pageVo
	 *            分页信息
	 * @param activeBaseRuleVo
	 *            活动基础信息
	 * @return
	 * @throws TException
	 * @see com.froad.thrift.service.ActiveSustainRelationService.Iface#findPromotionActiveByPage(com.froad.thrift.vo.PageVo,
	 *      com.froad.thrift.vo.active.ActiveBaseRuleVo)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public FindPromotionActiveByPageVO findPromotionActiveByPage(PageVo pageVo,
			ActiveBaseRuleVo activeBaseRuleVo) throws TException {
		FindPromotionActiveByPageVO promotionActiveByPageVO = new FindPromotionActiveByPageVO();
		ActiveBaseRule activeBaseRule  = (ActiveBaseRule) BeanUtil.copyProperties(ActiveBaseRule.class, activeBaseRuleVo);
		activeBaseRule = ActiveUtils.processingNullData(activeBaseRuleVo, activeBaseRule);
		Page page = (Page) BeanUtil.copyProperties(Page.class, pageVo);
		promotionActiveByPageVO = this.activeBaseRuleLogic.findActiveBaseRuleByPage(page, activeBaseRule);
		return promotionActiveByPageVO;
	}
}
