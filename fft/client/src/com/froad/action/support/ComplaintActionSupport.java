package com.froad.action.support;

import org.apache.log4j.Logger;
import com.froad.client.complaint.Complaint;
import com.froad.client.complaintReply.ComplaintReply;
import com.froad.client.complaint.ComplaintService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-20  
 * @version 1.0
 * 投诉 client service  ActionSupport
 */
public class ComplaintActionSupport {
	private static Logger logger = Logger.getLogger(ComplaintActionSupport.class);
	private ComplaintService complaintService;
	
	/**
	 * 查询投诉信息
	 * @param Complaint
	 * @return Complaint
	 */
	public Complaint getComplaintInfo(Complaint comp){
		Complaint complaint= null;
		try{
			complaint = complaintService.getComplaintById(comp.getId());
			if(complaint == null){
				complaint = comp;
			}
		}catch(Exception e){
			logger.error("ComplaintActionSupport.getComplaintInfo查询投诉异常,ID："+comp.getId());
		}
		return complaint;
	}
	
	/**
	 * 增加投诉
	 * @param mp
	 * @return
	 */
	public Integer addComplaint(Complaint mp){		
		logger.info("增加投诉信息");
		Integer num = null;
		try {
			num = complaintService.addComplaint(mp);
			if(num == null){
				num = new Integer(0);
			}
		} catch (Exception e) {
			logger.error("ComplaintActionSupport.addComplaint增加投诉异常 userID："+mp.getFromUserId()+" orderId:"+mp.getOrderId(), e);
			num = new Integer(0);
		}
		return num;
	}
	
	/**
	 * 修改投诉记录状态
	 * @param mp
	 * @return
	 */
	public boolean updateComplaint(Complaint mp){		
		logger.info("增加投诉信息");
		boolean flag = false;
		try {
			flag = complaintService.updateComplaintById(mp);
		} catch (Exception e) {
			logger.error("ComplaintActionSupport.updateComplaint修改投诉异常 userID："+mp.getToUserId()+" id:"+mp.getId()+" isReply:"+mp.getIsReply(), e);
		}
		return flag;
	}
	
	/**
	 * 分页查找 投诉
	 * @param pager
	 * @return
	 */
	public Complaint getComplaintListByPager(Complaint complaint){
		try{
			complaint=complaintService.getComplaintByPager(complaint);
			if(complaint==null){
				complaint = new Complaint();
			}
		}
		catch(Exception e){
			logger.error("ComplaintActionSupport.getComplaintListByPager分页查找投诉异常,用户ID："+complaint.getFromUserId());
		}
		return complaint;
	}
	
	public ComplaintService getComplaintService() {
		return complaintService;
	}
	public void setComplaintService(ComplaintService complaintService) {
		this.complaintService = complaintService;
	}
	
}
