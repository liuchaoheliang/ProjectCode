package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

public class Outlet_Comment_Replay_Req extends BasePojo {
	
	@NotEmpty(value = "评论id不能为空")
	private String id; 
	
	@NotEmpty(value = "回复内容不能为空")
	private String recomment; 

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRecomment() {
		return recomment;
	}

	public void setRecomment(String recomment) {
		this.recomment = recomment;
	}

	
}
