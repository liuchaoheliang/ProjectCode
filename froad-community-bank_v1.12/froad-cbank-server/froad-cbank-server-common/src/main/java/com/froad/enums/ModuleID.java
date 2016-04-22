package com.froad.enums;

public enum ModuleID {
	product("0","product"),
	merchant("1","merchant"),
	outlet("2","outlet"),
	order("3","order"),
	suborder("4","suborder"),
	settlement("5","settlement"),
	ticket("6","ticket"),
	refund("7","refund"),
	payment("8","payment"),
	paymentchannel("9","paymentchannel"),//支付渠道
	outletcomment("10","outletcomment"),//门店评论
	seckill("11","seckill"),//秒杀
	recvInfo("12","recvInfo"),//收货
	deliverInfo("13","deliverInfo"),
	vipProduct("14","vipProduct"),//VIP规则

	bossProduct("17","bossProduct"),//boss管理平台新加的商品
	terminalStart("18","terminalStart")//客户端启动页中的图片Id
	
	, process("19","process") // 审核流程
	, processnode("20","processnode")// 审核流程节点
	, instance("21","instance")// 审核实例
	, task("22","task")// 审核任务
	,bossPayment("23","bossPayment")
	,bossSettlement("24","bossSettlement")
	, active("25","active")//活动ID
	, activeOrder("26","activeOrder")// 活动订单	
	, vouchers("27","vouchers")// 活动订单
	
	, fftorg("28","fftorg")// 组织
	
	;
	
	  
	  
	private String code;
	private String modulename;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getModulename() {
		return modulename;
	}
	public void setDescribe(String modulename) {
		this.modulename = modulename;
	}
	
	private ModuleID(String code, String modulename) {
		this.code = code;
		this.modulename = modulename;
	}
	
}
