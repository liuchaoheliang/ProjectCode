package com.froad.CB.dao;

import java.util.List;
import com.froad.CB.po.Advert;
import com.froad.CB.po.GoodsGatherRackImages;

/** 
 * @author TXL 
 * @date 2013-2-26 pm
 * @version 1.0
 * 
 */
public interface GoodsGatherRackImagesDao {
	
	/**
	 * 
	 * INSERT GoodsGatherRackImages
	 */
	Integer addGoodsGatherRackImages(GoodsGatherRackImages cgcri);
	
	/**
	 * 
	 * UPDATE GoodsGatherRackImages(SET STATE)
	 */
    boolean deleteGoodsGatherRackImages(GoodsGatherRackImages cgcri);
    
    /**
	 * 
	 * UPDATE GoodsGatherRackImages
	 */
    boolean updateGoodsGatherRackImages(GoodsGatherRackImages cgcri);
    
    /**
	 * 
	 * SELECT GoodsGatherRackImages BY PRIMARY KEY
	 */
    GoodsGatherRackImages getByPrimaryKey(Integer id);

    /**
	 * 
	 * SELECT ALL GoodsGatherRackImages
	 */
    List<GoodsGatherRackImages> getAllGoodsGatherRackImages();
    
    /**
	 * 
	 * SELECT GoodsGatherRackImages BY goods_Gather_rack_id
	 */
    List<GoodsGatherRackImages> getByGoodsGatherRackId(String rackId);

	
	
}
