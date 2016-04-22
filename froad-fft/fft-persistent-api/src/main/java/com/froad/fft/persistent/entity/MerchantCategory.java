package com.froad.fft.persistent.entity;

import com.froad.fft.persistent.common.enums.DataState;

/**
 * 商户分类
 * @author FQ
 *
 */
public class MerchantCategory extends BaseEntity {
	
	public static final String PATH_SEPARATOR = ",";// 树路径分隔符

	private String name;// 分类名称
	private Integer orderValue;// 排序
	private String treePath;// 树路径
	private Long parentId;//上级分类ID
	
	private DataState dataState;//数据状态：有效、删除
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}

	public String getTreePath() {
		return treePath;
	}

	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}
	
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public DataState getDataState() {
		return dataState;
	}

	public void setDataState(DataState dataState) {
		this.dataState = dataState;
	}
	
}
