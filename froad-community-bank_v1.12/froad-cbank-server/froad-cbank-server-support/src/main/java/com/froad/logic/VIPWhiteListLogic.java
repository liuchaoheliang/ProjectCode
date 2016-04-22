/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
package com.froad.logic;

import com.froad.po.AddVIPWhiteListReq;
import com.froad.po.CheckVIPExistWhiteListReq;
import com.froad.po.CheckVIPExistWhiteListRes;
import com.froad.po.RemoveVIPWhiteListReq;
import com.froad.po.Result;

public interface VIPWhiteListLogic {
	/**
	 * 检查VIP是否存在白名单中
	 * @Title: checkVIPExistWhiteList 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param req
	 * @return CheckVIPExistWhiteListRes    返回类型 
	 * @throws
	 */
	public CheckVIPExistWhiteListRes checkVIPExistWhiteList(CheckVIPExistWhiteListReq req);
	
	/**
	 * 添加VIP白名单
	 * @Title: addVIPWhiteList 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param req
	 * @return
	 * @return Result    返回类型 
	 * @throws
	 */
	public Result addVIPWhiteList(AddVIPWhiteListReq req);
	
	/**
	 * 删除VIP白名单
	 * @Title: removeVIPWhiteList 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param req
	 * @return
	 * @return Result    返回类型 
	 * @throws
	 */
	public Result removeVIPWhiteList(RemoveVIPWhiteListReq req);
}
