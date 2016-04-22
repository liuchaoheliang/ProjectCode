package com.froad.fft.persistent.api;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.FriendLink;

import java.util.List;

public interface FriendLinkMapper {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>保存FriendLink</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月6日 下午5:11:26 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Long saveFriendLink(FriendLink friendLink);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键更新</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月6日 下午5:25:28 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Boolean updateFriendLinkById(FriendLink friendLink);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键获取对象</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月6日 下午5:30:49 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public FriendLink selectFriendLinkById(Long id);
    
    /**
        * 方法描述：分页查询
        *
        * @param:
        * @return:
        * @version: 1.0
        * @author: 侯国权
        * @time: 2014年4月15日 上午10:32:48
        */
       public List<FriendLink> selectFriendLinkByPage(Page page);
  
       /**
        * 方法描述：分页总条数
        *
        * @param:
        * @return:
        * @version: 1.0
        * @author:侯国权
        * @time: 2014年4月15日 上午10:32:50
        */
       public Integer selectFriendLinkByPageCount(Page page);
}
