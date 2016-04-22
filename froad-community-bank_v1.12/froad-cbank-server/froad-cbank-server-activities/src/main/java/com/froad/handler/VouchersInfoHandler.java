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
 * @Title: VouchersInfoHandler.java
 * @Package com.froad.handler
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.po.VouchersInfo;
import com.froad.po.VouchersUseDetails;

/**
 * 
 * <p>
 * @Title: VouchersInfoHandler.java
 * </p>
 * <p>
 * Description: 封装对MySQL对应的实体VouchersInfo的CURD操作接口
 * </p>
 * 
 * @author f-road
 * @version 1.0
 * @created 2015年3月17日
 */
public interface VouchersInfoHandler {

	/**
	 * 增加 VouchersInfo
	 * 
	 * @param vouchersInfo
	 * @return Long 主键ID
	 * @throws Exception
	 */
	public Long addVouchersInfo(VouchersInfo vouchersInfo) throws Exception;

	/**
	 * 增加 VouchersInfo
	 * 
	 * @param sqlSession
	 * @param vouchersInfo
	 * @return Long 主键ID
	 * @throws Exception
	 */
	public Long addVouchersInfo(SqlSession sqlSession, VouchersInfo vouchersInfo)
			throws Exception;

	/**
	 * 删除 VouchersInfo
	 * 
	 * @param vouchersInfo
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	public Integer deleteVouchersInfo(VouchersInfo vouchersInfo)
			throws Exception;

	/**
	 * 删除 VouchersInfo
	 * 
	 * @param sqlSession
	 * @param vouchersInfo
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	public Integer deleteVouchersInfo(SqlSession sqlSession,
			VouchersInfo vouchersInfo) throws Exception;

	/**
	 * 根据id删除 VouchersInfo
	 * 
	 * @param id
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	public Integer deleteVouchersInfoById(Long id) throws Exception;
	
	 /**
	  * @Title: deleteVouchersInfoByActiveId
	  * @Description: 根据activeId删除券码信息
	  * @author: shenshaocheng 2015年12月1日
	  * @modify: shenshaocheng 2015年12月1日
	  * @param activeId
	  * @return
	 */	
	public Integer deleteVouchersInfoByActiveId(String activeId);

	/**
	 * 根据id删除 VouchersInfo
	 * 
	 * @param id
	 * @param sqlSession
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	public Integer deleteVouchersInfoById(SqlSession sqlSession, Long id)
			throws Exception;

	/**
	 * 修改 VouchersInfo
	 * 
	 * @param vouchersInfo
	 * @return Boolean 受影响行数
	 * @throws Exception
	 */
	public Integer updateVouchersInfo(VouchersInfo vouchersInfo)
			throws Exception;

	/**
	 * 修改 VouchersInfo
	 * 
	 * @param sqlSession
	 * @param vouchersInfo
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	public Integer updateVouchersInfo(SqlSession sqlSession,
			VouchersInfo vouchersInfo) throws Exception;

	/**
	 * 修改 VouchersInfo 支付中
	 * <br>
	 * 批量
	 * @param sqlSession
	 * @param List<VouchersInfo>
	 * @return 是否成功
	 * @throws Exception
	 */
	public Boolean updateVouchersInfoPayIngBatch(List<VouchersInfo> vouchersInfoList) throws Exception;
	
	/**
	 * 修改 VouchersInfo 支付成功
	 * <br>
	 * 批量
	 * @param sqlSession
	 * @param List<vouchersId>
	 * @return 是否成功
	 * @throws Exception
	 */
	public Boolean updateVouchersInfoPaySuccessBatch(List<String> vouchersIdList, Long memberCode, String orderId) throws Exception;
	
	/**
	 * 修改 VouchersInfo 初始化
	 * <br>
	 * 批量
	 * @param sqlSession
	 * @param List<vouchersId>
	 * @return 是否成功
	 * @throws Exception
	 */
	public Boolean updateVouchersInfoInitBatch(List<String> vouchersIdList) throws Exception;
	
	/**
	 * 根据id修改 VouchersInfo
	 * 
	 * @param VouchersInfo
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	public Integer updateVouchersInfoById(VouchersInfo vouchersInfo)
			throws Exception;

	/**
	 * 根据id修改 VouchersInfo
	 * 
	 * @param sqlSession
	 * @param vouchersInfo
	 * @return Integer 受影响行数
	 * @throws Exception
	 */
	public Integer updateVouchersInfoById(SqlSession sqlSession,
			VouchersInfo vouchersInfo) throws Exception;

	/**
	 * 根据id查询单个 VouchersInfo
	 * 
	 * @param id
	 * @return VouchersInfo 单个VouchersInfo
	 * @throws Exception
	 */
	public VouchersInfo findVouchersInfoById(Long id) throws Exception;
	
	/**
	 * 根据活动id查询单个可用 VouchersInfo
	 * 
	 * @param activeId
	 * @return VouchersInfo 单个VouchersInfo
	 * @throws Exception
	 */
	public VouchersInfo findAvailableVouchersInfoByActiveId(String activeId) throws Exception;

	/**
	 * 根据活动id查询全部可用 VouchersInfo
	 * @param activeId
	 * @return
	 * @throws Exception
	 */
	public List<VouchersInfo> findAvailableVouchersListByActiveId(String activeId) throws Exception;
	/**
	 * 查询一个 VouchersInfo
	 * 
	 * @param vouchersInfo
	 * @return VouchersInfo 单个VouchersInfo
	 * @throws Exception
	 */
	public VouchersInfo findOneVouchersInfo(VouchersInfo vouchersInfo)
			throws Exception;

	/**
	 * 查询统计 VouchersInfo
	 * 
	 * @param vouchersInfo
	 * @return Integer 返回记录数VouchersInfo
	 * @throws Exception
	 */
	public Integer countVouchersInfo(VouchersInfo vouchersInfo)
			throws Exception;

	/**
	 * 查询 VouchersInfo
	 * 
	 * @param vouchersInfo
	 * @return List<VouchersInfo> VouchersInfo集合
	 * @throws Exception
	 */
	public List<VouchersInfo> findVouchersInfo(VouchersInfo vouchersInfo)
			throws Exception;

	/**
	 * 查询 VouchersInfo
	 * 
	 * @param vouchersInfo
	 * @param order
	 *            排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
	 * @return List<VouchersInfo> VouchersInfo集合
	 * @throws Exception
	 */
	public List<VouchersInfo> findVouchersInfo(VouchersInfo vouchersInfo,
			String order) throws Exception;

	/**
	 * 分页查询 VouchersInfo
	 * 
	 * @param page
	 * @param vouchersInfo
	 * @return Page<VouchersInfo> VouchersInfo分页结果
	 * @throws Exception
	 */
	public Page<VouchersInfo> findVouchersInfoByPage(Page<VouchersInfo> page,
			VouchersInfo vouchersInfo) throws Exception;

	/**
	 * 分页查询 VouchersInfo 未过期
	 * 
	 * @param page
	 * @param Long
	 *            memberCode
	 * @return List<VouchersInfo>
	 * @throws Exception
	 */
	List<VouchersInfo> findVouchersInfoUnExpiredByPage(Page page,
			Long memberCode) throws Exception;

	/**
	 * 分页查询 VouchersInfo 已过期
	 * 
	 * @param page
	 * @param Long
	 *            memberCode
	 * @return List<VouchersInfo>
	 * @throws Exception
	 */
	List<VouchersInfo> findVouchersInfoExpiredByPage(Page page, Long memberCode)
			throws Exception;

	/**
	 * 分页查询 VouchersInfo 已使用
	 * 
	 * @param page
	 * @param Long
	 *            memberCode
	 * @return List<VouchersInfo>
	 * @throws Exception
	 */
	List<VouchersInfo> findVouchersInfoUseByPage(Page page, Long memberCode)
			throws Exception;

	/**
	 * @Title: findRepeatVoucherList
	 * @Description: 查找已经存在的券码信息
	 * @author: shenshaocheng 2015年11月27日
	 * @modify: shenshaocheng 2015年11月27日
	 * @param vouchersIdList
	 *            券码ID
	 * @return
	 */
	public List<VouchersInfo> findRepeatVoucherList(List<String> vouchersIdList);

	/**
	 * @Title: findVouchersUseDetailsByPage
	 * @Description: 分页查询券码使用明细
	 * @author: shenshaocheng 2015年11月30日
	 * @modify: shenshaocheng 2015年11月30日
	 * @param page
	 * @param vouchersInfo
	 * @return
	 */
	public List<VouchersUseDetails> findVouchersUseDetailsByPage(
			Page<VouchersUseDetails> page, VouchersInfo vouchersInfo);
	
	 /**
	  * @Title: findVouchersUseDetails
	  * @Description: 查询券码使用明细
	  * @author: shenshaocheng 2015年12月3日
	  * @modify: shenshaocheng 2015年12月3日
	  * @param vouchersInfo 查询券码使用明细
	  * @return
	 */	
	public List<VouchersUseDetails> findVouchersUseDetails(
			VouchersInfo vouchersInfo);

	 /**
	  * @Title: findTemporaryByPage
	  * @Description: 查找临时红包券码信息
	  * @author: shenshaocheng 2015年12月1日
	  * @modify: shenshaocheng 2015年12月1日
	  * @param page 分页信息
	  * @param vouchersInfo 红包券码
	  * @return
	  * @throws Exception
	 */	
	public Page<VouchersInfo> findTemporaryByPage(Page<VouchersInfo> page,
			VouchersInfo vouchersInfo) throws Exception;
	
	 /**
	  * @Title: updateVouchersInfoByVouchersId
	  * @Description: 更新红包券码信息
	  * @author: yefeifei 2016年01月29日
	  * @modify: yefeifei 2016年01月29日
	  * @param vouchersInfo 红包券码
	  * @return
	  * @throws Exception
	 */	
	public Integer updateVouchersInfoByVouchersId(VouchersInfo vouchersInfo) throws Exception;
}