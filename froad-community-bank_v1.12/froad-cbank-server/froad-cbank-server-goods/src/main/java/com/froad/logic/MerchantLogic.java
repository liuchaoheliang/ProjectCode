package com.froad.logic;

import com.froad.thrift.vo.OutletDetailSimplePageVoRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.QueryProductFilterVo;

/**
 * 调用商户模块接口的处理逻辑
 * @author wangyan
 *
 */
public interface MerchantLogic {

	public OutletDetailSimplePageVoRes queryMerchantOutlets(QueryProductFilterVo filterVo, PageVo pageVo, int skip) throws Exception;
}
