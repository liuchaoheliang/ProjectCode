/**
 * 文件名称:GoodsPresellRackAction.java
 * 文件描述: 分分通前台系统
 * 产品标识: 分分通前台系统
 * 单元描述: client
 * 编写人: houguoquan@f-road.com.cn
 * 编写时间: 14-3-11
 * 历史修改:  
 */
package com.froad.action.buy;

import com.froad.action.support.*;
import com.froad.action.support.presell.PreSellActionSupport;
import com.froad.action.utils.TranStateResult;
import com.froad.action.utils.TransStateUtil;
import com.froad.action.web.tran.TransAddtionalInfoVo;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.authTicket.AuthTicketService;
import com.froad.client.goodsPresellRack.GoodsPresellRack;
import com.froad.client.merchantTrafficMAP.MerchantTrafficMAP;

import com.froad.client.presellBuyInfo.PresellBuyInfo;
import com.froad.client.trans.*;
import com.froad.client.transDetails.TransDetails;
import com.froad.client.transRule.TransRule;
import com.froad.client.userCertification.UserCertification;
import com.froad.common.*;
import com.froad.util.*;

import java.math.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author houguoquan@f-road.com.cn
 * @version 1.0
 */
public class GoodsPresellRackAction extends BaseActionSupport
{
    private static final long serialVersionUID = -2934011944567543485L;
    private GoodsPreSellRackActionSupport goodsPreSellRackActionSupport;
    private PreSellActionSupport preSellActionSupport;
    private TransActionSupport transActionSupport;
    private BuyersActionSupport buyersActionSupport;
    private AuthTicketActionSupport authTicketActionSupport;
    private TagActionSupport tagActionSupport;
    private UserActionSupport userActionSupport;
    private UserCertificationActionSupport userCertificationActionSupport;
    private PointObtainActionSupport pointObtainActionSupport;
    private PointActionSupport pointActionSupport;
    private UserCertification userCertification;
    private GoodsPresellRack pager;
    private GoodsPresellRack goodsPresellRack;
    private TransDetails transDetails;
    private Trans trans;
    private TransAddtionalInfoVo transAddtionalInfoVo;
    private String errorMsg;
    private com.froad.client.transRule.TransRule pointsExchCurrencyRule;
    private double cashFftPointsRatio;
    private double cashBankPointsRatio;
    private PointCashRuleActionSupport pointCashRuleActionSupport;
    private MerchantTrafficMAPActionSupport merchantTrafficMAPActionSupport;
    private MerchantTrafficMAP merchantTrafficMAP;//商户地图

    private AuthTicketService authTicketService;
    private String authId = null;

    //详情显示验证券
    private String authIdElse = null;
    private String securitiesNo = null;//券号

    /**
     * 方法描述：历史预售
     *
     * @param:
     * @return: json
     */
    public void presellGoodsHistoryList()
    {
        log.info("历史预售排行！");

        GoodsPresellRack presellQueryData = new GoodsPresellRack();

        presellQueryData.setIsRack(Command.IS_RACK_YES);//上架的商品
        presellQueryData.setState(Command.FBU_USERED_STATE);
        //        presellQueryData.setOrderBy("market_total_number*1");//销售数量
        presellQueryData.setOrderType(com.froad.client.goodsPresellRack.OrderType.DESC);
        presellQueryData.setPageSize(6);//每页6条
        List<GoodsPresellRack> rackList = goodsPreSellRackActionSupport.getHistory(presellQueryData);

        List<Object> list = new ArrayList();
        if (rackList != null && !rackList.isEmpty())
        {
            for (Object goodsPresellRack : rackList)
            {
                GoodsPresellRack ggr = (GoodsPresellRack) goodsPresellRack;
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", ggr.getId() + "");
                map.put("title", ggr.getSeoTitle());
                map.put("groupPrice", ggr.getGroupPrice());
                if (ggr.getGoodsPresellRackImages() != null && ggr.getGoodsPresellRackImages().size() > 0)
                {
                    map.put("img", Command.IMAGE_SERVER_URL + ggr.getGoodsPresellRackImages().get(0).getImagesUrl());
                }
                else
                {
                    map.put("img", Command.IMAGE_SERVER_URL + ggr.getGoods().getSourceUrl());
                }
                String marketTotalNumber = ggr.getTrueBuyerNum() + "";
                map.put("marketTotalNumber", marketTotalNumber);
                map.put("oralPrice", ggr.getGoods().getPrice());

                list.add(map);
            }
        }

        String jsonStr = JsonUtil.getJsonString4List(list);
        log.info("历史精品预售：" + jsonStr);

        ajaxJson(jsonStr);
    }

    public String getPresellTranDetailInfo()
    {
        //TODO:
        if (trans == null || Assert.empty(trans.getId()))
        {
            trans = new Trans();
            return "success";
        }
        trans = transActionSupport.getTransById(trans.getId());

        if (authIdElse != null && authIdElse.length() > 0)
        {
            com.froad.client.authTicket.AuthTicket aticket = new com.froad.client.authTicket.AuthTicket();
            aticket = authTicketService.getAuthTicketById(Integer.parseInt(authIdElse));
            securitiesNo = aticket != null || !Assert.empty(aticket.getSecuritiesNo()) ? aticket.getSecuritiesNo() : "";
            trans.setConsumeTime(aticket == null || Assert.empty(aticket.getConsumeTime()) ? "" : aticket.getConsumeTime());
            trans.setIsConsume(aticket == null || Assert.empty(aticket.getIsConsume()) ? "" : aticket.getIsConsume());
        }
        //团购交易详情根据数量计算单价,也卖弄只显示单价
        String num = trans.getTransDetailsList() != null && trans.getTransDetailsList().size() > 0 ? trans.getTransDetailsList().get(0).getGoodsNumber() : "0";
        BigDecimal goodsNum = new BigDecimal(num);
        //实体商品（目前只有实体商品会发送消费券）
        if (TranCommand.TRAN_SUCCESS.equals(trans.getState()) && (Assert.empty(trans.getVirtualType()) || (!Assert.empty(trans.getVirtualType()) && !TranCommand.VIRTUAL_TYPE.containsKey(trans
                .getVirtualType()))) && goodsNum != null && goodsNum.compareTo(new BigDecimal("1")) > 0)
        {
            //            if (!Assert.empty(trans.getCostpriceTotal()) && !"0".equals(trans.getCostpriceTotal()))
            //            {
            //                BigDecimal costpriceTotal = new BigDecimal(trans.getCostpriceTotal());
            //                costpriceTotal = costpriceTotal.divide(goodsNum, 2, RoundingMode.DOWN);
            //                trans.setCostpriceTotal(costpriceTotal.toString());
            //            }
            //            if (!Assert.empty(trans.getBankPointsValueRealAll()) && !"0".equals(trans.getBankPointsValueRealAll()))
            //            {
            //                BigDecimal bankPoints = new BigDecimal(trans.getBankPointsValueRealAll());
            //                bankPoints = bankPoints.divide(goodsNum, 2, RoundingMode.DOWN);
            //                trans.setBankPointsValueRealAll(bankPoints.toString());
            //            }
            //            if (!Assert.empty(trans.getFftPointsValueRealAll()) && !"0".equals(trans.getFftPointsValueRealAll()))
            //            {
            //                BigDecimal fftPoints = new BigDecimal(trans.getFftPointsValueRealAll());
            //                fftPoints = fftPoints.divide(goodsNum, 2, RoundingMode.DOWN);
            //                trans.setFftPointsValueRealAll(fftPoints.toString());
            //            }
            //            if (!Assert.empty(trans.getCurrencyValueRealAll()) && !"0".equals(trans.getCurrencyValueRealAll()))
            //            {
            //                BigDecimal currencyValue = new BigDecimal(trans.getCurrencyValueRealAll());
            //                currencyValue = currencyValue.divide(goodsNum, 2, RoundingMode.DOWN);
            //                trans.setCurrencyValueRealAll(currencyValue.toString());
            //            }
        }


        if (trans == null)
        {
            trans = new Trans();
        }

        TranStateResult stateResult = TransStateUtil.getTranState(trans, goodsPreSellRackActionSupport);
        //处理交易状态
        trans.setState(stateResult.getColourFlag());
        trans.setRemark(stateResult.getStateShow());

        PresellBuyInfo presellBuyInfo = new PresellBuyInfo();
        presellBuyInfo.setTransId( trans.getId());
        List<PresellBuyInfo> list = preSellActionSupport.findPreSellBuyInfobyConditions(presellBuyInfo);
        PresellBuyInfo _buy = list.get(0);
        com.froad.client.presellBuyInfo.PresellDelivery delivery = _buy.getPresellDelivery();
        //临时储存的数据         
        trans.setRespCode(delivery.getName()+" "+delivery.getAddress());
        trans.setRespMsg(delivery.getBusinessTime());
        trans.setBeName(delivery.getTelephone());
        return "success";
    }

    public static long getSerialVersionUID()
    {
        return serialVersionUID;
    }

    public GoodsPreSellRackActionSupport getGoodsPreSellRackActionSupport()
    {
        return goodsPreSellRackActionSupport;
    }

    public void setGoodsPreSellRackActionSupport(GoodsPreSellRackActionSupport goodsPreSellRackActionSupport)
    {
        this.goodsPreSellRackActionSupport = goodsPreSellRackActionSupport;
    }

    public TransActionSupport getTransActionSupport()
    {
        return transActionSupport;
    }

    public void setTransActionSupport(TransActionSupport transActionSupport)
    {
        this.transActionSupport = transActionSupport;
    }

    public BuyersActionSupport getBuyersActionSupport()
    {
        return buyersActionSupport;
    }

    public void setBuyersActionSupport(BuyersActionSupport buyersActionSupport)
    {
        this.buyersActionSupport = buyersActionSupport;
    }

    public TagActionSupport getTagActionSupport()
    {
        return tagActionSupport;
    }

    public void setTagActionSupport(TagActionSupport tagActionSupport)
    {
        this.tagActionSupport = tagActionSupport;
    }

    public UserActionSupport getUserActionSupport()
    {
        return userActionSupport;
    }

    public void setUserActionSupport(UserActionSupport userActionSupport)
    {
        this.userActionSupport = userActionSupport;
    }

    public UserCertificationActionSupport getUserCertificationActionSupport()
    {
        return userCertificationActionSupport;
    }

    public void setUserCertificationActionSupport(UserCertificationActionSupport userCertificationActionSupport)
    {
        this.userCertificationActionSupport = userCertificationActionSupport;
    }

    public PointObtainActionSupport getPointObtainActionSupport()
    {
        return pointObtainActionSupport;
    }

    public void setPointObtainActionSupport(PointObtainActionSupport pointObtainActionSupport)
    {
        this.pointObtainActionSupport = pointObtainActionSupport;
    }

    public PointActionSupport getPointActionSupport()
    {
        return pointActionSupport;
    }

    public void setPointActionSupport(PointActionSupport pointActionSupport)
    {
        this.pointActionSupport = pointActionSupport;
    }

    public UserCertification getUserCertification()
    {
        return userCertification;
    }

    public void setUserCertification(UserCertification userCertification)
    {
        this.userCertification = userCertification;
    }

    public GoodsPresellRack getPager()
    {
        return pager;
    }

    public void setPager(GoodsPresellRack pager)
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

    public TransDetails getTransDetails()
    {
        return transDetails;
    }

    public void setTransDetails(TransDetails transDetails)
    {
        this.transDetails = transDetails;
    }

    public Trans getTrans()
    {
        return trans;
    }

    public void setTrans(Trans trans)
    {
        this.trans = trans;
    }

    public TransAddtionalInfoVo getTransAddtionalInfoVo()
    {
        return transAddtionalInfoVo;
    }

    public void setTransAddtionalInfoVo(TransAddtionalInfoVo transAddtionalInfoVo)
    {
        this.transAddtionalInfoVo = transAddtionalInfoVo;
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    public TransRule getPointsExchCurrencyRule()
    {
        return pointsExchCurrencyRule;
    }

    public void setPointsExchCurrencyRule(TransRule pointsExchCurrencyRule)
    {
        this.pointsExchCurrencyRule = pointsExchCurrencyRule;
    }

    public double getCashFftPointsRatio()
    {
        return cashFftPointsRatio;
    }

    public void setCashFftPointsRatio(double cashFftPointsRatio)
    {
        this.cashFftPointsRatio = cashFftPointsRatio;
    }

    public double getCashBankPointsRatio()
    {
        return cashBankPointsRatio;
    }

    public void setCashBankPointsRatio(double cashBankPointsRatio)
    {
        this.cashBankPointsRatio = cashBankPointsRatio;
    }

    public PointCashRuleActionSupport getPointCashRuleActionSupport()
    {
        return pointCashRuleActionSupport;
    }

    public void setPointCashRuleActionSupport(PointCashRuleActionSupport pointCashRuleActionSupport)
    {
        this.pointCashRuleActionSupport = pointCashRuleActionSupport;
    }

    public MerchantTrafficMAPActionSupport getMerchantTrafficMAPActionSupport()
    {
        return merchantTrafficMAPActionSupport;
    }

    public void setMerchantTrafficMAPActionSupport(MerchantTrafficMAPActionSupport merchantTrafficMAPActionSupport)
    {
        this.merchantTrafficMAPActionSupport = merchantTrafficMAPActionSupport;
    }

    public MerchantTrafficMAP getMerchantTrafficMAP()
    {
        return merchantTrafficMAP;
    }

    public void setMerchantTrafficMAP(MerchantTrafficMAP merchantTrafficMAP)
    {
        this.merchantTrafficMAP = merchantTrafficMAP;
    }

    public AuthTicketService getAuthTicketService()
    {
        return authTicketService;
    }

    public void setAuthTicketService(AuthTicketService authTicketService)
    {
        this.authTicketService = authTicketService;
    }

    public String getAuthId()
    {
        return authId;
    }

    public void setAuthId(String authId)
    {
        this.authId = authId;
    }

    public String getAuthIdElse()
    {
        return authIdElse;
    }

    public void setAuthIdElse(String authIdElse)
    {
        this.authIdElse = authIdElse;
    }

    public String getSecuritiesNo()
    {
        return securitiesNo;
    }

    public void setSecuritiesNo(String securitiesNo)
    {
        this.securitiesNo = securitiesNo;
    }

    public AuthTicketActionSupport getAuthTicketActionSupport()
    {
        return authTicketActionSupport;
    }

    public void setAuthTicketActionSupport(AuthTicketActionSupport authTicketActionSupport)
    {
        this.authTicketActionSupport = authTicketActionSupport;
    }

    public PreSellActionSupport getPreSellActionSupport()
    {
        return preSellActionSupport;
    }

    public void setPreSellActionSupport(PreSellActionSupport preSellActionSupport)
    {
        this.preSellActionSupport = preSellActionSupport;
    }
}
