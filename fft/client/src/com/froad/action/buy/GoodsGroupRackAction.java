package com.froad.action.buy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.action.support.*;
import com.froad.action.web.tran.TransAddtionalInfoVo;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.authTicket.AuthTicket;
import com.froad.client.authTicket.AuthTicketService;
import com.froad.client.buyers.Buyers;
import com.froad.client.goodsGroupRack.GoodsGroupRack;
import com.froad.client.goodsPresellRack.GoodsPresellRack;
import com.froad.client.merchantTrafficMAP.MerchantTrafficMAP;
import com.froad.client.point.PointsAccount;
import com.froad.client.trans.*;
import com.froad.client.trans.Goods;
import com.froad.client.transRule.TransRule;
import com.froad.client.user.User;
import com.froad.client.userCertification.UserCertification;
import com.froad.common.SessionKey;
import com.froad.common.TranCommand;
import com.froad.util.Assert;
import com.froad.util.Command;
import com.froad.util.CommonUtil;
import com.froad.util.JsonUtil;
import com.froad.util.command.ClientType;
import com.froad.util.command.GoodCategory;
import com.froad.util.command.PointsType;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

/**
 * @author Qiaopeng.Lee
 * @version 1.0
 * @date 2013-3-8
 */
public class GoodsGroupRackAction extends BaseActionSupport
{

    /**
     * UID
     */
    private static final long serialVersionUID = -2934011944567543485L;
    private GoodsGroupRackActionSupport goodsGroupRackActionSupport;
    private GoodsPreSellRackActionSupport goodsPreSellRackActionSupport;
    private TransActionSupport transActionSupport;
    private BuyersActionSupport buyersActionSupport;
    private TagActionSupport tagActionSupport;
    private UserActionSupport userActionSupport;
    private UserCertificationActionSupport userCertificationActionSupport;
    private PointObtainActionSupport pointObtainActionSupport;
    private PointActionSupport pointActionSupport;
    private UserCertification userCertification;
    private GoodsGroupRack pager;
    private GoodsGroupRack goodsGroupRack;
    private TransDetails transDetails;
    private Trans trans;
    private TransAddtionalInfoVo transAddtionalInfoVo;
    private String errorMsg;
    private TransRule pointsExchCurrencyRule;
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
     * 查询团购上架goods的详细信息
     * 进入团购商品的详情页面
     *
     * @return
     */
    public String groupGoodsDetail()
    {
        //查询上架团购商品的详细信息
        if (goodsGroupRack == null)
        {
            goodsGroupRack = new GoodsGroupRack();
        }
        if (Assert.empty(goodsGroupRack.getId()))
        {
            return "failse";
        }
        goodsGroupRack = goodsGroupRackActionSupport.getGoodsGroupRackById(goodsGroupRack.getId());

        //商户地图
        merchantTrafficMAP = new MerchantTrafficMAP();
        merchantTrafficMAP = merchantTrafficMAPActionSupport.getMerchantTrafficMapByMerchantId(goodsGroupRack.getGoods().getMerchantId());

        return "success";
    }

    /**
     * 方法描述：团购热卖排行
     *
     * @param:
     * @return: json
     */
    public void groupGoodsHotList()
    {
        log.info("团购热卖排行！");

        GoodsGroupRack goodsGroupRackPager = new GoodsGroupRack();

        goodsGroupRackPager.setIsRack(Command.IS_RACK_YES);//上架的商品
        goodsGroupRackPager.setState(Command.FBU_USERED_STATE);
        goodsGroupRackPager.setOrderBy("market_total_number*1");//销售数量
        goodsGroupRackPager.setOrderType(com.froad.client.goodsGroupRack.OrderType.DESC);
        goodsGroupRackPager.setPageSize(6);//每页6条
        goodsGroupRackPager = goodsGroupRackActionSupport.findGoodsGroupByPager(goodsGroupRackPager);

        List<Object> list = new ArrayList();
        if (goodsGroupRackPager.getList() != null && !goodsGroupRackPager.getList().isEmpty())
        {
            List goodsGroupRackList = goodsGroupRackPager.getList();
            for (Object goodsGroupRack : goodsGroupRackList)
            {
                GoodsGroupRack ggr = (GoodsGroupRack) goodsGroupRack;
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", ggr.getId() + "");
                map.put("title", ggr.getSeoTitle());
                map.put("groupPrice", ggr.getGroupPrice());
                if (ggr.getGoodsGroupRackImages() != null && ggr.getGoodsGroupRackImages().size() > 0)
                {
                    map.put("img", Command.IMAGE_SERVER_URL + ggr.getGoodsGroupRackImages().get(0).getImagesUrl());
                }
                else
                {
                    map.put("img", Command.IMAGE_SERVER_URL + ggr.getGoods().getSourceUrl());
                }
                map.put("marketTotalNumber", ggr.getMarketTotalNumber() + "");

                list.add(map);
            }
        }

        String jsonStr = JsonUtil.getJsonString4List(list);
        //log.info("团购热卖：" + jsonStr);

        ajaxJson(jsonStr);
    }

    /**
     * 最新团购
     */
    public void groupGoodsNewList()
    {
        log.info("最新团购！");

        GoodsGroupRack goodsGroupRackPager = new GoodsGroupRack();

        goodsGroupRackPager.setIsRack(Command.IS_RACK_YES);//上架的商品
        goodsGroupRackPager.setState(Command.FBU_USERED_STATE);
        goodsGroupRackPager.setOrderBy("rack_time");//上架时间
        goodsGroupRackPager.setOrderType(com.froad.client.goodsGroupRack.OrderType.DESC);
        goodsGroupRackPager.setPageSize(5);//每页5条
        goodsGroupRackPager = goodsGroupRackActionSupport.findGoodsGroupByPager(goodsGroupRackPager);

        List<Object> list = new ArrayList();
        if (goodsGroupRackPager.getList() != null && !goodsGroupRackPager.getList().isEmpty())
        {
            List goodsGroupRackList = goodsGroupRackPager.getList();
            for (Object goodsGroupRack : goodsGroupRackList)
            {
                GoodsGroupRack ggr = (GoodsGroupRack) goodsGroupRack;
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", ggr.getId() + "");
                map.put("title", ggr.getSeoTitle());
                map.put("price", ggr.getGoods().getPrice());
                map.put("groupPrice", ggr.getGroupPrice());
                if (ggr.getGoodsGroupRackImages() != null && ggr.getGoodsGroupRackImages().size() > 0)
                {
                    map.put("img", Command.IMAGE_SERVER_URL + ggr.getGoodsGroupRackImages().get(0).getImagesUrl());
                }
                else
                {
                    map.put("img", Command.IMAGE_SERVER_URL + ggr.getGoods().getSourceUrl());
                }

                map.put("marketTotalNumber", ggr.getMarketTotalNumber() + "");

                list.add(map);
            }
        }

        String jsonStr = JsonUtil.getJsonString4List(list);
        //log.info("最新团购：" + jsonStr);

        ajaxJson(jsonStr);
    }

    /**
     * 查询团购交易列表
     *
     * @return String
     */
    public String groupTransList()
    {
        //查询团购交易列表
        return "success";
    }

    //验证订单信息
    private boolean checkTranDetail(TransDetails tranDetaill, Trans trans)
    {
        boolean result = false;
        if (transDetails.getGoodsRackId() == null)
        {
            errorMsg = "交易品为空！请选择要交易品";
            return result;
        }
        pager = goodsGroupRackActionSupport.getGoodsGroupRackById(Integer.parseInt(transDetails.getGoodsRackId()));

        if (pager == null)
        {
            errorMsg = "您选择的交易品有误，系统不存在这个交易品。";
            return result;
        }
        if (pager.getGoods().getMerchantId() == null || pager.getSeller() == null)
        {
            errorMsg = "这个交易品没有商户，或该商户没有卖家。";
            return result;
        }


        if (Assert.empty(transDetails.getGoodsNumber()))
        {
            errorMsg = "您未选择交易品的数量，请输入交易品的数量。";
            return result;
        }
        else if (!Assert.isIntOfGreaterEZero(transDetails.getGoodsNumber()))
        {
            errorMsg = "交易品的数量不是数字！";
            return result;
        }

        if (Assert.empty(trans.getPayMethod()))
        {
            errorMsg = "您未选择支付方式。";
            return result;
        }

        result = true;
        return result;
    }

    private boolean checkUser(User user)
    {
        boolean result = false;
        if (user == null) errorMsg = "您还没有登录！";
        else result = true;
        return result;
    }

    private boolean checkUserCertification(TransDetails transDetails, Trans trans)
    {
        boolean result = false;
        Map<String, Object> session = ActionContext.getContext().getSession();

        String userId = (String) session.get("userId");
        //		String userId="dc4fd752-91ef-4b23-8fa5-0ccff7b8435c";
        userCertification = userCertificationActionSupport.getUserCertification(userId, trans.getChannelId());
        if (userCertification == null) errorMsg = "您还没有进行账户绑定，请去认证！";
        else result = true;
        return result;
    }

    private boolean checkBuyer(Buyers buyer)
    {
        boolean result = false;
        if (buyer == null) errorMsg = "您不是买家！";
        else result = true;
        return result;
    }

    private boolean checkBuyerChannelAccountInfo(TransDetails transDetails, Trans trans, Buyers buyer)
    {
        boolean result = false;
        if (buyer != null && userCertification.getAccountName().equals(buyer.getBuyerChannelList().get(0).getAccountName()) && userCertification.getAccountNo().equals(buyer.getBuyerChannelList()
                .get(0).getAccountNumber()))
            errorMsg = "您的买家账户信息与认证通过的有效账户信息不一致！";
        else result = true;
        return result;
    }

//    private boolean check(TransDetails transDetails, Trans trans, User user, Buyers buyer, TransAddtionalInfoVo transAddtionalInfoVo)
//    {
//        boolean result = false;
//        result = checkTranDetail(transDetails, trans);
//        if (!result) return result;
//        result = checkUser(user);
//        if (!result) return result;
//        result = checkUserCertification(transDetails, trans);
//        if (!result) return result;
//        if (!GoodCategory.Points_Exch_Currency.getValue().equals(pager.getGoods().getGoodsCategory().getName()))
//        {
//            result = checkBuyer(buyer);
//            if (!result) return result;
//            result = checkBuyerChannelAccountInfo(transDetails, trans, buyer);
//            if (!result) return result;
//        }
//
//
//        result = checkPayMethod(transDetails, trans, user, buyer, transAddtionalInfoVo);
//        return result;
//    }
//
//    private boolean checkPayMethod(TransDetails transDetails, Trans trans, User user, Buyers buyer, TransAddtionalInfoVo transAddtionalInfoVo)
//    {
//        boolean result = false;
//        double fftPoints = 0;
//        double bankPoints = 0;
//
//        Map<String, Object> session = ActionContext.getContext().getSession();
//        String userId = (String) session.get("userId");
//        Map<String, PointsAccount> pointsTypePointsAccountMap = (Map<String, PointsAccount>) session.get(SessionKey.POINTS_ACCOUNT_MAP);
//
//        if (PayMethod.FFT_Points.getValue().equals(trans.getPayMethod()))
//        {
//            if (Assert.empty(pointsTypePointsAccountMap) || pointsTypePointsAccountMap.get("FFTPlatform") == null)
//            {
//                errorMsg = " 没有分分通积分账户 ";
//                return result;
//            }
//            if (Assert.empty(pager.getFftPointPricing()))
//            {
//                errorMsg = " 该交易品没有分分通积分定价 ";
//                return result;
//            }
//            if (Double.parseDouble(pointsTypePointsAccountMap.get("FFTPlatform").getPoints()) < Double.parseDouble(pager.getFftPointPricing()))
//            {
//                errorMsg = " 您的分分通积分不够 ";
//                return result;
//            }
//        }
//        if (PayMethod.Bank_Points.getValue().equals(trans.getPayMethod()))
//        {
//            if (Assert.empty(pointsTypePointsAccountMap) || pointsTypePointsAccountMap.get("ZHBank") == null)
//            {
//                errorMsg = " 没有珠海积分账户 ";
//                return result;
//            }
//            if (Assert.empty(pager.getBankPointPricing()))
//            {
//                errorMsg = " 该交易品没有珠海积分定价 ";
//                return result;
//            }
//            if (Double.parseDouble(pointsTypePointsAccountMap.get("ZHBank").getPoints()) < Double.parseDouble(pager.getBankPointPricing()))
//            {
//                errorMsg = " 您的金海洋/信通卡积分不够 ";
//                return result;
//            }
//        }
//        if (PayMethod.FFT_Points_Currency.getValue().equals(trans.getPayMethod()))
//        {
//            if (Assert.empty(pointsTypePointsAccountMap) || pointsTypePointsAccountMap.get("FFTPlatform") == null)
//            {
//                errorMsg = " 没有分分通积分账户 ";
//                return result;
//            }
//            if (Assert.empty(pager.getFftpointCashPricing()))
//            {
//                errorMsg = " 该交易品Id： " + transDetails.getGoodsRackId() + ",没有分分通积分和现金固定定价 ";
//                return result;
//            }
//            String fftpointsAndCurrency = pager.getFftpointCashPricing();
//            fftpointsAndCurrency = fftpointsAndCurrency.replace("|", ",");
//            String[] fftpointsAndCurrencyArr = fftpointsAndCurrency.split(",");
//
//            if (fftpointsAndCurrencyArr != null && fftpointsAndCurrencyArr.length == 2)
//            {
//                fftPoints = Double.valueOf(fftpointsAndCurrencyArr[0]);
//                fftPoints = fftPoints * Integer.valueOf(transDetails.getGoodsNumber());
//            }
//            else
//            {
//                errorMsg = " 团购交易品Id： " + transDetails.getGoodsRackId() + ",分分通积分和现金固定定价有误！";
//                return result;
//            }
//            if (Double.parseDouble(pointsTypePointsAccountMap.get("FFTPlatform").getPoints()) < fftPoints)
//            {
//                errorMsg = " 您的分分通积分不够 ";
//                return result;
//            }
//        }
//        if (PayMethod.Bank_Points_Currency.getValue().equals(trans.getPayMethod()))
//        {
//            if (Assert.empty(pointsTypePointsAccountMap) || pointsTypePointsAccountMap.get("ZHBank") == null)
//            {
//                errorMsg = " 没有珠海积分账户 ";
//                return result;
//            }
//            if (Assert.empty(pager.getBankpointCashPricing()))
//            {
//                errorMsg = " 该交易品Id： " + transDetails.getGoodsRackId() + ",没有金海洋/信通卡积分和现金固定定价 ";
//                return result;
//            }
//
//            String bankpointsAndCurrency = pager.getBankpointCashPricing();
//            bankpointsAndCurrency = bankpointsAndCurrency.replace("|", ",");
//
//            String[] bankpointsAndCurrencyArr = bankpointsAndCurrency.split(",");
//            if (bankpointsAndCurrencyArr != null && bankpointsAndCurrencyArr.length == 2)
//            {
//                bankPoints = Double.valueOf(bankpointsAndCurrencyArr[0]);
//            }
//            else
//            {
//                errorMsg = " 团购交易品Id： " + transDetails.getGoodsRackId() + ",金海洋/信通卡积分和现金固定定价有误！";
//                return result;
//            }
//            bankPoints = bankPoints * Integer.valueOf(transDetails.getGoodsNumber());
//            if (Double.parseDouble(pointsTypePointsAccountMap.get("ZHBank").getPoints()) < bankPoints)
//            {
//                errorMsg = " 您的金海洋/信通卡积分不够 ";
//                return result;
//            }
//        }
//
//        if (PayMethod.FFT_Points_Currency_Scope.getValue().equals(trans.getPayMethod()))
//        {
//            if (Assert.empty(pointsTypePointsAccountMap) || pointsTypePointsAccountMap.get("FFTPlatform") == null)
//            {
//                errorMsg = " 没有分分通积分账户 ";
//                return result;
//            }
//            if (Assert.empty(pager.getFftpointcashRatioPricing()))
//            {
//                errorMsg = " 该交易品Id： " + transDetails.getGoodsRackId() + ",没有分分通积分和现金范围定价 ";
//                return result;
//            }
//            String fftpointsMinMax = pager.getFftpointcashRatioPricing();
//            fftpointsMinMax = fftpointsMinMax.replace("|", ",");
//            String[] fftpointsMinMaxArr = fftpointsMinMax.split(",");
//
//            double fftPointsMin = Double.valueOf(fftpointsMinMaxArr[0]);
//            String fftPointsMinStr = pointCashRuleActionSupport.getPointByCash(String.valueOf(fftPointsMin * 0.01 * Double.parseDouble(pager.getCashPricing())), PointsType.FFT_POINTS);
//            fftPointsMin = CommonUtil.scale2(Double.parseDouble(fftPointsMinStr), 0);
//
//            double fftPointsMax = Double.valueOf(fftpointsMinMaxArr[1]);
//            String fftPointsMaxStr = pointCashRuleActionSupport.getPointByCash(String.valueOf(fftPointsMax * 0.01 * Double.parseDouble(pager.getCashPricing())), PointsType.FFT_POINTS);
//            fftPointsMax = CommonUtil.scale2(Double.parseDouble(fftPointsMaxStr), 0);
//
//            fftPoints = Double.parseDouble(transDetails.getFftPointsValueRealAll());
//            if (fftPoints > fftPointsMax)
//            {
//                errorMsg = " 您使用的分分通积分大于在定价的积分范围的最大值 ";
//                return result;
//            }
//            if (fftPoints < fftPointsMin)
//            {
//                errorMsg = " 您使用的分分通积分小于在定价的积分范围的最小值 ";
//                return result;
//            }
//            if (Double.parseDouble(pointsTypePointsAccountMap.get("FFTPlatform").getPoints()) < fftPoints)
//            {
//                errorMsg = " 您的分分通积分不够 ";
//                return result;
//            }
//        }
//        if (PayMethod.Bank_Points_Currency_Scope.getValue().equals(trans.getPayMethod()))
//        {
//            if (Assert.empty(pointsTypePointsAccountMap) || pointsTypePointsAccountMap.get("ZHBank") == null)
//            {
//                errorMsg = " 没有珠海积分账户 ";
//                return result;
//            }
//            if (Assert.empty(pager.getBankpointcashRatioPricing()))
//            {
//                errorMsg = " 该交易品没有珠海积分定价 ";
//                return result;
//            }
//
//            String bankpointsMinMax = pager.getBankpointcashRatioPricing();
//            bankpointsMinMax = bankpointsMinMax.replace("|", ",");
//            String[] bankpointsMinMaxArr = bankpointsMinMax.split(",");
//
//            double bankPointsMin = Double.valueOf(bankpointsMinMaxArr[0]);
//            String bankPointsMinStr = pointCashRuleActionSupport.getPointByCash(String.valueOf(bankPointsMin * 0.01 * Double.parseDouble(pager.getCashPricing())), PointsType.BANK_POINTS);
//            bankPointsMin = CommonUtil.scale2(Double.parseDouble(bankPointsMinStr), 0);
//
//            double bankPointsMax = Double.valueOf(bankpointsMinMaxArr[1]);
//            String bankPointsMaxStr = pointCashRuleActionSupport.getPointByCash(String.valueOf(bankPointsMax * 0.01 * Double.parseDouble(pager.getCashPricing())), PointsType.BANK_POINTS);
//            bankPointsMax = CommonUtil.scale2(Double.parseDouble(bankPointsMaxStr), 0);
//
//            bankPoints = Double.parseDouble(transDetails.getBankPointsValueRealAll());
//            if (bankPoints > bankPointsMax)
//            {
//                errorMsg = " 您使用的金海洋/信通卡积分大于在定价的积分范围的最大值 ";
//                return result;
//            }
//            if (bankPoints < bankPointsMin)
//            {
//                errorMsg = " 您使用的金海洋/信通卡积分小于在定价的积分范围的最小值 ";
//                return result;
//            }
//            if (Double.parseDouble(pointsTypePointsAccountMap.get("ZHBank").getPoints()) < bankPoints)
//            {
//                errorMsg = " 您的金海洋/信通卡积分不够 ";
//                return result;
//            }
//        }
//        result = true;
//        return result;
//    }

    private void updatePoints()
    {
        Map<String, Object> session = ActionContext.getContext().getSession();
        Boolean changedPoints = false;
        changedPoints = (Boolean) session.get("changedPoints");
        String userId = (String) session.get("userId");
        if (Assert.empty(userId)) return;
        if (changedPoints == null || changedPoints)
        {

            Map<String, PointsAccount> pointsTypePointsAccountMap = null;
            pointsTypePointsAccountMap = pointActionSupport.queryBankAndFftPointsByUserId(userId);
            if (pointsTypePointsAccountMap != null)
            {
                session.put(SessionKey.POINTS_ACCOUNT_MAP, pointsTypePointsAccountMap);
            }
        }
    }

    //积分兑换商品，下单
//    public String order()
//    {
//        Map<String, Object> session = ActionContext.getContext().getSession();
//        String userId = (String) session.get("userId");
//        updatePoints();
//        //		String userId="dc4fd752-91ef-4b23-8fa5-0ccff7b8435c";
//
//        Trans param = trans;
//        User user = userActionSupport.queryUserAllByUserID(userId);
//        Map<String, PointsAccount> pointsTypePointsAccountMap = (Map<String, PointsAccount>) session.get(SessionKey.POINTS_ACCOUNT_MAP);
//        if (!Assert.empty(pointsTypePointsAccountMap))
//        {
//            if (pointsTypePointsAccountMap.get("FFTPlatform") != null) trans.setFftPointsAccount(pointsTypePointsAccountMap.get("FFTPlatform").getAccountId());
//            if (pointsTypePointsAccountMap.get("ZHBank") != null) trans.setBankPointsAccount(pointsTypePointsAccountMap.get("ZHBank").getAccountId());
//        }
//
//        Buyers buyer = buyersActionSupport.getBuyerByUserId(userId);
//
//
//        boolean result = check(transDetails, trans, user, buyer, transAddtionalInfoVo);
//        if (!result)
//        {
//            log.error("userName :" + user.getUsername() + " 下单 验证失败！其失败原因为：" + errorMsg);
//            return "error";
//        }
//        trans = goodsGroupRackActionSupport.createTrans(pager, trans, transDetails, buyer, user, transAddtionalInfoVo, ClientType.ClientType_PC);
//        if (trans == null)
//        {
//            errorMsg = "下单失败！";
//            trans = param;
//            log.error("userName :" + user.getUsername() + " 下单失败");
//            return "error";
//        }
//        return "success";
//    }

    //下推积分兑换交易   --即收款
    //	public String pay(){
    ////		check(transDetails,trans);
    //		boolean result=transActionSupport.pay(trans.getId());
    //		log.info("交易ID为："+trans.getId()+",支付，调用webserver去支付的结果为："+result);
    //		if(!result){
    //			errorMsg="支付失败！";
    //		}else{
    //			Map<String,Object> session=ActionContext.getContext().getSession();
    //			session.put("changedPoints", true);
    //			this.updatePoints();
    //		}
    //
    //		return result?"success":"error";
    //	}

    public String getOrderInfo()
    {

        if (transDetails.getGoodsRackId() == null)
        {
            errorMsg = "交易品为空！请选择要交易品";
            return Action.ERROR;
        }
        pager = goodsGroupRackActionSupport.getGoodsGroupRackById(Integer.parseInt(transDetails.getGoodsRackId()));

        this.updatePoints();

        BigDecimal cashFftPointsRatioBD = pointCashRuleActionSupport.queryPointCashRule(TranCommand.PRICING_FFT_CASH);
        if (cashFftPointsRatioBD != null) cashFftPointsRatio = cashFftPointsRatioBD.doubleValue();
        BigDecimal cashBankPointsRatioBD = pointCashRuleActionSupport.queryPointCashRule(TranCommand.PRICING_BANK_CASH);
        if (cashBankPointsRatioBD != null) cashBankPointsRatio = cashBankPointsRatioBD.doubleValue();
        return Action.SUCCESS;
    }

    public String getGroupTranDetailInfo()
    {
        //TODO:
        if (trans == null || Assert.empty(trans.getId()))
        {
            trans = new Trans();
            return "success";
        }
        trans = transActionSupport.getTransById(trans.getId());
        //		if(authId != null && authId.length() > 0){
        //			trans.setConsumeTime(authTicketService.getAuthTicketById(Integer.parseInt(authId)).getConsumeTime());
        //		}
        if (authIdElse != null && authIdElse.length() > 0)
        {
            AuthTicket aticket = new AuthTicket();
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
            if (!Assert.empty(trans.getCostpriceTotal()) && !"0".equals(trans.getCostpriceTotal()))
            {
                BigDecimal costpriceTotal = new BigDecimal(trans.getCostpriceTotal());
                costpriceTotal = costpriceTotal.divide(goodsNum, 2, RoundingMode.DOWN);
                trans.setCostpriceTotal(costpriceTotal.toString());
            }
            if (!Assert.empty(trans.getBankPointsValueRealAll()) && !"0".equals(trans.getBankPointsValueRealAll()))
            {
                BigDecimal bankPoints = new BigDecimal(trans.getBankPointsValueRealAll());
                bankPoints = bankPoints.divide(goodsNum, 2, RoundingMode.DOWN);
                trans.setBankPointsValueRealAll(bankPoints.toString());
            }
            if (!Assert.empty(trans.getFftPointsValueRealAll()) && !"0".equals(trans.getFftPointsValueRealAll()))
            {
                BigDecimal fftPoints = new BigDecimal(trans.getFftPointsValueRealAll());
                fftPoints = fftPoints.divide(goodsNum, 2, RoundingMode.DOWN);
                trans.setFftPointsValueRealAll(fftPoints.toString());
            }
            if (!Assert.empty(trans.getCurrencyValueRealAll()) && !"0".equals(trans.getCurrencyValueRealAll()))
            {
                BigDecimal currencyValue = new BigDecimal(trans.getCurrencyValueRealAll());
                currencyValue = currencyValue.divide(goodsNum, 2, RoundingMode.DOWN);
                trans.setCurrencyValueRealAll(currencyValue.toString());
            }
        }

        //补精品预售的商品名 团购与精品预售的方法拆分后删除
//        if (TranCommand.PRE_SELL.equals(trans.getTransType()))
//        {
//            TransDetails detail = trans.getTransDetailsList().get(0);
//            GoodsPresellRack rack = goodsPreSellRackActionSupport.getGoodsPresellRackById(Integer.parseInt(detail.getGoodsRackId()));
//            Goods detailGoods = new Goods();
//            com.froad.client.goodsPresellRack.Goods rackGoods = rack.getGoods();
//            detailGoods.setGoodsName(rackGoods.getGoodsName());
//            detail.setGoods(detailGoods);
//        }

        if (trans == null)
        {
            trans = new Trans();
        }
        return "success";
    }

    public GoodsGroupRackActionSupport getGoodsGroupRackActionSupport()
    {
        return goodsGroupRackActionSupport;
    }

    public void setGoodsGroupRackActionSupport(GoodsGroupRackActionSupport goodsGroupRackActionSupport)
    {
        this.goodsGroupRackActionSupport = goodsGroupRackActionSupport;
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

    public GoodsGroupRack getPager()
    {
        return pager;
    }

    public void setPager(GoodsGroupRack pager)
    {
        this.pager = pager;
    }

    public GoodsGroupRack getGoodsGroupRack()
    {
        return goodsGroupRack;
    }

    public void setGoodsGroupRack(GoodsGroupRack goodsGroupRack)
    {
        this.goodsGroupRack = goodsGroupRack;
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

    public PointCashRuleActionSupport getPointCashRuleActionSupport()
    {
        return pointCashRuleActionSupport;
    }

    public void setPointCashRuleActionSupport(PointCashRuleActionSupport pointCashRuleActionSupport)
    {
        this.pointCashRuleActionSupport = pointCashRuleActionSupport;
    }

    public UserCertification getUserCertification()
    {
        return userCertification;
    }

    public void setUserCertification(UserCertification userCertification)
    {
        this.userCertification = userCertification;
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

    public GoodsPreSellRackActionSupport getGoodsPreSellRackActionSupport()
    {
        return goodsPreSellRackActionSupport;
    }

    public void setGoodsPreSellRackActionSupport(GoodsPreSellRackActionSupport goodsPreSellRackActionSupport)
    {
        this.goodsPreSellRackActionSupport = goodsPreSellRackActionSupport;
    }
}
