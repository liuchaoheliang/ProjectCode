package com.froad.action.admin.seller.trans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.froad.action.support.TransActionSupport;
import com.froad.client.sellers.Seller;
import com.froad.client.sellers.SellerChannel;
import com.froad.client.transRule.TransRule;

public class CashdeskService {
	private TransActionSupport transActionSupport;
	private static Logger log = Logger.getLogger(CashdeskService.class);
	public Cashdesk getCashdesk(Seller seller){
		Cashdesk cashdesk = new Cashdesk();
		try {
			if (seller == null)
				return cashdesk;
			cashdesk.setSeller(seller);
			List<SellerChannel> sellerDepositChannelList =seller.getSellerChannelList();
			cashdesk.setSellerDepositChannelList(sellerDepositChannelList);
			SellerChannel sellerDepositChannel = sellerDepositChannelList
					.get(0);
			Map<String, Map> ruleCache=new HashMap();
			ruleCache=transActionSupport.getRulesFromCache();
			TransRule sellerRule=(TransRule) ruleCache.get("rules-ruleId").get(sellerDepositChannel.getSellerRuleId());
			cashdesk.setSellerRule(sellerRule);
			Map<String, SellerChannel> accountIdSellerAccountMap = new HashMap();
			for (SellerChannel sellerChannel : sellerDepositChannelList) {
				accountIdSellerAccountMap.put(String.valueOf(sellerChannel
						.getId()), sellerChannel);
			}
			cashdesk.setSellerAccountMap(accountIdSellerAccountMap);
		} catch (Exception e) {
			log.error("调用服务失败！", e);
		}
		return cashdesk;
	}
	public TransActionSupport getTransActionSupport() {
		return transActionSupport;
	}
	public void setTransActionSupport(TransActionSupport transActionSupport) {
		this.transActionSupport = transActionSupport;
	}
	
	
}
