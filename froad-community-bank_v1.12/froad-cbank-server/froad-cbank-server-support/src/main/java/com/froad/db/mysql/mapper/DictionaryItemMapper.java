package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.Dictionaryitem;
import com.froad.po.MerchantAccount;

public interface DictionaryItemMapper {

	
	/**
	 * 
	 * @Title: findByPage 
	 * @Description: TODO
	 * @author: Yaolong Liang 2015年11月26日
	 * @modify: Yaolong Liang 2015年11月26日
	 * @param page
	 * @param dictionaryitem
	 * @return
	 * @return List<Dictionaryitem>
	 * @throws
	 */

	public List<Dictionaryitem> findDictionaryitemByDicCode(@Param("DicCode")String DicCode,@Param("clientId")String clientId);
	/**
	 * 
	 * @Title: getDictionaryitemById 
	 * @Description: TODO
	 * @author: Yaolong Liang 2015年11月27日
	 * @modify: Yaolong Liang 2015年11月27日
	 * @param id
	 * @return
	 * @return Dictionaryitem
	 * @throws
	 */
	public Dictionaryitem getDictionaryitemById(Long id);
}
