package com.froad.CB.dao;

import java.util.List;
import com.froad.CB.po.Advert;
import com.froad.CB.po.ClientGoodsExchangeRackImages;

/** 
 * @author TXL 
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
public interface ClientGoodsExchangeRackImagesDao {
	
	/**
	 * 
	 * INSERT ClientGoodsExchangeRackImages
	 */
	Integer addClientGoodsExchangeRackImages(ClientGoodsExchangeRackImages cgcri);
	
	/**
	 * 
	 * UPDATE ClientGoodsExchangeRackImages(SET STATE)
	 */
    boolean deleteClientGoodsExchangeRackImages(ClientGoodsExchangeRackImages cgcri);
    
    /**
	 * 
	 * UPDATE ClientGoodsExchangeRackImages
	 */
    boolean updateClientGoodsExchangeRackImages(ClientGoodsExchangeRackImages cgcri);
    
    /**
	 * 
	 * SELECT ClientGoodsExchangeRackImages BY PRIMARY KEY
	 */
    ClientGoodsExchangeRackImages getByPrimaryKey(Integer id);

    /**
	 * 
	 * SELECT ALL ClientGoodsExchangeRackImages
	 */
    List<ClientGoodsExchangeRackImages> getAllClientGoodsExchangeRackImages();
    
    /**
	 * 
	 * SELECT ClientGoodsExchangeRackImages BY client_goods_Exchange_rack_id
	 */
    List<ClientGoodsExchangeRackImages> getByClientGoodsExchangeRackId(String rackId);

	
	
}
