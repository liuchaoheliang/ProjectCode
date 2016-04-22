package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.logback.LogCvt;
import com.froad.logic.DictionaryLogic;
import com.froad.logic.impl.DictionaryLogicImpl;
import com.froad.po.Dictionaryitem;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.DictionaryService.Iface;
import com.froad.thrift.vo.DictionaryItemVo;
import com.froad.thrift.vo.MerchantVo;
import com.froad.thrift.vo.OutletDetailVo;
import com.froad.util.BeanUtil;

public class DictionaryServiceImpl extends BizMonitorBaseService implements  Iface{
     private DictionaryLogic dictionaryLogic=new DictionaryLogicImpl();
   
 	public DictionaryServiceImpl() {}
 	public DictionaryServiceImpl(String name, String version) {
 		super(name, version);
 	}
     
	@Override
	public List<DictionaryItemVo> getDictionaryItemList(String dicCode, String clientId)
			throws TException {
		LogCvt.info("根据dicCode,clientId查询字典,dicCode="+dicCode+",clientId"+clientId);
		List<DictionaryItemVo> voList = new ArrayList<DictionaryItemVo>();
			if(dicCode==null||clientId==null){
				LogCvt.error("dicCode"+dicCode+",clientId"+clientId+",存在空值");
				return voList;
			}
			List<Dictionaryitem> items=	dictionaryLogic.findDictionaryitem(dicCode, clientId);
			voList =  (List<DictionaryItemVo>) BeanUtil.copyProperties(DictionaryItemVo.class, items);
			
		   return voList;
	}
	@Override
	public DictionaryItemVo getDictionaryitemById(long id) throws TException {
		LogCvt.info("根据ID查询字典,id="+id);
	    DictionaryItemVo itemVo = new DictionaryItemVo();
			Dictionaryitem item=	dictionaryLogic.getDictionaryitemById(id);
			itemVo =  (DictionaryItemVo) BeanUtil.copyProperties(DictionaryItemVo.class, item);
		return itemVo;
	}
	
 
	
	
	
	
	
	
	
	
	
	
	
	
	
}
