package com.froad.timer.task.support;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.froad.cons.AccountCheckingConst;
import com.froad.cons.AccountCheckingConst.AccountFileName;
import com.froad.db.mongo.PaymentMongoService;
import com.froad.db.mongo.impl.PaymentMongoServiceImpl;
import com.froad.logback.LogCvt;
import com.froad.po.mongo.PaymentMongo;
import com.froad.util.SFTPUtils;

public class PaymentAccountCheckingDaily {

	PaymentMongoService paymentMongo = new PaymentMongoServiceImpl();
	
	
	AccountCheckingOfCash accountCheckingOfCash = new AccountCheckingOfCash();
	AccountCheckingOfPoint accountCheckingOfPoint = new AccountCheckingOfPoint();
	AccountCheckingOfDetails accountCheckingOfDetails = new AccountCheckingOfDetails();
	AccountCheckingOfOrder accountCheckingOfOrder = new AccountCheckingOfOrder();
	
	public static void main(String[] args) {
		System.setProperty("CONFIG_PATH", "./config");
		PaymentAccountCheckingDaily p = new PaymentAccountCheckingDaily();
		p.taskStart();
	}
	
	/**
	 * 对账系统触发
	* <p>Function: taskStart</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015年7月30日 上午10:21:23
	* @version 1.0
	 */
	public void taskStart() {
		//记录支付流水中的订单号集合
		Set<String> orderIdSet = new HashSet<String>();
		
		//查询原始数据
		List<PaymentMongo> paymentList = queryPaymentOfDaily();
		String uriCash = AccountFileName.CASH.getURI();
		this.createNewFile(uriCash);
		String uriPoint = AccountFileName.POINT.getURI();
		this.createNewFile(uriPoint);
		String uriDetails = AccountFileName.DETAILS.getURI();
		this.createNewFile(uriDetails);
		if(paymentList != null && paymentList.size() != 0){
			PaymentMongo pm = new PaymentMongo();
			LogCvt.info("已成功抓取基数据共计: " + paymentList.size()  + "条");
			for(int i=0;i<paymentList.size();i++){
				pm = paymentList.get(i);
				//orderId去重复的集合，"社区银行详细支付文件"的生成
				if(!orderIdSet.contains(pm.getOrderId())){
					orderIdSet.add(pm.getOrderId());
				}
				
				//分类并转发处理函数
				sortAndIssueDisposeFun(pm,uriCash,uriPoint);
			}
			
			//处理"社区银行详细支付文件"的生成
			accountCheckingOfOrder.doCheck(new ArrayList<String>(orderIdSet), uriDetails);
			
			AccountCheckingConst.resetClientInfo();
		}else{
			//未能成功抓取到初始数据
			LogCvt.info("未能成功抓取到初始数据");
		}
		//上传生成的文件
		SFTPUtils.uploadFile(uriCash);
		SFTPUtils.uploadFile(uriPoint);
		SFTPUtils.uploadFile(uriDetails);
	}
	
	/**
	 * 从MongoDb中获取原始数据
	* <p>Function: queryPaymentOfDaily</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015年7月30日 上午10:25:44
	* @version 1.0
	 */
	private List<PaymentMongo> queryPaymentOfDaily(){
		List<PaymentMongo> paymentList = null;
		try {
			paymentList = paymentMongo.queryPaymentOfDaily();
		} catch (Exception e) {
			LogCvt.error("定时任务: 查询支付信息失败------系统异常------", e);
		}
		return paymentList;
	}
	
	/**
	 * 对数据进行分类并转发处理函数
	* <p>Function: sortAndIssueDisposeFun</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015年7月30日 上午10:27:11
	* @version 1.0
	 */
	private void sortAndIssueDisposeFun(PaymentMongo payment,String uriCash,String uriPoint){
		try {
			if(2 == payment.getPaymentType()){ //现金类型流水
				accountCheckingOfCash.doCheck(payment,uriCash);
			}else{ //积分类型流水
				accountCheckingOfPoint.doCheck(payment,uriPoint);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建新文件，如果原来的文件存在则先删除
	 * Function : createNewFile<br/>
	 * 2015年11月2日 上午10:38:53 <br/>
	 *  
	 * @author KaiweiXiang
	 * @param path
	 */
	private void createNewFile(String path){
		File file = new File(path);
		if(file.exists()){
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
