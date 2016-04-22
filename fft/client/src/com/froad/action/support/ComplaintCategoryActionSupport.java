package com.froad.action.support;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.froad.client.complaintCategory.ComplaintCategory;
import com.froad.client.complaintCategory.ComplaintCategoryService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-20  
 * @version 1.0
 * 投诉分类 client service  ActionSupport
 */
public class ComplaintCategoryActionSupport {
	private static Logger logger = Logger.getLogger(ComplaintCategoryActionSupport.class);
	private ComplaintCategoryService complaintCategoryService;
	
	public List<ComplaintCategory> getComplaintCategoryList(){
		List<ComplaintCategory> list = new ArrayList<ComplaintCategory>();
		try {
			list = complaintCategoryService.getComplaintCategoryList();
		} catch (Exception e) {
			logger.error("ComplaintCategoryActionSupport.getComplaintCategoryList查找投诉分类异常",e);
		}
		return list;
	}
	
	public ComplaintCategoryService getComplaintCategoryService() {
		return complaintCategoryService;
	}
	public void setComplaintCategoryService(
			ComplaintCategoryService complaintCategoryService) {
		this.complaintCategoryService = complaintCategoryService;
	}
	

}
