package com.froad.CB.service.user;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.user.MemberCollect;

@WebService
public interface MemberCollectService {
	
	
	/**
	  * 方法描述：添加收藏
	  * @param: collect
	  * @return: id
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 4, 2013 3:13:46 PM
	  */
	public Integer addMemberCollect(MemberCollect collect);
	
	
	
	/**
	  * 方法描述：按编号更新收藏状态
	  * @param: id,state
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 4, 2013 3:26:48 PM
	  */
	public boolean updateStateById(Integer id,String state);
	
	
	/**
	  * 方法描述：删除收藏
	  * @param: id
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 4, 2013 3:14:40 PM
	  */
	public boolean deleteById(Integer id);
	
	
	/**
	  * 方法描述：分页查询收藏
	  * @param: MemberCollect
	  * @return: MemberCollect
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 4, 2013 3:15:35 PM
	  */
	public MemberCollect getMemberCollectByPager(MemberCollect collect);

	
	/**
	  * 方法描述：查询收藏
	  * @param: id
	  * @return: MemberCollect
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 7, 2013 8:41:02 PM
	  */
	public MemberCollect getMemberCollectById(Integer id);
	
	
	/**
	  * 方法描述：查询收藏
	  * @param: userId
	  * @return: List<MemberCollect>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 7, 2013 8:41:02 PM
	  */
	public List<MemberCollect> getMemberCollectByUserId(String userId);
	
	
	/**
	  * 方法描述：查询收藏
	  * @param: merchantId
	  * @return: List<MemberCollect>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 7, 2013 8:41:02 PM
	  */
	public List<MemberCollect> getMemberCollectByMerchantId(String merchantId);
}
