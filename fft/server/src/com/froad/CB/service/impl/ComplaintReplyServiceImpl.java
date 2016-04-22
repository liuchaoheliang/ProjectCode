package com.froad.CB.service.impl;

import java.util.Date;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.ComplaintReplyDao;
import com.froad.CB.po.ComplaintReply;
import com.froad.CB.service.ComplaintReplyService;
import com.froad.util.DateUtil;

@WebService(endpointInterface="com.froad.CB.service.ComplaintReplyService")
public class ComplaintReplyServiceImpl implements ComplaintReplyService{
	
	private static final Logger logger=Logger.getLogger(ComplaintReplyServiceImpl.class);
	
	private ComplaintReplyDao complaintReplyDao;

	@Override
	public ComplaintReply addComplaintReply(ComplaintReply reply) {
		if(reply==null){
			logger.error("参数为空，添加失败");
			return null;
		}
		String time=DateUtil.formatDate2Str(new Date());
		reply.setCreateTime(time);
		reply.setUpdateTime(time);
		Integer id=complaintReplyDao.addComplaintReply(reply);
		reply.setId(id);
		return reply;
	}

	@Override
	public boolean updateStateById(Integer id, String state) {
		if(id==null||state==null||"".equals(state)){
			logger.error("参数为空，状态更新失败");
			return false;
		}
		try {
			complaintReplyDao.updateStateById(id, state);
			return true;
		} catch (Exception e) {
			logger.error("状态修改时出现异常",e);
			return false;
		}
	}

	public void setComplaintReplyDao(ComplaintReplyDao complaintReplyDao) {
		this.complaintReplyDao = complaintReplyDao;
	}

}
