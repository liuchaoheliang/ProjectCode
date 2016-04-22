package com.froad.util.parserhtml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

/**
 * 类描述：根据URL抓去静态页面
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2011
 * @author: 勾君伟 goujunwei@f-road.com.cn
 * @time: May 3, 2011 5:47:21 PM
 */
public class DownLoadHTML {
	private static final Logger logger=Logger.getLogger(DownLoadHTML.class);
	static String dowlaodurl="http://www.ofcard.com/showcp/cp22.html";
    static String rooturl="d:/zc";
    static String temprooturl="d:/zc/zc.html";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		downloadhtml();
	}

	
	/**
	  * 方法描述：
	  * @param: tempurl:抓取路径  pathurl：抓取结果放置目录
	  * @return: 
	  * @version: 1.0
	  * @author: 勾君伟 goujunwei@f-road.com.cn
	  * @time: May 4, 2011 2:22:48 PM
	  */
	public static void downloadhtml(String requesturl,String tempurl,String pathurl){
		System.out.println(requesturl);
			URL url = null;
			URLConnection conn = null;
			String content = null;
			try {
				url = new URL(requesturl);
				conn = url.openConnection();
				conn.setDoOutput(true);
				InputStream in = null;
				try{
					in = url.openStream();
				} catch(FileNotFoundException e){
					logger.info("====="+requesturl+"===== 不存在！");
					return ;
				}
				
				//避免重复下载
				File file1 = new File(tempurl);
				if(file1.isFile()){
					return ;
				}
				
				content = pipe(in,tempurl,pathurl);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				init();
			} catch (IOException e) {
				e.printStackTrace();
				init();
			} catch (Exception e) {
				e.printStackTrace();
				init();
			}
//			System.out.println(content);
	}
	
	static String pipe(InputStream in,String tempurl,String pathurl) throws IOException {
		StringBuffer s = new StringBuffer();
		String rLine = null;
		BufferedReader bReader = new BufferedReader(new InputStreamReader(in,"gb2312"));
		PrintWriter pw = null;
		
		File file = new File(pathurl); // "d:/zc" 
		if (!file.isDirectory()) {
			file.mkdir();
		}

		FileOutputStream fo = new FileOutputStream(tempurl);//    "d:/zc/zc.html"
		OutputStreamWriter writer = new OutputStreamWriter(fo,"utf-8");
		pw = new PrintWriter(writer);
		while ((rLine = bReader.readLine()) != null) {
			String tmp_rLine = rLine;
			int str_len = tmp_rLine.length();
			if (str_len > 0) {
				s.append(tmp_rLine);
				pw.println(tmp_rLine);
				pw.flush();
			}
			tmp_rLine = null;
		}
		in.close();
		pw.close();
		return s.toString();
	}
	
	public static void init(){
		downloadhtml(dowlaodurl,temprooturl,rooturl);
	}
}