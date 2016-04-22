/**
 * 文件名称:ProductPresellService.java
 * 文件描述: 预售商品Service
 * 产品标识: fft
 * 单元描述: fft-server
 * 编写人: houguoquan_Aides
 * 编写时间: 14-3-27
 * 历史修改:  
 */
package com.froad.fft.service;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.PresellTrans;
import com.froad.fft.persistent.entity.ProductPresell;

import java.util.List;
import java.util.Map;

/**
 * 预售商品实体
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public interface ProductPresellService
{
    /**
     * *******************************************************
     * <p> 描述: *-- <b>保存ProductPresell</b> --* </p>
     * <p> 作者: 侯国权 </p>
     * <p> 时间: 2014年1月3日 下午4:29:27 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Long saveProductPresell(ProductPresell temp);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键更新</b> --* </p>
     * <p> 作者: 侯国权 </p>
     * <p> 时间: 2014年1月6日 上午9:59:25 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Boolean updateProductPresellById(ProductPresell temp);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键获取对象</b> --* </p>
     * <p> 作者: 侯国权 </p>
     * <p> 时间: 2014年1月6日 上午10:18:23 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public ProductPresell selectProductPresellById(Long id);

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    public Page findProductPresellByPage(Page page);

    /**
     * 根据商品Id查询预售交易列表
     *
     * @param productId 商品Id 而非预售Id
     * @return
     */
    public List<Map>  findPresellTransByProductId(Long productId);

    /**
     * 根据商品Id查询预售交易列表统计
     *
     * @param productId 商品Id 而非预售Id
     * @return
     */
    public List<PresellTrans>  findPresellTransStatisticByProductId(Long productId);

    /**
     * 查询所有未绑定预售商品
     * @return
     */
    public List<ProductPresell> findAllUnBindProductPresell();

    /**
     * 根据商品Id获取预售信息
     * @param productId 商品Id 而非预售Id
     * @return 预售实体
     */
    public ProductPresell selectByProductId(Long productId);

}
