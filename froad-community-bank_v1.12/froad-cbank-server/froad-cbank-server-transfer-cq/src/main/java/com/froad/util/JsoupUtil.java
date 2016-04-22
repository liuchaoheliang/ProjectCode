package com.froad.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupUtil {
	public static final String lineSeparator = System.getProperty("line.separator", "\n");  
	
	
	public static void main(String[] args) {

		StringBuffer sb = new StringBuffer();

		// sb.append("<span style=\"font-size:14px;font-family:SimSun;\">1.预售期内您可下单购买，待预售期截止并成团后统一为您发送提货验证码及提货网点地址信息。</span><br />");
		// sb.append("<span style=\"font-size:14px;font-family:SimSun;\">2.提货期开始后请持验证码短信至相应银行网点进行提货。</span><br />");
		// sb.append("<span style=\"font-size:14px;font-family:SimSun;\">3.根据国家有关法规，食品类商品一经售出非质量问题无法退货，敬请谅解。</span><br />");
		// sb.append("<span style=\"font-size:14px;font-family:SimSun;\">4.每个帐号限购1瓶,若超量购买,平台会自动退款.</span><span style=\"line-height:1.5;font-size:14px;font-family:SimSun;\"></span>");
		//

		sb.append("<p>");
		sb.append("<span style=\"font-size:14px;\">无限制</span>");
		sb.append("</p>");
		sb.append("<p>");
		sb.append("<span style=\"font-size:14px;\">营业时间 </span>");
		sb.append("</p>");
		sb.append("<p>");
		sb.append("<span style=\"font-size:14px;\">9：30-14:00<br />");
		sb.append("16：30-21:30<br />");
		sb.append("</span>");
		sb.append("</p>");
		sb.append("<div style=\"white-space:nowrap;\">");
		sb.append("<br />");
		sb.append("</div>");
		sb.append("<br />");
		sb.append("<p>");
		sb.append("<br />");
		sb.append("</p>");
		
		System.out.println(getHtmlText(sb.toString()));
	}
	
	
	public static String getHtmlSpanTextByTag(String content){
		if(content.indexOf("<span") == -1){
			return content;
		}
		Document doc = Jsoup.parse(content);
		StringBuilder sb = new StringBuilder();
		for (Element element : doc.getElementsByTag("span")) {
			sb.append(element.text()).append(lineSeparator);
		}
		return sb.toString();
	}
	
	public static String getHtmlPTextByTag(String content){
		if(content.indexOf("<p") == -1){
			return content;
		}
		Document doc = Jsoup.parse(content);
		StringBuilder sb = new StringBuilder();
		for (Element element : doc.getElementsByTag("p")) {
			sb.append(element.text()).append(lineSeparator);
		}
		return sb.toString();
	}
	
	
	public static String getHtmlText(String content){
		Document doc = Jsoup.parse(content);
		return doc.text();
	}
	
	
	public static String getHtmlTextByTag(String content,String tag){
		if(content.indexOf(tag) == -1){
			return content;
		}
		Document doc = Jsoup.parse(content);
		StringBuilder sb = new StringBuilder();
		for (Element element : doc.getElementsByTag(tag)) {
			sb.append(element.text()).append(lineSeparator);
		}
		return sb.toString();
	}
}
