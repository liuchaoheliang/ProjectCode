package com.froad.util;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;


/**
 * 生成或者追加文件
* <p>Function: IOTXTAppend</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2015年7月30日 下午5:09:44
* @version 1.0
 */
public class IOTXTAppend {
	
	private static void fileAppend(String fileURI, String content) {
		try {
			content = content + "\n";
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(fileURI, true);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成或者追加文件
	* <p>Function: appendData</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015年7月30日 下午5:10:20
	* @version 1.0
	* @param fileURI 文件uri
	* @param datas 内容
	 */
	public static void appendData(String fileURI,String[] datas){
		StringBuffer sb = new StringBuffer();
		if(datas == null || datas.length == 0){
			return;
		}
		int len = datas.length;
		for (int i = 0 ; i < len ; i ++) {
			sb.append(StringUtils.isEmpty(datas[i]) ? "" : datas[i]);
			if(i != len - 1){
				sb.append(",");
			}
		}
		fileAppend(fileURI, sb.toString());
	}
}