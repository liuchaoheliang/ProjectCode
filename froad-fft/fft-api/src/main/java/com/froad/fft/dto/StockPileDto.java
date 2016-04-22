/**
 * 文件名：StockPileDto.java
 * 版本信息：Version 1.0
 * 日期：2014年3月28日
 * author: 刘超 liuchao@f-road.com.cn
 */
package com.froad.fft.dto;

import java.util.Date;
import java.util.List;

/**
 * 类描述：
 *
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月28日 下午7:18:45
 */
public class StockPileDto extends BaseDto
{
    public enum WarnType
    {
        /**
         * 固定
         */
        fixed,

        /**
         * 比例
         */
        scale
    }

    private Long id;
    private Date createTime;
    private Long productId;//商品ID
    private ProductDto productDto;//关联商品
    private Long merchantOutletId;//名店ID
    private MerchantOutletDto merchantOutletDto;//关联门店信息
    private Integer quantity;//库存时时数量


    private Date lastIncomeTime;//最后入库时间
    private Date lastOutcomeTime;//最后一次出库时间

    private WarnType warnType;//库存警告类型
    private String warnValue;//警告值(警告类型为固定值时,此属性为数字，为比例时，此字段为0-100)
    private Integer totalQuantity;//总库存

    private String remark;

    private List<Long> merchantOutletIds;//根据id集合查询门店库存

    public Long getProductId()
    {
        return productId;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public Long getMerchantOutletId()
    {
        return merchantOutletId;
    }

    public void setMerchantOutletId(Long merchantOutletId)
    {
        this.merchantOutletId = merchantOutletId;
    }

    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }

    public Date getLastIncomeTime()
    {
        return lastIncomeTime;
    }

    public void setLastIncomeTime(Date lastIncomeTime)
    {
        this.lastIncomeTime = lastIncomeTime;
    }

    public Date getLastOutcomeTime()
    {
        return lastOutcomeTime;
    }

    public void setLastOutcomeTime(Date lastOutcomeTime)
    {
        this.lastOutcomeTime = lastOutcomeTime;
    }

    public WarnType getWarnType()
    {
        return warnType;
    }

    public void setWarnType(WarnType warnType)
    {
        this.warnType = warnType;
    }

    public String getWarnValue()
    {
        return warnValue;
    }

    public void setWarnValue(String warnValue)
    {
        this.warnValue = warnValue;
    }

    public Integer getTotalQuantity()
    {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity)
    {
        this.totalQuantity = totalQuantity;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
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

    public List<Long> getMerchantOutletIds()
    {
        return merchantOutletIds;
    }

    public void setMerchantOutletIds(List<Long> merchantOutletIds)
    {
        this.merchantOutletIds = merchantOutletIds;
    }

    public MerchantOutletDto getMerchantOutletDto()
    {
        return merchantOutletDto;
    }

    public void setMerchantOutletDto(MerchantOutletDto merchantOutletDto)
    {
        this.merchantOutletDto = merchantOutletDto;
    }

    public ProductDto getProductDto()
    {
        return productDto;
    }

    public void setProductDto(ProductDto productDto)
    {
        this.productDto = productDto;
    }

}
