package com.froad.cbank.coremodule.normal.boss.pojo.product;

import java.io.Serializable;

/**
 * 
 * @ClassName: CompetiviteProductListVoRes
 * @author chenzhangwei@f-road.com.cn
 * @createTime 2015年11月26日 下午5:37:04 
 * @desc <p>精品商城列表分页查询返回vo</p>
 */
public class CompetiviteProductListVoRes implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6528582053408532550L;
	private long id;
	private long createTime;//创建时间
	private String productId;//商品编号
	private String name;//商品名称
	private String fullName;//商品长名称
	private double price ;//商品价格
	private double vipPrice;//vip价格
	private String clientId;//客户端id
	private String clientName;//客户端名称
	 /**
	   * 是否秒杀 0非秒杀,1秒杀
	   */
	  private String isSeckill;
	/**
	 * 上下架状态
	 * 0-未上架
	 * 1-已上架
	 * 2-已下架
	 * 3-已删除
	 * 4-禁用下架
	 */
	private String marketableStatus;//0未上架,1已上架,2已下架,3已删除,4禁用下架
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(double vipPrice) {
		this.vipPrice = vipPrice;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getMarketableStatus() {
		return marketableStatus;
	}
	public void setMarketableStatus(String marketableStatus) {
		this.marketableStatus = marketableStatus;
	}
	public String getIsSeckill() {
		return isSeckill;
	}
	public void setIsSeckill(String isSeckill) {
		this.isSeckill = isSeckill;
	}
	
	
	
	
	
	
	
}
