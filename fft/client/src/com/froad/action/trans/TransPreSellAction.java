package com.froad.action.trans;

import com.froad.action.support.*;
import com.froad.action.support.presell.PreSellActionSupport;
import com.froad.action.support.trans.*;
import com.froad.action.support.trans.PayActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.Store.Store;
import com.froad.client.buyers.BuyerChannel;
import com.froad.client.buyers.Buyers;
import com.froad.client.goodsPresellRack.GoodsPresellRack;
import com.froad.client.merchantTrafficMAP.MerchantTrafficMAP;
import com.froad.client.point.PointsAccount;
import com.froad.client.presellBuyInfo.PresellBuyInfo;
import com.froad.client.presellDelivery.PresellDelivery;
import com.froad.client.sellers.*;
import com.froad.client.sellers.OrderType;
import com.froad.client.trans.*;
import com.froad.client.trans.SellerChannel;
import com.froad.client.user.User;
import com.froad.common.*;
import com.froad.common.Pager;
import com.froad.util.*;
import com.froad.util.Result;
import com.froad.util.command.Command;
import com.opensymphony.xwork2.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 侯国权
 * @version 1.0
 */
public class TransPreSellAction extends BaseActionSupport
{
    /**
     * UID
     */
    private static final long serialVersionUID = -112768927609061741L;
    private static final Logger logger = Logger.getLogger(TransGroupAction.class);
    private PreSellActionSupport preSellActionSupport;
    private GoodsPreSellRackActionSupport goodsPreSellRackActionSupport;
    private MerchantTrafficMAPActionSupport merchantTrafficMAPActionSupport;
    private TransActionSupport transActionSupport;
    private PayActionSupport payActionSupport;
    private PointActionSupport pointActionSupport;
    private BuyersActionSupport buyersActionSupport;
    private SellersActionSupport sellersActionSupport;
    private MerchantTrafficMAP merchantTrafficMAP;// 商户地图

    private GoodsPresellRack pager;
    private GoodsPresellRack goodsPresellRack;
    private Trans trans;
    private TempTrans tempTran;
    private String cash;
    private String fftPointPricing;// 分分通积分支付的积分数
    private String bankPointPricing;// 银行积分支付的积分数
    private String cashFftPointPricing;// 分分通积分+现金的组合支付的积分数
    private String cashFftPointPricingCash;// 分分通积分+现金的组合支付的现金
    private String cashBankPointPricing;// 银行积分+现金的组合支付的积分数
    private String cashBankPointPricingCash;// 银行积分+现金的组合支付的现金
    private String fftPoint;
    private String bankPoint;
    private String bankPointValueMin;
    private String fftPointValueMin;
    private String bankPointValueMax;
    private String fftPointValueMax;
    private String cashFftPointsRatio;
    private String cashBankPointsRatio;

    private String payResult;
    private String paymentUrl;
    private String errorMsg;
    private String startFalg = "1";

    private String notfind = "";

    // ===========================================================新版商户展示
    private StoreActionSupport storeActionSupport;
    private List<Store> storeList;
    // 新增提货点信息
    List<PresellDelivery> deliveryList;

    // 积分支付方式
    private String payPointType;
    // 现金支付方式
    private String payCashType;
    // 提货点信息
    private PresellDelivery delivery;

    private Date serverTime;

    /**
     * 预售结束时间判断
     */
    private Map<Object, String> leftTimeMap = new HashMap<Object, String>();

    /**
     * 商品时间状态
     */
    private Map<Object, String> goodsStatusMap = new HashMap<Object, String>();

    /**
     * 积分支付值，页面获取
     */
    private String payPointValue;

    private Map<Object, String> leftToStartMap = new HashMap<Object, String>();

    /**
     * 查询预售上架商品列表 进入预售首页面
     *
     * @return
     */
    public String index()
    {
        serverTime = goodsPreSellRackActionSupport.getServerDate();
        // 查询预售商品列表
        if (pager == null)
        {
            pager = new GoodsPresellRack();
        }

        if (Assert.empty(pager.getState()))
        {
            pager.setState(Command.STATE_START);
        }
        pager.setPageSize(5);// 每页5条
        pager.setIsRack("1");
        pager.setOrderBy(" priority ");
        pager.setOrderType(com.froad.client.goodsPresellRack.OrderType.DESC);
        pager = goodsPreSellRackActionSupport.findGoodsPreSellByPager(pager);

        // 计算预售剩余时间
        calculateSellLeftTimeAndDeliveryState(pager.getList());
        return "success";
    }

    /**
     * 精品预售商品详细页
     *
     * @return
     */
    public String presellGoodsDetail()
    {
        serverTime = goodsPreSellRackActionSupport.getServerDate();
        // 查询上架团购商品的详细信息
        if (goodsPresellRack == null)
        {
            goodsPresellRack = new GoodsPresellRack();
        }
        if (Assert.empty(goodsPresellRack.getId()))
        {
            return "error";
        }
        goodsPresellRack = goodsPreSellRackActionSupport.getGoodsPresellRackById(goodsPresellRack.getId());

        if (goodsPresellRack != null && !Assert.empty(goodsPresellRack.getBeginTime()) && !Assert.empty(goodsPresellRack.getEndTime()))
        {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            startFalg = "0";
            try
            {
                Date begineTime = df.parse(goodsPresellRack.getBeginTime());
                Date endTime = df.parse(goodsPresellRack.getEndTime());

                if (begineTime.before(serverTime) && serverTime.before(endTime))
                {
                    startFalg = "1";
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        // 商户地图
        merchantTrafficMAP = new MerchantTrafficMAP();
        merchantTrafficMAP = merchantTrafficMAPActionSupport.getMerchantTrafficMapByMerchantId(goodsPresellRack.getGoods().getMerchantId());

        // ===============================================新版商户展示 分页商家

        // 自提点信息查询
        deliveryList = preSellActionSupport.getGoodsDeliverysByRackId(goodsPresellRack.getId());
        // 计算预售剩余时间
        calculateSellLeftTimeAndDeliveryState(goodsPresellRack);

        return "success";
    }

    /**
     * 精品预售首页
     *
     * @return 空
     */
    public String preSell()
    {
        User user = getLoginUser();
        if (user == null || Assert.empty(user.getUserID()))
        {
            logger.info("用户没有登陆");
            return "login_page";
        }
        Object merchant = getSession(SessionKey.MERCHANT);
        if (merchant != null)
        {
            logger.info("商户不能购买精品预售商品");
            errorMsg = "商户不能购买精品预售商品";
            return "failse";
        }
        if (tempTran == null || tempTran.getGoodsRackId() == null)
        {
            logger.info("goodsRackId参数为空 ");
            errorMsg = "该页面已过期。";
            return "failse";
        }
        pager = goodsPreSellRackActionSupport.getGoodsPresellRackById(Integer.parseInt(tempTran.getGoodsRackId()));

        // 判断商品信息
        if (pager == null)
        {
            logger.info("没有查到商品 id: " + tempTran.getGoodsRackId());
            errorMsg = "暂时没有该商品，敬请等待";
            return "failse";
        }

        // 判断是否下架
        if (Assert.empty(pager.getIsRack()) || !"1".equals(pager.getIsRack()))
        {
            logger.info("商品已经下架！id: " + tempTran.getGoodsRackId());
            errorMsg = "此商品已经下架!";
            return "failse";
        }

        // 查询分分通，银行卡积分
        Map<String, PointsAccount> pointsTypePointsAccountMap = pointActionSupport.queryPointsByUsername(user.getUsername());

        if (!Assert.empty(pointsTypePointsAccountMap))
        {
            PointsAccount pointaccount = pointsTypePointsAccountMap.get("FFTPlatform");
            fftPoint = pointaccount == null ? "0" : pointaccount.getPoints();
            PointsAccount pointaccountBank = pointsTypePointsAccountMap.get("ZHBank");
            bankPoint = pointaccountBank == null ? "0" : pointaccountBank.getPoints();
        }
        else
        {
            logger.info("************进入精品预售抢购下单页面，积分查询失败***********! username:" + user.getUsername());
            fftPoint = "0";
            bankPoint = "0";
        }
        // 计算两种最大最小积分值范围以及支持积分团购的积分值
        fftAndBankPointInfo(pager);

        // 获取商品的提货点信息
        deliveryList = preSellActionSupport.getGoodsDeliverysByRackId(pager.getId());

        return "success";
    }

    /**
     * 组装交易信息并创建交易
     *
     * @return
     */
    public String order()
    {
        serverTime = goodsPreSellRackActionSupport.getServerDate();
        User user = getLoginUser();
        if (user == null || Assert.empty(user.getUserID()))
        {
            logger.info("用户没有登陆");
            return "login_page";
        }
        Object merchant = getSession(SessionKey.MERCHANT);
        if (merchant != null)
        {
            logger.info("商户暂时不能预购精品商品");
            errorMsg = "商户暂时不能预购精品商品";
            return "failse";
        }
        if (tempTran == null || tempTran.getGoodsRackId() == null)
        {
            errorMsg = "该页面已过期。";
            return "failse";// 返回
        }
        // 获取交易品信息
        goodsPresellRack = goodsPreSellRackActionSupport.getGoodsPresellRackById(Integer.valueOf(tempTran.getGoodsRackId()));
        if (goodsPresellRack == null)
        {
            logger.info("预售交易商品不存在");
            errorMsg = "预售商品不存在！";
            return "failse";// 返回
        }
        Integer goodsNumber = null;
        try
        {
            goodsNumber = Integer.parseInt(tempTran.getGoodsNumber());
        }
        catch (Exception e)
        {
            logger.error("商品数量只能为整数", e);
            errorMsg = "商品数量只能为整数！";
            return "failse";
        }
        Integer maxNum = goodsPresellRack.getMaxSaleNum();
        if (maxNum > 0)
        {//有最大成团数限制
            Integer restNum = maxNum - goodsPresellRack.getTrueBuyerNum();
            if (goodsNumber > restNum)
            {
                logger.error("购买数量超出最大可购买数量！");
                errorMsg = "购买数量超出最大可购买数量！";
                return "failse";
            }
        }

        // 判断商品是否在预售期限内
        if (!Assert.empty(goodsPresellRack.getBeginTime()) && !Assert.empty(goodsPresellRack.getEndTime()))
        {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //修改为服务端时间
            String now = df.format(serverTime);
            int startNum = goodsPresellRack.getBeginTime().compareTo(now);
            int endNum = now.compareTo(goodsPresellRack.getEndTime());
            if (startNum > 0 || endNum > 0)
            {// 判断是否超过时间 过期：startNum = 1; 未过期
                // startNum = 0、-1
                // 没有到团购时间 或者 超过团购时间
                logger.info("交易商品不在预售的期限内  id:" + goodsPresellRack.getId());
                payResult = "error";
                errorMsg = "交易商品不在预售的期限内";
                return "failse";// 返回
            }
        }
        // 判断是否下架
        if ((Assert.empty(goodsPresellRack.getIsRack()) || !"1".equals(goodsPresellRack.getIsRack())))
        {
            logger.info("商品已经下架！id: " + goodsPresellRack.getId());
            errorMsg = "此商品已经下架!";
            return "failse";// 返回
        }

        logger.info("---------------交易信息组装开始---------------");

        tempTran.setUserId(user.getUserID());
        // 交易类型设定为精品预售
        tempTran.setTransType(TranCommand.PRE_SELL);
        tempTran.setPayChannel(TranCommand.PAY_CHANNEL_PHONE);

        // 转换支付方式
        // 积分情况
        // 先默认为分分通
        String payType = TranCommand.POINTS_FFT;
        if (StringUtils.isNotBlank(payPointType))
        {
            // 分分通+？
            if ("1".equals(payPointType))
            {
                // 分分通加现金方式
                if (StringUtils.isNotBlank(payCashType))
                {
                    //设置交易的分分通积分值
                    tempTran.setFftPointsValueRealAll(payPointValue);
                    if ("1".equals(payCashType))
                    {
                        payType = TranCommand.POINTS_FFT_CASH_SCOPE;
                    }
                    else if ("2".equals(payCashType))
                    {
                        payType = TranCommand.POINTS_FFT_ALPAY_SCOPE;
                    }
                    else
                    {
                        logger.info("错误的现金支付类型！payCashType: " + payCashType);
                        errorMsg = "错误的现金支付类型!";
                        return "failse";// 返回
                    }
                }
            }
            else if ("2".equals(payPointType))
            {
                payType = TranCommand.POINTS_BANK;
                // 银行积分通加现金方式
                if (StringUtils.isNotBlank(payCashType))
                {
                    //设置交易的银行积分值
                    tempTran.setBankPointsValueRealAll(payPointValue);
                    if ("1".equals(payCashType))
                    {
                        payType = TranCommand.POINTS_BANK_CASH_SCOPE;
                    }
                    else if ("2".equals(payCashType))
                    {
                        payType = TranCommand.POINTS_BANK_ALPAY_SCOPE;
                    }
                    else
                    {
                        logger.info("错误的现金支付类型！payCashType: " + payCashType);
                        errorMsg = "错误的现金支付类型!";
                        return "failse";// 返回
                    }
                }
            }
            else
            {
                logger.info("错误的积分支付类型！payCashType: " + payCashType);
                errorMsg = "错误的积分支付类型!";
                return "failse";// 返回
            }
        }
        // 纯现金方式
        else
        {
            payType = TranCommand.CASH;
            if (StringUtils.isNotBlank(payCashType))
            {
                if ("1".equals(payCashType))
                {
                    payType = TranCommand.CASH;
                }
                else if ("2".equals(payCashType))
                {
                    payType = TranCommand.ALPAY;
                }
                else
                {
                    logger.info("错误的现金支付类型！payCashType: " + payCashType);
                    errorMsg = "错误的现金支付类型!";
                    return "failse";// 返回
                }
            }
            else
            {
                logger.info("未选择支付类型！ ");
                errorMsg = "未选择支付类型!";
                return "failse";// 返回
            }

        }
        // 重新初始化支付方式
        tempTran.setPayMethod(payType);

        // 通过团购价,购买数量,商品定价,积分等信息确定交易相关价格
        TempTrans ttp = tempTran;
        try
        {
            tempTran = getTranPrice(tempTran, goodsPresellRack);
            //清洗0数据的组合支付方式
            tempTran = clearPayMethod(tempTran);
        }
        catch (Exception e)
        {
            logger.error("计算价格出错", e);
            logger.info("*****************计算价格异常，输入的积分或者价格不在商品的定价范围内！*********************");
            tempTran = ttp;
            errorMsg = "您输入的积分或价格不在商品定价范围内";
            return "failse";
        }

        // 获取买家编号和支付渠道
        String payMethod = tempTran.getPayMethod();
        if (TranCommand.CASH.equals(payMethod) || TranCommand.POINTS_FFT_CASH_SCOPE.equals(payMethod) ||
                TranCommand.POINTS_BANK_CASH_SCOPE.equals(payMethod))
        {
            Buyers buyer = buyersActionSupport.getBuyerByUserId(user.getUserID());
            if (buyer == null)
            {
                logger.info("不存在买家支付信息，确认是否是手机银行卡用户!");
                errorMsg = "无法完成支付，请确认您是否为珠海农商行手机银行卡用户！";
                return "failse";// 返回
            }
            if (buyer.getBuyerChannelList() == null || buyer.getBuyerChannelList().size() == 0)
            {
                logger.info("不存在买家支付信息，确认是否是手机银行卡用户!");
                errorMsg = "无法完成支付，请确认您是否为珠海农商行手机银行卡用户！";
                return "failse";// 返回
            }
            tempTran.setBuyersId(String.valueOf(buyer.getId()));
            BuyerChannel bc = buyer.getBuyerChannelList().get(0);
            tempTran.setBuyerChannelId(String.valueOf(bc.getId()));
        }
        logger.info("买家信息查询完成");

        // 获取卖家信息和收款资金渠道
        com.froad.client.sellers.Seller seller = null;
        if (goodsPresellRack != null && !Assert.empty(goodsPresellRack.getSellerId()))
        {
            tempTran.setSellerId(String.valueOf(goodsPresellRack.getSellerId()));
            tempTran.setMerchantId(Integer.valueOf(goodsPresellRack.getMerchantId()));
            seller = sellersActionSupport.getSellerById(String.valueOf(goodsPresellRack.getSellerId()));
            logger.info("卖家信息查询完成");
        }

        if (seller != null && seller.getSellerChannelList() != null && seller.getSellerChannelList().size() > 0)
        {
            com.froad.client.sellers.SellerChannel sellerChannel = seller.getSellerChannelList().get(0);
            tempTran.setSellerChannelId(String.valueOf(sellerChannel.getId()));
            tempTran.setSellerRuleId(sellerChannel.getSellerRuleId());
        }
        else
        {
            logger.info("不存在卖家信息");
            // errorMsg = "不存在卖家信息";
            errorMsg = "无法完成支付，未查询到商品相关卖家信息，请确认您是否为珠海农商银行手机银行卡用户！";
            return "failse";// 返回
        }


        logger.info("-------------交易详情信息组装开始----------------");
        TransDetails tranDetail = makeCarryTransDetails(tempTran, goodsPresellRack);
        logger.info("---------------交易详情信息组装结束---------------");
        tempTran.setPhone(user.getMobilephone());
        trans = makeTrans(tempTran);
        trans.getTransDetailsList().add(tranDetail);
        logger.info("---------------交易信息组装结束---------------");

        // 组装pay并设置到trans里
        List<Pay> payList = payActionSupport.makePay(trans);
        if (payList != null)
        {
            logger.info("-----------------生成paylist成功-------------------");
            trans.getPayList().addAll(payList);
            trans = transActionSupport.addTrans(trans);
            if (trans == null)
            {
                logger.info("----------------交易添加失败----------------");
                errorMsg = "交易添加失败";
                return "failse";
            }
            else
            {
                logger.info("------------------交易添加成功---------------");
            }
        }
        else
        {
            logger.info("-------------生成paylist失败---------------------");
            logger.info("您不是手机贴膜卡用户，请到珠海农商银行办理。");
            // errorMsg = "您不是手机贴膜卡用户，请到珠海农商银行办理。";
            errorMsg = "无法完成支付，请确认您是否为珠海农商银行手机银行卡用户！";
            return "failse";
        }

        // 读取提货点信息
        // 保存预售交易信息
        PresellBuyInfo presellBuyInfo = new PresellBuyInfo();
        presellBuyInfo.setTransId(trans.getId());
        presellBuyInfo.setPresellDeliveryId(delivery.getId());
        presellBuyInfo.setMemberCode(user.getUserID());
        presellBuyInfo.setGoodsPresellRackId(Integer.parseInt(tempTran.getGoodsRackId()));
        presellBuyInfo.setTotalGoodsNum(tempTran.getGoodsNumber());

        Integer presellBuyInfoId = preSellActionSupport.addPresellBuyInfo(presellBuyInfo);
        if (null == presellBuyInfoId)
        {
            logger.info("交易失败，保存精品预购信息不成功。");
            // errorMsg = "您不是手机贴膜卡用户，请到珠海农商银行办理。";
            errorMsg = "无法完成支付，保存精品预购信息不成功！";
            return "failse";
        }

        // 保存预售销售信息
        delivery = preSellActionSupport.getPresellDeliveryById(delivery.getId());

        // 返回到下一页的商品名展示
        if (pager == null)
        {
            pager = new GoodsPresellRack();
        }
        return "success";
    }

    /**
     * 确认支付
     *
     * @return
     */
    public String pay()
    {
        User user = getLoginUser();

        if (user == null || Assert.empty(user.getUserID()))
        {
            logger.info("没有登陆");
            return "login_page";
        }

        Object merchant = getSession(SessionKey.MERCHANT);
        if (merchant != null)
        {// 商户暂时不能团购商品
            logger.info("商户暂时不能预售商品");
            payResult = "error";
            errorMsg = "商户暂时不能预售商品";
            return Action.SUCCESS;
        }

        if (trans == null || trans.getId() == null)
        {
            logger.info("页面已过期");
            payResult = "error";
            errorMsg = "页面已过期";
            return Action.SUCCESS;
        }
        trans = transActionSupport.getTransById(trans.getId());
        if (trans == null)
        {
            logger.info("不存在交易");
            payResult = "error";
            errorMsg = "交易不存在";

            return Action.SUCCESS;
        }

        Result result = transActionSupport.pay(trans);
        log.info("交易ID为：" + trans.getId() + ",支付，调用webserver去支付的结果为：" + result.getMsg());
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("changedPoints", true);
        if (Result.SUCCESS.equals(result.getCode()))
        {
            logger.info("支付请求发送成功");

            String payMethod = trans.getPayMethod();
            if (TranCommand.POINTS_FFT.equals(payMethod) || TranCommand.POINTS_BANK.equals(payMethod))
            {
                payResult = "success";
            }
            else if (TranCommand.ALPAY.equals(payMethod) ||
                    TranCommand.POINTS_FFT_ALPAY_SCOPE.equals(payMethod) || TranCommand.POINTS_BANK_ALPAY_SCOPE.equals(payMethod))
            {
                this.paymentUrl = result.getMsg();
                return "alipay";
            }
            else
            {
                payResult = "waiting";
            }
        }
        else
        {
            logger.info("支付失败，原因： " + result.getMsg());
            payResult = "fail";
            errorMsg = "支付失败！";
            if (result.getMsg().startsWith(TranCommand.EXCEPTION_PREFIX))
            {
                errorMsg = result.getMsg();
            }
        }
        return Action.SUCCESS;
    }

    /**
     * 支付宝支付结果检查
     *
     * @return
     */
    public String aliPayCheck()
    {
        User user = getLoginUser();
        if (user == null || Assert.empty(user.getUserID()))
        {
            logger.info("没有登陆");
            return "login_page";
        }

        if (trans == null || trans.getId() == null)
        {
            logger.info("======交易查询参数为空=====");
            payResult = "error";
            errorMsg = "页面已过期";
            return Action.SUCCESS;
        }
        logger.info("查询支付结果，交易ID：" + trans.getId());
        String aliPayCheckResult = transActionSupport.aliPayResultCheck(String.valueOf(trans.getId()));

        //        aliPayCheckResult = true;
        if (PayState.PAY_SUCCESS.equals(aliPayCheckResult))
        {
            return Action.SUCCESS;
        }
        else if (PayState.PAY_REQUEST_SUCCESS.equals(aliPayCheckResult))
        {
            return "wait";
        }
        else
        {
            errorMsg = "支付失败！";
            return "failse";
        }
    }

    /**
     * 方法描述：异步查询交易结果
     *
     * @param: transId
     * @return: void
     * @version: 1.0
     * @author: 侯国权 lijinkui@f-road.com.cn
     * @time: Apr 17, 2014 2:01:38 PM
     */
    public void queryTransResultByAjax()
    {
        String result = "";
        logger.info("开始异步查询交易结果，交易号为：" + trans.getId());
        if (trans.getId() == null)
        {
            sendMsg("notExist");
            return;
        }
        logger.info("查询支付结果，交易ID：" + trans.getId());
        String aliPayCheckResult = transActionSupport.aliPayResultCheck(String.valueOf(trans.getId()));

        if (PayState.PAY_SUCCESS.equals(aliPayCheckResult))
        {
            result = Action.SUCCESS;
        }
        else if (PayState.PAY_REQUEST_SUCCESS.equals(aliPayCheckResult))
        {
            result = "doing";
        }
        else
        {
            errorMsg = "支付失败！";
            result = "failse";
        }

        logger.info("结束异步查询交易结果，交易号为：" + trans.getId() + "结果为：" + result);
        sendMsg(result);
    }

    /**
     * 根据交易品信息，购买数量，交易品定价，启用哪种定价模式，积分数计算出结果给下面几种价格和积分赋值(不同交易需要设置的值不同) <p/>
     * 1:trans.setCostpriceTotal(); 原价总和 2:trans.setCurrencyValueAll();
     * 交易价格(买家所付现金额度，不包括手续费) 3:trans.setCurrencyValueRealAll(); 买家实际支付的现金额
     * 4:trans.setFftPointsValueRealAll(); 分分通积分数
     * 5:trans.setBankPointsValueRealAll(); 银行积分数 <p/> 每笔交易分分通积分和银行积分不同时存在
     *
     * @param tempTran
     * @param goods
     * @return TempTrans
     * @throws Exception
     */
    public TempTrans getTranPrice(TempTrans tempTran, GoodsPresellRack goods) throws Exception
    {
        logger.info("-------------计算交易积分额和价格信息开始---------------------");
        if (tempTran != null && !Assert.empty(tempTran.getGoodsNumber()) && !Assert.empty(goods.getCashPricing()))
        {
            BigDecimal currencyDindjia = new BigDecimal(goods.getCashPricing());
            BigDecimal goodsNumTotal = new BigDecimal(tempTran.getGoodsNumber());
            currencyDindjia = currencyDindjia.multiply(goodsNumTotal).setScale(2, BigDecimal.ROUND_DOWN);
            tempTran.setCostpriceTotal(String.valueOf(currencyDindjia));
            tempTran.setCurrencyValueAll(String.valueOf(currencyDindjia));
        }
        if (!Assert.empty(tempTran.getPayMethod()) && TranCommand.POINTS_FFT.equals(tempTran.getPayMethod()))
        {
            logger.info("-------------分分通积分计算开始" + "payMethod:" + tempTran.getPayMethod() + "---------------------");
            // 分分通积分支付

            tempTran.setCurrency("");
            // tempTran.setCostpriceTotal("0");
            // tempTran.setCurrencyValueAll("0");
            tempTran.setCurrencyValueRealAll("0");
            tempTran.setFftPointsValueRealAll("0");
            tempTran.setFftPointsValueAll("0");
            tempTran.setBankPointsValueRealAll("0");
            tempTran.setBankPointsValueAll("0");

            //定价检查取消
            //			if (!Command.price_availiable.equals(goods.getIsFftPoint())) {
            //				throw new Exception("交易品定价有误,请检查交易品id:" + goods.getId() + "的定价");
            //			}

            BigDecimal fftPoints = new BigDecimal(goods.getFftPointPricing());
            BigDecimal goodsNum = new BigDecimal(tempTran.getGoodsNumber());
            fftPoints = fftPoints.multiply(goodsNum).setScale(2, BigDecimal.ROUND_DOWN);
            tempTran.setFftPointsValueAll(String.valueOf(fftPoints));
            tempTran.setFftPointsValueRealAll(String.valueOf(fftPoints));
            logger.info("-------------分分通积分计算结束" + "fftPoint:" + String.valueOf(fftPoints) + "---------------------");
        }
        else if (!Assert.empty(tempTran.getPayMethod()) && TranCommand.POINTS_BANK.equals(tempTran.getPayMethod()))
        {
            // 银行积分支付
            logger.info("-------------银行积分计算开始" + "payMethod:" + tempTran.getPayMethod() + "---------------------");
            tempTran.setCurrency("");
            // tempTran.setCostpriceTotal("0");
            // tempTran.setCurrencyValueAll("0");
            tempTran.setCurrencyValueRealAll("0");
            tempTran.setFftPointsValueRealAll("0");
            tempTran.setFftPointsValueAll("0");
            tempTran.setBankPointsValueRealAll("0");
            tempTran.setBankPointsValueAll("0");

            //定价检查取消
            //			if (!Command.price_availiable.equals(goods.getIsBankPoint())) {
            //				throw new Exception("交易品定价有误,请检查交易品id:" + goods.getId() + "的定价");
            //			}

            BigDecimal bankPoints = new BigDecimal(goods.getBankPointPricing());
            BigDecimal goodsNum = new BigDecimal(tempTran.getGoodsNumber());
            bankPoints = bankPoints.multiply(goodsNum).setScale(2, BigDecimal.ROUND_DOWN);
            tempTran.setBankPointsValueAll(String.valueOf(bankPoints));
            tempTran.setBankPointsValueRealAll(String.valueOf(bankPoints));
            logger.info("-------------银行积分计算结束" + "bankPoint:" + String.valueOf(bankPoints) + "---------------------");
        }
        else if (!Assert.empty(tempTran.getPayMethod()) && (TranCommand.CASH.equals(tempTran.getPayMethod()) || TranCommand.ALPAY.equals(tempTran.getPayMethod())))
        {
            // 现金支付或者ali支付
            logger.info("-------------现金计算开始" + "payMethod:" + tempTran.getPayMethod() + "---------------------");
            // tempTran.setCostpriceTotal("0");
            // tempTran.setCurrencyValueAll("0");
            tempTran.setCurrencyValueRealAll("0");
            tempTran.setFftPointsValueRealAll("0");
            tempTran.setFftPointsValueAll("0");
            tempTran.setBankPointsValueRealAll("0");
            tempTran.setBankPointsValueAll("0");
            //定价检查取消
            //			if (!Command.price_availiable.equals(goods.getIsCash())) {
            //				throw new Exception("交易品定价有误,请检查交易品id:" + goods.getId() + "的定价");
            //			}

            BigDecimal currency = new BigDecimal(goods.getCashPricing());
            BigDecimal goodsNum = new BigDecimal(tempTran.getGoodsNumber());
            currency = currency.multiply(goodsNum).setScale(2, BigDecimal.ROUND_DOWN);
            // tempTran.setCostpriceTotal(String.valueOf(currency));
            // tempTran.setCurrencyValueAll(String.valueOf(currency));
            tempTran.setCurrencyValueRealAll(String.valueOf(currency));
            logger.info("-------------现金计算结束" + "现金:" + String.valueOf(currency) + "元。---------------------");

        }
        else if (!Assert.empty(tempTran.getPayMethod()) && (TranCommand.POINTS_FFT_CASH_SCOPE.equals(tempTran.getPayMethod()) || TranCommand.POINTS_FFT_ALPAY_SCOPE.equals(tempTran.getPayMethod())))
        {
            // 分分通积分+现金 范围
            logger.info("-------------分分通积分+现金(包含Alpay)比率计算开始" + "payMethod:" + tempTran.getPayMethod() + "---------------------");

            if (Assert.empty(tempTran.getFftPointsValueRealAll())) tempTran.setFftPointsValueRealAll("0");


            BigDecimal ratio = new BigDecimal("0.01");// 百分比
            BigDecimal goodsNum = new BigDecimal(tempTran.getGoodsNumber());// 购买商品数量
            BigDecimal cashPring = new BigDecimal(goods.getCashPricing());// 团购定价
            BigDecimal groupPring = new BigDecimal(goods.getGroupPrice());// 团购价

            BigDecimal fftPointsMin = new BigDecimal(0.0);// 配置的积分最小百分数
            BigDecimal fftPointsMinValue = fftPointsMin.multiply(ratio).multiply(cashPring).multiply(goodsNum).setScale(2, BigDecimal.ROUND_DOWN);// 最小使用分分通积分数(保留两位小数)

            BigDecimal fftPointsMax = new BigDecimal(100.0);// 配置的积分最大百分数
            BigDecimal fftPointsMaxValue = fftPointsMax.multiply(ratio).multiply(cashPring).multiply(goodsNum).setScale(2, BigDecimal.ROUND_DOWN);// 最大使用分分通积分数(保留两位小数)

            BigDecimal fftPoints = new BigDecimal(tempTran.getFftPointsValueRealAll() == null ? "0" : tempTran.getFftPointsValueRealAll());

            if (fftPoints.compareTo(fftPointsMaxValue) > 0)
            {
                throw new Exception("使用的积分不在定价的积分范围内");
            }

            if (fftPoints.compareTo(fftPointsMinValue) < 0)
            {
                throw new Exception("使用的积分不在定价的积分范围内");
            }

            String radioValue = fftCashPointRio();// 获取现金分分通积分比率
            BigDecimal fftRio = new BigDecimal(radioValue);
            BigDecimal fftPointsRealPrice = null;
            fftPointsRealPrice = fftPoints.multiply(fftRio);// 根据积分现金比率算出积分折合的现金额
            BigDecimal currency = cashPring.multiply(goodsNum).subtract(fftPointsRealPrice).setScale(2, BigDecimal.ROUND_DOWN);// 出去积分部分还需支付现金(单品定价
            // X 数量
            // -
            // 积分折合现金
            // =
            // 支付现金)
            tempTran.setFftPointsValueAll(String.valueOf(fftPoints));
            tempTran.setFftPointsValueRealAll(String.valueOf(fftPoints));
            tempTran.setCurrencyValueRealAll(String.valueOf(currency));
            tempTran.setBankPointsValueRealAll("0");
            tempTran.setBankPointsValueAll("0");
            logger.info("-------------分分通积分+现金比率计算结束" + "fftPoints:" + String.valueOf(fftPoints) + "   现金:" + String.valueOf(currency) + "元。---------------------");
        }
        else if (!Assert.empty(tempTran.getPayMethod()) && (TranCommand.POINTS_BANK_CASH_SCOPE.equals(tempTran.getPayMethod())) || TranCommand.POINTS_BANK_ALPAY_SCOPE.equals(tempTran.getPayMethod()))
        {
            // 银行积分+现金 范围
            logger.info("-------------银行积分+现金(包含Alpay)比率计算开始" + "payMethod:" + tempTran.getPayMethod() + "---------------------");
            if (Assert.empty(tempTran.getBankPointsValueRealAll())) tempTran.setBankPointsValueRealAll("0");
            BigDecimal ratio = new BigDecimal("0.01");// 百分比
            BigDecimal goodsNum = new BigDecimal(tempTran.getGoodsNumber());// 购买商品数量
            BigDecimal cashPring = new BigDecimal(goods.getCashPricing());// 团购定价(团购中不管现金定价是否启用，此值都部位空。换算比率用这个值作为计算参数)
            BigDecimal groupPring = new BigDecimal(goods.getGroupPrice());// 团购价

            BigDecimal bankPointsMin = new BigDecimal(0.0);// 配置的积分最小百分数
            BigDecimal bankPointsMinValue = bankPointsMin.multiply(ratio).multiply(cashPring).multiply(goodsNum).setScale(2, BigDecimal.ROUND_DOWN);// 最小使用分分通积分数(保留两位小数)

            BigDecimal bankPointsMax = new BigDecimal(100.0);// 配置的积分最大百分数
            BigDecimal bankPointsMaxValue = bankPointsMax.multiply(ratio).multiply(cashPring).multiply(goodsNum).setScale(2, BigDecimal.ROUND_DOWN);// 最大使用分分通积分数(保留两位小数)

            BigDecimal bankPoints = new BigDecimal(tempTran.getBankPointsValueRealAll() == null ? "0" : tempTran.getBankPointsValueRealAll());

            if (bankPoints.compareTo(bankPointsMaxValue) > 0)
            {
                throw new Exception("使用的积分不在定价的积分范围内");
            }

            if (bankPoints.compareTo(bankPointsMinValue) < 0)
            {
                throw new Exception("使用的积分不在定价的积分范围内");
            }

            String radioValue = bankCashPointRio();// 获取现金银行积分比率
            BigDecimal bankRio = new BigDecimal(radioValue);
            BigDecimal bankPointsRealPrice = null;
            bankPointsRealPrice = bankPoints.multiply(bankRio);// 根据积分现金比率算出积分折合的现金额
            BigDecimal currency = cashPring.multiply(goodsNum).subtract(bankPointsRealPrice).setScale(2, BigDecimal.ROUND_DOWN);// 出去积分部分还需支付现金(单品定价
            // X 数量
            // -
            // 积分折合现金
            // =
            // 支付现金)
            tempTran.setFftPointsValueAll("0");
            tempTran.setFftPointsValueRealAll("0");

            tempTran.setCurrencyValueRealAll(String.valueOf(currency));
            tempTran.setBankPointsValueRealAll(String.valueOf(bankPoints));
            tempTran.setBankPointsValueAll(String.valueOf(bankPoints));
            logger.info("-------------银行积分+现金比率计算结束" + "bankPoints:" + String.valueOf(bankPoints) + "   现金:" + String.valueOf(currency) + "元。---------------------");
        }
        return tempTran;
    }

    // 交易详情的java方法模板
    public TransDetails makeCarryTransDetails(TempTrans tempTran, GoodsPresellRack goodsPresellRack)
    {
        TransDetails details = new TransDetails();
        Goods good = new Goods();
        if (!Assert.empty(tempTran.getGoodsName()))
        {
            good.setGoodsName(tempTran.getGoodsName());
        }
        else if (goodsPresellRack.getGoods() != null)
        {
            good.setGoodsName(goodsPresellRack.getGoods().getGoodsName());
        }
        // TODO 待设值
        details.setGoodsRackId(tempTran.getGoodsRackId());
        details.setGoodsNumber(tempTran.getGoodsNumber());
        details.setGoods(good);
        details.setSellerRuleId(tempTran.getSellerRuleId());
        details.setCurrency("RMB");
        details.setCostpriceTotal(tempTran.getCostpriceTotal());
        details.setCurrencyValue(tempTran.getCurrencyValueAll());
        details.setCurrencyValueReal(tempTran.getCurrencyValueRealAll());
        details.setBankPointsValueAll(tempTran.getBankPointsValueAll());
        details.setBankPointsValueRealAll(tempTran.getBankPointsValueRealAll());
        details.setFftPointsValueAll(tempTran.getFftPointsValueAll());
        details.setFftPointsValueRealAll(tempTran.getFftPointsValueRealAll());
        details.setState(Command.STATE_START);
        return details;
    }

    /**
     * 组织交易的模板方法：这个方法的入参自定*
     */
    public Trans makeTrans(TempTrans tempTran)
    {
        Trans trans = new Trans();
        trans.setPhone(tempTran.getPhone());
        trans.setTransType(tempTran.getTransType());// 必填
        trans.setSellerId(tempTran.getSellerId());// 必填
        trans.setSellerChannelId(tempTran.getSellerChannelId());// 必填
        trans.setCurrency("RMB");// 包含现金的时候需要设置为“RMB”,否则不设值
        trans.setCostpriceTotal(tempTran.getCostpriceTotal());// 必填
        trans.setCurrencyValueAll(tempTran.getCurrencyValueAll());// 必填
        trans.setCurrencyValueRealAll(tempTran.getCurrencyValueRealAll());// 必填
        trans.setState(TranCommand.TRAN_PROCESSING);// 必填
        trans.setClientType(tempTran.getClientType());// 必填
        trans.setPayChannel(tempTran.getPayChannel());// 必填
        trans.setPayMethod(tempTran.getPayMethod());// 必填
        // TODO 待加入特定交易的必填字段

        trans.setMerchantId(tempTran.getMerchantId());
        trans.setUserId(tempTran.getUserId());
        trans.setBuyersId(tempTran.getBuyersId());
        trans.setBuyerChannelId(tempTran.getBuyerChannelId());
        trans.setFftPointsValueRealAll(tempTran.getFftPointsValueRealAll());
        trans.setFftPointsValueAll(tempTran.getFftPointsValueAll());
        trans.setBankPointsValueRealAll(tempTran.getBankPointsValueRealAll());
        trans.setBankPointsValueAll(tempTran.getBankPointsValueAll());
        return trans;
    }

    /**
     * 现金积分比例以及商品使用积分最大最小值
     *
     * @param groupRack
     */
    public void fftAndBankPointInfo(GoodsPresellRack presellRack)
    {
        cashFftPointsRatio = fftCashPointRio();// 获取现金分分通积分比率;//cash/fftPoint
        cashBankPointsRatio = bankCashPointRio();// 获取现金银行积分比率;//cash/bankPoint
        if (!Assert.empty(presellRack.getIsFftpointcashRatioPricing()) && "1".equals(presellRack.getIsFftpointcashRatioPricing()))
        {
            String fftpointsMinMax = presellRack.getFftpointcashRatioPricing();
            fftpointsMinMax = fftpointsMinMax.replace("|", ",");
            String[] fftpointsMinMaxArr = fftpointsMinMax.split(",");

            BigDecimal ratio = new BigDecimal("0.01");// 百分比
            BigDecimal cashPring = new BigDecimal(presellRack.getCashPricing());// 团购定价
            BigDecimal cashPricePoints = cashPring.divide(new BigDecimal(cashFftPointsRatio), 2, RoundingMode.DOWN);// 根据定价和现金积分比率算出相应的积分

            BigDecimal fftPointsMin = new BigDecimal(fftpointsMinMaxArr[0]);// 配置的积分最小百分数
            BigDecimal fftPointsMinValue = fftPointsMin.multiply(ratio).multiply(cashPricePoints).setScale(2, BigDecimal.ROUND_DOWN);// 最小使用分分通积分数(保留两位小数)

            fftPointValueMin = String.valueOf(fftPointsMinValue);
            logger.info("-------------分分通积分现金比率定价，单个商品fft积分使用最小值:" + fftPointValueMin + "---------------------");

            BigDecimal fftPointsMax = new BigDecimal(fftpointsMinMaxArr[1]);// 配置的积分最大百分数
            BigDecimal fftPointsMaxValue = fftPointsMax.multiply(ratio).multiply(cashPricePoints).setScale(2, BigDecimal.ROUND_DOWN);// 最小使用分分通积分数(保留两位小数)

            fftPointValueMax = String.valueOf(fftPointsMaxValue);
            logger.info("-------------分分通积分现金比率定价，单个商品fft积分使用最大值:" + fftPointValueMax + "---------------------");
        }
        if (!Assert.empty(presellRack.getIsBankpointcashRatioPricing()) && "1".equals(presellRack.getIsBankpointcashRatioPricing()))
        {
            String bankpointsMinMax = presellRack.getBankpointcashRatioPricing();
            bankpointsMinMax = bankpointsMinMax.replace("|", ",");
            String[] bankpointsMinMaxArr = bankpointsMinMax.split(",");

            BigDecimal ratio = new BigDecimal("0.01");// 百分比
            BigDecimal cashPring = new BigDecimal(presellRack.getCashPricing());// 团购定价

            BigDecimal cashPricePoints = cashPring.divide(new BigDecimal(cashBankPointsRatio), 2, RoundingMode.DOWN);// 根据定价和现金积分比率算出相应的积分

            BigDecimal bankPointsMin = new BigDecimal(bankpointsMinMaxArr[0]);// 配置的积分最小百分数
            BigDecimal bankPointsMinValue = bankPointsMin.multiply(ratio).multiply(cashPricePoints).setScale(2, BigDecimal.ROUND_DOWN);// 最小使用银行积分数(保留两位小数)

            bankPointValueMin = String.valueOf(bankPointsMinValue);
            logger.info("-------------银行积分现金比率定价，单个商品bank积分使用最小值:" + bankPointValueMin + "---------------------");

            BigDecimal bankPointsMax = new BigDecimal(bankpointsMinMaxArr[1]);// 配置的积分最大百分数
            BigDecimal bankPointsMaxValue = bankPointsMax.multiply(ratio).multiply(cashPricePoints).setScale(2, BigDecimal.ROUND_DOWN);// 最小使用银行积分数(保留两位小数)

            bankPointValueMax = String.valueOf(bankPointsMaxValue);
            logger.info("-------------银行积分现金比率定价，单个商品bank积分使用最大值:" + bankPointValueMax + "---------------------");
        }
        if (!Assert.empty(presellRack.getIsCash()) && "1".equals(presellRack.getIsCash()))
        {
            cash = presellRack.getCashPricing();
            logger.info("-------------现金定价，单个商品现金:" + cash + "---------------------");
        }
        if (!Assert.empty(presellRack.getIsFftPoint()) && "1".equals(presellRack.getIsFftPoint()))
        {
            fftPointPricing = presellRack.getFftPointPricing();
            logger.info("-------------纯fft积分定价，单个商品fft积分使用值:" + fftPointPricing + "---------------------");
        }
        if (!Assert.empty(presellRack.getIsBankPoint()) && "1".equals(presellRack.getIsBankPoint()))
        {
            bankPointPricing = presellRack.getBankPointPricing();
            logger.info("-------------纯bank积分定价，单个商品bank积分使用值:" + bankPointPricing + "---------------------");
        }
        if (!Assert.empty(presellRack.getIsFftpointCash()) && "1".equals(presellRack.getIsFftpointCash()))
        {
            String fftPointTemp = presellRack.getFftpointCashPricing();
            fftPointTemp = fftPointTemp.replace("|", ",");
            String[] fftPointArr = fftPointTemp.split(",");
            if (fftPointArr != null && fftPointArr.length == 2)
            {
                cashFftPointPricing = fftPointArr[0];
                cashFftPointPricingCash = fftPointArr[1];
                logger.info("-------------fft积分+现金定价，单个商品fft积分使用值:" + cashFftPointPricing + "  现金：" + cashFftPointPricingCash + "---------------------");
            }
        }
        if (!Assert.empty(presellRack.getIsBankpointCash()) && "1".equals(presellRack.getIsBankpointCash()))
        {
            String bankPointTemp = presellRack.getBankpointCashPricing();
            bankPointTemp = bankPointTemp.replace("|", ",");
            String[] bankPointArr = bankPointTemp.split(",");
            if (bankPointArr != null && bankPointArr.length == 2)
            {
                cashBankPointPricing = bankPointArr[0];
                cashBankPointPricingCash = bankPointArr[1];
                logger.info("-------------bank积分+现金定价，单个商品bank积分使用值:" + cashBankPointPricing + "  现金：" + cashBankPointPricingCash + "---------------------");
            }
        }

    }

    /**
     * 获取现金分分通积分比率
     *
     * @return
     */
    public String fftCashPointRio()
    {
        logger.info("查询现金分分通积分比率");
        String radioValue = TranCommand.PRICING_FFT_CASH;// trans.properties配置现金分分通积分比率
        String rio = "1";
        String[] radioValueArr = radioValue.split(":");
        if (radioValueArr != null && radioValueArr.length == 2 && !"0".equals(radioValueArr[1]))
        {
            BigDecimal cash = new BigDecimal(radioValueArr[0]);
            BigDecimal point = new BigDecimal(radioValueArr[1]);
            rio = cash.divide(point, 2, RoundingMode.DOWN).toString();
            logger.info("现金分分通积分比率 :" + rio);
            return rio;
        }
        else
        {
            logger.info("现金分分通积分比率 :" + rio);
            return rio;
        }
    }

    /**
     * 获取现金银行积分比率
     *
     * @return
     */
    public String bankCashPointRio()
    {
        logger.info("查询现金银行积分比率");
        String radioValue = TranCommand.PRICING_BANK_CASH;// trans.properties配置现金银行积分比率
        String rio = "1";
        String[] radioValueArr = radioValue.split(":");
        if (radioValueArr != null && radioValueArr.length == 2 && !"0".equals(radioValueArr[1]))
        {
            BigDecimal cash = new BigDecimal(radioValueArr[0]);
            BigDecimal point = new BigDecimal(radioValueArr[1]);
            rio = cash.divide(point, 2, RoundingMode.DOWN).toString();
            logger.info("现金银行积分比率 :" + rio);
            return rio;
        }
        else
        {
            logger.info("现金银行积分比率 :" + rio);
            return rio;
        }
    }

    /**
     * 计算预售剩余时间, 同时计算商品的状态
     */
    private void calculateSellLeftTimeAndDeliveryState(GoodsPresellRack rack)
    {
        String result = "00:00:00:00";
        String leftToStart = "00:00:00:00";
        //暂时初始化为未开始
        String status = "0";
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 获取服务系统时间
        try
        {
            Date startDate = sf.parse(rack.getBeginTime());
            Date closeDate = sf.parse(rack.getEndTime());
            Date deliveryStartDate = sf.parse(rack.getDeliveryStartTime());
            Date deliveryEndDate = sf.parse(rack.getDeliveryEndTime());


            if (serverTime.before(startDate))
            {
                //未预售
                status = "0";
            }
            else if (startDate.before(serverTime) && serverTime.before(closeDate))
            {
                //预售中
                status = "1";
            }
            else if (closeDate.before(serverTime) && serverTime.before(deliveryStartDate))
            {
                //预售结束
                status = "2";
            }
            else if (deliveryStartDate.before(serverTime) && serverTime.before(deliveryEndDate))
            {
                //提货中
                status = "3";
            }
            else if (serverTime.after(deliveryEndDate))
            {
                //提货结束
                status = "4";
            }
            else
            {
                //未知状态，弄死那个填数据的人。你可以尝试九十九种方法。
                status = "5";
            }

            long leftTime = 0;

            if ("0" == status || "1" == status)
            {
                if ("0" == status)
                {
                    //计算未开始时间
                    leftTime = (startDate.getTime() - serverTime.getTime()) / 1000;
                    leftToStart = getLeftShow(leftTime);
                    leftToStartMap.put(rack.getId(), leftToStart);

                    //补充，计算预售全部时间
                    leftTime = (closeDate.getTime() - startDate.getTime()) / 1000;
                    result = getLeftShow(leftTime);
                    leftTimeMap.put(rack.getId(), result);
                }
                else
                {
                    leftToStartMap.put(rack.getId(), leftToStart);
                    //计算预售结束剩余时间
                    leftTime = (closeDate.getTime() - serverTime.getTime()) / 1000;
                    result = getLeftShow(leftTime);
                    leftTimeMap.put(rack.getId(), result);
                }

            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        // 预售结束日期判断

        goodsStatusMap.put(rack.getId(), status);
    }

    /**
     * 返回剩余时间
     *
     * @param leftTime 剩余时间长度
     * @return 剩余时间字符串
     */
    private String getLeftShow(long leftTime)
    {
        long _day = 60 * 60 * 24;
        long _hour = 60 * 60;
        long _minute = 60;

        long day = leftTime / _day;
        leftTime -= (day * _day);
        long hour = leftTime / _hour;
        leftTime -= (hour * _hour);
        long minute = leftTime / _minute;
        long second = leftTime % _minute;
        StringBuilder sb = new StringBuilder();
        sb.append(day).append(":").append(hour).append(":").append(minute).append(":").append(second);
        return sb.toString();
    }

    /**
     * 批量计算货架的剩余预售时间
     *
     * @param rackList 初始数据包
     */
    private void calculateSellLeftTimeAndDeliveryState(List rackList)
    {
        for (Object o : rackList)
        {
            GoodsPresellRack rack = (GoodsPresellRack) o;
            calculateSellLeftTimeAndDeliveryState(rack);
        }
    }

    /**
     * 清洗支付方式
     *
     * @param tempTran 支付信息
     * @return 清洗后结果
     */
    private TempTrans clearPayMethod(TempTrans tempTran)
    {
        double fftPoint = 0.0;
        double bankPoint = 0.0;
        double currency = 0.0;
        if (StringUtils.isNotBlank(tempTran.getFftPointsValueRealAll()))
        {
            fftPoint = Double.parseDouble(tempTran.getFftPointsValueRealAll());
        }
        if (StringUtils.isNotBlank(tempTran.getBankPointsValueRealAll()))
        {
            bankPoint = Double.parseDouble(tempTran.getBankPointsValueRealAll());
        }
        if (StringUtils.isNotBlank(tempTran.getCurrencyValueRealAll()))
        {
            currency = Double.parseDouble(tempTran.getCurrencyValueRealAll());
        }
        //清洗分分通0分支付
        if (0.0 >= fftPoint)
        {
            if (TranCommand.POINTS_FFT_ALPAY_SCOPE.equals(tempTran.getPayMethod()))
            {
                tempTran.setPayMethod(TranCommand.ALPAY);
            }
            if (TranCommand.POINTS_FFT_CASH_SCOPE.equals(tempTran.getPayMethod()))
            {
                tempTran.setPayMethod(TranCommand.CASH);
            }
        }
        //清洗银行积分0支付
        if (0.0 >= bankPoint)
        {
            if (TranCommand.POINTS_BANK_ALPAY_SCOPE.equals(tempTran.getPayMethod()))
            {
                tempTran.setPayMethod(TranCommand.ALPAY);
            }
            if (TranCommand.POINTS_BANK_CASH_SCOPE.equals(tempTran.getPayMethod()))
            {
                tempTran.setPayMethod(TranCommand.CASH);
            }
        }

        //清洗现金0支付
        if (0.0 >= currency)
        {
            if (TranCommand.POINTS_FFT_ALPAY_SCOPE.equals(tempTran.getPayMethod()))
            {
                tempTran.setPayMethod(TranCommand.POINTS_FFT);
            }
            if (TranCommand.POINTS_FFT_CASH_SCOPE.equals(tempTran.getPayMethod()))
            {
                tempTran.setPayMethod(TranCommand.POINTS_FFT);
            }
            if (TranCommand.POINTS_BANK_ALPAY_SCOPE.equals(tempTran.getPayMethod()))
            {
                tempTran.setPayMethod(TranCommand.POINTS_BANK);
            }
            if (TranCommand.POINTS_BANK_CASH_SCOPE.equals(tempTran.getPayMethod()))
            {
                tempTran.setPayMethod(TranCommand.POINTS_BANK);
            }
        }
        return tempTran;
    }

    public List<PresellDelivery> getDeliveryList()
    {
        return deliveryList;
    }

    public void setDeliveryList(List<PresellDelivery> deliveryList)
    {
        this.deliveryList = deliveryList;
    }

    public PreSellActionSupport getPreSellActionSupport()
    {
        return preSellActionSupport;
    }

    public void setPreSellActionSupport(PreSellActionSupport preSellActionSupport)
    {
        this.preSellActionSupport = preSellActionSupport;
    }

    public SellersActionSupport getSellersActionSupport()
    {
        return sellersActionSupport;
    }

    public void setSellersActionSupport(SellersActionSupport sellersActionSupport)
    {
        this.sellersActionSupport = sellersActionSupport;
    }

    public TransActionSupport getTransActionSupport()
    {
        return transActionSupport;
    }

    public void setTransActionSupport(TransActionSupport transActionSupport)
    {
        this.transActionSupport = transActionSupport;
    }

    public PayActionSupport getPayActionSupport()
    {
        return payActionSupport;
    }

    public void setPayActionSupport(PayActionSupport payActionSupport)
    {
        this.payActionSupport = payActionSupport;
    }

    public BuyersActionSupport getBuyersActionSupport()
    {
        return buyersActionSupport;
    }

    public void setBuyersActionSupport(BuyersActionSupport buyersActionSupport)
    {
        this.buyersActionSupport = buyersActionSupport;
    }

    public PointActionSupport getPointActionSupport()
    {
        return pointActionSupport;
    }

    public void setPointActionSupport(PointActionSupport pointActionSupport)
    {
        this.pointActionSupport = pointActionSupport;
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

    public static long getSerialVersionUID()
    {
        return serialVersionUID;
    }

    public static Logger getLogger()
    {
        return logger;
    }

    public GoodsPreSellRackActionSupport getGoodsPreSellRackActionSupport()
    {
        return goodsPreSellRackActionSupport;
    }

    public void setGoodsPreSellRackActionSupport(GoodsPreSellRackActionSupport goodsPreSellRackActionSupport)
    {
        this.goodsPreSellRackActionSupport = goodsPreSellRackActionSupport;
    }

    public Trans getTrans()
    {
        return trans;
    }

    public void setTrans(Trans trans)
    {
        this.trans = trans;
    }

    public TempTrans getTempTran()
    {
        return tempTran;
    }

    public void setTempTran(TempTrans tempTran)
    {
        this.tempTran = tempTran;
    }

    public String getCash()
    {
        return cash;
    }

    public void setCash(String cash)
    {
        this.cash = cash;
    }

    public String getFftPointPricing()
    {
        return fftPointPricing;
    }

    public void setFftPointPricing(String fftPointPricing)
    {
        this.fftPointPricing = fftPointPricing;
    }

    public String getBankPointPricing()
    {
        return bankPointPricing;
    }

    public void setBankPointPricing(String bankPointPricing)
    {
        this.bankPointPricing = bankPointPricing;
    }

    public String getCashFftPointPricing()
    {
        return cashFftPointPricing;
    }

    public void setCashFftPointPricing(String cashFftPointPricing)
    {
        this.cashFftPointPricing = cashFftPointPricing;
    }

    public String getCashFftPointPricingCash()
    {
        return cashFftPointPricingCash;
    }

    public void setCashFftPointPricingCash(String cashFftPointPricingCash)
    {
        this.cashFftPointPricingCash = cashFftPointPricingCash;
    }

    public String getCashBankPointPricing()
    {
        return cashBankPointPricing;
    }

    public void setCashBankPointPricing(String cashBankPointPricing)
    {
        this.cashBankPointPricing = cashBankPointPricing;
    }

    public String getCashBankPointPricingCash()
    {
        return cashBankPointPricingCash;
    }

    public void setCashBankPointPricingCash(String cashBankPointPricingCash)
    {
        this.cashBankPointPricingCash = cashBankPointPricingCash;
    }

    public String getFftPoint()
    {
        return fftPoint;
    }

    public void setFftPoint(String fftPoint)
    {
        this.fftPoint = fftPoint;
    }

    public String getBankPoint()
    {
        return bankPoint;
    }

    public void setBankPoint(String bankPoint)
    {
        this.bankPoint = bankPoint;
    }

    public String getBankPointValueMin()
    {
        return bankPointValueMin;
    }

    public void setBankPointValueMin(String bankPointValueMin)
    {
        this.bankPointValueMin = bankPointValueMin;
    }

    public String getFftPointValueMin()
    {
        return fftPointValueMin;
    }

    public void setFftPointValueMin(String fftPointValueMin)
    {
        this.fftPointValueMin = fftPointValueMin;
    }

    public String getBankPointValueMax()
    {
        return bankPointValueMax;
    }

    public void setBankPointValueMax(String bankPointValueMax)
    {
        this.bankPointValueMax = bankPointValueMax;
    }

    public String getFftPointValueMax()
    {
        return fftPointValueMax;
    }

    public void setFftPointValueMax(String fftPointValueMax)
    {
        this.fftPointValueMax = fftPointValueMax;
    }

    public String getCashFftPointsRatio()
    {
        return cashFftPointsRatio;
    }

    public void setCashFftPointsRatio(String cashFftPointsRatio)
    {
        this.cashFftPointsRatio = cashFftPointsRatio;
    }

    public String getCashBankPointsRatio()
    {
        return cashBankPointsRatio;
    }

    public void setCashBankPointsRatio(String cashBankPointsRatio)
    {
        this.cashBankPointsRatio = cashBankPointsRatio;
    }

    public String getPayResult()
    {
        return payResult;
    }

    public void setPayResult(String payResult)
    {
        this.payResult = payResult;
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    public String getStartFalg()
    {
        return startFalg;
    }

    public void setStartFalg(String startFalg)
    {
        this.startFalg = startFalg;
    }

    public String getNotfind()
    {
        return notfind;
    }

    public void setNotfind(String notfind)
    {
        this.notfind = notfind;
    }

    public StoreActionSupport getStoreActionSupport()
    {
        return storeActionSupport;
    }

    public void setStoreActionSupport(StoreActionSupport storeActionSupport)
    {
        this.storeActionSupport = storeActionSupport;
    }

    public List<Store> getStoreList()
    {
        return storeList;
    }

    public void setStoreList(List<Store> storeList)
    {
        this.storeList = storeList;
    }

    public PresellDelivery getDelivery()
    {
        return delivery;
    }

    public void setDelivery(PresellDelivery delivery)
    {
        this.delivery = delivery;
    }

    public String getPayPointType()
    {
        return payPointType;
    }

    public void setPayPointType(String payPointType)
    {
        this.payPointType = payPointType;
    }

    public String getPayCashType()
    {
        return payCashType;
    }

    public void setPayCashType(String payCashType)
    {
        this.payCashType = payCashType;
    }

    public Date getServerTime()
    {
        return serverTime;
    }

    public void setServerTime(Date serverTime)
    {
        this.serverTime = serverTime;
    }

    public Map<Object, String> getLeftTimeMap()
    {
        return leftTimeMap;
    }

    public void setLeftTimeMap(Map<Object, String> leftTimeMap)
    {
        this.leftTimeMap = leftTimeMap;
    }

    public Map<Object, String> getGoodsStatusMap()
    {
        return goodsStatusMap;
    }

    public void setGoodsStatusMap(Map<Object, String> goodsStatusMap)
    {
        this.goodsStatusMap = goodsStatusMap;
    }

    public String getPaymentUrl()
    {
        return paymentUrl;
    }

    public String getPayPointValue()
    {
        return payPointValue;
    }

    public void setPayPointValue(String payPointValue)
    {
        this.payPointValue = payPointValue;
    }

    public Map<Object, String> getLeftToStartMap()
    {
        return leftToStartMap;
    }

    public void setLeftToStartMap(Map<Object, String> leftToStartMap)
    {
        this.leftToStartMap = leftToStartMap;
    }

}
