package com.froad.cbank.coremodule.normal.boss.pojo.merchant;

import java.io.Serializable;
import java.util.List;

import com.froad.thrift.vo.MerchantCategoryVo;
import com.froad.thrift.vo.OriginVo;

/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road
 * @time:  2015-4-30下午2:36:18
 */
public class MerchantCategoryVoRes implements Serializable{

	/**
	 * UID
	 */
	private static final long serialVersionUID = 4203270924099335342L;
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
	
	private List<MerchantCategoryVo> cateList;
	
	private OriginVo originVo;
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
	public OriginVo getOriginVo() {
		return originVo;
	}
	public void setOriginVo(OriginVo originVo) {
		this.originVo = originVo;
	}
	
}
