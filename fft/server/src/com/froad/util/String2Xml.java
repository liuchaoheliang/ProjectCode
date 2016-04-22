package com.froad.util;

import com.froad.CB.common.SysCommand;

/**
 * 类描述：工具类组xml报文
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2012
 * @author: meicy meiwenxiang@f-road.com.cn
 * @time: 2012-3-7 上午9:55:14
 */
public class String2Xml {

	protected StringBuilder sb;
	protected String rootName;// 根元素
	protected String elementName;//标签元素

	/**
	 * 类的构造方法 创建一个新的实例 String2Xml.
	 * 
	 * @param
	 * @param rootName
	 */
	public String2Xml(String rootName) {
		sb = new StringBuilder();
		this.rootName = rootName;
		sb.append("<" + rootName + ">");
	}

	/**
	 * 方法描述：附加标签对头部<elementName>
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: meicy meiwenxiang@f-road.com.cn
	 * @time: 2012-3-7 上午9:55:56
	 */
	public void addElementName(String elementName) {
		String tmpElement = "<" + elementName + ">";
		if (elementName == null) {
			elementName = "";
		}
		else if (this.elementName != null) {
			tmpElement ="</" + this.elementName + ">"+tmpElement;
			this.elementName=elementName;
			sb.append(tmpElement);
		}
		else{
		
		this.elementName=elementName;
//		this.elementName=null;
		sb.append(tmpElement);
		}
	}
	/**
	 * 方法描述：附加标签对
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: meicy meiwenxiang@f-road.com.cn
	 * @time: 2012-3-7 上午9:55:56
	 */
	public void endElementName(String elementName) {

		if (elementName == null) {
			elementName = "";
		}else{
		String tmpElement = "</" + elementName + ">";
		sb.append(tmpElement);
		}
	}
	/**
	 * 方法描述：附加标签对
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: meicy meiwenxiang@f-road.com.cn
	 * @time: 2012-3-7 上午9:55:56
	 */
	public void append(String elementName, String value) {

		if (value == null) {
			value = "";
		}
		String tmpElement = "<" + elementName + ">" + value + "</"
				+ elementName + ">";
		sb.append(tmpElement);
	}

	/**
	 * 方法描述：加上结束标签
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: meicy meiwenxiang@f-road.com.cn
	 * @time: 2012-3-7 上午9:56:13
	 */
	public String toXMLString() {
		String tmpElement=null;
		if (this.elementName != null) {
			tmpElement ="</" + this.elementName + ">";
		}
		if(tmpElement==null||"".equals(tmpElement)){
			sb.append("</" + rootName + ">");
		}else{
			sb.append(tmpElement+"</" + rootName + ">");
		}
		return sb.toString();
	}

	/**
	 * 方法描述：测试
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: meicy meiwenxiang@f-road.com.cn
	 * @time: 2012-3-7 上午9:55:07
	 */
	public static void main(String[] args) {

		// for (int i = 0; i < 10; i++) {
		// System.out.println(i);
		// if (i % 2 == 0) {
		//
		// String2Xml str2xml = new String2Xml("root");
		//
		// str2xml.append("wo", "123");
		// System.out.println(str2xml.toXMLString());
		// } else {
		// String2Xml str2xml = new String2Xml("r2oot");
		//
		// str2xml.append("w1", "1223");
		// System.out.println(str2xml.toXMLString());
		// }
		// }

//		String2Xml str2xml = new String2Xml("root");

		
		
        //str2xml.addElementName("sub");
//		str2xml.append("wo", "2");
//
//        str2xml.addElementName("cub");
//		str2xml.append("wo", "3");
//
//        str2xml.addElementName("tub");
//		str2xml.append("wo", "3");
//
//		str2xml.append("wo", "3");
		String2Xml bodyXml = new String2Xml("requestFroadApi");
		bodyXml.addElementName("refund");
		bodyXml.append("refundOrderID", "12345");
		bodyXml.append("refundAmount", "70");
		bodyXml.append("refundType", "01");
		bodyXml.addElementName("notification");
		bodyXml.append("noticeUrl", SysCommand.REFUND_NOTICE_URL);
		bodyXml.addElementName("system");
		bodyXml.append("reqID", "201205251555");

		bodyXml.append("signMsg", "gfdagfadgfdgfagfagfagfagdaafdsafdsafsdafas");
		System.out.println(bodyXml.toXMLString());
	}
}
