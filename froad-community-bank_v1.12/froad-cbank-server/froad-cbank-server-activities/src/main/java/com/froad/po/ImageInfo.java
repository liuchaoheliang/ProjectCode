package com.froad.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;

@JSONType(orders = {"large", "medium", "source", "thumbnail"})
public class ImageInfo {
	
	@JSONField(name = "large", serialize = true, deserialize = true)
	private String large;
	
	@JSONField(name = "medium", serialize = true, deserialize = true)
	private String medium;
	
	@JSONField(name = "source", serialize = true, deserialize = true)
	private String source;
	
	@JSONField(name = "thumbnail", serialize = true, deserialize = true)
	private String thumbnail;
	
	public ImageInfo() {
		super();
	}
	
	public ImageInfo(String large, String medium,String source,String thumbnail) {
		super();
		this.large = large;
		this.medium = medium;
		this.source = source;
		this.thumbnail = thumbnail;
	}

	public String getLarge() {
		return large;
	}

	public void setLarge(String large) {
		this.large = large;
	}

	public String getMedium() {
		return medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}	
}
