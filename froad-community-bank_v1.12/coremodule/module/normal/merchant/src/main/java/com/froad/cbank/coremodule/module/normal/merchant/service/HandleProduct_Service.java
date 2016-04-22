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
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Fallow_Execute_Base_Data_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Image_Info_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.ProductAudditReq;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.ProductDetailTempRes;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Product_Detail_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Product_Detail_Res;
import com.froad.cbank.coremodule.module.normal.merchant.utils.EnumTypes;
import com.froad.cbank.coremodule.module.normal.merchant.utils.ProductAuditEnum;
import com.froad.cbank.coremodule.module.normal.merchant.utils.TargetObjectFormat;
import com.froad.thrift.service.FallowExecuteService;
import com.froad.thrift.service.MerchantService;
import com.froad.thrift.service.ProductCategoryService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.vo.CreateInstanceReqVo;
import com.froad.thrift.vo.CreateInstanceResVo;
import com.froad.thrift.vo.EditProductAuditStateVo;
import com.froad.thrift.vo.MerchantDetailVo;
import com.froad.thrift.vo.MerchantVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.ProductCategoryVo;
import com.froad.thrift.vo.ProductDetailInfo;
import com.froad.thrift.vo.ProductDetailVo;
import com.froad.thrift.vo.ProductImageVo;
import com.froad.thrift.vo.ProductInfoVo;
import com.froad.thrift.vo.ProductOperateVoReq;
import com.froad.thrift.vo.TypeInfoVo;

/**
 * 管理员
 * 
 * @ClassName AdminService
 * @author zxl
 * @date 2015年3月21日 下午2:07:02
 */
@Service
public class HandleProduct_Service {
	@Resource
	ProductCategoryService.Iface productCategoryService;

	@Resource
	FallowExecuteService.Iface fallowExecuteService;
	
	@Resource
	ProductService.Iface productService;
	
	@Resource
	MerchantService.Iface merchantService;

	/**
	 * 说明 商品分类通用接口 
	 * 创建日期 2015年12月23日 下午3:02:56 
	 * 作者 artPing 
	 * 参数 @param clientId 
	 * 参数 @param productInfoVo 
	 * 参数 @return 
	 * 参数 @throws TException
	 */
	public ProductDetailTempRes setCateGorye(String clientId,ProductDetailVo productInfoVo) throws TException {
		ProductDetailTempRes res = new ProductDetailTempRes();
		if (productInfoVo!= null) {
			res.setCategoryId(String.valueOf(productInfoVo.getProductCategoryId()));
			res.setCategoryName(productInfoVo.getProductCategoryName());
			res.setTreePath(productInfoVo.getCategoryTreePath());
			/** 2015-11-07 迭代1.6需求商品多级类目 */
			long pcId = productInfoVo.getProductCategoryId();
			ProductCategoryVo procdCate = new ProductCategoryVo();
			procdCate.setClientId(clientId);
			procdCate.setId(pcId);
			ProductCategoryVo vo = productCategoryService.getProductCategoryById(procdCate);
			if (null != vo) {
				if (StringUtils.isNotBlank(productInfoVo.getCategoryTreePath())) {
					String treePaths = vo.getTreePath();
					if (StringUtils.isNotBlank(treePaths)) {
						String[] str = treePaths.split(" ");
						List<String> categoryNameList = new ArrayList<String>();
						for (int i = 0; i < str.length; i++) {
							ProductCategoryVo procdCate1 = new ProductCategoryVo();
							procdCate1.setClientId(clientId);
							procdCate1.setId(Long.parseLong(str[i].trim()));
							ProductCategoryVo vo1 = productCategoryService.getProductCategoryById(procdCate1);
							if (null != vo1) {
								categoryNameList.add(vo1.getName());
							}
						}
						res.setCategoryNameList(categoryNameList);
					}
				}
			}
		}
		return res;
	}
	
	/**
	 * 说明   商品审核（批量审核通用）
	 * 创建日期  2015年12月24日  下午1:27:18
	 * 作者  artPing
	 * 参数  @param req
	 * 参数  @return
	 * 参数  @throws MerchantException
	 * 参数  @throws Exception
	 */
	public Map<String, Object> auddit_product_batch(ProductAudditReq req) throws MerchantException, Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		OriginVo ori = new OriginVo();
		ori.setOperatorId(req.getMerchantUser().getId());
		ori.setOperatorIp(req.getIp());
		ori.setPlatType(req.getPlatType());
		ori.setOperatorUserName(req.getUserName());
		ori.setClientId(req.getClientId());
		ori.setRoleId(req.getRoleId());
		
		ori.setOrgId(String.valueOf(req.getMerchantUser().getMerchantId()));
		CreateInstanceReqVo vo=new CreateInstanceReqVo();
		vo.setOrigin(ori);
        vo.setOrgCode(req.getOrgCode());
        List<EditProductAuditStateVo> listE=new ArrayList<EditProductAuditStateVo>();
        List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>();
        
        if(StringUtils.isNotEmpty(req.getProductIds())){
        	String[] productIds=req.getProductIds().split(",");
        	String[] type=req.getTypes().split(",");int number=0;
        	for(String str:productIds){
        		
        		ProductOperateVoReq productVoReq=new ProductOperateVoReq();
        		productVoReq.setMerchantId(req.getMerchantUser().getMerchantId());
        		productVoReq.setProductId(str);
        		productVoReq.setClientId(req.getClientId());
        		productVoReq.setType(type[number++]);
        		Map<String,Object>  temp=query_product_temp_details(str,req.getClientId());
        		ProductDetailTempRes oldData=(ProductDetailTempRes)temp.get("tmepProductDeatls");
        		ProductDetailTempRes newData=(ProductDetailTempRes)temp.get("productDetail");
        		vo.setBessId(str);
        		 boolean isUpdate=false;//作为是否是更新还是新增,默认为新增审核
        		if(oldData!=null){
                	vo.setProcessType("3");
                	vo.setProcessTypeDetail("1");
                	ori.setDescription("更新审核");
                	isUpdate=true;
                }else{
                	vo.setProcessType("3");
                	vo.setProcessTypeDetail("0");
                	ori.setDescription("新增审核");
                }
        		//新增用商品详情内容      否则用临时表商品详情
        		String json="";
        		Fallow_Execute_Base_Data_Req base=new Fallow_Execute_Base_Data_Req();
        		//查询商户详情
    			MerchantDetailVo  resv = merchantService.getMerchantDetail(req.getMerchantUser().getMerchantId());
            	base.setMerchantId(newData.getMerchantId());
            	base.setMerchantName(newData.getMerchantName());
            	base.setMerchantFullName(newData.getMerchantName());
            	base.setProductId(str);
            	base.setOutletName("");//需要实现
            	base.setOutletFullName("");//需要实现
            	
            	
            	List<Long> longList = new ArrayList<Long>();
            	List<TypeInfoVo> typeInfo = resv.getTypeInfo();
            	for(TypeInfoVo typeVo : typeInfo){
            		longList.add(typeVo.getMerchantTypeId());
            	}
            	base.setTypeInfo(longList);    //商户类型
            	base.setUserOrgCode(resv.getOrgCode());  //添加商品商户的机构号
            	
            	if(isUpdate){
                	base.setProductName(oldData.getName());
                	base.setProductFullName(oldData.getFullName());
                	base.setCategoryInfo(oldData.getTreePath());  //商品分类
                	base.setStartTime(oldData.getStartTime());
                	base.setEndTime(oldData.getEndTime());
            	}else{
                	base.setProductName(newData.getName());
                	base.setProductFullName(newData.getFullName());
                	base.setCategoryInfo(newData.getTreePath());  //商品分类
                	base.setStartTime(newData.getStartTime());
                	base.setEndTime(newData.getEndTime());
                }
            	
            	if(base!=null){
                	json=JSON.toJSONString(base);
                }
                vo.setBessData(json);
        		CreateInstanceResVo res = fallowExecuteService.createInstance(vo);
        		
        		//同步商品审核
        		EditProductAuditStateVo edit=new EditProductAuditStateVo();
				edit.setAuditState(res.getResult().getResultCode().indexOf(EnumTypes.success.getCode())!=-1?"0":"3");
				edit.setProductId(str);
        		listE.add(edit);
        		
        		
        		Map<String,Object> map=new HashMap<String,Object>();
        		map.put("productId",edit.getProductId());
        		map.put("result", res.getResult().getResultCode());
        		map.put("msg", res.getResult().getResultDesc());
        		listMap.add(map);

        	}
            //商品审核同步
            updateProductAudit(listE);
//    		new ProductThead(listE,HandleProduct_Service.this);
        }
        resMap.put("Audits", listMap);
        resMap.put("code",EnumTypes.success.getCode());
        return resMap;
	}
	
    /**
     * 作为一个公共接口
     * 说明   查询商品详情query_product_details
     * 创建日期  2015年12月24日  下午2:21:00
     * 作者  artPing
     * 参数  @param req
     * 参数  @return
     * 参数  @throws MerchantException
     * 参数  @throws Exception
     */
	public Query_Product_Detail_Res query_product_details(Query_Product_Detail_Req req)
			throws MerchantException, Exception {
		LogCvt.info("商品详情数据请求参数 ： "+JSON.toJSONString(req));
		ProductOperateVoReq productVoReq=new ProductOperateVoReq();
		productVoReq.setMerchantId(req.getMerchantUser().getMerchantId());
		productVoReq.setProductId(req.getProductId());
		productVoReq.setClientId(req.getClientId());
		productVoReq.setType(req.getType());
		ProductInfoVo productInfoVo = productService.getMerchantProductDetail(productVoReq);
		LogCvt.info("商品详情server端返回数据 ：========  : "+JSON.toJSONString(productInfoVo));
		if(productInfoVo!=null){
			Query_Product_Detail_Res res=new Query_Product_Detail_Res();
			if(productInfoVo.getProduct()!=null){
				TargetObjectFormat.copyProperties(productInfoVo.getProduct(), res);
			}
			if(productInfoVo!=null){
				ProductDetailVo vo=new ProductDetailVo();
				vo.setCategoryTreePath(productInfoVo.getProductCategory().getTreePath());
				vo.setCategoryTreePathName(productInfoVo.getProductCategory().getName());
				vo.setProductCategoryId(productInfoVo.getProductCategory().getId());
				ProductDetailTempRes cate=setCateGorye(req.getClientId(),vo);
				if(cate!=null){
					res.setCategoryId(cate.getCategoryId());
					res.setCategoryName(cate.getCategoryName());
					res.setTreePath(cate.getTreePath());
					res.setCategoryNameList(cate.getCategoryNameList());
				}
			}
			if(productInfoVo.getBuyLimit()!=null){
				res.setMax(productInfoVo.getBuyLimit().getMax());
				res.setMaxVip(productInfoVo.getBuyLimit().getMaxVip());
				res.setMaxCard(productInfoVo.getBuyLimit().getMaxCard());
			}else{
				res.setMax(0);
				res.setMaxVip(0);
				res.setMaxCard(0);
			}
			if(productInfoVo.getImage()!=null&&productInfoVo.getImage().size()>0){
				List<Image_Info_Res> listRes=new ArrayList<Image_Info_Res>();
				for(ProductImageVo vo:productInfoVo.getImage()){
					Image_Info_Res pres=new Image_Info_Res();
					TargetObjectFormat.copyProperties(vo, pres);
					listRes.add(pres);
				}
				res.setImgList(listRes);
			}
			res.setCodeUrl(productInfoVo.getProduct().getCodeUrl());
			return res;
		}else{
			return null;
		}

	}
	
	
	/**
	 * 说明   查询商品详情和对比数据query_product_temp_details
	 * 创建日期  2015年12月24日  下午2:20:36
	 * 作者  artPing
	 * 参数  @param req
	 * 参数  @return
	 * 参数  @throws MerchantException
	 * 参数  @throws Exception
	 */
	public Map<String,Object> query_product_temp_details(String productId,String clientId)
			throws MerchantException, Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		ProductDetailInfo productDetailInfo = productService.getProductDetail(productId);
		if(productDetailInfo!=null){
			ProductDetailTempRes res=null;boolean isOld=false;String auditState="",auditCommit="",auditName="";
			if(productDetailInfo.getProductDetailVo()!=null){
				res=new ProductDetailTempRes();
				TargetObjectFormat.copyProperties(productDetailInfo.getProductDetailVo(), res);
				ProductDetailTempRes cate=setCateGorye(clientId,productDetailInfo.getProductDetailVo());
				if(cate!=null){
					res.setCategoryId(cate.getCategoryId());
					res.setCategoryName(cate.getCategoryName());
					res.setTreePath(cate.getTreePath());
					res.setCategoryNameList(cate.getCategoryNameList());
				}
				if(productDetailInfo.getProductDetailVo().getImages()!=null&&productDetailInfo.getProductDetailVo().getImages().size()>0){
					List<Image_Info_Res> listRes=new ArrayList<Image_Info_Res>();
					for(ProductImageVo vo:productDetailInfo.getProductDetailVo().getImages()){
						Image_Info_Res pres=new Image_Info_Res();
						TargetObjectFormat.copyProperties(vo, pres);
						listRes.add(pres);
					}
					res.setImgList(listRes);
				}
				
				if(productDetailInfo.getProductDetailVo().getBuyLimit()!=null){
					res.setMax(productDetailInfo.getProductDetailVo().getBuyLimit().getMax());
				}else{
					res.setMax(0);
				}
				res.setAuditStateName(ProductAuditEnum.values()[Integer.valueOf(res.getAuditState().trim())].getMsg());
				map.put("productDetail", res);
				isOld=true;auditState=res.getAuditState();auditCommit=res.getAuditComment();auditName=res.getAuditStateName();//作为对比数据的标志
			}
			if(productDetailInfo.getOldProductDetailVo()!=null){
				res=new ProductDetailTempRes();
				TargetObjectFormat.copyProperties(productDetailInfo.getOldProductDetailVo(), res);
				ProductDetailTempRes cate=setCateGorye(clientId,productDetailInfo.getOldProductDetailVo());
				if(cate!=null){
					res.setCategoryId(cate.getCategoryId());
					res.setCategoryName(cate.getCategoryName());
					res.setTreePath(cate.getTreePath());
					res.setCategoryNameList(cate.getCategoryNameList());
				}
				if(productDetailInfo.getOldProductDetailVo().getImages()!=null&&productDetailInfo.getOldProductDetailVo().getImages().size()>0){
					List<Image_Info_Res> listRes=new ArrayList<Image_Info_Res>();
					for(ProductImageVo vo:productDetailInfo.getOldProductDetailVo().getImages()){
						Image_Info_Res pres=new Image_Info_Res();
						TargetObjectFormat.copyProperties(vo, pres);
						listRes.add(pres);
					}
					res.setImgList(listRes);
				}
				
				if(productDetailInfo.getOldProductDetailVo().getBuyLimit()!=null){
					res.setMax(productDetailInfo.getOldProductDetailVo().getBuyLimit().getMax());
				}else{
					res.setMax(0);
				}
				res.setAuditStateName(ProductAuditEnum.values()[Integer.valueOf(res.getAuditState().trim())].getMsg());
				if(isOld){
					//将新的审核状态赋值给老数据
					res.setAuditState(auditState);res.setAuditComment(auditCommit);res.setAuditStateName(auditName);//作为对比数据的标志
				}
				map.put("tmepProductDeatls", res);
			}
			return map;
		}else{
			return null;
		}
	}
	
	/**
	 * 说明   商品提交审核后更改商品审核状态通用接口
	 * 创建日期  2015年12月25日  下午2:33:40
	 * 作者  artPing
	 * 参数  @param listProd
	 * @throws TException 
	 */
	public void updateProductAudit(List<EditProductAuditStateVo> listProd) throws TException{
		productService.updateProductAuditState(listProd);
	}
	
	/**
	 * 查询商户机构号
	 * 说明   description of the class
	 * 创建日期  2016年1月26日  下午5:50:41
	 * 作者  artPing
	 * 参数  @param merchantId
	 * 参数  @return
	 * 参数  @throws TException
	 */
	public String getorgCode(String merchantId) throws TException{
		 MerchantVo mervo=merchantService.getMerchantByMerchantId(merchantId);
	     if(mervo!=null){
	    	 return mervo.getOrgCode();
	     }
	     return null;
	}
}
