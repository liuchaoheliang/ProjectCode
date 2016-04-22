package com.froad.util;

import java.sql.Timestamp;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;



	/**
	 * 类描述：JAXB类型转换器
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: meicy meiwenxiang@f-road.com.cn
	 * @time: Oct 8, 2012 4:09:07 PM 
	 */
public class TimestampAdapter extends XmlAdapter<Date, Timestamp> {

	public Date marshal(Timestamp t) {
		return new Date(t.getTime());
	}

	public Timestamp unmarshal(Date d) {
		return new Timestamp(d.getTime());
	}

}