/**
 * 文件名称:PayDto.java
 * 文件描述: 支付信息
 * 产品标识: fft
 * 单元描述: fft-api
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-3
 * 历史修改:  
 */
package com.froad.fft.dto;

import com.froad.fft.enums.trans.PayRole;
import com.froad.fft.enums.trans.PayState;
import com.froad.fft.enums.trans.PayType;
import com.froad.fft.enums.trans.PayTypeDetails;

import java.io.Serializable;
import java.util.Date;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class PayDto implements Serializable
{

    private Long id;
    private Date createTime;

    private String sn;//支付编号
    private Long transId;//交易ID
    private PayType payType;//支付类型
    private PayTypeDetails payTypeDetails;//支付类型详情
    private Integer step;//支付步骤
    private PayState payState;//支付状态
    private String payOrg;//支付机构号

    private String billNo;     //openapi平台号
    private String refundOrderId;//openapi退款订单号
    private String pointBillNo;//积分平台号
    private String refundPointNo;//积分平台退分号

    private String payValue;//支付值
    private String fromAccountName;//从哪个账户名
    private String fromAccountNo;//从哪个账户号
    private String toAccountName;//到哪个账户名
    private String toAccountNo;//到哪个账户号
    private String fromPhone;//从哪个手机银行
    private String toPhone;//到哪个手机银行
    private PayRole fromRole;//从哪个角色
    private PayRole toRole;//到哪个角色
    private String fromUserName;//从哪个用户名
    private String toUserName;//到哪个用户名
    private Long merchantId;// 收款方商户Id
    private String resultCode;//结果码
    private String resultDesc;//结果码描述
    private String remark;//备注

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public String getSn()
    {
        return sn;
    }

    public void setSn(String sn)
    {
        this.sn = sn;
    }

    public Long getTransId()
    {
        return transId;
    }

    public void setTransId(Long transId)
    {
        this.transId = transId;
    }

    public PayType getPayType()
    {
        return payType;
    }

    public void setPayType(PayType payType)
    {
        this.payType = payType;
    }

    public PayTypeDetails getPayTypeDetails()
    {
        return payTypeDetails;
    }

    public void setPayTypeDetails(PayTypeDetails payTypeDetails)
    {
        this.payTypeDetails = payTypeDetails;
    }

    public Integer getStep()
    {
        return step;
    }

    public void setStep(Integer step)
    {
        this.step = step;
    }

    public PayState getPayState()
    {
        return payState;
    }

    public void setPayState(PayState payState)
    {
        this.payState = payState;
    }

    public String getPayOrg()
    {
        return payOrg;
    }

    public void setPayOrg(String payOrg)
    {
        this.payOrg = payOrg;
    }

    public String getBillNo()
    {
        return billNo;
    }

    public void setBillNo(String billNo)
    {
        this.billNo = billNo;
    }

    public String getRefundOrderId()
    {
        return refundOrderId;
    }

    public void setRefundOrderId(String refundOrderId)
    {
        this.refundOrderId = refundOrderId;
    }

    public String getPointBillNo()
    {
        return pointBillNo;
    }

    public void setPointBillNo(String pointBillNo)
    {
        this.pointBillNo = pointBillNo;
    }

    public String getRefundPointNo()
    {
        return refundPointNo;
    }

    public void setRefundPointNo(String refundPointNo)
    {
        this.refundPointNo = refundPointNo;
    }

    public String getPayValue()
    {
        return payValue;
    }

    public void setPayValue(String payValue)
    {
        this.payValue = payValue;
    }

    public String getFromAccountName()
    {
        return fromAccountName;
    }

    public void setFromAccountName(String fromAccountName)
    {
        this.fromAccountName = fromAccountName;
    }

    public String getFromAccountNo()
    {
        return fromAccountNo;
    }

    public void setFromAccountNo(String fromAccountNo)
    {
        this.fromAccountNo = fromAccountNo;
    }

    public String getToAccountName()
    {
        return toAccountName;
    }

    public void setToAccountName(String toAccountName)
    {
        this.toAccountName = toAccountName;
    }

    public String getToAccountNo()
    {
        return toAccountNo;
    }

    public void setToAccountNo(String toAccountNo)
    {
        this.toAccountNo = toAccountNo;
    }

    public String getFromPhone()
    {
        return fromPhone;
    }

    public void setFromPhone(String fromPhone)
    {
        this.fromPhone = fromPhone;
    }

    public String getToPhone()
    {
        return toPhone;
    }

    public void setToPhone(String toPhone)
    {
        this.toPhone = toPhone;
    }

    public PayRole getFromRole()
    {
        return fromRole;
    }

    public void setFromRole(PayRole fromRole)
    {
        this.fromRole = fromRole;
    }

    public PayRole getToRole()
    {
        return toRole;
    }

    public void setToRole(PayRole toRole)
    {
        this.toRole = toRole;
    }

    public String getFromUserName()
    {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName)
    {
        this.fromUserName = fromUserName;
    }

    public String getToUserName()
    {
        return toUserName;
    }

    public void setToUserName(String toUserName)
    {
        this.toUserName = toUserName;
    }

    public Long getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(Long merchantId)
    {
        this.merchantId = merchantId;
    }

    public String getResultCode()
    {
        return resultCode;
    }

    public void setResultCode(String resultCode)
    {
        this.resultCode = resultCode;
    }

    public String getResultDesc()
    {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc)
    {
        this.resultDesc = resultDesc;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }
}
