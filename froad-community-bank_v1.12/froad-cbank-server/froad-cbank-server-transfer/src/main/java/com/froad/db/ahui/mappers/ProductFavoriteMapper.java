package com.froad.db.ahui.mappers;

import java.util.List;

import com.froad.fft.persistent.entity.ProductFavorite;

public interface ProductFavoriteMapper {

	public List<ProductFavorite> selectByCondition();
}

