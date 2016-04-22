package com.froad.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class DateUtil {
	private static Logger logger = Logger.getLogger(DateUtil.class);
	public DateUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * ����}�����ڲ�, beginDateС��endDateʱ��������
	 * 
	 * @param beginDate
	 *            ��ʼ����
	 * @param endDate
	 *            ��������
	 * @return days }�������������
	 */
	public static long getDateDiff(Date beginDate, Date endDate) {
		long days = 0;
		days = endDate.getTime() - beginDate.getTime();
		days = days / (1000 * 60 * 60 * 24);
		return days;
	}

	/**
	 * ����}�����ڲ�,Ĭ�ϸ�ʽyyyyMMdd, beginDateС��endDateʱ��������
	 * 
	 * @param beginDate
	 *            ��ʼ����
	 * @param endDate
	 *            ��������
	 * @return days }�������������
	 */
	public static long getDateDiff(String beginDate, String endDate)
			throws Exception {

		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		sf.setLenient(false);// �ϸ��������
		Date d1 = sf.parse(beginDate);
		Date d2 = sf.parse(endDate);

		long beginTime = d1.getTime();
		long endTime = d2.getTime();
		long days = (long) ((endTime - beginTime) / (1000 * 60 * 60 * 24));
		return days;
	}

	/**
	 * ����}�����ڲ�,����ָ����ʽ, beginDateС��endDateʱ��������
	 * 
	 * @param beginDate
	 *            ��ʼ����
	 * @param endDate
	 *            ��������
	 * @param format
	 *            ���ڸ�ʽ
	 * @return days �����������
	 * @throws Exception
	 */

	public static long getDateDiff(String beginDate, String endDate,
			String format) throws Exception {

		SimpleDateFormat sf = new SimpleDateFormat(format);
		sf.setLenient(false);// �ϸ��������
		Date d1 = sf.parse(beginDate);
		Date d2 = sf.parse(endDate);

		long beginTime = d1.getTime();
		long endTime = d2.getTime();
		long days = (long) ((endTime - beginTime) / (1000 * 60 * 60 * 24));
		return days;
	}

	/**
	 * ���ڸ�ʽת��
	 * @param date
	 			��Ҫת��������
	 * @param srcFormat
	 			Ϊԭ4�����ڸ�ʽ
	 * @param format
	 			ת��������ڸ�ʽ
	 * @return
	 * @throws Exception
	 */
	public static String convertDateFormat(String date, String srcFormat,
			String format) throws Exception {
		SimpleDateFormat srcSdf = new SimpleDateFormat(srcFormat);
		// ��ԭ����ת���ɺ���
		long srcDateTimes = srcSdf.parse(date).getTime();
		Date d = new Date();
		d.setTime(srcDateTimes);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(d).toString();
	}
	
	
	  /**
     * ���ڼ�ȥXСʱ�������µ�����
     * @param date
     * @param hours
     * @param format
     * @return
     * @throws Exception
     */
    public static String getDateBySubHours(String date, int hours, String format)
		throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		// ��ԭ����ת���ɺ���
		long curDateTimes = sdf.parse(date).getTime();
		// ����Ҫ��ȥ������ת���ɺ���
		long daysTimes = Long.parseLong(String.valueOf(hours)) * 3600 * 1000;
		// �Ժ������������ת������Ҫ�ĸ�ʽ
		long result = (curDateTimes - daysTimes);
		Date d = new Date();
		d.setTime(result);
		return sdf.format(d).toString();
	}
    
    /**
	 * 得到当前时间yyyyMMddhhmmss
	 * @return
	 */
	public static String currentTimeToString(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String date = sdf.format(new Date());
		return date;
	}
	
	  
	  
	/**
	  * 方法描述：得到几天前的时间 
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2012-4-13 上午11:31:55
	  */
	public static Date getDateBefore(Date d,int day){    
	    Calendar now =Calendar.getInstance();    
	    now.setTime(d);    
	    now.set(Calendar.DATE,now.get(Calendar.DATE)-day);    
	    return now.getTime();    
	   }    

	
	/**
	 *  格式时间  yyyy-MM-dd|HH:mm:ss
	 * @param date
	 * @return
	 */
    public  static String formatDate2Str(Date date) {  
    	if (date == null) {   
            return null;   
        }   
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");   
        return sdf.format(date);   
    } 

    /**
     * 计算时间差
     * @param startTime
     * @param endTime
     * @param format
     */
	public static String dateDiff(String startTime, String endTime, String format) {  
        // 按照传入的格式生成一个simpledateformate对象  
        SimpleDateFormat sd = new SimpleDateFormat(format);  
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数  
        long nh = 1000 * 60 * 60;// 一小时的毫秒数  
        long nm = 1000 * 60;// 一分钟的毫秒数  
        long ns = 1000;// 一秒钟的毫秒数  
        long diff;  
        try {  
            // 获得两个时间的毫秒时间差异  
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();  
            long day = diff / nd;// 计算差多少天  
            long hour = diff % nd / nh;// 计算差多少小时  
            long min = diff % nd % nh / nm;// 计算差多少分钟  
            long sec = diff % nd % nh % nm / ns;// 计算差多少秒  
            // 输出结果  
            logger.info("时间相差：" + day + "天" + hour + "小时" + min + "分钟"  
                    + sec + "秒"); 
            
            return Long.toString(day).replace("-", "")+"-"+Long.toString(hour).replace("-", "")+"-"+Long.toString(min).replace("-", "")+"-"+Long.toString(sec).replace("-", "");
        } catch (Exception e) {  
            e.printStackTrace(); 
            return null;
        }  
}  
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		long days = 0;
		long days2 = 0;
		try {
			days = getDateDiff("20080201", "20090101");
			days2 = getDateDiff("2008-02-01", "2009-01-01", "yy-MM-dd");
			System.out.println("daysDiff>>>" + days);
			System.out.println("daysDiff>>>" + days2);
		} catch (Exception e) {
			logger.error("异常", e);
		}

		System.out.println(dateDiff("2013-01-15|15:00:11","2013-01-15|15:01:00","yyyy-MM-dd|HH:mm:ss"));
		String a="a-b-c";
		String[] b=a.split("-");
		System.out.println(b[0]+"|"+b[1]+"|"+b[2]);
	}

}
