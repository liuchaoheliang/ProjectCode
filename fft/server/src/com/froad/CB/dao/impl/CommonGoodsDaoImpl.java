package com.froad.CB.dao.impl;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.common.GoodsCommand;

public class CommonGoodsDaoImpl {
	
	private static final Logger logger=Logger.getLogger(CommonGoodsDaoImpl.class);

	private SqlMapClientTemplate sqlMapClientTemplate;
	
	
	/**
	  * 方法描述：查询上架商品
	  * @param: namespace sqlmap里的namespace
	  * @param: id 上架商品的编号
	  * @return: 上架商品对象
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: May 9, 2013 6:29:31 PM
	  */
	public Object getCommonGoodsById(String namespace,Integer id){
		return sqlMapClientTemplate.queryForObject(namespace+".selectByPrimaryKey",id);
	}

	
	/**
	  * 方法描述：增加商品的销售数量(仅用于团购和积分兑换)
	  * @param: namespace sqlmap里的namespace
	  * @param: addNumber 增加的数量
	  * @param: goodsRackId 上架商品的编号
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: May 9, 2013 6:26:57 PM
	  */
	public void addSaleNumberById(String namespace,int addNumber,Integer goodsRackId){
		if(!GoodsCommand.SALE_NUMBER_CLAZZ_NAME.contains(namespace)){
			logger.error("namespace:"+namespace+"，不支持添加销售数量。");
			return;
		}
		HashMap<String, Integer> params=new HashMap<String, Integer>();
		params.put("num", addNumber);
		params.put("id", goodsRackId);
		sqlMapClientTemplate.update(namespace+".addSaleNumberById",params);
	}
	
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}
	
}
