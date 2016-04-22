package com.froad.CB.dao;

import java.util.List;
import com.froad.CB.po.Advert;
import com.froad.CB.po.GoodsExchangeRackImages;

/** 
 * @author TXL 
 * @date 2013-2-26 pm
 * @version 1.0
 * 
 */
public interface GoodsExchangeRackImagesDao {
	
	/**
	 * 
	 * INSERT GoodsExchangeRackImages
	 */
	Integer addGoodsExchangeRackImages(GoodsExchangeRackImages cgcri);
	
	/**
	 * 
	 * UPDATE GoodsExchangeRackImages(SET STATE)
	 */
    boolean deleteGoodsExchangeRackImages(GoodsExchangeRackImages cgcri);
    
    /**
	 * 
	 * UPDATE GoodsExchangeRackImages
	 */
    boolean updateGoodsExchangeRackImages(GoodsExchangeRackImages cgcri);
    
    /**
	 * 
	 * SELECT GoodsExchangeRackImages BY PRIMARY KEY
	 */
    GoodsExchangeRackImages getByPrimaryKey(Integer id);

    /**
	 * 
	 * SELECT ALL GoodsExchangeRackImages
	 */
    List<GoodsExchangeRackImages> getAllGoodsExchangeRackImages();
    
    /**
	 * 
	 * SELECT GoodsExchangeRackImages BY goods_Exchange_rack_id
	 */
    List<GoodsExchangeRackImages> getByGoodsExchangeRackId(String rackId);

	
	
}
