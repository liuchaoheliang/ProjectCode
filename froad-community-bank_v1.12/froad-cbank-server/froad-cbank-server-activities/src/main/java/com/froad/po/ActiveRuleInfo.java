package com.froad.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ActiveRuleInfo implements Serializable {
	private ActiveTagRelation activeTagRelation;
	private ActiveBaseRule activeBaseRule;
	private ActiveDetailRule activeDetailRule;

	public ActiveTagRelation getActiveTagRelation() {
		return activeTagRelation;
	}

	public void setActiveTagRelation(ActiveTagRelation activeTagRelation) {
		this.activeTagRelation = activeTagRelation;
	}

	public ActiveBaseRule getActiveBaseRule() {
		return activeBaseRule;
	}

	public void setActiveBaseRule(ActiveBaseRule activeBaseRule) {
		this.activeBaseRule = activeBaseRule;
	}

	public ActiveDetailRule getActiveDetailRule() {
		return activeDetailRule;
	}

	public void setActiveDetailRule(ActiveDetailRule activeDetailRule) {
		this.activeDetailRule = activeDetailRule;
	}

	public ActiveRuleInfo() {
	}

	public ActiveRuleInfo(ActiveTagRelation activeTagRelation,
			ActiveBaseRule activeBaseRule, ActiveDetailRule activeDetailRule) {
		super();
		this.activeTagRelation = activeTagRelation;
		this.activeBaseRule = activeBaseRule;
		this.activeDetailRule = activeDetailRule;
	}
}
