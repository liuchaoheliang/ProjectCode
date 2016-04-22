package com.froad.db.mongo.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * 根据商户id统计销量与金额
 * @ClassName: MainMT 
 * @Description: TODO 
 * @author FQ 
 * @date 2015年7月30日 下午5:02:10 
 *
 */
public class MainMT {

	static Set<String> merchantIdSet = new HashSet<String>();

	public static void main(String[] args) throws Exception{

		// 读取订单ID
		String filePath = "C:\\Users\\FQ\\Desktop\\青峰\\mid.txt";
		readTxtFile(filePath);

		System.out.println("merchantId 数量:" + merchantIdSet.size());
		
		FileWriter fw = new FileWriter("C:\\Users\\FQ\\Desktop\\青峰\\农副特产类商户12.csv");
		String header = "商户ID,商户名称,子订单上商户ID,子订单上商户名称,销量,金额\r\n";
		fw.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF })); 
		fw.write(header);

		
		
		int a = 1;
		for (String merchantid : merchantIdSet) {
			System.out.println("第" + a + "个" + merchantid + "查询进行中");
			
			DBCollection merchant_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_merchant_detail");
			DBObject mer_detail = (DBObject) merchant_dbConnection.findOne(new BasicDBObject("_id",merchantid));
			JSONObject mer_detail_json = JSON.parseObject(mer_detail.toString());
			String mer_detail_name = mer_detail_json.getString("merchant_name");
			
			DBCollection sub_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_suborder_product");
			BasicDBObject where = new BasicDBObject();
			where.put("order_status", "6"); //订单支付完成
			where.put("merchant_id", merchantid);
			List<DBObject> subOrderlist = sub_dbConnection.find(where).toArray();
			System.out.println("subOrderlist.size "+subOrderlist.size());
			double total_buynum = 0;
			double total_money = 0;
			
			String merchantId = null;
	        String merchantName = null;
	        for(DBObject result : subOrderlist){
	        	System.out.println(result.toString());
	        	JSONObject sub_json = JSON.parseObject(result.toString());
	        	
	        	merchantId = sub_json.get("merchant_id").toString();
	        	merchantName = sub_json.get("merchant_name").toString();
	        	
	        	double product_buy_num = 0;
	        	double product_money = 0;
	        	JSONArray products_json = JSONArray.parseArray(sub_json.get("products").toString()); 
	        	if(products_json.size() > 0){
	        		System.out.println("products_json:"+products_json.toString());
	            	for(int i=0;i<products_json.size();i++){
	                    JSONObject product = products_json.getJSONObject(i);  // 遍历数组，把每一个对象转成 json 对象
	                    product_buy_num = Double.valueOf(product.get("quantity").toString());
	                    product_money = getMoney(Double.valueOf(product.get("money").toString())) ;  // 得到 每个对象中的属性值
	            	
	                    total_buynum = add(total_buynum,product_buy_num);
	                    total_money = add(total_money,mul(product_buy_num,product_money));
	            	}
	        	}
	        	
	        }
			
	        StringBuffer str = new StringBuffer();
            str.append(merchantid+",");
            str.append(mer_detail_name+",");
            str.append(merchantId+",");
            str.append(merchantName+",");
            str.append(total_buynum+",");
            str.append(total_money);
            
            str.append("\r\n");
            
            fw.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF })); 
			fw.write(str.toString());
		    fw.flush();
		    
		    System.out.println(" ");
		    a++;
		}
		
		fw.close();
        System.out.println("end");
	}

	public static void readTxtFile(String filePath) {
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					merchantIdSet.add(lineTxt.trim());
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}

	}
	
	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;
	
	private static double getMoney(double momey) {
        return div(momey, 1000);
    }
	
	private static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}
	
	private static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The   scale   must   be   a   positive   integer   or   zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	//相加
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}
	
	//相乘
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}
}
