package com.froad.CB.service;

import java.util.List;
import javax.jws.WebService;
import com.froad.CB.po.ClientGoodsGatherRackImages;

/** 
 * @author TXL 
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
@WebService
public interface ClientGoodsGatherRackImagesService {
	
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