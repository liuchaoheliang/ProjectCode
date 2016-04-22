package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;
import com.froad.CB.po.ClientGoodsCarryRackImages;

/** 
 * @author TXL 
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
@WebService
public interface ClientGoodsCarryRackImagesService {
	
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