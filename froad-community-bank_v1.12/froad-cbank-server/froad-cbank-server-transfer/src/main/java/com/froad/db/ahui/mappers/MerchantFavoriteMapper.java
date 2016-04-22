package com.froad.db.ahui.mappers;

import java.util.List;

import com.froad.fft.persistent.entity.MerchantFavorite;

public interface MerchantFavoriteMapper {

	public List<MerchantFavorite> selectByConditions();
}

