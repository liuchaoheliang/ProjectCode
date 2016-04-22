package com.froad.CB.po.bill;

/**
 * 文件名称:CombineResponse.java
 * 文件描述: 订单合并请求返回报文
 * 产品标识: 分分通
 * 单元描述: server
 * 编写人: gq.hou_Mimosa
 * 编写时间: 14-3-4
 * 历史修改:
 */
public class CombineResponse
{
    /**
     * 订单编号
     */
    public String orderID;
    /**
     * 支付链接地址
     */
    public String paymentURL;
    /**
     * 方付通支付号
     */
    public String froadBillNo;
    /**
     * 结果码
     */
    public String resultCode;
    /**
     * 签名类型
     */
    public String signType;
    /**
     * 签名字符串
     */
    public String signMsg;

    public String getOrderID()
    {
        return orderID;
    }

    public void setOrderID( String orderID )
    {
        this.orderID = orderID;
    }

    public String getPaymentURL()
    {
        return paymentURL;
    }

    public void setPaymentURL( String paymentURL )
    {
        this.paymentURL = paymentURL;
    }

    public String getFroadBillNo()
    {
        return froadBillNo;
    }

    public void setFroadBillNo( String froadBillNo )
    {
        this.froadBillNo = froadBillNo;
    }

    public String getResultCode()
    {
        return resultCode;
    }

    public void setResultCode( String resultCode )
    {
        this.resultCode = resultCode;
    }

    public String getSignType()
    {
        return signType;
    }

    public void setSignType( String signType )
    {
        this.signType = signType;
    }

    public String getSignMsg()
    {
        return signMsg;
    }

    public void setSignMsg( String signMsg )
    {
        this.signMsg = signMsg;
    }
}
