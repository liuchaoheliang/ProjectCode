package com.froad.log;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.froad.log.vo.ProductLog;



public class GoodsLogs {
	private static Logger logger = LoggerFactory.getLogger(GoodsLogs.class);
	 
	public static void main(String[] args) {
		GoodsLogs log= new GoodsLogs();

	}
	//商品新增
	public static Boolean addProduct(ProductLog product){
		logger.info(JSON.toJSONString(product));
		return true;
	} 
	//商品修改
	public static Boolean updateProduct(ProductLog product){
		logger.info(JSON.toJSONString(product));
		return true;
	}
	//商品删除
	public static Boolean deleteProduct(String productId,String clientId,Long create_time,
			String action){
		StringBuffer deleteStr = new StringBuffer();
		deleteStr = new StringBuffer();
		deleteStr.append("{");
		deleteStr.append("\"key\": { \"product_id\": \"");
		deleteStr.append(productId);
		deleteStr.append("\"},");
		deleteStr.append("\"action\": \"PRODUCTDELETE\",");
		deleteStr.append("\"client_id\": \"");
		deleteStr.append(clientId);
		deleteStr.append("\",");
		deleteStr.append("\"time\": ");
		deleteStr.append(new Date().getTime());
		deleteStr.append(",");
		deleteStr.append("\"data\": {");
		deleteStr.append("\"product_id\":\"");
		deleteStr.append(productId);
		deleteStr.append("\"");
		deleteStr.append("\"create_time\":\"");
		deleteStr.append(create_time);
		deleteStr.append("\"");
		deleteStr.append("}");
		deleteStr.append("}");
		deleteStr.append("\n");
		logger.info(deleteStr.toString());
		return true;
	}  
}
