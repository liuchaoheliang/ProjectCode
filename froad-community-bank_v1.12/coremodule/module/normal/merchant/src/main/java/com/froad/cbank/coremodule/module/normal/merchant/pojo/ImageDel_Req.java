package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import java.io.Serializable;

public class ImageDel_Req implements Serializable {
	private static final long serialVersionUID = -8679965791719132706L;
	private String fileHash;
	private String fileKey;// 裁切结果文件key

	public String getFileHash() {
		return fileHash;
	}

	public void setFileHash(String fileHash) {
		this.fileHash = fileHash;
	}

	public String getFileKey() {
		return fileKey;
	}

	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}

}
