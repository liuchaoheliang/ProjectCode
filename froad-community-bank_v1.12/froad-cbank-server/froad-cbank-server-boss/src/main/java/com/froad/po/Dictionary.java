package com.froad.po;

/**
 * 字典po
 * @author froad-ll
 *
 */
public class Dictionary implements java.io.Serializable {

    
    private Long id;                    //字典id(自增主键)
	private String dicCode;				//字典编号
	private String dicName;				//字典名称
	private String depiction;			//字典描述
	private Long categoryId;			//字典类别ID
	private Integer orderValue;			//排序值
	private Boolean isEnable;			//是否有效
	
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
	public String getDepiction() {
		return depiction;
	}
	public void setDepiction(String depiction) {
		this.depiction = depiction;
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
	public String getDicCode() {
		return dicCode;
	}
	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}
	public String getDicName() {
		return dicName;
	}
	public void setDicName(String dicName) {
		this.dicName = dicName;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
    
    
}