package com.froad.CB.service;

import java.util.List;
import javax.jws.WebService;
import com.froad.CB.po.GoodsGroupRackImages;

/** 
 * @author TXL 
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
@WebService
public interface GoodsGroupRackImagesService {
	
	/**
	 * 
	 * INSERT GoodsGroupRackImages
	 */
	Integer addGoodsGroupRackImages(GoodsGroupRackImages cgcri);
	
	/**
	 * 
	 * UPDATE GoodsGroupRackImages(SET STATE)
	 */
    boolean deleteGoodsGroupRackImages(GoodsGroupRackImages cgcri);
    
    /**
	 * 
	 * UPDATE GoodsGroupRackImages
	 */
    boolean updateGoodsGroupRackImages(GoodsGroupRackImages cgcri);
    
    /**
	 * 
	 * SELECT GoodsGroupRackImages BY PRIMARY KEY
	 */
    GoodsGroupRackImages getByPrimaryKey(Integer id);

    /**
	 * 
	 * SELECT ALL GoodsGroupRackImages
	 */
    List<GoodsGroupRackImages> getAllGoodsGroupRackImages();
    
    /**
	 * 
	 * SELECT GoodsGroupRackImages BY goods_Group_rack_id
	 */
    List<GoodsGroupRackImages> getByGoodsGroupRackId(String rackId);



    
    
    

	
}	