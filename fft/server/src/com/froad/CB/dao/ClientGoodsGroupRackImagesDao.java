package com.froad.CB.dao;

import java.util.List;
import com.froad.CB.po.Advert;
import com.froad.CB.po.ClientGoodsGroupRackImages;

/** 
 * @author TXL 
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
public interface ClientGoodsGroupRackImagesDao {
	
	/**
	 * 
	 * INSERT ClientGoodsGroupRackImages
	 */
	Integer addClientGoodsGroupRackImages(ClientGoodsGroupRackImages cgcri);
	
	/**
	 * 
	 * UPDATE ClientGoodsGroupRackImages(SET STATE)
	 */
    boolean deleteClientGoodsGroupRackImages(ClientGoodsGroupRackImages cgcri);
    
    /**
	 * 
	 * UPDATE ClientGoodsGroupRackImages
	 */
    boolean updateClientGoodsGroupRackImages(ClientGoodsGroupRackImages cgcri);
    
    /**
	 * 
	 * SELECT ClientGoodsGroupRackImages BY PRIMARY KEY
	 */
    ClientGoodsGroupRackImages getByPrimaryKey(Integer id);

    /**
	 * 
	 * SELECT ALL ClientGoodsGroupRackImages
	 */
    List<ClientGoodsGroupRackImages> getAllClientGoodsGroupRackImages();
    
    /**
	 * 
	 * SELECT ClientGoodsGroupRackImages BY client_goods_Group_rack_id
	 */
    List<ClientGoodsGroupRackImages> getByClientGoodsGroupRackId(String rackId);

	
	
}
