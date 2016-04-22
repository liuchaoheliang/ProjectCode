package com.froad.cbank.coremodule.normal.boss.pojo.merchant;

import java.io.Serializable;
import java.util.List;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.thrift.vo.MerchantCategoryVo;
import com.froad.thrift.vo.OriginVo;

/**
 * 类描述：
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015
 * @author: f-road
 * @time: 2015-4-30下午2:35:50
 */
public class MerchantCategoryVoReq extends Page implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2984063408753119820L;
	/**
	 * 主键ID
	 */
	private long id; // optional
	/**
	 * 客户端ID
	 */
	private String clientId; // optional
	/**
	 * 上级分类ID
	 */
	private long parentId; // optional
	/**
	 * 分类名称
	 */
	private String name; // optional
	/**
	 * 树路径
	 */
	private String treePath; // optional
	/**
	 * 商户分类图标
	 */
	private String icoUrl; // optional
	/**
	 * 是否删除
	 */
	private boolean isDelete; // optional
	/**
	 * 排序
	 */
	private int orderValue; // optional

	private MerchantCategoryVo merchantCategoryVo;

	private List<MerchantCategoryVo> cateList;

	private OriginVo originVo;

	private long begDate;
	private long startDate;
	private long endDate;
	private Long beginTime;
	private Long endTime;

	public long getBegDate() {
		return begDate;
	}

	public void setBegDate(long begDate) {
		this.begDate = begDate;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public long getEndDate() {
		return endDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}

	public Long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTreePath() {
		return treePath;
	}

	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	public String getIcoUrl() {
		return icoUrl;
	}

	public void setIcoUrl(String icoUrl) {
		this.icoUrl = icoUrl;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public int getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(int orderValue) {
		this.orderValue = orderValue;
	}

	public List<MerchantCategoryVo> getCateList() {
		return cateList;
	}

	public void setCateList(List<MerchantCategoryVo> cateList) {
		this.cateList = cateList;
	}

	public MerchantCategoryVo getMerchantCategoryVo() {
		return merchantCategoryVo;
	}

	public void setMerchantCategoryVo(MerchantCategoryVo merchantCategoryVo) {
		this.merchantCategoryVo = merchantCategoryVo;
	}

	public OriginVo getOriginVo() {
		return originVo;
	}

	public void setOriginVo(OriginVo originVo) {
		this.originVo = originVo;
	}

}
