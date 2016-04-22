package com.froad.cbank.coremodule.module.normal.merchant.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.MD5Util;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.merchant.enums.MerchantDisableStatusEnum;
import com.froad.cbank.coremodule.module.normal.merchant.enums.UserType;
import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.BasePojo;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.FinitResourceVo;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Login_Fails_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Login_No_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Login_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Login_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Reset_Merchant_User_Pwd_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Update_Merchant_User_Pwd_Req;
import com.froad.cbank.coremodule.module.normal.merchant.utils.EnumTypes;
import com.froad.thrift.service.MerchantRoleResourceService;
import com.froad.thrift.service.MerchantService;
import com.froad.thrift.service.MerchantUserService;
import com.froad.thrift.service.OutletService;
import com.froad.thrift.service.SmsMessageService;
import com.froad.thrift.vo.MerchantDetailVo;
import com.froad.thrift.vo.MerchantUserLoginVoRes;
import com.froad.thrift.vo.MerchantUserVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.OutletVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.SmsMessageResponseVo;
import com.froad.thrift.vo.SmsMessageVo;
import com.froad.thrift.vo.SmsTypeEnum;
import com.froad.thrift.vo.TypeInfoVo;

/**
 * 登录
 * 
 * @ClassName LoginService
 * @author zxl
 * @date 2015年4月8日 上午11:46:13
 */
@Service
public class Login_Service {

	@Resource
	MerchantUserService.Iface merchantUserService;
	@Resource
	MerchantRoleResourceService.Iface merchantRoleResourceService;
	@Resource
	private ResourceService resourceService;
	@Resource
	SmsMessageService.Iface smsMessageService;
	@Resource
	MerchantService.Iface merchantService;
	@Resource
	OutletService.Iface outletService;


	/**
	 * 验证码登录
	 * 
	 * @param req
	 * @return
	 * @throws MerchantException
	 * @throws TException
	 */
	public Login_Res login_vertify(Login_Req req) throws MerchantException, TException {
		int count = 0;
		OriginVo ori = new OriginVo();
		ori.setOperatorId(0);
		ori.setOperatorIp(req.getIp());
		ori.setPlatType(req.getPlatType());
		ori.setClientId(req.getClientId());

		MerchantUserLoginVoRes merchantUserLoginVoRes = merchantUserService.getLoginFailureCount(ori,
				req.getUserName());
		LogCvt.debug("====>用户登录错误次数返回数据<====:" + JSON.toJSONString(merchantUserLoginVoRes));
		if (null != merchantUserLoginVoRes) {
			ResultVo resultVo = merchantUserLoginVoRes.getResult();
			if (null != resultVo && resultVo.getResultCode().equals(EnumTypes.success.getCode())) {
				count = merchantUserLoginVoRes.getLoginFailureCount();
			}
		}
		// 超过5次,校验验证码
		if (count >= 5) {
			if (!StringUtil.isNotBlank(req.getCode())) {
				throw new MerchantException("608", "验证码不能为空", count);
			}
			if (!StringUtil.isNotBlank(req.getKo())) {
				throw new MerchantException("608", "验证码标识不能为空", count);
			}
			LogCvt.info("商户pc登录校验验证码请求参数: token=" + req.getKo() + ",code=" + req.getCode());
			ResultVo rs = smsMessageService.verifyMobileToken(req.getKo(), req.getCode());
			LogCvt.info("商户pc登录校验验证码返回数据:" + JSON.toJSONString(rs));
			if (rs != null && !rs.getResultCode().equals(EnumTypes.success.getCode())) {
				throw new MerchantException(rs.getResultCode(), rs.getResultDesc(), count);
			}
		}
		// 正常登录逻辑
		Login_Res user = new Login_Res();
		MerchantUserLoginVoRes res = merchantUserService.login(ori, req.getUserName(), req.getPassword());
		if (EnumTypes.success.getCode().equals(res.result.resultCode)) {
			user.setUserId(res.getMerchantUser().getId());
			user.setMerchantId(res.getMerchantUser().getMerchantId());
			user.setOutletId(res.getMerchantUser().getOutletId());
			user.setUserName(res.getMerchantUser().getUsername());
			user.setFirstLogin(res.getMerchantUser().isIsRest() ? "0" : "1");
			user.setMerchantRole(res.getMerchantUser().getMerchantRoleId());
			user.setRoleType(res.isIsAdmin() ? "1" : "2");
			user.setToken(res.getToken());
			user.setRealname(res.getMerchantUser().getRealname());
			if (UserType.admin.getCode().equals(res.getMerchantUser().getMerchantRoleId() + "")) {
				user.setIsAdmin(true);
			} else {
				user.setIsAdmin(false);
			}
			if (res.getMerchantUser().isMerchantIsEnable()) {
				user.setMerchantDisableStatus(MerchantDisableStatusEnum.normal.getCode());
			} else {
				user.setMerchantDisableStatus(res.getMerchantUser().getMerchantDisableStatus());
			}
			
			OutletVo outletVo = outletService.getOutletByOutletId(res.getMerchantUser().getOutletId());
			LogCvt.info("门店状态："+outletVo.getDisableStatus());
			user.setOutletStatus(outletVo.getDisableStatus()); //门店状态
			
			// 获取商户用户类型
			String merchantType = query_merchant_types(res.getMerchantUser().getMerchantId() + "");
			user.setMerchantType(merchantType);

			HashMap<String, Object> respMap = resourceService.findResourceByUser(null, res.getMerchantUser().getId(),
					res.getMerchantUser().getMerchantRoleId());
			@SuppressWarnings("unchecked")
			List<FinitResourceVo> rootResources = ((List<FinitResourceVo>) respMap.get("rootResources"));
			user.setRootResources(rootResources);
			return user;
		} else {
			if (res.getResult().getResultCode().equals("8001")) {
				throw new MerchantException(res.result.resultCode, res.result.resultDesc, count);
			} else {
				throw new MerchantException(res.result.resultCode, res.result.resultDesc, count + 1);
			}
		}
	}

	/**
	 * 无验证码登录
	 * 
	 * @param req
	 * @return
	 * @throws MerchantException
	 * @throws TException
	 */
	@SuppressWarnings("unchecked")
	public Login_Res login_(Login_No_Req req) throws MerchantException, TException {
		Login_Res user = new Login_Res();
		// 查看是否可以登录
		// MerchantUserVo vv=new MerchantUserVo();
		// vv.setClientId(req.getClientId());
		// vv.setUsername(req.getUserName());
		// vv.setPassword(req.getPassword());
		// List<MerchantUserVo> vs = merchantUserService.getMerchantUser(vv);
		// MerchantUserVo iss = merchantUserService.getMerchantUserByUsername
		// (req.getUserName());
		// if(iss!=null){
		// int resl =
		// merchantUserService.queryErrorCount(req.getClientId(),iss.getMerchantId(),iss.getId());
		// if(resl>=5&&req.getKo()!=null&&req.getCode()!=null){
		// ResultVo rs=smsMessageService.verifyMobileToken(req.getKo(),
		// req.getCode());
		// if(rs!=null&&rs.getResultCode().equals(EnumTypes.success.getCode())){}
		// else{
		// throw new MerchantException(rs.getResultCode(), rs.getResultDesc());
		// }
		// }else if(resl>=5&&(req.getKo()==null||req.getCode()==null)){
		// throw new MerchantException(EnumTypes.fail.getCode(), "验证码不能为空");
		// }
		//

		OriginVo ori = new OriginVo();
		ori.setOperatorId(0);
		ori.setOperatorIp(req.getIp());
		ori.setPlatType(req.getPlatType());
		ori.setClientId(req.getClientId());
		MerchantUserLoginVoRes res = merchantUserService.login(ori, req.getUserName(), req.getPassword());
		if (EnumTypes.success.getCode().equals(res.getResult().getResultCode())) {
			user.setUserId(res.getMerchantUser().getId());
			user.setMerchantId(res.getMerchantUser().getMerchantId());
			user.setOutletId(res.getMerchantUser().getOutletId());
			user.setUserName(res.getMerchantUser().getUsername());
			user.setFirstLogin(res.getMerchantUser().isIsRest() ? "0" : "1");
			user.setMerchantRole(res.getMerchantUser().getMerchantRoleId());
			user.setRoleType(res.isIsAdmin() ? "1" : "2");
			user.setToken(res.getToken());
			user.setRealname(res.getMerchantUser().getRealname());
			user.setRest(res.getMerchantUser().isIsRest());
			
			
			OutletVo outletVo = outletService.getOutletByOutletId(res.getMerchantUser().getOutletId());
			LogCvt.info("门店状态："+outletVo.getDisableStatus());
			user.setOutletStatus(outletVo.getDisableStatus()); //门店状态
			
			if (UserType.admin.getCode().equals(res.getMerchantUser().getMerchantRoleId() + "")) {
				user.setIsAdmin(true);
			} else {
				user.setIsAdmin(false);
			}
			if (res.getMerchantUser().isMerchantIsEnable()) {
				user.setMerchantDisableStatus(MerchantDisableStatusEnum.normal.getCode());
			} else {
				user.setMerchantDisableStatus(res.getMerchantUser().getMerchantDisableStatus());
			}

			// 获取商户用户类型
			String merchantType = query_merchant_types(res.getMerchantUser().getMerchantId() + "");
			user.setMerchantType(merchantType);

			HashMap<String, Object> respMap = resourceService.findResourceByUser(null, res.getMerchantUser().getId(),
					res.getMerchantUser().getMerchantRoleId());
			List<FinitResourceVo> rootResources = ((List<FinitResourceVo>) respMap.get("rootResources"));
			user.setRootResources(rootResources);

		} else {
			throw new MerchantException(res.getResult().getResultCode(), res.getResult().getResultDesc());
		}
		// }else{
		// throw new MerchantException(EnumTypes.fail.getCode(), "用户名错误");
		// }

		return user;
	}

	/**
	 * 获取验证码图片
	 * 
	 * @param clientId
	 * @return
	 * @throws MerchantException
	 * @throws TException
	 */
	public Map<String, String> verifyUrl(String clientId) throws MerchantException, TException {
		Map<String, String> map = new HashMap<String, String>();
		SmsMessageVo vo = new SmsMessageVo();
		vo.setClientId(clientId);
		vo.setSmsType(-1);
		SmsMessageResponseVo url = smsMessageService.createImgage(vo);
		if (url != null && url.getResultCode().equals(EnumTypes.success.getCode())) {
			map.put("url", url.getUrl());
			map.put("ko", url.getToken());
			return map;
		} else {
			throw new MerchantException(url.getResultCode(), url.getResultDesc());
		}
	}

	/**
	 * 登录退出
	 * 
	 * @param u
	 * @param p
	 * @param userId
	 * @return
	 * @throws MerchantException
	 * @throws TException
	 */
	public Map<String, String> loginOut(String userId, String token, BasePojo req)
			throws MerchantException, TException {
		Map<String, String> reMap = new HashMap<String, String>();

		OriginVo ori = new OriginVo();
		ori.setOperatorId(0);
		ori.setOperatorIp(req.getIp());
		ori.setPlatType(req.getPlatType());

		merchantUserService.logout(ori, token);

		return reMap;
	}

	/**
	 * 修改登录密码
	 * 
	 * @param u
	 * @param p
	 * @param userId
	 * @return
	 * @throws MerchantException
	 * @throws TException
	 */
	public Map<String, String> update(Update_Merchant_User_Pwd_Req req) throws MerchantException, TException {
		Map<String, String> reMap = new HashMap<String, String>();
		MerchantUserVo vo = new MerchantUserVo();
		vo.setId(Long.valueOf(req.getMerchantUser().getId()));
		vo.setClientId(req.getClientId());
		vo.setMerchantId(req.getMerchantUser().getMerchantId());
		List<MerchantUserVo> al = merchantUserService.getMerchantUser(vo);
		if (al.size() > 0) {
			MerchantUserVo resp = al.get(0);
			if (!resp.getPassword().equals(req.getPassword())) {
				throw new MerchantException(EnumTypes.fail.getCode(), "原密码错误");
			}
			if (resp.getPassword().equals(req.getNewPassword())) {
				throw new MerchantException(EnumTypes.fail.getCode(), "原密码与新密码不能相同");
			}
			vo.setPassword(req.getNewPassword());
			vo.setIsRest(true);

			OriginVo ori = new OriginVo();
			ori.setOperatorId(req.getMerchantUser().getId());
			ori.setOperatorIp(req.getIp());
			ori.setPlatType(req.getPlatType());

			ResultVo res = merchantUserService.updateMerchantUser(ori, vo);
			if (!res.getResultCode().equals(EnumTypes.success.getCode())) {
				throw new MerchantException(EnumTypes.fail.getCode(), "密码修改失败");
			}
		} else {
			throw new MerchantException(EnumTypes.fail.getCode(), "用户不存在");
		}
		return reMap;
	}

	/**
	 * 密码重置
	 * 
	 * @tilte reset
	 * @author zxl
	 * @date 2015年4月11日 下午2:57:50
	 * @param req
	 * @return
	 * @throws MerchantException
	 * @throws TException
	 */
	public Map<String, String> reset(Reset_Merchant_User_Pwd_Req req) throws MerchantException, TException {
		Map<String, String> reMap = new HashMap<String, String>();
		MerchantUserVo vo = new MerchantUserVo();
		vo.setId(Long.valueOf(req.getUserId()));
		vo.setClientId(req.getClientId());
		vo.setMerchantId(req.getMerchantUser().getMerchantId());
		List<MerchantUserVo> al = merchantUserService.getMerchantUser(vo);
		if (al.size() > 0) {
			MerchantUserVo user = al.get(0);
			String newPwd = MD5Util.getMD5Str("111111");
			vo.setIsRest(false);
			vo.setPassword(newPwd);

			OriginVo ori = new OriginVo();
			ori.setOperatorId(req.getMerchantUser().getId());
			ori.setOperatorIp(req.getIp());
			ori.setPlatType(req.getPlatType());

			ResultVo res = merchantUserService.updateMerchantUser(ori, vo);
			if (!res.getResultCode().equals(EnumTypes.success.getCode())) {
				throw new MerchantException(EnumTypes.fail.getCode(), "密码重置失败");
			}
			if (req.getMobile() != null) {
				SmsMessageVo svo = new SmsMessageVo();
				svo.setClientId(req.getClientId());
				svo.setMobile(user.getPhone());
				svo.setSmsType(SmsTypeEnum.merchantResetLoginPwd.getValue());
				List<String> values = new ArrayList<String>();
				values.add(user.getUsername());
				values.add(user.getPhone());
				values.add("111111");
				svo.setValues(values);
				try {
					SmsMessageResponseVo r = smsMessageService.sendSMS(svo);
					LogCvt.info("send msg respone:" + JSON.toJSONString(r));
				} catch (Exception e) {
					LogCvt.error("send msg error:" + e.getMessage());
				}
			}
		} else {
			throw new MerchantException(EnumTypes.fail.getCode(), "用户不存在");
		}
		return reMap;
	}

	/**
	 * 获取当前登录商户的商户类型
	 * 
	 * @param id
	 * @return
	 * @throws MerchantException
	 * @throws TException
	 */
	public String query_merchant_types(String merchantId) throws MerchantException, TException {
		String merchantTypes = "";
		MerchantDetailVo resv = merchantService.getMerchantDetail(merchantId);
		if (resv.getTypeInfo() != null && resv.getTypeInfo().size() > 0) {
			for (TypeInfoVo tyv : resv.getTypeInfo()) {
				merchantTypes += tyv.getMerchantTypeId() + ",";
			}
		}
		return merchantTypes;
	}

	/**
	 * 
	 * getFailsCount:获取登录错误统计的次数
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月17日 上午11:33:03
	 * @param count_KEY
	 * @return
	 * @throws TException
	 *
	 */
	public Map<String, Object> getFailsCount(Login_Fails_Req reqVo) throws TException {
		Map<String, Object> resMap = new HashMap<String, Object>();
		OriginVo ori = new OriginVo();
		ori.setOperatorId(0);
		ori.setOperatorIp(reqVo.getIp());
		ori.setPlatType(reqVo.getPlatType());
		ori.setClientId(reqVo.getClientId());
		MerchantUserLoginVoRes merchantUserLoginVoRes = merchantUserService.getLoginFailureCount(ori,
				reqVo.getUserName());
		LogCvt.info("=====>登录错误次数返回数据<=====:" + JSON.toJSONString(merchantUserLoginVoRes));
		if (null != merchantUserLoginVoRes) {
			ResultVo resultVo = merchantUserLoginVoRes.getResult();
			String code = resultVo.getResultCode();
			if (null != resultVo && (code.equals(EnumTypes.success.getCode()) || code.equals("8001"))) {
				resMap.put("loginFailureCount", merchantUserLoginVoRes.getLoginFailureCount());
				resMap.put("code", resultVo.getResultCode());
				resMap.put("message", resultVo.getResultDesc());
			}

		} else {
			resMap.put("code", "608");
			resMap.put("message", "获取登录错误统计次数失败");
		}
		return resMap;
	}

}
