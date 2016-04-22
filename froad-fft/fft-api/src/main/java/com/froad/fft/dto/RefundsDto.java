
	 /**
  * 文件名：RefundsDto.java
  * 版本信息：Version 1.0
  * 日期：2014年3月27日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.dto;

import java.io.Serializable;
import java.util.Date;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月27日 下午5:25:48 
 */
public class RefundsDto implements Serializable{
	public enum State {
		/**
		 * 申请退款，处理中
		 */
		apply("1","退款待审核"),
		
		/**
		 * 审核通过
		 */
		audit_pass("2","退款审核通过"),
		
		/**
		 * 审核不通过
		 */
		audit_no_pass("3","退款审核不通过");
		
		private String code;
	    private String describe;
	    
		private State(String code,String describe){
			this.code=code;
			this.describe = describe;
		}
		
		 	public String getCode()
		    {
		        return code;
		    }

		    public String getDescribe()
		    {
		        return describe;
		    }

		    @Override
		    public String toString()
		    {
		        return this.code;
		    }
	}
	private Long id;
	private Date createTime;
	private String sn;//编号
	private Long transId;//交易ID
	private State state;//状态
	private String reason;//退款事由
	
	private String sysUserId;//操作员
	private String remark;//备注
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Long getTransId() {
		return transId;
	}
	public void setTransId(Long transId) {
		this.transId = transId;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getSysUserId() {
		return sysUserId;
	}
	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
		
	
}
