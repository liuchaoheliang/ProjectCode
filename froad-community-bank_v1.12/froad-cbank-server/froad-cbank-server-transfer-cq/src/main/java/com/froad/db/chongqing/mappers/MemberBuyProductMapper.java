package com.froad.db.chongqing.mappers;

import java.util.List;
import java.util.Map;

import com.froad.cbank.persistent.entity.MemberBuyProduct;
import com.froad.cbank.persistent.entity.base.PageEntity;

public interface MemberBuyProductMapper {

	public Long insert(MemberBuyProduct t);
	
	public List<MemberBuyProduct> selectOfPage(PageEntity<MemberBuyProduct> pageEntity);
	
	public MemberBuyProduct selectById(Long id);

	public List<MemberBuyProduct> selectByCondition(MemberBuyProduct t);
	
	public boolean updateById(MemberBuyProduct t);
	
	/**
	  * 方法描述：修改会员商品购买数量
	  * @param: memberCode
	  * @param: productId
	  * @param: count
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2015年1月25日 下午3:05:56
	  */
	public void modifyBuyerCountByMemberCodeAndProductId(Map<String, Object> map);
}
