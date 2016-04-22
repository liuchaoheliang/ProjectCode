package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.FriendLinkMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.FriendLink;

import java.util.List;

public class FriendLinkMapperImpl implements FriendLinkMapper
{

    @Resource
    private FriendLinkMapper friendLinkMapper;

    @Override
    public Long saveFriendLink(FriendLink friendLink)
    {
        friendLinkMapper.saveFriendLink(friendLink);
        return friendLink.getId();
    }

    @Override
    public Boolean updateFriendLinkById(FriendLink friendLink)
    {

        return friendLinkMapper.updateFriendLinkById(friendLink);
    }

    @Override
    public FriendLink selectFriendLinkById(Long id)
    {
        return friendLinkMapper.selectFriendLinkById(id);
    }

    public List<FriendLink> selectFriendLinkByPage(Page page)
    {
        return friendLinkMapper.selectFriendLinkByPage(page);
    }

    public Integer selectFriendLinkByPageCount(Page page)
    {
        return friendLinkMapper.selectFriendLinkByPageCount(page);
    }

}
