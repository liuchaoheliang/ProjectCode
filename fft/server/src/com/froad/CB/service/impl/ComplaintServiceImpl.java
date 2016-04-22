package com.froad.CB.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.common.Command;
import com.froad.CB.dao.complaint.ComplaintDao;
import com.froad.CB.dao.user.UserDao;
import com.froad.CB.po.complaint.Complaint;
import com.froad.CB.po.user.User;
import com.froad.CB.service.ComplaintService;
import com.froad.util.DateUtil;

@WebService(endpointInterface="com.froad.CB.service.ComplaintService")
public class ComplaintServiceImpl implements ComplaintService{

	private static final Logger logger=Logger.getLogger(ComplaintServiceImpl.class);
	
	private ComplaintDao complaintDao;
	
	private UserDao userDao;
	
	@Override
	public Integer addComplaint(Complaint complaint) {
		if(complaint==null){
			logger.error("投诉信息为空，添加投诉信息失败！");
			return null;
		}
		String time=DateUtil.formatDate2Str(new Date());
		complaint.setCreateTime(time);
		complaint.setUpdateTime(time);
		return complaintDao.addComplaint(complaint);
	}

	@Override
	public boolean deleteComplaintById(Integer id) {
		if(id==null){
			logger.error("编号为空，删除投诉信息失败！");
			return false;
		}
		return complaintDao.deleteComplaintById(id);
	}

	@Override
	public Complaint getComplaintById(Integer id) {
		if(id==null){
			logger.error("编号为空，查询投诉信息失败！");
			return null;
		}
		Complaint complaint=complaintDao.getComplaintById(id);
		if(complaint!=null){
			complaint.setFromUser(userDao.queryUserAllByUserID(complaint.getFromUserId()));
			complaint.setToUser(userDao.queryUserAllByUserID(complaint.getToUserId()));
		}
		return getComplaintByPager(complaint);
	}

	@Override
	public Complaint getComplaintByPager(Complaint complaint) {
		if(complaint==null){
			logger.error("参数为空，分页查询失败！");
			return null;
		}
		if(complaint.getState()==null||"".equals(complaint.getState().trim())){
			complaint.setState(Command.STATE_START);
		}
		Complaint result=complaintDao.getComplaintByPager(complaint);
		List<Complaint> list=result.getList();
		List<String> fromUserIds=new ArrayList<String>();
		List<String> toUserIds=new ArrayList<String>();
		String uid=null;
		for (int i = 0; i < list.size(); i++) {
			uid=list.get(i).getFromUserId();
			if(uid!=null){
				fromUserIds.add(uid);
			}
			uid=list.get(i).getToUserId();
			if(uid!=null){
				toUserIds.add(uid);
			}
		}
		List<User> fromUsers=null;
		if(fromUserIds.size()>0){
			fromUsers=userDao.getUsersByIdList(fromUserIds);
		}
		
		List<User> toUsers=null;
		if(toUserIds.size()>0){
			toUsers=userDao.getUsersByIdList(toUserIds);
		}
		String userId=null;
		Complaint comp=null;
		for (int i = 0; i < list.size(); i++) {
			comp=list.get(i);
			userId=comp.getFromUserId();
			if(userId!=null&&!"".equals(userId)){
				for (int j = 0; j <fromUsers.size(); j++) {
					if(userId.equals(fromUsers.get(j).getUserID())){
						list.get(i).setFromUser(fromUsers.get(j));
						break;
					}
				}
			}
			
			userId=comp.getToUserId();
			if(userId!=null&&!"".equals(userId)){
				for (int j = 0; j <toUsers.size(); j++) {
					if(userId.equals(toUsers.get(j).getUserID())){
						list.get(i).setToUser(toUsers.get(j));
						break;
					}
				}
			}
		}
		return result;
	}

	@Override
	public boolean updateComplaintById(Complaint complaint) {
		if(complaint==null||complaint.getId()==null){
			logger.error("参数不完整，投诉信息更新失败！");
			return false;
		}
		complaint.setUpdateTime(DateUtil.formatDate2Str(new Date()));
		return complaintDao.updateComplaintById(complaint);
	}

	public void setComplaintDao(ComplaintDao complaintDao) {
		this.complaintDao = complaintDao;
	}

	@Override
	public boolean updateComplaintStateById(Integer id, String state) {
		if(id==null||state==null||"".equals(state)){
			logger.error("参数不完整，状态更新失败！");
			return false;
		}
		return complaintDao.updateComplaintStateById(id, state);
	}

	@Override
	public boolean stopComplaintById(Integer id) {
		if(id==null){
			logger.error("投诉编号为空，停用操作失败！");
			return false;
		}
		return complaintDao.stopComplaintById(id);
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
