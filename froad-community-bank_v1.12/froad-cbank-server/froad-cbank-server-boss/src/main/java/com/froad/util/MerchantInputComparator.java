/**
 * Project Name:froad-cbank-server-boss
 * File Name:MerchantInputComparator.java
 * Package Name:com.froad.util
 * Date:2015年11月9日下午4:54:43
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.util;

import java.util.Comparator;

import com.froad.po.MerchantCategoryInput;

/**
 * ClassName:MerchantInputComparator
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月9日 下午4:54:43
 * @author   kevin
 * @version  
 * @see 	 
 */
public class MerchantInputComparator implements Comparator<MerchantCategoryInput> {

	@Override
	public int compare(MerchantCategoryInput o1, MerchantCategoryInput o2) {
		long rs = o1.getCreateTime().getTime() - o2.getCreateTime().getTime();
		if(rs < 0) {
			return 1;
		} else if(rs == 0) {
			return 0;
		} else {
			return -1;
		}
	}
}
