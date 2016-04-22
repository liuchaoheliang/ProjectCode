package com.froad.thirdparty.dto.response.pe;

import java.io.Serializable;
import java.util.Date;

public class MemberSecurityQuestion implements Serializable{

	private static final long serialVersionUID = 2918941796197610284L;
	
	private Integer ID;
	private Integer questionID;
	private String questionName;
	private String questionDemo;
	private String type;
	private Long memberCode; 
	private String answer; 
	private Integer state;
	private Date createTime;
	
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getQuestionDemo() {
		return questionDemo;
	}
	public void setQuestionDemo(String questionDemo) {
		this.questionDemo = questionDemo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
 
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	 
	public Integer getQuestionID() {
		return questionID;
	}
	public void setQuestionID(Integer questionID) {
		this.questionID = questionID;
	}
	public String getQuestionName() {
		return questionName;
	}
	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
