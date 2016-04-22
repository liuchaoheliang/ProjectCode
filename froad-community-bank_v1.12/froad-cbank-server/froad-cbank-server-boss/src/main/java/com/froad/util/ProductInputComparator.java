/**
 * Project Name:froad-cbank-server-boss
 * File Name:ProductInputComparator.java
 * Package Name:com.froad.util
 * Date:2015年11月9日下午5:00:52
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.util;

import java.util.Comparator;

import com.froad.po.ProductCategoryInput;

/**
 * ClassName:ProductInputComparator
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月9日 下午5:00:52
 * @author   kevin
 * @version  
 * @see 	 
 */
public class ProductInputComparator implements Comparator<ProductCategoryInput> {

	@Override
	public int compare(ProductCategoryInput o1, ProductCategoryInput o2) {
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
