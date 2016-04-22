package com.froad.action.member;

import java.util.List;

import com.froad.action.support.BuyersActionSupport;
import com.froad.action.support.MerchantActionSupport;
import com.froad.action.support.UserActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.buyers.Buyers;
import com.froad.client.merchant.Merchant;
import com.froad.util.command.MallCommand;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-3-1  
 * @version 1.0
 */
public class MenuAction extends BaseActionSupport {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -4122067420154329239L;
	private UserActionSupport userActionSupport;
	private MerchantActionSupport merchantActionSupport;
	private BuyersActionSupport buyersActionSupport;
	
	

	private Merchant newMerchantPager=new Merchant();
	
	private String flag="false";
	

	// 主体
	public String main() {
		
		newMerchantPager.setPageNumber(1);
		newMerchantPager.setPageSize(4);
		newMerchantPager.setState("30");
		newMerchantPager.setPreferentialType("1");
		newMerchantPager=merchantActionSupport.queryMerchantByPager(newMerchantPager);
		
		//查询是否是买家
		String userId = (String)getSession(MallCommand.USER_ID);
		Buyers buyers=buyersActionSupport.getBuyerByUserId(userId);
		
		if(buyers==null){
			flag="true";
		}
		log.info("userId:"+userId+" flag："+ flag);
		
		return "main";
	}

	public UserActionSupport getUserActionSupport() {
		return userActionSupport;
	}

	public void setUserActionSupport(UserActionSupport userActionSupport) {
		this.userActionSupport = userActionSupport;
	}

	public MerchantActionSupport getMerchantActionSupport() {
		return merchantActionSupport;
	}

	public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport) {
		this.merchantActionSupport = merchantActionSupport;
	}

	public Merchant getNewMerchantPager() {
		return newMerchantPager;
	}

	public void setNewMerchantPager(Merchant newMerchantPager) {
		this.newMerchantPager = newMerchantPager;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public BuyersActionSupport getBuyersActionSupport() {
		return buyersActionSupport;
	}

	public void setBuyersActionSupport(BuyersActionSupport buyersActionSupport) {
		this.buyersActionSupport = buyersActionSupport;
	}
}
