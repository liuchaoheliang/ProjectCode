package com.froad.fft.persistent.entity;

/**
 * 商圈
 * @author FQ
 *
 */
public class BusinessCircle extends BaseEntity{
	
	public static final String PATH_SEPARATOR = ",";// 树路径分隔符
	
	private String name;// 商圈名称
	private Integer orderValue;// 排序
	private String treePath;// 树路径
	private Long parentId;//上级商圈ID
	
	private Long areaId;//所属地区

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

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	
}
