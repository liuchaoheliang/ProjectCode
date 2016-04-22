package com.froad.thrift.service.impl;

import org.apache.thrift.TException;

import com.froad.logic.ActiveSearchMerchanLogic;
import com.froad.logic.ActiveSearchProductLogic;
import com.froad.logic.MinatoSingleProductLogic;
import com.froad.logic.impl.ActiveSearchMerchantLogicImpl;
import com.froad.logic.impl.ActiveSearchProductLogicImpl;
import com.froad.logic.impl.MinatoSingleProductLogicImpl;
import com.froad.po.ActiveSearchMerchantInfo;
import com.froad.po.ActiveSearchProductInfo;
import com.froad.po.MinatoSingleParamInfo;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.ActiveSearchService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.active.FindActiveRuleByMerchantVo;
import com.froad.thrift.vo.active.FindActiveRuleByProductVo;
import com.froad.thrift.vo.active.FindActiveRuleListResVo;
import com.froad.thrift.vo.active.MinatoSingleParamVo;
import com.froad.thrift.vo.active.MinatoSingleProductListVo;
import com.froad.util.BeanUtil;

public class ActiveSearchServiceImpl extends BizMonitorBaseService implements
		ActiveSearchService.Iface {

	/**
	 * @Fields activeSearchLogic : 商户查询逻辑层
	 */
	private ActiveSearchMerchanLogic activeSearchLogic = new ActiveSearchMerchantLogicImpl();

	/**
	 * @Fields activeSearchProductLogic : 商品查询逻辑层
	 */
	private ActiveSearchProductLogic activeSearchProductLogic = new ActiveSearchProductLogicImpl();

	/**
	 * @Fields minatoSingleProductLogic : 商品凑单逻辑层
	 */
	private MinatoSingleProductLogic minatoSingleProductLogic = new MinatoSingleProductLogicImpl();

	public ActiveSearchServiceImpl() {
	}

	public ActiveSearchServiceImpl(String n, String v) {
		super(n, v);
	}

	/**
	 * @Title: findActiveRuleByMerchantIds
	 * @Description: 根据商户ID获取商户相关活动信息。
	 * @author: shenshaocheng 2015年11月6日
	 * @modify: shenshaocheng 2015年11月6日
	 * @param arg0
	 *            前端传入的商户ID集合
	 * @return
	 * @throws TException
	 * @see com.froad.thrift.service.ActiveSearchService.Iface#findActiveRuleByMerchantIds(com.froad.thrift.vo.active.FindActiveRuleByMerchantVo)
	 */
	@Override
	public FindActiveRuleListResVo findActiveRuleByMerchantIds(
			FindActiveRuleByMerchantVo arg0) throws TException {
		FindActiveRuleListResVo findActiveRuleListResVo = new FindActiveRuleListResVo();
		// 类型转换
		ActiveSearchMerchantInfo activeSearchMerchantInfo = (ActiveSearchMerchantInfo) BeanUtil
				.copyProperties(ActiveSearchMerchantInfo.class, arg0);
		findActiveRuleListResVo = activeSearchLogic
				.findActiveRuleByMerchantIds(activeSearchMerchantInfo);
		return findActiveRuleListResVo;
	}

	/**
	 * @Title: findActiveRuleByProductIds
	 * @Description: 根据商品ID获取相关商品信息。
	 * @author: shenshaocheng 2015年11月6日
	 * @modify: shenshaocheng 2015年11月6日
	 * @param arg0
	 *            前端传入的商品ID集合
	 * @return
	 * @throws TException
	 * @see com.froad.thrift.service.ActiveSearchService.Iface#findActiveRuleByProductIds(com.froad.thrift.vo.active.FindActiveRuleByProductVo)
	 */
	@Override
	public FindActiveRuleListResVo findActiveRuleByProductIds(
			FindActiveRuleByProductVo arg0) throws TException {
		FindActiveRuleListResVo findActiveRuleListResVo = new FindActiveRuleListResVo();
		// 类型转换
		ActiveSearchProductInfo activeSearchProductInfo = (ActiveSearchProductInfo) BeanUtil
				.copyProperties(ActiveSearchProductInfo.class, arg0);
		findActiveRuleListResVo = activeSearchProductLogic
				.findActiveRuleByProductIds(activeSearchProductInfo);
		return findActiveRuleListResVo;
	}

	/**
	 * @Title: findProductListOfMinatoSingle
	 * @Description: 商品凑单查询。
	 * @author: shenshaocheng 2015年11月6日
	 * @modify: shenshaocheng 2015年11月6日
	 * @param arg0
	 * @return
	 * @throws TException
	 * @see com.froad.thrift.service.ActiveSearchService.Iface#findProductListOfMinatoSingle(com.froad.thrift.vo.active.MinatoSingleParamVo)
	 */
	@Override
	public MinatoSingleProductListVo findProductListOfMinatoSingle(PageVo arg0,
			MinatoSingleParamVo minatoSingleParamVo) throws TException {
		MinatoSingleProductListVo minatoSingleProductListVo = new MinatoSingleProductListVo();
		MinatoSingleParamInfo minatoSingleParamInfo = (MinatoSingleParamInfo) BeanUtil
				.copyProperties(MinatoSingleParamInfo.class, minatoSingleParamVo);	
		if(this.minatoSingleProductLogic.isFirstQuery(minatoSingleParamInfo)) {
			arg0.setPageSize(arg0.getPageSize() - 1);
		}
		
		minatoSingleProductListVo = minatoSingleProductLogic
				.findProductListOfMinatoSingle(minatoSingleParamInfo, arg0);
		
		return minatoSingleProductListVo;
	}
}
