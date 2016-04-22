/**
 * Project Name : froad-cbank-server-common
 * File Name : Snippet.java
 * Package Name : com.froad.log.vo
 * Date : 2015年11月25日下午3:54:31
 * Copyright (c) 2015, i2Finance Software All Rights Reserved
 *
 */
package com.froad.log.vo;

/**
 * 结算的日志
 * Description : TODO<br/>
 * Project Name : froad-cbank-server-common<br/>
 * File Name : SettlementLog.java<br/>
 * 
 * Date : 2015年11月25日 下午3:55:07 <br/> 
 * @author KaiweiXiang
 * @version
 */
public class SettlementLog  extends Head {
	private SettlementLogDetail data;

	public SettlementLogDetail getData() {
		return data;
	}

	public void setData(SettlementLogDetail data) {
		this.data = data;
	}
	
}
