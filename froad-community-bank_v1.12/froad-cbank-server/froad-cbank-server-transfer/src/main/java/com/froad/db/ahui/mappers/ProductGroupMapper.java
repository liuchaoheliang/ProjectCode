package com.froad.db.ahui.mappers;

import com.froad.db.ahui.entity.ProductGroup;
import com.froad.fft.persistent.bean.page.Page;

import java.util.List;
import java.util.Map;

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

    /**
     * 查询所有未绑定团购商品
     *
     * @return
     */
    public List<ProductGroup> selectAllUnBindProductGroup( Map<String, Object> para);
    
    
    /**
      * 方法描述：查询
      * @param: productId 商品编号
      * @return: ProductGroup
      * @version: 1.0
      * @author: 李金魁 lijinkui@f-road.com.cn
      * @time: 2014年5月14日 下午3:12:38
      */
    public ProductGroup selectByProductId(Long productId);
    /**
     * 
      * @Title: selectAllProductGroup
      * @Description: TODO
      * @author: Yaren Liang 2015年5月3日
      * @modify: Yaren Liang 2015年5月3日
      * @param @return    
      * @return ProductGroup    
      * @throws
     */
    public List<ProductGroup> selectAllProductGroup();
}
