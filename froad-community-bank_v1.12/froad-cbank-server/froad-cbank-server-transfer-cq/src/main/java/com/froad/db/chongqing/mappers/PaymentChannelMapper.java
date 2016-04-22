package com.froad.db.chongqing.mappers;

import java.util.List;

import com.froad.cbank.persistent.entity.PaymentChannel;
import com.froad.cbank.persistent.entity.base.PageEntity;

public interface PaymentChannelMapper {

	/**
	 *  数据插入
	  * @Title: insert
	  * @Description: TODO
	  * @author: share 2014年12月24日
	  * @modify: share 2014年12月24日
	  * @param @param paymentChannel
	  * @param @return    
	  * @return Long    
	  * @throws
	 */
	public Long insert(PaymentChannel paymentChannel);
	
	/**
	 *  基础分页查询
	  * @Title: selectOfPage
	  * @Description: TODO
	  * @author: share 2014年12月24日
	  * @modify: share 2014年12月24日
	  * @param @param pageEntity
	  * @param @return    
	  * @return List<PaymentChannel>    
	  * @throws
	 */
	public List<PaymentChannel>selectOfPage(PageEntity<PaymentChannel> pageEntity);
	
	/**
	 *  通过数据主键获取数据
	  * @Title: selectById
	  * @Description: TODO
	  * @author: share 2014年12月24日
	  * @modify: share 2014年12月24日
	  * @param @param id
	  * @param @return    
	  * @return PaymentChannel    
	  * @throws
	 */
	public PaymentChannel selectById(Long id);

	
	/**
	 *  通过传入的JavaBean非空属性组合查询条件
	  * @Title: selectByCondition
	  * @Description: TODO
	  * @author: share 2014年12月24日
	  * @modify: share 2014年12月24日
	  * @param @param paymentChannel
	  * @param @return    
	  * @return List<Client>    
	  * @throws
	 */
	public List<PaymentChannel> selectByCondition(PaymentChannel paymentChannel);
	
	
	/**
	 *  通过传入的JavaBean id 属性确定数据，将该JavaBean的其他非空属性执行更新命令
	  * @Title: updateById
	  * @Description: TODO
	  * @author: share 2014年12月24日
	  * @modify: share 2014年12月24日
	  * @param @param paymentChannel
	  * @param @return    
	  * @return boolean    
	  * @throws
	 */
	public boolean updateById(PaymentChannel paymentChannel);
	
	/**
	  * 方法描述：查询支付渠道列表
	  * @param: clientId
	  * @return: List<PaymentChannel>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年12月29日 下午12:01:10
	  */
	public List<PaymentChannel> selectByClientId(Long clientId);
}
