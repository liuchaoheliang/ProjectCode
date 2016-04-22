package com.froad.fft.service;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.MerchantCategory;

public interface MerchantCategoryService {

	public Long saveMerchantCategory(MerchantCategory merchantCategory);
	public Boolean updateMerchantCategoryById(MerchantCategory merchantCategory);
	public MerchantCategory selectMerchantCategoryById(Long id);



    /**
   	  * 方法描述：分页查询
   	  * @param:
   	  * @return:
   	  * @version: 1.0
   	  * @author: 刘超 liuchao@f-road.com.cn
   	  * @time: 2014年3月28日 上午11:59:11
   	  */
   	public Page findMerchantCategoryByPage(Page page);
}
