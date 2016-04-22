package com.froad.cbank.coremodule.normal.boss.support.merchant;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.common.BankOrgInfoVo;
import com.froad.cbank.coremodule.normal.boss.pojo.merchant.MerchantDetailRes;
import com.froad.cbank.coremodule.normal.boss.pojo.merchant.MerchantListRes;
import com.froad.cbank.coremodule.normal.boss.pojo.merchant.MerchantReq;
import com.froad.cbank.coremodule.normal.boss.pojo.merchant.MerchantTypeRes;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.thrift.service.AreaService;
import com.froad.thrift.service.BossMerchantService;
import com.froad.thrift.service.MerchantAccountService;
import com.froad.thrift.service.MerchantService;
import com.froad.thrift.service.MerchantTypeService;
import com.froad.thrift.service.MerchantUserService;
import com.froad.thrift.service.OrgService;
import com.froad.thrift.vo.AreaVo;
import com.froad.thrift.vo.CategoryInfoVo;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.MerchantTypeVo;
import com.froad.thrift.vo.MerchantUserVo;
import com.froad.thrift.vo.OrgVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.TypeInfoVo;
import com.froad.thrift.vo.merchant.BossMerchantAccountResVo;
import com.froad.thrift.vo.merchant.BossMerchantDetailResVo;
import com.froad.thrift.vo.merchant.BossMerchantInfoVo;
import com.froad.thrift.vo.merchant.BossMerchantPageReqVo;
import com.froad.thrift.vo.merchant.BossMerchantPageResVo;

/**
 * 类描述：商户支持接口
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2015年4月27日 下午4:36:41 
 */
@Service
public class MerchantSupport {	
	
	@Resource
	private BossMerchantService.Iface  bossMerchantService;	
	@Resource
	private MerchantService.Iface  merchantService;
	@Resource
	private MerchantTypeService.Iface  merchantTypeService;	
	@Resource
	private MerchantUserService.Iface  merchantUserService;	
	@Resource
	private MerchantAccountService.Iface  merchantAccountService;
	@Resource
	private OrgService.Iface  orgService;
	@Resource
	private AreaService.Iface areaService;
	
	
	/**
	 * 商户列表查询
	 * @tilte queryList
	 * @author zxl
	 * @date 2016年1月20日 上午9:34:01
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> queryList(MerchantReq req) throws Exception{
		HashMap<String, Object> resMap=new HashMap<String, Object>();				
		//分页对象转换
		PageVo pageVo=new PageVo();
		pageVo.setPageNumber(req.getPageNumber());
		pageVo.setPageSize(req.getPageSize());
		pageVo.setFirstRecordTime(req.getFirstRecordTime());
		pageVo.setLastPageNumber(req.getLastPageNumber());
		pageVo.setLastRecordTime(req.getLastRecordTime());		
		
		BossMerchantPageReqVo vo = new BossMerchantPageReqVo();
		vo.setClientId(req.getClientId());
		vo.setMerchantName(req.getMerchantName());
		
		List<String> orgs = new ArrayList<String>();
		
		for(String org:req.getOrgCodes().split(",")){
			orgs.add(org);
		}
		
		vo.setOrgCodes(orgs);
		
		BossMerchantPageResVo res = bossMerchantService.findMerchantByPage(vo,pageVo);
		
		if(Constants.RESULT_SUCCESS.equals(res.getResultVo().getResultCode())){
			List<MerchantListRes> list= new ArrayList<MerchantListRes>();
			Page page=new Page();
			BeanUtils.copyProperties(page, res.getPageVo());
			
			for(BossMerchantInfoVo v : res.getInfoVos()){
				MerchantListRes r=new MerchantListRes();
				if(StringUtil.isNotBlank(v.getAreaId()) && v.getAreaId() != 0){
					String areaid = String.valueOf(v.getAreaId());
					r.setAreaId(areaid);
					AreaVo avo = areaService.findAreaById(v.getAreaId());
					if(avo!=null){
						r.setAreaName(avo.getName());
					}
				}
				r.setMerchantFullName(v.getMerchantFullname());
				r.setMerchantName(v.getMerchantName());
				r.setMerchantId(v.getMerchantId());
				r.setCreateTime(v.getCreateTime()+"");
				list.add(r);
			}
			
			resMap.put("page", page);
			resMap.put("list", list);
		}else{
			throw new BossException(res.getResultVo().getResultCode(), res.getResultVo().getResultDesc());
		}
		
		return  resMap;
	}
		
		
	/**
	  * 方法描述：导出商户列表查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: f-road.com.cn
	 * @throws TException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	  * @time: 2015年4月27日 下午4:43:27
	  */
	public HashMap<String, Object> export(MerchantReq req ) throws Exception{
		HashMap<String, Object> resMap=new HashMap<String, Object>();				
		
		BossMerchantPageReqVo vo = new BossMerchantPageReqVo();
		vo.setClientId(req.getClientId());
		vo.setMerchantName(req.getMerchantName());
		
		List<String> orgs = new ArrayList<String>();
		
		for(String org:req.getOrgCodes().split(",")){
			orgs.add(org);
		}
		
		vo.setOrgCodes(orgs);
		
		ExportResultRes res = bossMerchantService.exportMerchantInfo(vo);
		if(Constants.RESULT_SUCCESS.equals(res.getResultVo().getResultCode())){
			resMap.put("code", Constants.RESULT_SUCCESS);
			resMap.put("url", res.getUrl());
		}else{
			throw new BossException(res.getResultVo().getResultCode(), res.getResultVo().getResultDesc());
		}
		
		return  resMap;
	}
		
		
	/**
	  * 方法描述：商户详情查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	 * @throws TException 
	  * @time: 2015年4月27日 下午4:48:56
	  */
	public HashMap<String, Object> detail(String merchantId) throws Exception{
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		
		BossMerchantDetailResVo resp = bossMerchantService.getMerchantDetail(merchantId);
		LogCvt.info("商户详情查询后端返回数据："+JSON.toJSONString(resp));
		if(Constants.RESULT_SUCCESS.equals(resp.getResultVo().getResultCode())){
			
			BossMerchantInfoVo resMerchantVo = resp.getInfoVo();
			
			//查询商户分类
			List<CategoryInfoVo> cateTmpList = merchantService.getMerchantCategoryInfo(merchantId);
			//查询商户类型
			List<TypeInfoVo> typeTmpList = merchantService.getMerchantTypeInfo(merchantId);
			//查询商户账户
			BossMerchantAccountResVo acct = bossMerchantService.getMerchantAccount(merchantId);
			
			MerchantDetailRes res=new MerchantDetailRes();
			String addressTemp = StringUtil.isNotBlank(resMerchantVo.getAddress())?resMerchantVo.getAddress():"";
			if(StringUtil.isNotBlank(resMerchantVo.getAreaId()) && resMerchantVo.getAreaId() != 0){
				String areaid = String.valueOf(resMerchantVo.getAreaId());
				res.setAreaId(areaid);
				AreaVo avo = areaService.findAreaById(resMerchantVo.getAreaId());
				if(avo != null && avo.getId() != 0){
					String[] treePath = null;	//获取省市区
					String[] treePathName = null;	//获取省市区名称
					if(StringUtils.isNotEmpty(avo.getTreePath()) && StringUtils.isNotEmpty(avo.getTreePathName())){
						treePath = avo.getTreePath().split(",");
						treePathName = avo.getTreePathName().split(",");
						if(treePath.length > 2 && treePathName.length > 2){
							res.setProOrgCode(treePath[0]);//省
							res.setProOrgName(treePathName[0]);//省名称
							res.setCityOrgCode(treePath[1]);//市
							res.setCityOrgName(treePathName[1]);//市名称
							res.setCountyOrgCode(String.valueOf(avo.getId()));//区
							res.setCountyOrgName(avo.getName());//区名称
							addressTemp = treePathName[0]+treePathName[1]+avo.getName()+resMerchantVo.getAddress();
						}else if(treePath.length > 1 && treePathName.length > 1){
							res.setProOrgCode(String.valueOf(avo.getParentId()));//省
							res.setProOrgName(avo.getTreePathName());//省名称
							res.setCityOrgCode(String.valueOf(avo.getId()));//市
							res.setCityOrgName(avo.getName());//市名称
							res.setCountyOrgName("");//区名称
							addressTemp = avo.getTreePathName() + avo.getName()+resMerchantVo.getAddress();
						}else{
							res.setProOrgCode(String.valueOf(avo.getId()));//省
							res.setProOrgName(avo.getName());//省名称
							res.setCityOrgName("");//市名称
							res.setCountyOrgName("");//区名称
							addressTemp = avo.getName()+resMerchantVo.getAddress();
						}
					}
				}
			}
			res.setAddress(resMerchantVo.getAddress());
			res.setAddressTemp(addressTemp);

			//商户分类
			if(cateTmpList!= null && cateTmpList.size()>0){
				res.setCategoryInfoList(cateTmpList);							
			}
			//商户类型
			if(typeTmpList!= null && typeTmpList.size()>0){
				res.setTypeInfoVoList(typeTmpList);
			}
			//商户账户
			if(Constants.RESULT_SUCCESS.equals(acct.getResultVo().getResultCode())){
				res.setOpeningBank(acct.getAccountVo().getOpeningBank());
				res.setAcctName(acct.getAccountVo().getAcctName());
				res.setAcctNumber(acct.getAccountVo().getAcctNumber());
			}
			
			res.setAuditComment(resMerchantVo.getAuditComment());
			res.setAuditEndOrgCode(resMerchantVo.getAuditEndOrgCode());
			res.setAuditOrgCode(resMerchantVo.getAuditOrgCode());
			res.setAuditStaff(resMerchantVo.getAuditStaff());
			res.setAuditStage(resMerchantVo.getAuditStage());
			res.setAuditStartOrgCode(resMerchantVo.getAuditStartOrgCode());
			res.setAuditState(resMerchantVo.getAuditState());
			res.setAuditTime(resMerchantVo.getAuditTime());
			res.setComplaintPhone(resMerchantVo.getComplaintPhone());
			if(StringUtil.isNotBlank(resMerchantVo.getContractBegintime())){
				res.setContractBegintime(String.valueOf(resMerchantVo.getContractBegintime()));
			}else{
				res.setContractBegintime("");
			}
			if(StringUtil.isNotBlank(resMerchantVo.getContractEndtime())){
				res.setContractEndtime(String.valueOf(resMerchantVo.getContractEndtime()));
			}else{
				res.setContractBegintime("");
			}
			res.setBankType(resMerchantVo.getClientId());
			res.setContactEmail(resMerchantVo.getContactEmail());
			res.setContactName(resMerchantVo.getContactName());
			res.setContactPhone(resMerchantVo.getContactPhone());
			res.setContractStaff(resMerchantVo.getContractStaff());
			res.setCreateTime(StringUtil.isNotBlank(resMerchantVo.getCreateTime())?String.valueOf(resMerchantVo.getCreateTime()):"");
			res.setDisableStatus(resMerchantVo.getDisableStatus());
			res.setEndAuditTime(resMerchantVo.getEndAuditTime());
			res.setEndCreateTime(resMerchantVo.getEndCreateTime());
			res.setIntroduce(resMerchantVo.getIntroduce());
			res.setEnable(resMerchantVo.isIsEnable());
			res.setTop(resMerchantVo.isIsTop());
			res.setLegalCredentNo(resMerchantVo.getLegalCredentNo());
			if(StringUtil.isNotBlank(resMerchantVo.getLegalCredentType())){
				res.setLegalCredentType(String.valueOf(resMerchantVo.getLegalCredentType()));
			}
			res.setLegalName(resMerchantVo.getLegalName());
			res.setLicense(resMerchantVo.getLicense());
			res.setLogo(resMerchantVo.getLogo());
			res.setMerchantFullName(resMerchantVo.getMerchantFullname());
			res.setMerchantId(resMerchantVo.getMerchantId());
			res.setMerchantName(resMerchantVo.getMerchantName());
			res.setMerchantStatus(resMerchantVo.isMerchantStatus());
			res.setNeedReview(resMerchantVo.isNeedReview());
			res.setOrgCode(resMerchantVo.getOrgCode());
			res.setPhone(resMerchantVo.getPhone());
			res.setReviewStaff(resMerchantVo.getReviewStaff());
			res.setStartAuditTime(resMerchantVo.getStartAuditTime());
			res.setStartCreateTime(resMerchantVo.getStartCreateTime());
			res.setTaxReg(resMerchantVo.getTaxReg());
			
			//商户管理员
			List<String>  listId = new ArrayList<String>();
			listId.add(resMerchantVo.getMerchantId());
			List<MerchantUserVo> listUser = merchantUserService.getMerchantUserByRoleIdAndMerchantIds(100000000l, listId);
			LogCvt.info("商户详情查询之查询商户管理员后端返回数据："+JSON.toJSONString(listUser));
			
			if(listUser!=null && listUser.size() > 0){
				MerchantUserVo userVo = new MerchantUserVo();
				userVo = listUser.get(0);
				res.setUserName(userVo.getUsername());
				res.setLoginPhone(userVo.getPhone());
			}
			
			resMap.put("merchanrDetailRes", res);
			
		}else{
			throw new BossException(resp.getResultVo().getResultCode(), resp.getResultVo().getResultDesc());
		}
		
		return  resMap;
	}
		
	
	/**
	 * 根据客户端ID获取商户类型
	 * @param clientId
	 * @return
	 * @throws TException
	 */
	public HashMap<String, Object> getMerchantType(String clientId) throws TException{
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		MerchantTypeVo mt = new MerchantTypeVo();
		mt.setClientId(clientId);
		mt.setIsDelete(false);
		List<MerchantTypeRes> resList = new ArrayList<MerchantTypeRes>();
		MerchantTypeRes mres = null;
		List<MerchantTypeVo> list = merchantTypeService.getMerchantType(mt);
		for(MerchantTypeVo mtVo : list) {
			mres = new MerchantTypeRes();
			mres.setId(mtVo.getId()+"");
			mres.setTypeName(mtVo.getTypeName());
			resList.add(mres);
		}
		resMap.put("merchantTypeList", resList);
		return  resMap;
	}
	
	
	/**
	 * 批量获取机构名称
	 * @param clientId
	 * @param orgLevel
	 * @return
	 * @throws TException 
	 */
	public List<BankOrgInfoVo> getByOrgCode(String clientId,List<String> orgCodeList) throws TException{
		List<BankOrgInfoVo> bankOrgList = new ArrayList<BankOrgInfoVo>();
		if(orgCodeList != null && orgCodeList.size() > 0){
			List<OrgVo> orgList = orgService.getOrgByList(clientId, orgCodeList);
			LogCvt.info("orgService.getOrgByList批量获取机构名称后端返回参数"+JSON.toJSONString(orgList));
			if(orgList != null && orgList.size() > 0){
				for(OrgVo org : orgList){
					BankOrgInfoVo bankOrg = new BankOrgInfoVo();
					bankOrg.setOrgCode(org.getOrgCode());
					bankOrg.setOrgName(org.getOrgName());
					bankOrgList.add(bankOrg);
				}
			}
		}
		return bankOrgList;
	}
}
