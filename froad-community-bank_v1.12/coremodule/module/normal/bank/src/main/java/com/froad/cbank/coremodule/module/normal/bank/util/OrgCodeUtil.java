/**
 * Project Name:coremodule-bank
 * File Name:OrgNamesUtil.java
 * Package Name:com.froad.cbank.coremodule.module.normal.bank.util
 * Date:2015年9月14日下午9:17:10
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.bank.util;

import java.util.HashMap;
import java.util.Map;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.module.normal.bank.enums.OrderTypeEnum;
import com.froad.thrift.vo.AreaVo;

/**
 * ClassName:OrgNamesUtil
 * Reason:	 机构名称截取工具类
 * Date:     2015年9月14日 下午9:17:10
 * @author   明灿
 * @version  
 * @see 	 
 */
public class OrgCodeUtil {
	
	/**
	 * 
	 * getOrgNames:如果orgNames超过三个就取后面两个.
	 *
	 * @author 明灿
	 * 2015年9月14日 下午9:21:44
	 * @param orgNames
	 * @return
	 *
	 */
	public static String getOrgNames(String orgNames, String type) {
		StringBuffer sb = new StringBuffer();
		if (OrderTypeEnum.PRESALE.getCode().equals(type) && StringUtil.isNotBlank(orgNames)) {
			String[] split = orgNames.split(",");
			if (split != null && split.length > 1) {
				for (int i = 0; i < split.length; i++) {
					if (i != (split.length - 1)) {
						sb.append(splitOrgNames(split[i])).append(",");
					} else {
						sb.append(splitOrgNames(split[i]));
					}
				}
			} else {
				return splitOrgNames(orgNames);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * splitOrgNames:切割方法
	 *
	 * @author 明灿
	 * 2015年9月20日 上午1:30:07
	 * @param orgNames
	 * @return
	 *
	 */
	public static String splitOrgNames(String orgNames) {
		String result = orgNames;
		if (StringUtil.isEmpty(orgNames)) {
			return result;
		} else {
			String[] temp = orgNames.split("-");
			if (temp != null && temp.length > 2) {
				result = temp[1] + "-" + temp[2];
			}
		}
		return result;
	}

	/**
	 * 
	 * getArea:获取门店区域名称
	 * 
	 * @author chenmingcan@froad.com.cn 2016年1月4日 下午2:55:21
	 * @param listvo
	 * @return
	 *
	 */
	public static Map<String, String> getArea(AreaVo listvo) {
		Map<String, String> str = new HashMap<String, String>();
		if (listvo != null) {
			String[] pathid = listvo.getTreePath().split(",");
			String[] name = listvo.getTreePathName().split(",");
			if (pathid.length > 0 && name.length > 0) {
				str.put("countyId", pathid[0]);
				str.put("countyName", name[0]);
				if (pathid.length > 1) {
					str.put("cityId", pathid[1]);
					str.put("cityName", name[1]);
				}
				if (pathid.length > 2) {
					str.put("areaId", pathid[2]);
					str.put("areaName", name[2]);
				}
			}
		}
		return str;
	}
}
