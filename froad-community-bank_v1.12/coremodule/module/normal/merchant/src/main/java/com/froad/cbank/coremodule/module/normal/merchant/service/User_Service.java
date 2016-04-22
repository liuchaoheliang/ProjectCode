package com.froad.cbank.coremodule.module.normal.merchant.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.MD5Util;
import com.froad.cbank.coremodule.module.normal.merchant.enums.UserType;
import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Add_Merchant_User_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Del_Merchant_User_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.MerchantUser;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.PageRes;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Merchant_User_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Merchant_User_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Update_Merchant_User_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Update_Merchant_User_Status_Req;
import com.froad.cbank.coremodule.module.normal.merchant.utils.EnumTypes;
import com.froad.cbank.coremodule.module.normal.merchant.utils.TargetObjectFormat;
import com.froad.thrift.service.FinityMerchantUserService;
import com.froad.thrift.service.MerchantRoleService;
import com.froad.thrift.service.MerchantUserService;
import com.froad.thrift.vo.CommonAddVoRes;
import com.froad.thrift.vo.MerchantUserPageVoRes;
import com.froad.thrift.vo.MerchantUserVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;

/**
 * 商户用户
 * @ClassName User_Service
 * @author zxl
 * @date 2015年4月17日 上午10:04:33
 */
@Service
public class User_Service {
	
	@Resource
	MerchantUserService.Iface merchantUserService;
	
	@Resource
	FinityMerchantUserService.Iface finityMerchantUserService;
	
	@Resource
	MerchantRoleService.Iface merchantRoleService;
	
	/**
	 * 门店用户查询 query_merchant_outlet_user
	 * 
	 * @param reqMap
	 * @param
	 */
	public Map<String, Object> query_merchant_outlet_user(Query_Merchant_User_Req req,MerchantUser user) throws MerchantException, Exception {
		Map<String, Object> mapList = new HashMap<String, Object>();
		
		MerchantUserVo userVo = new MerchantUserVo();
		userVo.setClientId(req.getClientId());
//		userVo.setMerchantId(req.getMerchantUser().getMerchantId());
		userVo.setMerchantId(user.getMerchantId());
		if(req.getMerchantUser().getIsSuperAdmin()){
			if(StringUtils.isNotBlank(req.getOutletId())){
				userVo.setOutletId(req.getOutletId());
			}
		}else{
			userVo.setOutletId(req.getMerchantUser().getOutletId());
		}
		if(StringUtils.isNotBlank(req.getUsername())){
			userVo.setUsername(req.getUsername());
		} 
		if(req.getIsDelete()!=null){
			userVo.setIsDelete(req.getIsDelete());
		}
		if(StringUtils.isNotBlank(req.getId())){
			userVo.setId(Long.valueOf(req.getId()));
		}
		
		if(!user.getMerchantRoleId().equals(UserType.admin.getCode())){
			userVo.setOperatorUserId(user.getId());
		} 
		
		PageVo pageVo=new PageVo();
		pageVo.setPageNumber(req.getPageNumber());
		pageVo.setPageSize(req.getPageSize());
		pageVo.setLastPageNumber(req.getLastPageNumber());
		pageVo.setFirstRecordTime(req.getFirstRecordTime());
		pageVo.setLastRecordTime(req.getLastRecordTime());
		MerchantUserPageVoRes res =finityMerchantUserService.getMerchantUserByPage(pageVo,userVo);
//		if (res.getMerchantUserVoListSize()>0) {
//			MerchantRoleVo merchantRoleVo = new MerchantRoleVo();
////			merchantRoleVo.setClientId(req.getClientId());
//			List<MerchantRoleVo> list = merchantRoleService.getMerchantRole(merchantRoleVo);
//			HashMap<String,MerchantRoleVo> h = new HashMap<String, MerchantRoleVo>();
//			if(list!=null&&list.size()>0){
//				for(MerchantRoleVo vo : list){
//					h.put(vo.getId()+"", vo);
//				}
//			}
//			List<Query_Merchant_User_Res> listRes=new ArrayList<Query_Merchant_User_Res>();
//			for(MerchantUserVo vo:res.getMerchantUserVoList()){
//				Query_Merchant_User_Res r=new Query_Merchant_User_Res();
//				TargetObjectFormat.copyProperties(vo, r);
//				if(h.get(vo.getMerchantRoleId()+"")!=null){
//					r.setRoleName(h.get(vo.getMerchantRoleId()+"").getName());
//				}
//				if("0".equals(vo.getOutletId())){
//					r.setOutletName("总店");
//				}
//				listRes.add(r);
//			}
		
		List<Query_Merchant_User_Res> listRes = new ArrayList<Query_Merchant_User_Res>();
		if (res.getMerchantUserVoListSize()>0) {
			List<MerchantUserVo> merchantUserVoList = res.getMerchantUserVoList();
			for (MerchantUserVo vo : merchantUserVoList) {
				Query_Merchant_User_Res r = new Query_Merchant_User_Res();
				TargetObjectFormat.copyProperties(vo, r);
				//如果是管理员，添加特殊处理
				if(r.getMerchantRoleId().equals(UserType.admin.getCode())){
					r.setRealname("(管理员)");
					r.setOutletName("(管理员)");
				}
				listRes.add(r);
			}
			mapList.put("merchantUserVo", listRes);
			PageRes pageRes = new PageRes();
			TargetObjectFormat.copyProperties(res.getPage(), pageRes);
			mapList.put("page",pageRes);
		} else {
			throw new MerchantException(EnumTypes.noresult.getCode(),EnumTypes.noresult.getMsg());
		}
		
		return mapList;
	}
	
	
	/**
	 * 门店用户添加merchant_outlet_user_add
	 * 
	 * @param reqMap
	 * @param
	 */
	public Map<String, String> merchant_outlet_user_add(Add_Merchant_User_Req req) throws MerchantException, Exception {
		Map<String, String> map = new HashMap<String, String>();
		
		MerchantUserVo outletVo = new MerchantUserVo();
		TargetObjectFormat.copyProperties(req, outletVo);
		outletVo.setPassword(MD5Util.getMD5Str("111111"));
		outletVo.setIsRest(false);
		outletVo.setIsDelete(false);
		outletVo.setClientId(req.getClientId());
		outletVo.setMerchantId(req.getMerchantUser().getMerchantId());
		outletVo.setOutletId(req.getOutletId());
		outletVo.setOperatorUserId(req.getOperatorUserId());
		
		OriginVo ori = new OriginVo();
		ori.setOperatorId(req.getMerchantUser().getId());
		ori.setOperatorIp(req.getIp());
		ori.setPlatType(req.getPlatType());
		
		CommonAddVoRes res = finityMerchantUserService.addMerchantUser(ori, outletVo, req.getResourceIds());
		if (!res.getResult().getResultCode().equals(EnumTypes.success.getCode())) {
			throw new MerchantException(res.getResult().getResultCode(),res.getResult().getResultDesc());
		}
		return map;
	}
	
	
	/**
	 * 门店用户修改merchant_outlet_user_update
	 * 
	 * @param reqMap
	 * @param
	 */
	public Map<String, String>merchant_outlet_user_update(Update_Merchant_User_Req req) throws MerchantException, Exception {
		Map<String, String> map = new HashMap<String, String>();
		MerchantUserVo outletVo = new MerchantUserVo();
		TargetObjectFormat.copyProperties(req, outletVo);
		outletVo.setClientId(req.getClientId());
		outletVo.setMerchantId(req.getMerchantUser().getMerchantId());
		outletVo.setOperatorUserId(req.getOperatorUserId());
		
		OriginVo ori = new OriginVo();
		ori.setOperatorId(req.getMerchantUser().getId());
		ori.setOperatorIp(req.getIp());
		ori.setPlatType(req.getPlatType());
		
		ResultVo res = finityMerchantUserService.updateMerchantUser(ori, outletVo, req.getResourceIds());
		if (!res.getResultCode().equals(EnumTypes.success.getCode())) {
			throw new MerchantException(res.getResultCode(),res.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 用户禁启用
	 * @tilte merchant_outlet_user_status
	 * @author zxl
	 * @date 2015年5月16日 下午2:20:42
	 * @param req
	 * @return
	 * @throws MerchantException
	 * @throws Exception
	 */
	public Map<String, String>merchant_outlet_user_update_status(Update_Merchant_User_Status_Req req) throws MerchantException, Exception {
		Map<String, String> map = new HashMap<String, String>();
		MerchantUserVo outletVo = new MerchantUserVo();
		outletVo.setId(Long.valueOf(req.getId()));
		outletVo.setIsDelete(req.getIsDelete());
		outletVo.setClientId(req.getClientId());
		outletVo.setMerchantId(req.getMerchantUser().getMerchantId());
		
		OriginVo ori = new OriginVo();
		ori.setOperatorId(req.getMerchantUser().getId());
		ori.setOperatorIp(req.getIp());
		ori.setPlatType(req.getPlatType());
		
		ResultVo res = merchantUserService.updateMerchantUser(ori,outletVo);
		if (!res.getResultCode().equals(EnumTypes.success.getCode())) {
			throw new MerchantException(res.getResultCode(),res.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 门店用户删除merchant_outlet_user_delete
	 * 
	 * @param reqMap
	 * @param
	 */
	public Map<String, String> merchant_outlet_user_delete(Del_Merchant_User_Req req) throws MerchantException, Exception {
		Map<String, String> map = new HashMap<String, String>();
		MerchantUserVo outletVo = new MerchantUserVo();
		outletVo.setId(Long.valueOf(req.getId()));
		outletVo.setClientId(req.getClientId());
		outletVo.setMerchantId(req.getMerchantUser().getMerchantId());
		outletVo.setOutletId(req.getOutletId());
		
		OriginVo ori = new OriginVo();
		ori.setOperatorId(req.getMerchantUser().getId());
		ori.setOperatorIp(req.getIp());
		ori.setPlatType(req.getPlatType());
		
		ResultVo res =merchantUserService.deleteMerchantUser(ori,outletVo);
		if (!res.getResultCode().equals(EnumTypes.success.getCode())) {
			throw new MerchantException(res.getResultCode(),res.getResultDesc());
		}
		return map;
	}
	
	
	/**
	 * 查询操作人 根据用户id查询
	 * 
	 * @param reqMap
	 * @param
	 */
	public String query_user_by_userId(long userId) throws MerchantException, Exception {
		MerchantUserVo res =merchantUserService.getMerchantUserById(userId);
		if (res!=null)return res.getUsername();
		return "--";
	}
}
