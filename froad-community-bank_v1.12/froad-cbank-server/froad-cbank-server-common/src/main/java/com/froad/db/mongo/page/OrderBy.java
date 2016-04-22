package com.froad.db.mongo.page;

/**
 *  排序
  * @ClassName: Order
  * @Description: TODO
  * @author share 2015年3月26日
  * @modify share 2015年3月26日
 */
public enum OrderBy {  
      
    ASC(1, "升序排列"), DESC(-1, "降序排列");
    
	private int code;
	private String description;
	
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	private OrderBy(int code, String description) {
		this.code = code;
		this.description = description;
	}
}  