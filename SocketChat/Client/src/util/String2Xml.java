package util;

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
		String tmpElement = "<" + elementName + "><![CDATA[" + value + "]]></"
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

	}
}
