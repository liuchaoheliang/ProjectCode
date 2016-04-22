package util;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import po.Response;


public class ServerXMLAnalysis {

	private static Logger log = Logger.getLogger(ServerXMLAnalysis.class);
	
	
	public static Response analysisXML(String xml){
		Response response=new Response();
		System.out.println(xml);
		List<List<String>> lists = new ArrayList<List<String>>();
		try {
			Document doc =  DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			response.setCode(root.element("code").getText());//code
			Element data = root.element("data");
			if(data != null ){
				List<Element> datas = data.elements();
				
				for (Element params : datas) {
					List<String> list = new ArrayList<String>();
					List<Element> paramsEl = params.elements();
					for (Element param : paramsEl) {
						list.add(param.getText());
					}
					lists.add(list);
				}
				response.setData(lists);
			}
			
			return response;
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
	}

}
