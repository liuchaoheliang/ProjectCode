package com.froad.CB.service;

import java.util.List;
import javax.jws.WebService;
import com.froad.CB.po.GoodsExchangeRackImages;

/** 
 * @author TXL 
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
@WebService
public interface GoodsExchangeRackImagesService {
	
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