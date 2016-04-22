/**
 * 文件名称:DeliveryAction.java
 * 文件描述: 自提点信息
 * 产品标识: 分分通
 * 单元描述: client
 * 编写人: gq.hou_Mimosa
 * 编写时间: 14-3-17
 * 历史修改:  
 */
package com.froad.action.web;

import com.froad.action.support.*;
import com.froad.action.support.presell.PreSellActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.Store.Store;
import com.froad.client.goodsExchangeRack.GoodsExchangeRack;
import com.froad.client.goodsGroupRack.GoodsGroupRack;
import com.froad.client.goodsPresellRack.GoodsPresellRack;
import com.froad.client.memberCollect.MemberCollect;
import com.froad.client.merchant.Merchant;
import com.froad.client.merchantTrafficMAP.MerchantTrafficMAP;
import com.froad.client.merchantTrain.MerchantTrain;
import com.froad.client.presellDelivery.PresellDelivery;
import com.froad.client.presellDeliveryMap.*;
import com.froad.util.JsonUtil;
import com.froad.util.command.MallCommand;

import java.util.*;

/**
 * @author leo
 * @version 1.0
 */
public class DeliveryAction extends BaseActionSupport
{
    private GoodsPreSellRackActionSupport goodsPreSellRackActionSupport;
    private PreSellActionSupport preSellActionSupport;
    private GoodsGroupRackActionSupport goodsGroupRackActionSupport;
    private GoodsExchangeRackActionSupport goodsExchangeRackActionSupport;
    private MemberCollectActionSupport memberCollectActionSupport;
    private MerchantActionSupport merchantActionSupport;
    private MerchantTrainActionSupport merchantTrainActionSupport;
    private StoreActionSupport storeActionSupport;
    private PresellDeliveryMap pager = new PresellDeliveryMap();
    private GoodsPresellRack goodsPresellRack;
    private MemberCollect memberCollectPager = new MemberCollect();
    private MerchantTrain merchantTrain;
    private Merchant merchant;
    private List<GoodsGroupRack> goodsGroupRackList;
    private List<GoodsExchangeRack> goodsExchangeRackList;
    private List<GoodsPresellRack> presellRackList;
    private MerchantTrafficMAP merchantTrafficMAP;//商户地图
    private MerchantTrafficMAPActionSupport merchantTrafficMAPActionSupport;

    //分店查询
    public String deliveryList()
    {
        goodsPresellRack = goodsPreSellRackActionSupport.getGoodsPresellRackById(goodsPresellRack.getId());

        String merchantId = goodsPresellRack.getMerchantId();
        //点击人数查询
        merchantTrain = merchantTrainActionSupport.getMerchantTrainByMerchantId(goodsPresellRack.getMerchantId() + "", null);
        //商户相关信息查询
        merchant = merchantActionSupport.getMerchantById(merchantId + "");

        //当前用户的ID
        String userId = (String) getSession(MallCommand.USER_ID);
        if (userId != null)
        {
            //获得登录用户收藏的商家集合
            memberCollectPager.setUserid(userId);
            memberCollectPager.setMerchantId(merchantId + "");
            memberCollectPager = memberCollectActionSupport.getMemberCollectByPager(memberCollectPager);
        }

        GoodsGroupRack ggr = new GoodsGroupRack();
        ggr.setMerchantId(merchantId + "");
        ggr.setPageSize(2);
        ggr.setIsRack("1");
        ggr.setState("30");
        //查询该商户下的团购商品
        List<Object> list = goodsGroupRackActionSupport.findGoodsGroupByPager(ggr).getList();
        goodsGroupRackList = new ArrayList<GoodsGroupRack>();
        for (Object object : list)
        {
            goodsGroupRackList.add((GoodsGroupRack) object);
        }

        //查询该商户下的积分兑换商品
        GoodsExchangeRack ger = new GoodsExchangeRack();
        ger.setMerchantId(merchantId + "");
        ger.setState("30");
        ger.setIsRack("1");
        ger.setPageSize(2);
        List<Object> objList = goodsExchangeRackActionSupport.getGoodsExchangeRacks(ger).getList();
        goodsExchangeRackList = new ArrayList<GoodsExchangeRack>();
        for (Object object : objList)
        {
            goodsExchangeRackList.add((GoodsExchangeRack) object);
        }

        //查询该商户下的精品预售
        goodsPresellRack.setMerchantId(merchantId + "");
        goodsPresellRack.setState("30");
        goodsPresellRack.setIsRack("1");
        goodsPresellRack.setPageSize(2);
        List<Object> preList = goodsPreSellRackActionSupport.findGoodsPreSellByPager(goodsPresellRack).getList();
        presellRackList = new ArrayList<GoodsPresellRack>();
        for (Object object : preList)
        {
            presellRackList.add((GoodsPresellRack) object);
        }

        //分店信息查询 begin
        pager.setPageSize(6);
        pager.setPresellRackId( goodsPresellRack.getId());
        pager = preSellActionSupport.getDeliveryPagerByRackId(pager);

        List _list = pager.getList();
        List< com.froad.client.presellDeliveryMap.PresellDelivery> resultList = new ArrayList< com.froad.client.presellDeliveryMap.PresellDelivery>();
        for(Object o:_list)
        {
            PresellDeliveryMap temp =(PresellDeliveryMap)o;
            com.froad.client.presellDeliveryMap.PresellDelivery delivery = temp.getPresellDelivery();
            resultList.add(delivery);
        }
        pager.getList().clear();
        pager.getList().addAll(resultList);
        //分店信息查询 over
        return SUCCESS;
    }

    //商户分店地址
    public void ajaxDeliveryList()
    {
        //查询
        Merchant merchant = merchantActionSupport.getMerchantById(id);

        List<PresellDelivery> deliveryList = preSellActionSupport.getGoodsDeliverysByRackId( goodsPresellRack.getId());

        Map<String, Object> merchantStoreMap = new HashMap<String, Object>();
        merchantStoreMap.put("merchantId", id);
        merchantStoreMap.put("storeTotalNumber", deliveryList.size());

        List list = new ArrayList();
        List storeList = new ArrayList();

        if (deliveryList.size() > 0)
        {
            for (PresellDelivery delivery : deliveryList)
            {
                Map<String, Object> storeMap = new HashMap<String, Object>();
                storeMap.put("fullName", delivery.getName());
                storeMap.put("address", delivery.getAddress());
                storeMap.put("telephone", delivery.getTelephone());
                storeMap.put("coordinate", delivery.getCoordinate());

                storeList.add(storeMap);
            }
            merchantStoreMap.put("storeList", storeList);
        }
        else
        {
            merchantTrafficMAP = merchantTrafficMAPActionSupport.getMerchantTrafficMapByMerchantId(id);

            Map<String, Object> storeMap = new HashMap<String, Object>();
            storeMap.put("fullName", merchant.getMstoreFullName());
            storeMap.put("address", merchant.getMstoreAddress());
            storeMap.put("telephone", merchant.getMstoreTel());
            storeMap.put("coordinate", merchantTrafficMAP == null ? null : merchantTrafficMAP.getCoordinate());

            storeList.add(storeMap);
            merchantStoreMap.put("storeList", storeList);
        }
        String jsonStr = JsonUtil.getJsonString4Map(merchantStoreMap);
        //log.info("商户分店地址：" + jsonStr);

        ajaxJson(jsonStr);
    }

    public GoodsPreSellRackActionSupport getGoodsPreSellRackActionSupport()
    {
        return goodsPreSellRackActionSupport;
    }

    public void setGoodsPreSellRackActionSupport(GoodsPreSellRackActionSupport goodsPreSellRackActionSupport)
    {
        this.goodsPreSellRackActionSupport = goodsPreSellRackActionSupport;
    }

    public PreSellActionSupport getPreSellActionSupport()
    {
        return preSellActionSupport;
    }

    public void setPreSellActionSupport(PreSellActionSupport preSellActionSupport)
    {
        this.preSellActionSupport = preSellActionSupport;
    }

    public GoodsGroupRackActionSupport getGoodsGroupRackActionSupport()
    {
        return goodsGroupRackActionSupport;
    }

    public void setGoodsGroupRackActionSupport(GoodsGroupRackActionSupport goodsGroupRackActionSupport)
    {
        this.goodsGroupRackActionSupport = goodsGroupRackActionSupport;
    }

    public GoodsExchangeRackActionSupport getGoodsExchangeRackActionSupport()
    {
        return goodsExchangeRackActionSupport;
    }

    public void setGoodsExchangeRackActionSupport(GoodsExchangeRackActionSupport goodsExchangeRackActionSupport)
    {
        this.goodsExchangeRackActionSupport = goodsExchangeRackActionSupport;
    }

    public MemberCollectActionSupport getMemberCollectActionSupport()
    {
        return memberCollectActionSupport;
    }

    public void setMemberCollectActionSupport(MemberCollectActionSupport memberCollectActionSupport)
    {
        this.memberCollectActionSupport = memberCollectActionSupport;
    }

    public MerchantActionSupport getMerchantActionSupport()
    {
        return merchantActionSupport;
    }

    public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport)
    {
        this.merchantActionSupport = merchantActionSupport;
    }

    public MerchantTrainActionSupport getMerchantTrainActionSupport()
    {
        return merchantTrainActionSupport;
    }

    public void setMerchantTrainActionSupport(MerchantTrainActionSupport merchantTrainActionSupport)
    {
        this.merchantTrainActionSupport = merchantTrainActionSupport;
    }

    public StoreActionSupport getStoreActionSupport()
    {
        return storeActionSupport;
    }

    public void setStoreActionSupport(StoreActionSupport storeActionSupport)
    {
        this.storeActionSupport = storeActionSupport;
    }

    public PresellDeliveryMap getPager()
    {
        return pager;
    }

    public void setPager(PresellDeliveryMap pager)
    {
        this.pager = pager;
    }

    public GoodsPresellRack getGoodsPresellRack()
    {
        return goodsPresellRack;
    }

    public void setGoodsPresellRack(GoodsPresellRack goodsPresellRack)
    {
        this.goodsPresellRack = goodsPresellRack;
    }

    public MemberCollect getMemberCollectPager()
    {
        return memberCollectPager;
    }

    public void setMemberCollectPager(MemberCollect memberCollectPager)
    {
        this.memberCollectPager = memberCollectPager;
    }

    public MerchantTrain getMerchantTrain()
    {
        return merchantTrain;
    }

    public void setMerchantTrain(MerchantTrain merchantTrain)
    {
        this.merchantTrain = merchantTrain;
    }

    public Merchant getMerchant()
    {
        return merchant;
    }

    public void setMerchant(Merchant merchant)
    {
        this.merchant = merchant;
    }

    public List<GoodsGroupRack> getGoodsGroupRackList()
    {
        return goodsGroupRackList;
    }

    public void setGoodsGroupRackList(List<GoodsGroupRack> goodsGroupRackList)
    {
        this.goodsGroupRackList = goodsGroupRackList;
    }

    public List<GoodsExchangeRack> getGoodsExchangeRackList()
    {
        return goodsExchangeRackList;
    }

    public void setGoodsExchangeRackList(List<GoodsExchangeRack> goodsExchangeRackList)
    {
        this.goodsExchangeRackList = goodsExchangeRackList;
    }

    public List<GoodsPresellRack> getPresellRackList()
    {
        return presellRackList;
    }

    public void setPresellRackList(List<GoodsPresellRack> presellRackList)
    {
        this.presellRackList = presellRackList;
    }

    public MerchantTrafficMAP getMerchantTrafficMAP()
    {
        return merchantTrafficMAP;
    }

    public void setMerchantTrafficMAP(MerchantTrafficMAP merchantTrafficMAP)
    {
        this.merchantTrafficMAP = merchantTrafficMAP;
    }

    public MerchantTrafficMAPActionSupport getMerchantTrafficMAPActionSupport()
    {
        return merchantTrafficMAPActionSupport;
    }

    public void setMerchantTrafficMAPActionSupport(MerchantTrafficMAPActionSupport merchantTrafficMAPActionSupport)
    {
        this.merchantTrafficMAPActionSupport = merchantTrafficMAPActionSupport;
    }
}
