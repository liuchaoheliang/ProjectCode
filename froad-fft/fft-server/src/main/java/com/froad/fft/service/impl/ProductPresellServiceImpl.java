/**
 * 文件名称:ProductPresellServiceImpl.java
 * 文件描述: 预售接口实现
 * 产品标识: fft
 * 单元描述: fft-server
 * 编写人: houguoquan_Aides
 * 编写时间: 14-3-27
 * 历史修改:  
 */
package com.froad.fft.service.impl;

import com.froad.fft.persistent.api.ProductPresellMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.PresellTrans;
import com.froad.fft.persistent.entity.ProductPresell;
import com.froad.fft.service.ProductPresellService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
@Service("productPresellServiceImpl")
public class ProductPresellServiceImpl implements ProductPresellService
{
    private static Logger logger = Logger.getLogger(ProductPresellService.class);

    @Resource
    private ProductPresellMapper productPresellMapper;

    public Long saveProductPresell(ProductPresell temp)
    {
        return productPresellMapper.saveProductPresell(temp);
    }

    public Boolean updateProductPresellById(ProductPresell temp)
    {
        if (temp.getId() == null)
        {
            logger.error("更新对象缺少必要Id字段值");
            return false;
        }
        return productPresellMapper.updateProductPresellById(temp);
    }

    public ProductPresell selectProductPresellById(Long id)
    {
        if (id == null)
        {
            logger.error("查询数据id为空");
            return null;
        }
        return productPresellMapper.selectProductPresellById(id);
    }

    public Page findProductPresellByPage(Page page)
    {
        if (page == null)
        {
            page = new Page();
        }
        page.setResultsContent(productPresellMapper.selectProductPresellByPage(page));
        page.setTotalCount(productPresellMapper.selectProductPresellByPageCount(page));
        return page;
    }

    public List<Map> findPresellTransByProductId(Long productId)
    {
        return productPresellMapper.selectPresellTransByProductId(productId);
    }

    public List<PresellTrans> findPresellTransStatisticByProductId(Long productId)
    {
        return productPresellMapper.selectPresellTransStatisticByProductId(productId);
    }

    public List<ProductPresell> findAllUnBindProductPresell()
    {
        return productPresellMapper.selectAllUnBindProductPresell();
    }

    public ProductPresell selectByProductId(Long productId)
    {
        return productPresellMapper.selectByProductId(productId);
    }


}
