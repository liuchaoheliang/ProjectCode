package com.froad.fft.util;

import java.util.List;
import java.util.Map;


/**
 * *******************************************************
 *<p> 工程: fft-server </p>
 *<p> 类名: NullValueCheckUtil.java </p>
 *<p> 描述: *-- <b>检查常用类型的数据是否为空</b> --* </p>
 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
 *<p> 时间: 2014年1月24日 下午2:05:30 </p>
 ********************************************************
 */
public class NullValueCheckUtil {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>字符串是否为空</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月24日 下午2:07:15 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public static boolean isStrEmpty(String val){
		if(val == null || val.length() == 0){
			return true;		
		}
		return false;
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>字符串数组是否为空</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月24日 下午2:08:45 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public static boolean isStrArrayEmpty(String[] val){
		if(val == null || val.length == 0){
			return true;
		}
		return false;
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>List集合是否为空</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月24日 下午2:11:02 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public static boolean isListArrayEmpty(List<?> list){
		if(list==null||list.size()==0){
			return true;
		}
		return false;
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>Map集合是否为空</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月24日 下午2:12:03 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public static boolean isMapArrayEmpty(Map<?, ?> map){
		if(map==null||map.isEmpty()){
			return true;
		}
		return false;
	}
}
