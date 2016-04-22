package com.froad.po.mongo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class MemberInfo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4928221265158168067L;

    /**
     * 用户编码
     */
    private String memberCode;
    /**
     * 评论人
     */
    private String memberName;
    
    @JSONField(name="member_code")
    public String getMemberCode() {
        return memberCode;
    }
    
    @JSONField(name="member_code")
    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }
    
    @JSONField(name="member_name")
    public String getMemberName() {
        return memberName;
    }
    
    @JSONField(name="member_name")
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
    
}
