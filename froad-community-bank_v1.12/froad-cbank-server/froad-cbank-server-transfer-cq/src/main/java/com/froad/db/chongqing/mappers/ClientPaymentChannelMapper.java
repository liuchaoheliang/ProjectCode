package com.froad.db.chongqing.mappers;

import com.froad.cbank.persistent.entity.ClientPaymentChannel;

/**
 *  mybatis接口
  * @ClassName: ClientPaymentChannelMapper
  * @Description: TODO
  * @author share 2014年12月31日
  * @modify share 2014年12月31日
 */
public interface ClientPaymentChannelMapper {

	/**
	 *  添加
	  * @Title: insert
	  * @Description: TODO
	  * @author: share 2014年12月29日
	  * @modify: share 2014年12月29日
	  * @param @param clientPaymentChannel
	  * @param @return    
	  * @return Long    
	  * @throws
	 */
	public Long insert(ClientPaymentChannel clientPaymentChannel);
	
	/**
	 *   删除
	  * @Title: delete
	  * @Description: TODO
	  * @author: share 2014年12月29日
	  * @modify: share 2014年12月29日
	  * @param @param clientPaymentChannel
	  * @param @return    
	  * @return long    
	  * @throws
	 */
	public long delete(ClientPaymentChannel clientPaymentChannel);
}
