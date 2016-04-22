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
import com.froad.cbank.coremodule.module.normal.merchant.enums.PlatType;
import com.froad.cbank.coremodule.module.normal.merchant.enums.ProductAuditState;
import com.froad.cbank.coremodule.module.normal.merchant.enums.ProductStatus;
import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Add_Product_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.BasePojo;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Del_Product_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Image_Info_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.PageRes;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.ProductAudditReq;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.ProductDetailTempRes;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.QueryProductListReq;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.QueryProductListRes;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Product_By_OuletId_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Product_Category_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Product_Detail_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Update_Product_Auddit_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Update_Product_Down_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Update_Product_Req;
import com.froad.cbank.coremodule.module.normal.merchant.utils.EnumTypes;
import com.froad.cbank.coremodule.module.normal.merchant.utils.HandleProduct;
import com.froad.cbank.coremodule.module.normal.merchant.utils.PramasUtil;
import com.froad.cbank.coremodule.module.normal.merchant.utils.ProductAuditEnum;
import com.froad.cbank.coremodule.module.normal.merchant.utils.TargetObjectFormat;
import com.froad.thrift.service.ClientProductAuditService;
import com.froad.thrift.service.MerchantService;
import com.froad.thrift.service.OrgService;
import com.froad.thrift.service.ProductCategoryService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.service.TicketService;
import com.froad.thrift.vo.AddProductVoRes;
import com.froad.thrift.vo.FiledSort;
import com.froad.thrift.vo.MerchantVo;
import com.froad.thrift.vo.OrgVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ProductAuditVo;
import com.froad.thrift.vo.ProductBriefPageVoRes;
import com.froad.thrift.vo.ProductBriefVo;
import com.froad.thrift.vo.ProductBuyLimitVo;
import com.froad.thrift.vo.ProductCategoryVo;
import com.froad.thrift.vo.ProductFilterReqVo;
import com.froad.thrift.vo.ProductFilterVoReq;
import com.froad.thrift.vo.ProductImageVo;
import com.froad.thrift.vo.ProductInfoVo;
import com.froad.thrift.vo.ProductOperateVoReq;
import com.froad.thrift.vo.ProductOutletVo;
import com.froad.thrift.vo.ProductPageVo;
import com.froad.thrift.vo.ProductStatusVoReq;
import com.froad.thrift.vo.ProductVo;
import com.froad.thrift.vo.ResultVo;

/**
 * 商品
 * @ClassName Product_Service
 * @author zxl
 * @date 2015年4月23日 下午8:57:45
 */
@Service
public class Product_Service  {
	
	@Resource
	ProductService.Iface productService;
	
	@Resource
	OrgService.Iface orgService;

	@Resource
	ProductCategoryService.Iface productCategoryService;
	
	@Resource
	ClientProductAuditService.Iface clientProductAuditService;
	
	@Resource
	Common_Service common_Service;
	
	@Resource
	TicketService.Iface ticketService;
	@Resource
	MerchantService.Iface merchantService;
	
	@Resource
	HandleProduct_Service handleProduct_Service;
	/**
	 * 商品查询 product_query
	 * 
	 * @param reqMap
	 * @param
	 */
	public Map<String,Object> product_query(QueryProductListReq req) throws MerchantException, Exception {
		Map<String,Object> mapList = new HashMap<String,Object>();
		//新接口12-24商品审核
		ProductFilterReqVo reqv=new ProductFilterReqVo();
		reqv.setClientId(req.getClientId());
		reqv.setMerchantId(req.getMerchantUser().getMerchantId());
		reqv.setType(req.getType());
		if(StringUtils.isNotEmpty(req.getProductName()))reqv.setProductName(req.getProductName());
		if(StringUtils.isNotEmpty(req.getIsMarketable()))reqv.setIsMarketable(req.getIsMarketable());
		if(StringUtils.isNotEmpty(req.getAuditState()))reqv.setAuditState(req.getAuditState());
		//根据特惠时间查询  -- 2015-06-18-
		if(StringUtils.isNotBlank(req.getStartTime())){
			reqv.setStartTimeStart(PramasUtil.DateFormat(req.getStartTime()));
			reqv.setEndTimeStart(PramasUtil.DateFormat(req.getStartTime()));
		}
		if(StringUtils.isNotBlank(req.getEndTime())){
			reqv.setStartTimeEnd(PramasUtil.DateFormatEnd(req.getEndTime()));
			reqv.setEndTimeEnd(PramasUtil.DateFormatEnd(req.getEndTime()));
		}
		
		List<FiledSort> sort=new ArrayList<FiledSort>();
		//排序 时间倒序
		FiledSort file1=new FiledSort();
		file1.setSortBy(-1);file1.setSortPrior(1);file1.setSortName("createTime");
		sort.add(file1);
		//排序 销量倒序
		FiledSort file2=new FiledSort();
		file2.setSortBy(-1);file2.setSortPrior(2);file2.setSortName("sellCount");
		sort.add(file2);
		
		PageVo pageVo=new PageVo();
		pageVo.setPageNumber(req.getPageNumber());
		pageVo.setPageSize(req.getPageSize());
		pageVo.setLastPageNumber(req.getLastPageNumber());
		pageVo.setFirstRecordTime(req.getFirstRecordTime());
		pageVo.setLastRecordTime(req.getLastRecordTime());
		ProductPageVo productPageVo=productService.findProductsByPage(reqv, sort, pageVo);
		if(productPageVo!=null&&productPageVo.getProductBriefVoList().size()>0){
			List<QueryProductListRes> listRes=new ArrayList<QueryProductListRes>();
			for(ProductBriefVo  vo:productPageVo.getProductBriefVoList()){
				QueryProductListRes res=new QueryProductListRes();
				TargetObjectFormat.copyProperties(vo, res);
				res.setAuditStateName(ProductAuditEnum.values()[Integer.valueOf(res.getAuditState().trim())].getMsg());
				
				listRes.add(res);
			}
			mapList.put("productList", listRes);
			PageRes pageRes = new PageRes();
			TargetObjectFormat.copyProperties(productPageVo.getPage(), pageRes);
			mapList.put("page",pageRes);
		}else{
			throw new MerchantException(EnumTypes.noresult.getCode(), EnumTypes.noresult.getMsg());
		}
		
		return mapList;
	}

	/**
	 * 说明   查询商品详情（操作修改显示和想起显示区别开）
	 * 创建日期  2015年12月23日  下午1:49:14
	 * 作者  artPing
	 * 参数  @param req
	 * 参数  @return
	 * @throws Exception 
	 * @throws MerchantException 
	 */
	public Map<String, Object> product_details(Query_Product_Detail_Req req) throws MerchantException, Exception{
		
		Map<String, Object> mapList = new HashMap<String, Object>();
		//原始数据和临时表数据
		Map<String,Object> data=handleProduct_Service.query_product_temp_details(req.getProductId(), req.getClientId());//查询临时表数据 审核通过=删除这条数据
		ProductDetailTempRes oldData=(ProductDetailTempRes)data.get("tmepProductDeatls");
		ProductDetailTempRes newData=(ProductDetailTempRes)data.get("productDetail");		

		mapList.put("clientId", req.getClientId());
		mapList.put("merchantId", req.getMerchantUser().getMerchantId());
		mapList.put("productId", req.getProductId());
		//有对比值，，修改用
		if(oldData!=null){
			//审核通过（审核不通过）显示原数据
			if(req.getDoType().trim().equals("1")&&(!newData.getAuditState().equals(ProductAuditEnum.shtgshbtg.getCode()))){
				mapList.put("productDetail", newData);
			}else{
				mapList.put("productDetail", oldData);
			}
			return mapList;
		}
		mapList.put("productDetail", newData);
		return mapList;
	}
	
	/**
	 * 商品添加 product_add
	 * 
	 * @param reqMap
	 * @param
	 * @throws Exception 
	 */
	public Map<String, Object> product_add(Add_Product_Req req) throws Exception {
        if(StringUtil.isNotBlank(req.getExpireEndTime())){
        	if(PramasUtil.DateFormat(req.getExpireEndTime())<PramasUtil.DateFormat(req.getEndTime())){
        		throw new MerchantException(EnumTypes.fail.getCode(), "验证有效期结束时间早于团购结束时间");
        	}
        	  
        }
		//检查商户状态
		common_Service.checkMerchantStatus(req.getMerchantUser().getMerchantDisableStatus());
		
		Map<String, Object> map = new HashMap<String, Object>();
		ProductInfoVo infoVo = new ProductInfoVo();
		infoVo.setPlatType(PlatType.merchant.getCode());//1=boss.2=bank,3=merchant
		ProductVo vo1 = new ProductVo();
		TargetObjectFormat.copyProperties(req, vo1);
		vo1.setMerchantId(req.getMerchantUser().getMerchantId());
		vo1.setClientId(req.getClientId());
		vo1.setRackTime(PramasUtil.DateFormat(req.getRackTime()));
        vo1.setStartTime(PramasUtil.DateFormat(req.getStartTime()));
        vo1.setEndTime(PramasUtil.DateFormatEnd(req.getEndTime()));
        vo1.setExpireStartTime(PramasUtil.DateFormat(req.getExpireStartTime()));
        vo1.setExpireEndTime(PramasUtil.DateFormatEnd(req.getExpireEndTime()));
        vo1.setDeliveryStartTime(PramasUtil.DateFormat(req.getDeliveryStartTime()));
        vo1.setDeliveryEndTime(PramasUtil.DateFormatEnd(req.getDeliveryEndTime()));
        vo1.setDownTime(PramasUtil.DateFormat(req.getDownTime()));
        vo1.setAuditTime(PramasUtil.DateFormat(req.getAuditTime()));
		vo1.setOrgCode(req.getMerchantUser().getOrgCode());
		if(StringUtils.isNotBlank(req.getDeliveryOption())){
			vo1.setDeliveryOption(req.getDeliveryOption());
		}
		//应产品需求，如果限购数量输入的是0，也要回显2016-1-28
		Integer max=0;
		if (req.getMax() != null && req.getMax() > 0) {
            vo1.setIsLimit(true);
            max = Integer.valueOf(req.getMax());
        } else {
            vo1.setIsLimit(false);
        }
		//新增审核状态都未提交
		vo1.setAuditState("3");
		infoVo.setProduct(vo1);
		ProductCategoryVo vo2 = new ProductCategoryVo();
		vo2.setClientId(req.getClientId());
		vo2.setId(Long.valueOf(req.getCategoryId()));
		infoVo.setProductCategory(vo2);

		ProductBuyLimitVo vo3 = new ProductBuyLimitVo();
		
		TargetObjectFormat.copyProperties(req, vo3);
		vo3.setEndTime(PramasUtil.DateFormatEnd(req.getEndTime()));
		vo3.setStartTime(PramasUtil.DateFormat(req.getStartTime()));
		vo3.setMax(max);
		infoVo.setBuyLimit(vo3);
	
		List<ProductImageVo> al1 = new ArrayList<ProductImageVo>();	
		if(req.getImgList()!=null&&req.getImgList().size()>0){
			for(Image_Info_Req img : req.getImgList()){
				ProductImageVo v = new ProductImageVo();
				TargetObjectFormat.copyProperties(img, v);
				al1.add(v);
			}
		}
		infoVo.setImage(al1);
		List<ProductOutletVo> al2 = new ArrayList<ProductOutletVo>();
		if(StringUtil.isNotEmpty(req.getOutletIds())){
			String[] outletIds=req.getOutletIds().split(",");
			for(String str:outletIds){
				ProductOutletVo v=new ProductOutletVo();
				v.setOutletId(str);
				al2.add(v);
			}
			infoVo.setOutlet(al2);
		}
		
		OriginVo ori = new OriginVo();
		ori.setOperatorId(req.getMerchantUser().getId());
		ori.setOperatorIp(req.getIp());
		ori.setPlatType(req.getPlatType());

		AddProductVoRes res = productService.addProduct(ori,infoVo);
		if (!res.getResult().getResultCode().equals(EnumTypes.success.getCode()))  {
			throw new MerchantException(res.getResult().getResultCode(),res.getResult().getResultDesc());
		}else{
			//商品添加后提交审核
			if(req.getBoxType()==1){
				//提交审核接口返回成功为0=待审核，否则为3=未提交
		        MerchantVo mervo=merchantService.getMerchantByMerchantId(req.getMerchantUser().getMerchantId());
				ProductAudditReq auddit=new ProductAudditReq();
				auddit.setOrgCode(mervo.getOrgCode());
				auddit.setRoleId(req.getRoleId());
				auddit.setUserName(req.getUserName());
				auddit.setTypes(req.getType());
				auddit.setProductIds(res.getProductId());
				auddit.setMerchantUser(req.getMerchantUser());
				auddit.setIp(req.getIp());
				auddit.setPlatType(req.getPlatType());
				auddit.setClientId(req.getClientId());
				Map<String, Object> mapAuddit=handleProduct_Service.auddit_product_batch(auddit);
				map.put("res", mapAuddit.get("Audits"));
			}
		}
		
		map.put("productId", res.getProductId());
		return map;
		

	}
	
	
	/**
	 * 商品修改product_update
	 * 
	 * @param reqMap
	 * @param
	 * @throws Exception 
	 */
	public Map<String, Object> product_update(Update_Product_Req req) throws MerchantException, Exception {
        if(StringUtil.isNotBlank(req.getExpireEndTime())){
        	if(PramasUtil.DateFormat(req.getExpireEndTime())<PramasUtil.DateFormat(req.getEndTime())){
        		throw new MerchantException(EnumTypes.fail.getCode(), "验码有效期结束时间必须大于团购结束时间");
        	}
        	  
        }
		//检查商户状态
		common_Service.checkMerchantStatus(req.getMerchantUser().getMerchantDisableStatus());
		Map<String, Object> map = new HashMap<String, Object>();
		//查询商品详情  -- 06-25
		ProductOperateVoReq productVoReq=new ProductOperateVoReq();
		productVoReq.setMerchantId(req.getMerchantUser().getMerchantId());
		productVoReq.setProductId(req.getProductId());
		productVoReq.setClientId(req.getClientId());
		productVoReq.setType(req.getType());
		ProductInfoVo productInfoVo = productService.getMerchantProductDetail(productVoReq);
		if(productInfoVo.getBuyLimit()==null){
			ProductBuyLimitVo vo3 = new ProductBuyLimitVo();
			vo3.setMax(0);
			productInfoVo.setBuyLimit(vo3);
		}
		boolean bug=HandleProduct.isEquals(productInfoVo, req);
		if(bug){
			//没修改
			return map;
		}
		
		ProductInfoVo infoVo = new ProductInfoVo();
		infoVo.setPlatType(PlatType.merchant.getCode());//1=boss.2=bank,3=merchant
		ProductVo vo1 = new ProductVo();
		TargetObjectFormat.copyProperties(req, vo1);
		//审核通过
		if(productInfoVo.getProduct().getAuditState().trim().equals("1")){
			//验证是否符合审核条件
			if(StringUtil.isNotBlank(req.getStore())){
				if(productInfoVo.getProduct().getStore() > req.getStore()){
					throw new MerchantException(EnumTypes.fail.getCode(), "库存必须大于当前库存");
				}
			}
			
			if(StringUtil.isNotBlank(req.getEndTime())){
	        	if(productInfoVo.getProduct().getEndTime() - PramasUtil.DateFormat(req.getEndTime()) > 86399000){
	        		throw new MerchantException(EnumTypes.fail.getCode(), "团购结束时间必须大于当前结束时间");
	        	}
	        }
			if(StringUtil.isNotBlank(req.getExpireEndTime())){
	        	if(productInfoVo.getProduct().getExpireEndTime() - PramasUtil.DateFormat(req.getExpireEndTime()) > 86399000){
	        		throw new MerchantException(EnumTypes.fail.getCode(), "验码有效期必须大于当前验码有效期");
	        	}
	        }
		}
		
		//免审核且 状态为非 “未提交”状态   .......
		if("2".equals(req.getIsAudit())){
			//审核状态 和上架状态不变//
			vo1.setAuditState(productInfoVo.getProduct().getAuditState());
			vo1.setIsMarketable(productInfoVo.getProduct().getIsMarketable());
		}else{
			//如果需要审核那么都是未上架 未提交
			vo1.setAuditState(ProductAuditState.noCommit.getCode());
			vo1.setIsMarketable(ProductStatus.noShelf.getCode());//只要是不是免审核那么上架状态都是
		}


		vo1.setClientId(req.getClientId());
		vo1.setMerchantId(req.getMerchantUser().getMerchantId());
		vo1.setRackTime(PramasUtil.DateFormat(req.getRackTime()));
        vo1.setStartTime(PramasUtil.DateFormat(req.getStartTime()));
        vo1.setEndTime(PramasUtil.DateFormatEnd(req.getEndTime()));
        vo1.setExpireStartTime(PramasUtil.DateFormat(req.getExpireStartTime()));
        vo1.setExpireEndTime(PramasUtil.DateFormatEnd(req.getExpireEndTime()));
        vo1.setDeliveryStartTime(PramasUtil.DateFormat(req.getDeliveryStartTime()));
        vo1.setDeliveryEndTime(PramasUtil.DateFormatEnd(req.getDeliveryEndTime()));
        vo1.setDownTime(PramasUtil.DateFormat(req.getDownTime()));
        vo1.setAuditTime(PramasUtil.DateFormat(req.getAuditTime()));
        vo1.setOrgCode(req.getMerchantUser().getOrgCode());
        
        if(req.getMax() != null && 0 !=req.getMax()){
			vo1.setIsLimit(true);
		}else{
			vo1.setIsLimit(false);
		}
		infoVo.setProduct(vo1);
		
		ProductCategoryVo vo2 = new ProductCategoryVo();
		vo2.setClientId(req.getClientId());
		vo2.setId(Long.valueOf(req.getCategoryId()));
		infoVo.setProductCategory(vo2);
	
		ProductBuyLimitVo vo3 = new ProductBuyLimitVo();
		TargetObjectFormat.copyProperties(req, vo3);
		vo3.setEndTime(PramasUtil.DateFormatEnd(req.getEndTime()));
		vo3.setStartTime(PramasUtil.DateFormat(req.getStartTime()));
		infoVo.setBuyLimit(vo3);
		
	
		List<ProductImageVo> al1 = new ArrayList<ProductImageVo>();	
		if(req.getImgList()!=null&&req.getImgList().size()>0){
			for(Image_Info_Req img : req.getImgList()){
				ProductImageVo v = new ProductImageVo();
				TargetObjectFormat.copyProperties(img, v);
				al1.add(v);
			}
			infoVo.setImage(al1);
		}
		
		List<ProductOutletVo> al2 = new ArrayList<ProductOutletVo>();
		if(StringUtil.isNotEmpty(req.getOutletIds())){
			String[] outletIds=req.getOutletIds().split(",");
			for(String str:outletIds){
				ProductOutletVo v=new ProductOutletVo();
				v.setOutletId(str);
				al2.add(v);
			}
			infoVo.setOutlet(al2);
		}
		OriginVo ori = new OriginVo();
		ori.setOperatorId(req.getMerchantUser().getId());
		ori.setOperatorIp(req.getIp());
		ori.setPlatType(req.getPlatType());
		
		ResultVo res = productService.updateProduct(ori,infoVo);
		
		if (res.getResultCode().equals(EnumTypes.success.getCode()))  {
			//审核通过状态调卷码延长方法
	  		if(infoVo.getProduct().getAuditState().equals(ProductAuditState.passAudit.getCode())){
				// 延长指定商品id的券有效期
				ResultVo ticket_res = ticketService.extendEndDateByProductId(req.getProductId(),PramasUtil.DateFormatEnd(req.getExpireEndTime()));
				if (!ticket_res.getResultCode().equals(EnumTypes.success.getCode()))  {
					throw new MerchantException(res.getResultCode(),res.getResultDesc());
				}
	  		}
	  		
			//商品修改后提交审核--》需要审核
			if("2".equals(req.getIsAudit())){}
			else{
				//提交审核接口返回成功为0=待审核，否则为3=未提交
		        MerchantVo mervo=merchantService.getMerchantByMerchantId(req.getMerchantUser().getMerchantId());
				ProductAudditReq auddit=new ProductAudditReq();
				auddit.setOrgCode(mervo.getOrgCode());
				auddit.setRoleId(req.getRoleId());
				auddit.setUserName(req.getUserName());
				auddit.setTypes(req.getType());
				auddit.setProductIds(req.getProductId());
				auddit.setMerchantUser(req.getMerchantUser());
				auddit.setIp(req.getIp());
				auddit.setPlatType(req.getPlatType());
				auddit.setClientId(req.getClientId());
				Map<String, Object> mapAuddit=handleProduct_Service.auddit_product_batch(auddit);
				map.put("res", mapAuddit.get("Audits"));
			}
	  		
		}else{
			throw new MerchantException(res.getResultCode(),res.getResultDesc());
		}
		
		return map;

	}
	
//	/**
//	 * 商品部分信息修改
//	 * @tilte product_update_part
//	 * @author zxl
//	 * @date 2015年5月5日 下午5:16:05
//	 * @param req
//	 * @return
//	 * @throws MerchantException
//	 * @throws Exception
//	 */
//	public Map<String, String> product_update_part(Update_Product_Part_Req req) throws MerchantException, Exception {
//		//检查商户状态
//		common_Service.checkMerchantStatus(req.getMerchantUser().getMerchantDisableStatus());
//				
//		Map<String, String> map = new HashMap<String, String>();
//		ProductBaseVo vo = new ProductBaseVo();
//		vo.setProductId(req.getProductId());
//		vo.setClientId(req.getClientId());
//		vo.setStore(req.getStore());
//		vo.setEndTime(PramasUtil.DateFormatEnd(req.getEndTime()));
//		vo.setExpireEndTime(PramasUtil.DateFormatEnd(req.getExpireEndTime()));
//        
//		OriginVo ori = new OriginVo();
//		ori.setOperatorId(req.getMerchantUser().getId());
//		ori.setOperatorIp(req.getIp());
//		ori.setPlatType(req.getPlatType());
//		
//		ResultVo res = productService.updateProductInfo(ori,vo);
//		
//		if (!res.getResultCode().equals(EnumTypes.success.getCode()))  {
//			throw new MerchantException(res.getResultCode(),res.getResultDesc());
//		}
//		
//		return map;
//
//	}


	/**
	 * 商品删除query_product_delete
	 * 
	 * @param reqMap
	 * @param
	 */
	public Map<String, Object> query_product_delete(Del_Product_Req req) throws MerchantException, Exception {
		LogCvt.info("请求参数："+JSON.toJSONString(req));
		Map<String, Object> map = new HashMap<String, Object>();
		ProductOperateVoReq productVoReq=new ProductOperateVoReq();
		productVoReq.setProductId(req.getProductId());
		productVoReq.setClientId(req.getClientId());
		
		OriginVo ori = new OriginVo();
		ori.setOperatorId(req.getMerchantUser().getId());
		ori.setOperatorIp(req.getIp());
		ori.setPlatType(req.getPlatType());
		
		ResultVo res = productService.deleteProduct(ori,productVoReq);
		if (!res.getResultCode().equals(EnumTypes.success.getCode()))  {
			throw new MerchantException(res.getResultCode(),res.getResultDesc());
		}
		return map;
	}


	/**
	 * 商品上下架shangquery_product_delete
	 * 
	 * @param reqMap	
	 * @param
	 */
	public Map<String, String> update_product_updown(Update_Product_Down_Req req) throws MerchantException, Exception {
		//检查商户状态
		common_Service.checkMerchantStatus(req.getMerchantUser().getMerchantDisableStatus());
				
		Map<String, String> map = new HashMap<String, String>();
		
		//查询商品详情  -- 06-25
		ProductOperateVoReq productVoReq=new ProductOperateVoReq();
		productVoReq.setMerchantId(req.getMerchantUser().getMerchantId());
		productVoReq.setProductId(req.getProductId());
		productVoReq.setClientId(req.getClientId());
		ProductInfoVo productInfoVo = productService.getMerchantProductDetail(productVoReq);
		//团购结束时间大于当前时间不能下架
		if(productInfoVo.getProduct().getEndTime() >System.currentTimeMillis()){
			throw new MerchantException(EnumTypes.fail.getCode(), "团购时间未结束不能下架!");
		}
		ProductStatusVoReq vo = new ProductStatusVoReq();
		vo.setProductId(req.getProductId());
		vo.setClientId(req.getClientId());
		vo.setStatus(req.getStatus());//1=上架，2=下架
		
		OriginVo ori = new OriginVo();
		ori.setOperatorId(req.getMerchantUser().getId());
		ori.setOperatorIp(req.getIp());
		ori.setPlatType(req.getPlatType());
		
		ResultVo res = productService.updateProductStatus(ori, vo);
		
		if (!res.getResultCode().equals(EnumTypes.success.getCode()))  {
			throw new MerchantException(res.getResultCode(),res.getResultDesc());
		}
		
		return map;
	}

	
	
	/**
	 * 查询用户机构号query_user_orgCode
	 * 
	 * @param reqMap
	 * @param
	 */
	public Map<String, Object> query_user_orgCode(String merchantId,String outletId)
			throws MerchantException, Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		OrgVo orgVo=new OrgVo();
		orgVo.setMerchantId(merchantId);
		orgVo.setOutletId(outletId);
		List<OrgVo> listorgVo=orgService.getOrg(orgVo);
		String orgName=listorgVo.size()>0?listorgVo.get(0).getOrgCode().toString():"";
		if(orgName.length()>0){
			map.put("orgCode", orgName);
		}else{
			throw new MerchantException(EnumTypes.fail.getCode(), EnumTypes.fail.getMsg());
		}
		return map;
	}
	
	/**
	 * 查询商品分类query_product_categorye
	 * 
	 * @param reqMap
	 * @param
	 */
	public Map<String, Object> query_product_categorye(BasePojo po) throws MerchantException, Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		ProductCategoryVo productCategoryVo=new ProductCategoryVo();
		productCategoryVo.setClientId(po.getClientId());
		List<ProductCategoryVo> list = productCategoryService.findCategorys(po.getClientId());
		LogCvt.info("商品分类后端返回数据 : " + JSON.toJSONString(list.toString()));
		if(list!=null&&list.size() > 0){
			List<Query_Product_Category_Res> listRes=new ArrayList<Query_Product_Category_Res>();
			for(ProductCategoryVo vo : list){
//				if(vo.getParentId() == 0 ) continue;
				Query_Product_Category_Res res=new Query_Product_Category_Res();
				TargetObjectFormat.copyProperties(vo, res);
				listRes.add(res);
			}
			map.put("categoryeList", listRes);
		}else{
			throw new MerchantException(EnumTypes.noresult.getCode(), EnumTypes.noresult.getMsg());
		}
		return map;
	}
	
	
	/**
	 * 商品批量审核auddit_product_batch
	 * 
	 * @param reqMap
	 * @param
	 * @throws Exception 
	 */
	public Map<String, String> auddit_product_batch(Update_Product_Auddit_Req req) throws MerchantException, Exception {
		//检查商户状态
		common_Service.checkMerchantStatus(req.getMerchantUser().getMerchantDisableStatus());
				
		Map<String, String> map = new HashMap<String, String>();
		String[] productId = req.getProductId().split(",");
		List<ProductAuditVo> productAuditVoList = new ArrayList<ProductAuditVo>();
		for(String p : productId){
			ProductAuditVo vo=new ProductAuditVo();
			vo.setMerchantId(req.getMerchantUser().getMerchantId());
			vo.setClientId(req.getClientId());
			vo.setProductId(p);
			vo.setAuditState(ProductAuditState.noAudit.getCode());
			productAuditVoList.add(vo);
		}
		
		OriginVo ori = new OriginVo();
		ori.setOperatorId(req.getMerchantUser().getId());
		ori.setOperatorIp(req.getIp());
		ori.setPlatType(req.getPlatType());
		
		ResultVo res = productService.updateAuditProductBatch(ori,productAuditVoList);
		if (!res.getResultCode().equals(EnumTypes.success.getCode()))  {
			throw new MerchantException(res.getResultCode(),res.getResultDesc());
		} 
		return map;

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
	public Map<String, String> query_merchant_authority(Query_Product_By_OuletId_Req req) throws MerchantException, TException {
		    Map<String, String> map = new HashMap<String, String>();
			//查询是否存在上架商品
			PageVo pageVo=new PageVo();
			pageVo.setPageNumber(req.getPageNumber());
			pageVo.setPageSize(req.getPageSize());
			pageVo.setLastPageNumber(req.getLastPageNumber());
			pageVo.setFirstRecordTime(req.getFirstRecordTime());
			pageVo.setLastRecordTime(req.getLastRecordTime());
			ProductFilterVoReq rev=new ProductFilterVoReq();
			rev.setFilter(JSON.toJSONString(req));
			ProductBriefPageVoRes  resv=productService.queryProducts(rev, pageVo);
			if(resv!=null&&resv.getProductBriefVoList().size()>0){
				map.put("istrue", "true");
			}else{
				map.put("istrue", "false");
			}
			return map;
	}
	
	
	
	public Map<String,Object> product_by_categorye(String parentId,String clientId)
	     throws MerchantException, Exception{
		Map<String,Object> mapList=new HashMap<String,Object>();
		List<ProductCategoryVo> listvo=productCategoryService.queryManageProductCategorys(clientId, Long.parseLong(parentId));
		LogCvt.info("商品分类数据server端返回 ： "+JSON.toJSONString(listvo.toString()));
		List<Query_Product_Category_Res> listCate = new ArrayList<Query_Product_Category_Res>(); 
		if(listvo!=null&&listvo.size()>0){
			for(ProductCategoryVo category : listvo){
				Query_Product_Category_Res res = new Query_Product_Category_Res();
				TargetObjectFormat.copyProperties(category, res);
				listCate.add(res);
			}
		}
		mapList.put("categoryeList",listCate);
		return mapList;
	}

}
