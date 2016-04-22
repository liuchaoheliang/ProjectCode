package com.froad.po;

/**
 * 字典类别po
 * @author froad-ll
 *
 */
public class DictionaryCategory implements java.io.Serializable {

    
    private Long id;                    //字典类别id(自增主键)
	private String categoryCode;		//字典类别编号
	private String categoryName;		//字典类别名称
	private String depiction;			//字典类别描述
	private String categoryLevel;		//字典类别级别 0-系统字典 1-业务字典
	private Long parentId;				//父字典类别ID
	private String treePath;			//树路径
	private Integer orderValue;			//排序值
	private Boolean isEnable;			//是否有效
	
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getDepiction() {
		return depiction;
	}
	public void setDepiction(String depiction) {
		this.depiction = depiction;
	}
	public String getCategoryLevel() {
		return categoryLevel;
	}
	public void setCategoryLevel(String categoryLevel) {
		this.categoryLevel = categoryLevel;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getTreePath() {
		return treePath;
	}
	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}
	public Integer getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}
	public Boolean getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}
    
    
}