
package com.froad.db.chongqing.mappers;

import java.util.List;

import com.froad.db.chongqing.entity.OrderEx;
import com.froad.db.chongqing.entity.OrderProduct;

/**
 * 向server层暴露接口提供服务
 * <p>Function: OrderMapper</p>
 * <p>Description: </p>
 * @version 1.0
 * @author 蔡世璨 <mailto>caishican@f-road.com.cn</mailto>
 * @date 2014-12-24 11:46:09
 *
 */
public interface OrderMapper {

    /**
     * 通过数据主键获取数据
    * <p>Function: selectById</p>
    * <p>Description: </p>
    * @version 1.0
    * @param id
    * @return
     */
    public OrderEx selectById(Long id);

    /**
     * 通过订单编号获取数据
     * <p>Function: selectById</p>
     * <p>Description: </p>
     * @version 1.0
     * @param id
     * @return
     */
    public OrderEx selectBySN(String sn);
    
    /**
     *  查询所有订单信息
      * @Title: selectAll
      * @author: share 2015年6月29日
      * @modify: share 2015年6月29日
      * @param @return    
      * @return List<OrderEx>    
      * @throws
     */
    public List<OrderEx> selectAll();
    
    /**
     *  查询所有积分支付的积分比例
      * @Title: selectPointAll
      * @author: share 2015年7月20日
      * @modify: share 2015年7月20日
      * @param @return    
      * @return List<OrderEx>    
      * @throws
     */
    public List<OrderEx> selectPointAll();
    
    /**
     *  查询面对面结算订单
      * @Title: selectFaceSettelment
      * @author: share 2015年7月23日
      * @modify: share 2015年7月23日
      * @param @return    
      * @return List<OrderEx>    
      * @throws
     */
    public List<OrderEx> selectFaceSettelment();
    
    
    /**
     * select product information by order id
     * 
     * @param orderId
     * @return
     */
    public OrderProduct selectProductInfoByOrderId(Long orderId);
    
    /**
     * select product information by order sn
     * 
     * @param sn
     * @return
     */
    public OrderProduct selectProductInfoBySN(String sn);    
    
    /**
     * 预售未生成券商品
     * 
     * @return
     */
    public List<OrderEx> selectPresellProduct();
}