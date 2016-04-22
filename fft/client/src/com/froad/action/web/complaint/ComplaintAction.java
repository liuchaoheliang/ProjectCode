package com.froad.action.web.complaint;

import java.util.ArrayList;
import java.util.List;

import com.froad.action.support.ComplaintActionSupport;
import com.froad.action.support.ComplaintCategoryActionSupport;
import com.froad.action.support.ComplaintReplyActionSupport;
import com.froad.action.support.MerchantActionSupport;
import com.froad.action.support.UserActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.complaint.Complaint;
import com.froad.client.complaintCategory.ComplaintCategory;
import com.froad.client.complaintReply.ComplaintReply;
import com.froad.client.user.User;
import com.froad.common.SessionKey;
import com.froad.util.Assert;
import com.froad.util.command.MallCommand;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-3-12  
 * @version 1.0
 * 投诉 action
 */
public class ComplaintAction extends BaseActionSupport {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -4223104129172580688L;
	private UserActionSupport userActionSupport;
	private MerchantActionSupport merchantActionSupport;
	private ComplaintCategoryActionSupport complaintCategoryActionSupport;
	private ComplaintActionSupport complaintActionSupport;
	private ComplaintReplyActionSupport complaintReplyActionSupport;
	private Complaint complaint;
	private ComplaintReply complaintReply;
	private Complaint pager;
	private ComplaintCategory complaintCategory;
	private List<ComplaintCategory> complaintCategoryList = new ArrayList<ComplaintCategory>();
	private User user;
	private String isAdmin;
	
	/**
	 * 进入交易投诉页面
	 * @return
	 */
	public String toComplaint(){
		//需要订单号到页面
		if(complaint == null){
			complaint = new Complaint();
		}
		String userId = (String)getSession(MallCommand.USER_ID);
		if(Assert.empty(userId) || Assert.empty(complaint.getOrderId()) || Assert.empty(complaint.getToUserId())){
			return "failse";
		}
		complaintCategoryList = complaintCategoryActionSupport.getComplaintCategoryList();
		
		if(complaintCategoryList == null){
			complaintCategoryList = new ArrayList<ComplaintCategory>();
		}
		return "success";
	}
	
	/**
	 * 会员对交易进行投诉
	 * @return
	 */
	public String addComplaint(){
		if(complaint == null){
			return "failse";
		}
		//天剑投诉信息记录
		if(complaint == null || Assert.empty(complaint.getContent()) || Assert.empty(complaint.getOrderId()) || Assert.empty(complaint.getToUserId())){
			complaint = new Complaint();
			return "failse";
		}
		if(Assert.empty(complaint.getFromUserId())){
			String userid = (String)getSession(MallCommand.USER_ID);
			complaint.setFromUserId(userid);
		}
		if(complaintCategory != null && !Assert.empty(complaintCategory.getId())){
			complaint.setComplaintCategoryId(String.valueOf(complaintCategory.getId()));
		}
		complaint.setState("30");
		complaint.setIsReply("10");//默认尚未回复
		complaintActionSupport.addComplaint(complaint);
		return getComplaintList();
	}
	
	/**
	 * 查询会员投诉信息
	 * @return
	 */
	public String getComplaintInfo(){
		if(complaint == null || Assert.empty(complaint.getId())){
			return getComplaintList();
		}
		//获取投诉信息
		if(Assert.empty(complaint.getFromUserId())){
			String userId = (String)getSession(MallCommand.USER_ID);
			complaint.setFromUserId(userId);
		}else{
			user = userActionSupport.queryUserAllByUserID(complaint.getFromUserId());
		}

		complaint = complaintActionSupport.getComplaintInfo(complaint);
		
		complaintCategoryList = complaintCategoryActionSupport.getComplaintCategoryList();
		
		if(complaintCategoryList == null){
			complaintCategoryList = new ArrayList<ComplaintCategory>();
		}
		
		if(complaint == null){
			complaint = new Complaint();
		}
		return "success";
	}
	
	/**
	 * 用户投诉列表查询
	 * @return
	 */
	public String getComplaintList(){
		if(pager == null){
			pager = new Complaint();
		}
		//分页查询投诉列表
		if(user == null){
			user = new User();
		}
		String userId = (String)getSession(MallCommand.USER_ID);
		if(!Assert.empty(userId)){
			pager.setFromUserId(userId);
			user.setUserID(userId);
		}else{
			log.info("尚未登录系统,跳到首页面 userID:"+userId);
			return "login_index";
		}
		pager.setPageSize(10);
		pager = complaintActionSupport.getComplaintListByPager(pager);
		return "successComplaintList";
	}
	
	/**
	 * 商家投诉列表查询
	 * @return String
	 */
	public String getMerchantComplaintList(){
		if("1".equals(getSession(SessionKey.MERCHANT_ROLE))){
			return "nopower";
		}
		if(pager == null){
			pager = new Complaint();
		}
		//分页查询投诉列表
		if(user == null){
			user = new User();
		}
		String userId = (String)getSession(MallCommand.USER_ID);
		if(!Assert.empty(userId)){
			pager.setToUserId(userId);
			user.setUserID(userId);
		}
		pager.setPageSize(10);
		pager = complaintActionSupport.getComplaintListByPager(pager);
		return "successMerchantComplaintList";
	}
	
	/**
	 * 会员查看投诉回复
	 * @return
	 */
	public String getComplaintReplyInfo(){
		//获取投诉回复
		if(complaint == null || Assert.empty(complaint.getId())){
			return getComplaintList();
		}
		if(Assert.empty(complaint.getFromUserId())){
			String userId = (String)getSession(MallCommand.USER_ID);
			complaint.setFromUserId(userId);
		}else{
			user = userActionSupport.queryUserAllByUserID(complaint.getFromUserId());
		}

		//complaint.setPageSize(5);
		pager = complaintActionSupport.getComplaintInfo(complaint);//查找
		if(pager == null){
			pager = new Complaint();
		}
		return "success";
	}
	
	public String toMerchantComplaintReply(){
		isAdmin = (String)getSession(MallCommand.LOGIN_MERCHANT_OR_CLERK);
		if(complaint == null || Assert.empty(complaint.getId())){
			return getMerchantComplaintList();
		}
		//获取投诉信息
		if(Assert.empty(complaint.getToUserId())){
			String userId = (String)getSession(MallCommand.USER_ID);
			complaint.setToUserId(userId);
		}else{
			user = userActionSupport.queryUserAllByUserID(complaint.getToUserId());
		}

		complaint = complaintActionSupport.getComplaintInfo(complaint);
		if(complaint == null){
			complaint = new Complaint();
		}
		return "success";
	}	
	
	/**
	 * 商户对交易的投诉进行回复
	 * @return
	 */
	public String  merchantComplaintReply(){
		//商户对都是进行回复
		if(complaintReply == null){
			return toMerchantComplaintReply();
		}
		if(Assert.empty(complaintReply.getContent()) || Assert.empty(complaint.getId())){
			complaintReply = new ComplaintReply();
			return toMerchantComplaintReply();
		}
		if(Assert.empty(complaint.getToUserId())){
			String userid = (String)getSession(MallCommand.USER_ID);
			complaint.setToUserId(userid);
		}
		complaint.setIsReply("20");//投诉记录改为已回复状态
		complaintReply.setState("30");
		complaintReply.setAnswererUserId(complaint.getToUserId());
		complaintReply.setComplaintId(String.valueOf(complaint.getId()));
		ComplaintReply reply = complaintReplyActionSupport.addComplaintReply(complaintReply);//增加投诉回复记录
		if(reply != null && !Assert.empty(reply.getId()) && reply.getId() > 0){
			complaintActionSupport.updateComplaint(complaint);//修改投诉记录为已经回复
		}
		return getMerchantComplaintList();
	}
	
	public UserActionSupport getUserActionSupport() {
		return userActionSupport;
	}
	public void setUserActionSupport(UserActionSupport userActionSupport) {
		this.userActionSupport = userActionSupport;
	}
	public MerchantActionSupport getMerchantActionSupport() {
		return merchantActionSupport;
	}
	public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport) {
		this.merchantActionSupport = merchantActionSupport;
	}
	public ComplaintCategoryActionSupport getComplaintCategoryActionSupport() {
		return complaintCategoryActionSupport;
	}
	public void setComplaintCategoryActionSupport(
			ComplaintCategoryActionSupport complaintCategoryActionSupport) {
		this.complaintCategoryActionSupport = complaintCategoryActionSupport;
	}
	public ComplaintActionSupport getComplaintActionSupport() {
		return complaintActionSupport;
	}
	public void setComplaintActionSupport(
			ComplaintActionSupport complaintActionSupport) {
		this.complaintActionSupport = complaintActionSupport;
	}
	public Complaint getComplaint() {
		return complaint;
	}
	public void setComplaint(Complaint complaint) {
		this.complaint = complaint;
	}
	public Complaint getPager() {
		return pager;
	}
	public void setPager(Complaint pager) {
		this.pager = pager;
	}
	public ComplaintCategory getComplaintCategory() {
		return complaintCategory;
	}
	public void setComplaintCategory(ComplaintCategory complaintCategory) {
		this.complaintCategory = complaintCategory;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ComplaintReplyActionSupport getComplaintReplyActionSupport() {
		return complaintReplyActionSupport;
	}

	public void setComplaintReplyActionSupport(
			ComplaintReplyActionSupport complaintReplyActionSupport) {
		this.complaintReplyActionSupport = complaintReplyActionSupport;
	}

	public void setComplaintReply(ComplaintReply complaintReply) {
		this.complaintReply = complaintReply;
	}
	public ComplaintReply getComplaintReply() {
		return complaintReply;
	}

	public List<ComplaintCategory> getComplaintCategoryList() {
		return complaintCategoryList;
	}

	public void setComplaintCategoryList(
			List<ComplaintCategory> complaintCategoryList) {
		this.complaintCategoryList = complaintCategoryList;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}
	
}
