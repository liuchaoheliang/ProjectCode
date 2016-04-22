/**
 * File Name:    GoodsPreSellRackActionSupport.java
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
 * Author:       侯国权(houguoquan@f-road.com.cn)
 *
 * History:      14-3-6 created by 侯国权
 */
package com.froad.action.support;

import com.froad.client.goodsPresellRack.*;
import org.apache.log4j.Logger;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.*;

/**
 * @author houguoquan_Aides on 14-3-6.
 * @version 1.0
 */
public class GoodsPreSellRackActionSupport {
    private static Logger logger = Logger.getLogger(GoodsGroupRackActionSupport.class);
    private GoodsPresellRackService goodsPresellRackService;

    /**
     * 根据分页条件获取架上商品列表
     *
     * @param goodsPresellRack 查询条件
     * @return 架上商品列表
     */
    public GoodsPresellRack findGoodsPreSellByPager(GoodsPresellRack goodsPresellRack) {
        try {
            goodsPresellRack = goodsPresellRackService.getByPager(goodsPresellRack);

        } catch (Exception e) {
            logger.error("GoodsPreSellRackActionSupport.findGoodsPreSellByPager查询预售商品列表出错", e);
        }
        return goodsPresellRack;
    }

    /**
     * 根据货架Id获取商品的货架信息
     *
     * @param goodsPresellRackId 商品货架id
     * @return 商品信息
     */
    public GoodsPresellRack getGoodsPresellRackById(Integer goodsPresellRackId) {
        GoodsPresellRack goodsPresellRack = null;
        try {
            goodsPresellRack = goodsPresellRackService.getById(goodsPresellRackId);
        } catch (Exception e) {
            logger.error("GoodsPreSellRackActionSupport.getGoodsPresellRackById查询预售商品信息出错", e);
        }
        return goodsPresellRack;
    }

    /**
     * 获取服务器的系统时间
     *
     * @return 服务系统的时间
     */
    public Date getServerDate() {
        Date result = new Date();
        try {
            XMLGregorianCalendar _temp = goodsPresellRackService.getServerNowTime();
            GregorianCalendar ca = _temp.toGregorianCalendar();
            result = ca.getTime();
        } catch (Exception e) {
            logger.error("GoodsPreSellRackActionSupport.getGoodsPresellRackById查询预售商品信息出错", e);
        }
        return result;
    }

    /**
     * 获取历史预售商品列表
     *
     * @param goodsPresellRack 查询条件
     * @return
     */
    public List<GoodsPresellRack> getHistory(GoodsPresellRack goodsPresellRack) {
        List<GoodsPresellRack> result = new ArrayList<GoodsPresellRack>();
        try {
            result = goodsPresellRackService.getHistory(goodsPresellRack);
        } catch (Exception e) {
            logger.error("GoodsPreSellRackActionSupport.getGoodsPresellRackById查询预售商品信息出错", e);
        }
        return result;
    }

    public GoodsPresellRackService getGoodsPresellRackService() {
        return goodsPresellRackService;
    }

    public void setGoodsPresellRackService(GoodsPresellRackService goodsPresellRackService) {
        this.goodsPresellRackService = goodsPresellRackService;
    }
}
