/**
 * @Title: VouchersRunHandlerImpl.java
 * @Package com.froad.handler.impl
 * @Description: TODO
 * Copyright:2015 F-Road All Rights Reserved   
 * Company:f-road.com.cn
 * 
 * @creater froad-Joker 2015年11月26日
 * @version V1.0
 **/

package com.froad.handler.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.VouchersInfoMapper;
import com.froad.db.redis.VouchersRedis;
import com.froad.enums.ResultCode;
import com.froad.enums.VouchersStatus;
import com.froad.handler.VouchersRunHandler;
import com.froad.logback.LogCvt;
import com.froad.po.FindVouchersOfSubmitReq;
import com.froad.po.PayResultNoticeReq;
import com.froad.po.VouchersInfo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.ActiveUtils;

/**
 * @ClassName: VouchersRunHandlerImpl
 * @Description: TODO
 * @author froad-Joker 2015年11月26日
 * @modify froad-Joker 2015年11月26日
 */

public class VouchersRunHandlerImpl implements VouchersRunHandler {


	/**
	 * @Title: findOneVouchersInfo
	 * @Description: TODO
	 * @author: Joker 2015年11月27日
	 * @modify: Joker 2015年11月27日
	 * @param checkVouchersReq
	 * @return
	 * @see com.froad.handler.VouchersRunHandler#checkVoucherVaild(com.froad.po.CheckVouchersReq)
	 */

	@Override
	public VouchersInfo findOneVouchersInfo(VouchersInfo vouchersInfo) {
		SqlSession sqlSession = null;
		VouchersInfo result = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			VouchersInfoMapper vouchersInfoMapper = sqlSession
					.getMapper(VouchersInfoMapper.class);

			result = vouchersInfoMapper.findOneVouchersInfo(vouchersInfo);

		} catch (Exception e) {
			LogCvt.error("查询[findOneVouchersInfo]失败，原因:" + e.getMessage(), e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;
	}

	/**
	 * @Title: payResultNotice
	 * @Description: TODO
	 * @author: Joker 2015年11月27日
	 * @modify: Joker 2015年11月27日
	 * @param payResultNoticeReq
	 * @return
	 * @see com.froad.handler.VouchersRunHandler#payResultNotice(com.froad.po.PayResultNoticeReq)
	 */

	@Override
	public ResultVo payResultNotice(PayResultNoticeReq payResultNoticeReq) {
		SqlSession sqlSession = null;
		ResultVo resultVo = new ResultVo(ResultCode.success.getCode(),
				"[payResultNotice]支付结果通知 请求成功");
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			List<String> vouchersIds = payResultNoticeReq.getVouchersIds();
			boolean isSuccess = payResultNoticeReq.getIsSuccess();
			Long memberCode = payResultNoticeReq.getMemberCode();
			String clientId = payResultNoticeReq.getClientId();
			String orderId = payResultNoticeReq.getOrderId();
			
			VouchersInfoMapper vouchersInfoMapper = sqlSession
					.getMapper(VouchersInfoMapper.class);
		
			for (String vouchersId : vouchersIds) {
				VouchersInfo vouchersInfo = new VouchersInfo();
				vouchersInfo.setClientId(payResultNoticeReq.getClientId());
				vouchersInfo.setVouchersId(vouchersId);
				VouchersInfo currentVouchersInfo = vouchersInfoMapper.findOneVouchersInfo(vouchersInfo);
				String activeId = currentVouchersInfo.getActiveId();
				if (isSuccess) {
					vouchersInfo.setStatus(VouchersStatus.paySuccess.getCode());
					vouchersInfo.setUpdateTime(new Date());
					vouchersInfo.setUseTime(new Date());
					vouchersInfo.setUserMemberCode(memberCode);
				} else {
					vouchersInfo.setStatus(VouchersStatus.init.getCode());
					vouchersInfo.setUpdateTime(new Date());
					vouchersInfo.setUseTime(null);
					vouchersInfo.setUserMemberCode(null);
				}
				//根据isSuccess来更新代金券信息
				vouchersInfoMapper.updateVouchersInfoByClientIdAndVouchersId(vouchersInfo);
				if(!isSuccess)
				{
					//回退个人资格
					int result=VouchersRedis.rollbackVouchersPersonLimit(clientId,activeId, memberCode);
					
					if(result!=0){
						//return  ActiveUtils.getEnumConstant(Integer.toString(result));
						LogCvt.info("----更新支付结果（交易失败）--code--"
						+ActiveUtils.getEnumConstant(Integer.toString(result)).getResultCode()
						+" msg--" 
						+ActiveUtils.getEnumConstant(Integer.toString(result)).getResultDesc()); 
					}
					
					//回退当日资格
					result=VouchersRedis.rollbackVouchersGlobalLimit(clientId, activeId, orderId, 1);
					if(result!=0){
						//return ActiveUtils.getEnumConstant(Integer.toString(result));
						LogCvt.info("----更新支付结果（交易失败）--code--"
						+ActiveUtils.getEnumConstant(Integer.toString(result)).getResultCode()
						+" msg--" 
						+ActiveUtils.getEnumConstant(Integer.toString(result)).getResultDesc());
					}
					//回退全局资格
					result=VouchersRedis.rollbackVouchersBaseGlobalLimit(clientId, activeId);
					if(result!=0){
						//return ActiveUtils.getEnumConstant(Integer.toString(result));
						LogCvt.info("----更新支付结果（交易失败）--code--"
						+ActiveUtils.getEnumConstant(Integer.toString(result)).getResultCode()
						+" msg--" 
						+ActiveUtils.getEnumConstant(Integer.toString(result)).getResultDesc());
					}
				}

			}

		} catch (Exception e) {
			resultVo = new ResultVo(ResultCode.failed.getCode(),
					"更新[payResultNotice]失败");
			LogCvt.error("更新[payResultNotice]失败，原因:" + e.getMessage(), e);
			if (null != sqlSession)
				sqlSession.rollback(true);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return resultVo;
	}

	 /**
	  * @Title: getAllVouchersInfoByClientIdAndMemberCode
	  * @Description: TODO
	  * @author: Joker 2015年12月1日
	  * @modify: Joker 2015年12月1日
	  * @param findVouchersOfSubmitReq
	  * @return
	  * @see com.froad.handler.VouchersRunHandler#getAllVouchersInfo(com.froad.po.FindVouchersOfSubmitReq)
	  */
	
	
	@Override
	public List<VouchersInfo> getAllVouchersInfoByClientIdAndMemberCode(
			FindVouchersOfSubmitReq findVouchersOfSubmitReq) {
		
		SqlSession sqlSession = null;
		List<VouchersInfo> result = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			VouchersInfoMapper vouchersInfoMapper = sqlSession
					.getMapper(VouchersInfoMapper.class);
			VouchersInfo vouchersInfo = new VouchersInfo();
			vouchersInfo.setClientId(findVouchersOfSubmitReq.getClientId());
			vouchersInfo.setMemberCode(findVouchersOfSubmitReq.getMemberCode());
			result = vouchersInfoMapper.findVouchersInfo(vouchersInfo, null);

		} catch (Exception e) {
			LogCvt.error("查询[getAllVouchersInfoByClientIdAndMemberCode]失败，原因:" + e.getMessage(), e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;
	}

}
