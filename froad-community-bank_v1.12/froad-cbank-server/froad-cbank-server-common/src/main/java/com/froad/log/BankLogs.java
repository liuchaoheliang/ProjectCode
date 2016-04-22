package com.froad.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.froad.log.vo.ProductLog;

public class BankLogs {
	private static Logger logger = LoggerFactory.getLogger(BankLogs.class);

	//商品新增
	public static Boolean updateProduct(ProductLog product){
		logger.info(JSON.toJSONString(product));
		return true;
	} 
}
