package com.froad.db.chongqing.mappers;

import java.util.List;

import com.froad.cbank.persistent.entity.DeliveryCorp;
import com.froad.cbank.persistent.entity.base.PageEntity;

/**
 *  物流公司mybatis接口
  * @ClassName: DeliveryCorpMapper
  * @Description: TODO
  * @author share 2015年01月03日
  * @modify share 2015年01月03日
 */
public interface DeliveryCorpMapper {
/**
 * 
  * @Title: findDeliveryCorp
  * @Description: TODO
  * @author: Yaren Liang 2015年6月25日
  * @modify: Yaren Liang 2015年6月25日
  * @param @param deliveryCorp
  * @param @return    
  * @return List<DeliveryCorp>    
  * @throws
 */
	public List<DeliveryCorp> findDeliveryCorp(DeliveryCorp deliveryCorp);
}
