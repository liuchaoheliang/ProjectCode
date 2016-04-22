package com.froad.action.support;

import java.util.List;
import org.apache.log4j.Logger;
import com.froad.client.merchant.Merchant;
import com.froad.client.merchant.MerchantService;
import com.froad.client.merchant.PreferType;
import com.froad.util.Assert;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 商户服务 client service  ActionSupport
 */
public class MerchantActionSupport {
	private static Logger logger = Logger.getLogger(MerchantActionSupport.class);
	private MerchantService merchantService;
	
	public List<Merchant> getInnerMerchant(){
		try {
			return merchantService.getInnerMerchant();
		} catch (Exception e) {
			logger.error("查询内部商户出现异常",e);
			return null;
		}
	}
	
	public List<Merchant> getMerchantByUserId(String userId){
		List<Merchant> merchant=null;
		try {
			merchant=merchantService.getMerchantByUserId(userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.error("调用 merchantService.getMerchantByUserId(userId) 异常", e);
		}
		return merchant;
	}
	
	/**
	 * 商户申请
	 * @param Merchant
	 * @return ture 成功        flase 失败
	 */
	public boolean addMerchant(Merchant merchant){
		boolean isSuccess=true;
		try{
			Integer id=merchantService.addMerchant(merchant);
			if(id==null){
				isSuccess=false;
			}
		}
		catch(Exception e){
			isSuccess=false;
			logger.error("商户申请", e);
		}
		return isSuccess;
	} 
	
	/**
	 * ID查询出商户信息.  
	 * @param id
	 * @return 商户信息
	 */
	public Merchant getMerchantById(String id) {
		Integer mid = null;
		if(!Assert.empty(id)){
			mid = Integer.valueOf(id);			
		}else{
			mid = new Integer("0");
		}
		Merchant merchant = null;
		try {
			merchant = merchantService.getMerchantById(mid);
		} catch (Exception e) {
			logger.error("MerchantActionSupport.getMerchantById查询出商户信息异常,商户ID："+id);
		}
		
		return	merchant;
	}
	
	/**
	 * 查询商户信息
	 * @param userId
	 * @return
	 */
	public Merchant getMerchantInfo(String userId){
		List<Merchant> merchantList = null;
		Merchant m = null;
		try {
			merchantList =  merchantService.getMerchantByUserId(userId);
			if(merchantList!=null && merchantList.size()>0){
				m = merchantList.get(0);
			}
		} catch (Exception e) {
			logger.error("MerchantActionSupport.getMerchantInfo出错 userId :"+userId);
		}
		return m;
	}
	
	/**
	 * 查询商户列表
	 */
	public Merchant getMerchantList(Merchant merchant){
		Merchant meq = null;
		try {
			meq = merchantService.getMerchantList(merchant);
		} catch (Exception e) {
			logger.error("MerchantActionSupport.getMerchantList出错",e);
		}
		return meq;
	}
	
	/**
	 * 分页查询 商户列表
	 * @param pager
	 * @return
	 */
	public Merchant queryMerchantByPager(Merchant merchant){
		try{
			merchant=merchantService.getMerchantByPager(merchant);
		}
		catch(Exception e){
			logger.error("MerchantActionSupport.queryMerchantByPager分页查找商户异常 ", e);
		}
		return merchant;
	}
	
	/**
	 * 返利商家或优惠商家商家  收索
	 * @param Merchant
	 * @return Merchant
	 */
	public Merchant getMerchantListIndex(Merchant merchant){
		Merchant meq = null;
		try {
			meq = merchantService.getMerchantListIndex(merchant);
		} catch (Exception e) {
			logger.error("MerchantActionSupport.getMerchantListIndex分页查找商户异常 ", e);
		}
		return meq;
	}
	
	
	/**
	  * 方法描述：按优惠类型查询商户列表
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 孙庆亚 sunqingya@f-road.com.cn
	  * @time: Feb 22, 2013 12:03:56 PM
	  */
	public List<Merchant> getMerchantByType_CASH(){
		List<Merchant> list = null;
		try {
			list = merchantService.getMerchantByType(PreferType.CASH);
		}catch (Exception e) {
			logger.error("MerchantActionSupport.getMerchantByType异常", e);
		}
		return list;
	}
	
	/**
	  * 方法描述：按优惠类型查询商户列表
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 孙庆亚 sunqingya@f-road.com.cn
	  * @time: Feb 22, 2013 12:03:56 PM
	  */
	public List<Merchant> getMerchantByType_POINTS(){
		List<Merchant> list = null;
		try {
			list = merchantService.getMerchantByType(PreferType.POINTS);
		}catch (Exception e) {
			logger.error("MerchantActionSupport.getMerchantByType异常", e);
		}
		return list;
	}
	
	public Merchant getMerchantByPager(Merchant merchant){
		try{
			return merchantService.getMerchantByPager(merchant);
		}catch (Exception e) {
			logger.error("MerchantActionSupport.getMerchantByType异常", e);
		}
		return merchant;
	}
	//按条件查询除第三类商户以外的商户信息
	public Merchant getMerchantsPreferentialType(Merchant merchant){
		
		try{
			return merchantService.getMerchantsPreferentialType(merchant);
		}catch (Exception e) {
			logger.error("MerchantActionSupport.getMerchantsPreferentialType异常", e);
		}
		return merchant;		
	}
	
	
	public MerchantService getMerchantService() {
		return merchantService;
	}
	public void setMerchantService(MerchantService merchantService) {
		this.merchantService = merchantService;
	}
	
}
