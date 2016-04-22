package com.froad.cbank.coremodule.normal.boss.support.merchant;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.merchant.MerchantUserInfoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.merchant.MerchantUserInfoVo;
import com.froad.cbank.coremodule.normal.boss.pojo.merchant.OutletCommentVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.merchant.OutletCommentVoRes;
import com.froad.cbank.coremodule.normal.boss.pojo.merchant.OutletInfoVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.merchant.OutletInfoVoRes;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.thrift.service.AreaService;
import com.froad.thrift.service.BossMerchantService;
import com.froad.thrift.service.MerchantService;
import com.froad.thrift.service.MerchantUserService;
import com.froad.thrift.service.OutletCommentService;
import com.froad.thrift.vo.MerchantUserPageVoRes;
import com.froad.thrift.vo.MerchantUserVo;
import com.froad.thrift.vo.OutletCommentPageVoRes;
import com.froad.thrift.vo.OutletCommentVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.merchant.BossMerchantOutletReqVo;
import com.froad.thrift.vo.merchant.BossMerchantOutletResVo;
import com.froad.thrift.vo.merchant.BossMerchantOutletVo;

/**
 * 类描述：商户门店相关
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015
 * @author: f-road
 * @time: 2015-5-1下午3:01:12
 */
@Service
public class MerchantOutletSupport {
	@Resource
	OutletCommentService.Iface outletCommentService;

	@Resource
	private BossMerchantService.Iface  bossMerchantService;	

	@Resource
	private MerchantService.Iface merchantService;

	@Resource
	private MerchantUserService.Iface merchantUserService;

	@Resource
	private AreaService.Iface areaService;
	/**
	 * 方法描述：商户门店评价列表分页查询
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘超 liuchao@f-road.com.cn
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws TException 
	 * @time: 2015年4月27日 下午4:43:27
	 */
	public HashMap<String, Object> queryList(OutletCommentVoReq req) throws IllegalAccessException, InvocationTargetException, TException {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		// 分页对象转换
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(req.getPageNumber()==0?1:req.getPageNumber());
		pageVo.setPageSize(req.getPageSize()==0?10:req.getPageSize());
		pageVo.setFirstRecordTime(req.getFirstRecordTime());
		pageVo.setLastPageNumber(req.getLastPageNumber());
		pageVo.setLastRecordTime(req.getLastRecordTime());
		pageVo.setBegDate(req.getBegDate()==0?req.getStartDate():req.getBegDate());
		pageVo.setEndDate(req.getEndDate());

		Page page = new Page();
		// List<OutletCommentVo> list= null;
		List<OutletCommentVoRes> merchantCommentList = null;
		OutletCommentVoRes res = null;
		OutletCommentPageVoRes outletCommentRes = outletCommentService.getOutletCommentByPage(pageVo, req.getOutletCommentVo());
		// 分页实体转换
		BeanUtils.copyProperties(page, outletCommentRes.getPage());
		// 查询数据集数据集转换
		if (!ArrayUtil.empty(outletCommentRes.getOutletCommentVoList())) {
			merchantCommentList = new ArrayList<OutletCommentVoRes>();
			for (OutletCommentVo temp : outletCommentRes
					.getOutletCommentVoList()) {
				res = new OutletCommentVoRes();
				// BeanUtils.copyProperties(res, temp);
				res.setCommentId(temp.getId());
				res.setClientName(temp.getClientId());
				res.setMerchantName(temp.getMerchantName());
				// res.setBusinessType(temp.get)
				if(StringUtil.isNotBlank(temp.getCreateTime())){
					res.setCommentTime(String.valueOf(temp.getCreateTime()));
				}
				res.setCommentContent(temp.getCommentDescription());
				res.setStarLevel(temp.getStarLevel());
				if(StringUtil.isNotBlank(temp.getRecomment())){
					res.setCommentStatus("已回复");						
				}else{
					res.setCommentStatus("未回复");
				}

				merchantCommentList.add(res);
			}
		}
		resMap.put("page", page);
		resMap.put("merchantCommentList", merchantCommentList);
		return resMap;
	}

	/**
	 * 方法描述：商户门店列表查询
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: f-road.com.cn
	 * @throws TException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @time: 2015年4月27日 下午4:43:27
	 */
	public HashMap<String, Object> queryOutletList(OutletInfoVoReq req) throws Exception {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		// 分页对象转换
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(req.getPageNumber() );
		pageVo.setPageSize(req.getPageSize());
		pageVo.setFirstRecordTime(req.getFirstRecordTime());
		pageVo.setLastPageNumber(req.getLastPageNumber());
		pageVo.setLastRecordTime(req.getLastRecordTime());
		
		//商户条件筛选
		BossMerchantOutletReqVo outletVo = new BossMerchantOutletReqVo();
		
		outletVo.setClientId(req.getClientId());	
		
		List<String> orgs = new ArrayList<String>();
		
		for(String org:req.getOrgCodes().split(",")){
			orgs.add(org);
		}
		
		outletVo.setOrgCodes(orgs);
		
		if(req != null && StringUtil.isNotBlank(req.getAreaId())){
			outletVo.setAreaId(Long.valueOf(req.getAreaId()));	
		}
		if(req != null && StringUtil.isNotBlank(req.getMerchantName())){
			outletVo.setMerchantName(req.getMerchantName());
		}
		
		BossMerchantOutletResVo resp = bossMerchantService.getMerchantOutletByPage(outletVo, pageVo);
		
		LogCvt.info("商户门店查询列表查询返回数据："+JSON.toJSONString(resp));
		
		if(Constants.RESULT_SUCCESS.equals(resp.getResultVo().getResultCode())){
			List<OutletInfoVoRes> list = new ArrayList<OutletInfoVoRes>();
			// 分页实体转换
			Page pageRes = new Page();
			BeanUtils.copyProperties(pageRes, resp.getPageVo());
			
			for(BossMerchantOutletVo vo:resp.getOutletVos()){
				OutletInfoVoRes res = new OutletInfoVoRes();
				res.setStoreName(vo.getOutletName());
				res.setMerchantName(vo.getMerchantName());
				res.setMerchantId(vo.getMerchantId());
				res.setCreateTime(vo.getCreateTime()+"");
				res.setAreaId(vo.getAreaName());

				list.add(res);
			}
			
			resMap.put("page", pageRes);
			resMap.put("storeList", list);
		}else{
			throw new BossException(resp.getResultVo().getResultCode(), resp.getResultVo().getResultDesc());
		}
		return resMap;
	}

	/**
	 * 方法描述：商户门店用户列表查询
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: f-road.com.cn
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws TException 
	 * @time: 2015年5月3日 下午4:43:27
	 */
	public HashMap<String, Object> queryOutletUserList(MerchantUserInfoReq req) throws IllegalAccessException, InvocationTargetException, TException {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		// 分页对象转换
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(req.getPageNumber()==0?1:req.getPageNumber());
		pageVo.setPageSize(req.getPageSize()==0?10:req.getPageSize());
		pageVo.setFirstRecordTime(req.getFirstRecordTime());
		pageVo.setLastPageNumber(req.getLastPageNumber());
		pageVo.setLastRecordTime(req.getLastRecordTime());

		Page page = new Page();
		List<MerchantUserInfoVo> merchantUserInfoVoList = null;
		MerchantUserInfoVo res = null;
		MerchantUserPageVoRes outletUserPageRes = merchantUserService.getMerchantUserByPage(pageVo, req.getMerchantUserVo());
		LogCvt.info("商户门店用户列表查询后端返回数据："+JSON.toJSONString(outletUserPageRes));
		// 分页实体转换
		BeanUtils.copyProperties(page, outletUserPageRes.getPage());

		// 查询数据集数据集转换
		if (!ArrayUtil.empty(outletUserPageRes.getMerchantUserVoList())) {
			merchantUserInfoVoList = new ArrayList<MerchantUserInfoVo>();
			for (MerchantUserVo temp : outletUserPageRes
					.getMerchantUserVoList()) {
				res = new MerchantUserInfoVo();
				res.setClientId(temp.getClientId());
				res.setCreateTime(StringUtil.isNotBlank(temp
						.getCreateTime()) ? String.valueOf(temp
						.getCreateTime()) : "");
				// res.setDelete(temp.isIsDelete());
				res.setEmail(temp.getEmail());
				res.setMerchantId(temp.getMerchantId());
				if (StringUtil.isNotBlank(temp.getId())
						&& temp.getId() != 0) {
					String id = String.valueOf(temp.getId());
					res.setId(id);
				}
				// res.setLastLogin(temp.);
				// res.setLocal(temp.i);
				res.setMerchantId(temp.getMerchantId());
				res.setMerchantName(temp.getMerchantName());
				if (StringUtil.isNotBlank(temp.getMerchantRoleId())
						&& temp.getMerchantRoleId() != 0) {
					String roleid = String
							.valueOf(temp.getMerchantRoleId());
					res.setMerchantRoleId(roleid);
				}
				res.setOrgCode(temp.getOrgCode());
				res.setOutletId(temp.getOutletId());
				res.setOutletName(temp.getOutletName());
				res.setRealname(temp.getRealname());
				res.setRest(temp.isIsRest());
				res.setUse(temp.isIsDelete());
				res.setUserName(temp.getUsername());
				res.setPhone(temp.getPhone());

				merchantUserInfoVoList.add(res);
			}
		}
		resMap.put("page", page);
		resMap.put("merchantUserList", merchantUserInfoVoList);
		return resMap;
	}
	
}
