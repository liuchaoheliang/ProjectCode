package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import java.io.Serializable;

public class UploadCrop_Req implements Serializable {
	private static final long serialVersionUID = -8679965791719132706L;
	private int appId;
	private String appKey;
	private String fileKey;
	private int scale;
	private double offsetX;
	private double offsetY;
	private double width;
	private double height;
	private int forceSize;

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getFileKey() {
		return fileKey;
	}

	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public double getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(double offsetX) {
		this.offsetX = offsetX;
	}

	public double getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(double offsetY) {
		this.offsetY = offsetY;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public int getForceSize() {
		return forceSize;
	}

	public void setForceSize(int forceSize) {
		this.forceSize = forceSize;
	}

}
