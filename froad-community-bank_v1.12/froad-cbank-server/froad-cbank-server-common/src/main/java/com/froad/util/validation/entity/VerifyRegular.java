package com.froad.util.validation.entity;

import com.froad.util.validation.anno.ExpandVerify;
import com.froad.util.validation.anno.FieldVerify;


public class VerifyRegular {

	private FieldVerify fieldVerify;
	
	private ExpandVerify expandVerify;

	public VerifyRegular(FieldVerify fieldVerify, ExpandVerify expandVerify) {
		this.fieldVerify = fieldVerify;
		this.expandVerify = expandVerify;
	}

	public FieldVerify getFieldVerify() {
		return fieldVerify;
	}

	public ExpandVerify getExpandVerify() {
		return expandVerify;
	}
	
	
	
}
