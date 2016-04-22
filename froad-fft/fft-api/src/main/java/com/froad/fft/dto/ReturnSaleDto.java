package com.froad.fft.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
* @ClassName: ReturnSaleDto
* @Description: 退/换货
* @author larry
* @date 2014-4-3 下午04:26:32
*
 */
public class ReturnSaleDto implements Serializable{
	
	public enum Type{
		/**
		 * 退货
		 */
		sale_return,
		
		/**
		 * 换货
		 */
		sale_swap
	}
	private Long id;
	
	private String sn;//编号
	
	private Type type;//类型
	private Long merchantOutletId;//门店ID
	private MerchantOutletDto merchantOutletDto;//关联门店信息
	private String reason;//退货事由
	
	private Long sysUserId;//操作员
	private SysUserDto sysUserDto;//关联操作员信息
	private String remark;//备注
	private Date createTime;//创建时间
	
	private List<Long> sysUserIds;  //操作员集合
	
	private ReturnSaleDetailDto returnSaleDetailDto;//退换货详情关联数据
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public Long getMerchantOutletId() {
		return merchantOutletId;
	}
	public void setMerchantOutletId(Long merchantOutletId) {
		this.merchantOutletId = merchantOutletId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Long getSysUserId() {
		return sysUserId;
	}
	public void setSysUserId(Long sysUserId) {
		this.sysUserId = sysUserId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ReturnSaleDetailDto getReturnSaleDetailDto() {
		return returnSaleDetailDto;
	}
	public void setReturnSaleDetailDto(ReturnSaleDetailDto returnSaleDetailDto) {
		this.returnSaleDetailDto = returnSaleDetailDto;
	}
	public List<Long> getSysUserIds() {
		return sysUserIds;
	}
	public void setSysUserIds(List<Long> sysUserIds) {
		this.sysUserIds = sysUserIds;
	}
	public MerchantOutletDto getMerchantOutletDto() {
		return merchantOutletDto;
	}
	public void setMerchantOutletDto(MerchantOutletDto merchantOutletDto) {
		this.merchantOutletDto = merchantOutletDto;
	}
	public SysUserDto getSysUserDto() {
		return sysUserDto;
	}
	public void setSysUserDto(SysUserDto sysUserDto) {
		this.sysUserDto = sysUserDto;
	}	
	
	
}
