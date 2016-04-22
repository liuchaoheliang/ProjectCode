package com.froad.po;
/**
 * 商品销售详情列表
 * @ClassName: ProductSaleDetail 
 * @Description:  
 * @author longyunbo
 * @date 2015年6月6日 上午1:40:36
 */
public class ProductSaleDetail {
	private String orgCode;
	private String orgName;
	private int addProductCount;	//增商品数
	private int faceProductCount;	//面对面商品总数
	private int productCount;		//商品总数
	private int productSaleCount;	//商品销售数量
	private double productSaleAmount;	//商品销售金额
	private double refundAmount;	//退款总金额
	private String forgCode;		//一级机构号
	private String forgName;		//一级机构名
	private String sorgCode;		//二级机构号
	private String sorgName;		//二级机构名
	private String torgCode;		//三级机构号
	private String torgName;		//三级机构名
	private String lorgCode;		//四级机构号
	private String lorgName;		//四级机构名
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public int getAddProductCount() {
		return addProductCount;
	}
	public void setAddProductCount(int addProductCount) {
		this.addProductCount = addProductCount;
	}
	/**
	 * @return the faceProductCount
	 */
	public int getFaceProductCount() {
		return faceProductCount;
	}
	/**
	 * @param faceProductCount the faceProductCount to set
	 */
	public void setFaceProductCount(int faceProductCount) {
		this.faceProductCount = faceProductCount;
	}
	public int getProductCount() {
		return productCount;
	}
	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}
	public int getProductSaleCount() {
		return productSaleCount;
	}
	public void setProductSaleCount(int productSaleCount) {
		this.productSaleCount = productSaleCount;
	}
	public double getProductSaleAmount() {
		return productSaleAmount;
	}
	public void setProductSaleAmount(double productSaleAmount) {
		this.productSaleAmount = productSaleAmount;
	}
	public double getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(double refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getForgCode() {
		return forgCode;
	}
	public void setForgCode(String forgCode) {
		this.forgCode = forgCode;
	}
	public String getForgName() {
		return forgName;
	}
	public void setForgName(String forgName) {
		this.forgName = forgName;
	}
	public String getSorgCode() {
		return sorgCode;
	}
	public void setSorgCode(String sorgCode) {
		this.sorgCode = sorgCode;
	}
	public String getSorgName() {
		return sorgName;
	}
	public void setSorgName(String sorgName) {
		this.sorgName = sorgName;
	}
	public String getTorgCode() {
		return torgCode;
	}
	public void setTorgCode(String torgCode) {
		this.torgCode = torgCode;
	}
	public String getTorgName() {
		return torgName;
	}
	public void setTorgName(String torgName) {
		this.torgName = torgName;
	}
	public String getLorgCode() {
		return lorgCode;
	}
	public void setLorgCode(String lorgCode) {
		this.lorgCode = lorgCode;
	}
	public String getLorgName() {
		return lorgName;
	}
	public void setLorgName(String lorgName) {
		this.lorgName = lorgName;
	}
	
	
}
