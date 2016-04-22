package com.froad.po;

import java.util.Date;

public class HotWordDetaill {
	// Fields
		//主键id
		private Long id;             
		 //客户端ID 
		private String clientId;      
		 // 创建时间
		private Date createTime;        
		//省-地区id
		private long provinceAreaId; 
		//市-地区id
		private long cityAreaId;       
		//区-地区id
		private long countyAreaId;  
		//开始时间
		private Date startTime;	
		//结束时间
		private Date endTime;	
		//热词
		private String hotWord;	
		//搜索次数
		private Integer searchCount;	
		//搜索有结果次数
		private Integer searchCountResult;	
		//分类：1商品2，商户
		private Integer categoryType;
		//类型：1手工输入2，系统统计
		private Integer type;
		//排序id
		private Integer sortId;
		//地区id
		private long areaId;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getClientId() {
			return clientId;
		}
		public void setClientId(String clientId) {
			this.clientId = clientId;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		public long getProvinceAreaId() {
			return provinceAreaId;
		}
		public void setProvinceAreaId(long provinceAreaId) {
			this.provinceAreaId = provinceAreaId;
		}
		public long getCityAreaId() {
			return cityAreaId;
		}
		public void setCityAreaId(long cityAreaId) {
			this.cityAreaId = cityAreaId;
		}
		public long getCountyAreaId() {
			return countyAreaId;
		}
		public void setCountyAreaId(long countyAreaId) {
			this.countyAreaId = countyAreaId;
		}
		public Date getStartTime() {
			return startTime;
		}
		public void setStartTime(Date startTime) {
			this.startTime = startTime;
		}
		public Date getEndTime() {
			return endTime;
		}
		public void setEndTime(Date endTime) {
			this.endTime = endTime;
		}
		public String getHotWord() {
			return hotWord;
		}
		public void setHotWord(String hotWord) {
			this.hotWord = hotWord;
		}
		public Integer getSearchCount() {
			return searchCount;
		}
		public void setSearchCount(Integer searchCount) {
			this.searchCount = searchCount;
		}
		public Integer getSearchCountResult() {
			return searchCountResult;
		}
		public void setSearchCountResult(Integer searchCountResult) {
			this.searchCountResult = searchCountResult;
		}
		public Integer getCategoryType() {
			return categoryType;
		}
		public void setCategoryType(Integer categoryType) {
			this.categoryType = categoryType;
		}
		public Integer getType() {
			return type;
		}
		public void setType(Integer type) {
			this.type = type;
		}
		public Integer getSortId() {
			return sortId;
		}
		public void setSortId(Integer sortId) {
			this.sortId = sortId;
		}
		public long getAreaId() {
			return areaId;
		}
		public void setAreaId(long areaId) {
			this.areaId = areaId;
		}
		
}
