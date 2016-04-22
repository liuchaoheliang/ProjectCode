package com.froad.CB.dao;

import java.util.List;
import com.froad.CB.po.Advert;
import com.froad.CB.po.GoodsRebateRackImages;

/** 
 * @author TXL 
 * @date 2013-2-26 pm
 * @version 1.0
 * 
 */
public interface GoodsRebateRackImagesDao {
	
	/**
	 * 
	 * INSERT GoodsRebateRackImages
	 */
	Integer addGoodsRebateRackImages(GoodsRebateRackImages cgcri);
	
	/**
	 * 
	 * UPDATE GoodsRebateRackImages(SET STATE)
	 */
    boolean deleteGoodsRebateRackImages(GoodsRebateRackImages cgcri);
    
    /**
	 * 
	 * UPDATE GoodsRebateRackImages
	 */
    boolean updateGoodsRebateRackImages(GoodsRebateRackImages cgcri);
    
    /**
	 * 
	 * SELECT GoodsRebateRackImages BY PRIMARY KEY
	 */
    GoodsRebateRackImages getByPrimaryKey(Integer id);

    /**
	 * 
	 * SELECT ALL GoodsRebateRackImages
	 */
    List<GoodsRebateRackImages> getAllGoodsRebateRackImages();
    
    /**
	 * 
	 * SELECT GoodsRebateRackImages BY goods_Rebate_rack_id
	 */
    List<GoodsRebateRackImages> getByGoodsRebateRackId(String rackId);

	
	
}
