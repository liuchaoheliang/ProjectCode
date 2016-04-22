package team.core.fn.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import team.core.constant.TimeConst;



public class TimeFun {

		/**
		 * 获取SimpleDateFormat
		 * @param timeType 格式化日期格式
		 * @return
		 * @author Zxy
		 */
		public static SimpleDateFormat getTimeFormat(String timeType){
			return new SimpleDateFormat(timeType);
		}
		
		public static String getNowTimeType(){
			return getTimeFormat(TimeConst.TIME_TYPE).format(new Date());
		}
		public static String getNowTimeType(String TimeType){
			return getTimeFormat(TimeType).format(new Date());
		}
		/**
		 * Date类型转String
		 * @param timeType
		 * @param time Date类型日期
		 * @return
		 * @author Zxy
		 */
		public static String dateToString(String timeType,Date time){
			return getTimeFormat(timeType).format(time);
		}
		/**
		 * String类型的时间格式转Date类型时间
		 * @param timeType
		 * @param time
		 * @return
		 * @author Zxy
		 */
		public static Date stringToDate(String timeType,String time){
			Date date = null; 
			try {
				date = getTimeFormat(timeType).parse(time);
			} catch (ParseException e) {
			}
			return date;
		}
		
		/**
		 * 判断时间先后
		 * @param strTime
		 * @param timeType
		 * @return
		 */
		public static boolean isTimeBefor(String earlyTime,String lastTime,String timeType){
			Date compareTimeEarly = null;
			Date compareTimeLast = null;
			try {
				compareTimeEarly = getTimeFormat(timeType).parse(earlyTime);
				compareTimeLast = getTimeFormat(timeType).parse(lastTime);
			} catch (ParseException e) {
				return false;
			}
			return compareTimeLast.before(compareTimeEarly);
		}
		/**
		 * 将时间time往后加delayDays天
		 * @param time
		 * @param delayDays
		 * @param timeType
		 * @return
		 * @author Zxy
		 */
		public static String addHours(Date time, int delayHour,String timeType) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(time);
			calendar.add(Calendar.HOUR, delayHour);
			return dateToString(timeType,calendar.getTime());
		}
		
		public static String addMinutes(Date time, int delayMinute,String timeType) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(time);
			calendar.add(Calendar.MINUTE, delayMinute);
			return dateToString(timeType,calendar.getTime());
		}
		
		public static int apartMinuteTime(String earlyTime,String oldTime){
			int apart = 0;
			SimpleDateFormat sdf = getTimeFormat(TimeConst.TIME_TYPE);
			try {
				Date early = sdf.parse(earlyTime);
				Date old = sdf.parse(oldTime);
				apart = (int)((early.getTime() - old.getTime()) / 1000 / 60);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return apart;
		}
}
