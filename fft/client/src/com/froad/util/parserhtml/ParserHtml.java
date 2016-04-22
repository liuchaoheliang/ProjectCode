package com.froad.util.parserhtml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//import com.froad.client.MGames.AppException_Exception;
//import com.froad.client.MGames.MGames;

/**
 * 类描述：JAVA中解析HTML文档
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2011
 * @author: 勾君伟 goujunwei@f-road.com.cn
 * @time: May 3, 2011 5:28:46 PM
 */
public class ParserHtml {
	private static ApplicationContext ctx = new ClassPathXmlApplicationContext("client-beans.xml");
	
	private static String rooturl="http://www.ofcard.com";
//	static MGames MGAMES=(MGames) ctx.getBean("MGames");
	
	public static void main(String[] args)  {
		
//		MGAMES.addCrawlOfcard("111111", "可选项", "1");
		
		DownLoadHTML.init();
		
		File f = new File(DownLoadHTML.rooturl);
		if(!f.isDirectory()){
			f.mkdir();
		}
		
		File[] t =f.listFiles(new FileFilter(){
			public boolean accept(File pathname) {
				if(pathname.isFile()) return true;
				return false;
			}
		});
		int count=t.length;
		for(int i=0;i<count;i++){
			List<String[]> str=parseHtml(t[i].toString(),"bigListc"); //t[i].toURI().getPath()
			int num=str.get(0).length;
			for(int n=0;n<num;n++){
				DownLoadHTML.downloadhtml(str.get(0)[n], DownLoadHTML.rooturl+"/1/"+str.get(1)[n], DownLoadHTML.rooturl+"/1");
			}
			
			File f1 = new File(DownLoadHTML.rooturl+"/1");
			File[] t1 =f1.listFiles(new FileFilter(){
				public boolean accept(File pathname) {
					if(pathname.isFile()) return true;
					return false;
				}
			});
			int t1count=t1.length;
			for(int q=0;q<t1count;q++){
				List<String> strt1=parseHtml1(t1[q].toString());
				if(strt1==null){
					System.out.println(t1[q].toString());
					continue;
				}
				String hrefpath = null;
				for   (Iterator   it   =   strt1.iterator();   it.hasNext();){
					String url = (String)it.next();
					hrefpath = rooturl + "/card/" +  url;
					DownLoadHTML.downloadhtml(hrefpath, DownLoadHTML.rooturl+"/2/"+url, DownLoadHTML.rooturl+"/2");
				} 
			}
			
			File f2 = new File(DownLoadHTML.rooturl+"/2");
			File[] t2 =f2.listFiles(new FileFilter(){
				public boolean accept(File pathname) {
					if(pathname.isFile()) return true;
					return false;
				}
			});
			int t2count=t2.length;
			for(int q=0;q<t2count;q++){
				List<String[]> strt1=parseHtml2(t2[q].toString());
//				DownLoadHTML.downloadhtml(strt1.get(0)[0], DownLoadHTML.rooturl+"/2/"+strt1.get(1)[0], DownLoadHTML.rooturl+"/2");
				int a=9;
//				mgames.addCrawlOfcard(gameid, columnName,columnType);
			}
			
		}
	}
	
	/**
	  * 方法描述：
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 勾君伟 goujunwei@f-road.com.cn
	  * @time: May 4, 2011 2:56:13 PM
	  */
	public static List<String[]> parseHtml(String path,String value) {
		List<String[]> str = new ArrayList<String[]>();
		String[] str1 = null;
		String[] str2 = null;
		try {
			StringBuffer s = new StringBuffer();
			String rLine = null;
			FileInputStream fo = new FileInputStream(path);

			BufferedReader bReader = new BufferedReader(new InputStreamReader(
					fo));
			while ((rLine = bReader.readLine()) != null) {
				String tmp_rLine = rLine;
				int str_len = tmp_rLine.length();
				if (str_len > 0) {
					s.append(tmp_rLine);
				}
				tmp_rLine = null;
			}
			bReader.close();
			fo.close();

			Parser parser = Parser.createParser(s.toString(), "UTF-8");
			String temp = "";
			try {
				NodeFilter filter1 = new HasAttributeFilter("id", value);
				NodeList nodes = parser.extractAllNodesThatMatch(filter1);

				if (nodes != null) {
					for (int i = 0; i < nodes.size(); i++) {
						Node textnode = (Node) nodes.elementAt(i);
						temp = textnode.toHtml();
					}
				}

				Parser parser1 = Parser.createParser(temp, "UTF-8");
				// 通过过滤器过滤出<A>标签
				NodeList nodeList = parser1
						.extractAllNodesThatMatch(new NodeFilter() {
							// 实现该方法,用以过滤标签
							public boolean accept(Node node) {
								if (node instanceof LinkTag)
									return true;
								return false;
							}
						});
				
				int count=nodeList.size();
				str1=new String[count];
				str2=new String[count];

				for (int i = 0; i < count; i++) {
					LinkTag n = (LinkTag) nodeList.elementAt(i);
					System.out.print(n.getStringText() + " ==>> ");
					System.out.println(n.extractLink());
					String tempstr=n.extractLink().split("/")[2];
					str1[i]=rooturl+n.extractLink();
					str2[i]=tempstr;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		str.add(0, str1);
		str.add(1, str2);
		return str;
	}
	
	/**
	  * 方法描述：
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 勾君伟 goujunwei@f-road.com.cn
	  * @time: May 4, 2011 2:56:13 PM
	  */
	/*public static List<String[]> parseHtml1(String path) {
		List<String[]> str = new ArrayList<String[]>();
		String[] str1 = null;
		String[] str2 = null;
		String[] str3 = null;
		try {
			StringBuffer s = new StringBuffer();
			String rLine = null;
			FileInputStream fo = new FileInputStream(path);

			BufferedReader bReader = new BufferedReader(new InputStreamReader(
					fo));
			while ((rLine = bReader.readLine()) != null) {
				String tmp_rLine = rLine;
				int str_len = tmp_rLine.length();
				if (str_len > 0) {
					s.append(tmp_rLine);
				}
				tmp_rLine = null;
			}
			bReader.close();
			fo.close();

			Parser parser = Parser.createParser(s.toString(), "UTF-8");
			String temp = "";
			try {
				NodeFilter filter1 = new HasAttributeFilter("class", "cz");
				NodeList nodes = parser.extractAllNodesThatMatch(filter1);

				if (nodes.size()>0) {
//					for (int i = 0; i < nodes.size(); i++) {
						Node textnode = (Node) nodes.elementAt(0);
						temp = textnode.toHtml();
//					}
				}else{
					return null;
				}

				Parser parser1 = Parser.createParser(temp, "UTF-8");
				// 通过过滤器过滤出<A>标签
				NodeList nodeList = parser1
						.extractAllNodesThatMatch(new NodeFilter() {
							// 实现该方法,用以过滤标签
							public boolean accept(Node node) {
								if (node instanceof LinkTag)
									return true;
								return false;
							}
						});
				
				int count=nodeList.size();
				str1=new String[count];
				str2=new String[count];
				str3=new String[count];

				for (int i = 0; i < count; i++) {
					LinkTag n = (LinkTag) nodeList.elementAt(i);
					System.out.print(n.getStringText() + " ==>> ");
					System.out.println(n.extractLink());
					if(n.extractLink().contains(".jsp?")){
						String tempstr=n.extractLink().split("=")[1];
						str1[i]=rooturl+"/"+n.extractLink();
						str2[i]=tempstr;
						str3[i]=n.extractLink().split("\\?")[1].split("=")[1]; //tempstr.split("\\.")[0];
					}else{
						String tempstr=n.extractLink().split("/")[2];
						str1[i]=rooturl+n.extractLink();
						str2[i]=tempstr;
						str3[i]=tempstr.split("\\.")[0];
					}
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		str.add(0, str1);
		str.add(1, str2);
		return str;
	}*/
	public static List<String> parseHtml1(String path) {
		String regStr = path.substring(8, 12);
		List<String> str = new ArrayList<String>();
		
		
		try {
			StringBuffer s = new StringBuffer();
			String rLine = null;
			FileInputStream fo = new FileInputStream(path);

			BufferedReader bReader = new BufferedReader(new InputStreamReader(
					fo));
			while ((rLine = bReader.readLine()) != null) {
				String tmp_rLine = rLine;
				int str_len = tmp_rLine.length();
				if (str_len > 0) {
					s.append(tmp_rLine);
				}
				tmp_rLine = null;
			}
			bReader.close();
			fo.close();

			Parser parser = Parser.createParser(s.toString(), "UTF-8");
			String temp = "";
			
				NodeFilter filter1 = new HasAttributeFilter("class", "cz");
				NodeList nodes = parser.extractAllNodesThatMatch(filter1);

				if (nodes.size()>0) {
					for (int i = 0; i < nodes.size(); i++) {
						Node textnode = (Node) nodes.elementAt(i);
						temp = textnode.toHtml();
						Parser parser1 = Parser.createParser(temp, "UTF-8");
						// 通过过滤器过滤出<A>标签
						NodeList nodeList = parser1
								.extractAllNodesThatMatch(new NodeFilter() {
									// 实现该方法,用以过滤标签
									public boolean accept(Node node) {
										if (node instanceof LinkTag)
											return true;
										return false;
									}
								});
						for (int j = 0; j < nodeList.size(); j++) {
							LinkTag n = (LinkTag) nodeList.elementAt(j);
							String[] href = n.extractLink().split("/");
							
							if(href[href.length - 1].startsWith(regStr))
								str.add(href[href.length - 1]);
						}
					}
				}else{
					return null;
				}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return str;
	}
	
	
	
	public static List<String[]> parseHtml2(String path) {
		List<String[]> str = new ArrayList<String[]>();
		String[] str1 = null;
		String[] str2 = null;
		try {
			StringBuffer s = new StringBuffer();
			String rLine = null;
			FileInputStream fo = new FileInputStream(path);

			BufferedReader bReader = new BufferedReader(new InputStreamReader(
					fo));
			while ((rLine = bReader.readLine()) != null) {
				String tmp_rLine = rLine;
				int str_len = tmp_rLine.length();
				if (str_len > 0) {
					s.append(tmp_rLine);
				}
				tmp_rLine = null;
			}
			bReader.close();
			fo.close();

			Parser parser = Parser.createParser(s.toString(), "UTF-8");
			String temp = "";
			try {
				
				NodeFilter filter1 = new HasAttributeFilter("class", "NamWid");
				NodeList nodes = parser.extractAllNodesThatMatch(filter1);
				int counts=nodes.size();
				if (nodes != null) {
					for (int i = counts-1; i < counts; i--) {
						Node textnode = (Node) nodes.elementAt(i);
						boolean flag=textnode.getParent().toHtml().contains("style=\"display:none;\"");//包含style说明可能是隐藏域
						String planstr=textnode.toPlainTextString(); //获取积分：
						if("".equals(planstr)||"&nbsp;".equals(planstr)||planstr.contains("提示")
								||planstr.contains("注意")||planstr.contains("选填项：")){
							if(flag){
								System.out.println(path+"===================");
							}
							continue;
						}
						if(planstr.equals("获取积分：")){
							break;
						}
						//MGAMES.addCrawlOfcard(path.split("\\\\")[3].split("\\.")[0], planstr, i);
					}
				}

				Parser parser1 = Parser.createParser(temp, "UTF-8");
				// 通过过滤器过滤出<A>标签
				NodeList nodeList = parser1
						.extractAllNodesThatMatch(new NodeFilter() {
							// 实现该方法,用以过滤标签
							public boolean accept(Node node) {
								if (node instanceof LinkTag)
									return true;
								return false;
							}
						});
				
				int count=nodeList.size();
				str1=new String[count];
				str2=new String[count];

				for (int i = 0; i < count; i++) {
					LinkTag n = (LinkTag) nodeList.elementAt(i);
					System.out.print(n.getStringText() + " ==>> ");
					System.out.println(n.extractLink());
					String tempstr=n.extractLink().split("/")[2];
					str1[i]=rooturl+n.extractLink();
					str2[i]=tempstr;
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		str.add(0, str1);
		str.add(1, str2);
		return str;
	}
}