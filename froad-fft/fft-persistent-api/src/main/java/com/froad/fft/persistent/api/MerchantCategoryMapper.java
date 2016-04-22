package com.froad.fft.persistent.api;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.MerchantCategory;

import java.util.List;

public interface MerchantCategoryMapper
{

    //基本操作|开始
    public Long saveMerchantCategory(MerchantCategory merchantCategory);

    public Boolean updateMerchantCategoryById(MerchantCategory merchantCategory);

    public MerchantCategory selectMerchantCategoryById(Long id);
    //基本操作|结束

    /**
     * 方法描述：分页查询
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 侯国权
     * @time: 2014年3月28日 上午10:32:48
     */
    public List<MerchantCategory> selectMerchantCategoryByPage(Page page);

    /**
     * 方法描述：分页总条数
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author:侯国权
     * @time: 2014年3月28日 上午10:32:50
     */
    public Integer selectMerchantCategoryByPageCount(Page page);

}
