/**
 * 
 * @Title: ProductImpl.java
 * @Package com.froad.thrift.service.impl
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.thrift.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.thrift.TException;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.froad.Constants;
import com.froad.common.beans.ResultBean;
import com.froad.common.enums.ProductQuerySort;
import com.froad.common.enums.ProductTempAuditState;
import com.froad.db.mongo.ProductDetialMongo;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mysql.bean.Page;
import com.froad.db.redis.ProductRedis;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.ActivitiesStatus;
import com.froad.enums.ActivitiesType;
import com.froad.enums.DeliveryType;
import com.froad.enums.PlatTypeEnum;
import com.froad.enums.ProductAuditState;
import com.froad.enums.ProductStatus;
import com.froad.enums.ProductType;
import com.froad.enums.ResultCode;
import com.froad.enums.SeckillFlagEnum;
import com.froad.ftp.ExcelWriter;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.MerchantLogic;
import com.froad.logic.ProductLogic;
import com.froad.logic.VipProductLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.logic.impl.MerchantLogicImpl;
import com.froad.logic.impl.ProductLogicImpl;
import com.froad.logic.impl.VipProductLogicImpl;
import com.froad.monitor.MonitorService;
import com.froad.monitor.impl.MonitorManager;
import com.froad.po.ActivitiesInfo;
import com.froad.po.Area;
import com.froad.po.Client;
import com.froad.po.Org;
import com.froad.po.OutletProduct;
import com.froad.po.OutletProductInfo;
import com.froad.po.OutletProductQrCode;
import com.froad.po.Product;
import com.froad.po.ProductAreaOutlet;
import com.froad.po.ProductBaseInfo;
import com.froad.po.ProductCategory;
import com.froad.po.ProductComment;
import com.froad.po.ProductCommentFilter;
import com.froad.po.ProductDetailSimple;
import com.froad.po.ProductFilter;
import com.froad.po.ProductGroup;
import com.froad.po.ProductInfo;
import com.froad.po.ProductListInfo;
import com.froad.po.ProductPresell;
import com.froad.po.ProductTemp;
import com.froad.po.ProductViewInfo;
import com.froad.po.Result;
import com.froad.po.VipBindProductInfo;
import com.froad.po.mongo.ProductActivitiesInfo;
import com.froad.po.mongo.ProductArea;
import com.froad.po.mongo.ProductBuyLimit;
import com.froad.po.mongo.ProductCategoryInfo;
import com.froad.po.mongo.ProductCityOutlet;
import com.froad.po.mongo.ProductCityArea;
import com.froad.po.mongo.ProductCommentInfo;
import com.froad.po.mongo.ProductDetail;
import com.froad.po.mongo.ProductImage;
import com.froad.po.mongo.ProductOutlet;
import com.froad.po.mongo.StoreProductInfo;
import com.froad.vo.ProductFilterStr;
import com.froad.sword.SensitivewordFilter;
import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.vo.GroupProductPageVoRes;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.service.SenseWordsService;
import com.froad.thrift.vo.ActivityProductPageVo;
import com.froad.thrift.vo.ActivityProductVo;
import com.froad.thrift.vo.AddOutletProductVoRes;
import com.froad.thrift.vo.AddProductVoRes;
import com.froad.thrift.vo.AreaVo;
import com.froad.thrift.vo.BoutiqueGoodsInfoVo;
import com.froad.thrift.vo.BoutiqueProductPageVoRes;
import com.froad.thrift.vo.BoutiqueProductVo;
import com.froad.thrift.vo.EditProductAuditStateVo;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.FiledSort;
import com.froad.thrift.vo.GoodsInfoVo;
import com.froad.thrift.vo.GroupProductOutletVo;
import com.froad.thrift.vo.GroupProductVo;
import com.froad.thrift.vo.InvalidProductBatchVo;
import com.froad.thrift.vo.MerchantProductPageVoRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.OutletDetailSimplePageVoRes;
import com.froad.thrift.vo.OutletDetailSimpleVo;
import com.froad.thrift.vo.OutletProductDiscountVo;
import com.froad.thrift.vo.OutletProductPageVo;
import com.froad.thrift.vo.OutletProductQrCodeVo;
import com.froad.thrift.vo.OutletProductVo;
import com.froad.thrift.vo.OutletProductVoReq;
import com.froad.thrift.vo.OutletProductVoRes;
import com.froad.thrift.vo.OutletVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ProductActivitiesVo;
import com.froad.thrift.vo.ProductAreaVo;
import com.froad.thrift.vo.ProductAuditVo;
import com.froad.thrift.vo.ProductBaseInfoVo;
import com.froad.thrift.vo.ProductBaseVo;
import com.froad.thrift.vo.ProductBriefInfoVo;
import com.froad.thrift.vo.ProductBriefPageVo;
import com.froad.thrift.vo.ProductBriefPageVoRes;
import com.froad.thrift.vo.ProductBriefVo;
import com.froad.thrift.vo.ProductBriefVoRes;
import com.froad.thrift.vo.ProductBuyLimitVo;
import com.froad.thrift.vo.ProductCategoryVo;
import com.froad.thrift.vo.ProductCommentFilterReq;
import com.froad.thrift.vo.ProductCommentPageVo;
import com.froad.thrift.vo.ProductCommentVo;
import com.froad.thrift.vo.ProductDetailInfo;
import com.froad.thrift.vo.ProductDetailVo;
import com.froad.thrift.vo.ProductExistVoReq;
import com.froad.thrift.vo.ProductFilterReqVo;
import com.froad.thrift.vo.ProductFilterVoReq;
import com.froad.thrift.vo.ProductImageVo;
import com.froad.thrift.vo.ProductInfoVo;
import com.froad.thrift.vo.ProductListPageVo;
import com.froad.thrift.vo.ProductListVo;
import com.froad.thrift.vo.ProductOperateVoReq;
import com.froad.thrift.vo.ProductOutletPageVo;
import com.froad.thrift.vo.ProductOutletVo;
import com.froad.thrift.vo.ProductPageVo;
import com.froad.thrift.vo.ProductPageVoRes;
import com.froad.thrift.vo.ProductRelateActivitiesVo;
import com.froad.thrift.vo.ProductRelateOutletVo;
import com.froad.thrift.vo.ProductSearchVo;
import com.froad.thrift.vo.ProductStatusBatchVoReq;
import com.froad.thrift.vo.ProductStatusVoReq;
import com.froad.thrift.vo.ProductStoreFilterVo;
import com.froad.thrift.vo.ProductVo;
import com.froad.thrift.vo.QueryBoutiqueGoodsFilterVo;
import com.froad.thrift.vo.QueryProductFilterVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.StoreProductInfoPageVoRes;
import com.froad.thrift.vo.StoreProductInfoVo;
import com.froad.util.Arith;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.DateUtil;
import com.froad.util.DownUtils;
import com.froad.util.GoodsConstants;
import com.froad.util.GoodsSortConstants;
import com.froad.util.JSonUtil;
import com.froad.util.LogUtils;
import com.froad.util.ProductBeanUtil;
import com.froad.util.RedisKeyUtil;
import com.froad.util.Util;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 
 * <p>@Title: ProductImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class ProductServiceImpl extends BizMonitorBaseService implements ProductService.Iface {
	private ProductLogic productLogic = new ProductLogicImpl();
    private VipProductLogic vipProductLogic = new VipProductLogicImpl();
	public ProductServiceImpl() {}
	public ProductServiceImpl(String name, String version) {
        super(name, version);
    }
	/**
     * 监控服务
     * */
    private MonitorService monitorService = new MonitorManager();


	/**
     * 新加商品接口
     * @param ProductInfoVo
     * @return long    主键ID
     */
    @Override
    public AddProductVoRes addProduct(OriginVo originVo, ProductInfoVo productInfoVo) throws TException {
    	long startTime = new Date().getTime();
        LogCvt.debug("添加商品参数:"+"originVo:"+JSON.toJSONString(originVo)+",Product:"+JSON.toJSONString(productInfoVo));
        AddProductVoRes resVo = new AddProductVoRes();
        ResultVo result = new ResultVo();
        resVo.setProductId(null);
        try {
        	String describe="";
        	//添加操作日志记录
        	if(Checker.isNotEmpty(productInfoVo)){
        		//商品类型:1团购、2预售、3名优特惠、4在线积分兑换、5网点礼品
        		String type=productInfoVo.getProduct().getType();
        		if(ProductType.group.getCode().equals(type)){
        			describe=ProductType.group.getDescribe();
        		}else if(ProductType.presell.getCode().equals(type)){
        			describe=ProductType.presell.getDescribe();
        		}else if(ProductType.special.getCode().equals(type)){
        			describe=ProductType.special.getDescribe();
        		}else if(ProductType.onlinePoint.getCode().equals(type)){
        			describe=ProductType.onlinePoint.getDescribe();
        		}else if(ProductType.dotGift.getCode().equals(type)){
        			describe=ProductType.dotGift.getDescribe();
        		}
        	}
			originVo.setDescription("添加"+describe+"商品信息");
			LogUtils.addLog(originVo);
			
			//设置平台参数 是boss平台还是银行机构平台还是商户平台等等
            if(productInfoVo.getPlatType()==null || "".equals(productInfoVo.getPlatType())){
                if(originVo.getPlatType()!=null){
                    productInfoVo.setPlatType(originVo.getPlatType().getValue()+"");
                }
            }
            //验证数据的合法性
            ResultVo validationResultVo = validationFileds(productInfoVo,"add");
            if(validationResultVo!=null){
                resVo.setResult(validationResultVo);
                return resVo;
            }
            //将前端传过来的vo转换成后端的po结构
            ProductInfo productInfo = this.changFromProductInfoVo(productInfoVo);
            if(productInfo!=null){
                ResultBean resultBean = productLogic.addProduct(productInfo);
                if(resultBean!=null){
                    result.setResultCode(resultBean.getCode());
                    result.setResultDesc(resultBean.getMsg());
                    resVo.setProductId((String)resultBean.getData());
                } else {
                    result.setResultCode(ResultCode.failed.getCode());
                    result.setResultDesc("添加商品失败");
                }
                resVo.setResult(result);
                return resVo;
            } else{
                result.setResultCode(ResultCode.failed.getCode());
                result.setResultDesc("商品基础信息不能为空");
                resVo.setResult(result);
                return resVo;
            }
        } catch (Exception e) {
            LogCvt.error("添加商品失败, 原因:" + e.getMessage(), e);
            result.setResultCode(ResultCode.failed.getCode());
            result.setResultDesc("添加商品失败");
        }
        long endTime = new Date().getTime();
        LogCvt.debug("添加商品开始时间:"+startTime);
        LogCvt.debug("添加商品结束时间:"+endTime);
        LogCvt.debug("添加商品总用时时间(ms):"+(endTime-startTime));
        return resVo;
    }
    
    /**
     * 判断能否新加 指定商品类型只能在指定平台新加
     * @param type
     * @param platType
     * @return Boolean
     */
    private Boolean isValidTypeForPlatType(String type,String platType){
        String typesPlatTypes = GoodsConstants.GOODS_PLAT_TYPE_PRODUCT_TYPE;
        if(typesPlatTypes!=null && !"".equals(typesPlatTypes)){
            String[] typePlatTypes = typesPlatTypes.split(";");
            for(String typePlatType : typePlatTypes){
                String[] typePlats = typePlatType.split(":");
                if(typePlats.length==2 && type.equals(typePlats[0])){
                    if(platType.equals(typePlats[1])){
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    /**
     * 删除 Product
     * @param product
     * @return boolean    
     */
    @Override
    public ResultVo deleteProduct(OriginVo originVo, ProductOperateVoReq productVoReq)
            throws TException {
        LogCvt.info("删除商品参数,originVo:"+JSON.toJSONString(originVo)+",productVoReq:"+JSON.toJSONString(productVoReq));
        ResultVo resultVo = new ResultVo();
        try {
        	//添加操作日志记录
			originVo.setDescription("删除商品信息");
			LogUtils.addLog(originVo);
			
            if(productVoReq.getClientId()!=null && !"".equals(productVoReq.getClientId()) && productVoReq.getProductId()!=null && !"".equals(productVoReq.getProductId())){
                Product product = new Product();
                product.setClientId(productVoReq.getClientId());
                product.setMerchantId(productVoReq.getMerchantId());
                product.setProductId(productVoReq.getProductId());
                Boolean result = productLogic.deleteProduct(product);
                if(result){
                    resultVo.setResultCode(ResultCode.success.getCode());
                    resultVo.setResultDesc("删除商品成功");
                } else {
                    resultVo.setResultCode(ResultCode.failed.getCode());
                    resultVo.setResultDesc("删除商品失败");
                }
            } else {
                resultVo.setResultCode(ResultCode.failed.getCode());
                resultVo.setResultDesc("clinetId不能为空，productId不能为空");
            }
        } catch (Exception e) {
            LogCvt.error("删除商品失败, 原因:" + e.getMessage(), e);
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("删除商品失败");
        }
        return resultVo;
    }


    /**
     * 修改 Product
     * @param product
     * @return boolean    
     */
    @Override
    public ResultVo updateProduct(OriginVo originVo, ProductInfoVo productInfoVo) throws TException {
        LogCvt.info("修改商品参数,originVo:"+JSON.toJSONString(originVo)+",productInfoVo:"+JSON.toJSONString(productInfoVo));
        ResultVo resultVo = new ResultVo();
        try {
        	//添加操作日志记录
        	originVo.setDescription("修改商品信息");
			LogUtils.addLog(originVo);
			
            if(productInfoVo.getPlatType()==null || "".equals(productInfoVo.getPlatType())){
                if(originVo.getPlatType()!=null){
                    productInfoVo.setPlatType(originVo.getPlatType().getValue()+"");
                }
            }
            //验证数据的合法性
            ResultVo validationResultVo = validationFileds(productInfoVo,"update");
            if(validationResultVo!=null){
                return validationResultVo;
            }
            if(productInfoVo.getProduct().getProductId()==null || "".equals(productInfoVo.getProduct().getProductId())){
                resultVo.setResultCode(ResultCode.failed.getCode());
                resultVo.setResultDesc("商品id不能为空");
                return resultVo;
            }
            //vo转换成po
            ProductInfo productInfo = this.changFromProductInfoVo(productInfoVo);
            if(productInfo!=null){
                Result result = productLogic.updateProduct(productInfo);
                ProductBeanUtil.copyProperties(resultVo, result);
            } else {
                resultVo.setResultCode(ResultCode.failed.getCode());
                resultVo.setResultDesc("商品基础信息不能为空");
            }
        } catch (Exception e) {
            LogCvt.error("修改商品失败, 原因:" + e.getMessage(), e);
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("修改商品失败");
        }
        return resultVo;
    }
    
    /**
     * 验证商品基本字段.
     * @param productInfoVo
     * @param opeStr 新增还是修改的字符串
     * @return
     * @return ResultVo
     */
    private ResultVo validationFileds(ProductInfoVo productInfoVo,String opeStr){
        ResultVo resultVo = null;
        
        if(productInfoVo.getPlatType()==null || "".equals(productInfoVo.getPlatType())){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("没有指定新加平台，不能"+opeStr+"商品");
            return resultVo;
        }
        if(productInfoVo.getProduct()==null){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品基础信息不能为空");
            return resultVo;
        }
        ProductVo productVo = productInfoVo.getProduct();
        if(productVo.getClientId()==null || "".equals(productVo.getClientId())){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("所属客户端不能为空");
            return resultVo;
        }
        if(productInfoVo.getProduct().getType()==null || "".equals(productInfoVo.getProduct().getType())){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品类型不能为空");
            return resultVo;
        }
        if("update".equals(opeStr)){
//            if(ProductStatus.onShelf.toString().equals(productVo.getIsMarketable())){
//                resultVo = new ResultVo();
//                resultVo.setResultCode(ResultCode.failed.getCode());
//                resultVo.setResultDesc("已上架的商品不允许编辑");
//                return resultVo;
//            }
            if(productVo.getProductId()==null || "".equals(productVo.getProductId())){
                resultVo = new ResultVo();
                resultVo.setResultCode(ResultCode.failed.getCode());
                resultVo.setResultDesc("修改商品时productId不能为空");
                return resultVo;
            }
        }
        
        //判断指定商品类型能否在指定的平台新加或修改
        boolean isValid = isValidTypeForPlatType(productInfoVo.getProduct().getType(),productInfoVo.getPlatType());
        if(isValid==false){
            resultVo = new ResultVo();
            ProductType productType = ProductType.getType(productInfoVo.getProduct().getType());
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc(productType.getDescribe()+"类型的商品只能在指定的平台上"+opeStr+"，"+opeStr+"商品失败");
            return resultVo;
        }
        if(productVo.getPrice()<=0.0){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("销售价必填");
            return resultVo;
        }
        if(Util.getDoubleDecimalNum(productVo.getPrice())>2){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("销售价只能有2位有效小数");
            return resultVo;
        }
//        if(productVo.getMarketPrice()<=0.0){
//            resultVo = new ResultVo();
//            resultVo.setResultCode(ResultCode.failed.getCode());
//            resultVo.setResultDesc("市场价必填");
//            return resultVo;
//        }
        if(productVo.getMarketPrice()>0.0 && Util.getDoubleDecimalNum(productVo.getMarketPrice())>2){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("市场价只能有2位有效小数");
            return resultVo;
        }
        if(productVo.getName()==null || "".equals(productVo.getName())){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品简称不能为空");
            return resultVo;
        }
        if(productVo.getName().length()>100){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品简称不能超过100个字符");
            return resultVo;
        }
//        if(productVo.getFullName()==null || "".equals(productVo.getFullName())){
//            resultVo = new ResultVo();
//            resultVo.setResultCode(ResultCode.failed.getCode());
//            resultVo.setResultDesc("商品全称不能为空");
//            return resultVo;
//        }
        if(productVo.getFullName()!=null && !"".equals(productVo.getFullName()) && productVo.getFullName().length()>200){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品全称不能超过200个字符");
            return resultVo;
        }
        if(productVo.getOrgCode()==null || "".equals(productVo.getOrgCode())){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品所属机构不能为空");
            return resultVo;
        }
        if(productVo.getStore()<1){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品库存不能为空");
            return resultVo;
        }
        if(productInfoVo.getImage()==null || productInfoVo.getImage().size()<1){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品图片不能为空");
            return resultVo;
        }
        if(productVo.getDeliveryOption()!=null && !"".equals(productVo.getDeliveryOption())){
            String typesDeliveryOptions = GoodsConstants.GOODS_PRODUCT_TYPE_DELIVERY_OPTION;
            if(typesDeliveryOptions!=null && !"".equals(typesDeliveryOptions)){
                String[] typeDeliveryOptions = typesDeliveryOptions.split(";");
                if(typeDeliveryOptions!=null && typeDeliveryOptions.length>0){
                    String deliveryOptionsStr = null;
                    for(String typeDeliveryOption : typeDeliveryOptions){
                        if(typeDeliveryOption.indexOf(productVo.getType()+":")!=-1){
                            deliveryOptionsStr = typeDeliveryOption.substring(typeDeliveryOption.indexOf(":")+":".length(),typeDeliveryOption.length());
                            break;
                        }
                    }
                    if(deliveryOptionsStr!=null && !"".equals(deliveryOptionsStr)){
                        String[] deliveryOptions = deliveryOptionsStr.split(",");
                        if(deliveryOptions!=null && deliveryOptions.length>0){
                            boolean isValidDeOp = false;
                            for(String deliveryOption : deliveryOptions){
                                if(productVo.getDeliveryOption().equals(deliveryOption)){
                                    isValidDeOp = true;
                                    break;
                                }
                            }
                            if(isValidDeOp==false){
                                resultVo = new ResultVo();
                                resultVo.setResultCode(ResultCode.failed.getCode());
                                ProductType pt = ProductType.getType(productVo.getType());
                                
                                StringBuilder dtNames = new StringBuilder();
                                DeliveryType dt = null;
                                for(String deliveryOption : deliveryOptions){
                                    dt = DeliveryType.getType(deliveryOption);
                                    if(dt!=null){
                                        dtNames.append(dt.getDescribe()).append(",");
                                    }
                                }
                                if(pt!=null){
                                    resultVo.setResultDesc(pt.getDescribe()+"类型商品可以选择的配送方式只能有"+dtNames.toString());
                                } else {
                                    resultVo.setResultDesc("该类型商品可以选择的配送方式只能有"+dtNames.toString());
                                }
                                return resultVo;
                            }
                        }
                    }
                }
            }
        } else {
            productVo.setDeliveryOption(getDeliveryOption(productVo.getType()));
        }
        if(productVo.getDeliveryOption()==null || "".equals(productVo.getDeliveryOption())){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品的配送方式不能为空");
            return resultVo;
        }
        if(productVo.getType().equals(ProductType.group.getCode())){
            if(productVo.getExpireStartTime()==0){
                productVo.setExpireStartTime(productVo.getDeliveryStartTime());
            }
            if(productVo.getExpireEndTime()==0){
                productVo.setExpireEndTime(productVo.getDeliveryEndTime());
            }
            if(productVo.getExpireStartTime()>=productVo.getExpireEndTime()){
                resultVo = new ResultVo();
                resultVo.setResultCode(ResultCode.failed.getCode());
                resultVo.setResultDesc("团购类型商品有券有效开始时间只能小于券有效结束时间");
                return resultVo;
            }
            //只要是自提的都限制
            if(productVo.getDeliveryOption().equals(DeliveryType.take.toString()) || productVo.getDeliveryOption().equals(DeliveryType.home_or_take.toString())){
                if(productVo.getExpireEndTime()==0){
                    resultVo = new ResultVo();
                    resultVo.setResultCode(ResultCode.failed.getCode());
                    resultVo.setResultDesc("配送方式可以自提的团购类型商品券有效结束时间不能为空");
                    return resultVo;
                }
            }
            
        } else if(productVo.getType().equals(ProductType.presell.getCode())){
            if(productVo.getExpireStartTime()==0){
                productVo.setExpireStartTime(productVo.getDeliveryStartTime());
            }
            if(productVo.getExpireEndTime()==0){
                productVo.setExpireEndTime(productVo.getDeliveryEndTime());
            }
            if(productVo.getExpireStartTime()>=productVo.getExpireEndTime()){
                resultVo = new ResultVo();
                resultVo.setResultCode(ResultCode.failed.getCode());
                resultVo.setResultDesc("预售类型商品有券有效开始时间只能小于券有效结束时间");
                return resultVo;
            }
            if(productVo.getDeliveryStartTime()>=productVo.getDeliveryEndTime()){
                resultVo = new ResultVo();
                resultVo.setResultCode(ResultCode.failed.getCode());
                resultVo.setResultDesc("预售类型商品提货开始时间只能小于提货结束时间");
                return resultVo;
            }
            
            //只要是自提的都限制
            if(productVo.getDeliveryOption().equals(DeliveryType.take.toString()) || productVo.getDeliveryOption().equals(DeliveryType.home_or_take.toString())){
                if(productVo.getExpireStartTime()==0){
                    resultVo = new ResultVo();
                    resultVo.setResultCode(ResultCode.failed.getCode());
                    resultVo.setResultDesc("配送方式可以自提的预售类型商品券有效开始时间不能为空");
                    return resultVo;
                }
                if(productVo.getExpireEndTime()==0){
                    resultVo = new ResultVo();
                    resultVo.setResultCode(ResultCode.failed.getCode());
                    resultVo.setResultDesc("配送方式可以自提的预售类型商品券有效结束时间不能为空");
                    return resultVo;
                }
                if(productVo.getDeliveryStartTime()==0){
                    resultVo = new ResultVo();
                    resultVo.setResultCode(ResultCode.failed.getCode());
                    resultVo.setResultDesc("配送方式可以自提的预售类型商品提货开始时间不能为空");
                    return resultVo;
                }
                if(productVo.getDeliveryEndTime()==0){
                    resultVo = new ResultVo();
                    resultVo.setResultCode(ResultCode.failed.getCode());
                    resultVo.setResultDesc("配送方式可以自提的预售类型商品提货结束时间不能为空");
                    return resultVo;
                }
                if(productVo.getDeliveryMoney()>0.0 && Util.getDoubleDecimalNum(productVo.getDeliveryMoney())>2){
                    resultVo = new ResultVo();
                    resultVo.setResultCode(ResultCode.failed.getCode());
                    resultVo.setResultDesc("运费只能有2位有效小数");
                    return resultVo;
                }
            }
        }
//        if(productVo.getBriefIntroduction()==null || "".equals(productVo.getBriefIntroduction()) ){
//            resultVo = new ResultVo();
//            resultVo.setResultCode(ResultCode.failed.getCode());
//            resultVo.setResultDesc("商品副标题不能为空");
//            return resultVo;
//        }
        
        if(productVo.getStartTime()==0){
            productVo.setStartTime(new Date().getTime());
        }
        if(productVo.getEndTime()==0){
            productVo.setEndTime(GoodsConstants.FUTURE_END_DATE.getTime());
        }
        if(productVo.getStartTime()>=productVo.getEndTime()){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品销售期开始时间只能小于结束时间");
            return resultVo;
        }
        if(productVo.isLimit && productInfoVo.getBuyLimit()!=null){
            if(productInfoVo.getBuyLimit().getStartTime()==0){
                productInfoVo.getBuyLimit().setStartTime(productVo.getStartTime());
            }
            if(productInfoVo.getBuyLimit().getEndTime()==0){
                productInfoVo.getBuyLimit().setEndTime(productVo.getEndTime());
            }
            if(productInfoVo.getBuyLimit().getStartTime()>productInfoVo.getBuyLimit().getEndTime()){
                resultVo = new ResultVo();
                resultVo.setResultCode(ResultCode.failed.getCode());
                resultVo.setResultDesc("商品限购开始时间只能小于等于结束时间");
                return resultVo;
            }
        }
        return resultVo;
    }
    
    /**
     * 根据商品类型获取默认的配送方式
     * @param type
     * @return String
     */
    private String getDeliveryOption(String type){
        String typesDeliveryOptions = GoodsConstants.GOODS_PRODUCT_TYPE_DELIVERY_OPTION;
        if(typesDeliveryOptions!=null && !"".equals(typesDeliveryOptions)){
            String[] typeDeliveryOptions = typesDeliveryOptions.split(";");
            if(typeDeliveryOptions!=null && typeDeliveryOptions.length>0){
                String deliveryOptionsStr = null;
                for(String typeDeliveryOption : typeDeliveryOptions){
                    if(typeDeliveryOption.indexOf(type+":")!=-1){
                        deliveryOptionsStr = typeDeliveryOption.substring(typeDeliveryOption.indexOf(":")+":".length());
                        break;
                    }
                }
                if(deliveryOptionsStr!=null && !"".equals(deliveryOptionsStr)){
                    String[] deliveryOptions = deliveryOptionsStr.split(",");
                    if(deliveryOptions!=null && deliveryOptions.length>0){
                        return deliveryOptions[0];
                    }
                }
            }
        }
        return null;
    }


    /**
     * 查询 Product
     * @param product
     * @return List<ProductVo>
     */
    @Override
    public ProductInfoVo getMerchantProductDetail(
            ProductOperateVoReq productVoReq) throws TException {
        LogCvt.info("管理平台查看商品明细参数productVoReq:"+JSON.toJSONString(productVoReq));
        Product product = new Product();
        //参数vo转换成po
        ProductBeanUtil.copyProperties(product, productVoReq);
        //查询明细
        ProductViewInfo productViewInfo = productLogic.getProduct(product);
        if(productViewInfo!=null){
        	if("".equals(productViewInfo.getCodeUrl()) || productViewInfo.getCodeUrl()==null){
                Product codeInfo=productLogic.findUrlCodeById(productViewInfo.getProductId());
                productViewInfo.setCodeUrl(codeInfo.getCodeUrl());
            }
        }
        
        ProductInfoVo productInfoVo = new ProductInfoVo();
        if (productViewInfo!=null) {
            ProductVo vo = new ProductVo();
            //返回vo 查询出的po转换成vo,价格等 表中int的转换成vo的double类型需要除以1000
            ProductBeanUtil.copyPropertiesScale(vo, productViewInfo, GoodsConstants.DOUBLE_INTGER_CHANGE);
            if(vo.getClientId()!=null && !"".equals(vo.getClientId())){
            	CommonLogic commonLogic = new CommonLogicImpl();
            	Client c = commonLogic.getClientById(vo.getClientId());
            	if(c!=null){
            		vo.setClientName(c.getName());
            	}
            	//设置对应的机构名称
                if(vo.getClientId()!=null && vo.getOrgCode()!=null && !"".equals(vo.getOrgCode())){
                    Org org = commonLogic.queryByOrgCode(vo.getClientId(), vo.getOrgCode());
                    if(org!=null){
                        vo.setOrgName(org.getOrgName());
                    }
                }
            }
            
            productInfoVo.setProduct(vo);
            //获取mongo中的商品多表关系的信息查出来设置到返回的商品中
            getProductAttachProperties(productInfoVo,product,"PC");
        }
        
        return productInfoVo;
    }
    
    /**
     * 获取mongo中的信息
     * @param productInfoVo
     * @param product
     * @param flag "H5","PC"
     * @return ProductDetail
     */
    private ProductDetail getProductAttachProperties(ProductInfoVo productInfoVo,Product product,String flag){
        long pdstart = System.currentTimeMillis();
        ProductDetail pd = productLogic.getProductDetail(product,null);//查询mongo中的数据
        long pdend = System.currentTimeMillis();
        LogCvt.info("H5用户查看商品详情查询商品mongo总耗时:"+(pdend-pdstart));
        if(pd!=null){
        	if(pd.getDeliveryTime()!=null){
        		productInfoVo.getProduct().setDeliveryTime(pd.getDeliveryTime().getTime());
        		productInfoVo.getProduct().setIsBatchDelivery(true);
        	} else {
        		productInfoVo.getProduct().setIsBatchDelivery(false);
        	}
            
            if(pd.getIsRecommend()!=null && pd.getIsRecommend()==1){
            	productInfoVo.getProduct().setIsRecommend(true);
            } else {
            	productInfoVo.getProduct().setIsRecommend(false);
            }
            if(pd.getIsHot()!=null && pd.getIsHot()==1){
            	productInfoVo.getProduct().setIsHot(true);
            } else {
            	productInfoVo.getProduct().setIsHot(false);
            }
            if(pd.getIsNew()!=null && pd.getIsNew()==1){
            	productInfoVo.getProduct().setIsNew(true);
            } else {
            	productInfoVo.getProduct().setIsNew(false);
            }
            
            //缓存中或mysql中没有获取到就去mongo的商户名称
            if(productInfoVo.getProduct().getMerchantName()==null
                    || "".equals(productInfoVo.getProduct().getMerchantName())){
                productInfoVo.getProduct().setMerchantName(pd.getMerchantName());
            }
            RedisManager redis = new RedisManager();
            //管理端才返回分类信息
            if(flag.equals("PC")){
                //获取商品分类信息
                long cstart = System.currentTimeMillis();
                ProductCategoryVo productCategory = new ProductCategoryVo();
                List<ProductCategoryInfo> pcpos = pd.getProductCategoryInfo();
                if(pcpos!=null && pcpos.size()>0){
                  //获取分类名称 优先去redis获取 获取不到再去mysql获取
                    CommonLogic common = new CommonLogicImpl();
                    ProductCategoryInfo pcpo = pcpos.get(0);
                    //最大的分类节点是该商品对应的分类
                    ProductCategory pc1 = null;
                    ProductCategory pc2 = null;
                    for(ProductCategoryInfo pcpo2 : pcpos){
                        pc1 = common.findCategoryById(pd.getClientId(), pcpo.getProductCategoryId());
                        pc2 = common.findCategoryById(pd.getClientId(), pcpo2.getProductCategoryId());
                        if(pc1!=null && pc2!=null && pc1.getTreePath()!=null && pc2.getTreePath()!=null){
                            if(pc2.getTreePath().length()>pc1.getTreePath().length()){
                                pcpo = pcpo2;
                            }
                        } else if(pcpo2.getProductCategoryId()>pcpo.getProductCategoryId()){
                            pcpo = pcpo2;
                        }
                    }
                    ProductCategory pc = common.findCategoryById(pd.getClientId(), pcpo.getProductCategoryId());
                    if(pc!=null){
                        productCategory.setName(pc.getName());
                        productCategory.setTreePath(pc.getTreePath());
                        if(pc.getParentId()!=null){
                        	productCategory.setParentId(pc.getParentId());
                        }
                    }
                    productCategory.setId(pcpo.getProductCategoryId());
                    
                    productInfoVo.setProductCategory(productCategory);
                }
                long cend = System.currentTimeMillis();
                LogCvt.info("H5用户查看商品详情查询mongo商品分类信息耗时:"+(cend-cstart));
            }
            //VIP价格 是否是vip价
            if(pd.getVipPrice()!=null && pd.getVipPrice()>0){
                productInfoVo.getProduct().setVipPrice(Arith.div(pd.getVipPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));
                productInfoVo.getProduct().setIsVip(true);
            } else {
                productInfoVo.getProduct().setIsVip(false);
            }
            
            //获取限购信息
            if(productInfoVo.getProduct().isLimit==true && pd.getBuyLimit()!=null){
                ProductBuyLimitVo buyLimit = new ProductBuyLimitVo();
                ProductBeanUtil.copyProperties(buyLimit, pd.getBuyLimit());
                productInfoVo.setBuyLimit(buyLimit);
            }
            //获取图片信息
            List<ProductImage> images = pd.getImageInfo();
            if(images!=null && images.size()>0){
                List<ProductImageVo> image = new ArrayList<ProductImageVo>();
                ProductImageVo imageVo = null;
                for(ProductImage imagePo : images){
                    imageVo = new ProductImageVo();
                    ProductBeanUtil.copyProperties(imageVo, imagePo);
                    image.add(imageVo);
                }
                productInfoVo.setImage(image);
            }
            
            //获取对应的门店信息
            List<ProductCityOutlet> orgOutlets = pd.getOrgOutlets();
            if(orgOutlets!=null && orgOutlets.size()>0){
                List<ProductOutletVo> outletVos = new ArrayList<ProductOutletVo>();
                ProductOutletVo outletVo = null;
                
                long cityId = 0L;
            	long areaId = 0L;
                if(product.getCityId()!=null && product.getCityId()>0){
                	CommonLogic com = new CommonLogicImpl();
                    Area area = com.findAreaById(product.getCityId());
                    if(area!=null){
                        //最后的节点
                        if(area.getTreePath()!=null){
                            String[] areas = area.getTreePath().split(",");
                            if(areas.length==2){//两级代表市
                            	cityId = product.getCityId();
                            } else if(areas.length==3) {//3级代表区
                            	cityId = Long.valueOf(areas[1]);
                            	areaId = product.getCityId();
                            }
                        }
                    }
                } 
                for(ProductCityOutlet cityOutlet : orgOutlets){
                    if(cityId==0 || (cityId>0 && cityId==cityOutlet.getCityId().longValue())){
                        List<ProductOutlet> outlets = cityOutlet.getOrgOutlets();
                        for(ProductOutlet outlet : outlets){
                        	if(areaId==0 || (areaId>0 && areaId==outlet.getAreaId().longValue())){
                        		outletVo = new ProductOutletVo();
                                ProductBeanUtil.copyProperties(outletVo, outlet);
                                outletVos.add(outletVo);
                        	}
                            
                        }
                    }
                }
                productInfoVo.setOutlet(outletVos);
            }
            
            //获取对应的活动信息
            long astart = System.currentTimeMillis();
            List<ProductActivitiesInfo> activitiesPos = pd.getActivitiesInfo();
            if(activitiesPos!=null && activitiesPos.size()>0){
                
                List<Long> activitiesIds = new ArrayList<Long>();
                List<ProductActivitiesVo> activities = new ArrayList<ProductActivitiesVo>();
                ProductActivitiesVo activitiesVo = null;
                //活动表缓存信息
                String ackey = null;
                Map<String,String> acRedisMap = null;
                for(ProductActivitiesInfo activitiesPo : activitiesPos){
                    ackey = RedisKeyUtil.cbbank_activities_client_id_activities_id(product.getClientId(),activitiesPo.getActivitiesId()) ;
                    acRedisMap = redis.getMap(ackey);
                    if(acRedisMap!=null && acRedisMap.size()>0){
                        if(flag.equals("H5")){//H5用户查询明细时已生效的送积分活动才可以看到
                            if(String.valueOf(ActivitiesStatus.yes.getCode()).equals(acRedisMap.get("status"))){
                                activitiesVo =  new ProductActivitiesVo();
                            }
                        } else {//管理平台查询明细时
                            activitiesVo =  new ProductActivitiesVo();
                        }
                        if(activitiesVo!=null){
                            activitiesVo.setActivitiesId(activitiesPo.getActivitiesId());
                            activitiesVo.setActivitiesType(activitiesPo.getActivitiesType());
                            if(Checker.isNotEmpty(acRedisMap.get("points"))){
                                activitiesVo.setPoints(Arith.div(Integer.valueOf(acRedisMap.get("points")), GoodsConstants.DOUBLE_INTGER_CHANGE));
                            }
                            activities.add(activitiesVo);
                        }
                    } else {
                        activitiesIds.add(activitiesPo.getActivitiesId());
                    }
                }
                if(activitiesIds.size()>0){
                    Map<String,Object> param = new HashMap<String,Object>();
                    param.put("clientId", product.getClientId());
                    if(activitiesIds.size()==0){
                        param.put("id", activitiesIds.get(0));
                    } else {
                        param.put("activitiesIds", activitiesIds);
                    }
                    if(flag.equals("H5")){//H5用户查询明细时启用的活动才可以看到
                        param.put("status",ActivitiesStatus.yes.getCode());
                    }
                    List<ActivitiesInfo> acInfos = productLogic.getPointActivities(param);
                    if(acInfos!=null && acInfos.size()>0){
                        for(ActivitiesInfo activitiesPo : acInfos){
                            activitiesVo =  new ProductActivitiesVo();
                            activitiesVo.setPoints(Arith.div(activitiesPo.getPoints(), GoodsConstants.DOUBLE_INTGER_CHANGE));
                            activitiesVo.setActivitiesType(ActivitiesType.point.getCode());
                            activitiesVo.setActivitiesId(activitiesPo.getId());
                            activities.add(activitiesVo);
                        }
                    }
                }
                productInfoVo.setActivities(activities);
                if(activities.size()>0){
                    productInfoVo.getProduct().setIsPoint(true);
                } else {
                    productInfoVo.getProduct().setIsPoint(false);
                }
            }
            long aend = System.currentTimeMillis();
            LogCvt.info("H5用户查看商品详情查询mongo商品活动信息耗时:"+(aend-astart));
            //设置对应的法人行社机构信息
            productInfoVo.setOrgCodes(pd.getOrgCodes());
        }
        return pd;
    }


    @Override
    public boolean isProductExist(ProductExistVoReq productExistVoReq)
            throws TException {
        LogCvt.info("是否已经存在Product,productExistVoReq:"+productExistVoReq);
        Product product = new Product();
        product.setClientId(productExistVoReq.getClientId());
        product.setMerchantId(productExistVoReq.getMerchantId());
        product.setProductId(productExistVoReq.getProductId());
        product.setName(productExistVoReq.getName());
        product.setType(productExistVoReq.getType());
        //已经删除的不查询出来
        product.setIsMarketable(ProductStatus.isDeleted.getCode());
        Boolean result = productLogic.isProductExist(product);
        return null == result ? false : result;
    }


    @Override
    public ProductBriefPageVo findMerchantProductsByPage(
            ProductFilterVoReq productFilterVoReq, PageVo pageVo)
            throws TException {
        long startTime = System.currentTimeMillis();
        
        LogCvt.debug("查询商品管理里面的商品列表参数,productFilterVoReq:"+JSON.toJSONString(productFilterVoReq)+",pageVo:"+JSON.toJSONString(pageVo));
        ProductBriefPageVo productPageVo = new ProductBriefPageVo();
        try{
            ProductFilter product = new ProductFilter();
            ProductFilterStr productFilterVo = null;
            Page<ProductListInfo> page = new Page<ProductListInfo>();
            ProductBeanUtil.copyProperties(page, pageVo);
            String filter = productFilterVoReq.getFilter();
            if(filter!=null){
                try{
                    String orderField = null;
                    if(filter.indexOf("orderField")!=-1){
                        int ofIndex = filter.indexOf("orderField");
                        int k = filter.indexOf("}");
                        if(k<(filter.length()-1)){
                            orderField = filter.substring(ofIndex, k+"}".length());
                            orderField = orderField.substring(orderField.indexOf("{"));
                            filter=filter.substring(0,filter.indexOf(orderField)) + filter.substring(filter.indexOf(orderField)+orderField.length());
//                            filter=filter.replaceAll(":,", ":\"\",");
                        }
                    }
                    if(filter!=null && !"".equals(filter)){
                        productFilterVo = JSonUtil.toObject(filter, ProductFilterStr.class);
                    }
                    if(productFilterVo==null){
                        productFilterVo = new ProductFilterStr();
                    }
                    productFilterVo.setOrderField(orderField);
                } catch (Exception e) {
                    LogCvt.error("商品用户分页查询list 解释filter失败，原因:" + e.getMessage(),e); 
                }
            }
            if(productFilterVo!=null){
                //1正在预售,2下期预售,3往期预售
                if("1".equals(productFilterVo.getPresellNum())){
                    productFilterVo.setStartTimeEnd(new Date().getTime());
                    productFilterVo.setEndTimeStart(new Date().getTime());
                } else if("2".equals(productFilterVo.getPresellNum())){
                    productFilterVo.setStartTimeStart(new Date().getTime());
                } else if("3".equals(productFilterVo.getPresellNum())){
                    productFilterVo.setEndTimeEnd(new Date().getTime());
                }
                ProductBeanUtil.copyPropertiesScale(product, productFilterVo, GoodsConstants.DOUBLE_INTGER_CHANGE);
                LinkedHashMap<String, String> sortOrderFileds = GoodsSortConstants.getSortOrderFileds(product.getOrderField(), GoodsSortConstants.sqlOrderFiledsMap);
                if(sortOrderFileds==null || sortOrderFileds.size()==0){
                    if(product.getType()!=null && !"".equals(product.getType())){
                        sortOrderFileds = GoodsSortConstants.getSortOrderFileds(GoodsSortConstants.sqlSortOrdersMap.get(product.getType()), GoodsSortConstants.sqlOrderFiledsMap);
                    } else {
                        sortOrderFileds = GoodsSortConstants.getSortOrderFileds(GoodsSortConstants.sqlSortOrdersMap.get("0"), GoodsSortConstants.sqlOrderFiledsMap);
                    }
                }
                product.setOrderFileds(sortOrderFileds);// 设置排序字段
                if(product!=null && product.getIsHot()!=null && "1".equals(product.getIsHot())){
                    product.setSellCountStart(Integer.valueOf(GoodsConstants.HOT_SELLl_COUNT));
                }
            }
            //已经删除的不查询出来
            product.setFilterStatuts(ProductStatus.isDeleted.getCode());
            if(product.getIsMarketable()!=null && "-1".equals(product.getIsMarketable())){
                product.setIsMarketable(null);
            }
            if(product.getDeliveryOption()!=null && "-1".equals(product.getDeliveryOption())){
                product.setDeliveryOption(null);
            }
            if(product.getType()!=null && "-1".equals(product.getType())){
                product.setType(null);
            }
            if(product.getIsBest()!=null && "-1".equals(product.getIsBest())){
                product.setIsBest(null);
            }
            if(product.getIsLimit()!=null && "-1".equals(product.getIsLimit())){
                product.setIsLimit(null);
            }
            if(product.getAuditState()!=null && "-1".equals(product.getAuditState())){
                product.setAuditState(null);
            }
            if(product.getAuditStage()!=null && "-1".equals(product.getAuditStage())){
                product.setAuditStage(null);
            }
            if(product.getClusterState()!=null && "-1".equals(product.getClusterState())){
                product.setClusterState(null);
            }
            if(product.getClusterType()!=null && "-1".equals(product.getClusterType())){
                product.setClusterType(null);
            }
            product.setClientId(productFilterVoReq.getClientId());
            if(productFilterVoReq.getMerchantId()!=null){
                product.setMerchantId(productFilterVoReq.getMerchantId());
            }
            if(product.getTypes()!=null && !"".equals(product.getTypes())){
                String[] typesArray = product.getTypes().split(",");
                List<String> types = new ArrayList<String>();
                if(typesArray!=null && typesArray.length>0){
                    for(String type : typesArray){
                        if(type!=null && !"".equals(type)){
                            types.add(type);
                        }
                    }
                }
                if(types.size()>0){
                    product.setTypeList(types);
                }
            }
            if(product.getCategoryId()!=null && product.getCategoryId()>0){
                CommonLogic common = new CommonLogicImpl();
                ProductCategory pc = common.findCategoryById(product.getClientId(), product.getCategoryId());
                if(Checker.isNotEmpty(pc)){
                    product.setCategoryTreePath(pc.getTreePath());
                }
            }
            List<ProductListInfo> productList = productLogic.findProductListByPage(page,product);
            List<ProductBriefInfoVo> productBriefInfoVoList = new ArrayList<ProductBriefInfoVo>();
            if (!CollectionUtils.isEmpty(productList)) {
                ProductBriefInfoVo vo = null;
                CommonLogic commonLogic = new CommonLogicImpl();
                Map<String, String> hash = null;
                String[] pcs = null;
                String categoryId = null;
                ProductCategory productCategory = null;
                Org org = null;
                for (ProductListInfo po : productList) {
                    try{
                        vo = new ProductBriefInfoVo();
                        //根据clientId+orgCode查询orgName机构名称
                        if(po.getOrgCode()!=null && !"".equals(po.getOrgCode().trim())){
                            org = commonLogic.queryByOrgCode(po.getClientId(), po.getOrgCode());
                            if(org!=null){
                                po.setOrgName(org.getOrgName());
                            }
                        }
                        
                        //团购商品以及预售商品需要查商品缓存信息
                        if(ProductType.group.getCode().equals(po.getType())){
                            hash = commonLogic.getProductRedis(po.getClientId(), po.getMerchantId(), po.getProductId());
                            if(hash!=null){
                                if(Checker.isNotEmpty(hash.get("expire_start_time"))){//团购券有效起始日
                                    po.setExpireStartTime(new Date(Long.valueOf(hash.get("expire_start_time"))));
                                }
                                if(Checker.isNotEmpty(hash.get("expire_end_time"))){//团购券有效结束日
                                    po.setExpireEndTime(new Date(Long.valueOf(hash.get("expire_end_time"))));
                                }
                            }
                        } else if(ProductType.presell.getCode().equals(po.getType())){
                            hash = commonLogic.getProductRedis(po.getClientId(), po.getMerchantId(), po.getProductId());
                            if(hash!=null){
                                if(Checker.isNotEmpty(hash.get("expire_start_time"))){//预售券有效起始日
                                    po.setExpireStartTime(new Date(Long.valueOf(hash.get("expire_start_time"))));
                                }
                                if(Checker.isNotEmpty(hash.get("expire_end_time"))){//预售券有效结束日
                                    po.setExpireEndTime(new Date(Long.valueOf(hash.get("expire_end_time"))));
                                }
                                if(Checker.isNotEmpty(hash.get("delivery_start_time"))){//提货-开始
                                    po.setDeliveryStartTime(new Date(Long.valueOf(hash.get("delivery_start_time"))));
                                }
                                if(Checker.isNotEmpty(hash.get("delivery_end_time"))){//提货-结束
                                    po.setDeliveryEndTime(new Date(Long.valueOf(hash.get("delivery_end_time"))));
                                }
                            }
                        }
                        if(Checker.isNotEmpty(po.getCategoryTreePath())){//商品分类名称
                            pcs = po.getCategoryTreePath().split(" ");
                            if(pcs!=null && pcs.length>0){
                                categoryId = pcs[pcs.length-1];
                            }
                            if(Checker.isNotEmpty(categoryId)){
                                productCategory = commonLogic.findCategoryById(po.getClientId(), Long.valueOf(categoryId));
                                if(Checker.isNotEmpty(productCategory)){
                                    po.setCategoryName(productCategory.getName());
                                }
                            }
                        }
                        //po转vo属性
                        ProductBeanUtil.copyPropertiesScale(vo, po, GoodsConstants.DOUBLE_INTGER_CHANGE);
                        vo.setAuditStateName(ProductTempAuditState.getType(vo.getAuditState()).getDescribe());
                        //添加到list中进行返回
                        productBriefInfoVoList.add(vo);
                    } catch (Exception e) {
                        LogCvt.error("商品管理分页查询list Product po转换vo失败，原因:" + e.getMessage(),e); 
                    }
                    
                }
            }
            productPageVo.setProductBriefInfoVoList(productBriefInfoVoList);
            pageVo.setPageCount(page.getPageCount());
            pageVo.setTotalCount(page.getTotalCount());
            if(pageVo.getPageCount()>pageVo.getPageNumber()){
                pageVo.setHasNext(true);
            } else {
                pageVo.setHasNext(false);
            }
            productPageVo.setPage(pageVo);
        }catch (Exception e) {
            LogCvt.error("查询商品管理里面的商品列表:Product by page失败，原因:" + e.getMessage(),e); 
        }
        long endTime = System.currentTimeMillis();
        LogCvt.debug("-------findMerchantProductsByPage:耗时"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);
        return productPageVo;
    }
    

    @SuppressWarnings("unchecked")
	@Override
    public ProductBriefPageVoRes queryProducts(
            ProductFilterVoReq productFilterVoReq, PageVo pageVo)
            throws TException {
        long start = System.currentTimeMillis();
        String monitorFlag = "";
        LogCvt.debug("H5查询商品列表,time:"+DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT5, new Date())+"参数ProductFilterVoReq:"+JSON.toJSONString(productFilterVoReq)+"pageVo:"+JSON.toJSONString(pageVo));
        
        ProductFilter product = new ProductFilter();
        ProductFilterStr productFilterVo = null;
        
        Page<ProductViewInfo> page = new Page<ProductViewInfo>();
        
        ProductBriefPageVoRes productPageVo = new ProductBriefPageVoRes();
        try{
            ProductBeanUtil.copyProperties(page, pageVo);
            String filter = productFilterVoReq.getFilter();
            if(filter!=null){
                try{
                    String orderField = null;
                    if(filter.indexOf("orderField")!=-1){
                        int ofIndex = filter.indexOf("orderField");
                        int k = filter.indexOf("}");
                        if(k<(filter.length()-1)){
                            orderField = filter.substring(ofIndex, k+"}".length());
                            orderField = orderField.substring(orderField.indexOf("{"));
                            filter=filter.substring(0,filter.indexOf(orderField)) + filter.substring(filter.indexOf(orderField)+orderField.length());
//                            filter=filter.replaceAll(":,", ":\"\",");
                        }
                    }
                    if(filter!=null && !"".equals(filter)){
                        productFilterVo = JSonUtil.toObject(filter, ProductFilterStr.class);
                    }
                    if(productFilterVo==null){
                        productFilterVo = new ProductFilterStr();
                    }
                    productFilterVo.setOrderField(orderField);
                    ProductBeanUtil.copyPropertiesScale(product, productFilterVo, GoodsConstants.DOUBLE_INTGER_CHANGE);
                    
                    LinkedHashMap<String, String> sortOrderFileds = GoodsSortConstants.getSortOrderFileds(product.getOrderField(), GoodsSortConstants.mongoOrderFiledsMap);
                    if((sortOrderFileds==null || sortOrderFileds.size()==0) && product.getType()!=null && !"".equals(product.getType())){
                        sortOrderFileds = GoodsSortConstants.getSortOrderFileds(GoodsSortConstants.mongoSortOrdersMap.get(product.getType()), GoodsSortConstants.mongoOrderFiledsMap);
                    }
                    product.setOrderFileds(sortOrderFileds);
                } catch (Exception e) {
                    LogCvt.error("商品用户分页查询list 解释filter失败，原因:" + e.getMessage(),e); 
                }
            }
            //已经上架的查询出来
            product.setIsMarketable(ProductStatus.onShelf.getCode());
            product.setClientId(productFilterVoReq.getClientId());
            if(productFilterVoReq.getMerchantId()!=null){
                product.setMerchantId(productFilterVoReq.getMerchantId());
            }
            if(product.getDeliveryOption()!=null && "-1".equals(product.getDeliveryOption())){
                product.setDeliveryOption(null);
            }
            if(product.getType()!=null && "-1".equals(product.getType())){
                product.setType(null);
            }
            if(product.getIsBest()!=null && "-1".equals(product.getIsBest())){
                product.setIsBest(null);
            }
            if(product.getIsLimit()!=null && "-1".equals(product.getIsLimit())){
                product.setIsLimit(null);
            }
            if(product.getIsHot()!=null && "1".equals(product.getIsHot())){
                product.setIsHot("1");
            } else {
                product.setIsHot(null);
            }
            boolean h5 = true;
            if(Checker.isEmpty(product.getType())) {
            	
            }
            if(!ProductType.group.getCode().equals(product.getType()) 
                    && !ProductType.presell.getCode().equals(product.getType())
                    && !ProductType.special.getCode().equals(product.getType())
                    && !ProductType.onlinePoint.getCode().equals(product.getType())
                    && !ProductType.dotGift.getCode().equals(product.getType())){//没有商品类型的查询 不考虑redis缓存
                h5 = false;
            } else if(ProductType.dotGift.getCode().equals(product.getType()) 
                    && (
                            (product.getAreaId()!=null && product.getAreaId()>0) 
                            || (product.getCityId()!=null && product.getCityId()>0)
                            || (Checker.isNotEmpty(product.getAreaCode()))
                     )){//根据区域查询线下积分的商品查询 不考虑redis缓存
                h5 = false;
            } else {
                if(product.getMerchantId()!=null && !"".equals(product.getMerchantId())){
                    h5 = false;
                }
                if(product.getFullName()!=null && !"".equals(product.getFullName())){
                    h5 = false;
                }
                if(product.getDeliveryOption()!=null && !"".equals(product.getDeliveryOption())){
                    h5 = false;
                }
                if(product.getIsBest()!=null && !"".equals(product.getIsBest())){
                    h5 = false;
                }
                if(product.getIsLimit()!=null && !"".equals(product.getIsLimit())){
                    h5 = false;
                }
                if(product.getIsHot()!=null){
                    h5 = false;
                } else {
                    if(product.getSellCountStart()!=null && product.getSellCountStart()>0 && product.getSellCountEnd()!=null && product.getSellCountEnd()>0){
                        h5 = false;
                    } else if(product.getSellCountStart()!=null && product.getSellCountStart()>0){
                        h5 = false;
                    } else if(product.getSellCountEnd()!=null && product.getSellCountEnd()>0){
                        h5 = false;
                    }
                }
                if(product.getMarketPriceStart()!=null && product.getMarketPriceStart()>0 && product.getMarketPriceEnd()!=null && product.getMarketPriceEnd()>0){
                    h5 = false;
                } else if(product.getMarketPriceStart()!=null && product.getMarketPriceStart()>0){
                    h5 = false;
                } else if(product.getMarketPriceEnd()!=null && product.getMarketPriceEnd()>0){
                    h5 = false;
                }
                if(product.getPriceStart()!=null && product.getPriceStart()>0 && product.getPriceEnd()!=null && product.getPriceEnd()>0){
                    h5 = false;
                } else if(product.getPriceStart()!=null && product.getPriceStart()>0){
                    h5 = false;
                } else if(product.getPriceEnd()!=null && product.getPriceEnd()>0){
                    h5 = false;
                }
                if(product.getMerchantName()!=null && !"".equals(product.getMerchantName())){
                    h5 = false;
                }
                if(product.getOutletId()!=null && !"".equals(product.getOutletId())){//根据门店查询商品也不考虑redis缓存
                    h5 = false;
                }
            }
            //返回变量
            List<ProductBriefVoRes> productBriefVoList = new ArrayList<ProductBriefVoRes>();
            MongoPage mongoPage = null;
            
            List<ProductDetail> productDetails = null;
            if(h5){//考虑redis缓存
                monitorFlag = "goodssearch";//个人H5商品查询监控:个人H5商品每秒查询次数和个人H5商品查询耗时
                mongoPage = productLogic.findProducts(page,product);
                productDetails = (List<ProductDetail>)mongoPage.getItems();
                if(productDetails!=null && productDetails.size()>0 && productDetails.size()==pageVo.getPageSize()){
                	//原来查询条件 再查询下一页一条数据
                	page.setPageNumber(page.getPageNumber()+1);
                	MongoPage nextMongoPage = productLogic.findProducts(page,product);
                	if(nextMongoPage.getItems()!=null && nextMongoPage.getItems().size()>0){
                		pageVo.setHasNext(true);
                	} else {
                    	pageVo.setHasNext(false);
                    }
                } else {
                	pageVo.setHasNext(false);
                }
            } else {//不考虑redis缓存 每次都是直接查询mongo
                if((product.getAreaId()!=null && product.getAreaId()>0) 
                        || (product.getCityId()!=null && product.getCityId()>0)
                        || (Checker.isNotEmpty(product.getAreaCode()))){
                    monitorFlag = "exchangesearch";//个人H5线下积分兑换商品查监控询:个人H5线下积分兑换商品每秒查询次数和个人H5线下积分兑换商品查询耗时
                }
                mongoPage = productLogic.findProductsByPage(page,product);
                if(mongoPage.getPageCount()>mongoPage.getPageNumber()){
                    pageVo.setHasNext(true);
                } else {
                    pageVo.setHasNext(false);
                }
                productDetails = (List<ProductDetail>)mongoPage.getItems();
            }
            
            if(productDetails!=null && productDetails.size()>0){
                ProductBriefVoRes vo = null;
                Long now = new Date().getTime();
                RedisManager redis = new RedisManager();
                for(ProductDetail pd : productDetails){
                    vo = new ProductBriefVoRes();
                    vo.setClientId(pd.getClientId());
                    vo.setMerchantId(pd.getMerchantId());
                    vo.setProductId(pd.getId());
                    vo.setName(pd.getName());
                    vo.setType(pd.getProductType());
                    //团购 特惠商品推荐首页查询
                    vo.setPrice(Arith.div(pd.getPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));
                    vo.setSellCount(pd.getSellCount());
                    if(pd.getMarketPrice()!=null){
                    	vo.setMarketPrice(Arith.div(pd.getMarketPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));
                    }
                    vo.setStartTime(pd.getStartTime().getTime());
                    vo.setEndTime(pd.getEndTime().getTime());
                    if(vo.getStartTime()>(new Date()).getTime()){
                       vo.setIsStart(false); 
                    } else {
                        vo.setIsStart(true); 
                    }
                    if(vo.getEndTime()>(new Date()).getTime()){
                        vo.setIsEnd(false); 
                    } else {
                        vo.setIsEnd(true); 
                    }
                    vo.setIsSeckill(pd.getIsSeckill());
                    vo.setFullName(pd.getFullName());
                    vo.setBriefIntroduction(pd.getBriefIntroduction());
                    if(pd.getStore()!=null){
                        vo.setStore(pd.getStore());
                    }
                    if(pd.getImageInfo()!=null && pd.getImageInfo().size()>0){
                        ProductImage imagepo = pd.getImageInfo().get(0);
                        vo.setSmallImgUrl(imagepo.getThumbnail());
                    }
                    vo.setServerTime(now);
                    
                    //秒杀商品
                    if(!SeckillFlagEnum.notSeckill.getCode().equals(vo.getIsSeckill())){
                        //获取秒杀商品秒杀开始时间和秒杀结束时间
                        Map<String, String> hash = redis.getMap(RedisKeyUtil.cbbank_seckill_product_client_id_product_id(vo.getClientId(), vo.getProductId()));
                        if(hash!=null){
                            if(Checker.isNotEmpty(hash.get("start_time"))){//秒杀开始时间
                                vo.setSecStartTime(Long.valueOf(hash.get("start_time")));
                            }
                            if(Checker.isNotEmpty(hash.get("end_time"))){//秒杀结束时间
                                vo.setSecEndTime(Long.valueOf(hash.get("end_time")));
                            }
                        }
                        //获取秒杀库存库存量
                        String secSotreStr = redis.getString(RedisKeyUtil.cbbank_seckill_product_store_client_id_product_id(vo.getClientId(), vo.getProductId()));
                        if(NumberUtils.isNumber(secSotreStr)){
                            vo.setSecStore(Integer.parseInt(secSotreStr));
                        }
                    }
                    //vip价格 是否参与VIP规则
                    if(pd.getVipPrice()!=null && pd.getVipPrice()>0){
                        vo.setVipPrice(Arith.div(pd.getVipPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));
                        vo.setIsVip(true);
                    } else {
                        vo.setIsVip(false);
                    }
                    vo.setIsPoint(false);
                    List<ProductActivitiesInfo> activitiesPos = pd.getActivitiesInfo();
                    if(activitiesPos!=null && activitiesPos.size()>0){
                        //活动表缓存信息
                        ProductActivitiesInfo activitiesPo = activitiesPos.get(0);
                        String ackey = RedisKeyUtil.cbbank_activities_client_id_activities_id(product.getClientId(),activitiesPo.getActivitiesId()) ;
                        Map<String,String> acRedisMap = redis.getMap(ackey);
                        if(acRedisMap!=null && acRedisMap.size()>0){
                            if(String.valueOf(ActivitiesStatus.yes.getCode()).equals(acRedisMap.get("status"))){
                                vo.setIsPoint(true);
                            }
                        }
                    }
                    
                    productBriefVoList.add(vo);
                }
            }
            productPageVo.setProductBriefVoList(productBriefVoList);
            pageVo.setPageCount(mongoPage.getPageCount());
            pageVo.setTotalCount(mongoPage.getTotalCount());
            productPageVo.setPage(pageVo);
            
            long end = System.currentTimeMillis();
            if(monitorFlag.equals("goodssearch")){//个人H5商品查询监控
                //个人H5商品每秒查询次数
                monitorService.send(Constants.MONITOR_BUSINAME, GoodsConstants.MONITOR_GOODS_GOODSSEARCH_SEARCHNUMBER_ATTRID, "1", GoodsConstants.MONITOR_GOODS_GOODSSEARCH_SEARCHNUMBER_TYPE, "");
                //个人H5商品查询耗时
                monitorService.send(Constants.MONITOR_BUSINAME, GoodsConstants.MONITOR_GOODS_GOODSSEARCH_SPEND_TIME_ATTRID, String.valueOf(end-start), GoodsConstants.MONITOR_GOODS_GOODSSEARCH_SPEND_TIME_TYPE, "");
            } else if(monitorFlag.equals("exchangesearch")){//个人H5线下积分兑换商品查监控询
                //个人H5线下积分兑换商品每秒查询次数
                monitorService.send(Constants.MONITOR_BUSINAME, GoodsConstants.MONITOR_GOODS_EXCHANGESEARCH_SEARCHNUMBER_ATTRID, "1", GoodsConstants.MONITOR_GOODS_EXCHANGESEARCH_SEARCHNUMBER_TYPE, "");
                //个人H5线下积分兑换商品查询耗时
                monitorService.send(Constants.MONITOR_BUSINAME, GoodsConstants.MONITOR_GOODS_EXCHANGESEARCH_SPEND_TIME_ATTRID, String.valueOf(end-start), GoodsConstants.MONITOR_GOODS_EXCHANGESEARCH_SPEND_TIME_TYPE, "");
            }
        } catch (Exception e) {
            LogCvt.error("商品用户分页查询list Product失败，原因:" + e.getMessage(),e); 
        }
        return productPageVo;
    }


    /**
     * H5用户查看商品详情
     */
    @Override
    public ProductInfoVo queryProductDetail(ProductOperateVoReq productVoReq)
            throws TException {
        long start = System.currentTimeMillis();
        LogCvt.info("H5用户查看商品详情参数productVoReq:"+JSON.toJSONString(productVoReq));
        Product product = new Product();
        ProductBeanUtil.copyProperties(product, productVoReq);
        
        long pstart = System.currentTimeMillis();
        ProductViewInfo productViewInfo = productLogic.getProduct(product);
        long pend = System.currentTimeMillis();
        LogCvt.info("H5用户查看商品详情查询mysql中商品基础信息总耗时:"+(pend-pstart));
        
        ProductInfoVo productInfoVo = new ProductInfoVo();
        if (productViewInfo!=null) {
            ProductVo vo = new ProductVo();
            ProductBeanUtil.copyPropertiesScale(vo, productViewInfo, GoodsConstants.DOUBLE_INTGER_CHANGE);
            
            //设置对应的机构名称
            if(vo.getClientId()!=null && vo.getOrgCode()!=null && !"".equals(vo.getClientId()) && !"".equals(vo.getOrgCode())){
                CommonLogic commonLogic = new CommonLogicImpl();
                Org org = commonLogic.queryByOrgCode(vo.getClientId(), vo.getOrgCode());
                if(org!=null){
                    vo.setOrgName(org.getOrgName());
                }
            }
            
            //返回服务时间
            vo.setServerTime((new Date()).getTime());
            
            RedisManager redis = new RedisManager();
            //秒杀商品
            if(!SeckillFlagEnum.notSeckill.getCode().equals(vo.getIsSeckill())){
                //获取秒杀商品秒杀开始时间和秒杀结束时间
                Map<String, String> hash = redis.getMap(RedisKeyUtil.cbbank_seckill_product_client_id_product_id(vo.getClientId(), vo.getProductId()));
                if(hash!=null){
                    if(Checker.isNotEmpty(hash.get("start_time"))){//秒杀开始时间
                        vo.setSecStartTime(Long.valueOf(hash.get("start_time")));
                    }
                    if(Checker.isNotEmpty(hash.get("end_time"))){//秒杀结束时间
                        vo.setSecEndTime(Long.valueOf(hash.get("end_time")));
                    }
                }
                //获取秒杀库存库存量
                String secSotreStr = redis.getString(RedisKeyUtil.cbbank_seckill_product_store_client_id_product_id(vo.getClientId(), vo.getProductId()));
                if(NumberUtils.isNumber(secSotreStr)){
                    vo.setSecStore(Integer.parseInt(secSotreStr));
                }
            }
            
            //获取库存量，redis中的库存量比mysql的更准确
            String sotreStr = redis.getString(RedisKeyUtil.cbbank_product_store_client_id_merchant_id_product_id(vo.getClientId(), vo.getMerchantId(), vo.getProductId()));
            if(NumberUtils.isNumber(sotreStr)){
                vo.setStore(Integer.parseInt(sotreStr));
            }
            productInfoVo.setProduct(vo);
            //获取mongo中的商品多表关系的信息查出来设置到返回的商品中
            long pdstart = System.currentTimeMillis();
            ProductDetail pd = getProductAttachProperties(productInfoVo,product,"H5");
            long pdend = System.currentTimeMillis();
            LogCvt.info("H5用户查看商品详情查询mongo中商品信息总耗时:"+(pdend-pdstart));
            //销售量用mongo中的字段值
            if(pd!=null && pd.getSellCount()!=null){
                productInfoVo.getProduct().setSellCount(pd.getSellCount());
            }
        }
        long end = System.currentTimeMillis();
        LogCvt.info("H5用户查看商品详情总耗时:"+(end-start));
        return productInfoVo;
    }


    @Override
    public ResultVo updateProductStatus(OriginVo originVo, ProductStatusVoReq productStatusVoReq)
            throws TException {
        LogCvt.debug("商品上下架参数originVo:"+JSON.toJSONString(originVo)+",productStatusVoReq:"+JSON.toJSONString(productStatusVoReq));
        
        //添加操作日志记录
    	originVo.setDescription("修改商品上下架状态");
		LogUtils.addLog(originVo);
		
        ResultVo resultVo = new ResultVo();
        if(productStatusVoReq.getClientId()!=null && !"".equals(productStatusVoReq.getClientId()) 
                && productStatusVoReq.getProductId()!=null && !"".equals(productStatusVoReq.getProductId())
                && productStatusVoReq.getStatus()!=null && !"".equals(productStatusVoReq.getStatus())){
            Product product = new Product();
            product.setClientId(productStatusVoReq.getClientId());
            product.setMerchantId(productStatusVoReq.getMerchantId());
            product.setProductId(productStatusVoReq.getProductId());
            product.setIsMarketable(productStatusVoReq.getStatus());
            Result result = productLogic.updateProductStatus(product);
            ProductBeanUtil.copyProperties(resultVo, result);
        } else {
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("clinetId不能为空，productId不能为空,上下架状态不能为空");
        }
        return resultVo;
    }
    
    
    private ProductInfo changFromProductInfoVo(ProductInfoVo productInfoVo){
        ProductInfo productInfo = null;
        ProductVo productVo = productInfoVo.getProduct();
        if(productVo!=null && productVo.getType()!=null){
            //讲productvo转成productInfo
            productInfo = new ProductInfo();
            ProductBeanUtil.copyProperties(productInfo, productInfoVo);//复制平台信息
            Product product = new Product();
            //复制基础信息 double类型的价格乘以1000转换成int型存储在数据里
            ProductBeanUtil.copyPropertiesScale(product, productVo, GoodsConstants.DOUBLE_INTGER_CHANGE);
            productInfo.setProduct(product);
            
            if(productVo.getType().equals(ProductType.group.getCode())){//团购商品
                ProductGroup productGroup = new ProductGroup();
                ProductBeanUtil.copyProperties(productGroup, productVo);
                productInfo.setProductGroup(productGroup);
            } else if(productVo.getType().equals(ProductType.presell.getCode())){//预售商品
                ProductPresell productPresell = new ProductPresell();
                ProductBeanUtil.copyProperties(productPresell, productVo);
                productInfo.setProductPresell(productPresell);
            }
            if(productInfoVo.getProductCategory()!=null){//商品分类
                ProductCategory productCategory = new ProductCategory();
                ProductBeanUtil.copyProperties(productCategory, productInfoVo.getProductCategory());
                productInfo.setProductCategory(productCategory);
            }
            if(productVo.isLimit && productInfoVo.getBuyLimit()!=null){//普通限购信息
                if(productInfoVo.getBuyLimit().getMax()>0 
                        || productInfoVo.getBuyLimit().getMaxVip()>0 
                        || productInfoVo.getBuyLimit().getMaxCard()>0){//vip限购数量 普通限购数量 贴膜卡限购数量 3个 其中有一个有值代表限购
                    ProductBuyLimit buyLimit = new ProductBuyLimit();
                    ProductBeanUtil.copyProperties(buyLimit, productInfoVo.getBuyLimit());
                    productInfo.setBuyLimit(buyLimit);
                } else {
                    productVo.setIsLimit(false);
                    productInfo.setBuyLimit(null);
                }
            }
            if(productInfoVo.getImage()!=null && productInfoVo.getImage().size()>0){//图片信息
                List<ProductImageVo> images = productInfoVo.getImage();
                List<ProductImage> productImages = new ArrayList<ProductImage>();
                ProductImage productIamge = null;
                for(ProductImageVo image : images){
                    productIamge = new ProductImage();
                    ProductBeanUtil.copyProperties(productIamge, image);
                    productImages.add(productIamge);
                }
                productInfo.setProductImages(productImages);
            }
            if(productInfoVo.getOutlet()!=null && productInfoVo.getOutlet().size()>0){//门店信息
                List<ProductOutlet> productOutlets = new ArrayList<ProductOutlet>();
                
                List<ProductOutletVo> productOutletVos = productInfoVo.getOutlet();
                ProductOutlet productOutlet = null;
                for(ProductOutletVo productOutletVo : productOutletVos){
                    productOutlet = new ProductOutlet();
                    ProductBeanUtil.copyProperties(productOutlet, productOutletVo);
                    productOutlets.add(productOutlet);
                }
                productInfo.setProductOutlets(productOutlets);
            }
            if(productInfoVo.getActivities()!=null && productInfoVo.getActivities().size()>0){//活动信息
                List<ActivitiesInfo> productActivitePos = new ArrayList<ActivitiesInfo>();
                List<ProductActivitiesVo> productActiviteVos = productInfoVo.getActivities();
                ActivitiesInfo po = null;
                for(ProductActivitiesVo vo : productActiviteVos){
                    po = new ActivitiesInfo();
                    ProductBeanUtil.copyProperties(po, vo);
                    po.setId(vo.getActivitiesId());
                    productActivitePos.add(po);
                }
                productInfo.setActivities(productActivitePos);
            }
            productInfo.setOrgCodes(productInfoVo.getOrgCodes());//前端所选法人行社机构 机构人员新加商品
        }
        return productInfo;
    }
    

    @Override
    public AddOutletProductVoRes addOutletProduct(OriginVo originVo, OutletProductVo outletProductVo)
            throws TException {
        long start = System.currentTimeMillis();
        LogCvt.debug("新加面对面商品参数originVo:"+JSON.toJSONString(originVo)+",OutletProductVo"+JSON.toJSONString(outletProductVo));
        
        //添加操作日志记录
    	originVo.setDescription("添加面对面商品");
		LogUtils.addLog(originVo);
		
        AddOutletProductVoRes addOutletProductVoRes = new AddOutletProductVoRes();
        ResultVo resultVo = new ResultVo();
        if(outletProductVo.getClientId()==null || "".equals(outletProductVo.getClientId())){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("客户端id不能为空");
            addOutletProductVoRes.setResultVo(resultVo);
            return addOutletProductVoRes;
        }
        if(outletProductVo.getOutletId()==null || "".equals(outletProductVo.getOutletId())){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("面对面商品所属的门店id不能为空");
            addOutletProductVoRes.setResultVo(resultVo);
            return addOutletProductVoRes;
        }
        if(outletProductVo.getMerchantId()==null || "".equals(outletProductVo.getMerchantId())){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("面对面商品所属的商户id不能为空");
            addOutletProductVoRes.setResultVo(resultVo);
            return addOutletProductVoRes;
        }
        if(outletProductVo.getUserId()==null || "".equals(outletProductVo.getUserId())){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("面对面商品所属的商户用户id不能为空");
            addOutletProductVoRes.setResultVo(resultVo);
            return addOutletProductVoRes;
        }
        if(outletProductVo.getCost()<=0.0){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("面对面商品销售价必填");
            addOutletProductVoRes.setResultVo(resultVo);
            return addOutletProductVoRes;
        }
        
        if(Util.getDoubleDecimalNum(outletProductVo.getCost())>2){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("面对面商品销售价只能有2位有效小数");
            addOutletProductVoRes.setResultVo(resultVo);
            return addOutletProductVoRes;
        }
        
        OutletProduct outletProduct = new OutletProduct();
        ProductBeanUtil.copyPropertiesScale(outletProduct, outletProductVo, GoodsConstants.DOUBLE_INTGER_CHANGE);
        OutletProductQrCode po =productLogic.addOutletProduct(outletProduct);
        if(po!=null){
            OutletProductQrCodeVo qrCodeVo = new OutletProductQrCodeVo();
            ProductBeanUtil.copyProperties(qrCodeVo, po);
            
            resultVo.setResultCode(ResultCode.success.getCode());
            resultVo.setResultDesc("新增面对面商品成功");
            addOutletProductVoRes.setResultVo(resultVo);
            addOutletProductVoRes.setOutletProductQrCodeVo(qrCodeVo);
        } else {
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("新增面对面商品失败");
            addOutletProductVoRes.setResultVo(resultVo);
        }
        long end = System.currentTimeMillis();
        //面对面商品创建接口每次耗时监控
        monitorService.send(Constants.MONITOR_BUSINAME, GoodsConstants.MONITOR_GOODS_F2FGOOD_ADD_SPEND_TIME_ATTRID, String.valueOf(end-start), GoodsConstants.MONITOR_GOODS_F2FGOOD_ADD_SPEND_TIME_TYPE, "");
        return addOutletProductVoRes;
    }
    
    
    /**
     * 根据二维码查看面对面商品详情
     * @param outletProductVoReq
     * @return OutletProductVoRes
     */
    @Override
    public OutletProductVoRes getOutletProduct(
            OutletProductVoReq outletProductVoReq) throws TException {
        LogCvt.debug("根据二维码查看面对面商品详情:"+ JSON.toJSONString(outletProductVoReq));
        OutletProductVoRes outletProductVoRes = new OutletProductVoRes();
        if(outletProductVoReq.getClientId()!=null && !"".equals(outletProductVoReq.getClientId()) 
                && outletProductVoReq.getQrCode()!=null && !"".equals(outletProductVoReq.getQrCode())){
            OutletProductInfo req = new OutletProductInfo();
            ProductBeanUtil.copyPropertiesScale(req, outletProductVoReq, GoodsConstants.DOUBLE_INTGER_CHANGE);
            OutletProductInfo outletProductInfo = productLogic.getOutletProduct(req);
            if(outletProductInfo!=null){
                ProductBeanUtil.copyPropertiesScale(outletProductVoRes, outletProductInfo, GoodsConstants.DOUBLE_INTGER_CHANGE);
            }
        }
        return outletProductVoRes;
    }


    @Override
    public ResultVo deleteOutletProduct(OriginVo originVo, OutletProductVo outletProductVo)
            throws TException {
        LogCvt.info("删除面对面商品参数originVo:"+JSON.toJSONString(originVo)+",outletProductVo:"+JSON.toJSONString(outletProductVo));
        
        //添加操作日志记录
    	originVo.setDescription("删除面对面商品");
		LogUtils.addLog(originVo);
        
        ResultVo resultVo = new ResultVo();
        OutletProduct outletProduct = new OutletProduct();
        ProductBeanUtil.copyPropertiesScale(outletProduct, outletProductVo, GoodsConstants.DOUBLE_INTGER_CHANGE);
        outletProduct.setId(outletProductVo.getProductId());
        Boolean result = productLogic.deleteOutletProduct(outletProduct);
        if(result){
            resultVo.setResultCode(ResultCode.success.getCode());
            resultVo.setResultDesc("删除OutletProduct成功");
        } else {
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("删除OutletProduct失败");
        }
        return resultVo;
    }


    @Override
    public OutletProductPageVo getOutletProductListByPage(
            OutletProductVo outletProductVo,PageVo pageVo) throws TException {
        LogCvt.info("查询面对面商品列表参数outletProductVo:"+JSON.toJSONString(outletProductVo)+",pageVo:"+JSON.toJSONString(pageVo));
        OutletProductPageVo outletProductPageVo= new OutletProductPageVo();
        Page<OutletProduct> page = new Page<OutletProduct>();
        OutletProduct outletProduct = new OutletProduct();
        ProductBeanUtil.copyProperties(page, pageVo);
        ProductBeanUtil.copyPropertiesScale(outletProduct, outletProductVo, GoodsConstants.DOUBLE_INTGER_CHANGE);
        MongoPage mPage = productLogic.getOutletProductListByPage(page, outletProduct);
        @SuppressWarnings("unchecked")
		List<OutletProduct> outletProducts = (List<OutletProduct>)mPage.getItems();
        List<OutletProductVo> outletProductVos = new ArrayList<OutletProductVo>();
        if(outletProducts != null && outletProducts.size() > 0 ){
            OutletProductVo opVo = null;
            for(OutletProduct opPo : outletProducts ){
                opVo = new OutletProductVo();
                ProductBeanUtil.copyPropertiesScale(opVo, opPo, GoodsConstants.DOUBLE_INTGER_CHANGE);
                outletProductVos.add(opVo);
            }
        }else{
            LogCvt.info("查询OutletProduct无数据,查询条件为:"+outletProduct+" - "+JSON.toJSONString(page));
        }
        outletProductPageVo.setOutletProductVoList(outletProductVos);
        pageVo.setPageCount(mPage.getPageCount());
        pageVo.setTotalCount(mPage.getTotalCount());
        outletProductPageVo.setPage(pageVo);
        return outletProductPageVo;
    }
    

    @Override
    public ProductCommentPageVo getProductCommentList(
            ProductCommentFilterReq productCommentFilterReq, PageVo pageVo)
            throws TException {
    	  LogCvt.info("分页查询商品评价列表参数productCommentFilterReq:"+JSON.toJSONString(productCommentFilterReq)+",pageVo:"+JSON.toJSONString(pageVo));
          ProductCommentPageVo productCommentPageVo= new ProductCommentPageVo();
          Page<ProductComment> page = new Page<ProductComment>();
          ProductCommentFilter productCommentFilter = null;
          
          String filter = productCommentFilterReq.getFilter();
          try {
              ProductBeanUtil.copyProperties(page, pageVo);
              productCommentFilter = JSonUtil.toObject(filter, ProductCommentFilter.class);
          } catch (Exception e) {
              LogCvt.error("分页查询ProductCommentList失败，原因:" + e.getMessage(),e);
          }
          if(productCommentFilter==null){
              productCommentFilter = new ProductCommentFilter();
          }
          productCommentFilter.setClientId(productCommentFilterReq.getClientId());
          
          MongoPage mPage = productLogic.getProductCommentList(page, productCommentFilter);
                  
          @SuppressWarnings("unchecked")
		List<ProductCommentInfo> productComments = (List<ProductCommentInfo>)mPage.getItems();
          List<ProductCommentVo> productCommentVos = new ArrayList<ProductCommentVo>();
          if(productComments != null && productComments.size() > 0 ){
              ProductCommentVo vo = null;
              for(ProductCommentInfo po : productComments ){
                  vo = new ProductCommentVo();
                  ProductBeanUtil.copyProperties(vo, po);
                  if(po.getLorgCode()!=null && !"".equals(po.getLorgCode())){
                	  vo.setOrgCode(po.getLorgCode());
                  }else if(po.getTorgCode()!=null && !"".equals(po.getTorgCode())){
                	  vo.setOrgCode(po.getTorgCode());
                  }else if(po.getSorgCode()!=null && !"".equals(po.getSorgCode())){
                	  vo.setOrgCode(po.getSorgCode());
                  }else if(po.getForgCode()!=null && !"".equals(po.getForgCode())){
                	  vo.setOrgCode(po.getForgCode());
                  }
                  productCommentVos.add(vo);
              }
          }else{
              LogCvt.info("查询ProductCommentList无数据,查询条件为:"+productCommentFilterReq+" - "+JSON.toJSONString(page));
          }
          productCommentPageVo.setProductCommentVoList(productCommentVos);
          pageVo.setPageCount(mPage.getPageCount());
          pageVo.setTotalCount(mPage.getTotalCount());
          if(pageVo.getPageCount()>pageVo.getPageNumber()){
              pageVo.setHasNext(true);
          } else {
              pageVo.setHasNext(false);
          }
          productCommentPageVo.setPage(pageVo);
          return productCommentPageVo;
    }


    @Override
    public ProductCommentVo getProductCommentDetail(
            ProductCommentVo productCommentVo) throws TException {
        LogCvt.info("查询商品评论明细参数productCommentVo:"+JSON.toJSONString(productCommentVo));
        ProductComment productComment = new ProductComment();
        ProductBeanUtil.copyProperties(productComment, productCommentVo);
        ProductCommentVo pcVo = new ProductCommentVo();
        ProductComment pcPo = productLogic.getProductCommentDetail(productComment);
        if(pcPo!=null){
            ProductBeanUtil.copyProperties(pcVo, pcPo);
        }
        return pcVo;
    }


    @Override
    public ResultVo deleteProductComment(ProductCommentVo productCommentVo)
            throws TException {
        LogCvt.info("删除商品评论参数productCommentVo:"+JSON.toJSONString(productCommentVo));
        ProductComment productComment = new ProductComment();
        ProductBeanUtil.copyProperties(productComment, productCommentVo);
        ResultBean result = productLogic.deleteProductComment(productComment);
        ResultVo resultVo=new ResultVo();
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getCode())){
			LogCvt.error(result.getMsg());
			resultVo.setResultCode(result.getCode());
			resultVo.setResultDesc(result.getMsg());
		}else{
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc("删除商品评论成功");
		}
        return  resultVo;
    }


    @Override
    public ResultVo replayProductComment(ProductCommentVo productCommentVo)
            throws TException {
        LogCvt.info("回复评论参数productCommentVo:"+JSON.toJSONString(productCommentVo));
        ResultVo resultVo=new ResultVo();
        if(productCommentVo.getRecomment()==null || "".equals(productCommentVo.getRecomment())){
            resultVo.setResultCode(ResultCode.productcomment_param_empty.getCode());
            resultVo.setResultDesc("回复内容不能为空");
            return resultVo;
        } else {
    		try{
    			SenseWordsService.Iface swService = 
    				(SenseWordsService.Iface)ThriftClientProxyFactory.createIface(SenseWordsService.class.getName(), SenseWordsService.class.getSimpleName(), GoodsConstants.THRIFT_SUPPORT_HOST, GoodsConstants.THRIFT_SUPPORT_PORT);
    			ResultVo isSense = swService.isContaintSensitiveWord(productCommentVo.getRecomment());
    			
    			if( isSense.getResultCode().equals(ResultCode.failed.getCode())){
    				   LogCvt.info("敏感词:" +isSense.getResultDesc());
    	                resultVo.setResultCode(ResultCode.failed.getCode());
    	                resultVo.setResultDesc("您的评论包含敏感词，请修改后再提交！敏感词:"+isSense.getResultDesc());
    	                return resultVo;
    			}
    			
    		}catch(Exception e){
    			LogCvt.error("调用外围模块判断评论描述是否有敏感词 出现异常 "+e.getMessage(), e);
    		}
        }
        if(productCommentVo.getMerchantUserName()==null || "".equals(productCommentVo.getMerchantUserName())){
            resultVo.setResultCode(ResultCode.productcomment_param_empty.getCode());
            resultVo.setResultDesc("回复人姓名不能为空");
            return resultVo;
        }
        if(productCommentVo.getCommentId()==null || "".equals(productCommentVo.getCommentId())){
            resultVo.setResultCode(ResultCode.productcomment_param_empty.getCode());
            resultVo.setResultDesc("评论ID不能为空");
            return resultVo;
        }
        ProductComment productComment = new ProductComment();
        ProductBeanUtil.copyProperties(productComment, productCommentVo); 
        
        Result result = productLogic.replayProductComment(productComment);
        ProductBeanUtil.copyProperties(resultVo, result); 
        return resultVo;
    }


    @Override
    public ResultVo addProductComment(ProductCommentVo productCommentVo)
            throws TException {
        LogCvt.info("新增商品评论参数productCommentVo:"+JSON.toJSONString(productCommentVo));
        ProductComment productComment = new ProductComment();
        ProductBeanUtil.copyProperties(productComment, productCommentVo);
        ResultVo resultVo=new ResultVo();
        if(productComment.getOrderId()==null || "".equals(productComment.getOrderId())){
            resultVo.setResultCode(ResultCode.productcomment_param_empty.getCode());
            resultVo.setResultDesc("订单号不能为空");
            return resultVo;
        }
        if(productComment.getProductId()==null || "".equals(productComment.getProductId())){
            resultVo.setResultCode(ResultCode.productcomment_param_empty.getCode());
            resultVo.setResultDesc("商品id不能为空");
            return resultVo;
        }
        if(productComment.getMemberCode()==null || "".equals(productComment.getMemberCode())){
            resultVo.setResultCode(ResultCode.productcomment_param_empty.getCode());
            resultVo.setResultDesc("用户编码不能为空");
            return resultVo;
        }
        if(productComment.getMemberName()==null || "".equals(productComment.getMemberName())){
            resultVo.setResultCode(ResultCode.productcomment_param_empty.getCode());
            resultVo.setResultDesc("评论人不能为空");
            return resultVo;
        }
        if(productComment.getStarLevel()==null || "".equals(productComment.getStarLevel())){
            resultVo.setResultCode(ResultCode.productcomment_param_empty.getCode());
            resultVo.setResultDesc("星级评分不能为空");
            return resultVo;
        }
        if(Checker.isNotEmpty(productComment.getCommentDescription())){
    		try{
    			SenseWordsService.Iface swService = 
    				(SenseWordsService.Iface)ThriftClientProxyFactory.createIface(SenseWordsService.class.getName(), SenseWordsService.class.getSimpleName(), GoodsConstants.THRIFT_SUPPORT_HOST, GoodsConstants.THRIFT_SUPPORT_PORT);
    			ResultVo isSense = swService.isContaintSensitiveWord(productComment.getCommentDescription());
    			
    			if( isSense.getResultCode().equals(ResultCode.failed.getCode())){
    				   LogCvt.info("敏感词:" +isSense.getResultDesc());
    	                resultVo.setResultCode(ResultCode.failed.getCode());
    	                resultVo.setResultDesc("您的评论包含敏感词，请修改后再提交！,敏感词:"+isSense.getResultDesc());
    	                return resultVo;
    			}
    			
    		}catch(Exception e){
    			LogCvt.error("调用外围模块判断评论描述是否有敏感词 出现异常 "+e.getMessage(), e);
    		}
        }
        Result result =  productLogic.addProductComment(productComment);
        ProductBeanUtil.copyProperties(resultVo, result);
        return  resultVo;
    }


    @Override
    public ResultVo updateProductComment(ProductCommentVo productCommentVo)
            throws TException {
        LogCvt.info("追加商品评论参数productCommentVo:"+JSON.toJSONString(productCommentVo));
        ProductComment productComment = new ProductComment();
        ProductBeanUtil.copyProperties(productComment, productCommentVo);
        ResultBean result = productLogic.updateProductComment(productComment);
        ResultVo resultVo=new ResultVo();
        if(!StringUtils.equals(ResultCode.success.getCode(), result.getCode())){
			LogCvt.error(result.getMsg());
			resultVo.setResultCode(result.getCode());
			resultVo.setResultDesc(result.getMsg());
		}else{
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc("追加商品评论成功");
		}
        return  resultVo;
    }

    
    @Override
    public ProductCommentVo queryProductComment(
            ProductCommentVo productCommentVo) throws TException {
        LogCvt.info("查询商品评论内容参数productCommentVo:"+JSON.toJSONString(productCommentVo));
        ProductComment productComment = new ProductComment();
        ProductBeanUtil.copyProperties(productComment, productCommentVo);
        ProductCommentVo pcVo = new ProductCommentVo();
        ProductComment pcPo = productLogic.queryProductComment(productComment);
        if(pcPo!=null){
            ProductBeanUtil.copyProperties(pcVo, pcPo);
        }
        return pcVo;
    }
    

    @Override
    public ProductCommentPageVo queryProductComments(
            ProductCommentFilterReq productCommentFilterReq, PageVo pageVo) throws TException {
        LogCvt.info("H5查询商品评论列表参数productCommentFilterReq:"+JSON.toJSONString(productCommentFilterReq)+",pageVo:"+JSON.toJSONString(pageVo));
        ProductCommentPageVo productCommentPageVo= new ProductCommentPageVo();
        Page<ProductComment> page = new Page<ProductComment>();
        
        ProductCommentFilter productCommentFilter = null;
        
        String filter = productCommentFilterReq.getFilter();
        try {
            ProductBeanUtil.copyProperties(page, pageVo);
            productCommentFilter = JSonUtil.toObject(filter, ProductCommentFilter.class);
        } catch (Exception e) {
            LogCvt.error("分页查询queryProductComments失败，原因:" + e.getMessage(),e);
        }
        if(productCommentFilter==null){
            productCommentFilter = new ProductCommentFilter();
        }
        productCommentFilter.setClientId(productCommentFilterReq.getClientId());
        
        MongoPage mPage = productLogic.queryProductComments(page, productCommentFilter);
        
        @SuppressWarnings("unchecked")
		List<ProductCommentInfo> productComments = (List<ProductCommentInfo>)mPage.getItems();
        List<ProductCommentVo> productCommentVos = new ArrayList<ProductCommentVo>();
        if(productComments != null && productComments.size() > 0 ){
            ProductCommentVo vo = null;
            for(ProductCommentInfo po : productComments ){
                vo = new ProductCommentVo();
                ProductBeanUtil.copyProperties(vo, po);
                if(po.getLorgCode()!=null && !"".equals(po.getLorgCode())){
                    vo.setOrgCode(po.getLorgCode());
                }else if(po.getTorgCode()!=null && !"".equals(po.getTorgCode())){
                    vo.setOrgCode(po.getTorgCode());
                }else if(po.getSorgCode()!=null && !"".equals(po.getSorgCode())){
                    vo.setOrgCode(po.getSorgCode());
                }else if(po.getForgCode()!=null && !"".equals(po.getForgCode())){
                    vo.setOrgCode(po.getForgCode());
                }
                productCommentVos.add(vo);
            }
        }else{
            LogCvt.info("查询queryProductComments无数据,查询条件为:"+productCommentFilter+" - "+JSON.toJSONString(page));
        }
        productCommentPageVo.setProductCommentVoList(productCommentVos);
        pageVo.setPageCount(mPage.getPageCount());
        pageVo.setTotalCount(mPage.getTotalCount());
        if(pageVo.getPageCount()>pageVo.getPageNumber()){
            pageVo.setHasNext(true);
        } else {
            pageVo.setHasNext(false);
        }
        productCommentPageVo.setPage(pageVo);
        return productCommentPageVo;
    }


    @Override
    public ResultVo addProductActivties(OriginVo originVo, 
            ProductRelateActivitiesVo relateActivities) throws TException {
    	LogCvt.info("添加商品关联活动参数originVo:"+JSON.toJSONString(originVo)+",relateActivities:"+JSON.toJSONString(relateActivities));
    	
    	//添加操作日志记录
    	originVo.setDescription("添加商品关联活动");
		LogUtils.addLog(originVo);
    	
    	List<ProductActivitiesInfo> productActivities=new ArrayList<ProductActivitiesInfo>();
    	ResultVo resultVo=new ResultVo();
    	ProductActivitiesInfo po=null;
    	ProductDetail productDetail=new ProductDetail();
    	if(relateActivities.getClientId()!=null&&!"".equals(relateActivities.getClientId())){
    		productDetail.setClientId(relateActivities.getClientId());
    	}else{
    		resultVo.setResultCode(ResultCode.productcomment_param_empty.getCode());
    		resultVo.setResultDesc("用户ID不能为空");
    		return resultVo;
    	}
    	if(relateActivities.getProductId()!=null){
    		productDetail.setId(relateActivities.getProductId());
    	}else{
    		resultVo.setResultCode(ResultCode.productcomment_param_empty.getCode());
    		resultVo.setResultDesc("商品ID不能为空");
    		return resultVo;
    	}
    	if(relateActivities.getMerchantId()!=null){
    		productDetail.setMerchantId(relateActivities.getMerchantId());
    	}else{
    		resultVo.setResultCode(ResultCode.productcomment_param_empty.getCode());
    		resultVo.setResultDesc("商户ID不能为空");
    		return resultVo;
    	}
    	if(relateActivities.getActivities()!=null&&relateActivities.getActivities().size()>0){
    		List<ProductActivitiesVo> productrelateActivities=relateActivities.getActivities();
    		for(ProductActivitiesVo vo : productrelateActivities){
    			po=new ProductActivitiesInfo();
    			po.setActivitiesId(vo.getActivitiesId());
    			po.setActivitiesType(vo.getActivitiesType());
    			productActivities.add(po);
    		}
    	}else{
    		resultVo.setResultCode(ResultCode.productcomment_param_empty.getCode());
    		resultVo.setResultDesc("活动信息不能为空");
    		return resultVo;
    	}
    	
    		productDetail.setActivitiesInfo(productActivities);
    	    ResultBean result=	productLogic.updateProductDetail(productDetail);
    	    if(!StringUtils.equals(ResultCode.success.getCode(), result.getCode())){
    			LogCvt.error(result.getMsg());
    			resultVo.setResultCode(result.getCode());
    			resultVo.setResultDesc(result.getMsg());
    		}else{
    			resultVo.setResultCode(ResultCode.success.getCode());
    			resultVo.setResultDesc("添加商品关联活动成功");
    		}
            return  resultVo;
    	
    }


    @Override
    public ResultVo addProductOutlets(OriginVo originVo, ProductRelateOutletVo outlet)
            throws TException {
    	LogCvt.info("originVo:"+JSON.toJSONString(originVo)+"添加商品门店关联信息ProductOutlets,outlet:"+outlet);
    	
    	//添加操作日志记录
    	originVo.setDescription("添加商品门店关联信息");
		LogUtils.addLog(originVo);
    	
    	ProductDetail productDetail=new ProductDetail();
    	List<ProductOutlet> outlets=new ArrayList<ProductOutlet>();
    	ProductOutlet po=null;
    	productDetail.setClientId(outlet.getClientId());
    	productDetail.setId(outlet.getProductId());
    	productDetail.setMerchantId(outlet.getMerchantId());
    	for(String id:outlet.getOutletIds()){
            po=new ProductOutlet();
            po.setOutletId(id);
            outlets.add(po);
        }
        productDetail.setOutletInfo(outlets);
        ResultBean result= productLogic.addProductOutlets(productDetail);
        ResultVo resultVo=new ResultVo();
        if(!StringUtils.equals(ResultCode.success.getCode(), result.getCode())){
            LogCvt.error(result.getMsg());
            resultVo.setResultCode(result.getCode());
            resultVo.setResultDesc(result.getMsg());
        }else{
            resultVo.setResultCode(ResultCode.success.getCode());
            resultVo.setResultDesc("添加商品关联活动成功");
        }
        return  resultVo;
    }


    @Override
    public ResultVo updateProductStatusBatch(OriginVo originVo, 
            ProductStatusBatchVoReq productStatusBatchVoReq) throws TException {
        LogCvt.info("批量上下架商品参数originVo:"+JSON.toJSONString(originVo)+",productStatusBatchVoReq:"+JSON.toJSONString(productStatusBatchVoReq));
        
        //添加操作日志记录
    	originVo.setDescription("批量修改上下架状态");
		LogUtils.addLog(originVo);
        
        ResultVo resultVo = new ResultVo();
        if(productStatusBatchVoReq.getStatus()==null || "".equals(productStatusBatchVoReq.getStatus())){
            resultVo.setResultDesc("修改状态不能为空，status必须传值");
            resultVo.setResultCode(ResultCode.failed.getCode());
            return  resultVo;
        }
        if(productStatusBatchVoReq!=null && productStatusBatchVoReq.getProductIds().size()>0){
            Result result = productLogic.updateProductStatusBatch(productStatusBatchVoReq.getClientId(),productStatusBatchVoReq.getStatus(),productStatusBatchVoReq.getProductIds());
            ProductBeanUtil.copyProperties(resultVo, result);
        } else {
            resultVo.setResultDesc("没有需要修改的数据");
            resultVo.setResultCode(ResultCode.failed.getCode());
        }
        return  resultVo;
    }


    @Override
    public ResultVo replayProductCommentBatch(List<ProductCommentVo> productCommentVo)
			throws TException {
    	 LogCvt.info("批量回复评论参数productCommentVo:"+JSON.toJSONString(productCommentVo));
    	 ResultVo resultVo = new ResultVo();
    	 if(productCommentVo==null || productCommentVo.size()==0){
    	     resultVo.setResultCode(ResultCode.failed.getCode());
             resultVo.setResultDesc("没有批量回复的数据");
             return resultVo;
    	 }
    	 ProductCommentVo pcVo = productCommentVo.get(0);
         if(pcVo.getRecomment()==null || "".equals(pcVo.getRecomment())){
             resultVo.setResultCode(ResultCode.productcomment_param_empty.getCode());
             resultVo.setResultDesc("回复内容不能为空");
             return resultVo;
         } else {
     		try{
    			SenseWordsService.Iface swService = 
    				(SenseWordsService.Iface)ThriftClientProxyFactory.createIface(SenseWordsService.class.getName(), SenseWordsService.class.getSimpleName(), GoodsConstants.THRIFT_SUPPORT_HOST, GoodsConstants.THRIFT_SUPPORT_PORT);
    			ResultVo isSense = swService.isContaintSensitiveWord(pcVo.getRecomment());
    			
    			if( isSense.getResultCode().equals(ResultCode.failed.getCode())){
    				   LogCvt.info("敏感词:" +isSense.getResultDesc());
    	                resultVo.setResultCode(ResultCode.failed.getCode());
    	                resultVo.setResultDesc("您的评论包含敏感词，请修改后再提交！敏感词:"+isSense.getResultDesc());
    	                return resultVo;
    			}
    			
    		}catch(Exception e){
    			LogCvt.error("调用外围模块判断评论描述是否有敏感词 出现异常 "+e.getMessage(), e);
    		}
         }
         if(pcVo.getMerchantUserName()==null || "".equals(pcVo.getMerchantUserName())){
             resultVo.setResultCode(ResultCode.productcomment_param_empty.getCode());
             resultVo.setResultDesc("回复人姓名不能为空");
             return resultVo;
         }
         for(ProductCommentVo vo : productCommentVo){
             if(vo.getCommentId()==null || "".equals(vo.getCommentId())){
                 resultVo.setResultCode(ResultCode.productcomment_param_empty.getCode());
                 resultVo.setResultDesc("评论ID不能为空");
                 return resultVo;
             }
         }
    	 List<ProductComment> productComments=new ArrayList<ProductComment>();
         ProductComment productComment =null;
         for(ProductCommentVo vo : productCommentVo){
             productComment = new ProductComment();
             productComment.setClientId(vo.getClientId());
             productComment.setCommentId(vo.getCommentId());
             productComments.add(productComment);
         } 
         
         Result result = productLogic.replayProductCommentBatch(pcVo.getRecomment(),pcVo.getMerchantUserName(),productComments);
         ProductBeanUtil.copyProperties(resultVo, result); 
         return resultVo;
    }


    @Override
    public List<ProductAreaVo> getProductAreaOutlets(ProductAreaVo productAreaVo)
            throws TException {
        LogCvt.info("获取商品该区域对应的门店参数productAreaVo:"+JSON.toJSONString(productAreaVo));
        List<ProductAreaVo> pavos = new ArrayList<ProductAreaVo>();//返回list
        
        ProductAreaOutlet productArea = new ProductAreaOutlet();
        if(productAreaVo.getProductId()!=null && !"".equals(productAreaVo.getProductId())){
        	productArea.setProductId(productAreaVo.getProductId());
        } else {
        	return pavos;
        }
        if(productAreaVo.getClientId()!=null&&!"".equals(productAreaVo.getClientId())){
        	productArea.setClientId(productAreaVo.getClientId());
        }
        if(productAreaVo.getAreaId()>0){
        	productArea.setAreaId(productAreaVo.getAreaId());
        } else if(productAreaVo.getCityId()>0){
        	productArea.setAreaId(productAreaVo.getCityId());
        }
        List<ProductAreaOutlet> productAreas = productLogic.getProductAreaOutlets(productArea);
        if(productAreas!=null && productAreas.size()>0){
            ProductAreaVo vo = null;
            for(ProductAreaOutlet po : productAreas){
                vo = new ProductAreaVo();
                ProductBeanUtil.copyProperties(vo, po);
                pavos.add(vo);
            }
        }
        return pavos;
    }


    @Override
    public ResultVo invalidProductBatch(OriginVo originVo, InvalidProductBatchVo invalidProductBatchVo) throws TException {
        LogCvt.info("商户解约时候商品批量失效参数originVo:"+JSON.toJSONString(originVo)+",invalidProductBatchVo:"+JSON.toJSONString(invalidProductBatchVo));
        //添加操作日志记录
    	originVo.setDescription("批量商品失效");
		LogUtils.addLog(originVo);
        
        ResultVo resultVo = new ResultVo();
        if(invalidProductBatchVo.getClientId()!=null && !"".equals(invalidProductBatchVo.getClientId())){
            Product product = new Product();
            ProductBeanUtil.copyProperties(product, invalidProductBatchVo); 
            Boolean result = productLogic.invalidProductBatch(product);
            if(result){
                resultVo.setResultCode(ResultCode.success.getCode());
                resultVo.setResultDesc("批量商品失效成功");
            } else {
                resultVo.setResultCode(ResultCode.failed.getCode());
                resultVo.setResultDesc("批量商品失效失败");
            }
        } else {
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("客户端id不能为空");
        }
        return resultVo;
    }


    @Override
    public int queryProductCommentCount(
            ProductCommentFilterReq productCommentFilterReq) throws TException {
    	LogCvt.info("查询商品回复数量参数ProductCommentFilterReq:"+JSON.toJSONString(productCommentFilterReq));
    	ProductCommentFilter po=new ProductCommentFilter();;
    	String filter=productCommentFilterReq.getFilter();
    	ProductBeanUtil.copyProperties(po, productCommentFilterReq);
		 po = JSonUtil.toObject(filter, ProductCommentFilter.class); 
    	 po.setClientId(productCommentFilterReq.getClientId());
        return productLogic.queryProductCommentCount(po);
    }


    @Override
    public ResultVo deleteProductBatch(OriginVo originVo, ProductStatusBatchVoReq productStatusBatchVoReq)
            throws TException {
        LogCvt.info("批量删除商品参数originVo:"+JSON.toJSONString(originVo)+",ProductStatusBatchVoReq:"+JSON.toJSONString(productStatusBatchVoReq));
        
        //添加操作日志记录
    	originVo.setDescription("批量删除商品信息");
		LogUtils.addLog(originVo);
        
        ResultVo resultVo = new ResultVo();
        if(productStatusBatchVoReq!=null && productStatusBatchVoReq.getProductIds().size()>0){
            Result result = productLogic.deleteProductBatch(productStatusBatchVoReq.getClientId(),productStatusBatchVoReq.getProductIds());
            ProductBeanUtil.copyProperties(resultVo, result);
        } else {
            resultVo.setResultDesc("没有需要删除的数据");
            resultVo.setResultCode(ResultCode.failed.getCode());
        }
        return resultVo;
    }


	@Override
	public List<ProductStatusVoReq> getProductStatus() throws TException {
		LogCvt.info("获取商品状态ProductStatusVoReq");
		List<ProductStatusVoReq> productStatusVoReqs=new ArrayList<ProductStatusVoReq>();
		ProductStatusVoReq onShelf=new ProductStatusVoReq();
		onShelf.setStatus(ProductStatus.onShelf.getCode());
		onShelf.setName(ProductStatus.onShelf.getDescribe());
		ProductStatusVoReq offShelf=new ProductStatusVoReq();
		offShelf.setStatus(ProductStatus.offShelf.getCode());
		offShelf.setName(ProductStatus.offShelf.getDescribe());
		ProductStatusVoReq noShelf=new ProductStatusVoReq();
		noShelf.setStatus(ProductStatus.noShelf.getCode());
		noShelf.setName(ProductStatus.noShelf.getDescribe());
		productStatusVoReqs.add(onShelf);
		productStatusVoReqs.add(offShelf);
		productStatusVoReqs.add(noShelf);
		return productStatusVoReqs;
	}
	
	
	/**
     * 分页查询 StoreProductInfo
     * @param page
     * @param storeProductInfo
     * @return Page<StoreProductInfo>    结果集合 
     */
	@SuppressWarnings("unchecked")
	public StoreProductInfoPageVoRes getStoreProductInfoByPage(PageVo pageVo, String clientId, long memberCode)throws TException {
		
		LogCvt.info("分页查询StoreProductInfo");
		StoreProductInfoPageVoRes storeProductInfoPageVoRes = new StoreProductInfoPageVoRes();
		Page<StoreProductInfo>  page = new Page<StoreProductInfo>();
		page = (Page<StoreProductInfo>)BeanUtil.copyProperties(Page.class, pageVo);
		List<StoreProductInfoVo> storeProductInfoVoList = new ArrayList<StoreProductInfoVo>();
		page = productLogic.findStoreProductInfoByPage(page,clientId, memberCode);
		if(page.getResultsContent() !=null){
			for(StoreProductInfo po : page.getResultsContent()){
				StoreProductInfoVo vo = (StoreProductInfoVo)BeanUtil.copyProperties(StoreProductInfoVo.class, po);
				storeProductInfoVoList.add(vo);
			}
			pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		}
		if(pageVo.getPageCount()>pageVo.getPageNumber()){
            pageVo.setHasNext(true);
        } else {
            pageVo.setHasNext(false);
        }
		storeProductInfoPageVoRes.setPage(pageVo);
		storeProductInfoPageVoRes.setStoreProductInfoVoList(storeProductInfoVoList);
		return storeProductInfoPageVoRes;
		
	}
	
	
	/** 是否收藏商品
	* @Title: isExitsStoreProductInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param productId
	* @param @return
	* @param @throws TException
	* @return int
	* @throws 
	*/
	@Override
	public boolean isExitsStoreProductInfo(String clientId, long memberCode,String productId) 
			throws TException {
		boolean result = false;
		LogCvt.info("查询StoreProductInfoVo");
		int rint = productLogic.isExitsStoreProductInfo(clientId, memberCode, productId);
		result = rint > 0 ? true : false;
		return result;
	}
	
	/**
     * 增加商品收藏
     * @param storeProductInfoVo
     * @return long    主键ID
     * @param clientId
     * @param memberCode
     * @param storeProductInfoVo
     */
	@Override
	public ResultVo addStoreProductInfoVo(String clientId, long memberCode,
			StoreProductInfoVo storeProductInfoVo) throws TException {
		ResultVo resultVo=new ResultVo();
		StoreProductInfo storeProductInfo =null;
		storeProductInfo = (StoreProductInfo)BeanUtil.copyProperties(StoreProductInfo.class, storeProductInfoVo);
		ResultBean result = productLogic.addStoreProductInfo(clientId, memberCode, storeProductInfo);
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("添加商品收藏成功");
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getCode())){
			LogCvt.error(result.getMsg());
			resultVo.setResultCode(result.getCode());
			resultVo.setResultDesc(result.getMsg());
		}
		return resultVo;
		
	}

	/**
	 * 取消商品收藏 StoreProductInfoVo
	 * @param storeProductInfoVo
	 * @return boolean
	 * @param clientId
	 * @param memberCode
	 * @param productId
	 */
	@Override
	public boolean deleteStoreProductInfoVo(String clientId, long memberCode,
			String productId) throws TException {
		boolean result = false;
		LogCvt.info("移除StoreProductInfoVo");
		int rint = productLogic.removeStoreProductInfo(clientId, memberCode, productId);
		result = rint > -1 ? true : false;
		return result;
	}
	
	/**
     * 商品收藏查询接口 StoreProductInfoVo
     * @param storeProductInfoVo
     * @return List<StoreProductInfoVo>
     * @param clientId
     * @param memberCode
     */
	@Override
	public List<StoreProductInfoVo> getStoreProductInfoVo(String clientId,
			long memberCode) throws TException {
		List<StoreProductInfoVo> storeProductInfoVoList = new ArrayList<StoreProductInfoVo>();
		List<StoreProductInfo> storeProductInfoList = new ArrayList<StoreProductInfo>();
		storeProductInfoList = productLogic.queryStoreProductInfo(clientId, memberCode);
		for(StoreProductInfo po : storeProductInfoList){
			StoreProductInfoVo vo = (StoreProductInfoVo)BeanUtil.copyProperties(StoreProductInfoVo.class, po);
			storeProductInfoVoList.add(vo);
		}
		return storeProductInfoVoList;
	}

	@Override 
	public ResultVo updateAuditProductBatch(OriginVo originVo,
			List<ProductAuditVo> productAuditVoList) throws TException {
		LogCvt.info("批量提交审核参数originVo:"+JSON.toJSONString(originVo)+",List<ProductAuditVo>:"+JSON.toJSONString(productAuditVoList));
		
		//添加操作日志记录
    	originVo.setDescription("批量更新审核状态");
		LogUtils.addLog(originVo);
		
		List<Product> products=new ArrayList<Product>();
		for(ProductAuditVo vo:productAuditVoList){
			Product po=new Product();
			po.setClientId(vo.getClientId());
			po.setProductId(vo.getProductId());
			po.setAuditState(vo.getAuditState());
			products.add(po);
		}
		ResultBean result=productLogic.updateAuditProductBatch(products);
		ResultVo resultVo=new ResultVo();
        if(!StringUtils.equals(ResultCode.success.getCode(), result.getCode())){
            LogCvt.error(result.getMsg());
            resultVo.setResultCode(result.getCode());
            resultVo.setResultDesc(result.getMsg());
        }else{
            resultVo.setResultCode(ResultCode.success.getCode());
            resultVo.setResultDesc("商品批量更新审核状态成功");
        }
        return  resultVo;
	}


    @Override
	public List<ProductBaseInfoVo> getProductBaseInfo(String clientId,
			List<ProductStoreFilterVo> productStoreFilters) throws TException {
        LogCvt.info("查询商品价格等简单信息供商品收藏用参数clientId:"+clientId+",productStoreFilters:"+JSON.toJSONString(productStoreFilters));
        List<ProductBaseInfoVo> productBaseInfos = new ArrayList<ProductBaseInfoVo>();
        
        List<ProductBaseInfo> psfs = new ArrayList<ProductBaseInfo>();
        ProductBaseInfo p = null;
        for(ProductStoreFilterVo productStoreFilter : productStoreFilters){
            p= new ProductBaseInfo();
            p.setMerchantId(productStoreFilter.getMerchantId());
            p.setProductId(productStoreFilter.getProductId());
            psfs.add(p);
        }
        List<ProductBaseInfo> products = productLogic.getProductBaseInfo(clientId,psfs);
        if(products!=null && products.size()>0){
            for(ProductBaseInfo productBaseInfo : products){
                ProductBaseInfoVo productBaseInfoVo = new ProductBaseInfoVo();
                ProductBeanUtil.copyPropertiesScale(productBaseInfoVo, productBaseInfo, GoodsConstants.DOUBLE_INTGER_CHANGE);
                productBaseInfos.add(productBaseInfoVo);
            }
        }
        return productBaseInfos;
    }


    @Override
    public List<AddProductVoRes> addProductBatch(OriginVo originVo,
			List<ProductInfoVo> productInfoVos) throws TException {
        LogCvt.info("批量添加商品参数originVo:"+JSON.toJSONString(originVo)+",List<ProductInfoVo>:"+JSON.toJSONString(productInfoVos));
        
        //添加操作日志记录
    	originVo.setDescription("批量添加商品信息");
		LogUtils.addLog(originVo);
        
        List<AddProductVoRes> resultVos = new ArrayList<AddProductVoRes>();
        List<ProductInfo> productInfos = new ArrayList<ProductInfo>();
        for(ProductInfoVo productInfoVo : productInfoVos){
            ProductInfo productInfo = this.changFromProductInfoVo(productInfoVo);
            if(productInfo!=null){
                productInfos.add(productInfo);
            }
        }
        if(productInfos.size()>0){
            return productLogic.addProductBatch(productInfos);
        } else {
            AddProductVoRes addProductVoRes= new AddProductVoRes();
            addProductVoRes.setProductId(null);
            ResultVo resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.success.getCode());
            resultVo.setResultDesc("没有批量新加商品");
            addProductVoRes.setResult(resultVo);
            resultVos.add(addProductVoRes);
        }
        return resultVos;
    }
    
   
    /**
     * 修改 Product模块的敏感词
     * @param opt 1表示新增0表示删除
     * @param word 敏感词
     * @return
     * @param opt
     * @param word
     */
    @Override
    public void modifySenseWord(int opt, String word) throws TException
    {
        LogCvt.info("修改敏感词:"+ word);
        Set<String> newWordSets = new HashSet<String>();
        newWordSets.add(word);
        SensitivewordFilter.addNewSensitiveWordsToMap(newWordSets);
        return;
    }
    
    
    @Override
    public ResultVo updateProductInfo(OriginVo originVo,
            ProductBaseVo productBaseVo) throws TException {
        LogCvt.info("上架的商品可以修改库存和结束时间接口参数originVo:"+JSON.toJSONString(originVo)+",productBaseVo:"+JSON.toJSONString(productBaseVo));
        ResultVo resultVo = new ResultVo();
        resultVo.setResultCode(ResultCode.failed.getCode());
        resultVo.setResultDesc("此接口已经不用");
        return resultVo;
    }
    
    @Override
    public ResultVo endisableProductBatch(OriginVo originVo,
            InvalidProductBatchVo invalidProductBatchVo, String flag)
            throws TException {
        LogCvt.info("商户禁用启用时候批量启用禁用商品参数originVo:"+JSON.toJSONString(originVo)+",invalidProductBatchVo:"+JSON.toJSONString(invalidProductBatchVo)+",flag:"+flag);
        //添加操作日志记录
        if("0".equals(flag)){
            originVo.setDescription("批量禁用商品");
        } else if("1".equals(flag)){
            originVo.setDescription("批量启用商品");
        }
        LogUtils.addLog(originVo);
        
        ResultVo resultVo = new ResultVo();
        if(invalidProductBatchVo.getClientId()!=null && !"".equals(invalidProductBatchVo.getClientId())){
            Map<String,String> param = new HashMap<String,String>();
            param.put("clientId", invalidProductBatchVo.getClientId());
            param.put("merchantId", invalidProductBatchVo.getMerchantId());
            param.put("flag", flag);
            Boolean result = productLogic.endisableProductBatch(param);
            if("0".equals(flag)){
                resultVo.setResultDesc("商品批量禁用");
            } else if("1".equals(flag)){
                resultVo.setResultDesc("商品批量启用");
            }
            if(result){
                resultVo.setResultCode(ResultCode.success.getCode());
                resultVo.setResultDesc(resultVo.getResultDesc()+"成功");
            } else {
                resultVo.setResultCode(ResultCode.failed.getCode());
                resultVo.setResultDesc(resultVo.getResultDesc()+"失败");
            }
        } else {
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("客户端id不能为空");
        }
        return resultVo;
    }
    
    @Override
    public ResultVo addActivtyToProducts(OriginVo originVo, long activityId,
			List<String> productIds) throws TException {
        LogCvt.info("活动绑定商品参数 originVo:"+JSON.toJSONString(originVo)+",activityId:"+activityId+",productIds:"+JSON.toJSONString(productIds));
        //添加操作日志记录
        originVo.setDescription("活动绑定商品");
        LogUtils.addLog(originVo);
        
        ResultVo resultVo = new ResultVo();
        if(activityId<1){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("活动id不能为空");
        }
        if(productIds==null || productIds.size()==0){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("活动绑定的商品不能为空");
        }
        ActivitiesInfo activitiesInfo = new ActivitiesInfo();
        activitiesInfo.setId(activityId);
        Result result = productLogic.addActivtyToProducts(activitiesInfo.getId(), productIds);
        ProductBeanUtil.copyProperties(resultVo, result);
        return resultVo;
    }
    
    @Override
    public ResultVo removeActivtyFromProducts(OriginVo originVo,
            long activtyId, String productId) throws TException {
        LogCvt.info("活动解除绑定商品参数 originVo:"+JSON.toJSONString(originVo)+",activityId:"+activtyId+",productId:"+productId);
        //添加操作日志记录
        originVo.setDescription("活动解除绑定商品");
        LogUtils.addLog(originVo);
        ResultVo resultVo = new ResultVo();
        if(activtyId<1){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("活动id不能为空");
        }
        Result result = productLogic.removeActivtyFromProducts(activtyId, productId);
        ProductBeanUtil.copyProperties(resultVo, result);
        return resultVo;
    }
    
    /**
     * 查询送积分活动已经绑定的商品列表
     */
    @Override
    public ActivityProductPageVo findActivtyProductsByPage(long activtyId, PageVo pageVo)
            throws TException {
        LogCvt.info("查询送积分活动已经绑定的商品列表参数:activtyId:"+activtyId+",pageVo:"+JSON.toJSONString(pageVo));
        ActivityProductPageVo apPageVo = new ActivityProductPageVo();
        if(activtyId<1){
            return apPageVo;
        }
        try{
            Page<ProductDetail> page = new Page<ProductDetail>();
            ProductBeanUtil.copyProperties(page, pageVo);
            
            MongoPage mPage = productLogic.findActivtyProductsByPage(activtyId,page);
            @SuppressWarnings("unchecked")
			List<ProductDetail> productDetails = (List<ProductDetail>)mPage.getItems();
            List<ActivityProductVo> productVoList = new ArrayList<ActivityProductVo>();
            if (productDetails!=null && productDetails.size()>0) {
                ActivityProductVo vo = null;
                for (ProductDetail po : productDetails) {
                    vo = new ActivityProductVo();
                    vo.setClientId(po.getClientId());//客户端id
                    vo.setProductId(po.getId());//商品id
                    vo.setName(po.getName());//商品名
                    if(po.getRackTime()!=null){
                        vo.setRackTime(po.getRackTime().getTime());//上架时间 long类型
                    }
                    if(po.getPrice()!=null){
                        vo.setPrice(Arith.div(po.getPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));//销售价
                    }
                    if(ProductStatus.onShelf.getCode().equals(po.getIsMarketable())){
                        vo.setIsMarketable("是");
                    } else {
                        vo.setIsMarketable("否");
                    }
                    if(po.getCreateTime()!=null){
                        vo.setCreateTime(po.getCreateTime().getTime());//创建时间 long类型
                    }
                    VipBindProductInfo vipBind= productLogic.findVipBindProduct(po.getId());
                    if(vipBind!=null){
                        vo.setVip1Price(Arith.div(vipBind.getVipPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));// vip1价
                        vo.setMax(vipBind.getVipLimit());//// vip1限购
                    }
                    //添加到list中进行返回
                    productVoList.add(vo);
                }
            }
            apPageVo.setActivityProductVoList(productVoList);
            pageVo.setPageCount(mPage.getPageCount());
            pageVo.setTotalCount(mPage.getTotalCount());
            if(pageVo.getPageCount()>pageVo.getPageNumber()){
                pageVo.setHasNext(true);
            } else {
                pageVo.setHasNext(false);
            }
            apPageVo.setPage(pageVo);
        }catch (Exception e) {
            LogCvt.error("查询活动绑定的商品列表失败，原因:" + e.getMessage(),e); 
        }
        return apPageVo;
    }
    
    /**
     * 查询可以绑定送积分活动的商品列表
     */
    @Override
    public ActivityProductPageVo findProductsForActivtyByPage(long activtyId, String name, PageVo pageVo)
            throws TException {
        LogCvt.info("查询可以绑定送积分活动的商品列表参数activtyId:"+activtyId+",name:"+name+",pageVo:"+JSON.toJSONString(pageVo));
        
        ActivityProductPageVo apPageVo = new ActivityProductPageVo();
        ResultVo resultVo = new ResultVo();
        if(activtyId<1){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("活动id不能为空");
        }
        Page<Product> page = new Page<Product>();
        try{
            ProductBeanUtil.copyProperties(page, pageVo);
            
            List<Product> pList = vipProductLogic.findProductsForActivtyByPage(activtyId,name,page);
            List<ActivityProductVo> productVoList = new ArrayList<ActivityProductVo>();
            if (pList!=null && pList.size()>0) {
                ActivityProductVo vo = null;
                for (Product po : pList) {
                    vo = new ActivityProductVo();
                    vo.setClientId(po.getClientId());//客户端id
                    vo.setProductId(po.getProductId());//商品id
                    vo.setName(po.getName());//商品名
                    if(po.getRackTime()!=null){
                        vo.setRackTime(po.getRackTime().getTime());//上架时间 long类型
                    }
                    if(po.getPrice()!=null){
                        vo.setPrice(Arith.div(po.getPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));//销售价
                    }
                    if(ProductStatus.onShelf.getCode().equals(po.getIsMarketable())){
                        vo.setIsMarketable("是");
                    } else {
                        vo.setIsMarketable("否");
                    }
                    if(po.getCreateTime()!=null){
                        vo.setCreateTime(po.getCreateTime().getTime());//创建时间 long类型
                    }
                    VipBindProductInfo vipBind= productLogic.findVipBindProduct(po.getProductId());
                    if(vipBind!=null){
                        vo.setVip1Price(Arith.div(vipBind.getVipPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));// vip1价
                        vo.setMax(vipBind.getVipLimit());//// vip1限购
                    }

                    //添加到list中进行返回
                    productVoList.add(vo);
                }
            }
            apPageVo.setActivityProductVoList(productVoList);
            pageVo.setPageCount(page.getPageCount());
            pageVo.setTotalCount(page.getTotalCount());
            if(pageVo.getPageCount()>pageVo.getPageNumber()){
                pageVo.setHasNext(true);
            } else {
                pageVo.setHasNext(false);
            }
            apPageVo.setPage(pageVo);
        }catch (Exception e) {
            LogCvt.error("查询活动绑定的商品列表失败，原因:" + e.getMessage(),e); 
        }
        return apPageVo;
    }
    
    /**
     * 启用商户时 将该普通商户的商品的未提交状态变成待审核
     */
    @Override
    public ResultVo validMerchantProductBatch(String merchantId)
            throws TException {
        LogCvt.info("启用商户时候启用商品merchantId:"+merchantId);
        ResultVo resultVo = new ResultVo();
        if(merchantId!=null && !"".equals(merchantId)){
            Boolean result = productLogic.validMerchantProductBatch(merchantId);
            if(result){
                resultVo.setResultCode(ResultCode.success.getCode());
                resultVo.setResultDesc("启用商户时 将该普通商户的商品的未提交状态变成待审核");
            } else {
                resultVo.setResultCode(ResultCode.failed.getCode());
                resultVo.setResultDesc("启用商户时 将该普通商户的商品的未提交状态变成待审核");
            }
        } else {
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商户id不能为空");
        }
        return resultVo;
    }
    
	/**
	 * 查询一个商品下面所有可以提货的网点
	 */
	public ProductOutletPageVo findProductsForAreaByPage(String productId,
			long areaid, String clientId, PageVo pageVo) throws TException {
		LogCvt.info("查询一个商品下面所有可以提货的网点;findProductsForAreaByPage:productId:"+productId+"areaid"+areaid+"clientId"+clientId);
		ProductOutletPageVo page = new ProductOutletPageVo();
		PageVo resultPageVo = new PageVo();
		Product product = new Product();
		product.setProductId(productId);
		product.setClientId(clientId);
		ProductDetail pd = productLogic.getProductDetail(product,null);// 查询mongo中的数据
		List<ProductCityOutlet> orgOutlets = pd.getOrgOutlets();
		List<ProductOutletVo> productOut = new ArrayList<ProductOutletVo>();
        //根据查询条件传进来的areaid判断是市级地区id还是区级地区id
        Long cityId = null;//市级地区id
        Long careaId = null;//区级地区id
        if(areaid>0){
            CommonLogic comLogic = new CommonLogicImpl();
            Area area = comLogic.findAreaById(areaid);
            if(area!=null){
                String areaTreePath = area.getTreePath();
                if(Checker.isNotEmpty(areaTreePath)){
                    String[] treePtah = areaTreePath.split(",");
                    if(treePtah.length==2){//areaId为市
                    	cityId = areaid;
                    } 
                    if(treePtah.length==3){//areaId为区
                    	cityId=Long.valueOf(treePtah[1]);
                    	careaId = areaid;
                    } 
                }
            }
        }
		int count=0;
		int pageSize = pageVo.getPageSize();
		int pageNumber=pageVo.getPageNumber();
		if (orgOutlets != null && orgOutlets.size() > 0) {
		  if(areaid>0){
			for (ProductCityOutlet out : orgOutlets) {
				if (cityId.equals(out.getCityId())) {  //先查城市 
					int i = 0;
					if (out.getOrgOutlets() != null && out.getOrgOutlets().size() > 0) {
						count = out.getOrgOutlets().size();
						int divisor = count / pageSize;
						int remainder = count % pageSize;
						resultPageVo.setPageSize(pageSize);
						resultPageVo.setPageNumber(pageNumber);
						resultPageVo.setPageCount(remainder == 0 ? divisor == 0 ? 1 : divisor : divisor + 1);
						resultPageVo.setTotalCount(count);
			            if(resultPageVo.getPageCount()>pageNumber){
			            	resultPageVo.setHasNext(true);
			            } else {
			            	resultPageVo.setHasNext(false);
			            }
						for (ProductOutlet childOutlet : out.getOrgOutlets()) {
							i = i + 1;
							if (i>pageSize*(pageNumber-1) && i <= pageNumber * pageSize) {
								ProductOutletVo newout = new ProductOutletVo();
								if(careaId!=null&&careaId>0){  //在查区
									if(careaId.equals(childOutlet.getAreaId())){
								       ProductBeanUtil.copyProperties(newout, childOutlet);
								       productOut.add(newout);
									}
								}else{
								       ProductBeanUtil.copyProperties(newout, childOutlet);
								       productOut.add(newout);	
								}
							}
						}
					}
				}
			 }
			}
		}
		page.setProductOutletVo(productOut);
		page.setPage(resultPageVo);
		return page;
	}
	
	/**
     * 查询一个商品下面所有可以提货的区
     * @param productId
     * @return list
     */
	@Override
	public List<AreaVo> findAreaForCityByList(String productId, long cityId,
			String clientId) throws TException {
		List<AreaVo> list=new ArrayList<AreaVo>();
		Product product =new Product();
		product.setProductId(productId);
		product.setClientId(clientId);
        ProductDetail pd = productLogic.getProductDetail(product,null);//查询mongo中的数据
        if(pd!=null){
        	//根据查询条件传进来的areaid判断是市级地区id还是区级地区id
            Long ccityId = null;//市级地区id
            if(cityId>0){
                CommonLogic comLogic = new CommonLogicImpl();
                Area area = comLogic.findAreaById(cityId);
                if(area!=null){
                    String areaTreePath = area.getTreePath();
                    if(Checker.isNotEmpty(areaTreePath)){
                        String[] treePtah = areaTreePath.split(",");
                        if(treePtah.length==2){//areaId为市
                        	ccityId = cityId;
                        } 
                        if(treePtah.length==3){//areaId为区
                        	ccityId = Long.valueOf(treePtah[1]);
                        } 
                    }
                }
            }
        	List<ProductCityArea> orgOutlets=pd.getCityAreas();
            for(ProductCityArea out:orgOutlets){
            	if(ccityId!=null && ccityId>0 && ccityId.longValue()==out.getCityId() && out.getCountys().size()>0){
            		for(ProductArea childArea:out.getCountys()){
            			   AreaVo newarea=new AreaVo(); 
            			   newarea.setId(childArea.getAreaId());
            			   newarea.setName(childArea.getAreaName());
            			   newarea.setClientId(clientId);
                	       list.add(newarea);
            		}
            	}
            }
        }
		return list;
	}
	
    /**
     * VIP预售推荐列表
     * 1.数据取自精品预售商品数据；
     * 2.优先取“正在预售”中的商品，取最优惠的前三个商品，“最优惠”定义为：VIP会员价/普通会员价，数值越小，折扣越大，表示越优惠；若折扣率相同，则VIP价最高的排最前；若VIP价也相同，则最新上架的商品排最前。
     * 3.若正在预售中的商品取出来后不足3个，则有几个展示几个；
     * 4.若没有正在预售中的商品，则取“下期预售”中的商品，并按照最优惠定义展示前3个；
     */
    @Override
    public ProductBriefPageVoRes queryVipPresellProducts(
            ProductFilterVoReq productFilterVoReq, PageVo pageVo)
            throws TException {
        LogCvt.debug("VIP预售推荐列表,time:"+DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT5, new Date())+"参数ProductFilterVoReq:"+JSON.toJSONString(productFilterVoReq)+"pageVo:"+JSON.toJSONString(pageVo));
        
        MongoPage mongoPage = new MongoPage();
        mongoPage.setPageNumber(pageVo.getPageNumber());
        mongoPage.setPageSize(pageVo.getPageSize());
        
        ProductBriefPageVoRes productPageVo = new ProductBriefPageVoRes();
        try{
            ProductFilter product = new ProductFilter();
            ProductFilterStr productFilterVo = null;
            String filter = productFilterVoReq.getFilter();
            if(filter!=null){
                try{
                    if(filter.indexOf("orderField")!=-1){
                        int ofIndex = filter.indexOf("orderField");
                        int k = filter.indexOf("}");
                        if(k<(filter.length()-1)){
                            String orderField = filter.substring(ofIndex, k+"}".length());
                            orderField = orderField.substring(orderField.indexOf("{"));
                            filter=filter.substring(0,filter.indexOf(orderField)) + filter.substring(filter.indexOf(orderField)+orderField.length());
                        }
                    }
                    if(filter!=null && !"".equals(filter)){
                        productFilterVo = JSonUtil.toObject(filter, ProductFilterStr.class);
                    }
                } catch (Exception e) {
                    LogCvt.error("VIP预售推荐列表 解析filter失败，原因:" + e.getMessage(),e); 
                }
            }
            if(productFilterVo==null){
                productFilterVo = new ProductFilterStr();
            }
//            ProductBeanUtil.copyPropertiesScale(product, productFilterVo, GoodsConstants.DOUBLE_INTGER_CHANGE);
            product.setAreaId(productFilterVo.getAreaId());
            product.setIsMarketable(ProductStatus.onShelf.getCode());//已经上架的查询出来
            product.setClientId(productFilterVoReq.getClientId());
            product.setType(ProductType.presell.getCode());//预售商品
            
            //返回变量
            List<ProductBriefVoRes> productBriefVoList = new ArrayList<ProductBriefVoRes>();
            mongoPage = productLogic.queryVipPresellProducts(mongoPage,product);
            
            @SuppressWarnings("unchecked")
			List<ProductDetail> productDetails = (List<ProductDetail>)mongoPage.getItems();
            //po转成vo
            if(productDetails!=null && productDetails.size()>0){
                ProductBriefVoRes vo = null;
                Long now = new Date().getTime();
                RedisManager redis = new RedisManager();
                for(ProductDetail pd : productDetails){
                    vo = new ProductBriefVoRes();
                    vo.setClientId(pd.getClientId());
                    vo.setMerchantId(pd.getMerchantId());
                    vo.setProductId(pd.getId());
                    vo.setName(pd.getName());
                    vo.setType(pd.getProductType());
                    vo.setPrice(Arith.div(pd.getPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));
                    vo.setSellCount(pd.getSellCount());
                    if(pd.getMarketPrice()!=null){
                        vo.setMarketPrice(Arith.div(pd.getMarketPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));
                    }
                    vo.setStartTime(pd.getStartTime().getTime());
                    vo.setEndTime(pd.getEndTime().getTime());
                    if(vo.getStartTime()>(new Date()).getTime()){
                       vo.setIsStart(false); 
                    } else {
                        vo.setIsStart(true); 
                    }
                    if(vo.getEndTime()>(new Date()).getTime()){
                        vo.setIsEnd(false); 
                    } else {
                        vo.setIsEnd(true); 
                    }
                    vo.setIsSeckill(pd.getIsSeckill());
                    vo.setFullName(pd.getFullName());
                    vo.setBriefIntroduction(pd.getBriefIntroduction());
                    if(pd.getStore()!=null){
                        vo.setStore(pd.getStore());
                    }
                    if(pd.getImageInfo()!=null && pd.getImageInfo().size()>0){
                        ProductImage imagepo = pd.getImageInfo().get(0);
                        vo.setSmallImgUrl(imagepo.getThumbnail());
                    }
                    vo.setServerTime(now);
                    //秒杀商品
                    if(!SeckillFlagEnum.notSeckill.getCode().equals(vo.getIsSeckill())){
                        //获取秒杀商品秒杀开始时间和秒杀结束时间
                        Map<String, String> hash = redis.getMap(RedisKeyUtil.cbbank_seckill_product_client_id_product_id(vo.getClientId(), vo.getProductId()));
                        if(hash!=null){
                            if(Checker.isNotEmpty(hash.get("start_time"))){//秒杀开始时间
                                vo.setSecStartTime(Long.valueOf(hash.get("start_time")));
                            }
                            if(Checker.isNotEmpty(hash.get("end_time"))){//秒杀结束时间
                                vo.setSecEndTime(Long.valueOf(hash.get("end_time")));
                            }
                        }
                        //获取秒杀库存库存量
                        String secSotreStr = redis.getString(RedisKeyUtil.cbbank_seckill_product_store_client_id_product_id(vo.getClientId(), vo.getProductId()));
                        if(NumberUtils.isNumber(secSotreStr)){
                            vo.setSecStore(Integer.parseInt(secSotreStr));
                        }
                    }
                    //vip价格 是否参与VIP规则
                    if(pd.getVipPrice()!=null && pd.getVipPrice()>0){
                        vo.setVipPrice(Arith.div(pd.getVipPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));
                        vo.setIsVip(true);
                    } else {
                        vo.setIsVip(false);
                    }
                    vo.setIsPoint(false);
                    List<ProductActivitiesInfo> activitiesPos = pd.getActivitiesInfo();
                    if(activitiesPos!=null && activitiesPos.size()>0){
                        //活动表缓存信息
                        ProductActivitiesInfo activitiesPo = activitiesPos.get(0);
                        String ackey = RedisKeyUtil.cbbank_activities_client_id_activities_id(product.getClientId(),activitiesPo.getActivitiesId()) ;
                        Map<String,String> acRedisMap = redis.getMap(ackey);
                        if(acRedisMap!=null && acRedisMap.size()>0){
                            if(String.valueOf(ActivitiesStatus.yes.getCode()).equals(acRedisMap.get("status"))){
                                vo.setIsPoint(true);
                            }
                        }
                    }
                    productBriefVoList.add(vo);
                }
            }
            productPageVo.setProductBriefVoList(productBriefVoList);
            if(mongoPage.getPageCount()>mongoPage.getPageNumber()){
                pageVo.setHasNext(true);
            } else {
                pageVo.setHasNext(false);
            }
            pageVo.setPageCount(mongoPage.getPageCount());
            pageVo.setTotalCount(mongoPage.getTotalCount());
            productPageVo.setPage(pageVo);
        } catch (Exception e) {
            LogCvt.error("VIP预售推荐列表失败，原因:" + e.getMessage(),e); 
        }
        return productPageVo;
    }
    
    /**
     * 特惠商品推荐列表，特惠商品列表
     */
    @Override
    public GroupProductPageVoRes queryGroupProducts(QueryProductFilterVo filterVo,
			List<FiledSort> filedSorts, PageVo pageVo) throws TException {
        LogCvt.debug("time:"+DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT5, new Date())+"特惠商品推荐列表,特惠商品列表参数QueryProductFilterVo:"+JSON.toJSONString(filterVo)+"pageVo:"+JSON.toJSONString(pageVo));
        GroupProductPageVoRes groupProductPageVo = new GroupProductPageVoRes();//返回变量
        //转换成vo
        List<GroupProductOutletVo> productOutletVos = new ArrayList<GroupProductOutletVo>();//5个就够了
        
        final String clientId = ObjectUtils.toString(filterVo.getClientId(), "");
        
        if(Checker.isNotEmpty(clientId)){
            try{
                
                List<OutletDetailSimpleVo> groupProductOutlets = new ArrayList<OutletDetailSimpleVo>();//5个就够了
                Map<String,List<ProductDetail>> groupProductMap = new HashMap<String,List<ProductDetail>>();//5个就够了
                
                int nextNum = this.findConditionGroupProductOutlets(pageVo, filterVo, groupProductOutlets, groupProductMap);
                if(nextNum>0){
                    pageVo.setHasNext(true);
                } else {
                    pageVo.setHasNext(false);
                }
                
                //组装返回的数据
                Long now = new Date().getTime();
                RedisManager redis = new RedisManager();
                
                List<ProductDetail> pds = null;
                GroupProductOutletVo gpOutletVo = null;//商户门店信息
                List<GroupProductVo> groupProductVos = null;//商户门店下的特惠商品信息集合，最多2个
                GroupProductVo gpVo = null;//商户门店下的特惠商品信息
                for(OutletDetailSimpleVo groupProductOutletVo : groupProductOutlets){
                    pds = groupProductMap.get(groupProductOutletVo.getMerchantId());
                    if(pds!=null && pds.size()>0){
                        gpOutletVo = new GroupProductOutletVo();
                        gpOutletVo.setClientId(clientId);
                        gpOutletVo.setOutletId(groupProductOutletVo.getOutletId());//门店Id
                        gpOutletVo.setOutletName(groupProductOutletVo.getOutletName());//门店名称
                        gpOutletVo.setAddress(groupProductOutletVo.getAddress());//地址
                        if(Checker.isNotEmpty(groupProductOutletVo.getStarLevel())){
                            gpOutletVo.setStarLevel(groupProductOutletVo.getStarLevel());//星评
                        }
                        gpOutletVo.setDistance(groupProductOutletVo.getDis());//计算出的距离
                        gpOutletVo.setMerchantId(groupProductOutletVo.getMerchantId());//商户id
                        gpOutletVo.setMerchantName(groupProductOutletVo.getMerchantName());//商户名称
                        
                        groupProductVos = new ArrayList<GroupProductVo>();
                        for(ProductDetail pd : pds){
                            gpVo = new GroupProductVo();
                            gpVo.setProductId(pd.getId());
                            gpVo.setName(pd.getName());
                            gpVo.setFullName(pd.getFullName());
                            gpVo.setPrice(Arith.div(pd.getPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));
                            if(pd.getMarketPrice()!=null){
                                gpVo.setMarketPrice(Arith.div(pd.getMarketPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));
                            }
                            gpVo.setSellCount(pd.getSellCount());
                            gpVo.setBriefIntroduction(pd.getBriefIntroduction());
                            if(pd.getImageInfo()!=null && pd.getImageInfo().size()>0){
                                ProductImage imagepo = pd.getImageInfo().get(0);
                                gpVo.setSmallImgUrl(imagepo.getThumbnail());
                            }
                            gpVo.setStartTime(pd.getStartTime().getTime());
                            gpVo.setEndTime(pd.getEndTime().getTime());
                            gpVo.setIsSeckill(pd.getIsSeckill());
                            gpVo.setServerTime(now);
                            //秒杀商品
                            if(!SeckillFlagEnum.notSeckill.getCode().equals(pd.getIsSeckill())){
                                //获取秒杀商品秒杀开始时间和秒杀结束时间
                                Map<String, String> hash = redis.getMap(RedisKeyUtil.cbbank_seckill_product_client_id_product_id(pd.getClientId(), pd.getId()));
                                if(hash!=null){
                                    if(Checker.isNotEmpty(hash.get("start_time"))){//秒杀开始时间
                                        gpVo.setSecStartTime(Long.valueOf(hash.get("start_time")));
                                    }
                                    if(Checker.isNotEmpty(hash.get("end_time"))){//秒杀结束时间
                                        gpVo.setSecEndTime(Long.valueOf(hash.get("end_time")));
                                    }
                                }
                            }
                            
                            groupProductVos.add(gpVo);
                        }
                        gpOutletVo.setGroupProductVos(groupProductVos);
                        productOutletVos.add(gpOutletVo);
                    }
                }
                
            } catch (Exception e) {
                LogCvt.error("特惠商品推荐列表，特惠商品列表失败，原因:" + e.getMessage(),e); 
            }
        }
        groupProductPageVo.setProductOutletVos(productOutletVos);
        groupProductPageVo.setPage(pageVo);
        return groupProductPageVo;
    }
    

    /**
     * 查询满足条件的商户门店下的特惠商品列表--有商品名称和商品分类查询条件 需要查全部的门店商户信息
     * @param pageVo
     * @param filterVo
     * @param pageSize
     * @param groupProductOutlets
     * @param groupProductMap
     * @return int
     */
    private int findConditionGroupProductOutlets(PageVo pageVo,QueryProductFilterVo filterVo,
            List<OutletDetailSimpleVo> groupProductOutlets,Map<String,List<ProductDetail>> groupProductMap){
        try {
        	final String clientId = ObjectUtils.toString(filterVo.getClientId(), "");
        	final String productName = ObjectUtils.toString(filterVo.getProductName(), "");
        	
        	//符合条件的商户门店查询条件的商户
            String groupMerchantKey = RedisKeyUtil.cbbank_product_merchant_client_id_lat_con(
            		clientId, filterVo.getAreaId(), filterVo.getLatitude(), filterVo.getLongitude(), filterVo.getDistance());
            
            //符合条件的商户门店查询条件的商户 和符合商户商品查询条件的商户 的交集key
            String unionGroupProductkey = RedisKeyUtil.cbbank_group_product_merchant_client_id_category_id_lat_con_product_name_dis(
            		clientId, filterVo.getAreaId(), filterVo.getProductCategoryId(), filterVo.getLatitude(), filterVo.getLongitude(), productName, filterVo.getDistance());
            
            int start = (pageVo.getPageNumber()-1)*pageVo.getPageSize();
	        int end = pageVo.getPageNumber()*pageVo.getPageSize()-1;
            
        	Set<String> merchatIds = productLogic.findProductMerchantIds(pageVo, filterVo);
            if(merchatIds!=null && merchatIds.size()>0){
                List<String> list = new ArrayList<String> ();  
                for(String mid : merchatIds){
                	list.add(mid); 
                }
                MongoPage mPage = new MongoPage(1, 2);//特惠商品列表每个商户查询2条商品就可以了
                Map<String,List<ProductDetail>> map = productLogic.queryGroupProducts(mPage, filterVo, list, pageVo.getPageSize());//根据商户门店查询该商户门店下的特惠商品
                if(map!=null){
                    for(String merchatId : merchatIds){
                        OutletDetailSimpleVo outletDetailSimpleVo = ProductRedis.get_cbbank_product_merchant_outlet_h5_redis(groupMerchantKey+":"+merchatId);
                        if(outletDetailSimpleVo==null){
                        	filterVo.setMerchantId(merchatId);
                        	outletDetailSimpleVo = this.getOutletDetailSimpleVo(filterVo);
                        }
                        groupProductMap.put(outletDetailSimpleVo.getMerchantId(), map.get(outletDetailSimpleVo.getMerchantId()));
                        groupProductOutlets.add(outletDetailSimpleVo);
                    }
                }
            }
            LogCvt.debug("个人h5查询特惠商品交集商户id:start--end+1:"+JSON.toJSONString(ProductRedis.get_cbbank_product_merchant_h5_redis(unionGroupProductkey, start, end+1)));
            if(merchatIds!=null && merchatIds.size()<pageVo.getPageSize()){
                return 0;
            }
            Set<String> nextMerchatIds = ProductRedis.get_cbbank_product_merchant_h5_redis(unionGroupProductkey, end+1, end+1);
            
            return nextMerchatIds==null?0:nextMerchatIds.size();
            
        } catch (Exception e) {
            LogCvt.error("有商品名称或商品分类查询条件的特惠商品推荐列表，特惠商品列表 查询门店信息和门店下商品信息失败，原因:" + e.getMessage(),e);
        }
        return 0;
    }
    
    
    
	/* (non-Javadoc)
	 * @see com.froad.thrift.service.ProductService.Iface#queryMerchantProducts(com.froad.thrift.vo.QueryProductFilterVo, java.util.List, com.froad.thrift.vo.PageVo)
	 */
	@Override
	public MerchantProductPageVoRes queryMerchantProducts(
			QueryProductFilterVo filterVo, List<FiledSort> filedSorts, PageVo pageVo)
			throws TException {
		LogCvt.debug("time:"+DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT5, new Date())+"根据商户id查询特惠商品列表参数QueryProductFilterVo:"+JSON.toJSONString(filterVo)+"pageVo:"+JSON.toJSONString(pageVo));
		
		MerchantProductPageVoRes merchantProductPageVo = new MerchantProductPageVoRes();//返回变量
		GroupProductOutletVo gpOutletVo = new GroupProductOutletVo();
		List<GroupProductVo> groupProductVos = new ArrayList<GroupProductVo>();
		
        if(Checker.isNotEmpty(filterVo.getMerchantId())){
            try{
            	final String clientId = ObjectUtils.toString(filterVo.getClientId(), "");
            	
            	OutletDetailSimpleVo groupProductOutletVo = null;
            	/*设置缓存有效时间*/
            	//商户门店缓存时间
                int merchantTime = Integer.valueOf(GoodsConstants.GOODS_GROUP_REDIS_STEADY_LOCK_TIME);//团购商品查询条件不常变缓存有效时间,30代表30分钟;
                
                Long areaId = filterVo.getAreaId();
                
                //符合条件的商户门店查询条件的商户
                String groupMerchantKey = RedisKeyUtil.cbbank_group_product_merchant_outlet_merchant_id_client_id_lat_con(
                		filterVo.getMerchantId(), clientId, areaId, filterVo.getLatitude(), filterVo.getLongitude());
                
                LogCvt.debug("个人h5根据商户id查询特惠商品的商户门店的商户key:groupMerchantKey[" + groupMerchantKey + "]");
                long groupMerchantLockResult = ProductRedis.setLock(groupMerchantKey, merchantTime);// 将 key 的值设为 value ，当且仅当 key 不存在。 设置成功，返回 1 。设置失败，返回 0 。
                if(groupMerchantLockResult == 1) {// key不存在   此次设置成功   说明原来没设置过这个值   需要查询全部的商户门店，调用商户模块的门店接口
                	long startTime = System.currentTimeMillis();
                	LogCvt.debug("个人h5根据商户id查询特惠商品列表时查询商户门店信息是第一次查询, 查询该商户id的商户门店信息，调用门店接口,数据[开始...]时间戳=" + startTime);
                    
                    //商户门店信息
                	groupProductOutletVo = getOutletDetailSimpleVo(filterVo);
                    //将商户门店信息放redis里面
                	int cacheTimeout = merchantTime * 60;// 设置超时时间 为 time分钟
                    ProductRedis.set_cbbank_merchant_outlet_h5_redis(groupMerchantKey, groupProductOutletVo, cacheTimeout);
                    
                    long endTime = System.currentTimeMillis();
                    LogCvt.debug("个人h5根据商户id查询特惠商品列表时查询商户门店信息数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");
                } else {
                	groupProductOutletVo = ProductRedis.get_cbbank_product_merchant_outlet_h5_redis(groupMerchantKey);
                	if(groupProductOutletVo==null) {
                		groupProductOutletVo = getOutletDetailSimpleVo(filterVo);
                	}
                }
                
                MongoPage mPage = new MongoPage();//mongo查询分页参数
                mPage.setPageNumber(pageVo.getPageNumber());
                mPage.setPageSize(pageVo.getPageSize());
                
                List<String> merchatIds = new ArrayList<String> ();  
            	merchatIds.add(filterVo.getMerchantId());
                
                Map<String,List<ProductDetail>> map = productLogic.queryGroupProducts(mPage, filterVo, merchatIds, 1);//根据商户门店查询该商户门店下的特惠商品
                List<ProductDetail> pds = null;
                if(map!=null){
                	pds = map.get(filterVo.getMerchantId());
                }
                if(pds!=null && pds.size()>0){
                	//组装返回的数据
                    Long now = new Date().getTime();
                    RedisManager redis = new RedisManager();
                    
                    gpOutletVo.setClientId(clientId);
                    gpOutletVo.setOutletId(groupProductOutletVo.getOutletId());//门店Id
                    gpOutletVo.setOutletName(groupProductOutletVo.getOutletName());//门店名称
                    gpOutletVo.setAddress(groupProductOutletVo.getAddress());//地址
                    if(Checker.isNotEmpty(groupProductOutletVo.getStarLevel())){
                        gpOutletVo.setStarLevel(groupProductOutletVo.getStarLevel());//星评
                    }
                    gpOutletVo.setDistance(groupProductOutletVo.getDis());//计算出的距离
                    gpOutletVo.setMerchantId(groupProductOutletVo.getMerchantId());//商户id
                    gpOutletVo.setMerchantName(groupProductOutletVo.getMerchantName());//商户名称
                    
                    GroupProductVo gpVo = null;
                    for(ProductDetail pd : pds){
                        gpVo = new GroupProductVo();
                        gpVo.setProductId(pd.getId());
                        gpVo.setName(pd.getName());
                        gpVo.setFullName(pd.getFullName());
                        gpVo.setPrice(Arith.div(pd.getPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));
                        if(pd.getMarketPrice()!=null){
                            gpVo.setMarketPrice(Arith.div(pd.getMarketPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));
                        }
                        gpVo.setSellCount(pd.getSellCount());
                        gpVo.setBriefIntroduction(pd.getBriefIntroduction());
                        if(pd.getImageInfo()!=null && pd.getImageInfo().size()>0){
                            ProductImage imagepo = pd.getImageInfo().get(0);
                            gpVo.setSmallImgUrl(imagepo.getThumbnail());
                        }
                        gpVo.setStartTime(pd.getStartTime().getTime());
                        gpVo.setEndTime(pd.getEndTime().getTime());
                        gpVo.setIsSeckill(pd.getIsSeckill());
                        gpVo.setServerTime(now);
                        //秒杀商品
                        if(!SeckillFlagEnum.notSeckill.getCode().equals(pd.getIsSeckill())){
                            //获取秒杀商品秒杀开始时间和秒杀结束时间
                            Map<String, String> hash = redis.getMap(RedisKeyUtil.cbbank_seckill_product_client_id_product_id(pd.getClientId(), pd.getId()));
                            if(hash!=null){
                                if(Checker.isNotEmpty(hash.get("start_time"))){//秒杀开始时间
                                    gpVo.setSecStartTime(Long.valueOf(hash.get("start_time")));
                                }
                                if(Checker.isNotEmpty(hash.get("end_time"))){//秒杀结束时间
                                    gpVo.setSecEndTime(Long.valueOf(hash.get("end_time")));
                                }
                            }
                        }
                        groupProductVos.add(gpVo);
                    }
                    for(String key : map.keySet()){
                    	if(key.indexOf("mPagePageCount_")!=-1){//总页数
                    		String[] pageCounts = key.split("_");
                    		pageVo.setPageCount(Integer.valueOf(pageCounts[1]));
                    		
                    	} else if(key.indexOf("mPageTotalCount_")!=-1){//总数量
                    		String[] totalCounts = key.split("_");
                    		pageVo.setTotalCount(Integer.valueOf(totalCounts[1]));
                    	}
                    	
                    }
                }
            } catch (Exception e) {
                LogCvt.error("特惠商品推荐列表，特惠商品列表失败，原因:" + e.getMessage(),e); 
            }
        }
        if(pageVo.getPageNumber()<pageVo.getPageCount()){
        	pageVo.setHasNext(true);
        } else {
        	pageVo.setHasNext(false);
        }
        merchantProductPageVo.setPage(pageVo);
        merchantProductPageVo.setMerchantOutletVo(gpOutletVo);
        merchantProductPageVo.setGroupProductVos(groupProductVos);
        
		return merchantProductPageVo;
	}
	
	/**
	 * 
	 * getOutletDetailSimpleVo:(根据商户id查询商户门店信息).
	 *
	 * @author wangyan
	 * 2015-8-10 上午10:23:26
	 * @param filterVo
	 * @return
	 * @throws Exception
	 *
	 */
	private OutletDetailSimpleVo getOutletDetailSimpleVo(QueryProductFilterVo filterVo) throws Exception {
		OutletDetailSimpleVo groupProductOutletVo = new OutletDetailSimpleVo();
		PageVo mpageVo = new PageVo();
        mpageVo.setPageNumber(1);
        mpageVo.setPageSize(1);
        MerchantLogic merchantLogic  = new MerchantLogicImpl();
        OutletDetailSimplePageVoRes outletPageVo = merchantLogic.queryMerchantOutlets(filterVo, mpageVo, 0);
        
        //商户门店信息
        List<OutletDetailSimpleVo> outletDetailSimpleVos = outletPageVo!=null?outletPageVo.getOutletDetailSimpleVoList():null;
        if(outletDetailSimpleVos!=null && outletDetailSimpleVos.size()>0){
        	groupProductOutletVo = outletDetailSimpleVos.get(0);
        }
        return groupProductOutletVo;
	}
	
    /**
	 * 批量获取商品二维码信息
	 * @param ProductVo 
	 * @param list
	 * @return list<ProductVo>
	 */
	@Override
	public List<ProductVo> findUrlCodeByList(List<ProductVo> productList)
			throws TException {
		List<ProductVo> products=new ArrayList<ProductVo>();
		if(productList.size()>0){
			for(ProductVo pro:productList){
				ProductVo vo=new ProductVo();
				Product product=productLogic.findUrlCodeById(pro.getProductId());
				ProductBeanUtil.copyPropertiesScale(vo, product, GoodsConstants.DOUBLE_INTGER_CHANGE);
				products.add(vo);
			}
			
		}
		return products;
	}
	
    /**
	 * 下载商品
	 * @param productSearchVo 
	 * @param type
	 * @return ExportResultRes
	 */
	public ExportResultRes downProducts(ProductSearchVo productSearchVo, String type)
			throws TException {
		long startTime = System.currentTimeMillis();
		LogCvt.debug("下载商品downProducts:"+"参数ProductSearchVo:"+JSON.toJSONString(productSearchVo)+",type:"+type);
		
		ExportResultRes res = new ExportResultRes();
		ResultVo resultVo = new ResultVo();
		
        try{
        	int everySheetNum = Integer.valueOf(GoodsConstants.GOODS_DOWN_ROWNUM);//下载商品每个sheet有多少行数 默认5万
        	int pageNum = 1;
        	int pageSize = Integer.valueOf(GoodsConstants.GOODS_DOWN_SEARCH_COUNT);;//2万条一查询
            //分页查询设置
            Page<ProductListInfo> page = new Page<ProductListInfo>();
            page.setPageNumber(1);
            page.setPageSize(pageSize);
            
            ProductFilter product = new ProductFilter();
            
            //设置排序字段
            LinkedHashMap<String, String> sortOrderFileds = new LinkedHashMap<String, String>();
            sortOrderFileds.put("create_time", "desc");
            product.setOrderFileds(sortOrderFileds);
            
            //设置查询条件
            product.setClientId(productSearchVo.getClientId());//客户端ID
            product.setOrgCode(productSearchVo.getOrgCode());//机构号
            product.setType(type);
            product.setAuditState(productSearchVo.getAuditState());//审核状态
            product.setFilterStatuts(ProductStatus.isDeleted.getCode());//已经删除的不查询出来
    		product.setFilterAuditState(ProductAuditState.noCommit.getCode());
    		product.setIsMarketable(productSearchVo.getIsMarketable());//上下架状态
    		product.setMerchantName(productSearchVo.getMerchantName());//商户名称
    		product.setName(productSearchVo.getProductName());//商品名称
    		
    		if(productSearchVo.isSetAuditStartTime() && productSearchVo.getAuditStartTime()>0) {
    			product.setAuditStartTime(new Date(productSearchVo.getAuditStartTime()));//审核开始时间
    		}
    		if(productSearchVo.isSetAuditEndTime() && productSearchVo.getAuditEndTime()>0) {
    			product.setAuditEndTime(new Date(productSearchVo.getAuditEndTime()));//审核结束时间
    		}
    		if(productSearchVo.isSetPointStart()) {
    			product.setPriceStart(Arith.mul(productSearchVo.getPointStart(),GoodsConstants.DOUBLE_INTGER_CHANGE));//积分开始值
    		}
    		if(productSearchVo.isSetPointEnd()) {
    			product.setPriceEnd(Arith.mul(productSearchVo.getPointEnd(),GoodsConstants.DOUBLE_INTGER_CHANGE));//积分截止值
    		}
    		String url = null;
			DownUtils down=new DownUtils();
			//第一步：根据条件查询要导出的所有数据
			ExcelWriter excelWriter = new ExcelWriter(everySheetNum);
			
            List<ProductListInfo> products = productLogic.findProductListByPage(page,product);
            if(products==null || products.size()==0){
				//第二步：将excel列头放入List<String>   将数据放入List<List<String>> 
				//生成一个sheet
				long startTime1 = System.currentTimeMillis();
				if(ProductType.group.getCode().equals(type)){
    	        	excelWriter.write(down.downGroupProductHeader(), down.downGroupProductData(null), "团购商品列表","团购商品列表");
    	        }else if(ProductType.presell.getCode().equals(type)){
    	        	excelWriter.write(down.downPresellProductHeader(), down.downPresellProductData(null), "预售商品列表","预售商品列表");
    	        }else if(ProductType.special.getCode().equals(type)){
    	        	excelWriter.write(down.downSpecialProductHeader(), down.downSpecialProductData(null), "名优特惠商品列表","名优特惠商品列表");
    	        }else if(ProductType.dotGift.getCode().equals(type)){
    	        	excelWriter.write(down.downDotGiftProductHeader(), down.downDotGiftProductData(null), "线下积分礼品列表","线下积分礼品列表");
    	        }
				long endTime1 = System.currentTimeMillis();
    	        LogCvt.debug("下载商品写ftp耗时:"+(endTime1 - startTime1)+"--startTime1:"+startTime1+"---endTime1:"+endTime1);
    		} else {
    			List<ProductListInfo> sheetProducts = new ArrayList<ProductListInfo>();
    			sheetProducts.addAll(products);
    			
    			int totalCount = page.getTotalCount();//总条数
    			int conCount = everySheetNum/pageSize+(everySheetNum%pageSize>0?1:0);//组成一个sheet需要查询mysql的次数
    			
    			if(everySheetNum>totalCount){
    				conCount = totalCount/pageSize+(totalCount%pageSize>0?1:0);//组成一个sheet需要查询mysql的次数
    			}
    			int sheetTotalNum = totalCount/everySheetNum+(totalCount%everySheetNum>0?1:0);//一个excel文件sheet总数量
    			
    			for(int sheetNum=0;sheetNum<sheetTotalNum;sheetNum++){
    				if(sheetNum>0){
    					sheetProducts = new ArrayList<ProductListInfo>();
    				}
    				for(int count=(sheetNum==0?1:0);count<conCount;count++){
        				pageNum = pageNum+1;
        				page.setPageNumber(pageNum);
    	    			products = productLogic.findProductListByPage(page,product);
    	    			if(products!=null && products.size()>0){//导出商品列表
    	    				sheetProducts.addAll(products);
        				}
        			}
    				//第二步：将excel列头放入List<String>   将数据放入List<List<String>> 
    				//生成一个sheet
    				long startTime1 = System.currentTimeMillis();
    				if(ProductType.group.getCode().equals(type)){
	    	        	excelWriter.write(down.downGroupProductHeader(), down.downGroupProductData(sheetProducts), "团购商品列表","团购商品列表");
	    	        }else if(ProductType.presell.getCode().equals(type)){
	    	        	excelWriter.write(down.downPresellProductHeader(), down.downPresellProductData(sheetProducts), "预售商品列表","预售商品列表");
	    	        }else if(ProductType.special.getCode().equals(type)){
	    	        	excelWriter.write(down.downSpecialProductHeader(), down.downSpecialProductData(sheetProducts), "名优特惠商品列表","名优特惠商品列表");
	    	        }else if(ProductType.dotGift.getCode().equals(type)){
	    	        	excelWriter.write(down.downDotGiftProductHeader(), down.downDotGiftProductData(sheetProducts), "线下积分礼品列表","线下积分礼品列表");
	    	        }
    				long endTime1 = System.currentTimeMillis();
        	        LogCvt.debug("下载商品写ftp耗时:"+(endTime1 - startTime1)+"--startTime1:"+startTime1+"---endTime1:"+endTime1);
    			}
    		}
            
            //第三步：调用导出共通方法，返回url。url就是生成的Excel在文件服务器上的相对路径。
			long startTime2 = System.currentTimeMillis();
			url = excelWriter.getUrl();
			long endTime2 = System.currentTimeMillis();
	        LogCvt.debug("下载商品上传ftp耗时:"+(endTime2 - startTime2)+"--startTime2:"+startTime2+"---endTime2:"+endTime2);
	        
	        
            //返回生成好的excel
			if(Checker.isNotEmpty(url)){
        		resultVo.setResultCode(ResultCode.success.getCode());
            	resultVo.setResultDesc("下载成功");
            	res.setUrl(url);
        	} else {
        		resultVo.setResultCode(ResultCode.failed.getCode());
            	resultVo.setResultDesc("下载失败");
        	}
        }catch (Exception e) {
        	resultVo.setResultCode(ResultCode.failed.getCode());
        	resultVo.setResultDesc("下载失败");
            LogCvt.error("下载商品列表异常，原因:" + e.getMessage(),e); 
        }
        long endTime = System.currentTimeMillis();
        LogCvt.debug("下载商品总耗时:"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);
		res.setResultVo(resultVo);
		return res;
	}
	
	/**
	 * 根据定位的距离查询类目营销里的特惠商品推荐列表
	 */
	@Override
	public ProductPageVoRes searchProductByType(QueryProductFilterVo filterVo,
			List<FiledSort> filedSorts, PageVo pageVo) throws TException {
		LogCvt.debug("time:"+DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT5, new Date())
				+"类目营销里的特惠商品推荐列表QueryProductFilterVo:"+JSON.toJSONString(filterVo)
				+",List<FiledSort>:"+JSON.toJSONString(filedSorts)
				+",pageVo:"+JSON.toJSONString(pageVo));
        ProductPageVoRes productPageVo = new ProductPageVoRes();//返回变量
        try {
        	//目前只支持一个类型的排序
            if(filedSorts!=null && filedSorts.size()==1){
            	FiledSort fs = filedSorts.get(0);
            	
            	//-距我最近排序查询 ：类目商品按照所在门店距离由近至远进行排序，如距离相同按照商品销售数量由高到低排序，如销售数量相同则按商品上架时间由近至远排序
            	//销量优先：在列表中，类目商品按照商品销售数量由高到低进行排序，如商品销售数量相同则按照商品折扣由高到低排序，如商品折扣相同则按照商品上架时间由近至远排序。
            	//价格最低：在列表中，类目商品按照商品价格由低至高进行排序，如商品价格相同则按照销售数量由高到低排序。如销售数量相同则按照商品上架时间由近至远排序。
            	//折扣最大：在列表中，类目商品按照商品折扣率（商品售价/商品原价）由低到高进行排序，如商品折扣率相同则按照销量由高至低排序，如销量相同则按照商品上架时间由近至远排序。
            	List<ProductDetailSimple> productDetailSimples = null;
            	if(ProductQuerySort.distance.getCode().equals(fs.getSortName())){//距离我最近排序
                    productDetailSimples = productLogic.queryDistanceGroupGoods(pageVo, filterVo, fs);
            	} else if(ProductQuerySort.sellCount.getCode().equals(fs.getSortName())
            			|| ProductQuerySort.price.getCode().equals(fs.getSortName())
            			|| ProductQuerySort.disCount.getCode().equals(fs.getSortName())){//销量排序//价格排序//折扣排序
            		productDetailSimples = productLogic.queryGeneralGroupGoods(pageVo, filterVo, fs);
            	}
            	if(productDetailSimples!=null && productDetailSimples.size()>0){
            		Map<String, OutletDetailSimpleVo> odsVoMap = productLogic.queryGroupGoodMerhantOutlets(productDetailSimples, filterVo, pageVo);
            		
            		List<GroupProductVo> productVos = new ArrayList<GroupProductVo>();
                	//组装返回的数据
                    Long now = new Date().getTime();
                    RedisManager redis = new RedisManager();
                    CommonLogic com = new CommonLogicImpl();
                    OutletDetailSimpleVo odsVo = null;
                    GroupProductVo gpVo = null;//商户门店下的特惠商品信息
                    for(ProductDetailSimple productDetailSimple : productDetailSimples){
                    	gpVo = new GroupProductVo();
                        gpVo.setProductId(productDetailSimple.getProductId());
                        gpVo.setName(productDetailSimple.getName());
                        gpVo.setFullName(productDetailSimple.getFullName());
                        gpVo.setPrice(productDetailSimple.getPrice());
                        gpVo.setMarketPrice(productDetailSimple.getMarketPrice());
                        gpVo.setSellCount(productDetailSimple.getSellCount());
                        gpVo.setBriefIntroduction(productDetailSimple.getBriefIntroduction());
                        gpVo.setSmallImgUrl(productDetailSimple.getSmallImgUrl());
                        gpVo.setStartTime(productDetailSimple.getStartTime());
                        gpVo.setEndTime(productDetailSimple.getEndTime());
                        gpVo.setIsSeckill(productDetailSimple.getIsSeckill());
                        gpVo.setServerTime(now);
                        //秒杀商品
                        if(!SeckillFlagEnum.notSeckill.getCode().equals(productDetailSimple.getIsSeckill())){
                            //获取秒杀商品秒杀开始时间和秒杀结束时间
                            Map<String, String> hash = redis.getMap(RedisKeyUtil.cbbank_seckill_product_client_id_product_id(productDetailSimple.getClientId(), productDetailSimple.getProductId()));
                            if(hash!=null){
                                if(Checker.isNotEmpty(hash.get("start_time"))){//秒杀开始时间
                                    gpVo.setSecStartTime(Long.valueOf(hash.get("start_time")));
                                }
                                if(Checker.isNotEmpty(hash.get("end_time"))){//秒杀结束时间
                                    gpVo.setSecEndTime(Long.valueOf(hash.get("end_time")));
                                }
                            }
                        }
                        Map<String,String> pm = com.getProductRedis(productDetailSimple.getClientId(), productDetailSimple.getMerchantId(), productDetailSimple.getProductId());
                        if(pm!=null && pm.get("point")!=null && !"".equals(pm.get("point"))){
                        	gpVo.setStarLevel(Integer.valueOf(pm.get("point")));//评论星级
                        }
                        odsVo = odsVoMap==null?null:odsVoMap.get(productDetailSimple.getMerchantId());
                        if(odsVo!=null){
                        	gpVo.setAddress(odsVo.getAddress());//门店地址
                        	gpVo.setDistance(odsVo.getDis());//计算出的距离
                        }
                        productVos.add(gpVo);
                    }
                    productPageVo.setProductVos(productVos);
                	
                    //查询类目营销里的特惠商品列表总条数
                    ProductDetialMongo productDetialMongo = new ProductDetialMongo();
    				int totalCount = productDetialMongo.queryGroupProductCount(filterVo);
    				pageVo.setTotalCount(totalCount);
    				int pageCount = totalCount/pageVo.getPageSize()+(totalCount%pageVo.getPageSize()>0?1:0);
    				pageVo.setPageCount(pageCount);
                    if(pageVo.getPageCount()>pageVo.getPageNumber()){
                        pageVo.setHasNext(true);
                    } else {
                        pageVo.setHasNext(false);
                    }
            	} else {
            		pageVo.setTotalCount(0);
    				pageVo.setPageCount(0);
            		pageVo.setHasNext(false);
            	}
            	productPageVo.setPage(pageVo);
            }
                
        } catch (Exception e) {
        	LogCvt.error("个人h5查询营销类目里的其他排序的特惠商品列表失败, 原因:"+ e.getMessage(), e);
		}
        return productPageVo;
	}
	
	@Override
	public ResultVo updateGroupProductByOutlet(String clientId, String type, OutletVo outletVo) throws TException {
		LogCvt.debug("商户的门店有新加，修改，或删除，需要更新商品关联的门店所在的区域,参数:clientId:"+clientId+",type:"+type+",OutletVo:"+JSON.toJSONString(outletVo));
		ResultVo resultVo = new ResultVo();
		try{
			if(Checker.isEmpty(type)){
				resultVo.setResultCode(ResultCode.failed.getCode());
	            resultVo.setResultDesc("门店操作类型不可以为空");
	            return resultVo;
			}
			if(!type.equals("1") && !type.equals("2") && !type.equals("3")){
				resultVo.setResultCode(ResultCode.failed.getCode());
	            resultVo.setResultDesc("门店操作类型只能有新加，修改，或删除");
	            return resultVo;
			}
			if(Checker.isEmpty(outletVo.getOutletId())){
				resultVo.setResultCode(ResultCode.failed.getCode());
	            resultVo.setResultDesc("门店id不可以为空");
	            return resultVo;
			}
			if(type.equals("1") || type.equals("2")){
				if(Checker.isEmpty(outletVo.getMerchantId())){
					resultVo.setResultCode(ResultCode.failed.getCode());
		            resultVo.setResultDesc("商户id不可以为空");
				}
			}
			if(type.equals("1")){
				if(outletVo.getAreaId()==0){
					resultVo.setResultCode(ResultCode.failed.getCode());
		            resultVo.setResultDesc("门店所在区域不可以为空");
		            return resultVo;
				}
			}
			boolean result = productLogic.updateGroupProductAreaOutlet(type, outletVo);
			if(result){
				resultVo.setResultCode(ResultCode.success.getCode());
	            resultVo.setResultDesc("更新商品关联的门店所在的区域成功");
			} else {
				resultVo.setResultCode(ResultCode.failed.getCode());
	            resultVo.setResultDesc("更新商品关联的门店所在的区域失败");
			}
		} catch (Exception e) {
			LogCvt.error("更新商品关联的门店所在的区域失败, 原因:" + e.getMessage(), e);
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("更新商品关联的门店所在的区域失败");
        }
        return resultVo;
	}
	
	@Override
	public ResultVo updateHisGroupGoodsOutlets() throws TException {
		ResultVo resultVo = new ResultVo();
		try{
			productLogic.updateHisGroupGoodsOutlets();
			resultVo.setResultCode(ResultCode.success.getCode());
            resultVo.setResultDesc("更新历史商品数据关联的门店所在的区域成功");
		} catch (Exception e) {
			LogCvt.error("更新历史商品数据关联的门店所在的区域失败, 原因:" + e.getMessage(), e);
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("更新历史商品数据关联的门店所在的区域失败");
        }
        return resultVo;
	}
	
	@Override
	public ResultVo autoOffShelfProductSaleEnd() throws TException {
        ResultVo resultVo = new ResultVo();
        Result result = productLogic.autoOffShelfProductSaleEnd();
        ProductBeanUtil.copyProperties(resultVo, result);
        return  resultVo;
	}
	
	@Override
	public AddProductVoRes addGoods(OriginVo originVo, GoodsInfoVo goodsInfoVo)
			throws TException {
    	long startTime = new Date().getTime();
        LogCvt.debug("添加精品商城商品参数:"+"originVo:"+JSON.toJSONString(originVo)+",GoodsInfoVo:"+JSON.toJSONString(goodsInfoVo));
        AddProductVoRes resVo = new AddProductVoRes();
        ResultVo result = new ResultVo();
        resVo.setProductId(null);
        try {
        	//添加操作日志记录
			originVo.setDescription("添加精品商城商品信息");
			LogUtils.addLog(originVo);
			
			//设置商品类型为精品商城商品
			if(goodsInfoVo.getType()==null) {
				goodsInfoVo.setType(ProductType.boutique.getCode());
			}
			//设置平台参数 是boss
			String platType = "";
			if(originVo.getPlatType()==null) {
				platType = String.valueOf(PlatTypeEnum.boss.getValue());
			} else {
				platType = String.valueOf(originVo.getPlatType().getValue());
			}
			/************验证数据的合法性 start**************/
			ResultVo validationResultVo = validationGoodsFileds(originVo,goodsInfoVo);
            if(validationResultVo!=null){
                resVo.setResult(validationResultVo);
                return resVo;
            }
	        /************验证数据的合法性 end**************/
	        
            ResultBean resultBean = productLogic.addGoods(goodsInfoVo, platType);
            if(resultBean!=null){
                result.setResultCode(resultBean.getCode());
                result.setResultDesc(resultBean.getMsg());
                resVo.setProductId((String)resultBean.getData());
            } else {
            	result.setResultCode(ResultCode.failed.getCode());
            	result.setResultDesc("添加精品商城商品失败");
            }
            resVo.setResult(result);
            return resVo;
            
        } catch (Exception e) {
            LogCvt.error("添加精品商城商品失败, 原因:" + e.getMessage(), e);
            result.setResultCode(ResultCode.failed.getCode());
            result.setResultDesc("添加精品商城商品失败");
        }
        long endTime = new Date().getTime();
        LogCvt.debug("添加精品商城商品开始时间:"+startTime);
        LogCvt.debug("添加精品商城商品结束时间:"+endTime);
        LogCvt.debug("添加精品商城商品总用时时间(ms):"+(endTime-startTime));
        return resVo;
    }
	
	/**
	 * 验证商品基本字段.
	 * @param originVo
	 * @param goodsInfoVo
	 * @return
	 */
    private ResultVo validationGoodsFileds(OriginVo originVo, GoodsInfoVo productVo){
        ResultVo resultVo = null;
		//必须是boss的
		if(originVo.getPlatType()!=null && originVo.getPlatType().getValue()!=PlatTypeEnum.boss.getValue()){
			resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("只有boss平台可以新加精品商城商品");
            return resultVo;
        }
        if(productVo.getClientId()==null || "".equals(productVo.getClientId())){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("所属客户端不能为空");
            return resultVo;
        }
        if(productVo.getType()==null || "".equals(productVo.getType())){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品类型不能为空");
            return resultVo;
        }
        if(productVo.getMerchantId()==null || "".equals(productVo.getMerchantId())){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("供货商不能为空");
            return resultVo;
        }
        if(productVo.getCategoryId()<=0){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品分类不能为空");
            return resultVo;
        }
        if(productVo.getName()==null || "".equals(productVo.getName())){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品简称不能为空");
            return resultVo;
        }
        if(productVo.getName().length()>100){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品简称不能超过100个字符");
            return resultVo;
        }
        if(productVo.getFullName()==null || "".equals(productVo.getFullName())){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品全称不能为空");
            return resultVo;
        }
        if(productVo.getFullName()!=null && !"".equals(productVo.getFullName()) && productVo.getFullName().length()>200){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品全称不能超过200个字符");
            return resultVo;
        }
        if(productVo.getBriefIntroduction()==null || "".equals(productVo.getBriefIntroduction()) ){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品副标题不能为空");
            return resultVo;
        }
        if(productVo.getPrice()<=0.0){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商城价必填");
            return resultVo;
        }
        if(productVo.getPrice()>0.0 && Util.getDoubleDecimalNum(productVo.getPrice())>2){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商城价只能有2位有效小数");
            return resultVo;
        }
        if(productVo.getMarketPrice()<=0.0){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("市场价必填");
            return resultVo;
        }
        if(productVo.getMarketPrice()>0.0 && Util.getDoubleDecimalNum(productVo.getMarketPrice())>2){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("市场价只能有2位有效小数");
            return resultVo;
        }
        if(productVo.getVipPrice()<0.0){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("VIP价必填");
            return resultVo;
        }
        if(productVo.getVipPrice()>0.0 && Util.getDoubleDecimalNum(productVo.getVipPrice())>2){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("VIP价只能有2位有效小数");
            return resultVo;
        }
        if(productVo.getVipPrice()>0.0 && productVo.getVipPrice()>productVo.getPrice()){
        	resultVo = new ResultVo();
        	resultVo.setResultCode(ResultCode.failed.getCode());
        	resultVo.setResultDesc("VIP价必须小于商城价");
            return resultVo;
        }
        if(productVo.getMaxVip()<0){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("VIP限购数量不能为空");
            return resultVo;
        }
        if(productVo.getMaxVip()>0 && productVo.getVipPrice()<=0.0) {
        	resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("有VIP限购数量时VIP价必须大于0");
            return resultVo;
        }
        if(productVo.getMax()<0){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("普通商品限购数量不能为空");
            return resultVo;
        }
        if(productVo.getMin()<0){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("最低购买数量不能为空");
            return resultVo;
        }
        if(productVo.getIntroduction()==null || "".equals(productVo.getIntroduction()) ){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品介绍不能为空");
            return resultVo;
        }
        if(productVo.getBuyKnow()==null || "".equals(productVo.getBuyKnow()) ){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品购买须知不能为空");
            return resultVo;
        }
        if(productVo.getAfterShop()==null || "".equals(productVo.getAfterShop()) ){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品售后说明不能为空");
            return resultVo;
        }
        if(productVo.getStore()<0){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品库存不能为空");
            return resultVo;
        }
        if(productVo.getImages()==null || productVo.getImages().size()<1){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品图片不能为空");
            return resultVo;
        }
        return resultVo;
	}
	
	@Override
	public ProductListPageVo findGoodsByPage(
			com.froad.thrift.vo.ProductFilterVo productFilterVo, PageVo pageVo)
			throws TException {
		long startTime = System.currentTimeMillis();
        LogCvt.debug("boss查询精品商城商品列表参数,ProductFilterVo:"+JSON.toJSONString(productFilterVo)+",pageVo:"+JSON.toJSONString(pageVo));
        ProductListPageVo productPageVo = new ProductListPageVo();
        try{
            Page<ProductListInfo> page = new Page<ProductListInfo>();
            ProductBeanUtil.copyProperties(page, pageVo);
            
            ProductFilter productFilter = new ProductFilter();
            productFilter.setProductId(productFilterVo.getProductId());;
            productFilter.setName(productFilterVo.getProductName());
            productFilter.setCategoryId(productFilterVo.getCategoryId());
            productFilter.setClientId(productFilterVo.getClientId());
            productFilter.setSeoKeyWords(productFilterVo.getSeoKeyWords());
            productFilter.setFilterStatuts(ProductStatus.isDeleted.getCode());//已经删除的不查询出来
            if(Checker.isEmpty(productFilter.getType())) {
            	productFilter.setType(ProductType.boutique.getCode());//精品商城商品
            } else if(!"-1".equals(productFilter.getType())) {
            	String[] typesArray = productFilter.getType().split(",");
                if(typesArray!=null) {
                	if(typesArray.length==1) {
                		productFilter.setType(typesArray[0]);
                	} else if(typesArray.length>0) {
                		List<String> types = new ArrayList<String>();
                		for(String type : typesArray){
                            if(type!=null && !"".equals(type)){
                                types.add(type);
                            }
                        }
                		productFilter.setTypeList(types);
                	}
                }
            }
            productFilter.setIsSeckill(productFilterVo.getIsSeckill());
            productFilter.setIsMarketable(productFilterVo.getIsMarketable());
            productFilter.setAuditState(productFilterVo.getAuditState());
            
            List<Product> productList = productLogic.findGoodsByPage(page, productFilter);
            
            List<ProductListVo> productListVos = new ArrayList<ProductListVo>();
            if (!CollectionUtils.isEmpty(productList)) {
            	ProductListVo vo = null;
                for (Product po : productList) {
                    try{
                        vo = new ProductListVo();
                        vo.setId(po.getId());
                        vo.setProductId(po.getProductId());
                        vo.setCreateTime(po.getCreateTime().getTime());
                        vo.setName(po.getName());
                        vo.setFullName(po.getFullName());
                        vo.setPrice(Arith.div(po.getPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));
                        if(po.getVipPrice()!=null){
                        	vo.setVipPrice(Arith.div(po.getVipPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));
                        }
                        vo.setClientId(po.getClientId());
                        
                        CommonLogic commonLogic = new CommonLogicImpl();
                        Client client = commonLogic.getClientById(po.getClientId());
    	                if(client!=null){
    	                    vo.setClientName(client.getName());//客户端名称
    	                }
                        vo.setMarketableStatus(po.getIsMarketable());
                        vo.setProductType(po.getType());
                        vo.setStore(po.getStore());
                        vo.setMerchantId(po.getMerchantId());
                        vo.setMerchantName(po.getMerchantName());
                        if(po.getStartTime()!=null) {
                        	vo.setStartTime(po.getStartTime().getTime());
                        }
                        if(po.getEndTime()!=null) {
                        	vo.setEndTime(po.getEndTime().getTime());
                        }
                        vo.setIsSeckill(po.getIsSeckill());
                        
                        //添加到list中进行返回
                        productListVos.add(vo);
                    } catch (Exception e) {
                        LogCvt.error("boss查询精品商城商品列表list Product po转换vo失败，原因:" + e.getMessage(),e); 
                    }
                }
            }
            productPageVo.setProductListVos(productListVos);
            pageVo.setPageCount(page.getPageCount());
            pageVo.setTotalCount(page.getTotalCount());
            if(pageVo.getPageCount()>pageVo.getPageNumber()){
                pageVo.setHasNext(true);
            } else {
                pageVo.setHasNext(false);
            }
            productPageVo.setPage(pageVo);
        }catch (Exception e) {
            LogCvt.error("boss查询精品商城商品列表失败，原因:" + e.getMessage(),e); 
        }
        long endTime = System.currentTimeMillis();
        LogCvt.debug("boss查询精品商城商品列表耗时"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);
        return productPageVo;
	}
	
	@Override
	public GoodsInfoVo getGoodsDetail(String productId) throws TException {
		LogCvt.debug("boss查询精品商城商品明细参数productId:"+productId);
        Product product = productLogic.getGoodsDetail(productId);
        
        GoodsInfoVo productInfoVo = new GoodsInfoVo();
        if (product!=null) {
        	productInfoVo.setClientId(product.getClientId());
        	productInfoVo.setMerchantId(product.getMerchantId());
        	productInfoVo.setCreateTime(product.getCreateTime().getTime());
        	productInfoVo.setMarketableStatus(product.getIsMarketable());
        	productInfoVo.setId(product.getId());
        	productInfoVo.setProductId(product.getProductId());
        	productInfoVo.setType(product.getType());
        	productInfoVo.setAfterShop(product.getAfterShop());
        	productInfoVo.setBuyKnow(product.getBuyKnow());
        	productInfoVo.setIntroduction(product.getIntroduction());
        	productInfoVo.setIsSeckill(product.getIsSeckill());
        	if(product.getDeliveryTime()!=null){
        		productInfoVo.setDeliveryTime(product.getDeliveryTime().getTime());
        		productInfoVo.setIsBatchDelivery(true);
        	} else {
        		productInfoVo.setIsBatchDelivery(false);
        	}
        	productInfoVo.setStore(product.getStore());
        	productInfoVo.setPrice(Arith.div(product.getPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));
        	productInfoVo.setMarketPrice(Arith.div(product.getMarketPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));
            if(product.getVipPrice()!=null){
            	productInfoVo.setVipPrice(Arith.div(product.getVipPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));
            }
        	productInfoVo.setSeoKeyWords(product.getSeoKeyWords());
        	productInfoVo.setName(product.getName());
        	productInfoVo.setFullName(product.getFullName());
        	productInfoVo.setBriefIntroduction(product.getBriefIntroduction());
        	
        	CommonLogic commonLogic = new CommonLogicImpl();
            Client client = commonLogic.getClientById(product.getClientId());
            if(client!=null){
            	productInfoVo.setClientName(client.getName());//客户端名称
            }
            Map<String,String> providerMap = commonLogic.getProviderRedis(product.getMerchantId());
    		if(Checker.isNotEmpty(providerMap)){
    			productInfoVo.setMerchantName(providerMap.get("merchant_name"));
            }
    		if(Checker.isNotEmpty(product.getCategoryTreePath())){
    			String[] pcids = product.getCategoryTreePath().split(" ");
    			ProductCategory pc = null;
                for(int i=(pcids.length-1);i>=0;i--){
                    if(Checker.isNotEmpty(pcids[i])){
                    	pc = commonLogic.findCategoryById(product.getClientId(), Long.valueOf(pcids[i]));
                    	if(pc!=null && !pc.getIsDelete()){
                    		productInfoVo.setCategoryId(pc.getId());
                			productInfoVo.setCategoryName(pc.getName());
                    		break;
                    	}
                    }
                }
    		}
    		DBObject keys = new BasicDBObject();
    		keys.put("buy_limit", "$buy_limit");
    		keys.put("is_recommend", "$is_recommend");
    		keys.put("is_new", "$is_new");
    		keys.put("is_hot", "$is_hot");
    		keys.put("image_info", "$image_info");
            ProductDetail pd = productLogic.getProductDetail(product, keys);
            if(pd!=null){
            	if(pd.getBuyLimit()!=null){
            		ProductBuyLimit buyLimit = pd.getBuyLimit();
            		productInfoVo.setMax(buyLimit.getMax());
            		productInfoVo.setMin(buyLimit.getMin());
            		productInfoVo.setMaxVip(buyLimit.getMaxVip());
            	}
            	//获取图片信息
                List<ProductImage> images = pd.getImageInfo();
                if(images!=null && images.size()>0){
                    List<ProductImageVo> imageVos = new ArrayList<ProductImageVo>();
                    ProductImageVo imageVo = null;
                    for(ProductImage imagePo : images){
                        imageVo = new ProductImageVo();
                        ProductBeanUtil.copyProperties(imageVo, imagePo);
                        imageVos.add(imageVo);
                    }
                    productInfoVo.setImages(imageVos);
                }
                if(pd.getIsRecommend()!=null && pd.getIsRecommend()==1){
                	productInfoVo.setIsRecommend(true);
                } else {
                	productInfoVo.setIsRecommend(false);
                }
                if(pd.getIsHot()!=null && pd.getIsHot()==1){
                	productInfoVo.setIsHot(true);
                } else {
                	productInfoVo.setIsHot(false);
                }
                if(pd.getIsNew()!=null && pd.getIsNew()==1){
                	productInfoVo.setIsNew(true);
                } else {
                	productInfoVo.setIsNew(false);
                }
            }
        }
        return productInfoVo;
	}
	
	@Override
	public BoutiqueProductPageVoRes queryBoutiqueGoods(
			QueryBoutiqueGoodsFilterVo filterVo, FiledSort filedSort, PageVo pageVo)
			throws TException {
		long start = System.currentTimeMillis();
		LogCvt.debug("time:"+DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT5, new Date())
				+"精品商城商品列表QueryBoutiqueGoodsFilterVo:"+JSON.toJSONString(filterVo)
				+",FiledSort:"+JSON.toJSONString(filedSort)
				+",pageVo:"+JSON.toJSONString(pageVo));
		
		BoutiqueProductPageVoRes productPageVo = new BoutiqueProductPageVoRes();//返回变量
        try {
        	List<ProductDetail> productDetails = productLogic.queryGoods(pageVo, filterVo, filedSort, pageVo.getPageSize());
            if(productDetails!=null && productDetails.size()>0 && productDetails.size()==pageVo.getPageSize()){
            	//原来查询条件 再查询下一页一条数据
            	PageVo nextPageVo = new PageVo();
            	nextPageVo.setPageNumber(pageVo.getPageNumber()+1);
            	nextPageVo.setPageSize(pageVo.getPageSize());
            	List<ProductDetail> nextproductDetails = productLogic.queryGoods(nextPageVo, filterVo, filedSort, 1);
            	if(nextproductDetails!=null && nextproductDetails.size()>0){
            		pageVo.setHasNext(true);
            	} else {
                	pageVo.setHasNext(false);
                }
            } else {
            	pageVo.setHasNext(false);
            }
            List<BoutiqueProductVo> productVoList = new ArrayList<BoutiqueProductVo>();
            if(productDetails!=null && productDetails.size()>0){
            	BoutiqueProductVo vo = null;
                Long now = new Date().getTime();
                for(ProductDetail pd : productDetails){
                    vo = new BoutiqueProductVo();
                    vo.setProductId(pd.getId());
                    vo.setName(pd.getName());
                    vo.setFullName(pd.getFullName());
                    vo.setPrice(Arith.div(pd.getPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));
                    if(pd.getVipPrice()!=null){
                    	vo.setVipPrice(Arith.div(pd.getVipPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));
                    }
                    vo.setSellCount(pd.getSellCount());
                    vo.setBriefIntroduction(pd.getBriefIntroduction());
                    if(pd.getImageInfo()!=null && pd.getImageInfo().size()>0){
                        ProductImage imagepo = pd.getImageInfo().get(0);
                        ProductImageVo image = new ProductImageVo();
                        image.setThumbnail(imagepo.getThumbnail());
                        image.setMedium(imagepo.getMedium());
                        vo.setImage(image);
                    }
                    if(pd.getIsRecommend()!=null && pd.getIsRecommend()==1){
                    	vo.setIsRecommend(true);
                    } else {
                    	vo.setIsRecommend(false);
                    }
                    if(pd.getIsHot()!=null && pd.getIsHot()==1){
                    	vo.setIsHot(true);
                    } else {
                    	vo.setIsHot(false);
                    }
                    if(pd.getIsNew()!=null && pd.getIsNew()==1){
                    	vo.setIsNew(true);
                    } else {
                    	vo.setIsNew(false);
                    }
                    vo.setServerTime(now);
                    vo.setIsSeckill(pd.getIsSeckill());
                    productVoList.add(vo);
                }
            }
            productPageVo.setProductVos(productVoList);
            productPageVo.setPage(pageVo);
            
            long end = System.currentTimeMillis();
            //个人H5商品每秒查询次数
            monitorService.send(Constants.MONITOR_BUSINAME, GoodsConstants.MONITOR_GOODS_GOODSSEARCH_SEARCHNUMBER_ATTRID, "1", GoodsConstants.MONITOR_GOODS_GOODSSEARCH_SEARCHNUMBER_TYPE, "");
            //个人H5商品查询耗时
            monitorService.send(Constants.MONITOR_BUSINAME, GoodsConstants.MONITOR_GOODS_GOODSSEARCH_SPEND_TIME_ATTRID, String.valueOf(end-start), GoodsConstants.MONITOR_GOODS_GOODSSEARCH_SPEND_TIME_TYPE, "");
       
        } catch (Exception e) {
            LogCvt.error("个人H5查询精品商城商品失败，原因:" + e.getMessage(),e); 
        }
        return productPageVo;
	}
	
	@Override
	public BoutiqueGoodsInfoVo queryBoutiqueGoodsDetail(String productId)
			throws TException {
		long start = System.currentTimeMillis();
        LogCvt.debug("H5查询精品商城商品明细参数,productId:"+productId);
        
        long pstart = System.currentTimeMillis();
        Product product = productLogic.getGoodsDetail(productId);
        long pend = System.currentTimeMillis();
        LogCvt.debug("H5查询精品商城商品明细mysql中商品基础信息总耗时:"+(pend-pstart));
        
        BoutiqueGoodsInfoVo productInfoVo = new BoutiqueGoodsInfoVo();
        if (product!=null) {
        	productInfoVo.setProductId(product.getProductId());
        	productInfoVo.setClientId(product.getClientId());
        	productInfoVo.setMerchantId(product.getMerchantId());
        	productInfoVo.setType(product.getType());
        	productInfoVo.setName(product.getName());
        	productInfoVo.setFullName(product.getFullName());
        	productInfoVo.setBriefIntroduction(product.getBriefIntroduction());
        	productInfoVo.setPrice(Arith.div(product.getPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));
        	productInfoVo.setMarketPrice(Arith.div(product.getMarketPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));
            if(product.getVipPrice()!=null){
            	productInfoVo.setVipPrice(Arith.div(product.getVipPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));
            }
            productInfoVo.setStore(product.getStore());
            if(product.getDeliveryTime()!=null){
        		productInfoVo.setDeliveryTime(product.getDeliveryTime().getTime());
        	}
            productInfoVo.setIntroduction(product.getIntroduction());
            productInfoVo.setBuyKnow(product.getBuyKnow());
            productInfoVo.setAfterShop(product.getAfterShop());
            productInfoVo.setIsSeckill(product.getIsSeckill());
            CommonLogic commonLogic = new CommonLogicImpl();
            Map<String,String> providerMap = commonLogic.getProviderRedis(product.getMerchantId());
    		if(Checker.isNotEmpty(providerMap)){
    			productInfoVo.setMerchantName(providerMap.get("merchant_name"));
            }
    		
    		DBObject keys = new BasicDBObject();
    		keys.put("sell_count", "$sell_count");
    		keys.put("buy_limit", "$buy_limit");
    		keys.put("is_recommend", "$is_recommend");
    		keys.put("is_new", "$is_new");
    		keys.put("is_hot", "$is_hot");
    		keys.put("image_info", "$image_info");
    		
    		//获取mongo中的商品多表关系的信息查出来设置到返回的商品中
            long pdstart = System.currentTimeMillis();
            ProductDetail pd = productLogic.getProductDetail(product, keys);
            long pdend = System.currentTimeMillis();
            LogCvt.debug("H5查询精品商城商品明细mongo中商品信息总耗时:"+(pdend-pdstart));
            
            if(pd!=null){
            	if(pd.getSellCount()!=null){
            		productInfoVo.setSellCount(pd.getSellCount());
            	}
            	if(pd.getBuyLimit()!=null){
            		ProductBuyLimit buyLimit = pd.getBuyLimit();
            		productInfoVo.setMax(buyLimit.getMax());
            		productInfoVo.setMin(buyLimit.getMin());
            		productInfoVo.setMaxVip(buyLimit.getMaxVip());
            	}
            	//获取图片信息 大图
                List<ProductImage> images = pd.getImageInfo();
                if(images!=null && images.size()>0){
                    List<String> imageUrls = new ArrayList<String>();
                    for(ProductImage imagePo : images){
                        imageUrls.add(imagePo.getLarge());
                    }
                    productInfoVo.setImageUrls(imageUrls);
                }
                if(pd.getIsRecommend()!=null && pd.getIsRecommend()==1){
                	productInfoVo.setIsRecommend(true);
                } else {
                	productInfoVo.setIsRecommend(false);
                }
                if(pd.getIsHot()!=null && pd.getIsHot()==1){
                	productInfoVo.setIsHot(true);
                } else {
                	productInfoVo.setIsHot(false);
                }
                if(pd.getIsNew()!=null && pd.getIsNew()==1){
                	productInfoVo.setIsNew(true);
                } else {
                	productInfoVo.setIsNew(false);
                }
            }
        }
        long end = System.currentTimeMillis();
        LogCvt.debug("H5查询精品商城商品明细总耗时:"+(end-start));
        return productInfoVo;
	}
	
	@Override
	public ResultVo updateGoods(OriginVo originVo, GoodsInfoVo goodsInfoVo)
			throws TException {
        LogCvt.debug("修改精品商城商品参数,originVo:"+JSON.toJSONString(originVo)+",GoodsInfoVo:"+JSON.toJSONString(goodsInfoVo));
        ResultVo resultVo = new ResultVo();
        try {
        	//添加操作日志记录
        	originVo.setDescription("修改精品商城商品信息");
			LogUtils.addLog(originVo);
			
			if(goodsInfoVo.getProductId()==null || "".equals(goodsInfoVo.getProductId())){
                resultVo.setResultCode(ResultCode.failed.getCode());
                resultVo.setResultDesc("修改商品时商品id不能为空");
                return resultVo;
            }
			
			/************验证数据的合法性 start**************/
			ResultVo validationResultVo = validationGoodsFileds(originVo,goodsInfoVo);
            if(validationResultVo!=null){
                return validationResultVo;
            }
	        /************验证数据的合法性 end**************/
            
            Result result = productLogic.updateGoods(goodsInfoVo);
            ProductBeanUtil.copyProperties(resultVo, result);
        } catch (Exception e) {
            LogCvt.error("修改精品商城商品失败, 原因:" + e.getMessage(), e);
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("修改精品商城商品失败");
        }
        return resultVo;
    }
	
	@Override
	public ResultVo updateGoodsStatusOff(OriginVo originVo, String productId)
			throws TException {
		LogCvt.debug("精品商城商品下架参数originVo:"+JSON.toJSONString(originVo)+",productId:"+productId);
        
        //添加操作日志记录
    	originVo.setDescription("精品商城商品下架");
		LogUtils.addLog(originVo);
		
        ResultVo resultVo = new ResultVo();
        Result result = productLogic.updateGoodsStatusOff(productId);
        ProductBeanUtil.copyProperties(resultVo, result);
        return resultVo;
	}
	
	@Override
	public ResultVo updateGoodsStatusOn(OriginVo originVo, String productId)
			throws TException {
		LogCvt.debug("精品商城商品上架参数originVo:"+JSON.toJSONString(originVo)+",productId:"+productId);
        
        //添加操作日志记录
    	originVo.setDescription("精品商城商品上架");
		LogUtils.addLog(originVo);
		
        ResultVo resultVo = new ResultVo();
        Result result = productLogic.updateGoodsStatusOn(productId);
        ProductBeanUtil.copyProperties(resultVo, result);
        return resultVo;
	}
	
	@Override
	public AddOutletProductVoRes addH5OutletProduct(OriginVo originVo,
			OutletProductDiscountVo outletProductDiscountVo) throws TException {
		long start = System.currentTimeMillis();
        LogCvt.debug("个人h5新加面对面商品参数originVo:"+JSON.toJSONString(originVo)+",OutletProductDiscountVo"+JSON.toJSONString(outletProductDiscountVo));
        
        //添加操作日志记录
    	originVo.setDescription("个人h5添加面对面商品");
		LogUtils.addLog(originVo);
		
        AddOutletProductVoRes addOutletProductVoRes = new AddOutletProductVoRes();
        ResultVo resultVo = new ResultVo();
        if(outletProductDiscountVo.getClientId()==null || "".equals(outletProductDiscountVo.getClientId())){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("客户端id不能为空");
            addOutletProductVoRes.setResultVo(resultVo);
            return addOutletProductVoRes;
        }
        if(outletProductDiscountVo.getOutletId()==null || "".equals(outletProductDiscountVo.getOutletId())){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("面对面商品所属的门店id不能为空");
            addOutletProductVoRes.setResultVo(resultVo);
            return addOutletProductVoRes;
        }
        if(outletProductDiscountVo.getMerchantId()==null || "".equals(outletProductDiscountVo.getMerchantId())){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("面对面商品所属的商户id不能为空");
            addOutletProductVoRes.setResultVo(resultVo);
            return addOutletProductVoRes;
        }
        //1.消费总金额（原始值大于0，原始值小数位只能有2位）、2.不参与优惠金额（没有默认为0，原始值小数位只能有2位）、3.折扣优惠（没有默认为10，原始值范围为0-10，原始值小数位只能有1位）
        //1.消费总金额（原始值大于0，原始值小数位只能有2位）
        if(outletProductDiscountVo.getConsumeAmount()<=0.0){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("消费总金额必填");
            addOutletProductVoRes.setResultVo(resultVo);
            return addOutletProductVoRes;
        }
        if(Util.getDoubleDecimalNum(outletProductDiscountVo.getConsumeAmount())>2){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("消费总金额只能有2位有效小数");
            addOutletProductVoRes.setResultVo(resultVo);
            return addOutletProductVoRes;
        }
        //2.不参与优惠金额（没有默认为0，原始值小数位只能有2位）
        if(outletProductDiscountVo.getNotDiscountAmount()<0.0){
        	resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("不参与优惠金额不能为负数");
            addOutletProductVoRes.setResultVo(resultVo);
            return addOutletProductVoRes;
        }
        if(outletProductDiscountVo.getNotDiscountAmount()>0.0 && Util.getDoubleDecimalNum(outletProductDiscountVo.getNotDiscountAmount())>2){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("不参与优惠金额只能有2位有效小数");
            addOutletProductVoRes.setResultVo(resultVo);
            return addOutletProductVoRes;
        }
        //3.折扣优惠（没有默认为10，原始值范围为0-10，原始值小数位只能有1位）
        if(outletProductDiscountVo.getDiscountRate()>10.0 || outletProductDiscountVo.getDiscountRate()<0.0){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("折扣优惠值范围只能为0-10");
            addOutletProductVoRes.setResultVo(resultVo);
            return addOutletProductVoRes;
        }
        if(outletProductDiscountVo.getDiscountRate()==0.0){//默认为10
        	outletProductDiscountVo.setDiscountRate(10.0);
        }
        if(Util.getDoubleDecimalNum(outletProductDiscountVo.getDiscountRate())>1){
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("折扣优惠只能有1位有效小数");
            addOutletProductVoRes.setResultVo(resultVo);
            return addOutletProductVoRes;
        }
        OutletProduct outletProduct = new OutletProduct();
        ProductBeanUtil.copyPropertiesScale(outletProduct, outletProductDiscountVo, GoodsConstants.DOUBLE_INTGER_CHANGE);
        if(outletProductDiscountVo.getDiscountRate()==10.0){
        	outletProduct.setCost(outletProduct.getConsumeAmount());
        } else {//cost=(消费总金额-不参与优惠金额)*折扣优惠+不参与优惠金额
        	double discountTotalAmount = 
        		Arith.div(
        			Arith.mul(
        				Arith.sub(outletProductDiscountVo.getConsumeAmount()
        						,outletProductDiscountVo.getNotDiscountAmount())
    					,outletProductDiscountVo.getDiscountRate())
    				,10);
        	
        	BigDecimal bg = new BigDecimal(discountTotalAmount);  
        	double discountTotalAmountRoundHalfUp = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
        	outletProduct.setCost(
                Arith.mul(
                	Arith.add(discountTotalAmountRoundHalfUp
                			,outletProductDiscountVo.getNotDiscountAmount())
                	, GoodsConstants.DOUBLE_INTGER_CHANGE
                )
            );
        }
        
        OutletProductQrCode po =productLogic.addH5OutletProduct(outletProduct);
        if(po!=null){
            OutletProductQrCodeVo qrCodeVo = new OutletProductQrCodeVo();
            ProductBeanUtil.copyProperties(qrCodeVo, po);
            
            resultVo.setResultCode(ResultCode.success.getCode());
            resultVo.setResultDesc("新增面对面商品成功");
            addOutletProductVoRes.setResultVo(resultVo);
            addOutletProductVoRes.setOutletProductQrCodeVo(qrCodeVo);
        } else {
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("新增面对面商品失败");
            addOutletProductVoRes.setResultVo(resultVo);
        }
        long end = System.currentTimeMillis();
        //面对面商品创建接口每次耗时监控
        monitorService.send(Constants.MONITOR_BUSINAME, GoodsConstants.MONITOR_GOODS_F2FGOOD_ADD_SPEND_TIME_ATTRID, String.valueOf(end-start), GoodsConstants.MONITOR_GOODS_F2FGOOD_ADD_SPEND_TIME_TYPE, "");
        return addOutletProductVoRes;
	}


	@Override
	public ProductPageVo findProductsByPage(ProductFilterReqVo filterVo,
			List<FiledSort> filedSorts, PageVo pageVo) throws TException {
		long startTime = System.currentTimeMillis();
        LogCvt.debug("商品管理里面查询商品列表参数,ProductFilterReqVo:"+JSON.toJSONString(filterVo)+",List<FiledSort>:"+JSON.toJSONString(filedSorts)+",pageVo:"+JSON.toJSONString(pageVo));
        ProductPageVo productPageVo = new ProductPageVo();
        try{
        	Page<ProductListInfo> page = new Page<ProductListInfo>();
            ProductBeanUtil.copyProperties(page, pageVo);
            
            ProductFilter productFilter = new ProductFilter();
            ProductBeanUtil.copyPropertiesScale(productFilter, filterVo, GoodsConstants.DOUBLE_INTGER_CHANGE);
            productFilter.setCategoryId(filterVo.getProductCategoryId());
            
            LinkedHashMap<String, String> sortOrderFileds = null;
            if(filedSorts!=null && filedSorts.size()>0) {
            	sortOrderFileds = new LinkedHashMap<String, String>();
            	Comparator<FiledSort> comparator = new Comparator<FiledSort>() {
            		public int compare(FiledSort obj1, FiledSort obj2) {
            			// 降序排序
            			if(obj2!=null && obj1!=null) {
            				return obj2.getSortPrior()-obj1.getSortPrior();
            			}
            			return 0;
            		}
            	};
            	Collections.sort(filedSorts, comparator);//用我们写好的Comparator对student进行排序
            	
            	for(FiledSort filedSort : filedSorts) {
            		if(Checker.isNotEmpty(GoodsSortConstants.sqlOrderFiledsMap.get(filedSort.getSortName()))) {
            			sortOrderFileds.put(GoodsSortConstants.sqlOrderFiledsMap.get(filedSort.getSortName()), filedSort.getSortBy()>0?"ASC":"DESC");
            		}
            	}
            } else if(Checker.isNotEmpty(filterVo.getType())) {
            	sortOrderFileds = GoodsSortConstants.getSortOrderFileds(GoodsSortConstants.sqlSortOrdersMap.get(productFilter.getType()), GoodsSortConstants.sqlOrderFiledsMap);
            } else {
            	sortOrderFileds = GoodsSortConstants.getSortOrderFileds(GoodsSortConstants.sqlSortOrdersMap.get("0"), GoodsSortConstants.sqlOrderFiledsMap);
            }
            productFilter.setOrderFileds(sortOrderFileds);// 设置排序字段
            
            //已经删除的不查询出来
            productFilter.setFilterStatuts(ProductStatus.isDeleted.getCode());
            if(productFilter.getCategoryId()!=null && productFilter.getCategoryId()>0 && Checker.isNotEmpty(productFilter.getClientId())){
                CommonLogic common = new CommonLogicImpl();
                ProductCategory pc = common.findCategoryById(productFilter.getClientId(), productFilter.getCategoryId());
                if(Checker.isNotEmpty(pc)){
                	productFilter.setCategoryTreePath(pc.getTreePath());
                }
            }
            if(productFilter.getPriceStart()==0) {
            	productFilter.setPriceStart(null);
            }
            if(productFilter.getPriceEnd()==0) {
            	productFilter.setPriceEnd(null);
            }
            List<ProductListInfo> productList = productLogic.getProductsByPage(page,productFilter);
            List<ProductBriefVo> productBriefVoList = new ArrayList<ProductBriefVo>();
            if (!CollectionUtils.isEmpty(productList)) {
                ProductBriefVo vo = null;
                CommonLogic commonLogic = new CommonLogicImpl();
                Map<String, String> hash = null;
                String[] pcids = null;
    			ProductCategory pc = null;
                Org org = null;
                for (ProductListInfo po : productList) {
                    try{
                        vo = new ProductBriefVo();
                        //根据clientId+orgCode查询orgName机构名称
                        if(po.getOrgCode()!=null && !"".equals(po.getOrgCode().trim())){
                            org = commonLogic.queryByOrgCode(po.getClientId(), po.getOrgCode());
                            if(org!=null){
                                po.setOrgName(org.getOrgName());
                            }
                        }
                        
                        //团购商品以及预售商品需要查商品缓存信息
                        if(ProductType.group.getCode().equals(po.getType())){
                            hash = commonLogic.getProductRedis(po.getClientId(), po.getMerchantId(), po.getProductId());
                            if(hash!=null){
                                if(Checker.isNotEmpty(hash.get("expire_start_time"))){//团购券有效起始日
                                    po.setExpireStartTime(new Date(Long.valueOf(hash.get("expire_start_time"))));
                                }
                                if(Checker.isNotEmpty(hash.get("expire_end_time"))){//团购券有效结束日
                                    po.setExpireEndTime(new Date(Long.valueOf(hash.get("expire_end_time"))));
                                }
                            }
                        }
                        
                        if(Checker.isNotEmpty(po.getCategoryTreePath())){
                			pcids = po.getCategoryTreePath().split(" ");
                            for(int i=(pcids.length-1);i>=0;i--){
                                if(Checker.isNotEmpty(pcids[i])){
                                	pc = commonLogic.findCategoryById(po.getClientId(), Long.valueOf(pcids[i]));
                                	if(pc!=null && !pc.getIsDelete()){
                                		po.setCategoryName(pc.getName());
                                		break;
                                	}
                                }
                            }
                		}
                        //po转vo属性
                        ProductBeanUtil.copyPropertiesScale(vo, po, GoodsConstants.DOUBLE_INTGER_CHANGE);
                        vo.setAuditStateName(ProductTempAuditState.getType(vo.getAuditState()).getDescribe());
                        //添加到list中进行返回
                        productBriefVoList.add(vo);
                    } catch (Exception e) {
                        LogCvt.error("商品管理分页查询商品列表po转换vo失败，原因:" + e.getMessage(),e); 
                    }
                    
                }
            }
            productPageVo.setProductBriefVoList(productBriefVoList);
            pageVo.setPageCount(page.getPageCount());
            pageVo.setTotalCount(page.getTotalCount());
            if(pageVo.getPageCount()>pageVo.getPageNumber()){
                pageVo.setHasNext(true);
            } else {
                pageVo.setHasNext(false);
            }
            productPageVo.setPage(pageVo);
        }catch (Exception e) {
            LogCvt.error("查询商品管理里面的商品列表失败，原因:" + e.getMessage(),e); 
        }
        long endTime = System.currentTimeMillis();
        LogCvt.debug("-------findProductsByPage:耗时"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);
        return productPageVo;
	}
	
	@Override
	public ProductDetailInfo getProductDetail(String productId) throws TException {
		LogCvt.debug("管理平台查看商品明细getProductDetail productId:"+productId);
		
		ProductDetailInfo productDetailInfo = new ProductDetailInfo();
		Map<String,Object> productMap = productLogic.getProductInfo(productId);
		Product product = (Product)productMap.get("p");
		ProductGroup pg = (ProductGroup)productMap.get("pg");
//		ProductPresell pr = (ProductPresell)productMap.get("pr");
		ProductTemp pt = (ProductTemp)productMap.get("pt");
		ProductDetail pd = (ProductDetail)productMap.get("pd");
		
		ProductDetailVo productDetailVo = null;
		ProductDetailVo oldProductDetailVo = null;
		
		if(pt!=null && ProductAuditState.passAudit.getCode().equals(product.getAuditState())) {
			oldProductDetailVo = new ProductDetailVo();
			ProductBeanUtil.copyPropertiesScale(oldProductDetailVo, product, GoodsConstants.DOUBLE_INTGER_CHANGE);
			if(ProductType.group.getCode().equals(product.getType()) && pg!=null) {
				if(pg.getExpireStartTime()!=null) {
					oldProductDetailVo.setExpireStartTime(pg.getExpireStartTime().getTime());
				}
				if(pg.getExpireEndTime()!=null) {
					oldProductDetailVo.setExpireEndTime(pg.getExpireEndTime().getTime());
				}
			} else if(ProductType.presell.getCode().equals(product.getType()) && pg!=null) {
				
			}
			attachRedisProperties(oldProductDetailVo);
			if(pd!=null) {
				attachMongoProperties(oldProductDetailVo,pd);
			}
			
			//商品审核状态对应的名称("0审核中","1审核通过","2审核不通过","3未提交","4审核通过(更新中)","5审核通过(更新审核未通过)"，6更新未提交)
	        if(ProductAuditState.noAudit.getCode().equals(pt.getAuditState())) {
	        	oldProductDetailVo.setAuditState("4");
	        	oldProductDetailVo.setAuditStateName("审核通过(更新中)");
	        } else if(ProductAuditState.failAudit.getCode().equals(pt.getAuditState())) {
	        	oldProductDetailVo.setAuditState("5");
	        	oldProductDetailVo.setAuditStateName("审核通过(更新审核未通过)");
	        } else if(ProductAuditState.noCommit.getCode().equals(pt.getAuditState())) {
	        	oldProductDetailVo.setAuditState("6");
	        	oldProductDetailVo.setAuditStateName("审核通过(更新未提交)");
	        }
			
			productDetailVo = new ProductDetailVo();
			ProductBeanUtil.copyPropertiesScale(productDetailVo, pt, GoodsConstants.DOUBLE_INTGER_CHANGE);
			attachRedisProperties(productDetailVo);
			if(productDetailVo.isLimit && Checker.isNotEmpty(pt.getBuyLimit())){
	            ProductBuyLimitVo buyLimitVo = new ProductBuyLimitVo();
	            ProductBuyLimit buyLimit = JSON.parseObject(pt.getBuyLimit(), ProductBuyLimit.class);
	            ProductBeanUtil.copyProperties(buyLimitVo, buyLimit);
	            productDetailVo.setBuyLimit(buyLimitVo);
	        }
			if(Checker.isNotEmpty(pt.getPhotoList())){
				List<ProductImage> images = JSON.parseArray(pt.getPhotoList(), ProductImage.class);
		        if(images!=null && images.size()>0){
		            List<ProductImageVo> imageVos = new ArrayList<ProductImageVo>();
		            ProductImageVo imageVo = null;
		            for(ProductImage imagePo : images){
		                imageVo = new ProductImageVo();
		                ProductBeanUtil.copyProperties(imageVo, imagePo);
		                imageVos.add(imageVo);
		            }
		            productDetailVo.setImages(imageVos);
		        }
			}
			//商品审核状态对应的名称("0审核中","1审核通过","2审核不通过","3未提交","4审核通过(更新中)","5审核通过(更新审核未通过)"，6更新未提交)
	        if(ProductAuditState.noAudit.getCode().equals(pt.getAuditState())) {
	        	productDetailVo.setAuditState("4");
	        	productDetailVo.setAuditStateName("审核通过(更新中)");
	        } else if(ProductAuditState.failAudit.getCode().equals(pt.getAuditState())) {
	        	productDetailVo.setAuditState("5");
	        	productDetailVo.setAuditStateName("审核通过(更新审核未通过)");
	        } else if(ProductAuditState.noCommit.getCode().equals(pt.getAuditState())) {
	        	productDetailVo.setAuditState("6");
	        	productDetailVo.setAuditStateName("审核通过(更新未提交)");
	        }
		} else {
			productDetailVo = new ProductDetailVo();
			//返回vo 查询出的po转换成vo,价格等 表中int的转换成vo的double类型需要除以1000
			ProductBeanUtil.copyPropertiesScale(productDetailVo, product, GoodsConstants.DOUBLE_INTGER_CHANGE);
			if(ProductType.group.getCode().equals(product.getType()) && pg!=null) {
				if(pg.getExpireStartTime()!=null) {
					productDetailVo.setExpireStartTime(pg.getExpireStartTime().getTime());
				}
				if(pg.getExpireEndTime()!=null) {
					productDetailVo.setExpireEndTime(pg.getExpireEndTime().getTime());
				}
			}
			attachRedisProperties(productDetailVo);
			if(pd!=null) {
				attachMongoProperties(productDetailVo,pd);
			}
			//商品审核状态对应的名称("0审核中","1审核通过","2审核不通过","3未提交","4审核通过(更新中)","5审核通过(更新审核未通过)"，6更新未提交)
			if(ProductAuditState.noAudit.getCode().equals(product.getAuditState())) {
	        	productDetailVo.setAuditStateName("审核中");
	        } else if(ProductAuditState.failAudit.getCode().equals(product.getAuditState())) {
	        	productDetailVo.setAuditStateName("审核不通过");
	        } else if(ProductAuditState.passAudit.getCode().equals(product.getAuditState())) {
	        	productDetailVo.setAuditStateName("审核通过");
	        } else if(ProductAuditState.noCommit.getCode().equals(product.getAuditState())) {
	        	productDetailVo.setAuditStateName("未提交");
	        }
		}
		productDetailInfo.setOldProductDetailVo(oldProductDetailVo);
		productDetailInfo.setProductDetailVo(productDetailVo);
		
        return productDetailInfo;
	}
	
	private void attachRedisProperties(ProductDetailVo productDetailVo) {
		CommonLogic common = new CommonLogicImpl();
		if(Checker.isNotEmpty(productDetailVo.getClientId())) {
			if(Checker.isNotEmpty(productDetailVo.getCategoryTreePath())) {
				String[] categoryIds = productDetailVo.getCategoryTreePath().split(" ");
				if(categoryIds!=null && categoryIds.length>0) {
					StringBuilder categoryTreePathName = new StringBuilder();//商品分类全名包括父类节点名
					Long categoryId = null;
					ProductCategory pc = null;
					for(int i=0;i<categoryIds.length;i++) {
						categoryId = Long.valueOf(categoryIds[i]);
						pc = common.findCategoryById(productDetailVo.getClientId(), categoryId);
		                if(pc!=null && !pc.getIsDelete()){
		                	productDetailVo.setProductCategoryId(categoryId);//商品分类id
							productDetailVo.setProductCategoryName(pc.getName());//商品分类名称
		                	if(i>0) {
								categoryTreePathName.append("->");
							}
							categoryTreePathName.append(pc.getName());
		                } else {
		                	break;
		                }
					}
					productDetailVo.setCategoryTreePathName(categoryTreePathName.toString());
				}
			}
			
			Client c = common.getClientById(productDetailVo.getClientId());
        	if(c!=null){
        		productDetailVo.setClientName(c.getName());
        	}
        	//设置对应的机构名称
        	if(Checker.isNotEmpty(productDetailVo.getOrgCode())) {
                Org org = common.queryByOrgCode(productDetailVo.getClientId(), productDetailVo.getOrgCode());
                if(org!=null){
                	productDetailVo.setOrgName(org.getOrgName());
                }
            }
        	
        	Map<String,String> merchantMap = common.getMerchantRedis(productDetailVo.getClientId(), productDetailVo.getMerchantId());
        	if(Checker.isNotEmpty(merchantMap)){
        		//商户缓存数据
        		productDetailVo.setMerchantName(merchantMap.get("merchant_name"));
        	}
		}
	}
	
	private void attachMongoProperties(ProductDetailVo productDetailVo, ProductDetail pd) {
		if(productDetailVo.isLimit && pd.getBuyLimit()!=null){
            ProductBuyLimitVo buyLimit = new ProductBuyLimitVo();
            ProductBeanUtil.copyProperties(buyLimit, pd.getBuyLimit());
            productDetailVo.setBuyLimit(buyLimit);
        }
        List<ProductImage> images = pd.getImageInfo();
        if(images!=null && images.size()>0){
            List<ProductImageVo> imageVos = new ArrayList<ProductImageVo>();
            ProductImageVo imageVo = null;
            for(ProductImage imagePo : images){
                imageVo = new ProductImageVo();
                ProductBeanUtil.copyProperties(imageVo, imagePo);
                imageVos.add(imageVo);
            }
            productDetailVo.setImages(imageVos);
        }
	}
	
	@Override
	public ResultVo updateProductAuditState(List<EditProductAuditStateVo> productAuditStates)
			throws TException {
		LogCvt.debug("商品审核状态更改参数originVo:"+JSON.toJSONString(productAuditStates));
		
        ResultVo resultVo = new ResultVo();
        if(productAuditStates==null || productAuditStates.size()==0) {
        	resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("没有参数");
            return resultVo;
        }
        for(EditProductAuditStateVo productAuditState : productAuditStates) {
        	if(Checker.isEmpty(productAuditState.getProductId())) {
        		resultVo.setResultCode(ResultCode.failed.getCode());
                resultVo.setResultDesc("商品Id不能为空");
                return resultVo;
        	}
        	if(!ProductAuditState.noCommit.getCode().equals(productAuditState.getAuditState()) 
        			&& !ProductAuditState.noAudit.getCode().equals(productAuditState.getAuditState())) {
        		resultVo.setResultCode(ResultCode.failed.getCode());
                resultVo.setResultDesc("审核状态只能是待审核或未提交");
                return resultVo;
        	}
        }
        Result result = productLogic.updateProductAuditState(productAuditStates);
        ProductBeanUtil.copyProperties(resultVo, result);
        return resultVo;
	}

}
