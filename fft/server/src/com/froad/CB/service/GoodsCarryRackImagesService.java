package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;
import com.froad.CB.po.GoodsCarryRackImages;

/** 
 * @author TXL 
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
@WebService
public interface GoodsCarryRackImagesService {
	
	/**
	 * 
	 * INSERT GoodsCarryRackImages
	 */
	Integer addGoodsGroupRackImages(GoodsCarryRackImages cgcri);
	
	/**
	 * 
	 * UPDATE GoodsCarryRackImages(SET STATE)
	 */
    boolean deleteGoodsGroupRackImages(GoodsCarryRackImages cgcri);
    
    /**
	 * 
	 * UPDATE GoodsCarryRackImages
	 */
    boolean updateGoodsGroupRackImages(GoodsCarryRackImages cgcri);
    
    /**
	 * 
	 * SELECT GoodsCarryRackImages BY PRIMARY KEY
	 */
    GoodsCarryRackImages getByPrimaryKey(Integer id);

    /**
	 * 
	 * SELECT ALL GoodsCarryRackImages
	 */
    List<GoodsCarryRackImages> getAllGoodsGroupRackImages();
    
    /**
	 * 
	 * SELECT GoodsCarryRackImages BY goods_carry_rack_id
	 */
    List<GoodsCarryRackImages> getByGoodsCarryRackId(String rackId);



    
    
    

	
}	