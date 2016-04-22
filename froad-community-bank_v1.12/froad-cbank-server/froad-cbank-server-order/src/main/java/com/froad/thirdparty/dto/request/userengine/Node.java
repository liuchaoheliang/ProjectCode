package com.froad.thirdparty.dto.request.userengine;

public class Node {

	private String value;

	private String text;
	
	private Integer level;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	
	public Integer getLevel() {
		return level;
	}

	
	public void setLevel(Integer level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return String.format("{value:'%s', text:'%s'}", value,text);
	}

}
