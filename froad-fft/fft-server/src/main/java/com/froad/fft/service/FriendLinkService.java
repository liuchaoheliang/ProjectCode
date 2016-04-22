package com.froad.fft.service;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.FriendLink;

public interface FriendLinkService
{

    /**
     * *******************************************************
     * <p> 描述: *-- <b>保存FriendLink并返回数据主键（如果主键为null则插入失败）</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月6日 下午5:13:09 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Long saveFriendLink(FriendLink friendLink);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键更改数据</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月6日 下午5:26:07 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Boolean updateFriendLinkById(FriendLink friendLink);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键获取对象</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月6日 下午5:31:19 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public FriendLink selectFriendLinkById(Long id);

    /**
     * 方法描述：分页查询
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 侯国权
     * @time: 2014年4月15日 上午11:59:11
     */
    public Page findFriendLinkByPage(Page page);


}
