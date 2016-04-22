package com.froad.cbank.coremodule.normal.boss.pojo.external;

public class CallBackVo {

	/**
	 * 监控状态:polling:监控中，shutdown:结束，abort:中止，updateall：重新推送。
	 * 其中当快递单为已签收时status=shutdown，当message为“3天查询无记录”或“60天无变化时”status= abort ，
	 * 对于stuatus=abort的状度，需要增加额外的处理逻辑，详见本节最后的说明
	 */
	private String status;
//	/**
//	 * 包括got、sending、check三个状态，由于意义不大，已弃用，请忽略
//	 */
//	private String billstatus;
	/**
	 * 监控状态相关消息，如:3天查询无记录，60天无变化
	 */
	private String message;
	/**
	 * 最新查询结果，全量，倒序（即时间最新的在最前）
	 */
	private Result lastResult = new Result();
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
//	public String getBillstatus() {
//		return billstatus;
//	}
//	public void setBillstatus(String billstatus) {
//		this.billstatus = billstatus;
//	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Result getLastResult() {
		return lastResult;
	}
	public void setLastResult(Result lastResult) {
		this.lastResult = lastResult;
	}
	
}
