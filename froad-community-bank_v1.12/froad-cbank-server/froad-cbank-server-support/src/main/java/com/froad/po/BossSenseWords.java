package com.froad.po;



/**
 * Boss敏感词表
 */
public class BossSenseWords implements java.io.Serializable {

	// Fields

	private Long id;//主键id
	private String word;//敏感词
	private Boolean isEnable;// 是否启用

	/** default constructor */
	public BossSenseWords() {
	}

	/** full constructor */
	public BossSenseWords(String word, Boolean isEnable) {
		this.word = word;
		this.isEnable = isEnable;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWord() {
		return this.word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Boolean getIsEnable() {
		return this.isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

}