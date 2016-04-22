package com.froad.db.ahui.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.froad.fft.persistent.entity.TransRefunds;

/**
 *  退款Mybatis接口
  * @ClassName: TransRefundsMapper
  * @Description: TODO
  * @author share 2015年5月2日
  * @modify share 2015年5月2日
 */
public interface TransRefundsMapper {

	/**
	 *  卷号查询退款成功的记录
	  * @Title: getTransRefundsByTickeId
	  * @Description: TODO
	  * @author: share 2015年5月3日
	  * @modify: share 2015年5月3日
	  * @param @param id
	  * @param @return    
	  * @return TransRefunds    
	  * @throws
	 */
	public TransRefunds getTransRefundsByTickeId(@Param("ticketId")Long id);
	
	/**
	 *  查询所有的退款记录信息
	  * @Title: getTransRefundsAll
	  * @Description: TODO
	  * @author: share 2015年5月3日
	  * @modify: share 2015年5月3日
	  * @param @return    
	  * @return TransRefunds    
	  * @throws
	 */
	public List<TransRefunds> getTransRefundsAll();
	
	/**
	 *  查询重复组合支付的订单号
	  * @Title: getTransRefundsDuplicate
	  * @Description: TODO
	  * @author: share 2015年5月3日
	  * @modify: share 2015年5月3日
	  * @param @return    
	  * @return List<String>    
	  * @throws
	 */
	public List<Map<String,String>> getTransRefundsDuplicate();
	
}

