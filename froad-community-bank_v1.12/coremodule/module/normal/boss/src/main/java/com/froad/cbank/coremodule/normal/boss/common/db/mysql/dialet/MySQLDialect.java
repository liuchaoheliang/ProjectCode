package com.froad.cbank.coremodule.normal.boss.common.db.mysql.dialet;

/**
 * @Description MySql 
 * @author FQ
 * @date 2014年9月16日 下午3:19:21
 * @version 1.0
 */

public class MySQLDialect extends Dialect {

	public boolean supportsLimitOffset(){
		return true;
	}
	
    public boolean supportsLimit() {   
        return true;   
    }  
    
    public String getLimitString(String sql, int offset,String offsetPlaceholder, int limit, String limitPlaceholder) {
        if (offset > 0) {   
        	return sql + " limit "+offsetPlaceholder+","+limitPlaceholder; 
        } else {   
            return sql + " limit "+limitPlaceholder;
        }  
	} 
}


