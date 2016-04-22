package com.froad.db.mongo.test;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryOperators;
import com.mongodb.ServerAddress;

/**
 * 根据预售商品名称查询订单相关
 * ClassName: MainYs
 * Function: TODO ADD FUNCTION
 * date: 2015年8月6日 下午2:04:39
 *
 * @author FQ
 * @version
 */
public class MainYs {

	protected static MongoClient readMongoClient = null;
	protected static DB readMongoDB = null;
	
	static DBHelper db1 = null;
	static ResultSet ret1 = null;
	
	public static void main(String[] args) throws Exception{
		
		//商品ID
//		String proudctId="097A70928000";//
//		String fileName="莫斯利安-巢湖市";
//		String proudctId="095E57920000";//
//		String fileName="莫斯利安-肥西县";
//		String proudctId="0950F0728000";//
//		String fileName="莫斯利安-肥东县";
//		String proudctId="094CEEE20000";//
//		String fileName="莫斯利安-长丰县";
//		String proudctId="094B45628000";//
//		String fileName="莫斯利安-合肥市";
//		String proudctId="09A43BB20000";//
//		String fileName="醉金香葡萄采摘劵";
//		String proudctId="097566E28000";//
//		String fileName="光明莫斯利安酸奶";
		
//		String proudctId="08E601020000";//
//		String fileName="寿玉大米";
//		String proudctId="099E89828000";//
//		String fileName="夏黑葡萄";
//		String proudctId="099EE7220000";//
//		String fileName="小磨香油";
//		String proudctId="09C9A7428000";//
//		String fileName="黄麓精华葡萄";
		
		String proudctId="095F81120000";//
		String fileName="文彩黑芝麻香油-12-1";
		
		
		FileWriter fw = new FileWriter("C:\\Users\\FQ\\Desktop\\预售\\"+fileName+".csv");
		String header = "创建时间,订单号,是否有退款,状态,账单号,支付方式,总价,现金,积分,提货人姓名,手机号,所属行社CODE,提货网点CODE,提货网点名称,商品ID,商品名称,购买数量\r\n";
		fw.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF })); 
		fw.write(header);
		
		DBCollection sub_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_suborder_product");
		
		BasicDBObject where = new BasicDBObject();
		where.put("products.product_id",proudctId);
		
		List<DBObject> subOrderlist = sub_dbConnection.find(where).toArray();
		
		System.out.println("数量："+subOrderlist.size());
		
		int a=1;
        for(DBObject result : subOrderlist){
        	System.out.println("查询"+a);
        	System.out.println(result.toString());
        	JSONObject sub_json = JSON.parseObject(result.toString());
        	
        	//订单创建时间
        	String creatTime=sub_json.get("create_time").toString();
        	Date date= new Date(Long.parseLong(creatTime.trim()));  
    	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    	    String dateString = formatter.format(date); 
        	
        	//订单号
            String orderId = sub_json.get("order_id").toString();
            
            //二级机构
            String sOrgCode = sub_json.get("s_org_code").toString();
           
            //商品
            String product_id = null;
            String product_name = null;
            String product_buy_num = null;
            
            //提货点 
            String dOrgCdoe = null;
            String dOrgName = null;
            
            JSONArray products_json = JSONArray.parseArray(sub_json.get("products").toString()); 
            if(products_json.size() > 0){
            	for(int i=0;i<products_json.size();i++){
                    JSONObject product = products_json.getJSONObject(i);  // 遍历数组，把每一个对象转成 json 对象
                    product_id = product.get("product_id").toString() ;  // 得到 每个对象中的属性值
                    if(proudctId.equals(product_id)){
                    	 product_name = product.get("product_name").toString();
                         product_buy_num = product.get("quantity").toString();
                         
                         dOrgCdoe = product.get("org_code").toString();
                         dOrgName = product.get("org_name").toString();
                    }
                    else{
                    	System.out.println("=======还买有其它商品========");
                    	product_name = product.get("product_name").toString();
                    	continue; // 跳过其他商品
                    }
                 }
            }
            
            //大订单
            DBCollection ord_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_order");
            DBObject order = (DBObject) ord_dbConnection.findOne(new BasicDBObject("_id",orderId));
        
            System.out.println(order.toString());
            JSONObject ord_json = JSON.parseObject(order.toString()); 
            
            String totalPrice = ord_json.get("total_price").toString();
            String realPrice = ord_json.get("real_price").toString();
            String bankPoints = ord_json.get("bank_points").toString();
            String orderStatus = ord_json.get("order_status").toString();
            String paymentMethod = ord_json.get("payment_method").toString();
            
            //大订单支付
            StringBuffer billNo = new StringBuffer();
            DBCollection pay_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_payment");
            BasicDBObject pay_where = new BasicDBObject();
            pay_where.put("order_id",orderId);
            pay_where.put("payment_reason","2");
            pay_where.put("is_enable",true);
            
            List<DBObject> paymentList = pay_dbConnection.find(pay_where).toArray();
            for(DBObject payment : paymentList){
                System.out.println(payment.toString());
                JSONObject pay_json = JSON.parseObject(payment.toString()); 
                
                if(pay_json.get("payment_type").toString().equals("2")){
                    billNo.append("现金："); 
                    if(pay_json.containsKey("bill_no")){
                        billNo.append(pay_json.get("bill_no").toString());
                    }
                }
                
                if(pay_json.get("payment_type").toString().equals("3")){
                    billNo.append("银行积分："); 
                    if(pay_json.containsKey("bill_no")){
                        billNo.append(pay_json.get("bill_no").toString());
                    }
                }
            }
            
            //收货信息
            String recv_id=ord_json.get("deliver_id").toString();
            DBCollection mof_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_merchant_outlet_favorite");
            DBObject mof = (DBObject) mof_dbConnection.findOne(new BasicDBObject("recv_info.recv_id",recv_id));
            System.out.println(mof.toString());
            JSONArray recv_info_json = JSONArray.parseArray(mof.get("recv_info").toString());  
            
            String consignee = null;
            String phone = null;
            
            if(recv_info_json.size() > 0){
            	for(int i=0;i<recv_info_json.size();i++){
                    JSONObject recv = recv_info_json.getJSONObject(i);  // 遍历数组，把每一个对象转成 json 对象
                    String recvId = recv.get("recv_id").toString() ;
                    if(recv_id.equals(recvId)){
                    	consignee = recv.get("consignee").toString() ;
                    	phone = recv.get("phone").toString() ;
                    }
                 }
            }
            
            
            DBCollection ref_dbConnection = MongoDBHelper.getMongoDBHelperInstance().getCollection("cb_order_refunds");
			BasicDBObject ref_where = new BasicDBObject();
			ref_where.put("order_id",orderId);
			List<DBObject> refOrderlist = ref_dbConnection.find(ref_where).toArray();
            
            
            StringBuffer str = new StringBuffer();
            str.append(dateString+",");
            str.append(orderId+",");
            if(refOrderlist !=null && refOrderlist.size() > 0){
            	str.append("有,");
			}
            else{
            	str.append("无,");
            }
            
            str.append(orderStatus+",");
            str.append(billNo+",");
            str.append(paymentMethod+",");
            str.append(getMoney(Double.valueOf(totalPrice))+",");
            str.append(getMoney(Double.valueOf(realPrice))+",");
            str.append(getPoint(Double.valueOf(bankPoints))+",");
            str.append(consignee+",");
            str.append(phone+",");
            str.append(sOrgCode+",");
            str.append(dOrgCdoe+",");
            str.append(dOrgName+",");
            str.append(product_id+",");
            str.append(product_name+",");
            str.append(product_buy_num);
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
	
	


	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;
	
	private static double getMoney(double momey) {
        return div(momey, 1000);
    }
	
	private static double getPoint(double momey) {
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
	
	private static int mul(double v1, int v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).intValue();
	}
}
