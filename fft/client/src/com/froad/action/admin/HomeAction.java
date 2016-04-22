package com.froad.action.admin;

import java.util.List;

import com.froad.action.support.MerchantActionSupport;
import com.froad.action.support.MerchantTrainActionSupport;
import com.froad.action.support.SellersActionSupport;
import com.froad.action.support.StoreActionSupport;
import com.froad.action.support.UserActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.Store.Store;
import com.froad.client.merchant.Merchant;
import com.froad.client.merchantTrain.MerchantTrain;
import com.froad.client.user.AppException_Exception;
import com.froad.client.user.User;
import com.froad.common.SessionKey;
import com.froad.util.Assert;
import com.froad.util.command.MallCommand;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-26  
 * @version 1.0
 */
public class HomeAction extends BaseActionSupport {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -7154915698469916852L;
	private MerchantActionSupport merchantActionSupport;
	private UserActionSupport userActionSupport;
	private MerchantTrainActionSupport merchantTrainActionSupport;
	private StoreActionSupport storeActionSupport;
	private Merchant merchant;
	private User user;
	//private Cashdesk cashdesk;
	private SellersActionSupport sellersActionSupport;
	
	private MerchantTrain merchantTrain;//商户直通车
	
	

	//基本信息
	public String home(){
		log.info("基本信息");
		
		//当前用户ID
		String userId=(String) getSession(MallCommand.USER_ID);
		if(Assert.empty(userId)){
			return "failse";
		}
		user=userActionSupport.queryUserAllByUserID(userId);
		
		merchant=new Merchant();
		merchant=merchantActionSupport.getMerchantInfo(userId);
		List<Store> slist=storeActionSupport.getStoresByMerchantId(merchant.getId());
		if(slist.size()>0){
			merchant.setMstoreAddress("<a style='text-decoration:none;color:#660000;' target='_blank' href=\"merchant_annex_info.action?pager.merchantId="+merchant.getId()+"&pager.pageNumber=1\">查看全部"+slist.size()+"家门店地址</a>");
		}else{
			String s=merchant.getMstoreAddress().replaceAll("<", "&lt;");
			merchant.setMstoreAddress(s.replaceAll(">", "&gt;"));
		}
		
		//cashdesk=(Cashdesk)getSession("cashdesk");
		
//		try{
//			if(cashdesk==null){
//				cashdesk=sellerActionSupport.getCashdesk(userId);
//			}
//		}catch(Exception e){
//			log.error("调用服务失败！", e);
//			return "failse";
//		}
//		setSession("cashdesk", cashdesk);
		
		
		//直通车
		merchantTrain=merchantTrainActionSupport.getMerchantTrainByMerchantId(merchant.getId()!=null?merchant.getId().toString():"", null);

		if("1".equals(getSession(SessionKey.MERCHANT_ROLE))){
			return "finance";
		}
		return "home";
	}
	
	public MerchantActionSupport getMerchantActionSupport() {
		return merchantActionSupport;
	}

	public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport) {
		this.merchantActionSupport = merchantActionSupport;
	}
	
	public UserActionSupport getUserActionSupport() {
		return userActionSupport;
	}

	public void setUserActionSupport(UserActionSupport userActionSupport) {
		this.userActionSupport = userActionSupport;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public SellersActionSupport getSellesActionSupport() {
		return sellersActionSupport;
	}

	public void setSellersActionSupport(SellersActionSupport sellersActionSupport) {
		this.sellersActionSupport = sellersActionSupport;
	}
	
	public MerchantTrain getMerchantTrain() {
		return merchantTrain;
	}

	public void setMerchantTrain(MerchantTrain merchantTrain) {
		this.merchantTrain = merchantTrain;
	}

	public MerchantTrainActionSupport getMerchantTrainActionSupport() {
		return merchantTrainActionSupport;
	}

	public void setMerchantTrainActionSupport(
			MerchantTrainActionSupport merchantTrainActionSupport) {
		this.merchantTrainActionSupport = merchantTrainActionSupport;
	}

	public SellersActionSupport getSellersActionSupport() {
		return sellersActionSupport;
	}

	public StoreActionSupport getStoreActionSupport() {
		return storeActionSupport;
	}

	public void setStoreActionSupport(StoreActionSupport storeActionSupport) {
		this.storeActionSupport = storeActionSupport;
	}
	
	
}
