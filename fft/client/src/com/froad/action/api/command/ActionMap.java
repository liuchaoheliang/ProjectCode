package com.froad.action.api.command;

import java.util.Map;
import java.util.TreeMap;

public class ActionMap {
	
public static final Map<String, String> actionNameMap;//action
	
	static{
		actionNameMap = new TreeMap<String, String>();
		actionNameMap.put("0000", "common_versionSearch");
		actionNameMap.put("0001", "common_shortmsg");
		actionNameMap.put("0002", "common_queryRule");
		actionNameMap.put("0003", "common_msgResend");
		actionNameMap.put("0101", "user_registered");
		actionNameMap.put("0102", "user_login");
		actionNameMap.put("0103", "user_changePwd");
		actionNameMap.put("0104", "user_queryPointAccount");
		actionNameMap.put("0105", "user_changeUserPhone");
		actionNameMap.put("0106", "user_changeUserInfo");
		actionNameMap.put("0113", "user_queryUserInfo");
		actionNameMap.put("0107", "user_querySuggestion");
		actionNameMap.put("0108", "user_addSuggestion");
		actionNameMap.put("0109", "user_querySuggestionReply");
		actionNameMap.put("0110", "user_favorite");
		actionNameMap.put("0111", "user_favoriteadd");
		actionNameMap.put("0112", "user_favoritedel");
		actionNameMap.put("0201", "task_sign");
		actionNameMap.put("0301", "pointObtain_validate_hfcz");
		actionNameMap.put("0302", "pointObtain_validate_lottery");
		actionNameMap.put("0303", "pointObtain_validate_goods");
		actionNameMap.put("0304", "pointObtain_addTran_hfcz");
		actionNameMap.put("0305", "pointObtain_addTran_Lottery");
		actionNameMap.put("0306", "pointObtain_addTran_goods");
		actionNameMap.put("0401", "merPre_queryMerchantByType");
		actionNameMap.put("0402", "merPre_merPreInfoByMerchantId");
		actionNameMap.put("0501", "tran_queryGoodsGroup");
		actionNameMap.put("0502", "tran_queryGoodsExchange");
		actionNameMap.put("0503", "tran_queryGoodsCarry");
		actionNameMap.put("0601", "goodsRebateRack_getMerchantByType_POINTS");
		actionNameMap.put("0602", "goodsRebateRack_queryMerchantProductByPager");
		actionNameMap.put("0701", "carryPoint_queryCarryPoints");
		actionNameMap.put("0702", "carryPoint_carryPoints");
		actionNameMap.put("0801", "recommend_queryTypeRecommend");
		actionNameMap.put("0901", "goodsGroup_queryClientGoodsGroup");
		actionNameMap.put("0902", "goodsGroup_queryClientGroupDetail");
		actionNameMap.put("0903", "goodsGroup_addGoodsGroupTran");
		//商户部分
		actionNameMap.put("10011", "merchantAPIAction_login");
		actionNameMap.put("10021", "merchantAPIAction_sendTran");
		actionNameMap.put("10022", "merchantAPIAction_confirmTran");
		actionNameMap.put("10031", "merchantAPIAction_presentPoints");
		actionNameMap.put("10041", "merchantAPIAction_queryGroup");
		actionNameMap.put("10042", "merchantAPIAction_inUseGroup");
		actionNameMap.put("10051", "merchantTranAction_queryGroupTran");
		actionNameMap.put("10052", "merchantTranAction_queryReceiveTran");
		actionNameMap.put("10053", "merchantTranAction_queryPointsTran");
		actionNameMap.put("10054", "merchantTranAction_queryExchangeTran");
		//Test测试
		actionNameMap.put("0904", "select");
	}
	 
}