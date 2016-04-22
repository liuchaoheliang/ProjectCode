/**
 * 文件名：ProductGroupServiceImpl.java
 * 版本信息：Version 1.0
 * 日期：2014年2月21日
 * author: 刘超 liuchao@f-road.com.cn
 */
package com.froad.fft.service.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.bean.page.Page;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.ProductGroupMapper;
import com.froad.fft.persistent.entity.ProductGroup;
import com.froad.fft.service.ProductGroupService;

/**
 * 类描述：
 *
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年2月21日 上午9:19:56
 */

@Service("productGroupServiceImpl")
public class ProductGroupServiceImpl implements ProductGroupService
{

    private static Logger logger = Logger.getLogger(ProductServiceImpl.class);

    @Resource
    private ProductGroupMapper productGroupMapper;

    @Override
    public Long saveProductGroup(ProductGroup productGroup)
    {
        return productGroupMapper.saveProductGroup(productGroup);
    }

    @Override
    public boolean updateProductGroupById(ProductGroup productGroup)
    {
        if (productGroup.getId() == null)
        {
            logger.error("更新对象缺少必要Id字段值");
            return false;
        }
        return productGroupMapper.updateProductGroupById(productGroup);
    }

    @Override
    public ProductGroup selectProductGroupById(Long id)
    {
        if (id == null)
        {
            logger.error("查询数据id为空");
        }
        return productGroupMapper.selectProductGroupById(id);
    }

    public Page findProductGroupByPage(Page page)
    {
        if (page == null)
        {
            page = new Page();
        }
        page.setResultsContent(productGroupMapper.selectProductGroupByPage(page));
        page.setTotalCount(productGroupMapper.selectProductGroupByPageCount(page));
        return page;
    }

}
