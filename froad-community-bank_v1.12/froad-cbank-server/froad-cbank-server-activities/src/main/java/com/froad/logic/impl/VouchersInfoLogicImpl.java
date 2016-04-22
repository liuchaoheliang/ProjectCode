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
 * @Title: VouchersInfoLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.handler.VouchersInfoHandler;
import com.froad.handler.impl.VouchersInfoHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.VouchersInfoLogic;
import com.froad.po.VouchersInfo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.active.FindVouchersDetailInfoVO;
import com.froad.thrift.vo.active.VouchersCouponCodeVO;
import com.froad.util.BeanUtil;

/**
 * 
 * <p>
 * @Title: VouchersInfoLogic.java
 * </p>
 * <p>
 * Description: 实现对VouchersInfo所有业务逻辑处理
 * </p>
 * 
 * @author f-road
 * @version 1.0
 * @created 2015年3月17日
 */
public class VouchersInfoLogicImpl implements VouchersInfoLogic {
	private VouchersInfoHandler vouchersInfoHandler = new VouchersInfoHandlerImpl();

	/**
	 * 增加 VouchersInfo
	 * 
	 * @param vouchersInfo
	 * @return Long 主键ID
	 */
	@Override
	public Long addVouchersInfo(VouchersInfo vouchersInfo) {

		Long result = null;
		try {
			/********************** 操作MySQL数据库 **********************/
			result = vouchersInfoHandler.addVouchersInfo(vouchersInfo);

			if (null != result) { // 添加成功
				/********************** 操作Mongodb数据库 **********************/

				/********************** 操作Redis缓存 **********************/
			} else { // 添加失败

			}
		} catch (Exception e) {
			result = null;
			LogCvt.error("添加VouchersInfo失败，原因:" + e.getMessage(), e);
		} finally {
		}
		return result;

	}

	/**
	 * 删除 VouchersInfo
	 * 
	 * @param vouchersInfo
	 * @return Boolean 是否成功
	 */
	@Override
	public Boolean deleteVouchersInfo(VouchersInfo vouchersInfo) {

		Boolean result = false;
		try {
			/********************** 操作MySQL数据库 **********************/
			result = vouchersInfoHandler.deleteVouchersInfo(vouchersInfo) > 0;

			if (result) { // 删除成功

				/********************** 操作Mongodb数据库 **********************/

				/********************** 操作Redis缓存 **********************/
			} else { // 删除失败
			}
		} catch (Exception e) {
			result = false;
			LogCvt.error("删除VouchersInfo失败，原因:" + e.getMessage(), e);
		} finally {
		}
		return result;

	}

	/**
	 * 根据id删除 VouchersInfo
	 * 
	 * @param id
	 * @return Boolean 是否成功
	 */
	@Override
	public Boolean deleteVouchersInfoById(Long id) {

		Boolean result = false;
		try {
			/********************** 操作MySQL数据库 **********************/
			result = vouchersInfoHandler.deleteVouchersInfoById(id) > 0;

			if (result) { // 删除成功

				/********************** 操作Mongodb数据库 **********************/

				/********************** 操作Redis缓存 **********************/
			} else { // 删除失败
			}
		} catch (Exception e) {
			result = false;
			LogCvt.error("根据id删除VouchersInfo失败，原因:" + e.getMessage(), e);
		} finally {
		}
		return result;

	}

	/**
	 * 修改 VouchersInfo
	 * 
	 * @param vouchersInfo
	 * @return Boolean 是否成功
	 */
	@Override
	public Boolean updateVouchersInfo(VouchersInfo vouchersInfo) {

		Boolean result = false;
		try {
			/********************** 操作MySQL数据库 **********************/
			result = vouchersInfoHandler.updateVouchersInfo(vouchersInfo) > 0;
			if (result) { // 修改成功

				/********************** 操作Mongodb数据库 **********************/

				/********************** 操作Redis缓存 **********************/
				result = true;

			} else { // 修改失败
			}
		} catch (Exception e) {
			result = false;
			LogCvt.error("修改VouchersInfo失败，原因:" + e.getMessage(), e);
		} finally {
		}
		return result;

	}

	/**
	 * 根据id修改 VouchersInfo
	 * 
	 * @param vouchersInfo
	 * @return Boolean 是否成功
	 */
	@Override
	public Boolean updateVouchersInfoById(VouchersInfo vouchersInfo) {

		Boolean result = false;
		try {
			/********************** 操作MySQL数据库 **********************/
			result = vouchersInfoHandler.updateVouchersInfoById(vouchersInfo) > 0;

			if (result) { // 修改成功

				/********************** 操作Mongodb数据库 **********************/

				/********************** 操作Redis缓存 **********************/
			} else { // 修改失败
			}
		} catch (Exception e) {
			result = false;
			LogCvt.error("根据id修改VouchersInfo失败，原因:" + e.getMessage(), e);
		} finally {
		}
		return result;

	}

	/**
	 * 根据id查询单个 VouchersInfo
	 * 
	 * @param id
	 * @return VouchersInfo 单个 VouchersInfo
	 */
	@Override
	public VouchersInfo findVouchersInfoById(Long id) {

		VouchersInfo result = null;
		try {
			/********************** 操作Redis缓存 **********************/

			/********************** 操作Mongodb数据库 **********************/

			/********************** 操作MySQL数据库 **********************/
			result = vouchersInfoHandler.findVouchersInfoById(id);
		} catch (Exception e) {
			result = null;
			LogCvt.error("根据id查询VouchersInfo失败，原因:" + e.getMessage(), e);
		} finally {
		}
		return result;

	}

	/**
	 * 查询一个 VouchersInfo
	 * 
	 * @param vouchersInfo
	 * @return VouchersInfo 单个 VouchersInfo
	 */
	@Override
	public VouchersInfo findOneVouchersInfo(VouchersInfo vouchersInfo) {

		VouchersInfo result = null;
		try {
			/********************** 操作Redis缓存 **********************/

			/********************** 操作Mongodb数据库 **********************/

			/********************** 操作MySQL数据库 **********************/
			result = vouchersInfoHandler.findOneVouchersInfo(vouchersInfo);
		} catch (Exception e) {
			result = null;
			LogCvt.error("根据条件查询一个VouchersInfo失败，原因:" + e.getMessage(), e);
		} finally {
		}
		return result;

	}

	/**
	 * 统计 VouchersInfo
	 * 
	 * @param vouchersInfo
	 * @return Integer 返回记录数 VouchersInfo
	 */
	@Override
	public Integer countVouchersInfo(VouchersInfo vouchersInfo) {

		Integer result = null;
		try {
			/********************** 操作Redis缓存 **********************/

			/********************** 操作Mongodb数据库 **********************/

			/********************** 操作MySQL数据库 **********************/
			result = vouchersInfoHandler.countVouchersInfo(vouchersInfo);
		} catch (Exception e) {
			result = null;
			LogCvt.error("根据条件统计VouchersInfo失败，原因:" + e.getMessage(), e);
		} finally {
		}
		return result;

	}

	/**
	 * 查询 VouchersInfo
	 * 
	 * @param vouchersInfo
	 * @return List<VouchersInfo> VouchersInfo集合
	 */
	@Override
	public List<VouchersInfo> findVouchersInfo(VouchersInfo vouchersInfo) {

		List<VouchersInfo> result = null;
		try {
			/********************** 操作Redis缓存 **********************/

			/********************** 操作Mongodb数据库 **********************/

			/********************** 操作MySQL数据库 **********************/
			result = vouchersInfoHandler.findVouchersInfo(vouchersInfo);
		} catch (Exception e) {
			result = null;
			LogCvt.error("根据条件查询VouchersInfo失败，原因:" + e.getMessage(), e);
		} finally {
		}
		return result;

	}

	/**
	 * 分页查询 VouchersInfo
	 * 
	 * @param page
	 * @param vouchersInfo
	 * @return Page<VouchersInfo> VouchersInfo分页结果
	 */
	@Override
	public FindVouchersDetailInfoVO findVouchersInfoByPage(
			Page<VouchersInfo> page, VouchersInfo vouchersInfo) {
		FindVouchersDetailInfoVO findVouchersDetailInfoVO = new FindVouchersDetailInfoVO();
		try {
			page = vouchersInfoHandler.findVouchersInfoByPage(page,
					vouchersInfo);
			List<VouchersCouponCodeVO> vouchersCouponCodelist = new ArrayList<VouchersCouponCodeVO>();
			List<VouchersInfo> vouchersInfoList = page.getResultsContent();
			int count = 0;
			for (VouchersInfo po : vouchersInfoList) {
				VouchersCouponCodeVO vo = new VouchersCouponCodeVO();
				StringBuffer vouchersId = new StringBuffer(po.getVouchersId());
				if(vouchersId.length() > 4) {
					vouchersId.replace(2, vouchersId.length()-2, "********");
				} else if (vouchersId.length() > 1){
					vouchersId.replace(1, vouchersId.length()-1, "********");
				} else {
					vouchersId.append("********");
				}
				vo.setCreateTime(po.getCreateTime().getTime());
				vo.setNumberCode((short) ++count);
				vo.setStatus(po.getStatus());
				vo.setVouchersIds(vouchersId.toString());
				vo.setVouchersMoney(po.getVouchersMoney());
				vo.setVouchersResMoney(po.getVouchersResMoney());
				vouchersCouponCodelist.add(vo);
			}
			PageVo pageVo = (PageVo) BeanUtil
					.copyProperties(PageVo.class, page);
			findVouchersDetailInfoVO.vouchersCouponCodelist = vouchersCouponCodelist;
			findVouchersDetailInfoVO.page = pageVo;
		} catch (Exception e) {
			LogCvt.error("分页查询VouchersInfo失败，原因:" + e.getMessage(), e);
		} finally {
		}
		return findVouchersDetailInfoVO;
	}

	/**
	 * 分页查询 VouchersInfo
	 * 
	 * @param page
	 * @param vouchersInfo
	 * @return Page<VouchersInfo> VouchersInfo分页结果
	 */
	@Override
	public FindVouchersDetailInfoVO findTemporaryVouchersInfoByPage(
			Page<VouchersInfo> page, VouchersInfo vouchersInfo) {
		FindVouchersDetailInfoVO findVouchersDetailInfoVO = new FindVouchersDetailInfoVO();
		try {
			page = vouchersInfoHandler.findTemporaryByPage(page, vouchersInfo);
			List<VouchersCouponCodeVO> vouchersCouponCodelist = new ArrayList<VouchersCouponCodeVO>();
			List<VouchersInfo> vouchersInfoList = page.getResultsContent();
			int count = 0;
			for (VouchersInfo po : vouchersInfoList) {
				VouchersCouponCodeVO vo = new VouchersCouponCodeVO();
				StringBuffer vouchersId = new StringBuffer(po.getVouchersId());
				if(vouchersId.length() > 4) {
					vouchersId.replace(2, vouchersId.length()-2, "********");
				} else if (vouchersId.length() > 1){
					vouchersId.replace(1, vouchersId.length()-1, "********");
				} else {
					vouchersId.append("********");
				}
				
				vo.setCreateTime(po.getCreateTime().getTime());
				vo.setNumberCode((short) ++count);
				vo.setStatus(po.getStatus());
				vo.setVouchersIds(vouchersId.toString());
				vo.setVouchersMoney(po.getVouchersMoney());
				vo.setVouchersResMoney(po.getVouchersResMoney());
				vouchersCouponCodelist.add(vo);
			}
			PageVo pageVo = (PageVo) BeanUtil
					.copyProperties(PageVo.class, page);
			findVouchersDetailInfoVO.vouchersCouponCodelist = vouchersCouponCodelist;
			findVouchersDetailInfoVO.page = pageVo;
		} catch (Exception e) {
			LogCvt.error("分页查询VouchersInfo失败，原因:" + e.getMessage(), e);
		} finally {
		}
		return findVouchersDetailInfoVO;

	}

	@Override
	public List<VouchersInfo> findAvailableVouchersListByActiveId(
			String activeId) throws Exception {
		List<VouchersInfo> vouchersInfoList = 
				this.vouchersInfoHandler.findAvailableVouchersListByActiveId(activeId);
		return vouchersInfoList;
	}

}