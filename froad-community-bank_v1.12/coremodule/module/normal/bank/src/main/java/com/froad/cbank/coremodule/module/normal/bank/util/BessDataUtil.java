package com.froad.cbank.coremodule.module.normal.bank.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.module.normal.bank.enums.BessDataEnum;
import com.froad.thrift.vo.Restrictions;

public class BessDataUtil {
	
	/**
	 * 
	 * @param merchantName
	 *            商户名称:如果为空传null即可
	 * @param outletName
	 *            门店名称:如果为空传null即可
	 * @param productName
	 *            商品名称:如果为空传null即可
	 * @param orBessData
	 *            返回orBessData对象
	 * @return
	 */
	public static Map<Restrictions, String> getOrBessDataMapWithMerchantNameAndOutName(
			String merchantName, String outletName, String productName,
			String productCategory, Map<Restrictions, String> orBessData) {
		Map<String, Object> tempMap = new HashMap<String, Object>();
		if (StringUtil.isNotBlank(merchantName) ){
			tempMap.put(BessDataEnum.merchantName.getKey(), merchantName);
			tempMap.put(BessDataEnum.merchantFullName.getKey(), merchantName);
		}
		if (StringUtil.isNotBlank(outletName) ){
			tempMap.put(BessDataEnum.outletName.getKey(), outletName);
			tempMap.put(BessDataEnum.outletFullName.getKey(), outletName);
		}

		if (StringUtil.isNotBlank(productName)) {
			tempMap.put(BessDataEnum.productName.getKey(), productName);
			tempMap.put(BessDataEnum.productFullName.getKey(), productName);
		}
		if (StringUtil.isNotBlank(productCategory)) {
			// 商品分类
			tempMap.put(BessDataEnum.categoryInfo.getKey(), productCategory);
		}
		orBessData.put(Restrictions.LIKE, JSON.toJSONString(tempMap));
		return orBessData;
	}
}
