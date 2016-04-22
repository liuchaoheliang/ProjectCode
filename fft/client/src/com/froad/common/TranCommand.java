package com.froad.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class TranCommand
{

    /**
     * 用于积分兑换和团购交易(收款时会用到第一个)*
     */
    public static final String COLLECT_CASH = "000";//资金支付
    public static final String PAY_FFT_POINTS = "001";// 分分通积分支付
    public static final String PAY_BANK_POINTS = "002";//银行积分支付

    /**
     * 用于积分返利和纯返积分*
     */
    public static final String BUY_POINTS_CASH = "100";// 付购买积分的金额
    public static final String BUY_POINTS_FEE = "101";// 付购买积分的手续费
    public static final String SEND_POINTS = "102";// 给买家返分分通积分

    /**
     * 用于积分提现*
     */
    public static final String DEDUCT_POINTS = "200";// 积分提现-扣分分通积分
    public static final String TRANSFER_FOR_BUYER = "201";// 积分提现-给买家充钱
    public static final String DEDUCT_FEE = "202";// 积分提现-扣提现手续费

    /**
     * 用于用户签到时赠送积分*
     */
    public static final String CHECK_IN_PRESENT_POINTS = "301";//分分通给签到用户送积分

    /*******************************************************************************/
    /**
     * 资金流转记录表(pay表)里的type字段：支付现金*
     */
    public static String TYPE_CASH = "00";//支付现金

    /**
     * 资金流转记录表(pay表)里的type字段：扣积分*
     */
    public static String TYPE_POINTS = "01";//支付积分

    /**
     * 资金流转记录表(pay表)里的type字段：彩票**
     */
    public static String TYPE_LOTTERY = "10";

    /**
     * 资金流转记录表(pay表)里的type字段：话费充值**
     */
    public static String TYPE_HFCZ = "11";

    /**
     * 交易状态state*
     */
    public static final String TRAN_PROCESSING = "01"; //交易处理中
    public static final String TRAN_SUCCESS = "02"; //交易成功
    public static final String TRAN_FAIL = "03";//交易失败
    public static final String TRAN_CLOSE = "04";//交易关闭

    public static Map<String, String> TRAN_STATUS_MAP = new HashMap<String, String>();

    static
    {
        TRAN_STATUS_MAP.put(TRAN_PROCESSING, "处理中");
        TRAN_STATUS_MAP.put(TRAN_SUCCESS, "成功");
        TRAN_STATUS_MAP.put(TRAN_FAIL, "失败");
        TRAN_STATUS_MAP.put(TRAN_CLOSE, "关闭");
    }

    /**
     * 交易类型transType*
     */
    public static final String POINTS_EXCH_PRODUCT = "01";//积分兑换
    public static final String GROUP = "02";//团购交易
    public static final String POINTS_REBATE = "03";//返利积分
    public static final String POINTS_EXCH_CASH = "04";//积分提现交易
    public static final String COLLECT = "05";//纯收款交易
    public static final String PRESENT_POINTS = "06";//送积分交易
    public static final String CHECK_IN = "07";//签到交易(用户签到送积分)
    public static final String PRE_SELL = "08";//精品预售
    public static final List<String> TRANS_TYPES = new ArrayList<String>();

    static
    {
        TRANS_TYPES.add(POINTS_EXCH_PRODUCT);
        TRANS_TYPES.add(GROUP);
        TRANS_TYPES.add(POINTS_REBATE);
        TRANS_TYPES.add(POINTS_EXCH_CASH);
        TRANS_TYPES.add(COLLECT);
        TRANS_TYPES.add(PRESENT_POINTS);
        TRANS_TYPES.add(CHECK_IN);
        TRANS_TYPES.add(PRE_SELL);
    }

    /**
     * ********虚拟商品类目ID的hashMap******begin*****
     */
    public static Map<String, String> VIRTUAL_TYPE = null;

    static
    {
        VIRTUAL_TYPE = new HashMap<String, String>();

        VIRTUAL_TYPE.put("100001005", "彩票");
        VIRTUAL_TYPE.put("100001006", "话费充值");
    }
    /***********虚拟商品类目ID的hashMap******end******/

    /**
     * 支付方式payMethod*
     */
    public static final String POINTS_FFT = "00";//方付通积分支付
    public static final String POINTS_BANK = "01";//银行积分支付
    public static final String CASH = "02";//现金支付
    public static final String POINTS_FFT_CASH = "03";//方付通积分+现金
    public static final String POINTS_BANK_CASH = "04";//银行积分+现金
    public static final String POINTS_FFT_CASH_SCOPE = "05";//方付通积分+现金 范围
    public static final String POINTS_BANK_CASH_SCOPE = "06";//银行积分+现金  范围
    public static final String POINTS_FFT_ALPAY_SCOPE = "07";//方付通积分+支付宝 范围
    public static final String POINTS_BANK_ALPAY_SCOPE = "08";//银行积分+支付宝  范围
    public static final String ALPAY = "09";//支付宝
    public static final List<String> PAY_METHODS = new ArrayList<String>();

    static
    {
        PAY_METHODS.add(POINTS_FFT);
        PAY_METHODS.add(POINTS_BANK);
        PAY_METHODS.add(CASH);
        PAY_METHODS.add(POINTS_FFT_CASH);
        PAY_METHODS.add(POINTS_BANK_CASH);
        PAY_METHODS.add(POINTS_FFT_CASH_SCOPE);
        PAY_METHODS.add(POINTS_BANK_CASH_SCOPE);
        PAY_METHODS.add(POINTS_FFT_ALPAY_SCOPE);
        PAY_METHODS.add(POINTS_BANK_ALPAY_SCOPE);
        PAY_METHODS.add(ALPAY);
    }

    /**
     * 支付渠道payChannel*
     */
    public static final String PAY_CHANNEL_PHONE = "1";//手机贴膜卡
    public static final String PAY_CHANNEL_CARD = "2";//银行卡
    public static final String PAY_CHANNEL_ALIPAY="3";//支付宝支付
    public static final List<String> PAY_CHANNELS = new ArrayList<String>();

    static
    {
        PAY_CHANNELS.add(PAY_CHANNEL_PHONE);
        PAY_CHANNELS.add(PAY_CHANNEL_CARD);
        PAY_CHANNELS.add(PAY_CHANNEL_ALIPAY);
    }

    /**
     * ****异常信息的前缀******
     */
    public static final String EXCEPTION_PREFIX = "银行异常:";

    /**
     * 交易品属性ID:goodsRackAttribute表的id*
     */
    public static final String ID_PHONE_NO = "100001001";//充值号码
    public static final String ID_ADDRESS = "100001002";//充值号码归属地
    public static final String ID_OPERATOR = "100001003";//运营商
    public static final String ID_PRICE = "100001004";//面值

    /**
     * 彩票的交易属性*
     */
    public static final String ID_AWD_AMOUNT = "100001008";//中奖金额
    public static final String ID_PRIZE_GRADE = "100001014";//中奖等级
    public static final String ID_PRIZE_COUNT = "100001015";//中奖注数

    public static final String ID_NUM_COUNT = "100001006";//投注注数
    public static final String ID_PERIOAD = "100001005";//投注期号
    public static final String ID_NUMBER = "100001007";//投注号码
    public static final String ID_LOTTERY_NO = "100001009";//彩票编码
    public static final String ID_PLAY_TYPE = "100001010";//玩法
    public static final String ID_BUY_AMOUNT = "100001011";//投注倍数
    public static final String ID_AMOUNT = "100001012";//投注金额
    public static final String ID_LOTTERY_PHONE = "100001013";//联系电话
    public static final String ID_NUM_TYPE = "100001016";//单复和合胆

    /**
     * 商品分类表ID:goodsCategory表的id*
     */
    public static final String CATEGORY_REBATE = "100001001";//返利商品
    public static final String CATEGORY_VIRTUAL = "100001002";//虚拟产品
    public static final String CATEGORY_LOTTORY = "100001005";//彩票
    public static final String CATEGORY_HFCZ = "100001006";//话费充值
    public static final String CATEGORY_PRODUCT = "100001003";//农特产品
    public static final String CATEGORY_WITHDRAW = "100001007";//积分提现
    public static final String CATEGORY_GROUP = "100001008";//团购商品

    /**
     * 绑定买家时用到的响应码***
     */
    public static final String RESP_CODE_IS_USER = "00";//成功绑定用户
    public static final String RESP_CODE_IS_BUYER = "01";//成功绑定买家
    public static final String RESP_CODE_FAIL = "02";//绑定失败

    /**
     * 交易相关的响应码**
     */
    public static final String RESP_CODE_CREATE_OK = "10";//交易组装成功
    public static final String RESP_CODE_CREATE_FAIL = "11";//交易组装失败
    public static final String RESP_CODE_ADD_OK = "01";//交易添加成功
    public static final String RESP_CODE_ADD_FAIL = "02";//交易添加失败
    public static final String RESP_CODE_PAY_REQ_OK = "03";//支付请求发送成功
    public static final String RESP_CODE_PAY_REQ_FAIL = "04";//支付请求发送失败
    public static final String RESP_CODE_EXCEPTION = "05";//异常

    /**
     * ****账户校验时用到checkType*******
     */
    public static final String CHECK_PHONE = "1";//验证手机号

    public static final String CHECK_CARD = "2";//验证银行卡

    /**
     * openapi版本号*
     */
    public static final String VERSION = "1.1.0";

    /**
     * 账户校验或查询接口成功*
     */
    public static final String SUCCESS = "00";

    /**
     * *******************读取交易属性文件里的配置信息***********************
     */
    public static String CHANNEL_ID;//资金渠道表(FundsChannel)中的主键编号
    public static String FFT_ORG_NO;//分分通积分机构号
    public static String BANK_ORG_NO;//银行积分机构号
    public static String POINTS_CASH_RATE;//积分现金比例 用于提现
    public static String PRICING_FFT_CASH;//分分通积分和现金的比例 用于上架商品的定价
    public static String PRICING_BANK_CASH;//#银行积分和现金的比例 用于上架商品的定价
    /**
     * 合作伙伴ID*
     */
    public static String PARTNER_ID;

    static
    {
        Properties props = new Properties();
        InputStream inputStream = TranCommand.class.getClassLoader().getResourceAsStream("trans.properties");
        try
        {
            props.load(inputStream);
            inputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        CHANNEL_ID = props.getProperty("CHANNEL_ID");
        FFT_ORG_NO = props.getProperty("FFT_ORG_NO");
        BANK_ORG_NO = props.getProperty("BANK_ORG_NO");
        POINTS_CASH_RATE = props.getProperty("POINTS_CASH_RATE");
        PRICING_FFT_CASH = props.getProperty("PRICING_FFT_CASH");
        PRICING_BANK_CASH = props.getProperty("PRICING_BANK_CASH");
        PARTNER_ID = props.getProperty("PARTNER_ID");
    }

    public static void main(String[] args)
    {
        System.out.println(CHANNEL_ID);
    }

}
