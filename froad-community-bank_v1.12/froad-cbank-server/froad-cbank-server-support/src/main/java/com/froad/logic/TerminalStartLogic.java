package com.froad.logic;

import com.froad.po.TerminalStart;

/**
 * 
 * <p>@Title: TerminalStartLogic.java</p>
 * <p>Description: 描述 </p> 客户端启动页Logic接口类
 * @author f-road-ll  
 * @version 1.2
 * @created 2015年9月16日
 */
public interface TerminalStartLogic {

	/**
     * 客户端启动页查询
     * @param clientId 客户端id
     * @param appType 客户端类型  1-个人 2-商户
     * @param terminalType 终端类型 1-pc 2-andriod 3-ios
     * @return 启动页详情 TerminalStart
     */
	public TerminalStart getTerminalStart(String clientId, String appType,String terminalType);


}