package com.froad.cbank.coremodule.normal.boss.support.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.thrift.service.DictionaryService;
import com.froad.thrift.vo.DictionaryItemVo;

/**
 * 字典查询
 * @ClassName DicSupport
 * @author zxl
 * @date 2016年1月14日 上午10:44:56
 */
@Service
public class DicSupport {
	
	@Resource
	DictionaryService.Iface dictionaryService;
	
	public List<Map<String,Object>> getItem(String dicCode, String clientId) throws TException, BossException{
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		//调用server端字典查询删除接口
		List<DictionaryItemVo> dicVoList = dictionaryService.getDictionaryItemList(dicCode, clientId);
		if(dicVoList != null && dicVoList.size() > 0){
			for(DictionaryItemVo dicVo : dicVoList){
				HashMap<String, Object> temp = new HashMap<String, Object>();
				temp.put("itemName", dicVo.getItemName());
				temp.put("itemValue", dicVo.getItemValue());
				list.add(temp);
			}
		}
		return list;
	}
}
