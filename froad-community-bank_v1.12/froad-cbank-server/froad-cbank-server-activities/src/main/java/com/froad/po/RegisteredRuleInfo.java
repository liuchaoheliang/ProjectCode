/**
 * @Title: RegisteredRuleInfo.java
 * @Package com.froad.po
 * @Description: TODO
 * Copyright:2015 F-Road All Rights Reserved   
 * Company:f-road.com.cn
 * 
 * @creater froad-Joker 2015年12月1日
 * @version V1.0
 **/

package com.froad.po;

import java.io.Serializable;

/**
 * @ClassName: RegisteredRuleInfo
 * @Description: TODO
 * @author froad-Joker 2015年12月1日
 * @modify froad-Joker 2015年12月1日
 */

public class RegisteredRuleInfo implements Serializable {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	
	
	private static final long serialVersionUID = 1L;
	/**
	 * 营销活动标签关联
	 */
	private ActiveTagRelation activeTagRelation;
	/**
	 * 营销活动基础规则
	 */
	private ActiveBaseRule activeBaseRule;
	/**
	 * 注册活动明细规则
	 */
	private RegistDetailRule registeredDetailRule;

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

	public RegistDetailRule getRegisteredDetailRule() {
		return registeredDetailRule;
	}

	public void setRegisteredDetailRule(RegistDetailRule registeredDetailRule) {
		this.registeredDetailRule = registeredDetailRule;
	}

}
