package com.froad.thirdparty.dto.response.points;


public class PointsApiQueryParams {

	private String queryOrderID;
	private String queryOrderType;
	private String stateCode;
	
	public enum StateCode{
		success("01"),failed("02"),other("03"),not_exists("04");
		private String code;
		
		private StateCode(String code) {
			this.code = code;
		}

		public String getCode() {
			return code;
		}
		
	}
	
	public StateCode getOrderProccessState(){
		if(stateCode == null || stateCode.length() == 0){
			return null;
		}
		
		if(stateCode.equals("01")){
			return StateCode.success;
		}
		if(stateCode.equals("02")){
			return StateCode.failed;
		}
		if(stateCode.equals("03")){
			return StateCode.other;
		}
		if(stateCode.equals("04")){
			return StateCode.not_exists;
		}
		
		throw new RuntimeException("未知的订单状态");
	}
	public String getQueryOrderID() {
		return queryOrderID;
	}
	public void setQueryOrderID(String queryOrderID) {
		this.queryOrderID = queryOrderID;
	}
	public String getQueryOrderType() {
		return queryOrderType;
	}
	public void setQueryOrderType(String queryOrderType) {
		this.queryOrderType = queryOrderType;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	
	public void setStateCode(StateCode code){
		if(code==null){
			return;
		}
		this.stateCode = code.getCode();
	}

}
