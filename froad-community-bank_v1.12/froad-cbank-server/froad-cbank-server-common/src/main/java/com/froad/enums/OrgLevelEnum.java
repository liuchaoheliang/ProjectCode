package com.froad.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 机构级别Enum
 * @author ll 20150326
 *
 */
public enum OrgLevelEnum {

	
	// 机构级别1-2-3-4-
	orgLevel_one("1"),
	orgLevel_two("2"),
	orgLevel_three("3"),
	orgLevel_four("4"),
	
	
	;

	private String level;
	
	public String getLevel() {
		return level;
	}
	private OrgLevelEnum(String level) {
        this.level = level;
    }
	
	public static OrgLevelEnum getByLevel(String level){
		if(StringUtils.isNotEmpty(level)){
			for(OrgLevelEnum o : values()){
				if(o.getLevel().equals(level)){
					return o;
				}
			}
		}
		return null;
	}
}
