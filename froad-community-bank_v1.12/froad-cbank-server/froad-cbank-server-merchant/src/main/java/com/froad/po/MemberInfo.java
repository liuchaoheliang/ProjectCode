package com.froad.po;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.froad.db.mongo.OutletCommentMongo;

public class MemberInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Fields
	public String memberCode;
	public String memberName;
	public String memberHead;
	
	public String getMemberCode() {
		return memberCode;
	}
	@JSONField(name=OutletCommentMongo.MONGO_KEY_MEMBER_CODE)
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getMemberName() {
		return memberName;
	}
	@JSONField(name=OutletCommentMongo.MONGO_KEY_MEMBER_NAME)
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberHead() {
		return memberHead;
	}
	@JSONField(name=OutletCommentMongo.MONGO_KEY_MEMBER_HEAD)
	public void setMemberHead(String memberHead) {
		this.memberHead = memberHead;
	} 
	

}
