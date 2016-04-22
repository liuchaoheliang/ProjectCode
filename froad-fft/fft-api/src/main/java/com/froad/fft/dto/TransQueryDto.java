/**
 * 文件名称:TransQueryDto.java
 * 文件描述: 交易查询dtobean
 * 产品标识: fft
 * 单元描述: fft-api
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-3
 * 历史修改:  
 */
package com.froad.fft.dto;

import com.froad.fft.enums.PayMethodShow;
import com.froad.fft.enums.trans.TransCreateSource;
import com.froad.fft.enums.trans.TransPayChannel;
import com.froad.fft.enums.trans.TransPayState;
import com.froad.fft.enums.trans.TransState;
import com.froad.fft.enums.trans.TransType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class TransQueryDto implements Serializable
{

    private Long id;
    private Date createTime;

    private String sn;//编号
    private TransCreateSource createSource;// 交易创建来源
    private TransType type;// 交易类型
    private PayMethodShow payMethod;// 支付方式
    private TransPayChannel payChannel;//支付渠道
    private TransState state;// 交易状态
    private Long memberCode;// 账户服务ID(买东西的用户或者返积分的用户)
    private TransPayState payState;//支付状态
    private Long clientId;//客户端编号(SysClient主键)
    private String clientNumber;//三位数的客户端号(瞬态字段)

    //现金或者积分
    private String totalPrice;// 总货币值
    private String realPrice;// 实际总货币数值
    private String fftPoints;// 消费或提现的分分通积分
    private String bankPoints;// 银行积分
    private String pointsAmountValue;//买积分所需总金额(总金额=实际金额+手续费)
    private String pointsAmountRealValue;//买积分所需实际金额
    private String buyPoints;//购买的积分数
    private String buyPointsFactorage;// 买积分手续费
    private String gatheringValue;//收款金额
    private String pointsWithdrawFactorage;// 积分提现手续费
    private String givePoints; // 赠送积分数(积分兑换商品送积分)

    private Long merchantId;//商户编号(商户发起的交易)

    private String reason;//事由

    private String filmMobile;//付款贴膜卡手机号

    private String phone;//用于接收券短信的手机号
    private String remark;//备注
    
    private SysUserDto sysUserDto;//存放操作员信息

    private Long deliveryId;//提货点编号
    private String deliveryName;//提货人姓名
    
    private List<TransDetailDto> transDetailDto;//关联商品详情

    private List<Long> sysUserIds;//操作员集合
    
    private List<TransPayState> payStates;
    
    private RefundsDto refundsDto;//是否申请退款

	public RefundsDto getRefundsDto() {
		return refundsDto;
	}

	public void setRefundsDto(RefundsDto refundsDto) {
		this.refundsDto = refundsDto;
	}

	public List<TransPayState> getPayStates() {
		return payStates;
	}

	public void setPayStates(List<TransPayState> payStates) {
		this.payStates = payStates;
	}

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

    public TransCreateSource getCreateSource()
    {
        return createSource;
    }

    public void setCreateSource(TransCreateSource createSource)
    {
        this.createSource = createSource;
    }

    public TransType getType()
    {
        return type;
    }

    public void setType(TransType type)
    {
        this.type = type;
    }

    public PayMethodShow getPayMethod()
    {
        return payMethod;
    }

    public void setPayMethod(PayMethodShow payMethod)
    {
        this.payMethod = payMethod;
    }

    public TransPayChannel getPayChannel()
    {
        return payChannel;
    }

    public void setPayChannel(TransPayChannel payChannel)
    {
        this.payChannel = payChannel;
    }

    public TransState getState()
    {
        return state;
    }

    public void setState(TransState state)
    {
        this.state = state;
    }

    public Long getMemberCode()
    {
        return memberCode;
    }

    public void setMemberCode(Long memberCode)
    {
        this.memberCode = memberCode;
    }

    public TransPayState getPayState()
    {
        return payState;
    }

    public void setPayState(TransPayState payState)
    {
        this.payState = payState;
    }

    public Long getClientId()
    {
        return clientId;
    }

    public void setClientId(Long clientId)
    {
        this.clientId = clientId;
    }

    public String getClientNumber()
    {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber)
    {
        this.clientNumber = clientNumber;
    }

    public String getTotalPrice()
    {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice)
    {
        this.totalPrice = totalPrice;
    }

    public String getRealPrice()
    {
        return realPrice;
    }

    public void setRealPrice(String realPrice)
    {
        this.realPrice = realPrice;
    }

    public String getFftPoints()
    {
        return fftPoints;
    }

    public void setFftPoints(String fftPoints)
    {
        this.fftPoints = fftPoints;
    }

    public String getBankPoints()
    {
        return bankPoints;
    }

    public void setBankPoints(String bankPoints)
    {
        this.bankPoints = bankPoints;
    }

    public String getPointsAmountValue()
    {
        return pointsAmountValue;
    }

    public void setPointsAmountValue(String pointsAmountValue)
    {
        this.pointsAmountValue = pointsAmountValue;
    }

    public String getPointsAmountRealValue()
    {
        return pointsAmountRealValue;
    }

    public void setPointsAmountRealValue(String pointsAmountRealValue)
    {
        this.pointsAmountRealValue = pointsAmountRealValue;
    }

    public String getBuyPoints()
    {
        return buyPoints;
    }

    public void setBuyPoints(String buyPoints)
    {
        this.buyPoints = buyPoints;
    }

    public String getBuyPointsFactorage()
    {
        return buyPointsFactorage;
    }

    public void setBuyPointsFactorage(String buyPointsFactorage)
    {
        this.buyPointsFactorage = buyPointsFactorage;
    }

    public String getGatheringValue()
    {
        return gatheringValue;
    }

    public void setGatheringValue(String gatheringValue)
    {
        this.gatheringValue = gatheringValue;
    }

    public String getPointsWithdrawFactorage()
    {
        return pointsWithdrawFactorage;
    }

    public void setPointsWithdrawFactorage(String pointsWithdrawFactorage)
    {
        this.pointsWithdrawFactorage = pointsWithdrawFactorage;
    }

    public String getGivePoints()
    {
        return givePoints;
    }

    public void setGivePoints(String givePoints)
    {
        this.givePoints = givePoints;
    }

    public Long getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(Long merchantId)
    {
        this.merchantId = merchantId;
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public String getFilmMobile()
    {
        return filmMobile;
    }

    public void setFilmMobile(String filmMobile)
    {
        this.filmMobile = filmMobile;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public Long getDeliveryId()
    {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId)
    {
        this.deliveryId = deliveryId;
    }

    public String getDeliveryName()
    {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName)
    {
        this.deliveryName = deliveryName;
    }

	public List<TransDetailDto> getTransDetailDto() {
		return transDetailDto;
	}

	public void setTransDetailDto(List<TransDetailDto> transDetailDto) {
		this.transDetailDto = transDetailDto;
	}

	public List<Long> getSysUserIds() {
		return sysUserIds;
	}

	public void setSysUserIds(List<Long> sysUserIds) {
		this.sysUserIds = sysUserIds;
	}

	public SysUserDto getSysUserDto() {
		return sysUserDto;
	}

	public void setSysUserDto(SysUserDto sysUserDto) {
		this.sysUserDto = sysUserDto;
	}
    
    
}
