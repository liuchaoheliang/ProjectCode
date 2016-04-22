/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
package com.froad.logic.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.functors.StringValueTransformer;
import org.apache.commons.lang.StringUtils;

import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.VIPWhiteListLogic;
import com.froad.po.AddVIPWhiteListReq;
import com.froad.po.CheckVIPExistWhiteListReq;
import com.froad.po.CheckVIPExistWhiteListRes;
import com.froad.po.Origin;
import com.froad.po.RemoveVIPWhiteListReq;
import com.froad.po.Result;
import com.froad.util.RedisKeyUtil;

public class VIPWhiteListLogicImpl implements VIPWhiteListLogic {
	RedisManager redis = new RedisManager();

	@Override
	public CheckVIPExistWhiteListRes checkVIPExistWhiteList(CheckVIPExistWhiteListReq req) {
		CheckVIPExistWhiteListRes res = new CheckVIPExistWhiteListRes();
		Result result = new Result(ResultCode.failed);
		Boolean checkedWhiteList = null; //  客户端是否需要检查会员白名单
		Boolean existWhiteList = null; // 会员是否存在白名单
		try {
			Long memberCode = req.getMemberCode();
			Origin origin = req.getOrigin();
			if (null == origin) {
				throw new FroadServerException("源对象[origin]不能为空!");
			}
			String clientId = origin.getClientId();
			if (StringUtils.isBlank(clientId)) {
				throw new FroadServerException("客户端ID[clientId]不能为空!");
			}
			String key_checked = RedisKeyUtil.cbbank_check_VIPWhiteList_client();
			checkedWhiteList = redis.sismember(key_checked, clientId);
			if (null == checkedWhiteList || !checkedWhiteList) {
				existWhiteList = true;
				LogCvt.info("客户端ID[" + clientId + "]不检查白名单设置, 认为所有客户存在于白名单系统中");
			} else {
				if (null == memberCode) {
					existWhiteList = false; 
					LogCvt.info("客户端ID[" + clientId + "]须检查白名单设置, 上送的memberCode的为空, 返回不在白名单中");
				} else {
					String key_white = RedisKeyUtil.cbbank_VIPWhiteList_client_id(clientId);
					existWhiteList = redis.sismember(key_white, memberCode.toString());
					LogCvt.info("客户端ID[" + clientId + "]须检查白名单设置, 上送的memberCode=[" + memberCode + "]是否[" + existWhiteList + "]存在客户端clientId=[" + clientId + "]的白名单中");
				}
			}
			result.setResult(ResultCode.success);
		} catch (FroadServerException e) {
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
			LogCvt.error("检查VIP白名单失败，原因:" + e.getMessage(), e);
		} catch (Exception e) {
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc("检查VIP白名单[系统异常]!");
			LogCvt.error("检查VIP白名单[系统异常]，原因:" + e.getMessage(), e);
		}
		res.setExistWhiteList(existWhiteList);
		res.setResult(result);
		return res;
	}

	@Override
	public Result addVIPWhiteList(AddVIPWhiteListReq req) {
		Result result = new Result(ResultCode.failed);
		try {
			Origin origin = req.getOrigin();
			if (null == origin) {
				throw new FroadServerException("源对象[origin]不能为空!");
			}
			String clientId = origin.getClientId();
			if (StringUtils.isBlank(clientId)) {
				throw new FroadServerException("客户端ID[clientId]不能为空!");
			}
			List<Long> memberCodeList = req.getMemberCode();
			if (CollectionUtils.isEmpty(memberCodeList)) {
				throw new FroadServerException("会员编号集合[memberCode]不能为空!");
			}
			int size = memberCodeList.size(); // 上送的集合大小
			Set<String> memberCodeSet = new HashSet<String>(size);
			CollectionUtils.collect(memberCodeList.iterator(), StringValueTransformer.getInstance(), memberCodeSet);
			String key = RedisKeyUtil.cbbank_VIPWhiteList_client_id(clientId);
			Long res = redis.putSet(key, memberCodeSet);
//			if (null != res && res.intValue() == size) {
//				result.setResult(ResultCode.success);
//			}
			result.setResult(ResultCode.success); // 只要不报错就认为成功
		} catch (FroadServerException e) {
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
			LogCvt.error("添加VIP白名单失败，原因:" + e.getMessage(), e);
		} catch (Exception e) {
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc("添加VIP白名单[系统异常]!");
			LogCvt.error("添加VIP白名单[系统异常]，原因:" + e.getMessage(), e);
		}
		return result;
	}
	
	@Override
	public Result removeVIPWhiteList(RemoveVIPWhiteListReq req) {
		Result result = new Result(ResultCode.failed);
		try {
			Origin origin = req.getOrigin();
			if (null == origin) {
				throw new FroadServerException("源对象[origin]不能为空!");
			}
			String clientId = origin.getClientId();
			if (StringUtils.isBlank(clientId)) {
				throw new FroadServerException("客户端ID[clientId]不能为空!");
			}
			List<Long> memberCodeList = req.getMemberCode();
			if (CollectionUtils.isEmpty(memberCodeList)) {
				throw new FroadServerException("会员编号集合[memberCode]不能为空!");
			}
			int size = memberCodeList.size(); // 上送的集合大小
			Set<String> memberCodeSet = new HashSet<String>(size);
			CollectionUtils.collect(memberCodeList.iterator(), StringValueTransformer.getInstance(), memberCodeSet);
			String key = RedisKeyUtil.cbbank_VIPWhiteList_client_id(clientId);
			Long res = redis.srem(key, memberCodeSet.toArray(new String[size]));
//			if (null != res && res.intValue() == size) {
//				result.setResult(ResultCode.success);
//			}
			result.setResult(ResultCode.success); // 只要不报错就认为成功
		} catch (FroadServerException e) {
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
			LogCvt.error("移除VIP白名单失败，原因:" + e.getMessage(), e);
		} catch (Exception e) {
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc("移除VIP白名单[系统异常]!");
			LogCvt.error("移除VIP白名单[系统异常]，原因:" + e.getMessage(), e);
		}
		return result;
	}
}
