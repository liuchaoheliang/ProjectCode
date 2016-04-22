package com.froad.action.support;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.client.PresentPointRule.*;
import org.apache.log4j.Logger;

import com.froad.AppException.PointsUseError;
import com.froad.AppException.PriceError;
import com.froad.action.web.tran.TransAddtionalInfoVo;
import com.froad.client.buyers.BuyerChannel;
import com.froad.client.buyers.Buyers;
import com.froad.client.buyers.BuyerChannel;
import com.froad.client.clientGoodsExchangeRack.ClientGoodsExchangeRack;
import com.froad.client.goodsExchangeRack.GoodsExchangeRack;
import com.froad.client.goodsExchangeRack.GoodsExchangeRackService;
import com.froad.client.goodsExchangeRack.GoodsRackAttribute;
import com.froad.client.merchant.Merchant;
import com.froad.client.sellers.Seller;
import com.froad.client.pointCashRule.PointCashRule;
import com.froad.client.pointCashRule.PointCashRuleService;
import com.froad.client.sellers.SellerChannel;
import com.froad.client.trans.Goods;
import com.froad.client.trans.GoodsCategory;
import com.froad.client.trans.Trans;
import com.froad.client.trans.TransDetails;
import com.froad.client.trans.TransGoodsAttribute;
import com.froad.client.transRule.TransRule;
import com.froad.client.user.User;
import com.froad.client.userCertification.UserCertification;
import com.froad.server.tran.TranService;
import com.froad.util.Assert;
import com.froad.util.CommonUtil;
import com.froad.util.command.ClientType;
import com.froad.util.command.Command;
import com.froad.util.command.GoodCategory;
import com.froad.util.command.PayChannel;
import com.froad.util.command.PointsType;
import com.froad.util.command.RuleType;
import com.froad.util.command.TranGoodsAttributes;
import com.froad.util.command.TransState;
import com.froad.util.command.TransType;
import com.froad.util.command.UseTime;
import com.froad.vo.trans.GoodsExchangeRackTrans;
import com.sun.jmx.snmp.Timestamp;

/**
 * @author Qiaopeng.Lee
 * @version 1.0
 *          商品兑换架 client service  ActionSupport
 * @date 2013-2-19
 */
public class GoodsExchangeRackActionSupport
{
    private static Logger logger = Logger.getLogger(GoodsExchangeRackActionSupport.class);
    private GoodsExchangeRackService goodsExchangeRackService;
    private PresentPointRuleService presentPointRuleService;
    private TranService transServiceInClient;
    private SellersActionSupport sellerActionSupport;
    private TransActionSupport transActionSupport;
    private PointCashRuleActionSupport pointCashRuleActionSupport;
    private GoodsActionSupport goodsActionSupport;
    private MerchantActionSupport merchantActionSupport;

    public TransRule getExchCurrencyRule()
    {
        TransRule pointsExchCurrencyRule = null;
        Map<String, Map> cache = new HashMap();
        cache = transActionSupport.getRulesFromCache();
        Map<String, List<TransRule>> rules_ruleType = new HashMap();
        if (!Assert.empty(cache)) rules_ruleType = cache.get("rules-ruleType");
        List<TransRule> pointsExchCurrencyRuleList = null;
        if (!Assert.empty(rules_ruleType)) pointsExchCurrencyRuleList = rules_ruleType.get(RuleType.WITHDRAW.getValue());
        if (!Assert.empty(pointsExchCurrencyRuleList)) pointsExchCurrencyRule = pointsExchCurrencyRuleList.get(0);
        return pointsExchCurrencyRule;
    }

    public GoodsExchangeRack selectById(String id)
    {
        return goodsExchangeRackService.selectById(Integer.parseInt(id));
    }

    public GoodsExchangeRack getGoodsExchangeRacks(GoodsExchangeRack queryCon)
    {
        GoodsExchangeRack result = null;
        try
        {
            result = (GoodsExchangeRack) goodsExchangeRackService.getGoodsExchangeRackListByPager(queryCon);
        }
        catch (Exception e)
        {
            // TODO: handle exception
            logger.error("查询积分兑换的交易品失败!", e);
        }
        return result;
    }

    /**
     * 积分兑换首页列表
     * @return 首页积分兑换列表
     */
    public List<GoodsExchangeRack>  getIndexGoodsRack()
     {
         List<GoodsExchangeRack>  result =  new ArrayList<GoodsExchangeRack>();
         try
         {
             result = goodsExchangeRackService.getIndexGoodsRack();
         }
         catch (Exception e)
         {
             // TODO: handle exception
             logger.error("查询积分兑换的交易品失败!", e);
         }
         return result;
     }

    public GoodsExchangeRack getGoodsExchangeRack(String goodsId)
    {
        GoodsExchangeRack result = null;
        List<GoodsExchangeRack> GoodsExchangeRackList = new ArrayList();
        try
        {
            GoodsExchangeRackList = goodsExchangeRackService.getGoodsExchangeRackByGoodsId(goodsId);
        }
        catch (Exception e)
        {
            // TODO: handle exception
            logger.error("查询积分兑换的交易品信息失败!", e);
        }
        result = GoodsExchangeRackList.get(0);
        return result;
    }

    public GoodsExchangeRack getGoodsExchangeRacksById(String id)
    {
        GoodsExchangeRack result = null;
        if (id == null) return result;
        try
        {
            result = (GoodsExchangeRack) goodsExchangeRackService.selectById(id == null ? new Integer(0) : Integer.valueOf(id));
        }
        catch (Exception e)
        {
            logger.error("查询积分兑换的交易品失败!", e);
        }
        return result;
    }

    //创建积分兑换交易  客户端
//    public Trans createPointsExchProductTrans(ClientGoodsExchangeRack goodsRack, Trans trans, TransDetails transDetails, Buyers buyer, User user, UserCertification userCertification,
//                                              TransAddtionalInfoVo transAddtionalInfoVo, String clientType)
//    {
//        GoodsExchangeRackTrans goodsExchangeRackTrans = new GoodsExchangeRackTrans();
//
//        if (goodsRack == null)
//        {
//            logger.error("客户版的ClientGoodsExchangeRack为空");
//            return null;
//        }
//
//        com.froad.client.clientGoodsExchangeRack.Goods goods = goodsRack.getGoods();
//        if (goods == null)
//        {
//            logger.error("客户版的ClientGoodsExchangeRack的good为空！");
//            return null;
//        }
//
//        if (goodsRack == null || Assert.empty(goodsRack.getGoods().getMerchantId()))
//        {
//            logger.error("客户版的ClientGoodsExchangeRack的merchantID为空！");
//            return null;
//        }
//
//
//        Goods goodsInTrans = new Goods();
//        GoodsCategory goodsCategory = new GoodsCategory();
//        goodsCategory.setId(goods.getGoodsCategory().getId());
//        goodsCategory.setName(goods.getGoodsCategory().getName());
//        goodsInTrans.setGoodsCategory(goodsCategory);
//        goodsInTrans.setGoodsCategoryId(goods.getGoodsCategoryId());
//        goodsInTrans.setId(goods.getId());
//        goodsExchangeRackTrans.setGoods(goodsInTrans);
//        goodsExchangeRackTrans.setMerchantId(goodsRack.getGoods().getMerchantId());
//        goodsExchangeRackTrans.setId(goodsRack.getId());
//        goodsExchangeRackTrans.setIsCash(goodsRack.getIsCash());
//        goodsExchangeRackTrans.setIsBankPoint(goodsRack.getIsBankPoint());
//        goodsExchangeRackTrans.setIsBankpointCash(goodsRack.getIsBankpointCash());
//        goodsExchangeRackTrans.setIsFftPoint(goodsRack.getIsFftPoint());
//        goodsExchangeRackTrans.setIsFftpointCash(goodsRack.getIsFftpointCash());
//        goodsExchangeRackTrans.setIsFftpointcashRatioPricing(goodsRack.getIsFftpointcashRatioPricing());
//        goodsExchangeRackTrans.setIsBankpointcashRatioPricing(goodsRack.getIsBankpointcashRatioPricing());
//
//        goodsExchangeRackTrans.setCashPricing(goodsRack.getCashPricing());
//        goodsExchangeRackTrans.setBankPointPricing(goodsRack.getBankPointPricing());
//        goodsExchangeRackTrans.setBankpointCashPricing(goodsRack.getBankpointCashPricing());
//        goodsExchangeRackTrans.setFftPointPricing(goodsRack.getFftPointPricing());
//        goodsExchangeRackTrans.setFftpointCashPricing(goodsRack.getFftpointCashPricing());
//        goodsExchangeRackTrans.setFftpointcashRatioPricing(goodsRack.getFftpointcashRatioPricing());
//        goodsExchangeRackTrans.setBankpointcashRatioPricing(goodsRack.getBankpointcashRatioPricing());
//
//        goodsExchangeRackTrans.setSellerId(String.valueOf(goodsRack.getSellerId()));
//        return createPointsExchProductTrans(goodsExchangeRackTrans, trans, transDetails, buyer, user, userCertification, transAddtionalInfoVo, clientType);
//    }

    //创建积分兑换交易  PC端
//    public Trans createPointsExchProductTrans(GoodsExchangeRack goodsRack, Trans trans, TransDetails transDetails, Buyers buyer, User user, UserCertification userCertification,
//                                              TransAddtionalInfoVo transAddtionalInfoVo, String clientType)
//    {
//        GoodsExchangeRackTrans goodsExchangeRackTrans = new GoodsExchangeRackTrans();
//
//        if (goodsRack == null)
//        {
//            logger.error("PC版的GoodsExchangeRack为空");
//            return null;
//        }
//
//        com.froad.client.goodsExchangeRack.Goods goods = goodsRack.getGoods();
//        if (goods == null)
//        {
//            logger.error("PC版的GoodsExchangeRack的good为空！");
//            return null;
//        }
//        if (goodsRack == null || Assert.empty(goodsRack.getGoods().getMerchantId()))
//        {
//            logger.error("PC版的GoodsExchangeRack的merchantID为空！");
//            return null;
//        }
//
//        Goods goodsInTrans = new Goods();
//        GoodsCategory goodsCategory = new GoodsCategory();
//        goodsCategory.setId(goods.getGoodsCategory().getId());
//        goodsCategory.setName(goods.getGoodsCategory().getName());
//        goodsInTrans.setGoodsCategory(goodsCategory);
//        goodsInTrans.setGoodsCategoryId(goods.getGoodsCategoryId());
//        goodsInTrans.setId(goods.getId());
//        goodsExchangeRackTrans.setGoods(goodsInTrans);
//        goodsExchangeRackTrans.setMerchantId(goodsRack.getGoods().getMerchantId());
//        goodsExchangeRackTrans.setId(goodsRack.getId());
//        goodsExchangeRackTrans.setIsCash(goodsRack.getIsCash());
//        goodsExchangeRackTrans.setIsBankPoint(goodsRack.getIsBankPoint());
//        goodsExchangeRackTrans.setIsBankpointCash(goodsRack.getIsBankpointCash());
//        goodsExchangeRackTrans.setIsFftPoint(goodsRack.getIsFftPoint());
//        goodsExchangeRackTrans.setIsFftpointCash(goodsRack.getIsFftpointCash());
//        goodsExchangeRackTrans.setIsFftpointcashRatioPricing(goodsRack.getIsFftpointcashRatioPricing());
//        goodsExchangeRackTrans.setIsBankpointcashRatioPricing(goodsRack.getIsBankpointcashRatioPricing());
//
//        goodsExchangeRackTrans.setCashPricing(goodsRack.getCashPricing());
//        goodsExchangeRackTrans.setBankPointPricing(goodsRack.getBankPointPricing());
//        goodsExchangeRackTrans.setBankpointCashPricing(goodsRack.getBankpointCashPricing());
//        goodsExchangeRackTrans.setFftPointPricing(goodsRack.getFftPointPricing());
//        goodsExchangeRackTrans.setFftpointCashPricing(goodsRack.getFftpointCashPricing());
//        goodsExchangeRackTrans.setFftpointcashRatioPricing(goodsRack.getFftpointcashRatioPricing());
//        goodsExchangeRackTrans.setBankpointcashRatioPricing(goodsRack.getBankpointcashRatioPricing());
//        goodsExchangeRackTrans.setSellerId(String.valueOf(goodsRack.getSellerId()));
//        //		goodsExchangeRackTrans.setGroupPrice(goodsRack.getGroupPrice());
//
//        return createPointsExchProductTrans(goodsExchangeRackTrans, trans, transDetails, buyer, user, userCertification, transAddtionalInfoVo, clientType);
//    }

    //创建积分兑换交易   --所有积分兑换交易  不管是兑换的是实体商品还是虚拟商品还是积分提现
//    public Trans createPointsExchProductTrans(GoodsExchangeRackTrans goodsRack, Trans trans, TransDetails transDetails, Buyers buyer, User user, UserCertification userCertification,
//                                              TransAddtionalInfoVo transAddtionalInfoVo, String clientType)
//    {
//        Trans result = trans;
//        try
//        {
//            if (trans == null) trans = new Trans();
//            trans.setClientType(clientType);
//
//            if (!Assert.empty(trans.getPayMethod()))
//            {
//                PayMethod payMethod = PayMethod.getPayMethod(trans.getPayMethod());
//                price(goodsRack, transDetails, payMethod);
//            }
//            else
//            {
//                logger.error("支付方式为空!");
//                //				String currencyPriceStr = goodsRack.getCashPricing();
//                //				if (!Assert.empty(currencyPriceStr))
//                //					countPayInfoAndPayMethod(Double.valueOf(currencyPriceStr),
//                //							transDetails, trans);
//            }
//            com.froad.client.merchant.Merchant merchant = merchantActionSupport.getMerchantById(goodsRack.getMerchantId());
//            String sellerId = goodsRack.getSellerId();
//            com.froad.client.sellers.Seller seller1 = sellerActionSupport.getSellerById(sellerId);
//            List<SellerChannel> sellerChannelList = seller1.getSellerChannelList();
//            com.froad.client.sellers.SellerChannel sellerDepositChannel1 = sellerChannelList.get(0);
//
//            trans.getTransDetailsList().add(transDetails);
//            updateTransBaseInfo(trans, transDetails, goodsRack, seller1, buyer);
//            updateTransAdditionalInfo(user, trans, transDetails, goodsRack, transAddtionalInfoVo);
//            List<BuyerChannel> buyersDepositChannelList = buyer.getBuyerChannelList();
//            BuyerChannel buyersDepositChannel = buyersDepositChannelList.get(0);
//
//
//            try
//            {
//                transServiceInClient.countTansferInfoTran(trans, seller1, sellerDepositChannel1, buyer, buyersDepositChannel, user, userCertification, UseTime.COUNT_TRANS_CURRENCY);
//            }
//            catch (Exception e)
//            {
//                // TODO Auto-generated catch block
//                logger.error("计算积分流转信息失败", e);
//                return null;
//            }
//            result = transActionSupport.addTrans(trans);
//        }
//        catch (Exception e)
//        {
//            // TODO: handle exception
//            logger.error("积分兑换下单时，报异常：", e);
//            result = null;
//        }
//        return result;
//    }

    //更新交易信息
    private void updateTransBaseInfo(Trans trans, TransDetails transDetails, GoodsExchangeRackTrans goodsRack, Seller seller, Buyers buyer)
    {
        trans.setTrackNo(transActionSupport.generateTrackNo());
        trans.setTransSn(transActionSupport.generateTrackNo());
        //		trans.setClientType(ClientType.ClientType_PC);
        trans.setPayChannel(PayChannel.PAYTYPE_MPhoneCard);
        trans.setMerchantId(Integer.valueOf(goodsRack.getMerchantId()));
        trans.setSellerType(seller.getSellerType());
        //因包不同
        Goods goods = goodsRack.getGoods();
        Goods goodsInTrans = new Goods();
        GoodsCategory goodsCategory = new GoodsCategory();
        goodsCategory.setId(goods.getGoodsCategory().getId());
        goodsCategory.setName(goods.getGoodsCategory().getName());
        goodsInTrans.setGoodsCategory(goodsCategory);
        goodsInTrans.setGoodsCategoryId(goods.getGoodsCategoryId());
        goodsInTrans.setId(goods.getId());
        transDetails.setGoods(goodsInTrans);

        List<com.froad.client.sellers.SellerChannel> sellerChannelList = seller.getSellerChannelList();
        List<BuyerChannel> buyersDepositChannelList = buyer.getBuyerChannelList();
        BuyerChannel buyersDepositChannel = buyersDepositChannelList.get(0);
        trans.setSellerId(String.valueOf(seller.getId()));
        if (!Assert.empty(sellerChannelList)) trans.setSellerChannelId(String.valueOf(sellerChannelList.get(0).getId()));
        trans.setBuyersId(String.valueOf(buyer.getId()));
        if (buyersDepositChannel != null) trans.setBuyerChannelId(String.valueOf(buyersDepositChannel.getId()));


        if (GoodCategory.Points_Exch_Currency.getValue().equals(goodsRack.getGoods().getGoodsCategory().getName())) trans.setTransType(TransType.Trans_Points_Exch_Currency.getValue());
        else trans.setTransType(TransType.Trans_Points_Exch_Product.getValue());

        if (!GoodCategory.Points_Exch_Currency.getValue().equals(goodsRack.getGoods().getGoodsCategory().getName())) trans.setCostpriceTotal(goodsRack.getCashPricing());
        else trans.setCostpriceTotal("0");
        trans.setCurrencyValueAll(transDetails.getCurrencyValue());
        trans.setCurrencyValueRealAll(transDetails.getCurrencyValueReal());
        trans.setFftPointsValueAll(transDetails.getFftPointsValueAll());
        trans.setFftPointsValueRealAll(transDetails.getFftPointsValueRealAll());
        trans.setBankPointsValueAll(transDetails.getBankPointsValueAll());
        trans.setBankPointsValueRealAll(transDetails.getBankPointsValueRealAll());

        trans.setPayChannel(PayChannel.PAYTYPE_MPhoneCard);
        trans.setState(TransState.doing);
    }

    //更新交易信息
    private void updateTransAdditionalInfo(User user, Trans trans, TransDetails transDetails, GoodsExchangeRackTrans goodsRack, TransAddtionalInfoVo transAddtionalInfoVo)
    {
        GoodCategory goodsCategory = GoodCategory.getGoodCategory(goodsRack.getGoods().getGoodsCategory().getName());
        com.froad.client.goods.Goods goodsInGoodsPackage = goodsActionSupport.getGoodById(String.valueOf(goodsRack.getGoods().getId()));
        List<com.froad.client.goods.GoodsRackAttribute> transGoodsAdditionalInfos = goodsInGoodsPackage.getGoodsRackAttrList();
        Map<String, com.froad.client.goods.GoodsRackAttribute> transGoodsAdditionalInfosMap = new HashMap();

        if (Assert.empty(transGoodsAdditionalInfos))
        {
            logger.info("商品ID，goodsId:" + goodsInGoodsPackage.getId() + ",没有交易附加信息");
            return;
        }
        for (com.froad.client.goods.GoodsRackAttribute transGoodsAdditionalInfo : transGoodsAdditionalInfos)
        {
            transGoodsAdditionalInfosMap.put(transGoodsAdditionalInfo.getAliasCode(), transGoodsAdditionalInfo);
        }

        switch (goodsCategory)
        {
            case Goods_Category_Lottery:
                if (transGoodsAdditionalInfosMap.get(TranGoodsAttributes.Tran_Goods_Attributes_Content) != null)
                {
                    TransGoodsAttribute transGoodsAttribute1 = new TransGoodsAttribute();
                    transGoodsAttribute1.setGoodsRackAttributeId(String.valueOf(transGoodsAdditionalInfosMap.get(TranGoodsAttributes.Tran_Goods_Attributes_Content).getId()));
                    transGoodsAttribute1.setTransId(String.valueOf(trans.getId()));
                    transGoodsAttribute1.setElement(transAddtionalInfoVo.getLotteryContent());
                    trans.getTransGoodsAttrList().add(transGoodsAttribute1);
                }
                if (transGoodsAdditionalInfosMap.get(TranGoodsAttributes.Tran_Goods_Attributes_PlayType) != null)
                {
                    TransGoodsAttribute transGoodsAttribute2 = new TransGoodsAttribute();
                    transGoodsAttribute2.setGoodsRackAttributeId(String.valueOf(transGoodsAdditionalInfosMap.get(TranGoodsAttributes.Tran_Goods_Attributes_PlayType).getId()));
                    transGoodsAttribute2.setTransId(String.valueOf(trans.getId()));
                    //transGoodsAttribute2.setElement(transAddtionalInfoVo.getLottery().getPlayType());
                    transGoodsAttribute2.setElement("1");
                    trans.getTransGoodsAttrList().add(transGoodsAttribute2);
                }

                if (transGoodsAdditionalInfosMap.get(TranGoodsAttributes.Tran_Goods_Attributes_Period) != null)
                {
                    TransGoodsAttribute transGoodsAttribute3 = new TransGoodsAttribute();
                    transGoodsAttribute3.setGoodsRackAttributeId(String.valueOf(transGoodsAdditionalInfosMap.get(TranGoodsAttributes.Tran_Goods_Attributes_Period).getId()));
                    transGoodsAttribute3.setTransId(String.valueOf(trans.getId()));
                    transGoodsAttribute3.setElement(transAddtionalInfoVo.getLottery().getPeriod());//invoke method supplied by shunqingya
                    trans.getTransGoodsAttrList().add(transGoodsAttribute3);
                }
                if (transGoodsAdditionalInfosMap.get(TranGoodsAttributes.Tran_Goods_Attributes_NumCount) != null)
                {
                    TransGoodsAttribute transGoodsAttribute4 = new TransGoodsAttribute();
                    transGoodsAttribute4.setGoodsRackAttributeId(String.valueOf(transGoodsAdditionalInfosMap.get(TranGoodsAttributes.Tran_Goods_Attributes_NumCount).getId()));
                    transGoodsAttribute4.setTransId(String.valueOf(trans.getId()));
                    //transGoodsAttribute4.setElement(transAddtionalInfoVo.getLottery().getNumCount());//invoke method supplied by shunqingya
                    transGoodsAttribute4.setElement("1");
                    trans.getTransGoodsAttrList().add(transGoodsAttribute4);
                }
                //			if(transGoodsAdditionalInfosMap.get(TranGoodsAttributes.Tran_Goods_Attributes_zj)!=null){
                //			TransGoodsAttribute transGoodsAttribute5=new TransGoodsAttribute();
                //			transGoodsAttribute5.setGoodsRackAttributeId(String.valueOf(transGoodsAdditionalInfosMap.get(TranGoodsAttributes.Tran_Goods_Attributes_zj).getId()));
                //			transGoodsAttribute5.setTransId(String.valueOf(trans.getId()));
                //			transGoodsAttribute5.setElement(transAddtionalInfoVo.getLottery().getWinAmount());//invoke method supplied by shunqingya
                //			trans.getTransGoodsAttrList().add(transGoodsAttribute5);
                //			}
                if (transGoodsAdditionalInfosMap.get(TranGoodsAttributes.Tran_Goods_Attributes_cpbm) != null)
                {
                    TransGoodsAttribute transGoodsAttribute5 = new TransGoodsAttribute();
                    transGoodsAttribute5.setGoodsRackAttributeId(String.valueOf(transGoodsAdditionalInfosMap.get(TranGoodsAttributes.Tran_Goods_Attributes_cpbm).getId()));
                    transGoodsAttribute5.setTransId(String.valueOf(trans.getId()));
                    transGoodsAttribute5.setElement(transAddtionalInfoVo.getLottery().getLotteryNo());//invoke method supplied by shunqingya
                    //				transGoodsAttribute4.setElement("FC_SSQ");
                    trans.getTransGoodsAttrList().add(transGoodsAttribute5);
                }
                if (transGoodsAdditionalInfosMap.get(TranGoodsAttributes.Tran_Goods_Attributes_dfhhd) != null)
                {
                    TransGoodsAttribute transGoodsAttribute6 = new TransGoodsAttribute();
                    transGoodsAttribute6.setGoodsRackAttributeId(String.valueOf(transGoodsAdditionalInfosMap.get(TranGoodsAttributes.Tran_Goods_Attributes_dfhhd).getId()));
                    transGoodsAttribute6.setTransId(String.valueOf(trans.getId()));
                    //transGoodsAttribute4.setElement(transAddtionalInfoVo.getLottery().getNumCount());//invoke method supplied by shunqingya
                    transGoodsAttribute6.setElement("1");
                    trans.getTransGoodsAttrList().add(transGoodsAttribute6);
                }
                if (transGoodsAdditionalInfosMap.get(TranGoodsAttributes.Tran_Goods_Attributes_tzbx) != null)
                {
                    TransGoodsAttribute transGoodsAttribute7 = new TransGoodsAttribute();
                    transGoodsAttribute7.setGoodsRackAttributeId(String.valueOf(transGoodsAdditionalInfosMap.get(TranGoodsAttributes.Tran_Goods_Attributes_tzbx).getId()));
                    transGoodsAttribute7.setTransId(String.valueOf(trans.getId()));
                    //transGoodsAttribute4.setElement(transAddtionalInfoVo.getLottery().getNumCount());//invoke method supplied by shunqingya
                    transGoodsAttribute7.setElement(transDetails.getGoodsNumber());
                    trans.getTransGoodsAttrList().add(transGoodsAttribute7);
                }
                if (transGoodsAdditionalInfosMap.get(TranGoodsAttributes.Tran_Goods_Attributes_tzje) != null)
                {
                    TransGoodsAttribute transGoodsAttribute8 = new TransGoodsAttribute();
                    transGoodsAttribute8.setGoodsRackAttributeId(String.valueOf(transGoodsAdditionalInfosMap.get(TranGoodsAttributes.Tran_Goods_Attributes_tzje).getId()));
                    transGoodsAttribute8.setTransId(String.valueOf(trans.getId()));
                    //transGoodsAttribute4.setElement(transAddtionalInfoVo.getLottery().getNumCount());//invoke method supplied by shunqingya
                    transGoodsAttribute8.setElement(goodsRack.getCashPricing());
                    trans.getTransGoodsAttrList().add(transGoodsAttribute8);
                }
                if (transGoodsAdditionalInfosMap.get(TranGoodsAttributes.Tran_Goods_Attributes_lxdh) != null)
                {
                    TransGoodsAttribute transGoodsAttribute9 = new TransGoodsAttribute();
                    transGoodsAttribute9.setGoodsRackAttributeId(String.valueOf(transGoodsAdditionalInfosMap.get(TranGoodsAttributes.Tran_Goods_Attributes_lxdh).getId()));
                    transGoodsAttribute9.setTransId(String.valueOf(trans.getId()));
                    transGoodsAttribute9.setElement(user.getMobilephone());//invoke method supplied by shunqingya
                    //				transGoodsAttribute4.setElement("1");
                    trans.getTransGoodsAttrList().add(transGoodsAttribute9);
                }
                break;
            case Goods_Category_Recharge_Phone:
                //买家可以给别人的手机号充值，即买家的手机号和充值的手机号可以不同
                if (transGoodsAdditionalInfosMap.get(TranGoodsAttributes.Tran_Goods_Attributes_Phone) != null)
                {
                    TransGoodsAttribute transGoodsAttributeOfPhone = new TransGoodsAttribute();
                    transGoodsAttributeOfPhone.setGoodsRackAttributeId(String.valueOf(transGoodsAdditionalInfosMap.get(TranGoodsAttributes.Tran_Goods_Attributes_Phone).getId()));
                    transGoodsAttributeOfPhone.setTransId(String.valueOf(trans.getId()));
                    transGoodsAttributeOfPhone.setElement(transAddtionalInfoVo.getPhone());//invoke method supplied by shunqingya
                    trans.getTransGoodsAttrList().add(transGoodsAttributeOfPhone);
                }
                if (transGoodsAdditionalInfosMap.get(TranGoodsAttributes.Tran_Goods_Attributes_Phone_local) != null)
                {
                    TransGoodsAttribute transGoodsAttributeOfPhone1 = new TransGoodsAttribute();
                    transGoodsAttributeOfPhone1.setGoodsRackAttributeId(String.valueOf(transGoodsAdditionalInfosMap.get(TranGoodsAttributes.Tran_Goods_Attributes_Phone_local).getId()));
                    transGoodsAttributeOfPhone1.setTransId(String.valueOf(trans.getId()));
                    transGoodsAttributeOfPhone1.setElement(transAddtionalInfoVo.getHfcz().getArea());//invoke method supplied by shunqingya
                    trans.getTransGoodsAttrList().add(transGoodsAttributeOfPhone1);
                }
                if (transGoodsAdditionalInfosMap.get(TranGoodsAttributes.Tran_Goods_Attributes_Phone_mz) != null)
                {
                    TransGoodsAttribute transGoodsAttributeOfPhone2 = new TransGoodsAttribute();
                    transGoodsAttributeOfPhone2.setGoodsRackAttributeId(String.valueOf(transGoodsAdditionalInfosMap.get(TranGoodsAttributes.Tran_Goods_Attributes_Phone_mz).getId()));
                    transGoodsAttributeOfPhone2.setTransId(String.valueOf(trans.getId()));
                    transGoodsAttributeOfPhone2.setElement(String.valueOf(transAddtionalInfoVo.getHfcz().getMoney().doubleValue()));//invoke method supplied by shunqingya
                    trans.getTransGoodsAttrList().add(transGoodsAttributeOfPhone2);
                }
                if (transGoodsAdditionalInfosMap.get(TranGoodsAttributes.Tran_Goods_Attributes_Phone_yys) != null)
                {
                    TransGoodsAttribute transGoodsAttributeOfPhone3 = new TransGoodsAttribute();
                    transGoodsAttributeOfPhone3.setGoodsRackAttributeId(String.valueOf(transGoodsAdditionalInfosMap.get(TranGoodsAttributes.Tran_Goods_Attributes_Phone_yys).getId()));
                    transGoodsAttributeOfPhone3.setTransId(String.valueOf(trans.getId()));
                    transGoodsAttributeOfPhone3.setElement(transAddtionalInfoVo.getHfcz().getOperater());//invoke method supplied by shunqingya
                    trans.getTransGoodsAttrList().add(transGoodsAttributeOfPhone3);
                }
                break;
        }
    }

//    private void countPayInfoAndPayMethod(double currencyPrice, TransDetails transDetails, Trans tran)
//    {
//        PointCashRule pointCashRule = transActionSupport.getPointCashRulesByPointType(PointsType.FFT_POINTS);
//        int points = Integer.valueOf(pointCashRule.getPointValue());
//        int currency = Integer.valueOf(pointCashRule.getCashValue());
//        double restCurrency = 0;
//        String payMethod = PayMethod.Currency.getValue();
//        if (!Assert.empty(transDetails.getBankPointsValueRealAll()))
//        {
//            int bankPoints = Integer.valueOf(transDetails.getBankPointsValueRealAll());
//            restCurrency = currencyPrice - (currency * bankPoints) / points;
//            if (restCurrency > 0) payMethod = PayMethod.Bank_Points_Currency.getValue();
//        }
//        if (!Assert.empty(transDetails.getFftPointsValueRealAll()))
//        {
//            int fftPoints = Integer.valueOf(transDetails.getFftPointsValueRealAll());
//            restCurrency = currencyPrice - (currency * fftPoints) / points;
//            if (restCurrency > 0) payMethod = PayMethod.FFT_Points_Currency.getValue();
//        }
//        restCurrency = CommonUtil.scale2(restCurrency, 2);
//        transDetails.setCurrencyValue(String.valueOf(restCurrency));
//        transDetails.setCurrencyValueReal(String.valueOf(restCurrency));
//        tran.setPayMethod(payMethod);
//    }
//
//    private void price(GoodsExchangeRackTrans goods, TransDetails transDetails, PayMethod payMethod) throws PriceError, PointsUseError
//    {
//        if (Assert.empty(transDetails.getFftPointsValueAll())) transDetails.setFftPointsValueAll("0");
//        if (Assert.empty(transDetails.getFftPointsValueRealAll())) transDetails.setFftPointsValueRealAll("0");
//        if (Assert.empty(transDetails.getCurrencyValue())) transDetails.setCurrencyValue("0");
//        if (Assert.empty(transDetails.getCurrencyValueReal())) transDetails.setCurrencyValueReal("0");
//        if (Assert.empty(transDetails.getBankPointsValueAll())) transDetails.setBankPointsValueAll("0");
//        if (Assert.empty(transDetails.getBankPointsValueRealAll())) transDetails.setBankPointsValueRealAll("0");
//        String fftPointsStr = goods.getFftPointPricing();
//        double fftPoints = 0;
//        String currencyStr = goods.getCashPricing();
//        double currency = 0.0;
//        String bankPointsStr = goods.getBankPointPricing();
//        double bankPoints = 0;
//        String goodsNumStr = transDetails.getGoodsNumber();
//        int goodsNum = Integer.valueOf(goodsNumStr);
//        ;
//        if (GoodCategory.Points_Exch_Currency.getValue().equals(goods.getGoods().getGoodsCategory().getName()))
//        {
//            currency = Double.valueOf(currencyStr);
//            transDetails.setFftPointsValueAll(goodsNumStr);
//            //			transDetails.setFftPointsValueRealAll(fftPointsStr);
//            double totalCurrency = currency * goodsNum;
//            totalCurrency = CommonUtil.scale2(totalCurrency, 2);
//            transDetails.setCurrencyValue(String.valueOf(totalCurrency));
//            //			transDetails.setCurrencyValueReal(String.valueOf(totalCurrency));
//            return;
//        }
//
//
//        switch (payMethod)
//        {
//            case FFT_Points:
//                transDetails.setFftPointsValueRealAll("0");
//                transDetails.setFftPointsValueAll("0");
//                transDetails.setCurrencyValue("0");
//                transDetails.setCurrencyValueReal("0");
//                transDetails.setBankPointsValueRealAll("0");
//                transDetails.setBankPointsValueAll("0");
//                if (!Command.price_availiable.equals(goods.getIsFftPoint()))
//                {
//                    throw new PriceError("交易品定价有误,请检查交易品id:" + goods.getId() + "的定价");
//                }
//                //注意话费按定价来收款,而不是按照话费的实际金额来收款
//                //			if(GoodCategory.Goods_Category_Recharge_Phone.getValue().equals(goods.getGoods().getGoodsCategory().getName())){
//                //				String cash=goods.getRemark();
//                //				fftPointsStr=pointCashRuleActionSupport.getPointByCash(cash, PointsType.FFT_POINTS);
//                //				Double fftPointsD=Double.valueOf(fftPointsStr);
//                //				fftPointsD=fftPointsD*goodsNum;
//                //				fftPoints=fftPointsD.intValue();
//                //				if(fftPoints!=fftPointsD){
//                //					int pointIndex=String.valueOf(fftPointsD).indexOf(".");
//                //					if(pointIndex!=-1){
//                //						String currencyValue="0."+String.valueOf(fftPointsD).substring(pointIndex+1);
//                //						transDetails.setCurrencyValue(currencyValue);
//                //						transDetails.setCurrencyValueReal(currencyValue);
//                //					}
//                //				}
//                //			}else{
//                //				fftPoints=Integer.valueOf(fftPointsStr);
//                //				fftPoints=fftPoints*goodsNum;
//                //			}
//                fftPoints = Double.valueOf(fftPointsStr);
//                fftPoints = fftPoints * goodsNum;
//                transDetails.setFftPointsValueAll(String.valueOf(fftPoints));
//                transDetails.setFftPointsValueRealAll(String.valueOf(fftPoints));
//                break;
//            case Bank_Points:
//                transDetails.setFftPointsValueRealAll("0");
//                transDetails.setFftPointsValueAll("0");
//                transDetails.setCurrencyValue("0");
//                transDetails.setCurrencyValueReal("0");
//                transDetails.setBankPointsValueRealAll("0");
//                transDetails.setBankPointsValueAll("0");
//                if (!Command.price_availiable.equals(goods.getIsBankPoint()))
//                {
//                    throw new PriceError("交易品定价有误,请检查交易品id:" + goods.getId() + "的定价");
//                }
//                //			//注意话费按定价来收款,而不是按照话费的实际金额来收款
//                //			if(GoodCategory.Goods_Category_Recharge_Phone.getValue().equals(goods.getGoods().getGoodsCategory().getName())){
//                //				String cash=goods.getRemark();
//                //				bankPointsStr=pointCashRuleActionSupport.getPointByCash(cash, PointsType.BANK_POINTS);
//                //			}
//                bankPoints = Double.valueOf(bankPointsStr);
//                bankPoints = bankPoints * goodsNum;
//                transDetails.setBankPointsValueAll(String.valueOf(bankPoints));
//                transDetails.setBankPointsValueRealAll(String.valueOf(bankPoints));
//                break;
//            case Currency:
//                transDetails.setFftPointsValueRealAll("0");
//                transDetails.setFftPointsValueAll("0");
//                transDetails.setCurrencyValue("0");
//                transDetails.setCurrencyValueReal("0");
//                transDetails.setBankPointsValueRealAll("0");
//                transDetails.setBankPointsValueAll("0");
//                if (!Command.price_availiable.equals(goods.getIsCash()))
//                {
//                    throw new PriceError("交易品定价有误,请检查交易品id:" + goods.getId() + "的定价");
//                }
//                //			//注意话费按定价来收款,而不是按照话费的实际金额来收款
//                //			if(GoodCategory.Goods_Category_Recharge_Phone.getValue().equals(goods.getGoods().getGoodsCategory().getName())){
//                //				currencyStr=goods.getRemark();
//                //			}
//                currency = Double.valueOf(currencyStr);
//                currency = currency * goodsNum;
//                currency = CommonUtil.scale2(currency, 2);
//                transDetails.setCurrencyValue(String.valueOf(currency));
//                transDetails.setCurrencyValueReal(String.valueOf(currency));
//                break;
//            case FFT_Points_Currency:
//                transDetails.setFftPointsValueRealAll("0");
//                transDetails.setFftPointsValueAll("0");
//                transDetails.setCurrencyValue("0");
//                transDetails.setCurrencyValueReal("0");
//                transDetails.setBankPointsValueRealAll("0");
//                transDetails.setBankPointsValueAll("0");
//                if (!Command.price_availiable.equals(goods.getIsFftpointCash()))
//                {
//                    throw new PriceError("交易品定价有误,请检查交易品id:" + goods.getId() + "的定价");
//                }
//
//                String fftpointsAndCurrency = goods.getFftpointCashPricing();
//                int index = fftpointsAndCurrency.indexOf("|");
//                if (index == -1 || index == 0 || index == fftpointsAndCurrency.length()) throw new PriceError("交易品定价有误,请检查交易品id:" + goods.getId() + "的定价");
//                String[] fftpointsAndCurrencyArr = {fftpointsAndCurrency.substring(0, index), fftpointsAndCurrency.substring(index + 1)};
//                if (fftpointsAndCurrencyArr != null && fftpointsAndCurrencyArr.length == 2)
//                {
//                    fftPoints = Double.valueOf(fftpointsAndCurrencyArr[0]);
//                    fftPoints = fftPoints * goodsNum;
//                    currency = Double.valueOf(fftpointsAndCurrencyArr[1]);
//                    currency = currency * goodsNum;
//                    currency = CommonUtil.scale2(currency, 2);
//                    transDetails.setFftPointsValueAll(String.valueOf(fftPoints));
//                    transDetails.setFftPointsValueRealAll(String.valueOf(fftPoints));
//                    transDetails.setCurrencyValue(String.valueOf(currency));
//                    transDetails.setCurrencyValueReal(String.valueOf(currency));
//                }
//                break;
//            case Bank_Points_Currency:
//                transDetails.setFftPointsValueRealAll("0");
//                transDetails.setFftPointsValueAll("0");
//                transDetails.setCurrencyValue("0");
//                transDetails.setCurrencyValueReal("0");
//                transDetails.setBankPointsValueRealAll("0");
//                transDetails.setBankPointsValueAll("0");
//                if (!Command.price_availiable.equals(goods.getIsBankpointCash()))
//                {
//                    throw new PriceError("交易品定价有误,请检查交易品id:" + goods.getId() + "的定价");
//                }
//                String bankpointsAndCurrency = goods.getBankpointCashPricing();
//
//
//                int index2 = bankpointsAndCurrency.indexOf("|");
//                if (index2 == -1 || index2 == 0 || index2 == bankpointsAndCurrency.length()) throw new PriceError("交易品定价有误,请检查交易品id:" + goods.getId() + "的定价");
//                String[] bankpointsAndCurrencyArr = {bankpointsAndCurrency.substring(0, index2), bankpointsAndCurrency.substring(index2 + 1)};
//
//                bankPoints = Double.valueOf(bankpointsAndCurrencyArr[0]);
//                bankPoints = bankPoints * goodsNum;
//                currency = Double.valueOf(bankpointsAndCurrencyArr[1]);
//                currency = currency * goodsNum;
//                currency = CommonUtil.scale2(currency, 2);
//                if (bankpointsAndCurrencyArr != null && bankpointsAndCurrencyArr.length == 2)
//                {
//                    transDetails.setBankPointsValueAll(String.valueOf(bankPoints));
//                    transDetails.setBankPointsValueRealAll(String.valueOf(bankPoints));
//                    transDetails.setCurrencyValue(String.valueOf(currency));
//                    transDetails.setCurrencyValueReal(String.valueOf(currency));
//                }
//                break;
//
//            case FFT_Points_Currency_Scope:
//                if (!Command.price_availiable.equals(goods.getIsFftpointcashRatioPricing()))
//                {
//                    throw new PriceError("交易品定价有误,请检查交易品id:" + goods.getId() + "的定价");
//                }
//                if (Assert.empty(transDetails.getFftPointsValueRealAll())) transDetails.setFftPointsValueRealAll("0");
//                String fftpointsMinMax = goods.getFftpointcashRatioPricing();
//                fftpointsMinMax = fftpointsMinMax.replace("|", ",");
//                String[] fftpointsMinMaxArr = fftpointsMinMax.split(",");
//
//                //兑换的收款目前用的现金定价,因为没有这个字段
//                currency = Double.valueOf(currencyStr);
//
//                double fftPointsMin = Double.valueOf(fftpointsMinMaxArr[0]);
//                String fftPointsMinStr = pointCashRuleActionSupport.getPointByCash(String.valueOf(fftPointsMin * 0.01 * currency), PointsType.FFT_POINTS);
//                fftPointsMin = CommonUtil.scale2(Double.parseDouble(fftPointsMinStr), 0);
//
//                double fftPointsMax = Double.valueOf(fftpointsMinMaxArr[1]);
//                String fftPointsMaxStr = pointCashRuleActionSupport.getPointByCash(String.valueOf(fftPointsMax * 0.01 * currency), PointsType.FFT_POINTS);
//                fftPointsMax = CommonUtil.scale2(Double.parseDouble(fftPointsMaxStr), 0);
//
//                fftPoints = Double.parseDouble(transDetails.getFftPointsValueRealAll());
//                if (fftPoints > fftPointsMax) throw new PointsUseError("使用的积分不在定价的积分范围内");
//                if (fftPoints < fftPointsMin) throw new PointsUseError("使用的积分不在定价的积分范围内");
//
//                String fftPointsRealCurrencyValStr = pointCashRuleActionSupport.getCashByPoint(transDetails.getFftPointsValueRealAll(), PointsType.FFT_POINTS);
//                //如何现金与积分换算有误，则用资金收款
//                if (Assert.empty(fftPointsRealCurrencyValStr))
//                {
//                    fftPointsRealCurrencyValStr = "0";
//                    fftPoints = 0;
//                    logger.info("现金与积分换算有误，则用资金收款!" + new Timestamp(new Date().getTime()));
//                }
//                double fftPointsRealD = Double.valueOf(fftPointsRealCurrencyValStr);
//                currency = currency * goodsNum - fftPointsRealD;
//                currency = CommonUtil.scale2(currency, 2);
//
//                transDetails.setFftPointsValueRealAll(String.valueOf(fftPoints));
//                transDetails.setFftPointsValueAll(transDetails.getFftPointsValueRealAll());
//                transDetails.setCurrencyValue(String.valueOf(currency));
//                transDetails.setCurrencyValueReal(String.valueOf(currency));
//
//                transDetails.setBankPointsValueRealAll("0");
//                transDetails.setBankPointsValueAll("0");
//                break;
//
//            case Bank_Points_Currency_Scope:
//                if (!Command.price_availiable.equals(goods.getIsBankpointcashRatioPricing()))
//                {
//                    throw new PriceError("交易品定价有误,请检查交易品id:" + goods.getId() + "的定价");
//                }
//                if (Assert.empty(transDetails.getBankPointsValueRealAll())) transDetails.setBankPointsValueRealAll("0");
//                String bankpointsMinMax = goods.getBankpointcashRatioPricing();
//                bankpointsMinMax = bankpointsMinMax.replace("|", ",");
//                String[] bankpointsMinMaxArr = bankpointsMinMax.split(",");
//
//                //兑换的收款目前用的现金定价,因为没有这个字段
//                currency = Double.valueOf(currencyStr);
//
//                double bankPointsMin = Double.valueOf(bankpointsMinMaxArr[0]);
//                String bankPointsMinStr = pointCashRuleActionSupport.getPointByCash(String.valueOf(bankPointsMin * 0.01 * currency), PointsType.BANK_POINTS);
//                bankPointsMin = CommonUtil.scale2(Double.parseDouble(bankPointsMinStr), 0);
//
//                double bankPointsMax = Integer.valueOf(bankpointsMinMaxArr[1]);
//                String bankPointsMaxStr = pointCashRuleActionSupport.getPointByCash(String.valueOf(bankPointsMax * 0.01 * currency), PointsType.BANK_POINTS);
//                bankPointsMax = CommonUtil.scale2(Double.parseDouble(bankPointsMaxStr), 0);
//
//                bankPoints = Double.parseDouble(transDetails.getBankPointsValueRealAll());
//                if (bankPoints > bankPointsMax) throw new PointsUseError("使用的积分不在定价的积分范围内");
//                if (bankPoints < bankPointsMin) throw new PointsUseError("使用的积分不在定价的积分范围内");
//
//                //计算应收金额
//                String bankPointsRealCurrencyValStr = pointCashRuleActionSupport.getCashByPoint(transDetails.getBankPointsValueRealAll(), PointsType.BANK_POINTS);
//                //如何现金与积分换算有误，则用资金收款
//                if (Assert.empty(bankPointsRealCurrencyValStr))
//                {
//                    bankPointsRealCurrencyValStr = "0";
//                    bankPoints = 0;
//                    logger.info("现金与积分换算有误，则用资金收款!" + new Timestamp(new Date().getTime()));
//                }
//                double bankPointsRealD = Double.valueOf(bankPointsRealCurrencyValStr);
//                currency = currency * goodsNum - bankPointsRealD;
//                currency = CommonUtil.scale2(currency, 2);
//
//                transDetails.setBankPointsValueRealAll(String.valueOf(bankPoints));
//                transDetails.setBankPointsValueAll(transDetails.getBankPointsValueRealAll());
//                transDetails.setCurrencyValue(String.valueOf(currency));
//                transDetails.setCurrencyValueReal(String.valueOf(currency));
//                transDetails.setFftPointsValueRealAll("0");
//                transDetails.setFftPointsValueAll("0");
//                break;
//        }
//        ;
//    }

    /**
     * 根据条件获取送积分活动的详细信息
     * @param pointRule 查询条件
     * @return 送积分的数据列表
     */
    public List<PresentPointRule> getPresentPointRule(PresentPointRule pointRule)
    {
        List<PresentPointRule> result = null;
        try
        {
            result = presentPointRuleService.getByConditions(pointRule);
        }
        catch (Exception e)
        {
            logger.error("GoodsExchangeRackActionSupport.getPresentPointRule查询送积分数据列表出错", e);
        }
        return result;
    }

    public GoodsExchangeRackService getGoodsExchangeRackService()
    {
        return goodsExchangeRackService;
    }

    public void setGoodsExchangeRackService(GoodsExchangeRackService goodsExchangeRackService)
    {
        this.goodsExchangeRackService = goodsExchangeRackService;
    }

    public TranService getTransServiceInClient()
    {
        return transServiceInClient;
    }

    public void setTransServiceInClient(TranService transServiceInClient)
    {
        this.transServiceInClient = transServiceInClient;
    }

    public SellersActionSupport getSellerActionSupport()
    {
        return sellerActionSupport;
    }

    public void setSellerActionSupport(SellersActionSupport sellerActionSupport)
    {
        this.sellerActionSupport = sellerActionSupport;
    }

    public TransActionSupport getTransActionSupport()
    {
        return transActionSupport;
    }

    public void setTransActionSupport(TransActionSupport transActionSupport)
    {
        this.transActionSupport = transActionSupport;
    }

    public PointCashRuleActionSupport getPointCashRuleActionSupport()
    {
        return pointCashRuleActionSupport;
    }

    public void setPointCashRuleActionSupport(PointCashRuleActionSupport pointCashRuleActionSupport)
    {
        this.pointCashRuleActionSupport = pointCashRuleActionSupport;
    }

    public GoodsActionSupport getGoodsActionSupport()
    {
        return goodsActionSupport;
    }

    public void setGoodsActionSupport(GoodsActionSupport goodsActionSupport)
    {
        this.goodsActionSupport = goodsActionSupport;
    }

    public MerchantActionSupport getMerchantActionSupport()
    {
        return merchantActionSupport;
    }

    public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport)
    {
        this.merchantActionSupport = merchantActionSupport;
    }

    public PresentPointRuleService getPresentPointRuleService()
    {
        return presentPointRuleService;
    }

    public void setPresentPointRuleService(PresentPointRuleService presentPointRuleService)
    {
        this.presentPointRuleService = presentPointRuleService;
    }
}
