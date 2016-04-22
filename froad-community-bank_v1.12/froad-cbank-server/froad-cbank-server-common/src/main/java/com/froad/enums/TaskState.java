package com.froad.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 任务状态
 * ClassName: TaskState
 * Function: 
 * date: 2015年10月28日 上午11:32:11
 *
 * @author vania
 * @version
 */
public enum TaskState {

	FINISH("0", "结束")
	, ACTIVE("1", "活动")
	;

	private String code;

	private String description;

	private TaskState(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return this.description;
	}

	public static TaskState getByCode(String code) {
		if (StringUtils.isNotBlank(code)) {
			for (TaskState b : values()) {
				if (b.getCode().equals(code)) {
					return b;
				}
			}
		}
		return null;
	}
}
