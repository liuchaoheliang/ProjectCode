package com.froad.po;
import java.util.Date;

/**
 * CbActivities entity.
 */
public class Activities implements java.io.Serializable {

	// Fields
	private Long id;               //主键id
	private String clientId;         //客户端ID 
	private Date createTime;        // 创建时间
	private String activitiesName;  //活动名称
	private Integer points;         //赠送积分数量
	private Date beginTime;			//活动开始时间
	private Date endTime;			//活动结束时间
	private String status;		    //状态 0-未⽣生效 1-已⽣生效 2-已作废
	private Integer count;			//Integer
	/**
	 * VIP限购
	 */
	private Integer vipLimit;
	/**
	 * 限购规则
	 */
	private Integer limitNum;
	/**
	 * 是否删除
	 * */
	private Boolean isDelete;

	// Constructors

	/** default constructor */
	public Activities() {
	}

	/** minimal constructor */
	public Activities(String clientId, String activitiesName, Date beginTime, Date endTime) {
		this.clientId = clientId;
		this.activitiesName = activitiesName;
		this.beginTime = beginTime;
		this.endTime = endTime;
	}

	/** full constructor */
	public Activities(String clientId, String activitiesName, String activitiesType, Integer points, String vipPrice, Date beginTime, Date endTime, Integer enableType, String remark) {
		this.clientId = clientId;
		this.activitiesName = activitiesName;
		this.points = points;
		this.beginTime = beginTime;
		this.endTime = endTime;
	}

	// Property accessors
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getActivitiesName() {
		return this.activitiesName;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setActivitiesName(String activitiesName) {
		this.activitiesName = activitiesName;
	}

	public Integer getPoints() {
		return this.points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Date getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	public Integer getVipLimit() {
		return vipLimit;
	}

	public void setVipLimit(Integer vipLimit) {
		this.vipLimit = vipLimit;
	}

	public Integer getLimitNum() {
		return limitNum;
	}

	public void setLimitNum(Integer limitNum) {
		this.limitNum = limitNum;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}