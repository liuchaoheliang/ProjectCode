package com.froad.db.mysql.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.Waybill;

public interface WayBillCommonMapper {

	/**
	 * 
	 * @Title: findDeliveryWayBill 
	 * @Description: TODO
	 * @author: Yaolong Liang 2015年11月30日
	 * @modify: Yaolong Liang 2015年11月30日
	 * @return
	 * @return DeliveryWayBill
	 * @throws
	 */
	public Waybill findDeliveryWayBill(String subOrderId);
	
	
	/**
	 * 
	 * @Title: findOneDeliveryWayBill 
	 * @Description: TODO
	 * @author: Yaolong Liang 2015年12月1日
	 * @modify: Yaolong Liang 2015年12月1日
	 * @return
	 * @return Integer
	 * @throws
	 */
	public Waybill findOneDeliveryWayBill(Waybill deliveryWayBill);
	/**
	 * 
	 * @Title: addDeliveryWayBill 
	 * @Description: TODO
	 * @author: Yaolong Liang 2015年12月1日
	 * @modify: Yaolong Liang 2015年12月1日
	 * @return
	 * @return Long
	 * @throws
	 */
    public Long addDeliveryWayBill(Waybill deliveryWayBill);
    /**
     * 
     * @Title: findDeliveryWayBill 
     * @Description: TODO
     * @author: Yaolong Liang 2015年12月2日
     * @modify: Yaolong Liang 2015年12月2日
     * @return
     * @return List<DeliveryWayBill>
     * @throws
     */
    public List<Waybill> findDeliveryWayBillList(@Param("date")Date date,@Param("status")String[] status);
    
	
    /**
	 * 
	 * updateWayBill:(更新运单表信息).
	 *
	 * @author huangyihao
	 * 2015年11月30日 上午10:54:33
	 * @param waybill
	 * @return
	 *
	 */
	public Boolean updateWayBill(Waybill waybill);
	
	/**
	 * 
	 * @Title: updateWayBillByShippingId 
	 * @Description: TODO
	 * @author: Yaolong Liang 2016年1月11日
	 * @modify: Yaolong Liang 2016年1月11日
	 * @param waybill
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean updateWayBillByCondition(Waybill waybill);
    
	
	/**
	 * 
	 * getCountByShippingId:(查询有没重复物流单号).
	 *
	 * @author huangyihao
	 * 2016年1月13日 下午4:42:46
	 * @param shippingId
	 * @return
	 *
	 */
    public Integer getCountByShippingId(String shippingId);
}
