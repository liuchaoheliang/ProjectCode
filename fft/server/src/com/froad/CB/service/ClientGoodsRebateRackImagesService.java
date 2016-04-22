package com.froad.CB.service;

import java.util.List;
import javax.jws.WebService;
import com.froad.CB.po.ClientGoodsRebateRackImages;

/** 
 * @author TXL 
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
@WebService
public interface ClientGoodsRebateRackImagesService {
	
	/**
	 * 
	 * INSERT ClientGoodsRebateRackImages
	 */
	Integer addClientGoodsRebateRackImages(ClientGoodsRebateRackImages cgcri);
	
	/**
	 * 
	 * UPDATE ClientGoodsRebateRackImages(SET STATE)
	 */
    boolean deleteClientGoodsRebateRackImages(ClientGoodsRebateRackImages cgcri);
    
    /**
	 * 
	 * UPDATE ClientGoodsRebateRackImages
	 */
    boolean updateClientGoodsRebateRackImages(ClientGoodsRebateRackImages cgcri);
    
    /**
	 * 
	 * SELECT ClientGoodsRebateRackImages BY PRIMARY KEY
	 */
    ClientGoodsRebateRackImages getByPrimaryKey(Integer id);

    /**
	 * 
	 * SELECT ALL ClientGoodsRebateRackImages
	 */
    List<ClientGoodsRebateRackImages> getAllClientGoodsRebateRackImages();
    
    /**
	 * 
	 * SELECT ClientGoodsRebateRackImages BY client_goods_Rebate_rack_id
	 */
    List<ClientGoodsRebateRackImages> getByClientGoodsRebateRackId(String rackId);



    
    
    

	
}	