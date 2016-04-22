package com.froad.test.order;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.BooleanUtils;

import com.froad.util.Arith;

public class MyTest {

	public static void main(String[] args) {
		MyTest test = new MyTest();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
		Long time = 1435580186476L;
		Long time1 = 1431187200000L;
		Long time2 = 1432051200000L;
		System.out.println("LONG数据     "+time+"   转为时间为：    " + df.format(new Date(time)));
		System.out.println(BooleanUtils.toBoolean("1"));
		
		
		String str="2015-06-17 16:22:00";
		Date strDate = null;
		try {
			strDate = df.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(str + "    转为Long型：       " + strDate.getTime());
		
		System.out.println(Arith.round(Arith.div(190000, 1000), 1));
		System.out.println(190000/1000);
		
		
		// TODO Auto-generated method stub
		/*Long date = 1430230634400L;
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:sss");
		Date d = new Date(date);
		System.out.println(sd.format(d));*/
		
		
		Long endTime = Long.valueOf("1433570114013");
		SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");
		String endDateYYMMdd;
		endDateYYMMdd = String.valueOf(df1.format(new Date(endTime)));
		System.out.println("endDateYYMMdd:"+endDateYYMMdd);
		
		String count = "-1";
		System.out.println(Integer.valueOf(count));
		
		
		double one = 1.919;
		System.out.println(new BigDecimal(one).intValue());
		System.out.println(new BigDecimal(one).divide(new BigDecimal(1), 2, BigDecimal.ROUND_DOWN).doubleValue());
		
	}

}
