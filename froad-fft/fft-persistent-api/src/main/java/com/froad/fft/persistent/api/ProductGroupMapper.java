package com.froad.fft.persistent.api;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.ProductGroup;

import java.util.List;

public interface ProductGroupMapper
{

    /**
     * 方法描述：保存
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: liuchao liuchao@f-road.com.cn
     * @time: 2014年2月20日 下午5:49:16
     */
    public Long saveProductGroup(ProductGroup productGroup);

    /**
     * 方法描述：根据对象的ID更新对象属性
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: liuchao liuchao@f-road.com.cn
     * @time: 2014年2月20日 下午5:49:46
     */
    public Boolean updateProductGroupById(ProductGroup productGroup);

    /**
     * 方法描述：根据ID查询对象
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: liuchao liuchao@f-road.com.cn
     * @time: 2014年2月20日 下午5:50:57
     */
    public ProductGroup selectProductGroupById(Long id);

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    public List<ProductGroup> selectProductGroupByPage(Page page);

    /**
     * 分页统计
     *
     * @param page
     * @return
     */
    public Integer selectProductGroupByPageCount(Page page);
}
