package com.froad.fft.persistent.api;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Tag;

import java.util.List;

public interface TagMapper {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>保存Tag</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月13日 上午9:26:45 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Long saveTag(Tag tag);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键更新数据</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月13日 上午9:27:54 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Boolean updateTagById(Tag tag);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键获取对象</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月13日 上午9:28:31 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Tag selectTagById(Long id);
    
    /**
          * 方法描述：分页查询
          *
          * @param:
          * @return:
          * @version: 1.0
          * @author: 侯国权
          * @time: 2014年3月28日 上午10:32:48
          */
         public List<Tag> selectTagByPage(Page page);
    
         /**
          * 方法描述：分页总条数
          *
          * @param:
          * @return:
          * @version: 1.0
          * @author:侯国权
          * @time: 2014年3月28日 上午10:32:50
          */
         public Integer selectTagByPageCount(Page page);
	
}
