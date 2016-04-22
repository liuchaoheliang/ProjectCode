package com.froad.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 实例状态
 * ClassName: InstanceState
 * Function: 
 * date: 2015年10月28日 上午11:31:53
 *
 * @author vania
 * @version
 */
public enum InstanceState {

	FINISH("0", "结束")
	, ACTIVE("1", "活动")
	;

	private String code;

	private String description;

	private InstanceState(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return this.description;
	}

	public static InstanceState getByCode(String code) {
		if (StringUtils.isNotBlank(code)) {
			for (InstanceState b : values()) {
				if (b.getCode().equals(code)) {
					return b;
				}
			}
		}
		return null;
	}
}
