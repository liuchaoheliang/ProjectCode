package com.froad.test.order;

import java.util.ArrayList;
import java.util.List;

import com.froad.exceptions.FroadBusinessException;
import com.froad.ftp.ExcelWriter;
import com.froad.util.PropertiesUtil;

public class FtpTest {
	
	public void test(){
		//第一步：根据条件查询要导出的所有数据
		/*List<String> queryData = new ArrayList<String>();
		for (int i = 0; i < 500000; i++) {
			queryData.add("哈哈");
		}*/
		
		//第二步：将excel列头放入List<String>   将数据放入List<List<String>> 
		List<String> header = new ArrayList<String>();
		header.add("序号");
		header.add("订单号");
		header.add("子订单号");
		header.add("支付方式");
		header.add("支付渠道");
		header.add("订单金额");
		header.add("订单状态");
		header.add("订单类型");
		header.add("支付时间");
		header.add("创建时间");
		
		List<List<String>> data = new ArrayList<List<String>>();
		int count = 0;
		for (int i = 0; i < 100; i++) {
			List<String> record = new ArrayList<String>();
			record.add(""+(++count));
			for (int j = 1; j < 10; j++) {
				record.add("哈哈");
			}
			data.add(record);
		}
		
		ExcelWriter excelWriter = new ExcelWriter(30000);
		
		for (int i = 0; i < 1; i++) {
			try {
				excelWriter.write(header, data, "订单列表", "团购订单报表");
			} catch (FroadBusinessException e) {
				e.printStackTrace();
			}
		}
		
		String url = excelWriter.getUrl();
		
		//第三步：调用导出共通方法，返回url。url就是生成的Excel在文件服务器上的相对路径。
		/*try {
//			String url = ExcelUtil.export(header, data, "订单列表");
			String url = ExcelUtil.export(header, data, "订单列表","面对面订单报表");
			System.out.println("返回URL:" + url);
		} catch (FroadBusinessException e) {
			e.printStackTrace();
		}*/
	}
	
	public static void main(String[] args) {
		PropertiesUtil.load();
		FtpTest test = new FtpTest();
//		test.testUploadByFtp("aaa.xls");
//		test.test2();
		
		test.test();
	}
}
