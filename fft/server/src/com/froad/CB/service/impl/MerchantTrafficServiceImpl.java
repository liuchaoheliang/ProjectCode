package com.froad.CB.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.merchant.MerchantTrafficDAO;
import com.froad.CB.po.merchant.Merchant;
import com.froad.CB.po.merchant.MerchantTraffic;
import com.froad.CB.service.MerchantService;
import com.froad.CB.service.MerchantTrafficService;


@WebService(endpointInterface="com.froad.CB.service.MerchantTrafficService")
public class MerchantTrafficServiceImpl implements MerchantTrafficService{

	private static final Logger logger=Logger.getLogger(MerchantTrafficServiceImpl.class);
	
	private MerchantService merchantService;
	
	private MerchantTrafficDAO merchantTrafficDAO;
	
	@Override
	public List<MerchantTraffic> getMerchantTrafficInfo(String merchantId,
			String userId) {
		List<MerchantTraffic> result=new ArrayList<MerchantTraffic>();
		if(merchantId!=null&&!"".equals(merchantId)&&userId!=null&&!"".equals(userId)){
			Merchant merchant = null;
			List<Merchant> merchantList=merchantService.getMerchantByUserId(userId);
			if(merchantList!=null && merchantList.size()>0){
				merchant = merchantList.get(0);				
			}
			if(merchant!=null && merchantId.equals(String.valueOf(merchant.getId()))){
				return getMerchantTrafficInfoByMerchantId(merchantId);
			}else{
				return result;
			}
		}
		if(merchantId!=null&&!"".equals(merchantId)){
			return getMerchantTrafficInfoByMerchantId(merchantId);
		} 
		if(userId!=null&&!"".equals(userId)){
			return getMerchantTrafficInfoByUserId(userId);
		}
		return result;
	}
	
	@Override
	public List<MerchantTraffic> getMerchantTrafficInfoByMerchantId(String merchantId) {
		if(merchantId==null||"".equals(merchantId))
			return null;
		MerchantTraffic queryCon=new MerchantTraffic();
		queryCon.setMerchantId(merchantId);
		List<MerchantTraffic> results=merchantTrafficDAO.selectMerchantTraffics(queryCon);
		return results;
	}
	
	@Override
	public List<MerchantTraffic> getMerchantTrafficInfoByUserId(String userId) {
		if(userId==null||"".equals(userId)){
			return null;
		}
		List<Merchant> merchantList=merchantService.getMerchantByUserId(userId);
		Merchant merchant = null;
		if(merchantList!=null && merchantList.size()>0){
			merchant = merchantList.get(0);			
		}
		return merchant==null?null:this.getMerchantTrafficInfoByMerchantId(String.valueOf(merchant.getId()));
	}

	public void setMerchantService(MerchantService merchantService) {
		this.merchantService = merchantService;
	}

	public void setMerchantTrafficDAO(MerchantTrafficDAO merchantTrafficDAO) {
		this.merchantTrafficDAO = merchantTrafficDAO;
	}

	@Override
	public Integer addMerchantTraffic(MerchantTraffic merchantTraffic) {
		if(merchantTraffic==null){
			logger.error("参数为空，添加交通信息失败");
			return null;
		}
		return merchantTrafficDAO.insert(merchantTraffic);
	}

	@Override
	public void updateMerchantTraffic(MerchantTraffic merchantTraffic) {
		if(merchantTraffic==null||merchantTraffic.getId()==null||"".equals(merchantTraffic.getId())){
			logger.error("参数不完整，更新交通信息失败");
			return;
		}
		merchantTrafficDAO.updateByPrimaryKeySelective(merchantTraffic);
	}

}
