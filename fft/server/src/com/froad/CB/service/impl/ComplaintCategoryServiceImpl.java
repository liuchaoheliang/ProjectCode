package com.froad.CB.service.impl;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.common.Command;
import com.froad.CB.dao.complaint.ComplaintCategoryDao;
import com.froad.CB.po.complaint.ComplaintCategory;
import com.froad.CB.service.ComplaintCategoryService;
import com.froad.util.DateUtil;
import com.froad.util.cache.CacheKey;
import com.froad.util.cache.EhCacheUtil;

@WebService(endpointInterface="com.froad.CB.service.ComplaintCategoryService")
public class ComplaintCategoryServiceImpl implements ComplaintCategoryService{

	private static final Logger logger=Logger.getLogger(ComplaintCategoryServiceImpl.class);
	
	private ComplaintCategoryDao complaintCategoryDao;
	
	@Override
	public Integer addComplaintCategory(ComplaintCategory category) {
		if(category==null){
			logger.error("添加失败，参数为空！");
			return null;
		}
		Integer returnId=complaintCategoryDao.addComplaintCategory(category);
		
		ComplaintCategory cc=new ComplaintCategory();
		cc.setId(returnId);
		
		if(category.getParentId()!=null && !"".equals(category.getParentId())){
			ComplaintCategory complaintCategory_parent=complaintCategoryDao.getComplaintCategoryById(Integer.parseInt(category.getParentId()));
			String parentPath = complaintCategory_parent.getPath();
			cc.setPath(parentPath + ComplaintCategory.PATH_SEPARATOR + returnId);
		}
		else{
			cc.setPath(returnId+"");
		}
		complaintCategoryDao.updateComplaintCategoryById(cc);
		return returnId;
	}

	@Override
	public boolean deleteComplaintCategoryById(Integer id) {
		if(id==null){
			logger.error("删除失败，编号为空！");
			return false;
		}
		return complaintCategoryDao.deleteComplaintCategoryById(id);
	}

	@Override
	public ComplaintCategory getComplaintCategoryById(Integer id) {
		if(id==null){
			logger.error("查询失败，编号为空！");
			return null;
		}
		return complaintCategoryDao.getComplaintCategoryById(id);
	}

	@Override
	public List<ComplaintCategory> getComplaintCategoryList() {
		
		EhCacheUtil ehCacheUtil=EhCacheUtil.getInstance(); 
		List<ComplaintCategory> list=(List<ComplaintCategory>) ehCacheUtil.get(EhCacheUtil.CACHE_NAME,CacheKey.COMPLAINTCATEGORY_CACHE_KEY);
		if(list!=null){
			logger.info("缓存 key=complaintCategory存在数据！");
			return list;
		}
		
		logger.info("缓存 key=complaintCategory 不存在数据,查找数据库！");
		list=complaintCategoryDao.getComplaintCategoryList();
		ehCacheUtil.put(EhCacheUtil.CACHE_NAME,CacheKey.COMPLAINTCATEGORY_CACHE_KEY, list);
		
		return list;
	}

	@Override
	public boolean updateComplaintCategoryById(ComplaintCategory category) {
		if(category==null||category.getId()==null){
			logger.error("更新失败，参数为空！");
			return false;
		}
		category.setUpdateTime(DateUtil.formatDate2Str(new Date()));
		return complaintCategoryDao.updateComplaintCategoryById(category);
	}

	public void setComplaintCategoryDao(ComplaintCategoryDao complaintCategoryDao) {
		this.complaintCategoryDao = complaintCategoryDao;
	}

	@Override
	public boolean stopComplaintCategory(Integer id) {
		if(id==null){
			logger.error("停用操作失败，编号为空！");
			return false;
		}
		return this.updateStateById(id,Command.STATE_DELETE);
	}

	@Override
	public boolean updateStateById(Integer id, String state) {
		if(id==null||state==null||"".equals(state)){
			logger.error("更新失败，参数不完整！");
			return false;
		}
		return complaintCategoryDao.updateStateById(id, state);
	}

}
