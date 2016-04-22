package com.froad.CB.service;

import java.util.List;
import javax.jws.WebService;
import com.froad.CB.po.ClientGoodsGroupRackImages;

/** 
 * @author TXL 
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
@WebService
public interface ClientGoodsGroupRackImagesService {
	
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