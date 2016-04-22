package com.froad.po;

/**
 * 字典条目po
 * @author froad-ll
 *
 */
public class DictionaryItem implements java.io.Serializable {

    
    private Long id;                    //字典条目id(自增主键)
	private String itemCode;			//字典条目编号
	private String itemName;			//字典条目名称
	private String itemValue;			//字典条目值
	private String depiction;			//字典条目描述
	private Long dicId;					//字典ID
	private String clientId;			//所属客户端
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
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemValue() {
		return itemValue;
	}
	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}
	public Long getDicId() {
		return dicId;
	}
	public void setDicId(Long dicId) {
		this.dicId = dicId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
    
    
}