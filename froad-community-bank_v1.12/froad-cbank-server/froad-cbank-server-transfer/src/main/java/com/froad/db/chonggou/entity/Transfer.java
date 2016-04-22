package com.froad.db.chonggou.entity;

import com.froad.enums.TransferTypeEnum;

public class Transfer {
	
	private Long id;
	
	private String oldId;
	
	private String newId;
	
	private TransferTypeEnum type;

	public String getOldId() {
		return oldId;
	}

	public void setOldId(String oldId) {
		this.oldId = oldId;
	}

	public String getNewId() {
		return newId;
	}

	public void setNewId(String newId) {
		this.newId = newId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TransferTypeEnum getType() {
		return type;
	}

	public void setType(TransferTypeEnum type) {
		this.type = type;
	}
	
	
}

