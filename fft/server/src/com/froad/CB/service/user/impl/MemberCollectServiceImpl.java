package com.froad.CB.service.user.impl;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.user.MemberCollectDao;
import com.froad.CB.po.user.MemberCollect;
import com.froad.CB.service.user.MemberCollectService;
import com.froad.util.DateUtil;

@WebService(endpointInterface="com.froad.CB.service.user.MemberCollectService")
public class MemberCollectServiceImpl implements MemberCollectService{

	private static final Logger logger=Logger.getLogger(MemberCollectServiceImpl.class);
	
	private MemberCollectDao memberCollectDao;
	
	@Override
	public Integer addMemberCollect(MemberCollect collect) {
		if(collect==null){
			logger.error("添加失败，参数为空！");
			return null;
		}
		String time=DateUtil.formatDate2Str(new Date());
		collect.setCreateTime(time);
		collect.setUpdateTime(time);
		return memberCollectDao.addMemberCollect(collect);
	}

	@Override
	public boolean deleteById(Integer id) {
		if(id==null){
			logger.error("编号为空，删除失败！");
			return false;
		}
		return memberCollectDao.deleteById(id);
	}

	@Override
	public MemberCollect getMemberCollectByPager(MemberCollect collect) {
		if(collect==null){
			logger.error("分页查询失败，参数为空！");
			return null;
		}
		return memberCollectDao.getMemberCollectByPager(collect);
	}

	public void setMemberCollectDao(MemberCollectDao memberCollectDao) {
		this.memberCollectDao = memberCollectDao;
	}

	@Override
	public boolean updateStateById(Integer id, String state) {
		if(id==null||state==null||"".equals(state)){
			logger.error("更新失败，参数不完整！");
			return false;
		}
		return memberCollectDao.updateStateById(id, state);
	}

	@Override
	public MemberCollect getMemberCollectById(Integer id) {
		if(id==null){
			logger.error("编号为空，查询失败！");
			return null;
		}
		return memberCollectDao.getMemberCollectById(id);
	}

	@Override
	public List<MemberCollect> getMemberCollectByMerchantId(String merchantId) {
		if(merchantId==null||"".equals(merchantId)){
			logger.error("商户编号为空，查询失败！");
			return null;
		}
		return memberCollectDao.getMemberCollectByMerchantId(merchantId);
	}

	@Override
	public List<MemberCollect> getMemberCollectByUserId(String userId) {
		if(userId==null||"".equals(userId)){
			logger.error("用户编号为空，查询失败！");
			return null;
		}
		return memberCollectDao.getMemberCollectByUserId(userId);
	}

}
