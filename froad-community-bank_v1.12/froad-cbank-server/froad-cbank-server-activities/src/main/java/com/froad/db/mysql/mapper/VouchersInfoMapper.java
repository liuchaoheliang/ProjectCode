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
 * @Title: VouchersInfoMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.VouchersInfo;
import com.froad.po.VouchersUseDetails;

/**
 * 
 * <p>
 * @Title: VouchersInfoMapper.java
 * </p>
 * <p>
 * Description: 封装mybatis对MySQL映射的实体VouchersInfo的CURD操作Mapper
 * </p>
 * 
 * @author f-road
 * @version 1.0
 * @created 2015年3月17日
 */
public interface VouchersInfoMapper {

	/**
	 * 增加 VouchersInfo
	 * 
	 * @param vouchersInfo
	 * @return Long 受影响行数
	 */
	public Long addVouchersInfo(VouchersInfo vouchersInfo);

	/**
	 * 批量增加 VouchersInfo
	 * 
	 * @param vouchersInfoList
	 * @return Boolean 是否成功
	 */
	public Boolean addVouchersInfoByBatch(
			@Param("vouchersInfoList") List<VouchersInfo> vouchersInfoList);

	/**
	 * 删除 VouchersInfo
	 * 
	 * @param vouchersInfo
	 * @return Integer 受影响行数
	 */
	public Integer deleteVouchersInfo(VouchersInfo vouchersInfo);

	/**
	 * 根据id删除一个 VouchersInfo
	 * 
	 * @param id
	 * @return Integer 受影响行数
	 */
	public Integer deleteVouchersInfoById(Long id);

	/**
	 * @Title: deleteVouchersInfoByActiveId
	 * @Description: 根据activeid删除 VouchersInfo
	 * @author: shenshaocheng 2015年11月29日
	 * @modify: shenshaocheng 2015年11月29日
	 * @param id
	 * @return
	 */
	public Integer deleteVouchersInfoByActiveId(String activeId);

	/**
	 * 修改 VouchersInfo
	 * 
	 * @param vouchersInfo
	 * @return Integer 受影响行数
	 */
	public Integer updateVouchersInfo(VouchersInfo vouchersInfo);

	/**
	 * 根据id修改一个 VouchersInfo
	 * 
	 * @param vouchersInfo
	 * @return Integer 受影响行数
	 */
	public Integer updateVouchersInfoById(VouchersInfo vouchersInfo);

	/**
	 * 修改 VouchersInfo 支付中
	 * 
	 * @param vouchersInfo
	 * @return Integer 受影响行数
	 */
	public Integer updateVouchersInfoPayIng(VouchersInfo vouchersInfo);
	
	/**
	 * 修改 VouchersInfo 支付成功
	 * 
	 * @return Integer 受影响行数
	 */
	public Integer updateVouchersInfoPaySuccess(@Param("vouchersId")String vouchersId, @Param("memberCode")Long memberCode, @Param("orderId")String orderId);
	
	/**
	 * 修改 VouchersInfo 初始化
	 * 
	 * @return Integer 受影响行数
	 */
	public Integer updateVouchersInfoInit(String vouchersId);
	
	/**
	 * 根据id查询一个 VouchersInfo
	 * 
	 * @param id
	 * @return VouchersInfo 返回结果
	 */
	public VouchersInfo findVouchersInfoById(Long id);
	
	/**
	 * 根据活动id查询单个可用 VouchersInfo
	 * 
	 * @param activeId
	 * @return VouchersInfo 单个VouchersInfo
	 * @throws Exception
	 */
	public VouchersInfo findAvailableVouchersInfoByActiveId(String activeId);
	
	/**
	 * 根据活动id查询全部可用 VouchersInfo
	 * @param activeId
	 * @return
	 */
	public List<VouchersInfo> findAvailableVouchersListByActiveId(String activeId);
	
	/**
	 * 查询一个 VouchersInfo
	 * 
	 * @param vouchersInfo
	 * @return VouchersInfo 返回结果集
	 */
	public VouchersInfo findOneVouchersInfo(VouchersInfo vouchersInfo);

	/**
	 * 统计 VouchersInfo
	 * 
	 * @param vouchersInfo
	 * @return Integer 返回记录数
	 */
	public Integer countVouchersInfo(VouchersInfo vouchersInfo);

	/**
	 * 查询 VouchersInfo
	 * 
	 * @param vouchersInfo
	 * @return List<VouchersInfo> 返回结果集
	 */
	public List<VouchersInfo> findVouchersInfo(
			@Param("vouchersInfo") VouchersInfo vouchersInfo,
			@Param("order") String order);

	/**
	 * 分页查询 VouchersInfo
	 * 
	 * @param page
	 * @param vouchersInfo
	 * @return List<VouchersInfo> 返回分页查询结果集
	 */
	public List<VouchersInfo> findByPage(Page<VouchersInfo> page,
			@Param("vouchersInfo") VouchersInfo vouchersInfo);	
	
	 /**
	  * @Title: findTemporaryByPage
	  * @Description: 分页查询临时红包券码
	  * @author: shenshaocheng 2015年12月1日
	  * @modify: shenshaocheng 2015年12月1日
	  * @param page 分页信息
	  * @param vouchersInfo 券码信息
	  * @return
	 */	
	public List<VouchersInfo> findTemporaryByPage(Page<VouchersInfo> page,
			@Param("vouchersInfo") VouchersInfo vouchersInfo);

	/**
	 * 分页查询 VouchersInfo 未过期
	 * 
	 * @param page
	 * @param Long
	 *            memberCode
	 * @return List<VouchersInfo>
	 */
	List<VouchersInfo> findUnExpiredByPage(Page page,
			@Param("memberCode") Long memberCode);

	/**
	 * 分页查询 VouchersInfo 已过期
	 * 
	 * @param page
	 * @param Long
	 *            memberCode
	 * @return List<VouchersInfo>
	 */
	List<VouchersInfo> findExpiredByPage(Page page,
			@Param("memberCode") Long memberCode);

	/**
	 * 分页查询 VouchersInfo 已使用
	 * 
	 * @param page
	 * @param Long
	 *            memberCode
	 * @return List<VouchersInfo>
	 */
	List<VouchersInfo> findUseByPage(Page page,
			@Param("memberCode") Long memberCode);

	/**
	 * @Title: findRepeatVoucherList
	 * @Description: 查找已经存在的券码信息
	 * @author: shenshaocheng 2015年11月27日
	 * @modify: shenshaocheng 2015年11月27日
	 * @param vouchersIdList
	 *            券码ID
	 * @return
	 */
	List<VouchersInfo> findRepeatVoucherList(
			@Param("vouchersIdList") List<String> vouchersIdList);

	/**
	 * @Title: updateVouchersInfoByClientIdAndVouchersId
	 * @Description: 更新红包券码（订单）
	 * @author: shenshaocheng 2015年11月30日
	 * @modify: shenshaocheng 2015年11月30日
	 * @param vouchersInfo
	 * @return
	 */
	public Long updateVouchersInfoByClientIdAndVouchersId(
			VouchersInfo vouchersInfo);

	 /**
	  * @Title: findVouchersUseDetailsByPage
	  * @Description: 分页查询红包券码使用明细
	  * @author: shenshaocheng 2015年11月30日
	  * @modify: shenshaocheng 2015年11月30日
	  * @param page
	  * @param vouchersInfo
	  * @return
	 */	
	public List<VouchersUseDetails> findVouchersUseDetailsByPage(Page<VouchersUseDetails> page,
			@Param("vouchersInfo")VouchersInfo vouchersInfo);
	
	 /**
	  * @Title: findVouchersUseDetails
	  * @Description: 查询红包券码使用明细
	  * @author: shenshaocheng 2015年12月3日
	  * @modify: shenshaocheng 2015年12月3日
	  * @param vouchersInfo 查询条件
	  * @return
	 */	
	public List<VouchersUseDetails> findVouchersUseDetails(
			@Param("vouchersInfo")VouchersInfo vouchersInfo);
	
	 /**
	  * @Title: findTemporaryVouchers
	  * @Description: 查询临时劵码信息
	  * @author: shenshaocheng 2015年12月8日
	  * @modify: shenshaocheng 2015年12月8日
	  * @param vouchersInfo
	  * @return
	 */	
	public VouchersInfo findTemporaryVouchers(@Param("vouchersInfo") VouchersInfo vouchersInfo);
	
	 /**
	  * @Title: deleteTemporaryVouchersInfoByActiveId
	  * @Description: 删除临时劵码
	  * @author: shenshaocheng 2015年12月8日
	  * @modify: shenshaocheng 2015年12月8日
	  * @param activeId 临时劵码活动ID
	  * @return
	 */	
	public Long deleteTemporaryVouchersInfoByActiveId(@Param("activeId")String activeId);
	
	 /**
	  * @Title: updateVouchersInfoByVouchersId
	  * @Description: 更新代金券
	  * @author: yefeifei 2016年01月29日
	  * @modify: yefeifei 2016年01月29日
	  * @param 
	  * @return
	 */		
	public Integer updateVouchersInfoByVouchersId(VouchersInfo vouchersInfo);
	
}