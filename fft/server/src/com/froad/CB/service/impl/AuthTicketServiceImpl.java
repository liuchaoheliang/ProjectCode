package com.froad.CB.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.jws.WebService;

import com.froad.CB.dao.*;
import com.froad.CB.po.*;
import org.apache.log4j.Logger;

import com.froad.CB.AppException.AppException;
import com.froad.CB.common.Command;
import com.froad.CB.common.SmsLogType;
import com.froad.CB.common.constant.PayCommand;
import com.froad.CB.common.constant.TranCommand;
import com.froad.CB.dao.user.UserDao;
import com.froad.CB.po.user.User;
import com.froad.CB.service.AuthTicketService;
import com.froad.CB.service.MessageService;
import com.froad.util.DateUtil;
import com.froad.util.MessageSourceUtil;
import com.froad.util.Result;
import com.froad.util.DES.DESUtil;

@WebService(endpointInterface = "com.froad.CB.service.AuthTicketService")
public class AuthTicketServiceImpl implements AuthTicketService {

    private static final Logger logger = Logger
            .getLogger(AuthTicketServiceImpl.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd|HH:mm:ss");

    private static final SimpleDateFormat displayFormat = new SimpleDateFormat(
            "yyyy年MM月dd号");

    private static Random random = new Random();

    private AuthTicketDao authTicketDao;

    private UserDao userDao;

    private TransServiceImpl transServiceImpl;

    private MessageService messageService;

    private PresellDeliveryDao presellDeliveryDao;

    /**
     * 方法描述：生成券号(明文)
     *
     * @return: 券号
     * @version: 1.0
     * @author: 李金魁 lijinkui@f-road.com.cn
     * @time: Mar 13, 2013 11:40:50 AM
     */
    private String generateTicketNo() {
        String ticket = (random.nextLong() + "").substring(1, 15);
        if (this.isNotExist(ticket)) {// 数据库不存在此券
            return ticket;
        }
        return generateTicketNo();
    }

    @Override
    public AuthTicket addAuthTicket(AuthTicket authTicket) throws AppException {
        if (authTicket == null || authTicket.getUserId() == null) {
            logger.error("参数为空，添加失败");
            return null;
        }
        String ticket = this.generateTicketNo();
        String time = DateUtil.formatDate2Str(new Date());
        authTicket.setSecuritiesNo(this.encrypt(ticket));
        authTicket.setIsConsume("0");
        authTicket.setSmsNumber(1);
        authTicket.setSmsTime(time);
        authTicket.setCreateTime(time);
        authTicket.setUpdateTime(time);
        authTicket.setState(Command.STATE_START);
        Integer id = authTicketDao.addAuthTicket(authTicket);
        authTicket.setId(id);
        authTicket.setSecuritiesNo(ticket);
        return authTicket;
    }

    @Override
    public boolean deleteById(Integer id) {
        if (id == null) {
            logger.error("编号为空，删除失败");
            return false;
        }
        try {
            authTicketDao.deleteById(id);
            return true;
        } catch (Exception e) {
            logger.error("删除操作异常", e);
            return false;
        }
    }

    @Override
    public AuthTicket getAuthTicketById(Integer id) {
        if (id == null) {
            logger.error("编号为空，查询失败");
            return null;
        }
        AuthTicket ticket = authTicketDao.getAuthTicketById(id);
        if (ticket != null) {
            ticket.setSecuritiesNo(this.decrypt(ticket.getSecuritiesNo()));
        }
        return ticket;
    }

    @Override
    public boolean updateById(AuthTicket authTicket) {
        if (authTicket == null || authTicket.getId() == null) {
            logger.error("参数为空，更新失败");
            return false;
        }
        return authTicketDao.updateById(authTicket);
    }

    @Override
    public boolean updateStateById(Integer id, String state) {
        if (id == null || state == null || "".equals(state)) {
            logger.error("参数为空，更新状态失败");
            return false;
        }
        return authTicketDao.updateStateById(id, state);
    }

    public void setAuthTicketDao(AuthTicketDao authTicketDao) {
        this.authTicketDao = authTicketDao;
    }

    @Override
    public List<AuthTicket> getAuthTickBySelective(AuthTicket authTicket) {
        String securityNo = authTicket.getSecuritiesNo();
        if (securityNo != null && !"".equals(securityNo)) {
            authTicket.setSecuritiesNo(this.encrypt(securityNo));
        }
        List<AuthTicket> list = authTicketDao
                .getAuthTickBySelective(authTicket);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setSecuritiesNo(
                        this.decrypt(list.get(i).getSecuritiesNo()));
            }
            return list;
        }
        return null;
    }

    @Override
    public boolean isNotExist(String securitiesNo) {
        if (securitiesNo != null) {
            securitiesNo = this.encrypt(securitiesNo);
        }
        return authTicketDao.isNotExist(securitiesNo);
    }

    private String encrypt(String str) {
        try {
            DESUtil desUtil = new DESUtil("froad");
            return desUtil.encrypt(str);
        } catch (Exception e) {
            logger.error("加密时出现异常", e);
            return null;
        }
    }

    private String decrypt(String str) {
        try {
            DESUtil desUtil = new DESUtil("froad");
            return desUtil.decrypt(str);
        } catch (Exception e) {
            logger.error("解密时出现异常", e);
            return null;
        }
    }

    @Override
    public Result getAuthTickByAuthId(Integer transId, Integer authId) {
        AuthTicket authTicket = null;
        authTicket = authTicketDao.getAuthTicketById(authId);
        try {
            authTicket.setSecuritiesNo(this.decrypt(authTicket.getSecuritiesNo()));// 解密
            if ("1".equals(authTicket.getIsConsume())) {
                logger.info("**********用于申请重发的验证券 id " + authTicket.getId()
                        + " 已经消费，不予重发短信*******");
                return new Result(Result.FAIL, "对不起，你的验证券已经消费！");
            } else {
                String expireTime = authTicket.getExpireTime();// 获取该证券的过期时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd号");
                String dataCurry = df.format(new Date());
                int resultNum = dataCurry.compareTo(expireTime);
                if (resultNum > 0) {
                    logger.info("**********用于申请重发的验证券id " + authTicket.getId()
                            + " 已经过期,不予再次发送验证短信*******");
                    return new Result(Result.FAIL, "对不起，您的验证券已经过期！");
                } else if (authTicket.getSmsNumber() > 3) {
                    logger.info("**********用于申请重发的验证券id " + authTicket.getId()
                            + " 短信下发次数已达上限,已经发送了" + authTicket.getSmsNumber()
                            + "次不予再次发送验证短信*******");
                    return new Result(Result.FAIL, "对不起，该条验证券下发短信次数已达上限！");
                } else {
                    logger.info("**********用于申请重发的验证券id" + authTicket.getId()
                            + " 短息开始下发*******");
                    logger.info("************下发短信的验证券码为"
                            + authTicket.getSecuritiesNo() + "类型是: "
                            + (authTicket.getType().equals("0") ? "我的团购" : "积分兑换")
                            + "*********");

                    Trans trans = transServiceImpl.getTransById(transId);
                    authTicketDao.updateAuthTickMsgCount(authTicket);// 将短信发送次数+1
                    sendTicket(trans, authTicket);
                    return new Result(Result.SUCCESS, "短信已下发,请注意查收！");
                }
            }
        } catch (Exception e) {
            logger.info("**********error:  证券码：" + authId + "   不存在！非正常方式发送该验证请求**********************");
            return new Result(Result.FAIL, "证券码信息不存在！");
        }

    }

    private void sendTicket(Trans trans, AuthTicket ticketRes) {
        SimpleDateFormat preselFormat  =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")  ;
        String transType = trans.getTransType();// 交易类型
        String clientType = trans.getClientType();// 客户端类型
        String smsLogType = null;// 短信类型
        String goodsName = null;// 商品名
        String beginTime = null;// 开始时间
        String expireTime = null;// 过期时间
        String phone = null;// 商家咨询电话
        String[] msg = null;// 短信的动态内容
        String key = null;// message属性文件里的key值
        TransDetails transDetails = trans.getTransDetailsList().get(0);
        User user = userDao.queryUserAllByUserID(trans.getUserId());
        if (PayCommand.CLIENT_PC.equals(clientType)) {
            if (TranCommand.GROUP.equals(transType)) {
                logger.info("============开始添加团购券=============");
                smsLogType = SmsLogType.SMSLOG_GROUP_TICKET;
                GoodsGroupRack group = transDetails.getGoodsGroupRack();
                goodsName = group.getSeoTitle();
                try {
                    beginTime = displayFormat.format(dateFormat.parse(group
                            .getTicketBeginTime()));
                    expireTime = displayFormat.format(dateFormat.parse(group
                            .getTicketEndTime()));
                } catch (ParseException e) {
                    logger.error("时间格式转换异常，添加券失败", e);
                    return;
                }
                phone = group.getGoods().getMerchant().getMstoreTel();
                key = "group";
            } else if (TranCommand.PRESELL.equals(transType)) {
                logger.info("============开始添加精品预售券=============");
                smsLogType = SmsLogType.PRESELL_TAKE_PRODUCT;

                GoodsPresellRack presellRack = transServiceImpl.getGoodsPresellRackById(transDetails.getGoodsRackId());
                 goodsName=presellRack.getGoods().getGoodsName();
                String endTime = "";
                try {

                    beginTime = displayFormat.format(preselFormat.parse(presellRack
                            .getDeliveryStartTime()));
                    expireTime = displayFormat.format(preselFormat.parse(presellRack
                            .getDeliveryEndTime()));
                    endTime = displayFormat.format(preselFormat.parse(presellRack.getDeliveryEndTime()));
                } catch (ParseException e) {
                    logger.error("时间格式转换异常，添加券失败", e);
                    return;
                }
                phone = presellRack.getGoods().getMerchant().getMstoreTel();
                key = "presell";

                PresellDelivery delivery=presellDeliveryDao.queryByTransId(trans.getId());
                msg=new String[]{goodsName,ticketRes.getSecuritiesNo(),beginTime,endTime,delivery.getName(),delivery.getAddress(),delivery.getTelephone()};

            } else {
                logger.info("============开始添加积分兑换券=============");
                smsLogType = SmsLogType.SMSLOG_EXCHANGE_TICKET;
                GoodsExchangeRack exchange = transDetails
                        .getGoodsExchangeRack();
                goodsName = exchange.getGoods().getGoodsName();
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_MONTH, exchange.getDays());
                expireTime = displayFormat.format(cal.getTime());
                phone = exchange.getGoods().getMerchant().getMstoreTel();
                key = "exchange";
            }
        } else {
            if (TranCommand.GROUP.equals(transType)) {
                logger.info("============开始添加团购券=============");
                smsLogType = SmsLogType.SMSLOG_GROUP_TICKET;
                ClientGoodsGroupRack group = transDetails
                        .getClientGoodsGroupRack();
                goodsName = group.getSeoTitle();
                try {
                    beginTime = displayFormat.format(dateFormat.parse(group
                            .getTicketBeginTime()));
                    expireTime = displayFormat.format(dateFormat.parse(group
                            .getTicketEndTime()));
                } catch (ParseException e) {
                    logger.error("时间格式转换异常，添加券失败", e);
                    return;
                }
                phone = group.getGoods().getMerchant().getMstoreTel();
                key = "group";
            }  else {
                logger.info("============开始添加积分兑换券=============");
                smsLogType = SmsLogType.SMSLOG_EXCHANGE_TICKET;
                ClientGoodsExchangeRack exchange = transDetails
                        .getClientGoodsExchangeRack();
                goodsName = exchange.getGoods().getGoodsName();
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_MONTH, exchange.getDays());
                expireTime = displayFormat.format(cal.getTime());
                phone = exchange.getGoods().getMerchant().getMstoreTel();
                key = "exchange";
            }
        }
        if (TranCommand.GROUP.equals(transType)) {
            msg = new String[]{goodsName, ticketRes.getSecuritiesNo(),
                    beginTime, expireTime, phone};
        }
        //精品预售短信 add by 侯国权,精品预售的msg设置已前置
        else  if(!TranCommand.PRESELL.equals(transType)){
            msg = new String[]{goodsName, ticketRes.getSecuritiesNo(),
                    expireTime, phone};
        }

        String content = MessageSourceUtil.getSource().getMessage(key, msg,
                null);
        logger.info("短信内容："+content);
        SmsLog smsLog = new SmsLog();
        smsLog.setMobile(trans.getPhone() == null ? user.getMobilephone()
                : trans.getPhone());
        smsLog.setMessage(content);
        smsLog.setType(smsLogType);

        messageService.sendMessage(smsLog);
    }


    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public AuthTicketDao getAuthTicketDao() {
        return authTicketDao;
    }

    public TransServiceImpl getTransServiceImpl() {
        return transServiceImpl;
    }

    public void setTransServiceImpl(TransServiceImpl transServiceImpl) {
        this.transServiceImpl = transServiceImpl;
    }

    @Override
    public List<AuthTicket> getAuthTickByTransId(Integer transId) {
        if (transId == null) {
            logger.info("交易号为空");
            return null;
        }

        return authTicketDao.getAuthTickByTransId(transId);
    }

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public PresellDeliveryDao getPresellDeliveryDao()
    {
        return presellDeliveryDao;
    }

    public void setPresellDeliveryDao(PresellDeliveryDao presellDeliveryDao)
    {
        this.presellDeliveryDao = presellDeliveryDao;
    }
}
