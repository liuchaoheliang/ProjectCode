package com.froad.cbank.coremodule.module.normal.merchant.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.merchant.enums.CertificateType;
import com.froad.cbank.coremodule.module.normal.merchant.enums.MerchantDisableStatusEnum;
import com.froad.cbank.coremodule.module.normal.merchant.enums.MerchantType;
import com.froad.cbank.coremodule.module.normal.merchant.enums.OutletDisableStatusEnum;
import com.froad.cbank.coremodule.module.normal.merchant.enums.UserType;
import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Add_Outlet_Info_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.BasePojo;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Fallow_Execute_Base_Data_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Fallow_Execute_Service_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Image_Info_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Image_Info_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.MerchantUser;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Outlet_Temp_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.PageRes;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Area_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Merchant_Info_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Merchant_Info_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Outle_Category_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Outlet_Category_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Outlet_Detail_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Outlet_Info_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Outlet_Info_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Update_Outlet_Info_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Verify_AccountNum_Req;
import com.froad.cbank.coremodule.module.normal.merchant.utils.EnumTypes;
import com.froad.cbank.coremodule.module.normal.merchant.utils.HandleOutlet;
import com.froad.cbank.coremodule.module.normal.merchant.utils.PramasUtil;
import com.froad.cbank.coremodule.module.normal.merchant.utils.TargetObjectFormat;
import com.froad.thrift.service.AreaService;
import com.froad.thrift.service.BankCardService;
import com.froad.thrift.service.FallowExecuteService;
import com.froad.thrift.service.MerchantAccountService;
import com.froad.thrift.service.MerchantCategoryService;
import com.froad.thrift.service.MerchantOutletPhotoService;
import com.froad.thrift.service.MerchantService;
import com.froad.thrift.service.OrgService;
import com.froad.thrift.service.OutletService;
import com.froad.thrift.service.ProcessService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.vo.AreaVo;
import com.froad.thrift.vo.CategoryInfoVo;
import com.froad.thrift.vo.CreateInstanceReqVo;
import com.froad.thrift.vo.CreateInstanceResVo;
import com.froad.thrift.vo.MerchantAccountAddVoRes;
import com.froad.thrift.vo.MerchantAccountVo;
import com.froad.thrift.vo.MerchantCategoryVo;
import com.froad.thrift.vo.MerchantDetailVo;
import com.froad.thrift.vo.MerchantOutletPhotoVo;
import com.froad.thrift.vo.MerchantVo;
import com.froad.thrift.vo.OrgVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.OutletAddVoRes;
import com.froad.thrift.vo.OutletPageVoRes;
import com.froad.thrift.vo.OutletTempAddVoRes;
import com.froad.thrift.vo.OutletTempVo;
import com.froad.thrift.vo.OutletVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.TypeInfoVo;

/**
 * pc 门店service
 * 
 * @author args
 * @date 23/03/2015
 */
@Service
public class Outlet_Service {
	
	@Resource
	AreaService.Iface areaService;
	
	@Resource
	OutletService.Iface outletService;

	@Resource
	MerchantOutletPhotoService.Iface merchantOutletPhotoService;
	
	@Resource
	MerchantService.Iface merchantService;
	
	@Resource
	MerchantAccountService.Iface merchantAccountService;
	@Resource
	BankCardService.Iface bankCardService;

	@Resource
	FallowExecuteService.Iface fallowExecuteService;	
	
	@Resource
	OrgService.Iface orgService;
	
	@Resource
	Common_Service common_Service;
	@Resource
	Product_Service product_Service;
	@Resource
	Login_Service login_Service;
	
	
	@Resource
	ProductService.Iface productService;
	
	@Resource
	MerchantCategoryService.Iface merchantCategoryService;
	@Resource
	ProcessService.Iface processService;
	/**
	 * 查询商户基本信息query_merchant_info
	 * 
	 * @param reqMap
	 * @param
	 * @throws TException 
	 */
	public Map<String,Object> query_merchant_info(Query_Merchant_Info_Req req) throws MerchantException, TException {
		Map<String,Object> map=new HashMap<String,Object>();
		MerchantVo vo = merchantService.getMerchantByMerchantId(req.getMerchantUser().getMerchantId());
		if(vo == null){
			throw new MerchantException(EnumTypes.noresult.getCode(), EnumTypes.noresult.getMsg());
		}else{
			Query_Merchant_Info_Res  pojo=new Query_Merchant_Info_Res();
			TargetObjectFormat.copyProperties(vo, pojo);
	        pojo.setIsEnable(vo.isIsEnable());
	        pojo.setLegalCredentType(CertificateType.getDescribeByCode(vo.getLegalCredentType()+""));
			//查询商户详情
			MerchantDetailVo  resv=merchantService.getMerchantDetail(req.getMerchantUser().getMerchantId());
			LogCvt.info("查询商户详情接收server端参数:  "+JSON.toJSONString(resv));
			if(resv.getCategoryInfo()!=null&&resv.getCategoryInfo().size()>0)pojo.setCategory(resv.getCategoryInfo().get(0).getName());
			if(resv.getTypeInfo()!=null&&resv.getTypeInfo().size()>0){
				StringBuffer sb=new StringBuffer();
				for(TypeInfoVo tyv:resv.getTypeInfo()){
					sb.append(tyv.getTypeName()).append("/");
				}
				pojo.setMerchantType(sb.length()>0?sb.substring(0, sb.length()-1):"");
			}
			pojo.setOrgName(resv.getOrgName());
			if(resv.getTreePathName()!=null)pojo.setArea(getArea(resv.getTreePathName()));
			
			//帐号
			MerchantAccountVo merchantAccountVo = new MerchantAccountVo();
			merchantAccountVo.setClientId(req.getClientId());
			merchantAccountVo.setMerchantId(req.getMerchantUser().getMerchantId());
			List<MerchantAccountVo> r = merchantAccountService.getMerchantAccount(merchantAccountVo);
			if(r!=null&&r.size()>0){
				pojo.setForAccount(r.get(0).getAcctNumber());
				pojo.setAccountOrgNo(r.get(0).getOpeningBank());
			}
			map.put("merchantInfo",pojo);
			return map;
		}

	}
	
	/**
	 * 获取商户类型
	 * 100000000	团购
     * 100000001	面对面
     * 100000002	名优特惠
	 * @param reqMap
	 * @param
	 * @throws TException 
	 */
	public void query_merchant_type(String id) throws MerchantException, TException {
		    boolean isgroup=false;
			//查询商户详情
			MerchantDetailVo  resv=merchantService.getMerchantDetail(id);
			if(resv.getTypeInfo()!=null&&resv.getTypeInfo().size()>0){
				for(TypeInfoVo tyv:resv.getTypeInfo()){
					if(MerchantType.TH_Merchant.getCode().equals(Long.toString(tyv.getMerchantTypeId()))){
						isgroup=true;
						break;
					}	
				}
				if(!isgroup){
					throw new MerchantException(EnumTypes.authority.getCode(), EnumTypes.authority.getMsg());
				}
			}
	}
	
	/**
	 * 商户门店查询 query_merchant_outlet
	 * 
	 * @param reqMap
	 * @param
	 */
	public Map<String, Object> query_merchant_outlet(Query_Outlet_Info_Req req) throws MerchantException, Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		OutletVo outletVo = new OutletVo();
		TargetObjectFormat.copyProperties(req, outletVo);
		outletVo.setClientId(req.getClientId());
		/**管理员只看自己所属门店  这里用“非”为了兼容数据*/
		if(!req.getMerchantUser().getMerchantRoleId().equals(UserType.admin.getCode())){
			outletVo.setOutletId(req.getMerchantUser().getOutletId());
		}else{
			outletVo.setOutletId(null);
		}
		outletVo.setMerchantId(req.getMerchantUser().getMerchantId());
		outletVo.setAuditState(req.getAuditState());
//		0=待审核 ,1=审核通过 , 2=审核不通过 , 3=未提交 , 4=删除，5=全部
		List<String> disableStatusList = new ArrayList<String>();

		disableStatusList.add(OutletDisableStatusEnum.normal.getCode());
		disableStatusList.add(OutletDisableStatusEnum.disable.getCode());
		if("4,5".indexOf(req.getAuditState())!=-1){
			disableStatusList.add(OutletDisableStatusEnum.delete.getCode());
		}
		outletVo.setDisableStatusList(disableStatusList);
		
		PageVo pageVo=new PageVo();
		pageVo.setPageNumber(req.getPageNumber());
		pageVo.setPageSize(req.getPageSize());
		pageVo.setLastPageNumber(req.getLastPageNumber());
		pageVo.setFirstRecordTime(req.getFirstRecordTime());
		pageVo.setLastRecordTime(req.getLastRecordTime());
		OutletPageVoRes listOutletPage= outletService.getOutletByPage(pageVo,outletVo);
		LogCvt.info("门店列表server返回============:"+JSON.toJSONString(listOutletPage.getOutletVoList()));
		if(listOutletPage!=null&&listOutletPage.getOutletVoList().size()>0){
			List<Query_Outlet_Info_Res> listRes=new ArrayList<Query_Outlet_Info_Res>();
			for(OutletVo vo:listOutletPage.getOutletVoList()){
				Query_Outlet_Info_Res res=new Query_Outlet_Info_Res();
				TargetObjectFormat.copyProperties(vo, res);
				res.setEnable(vo.isIsEnable());
				if(vo.isIsDefault()){
					res.setIsDefault("1");//默认门店
				}else{
					res.setIsDefault("0");//不是默认门店
				}
				
				if(vo.getAreaId()!=0){
					res.setAreaName(query_areaId_by_cityId(String.valueOf(vo.getAreaId())));
				}else{
					res.setAreaName("");
				}
				listRes.add(res);
			}
			map.put("outletList", listRes);
			PageRes pageRes = new PageRes();
			TargetObjectFormat.copyProperties(listOutletPage.getPage(), pageRes);
			map.put("page",pageRes);
		}else{
			throw new MerchantException(EnumTypes.noresult.getCode(), EnumTypes.noresult.getMsg());
		}
		
		return map;
	}

	
	/**
	 * 查询商户所有门店 query_merchant_outlet
	 * 只拿门店id和门店名称
	 * @param reqMap
	 * @param
	 */
	public Map<String, Object> query_merchant_outlet_all(Query_Outlet_Info_Req req) throws MerchantException, Exception {
		Map<String, Object> mapList = new HashMap<String, Object>();
		OutletVo outletVo = new OutletVo();
		outletVo.setClientId(req.getClientId());
		outletVo.setMerchantId(req.getMerchantUser().getMerchantId());
		List<String> disableStatusList = new ArrayList<String>();
		disableStatusList.add(OutletDisableStatusEnum.normal.getCode());
		disableStatusList.add(OutletDisableStatusEnum.disable.getCode());
		outletVo.setDisableStatusList(disableStatusList);
		
		if(!req.getMerchantUser().getIsSuperAdmin()){
			outletVo.setOutletId(req.getMerchantUser().getOutletId());
		}
		List<OutletVo> listOutletPage= outletService.getOutlet(outletVo);
		if(listOutletPage!=null&&listOutletPage.size()>0){
			List<Query_Outlet_Info_Res> listRes=new ArrayList<Query_Outlet_Info_Res>();
			for(OutletVo vo:listOutletPage){
				Query_Outlet_Info_Res res=new Query_Outlet_Info_Res();
				res.setOutletName(vo.getOutletName());
				res.setOutletId(vo.getOutletId());
				res.setId(vo.getId());
				res.setEnable(vo.isIsEnable());
				
				//审核状态  审核状态只有审核通过才能显示（）
				OutletVo listOutletVo=outletService.getOutletByOutletId(vo.getOutletId());
				if(listOutletVo!=null){
					//审核通过
					if(listOutletVo.getAuditState().equals("1")||listOutletVo.getAuditState().equals("null")||StringUtils.isEmpty(listOutletVo.getAuditState())){
						listRes.add(res);
					}
				}
				
			}
			mapList.put("outletList", listRes);
			return mapList;
		}else {
		    throw new MerchantException(EnumTypes.noresult.getCode(),EnumTypes.noresult.getMsg());
		}
		
	}

	/**
	 * 商户门店添加merchant_outlet_add
	 * 
	 * @param reqMap
	 * @param
	 * @throws Exception 
	 */
	public Map<String, Object> merchant_outlet_add_(Add_Outlet_Info_Req req) throws MerchantException, Exception {
		//检查商户状态
		common_Service.checkMerchantStatus(req.getMerchantUser().getMerchantDisableStatus());
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<MerchantOutletPhotoVo>  listVo=new ArrayList<MerchantOutletPhotoVo>();
		OutletVo outletVo = new OutletVo();
		TargetObjectFormat.copyProperties(req, outletVo);
		outletVo.setClientId(req.getClientId());
		outletVo.setMerchantId(req.getMerchantUser().getMerchantId());
		outletVo.setOutletStatus(false);
		outletVo.setPreferStartPeriod(PramasUtil.DateFormat(req.getPreferStartPeriod()));
		outletVo.setPreferEndPeriod(PramasUtil.DateFormat(req.getPreferEndPeriod()));
		outletVo.setIsEnable(false);
		outletVo.setDisableStatus(OutletDisableStatusEnum.normal.getCode());
		
		//如果是直接优惠商户 优惠折扣和优惠详情不能为空
		String merchantTypes = login_Service.query_merchant_types(req.getMerchantUser().getMerchantId());
		if(merchantTypes.indexOf(MerchantType.ZJYH_Merchant.getCode()) > -1){  
			//2015-08-19 优惠折扣进行规范化   
			if(StringUtil.isBlank(req.getPreferDetails())){
				throw new MerchantException(EnumTypes.fail.getCode(),"优惠详情不能为空");
			}
			if(StringUtil.isBlank(req.getDiscountCode())){
				throw new MerchantException(EnumTypes.fail.getCode(),"优惠折扣不能为空");
			}
			if(StringUtil.isBlank(req.getDiscountRate())){
				throw new MerchantException(EnumTypes.fail.getCode(),"可享受折扣信息不能为空");
			}
		}  
		
		//2015-08-19 优惠折扣进行规范化   
		outletVo.setDiscountCode(req.getDiscountCode());
		outletVo.setDiscountRate(req.getDiscountRate());
		
		OriginVo ori = new OriginVo();
		ori.setOperatorId(req.getMerchantUser().getId());
		ori.setOperatorIp(req.getIp());
		ori.setPlatType(req.getPlatType());
		ori.setClientId(req.getClientId());
		
		/**2015-11-05 门店二级类目*/
		String[] categoryIds = req.getCategoryInfo();
		List<CategoryInfoVo> categoryInfo = new ArrayList<CategoryInfoVo>();
		if(categoryIds!=null&&categoryIds.length>0){
	        for(int i = 0;i<categoryIds.length;i++){
	    	    CategoryInfoVo vo = new CategoryInfoVo();
	    	    vo.setCategoryId(Long.parseLong(categoryIds[i]));
	    	    categoryInfo.add(vo);
	        }
		}

        LogCvt.info("门店类目ids:  "+JSON.toJSONString(categoryInfo));
        outletVo.setCategoryInfo(categoryInfo);
		
		//门店添加审核状态
		outletVo.setAuditState("3");
		OutletAddVoRes outletId = outletService.addOutlet(ori,outletVo);
		
		if (outletId!=null&&outletId.getResult().getResultCode().equals(EnumTypes.success.getCode())) {
			if(req.getImgList()!= null && req.getImgList().size()> 0){
				
				for (Image_Info_Req img : req.getImgList()) {
					MerchantOutletPhotoVo photoVo = new MerchantOutletPhotoVo();
					photoVo.setMedium(img.getMedium());
					photoVo.setMerchantId(req.getMerchantUser().getMerchantId());
					photoVo.setOutletId(outletId.getOutletId());
					photoVo.setLarge(img.getLarge());
					photoVo.setThumbnail(img.getThumbnail());
					photoVo.setIsDefault(img.getIsDefault());
					photoVo.setSource(img.getSource());
					listVo.add(photoVo);
				}
				merchantOutletPhotoService.addMerchantOutletPhotoByBatch(ori,listVo);
				
			}
			if(StringUtils.isNotBlank(req.getAcctName())&&StringUtils.isNotBlank(req.getAcctNumber())){
				MerchantAccountVo merchantAccountVo =setMerchantAccountVo(req.getClientId(),req.getMerchantUser().getMerchantId(),outletId.getOutletId(),req.getAcctNumber(),req.getAcctName());
				MerchantAccountAddVoRes r = merchantAccountService.addMerchantAccount(ori,merchantAccountVo);
				if(!r.getResult().getResultCode().equals(EnumTypes.success.getCode())){
					LogCvt.error("add account error:"+r.getResult().getResultDesc());
				}
			}
//		     * @param type门店操作类型（1:新增;2:修改;3:删除）
			outletVo.setOutletId(outletId.getOutletId());
			productService.updateGroupProductByOutlet(req.getClientId(), "1", outletVo);//商品关联门店
			
			map.put("outletId", outletId.getOutletId());
		}else {
			throw new MerchantException(outletId.getResult().getResultCode(),outletId.getResult().getResultDesc());
		}
		return map;

	}
	
	/**
	 * 商户门店修改merchant_outlet_update
	 * 
	 * @param reqMap
	 * @param
	 * @throws Exception 
	 */
	public Map<String, String> merchant_outlet_update_(Update_Outlet_Info_Req req) throws MerchantException, Exception {
		LogCvt.info("门店修改请求参数 ： "+JSON.toJSONString(req));
		//检查商户状态
		common_Service.checkMerchantStatus(req.getMerchantUser().getMerchantDisableStatus());

		Map<String, String> map = new HashMap<String, String>();
		OriginVo ori = new OriginVo();
		ori.setOperatorId(req.getMerchantUser().getId());
		ori.setOperatorIp(req.getIp());
		ori.setPlatType(req.getPlatType());
		ori.setClientId(req.getClientId());
		/**2015-11-11 老数据如果还是一级分类不可保存*/
		//查询商户详情
		MerchantDetailVo  merchant = merchantService.getMerchantDetail(req.getMerchantUser().getMerchantId());
		LogCvt.info("merchant对象返回server端返回; ： "+JSON.toJSONString(merchant.toString()));
		List<CategoryInfoVo> list= merchant.getCategoryInfo();
		if(null != list){
			Long mCateId = list.get(0).getCategoryId();
			if(StringUtil.isNotBlank(mCateId.toString())){
				String[] cateIds =req.getCategoryInfo();
				if(isHave(cateIds,mCateId.toString())){
					throw new MerchantException(EnumTypes.fail.getCode(),"请选择门店分类！");
				  }
			}
		}
		//如果是直接优惠商户 优惠折扣和优惠详情不能为空
		String merchantTypes = login_Service.query_merchant_types(req.getMerchantUser().getMerchantId());
		if(merchantTypes.indexOf(MerchantType.ZJYH_Merchant.getCode()) > -1){  
			//2015-08-19 优惠折扣进行规范化   
			if(StringUtil.isBlank(req.getPreferDetails())){
				throw new MerchantException(EnumTypes.fail.getCode(),"优惠详情不能为空");
			}
			if(StringUtil.isBlank(req.getDiscountCode())){
				throw new MerchantException(EnumTypes.fail.getCode(),"优惠折扣不能为空");
			}
			if(StringUtil.isBlank(req.getDiscountRate())){
				throw new MerchantException(EnumTypes.fail.getCode(),"可享受折扣信息不能为空");
			}
		}  
		
		OutletVo outletVo = new OutletVo();
		TargetObjectFormat.copyProperties(req, outletVo);
		outletVo.setClientId(req.getClientId());
		outletVo.setMerchantId(req.getMerchantUser().getMerchantId());
		outletVo.setPreferStartPeriod(PramasUtil.DateFormat(req.getPreferStartPeriod()));
		outletVo.setPreferEndPeriod(PramasUtil.DateFormat(req.getPreferEndPeriod()));
		outletVo.setIsEnable(false);
		String[] cateIds =req.getCategoryInfo();
		List<CategoryInfoVo> categoryInfo = new ArrayList<CategoryInfoVo>();
		String categoryIds = "";
		String categoryNames = "";
        for(int i = 0;i<cateIds.length;i++){
        	categoryIds += cateIds[i]+",";
        	MerchantCategoryVo cVo = merchantCategoryService.getMerchantCategoryById(req.getClientId(), Long.parseLong(cateIds[i]));
        	categoryNames += cVo.getName()+",";
        	CategoryInfoVo vo = new CategoryInfoVo();
    	    vo.setCategoryId(Long.parseLong(cateIds[i]));
    	    vo.setName(cVo.getName());
    	    categoryInfo.add(vo);
        }
        outletVo.setCategoryInfo(categoryInfo);

		LogCvt.info("门店修改请求参数outletVo ： "+JSON.toJSONString(outletVo));
		
		//门店修改审核状态 修改审核不通过/未提交 已编辑（编辑默认值为1）
        if("2,3".indexOf(req.getAuditState())!=-1)outletVo.setAuditState("3");
		if("1".indexOf(req.getAuditState())!=-1)outletVo.setEditAuditState("3");

    	//审核通过的需要加临时表数据存储
    	if(req.getAuditState().equals("1")){
    		Outlet_Temp_Req tempVo=new Outlet_Temp_Req();
    		TargetObjectFormat.copyProperties(req, tempVo);
    		tempVo.setAreaId(String.valueOf(req.getAreaId()));
    		tempVo.setAcctName(req.getAcctName());
    		tempVo.setAcctNumber(req.getAcctNumber());
    		tempVo.setMerchantId(req.getMerchantUser().getMerchantId());
    		tempVo.setPhotoList(JSON.toJSONString(req.getImgList()));
    		tempVo.setClientId(req.getClientId());
    		Query_Outlet_Detail_Res resold=(Query_Outlet_Detail_Res)queryOutlet(req.getOutletId(),req.getMerchantUser().getMerchantId(),req.getClientId());
    		tempVo.setPrimeval(JSON.toJSONString(resold));
    		tempVo.setCategoryIds(categoryIds);
    		tempVo.setCategoryNames(categoryNames);
    	    
    		return addOutletTemp(tempVo,ori);
    	}
//	     * @param clientId客户端id
//	     * @param type门店操作类型（1:新增;2:修改;3:删除）
		//只正对老数据
		if((StringUtils.isEmpty(outletVo.getAuditState())||outletVo.getAuditState().equals("null"))||(StringUtil.isEmpty(outletVo.getEditAuditState())||outletVo.getAuditState().equals("null"))){
			outletVo.setEditAuditState("1");
			outletVo.setAuditState("1");
		}
		
		productService.updateGroupProductByOutlet(req.getClientId(), "2", outletVo);//商品关联门店
		
		ResultVo outletId = outletService.updateOutlet(ori,outletVo);
		if (outletId!=null&&outletId.getResultCode().equals(EnumTypes.success.getCode())) {
			if((req.getImgList()!=null&& req.getImgList().size() > 0)){
				List<MerchantOutletPhotoVo>  listVo=new ArrayList<MerchantOutletPhotoVo>();
				for (Image_Info_Req img : req.getImgList()) {
					MerchantOutletPhotoVo photoVo = new MerchantOutletPhotoVo();
					photoVo.setMedium(img.getMedium());
					photoVo.setMerchantId(req.getMerchantUser().getMerchantId());
					photoVo.setOutletId(req.getOutletId());
					photoVo.setLarge(img.getLarge());
					photoVo.setThumbnail(img.getThumbnail());
					photoVo.setIsDefault(img.getIsDefault());
					photoVo.setSource(img.getSource());
					listVo.add(photoVo);
				}
				ResultVo r = merchantOutletPhotoService.saveMerchantOutletPhoto(ori,listVo);
				if(!r.getResultCode().equals(EnumTypes.success.getCode())){
					LogCvt.error("update photo error:"+r.getResultDesc());
				}
			}
			
			//查询原始收款帐号
			Query_Outlet_Detail_Res  oldres=queryOutlet(req.getOutletId(),req.getMerchantUser().getMerchantId(),req.getClientId());
			MerchantAccountVo merchantAccountVo=setMerchantAccountVo(req.getClientId(),req.getMerchantUser().getMerchantId(),req.getOutletId(),oldres.getAcctNumber(),oldres.getAcctName());
			if(StringUtils.isNotBlank(req.getAcctName())&&StringUtils.isNotBlank(req.getAcctNumber())){
				//查询是否存在，有就修改，没有就添加
				List<MerchantAccountVo> listMer = merchantAccountService.getMerchantAccount(merchantAccountVo);
				merchantAccountVo=setMerchantAccountVo(req.getClientId(),req.getMerchantUser().getMerchantId(),req.getOutletId(),req.getAcctNumber(),req.getAcctName());
				if(listMer!=null&&listMer.size()>0){
					merchantAccountService.updateMerchantAccount(ori,merchantAccountVo);
				}else{
					merchantAccountService.addMerchantAccount(ori,merchantAccountVo);
				}
			}else{//删除
				if(StringUtils.isNotEmpty(oldres.getAcctName()))
				merchantAccountService.deleteMerchantAccount(ori, merchantAccountVo);
			}
		} else {
			throw new MerchantException(outletId.getResultCode(),outletId.getResultDesc());
		}
		
		
		return map;

	}
	/**
	 * 商户门店删除 merchant_outlet_delete
	 * 
	 * @param reqMap
	 * @param
	 * @throws TException 
	 */
	public Map<String, String> merchant_outlet_delete(BasePojo req,String outletId,String clientId)
			throws MerchantException, Exception {
		Map<String, String> map=new HashMap<String,String>();
		OutletVo outletVo=new OutletVo();
		outletVo.setMerchantId(req.getMerchantUser().getMerchantId());
		outletVo.setOutletId(outletId);
		outletVo.setClientId(clientId);
		//查询门店信息
		OutletVo outlet = outletService.getOutletByOutletId(outletId);
		if(outlet.isDefault){
			throw new MerchantException(EnumTypes.fail.getCode(),"默认门店不能删除!");
		}
		
		OriginVo ori = new OriginVo();
		ori.setOperatorId(req.getMerchantUser().getId());
		ori.setOperatorIp(req.getIp());
		ori.setPlatType(req.getPlatType());
//	     * @param clientId客户端id
//	     * @param type门店操作类型（1:新增;2:修改;3:删除）
		productService.updateGroupProductByOutlet(clientId, "3", outletVo);//商品关联门店
		ResultVo isDelete=outletService.deleteOutlet(ori,outletVo);
		if (isDelete!=null&&isDelete.getResultCode().equals(EnumTypes.success.getCode())) {
			map.put("code", EnumTypes.success.getCode());
			map.put("message", EnumTypes.success.getMsg());			
			return map;
		} else {
			throw new MerchantException(isDelete.getResultCode(),isDelete.getResultDesc());
		}

	}

	
	/**
	 * 查询门店详情query_outlet_details
	 * type=1 详情，type=2 修改
	 * @param reqMap
	 * @param
	 * @throws Exception 
	 */
	public Map<String,Object> query_outlet_details(String merchantId,String outletId,String clientId,String type)
			throws MerchantException, Exception {
		   Map<String, Object> mapList = new HashMap<String, Object>();
		   Query_Outlet_Detail_Res pojo=new Query_Outlet_Detail_Res();
		   Query_Outlet_Detail_Res old=queryOutlet(outletId,merchantId,clientId);
		  //拿临时表数据（临时表有数据）
		   Map<String,Object> map=QueryOutletTemp(outletId);
		   if("2".equals(type)&&map!=null){
			   if(!old.getEditAuditState().equals("2")){
					pojo=(Query_Outlet_Detail_Res)map.get("newData");
					Query_Outlet_Detail_Res catoryRes = tcyCategory(pojo.getCategoryList(), merchantId, clientId);
					pojo.setCategoryList(catoryRes.getCategoryList());
					pojo.setIsBigCategory(catoryRes.getIsBigCategory());
					mapList.put("outletDetail", pojo);
					mapList.put("merchantId", merchantId);
					mapList.put("outletId", outletId);
					return mapList;
			   }
		   }
		   //拿老数据
		   pojo=old;
		   //查看详情修改值
			if(map!=null){
				mapList.put("Outlet_Temp_Res", HandleOutlet.getIsUpdate(pojo, (Query_Outlet_Detail_Res)map.get("newData"), map.get("category")+""));
			}
			
			/**2015-11-05 门店二级类目*/
			List<Query_Outlet_Category_Res> infoList= pojo.getCategoryList();
			List<Query_Outlet_Category_Res> listRes=  new ArrayList<Query_Outlet_Category_Res>(); 
			LogCvt.info("门店分类数据server端返回; ： "+JSON.toJSONString(infoList));
			if(null != infoList){
				for(Query_Outlet_Category_Res cvo : infoList){
					LogCvt.info("门店分类数据server端返回; ： "+JSON.toJSONString(cvo));
					Query_Outlet_Category_Res catRes = new Query_Outlet_Category_Res();
					MerchantCategoryVo mcVo = merchantCategoryService.getMerchantCategoryById(clientId, cvo.getCategoryId());
					pojo.setIsBigCategory(mcVo.getParentId() == 0 ? "1" : "0");
					catRes.setCategoryId(cvo.getCategoryId());
					catRes.setName(cvo.getName());
					listRes.add(catRes);
				}
			}
			pojo.setCategoryList(listRes);
			mapList.put("outletDetail", pojo);
			mapList.put("merchantId", merchantId);
			mapList.put("outletId", outletId);
			return mapList;
	}

	
	/**
	 * 根据区id查对象
	 * @param cityId
	 * @return
	 */
	public String query_areaId_by_cityId(String cityId) {
		try {
			AreaVo listvo = areaService.findAreaById(Long.valueOf(cityId));
			if (listvo != null) {
				// 区域id、name
				String[] pathid = listvo.getTreePath().split(",");
				String[] name = listvo.getTreePathName().split(",");
				StringBuffer sb = new StringBuffer();
				if (pathid.length > 0 && name.length > 0) {
					for (int i = 0; i < name.length; i++) {
						sb.append(name[i]);
					}
				}
				return sb.toString().substring(0, sb.toString().length());
			}
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	/**
	 * 根据id查询子集
	 * @param cityId
	 * @return
	 */
	public Map<String,Object> query_cityId_by_countyId(String countyId,String clientId)
	     throws MerchantException, Exception{
		Map<String,Object> mapList=new HashMap<String,Object>();
		//这里过滤掉非自己客户端的数据  08-03
		List<AreaVo> listvo=areaService.findChildrenInfo(StringUtil.isEmpty(countyId)?0:Long.valueOf(countyId),"",clientId);
		LogCvt.info("省市级联数据："+JSON.toJSONString(listvo.toString()));
		List<Query_Area_Res> listRes=new ArrayList<Query_Area_Res>();
		if(listvo!=null&&listvo.size()>0){
			for(AreaVo areavo:listvo){
				Query_Area_Res res=new Query_Area_Res();
				//区域id、name 
				String[] pathid=areavo.getTreePath().split(",");
				String[] name=areavo.getTreePathName().split(",");
				if(pathid.length>0&&name.length>0){
					if(pathid.length==1){
	                    res.setId(pathid[0]);
	                    res.setName(name[0]);
					}else if(pathid.length==2){
	                    res.setId(pathid[1]);
	                    res.setName(name[1]);
					}else if(pathid.length==3){
	                    res.setId(pathid[2]);
	                    res.setName(name[2]);
					}
				}
               listRes.add(res);
			}
			
		}
		mapList.put("areaList",listRes);
		return mapList;
	}
	
		
	/**
	 * 商户图片查询query_image
	 * @param cityId
	 * @return
	 */
	public Map<String,Object> query_image(String merchantId,String outletId)
	     throws MerchantException, Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		MerchantOutletPhotoVo merchantOutletPhotoVo=new MerchantOutletPhotoVo();
		merchantOutletPhotoVo.setMerchantId(merchantId);
		merchantOutletPhotoVo.setOutletId(outletId);
		List<MerchantOutletPhotoVo>  listimage=merchantOutletPhotoService.getMerchantOutletPhoto(merchantOutletPhotoVo);
        if(listimage!=null&&listimage.size()>0){
        	List<Image_Info_Res> listRes=new ArrayList<Image_Info_Res>();
        	for(MerchantOutletPhotoVo vo:listimage){
        		Image_Info_Res res=new Image_Info_Res();
        		TargetObjectFormat.copyProperties(vo, res);
        		listRes.add(res);
        	}
        	map.put("imgList", listRes);
        	return map;
        }else {
        	throw new MerchantException(EnumTypes.noresult.getCode(),EnumTypes.noresult.getMsg());
        }

	}
	

	
	/**
	 * 收款帐号验证
	 * @param car
	 * @return
	 * @throws MerchantException
	 * @throws Exception
	 */
	public Map<String,Object> verifyAccountNum(Verify_AccountNum_Req car)throws MerchantException, Exception{
		Map<String, Object> resMap = new HashMap<String, Object>();
		ResultVo res=new ResultVo();

		try {
			if(StringUtils.isEmpty(car.getCertificateNo()))
				 res = bankCardService.bankCardAccountCheck(car.getClientId(), car.getAcctName(), car.getAcctNumber(), null, null);
			else
				res = bankCardService.bankCardAccountCheck(car.getClientId(), car.getAcctName(), car.getAcctNumber(), car.getCertificateType(), car.getCertificateNo());
			LogCvt.info("收款帐号验证返回结果：----》"+JSON.toJSONString(res));
			if("00".equals(res.getResultCode())){
				resMap.put("code", EnumTypes.success.getCode());
				resMap.put("message", res.getResultDesc());
			}else{
				resMap.put("code", "9999");
				resMap.put("message", "银行卡号校验不通过，请检查输入是否正确");

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			resMap.put("code", "9999");
			resMap.put("message", "收款帐号验证超时");
			LogCvt.error("收款帐号验证异常-----》"+e);
		}
		return resMap;

	}	

	
    /**
     * 门店提交审核
     * @param outletId
     * @return
     * @throws MerchantException
     * @throws Exception
     */
	public Map<String,Object> fallowExecuteService(Fallow_Execute_Service_Req req)throws MerchantException, Exception{
		Map<String, Object> resMap = new HashMap<String, Object>();
		MerchantVo mer = merchantService.getMerchantByMerchantId(req.getMerchantUser().getMerchantId());
		
		OriginVo ori = new OriginVo();
		ori.setOperatorId(req.getMerchantUser().getId());
		ori.setOperatorIp(req.getIp());
		ori.setPlatType(req.getPlatType());
		ori.setOperatorUserName(req.getUserName());
		ori.setClientId(req.getClientId());
		ori.setRoleId(req.getRoleId());
		
		ori.setOrgId(String.valueOf(req.getMerchantUser().getMerchantId()));
		CreateInstanceReqVo vo=new CreateInstanceReqVo();
		vo.setBessId(req.getOutletId());
		vo.setOrigin(ori);
        vo.setOrgCode(mer.getOrgCode());
        boolean isUpdate=false;
        if(req.getAuditState().indexOf("3")!=-1){
        	vo.setProcessType("2");
        	vo.setProcessTypeDetail("0");
        	ori.setDescription("新增审核");
        }else{
        	vo.setProcessType("2");
        	vo.setProcessTypeDetail("1");
        	ori.setDescription("更新审核");
        	isUpdate=true;
        }
		//如果是新增未提交直接用门店详情内容作为json业务/否则用临时表门店详情作为json
		String json="";
		if(isUpdate){
        	Query_Outlet_Detail_Res res=(Query_Outlet_Detail_Res)QueryOutletTemp(req.getOutletId()).get("newData");
        	Fallow_Execute_Base_Data_Req base=new Fallow_Execute_Base_Data_Req();
        	TargetObjectFormat.copyProperties(res, base);
        	base.setMerchantName(mer.getMerchantName());
        	base.setMerchantFullName(mer.getMerchantFullname());
        	base.setOutletId(req.getOutletId());
        	base.setOutletFullName(res.getOutletFullname());
        	if(base!=null){
            	json=JSON.toJSONString(base);
            }
		}else{
        	Query_Outlet_Detail_Res res=(Query_Outlet_Detail_Res)queryOutlet(req.getOutletId(),req.getMerchantUser().getMerchantId(),req.getClientId());
        	Fallow_Execute_Base_Data_Req base=new Fallow_Execute_Base_Data_Req();
        	TargetObjectFormat.copyProperties(res, base);
            base.setMerchantName(mer.getMerchantName());
            base.setMerchantFullName(mer.getMerchantFullname());
            base.setOutletId(req.getOutletId());
            base.setOutletFullName(res.getOutletFullname());
        	if(base!=null){
            	json=JSON.toJSONString(base);
            }
		}
        vo.setBessData(json);
		CreateInstanceResVo res = fallowExecuteService.createInstance(vo);
		if(res.getResult().getResultCode().equals(EnumTypes.success.getCode())){
			resMap.put("code", res.getResult().getResultCode());
			resMap.put("message", res.getResult().getResultDesc());
		}else{
			throw new MerchantException(res.getResult().getResultCode(),res.getResult().getResultDesc());
		}
		return resMap;

	}
	
	/**
	 * 门店类型查询
	 * 说明   description of the class
	 * 创建日期  2015年11月6日  上午11:20:00
	 * 作者  wm
	 * 参数  @param parentId
	 * 参数  @param clientId
	 * 参数  @param user
	 * 参数  @return
	 * 参数  @throws MerchantException
	 * 参数  @throws Exception
	 */
	public Map<String,Object> query_outlet_by_category(String parentId,String clientId,MerchantUser user)
		     throws MerchantException, Exception{
		Map<String,Object> mapList=new HashMap<String,Object>();
		MerchantCategoryVo req = new MerchantCategoryVo();
		req.setClientId(clientId);
		List<CategoryInfoVo> list = null;
		if(parentId != null) {
			if(StringUtils.equals("0", parentId)){
				if(null != user){
					//查询商户详情
					MerchantDetailVo  merchant = merchantService.getMerchantDetail(user.getMerchantId());
					LogCvt.info("merchant对象返回server端返回; ： "+JSON.toJSONString(merchant.toString()));
					list= merchant.getCategoryInfo();
					if(null != list){
						Long mCateId = list.get(0).getCategoryId();
						if(StringUtil.isBlank(mCateId.toString())){
							req.setParentId(0);
						}else{
							req.setParentId(mCateId);
						}
					}else{
						throw new MerchantException(EnumTypes.fail.getCode(),"该商户没有所属分类信息！");
					}
				}
			}else{
				req.setParentId(Long.parseLong(parentId));
			}
		};
		req.setIsDelete(false);
		List<MerchantCategoryVo> listvo= merchantCategoryService.getMerchantCategory(req);
		LogCvt.info("门店分类数据server端返回; ： "+JSON.toJSONString(listvo.toString()));
		List<Query_Outle_Category_Res> listCate = new ArrayList<Query_Outle_Category_Res>(); 
		if(listvo!=null&&listvo.size()>0){
			for(MerchantCategoryVo category : listvo){
				Query_Outle_Category_Res res = new Query_Outle_Category_Res();
				TargetObjectFormat.copyProperties(category, res);
				listCate.add(res);
			}
		}
		mapList.put("categoryeList",listCate);
		mapList.put("bigCateInfolist",list);
		return mapList;
	}
	
	/**
	 * 此方法有两个参数，第一个是要查找的字符串数组，第二个是要查找的字符或字符串
	 * @param strs
	 * @param s
	 * @return
	 */
	 public static boolean isHave(String[] strs,String s){
	  for(int i=0;i<strs.length;i++){
		   if(strs[i].indexOf(s)!=-1){
			   return true;
		   }
	  }
	  return false;
	 }
	
	/**
     * 存储门店添加临时表
     * @param req
     * @return
     * @throws MerchantException
     * @throws Exception
     */
	public Map<String,String> addOutletTemp(Outlet_Temp_Req req,OriginVo ori)throws MerchantException, Exception{
		Map<String, String> resMap = new HashMap<String, String>();
		OutletTempVo vo=new OutletTempVo();
		TargetObjectFormat.copyProperties(req, vo);
		vo.setOutletCategoryId(req.getCategoryIds());
		vo.setOutletCategoryName(req.getCategoryNames());
		LogCvt.info("addOutletTemp === OutletTempVo 门店修改请求参数 ： "+JSON.toJSONString(vo.toString()));
		OutletTempAddVoRes res = outletService.saveOutletTempEditOutlet(ori, vo);
        if(res.getResult().getResultCode().equals(EnumTypes.success.getCode())){
        	resMap.put("code", res.getResult().getResultCode());
        	resMap.put("message", res.getResult().getResultDesc());
        }else{
        	throw new MerchantException(res.getResult().getResultCode(),res.getResult().getResultDesc());
        }
		return resMap;

	}
	
	/**
	 * 查询临时表转换成修改对象和原始对象
	 * 说明   description of the class
	 * 创建日期  2015年11月13日  下午5:31:06
	 * 作者  artPing
	 * 参数  @param outletId
	 * 参数  @return
	 * 参数  @throws TException
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> QueryOutletTemp(String outletId) throws TException{
		Map<String, Object> resMap = new HashMap<String, Object>();
		Query_Outlet_Detail_Res newData=new Query_Outlet_Detail_Res();
		Query_Outlet_Detail_Res oldData=new Query_Outlet_Detail_Res();
		try {
			OutletTempVo res = outletService.getOutletModifyInfos(outletId);
			if(res!=null){
				
				TargetObjectFormat.copyProperties(res, newData);
				newData.setImgList(HandleOutlet.getOutletImage(res.getPhotoList()));
				newData.setCategoryList(HandleOutlet.getOutletCateGroryName(res.getOutletCategoryName(), res.getOutletCategoryId()));
				AreaVo listvo=areaService.findAreaById(Long.valueOf(res.getAreaId()));
				
				Map<String,String> area=HandleOutlet.getArea(listvo);
				newData.setAreaName(area.get("areaName"));
				newData.setCityId(area.get("cityId"));
				newData.setCityName(area.get("cityName"));
				newData.setCountyId(area.get("countyId"));
				newData.setCountyName(area.get("countyName"));
				resMap.put("newData", newData);
				
				//以下老数据
				Map<String,Object> oldMap=(Map<String,Object>)JSON.parse(res.getPrimeval());
				oldData=HandleOutlet.setOutlet(oldMap);
				AreaVo listvo_=areaService.findAreaById(Long.valueOf(oldMap.get("areaId")+""));
				Map<String,String> mapar=HandleOutlet.getArea(listvo_);
				oldData.setAreaName(mapar.get("areaName")!=null?mapar.get("areaName")+"":"");
				oldData.setCityId(mapar.get("cityId")!=null?mapar.get("cityId")+"":"");
				oldData.setCityName(mapar.get("cityName")!=null?mapar.get("cityName")+"":"");
				oldData.setCountyId(mapar.get("countyId")!=null?mapar.get("countyId")+"":"");
				oldData.setCountyName(mapar.get("countyName")!=null?mapar.get("countyName")+"":"");
				resMap.put("newData", newData);
				resMap.put("oldData", oldData);
				resMap.put("category", res.getOutletCategoryName());
				
			}else{
				resMap=null;
			}

		} catch (Exception e) {
			resMap=null;
			LogCvt.error("查询临时表异常:"+e);
		}
		return resMap;
	}
	
	
    /**
     * 
     * 查询门店详情
     * @param outletId
     * @return
     * @throws TException 
     * @throws MerchantException
     * @throws Exception
     */
	public Query_Outlet_Detail_Res queryOutlet(String outletId,String merchantId,String clientId) throws TException{
		Query_Outlet_Detail_Res pojo=new Query_Outlet_Detail_Res();  
		//拿老数据
		OutletVo outletVo=outletService.getOutletByOutletId(outletId);
		if(outletVo!=null){
			TargetObjectFormat.copyProperties(outletVo, pojo);
			pojo.setIsEnable(outletVo.isIsEnable());
			//查询区域id、name
			AreaVo listvo=areaService.findAreaById(outletVo.getAreaId());
			Map<String,String> area=HandleOutlet.getArea(listvo);
			pojo.setAreaName(area.get("areaName"));
			pojo.setCityId(area.get("cityId"));
			pojo.setCityName(area.get("cityName"));
			pojo.setCountyId(area.get("countyId"));
			pojo.setCountyName(area.get("countyName"));
			/**2015-11-05 门店二级类目*/
		    List<CategoryInfoVo> infoList= outletVo.getCategoryInfo();
			List<Query_Outlet_Category_Res> listRes=  new ArrayList<Query_Outlet_Category_Res>(); 
			LogCvt.info("门店分类数据server端返回; ： "+JSON.toJSONString(infoList));
			if(null != infoList){
				for(CategoryInfoVo cvo : infoList){
					LogCvt.info("门店分类数据server端返回; ： "+JSON.toJSONString(cvo));
					Query_Outlet_Category_Res catRes = new Query_Outlet_Category_Res();
//					MerchantCategoryVo mcVo = merchantCategoryService.getMerchantCategoryById(clientId, cvo.getCategoryId());
//					pojo.setIsBigCategory(mcVo.getParentId() == 0 ? "1" : "0");
					catRes.setCategoryId(cvo.getCategoryId());
					catRes.setName(cvo.getName());
					listRes.add(catRes);
				}
			}
			pojo.setCategoryList(listRes);
			//查询相册
			MerchantOutletPhotoVo mevo=new MerchantOutletPhotoVo();
			mevo.setMerchantId(outletVo.getMerchantId());
			mevo.setOutletId(outletVo.getOutletId());
			List<MerchantOutletPhotoVo> listphoto=merchantOutletPhotoService.getMerchantOutletPhoto(mevo);
			List<Image_Info_Res> list=new ArrayList<Image_Info_Res>();
			if(listphoto.size()>0){
				for(MerchantOutletPhotoVo photo:listphoto){
					Image_Info_Res po=new Image_Info_Res();
					po.setLarge(photo.getLarge());
					po.setIsDefault(photo.isIsDefault());
					po.setId(String.valueOf(photo.getId()));
					po.setSource(photo.getSource());
					po.setMedium(photo.getMedium());
					po.setThumbnail(photo.getThumbnail());
					list.add(po);
				}
			}
			pojo.setImgList(list);	
			//门店折扣规范 0819
			pojo.setDiscountCode(outletVo.getDiscountCode());
			pojo.setDiscountRate(outletVo.getDiscountRate());
				
			//查询账号
			MerchantAccountVo vo =setMerchantAccountVo(clientId,merchantId,outletId,null,null);
			List<MerchantAccountVo> listAcct = merchantAccountService.getMerchantAccount(vo);
			if(listAcct!=null&&listAcct.size()>0){
				pojo.setAcctName(listAcct.get(0).getAcctName());
				pojo.setAcctNumber(listAcct.get(0).getAcctNumber());
				pojo.setOpeningBank(listAcct.get(0).getOpeningBank());
			}
				
			//查询开户行 级联
			if(StringUtils.isNotBlank(pojo.getOpeningBank())){
				List<OrgVo> l =orgService.getSuperOrgList(clientId, pojo.getOpeningBank());
				if(l!=null&&l.size()>0){
					for(OrgVo org : l){
						if("1".equals(org.getOrgLevel())){
							pojo.setOrgCodeLev1(org.getOrgCode());
							pojo.setOrgNameLev1(org.getOrgName());
						}
						if("2".equals(org.getOrgLevel())){
							pojo.setOrgCodeLev2(org.getOrgCode());
							pojo.setOrgNameLev2(org.getOrgName());
						}
						if(org.getOrgCode().equals(pojo.getOpeningBank())){
							pojo.setOrgCodeLev3(org.getOrgCode());
							pojo.setOrgNameLev3(org.getOrgName());
						}
					}
				}
			}
		}
         return pojo;
	}
	
	/**
	 * 获取商户类型
	 * 100000000	团购
     * 100000001	面对面
     * 100000002	名优特惠
	 * @param reqMap
	 * @param
	 * @throws TException 
	 */
	public Map<String, Object> query_merchant_type_(String id) throws MerchantException, TException {
		   Map<String, Object> map = new HashMap<String, Object>();    
		   boolean isgroup=false;
			//查询商户详情
			MerchantDetailVo  resv=merchantService.getMerchantDetail(id);
			LogCvt.info("查询商户详情:"+JSON.toJSONString(resv.toString()));
			if(resv.getTypeInfo()!=null&&resv.getTypeInfo().size()>0){
				for(TypeInfoVo tyv:resv.getTypeInfo()){
					if(MerchantType.TH_Merchant.getCode().equals(Long.toString(tyv.getMerchantTypeId()))){
						isgroup=true;
						break;
					}	
				}
				
			}
			map.put("isgroup", isgroup);
			map.put("message", "isgroup");
			return map;
	}
	
	/**
	 * 查询商户下是否有门店
	 * 
	 * @param reqMap
	 * @param
	 */
	public Map<String, Object> query_merchant_outlet_true(String clientId,MerchantUser use) throws MerchantException, Exception {
		Map<String, Object> mapList = new HashMap<String, Object>();
		
		MerchantVo merchantVo = merchantService.getMerchantByMerchantId(use.getMerchantId());
		LogCvt.info("商户状态："+merchantVo.getDisableStatus());
		if(MerchantDisableStatusEnum.disable.getCode().equals(merchantVo.getDisableStatus())){ //解约
			mapList.put("code", "9999");
			mapList.put("message", "商户已禁用");
			return mapList;
		}else if(MerchantDisableStatusEnum.unregistered.getCode().equals(merchantVo.getDisableStatus())){ //解约
			mapList.put("code", "9999");
			mapList.put("message", "商户已解约");
			return mapList;
		}
		if(use.getOutletId() != null && !use.getOutletId().equals("0")){
			LogCvt.info("门店id："+use.getOutletId());
			OutletVo OutletVo=outletService.getOutletByOutletId(use.getOutletId());
			if(OutletVo != null &&!OutletVo.getDisableStatus().equals("0")){
				LogCvt.info("门店删除状态："+OutletVo.getDisableStatus());
				mapList.put("code", "9999");
				mapList.put("message", "门店已删除");
				return mapList;
			}
		}
		boolean isOutlet=false;
		OutletVo outletVo = new OutletVo();
		outletVo.setClientId(clientId);
		outletVo.setMerchantId(use.getMerchantId());
		List<String> disableStatusList = new ArrayList<String>();
		disableStatusList.add(OutletDisableStatusEnum.normal.getCode());
		disableStatusList.add(OutletDisableStatusEnum.disable.getCode());
		outletVo.setDisableStatusList(disableStatusList);
		//商户下所有门店
		List<OutletVo> listOutletPage=outletService.getOutlet(outletVo);
		if(listOutletPage!=null&&listOutletPage.size()>0){
			for(OutletVo vo:listOutletPage){
				//审核状态
				OutletVo listOutletVo=outletService.getOutletByOutletId(vo.getOutletId());
				if(listOutletVo!=null){
					//审核通过
					if(listOutletVo.getAuditState().equals("1")){
							isOutlet=true;
							break;
					}

				}
			}
		}
		
		mapList.put("isOutlet", isOutlet);
		mapList.put(EnumTypes.success.getCode(),EnumTypes.success.getMsg());
		return mapList;
		
	}
	
	/**
	 * 查询商户是否正常，（解约/禁用）
	 * 
	 * @param reqMap
	 * @param
	 */
	public Map<String, Object> query_merchant_outlet_true(String merchantId) throws MerchantException, Exception {
		Map<String, Object> mapList = new HashMap<String, Object>();
		MerchantVo vo = merchantService.getMerchantByMerchantId(merchantId);
//        vo.getDisableStatus().e
//        无效状态,0正常;1禁用;2解约
		String isok=vo.getDisableStatus(),msg="";
		if("1".indexOf(isok)!=-1)msg="商户已禁用";
		if("2".indexOf(isok)!=-1)msg="商户已解约";
		mapList.put(EnumTypes.success.getCode(),EnumTypes.success.getMsg());
		mapList.put("msg",msg);
		mapList.put("isOK","1,2".indexOf(isok)!=-1?false:true);
		return mapList;
		
	}
	
	
	
	public MerchantAccountVo setMerchantAccountVo(String clientId,String merchantId,String outleId,String acctNumber,String acctName) throws TException{
		MerchantVo vo=merchantService.getMerchantByMerchantId(merchantId);
		MerchantAccountVo merchantAccountVo = new MerchantAccountVo();
		merchantAccountVo.setClientId(clientId);
		merchantAccountVo.setMerchantId(merchantId);
		merchantAccountVo.setOutletId(outleId);
		merchantAccountVo.setOpeningBank(vo.getOrgCode());
		if(StringUtils.isNotEmpty(acctName))merchantAccountVo.setAcctName(acctName);
		if(StringUtils.isNotEmpty(acctNumber))merchantAccountVo.setAcctNumber(acctNumber);
		merchantAccountVo.setAcctType("1");
		merchantAccountVo.setIsDelete(false);
		return merchantAccountVo;
	}
	
	public String getArea(String getArea) {
		String[] name = getArea.split(",");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < name.length; i++) {
			if(i>0){
				if(!name[i].contains(name[i-1])){
					sb.append(name[i]+" ");
				}
			}else{
				sb.append(name[i]+" ");
			}
		}
		LogCvt.info("地区处理  :  "+JSON.toJSONString(sb.toString()));
		return sb.toString();
	}
	
	
	public Query_Outlet_Detail_Res tcyCategory(List<Query_Outlet_Category_Res> catResList,String merchantId,String clientId) throws TException{
		Query_Outlet_Detail_Res res = new Query_Outlet_Detail_Res();
	    List<Query_Outlet_Category_Res> listRes=  new ArrayList<Query_Outlet_Category_Res>(); 
	   if(null != catResList){
			for(Query_Outlet_Category_Res cvo : catResList){

   			/**2015-11-16 Bug 6067 - [商品/门店分类]商户PC-门店管理-门店修改-所属分类修改（已在银行管理平台修改商户分类），所属分类还是以前的商户分类下的子类
			 * server端冯国源说server不好处理 直接让web端这么处理*/
			MerchantDetailVo  merchant = merchantService.getMerchantDetail(merchantId);
			LogCvt.info("merchant对象返回server端返回; ： "+JSON.toJSONString(merchant.toString()));
			List<CategoryInfoVo> list= merchant.getCategoryInfo();
			if(null != list){
				Long mCateId = list.get(0).getCategoryId();
				if(StringUtil.isNotBlank(mCateId.toString())){
					MerchantCategoryVo mcreq = new MerchantCategoryVo();
					mcreq.setClientId(clientId);
					mcreq.setParentId(mCateId);
					mcreq.setIsDelete(false);
					List<MerchantCategoryVo> listMcvo= merchantCategoryService.getMerchantCategory(mcreq);
					if(listMcvo!=null && listMcvo.size()>0){
						for(MerchantCategoryVo category : listMcvo){
							if(cvo.getCategoryId() == category.getId()){
								Query_Outlet_Category_Res catRes = new Query_Outlet_Category_Res();
								MerchantCategoryVo mcVo = merchantCategoryService.getMerchantCategoryById(clientId, cvo.getCategoryId());
								res.setIsBigCategory(mcVo.getParentId() == 0 ? "1" : "0");
								catRes.setCategoryId(cvo.getCategoryId());
								catRes.setName(cvo.getName());
								listRes.add(catRes);
							}else{
								res.setIsBigCategory("0");
							}
						}
					}
				}
			}
		}
		res.setCategoryList(listRes);
			
	   }
	return res;
	}

}
