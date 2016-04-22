package com.froad.cbank.coremodule.module.normal.merchant.utils;
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  

import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
  
/** 
 * @author        Der     
 * @date          2006-10-23 
 * @packeage_name regex 
 *  
 */  
public class RegexChk  
{  
  
    public static  boolean startCheck(String reg,String string)  
    {  
        boolean tem=false;  
          
        Pattern pattern = Pattern.compile(reg);  
        Matcher matcher=pattern.matcher(string);  
          
        tem=matcher.matches();  
        return tem;  
    }  
 

    
    /**
     * 正则收款帐号 
     * 重庆
     * @param url
     * @return
     */
    public static boolean checkAccountNum_C(String url)  
    {  
        //http://www.163.com  
        String reg="^[0-9]{16|18|19}$";  
          
        return startCheck(reg,url);  
    }  
    
    
    /**
     * 正则收款帐号 
     * 安徽
     * @param url
     * @return
     */
    public static boolean checkAccountNum_A(String url)  
    {  
        //http://www.163.com  
        String reg="^[0-9]{19|23}$";  
          
        return startCheck(reg,url);  
    } 
    
    /**
     * 正则收款帐号 
     * 
     * @param url
     * @return
     */
    public static boolean checkAccountNum(String url)  
    {  
        //http://www.163.com  
        String reg="^[0-9]{1,23}$";  
          
        return startCheck(reg,url);  
    }  
    
    
    public static String verifyAccountNum(String clientId,String accountNum) throws MerchantException{
    	accountNum=accountNum.replaceAll("\\s+","");//去除所有空白
		boolean bug=false;
		if(clientId.equalsIgnoreCase("anhui")){
			bug=RegexChk.checkAccountNum_A(accountNum);
		}else if(clientId.equalsIgnoreCase("chongqing")){
			bug=RegexChk.checkAccountNum_C(accountNum);
		}else{
			bug=RegexChk.checkAccountNum(accountNum);
		}
		if(!bug){
			throw new MerchantException(EnumTypes.fail.getCode(),"收款账号格式不正确");
		}else{
			return accountNum;
		}
    }
    
    
    public static void main(String[] ar){
    	String name="   kj    kjl   ";
    	name.replaceAll("\\s+","");
    	System.out.println(name);
    }
}  