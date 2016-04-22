package com.froad.cbank.coremodule.normal.boss.pojo.merchant;

import java.io.Serializable;

/**
 * 
 * 类描述:商户列表查询返回实体
 * 
 * @Copyright www.f-road.com.cn Corporation 2015
 * @author "chenzhangwei"
 * @time 2015年9月17日 上午9:30:12
 * @email "chenzhangwei@f-road.com.cn"
 */
public class MerchantCategoryRes implements Serializable{

	  /**
	 * 
	 */
	private static final long serialVersionUID = -6172595245697218378L;
	/**
	   * 分类id
	   */
	  public long id; // optional
	  /**
	   * 客户端id
	   */
	  private String clientId; // required
	  /**
	   * 分类名称
	   */
	  private String name; // required
	  /**
	   * 树路径 空格间隔
	   */
	  private String treePath; // required
	  /**
	   * 父节点id
	   */
	  private long parentId; // required
	  /**
	   * 小图标
	   */
	  private String icoUrl; // required
	  /**
	   * 排序
	   */
	  public short orderValue; // required
	  /**
	   * 是否禁用
	   */
	  private boolean isEnable; // required
	  
	  /**
	   *客户端名称
	   */
	  private String clientName;
	  /**
	   * 上级分类
	   */
	  private String parentName;
	  /**
	   * 分类全称
	   */
	  private String fullName;
	  /**
	   * 菜单是否展开
	   */
	  private boolean open;
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
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public String getIcoUrl() {
		return icoUrl;
	}
	public void setIcoUrl(String icoUrl) {
		this.icoUrl = icoUrl;
	}
	public short getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(short orderValue) {
		this.orderValue = orderValue;
	}
	public boolean isEnable() {
		return isEnable;
	}
	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	
	
	  
}