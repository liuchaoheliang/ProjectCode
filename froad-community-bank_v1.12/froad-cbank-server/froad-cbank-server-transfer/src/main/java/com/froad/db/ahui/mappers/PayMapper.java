/*   
* Copyright © 2008 F-Road All Rights Reserved.
*  
* This software is the confidential and proprietary information of   
* Founder. You shall not disclose such Confidential Information   
* and shall use it only in accordance with the terms of the agreements   
* you entered into with Founder.   
*   
*/

/**
 * 
 * @Title: OrgMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.ahui.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.froad.fft.persistent.entity.Pay;

/**
 *  支付模块Mybatis接口
  * @ClassName: PayMapper
  * @Description: TODO
  * @author share 2015年5月1日
  * @modify share 2015年5月1日
 */
public interface PayMapper {

	
	public Pay queryLastOrderPayInfo(@Param("transId")Long transId);
	
	/**
	 *  结算查询支付记录信息
	  * @Title: queryBySettlement
	  * @Description: TODO
	  * @author: share 2015年5月1日
	  * @modify: share 2015年5月1日
	  * @param @param transId
	  * @param @return    
	  * @return Pay    
	  * @throws
	 */
	public Pay queryBySettlement(@Param("transId")Long transId);
	/**
	 *  按卷号查询支付信息
	  * @Title: queryByTicketNo
	  * @Description: TODO
	  * @author: share 2015年5月1日
	  * @modify: share 2015年5月1日
	  * @param @param ticketNo
	  * @param @return    
	  * @return Pay    
	  * @throws
	 */
	public Pay queryByTicketNo(@Param("ticketNo")Long ticketNo);
	
	
	/**
	 *  查询根据SN号查询
	  * @Title: queryPayBySn
	  * @Description: TODO
	  * @author: share 2015年5月4日
	  * @modify: share 2015年5月4日
	  * @param @param sn
	  * @param @return    
	  * @return Pay    
	  * @throws
	 */
	public Pay queryPayBySn(@Param("sn")String sn);
	
	/**
	 *  查询组合支付的退款记录
	  * @Title: selectPayRefundsDuplicate
	  * @Description: TODO
	  * @author: share 2015年5月4日
	  * @modify: share 2015年5月4日
	  * @param @return    
	  * @return List<Map<String,String>>    
	  * @throws
	 */
	public List<Map<String,Long>> selectPayRefundsDuplicate();
	
	/**
	 *  查询退款的支付记录
	  * @Title: selectPayRefunds
	  * @Description: TODO
	  * @author: share 2015年5月4日
	  * @modify: share 2015年5月4日
	  * @param @return    
	  * @return List<Pay>    
	  * @throws
	 */
	public List<Pay> selectPayRefunds();
	
	
	/**
	 * 获取订单对应最早支付流水时间
	 * @return
	 */
	public List<Pay>  findAllGroupFirstDate();

}