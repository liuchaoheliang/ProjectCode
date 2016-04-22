package com.froad.CB.dao;

import java.util.List;
import com.froad.CB.po.Advert;
import com.froad.CB.po.ClientGoodsGatherRackImages;

/** 
 * @author TXL 
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
public interface ClientGoodsGatherRackImagesDao {
	
	/**
	 * 
	 * INSERT ClientGoodsGatherRackImages
	 */
	Integer addClientGoodsGatherRackImages(ClientGoodsGatherRackImages cgcri);
	
	/**
	 * 
	 * UPDATE ClientGoodsGatherRackImages(SET STATE)
	 */
    boolean deleteClientGoodsGatherRackImages(ClientGoodsGatherRackImages cgcri);
    
    /**
	 * 
	 * UPDATE ClientGoodsGatherRackImages
	 */
    boolean updateClientGoodsGatherRackImages(ClientGoodsGatherRackImages cgcri);
    
    /**
	 * 
	 * SELECT ClientGoodsGatherRackImages BY PRIMARY KEY
	 */
    ClientGoodsGatherRackImages getByPrimaryKey(Integer id);

    /**
	 * 
	 * SELECT ALL ClientGoodsGatherRackImages
	 */
    List<ClientGoodsGatherRackImages> getAllClientGoodsGatherRackImages();
    
    /**
	 * 
	 * SELECT ClientGoodsGatherRackImages BY client_goods_Gather_rack_id
	 */
    List<ClientGoodsGatherRackImages> getByClientGoodsGatherRackId(String rackId);

	
	
}
