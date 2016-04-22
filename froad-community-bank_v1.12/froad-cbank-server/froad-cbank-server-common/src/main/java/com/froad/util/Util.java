package com.froad.util;

import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

public class Util {

	public static final String moneyFormat = "#,##0.00";
	
	
    /**
     * 获取double的小数位数
     * @param d
     * @return int
     */
    public static int getDoubleDecimalNum(double d){
        int num = 0;
        String s = String.valueOf(d);
        if(s!=null && !"".equals(s)){
            if(s.indexOf(".")!=-1){
                if(s.indexOf("E")!=-1){
                    String intTotalNum=s.substring(s.indexOf("E")+"E".length());
                    if(s.indexOf(".")!=-1){
                        int f = s.indexOf(".")+".".length()+Integer.valueOf(intTotalNum);
//                        String is = s.substring(s.indexOf(".")+".".length(), f);//整数部分
                        String ss = s.substring(f,s.indexOf("E"));//小数部分
                        num = ss.length();
                    }
                } else {
                    num = s.substring(s.indexOf(".")+1, s.length()).length();
                }
            }
        }
        return num;
    }
    
    
    /**
     * 
     * formatDecimalNum:(格式化小数).
     *
     * @author huangyihao
     * 2015年12月14日 上午10:19:48
     * @param d
     * @param format
     * @return
     *
     */
    public static String formatDecimalNum(double d, String format){
    	DecimalFormat bf = new DecimalFormat(format);
    	return bf.format(d);
    }
    
    
    /** 
     * 转义正则特殊字符 （$()*+.[]?\^{},|） 
     *  
     * @param keyword 
     * @return 
     */  
    public static String escapeExprSpecialWord(String keyword) {  
        if (StringUtils.isNotBlank(keyword)) {  
            String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };  
            for (String key : fbsArr) {  
                if (keyword.contains(key)) {  
                    keyword = keyword.replace(key, "\\" + key);  
                }  
            }  
        }  
        return keyword;  
    }  
    
}
