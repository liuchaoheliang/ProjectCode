
package com.froad.db.chongqing.mappers;

import java.util.List;

import com.froad.cbank.persistent.entity.Refunds;
import com.froad.cbank.persistent.entity.base.PageEntity;
import com.froad.cbank.persistent.exception.DBFroadException;

/**
 * 向server层暴露接口提供服务
 * <p>Function: OrderRefundsMapper</p>
 * <p>Description: </p>
 * @version 1.0
 * @author 蔡世璨 <mailto>caishican@f-road.com.cn</mailto>
 * @date 2014-12-24 11:46:09
 *
 */
public interface RefundsMapper {

	public List<Refunds> selectForPayment();

    /**
     * 基础分页查询
    * <p>Function: selectOfPage</p>
    * <p>Description: </p>
    * @version 1.0
    * @param page
    * @return
     */
    public PageEntity<Refunds> selectOfPage(PageEntity<Refunds> pageEntity);

    /**
     * 通过数据主键获取数据
    * <p>Function: selectById</p>
    * <p>Description: </p>
    * @version 1.0
    * @param id
    * @return
     */
    public Refunds selectById(Long id);

    /**
     * 通过传入的JavaBean非空属性组合查询条件
    * <p>Function: selectByCondition</p>
    * <p>Description: </p>
    * @version 1.0
     * @return
     */
    public List<Refunds> selectAll();

    /**
     * 方法描述：查询券退款详情
     * @version: 1.0
     * @author: 蔡世璨 caishican@froad.com.cn
     * @time: 2015年1月7日 下午5:25:24
     * @param ticketId
     * @return List<Refunds>
     */
    public List<Refunds> selectByTicketId(Long ticketId);
    
    /**
     * 方法描述：根据ticket id 将所有非支付成功的退款信息设置为不可用
     * @version: 1.0
     * @author: 蔡世璨 caishican@froad.com.cn
     * @time: 2015年1月8日 下午3:01:17
     * @param ticketId
     * @return Integer
     */
    public Integer disableByTicketId(Long ticketId);

    /**
     * 方法描述：通过第三方退款单号查询退款信息
     * @version: 1.0
     * @author: 蔡世璨 caishican@froad.com.cn
     * @time: 2015年1月9日 上午11:43:15
     * @param refundBillNo
     * @return
     * @throws DBFroadException Refunds
     */
    public Refunds selectByRefundBillNo(String refundBillNo) throws DBFroadException;

    /**
     * 方法描述：通过订单SN查询退款详情
     * @version: 1.0
     * @author: 蔡世璨 caishican@froad.com.cn
     * @time: 2015年1月9日 上午11:43:41
     * @param orderSn
     * @return
     * @throws DBFroadException List<Refunds>
     */
    public List<Refunds> selectRefundedByOrderSn(String orderSn) throws DBFroadException;
    
    /**
     * 获取组合支付退款订单SN
     * 
     * @return
     */
    public List<String> getCombineOrderSn();
}
