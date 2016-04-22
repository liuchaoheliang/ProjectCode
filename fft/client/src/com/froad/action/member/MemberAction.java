package com.froad.action.member;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

import com.froad.action.support.*;
import com.froad.action.support.presell.PreSellActionSupport;
import com.froad.action.utils.TranStateResult;
import com.froad.action.utils.TransStateUtil;
import com.froad.client.goodsPresellRack.GoodsPresellRack;
import com.froad.client.presellBuyInfo.PresellBuyInfo;
import com.froad.client.presellDelivery.PresellDelivery;
import com.froad.client.trans.*;
import com.froad.client.trans.OrderType;
import com.froad.common.*;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.fundsChannel.FundsChannel;
import com.froad.client.user.User;
import com.froad.client.userCertification.UserCertification;
import com.froad.util.Assert;
import com.froad.util.command.Command;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class MemberAction extends BaseActionSupport
{
    /**
     * UID
     */
    private static final long serialVersionUID = -4923977384827812612L;
    private static final Logger logger = Logger.getLogger(MemberAction.class);
    private TransActionSupport transActionSupport;
    private PreSellActionSupport preSellActionSupport;
    private Trans pager;
    private UserActionSupport userActionSupport;
    private BuyersActionSupport buyersActionSupport;
    private SellersActionSupport sellersActionSupport;
    private UserCertificationActionSupport userCertificationActionSupport;
    private GoodsPreSellRackActionSupport goodsPreSellRackActionSupport;
    private UserCertification userCertification;
    private List<FundsChannel> fundsChannelList;
    private String errorMsg;
    private FundsChannel fundsChannel;
    private String isLotteryCertified = "00";//如果用户没有提现认证绑定银行卡，是否需要提示（只用于彩票 00：需要提示  01：不需要提示）

    public String queryPointsExchProductTran()
    {
        if (isCertifiedFn())
        {
            isLotteryCertified = "01";
        }

        if (pager == null) pager = new Trans();
        pager.setPageSize(10);//每页10条
        Map<String, Object> session = ActionContext.getContext().getSession();
        String userId = (String) session.get("userId");
        //		Buyers buyer=buyersActionSupport.getBuyerByUserId(userId);
        //		if(buyer==null)
        //			return Action.SUCCESS;
        pager.setUserId(userId);
        pager.setTransType(TranCommand.POINTS_EXCH_PRODUCT);//积分兑换："01"

        pager.setOrderType(OrderType.DESC);
        pager = transActionSupport.getGroupOrExchangeTransByPager(pager);

        //更新交易积分和价格信息改为单个商品的积分和价格
        List<Object> templist = pager.getList();
        List<Trans> newList = new ArrayList<Trans>();
        for (int i = 0; i < templist.size(); i++)
        {
            Trans t = (Trans) templist.get(i);
            BigDecimal goodsNum = new BigDecimal(t.getTransDetailsList().get(0).getGoodsNumber());
            //实体商品（目前只有实体商品会发送消费券）
            if (TranCommand.TRAN_SUCCESS.equals(t.getState()) && (Assert.empty(t.getVirtualType()) || (!Assert.empty(t.getVirtualType()) && !TranCommand.VIRTUAL_TYPE.containsKey(t.getVirtualType())
            )) && goodsNum != null && goodsNum.compareTo(new BigDecimal("1")) > 0)
            {//非虚拟商品
                if (!Assert.empty(t.getCostpriceTotal()) && !"0".equals(t.getCostpriceTotal()))
                {
                    BigDecimal costpriceTotal = new BigDecimal(t.getCostpriceTotal());
                    costpriceTotal = costpriceTotal.divide(goodsNum, 2, RoundingMode.DOWN);
                    t.setCostpriceTotal(costpriceTotal.toString());
                }
                if (!Assert.empty(t.getBankPointsValueRealAll()) && !"0".equals(t.getBankPointsValueRealAll()))
                {
                    BigDecimal bankPoints = new BigDecimal(t.getBankPointsValueRealAll());
                    bankPoints = bankPoints.divide(goodsNum, 2, RoundingMode.DOWN);
                    t.setBankPointsValueRealAll(bankPoints.toString());
                }
                if (!Assert.empty(t.getFftPointsValueRealAll()) && !"0".equals(t.getFftPointsValueRealAll()))
                {
                    BigDecimal fftPoints = new BigDecimal(t.getFftPointsValueRealAll());
                    fftPoints = fftPoints.divide(goodsNum, 2, RoundingMode.DOWN);
                    t.setFftPointsValueRealAll(fftPoints.toString());
                }
                if (!Assert.empty(t.getCurrencyValueRealAll()) && !"0".equals(t.getCurrencyValueRealAll()))
                {
                    BigDecimal currencyValue = new BigDecimal(t.getCurrencyValueRealAll());
                    currencyValue = currencyValue.divide(goodsNum, 2, RoundingMode.DOWN);
                    t.setCurrencyValueRealAll(currencyValue.toString());
                }
            }
            newList.add(t);
        }
        pager.getList().clear();
        pager.getList().addAll(newList);
        return Action.SUCCESS;
    }

    public String queryPointsExchCurrecyTran()
    {
        if (isCertifiedFn())
        {
            isLotteryCertified = "01";
        }
        if (pager == null) pager = new Trans();
        pager.setPageSize(10);//每页10条
        Map<String, Object> session = ActionContext.getContext().getSession();
        String userId = (String) session.get("userId");

        pager.setUserId(userId);
        pager.setTransType(TranCommand.POINTS_EXCH_CASH);//积分提现："04"

        pager.setOrderType(OrderType.DESC);
        pager = transActionSupport.getTransByPager(pager);
        return Action.SUCCESS;
    }

    public String queryGroupTran()
    {
        if (pager == null) pager = new Trans();
        pager.setPageSize(10);//每页10条
        Map<String, Object> session = ActionContext.getContext().getSession();
        String userId = (String) session.get("userId");
        //		Buyers buyer=buyersActionSupport.getBuyerByUserId(userId);
        //		if(buyer==null)
        //			return Action.SUCCESS;
        pager.setUserId(userId);
        pager.setTransType(TranCommand.GROUP);//我的团购："02"

        pager.setOrderType(OrderType.DESC);
        pager = transActionSupport.getGroupOrExchangeTransByPager(pager);
        //更新交易积分和价格信息改为单个商品的积分和价格
        List<Object> templist = pager.getList();
        List<Trans> newList = new ArrayList<Trans>();
        for (int i = 0; i < templist.size(); i++)
        {
            Trans t = (Trans) templist.get(i);
            //Integer num = Integer.valueOf(t.getTransDetailsList().get(0).getGoodsNumber());
            BigDecimal goodsNum = new BigDecimal(t.getTransDetailsList().get(0).getGoodsNumber());
            //实体商品（目前只有实体商品会发送消费券）
            if (TranCommand.TRAN_SUCCESS.equals(t.getState()) && (Assert.empty(t.getVirtualType()) || (!Assert.empty(t.getVirtualType()) && !TranCommand.VIRTUAL_TYPE.containsKey(t.getVirtualType())
            )) && goodsNum != null && goodsNum.compareTo(new BigDecimal("1")) > 0)
            {
                if (!Assert.empty(t.getCostpriceTotal()) && !"0".equals(t.getCostpriceTotal()))
                {
                    BigDecimal costpriceTotal = new BigDecimal(t.getCostpriceTotal());
                    costpriceTotal = costpriceTotal.divide(goodsNum, 2, RoundingMode.DOWN);
                    t.setCostpriceTotal(costpriceTotal.toString());
                }
                if (!Assert.empty(t.getBankPointsValueRealAll()) && !"0".equals(t.getBankPointsValueRealAll()))
                {
                    BigDecimal bankPoints = new BigDecimal(t.getBankPointsValueRealAll());
                    bankPoints = bankPoints.divide(goodsNum, 2, RoundingMode.DOWN);
                    t.setBankPointsValueRealAll(bankPoints.toString());
                }
                if (!Assert.empty(t.getFftPointsValueRealAll()) && !"0".equals(t.getFftPointsValueRealAll()))
                {
                    BigDecimal fftPoints = new BigDecimal(t.getFftPointsValueRealAll());
                    fftPoints = fftPoints.divide(goodsNum, 2, RoundingMode.DOWN);
                    t.setFftPointsValueRealAll(fftPoints.toString());
                }
                if (!Assert.empty(t.getCurrencyValueRealAll()) && !"0".equals(t.getCurrencyValueRealAll()))
                {
                    BigDecimal currencyValue = new BigDecimal(t.getCurrencyValueRealAll());
                    currencyValue = currencyValue.divide(goodsNum, 2, RoundingMode.DOWN);
                    t.setCurrencyValueRealAll(currencyValue.toString());
                }
            }
            newList.add(t);
        }
        pager.getList().clear();
        pager.getList().addAll(newList);
        return Action.SUCCESS;
    }

    public String queryPhysicShopTran()
    {
        if (pager == null) pager = new Trans();
        pager.setPageSize(10);//每页10条
        Map<String, Object> session = ActionContext.getContext().getSession();
        String userId = (String) session.get("userId");
        pager.setUserId(userId);
        //pager.setTransType(TranCommand.POINTS_REBATE);//积分返利："03"

        //添加支付方式查询条件
        pager.getPayMethodList().add(TranCommand.POINTS_REBATE);//方付通积分支付
        pager.getPayMethodList().add(TranCommand.COLLECT);//银行积分支付
        pager.getPayMethodList().add(TranCommand.PRESENT_POINTS);//现金支付

        pager.setOrderType(OrderType.DESC);
        pager = transActionSupport.getTransByPager(pager);

        return Action.SUCCESS;
    }

    /**
     * 1.查询资金渠道银行(珠海农行)
     * 2.查询提现认证账户信息
     *
     * @return
     */
    public String queryFundsChannels()
    {
        //查看是否已经绑定的银行卡信息，若有就查出来
        String userId = (String) getSession("userId");
        userCertification = userCertificationActionSupport.getUserCertificationBind(userId);
        if (userCertification == null||StringUtils.isEmpty(userCertification.getUserIdCard()))
        {
            fundsChannelList = transActionSupport.queryFundsChannel(new FundsChannel());
            fundsChannel = fundsChannelList.get(0);
            return "bind_new";
        }
        else
        {
            fundsChannelList = transActionSupport.queryFundsChannel(new FundsChannel());
            fundsChannel = fundsChannelList.get(0);
            String accountName = userCertification.getAccountName();
            String temp = "";
            for (int i = 0; i < accountName.length() - 1; i++)
            {
                temp += "*";
            }
            temp += accountName.charAt(accountName.length() - 1);
            userCertification.setAccountName(temp);

            String accountNo = userCertification.getAccountNo();
            temp = accountNo.substring(0, 3);
            for (int i = 0; i < accountNo.length() - 6; i++)
            {
                temp += "*";
            }
            temp += accountNo.substring(accountNo.length() - 3, accountNo.length());
            userCertification.setAccountNo(temp);
            
            String Idcard = userCertification.getUserIdCard();
            if(Idcard!=null && !"".equals(Idcard)){
                temp = Idcard.substring(0, 4);
                for (int i = 0; i < Idcard.length() - 8; i++)
                {
                    temp += "*";
                }
                temp += Idcard.substring(Idcard.length() - 4, Idcard.length());
                userCertification.setUserIdCard(temp);
            }

            
            errorMsg = "你已经有绑定过的银行账户";
            return "bind_old";
        }
    }

    public boolean isCertifiedFn()
    {
        String result = queryFundsChannels();
        if ("bind_new".equals(result))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * 1.查询资金渠道银行(珠海农行)
     *
     * @return
     */
    public String toBindingNewAccount()
    {
        fundsChannelList = transActionSupport.queryFundsChannel(new FundsChannel());
        //userCertification = new UserCertification();
        if (fundsChannelList == null || fundsChannelList.size() == 0)
        {
            return "failse";
        }
        fundsChannel = fundsChannelList.get(0);
        errorMsg = "";
        return "success";
    }

    private boolean check()
    {
        boolean result = false;
        if (Assert.empty(userCertification.getAccountName()))
        {
            errorMsg = "账户名为空！";
            return result;
        }
        if (Assert.empty(userCertification.getAccountNo()))
        {
            errorMsg = "账户号为空！";
            return result;
        }
        if (Assert.empty(userCertification.getChannelId()))
        {
            errorMsg = "未选择渠道！";
            return result;
        }
        List<FundsChannel> fundsChannelList = transActionSupport.queryFundsChannel(new FundsChannel());
        if (Assert.empty(fundsChannelList))
        {
            errorMsg = "系统没有渠道！";
            return result;
        }
        for (FundsChannel fundsChannel : fundsChannelList)
        {
            if (String.valueOf(fundsChannel.getId()).equals(userCertification.getChannelId()))
            {
                result = true;
                break;
            }
        }
        if (!result) errorMsg = "未选择渠道！";
        return result;
    }

    /**
     * 提现认证
     *
     * @return
     */
    public String bindingBankAccount()
    {
        logger.info("提现认证开始");
        String userId = (String) getSession("userId");
        User user = userActionSupport.queryUserAllByUserID(userId);
        if (userCertification == null || Assert.empty(userCertification.getAccountName()) || Assert.empty(userCertification.getAccountNo()))
        {
            fundsChannelList = transActionSupport.queryFundsChannel(new FundsChannel());
            fundsChannel = fundsChannelList.get(0);
            logger.info("提现认证参数错误");
            errorMsg = "请输入正确的账户名，账户号！";
            return Action.ERROR;
        }
        userCertification.setCertificationType(TranCommand.CHECK_CARD);
        userCertification.setState(Command.STATE_START);
        boolean flag = userCertificationActionSupport.checkAccount(userCertification);
        if (flag)
        {
            logger.info("银行卡验证通过");
            fundsChannelList = transActionSupport.queryFundsChannel(new FundsChannel());
            userCertification.setUserId(user.getUserID());
            userCertification.setCertificationResult("1");
            userCertification.setPhone(user.getMobilephone());
            if (fundsChannelList != null)
            {
                userCertification.setChannelId(String.valueOf(fundsChannelList.get(0).getId()));
            }
            Integer num = userCertificationActionSupport.saveBingAccount(userCertification);
            if (num != null && num > 0)
            {
                logger.info("认证成功");
                errorMsg = "认证成功！";
                return Action.SUCCESS;
            }
            else
            {
                logger.info("认证失败");
                errorMsg = "认证失败！";
                fundsChannelList = transActionSupport.queryFundsChannel(new FundsChannel());
                fundsChannel = fundsChannelList.get(0);
                return Action.ERROR;
            }
        }
        else
        {
            logger.info("银行卡验证不通过");
            fundsChannelList = transActionSupport.queryFundsChannel(new FundsChannel());
            fundsChannel = fundsChannelList.get(0);
            errorMsg = "账号信息验证失败!";
            return Action.ERROR;
        }
    }

    /**
     * 精品预售查询
     *
     * @return
     */
    public String queryPresellTran()
    {
        if (pager == null) pager = new Trans();
        pager.setPageSize(10);//每页10条
        Map<String, Object> session = ActionContext.getContext().getSession();
        String userId = (String) session.get("userId");
        //		Buyers buyer=buyersActionSupport.getBuyerByUserId(userId);
        //		if(buyer==null)
        //			return Action.SUCCESS;
        pager.setUserId(userId);
        pager.setTransType(TranCommand.PRE_SELL);//我的团购："02"

        pager.setOrderType(OrderType.DESC);
        pager = transActionSupport.getGroupOrExchangeTransByPager(pager);
        //更新交易积分和价格信息改为单个商品的积分和价格
        List<Object> templist = pager.getList();
        List<Trans> newList = new ArrayList<Trans>();
        for (int i = 0; i < templist.size(); i++)
        {
            Trans t = (Trans) templist.get(i);
            //Integer num = Integer.valueOf(t.getTransDetailsList().get(0).getGoodsNumber());
            BigDecimal goodsNum = new BigDecimal(t.getTransDetailsList().get(0).getGoodsNumber());
            //实体商品（目前只有实体商品会发送消费券）
            if (TranCommand.TRAN_SUCCESS.equals(t.getState()) && (Assert.empty(t.getVirtualType()) || (!Assert.empty(t.getVirtualType()) && !TranCommand.VIRTUAL_TYPE.containsKey(t.getVirtualType())
            )) && goodsNum != null && goodsNum.compareTo(new BigDecimal("1")) > 0)
            {
                //                if (!Assert.empty(t.getCostpriceTotal()) && !"0".equals(t.getCostpriceTotal()))
                //                {
                //                    BigDecimal costpriceTotal = new BigDecimal(t.getCostpriceTotal());
                //                    costpriceTotal = costpriceTotal.divide(goodsNum, 2, RoundingMode.DOWN);
                //                    t.setCostpriceTotal(costpriceTotal.toString());
                //                }
                //                if (!Assert.empty(t.getBankPointsValueRealAll()) && !"0".equals(t.getBankPointsValueRealAll()))
                //                {
                //                    BigDecimal bankPoints = new BigDecimal(t.getBankPointsValueRealAll());
                //                    bankPoints = bankPoints.divide(goodsNum, 2, RoundingMode.DOWN);
                //                    t.setBankPointsValueRealAll(bankPoints.toString());
                //                }
                //                if (!Assert.empty(t.getFftPointsValueRealAll()) && !"0".equals(t.getFftPointsValueRealAll()))
                //                {
                //                    BigDecimal fftPoints = new BigDecimal(t.getFftPointsValueRealAll());
                //                    fftPoints = fftPoints.divide(goodsNum, 2, RoundingMode.DOWN);
                //                    t.setFftPointsValueRealAll(fftPoints.toString());
                //                }
                //                if (!Assert.empty(t.getCurrencyValueRealAll()) && !"0".equals(t.getCurrencyValueRealAll()))
                //                {
                //                    BigDecimal currencyValue = new BigDecimal(t.getCurrencyValueRealAll());
                //                    currencyValue = currencyValue.divide(goodsNum, 2, RoundingMode.DOWN);
                //                    t.setCurrencyValueRealAll(currencyValue.toString());
                //                }
            }
            //交易状态
            TranStateResult stateResult = TransStateUtil.getTranState(t,goodsPreSellRackActionSupport);
            t.setState(stateResult.getColourFlag());
            t.setRemark(stateResult.getStateShow());

            PresellBuyInfo presellBuyInfo = new PresellBuyInfo();
            presellBuyInfo.setTransId(t.getId());
            List<PresellBuyInfo> list = preSellActionSupport.findPreSellBuyInfobyConditions(presellBuyInfo);
            PresellBuyInfo _buy = list.get(0);
            com.froad.client.presellBuyInfo.PresellDelivery delivery = _buy.getPresellDelivery();
            t.setRespCode(delivery.getName() + ":" + delivery.getAddress());
            newList.add(t);
        }
        pager.getList().clear();
        pager.getList().addAll(newList);
        return Action.SUCCESS;
    }

    public TransActionSupport getTransActionSupport()
    {
        return transActionSupport;
    }

    public void setTransActionSupport(TransActionSupport transActionSupport)
    {
        this.transActionSupport = transActionSupport;
    }

    public Trans getPager()
    {
        return pager;
    }

    public void setPager(Trans pager)
    {
        this.pager = pager;
    }

    public UserActionSupport getUserActionSupport()
    {
        return userActionSupport;
    }

    public void setUserActionSupport(UserActionSupport userActionSupport)
    {
        this.userActionSupport = userActionSupport;
    }

    public BuyersActionSupport getBuyersActionSupport()
    {
        return buyersActionSupport;
    }

    public void setBuyersActionSupport(BuyersActionSupport buyersActionSupport)
    {
        this.buyersActionSupport = buyersActionSupport;
    }

    public UserCertificationActionSupport getUserCertificationActionSupport()
    {
        return userCertificationActionSupport;
    }

    public void setUserCertificationActionSupport(UserCertificationActionSupport userCertificationActionSupport)
    {
        this.userCertificationActionSupport = userCertificationActionSupport;
    }

    public UserCertification getUserCertification()
    {
        return userCertification;
    }

    public void setUserCertification(UserCertification userCertification)
    {
        this.userCertification = userCertification;
    }

    public List<FundsChannel> getFundsChannelList()
    {
        return fundsChannelList;
    }

    public void setFundsChannelList(List<FundsChannel> fundsChannelList)
    {
        this.fundsChannelList = fundsChannelList;
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    public SellersActionSupport getSellersActionSupport()
    {
        return sellersActionSupport;
    }

    public void setSellersActionSupport(SellersActionSupport sellersActionSupport)
    {
        this.sellersActionSupport = sellersActionSupport;
    }

    public FundsChannel getFundsChannel()
    {
        return fundsChannel;
    }

    public void setFundsChannel(FundsChannel fundsChannel)
    {
        this.fundsChannel = fundsChannel;
    }

    public String getIsLotteryCertified()
    {
        return isLotteryCertified;
    }

    public void setIsLotteryCertified(String isLotteryCertified)
    {
        this.isLotteryCertified = isLotteryCertified;
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
}
