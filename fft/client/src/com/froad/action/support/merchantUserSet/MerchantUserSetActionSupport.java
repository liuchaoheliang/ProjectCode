package com.froad.action.support.merchantUserSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.froad.client.merchantUserSet.MerchantUserSetService;
import com.froad.client.merchantUserSet.MerchantUserSet;
/**  
 * @author Qiaopeng.Lee 
 * @date 2013-3-23  
 * @version 1.0
 */
public class MerchantUserSetActionSupport {
	private static Logger logger = Logger.getLogger(MerchantUserSetActionSupport.class);
	private MerchantUserSetService merchantUserSetService;
	
	/**
	 * 添加操作员
	 * @return boolean
	 */
	public Integer addMerchantClerk(MerchantUserSet merchantUserSet){
		Integer num = null;
		try {
			num = merchantUserSetService.addMerchantUserSet(merchantUserSet);
		} catch (Exception e) {
			logger.error("MerchantUserSetActionSupport.addMerchantClerk添加操作员出错 becode:"+merchantUserSet.getBeCode()+
					" loginName:"+merchantUserSet.getLoginName()+" beName:"+merchantUserSet.getBeName(), e);
		}
		return num;
	}
	
	/**
	 * 查询商户的所有操作员信息
	 * 商户名，工号
	 * @return List<MerchantUserSet>
	 */
	public List<MerchantUserSet> getMerchantUserSetList(MerchantUserSet merchantUserSet){
		List<MerchantUserSet> list = null;
		try {
			list = merchantUserSetService.getMerchantClerk(merchantUserSet);
		} catch (Exception e) {
			logger.error("MerchantUserSetActionSupport.getMerchantUserSetList查询商户的所有操作员信息出错 loginName:"+merchantUserSet.getLoginName()
					+" userId："+merchantUserSet.getUserId(), e);
		}
		return list;
	}
	
	public Map<String,String> getMerchantClerktMap(MerchantUserSet merchantUserSet){
		Map<String,String> merchantClerkMap = new HashMap<String,String>();
		List<MerchantUserSet> list = null;
		try {
			list = merchantUserSetService.getMerchantClerkList(merchantUserSet);
			if(list != null && list.size()>0){
				for(MerchantUserSet userset:list){
					merchantClerkMap.put(userset.getBeCode(), userset.getBeName());
				}				
			}
		} catch (Exception e) {
			logger.error("MerchantUserSetActionSupport.getMerchantUserSetMap查询商户的所有操作员MAP loginName:"+merchantUserSet.getLoginName()
					+" userId："+merchantUserSet.getUserId(), e);
		}		
		return merchantClerkMap;
	}
	
	
	public String getMaxBeCode(MerchantUserSet merchantUserSet){
		String maxBecode = null;
		try {
			maxBecode = merchantUserSetService.getMaxClerkBeCode(merchantUserSet);
		} catch (Exception e) {
			logger.error("MerchantUserSetActionSupport.getMaxBeCode查询商户操作员当前最大工号信息出错 loginName:"+merchantUserSet.getLoginName()
					+" userId："+merchantUserSet.getUserId(), e);
		}
		return maxBecode;
	}
	
	/**
	 * 更新操作员信息
	 * @return MerchantUserSet
	 */
	public MerchantUserSet updatePwdByUserIdAndBecode(MerchantUserSet merchantUserSet){
		Integer num = 0;
		MerchantUserSet clerkReq = new MerchantUserSet();
		List<MerchantUserSet> newClerkList = null;
		MerchantUserSet newClerk = null;
		try {
			num = merchantUserSetService.updatePwdByUserIdAndBecode(merchantUserSet);
		} catch (Exception e) {
			logger.info("MerchantUserSetActionSupport.updateMerchantUserSetById修改操作员信息失败 userId:"+merchantUserSet.getUserId() +" becode:"+merchantUserSet.getBeCode(),e);
		}
		if(num != null && num >0){
			logger.info("修改操作员信息成功");
			clerkReq.setId(merchantUserSet.getId());
			clerkReq.setUserId(merchantUserSet.getUserId());
			clerkReq.setBeCode(merchantUserSet.getBeCode());
			newClerkList = merchantUserSetService.getMerchantClerk(clerkReq);
		}else{
			logger.info("修改操作员信息失败 userId:"+merchantUserSet.getUserId() +" becode:"+merchantUserSet.getBeCode());
		}
		if(newClerkList !=null && newClerkList.size()>0){
			newClerk = newClerkList.get(0);
		}
		return newClerk;
	}
	
	/**
	 * 删除操作员
	 * @return boolean
	 */
	public boolean deleteMerchantClerk(MerchantUserSet merchantUserSet){
		Integer num = 0;
		try {
			num = merchantUserSetService.deleteByUserIdAndBecode(merchantUserSet);
		} catch (Exception e) {
			logger.info("MerchantUserSetActionSupport.deleteMerchantClerk删除操作员失败 userId:"+merchantUserSet.getUserId() +" becode:"+merchantUserSet.getBeCode(),e);
		}
		if(num != null && num >0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 更新操作员
	 * @return boolean
	 */
	public boolean updateMerchantClerk(MerchantUserSet merchantUserSet){
		Integer num = 0;
		try {
			num = merchantUserSetService.updateByUserIdAndBecode(merchantUserSet);
		} catch (Exception e) {
			logger.info("MerchantUserSetActionSupport.updateMerchantClerk更新操作员失败 userId:"+merchantUserSet.getUserId() +" becode:"+merchantUserSet.getBeCode(),e);
		}
		if(num != null && num >0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 分页查找  操作员
	 * @param pager
	 * @return
	 */
	public MerchantUserSet getMerchantUserSetListByPager(MerchantUserSet merchantUserSet){
		try{
			merchantUserSet=merchantUserSetService.getMerchantUserSetListByPager(merchantUserSet);
			if(merchantUserSet==null){
				merchantUserSet = new MerchantUserSet();
			}
		}
		catch(Exception e){
			logger.error("MerchantUserSetActionSupport.getMerchantUserSetListByPager分页查找操作员异常,用户ID："+merchantUserSet.getUserId());
		}
		return merchantUserSet;
	}
	
	
	public String getBelongUserBecode(String storeId){		
		return merchantUserSetService.getBelongUserBecode(storeId);
	}
	
	
	public void removeCacheMerchantUserSet(){
		merchantUserSetService.removeCacheMerchantUserSet();
	}
	
	public MerchantUserSetService getMerchantUserSetService() {
		return merchantUserSetService;
	}
	public void setMerchantUserSetService(
			MerchantUserSetService merchantUserSetService) {
		this.merchantUserSetService = merchantUserSetService;
	}	
	
	
}
