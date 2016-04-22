
package com.froad.db.chongqing.mappers;

import java.util.List;

import com.froad.cbank.persistent.entity.Payment;

/**
 * 向server层暴露接口提供服务
 * <p>Function: PaymentMapper</p>
 * <p>Description: </p>
 * @version 1.0
 * @author 蔡世璨 <mailto>caishican@f-road.com.cn</mailto>
 * @date 2014-12-24 11:46:09
 *
 */
public interface PaymentMapper {
    /**
     * 通过数据主键获取数据
    * <p>Function: selectById</p>
    * <p>Description: </p>
    * @version 1.0
    * @param id
    * @return
     */
    public Payment selectById(Long id);

    /**
     * 通过数据SN获取数据
     * <p>Function: selectBySN</p>
     * <p>Description: </p>
     * @version 1.0
     * @param id
     * @return
     */
    public Payment selectBySN(String sn);
    
    /**
     *  查询全部
      * @Title: selectAll
      * @Description: TODO
      * @author: share 2015年7月16日
      * @modify: share 2015年7月16日
      * @param @return    
      * @return Payment    
      * @throws
     */
    public List<Payment> selectAll();
    
    /**
     *  查询结算支付记录 
      * @Title: selectSettlementAll
      * @Description: TODO
      * @author: share 2015年7月24日
      * @modify: share 2015年7月24日
      * @param @return    
      * @return List<Payment>    
      * @throws
     */
    public List<Payment> selectSettlementAll();
}
