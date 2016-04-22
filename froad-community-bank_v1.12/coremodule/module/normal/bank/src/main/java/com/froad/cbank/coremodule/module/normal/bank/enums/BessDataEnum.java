package com.froad.cbank.coremodule.module.normal.bank.enums;

public enum BessDataEnum {
	contractStaff("contractStaff"), // 签约人
	loginMobile("loginMobile"), // 登录人手机
	legalCredentNo("legalCredentNo"), // 证件号
	legalCredentType("legalCredentType"), // 证件类型
	companyId("companyId"), // 外包公司id
	isOutsource("isOutsource"), // 是否外包
	phone("phone"), // 登录人手机
	acctNumber("acctNumber"), // 收货人账号
	acctName("acctName"), // 收款账户名
	categoryType("categoryType"), // 商户类型(审核箱)
	category("category"), // 所属分类
	legalName("legalName"), // 法人名
	contactName("contactName"), // 联系人
	orgCode("orgCode"), // 机构号
	newOrgCode("newOrgCode"), // 新机构号
	merchantId("merchantId"), // 商户id
	merchantUserId("merchantUserId"), // 商户用户id
	productId("productId"), // 商品主键id
	endTime("endTime"), // 团购结束时间
	startTime("startTime"), // 团购开始时间
	outletId("outletId"),//门店编号
	license("license"),//营业执照
	outletName("outletName"),//门店名称
	outletFullName("outletFullName"),//门店全称
	productName("productName"), // 商品名称
	productFullName("productFullName"), // 商品全称
	merchantName("merchantName"),//商户简称
	merchantFullName("merchantFullName"),//商户全称
	userOrgCode("userOrgCode"), // 操作员
	typeInfo("typeInfo"), // 商户类型
	categoryInfo("categoryInfo");// 商品分类
	// treePath("treePath");// 商品分类树
	
	private String key;

	private BessDataEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
