package com.froad.logic;

import com.froad.po.Waybill;
import com.froad.po.Result;

public interface DeliveryWayBillLogic {
	
	/**
	 * 
	 * @Title: getDeliveryWayBillBy 
	 * @Description: TODO
	 * @author: Yaolong Liang 2015年11月30日
	 * @modify: Yaolong Liang 2015年11月30日
	 * @return
	 * @return DeliveryWayBill
	 * @throws
	 */
	public Waybill getDeliveryWayBill(Waybill deliveryWayBill);
	
	/**
	 * 
	 * @Title: updateDeliveryWayBillBy 
	 * @Description: TODO
	 * @author: Yaolong Liang 2015年11月30日
	 * @modify: Yaolong Liang 2015年11月30日
	 * @param deliveryWayBill
	 * @return void
	 * @throws
	 */
	public Result updateDeliveryWayBill(Waybill deliveryWayBill);
	
	/**
	 * 
	 * @Title: updateDeliveryWayBillStates 
	 * @Description: TODO
	 * @author: Yaolong Liang 2015年12月2日
	 * @modify: Yaolong Liang 2015年12月2日
	 * @param deliveryWayBill
	 * @return
	 * @return Result
	 * @throws
	 */
	public Result updateDeliveryWayBillByCondition(Waybill deliveryWayBill);
	
	

}
