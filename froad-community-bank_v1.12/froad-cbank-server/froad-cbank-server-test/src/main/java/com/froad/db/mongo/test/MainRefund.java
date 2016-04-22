package com.froad.db.mongo.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class MainRefund {

	protected static MongoClient readMongoClient = null;
	protected static DB readMongoDB = null;
	
	static Set<String> orderId = new HashSet<String>();
	
	public static void main(String[] args) throws Exception{

		// 读取订单ID
		String filePath = "C:\\Users\\FQ\\Desktop\\青峰\\orderid.txt";
		readTxtFile(filePath);
				
		System.out.println("orderId 数量:"+orderId.size());
		
		int a=1;
		for(String orderid:orderId){
			System.out.println("第"+a+"个"+orderid+"查询进行中");
			
			DBCollection ref_dbConnection = getReadDBCollection("cb_order_refunds");
			BasicDBObject where = new BasicDBObject();
			where.put("order_id",orderid);
			List<DBObject> refOrderlist = ref_dbConnection.find(where).toArray();
			
			if(refOrderlist !=null && refOrderlist.size() > 0){
				System.out.println("有退款："+refOrderlist.size());
			}
			a++;
		}
		System.out.println("end");
		
		
	}
	
	private static DBCollection getReadDBCollection(String connName) throws Exception{
		
		readMongoClient=new MongoClient(new ServerAddress("10.24.3.53", 6330));
		readMongoDB = readMongoClient.getDB("cbank");
		readMongoDB.authenticate("cbank", "123456".toCharArray());
		
		return readMongoDB.getCollection(connName);
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
				int no = 0;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					orderId.add(lineTxt);
					no++;
				}
				System.out.println("payid数量：" + no);
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}

	}


}
