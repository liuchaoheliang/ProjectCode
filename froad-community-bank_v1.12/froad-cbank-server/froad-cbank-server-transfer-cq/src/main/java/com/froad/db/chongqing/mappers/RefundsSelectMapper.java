package com.froad.db.chongqing.mappers;

import java.util.List;

import com.froad.cbank.persistent.entity.OrderRefunds;
import com.froad.cbank.persistent.entity.base.PageEntity;

/**
 * 退款查询
 * 
 * @author luofan
 * @version $Id: RefundsSelectMapper.java, v 0.1 2014年1月12日 下午2:4:21 Exp $
 */
public interface RefundsSelectMapper {

	 /**
     * 按退款ID查询退款信息
     * @author luofan
     * @version $Id: 2015年1月12日 下午2:4:21 Exp $
     * @param id
     * @return OrderRefunds
     */
	public OrderRefunds selectById(Long id);

	 /**
     * 分页查询退款信息
     * @author luofan
     * @version $Id: 2015年1月12日 下午2:4:21 Exp $
     * @param PageEntity<OrderRefunds> pageDto
     * @return List<OrderRefunds>
     */
	public List<OrderRefunds> selectOfPage(PageEntity<OrderRefunds> pageEntity);

	 /**
     * 按条件查询退款信息
     * @author luofan
     * @version $Id: 2015年1月12日 下午2:4:21 Exp $
     * @param orderRefunds
     * @return List<OrderRefunds>
     */
	public List<OrderRefunds> selectByCondition(OrderRefunds orderRefundsDto);
}
