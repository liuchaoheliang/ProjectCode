package com.froad.action.support;

import org.apache.log4j.Logger;

import com.froad.client.memberCollect.MemberCollect;
import com.froad.client.memberCollect.MemberCollectService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-20  
 * @version 1.0
 * 收藏 client service  ActionSupport
 */
public class MemberCollectActionSupport {
	private static Logger logger = Logger.getLogger(MemberCollectActionSupport.class);
	private MemberCollectService memberCollectService;
	
	/**
	 * 增加收藏
	 * @param memberCollect
	 * @return ture 成功        flase 失败
	 */
	public boolean addMemberCollect(MemberCollect memberCollect){
		boolean isSuccess=true;
		try{
			Integer id=memberCollectService.addMemberCollect(memberCollect);
			if(id==null){
				isSuccess=false;
			}
		}
		catch(Exception e){
			isSuccess=false;
			logger.error("增加收藏异常", e);
		}
		return isSuccess;
	}
	
	/**
	 * 删除收藏
	 * @param id
	 * @return ture 成功        flase 失败
	 */
	public boolean deleteMemberCollect(Integer id){
		boolean isSuccess=true;
		try{
			isSuccess=memberCollectService.deleteById(id);
		}
		catch(Exception e){
			isSuccess=false;
			logger.error("删除收藏异常", e);
		}
		return isSuccess;
	}
	
	/**
	 * 分页查找 收藏
	 * @param memberCollect
	 * @return
	 */
	public MemberCollect getMemberCollectByPager(MemberCollect memberCollect){
		try{
			return memberCollectService.getMemberCollectByPager(memberCollect);
		}
		catch(Exception e){
			logger.error("分页查找收藏异常", e);
		}
		return memberCollect;
	}
	
	public MemberCollectService getMemberCollectService() {
		return memberCollectService;
	}
	public void setMemberCollectService(MemberCollectService memberCollectService) {
		this.memberCollectService = memberCollectService;
	}
	
}
