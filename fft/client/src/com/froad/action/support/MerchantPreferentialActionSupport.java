package com.froad.action.support;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.froad.client.merchantPreferential.MerchantPreferential;
import com.froad.client.merchantPreferential.MerchantPreferentialService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 商户优惠信息  client service  ActionSupport
 */
public class MerchantPreferentialActionSupport {
	private static Logger logger = Logger.getLogger(MerchantPreferentialActionSupport.class);
	private MerchantPreferentialService merchantPreferentialService;
	
	/**
	 * 分页查找  优惠活动
	 * @param pager
	 * @return
	 */
	public MerchantPreferential queryMerchantPreferentialByPager(MerchantPreferential merchantPreferential){
		try{
			merchantPreferential=merchantPreferentialService.getMerchantPreferentialByPager(merchantPreferential);
		}
		catch(Exception e){
			logger.error("MerchantPreferentialActionSupport.queryMerchantPreferentialByPager分页查找优惠活动异常,商户ID："+merchantPreferential.getMerchantId());
		}
		return merchantPreferential;
	}
	
	/**
	 * ID 查找优惠活动
	 * @param id
	 * @return
	 */
	public MerchantPreferential getMerchantPreferentialById(Integer id){
		MerchantPreferential mp = new MerchantPreferential();
		try{
			mp =  merchantPreferentialService.getMerchantPreferentialById(id);
			if(mp == null){
				mp = new MerchantPreferential();
			}
		}
		catch(Exception e){
			logger.error("MerchantPreferentialActionSupport.getMerchantPreferentialById查找优惠活动异常", e);
			mp = new MerchantPreferential();
		}
		return mp;
	}
	/**
	 * 增加商户优惠信息
	 * @param mp
	 * @return
	 */
	public Integer addMerchantPreferentials(MerchantPreferential mp){		
		logger.info("增加优惠");
		return merchantPreferentialService.addMerchantPreferential(mp);
	}
	/**
	 * 更新商家优惠信息记录
	 * @param merchantPreferential
	 * @return
	 */
	public MerchantPreferential updMerchantPreferentials(MerchantPreferential merchantPreferential){		
		logger.info("修改产品");		
		MerchantPreferential mp = new MerchantPreferential();
		try {
			mp = merchantPreferentialService.updMerchantPreferentialInfo(merchantPreferential);
			if(mp == null){
				 mp = new MerchantPreferential();
			}
		} catch (Exception e) {
			logger.error("MerchantPreferentialActionSupport.updMerchantPreferentials出错", e);
			mp = new MerchantPreferential();
		}
		return mp;
	}
	
	/**
	 * 通过商户ID查询出商户启用优惠信息
	 * @param merchantId
	 * @return 商户优惠信息
	 */
	public List<MerchantPreferential> getMerchantPreferentialListByMerchantId(MerchantPreferential merchantPreferential){
		List<MerchantPreferential> merchantPreferentialList=new ArrayList<MerchantPreferential>();
			try {
				merchantPreferentialList=merchantPreferentialService.getMerchantPreferentialListByMerchantId(merchantPreferential);
				if(merchantPreferentialList==null){
					merchantPreferentialList=new ArrayList<MerchantPreferential>();
				}
			} catch (Exception e) {
				logger.error("MerchantActionSupport.getMerchantPreferentialInfo出错,商户ID："+merchantPreferential.getMerchantId());
				e.printStackTrace();
				merchantPreferentialList=new ArrayList<MerchantPreferential>();
			}
		return merchantPreferentialList;
	}
	
	/**
	 * 根据ID删除优惠信息
	 * @param mp
	 * @return
	 */
	public Integer deletePreferentialInfo(MerchantPreferential mp){
		logger.info("删除相片");
		Integer count = 0;
		try {
			count = merchantPreferentialService.deletePreferentialById(mp);
		} catch (Exception e) {
			logger.error("MerchantActionSupport.deletePreferentialInfo出错 优惠信息ID："+mp.getId(), e);
			count = new Integer(0);
		}
		return count;
	}
	
	public MerchantPreferentialService getMerchantPreferentialService() {
		return merchantPreferentialService;
	}
	public void setMerchantPreferentialService(
			MerchantPreferentialService merchantPreferentialService) {
		this.merchantPreferentialService = merchantPreferentialService;
	}
	public List<MerchantPreferential> getMerchantPreferentialInfoByMerchantId(String merchantId){
		return merchantPreferentialService.getMerchantPreferentialInfoByMerchantId(merchantId);
	}
}
