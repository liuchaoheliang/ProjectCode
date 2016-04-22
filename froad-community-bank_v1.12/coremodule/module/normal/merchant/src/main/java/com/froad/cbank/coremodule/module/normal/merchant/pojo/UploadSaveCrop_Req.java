package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import java.io.Serializable;

public class UploadSaveCrop_Req implements Serializable {
	private static final long serialVersionUID = -8679965791719132706L;
	private int appId;
	private String appKey;
	private String fileKey;// 裁切结果文件key
	private String destKey;// 业务保存文件key

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

	public String getDestKey() {
		return destKey;
	}

	public void setDestKey(String destKey) {
		this.destKey = destKey;
	}

}
