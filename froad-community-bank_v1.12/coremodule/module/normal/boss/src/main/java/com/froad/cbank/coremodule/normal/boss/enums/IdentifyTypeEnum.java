package com.froad.cbank.coremodule.normal.boss.enums;

public enum IdentifyTypeEnum {
	
	  ID_CARD("011", "身份证"), 
	  Booklet("012", "户口簿"), 
	  Passport("013", "护照"), 
	  Military_officer("014", "军官证"), 
	  Armed_card("015", "武警证"), 
	  Card_soldiers("016", "士兵证"), 
	  Retirement_card("018", "文职干部退休证"), 
	  Hong_Kong_Pass("019", "香港通行证");

	  private String desc;
	  private String value;

	  private IdentifyTypeEnum(String value, String desc) {
	    this.value = value;
	    this.desc = desc;
	  }

	  public String getValue() {
	    return this.value;
	  }
	  public String getDesc() {
	    return this.desc;
	  }
	  public String toString() {
	    return this.desc;
	  }

	  public static boolean checkValue(int value){
		 IdentifyTypeEnum[] vds = values();
	     for (int i = 0; i < vds.length; i++) {
	       if (vds[i].getValue().equals(Integer.valueOf(value))) {
	        return true;
	       }
	     }
	     return false;
	  }
	  
	  public static String valueOfName(String value) {
		   IdentifyTypeEnum[] vds = values();
		   for (int i = 0; i < vds.length; i++) {
		      if (vds[i].getValue().equals(value)) {
		        return vds[i].getDesc();
		      }
		    }
		    return "";
	  }
}
