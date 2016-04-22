/**
 * 
 * @Title: ProductSeckillImpl.java
 * @Package com.froad.thrift.service.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.thrift.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.thrift.TException;

import com.alibaba.fastjson.JSON;
import com.froad.common.beans.ResultBean;
import com.froad.db.mysql.bean.Page;
import com.froad.enums.ProductStatus;
import com.froad.enums.ResultCode;
import com.froad.enums.SeckillProductStatus;
import com.froad.logback.LogCvt;
import com.froad.logic.ProductLogic;
import com.froad.logic.ProductSeckillLogic;
import com.froad.logic.impl.ProductLogicImpl;
import com.froad.logic.impl.ProductSeckillLogicImpl;
import com.froad.po.ActivitiesInfo;
import com.froad.po.Product;
import com.froad.po.ProductCategory;
import com.froad.po.ProductSeckill;
import com.froad.po.ProductSeckillInfo;
import com.froad.po.ProductViewInfo;
import com.froad.po.mongo.ProductBuyLimit;
import com.froad.po.mongo.ProductDetail;
import com.froad.po.mongo.ProductImage;
import com.froad.po.mongo.ProductOutlet;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.ProductSeckillService;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ProductActivitiesVo;
import com.froad.thrift.vo.ProductFilterVoReq;
import com.froad.thrift.vo.ProductImageVo;
import com.froad.thrift.vo.ProductOutletVo;
import com.froad.thrift.vo.ProductSeckillInfoPageVo;
import com.froad.thrift.vo.ProductSeckillInfoVo;
import com.froad.thrift.vo.ProductSeckillPageVo;
import com.froad.thrift.vo.ProductSeckillVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.Arith;
import com.froad.util.GoodsConstants;
import com.froad.util.JSonUtil;
import com.froad.util.LogUtils;
import com.froad.util.ProductBeanUtil;


/**
 * 
 * <p>@Title: ProductSeckillImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class ProductSeckillServiceImpl extends BizMonitorBaseService implements ProductSeckillService.Iface {
	private ProductSeckillLogic productSeckillLogic = new ProductSeckillLogicImpl();
	private ProductLogic productLogic = new ProductLogicImpl();
	public ProductSeckillServiceImpl() {}


    /**
     * 增加 ProductSeckill
     * @param productSeckill
     * @return long    主键ID
     */
	@Override
	public ResultVo addProductSeckill(List<ProductSeckillVo> productSeckillVoList, OriginVo originVo) throws TException {
		LogCvt.info("originVo:"+JSON.toJSONString(originVo)+"添加秒杀商品:"+productSeckillVoList.toString());
		
		ResultVo resultVo = new ResultVo();
		try {
			//添加操作日志记录
			originVo.setDescription("添加秒杀商品");
			LogUtils.addLog(originVo);
			
			
			for (ProductSeckillVo productSeckillVo : productSeckillVoList) {
				ResultVo validationResultVo = validationFileds(productSeckillVo, "1");
	            if(validationResultVo!=null){
	                return validationResultVo;
	            }
			}
            
            //讲productSeckillvo转成productSeckillInfo
			List<ProductSeckill> productSeckillList = new ArrayList<ProductSeckill>();
			for (ProductSeckillVo productSeckillVo : productSeckillVoList) {
				ProductSeckill productSeckill = new ProductSeckill();
				ProductBeanUtil.copyPropertiesScale(productSeckill, productSeckillVo, GoodsConstants.DOUBLE_INTGER_CHANGE);
				
				productSeckillList.add(productSeckill);
			}
			
			ResultBean result = productSeckillLogic.addProductSeckill(productSeckillList);
			if(result != null){
				resultVo.setResultCode(result.getCode());
				resultVo.setResultDesc(result.getMsg());
			} else {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("添加秒杀商品失败");
			}
			
		} catch (Exception e) {
            LogCvt.error("添加秒杀商品失败, 原因:" + e.getMessage(), e);
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc(e.getMessage());
        }
		
		return resultVo;
	}



    /**
     * 删除 ProductSeckill
     * @param productSeckill
     * @return resultVo   
     */
	@Override
	public ResultVo deleteProductSeckill(ProductSeckillVo productSeckillVo, OriginVo originVo) throws TException {
		LogCvt.info("originVo:"+JSON.toJSONString(originVo)+"删除秒杀商品"+productSeckillVo.toString());
		
		ProductSeckill productSeckill = new ProductSeckill();
		ResultVo resultVo = new ResultVo();
		try {
			//添加操作日志记录
			originVo.setDescription("删除秒杀商品");
			LogUtils.addLog(originVo);
			
			if(productSeckillVo.getProductId()!=null && !"".equals(productSeckillVo.getProductId())){
				BeanUtils.copyProperties(productSeckill, productSeckillVo);
				ResultBean result = productSeckillLogic.deleteProductSeckill(productSeckill);
				if(result != null){
					resultVo.setResultCode(result.getCode());
					resultVo.setResultDesc(result.getMsg());
				} else {
					resultVo.setResultCode(ResultCode.failed.getCode());
					resultVo.setResultDesc("删除秒杀商品失败");
				}
				
			}else {
				 resultVo.setResultCode(ResultCode.failed.getCode());
				 resultVo.setResultDesc("productId不能为空");
			 }
		} catch (IllegalAccessException e) {
			LogCvt.error(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			LogCvt.error(e.getMessage(), e);
		} catch (Exception e) {
            LogCvt.error("删除商品秒杀失败, 原因:" + e.getMessage(), e);
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc(e.getMessage());
        }
		return resultVo;
	}



    /**
     * 修改 ProductSeckill
     * @param productSeckill
     * @return resultVo   
     */
	@Override
	public ResultVo updateProductSeckill(ProductSeckillVo productSeckillVo, OriginVo originVo) throws TException {
		LogCvt.info("originVo:"+JSON.toJSONString(originVo)+"修改秒杀商品:"+productSeckillVo.toString());
		ProductSeckill productSeckill = new ProductSeckill();
		ResultVo resultVo = new ResultVo();
		try {
			//添加操作日志记录
			originVo.setDescription("修改秒杀商品");
			LogUtils.addLog(originVo);
			
			if(productSeckillVo.getProductId()!=null && !"".equals(productSeckillVo.getProductId())){
				ProductBeanUtil.copyPropertiesScale(productSeckill, productSeckillVo, GoodsConstants.DOUBLE_INTGER_CHANGE);
				
				ResultBean result = productSeckillLogic.updateProductSeckill(productSeckill);
				if(result != null){
					resultVo.setResultCode(result.getCode());
					resultVo.setResultDesc(result.getMsg());
				} else {
					resultVo.setResultCode(ResultCode.failed.getCode());
					resultVo.setResultDesc("修改秒杀商品失败");
				}
			}else {
				 resultVo.setResultCode(ResultCode.failed.getCode());
				 resultVo.setResultDesc("productId不能为空");
			 }
		} catch (Exception e) {
            LogCvt.error("修改秒杀商品失败, 原因:" + e.getMessage(), e);
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc(e.getMessage());
        }
		return resultVo;
	}

    /**
     * 修改 上下架状态ProductSeckill
     * @param productSeckill
     * @return resultVo    
     */
	@Override
	public ResultVo updateProductSeckillStatus(ProductSeckillVo productSeckillVo, OriginVo originVo) throws TException {
		LogCvt.info("originVo:"+JSON.toJSONString(originVo)+"修改上下架状态ProductSeckill"+productSeckillVo.toString());
		ProductSeckill productSeckill = new ProductSeckill();
		ResultVo resultVo = new ResultVo();
		try {
			//添加操作日志记录
			originVo.setDescription("修改商品秒杀配置上下架状态");
			LogUtils.addLog(originVo);
			
			if(productSeckillVo.getProductId()!=null && !"".equals(productSeckillVo.getProductId())){
				BeanUtils.copyProperties(productSeckill, productSeckillVo);
				ResultBean result = productSeckillLogic.updateProductSeckillStatus(productSeckill);
				if(result != null){
					resultVo.setResultCode(result.getCode());
					resultVo.setResultDesc(result.getMsg());
				} else {
					resultVo.setResultCode(ResultCode.failed.getCode());
					resultVo.setResultDesc("修改秒杀商品上下架失败");
				}
			}else {
				 resultVo.setResultCode(ResultCode.failed.getCode());
				 resultVo.setResultDesc("productId不能为空");
			 }
		} catch (IllegalAccessException e) {
			LogCvt.error(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			LogCvt.error(e.getMessage(), e);
		} catch (Exception e) {
            LogCvt.error("修改商品秒杀配置上下架状态失败, 原因:" + e.getMessage(), e);
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc(e.getMessage());
        }
		return resultVo;
	}
	
	
    /**
     * 查询详情 ProductSeckill
     * @param productSeckillVo
     * @return productSeckillVo
     */
	@Override
	public ProductSeckillInfoVo getProductSeckillDetail(ProductSeckillVo productSeckillVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询详情ProductSeckill"+productSeckillVo.toString());
		ProductSeckill productSeckill = new ProductSeckill();
		ProductSeckillInfoVo productSeckillInfoVo = new ProductSeckillInfoVo();
		try {
			BeanUtils.copyProperties(productSeckill, productSeckillVo);
		} catch (IllegalAccessException e) {
			LogCvt.error(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			LogCvt.error(e.getMessage(), e);
		} 
		ProductSeckill po = productSeckillLogic.getProductSeckillDetail(productSeckill);
		if (po!=null) {
	        Product product = new Product();
	        product.setProductId(po.getProductId());
	        product.setType(po.getType());
	        product.setClientId(po.getClientId());
	        ProductViewInfo productViewInfo = productLogic.getProduct(product);
	        
	        if (productViewInfo!=null) {
	        	po.setName(productViewInfo.getName());
	        	po.setFullName(productViewInfo.getFullName());
	        	po.setMarketPrice(productViewInfo.getMarketPrice());
	        	po.setDeliveryStartTime(productViewInfo.getDeliveryStartTime());
	        	po.setDeliveryEndTime(productViewInfo.getDeliveryEndTime());
	        	po.setBriefIntroduction(productViewInfo.getBriefIntroduction());
	        	po.setDeliveryOption(productViewInfo.getDeliveryOption());
	        	po.setBuyKnow(productViewInfo.getBuyKnow());
	        	po.setIntroduction(productViewInfo.getIntroduction());
	        	po.setPhone(productViewInfo.getPhone());
	        	po.setDeliveryMoney(productViewInfo.getDeliveryMoney());
	        	
	        	ProductSeckillVo vo = new ProductSeckillVo();
	            ProductBeanUtil.copyPropertiesScale(vo, po, GoodsConstants.DOUBLE_INTGER_CHANGE);
	            productSeckillInfoVo.setProductSeckill(vo);
	            //获取mongo中的商品详情
	            setProductSeckillInfoProperties(productSeckillInfoVo,product);
	        }
		}
		return productSeckillInfoVo;
	}

	 /**
     * 
     * 获取mongo中的商品详情
     * @param productInfoVo
     * @param product
     * @return void
     */
    private void setProductSeckillInfoProperties(ProductSeckillInfoVo productSeckillInfoVo,Product product){
        ProductDetail pd = productLogic.getProductDetail(product,null);
        if(pd != null){
        	productSeckillInfoVo.getProductSeckill().setMerchantName(pd.getMerchantName());
        	
            List<ProductImage> images = pd.getImageInfo();
            if(images!=null && images.size()>0){
                List<ProductImageVo> image = new ArrayList<ProductImageVo>();
                ProductImageVo imageVo = null;
                for(ProductImage imagePo : images){
                    imageVo = new ProductImageVo();
                    ProductBeanUtil.copyProperties(imageVo, imagePo);
                    image.add(imageVo);
                }
                productSeckillInfoVo.setImage(image);
            }
            
            List<ProductOutlet> outlets = pd.getOutletInfo();
            if(outlets!=null && outlets.size()>0){
                List<ProductOutletVo> outletVos = new ArrayList<ProductOutletVo>();
                ProductOutletVo outletVo = null;
                for(ProductOutlet outlet : outlets){
                    outletVo = new ProductOutletVo();
                    ProductBeanUtil.copyProperties(outletVo, outlet);
                    outletVos.add(outletVo);
                }
                productSeckillInfoVo.setOutlet(outletVos);
            }
            
           
            productSeckillInfoVo.setOrgCodes(pd.getOrgCodes());
        }
    }

	 /**
     * 查询列表ProductSeckill
     * @param productSeckillVo
     * @param pageVo
     * @return ProductSeckillPageVo
     */
    @Override
    public ProductSeckillPageVo findSeckillByPage(
    		ProductFilterVoReq productFilterVoReq, PageVo pageVo) throws TException {
        // TODO Auto-generated method stub
        LogCvt.info("查询商品秒杀配置分页信息:ProductSeckill by page");
        ProductSeckill productSeckill = new ProductSeckill();
        ProductSeckillVo productSeckillVo = null;
        Page<ProductSeckill> page = new Page<ProductSeckill>();
        String filter = productFilterVoReq.getFilter();
        try {
        	if(filter!=null){
        		productSeckillVo = JSonUtil.toObject(filter, ProductSeckillVo.class);

        		BeanUtils.copyProperties(productSeckill, productSeckillVo);
        		
        		if(productSeckillVo.getStartTime() == 0){
        			productSeckill.setStartTime(null);
        		}
        		if(productSeckillVo.getEndTime() == 0){
        			productSeckill.setEndTime(null);
        		}
        	}

        	BeanUtils.copyProperties(page, pageVo);
            
        } catch (IllegalAccessException e) {
            LogCvt.error(e.getMessage());
        } catch (InvocationTargetException e) {
            LogCvt.error(e.getMessage());
        } 
        productSeckill.setClientId(productFilterVoReq.getClientId());
        List<ProductSeckill> productSeckillList = productSeckillLogic.findSeckillByPage(page,productSeckill);
        List<ProductSeckillVo> productSeckillVoList = new ArrayList<ProductSeckillVo>();
        ProductSeckillPageVo pcPageVo = new ProductSeckillPageVo();
        if (!CollectionUtils.isEmpty(productSeckillList)) {
            ProductSeckillVo vo = null;
            for (ProductSeckill po : productSeckillList) {
                vo = new ProductSeckillVo();
                ProductBeanUtil.copyPropertiesScale(vo, po, GoodsConstants.DOUBLE_INTGER_CHANGE);
                productSeckillVoList.add(vo);
            }
        }
        pcPageVo.setSeckillVoList(productSeckillVoList);
        pageVo.setPageCount(page.getPageCount());
        pageVo.setTotalCount(page.getTotalCount());
        if(pageVo.getPageCount() > pageVo.getPageNumber()){
        	pageVo.setHasNext(true);
        } else {
        	pageVo.setHasNext(false);
        }
        pcPageVo.setPage(pageVo);
        return pcPageVo;
    }

    private ProductSeckillInfo changFromProductSeckillInfoVo(ProductSeckillInfoVo productSeckillInfoVo){
        ProductSeckillInfo productSeckillInfo = null;
        ProductSeckillVo productSeckillVo = productSeckillInfoVo.getProductSeckill();
        if(productSeckillVo!=null){
            //讲productSeckillvo转成productSeckillInfo
            productSeckillInfo = new ProductSeckillInfo();
            ProductBeanUtil.copyProperties(productSeckillInfo, productSeckillInfoVo);
            ProductSeckill productSeckill = new ProductSeckill();
            ProductBeanUtil.copyPropertiesScale(productSeckill, productSeckillVo, GoodsConstants.DOUBLE_INTGER_CHANGE);
            productSeckillInfo.setProductSeckill(productSeckill);
            
            if(productSeckillInfoVo.getProductCategory()!=null){
                ProductCategory productCategory = new ProductCategory();
                ProductBeanUtil.copyProperties(productCategory, productSeckillInfoVo.getProductCategory());
                productSeckillInfo.setProductCategory(productCategory);
            }
            if(productSeckillInfoVo.getBuyLimit()!=null){
                ProductBuyLimit buyLimit = new ProductBuyLimit();
                ProductBeanUtil.copyProperties(buyLimit, productSeckillInfoVo.getBuyLimit());
                productSeckillInfo.setBuyLimit(buyLimit);
            }
            if(productSeckillInfoVo.getImage()!=null && productSeckillInfoVo.getImage().size()>0){
                List<ProductImageVo> images = productSeckillInfoVo.getImage();
                List<ProductImage> productImages = new ArrayList<ProductImage>();
                ProductImage productIamge = null;
                for(ProductImageVo image : images){
                    productIamge = new ProductImage();
                    ProductBeanUtil.copyProperties(productIamge, image);
                    productImages.add(productIamge);
                }
                productSeckillInfo.setProductImages(productImages);
            }
            if(productSeckillInfoVo.getOutlet()!=null && productSeckillInfoVo.getOutlet().size()>0){
                List<ProductOutlet> productOutlets = new ArrayList<ProductOutlet>();
                
                List<ProductOutletVo> productOutletVos = productSeckillInfoVo.getOutlet();
                ProductOutlet productOutlet = null;
                for(ProductOutletVo productOutletVo : productOutletVos){
                    productOutlet = new ProductOutlet();
                    ProductBeanUtil.copyProperties(productOutlet, productOutletVo);
                    productOutlets.add(productOutlet);
                }
                productSeckillInfo.setProductOutlets(productOutlets);
            }
            if(productSeckillInfoVo.getActivities()!=null && productSeckillInfoVo.getActivities().size()>0){
                
                List<ActivitiesInfo> productActivitePos = new ArrayList<ActivitiesInfo>();
                List<ProductActivitiesVo> productActiviteVos = productSeckillInfoVo.getActivities();
                ActivitiesInfo po = null;
                for(ProductActivitiesVo vo : productActiviteVos){
                    po = new ActivitiesInfo();
                    ProductBeanUtil.copyProperties(po, vo);
                    productActivitePos.add(po);
                }
                productSeckillInfo.setActivities(productActivitePos);
            }
            productSeckillInfo.setOrgCodes(productSeckillInfoVo.getOrgCodes());
        }
        return productSeckillInfo;
    }

    /**
     * 
     *验证秒杀商品配置基本字段.
     * @param productInfoVo
     * @param opeStr 新增还是修改的字符串
     * @return
     * @return ResultVo
     */
    private ResultVo validationFileds(ProductSeckillVo productSeckillVo,String opeStr){
        ResultVo resultVo = null;
        if(productSeckillVo == null){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("商品秒杀配置信息不能为空");
            return resultVo;
        }

        if(productSeckillVo.getClientId()==null || "".equals(productSeckillVo.getClientId())){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("所属客户端不能为空");
            return resultVo;
        }
        
        //opeStr操作类型:	1:新增 	2:更新
        if (opeStr.endsWith("1")) {
            if(productSeckillVo.getProductId()==null || "".equals(productSeckillVo.getProductId())){
                resultVo = new ResultVo();
                resultVo.setResultCode(ResultCode.failed.getCode());
                resultVo.setResultDesc("新增商品时productId不能为空");
                return resultVo;
            }

		} else {
	        if(ProductStatus.onShelf.toString().equals(productSeckillVo.getIsMarketable())){
	            resultVo = new ResultVo();
	            resultVo.setResultCode(ResultCode.failed.getCode());
	            resultVo.setResultDesc("已上架的商品不允许编辑");
	            return resultVo;
	        }
		}
        
        if(productSeckillVo.getSecPrice() <=0.0){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("秒杀价必填");
            return resultVo;
        }
        
        String s = String.valueOf(productSeckillVo.getSecPrice());
        if(s!=null && !"".equals(s) && s.substring(s.indexOf(".")+1, s.length()).length()>2){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("秒杀价只能有2位有效小数");
            return resultVo;
        }

        //vip价为0，值同秒杀价
        if(productSeckillVo.getVipSecPrice()<=0.0){
        	productSeckillVo.setVipSecPrice(productSeckillVo.getSecPrice());
        }
        
//        String s = String.valueOf(productSeckillVo.getVipSecPrice());
//        if(s!=null && !"".equals(s) && s.substring(s.indexOf(".")+1, s.length()).length()>2){
//            resultVo = new ResultVo();
//            resultVo.setResultCode(ResultCode.failed.getCode());
//            resultVo.setResultDesc("vip秒杀价只能有2位有效小数");
//            return resultVo;
//        }
        
        
        if(productSeckillVo.getSecStore()<1){
        	resultVo = new ResultVo();
        	resultVo.setResultCode(ResultCode.failed.getCode());
        	resultVo.setResultDesc("秒杀库存数量不能为空");
        	return resultVo;
        }
        
        if(productSeckillVo.getBuyLimit()<0){
        	resultVo = new ResultVo();
        	resultVo.setResultCode(ResultCode.failed.getCode());
        	resultVo.setResultDesc("秒杀限购数量不能为空");
        	return resultVo;
        }
        
        if(productSeckillVo.getStartTime()>productSeckillVo.getEndTime()){
            resultVo = new ResultVo();
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("秒杀开始时间不能大于结束时间");
            return resultVo;
        }
        return resultVo;
    }

	 /**
     * H5查询列表ProductSeckill
     * @param ProductFilterVoReq
     * @param pageVo
     * @return ProductSeckillInfoPageVo
     */
	@Override
	public ProductSeckillInfoPageVo findH5SeckillByPage(ProductFilterVoReq productFilterVoReq, PageVo pageVo) throws TException {
		LogCvt.info("H5查询商品秒杀配置分页信息:ProductSeckill by page");
        ProductSeckill productSeckill = new ProductSeckill();
        ProductSeckillVo productSeckillVo = null;
        Page<ProductSeckill> page = new Page<ProductSeckill>();
        String filter = productFilterVoReq.getFilter();
        List<ProductSeckill> productSeckillList = null;
        
        try {
        	if(filter!=null){
        		productSeckillVo = JSonUtil.toObject(filter, ProductSeckillVo.class);
        		BeanUtils.copyProperties(productSeckill, productSeckillVo);
        		
        		if(productSeckillVo.getStartTime() == 0){
        			productSeckill.setStartTime(null);
        		}
        		if(productSeckillVo.getEndTime() == 0){
        			productSeckill.setEndTime(null);
        		}
        		
        		if(productSeckillVo.getStatus() == null){
        			productSeckill.setStatus(SeckillProductStatus.ing.getCode());
        		}
        	}

        	productSeckill.setClientId(productFilterVoReq.getClientId());
            BeanUtils.copyProperties(page, pageVo);

        } catch (IllegalAccessException e) {
            LogCvt.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            LogCvt.error(e.getMessage(), e);
        }
        
        //如果查询类型为5，分别查询获取记录，顺序为 正在秒杀 -> 即将秒杀
        if (SeckillProductStatus.ing_and_soon.getCode().equals(productSeckill.getStatus())) {
			productSeckill.setStatus(SeckillProductStatus.ing.getCode());
			productSeckillList = productSeckillLogic.findH5SeckillByPage(page,productSeckill);
			if (productSeckillList.size() < page.getPageSize()) {
				int pageSize = page.getPageSize();
				page.setPageSize(page.getPageSize() - productSeckillList.size());
				productSeckill.setStatus(SeckillProductStatus.soon.getCode());
				List<ProductSeckill> productSeckillList2 = productSeckillLogic.findH5SeckillByPage(page,productSeckill);
				productSeckillList.addAll(productSeckillList2);
				page.setPageSize(pageSize);
				page.setTotalCount(productSeckillList.size());
			}
		} else {
			productSeckillList = productSeckillLogic.findH5SeckillByPage(page,productSeckill);
		}
        
        List<ProductSeckillInfoVo> productSeckillInfoVoList = new ArrayList<ProductSeckillInfoVo>();
        ProductSeckillInfoPageVo pcPageVo = new ProductSeckillInfoPageVo();
        if (!CollectionUtils.isEmpty(productSeckillList)) {
            ProductSeckillVo vo = null;
            for (ProductSeckill po : productSeckillList) {
                vo = new ProductSeckillVo();
                ProductBeanUtil.copyPropertiesScale(vo, po, GoodsConstants.DOUBLE_INTGER_CHANGE);
                ProductSeckillInfoVo productSeckillInfoVo = new ProductSeckillInfoVo();
                productSeckillInfoVo.setProductSeckill(vo);
                
    	        Product product = new Product();
    	        product.setProductId(po.getProductId());
    	        product.setType(po.getType());
    	        product.setClientId(po.getClientId());
                
    	        setH5ProductSeckillInfoProperties(productSeckillInfoVo,product);
                
                productSeckillInfoVoList.add(productSeckillInfoVo);
            }
        }
        pcPageVo.setSeckillInfoVoList(productSeckillInfoVoList);
        pageVo.setPageCount(page.getPageCount());
        pageVo.setTotalCount(page.getTotalCount());
        if(pageVo.getPageCount() > pageVo.getPageNumber()){
        	pageVo.setHasNext(true);
        } else {
        	pageVo.setHasNext(false);
        }
        pcPageVo.setPage(pageVo);
        
        return pcPageVo;
	}
 
	 /**
     * 
     * H5分页获取mongo中的商品详情
     * @param productInfoVo
     * @param product
     * @return void
     */
    private void setH5ProductSeckillInfoProperties(ProductSeckillInfoVo productSeckillInfoVo,Product product){
        ProductDetail pd = productLogic.getProductDetail(product,null);
        if(pd != null){
        	productSeckillInfoVo.getProductSeckill().setName(pd.getName());
        	productSeckillInfoVo.getProductSeckill().setMarketPrice(Arith.div(pd.getMarketPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));
        	productSeckillInfoVo.getProductSeckill().setFullName(pd.getFullName());
        	productSeckillInfoVo.getProductSeckill().setMerchantName(pd.getMerchantName());
        	productSeckillInfoVo.getProductSeckill().setBriefIntroduction(pd.getBriefIntroduction());
        	
        	//获取图片信息列表
            List<ProductImage> images = pd.getImageInfo();
            if(images!=null && images.size()>0){
                List<ProductImageVo> image = new ArrayList<ProductImageVo>();
                ProductImageVo imageVo = null;
                for(ProductImage imagePo : images){
                    imageVo = new ProductImageVo();
                    ProductBeanUtil.copyProperties(imageVo, imagePo);
                    image.add(imageVo);
                }
                productSeckillInfoVo.setImage(image);
            }
            
            //获取门店信息列表
            List<ProductOutlet> outlets = pd.getOutletInfo();
            if(outlets!=null && outlets.size()>0){
                List<ProductOutletVo> outletVos = new ArrayList<ProductOutletVo>();
                ProductOutletVo outletVo = null;
                for(ProductOutlet outlet : outlets){
                    outletVo = new ProductOutletVo();
                    ProductBeanUtil.copyProperties(outletVo, outlet);
                    outletVos.add(outletVo);
                }
                productSeckillInfoVo.setOutlet(outletVos);
            }
        }
    }
    
}
