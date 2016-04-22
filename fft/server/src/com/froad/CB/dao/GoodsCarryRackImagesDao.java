package com.froad.CB.dao;

import java.util.List;
import com.froad.CB.po.Advert;
import com.froad.CB.po.GoodsCarryRackImages;

/** 
 * @author TXL 
 * @date 2013-2-26 pm
 * @version 1.0
 * 
 */
public interface GoodsCarryRackImagesDao {
	
	/**
	 * 
	 * INSERT GoodsCarryRackImages
	 */
	Integer addGoodsCarryRackImages(GoodsCarryRackImages cgcri);
	
	/**
	 * 
	 * UPDATE GoodsCarryRackImages(SET STATE)
	 */
    boolean deleteGoodsCarryRackImages(GoodsCarryRackImages cgcri);
    
    /**
	 * 
	 * UPDATE GoodsCarryRackImages
	 */
    boolean updateGoodsCarryRackImages(GoodsCarryRackImages cgcri);
    
    /**
	 * 
	 * SELECT GoodsCarryRackImages BY PRIMARY KEY
	 */
    GoodsCarryRackImages getByPrimaryKey(Integer id);

    /**
	 * 
	 * SELECT ALL GoodsCarryRackImages
	 */
    List<GoodsCarryRackImages> getAllGoodsCarryRackImages();
    
    /**
	 * 
	 * SELECT GoodsCarryRackImages BY goods_carry_rack_id
	 */
    List<GoodsCarryRackImages> getByGoodsCarryRackId(String rackId);

	
	
}
