package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import java.io.Serializable;

public class UploadToken_Req implements Serializable {

	private static final long serialVersionUID = -1239955268510795054L;
	private int appId;
	private String appKey;
	private int fsizeLimitMax;// 限定最大文件大小
	private int fsizeLimitMin;// 限定最小文件大小
	private String mimeLimit;// 限定文件类型

	private int widthLimitMin;// 限定图片宽度下限 int 单位：像素（px） Y
	private int widthLimitMax;// 限定图片宽度上限 int 单位：像素（px） Y
	private int heightLimitMin;// 限定图片高度下限 int 单位：像素（px） Y
	private int heightLimitMax;// 限定图片高度上限 int 单位：像素（px） Y

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

	public int getFsizeLimitMax() {
		return fsizeLimitMax;
	}

	public void setFsizeLimitMax(int fsizeLimitMax) {
		this.fsizeLimitMax = fsizeLimitMax;
	}

	public int getFsizeLimitMin() {
		return fsizeLimitMin;
	}

	public void setFsizeLimitMin(int fsizeLimitMin) {
		this.fsizeLimitMin = fsizeLimitMin;
	}

	public String getMimeLimit() {
		return mimeLimit;
	}

	public void setMimeLimit(String mimeLimit) {
		this.mimeLimit = mimeLimit;
	}

	public int getWidthLimitMin() {
		return widthLimitMin;
	}

	public void setWidthLimitMin(int widthLimitMin) {
		this.widthLimitMin = widthLimitMin;
	}

	public int getWidthLimitMax() {
		return widthLimitMax;
	}

	public void setWidthLimitMax(int widthLimitMax) {
		this.widthLimitMax = widthLimitMax;
	}

	public int getHeightLimitMin() {
		return heightLimitMin;
	}

	public void setHeightLimitMin(int heightLimitMin) {
		this.heightLimitMin = heightLimitMin;
	}

	public int getHeightLimitMax() {
		return heightLimitMax;
	}

	public void setHeightLimitMax(int heightLimitMax) {
		this.heightLimitMax = heightLimitMax;
	}

}
