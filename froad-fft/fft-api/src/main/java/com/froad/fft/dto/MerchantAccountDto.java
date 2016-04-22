/**
 * 文件名称:MerchantAccountDto.java
 * 文件描述: 商户账户Dto
 * 产品标识: fft
 * 单元描述: fft-api
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-1
 * 历史修改:  
 */
package com.froad.fft.dto;

import com.froad.fft.enums.AccountType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class MerchantAccountDto   implements Serializable
{
    private Long id;//id
    private Date createTime; //创建时间

    private Long merchantId;//商户ID

    private String acctName;//账户名

    private String acctNumber;//帐号

    private AccountType acctType;//账户类型

    private Boolean isEnabled;//是否启用

    private Long fundsChannelId;//资金渠道

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

    public Long getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(Long merchantId)
    {
        this.merchantId = merchantId;
    }

    public String getAcctName()
    {
        return acctName;
    }

    public void setAcctName(String acctName)
    {
        this.acctName = acctName;
    }

    public String getAcctNumber()
    {
        return acctNumber;
    }

    public void setAcctNumber(String acctNumber)
    {
        this.acctNumber = acctNumber;
    }

    public AccountType getAcctType()
    {
        return acctType;
    }

    public void setAcctType(AccountType acctType)
    {
        this.acctType = acctType;
    }

    public Boolean getIsEnabled()
    {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled)
    {
        this.isEnabled = isEnabled;
    }

    public Long getFundsChannelId()
    {
        return fundsChannelId;
    }

    public void setFundsChannelId(Long fundsChannelId)
    {
        this.fundsChannelId = fundsChannelId;
    }
}
