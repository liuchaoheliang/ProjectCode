/**
 * 文件名称:TransStateUtil.java
 * 文件描述: 交易状态处理
 * 产品标识: 分分通
 * 单元描述: 分分通client
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-16
 * 历史修改:  
 */
package com.froad.action.utils;

import com.froad.action.support.GoodsPreSellRackActionSupport;
import com.froad.client.goodsPresellRack.GoodsPresellRack;
import com.froad.client.trans.AuthTicket;
import com.froad.client.trans.Pay;
import com.froad.client.trans.Trans;
import com.froad.common.PayState;
import com.froad.common.TranCommand;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class TransStateUtil
{
    public static TranStateResult getTranState(Trans trans, GoodsPreSellRackActionSupport goodsPreSellRackActionSupport)
    {
        String stateShow = "处理中";
        String tranState = trans.getState();
        String colourFlag = "b";//绿

        Trans t = trans;
        //交易关闭
        //如果前面的状态全部落空，则关闭交易
        if (TranCommand.TRAN_CLOSE.equals(tranState))
        {
            List<Pay> payList = t.getPayList();
            Pay pay = payList.get(payList.size() - 1);

            if (PayState.POINT_REFUND_SUCCESS.equals(pay.getState()) || PayState.REFUND_SUCCESS.equals(pay.getState()))
            {
                stateShow = "退款成功";
                colourFlag = "r";
            }
            else
            {
                stateShow = "已关闭";
                colourFlag = "r";
            }
        }
        else
        {
            List<Pay> payList = t.getPayList();
            if (null == t.getTransDetailsList() || 0 >= t.getTransDetailsList().size())
            {
                stateShow = "交易数据明细异常！";
            }
            //已支付
            if (null != payList && 0 < payList.size())
            {
                //获取最后一条交易记录
                Pay pay = payList.get(payList.size() - 1);
                String rackId = t.getTransDetailsList().get(0).getGoodsRackId();
                GoodsPresellRack rack = goodsPreSellRackActionSupport.getGoodsPresellRackById(Integer.valueOf(rackId));
                Date serverTime = goodsPreSellRackActionSupport.getServerDate();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                boolean validFlag = false;
                String deliveryFlag = "0";

                try
                {
                    Date endTime = df.parse(rack.getEndTime());
                    Date deliveryStartTime = df.parse(rack.getDeliveryStartTime());
                    Date deliveryEndTime = df.parse(rack.getDeliveryEndTime());

                    if (serverTime.before(endTime))
                    {
                        validFlag = true;
                    }

                    if (deliveryStartTime.before(serverTime) && serverTime.before(deliveryEndTime))
                    {
                        deliveryFlag = "1";
                    }
                    else if (serverTime.after(deliveryEndTime))
                    {
                        deliveryFlag = "2";
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

                if (!PayState.PAY_WAIT.equals(pay.getState()) && !PayState.PAY_REQUEST_SUCCESS.equals(pay.getState()) && !PayState.PAY_REQUEST_FAIL.equals(pay.getState()))
                {
                    //未关闭
                    if (validFlag)
                    {
                        //只是在时间范围内，需要进一步检查支付状态

                        if (PayState.POINT_PAY_SUCCESS.equals(pay.getState()) || PayState.PAY_SUCCESS.equals(pay.getState()))
                        {
                            stateShow = "已付款，等待成团";
                        }
                        else if (PayState.PAY_REQUEST_SUCCESS.equals(pay.getState()))
                        {
                            stateShow = "已申请，等待付款";

                        }
                        else
                        {
                            stateShow = "付款失败";

                        }
                    }
                    else
                    {
                        //不是2001和1004,均未完成
                        if (!PayState.PAY_SUCCESS.equals(pay.getState()) && !PayState.POINT_PAY_SUCCESS.equals(pay.getState()))
                        {

                            if (PayState.POINT_REFUND_SUCCESS.equals(pay.getState()) || PayState.REFUND_SUCCESS.equals(pay.getState()))
                            {
                                stateShow = "退款成功";
                            }
                            else if (PayState.POINT_REFUND_FAIL.equals(pay.getState()) || PayState.REFUND_FAIL.equals(pay.getState()) || PayState.REFUND_REQ_FAIL.equals(pay.getState()) || PayState
                                    .REFUND_EXCEPTION.equals(pay.getState()) || PayState.REFUND_REQ_SUCCESS.equals(pay.getState()))
                            {
                                stateShow = "退款中";
                            }
                            else
                            {

                                stateShow = "已关闭";
                            }

                        }
                        else
                        {
                            //未成团，退款
                            if (rack.getClusteringNum() > (rack.getTrueBuyerNum() + rack.getVirtualBuyerNum()))
                            {
                                stateShow = "未成团，退款中";
                                colourFlag = "r";
                                if (PayState.REFUND_SUCCESS.equals(pay.getState()))
                                {
                                    stateShow = "未成团，已退款";
                                }
                            }
                            //已成团
                            else
                            {
                                stateShow = "已成团，正在备货";
                                if ("1".equals(deliveryFlag))
                                {
                                    stateShow = "已成团，提货中";
                                    List<AuthTicket> authTicketList = t.getAuthTicketList();
                                    if (null != authTicketList && 0 < authTicketList.size())
                                    {
                                        AuthTicket authTicket = authTicketList.get(0);
                                        if ("1".equals(authTicket.getIsConsume()))
                                        {
                                            stateShow = "已成团，提货已完成";
                                            colourFlag = "g";

                                        }
                                    }
                                }
                                //提货已结束
                                if ("2".equals(deliveryFlag))
                                {
                                    stateShow = "已成团，提货已过期";
                                    colourFlag = "r";
                                    List<AuthTicket> authTicketList = t.getAuthTicketList();
                                    if (null != authTicketList && 0 < authTicketList.size())
                                    {
                                        AuthTicket authTicket = authTicketList.get(0);
                                        if ("1".equals(authTicket.getIsConsume()))
                                        {
                                            stateShow = "已成团，提货已完成";
                                            colourFlag = "g";

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                else
                {
                    stateShow = "已申请，等待付款";
                }

            }
        }


        TranStateResult result = new TranStateResult();


        result.setStateShow(stateShow);
        result.setColourFlag(colourFlag);
        result.setTranState(tranState);
        return result;
    }

}
