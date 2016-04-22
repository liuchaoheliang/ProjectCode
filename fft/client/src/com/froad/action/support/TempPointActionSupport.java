package com.froad.action.support;

import java.util.List;

import org.apache.log4j.Logger;

import com.froad.client.tempPoint.TempPoint;
import com.froad.client.tempPoint.TempPointService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-3-7  
 * @version 1.0
 */
public class TempPointActionSupport {
	private static Logger logger = Logger.getLogger(TempPointActionSupport.class);
	private TempPointService tempPointService;
	
	public List<TempPoint> getTempPoint(TempPoint tempPoint){
		List<TempPoint> list = null;
		try {
			list = tempPointService.getTempPointBySelective(tempPoint);
		} catch (Exception e) {
			logger.error("TempPointActionSupport.getTempPoint出错 银行卡账户名:"+tempPoint.getAccountName()+" 身份证:"+tempPoint.getIdentificationCard(), e);
		}
		return list;
	}
	
	public TempPoint updateTempPointById(TempPoint tempPoint){
		TempPoint tempPointRes = new TempPoint();
		try {
			boolean flag = tempPointService.updateById(tempPoint);
		} catch (Exception e) {
			logger.error("TempPointActionSupport.updateTempPointById.updateById出错 。id:"+tempPoint.getId()
					+" isActivate："+tempPoint.getIsActivate()+"accountName:"+tempPoint.getAccountName()+" accountNumber:"+tempPoint.getAccountNumber(), e);
		}
		try {
			tempPointRes = tempPointService.getTempPointById(tempPoint.getId());
		} catch (Exception e) {
			logger.error("TempPointActionSupport.updateTempPointById.getTempPointById出错 id:"+tempPoint.getId()+" isActivate:"+tempPoint.getIsActivate(), e);
		}
		return tempPointRes;
	}
	
	public boolean updateTempPointAndFillPoint(String userId,String userName,String mobile,List<TempPoint> pointList){
		boolean flag = false;
		try {
			flag = tempPointService.updateTempPointAndFillPoint(userId,userName,mobile,pointList);	
			
		} catch (Exception e) {
			logger.info("激活积分失败 userId："+userId+" userName："+userName,e);
			logger.error("激活积分失败 userId："+userId+" userName："+userName,e);
		}
		return flag;
	}
	
	public boolean updateByIdOrNumandName(TempPoint tp){
		return tempPointService.updateById(tp);
	}
	
	public TempPointService getTempPointService() {
		return tempPointService;
	}
	public void setTempPointService(TempPointService tempPointService) {
		this.tempPointService = tempPointService;
	}
	
	
}
