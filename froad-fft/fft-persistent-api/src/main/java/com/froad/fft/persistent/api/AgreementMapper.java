package com.froad.fft.persistent.api;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Agreement;

import java.util.List;

public interface AgreementMapper {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>保存Agreement</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月6日 下午1:30:11 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Long saveAgreement(Agreement agreement);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键更新</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月6日 下午3:15:39 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Boolean updateAgreementById(Agreement agreement);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键获取对象</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月6日 下午3:25:18 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Agreement selectAgreementById(Long id);

    /**
       * /**
       * 方法描述：分页查询
       *
       * @param:
       * @return:
       * @version: 1.0
       * @author: 侯国权
       * @time: 2014年3月28日 上午10:32:48
       */
      public List<Agreement> selectAgreementByPage(Page page);

      /**
       * 方法描述：分页总条数
       *
       * @param:
       * @return:
       * @version: 1.0
       * @author:侯国权
       * @time: 2014年3月28日 上午10:32:50
       */
      public Integer selectAgreementByPageCount(Page page);
}
