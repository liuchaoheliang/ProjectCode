/**
 * File Name:    PreSellActionSupport.java
 *
 * File Desc:    
 *
 * Product AB:   
 *
 * Product Name: 
 *
 * Module Name:  
 *
 * Module AB:    
 *
 * Author:       侯国权
 *
 * History:      14-3-7 created by 侯国权
 */
package com.froad.action.support.presell;

import com.froad.action.support.GoodsGroupRackActionSupport;
import com.froad.client.presellBuyInfo.*;
import com.froad.client.presellDelivery.PresellDeliveryService;
import com.froad.client.presellDelivery.PresellDelivery;
import com.froad.client.presellDeliveryMap.PresellDeliveryMapService;
import org.apache.log4j.Logger;
import com.froad.client.presellDeliveryMap.*;
import java.util.*;

/**
 * @author 侯国权
 * @version 1.0
 */
public class PreSellActionSupport
{
    private static Logger logger = Logger.getLogger(PreSellActionSupport.class);

    private PresellDeliveryService presellDeliveryService;
    private PresellBuyInfoService presellBuyInfoService;
    private PresellDeliveryMapService presellDeliveryMapService;

    /**
     * 根据交易条件查询预售交易信息
     *
     * @param presellBuyInfo 预售交易条件
     * @return 预售交易记录
     */
    public List<PresellBuyInfo> findPreSellBuyInfobyConditions(PresellBuyInfo presellBuyInfo)
    {
        List<PresellBuyInfo> result = null;
        try
        {
            result = presellBuyInfoService.getByConditions(presellBuyInfo);
        }
        catch (Exception e)
        {
            logger.error("PreSellActionSupport.findPreSellBuyInfobyConditions查询预售商品列表出错", e);
        }
        return result;
    }

    /**
     * 根据查询条件查询预售提货点信息
     *
     * @param presellDelivery 预售提货点查询条件
     * @return 预售提货点查询条件信息
     */
    public List<PresellDelivery> findPreSellDeliveryByConditions(PresellDelivery presellDelivery)
    {
        List<PresellDelivery> result = null;
        try
        {
            result = presellDeliveryService.getByConditions(presellDelivery);
        }
        catch (Exception e)
        {
            logger.error("PreSellActionSupport.findPreSellDeliveryByConditions查询预售商品列表出错", e);
        }
        return result;
    }

    public List<PresellDelivery> getGoodsDeliverysByRackId(Integer rackId)
    {
        List<PresellDelivery> result = new ArrayList<PresellDelivery>();
        try
        {
            result = presellDeliveryService.getByRackId(rackId);
        }
        catch (Exception e)
        {
            logger.error("PreSellActionSupport.getGoodsDeliverysByRackId查询预售商品提货点列表出错", e);
        }
        return result;
    }

    /**
     * @param pager 自提点分页信息

     * @return 分页列表
     */
    public PresellDeliveryMap getDeliveryPagerByRackId(PresellDeliveryMap pager)
    {
        try
        {
            pager = presellDeliveryMapService.getBypager(pager);
        }
        catch (Exception e)
        {
            logger.error("PreSellActionSupport.getGoodsDeliverysByRackId查询预售商品提货点列表出错", e);
        }
        return pager;
    }

    public PresellDelivery getPresellDeliveryById(Integer deliveryId)
    {
        PresellDelivery result = null;
        try
        {
            result = presellDeliveryService.getById(deliveryId);
        }
        catch (Exception e)
        {
            logger.error("PreSellActionSupport.getGoodsDeliverysByRackId查询预售商品提货点列表出错", e);
        }
        return result;

    }

    public Integer addPresellBuyInfo(PresellBuyInfo presellBuyInfo)
    {
        Integer result = null;
        try
        {
            result = presellBuyInfoService.add(presellBuyInfo);
        }
        catch (Exception e)
        {
            logger.error("PreSellActionSupportaddPresellBuyInfo添加预售商品出错", e);
        }
        return result;
    }

    public PresellDeliveryService getPresellDeliveryService()
    {
        return presellDeliveryService;
    }

    public void setPresellDeliveryService(PresellDeliveryService presellDeliveryService)
    {
        this.presellDeliveryService = presellDeliveryService;
    }

    public PresellBuyInfoService getPresellBuyInfoService()
    {
        return presellBuyInfoService;
    }

    public void setPresellBuyInfoService(PresellBuyInfoService presellBuyInfoService)
    {
        this.presellBuyInfoService = presellBuyInfoService;
    }

    public PresellDeliveryMapService getPresellDeliveryMapService()
    {
        return presellDeliveryMapService;
    }

    public void setPresellDeliveryMapService(PresellDeliveryMapService presellDeliveryMapService)
    {
        this.presellDeliveryMapService = presellDeliveryMapService;
    }
}
