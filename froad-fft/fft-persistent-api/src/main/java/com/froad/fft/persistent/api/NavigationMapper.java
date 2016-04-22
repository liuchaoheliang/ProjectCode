package com.froad.fft.persistent.api;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Navigation;

import java.util.List;

public interface NavigationMapper {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>保存Navigation</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 下午2:35:52 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Long saveNavigation(Navigation navigation);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键更新数据</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 下午3:26:28 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Boolean updateNavigationById(Navigation navigation);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键查询数据</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 下午3:30:45 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Navigation selectNavigationById(Long id);

    /**
      * 分页查询
      *
      * @param page
      * @return
      */
     public List<Navigation> selectNavigationByPage(Page page);

     /**
      * 方法描述：分页总条数
      *
      * @param:
      * @return:
      * @version: 1.0
      * @author:侯国权
      * @time: 2014年4月17日 上午10:32:50
      */
     public Integer selectNavigationByPageCount(Page page);
}
