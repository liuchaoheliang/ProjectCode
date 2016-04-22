package com.froad.db.mysql.mappers;

import java.util.List;

import com.froad.po.MerchantOutlet;

public interface OutletMapper {

	/**
	 * 查找门店信息
	 * 
	 * @param merchantOutlet
	 * @return
	 */
	public List<MerchantOutlet> findByCondition(MerchantOutlet merchantOutlet);

}
