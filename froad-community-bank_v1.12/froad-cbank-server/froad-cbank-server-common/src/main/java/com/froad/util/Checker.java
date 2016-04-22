package com.froad.util;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Map;

/**

 * 工具类_提供基本对象、集合、数组的空值、非空值检查。

 * @author 张开

 */
public class Checker {
	    // 对于Collection、Dictionary、Map，不深入迭代，判断有没有子元素。
	    public static boolean isEmpty(Object obj) {
	        if (obj == null) {
	            return true;
	        }
	        if (obj instanceof String) {
	            return ((String) obj) == null || "".equals(obj) ? true : false;
	        }
	        if (obj instanceof Collection) {
	            return ((Collection<?>) obj).isEmpty();
	        }
	        if (obj instanceof Dictionary) {
	            return ((Dictionary<?, ?>) obj).isEmpty();
	        }
	        if (obj instanceof Map) {
	            return ((Map<?, ?>) obj).isEmpty();
	        }

	        return false;
	    }

	    // 数组里任何一个元素非空，返回false。
	    public static boolean isEmpty(Object... array) {
	        if (array == null || array.length == 0) {
	            return true;
	        }

	        for (Object o : array) {
	            if (isNotEmpty(o)) {
	                return false;
	            }
	        }

	        return true;
	    }

	    public static boolean isNotEmpty(Object obj) {
	        return !isEmpty(obj);
	    }

	    // 数组里任何一个元素为空，返回false。
	    public static boolean isNotEmpty(Object... array) {
	        if (array == null || array.length == 0) {
	            return false;
	        }

	        for (Object o : array) {
	            if (isEmpty(o)) {
	                return false;
	            }
	        }

	        return true;
	    }

	    public static boolean isNotEmpty(String... array) {
	        if (array == null || array.length == 0) {
	            return false;
	        }

	        for (String o : array) {
	            if (isEmpty(o)) {
	                return false;
	            }
	        }

	        return true;
	    }
	    
	    public static boolean isUsername(String userName){
//	    	return userName.matches("^[a-zA-Z]{1}[a-zA-Z0-9|-|_]{3,19}$");
	    	return userName.matches("^[a-zA-Z0-9]{4,16}$");
	    }
	    
	    public static boolean isPayPassword(String password){
//	    	^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$
//	    	return password.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,16})");
	    	return password.matches("^[a-zA-Z0-9_]{6,16}$");
	    }
	    
	    public static boolean isLoginPassword(String password){
	    	return password.matches("^[a-zA-Z0-9~!@#$%^&*():\";'<>?,./]{6,16}$");
	    }
	    
	    public static boolean isEmail(String email){ 	
	    	return email.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	    }
}
