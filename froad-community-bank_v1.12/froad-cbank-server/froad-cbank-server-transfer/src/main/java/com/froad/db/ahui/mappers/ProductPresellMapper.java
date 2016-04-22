package com.froad.db.ahui.mappers;
/**
 * 文件名称:ProductPresellMapper.java
 * 文件描述: 商品预售mappler接口定义
 * 产品标识: fft
 * 单元描述: fft-president-api
 * 编写人: gq.hou_Mimosa
 * 编写时间: 14-3-24
 * 历史修改: 2014-3-24 by gq.hou_Mimosa
 */

import com.froad.db.ahui.entity.ProductPresell;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.PresellTrans;

import java.util.List;
import java.util.Map;

public interface ProductPresellMapper
{

    /**
     * *******************************************************
     * <p> 描述: *-- <b>保存GivePointRule</b> --* </p>
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
      * 方法描述：查询预售属性
      * @param: productId 商品编号
      * @return: ProductPresell
      * @version: 1.0
      * @author: 李金魁 lijinkui@f-road.com.cn
      * @time: 2014年4月14日 下午2:40:53
      */
    public ProductPresell selectByProductId(Long productId);

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    public List<ProductPresell> selectProductPresellByPage(Page page);

    /**
     * 分页数据统计
     *
     * @param page
     * @return
     */
    public Integer selectProductPresellByPageCount(Page page);


    /**
     * 根据商品Id查询预售交易列表
     *
     * @param productId
     * @return
     */
    public List<Map>  selectPresellTransByProductId(Long productId);

    /**
     * 根据商品Id查询预售交易列表统计
     *
     * @param productId
     * @return
     */
    public List<PresellTrans>  selectPresellTransStatisticByProductId(Long productId);


    /**
     * 查询所有未绑定预售商品
     *
     * @return
     */
    public List<ProductPresell> selectAllUnBindProductPresell(Map<String, Object> para);
    /**
     * 
      * @Title: selectAllProductPresell
      * @Description: TODO
      * @author: Yaren Liang 2015年5月3日
      * @modify: Yaren Liang 2015年5月3日
      * @param @return    
      * @return List<ProductPresell>    
      * @throws
     */
    public List<ProductPresell> selectAllProductPresell();
}
