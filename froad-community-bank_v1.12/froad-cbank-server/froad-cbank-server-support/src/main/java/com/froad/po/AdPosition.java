package com.froad.po;


/**
 * CbAdPosition entity. 
 */
public class AdPosition implements java.io.Serializable {

	// Fields
	/**
	 * 广告位
	 */
	private Long id;             //主键ID 广告位ID
	private String clientId;        //客户端ID 
	private String name;          //名称 
	private Integer width;        // 宽度
	private Integer height;        //高度
	private String terminalType;    // 终端类型  
	private String positionStyle;  //形状
	private String positionPage;   // 页面标识
	private Integer positionPoint;  //页面原点
	private Integer sizeLimit;     // 大小限制  - kb
	private String description;     //描述
	private Boolean isEnable;      //是否有效

	// Constructors

	/** default constructor */
	public AdPosition() {
	}

	/** minimal constructor */
	public AdPosition(String clientId, String name, Integer width, Integer height, String positionPage, Integer positionPoint, Boolean isEnable) {
		this.clientId = clientId;
		this.name = name;
		this.width = width;
		this.height = height;
		this.positionPage = positionPage;
		this.positionPoint = positionPoint;
		this.isEnable = isEnable;
	}

	/** full constructor */
	public AdPosition(String clientId, String name, Integer width, Integer height, String positionStyle, String positionPage, Integer positionPoint, String description, Boolean isEnable) {
		this.clientId = clientId;
		this.name = name;
		this.width = width;
		this.height = height;
		this.positionStyle = positionStyle;
		this.positionPage = positionPage;
		this.positionPoint = positionPoint;
		this.description = description;
		this.isEnable = isEnable;
	}

	// Property accessors
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getWidth() {
		return this.width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return this.height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getPositionStyle() {
		return this.positionStyle;
	}

	public void setPositionStyle(String positionStyle) {
		this.positionStyle = positionStyle;
	}

	public String getPositionPage() {
		return this.positionPage;
	}

	public void setPositionPage(String positionPage) {
		this.positionPage = positionPage;
	}

	public Integer getPositionPoint() {
		return this.positionPoint;
	}

	public void setPositionPoint(Integer positionPoint) {
		this.positionPoint = positionPoint;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsEnable() {
		return this.isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}

	public Integer getSizeLimit() {
		return sizeLimit;
	}

	public void setSizeLimit(Integer sizeLimit) {
		this.sizeLimit = sizeLimit;
	}

}