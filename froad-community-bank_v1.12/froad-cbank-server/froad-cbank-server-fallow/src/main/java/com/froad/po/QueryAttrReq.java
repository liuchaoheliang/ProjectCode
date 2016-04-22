/**
 * Project Name:froad-cbank-server-fallow
 * File Name:QueryAttrReq.java
 * Package Name:com.froad.po
 * Date:2015年10月22日下午5:04:03
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;
/**
 * ClassName:QueryAttrReq
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月22日 下午5:04:03
 * @author   wm
 * @version  
 * @see 	 
 */
public class QueryAttrReq {
	
    /**
     * 源对象
     */
    public Origin origin;
    /**
     * 业务数据ID根据类型传对应的(商户、门店、商品)Id
     */
    public String bessId;
  
    
	public Origin getOrigin() {
		return origin;
	}
	public void setOrigin(Origin origin) {
		this.origin = origin;
	}
	public String getBessId() {
		return bessId;
	}
	public void setBessId(String bessId) {
		this.bessId = bessId;
	} 
	  
}
