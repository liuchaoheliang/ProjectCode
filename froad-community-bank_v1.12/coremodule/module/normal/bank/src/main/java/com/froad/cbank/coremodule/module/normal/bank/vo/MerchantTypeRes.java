package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

public class MerchantTypeRes implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1444932501656236815L;
	

	private String id;
	
	private String typeName;
	
	private String type;
	
	private boolean isDelete;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	
	

}
