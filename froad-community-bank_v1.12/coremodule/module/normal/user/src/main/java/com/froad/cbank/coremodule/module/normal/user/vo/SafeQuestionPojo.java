package com.froad.cbank.coremodule.module.normal.user.vo;

/**
 * 安全问题请求实体接口
 * @author wufei
 *
 */
public class SafeQuestionPojo {

	private Integer questionID;
	
	private String questionName;
	
	private String answer;

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
