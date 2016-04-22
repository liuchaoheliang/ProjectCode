/**
 * 文件名：ProductGroupService.java
 * 版本信息：Version 1.0
 * 日期：2014年2月21日
 * author: 刘超 liuchao@f-road.com.cn
 */
package com.froad.fft.service;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.ProductGroup;

/**
 * 类描述：团购商品接口
 *
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年2月21日 上午9:17:37
 */
public interface ProductGroupService
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
    public boolean updateProductGroupById(ProductGroup productGroup);

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
    public Page findProductGroupByPage(Page page);


}
