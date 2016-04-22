package com.froad.action.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import net.sf.json.JSONObject;

import com.froad.action.support.MerchantActionSupport;
import com.froad.action.support.StoreActionSupport;
import com.froad.action.support.merchantUserSet.MerchantUserSetActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.Store.Store;
import com.froad.client.merchant.Merchant;
import com.froad.client.merchantPhoto.MerchantPhoto;
import com.froad.client.merchantUserSet.MerchantUserSet;
import com.froad.client.user.User;
import com.froad.common.SessionKey;
import com.froad.util.Assert;
import com.froad.util.Command;
import com.froad.util.command.MallCommand;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-3-26  
 * @version 1.0
 * 操作员
 */
public class MerchantUserSetAction extends BaseActionSupport {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -386903232285478952L;
	private MerchantActionSupport merchantActionSupport;
	private MerchantUserSetActionSupport merchantUserSetActionSupport;
	private MerchantUserSet pager;
	private MerchantUserSet merchantUserSet;
	private StoreActionSupport storeActionSupport; 
	
	private String merchantName;
	
	private List<Store> storeList;
	private String StoreId;
	/**
	 * 查询操作员列表
	 * @return
	 */
	public String list(){		
		if("1".equals(getSession(SessionKey.MERCHANT_ROLE))){
			return "nopower";
		}
		if(pager == null){
			pager = new MerchantUserSet();
		}
		merchantName = ((Merchant)getSession(SessionKey.MERCHANT)).getMstoreShortName();
		
		User user = getLoginUser();
//		if(Assert.empty(pager.getState())){
//			pager.setState(Command.FBU_USERED_STATE);			
//		}
		if(Assert.empty(pager.getUserId())){
			pager.setUserId(user.getUserID());
		}
		pager.setPageSize(10);//每页10条
		pager.setRoleType("0");
		pager.setOrderType(com.froad.client.merchantUserSet.OrderType.ASC);
		pager = merchantUserSetActionSupport.getMerchantUserSetListByPager(pager);
		for(int i=0;i<pager.getList().size();i++){
			String storeId=((MerchantUserSet)pager.getList().get(i)).getBelongStoreId();
			if(storeId==null || "".equals(storeId)){
				((MerchantUserSet)pager.getList().get(i)).setBelongStoreId("unabsorbed");
			}else{
				String storeName=storeActionSupport.getStoreById(storeId).getShortName();
				((MerchantUserSet)pager.getList().get(i)).setBelongStoreId(storeName);
			}
		}
		return "successList";
	}
	
	/**
	 * 查看员工信息
	 * @return
	 */
	public String getMerchantUserSetInfo(){
		if(merchantUserSet == null || Assert.empty(merchantUserSet.getBeCode())){
			return list();
		}
		User user = getLoginUser();
		if(Assert.empty(merchantUserSet.getUserId())){
			merchantUserSet.setUserId(user.getUserID());
		}
		List<MerchantUserSet> clerkList = merchantUserSetActionSupport.getMerchantUserSetList(merchantUserSet);
		
		if(clerkList != null && clerkList.size() > 0){
			merchantUserSet = clerkList.get(0);
			Store store = new Store();
			try{
				store.setMerchantId(Integer.valueOf(merchantUserSet.getMerchantId()));
			}catch (Exception e) {
				log.error("查询Store信息时 传入参数转型Integer失败，参数为id="+id,e);
			}
			String stroeName="";
			if(merchantUserSet.getBelongStoreId()==null || "".equals(merchantUserSet.getBelongStoreId())){
				stroeName="全部门店";
			}else{
				stroeName=storeActionSupport.getStoreById(merchantUserSet.getBelongStoreId()).getShortName();
			}
			merchantUserSet.setBelongStoreId(stroeName);			
			return "success";
		}else{
			return list();
		}
		
	}
	
	/**
	 * 删除员工
	 * @return
	 */
	public String deleteClerk(){
		if(merchantUserSet == null || Assert.empty(merchantUserSet.getBeCode())){
			return list();
		}
		User user = getLoginUser();
		if(Assert.empty(merchantUserSet.getUserId())){
			merchantUserSet.setUserId(user.getUserID());
		}
		merchantUserSet.setState(Command.FBU_DELETE_STATE);
		if(!"1000".equals(merchantUserSet.getBeCode())){//不能删除商户管理员
			merchantUserSetActionSupport.deleteMerchantClerk(merchantUserSet);
		}		
		return list();			
	}
	
	/**
	 * 启用员工
	 * @return
	 */
	public String onlineClerk(){
		if(merchantUserSet == null || Assert.empty(merchantUserSet.getBeCode())){
			return list();
		}
		User user = getLoginUser();
		if(Assert.empty(merchantUserSet.getUserId())){
			merchantUserSet.setUserId(user.getUserID());
		}
		merchantUserSet.setState(Command.FBU_USERED_STATE);
		merchantUserSetActionSupport.updateMerchantClerk(merchantUserSet);
		return list();			
	}
	
	
	/**
	  * 方法描述：财务员列表
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2013-11-22 下午03:28:52
	  */
	public String  getTreasurerList(){
		if(pager == null){
			pager = new MerchantUserSet();
		}
		merchantName = ((Merchant)getSession(SessionKey.MERCHANT)).getMstoreShortName();
		
		
		User user = getLoginUser();
//		if(Assert.empty(pager.getState())){
//			pager.setState(Command.FBU_USERED_STATE);			
//		}
		if(Assert.empty(pager.getUserId())){
			pager.setUserId(user.getUserID());
		}
		pager.setRoleType("1");
		pager.setPageSize(10);//每页10条
		pager.setOrderType(com.froad.client.merchantUserSet.OrderType.ASC);
		pager = merchantUserSetActionSupport.getMerchantUserSetListByPager(pager);
		for(int i=0;i<pager.getList().size();i++){
			String storeId=((MerchantUserSet)pager.getList().get(i)).getBelongStoreId();
			if(storeId==null || "".equals(storeId)){
				((MerchantUserSet)pager.getList().get(i)).setBelongStoreId("unabsorbed");
			}else{
				String storeName=storeActionSupport.getStoreById(storeId).getShortName();
				((MerchantUserSet)pager.getList().get(i)).setBelongStoreId(storeName);
			}
		}
		return "successList";
	}
	
	public String UpdateClerk(){
		String user_becode=getSessionValue(MallCommand.USERID_BECODE);
		if(user_becode==null){
			return "failse";
		}
		String becode=user_becode.substring(user_becode.indexOf('|')+1);
		String userID=user_becode.substring(0,user_becode.indexOf('|'));
		if(merchantUserSet==null || !"1000".equals(becode)){
			log.info("不是管理员，没有权限");
			return "failse";
		}else{			
			merchantUserSet.setBelongStoreId(StoreId);
			if("".equals(merchantUserSet.getBeCodepwd())){
				merchantUserSet.setBeCodepwd(null);
				merchantUserSet.setUserId(userID);
				merchantUserSet.setState("30");
			}else{
				String pwd = new Md5PasswordEncoder().encodePassword(merchantUserSet.getBeCodepwd(), merchantUserSet.getLoginName()+String.valueOf(merchantUserSet.getBeCode()));
				merchantUserSet.setBeCodepwd(pwd);
				merchantUserSet.setUserId(userID);
				merchantUserSet.setState("30");
			}
			if(!merchantUserSetActionSupport.updateMerchantClerk(merchantUserSet)){
				return "failse";
			}
			List<MerchantUserSet> clerkList = merchantUserSetActionSupport.getMerchantUserSetList(merchantUserSet);			
			if(clerkList != null && clerkList.size() > 0){
				merchantUserSet = clerkList.get(0);
			}
			//设置操作员的所属门店
			String stroeName="";
			if(merchantUserSet.getBelongStoreId()==null || "".equals(merchantUserSet.getBelongStoreId())){
				stroeName="全部门店";
			}else{
				stroeName=storeActionSupport.getStoreById(merchantUserSet.getBelongStoreId()).getShortName();
			}
			merchantUserSet.setBelongStoreId(stroeName);
		}
		return SUCCESS;
	}
	
	
	public String toUpdateClerk(){
		if(merchantUserSet == null || Assert.empty(merchantUserSet.getBeCode())|| "1000".equals(merchantUserSet.getBeCode())){
			return list();
		}
		User user = getLoginUser();
		if(Assert.empty(merchantUserSet.getUserId())){
			merchantUserSet.setUserId(user.getUserID());
		}
		List<MerchantUserSet> clerkList = merchantUserSetActionSupport.getMerchantUserSetList(merchantUserSet);
		
		if(clerkList != null && clerkList.size() > 0){
			merchantUserSet = clerkList.get(0);
			storeList = storeActionSupport.getStoresByMerchantId(Integer.valueOf(merchantUserSet.getMerchantId()));
			return "success";
		}else{
			return list();
		}
		
	}
	
	
	public MerchantActionSupport getMerchantActionSupport() {
		return merchantActionSupport;
	}
	public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport) {
		this.merchantActionSupport = merchantActionSupport;
	}
	public MerchantUserSetActionSupport getMerchantUserSetActionSupport() {
		return merchantUserSetActionSupport;
	}
	public void setMerchantUserSetActionSupport(
			MerchantUserSetActionSupport merchantUserSetActionSupport) {
		this.merchantUserSetActionSupport = merchantUserSetActionSupport;
	}

	public MerchantUserSet getPager() {
		return pager;
	}

	public void setPager(MerchantUserSet pager) {
		this.pager = pager;
	}

	public MerchantUserSet getMerchantUserSet() {
		return merchantUserSet;
	}

	public void setMerchantUserSet(MerchantUserSet merchantUserSet) {
		this.merchantUserSet = merchantUserSet;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public StoreActionSupport getStoreActionSupport() {
		return storeActionSupport;
	}

	public void setStoreActionSupport(StoreActionSupport storeActionSupport) {
		this.storeActionSupport = storeActionSupport;
	}

	public List<Store> getStoreList() {
		return storeList;
	}

	public void setStoreList(List<Store> storeList) {
		this.storeList = storeList;
	}

	public String getStoreId() {
		return StoreId;
	}

	public void setStoreId(String storeId) {
		StoreId = storeId;
	}
	

	
}
