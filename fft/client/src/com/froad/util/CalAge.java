package com.froad.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.froad.AppException.AppException;
	/**
	 * 类描述：计算年龄
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2011 
	 * @author: 陈祥德 chenxiangde@f-road.com.cn
	 * @update:
	 * @time: 2011-8-6 下午02:53:33 
	 */
public class CalAge {
	public static int getAge(Date birthDay) throws AppException {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                //monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                } else {
                    //do nothing
                }
            } else {
                //monthNow>monthBirth
                age--;
            }
        } else {
            //monthNow<monthBirth
            //donothing
        }
        return age;
    }
	public static void main(String []args) throws AppException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date birth=null;
		try {
			birth = df.parse("1989-06-20");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(getAge(birth));
	}
}
