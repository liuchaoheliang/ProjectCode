package com.froad.action.support;

import org.apache.log4j.Logger;

import com.froad.client.complaintReply.ComplaintReply;
import com.froad.client.complaintReply.ComplaintReplyService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-3-15  
 * @version 1.0
 */
public class ComplaintReplyActionSupport {
	private static Logger logger = Logger.getLogger(ComplaintActionSupport.class);
	private ComplaintReplyService complaintReplyService;
	
	/**
	 * 增加投诉回复
	 * @param mp
	 * @return
	 */
	public ComplaintReply addComplaintReply(ComplaintReply mp){		
		logger.info("增加投诉回复信息");
		//ComplaintReply mpRes = new ComplaintReply();
		try {
			mp = complaintReplyService.addComplaintReply(mp);
			if(mp == null){
				mp = new ComplaintReply();
			}
		} catch (Exception e) {
			logger.error("ComplaintActionSupport.addComplaintReply增加投诉回复异常 answerUserID："+mp.getAnswererUserId()+" complaintId:"+mp.getComplaintId(), e);
			mp = new ComplaintReply();
		}
		return mp;
	}

	public ComplaintReplyService getComplaintReplyService() {
		return complaintReplyService;
	}

	public void setComplaintReplyService(ComplaintReplyService complaintReplyService) {
		this.complaintReplyService = complaintReplyService;
	}
	
	
}
