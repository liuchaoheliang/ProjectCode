package com.froad.logic;

import com.froad.po.MinatoSingleParamInfo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.active.MinatoSingleProductListVo;

public interface MinatoSingleProductLogic {

	/**
	 * @Title: findProductListOfMinatoSingle
	 * @Description: 商品凑单逻辑接口
	 * @author: shenshaocheng 2015年11月9日
	 * @modify: shenshaocheng 2015年11月9日
	 * @param minatoSingleParamInfo 凑单商品相关相关参数
	 * @param arg0分页信息
	 * @return 凑单商品信息.
	 */
	public MinatoSingleProductListVo findProductListOfMinatoSingle(
			MinatoSingleParamInfo minatoSingleParamInfo, PageVo arg0);
	
	 /**
	  * @Title: isFirstQuery
	  * @Description: 判断是否包含商品ID同时第一次进入
	  * @author: shenshaocheng 2015年11月18日
	  * @modify: shenshaocheng 2015年11月18日
	  * @param minatoSingleParamInfo 条件信息
	  * @return
	 */	
	public boolean isFirstQuery(MinatoSingleParamInfo minatoSingleParamInfo);
}
