package com.froad.util.order;

import java.text.DecimalFormat;

public class HelpUtil {
	/**
     * double 保留两位小数
     * @param d
     * @return int
     */
    public static String getDoubleDecimalString(double d){
    	DecimalFormat df = new DecimalFormat("#0.00");
        String str = df.format(d).toString();
        return str;
    }
}
