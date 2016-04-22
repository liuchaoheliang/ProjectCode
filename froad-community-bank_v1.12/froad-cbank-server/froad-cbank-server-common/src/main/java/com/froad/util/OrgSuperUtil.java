package com.froad.util;

import com.froad.enums.OrgLevelEnum;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.po.Client;
import com.froad.po.Org;

public class OrgSuperUtil {

	/**
	 * 取上一级机构orgCode
	 * @param org
	 * @return 上级机构编号
	 */
	public static String getOrgSuper(Org org){
		String orgTop="";
		OrgLevelEnum level = OrgLevelEnum.getByLevel(org.getOrgLevel());
		switch (level) {
		case orgLevel_two:
			orgTop=org.getProvinceAgency();
			break;
		case orgLevel_three:
			orgTop=org.getCityAgency();
			break;
		case orgLevel_four:
			orgTop=org.getCountyAgency();
			break;
		default:
			break;
		}
		return orgTop;
	}
	
	/**
	 * 取网点级别
	 * @param clientId
	 * @return
	 */
	public static String getPointOrgLevel(String clientId){
		String findOrg="";
		//取网点
		Client client = new CommonLogicImpl().getClientById(clientId);
		if(Checker.isNotEmpty(client)){
			int bankType = Integer.parseInt(client.getBankType().trim());
			//0重庆 代表省联社-支行-分理处    1安徽代表省联社-法人行社-网点  2-省联社-市-县-区
			switch (bankType) {
				case 0: 
					findOrg="3";
					break;
				case 1: 
					findOrg="3";
					break;	
				case 2: 
					findOrg="4";
					break;	
				default:
					findOrg="3";
					break;	
			}
		}
		return findOrg;
		
	}
	
	
	
}
