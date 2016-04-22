package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.thrift.TException;

import com.alibaba.fastjson.JSON;
import com.froad.common.beans.AddResultBean;
import com.froad.db.mysql.bean.Page;
import com.froad.enums.DeliveryType;
import com.froad.enums.ProductType;
import com.froad.enums.ResultCode;
import com.froad.enums.VipStatus;
import com.froad.logback.LogCvt;
import com.froad.logic.BossProductLogic;
import com.froad.logic.CommonLogic;
import com.froad.logic.impl.BossProductLogicImpl;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.po.BossProductFilter;
import com.froad.po.Client;
import com.froad.po.Product;
import com.froad.po.ProductCategory;
import com.froad.po.ProductGroup;
import com.froad.po.ProductInfo;
import com.froad.po.ProductPresell;
import com.froad.po.Result;
import com.froad.po.VipProduct;
import com.froad.po.mongo.ProductBuyLimit;
import com.froad.po.mongo.ProductCityOutlet;
import com.froad.po.mongo.ProductDetail;
import com.froad.po.mongo.ProductImage;
import com.froad.po.mongo.ProductOutlet;
import com.froad.enums.ProductStatus;
import com.froad.po.AuditFilter;
import com.froad.po.ProductFilter;
import com.froad.po.ProductTemp;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.BossProductService;
import com.froad.thrift.vo.AddProductVoRes;
import com.froad.thrift.vo.BossAuditProcessVo;
import com.froad.thrift.vo.BossProductDetailVo;
import com.froad.thrift.vo.BossProductFilterVo;
import com.froad.thrift.vo.BossProductInfoVo;
import com.froad.thrift.vo.BossProductListPageVo;
import com.froad.thrift.vo.BossProductListVo;
import com.froad.thrift.vo.BossProductStatusVoReq;
import com.froad.thrift.vo.BossProductVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.ProductImageVo;
import com.froad.thrift.vo.ProductOutletVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.VipProductVo;
import com.froad.util.Arith;
import com.froad.util.Checker;
import com.froad.util.GoodsConstants;
import com.froad.util.LogUtils;
import com.froad.util.ProductBeanUtil;
import com.froad.util.Util;

public class BossProductServiceImpl extends BizMonitorBaseService implements BossProductService.Iface{
    
    /**
     * boss管理平台的商品logic
     */
    private BossProductLogic bossProductLogic = new BossProductLogicImpl();
    
    public BossProductServiceImpl() {}
    
    public BossProductServiceImpl(String name, String version) {
        super(name, version);
    }

    /**
     * boss管理平台查询商品列表
     */
	@Override
	public BossProductListPageVo findProductsByPage(
			BossProductFilterVo productFilterVo, PageVo pageVo)
			throws TException {
		// TODO Auto-generated method stub
	    long startTime = System.currentTimeMillis();
	    
	    LogCvt.info("boss管理平台查询商品列表参数,BossProductFilterVo:"+JSON.toJSONString(productFilterVo)+",pageVo:"+JSON.toJSONString(pageVo));
	    
	    BossProductListPageVo bossProductListPageVo = new BossProductListPageVo();
	    
	    try{
	        BossProductFilter filter = new BossProductFilter();
	        Page<Product> page = new Page<Product>();
	        ProductBeanUtil.copyProperties(page, pageVo);
	        ProductBeanUtil.copyProperties(filter, productFilterVo);
	        //logic返回
	        List<Product> productList = bossProductLogic.findProductsByPage(page,filter);
	        
	        List<BossProductListVo> productListVos = new ArrayList<BossProductListVo>();
	        if (!CollectionUtils.isEmpty(productList)) {
	            CommonLogic commonLogic = new CommonLogicImpl();
	            BossProductListVo vo = null;
	            for(Product po : productList){
	                vo = new BossProductListVo();
	                
	                vo.setId(po.getId());
	                vo.setCreateTime(po.getCreateTime().getTime());//创建时间
	                vo.setProductId(po.getProductId());//商品编号
	                vo.setName(po.getName());//商品名称
	                
	                if(Checker.isNotEmpty(po.getCategoryTreePath())){
	                    String[] categoryTreePaths = po.getCategoryTreePath().split(" ");
	                    ProductCategory pc = bossProductLogic.findProductCategoryById(Long.valueOf(categoryTreePaths[categoryTreePaths.length-1]));
	                    //LogCvt.info("11111111111111111"+categoryTreePaths[categoryTreePaths.length-1]+"222222");
	                    if(pc!=null){
	                        vo.setCategoryName(pc.getName());//商品分类名称
	                    }
	                }
	                vo.setType(po.getType());//商品类型 "1":团购,"2":预售,"3":名优特惠,"4":在线积分兑换,"5":网点礼品;
	                vo.setClientId(po.getClientId());// 客户端id
	                Client client = commonLogic.getClientById(po.getClientId());
	                if(client!=null){
	                    vo.setClientName(client.getName());//客户端名称即所属行
                        vo.setBankName(client.getBankName());//银行名称
	                }
	                vo.setPlatType(po.getPlatType());//录入渠道 "1":boss,"2":银行端,"3":商户pc,"4":商户h5,"5":个人pc,"6":个人h5;
	                vo.setAuditStatus(po.getAuditState());//审核状态
	                vo.setMarketableStatus(po.getIsMarketable());//上下架状态
	                productListVos.add(vo);
	            }
	        }
	        bossProductListPageVo.setProductListVos(productListVos);
	        
	        pageVo.setPageCount(page.getPageCount());
	        pageVo.setTotalCount(page.getTotalCount());
	            
	        if(pageVo.getPageCount()>pageVo.getPageNumber()){
	            pageVo.setHasNext(true);
	        } else {
	            pageVo.setHasNext(false);
	        }
	        bossProductListPageVo.setPage(pageVo);
	    }catch (Exception e) {
	        LogCvt.error("boss管理平台查询商品列表失败，原因:" + e.getMessage(),e);
	    }
	    long endTime = System.currentTimeMillis();
	    LogCvt.info("-------boss管理平台查询商品列表findProductsByPage:耗时"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);
	    return bossProductListPageVo;
	}

	/**
	 * boss管理平台新加商品
	 */
	@Override
	public AddProductVoRes addProduct(OriginVo originVo,
			BossProductInfoVo productInfoVo) throws TException {
//		long startTime = new Date().getTime();
//	    LogCvt.info("boss管理平台添加商品参数:"+"originVo:"+JSON.toJSONString(originVo)+",Product:"+JSON.toJSONString(productInfoVo));
	    AddProductVoRes resVo = new AddProductVoRes();
	    ResultVo result = new ResultVo();
	    resVo.setProductId(null);
	    try{
	        //设置平台参数 是boss平台还是银行机构平台还是商户平台等等
	        if(originVo==null){
	            result.setResultCode(ResultCode.failed.getCode());
                result.setResultDesc("操作人信息不能为空");
                resVo.setResult(result);
                return resVo;
	        }
	        if(originVo.getPlatType()==null){
                result.setResultCode(ResultCode.failed.getCode());
                result.setResultDesc("平台类型不能为空");
                resVo.setResult(result);
                return resVo;
            }
	        if(Checker.isEmpty(originVo.getRoleId())){
	            result.setResultCode(ResultCode.failed.getCode());
                result.setResultDesc("操作人角色id不能为空");
                resVo.setResult(result);
                return resVo;
	        }
	        if(PlatType.boss.getValue()!=originVo.getPlatType().getValue()){
	            result.setResultCode(ResultCode.failed.getCode());
                result.setResultDesc("只有boss管理平台才能调用该新加接口");
                resVo.setResult(result);
                return resVo;
	        }
	        String platType = originVo.getPlatType().getValue()+"";
	        //验证数据的合法性
            ResultVo validationResultVo = validationAddFileds(platType,productInfoVo);
            if(validationResultVo!=null){
                resVo.setResult(validationResultVo);
                return resVo;
            }
	        
	        //添加操作日志记录
	        String describe="";
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
	        originVo.setDescription("boss管理平台添加"+describe+"商品信息");
	        LogUtils.addLog(originVo);
	        
            //将前端传过来的vo转换成后端的po结构
            ProductInfo productInfo = this.changFromProductInfoVo(platType,productInfoVo);
            if(productInfo!=null){
                AddResultBean resultBean = bossProductLogic.addProduct(productInfo,originVo.getRoleId());
                if(resultBean!=null){
                    result.setResultCode(resultBean.getCode());
                    result.setResultDesc(resultBean.getMessage());
                    resVo.setProductId(resultBean.getId());
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
	        LogCvt.error("boss管理平台添加商品失败, 原因:" + e.getMessage(), e);
            result.setResultCode(ResultCode.failed.getCode());
            result.setResultDesc("添加商品失败");
	    }
//	    long endTime = new Date().getTime();
//        LogCvt.info("boss添加商品开始时间:"+startTime);
//        LogCvt.info("boss添加商品结束时间:"+endTime);
//        LogCvt.info("boss添加商品总用时时间(ms):"+(endTime-startTime));
	    return resVo;
	}
	
	/**
     * 新加商品验证商品基本字段.
     * @param productInfoVo
     * @param opeStr 新增还是修改的字符串
     * @return
     * @return ResultVo
     */
    private ResultVo validationAddFileds(String platType, BossProductInfoVo productInfoVo){
        ResultVo resultVo = null;
        if(productInfoVo.getProduct()==null){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品基础信息不能为空");
            return resultVo;
        }
        BossProductVo productVo = productInfoVo.getProduct();
        if(productVo.getClientId()==null || "".equals(productVo.getClientId())){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("所属客户端不能为空");
            return resultVo;
        }
        if(productVo.getMaxVip()>0 && productVo.getVipPrice()==0.0){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("VIP价格不能为空");
            return resultVo;
        }
        if(productVo.getVipPrice()>0.0 && Checker.isEmpty(productVo.getVipId())){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("VIP规则id不能为空");
            return resultVo;
        }
        if(productVo.getType()==null || "".equals(productVo.getType())){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品类型不能为空");
            return resultVo;
        }
        //判断指定商品类型能否在指定的平台新加或修改
        boolean isValid = isValidTypeForPlatType(productVo.getType(),platType);
        if(isValid==false){
            resultVo = new ResultVo();
            ProductType productType = ProductType.getType(productInfoVo.getProduct().getType());
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc(productType.getDescribe()+"类型的商品只能在指定的平台上新增，新增商品失败");
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
        if(productVo.getVipPrice()>0.0 && productVo.getVipPrice()>productVo.getPrice()){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("VIP价格不能大于销售价");
            return resultVo;
        }
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
        return resultVo;
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
    
    private ProductInfo changFromProductInfoVo(String platType, BossProductInfoVo productInfoVo){
        ProductInfo productInfo = null;
        
        BossProductVo productVo = productInfoVo.getProduct();//商品基础信息
        if(productVo!=null && productVo.getType()!=null){
            //把productvo转成productInfo
            productInfo = new ProductInfo();
            Product product = new Product();
            //复制基础信息 double类型的价格乘以1000转换成int型存储在数据里
            ProductBeanUtil.copyPropertiesScale(product, productVo, GoodsConstants.DOUBLE_INTGER_CHANGE);
            
            product.setPlatType(platType);
            
            //限购处理逻辑
            ProductBuyLimit buyLimit = null;
            if(productVo.getMax()>0 || productVo.getMaxVip()>0){
                product.setIsLimit(true);
                buyLimit = new ProductBuyLimit();
                buyLimit.setMax(productVo.getMax());//普通限购
                buyLimit.setMaxVip(productVo.getMaxVip());//VIP限购
                if(productVo.getMax()>0){//普通限购有开始时间和结束时间
                    buyLimit.setStartTime(productVo.getStartTime());
                    buyLimit.setEndTime(productVo.getEndTime());
                }
            } else {
                product.setIsLimit(false);
            }
            productInfo.setBuyLimit(buyLimit);
            //vip价格复制
            if(productVo.getVipPrice()>0.0){
                product.setVipPrice(Arith.mul(productVo.getVipPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));
            }
            productInfo.setProduct(product);
            productInfo.setVipId(productVo.getVipId());
            
            if(productVo.getType().equals(ProductType.group.getCode())){//团购商品
                ProductGroup productGroup = new ProductGroup();
                ProductBeanUtil.copyProperties(productGroup, productVo);
                productInfo.setProductGroup(productGroup);
            } else if(productVo.getType().equals(ProductType.presell.getCode())){//预售商品
                ProductPresell productPresell = new ProductPresell();
                ProductBeanUtil.copyProperties(productPresell, productVo);
                productInfo.setProductPresell(productPresell);
            }
            
            //商品分类逻辑
            if(productVo.getCategoryId()>0){
                ProductCategory productCategory = new ProductCategory();
                productCategory.setId(productVo.getCategoryId());
                productInfo.setProductCategory(productCategory);
            }
            //商品图片处理逻辑
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
            //提货网点的门店信息处理逻辑
            if(productInfoVo.getOutletIds()!=null && productInfoVo.getOutletIds().size()>0){//门店信息
                List<ProductOutlet> productOutlets = new ArrayList<ProductOutlet>();
                List<String> outletIds = productInfoVo.getOutletIds();
                ProductOutlet productOutlet = null;
                for(String outletId : outletIds){
                    productOutlet = new ProductOutlet();
                    productOutlet.setOutletId(outletId);
                    productOutlets.add(productOutlet);
                }
                productInfo.setProductOutlets(productOutlets);
            }
            //前端提货网点所在的法人行社机构号列表
            productInfo.setOrgCodes(productInfoVo.getOrgCodes());
        }
        return productInfo;
    }

	@Override
	public ResultVo updateProduct(OriginVo originVo,
			BossProductInfoVo productInfoVo) throws TException {
		// TODO Auto-generated method stub
	    //LogCvt.info("boss管理平台修改商品参数,originVo:"+JSON.toJSONString(originVo)+",productInfoVo:"+JSON.toJSONString(productInfoVo));
	    ResultVo resultVo = new ResultVo();
	    try{
	        //设置平台参数 是boss平台还是银行机构平台还是商户平台等等
	        if(originVo==null){
	            resultVo.setResultCode(ResultCode.failed.getCode());
                resultVo.setResultDesc("操作人信息不能为空");
                return resultVo;
            }
            if(originVo.getPlatType()==null){
                resultVo.setResultCode(ResultCode.failed.getCode());
                resultVo.setResultDesc("平台类型不能为空");
                return resultVo;
            }
            if(Checker.isEmpty(originVo.getRoleId())){
                resultVo.setResultCode(ResultCode.failed.getCode());
                resultVo.setResultDesc("操作人角色id不能为空");
                return resultVo;
            }
            if(PlatType.boss.getValue()!=originVo.getPlatType().getValue()){
                resultVo.setResultCode(ResultCode.failed.getCode());
                resultVo.setResultDesc("只有boss管理平台才能调用该新加接口");
                return resultVo;
            }
            String platType = originVo.getPlatType().getValue()+"";
            
            //验证数据的合法性
            ResultVo validationResultVo = validationUpdateFileds(platType,productInfoVo);
            
            if(validationResultVo!=null){
                return validationResultVo;
            }
            
            //添加操作日志记录
            originVo.setDescription("boss修改商品信息");
            LogUtils.addLog(originVo);
            
            Result result = bossProductLogic.updateProduct(productInfoVo,originVo.getRoleId());
            ProductBeanUtil.copyProperties(resultVo, result);
	    } catch (Exception e) {
	        LogCvt.error("boss修改商品失败, 原因:" + e.getMessage(), e);
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("修改商品失败");
	    }
	    return resultVo;
	}
	
	/**
     * 验证商品基本字段.
     * @param productInfoVo
     * @return ResultVo
     */
    private ResultVo validationUpdateFileds(String platType, BossProductInfoVo productInfoVo){
        ResultVo resultVo = null;
        
        if(productInfoVo.getProduct()==null){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品基础信息不能为空");
            return resultVo;
        }
        BossProductVo productVo = productInfoVo.getProduct();
        
        if(productVo.getProductId()==null || "".equals(productVo.getProductId())){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("修改商品时productId不能为空");
            return resultVo;
        }
        
        //判断指定商品类型能否在指定的平台新加或修改
        boolean isValid = isValidTypeForPlatType(productInfoVo.getProduct().getType(),platType);
        if(isValid==false){
            resultVo = new ResultVo();
            ProductType productType = ProductType.getType(productInfoVo.getProduct().getType());
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc(productType.getDescribe()+"类型的商品只能在指定的平台上修改商品,修改商品失败");
            return resultVo;
        }
        if(productVo.getPrice()>0.0 && Util.getDoubleDecimalNum(productVo.getPrice())>2){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("销售价只能有2位有效小数");
            return resultVo;
        }
        if(productVo.getMarketPrice()>0.0 && Util.getDoubleDecimalNum(productVo.getMarketPrice())>2){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("市场价只能有2位有效小数");
            return resultVo;
        }
        if(Checker.isNotEmpty(productVo.getName()) && productVo.getName().length()>100){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品简称不能超过100个字符");
            return resultVo;
        }
        if(Checker.isNotEmpty(productVo.getFullName()) && productVo.getFullName().length()>200){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品全称不能超过200个字符");
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
        }
        //只要是自提的都限制
        if(productVo.getType().equals(ProductType.presell.getCode())){
            if(productVo.getDeliveryOption().equals(DeliveryType.take.toString()) || productVo.getDeliveryOption().equals(DeliveryType.home_or_take.toString())){
                if(productVo.getDeliveryMoney()>0.0 && Util.getDoubleDecimalNum(productVo.getDeliveryMoney())>2){
                    resultVo = new ResultVo();
                    resultVo.setResultCode(ResultCode.failed.getCode());
                    resultVo.setResultDesc("运费只能有2位有效小数");
                    return resultVo;
                }
            }
        }
        return resultVo;
    }
    
	@Override
	public BossProductDetailVo getBossProductDetail(String productId)
			throws TException {
		// TODO Auto-generated method stub
	    LogCvt.info("boss管理平台查看商品明细参数productId:"+productId);
	    
        //查询明细
	    Map<String,Object> map = bossProductLogic.getBossProductDetail(productId);
        
	    BossProductDetailVo productInfoVo = new BossProductDetailVo();
	    if(map!=null){
	        BossProductVo vo = new BossProductVo();
	        Product product = (Product)map.get("product");
	        //返回vo 查询出的po转换成vo,价格等 表中int的转换成vo的double类型需要除以1000
            ProductBeanUtil.copyPropertiesScale(vo, product, GoodsConstants.DOUBLE_INTGER_CHANGE);
            vo.setAuditStatus(product.getAuditState());
            vo.setMarketableStatus(product.getIsMarketable());
	        if(ProductType.presell.getCode().equals(product.getType())){//预售商品
	            ProductPresell productPresell = (ProductPresell)map.get("productPresell");
	            if(productPresell.getDeliveryStartTime()!=null){
	                vo.setDeliveryStartTime(productPresell.getDeliveryStartTime().getTime());
	            }
	            if(productPresell.getDeliveryEndTime()!=null){
	                vo.setDeliveryEndTime(productPresell.getDeliveryEndTime().getTime());
	            }
	            if(productPresell.getExpireStartTime()!=null){
	                vo.setExpireStartTime(productPresell.getExpireStartTime().getTime());
	            }
	            if(productPresell.getExpireEndTime()!=null){
	                vo.setExpireEndTime(productPresell.getExpireEndTime().getTime());
	            }
	        }
	        productInfoVo.setProduct(vo);
            //获取mongo中的商品多表关系的信息查出来设置到返回的商品中
	        ProductDetail pd = (ProductDetail)map.get("productDetail");
            getProductAttachProperties(productInfoVo,pd);
            CommonLogic common = new CommonLogicImpl();
            Client client = common.getClientById(vo.getClientId());
            if(client!=null){
                vo.setClientName(client.getName());
                vo.setBankName(client.getBankName());//银行名称
            }
            if(Checker.isNotEmpty(product.getCategoryTreePath())){
                String[] categoryIds = product.getCategoryTreePath().split(" ");
                ProductCategory pc = common.findCategoryById(vo.getClientId(), Long.valueOf(categoryIds[categoryIds.length-1]));
                if(pc!=null){
                    vo.setCategoryName(pc.getName());
                    vo.setCategoryId(pc.getId());
                }
            }
            VipProduct vipProduct = (VipProduct)map.get("vipProduct");
            if(vipProduct!=null){
               vo.setVipId(vipProduct.getVipId());
               vo.setVipName(vipProduct.getActivitiesName());
            }
	    }
		return productInfoVo;
	}
	
	/**
     * 获取mongo中的信息
     * @param productInfoVo
     * @return ProductDetail
     */
    private ProductDetail getProductAttachProperties(BossProductDetailVo productInfoVo,ProductDetail pd){
        if(pd!=null){
            //VIP价格 是否是vip价
            if(pd.getVipPrice()!=null && pd.getVipPrice()>0){
                productInfoVo.getProduct().setVipPrice(Arith.div(pd.getVipPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));
            }
            //获取限购信息
            if(pd.getBuyLimit()!=null){
                productInfoVo.getProduct().setMax(pd.getBuyLimit().getMax());
                productInfoVo.getProduct().setMaxVip(pd.getBuyLimit().getMaxVip());
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
                
                for(ProductCityOutlet cityOutlet : orgOutlets){
                    List<ProductOutlet> outlets = cityOutlet.getOrgOutlets();
                    for(ProductOutlet outlet : outlets){
                        outletVo = new ProductOutletVo();
                        ProductBeanUtil.copyProperties(outletVo, outlet);
                        outletVos.add(outletVo);
                    }
                }
                productInfoVo.setOutlets(outletVos);
            }
            //设置对应的法人行社机构信息
            productInfoVo.setOrgCodes(pd.getOrgCodes());
            if(pd.getBuyLimit()!=null){
                
            }
        }
        return pd;
    }

    /**
     * 审核商品
     */
	@Override
	public ResultVo auditProduct(OriginVo originVo, BossAuditProcessVo processVo)
			throws TException {
        // TODO Auto-generated method stub
        LogCvt.info("审核商品,BossAuditProcessVo:"+JSON.toJSONString(processVo));
        ResultVo result=new ResultVo();
        try{
        	AuditFilter auditFilter=new AuditFilter();
        	auditFilter.setUserId(processVo.getUserId());
        	auditFilter.setCreateTime(processVo.getAuditTime());
        	auditFilter.setRoleId(processVo.getRoleId());
        	auditFilter.setAuditState(processVo.getAuditState());
        	auditFilter.setAuditRemark(processVo.getAuditRemark());
        	auditFilter.setProductId(processVo.getProductId());        	
        	Result re=bossProductLogic.auditProduct(auditFilter);
        	result.setResultCode(re.getResultCode());
        	result.setResultDesc(re.getResultDesc());
        }catch(Exception e){
        	result.setResultCode(ResultCode.failed.getCode());
        	result.setResultDesc("审核商品失败");
        }
		return result;
	}
    /**
     * 分页查询待审核商品
     */
	@Override
	public BossProductListPageVo findAuditProductsByPage(
			BossProductFilterVo productFilterVoReq, PageVo pageVo)
			throws TException {
        // TODO Auto-generated method stub
        long startTime = System.currentTimeMillis();
        
        LogCvt.info("查询待审核商品管理里面的商品列表参数,productFilterVoReq:"+JSON.toJSONString(productFilterVoReq)+",pageVo:"+JSON.toJSONString(pageVo));
        BossProductListPageVo productPageVo = new BossProductListPageVo();
        try{
            ProductFilter product = new ProductFilter();
            Page<ProductTemp> page = new Page<ProductTemp>();
            ProductBeanUtil.copyProperties(page, pageVo);

            //已经删除的不查询出来
            product.setFilterStatuts(ProductStatus.isDeleted.getCode());
            product.setClientId(productFilterVoReq.getClientId());
            product.setType(productFilterVoReq.getType());
            product.setCategoryId(productFilterVoReq.getCategoryId());
            product.setAuditState(productFilterVoReq.getAuditStatus());
            product.setName(productFilterVoReq.getName());
            
            List<ProductTemp> productList = bossProductLogic.findAuditProductsByPage(product,page);
            List<BossProductListVo> productBriefInfoVoList = new ArrayList<BossProductListVo>();
            if (!CollectionUtils.isEmpty(productList)) {
            	BossProductListVo vo = null;
                for (ProductTemp po : productList) {
                    try{
                        vo = new BossProductListVo();
                        //po转vo属性
                        ProductBeanUtil.copyPropertiesScale(vo, po, GoodsConstants.DOUBLE_INTGER_CHANGE);
                        //添加到list中进行返回
                        productBriefInfoVoList.add(vo);
                    } catch (Exception e) {
                        // TODO: handle exception
                        LogCvt.error("商品管理分页查询list Product po转换vo失败，原因:" + e.getMessage(),e); 
                    }
                    
                }
            }
            productPageVo.setProductListVos(productBriefInfoVoList);
            pageVo.setPageCount(page.getPageCount());
            pageVo.setTotalCount(page.getTotalCount());
            if(pageVo.getPageCount()>pageVo.getPageNumber()){
                pageVo.setHasNext(true);
            } else {
                pageVo.setHasNext(false);
            }
            productPageVo.setPage(pageVo);
        }catch (Exception e) {
            LogCvt.error("待审核商品列表:Product by page失败，原因:" + e.getMessage(),e); 
        }
        long endTime = System.currentTimeMillis();
        LogCvt.info("-------findAuditProductsByPage:耗时"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);
        return productPageVo;
	}

	/**
	 * boss删除商品
	 */
    @Override
    public ResultVo deleteProduct(OriginVo originVo, String productId)
            throws TException {
        // TODO Auto-generated method stub
        LogCvt.info("boss删除商品参数,originVo:"+JSON.toJSONString(originVo)+",productId:"+productId);
        ResultVo resultVo = new ResultVo();
        try {
          //添加操作日志记录
            originVo.setDescription("boss删除商品信息");
            LogUtils.addLog(originVo);
            
            if(Checker.isNotEmpty(productId)){
                Result result = bossProductLogic.deleteProduct(productId);
                ProductBeanUtil.copyProperties(resultVo, result);
            } else {
                resultVo.setResultCode(ResultCode.failed.getCode());
                resultVo.setResultDesc("商品id不能为空");
            }
        } catch (Exception e) {
            LogCvt.error("boss删除商品失败, 原因:" + e.getMessage(), e);
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("删除商品失败");
        }
        return resultVo;
    }

    @Override
    public ResultVo updateProductStatus(OriginVo originVo,
            BossProductStatusVoReq productStatusVoReq) throws TException {
        // TODO Auto-generated method stub
        LogCvt.info("boss商品上下架参数originVo:"+JSON.toJSONString(originVo)+",productStatusVoReq:"+JSON.toJSONString(productStatusVoReq));
        
        //添加操作日志记录
        originVo.setDescription("boss修改商品上下架状态");
        LogUtils.addLog(originVo);
        
        ResultVo resultVo = new ResultVo();
        
        if(Checker.isNotEmpty(productStatusVoReq.getProductId()) && Checker.isNotEmpty(productStatusVoReq.getMarketableStatus())){
            Result result = bossProductLogic.updateProductStatus(productStatusVoReq.getProductId(), productStatusVoReq.getMarketableStatus());
            ProductBeanUtil.copyProperties(resultVo, result);
        } else {
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品id不能为空,上下架状态不能为空");
        }
        return resultVo;
    }

    @Override
    public List<VipProductVo> getVipProducts(String clientId) throws TException {
        // TODO Auto-generated method stub
        long startTime = System.currentTimeMillis();
        
        LogCvt.info("查询待审核商品管理里面的商品列表参数,clientId:"+JSON.toJSONString(clientId));
        List<VipProductVo> list=new ArrayList<VipProductVo>();
        try{     
        	VipProduct vip=new VipProduct();
        	vip.setClientId(clientId);
        	vip.setStatus(VipStatus.effect.getCode());
        	List<VipProduct> vipList = bossProductLogic.findVipProductByList(vip);
            if(vipList.size()==0){
            	vip.setStatus(VipStatus.ineffect.getCode());
            	vipList = bossProductLogic.findVipProductByList(vip);
            }
            if (!CollectionUtils.isEmpty(vipList)) {
            	VipProductVo vo = null;
                for (VipProduct po : vipList) {
                    try{
                    	vo = new VipProductVo();
                        //po转vo属性
                        ProductBeanUtil.copyPropertiesScale(vo, po, GoodsConstants.DOUBLE_INTGER_CHANGE);
                        //添加到list中进行返回
                        list.add(vo);
                    } catch (Exception e) {
                        // TODO: handle exception
                        LogCvt.error("vip查询list Product po转换vo失败，原因:" + e.getMessage(),e); 
                    }
                    
                }
            }
        }catch (Exception e) {
            LogCvt.error("vip列表:Product by list失败，原因:" + e.getMessage(),e); 
        }
        long endTime = System.currentTimeMillis();
        LogCvt.info("-------getVipProducts:耗时"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);
        return list;
    }

}
