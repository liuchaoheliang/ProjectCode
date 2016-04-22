package com.froad.action.web;

import java.util.List;

import net.sf.json.JSONObject;

import com.froad.action.support.MemberCollectActionSupport;
import com.froad.action.support.MerchantActionSupport;
import com.froad.action.support.MerchantTrainActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.memberCollect.MemberCollect;
import com.froad.client.merchant.Merchant;
import com.froad.client.merchantTrain.MerchantTrain;
import com.froad.util.Command;
import com.froad.util.command.MallCommand;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-26  
 * @version 1.0
 */
public class MerchantCollectAction extends BaseActionSupport {
	/**
	 * UID
	 */
	private static final long serialVersionUID = 5628657813562320245L;
	private MemberCollectActionSupport memberCollectActionSupport;
	private MerchantActionSupport merchantActionSupport;
	private MerchantTrainActionSupport merchantTrainActionSupport;
	private String merchantId;//商户ID
	private MemberCollect memberCollectPager=new MemberCollect();
	private Merchant newMerchantPager=new Merchant();
	
	public String list(){
		log.info("收藏列表");
		
		//当前用户的ID
		String userId=(String) getSession(MallCommand.USER_ID);
		
		memberCollectPager.setUserid(userId);
		memberCollectPager.setState("30");
		memberCollectPager=memberCollectActionSupport.getMemberCollectByPager(memberCollectPager);
		
		//最新商户
		newMerchantPager.setPageNumber(1);
		newMerchantPager.setPageSize(10);
		newMerchantPager.setState("30");
		newMerchantPager=merchantActionSupport.getMerchantsPreferentialType(newMerchantPager);
		return "list";
	}
	
	/**
	  * 方法描述：增加收藏
	  * @param: 
	  * @return: json
	  */
	public String addMerchantCollect(){
		log.info("增加收藏 merchantId="+merchantId);
		
		//当前用户的ID
		String userId=(String) getSession(MallCommand.USER_ID);
		
		if(userId==null){
			log.info("会员未登录");
			return ajaxJsonWarnMessage("收藏失败,请先登录!");
		}
		
		//是否是会员
		String isUser=(String) getSession(MallCommand.LOGIN_MERCHANT_OR_USER);
		if(isUser==null || !"0".equals(isUser)){
			log.info("不是会员");
			return ajaxJsonWarnMessage("收藏失败,商户操作无效!");
		}
		
		memberCollectPager.setUserid(userId);
		memberCollectPager.setMerchantId(merchantId);
		memberCollectPager=memberCollectActionSupport.getMemberCollectByPager(memberCollectPager);
		
		if(memberCollectPager.getList().size()>0){
			log.info("此商户已经收藏");
			return ajaxJsonWarnMessage("此商户已经收藏!");
		}
		
		
		MemberCollect memberCollect=new MemberCollect();
		
		memberCollect.setUserid(userId);
		memberCollect.setMerchantId(merchantId);
		memberCollect.setState("30");
		boolean isSuccess=memberCollectActionSupport.addMemberCollect(memberCollect);
		
		if(isSuccess){
			//增加直通车收藏数
			MerchantTrain merchantTran=merchantTrainActionSupport.getMerchantTrainByMerchantId(merchantId, null);
			if(merchantTran!=null){
				
				if(merchantTran.getCollectes()==null || "".equals(merchantTran.getCollectes())){
					merchantTran.setCollectes("0");
				}
				
				Integer oldCollect=Integer.parseInt(merchantTran.getCollectes());
				
				MerchantTrain newMerchantTran=new MerchantTrain();
				Integer newCollect=oldCollect+1;
				newMerchantTran.setId(merchantTran.getId());
				newMerchantTran.setCollectes(newCollect+"");
				merchantTrainActionSupport.updMerchantTrain(newMerchantTran);
			}
			return ajaxJsonSuccessMessage("收藏成功!");
		}
		else{
			return ajaxJsonErrorMessage("收藏失败!");
		}
	}
	
	public String delete(){
		log.info("删除收藏 ID="+id);		
		memberCollectActionSupport.deleteMemberCollect(Integer.parseInt(id));
		redirectionUrl="member_favorite_list.action";
		//增加直通车收藏数
		MerchantTrain merchantTran=merchantTrainActionSupport.getMerchantTrainByMerchantId(merchantId, null);
		if(merchantTran!=null){
			Integer oldCollect=Integer.parseInt(merchantTran.getCollectes());
			Integer newCollect=0;
			
			MerchantTrain newMerchantTran=new MerchantTrain();
			if(oldCollect<1){
				newCollect=0;
			}
			else{
				newCollect=oldCollect-1;
			}
			newMerchantTran.setId(merchantTran.getId());
			newMerchantTran.setCollectes(newCollect+"");
			merchantTrainActionSupport.updMerchantTrain(newMerchantTran);
		}
		
		return list();
	}
	
	public MemberCollectActionSupport getMemberCollectActionSupport() {
		return memberCollectActionSupport;
	}
	public void setMemberCollectActionSupport(
			MemberCollectActionSupport memberCollectActionSupport) {
		this.memberCollectActionSupport = memberCollectActionSupport;
	}
	public MerchantActionSupport getMerchantActionSupport() {
		return merchantActionSupport;
	}
	public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport) {
		this.merchantActionSupport = merchantActionSupport;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public MemberCollect getMemberCollectPager() {
		return memberCollectPager;
	}
	public void setMemberCollectPager(MemberCollect memberCollectPager) {
		this.memberCollectPager = memberCollectPager;
	}
	public Merchant getNewMerchantPager() {
		return newMerchantPager;
	}
	public void setNewMerchantPager(Merchant newMerchantPager) {
		this.newMerchantPager = newMerchantPager;
	}

	public MerchantTrainActionSupport getMerchantTrainActionSupport() {
		return merchantTrainActionSupport;
	}

	public void setMerchantTrainActionSupport(
			MerchantTrainActionSupport merchantTrainActionSupport) {
		this.merchantTrainActionSupport = merchantTrainActionSupport;
	}
}
