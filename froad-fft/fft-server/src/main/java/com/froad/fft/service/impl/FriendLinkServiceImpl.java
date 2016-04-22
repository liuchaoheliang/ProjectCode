package com.froad.fft.service.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.bean.page.Page;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.FriendLinkMapper;
import com.froad.fft.persistent.entity.FriendLink;
import com.froad.fft.service.FriendLinkService;

@Service("friendLinkServiceImpl")
public class FriendLinkServiceImpl implements FriendLinkService {

	private static Logger logger = Logger.getLogger(FriendLinkServiceImpl.class);
	
	@Resource
	private FriendLinkMapper friendLinkMapper;
	
	@Override
	public Long saveFriendLink(FriendLink friendLink) {
		return friendLinkMapper.saveFriendLink(friendLink);
	}

	@Override
	public Boolean updateFriendLinkById(FriendLink friendLink) {
		if(friendLink.getId() == null){
			logger.error("更新对象缺少必要Id字段值");
			return false;
		}
		return friendLinkMapper.updateFriendLinkById(friendLink);
	}

	@Override
	public FriendLink selectFriendLinkById(Long id) {
		if(id == null){
			logger.error("查询数据id为空");
			return null;
		}
		return friendLinkMapper.selectFriendLinkById(id);
	}
	
    public Page findFriendLinkByPage(Page page)
     {
         if (page == null)
         {
             page = new Page();
         }
         page.setResultsContent(friendLinkMapper.selectFriendLinkByPage(page));
         page.setTotalCount(friendLinkMapper.selectFriendLinkByPageCount(page));
         return page;
     }
}
