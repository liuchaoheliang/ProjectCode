package com.froad.action.admin.authTicket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.froad.action.support.*;
import com.froad.action.support.presell.PreSellActionSupport;
import com.froad.client.goodsPresellRack.GoodsPresellRack;
import com.froad.client.presellBuyInfo.PresellBuyInfo;
import com.froad.client.presellDelivery.PresellDelivery;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.froad.action.support.merchantUserSet.MerchantUserSellerActionSupport;
import com.froad.action.support.merchantUserSet.MerchantUserSetActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.authTicket.AuthTicket;
import com.froad.client.merchant.Merchant;
import com.froad.client.merchantUserSeller.MerchantUserSeller;
import com.froad.client.merchantUserSet.MerchantUserSet;
import com.froad.client.trans.Trans;
import com.froad.client.trans.TransDetails;
import com.froad.common.SessionKey;
import com.froad.common.TranCommand;
import com.froad.util.Assert;
import com.froad.util.Command;
import com.froad.util.DateUtil;
import com.froad.util.command.MallCommand;
import com.opensymphony.xwork2.ActionContext;

/**
 * @author Qiaopeng.Lee
 * @version 1.0
 *          认证(团购、兑换) action
 * @date 2013-3-4
 */
public class AuthTicketAction extends BaseActionSupport
{

    /**
     * UID
     */
    private static final long serialVersionUID = -5237990439075829502L;
    private static final Logger logger = Logger.getLogger(AuthTicketAction.class);
    private AuthTicketActionSupport authTicketActionSupport;
    private PreSellActionSupport preSellActionSupport;
    private MerchantActionSupport merchantActionSupport;
    private TransActionSupport transActionSupport;
    private AuthTicket authTicket;
    private String merchantId;

    private MerchantUserSetActionSupport merchantUserSetActionSupport;
    private MerchantUserSellerActionSupport merchantUserSellerActionSupport;
    private GoodsPreSellRackActionSupport goodsPreSellRackActionSupport;

    /**
     * 方法描述：进入团购或者兑换认证
     *
     * @param:
     * @return: String
     */
    public String toAuthentication()
    {

        if ("1".equals(getSession(SessionKey.MERCHANT_ROLE)))
        {
            return "nopower";
        }
        if (Assert.empty(merchantId))
        {//若商户ID为空则通过userId获取
            Merchant m = new Merchant();
            String userId = (String) getSession(MallCommand.USER_ID);
            if (!Assert.empty(userId))
            {
                m = merchantActionSupport.getMerchantInfo(userId);
                merchantId = m.getId() != null ? m.getId().toString() : "";


                Map<String, Object> session = ActionContext.getContext().getSession();
                String isAdmin = (String) session.get(MallCommand.LOGIN_MERCHANT_OR_CLERK);
                String clerkBeCode = (String) session.get("beCode");
                String isMerchant = (String) session.get(MallCommand.LOGIN_MERCHANT_OR_USER);
                if (!Assert.empty(isMerchant) && "1".equals(isMerchant))
                {//商户
                    if (!Assert.empty(isAdmin) && "1".equals(isAdmin))
                    {//管理员
                        //如果是管理员则直接进入收银台
                        return "authentication";
                    }
                    else
                    {
                        //如果不是管理员则需要对普通操作员进行权限认证
                        boolean isAllow = false;
                        MerchantUserSet clerk = new MerchantUserSet();
                        clerk.setUserId(userId);
                        clerk.setBeCode(clerkBeCode);
                        List<MerchantUserSet> clerkList = merchantUserSetActionSupport.getMerchantUserSetList(clerk);
                        MerchantUserSeller condition = new MerchantUserSeller();
                        condition.setMerchantUserId(clerkList.get(0).getId() + "");
                        condition.setMerchantId(clerkList.get(0).getMerchantId() + "");

                        //加载普通操作员权限
                        List<MerchantUserSeller> types = merchantUserSellerActionSupport.getMerchantUserSellerList(condition);
                        for (MerchantUserSeller merchantUserSeller : types)
                        {
                            if ("01".equals(merchantUserSeller.getSellerType()) || "02".equals(merchantUserSeller.getSellerType()))
                            {
                                isAllow = true;
                                break;
                            }
                        }
                        if (isAllow)
                        {
                            return "authentication";
                        }
                        else
                        {
                            //没有权限访问
                            return "nopower";
                        }

                    }
                }
                else
                {
                    return "nopower";
                }
            }
            else
            {
                return "nopower";
            }
        }
        return "authentication";
    }

    /**
     * 方法描述：团购或者兑换认证
     *
     * @return: json
     */
    public void authenticationForGroupANDExchange()
    {
        log.info("团购或者兑换认证开始！");
        JSONArray array = new JSONArray();
        JSONObject json = new JSONObject();
        Trans tran = null;
        List<AuthTicket> authList = new ArrayList<AuthTicket>();
        if (Assert.empty(authTicket.getMerchantId()))
        {//若商户ID为空则通过userId获取
            Merchant m = new Merchant();
            String userId = (String) getSession(MallCommand.USER_ID);
            if (!Assert.empty(userId))
            {
                m = merchantActionSupport.getMerchantInfo(userId);
                authTicket.setMerchantId(m.getId() != null ? m.getId().toString() : "");
            }
        }
        AuthTicket authTicketReq = new AuthTicket();

        logger.info("查询券开始 券No：" + authTicket.getSecuritiesNo() + " 商户ID：" + authTicket.getMerchantId());
        authList = authTicketActionSupport.getAuthTickList(authTicket);
        logger.info("查询券結束 list的size：" + authList.size());
        if (authList != null && authList.size() > 0)
        {
            authTicketReq = authList.get(0);
            if (authTicketReq != null && "0".equals(authTicketReq.getIsConsume()))
            {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
                String dataCurry = df.format(new Date());
                int resultNum = dataCurry.compareTo(authTicketReq.getExpireTime());
                if (resultNum > 0)
                {//判断是否超过消费时间   过期：resultNum = 1;  未过期 resultNum = 0、1
                    logger.info("团购或者兑换认证失败！已经过期");
                    json.put("reno", Command.respCode_FAIL);
                    json.put("message", "认证失败,你的消费券已经过期(过期时间为：" + authTicketReq.getExpireTime() + ")，不可以消费！");
                }
                else
                {
                    authTicket.setId(authTicketReq.getId());
                    authTicket.setIsConsume("1");//消费券号
                    String now = DateUtil.formatDate2Str(new Date());
                    authTicket.setConsumeTime(now);//消费时间
                    //记录操作员信息
                    String BeCode = (String) getSession("beCode");
                    String userId = (String) getSession(MallCommand.USER_ID);
                    String BelongUserBecode = userId + "|" + BeCode;
                    authTicket.setBelongUserBecode(BelongUserBecode);

                    boolean flag = authTicketActionSupport.updateAuthTicketState(authTicket);
                    if (flag)
                    {
                        logger.info("修改券已经消费状态成功");
                        //查询团购兑换的交易信息
                        logger.info("查詢此券的交易信息開始");
                        tran = transActionSupport.getTransById(authTicketReq.getTransId() != null ? Integer.valueOf(authTicketReq.getTransId()) : 0);
                        logger.info("查詢此券的交易信息結束");
                        logger.info("团购或者兑换认证成功！");
                        json.put("reno", Command.respCode_SUCCESS);
                        json.put("message", "认证成功,可以消费！");
                    }
                    else
                    {
                        logger.info("团购或者兑换认证失败！");
                        json.put("reno", Command.respCode_FAIL);
                        json.put("message", "认证失败！出错");
                    }
                    //修改交易操作员信息
                    logger.info("修改此券的交易belongCode信息");
                    String userBeCode = (String) getSession(MallCommand.USERID_BECODE);
                    Trans trans = new Trans();
                    trans.setBelongUserBecode(userBeCode);
                    trans.setId(authTicketReq.getTransId() != null ? Integer.valueOf(authTicketReq.getTransId()) : 0);
                    logger.info("修改此券的交易belongCode信息开始");
                    transActionSupport.updateByTransId(trans);
                    logger.info("修改此券的交易belongCode信息结束");
                }
            }
            else
            {
                //查询团购兑换的交易信息
                tran = transActionSupport.getTransById(authTicketReq.getTransId() != null ? Integer.valueOf(authTicketReq.getTransId()) : 0);
                logger.info("团购或者兑换认证失败，已经消费！");
                json.put("reno", Command.respCode_FAIL);
                json.put("message", "对不起你的券已经消费，不能再次消费！");
            }
        }
        else
        {
            logger.info("团购或者兑换认证失败！没有查找到相关记录。");
            json.put("reno", Command.respCode_FAIL);
            json.put("message", "没有查找到相关认证记录，认证失败！");
        }
        if (tran != null && tran.getTransDetailsList() != null && tran.getTransDetailsList().size() > 0)
        {
            BigDecimal goodsNum = new BigDecimal(tran.getTransDetailsList().get(0).getGoodsNumber());
            //实体商品交易积分和现金变为单价（目前只有实体商品会发送消费券）
            if ((Assert.empty(tran.getVirtualType()) || (!Assert.empty(tran.getVirtualType()) && !TranCommand.VIRTUAL_TYPE.containsKey(tran.getVirtualType()))) && goodsNum != null && goodsNum
                    .compareTo(new BigDecimal("1")) > 0)
            {
                if (!Assert.empty(tran.getCostpriceTotal()) && !"0".equals(tran.getCostpriceTotal()))
                {
                    BigDecimal costpriceTotal = new BigDecimal(tran.getCostpriceTotal());
                    costpriceTotal = costpriceTotal.divide(goodsNum, 2, RoundingMode.DOWN);
                    tran.setCostpriceTotal(costpriceTotal.toString());
                }
                if (!Assert.empty(tran.getBankPointsValueRealAll()) && !"0".equals(tran.getBankPointsValueRealAll()))
                {
                    BigDecimal bankPoints = new BigDecimal(tran.getBankPointsValueRealAll());
                    bankPoints = bankPoints.divide(goodsNum, 2, RoundingMode.DOWN);
                    tran.setBankPointsValueRealAll(bankPoints.toString());
                }
                if (!Assert.empty(tran.getFftPointsValueRealAll()) && !"0".equals(tran.getFftPointsValueRealAll()))
                {
                    BigDecimal fftPoints = new BigDecimal(tran.getFftPointsValueRealAll());
                    fftPoints = fftPoints.divide(goodsNum, 2, RoundingMode.DOWN);
                    tran.setFftPointsValueRealAll(fftPoints.toString());
                }
                if (!Assert.empty(tran.getCurrencyValueRealAll()) && !"0".equals(tran.getCurrencyValueRealAll()))
                {
                    BigDecimal currencyValue = new BigDecimal(tran.getCurrencyValueRealAll());
                    currencyValue = currencyValue.divide(goodsNum, 2, RoundingMode.DOWN);
                    tran.setCurrencyValueRealAll(currencyValue.toString());
                }
            }


            if (TranCommand.POINTS_EXCH_PRODUCT.equals(tran.getTransType()))
            {//兑换
                for (TransDetails detail : tran.getTransDetailsList())
                {
                    JSONObject orgs = new JSONObject();
                    if (detail.getGoodsExchangeRack() == null)
                    {
                        orgs.put("goodsName", detail.getClientGoodsExchangeRack() != null && detail.getClientGoodsExchangeRack().getGoods() != null &&
                                detail.getClientGoodsExchangeRack().getGoods().getGoodsName() != null ? detail.getClientGoodsExchangeRack().getGoods().getGoodsName() : "");
                        orgs.put("goodsDesc", detail.getClientGoodsExchangeRack() != null && detail.getClientGoodsExchangeRack().getGoods() != null && detail.getClientGoodsExchangeRack().getGoods()
                                .getGoodsName() != null ? detail.getClientGoodsExchangeRack().getGoods().getGoodsName() : "");
                        orgs.put("goodsNumber", detail.getGoodsNumber());
                    }
                    else
                    {
                        orgs.put("goodsName", detail.getGoodsExchangeRack() != null && detail.getGoodsExchangeRack().getGoods() != null &&
                                detail.getGoodsExchangeRack().getGoods().getGoodsName() != null ? detail.getGoodsExchangeRack().getGoods().getGoodsName() : "");
                        orgs.put("goodsDesc", detail.getGoodsExchangeRack() != null && detail.getGoodsExchangeRack().getGoodsRackName() != null ? detail.getGoodsExchangeRack().getGoodsRackName() :
                                "");
                        orgs.put("goodsNumber", detail.getGoodsNumber());
                    }
                    array.add(orgs);
                }
                json.put("tranDetailsList", array);
            }
            else if (TranCommand.GROUP.equals(tran.getTransType()))
            {//团购
                for (TransDetails detail : tran.getTransDetailsList())
                {
                    JSONObject orgs = new JSONObject();
                    if (detail.getGoodsGroupRack() == null)
                    {
                        orgs.put("goodsName", detail.getClientGoodsGroupRack() != null && detail.getClientGoodsGroupRack().getGoods() != null &&
                                detail.getClientGoodsGroupRack().getGoods().getGoodsName() != null ? detail.getClientGoodsGroupRack().getGoods().getGoodsName() : "");
                        orgs.put("goodsDesc", detail.getClientGoodsGroupRack() != null && detail.getClientGoodsGroupRack().getSeoDescription() != null ? detail.getClientGoodsGroupRack()
                                .getSeoDescription() : "");
                        orgs.put("goodsNumber", detail.getGoodsNumber());
                    }
                    else
                    {
                        orgs.put("goodsName", detail.getGoodsGroupRack() != null && detail.getGoodsGroupRack().getGoods() != null &&
                                detail.getGoodsGroupRack().getGoods().getGoodsName() != null ? detail.getGoodsGroupRack().getGoods().getGoodsName() : "");
                        orgs.put("goodsDesc", detail.getGoodsGroupRack() != null && detail.getGoodsGroupRack().getSeoDescription() != null ? detail.getGoodsGroupRack().getSeoDescription() : "");
                        orgs.put("goodsNumber", detail.getGoodsNumber());
                    }

                    array.add(orgs);
                }
                json.put("tranDetailsList", array);
            }

            if (TranCommand.POINTS_FFT.equals(tran.getPayMethod()) || TranCommand.POINTS_FFT_CASH.equals(tran.getPayMethod()) || TranCommand.POINTS_FFT_CASH_SCOPE.equals(tran.getPayMethod()))
            {
                if (!Assert.empty(tran.getFftPointsValueRealAll()) && !"0".equals(tran.getFftPointsValueRealAll()))
                {
                    json.put("pointsFFT", tran.getFftPointsValueRealAll());
                    json.put("pointsBank", "0");
                }
                else
                {
                    json.put("pointsFFT", "0");
                    json.put("pointsBank", "0");
                }
            }
            else if (TranCommand.POINTS_BANK.equals(tran.getPayMethod()) || TranCommand.POINTS_BANK_CASH.equals(tran.getPayMethod()) || TranCommand.POINTS_BANK_CASH_SCOPE.equals(tran.getPayMethod()))
            {
                if (!Assert.empty(tran.getBankPointsValueRealAll()) && !"0".equals(tran.getBankPointsValueRealAll()))
                {
                    json.put("pointsBank", tran.getBankPointsValueRealAll());
                    json.put("pointsFFT", "0");
                }
                else
                {
                    json.put("pointsBank", "0");
                    json.put("pointsFFT", "0");
                }
            }
            else
            {
                json.put("pointsFFT", "0");
                json.put("pointsBank", "0");
            }

            if (!Assert.empty(tran.getCurrencyValueRealAll()) && !"0".equals(tran.getCurrencyValueRealAll()))
            {
                json.put("currency", tran.getCurrencyValueRealAll());
            }
            else
            {
                json.put("currency", "0");
            }
            json.put("tranId", tran.getId());
            json.put("merchant", tran.getMerchant() != null && tran.getMerchant().getCompanyFullName() != null ? tran.getMerchant().getCompanyFullName() : "");
            json.put("tranReno", "0");
        }
        else
        {
            json.put("tranReno", "1");
        }
        ajaxJson(json.toString());
    }

    /**
     * 方法描述：精品预售认证
     *
     * @return: json
     */
    public void authenticationForPreSell()
    {
        //强制转换
        authTicket.setType("2");

        log.info("精品预售认证开始！");
        JSONArray array = new JSONArray();
        JSONObject json = new JSONObject();
        Trans tran = null;
        List<AuthTicket> authList = new ArrayList<AuthTicket>();
        if (Assert.empty(authTicket.getMerchantId()))
        {//若商户ID为空则通过userId获取
            Merchant m = new Merchant();
            String userId = (String) getSession(MallCommand.USER_ID);
            if (!Assert.empty(userId))
            {
                m = merchantActionSupport.getMerchantInfo(userId);
                authTicket.setMerchantId(m.getId() != null ? m.getId().toString() : "");
            }
        }
        AuthTicket authTicketReq = new AuthTicket();

        logger.info("查询券开始 券No：" + authTicket.getSecuritiesNo() + " 商户ID：" + authTicket.getMerchantId());
        authList = authTicketActionSupport.getAuthTickList(authTicket);
        logger.info("查询券結束 list的size：" + authList.size());
        if (authList != null && authList.size() > 0)
        {
            authTicketReq = authList.get(0);
            if (authTicketReq != null && "0".equals(authTicketReq.getIsConsume()))
            {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
                String dataCurry = df.format(new Date());
                int resultNum = dataCurry.compareTo(authTicketReq.getExpireTime());
                if (resultNum > 0)
                {//判断是否超过消费时间   过期：resultNum = 1;  未过期 resultNum = 0、1
                    logger.info("精品预售认证失败！已经过期");
                    json.put("reno", Command.respCode_FAIL);
                    json.put("message", "认证失败,你的消费券已经过期(过期时间为：" + authTicketReq.getExpireTime() + ")，不可以消费！");
                }
                else
                {
                    authTicket.setId(authTicketReq.getId());
                    authTicket.setIsConsume("1");//消费券号
                    String now = DateUtil.formatDate2Str(new Date());
                    authTicket.setConsumeTime(now);//消费时间
                    //记录操作员信息
                    String BeCode = (String) getSession("beCode");
                    String userId = (String) getSession(MallCommand.USER_ID);
                    String BelongUserBecode = userId + "|" + BeCode;
                    authTicket.setBelongUserBecode(BelongUserBecode);

                    boolean flag = authTicketActionSupport.updateAuthTicketState(authTicket);
                    if (flag)
                    {
                        logger.info("修改券已经消费状态成功");
                        //查询精品预售的交易信息
                        logger.info("查詢此券的交易信息開始");
                        tran = transActionSupport.getTransById(authTicketReq.getTransId() != null ? Integer.valueOf(authTicketReq.getTransId()) : 0);
                        logger.info("查詢此券的交易信息結束");
                        logger.info("精品预售认证成功！");
                        json.put("reno", Command.respCode_SUCCESS);
                        json.put("message", "认证成功,可以消费！");
                    }
                    else
                    {
                        logger.info("精品预售认证失败！");
                        json.put("reno", Command.respCode_FAIL);
                        json.put("message", "认证失败！出错");
                    }
                    //修改交易操作员信息
                    logger.info("修改此券的交易belongCode信息");
                    String userBeCode = (String) getSession(MallCommand.USERID_BECODE);
                    Trans trans = new Trans();
                    trans.setBelongUserBecode(userBeCode);
                    trans.setId(authTicketReq.getTransId() != null ? Integer.valueOf(authTicketReq.getTransId()) : 0);
                    logger.info("修改此券的交易belongCode信息开始");
                    transActionSupport.updateByTransId(trans);
                    logger.info("修改此券的交易belongCode信息结束");
                }
            }
            else
            {
                //查询精品预售的交易信息
                tran = transActionSupport.getTransById(authTicketReq.getTransId() != null ? Integer.valueOf(authTicketReq.getTransId()) : 0);
                logger.info("精品预售认证失败，已经消费！");
                json.put("reno", Command.respCode_FAIL);
                json.put("message", "对不起你的券已经消费，不能再次消费！");
            }
        }
        else
        {
            logger.info("精品预售认证失败！没有查找到相关记录。");
            json.put("reno", Command.respCode_FAIL);
            json.put("message", "没有查找到相关认证记录，认证失败！");
        }
        if (tran != null && tran.getTransDetailsList() != null && tran.getTransDetailsList().size() > 0)
        {
            BigDecimal goodsNum = new BigDecimal(tran.getTransDetailsList().get(0).getGoodsNumber());
           String rackId = tran.getTransDetailsList().get(0).getGoodsRackId();
            json.put("goodsNumber", goodsNum.toString());
            String fftpoint = "0";
            if (StringUtils.isNotBlank(tran.getFftPointsValueRealAll()))
            {
                fftpoint = tran.getFftPointsValueRealAll();
            }
            String bankpoint = "0";
            if (StringUtils.isNotBlank(tran.getBankPointsValueRealAll()))
            {
                bankpoint = tran.getBankPointsValueRealAll();
            }
            String currency = "0";
            if (StringUtils.isNotBlank(tran.getCurrencyValueRealAll()))
            {
                currency = tran.getCurrencyValueRealAll();
            }
            //实体商品消费信息
            json.put("pointsFFT", fftpoint);
            json.put("pointsBank", bankpoint);
            json.put("currency", currency);

            GoodsPresellRack goodsPresellRack = goodsPreSellRackActionSupport.getGoodsPresellRackById(Integer.parseInt(rackId));
            json.put("goodsName", goodsPresellRack.getSeoTitle());
            json.put("goodsDesc", goodsPresellRack.getSeoDescription());

            //装载提货点信息
            String deliveryName = "";
            PresellBuyInfo presellBuyInfoQuery = new PresellBuyInfo();
            presellBuyInfoQuery.setTransId(tran.getId());
            List<PresellBuyInfo> presellBuyInfoList = preSellActionSupport.findPreSellBuyInfobyConditions(presellBuyInfoQuery);

            if (null != presellBuyInfoList && 0 < presellBuyInfoList.size())
            {
                Integer deliveryId = presellBuyInfoList.get(0).getPresellDeliveryId();
                PresellDelivery presellDeliveryQuery = new PresellDelivery();
                presellDeliveryQuery.setId(deliveryId);
                List<PresellDelivery> deliveryList = preSellActionSupport.findPreSellDeliveryByConditions(presellDeliveryQuery);

                if (null != deliveryList && 0 < deliveryList.size())
                {
                    PresellDelivery delivery = deliveryList.get(0);
                    deliveryName = delivery.getName() + ":" + delivery.getAddress();
                }
            }

            json.put("deliveryName", deliveryName);

            json.put("tranId", tran.getId());
            json.put("merchant", tran.getMerchant() != null && tran.getMerchant().getCompanyFullName() != null ? tran.getMerchant().getCompanyFullName() : "");
            json.put("tranReno", "0");
        }
        else
        {
            json.put("tranReno", "1");
        }
        ajaxJson(json.toString());
    }

    public PreSellActionSupport getPreSellActionSupport()
    {
        return preSellActionSupport;
    }

    public void setPreSellActionSupport(PreSellActionSupport preSellActionSupport)
    {
        this.preSellActionSupport = preSellActionSupport;
    }

    public AuthTicketActionSupport getAuthTicketActionSupport()
    {
        return authTicketActionSupport;
    }

    public void setAuthTicketActionSupport(AuthTicketActionSupport authTicketActionSupport)
    {
        this.authTicketActionSupport = authTicketActionSupport;
    }

    public MerchantActionSupport getMerchantActionSupport()
    {
        return merchantActionSupport;
    }

    public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport)
    {
        this.merchantActionSupport = merchantActionSupport;
    }

    public AuthTicket getAuthTicket()
    {
        return authTicket;
    }

    public void setAuthTicket(AuthTicket authTicket)
    {
        this.authTicket = authTicket;
    }

    public String getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(String merchantId)
    {
        this.merchantId = merchantId;
    }

    public TransActionSupport getTransActionSupport()
    {
        return transActionSupport;
    }

    public void setTransActionSupport(TransActionSupport transActionSupport)
    {
        this.transActionSupport = transActionSupport;
    }

    public MerchantUserSetActionSupport getMerchantUserSetActionSupport()
    {
        return merchantUserSetActionSupport;
    }

    public void setMerchantUserSetActionSupport(MerchantUserSetActionSupport merchantUserSetActionSupport)
    {
        this.merchantUserSetActionSupport = merchantUserSetActionSupport;
    }

    public MerchantUserSellerActionSupport getMerchantUserSellerActionSupport()
    {
        return merchantUserSellerActionSupport;
    }

    public void setMerchantUserSellerActionSupport(MerchantUserSellerActionSupport merchantUserSellerActionSupport)
    {
        this.merchantUserSellerActionSupport = merchantUserSellerActionSupport;
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
