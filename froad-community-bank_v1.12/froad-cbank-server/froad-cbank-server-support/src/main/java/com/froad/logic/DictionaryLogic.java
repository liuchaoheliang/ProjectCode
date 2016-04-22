package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.po.Dictionaryitem;

public interface DictionaryLogic {
    
	/**
	 * 
	 * @Title: findDictionaryitemByPage 
	 * @Description: TODO
	 * @author: Yaolong Liang 2015年11月26日
	 * @modify: Yaolong Liang 2015年11月26日
	 * @param page
	 * @param dictionaryitem
	 * @return
	 * @return Page<Dictionaryitem>
	 * @throws
	 */
	public List<Dictionaryitem> findDictionaryitem(String DicCode,String clientId)throws org.apache.thrift.TException;
	/**
	 * 
	 * @Title: getDictionaryitemById 
	 * @Description: TODO
	 * @author: Yaolong Liang 2015年11月27日
	 * @modify: Yaolong Liang 2015年11月27日
	 * @param id
	 * @return
	 * @throws org.apache.thrift.TException
	 * @return Dictionaryitem
	 * @throws
	 */
	public Dictionaryitem getDictionaryitemById(Long id)throws org.apache.thrift.TException;
}
