
package com.froad.db.chongqing.mappers;

import java.util.List;

import com.froad.cbank.persistent.entity.OrderDetails;
import com.froad.cbank.persistent.entity.base.PageEntity;

/**
 * 向server层暴露接口提供服务
 * <p>Function: OrderDetailsMapper</p>
 * <p>Description: </p>
 * @version 1.0
 * @author 蔡世璨 <mailto>caishican@f-road.com.cn</mailto>
 * @date 2014-12-24 11:46:09
 *
 */
public interface OrderDetailsMapper {

    /**
     * 数据插入
    * <p>Function: insert</p>
    * <p>Description: </p>
    * @version 1.0
    * @param orderDetails
    * @return
     */
    public Long insert(OrderDetails orderDetails);
    /**
     * 数据插入
     * <p>Function: insert</p>
     * <p>Description: </p>
     * @version 1.0
     * @param orderDetails
     * @return
     */
    public Long insertMore(List<OrderDetails> orderDetails);

    /**
     * 基础分页查询
    * <p>Function: selectOfPage</p>
    * <p>Description: </p>
    * @version 1.0
    * @param page
    * @return
     */
    public List<OrderDetails> selectOfPage(PageEntity<OrderDetails> pageEntity);

    /**
     * 通过数据主键获取数据
    * <p>Function: selectById</p>
    * <p>Description: </p>
    * @version 1.0
    * @param id
    * @return
     */
    public OrderDetails selectById(Long id);

    /**
     * 通过订单数据主键获取数据
    * <p>Function: selectByOrderId</p>
    * <p>Description: </p>
    * @version 1.0
    * @param id
    * @return
     */
    public List<OrderDetails> selectByOrderID(Long id);
    /**
     * 通过传入的JavaBean非空属性组合查询条件
    * <p>Function: selectByCondition</p>
    * <p>Description: </p>
    * @version 1.0
    * @return
     */
    public List<OrderDetails> selectByCondition(OrderDetails orderDetails);

    /**
     * 通过传入的JavaBean id 属性确定数据，将该JavaBean的其他非空属性执行更新命令
    * <p>Function: updateById</p>
    * <p>Description: </p>
    * @version 1.0
    * @param orderDetails
    * @return
     */
    public boolean updateById(OrderDetails orderDetails);
}
