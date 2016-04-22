package com.froad.CB.dao;

import java.util.List;
import com.froad.CB.po.Advert;
import com.froad.CB.po.ClientGoodsCarryRackImages;

/** 
 * @author TXL 
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
public interface ClientGoodsCarryRackImagesDao {
	
	/**
	 * 
	 * INSERT ClientGoodsCarryRackImages
	 */
	Integer addClientGoodsCarryRackImages(ClientGoodsCarryRackImages cgcri);
	
	/**
	 * 
	 * UPDATE ClientGoodsCarryRackImages(SET STATE)
	 */
    boolean deleteClientGoodsCarryRackImages(ClientGoodsCarryRackImages cgcri);
    
    /**
	 * 
	 * UPDATE ClientGoodsCarryRackImages
	 */
    boolean updateClientGoodsCarryRackImages(ClientGoodsCarryRackImages cgcri);
    
    /**
	 * 
	 * SELECT ClientGoodsCarryRackImages BY PRIMARY KEY
	 */
    ClientGoodsCarryRackImages getByPrimaryKey(Integer id);

    /**
	 * 
	 * SELECT ALL ClientGoodsCarryRackImages
	 */
    List<ClientGoodsCarryRackImages> getAllClientGoodsCarryRackImages();
    
    /**
	 * 
	 * SELECT ClientGoodsCarryRackImages BY client_goods_carry_rack_id
	 */
    List<ClientGoodsCarryRackImages> getByClientGoodsCarryRackId(String rackId);

	
	
}
