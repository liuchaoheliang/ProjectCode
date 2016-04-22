/*   
 * Copyright © 2008 F-Road All Rights Reserved.
 *  
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */

/**
 * 
 * @Title: VouchersInfoLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.po.VouchersInfo;
import com.froad.thrift.vo.active.FindVouchersDetailInfoVO;

/**
 * 
 * <p>
 * @Title: VouchersInfoLogic.java
 * </p>
 * <p>
 * Description: 封装对VouchersInfo所有业务逻辑处理的接口
 * </p>
 * 
 * @author f-road
 * @version 1.0
 * @created 2015年3月17日
 */
public interface VouchersInfoLogic {

	/**
	 * 增加 VouchersInfo
	 * 
	 * @param vouchersInfo
	 * @return Long 主键ID
	 */
	public Long addVouchersInfo(VouchersInfo vouchersInfo);

	/**
	 * 删除 VouchersInfo
	 * 
	 * @param vouchersInfo
	 * @return Boolean 是否成功
	 */
	public Boolean deleteVouchersInfo(VouchersInfo vouchersInfo);

	/**
	 * 根据id删除 VouchersInfo
	 * 
	 * @param id
	 * @return Boolean 是否成功
	 */
	public Boolean deleteVouchersInfoById(Long id);

	/**
	 * 修改 VouchersInfo
	 * 
	 * @param vouchersInfo
	 * @return Boolean 是否成功
	 */
	public Boolean updateVouchersInfo(VouchersInfo vouchersInfo);

	/**
	 * 根据id修改 VouchersInfo
	 * 
	 * @param vouchersInfo
	 * @return Boolean 是否成功
	 */
	public Boolean updateVouchersInfoById(VouchersInfo vouchersInfo);

	/**
	 * 根据id查询单个 VouchersInfo
	 * 
	 * @param id
	 * @return VouchersInfo 单个 VouchersInfo
	 */
	public VouchersInfo findVouchersInfoById(Long id);

	/**
	 * 查询一个 VouchersInfo
	 * 
	 * @param vouchersInfo
	 * @return VouchersInfo 单个 VouchersInfo
	 */
	public VouchersInfo findOneVouchersInfo(VouchersInfo vouchersInfo);

	/**
	 * 统计 VouchersInfo
	 * 
	 * @param vouchersInfo
	 * @return Integer 返回记录数 VouchersInfo
	 */
	public Integer countVouchersInfo(VouchersInfo vouchersInfo);

	/**
	 * 查询 VouchersInfo
	 * 
	 * @param vouchersInfo
	 * @return List<VouchersInfo> VouchersInfo集合
	 */
	public List<VouchersInfo> findVouchersInfo(VouchersInfo vouchersInfo);

	/**
	 * 分页查询 VouchersInfo
	 * 
	 * @param page
	 * @param vouchersInfo
	 * @return Page<VouchersInfo> VouchersInfo分页结果
	 */
	public FindVouchersDetailInfoVO findVouchersInfoByPage(
			Page<VouchersInfo> page, VouchersInfo vouchersInfo);

	/**
	 * @Title: findTemporaryVouchersInfoByPage
	 * @Description: 分页查询临时红包券码信息
	 * @author: shenshaocheng 2015年12月1日
	 * @modify: shenshaocheng 2015年12月1日
	 * @param page 分页信息
	 * @param vouchersInfo 券码信息
	 * @return
	 */
	FindVouchersDetailInfoVO findTemporaryVouchersInfoByPage(
			Page<VouchersInfo> page, VouchersInfo vouchersInfo);

	/**
	 * 根据活动id查询全部可用 VouchersInfo
	 * @param activeId
	 * @return
	 * @throws Exception
	 */
	public List<VouchersInfo> findAvailableVouchersListByActiveId(String activeId) throws Exception;
}