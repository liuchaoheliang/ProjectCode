package com.froad.thirdparty.enums;

public enum CertificateType {

	IDCARD("1", "身份证"),
	PASSPORT("2", "护照"), 
	MILITARYID("3", "军官证"), 
	SOLDIERID("4", "士兵证"), 
	HOUSEHOLDREGISTER("5", "户口本"),
	POLICEID("6", "警官证"), 
	TAIWANGID("7", "台胞证");

	private String code;
	private String describe;

	private CertificateType(String code, String describe) {
		this.code = code;
		this.describe = describe;
	}

	public String getCode() {
		return code;
	}

	public String getDescribe() {
		return describe;
	}

	@Override
	public String toString() {
		return this.code;
	}
}
