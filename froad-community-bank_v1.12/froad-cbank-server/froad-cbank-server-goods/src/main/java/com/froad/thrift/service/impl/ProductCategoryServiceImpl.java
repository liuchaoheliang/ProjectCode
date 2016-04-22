/**
 * 
 * @Title: ProductCategoryImpl.java
 * @Package com.froad.thrift.service.impl
 * @see: 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;

import com.alibaba.fastjson.JSON;
import com.froad.db.mysql.bean.Page;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.ProductCategoryLogic;
import com.froad.logic.impl.ProductCategoryLogicImpl;
import com.froad.po.ProductCategory;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.ProductCategoryService;
import com.froad.thrift.vo.AddProductCategoryVoRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ProductCategoryPageVo;
import com.froad.thrift.vo.ProductCategoryVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.Checker;
import com.froad.util.GoodsConstants;
import com.froad.util.LogUtils;
import com.froad.util.ProductBeanUtil;


/**
 * 
 * <p>@Title: ProductCategoryImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class ProductCategoryServiceImpl extends BizMonitorBaseService implements ProductCategoryService.Iface {
	private ProductCategoryLogic productCategoryLogic = new ProductCategoryLogicImpl();
	public ProductCategoryServiceImpl() {}
	public ProductCategoryServiceImpl(String name, String version) {
        super(name, version);
    }


    /**
     * 增加 ProductCategory
     * @param productCategory
     * @return long    主键ID
     */
	@Override
	public AddProductCategoryVoRes addProductCategory(OriginVo originVo, ProductCategoryVo productCategoryVo) throws TException {
		LogCvt.info("OriginVo:"+JSON.toJSONString(originVo)+"添加ProductCategory"+productCategoryVo.toString());
		
		//添加操作日志记录
		originVo.setDescription("添加商品分类");
		LogUtils.addLog(originVo);
		
		AddProductCategoryVoRes addProductCategoryVoRes = new AddProductCategoryVoRes();
		ResultVo resultVo = new ResultVo();
		ProductCategory productCategory = new ProductCategory();
		ProductBeanUtil.copyProperties(productCategory, productCategoryVo);
		Long result = productCategoryLogic.addProductCategory(productCategory);
		if(result==null || result<1){
		    resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("新加商品分类失败");
		} else {
		    resultVo.setResultCode(ResultCode.success.getCode());
            resultVo.setResultDesc("新加商品分类成功");
            addProductCategoryVoRes.setId(result);
		}
		addProductCategoryVoRes.setResult(resultVo);
		return addProductCategoryVoRes;
	}



    /**
     * 删除 ProductCategory
     * @param productCategory
     * @return boolean    
     */
	@Override
	public ResultVo deleteProductCategory(OriginVo originVo, ProductCategoryVo productCategoryVo) throws TException {
		LogCvt.info("OriginVo:"+JSON.toJSONString(originVo)+"删除ProductCategory"+productCategoryVo.toString());
		
		//添加操作日志记录
		originVo.setDescription("删除商品分类");
		LogUtils.addLog(originVo);
		
        ResultVo resultVo = new ResultVo();
		ProductCategory productCategory = new ProductCategory();
		ProductBeanUtil.copyProperties(productCategory, productCategoryVo);
		if(productCategoryVo.getId()>0){
		    Result result = productCategoryLogic.deleteProductCategory(productCategory);
            ProductBeanUtil.copyProperties(resultVo, result);
		} else {
		    resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("删除商品分类id不能为空");
		}
        return resultVo;
	}



    /**
     * 修改 ProductCategory
     * @param productCategory
     * @return boolean    
     */
	@Override
	public ResultVo updateProductCategory(OriginVo originVo, ProductCategoryVo productCategoryVo) throws TException {
		LogCvt.info("OriginVo:"+JSON.toJSONString(originVo)+"修改ProductCategory"+productCategoryVo.toString());
		
		//添加操作日志记录
		originVo.setDescription("修改商品分类");
		LogUtils.addLog(originVo);
		
		ResultVo resultVo = new ResultVo();
		ProductCategory productCategory = new ProductCategory();
		ProductBeanUtil.copyProperties(productCategory, productCategoryVo);
		if(productCategoryVo.getId()>0){
		    Result result = productCategoryLogic.updateProductCategory(productCategory);
            ProductBeanUtil.copyProperties(resultVo, result);
        } else {
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("修改商品分类id不能为空");
        }
        return resultVo;
	}



    /**
     * 查询 ProductCategory
     * @param productCategory
     * @return List<ProductCategoryVo>
     */
	@Override
	public ProductCategoryVo getProductCategoryById(ProductCategoryVo productCategoryVo) throws TException {
		LogCvt.info("查询ProductCategory"+productCategoryVo.toString());
		ProductCategoryVo vo = new ProductCategoryVo();
		ProductCategory productCategory = new ProductCategory();
		ProductBeanUtil.copyProperties(productCategory, productCategoryVo);
		ProductCategory po = productCategoryLogic.getProductCategoryById(productCategory);
		if (po!=null) {
		    ProductBeanUtil.copyProperties(vo, po);
		}
		return vo;
	}


    @Override
    public boolean isProductCategoryExist(ProductCategoryVo productCategoryVo)
            throws TException {
        LogCvt.info("判断ProductCategory是否已经存在"+productCategoryVo.toString());
        ProductCategory productCategory = new ProductCategory();
        ProductBeanUtil.copyProperties(productCategory, productCategoryVo);
        return productCategoryLogic.isProductCategoryExist(productCategory);
    }


    @Override
    public ProductCategoryPageVo findCategorysByPage(
            ProductCategoryVo productCategoryVo, PageVo pageVo) throws TException {
        LogCvt.info("查询ProductCategory by page"+productCategoryVo.toString()+pageVo.toString());
        ProductCategory productCategory = new ProductCategory();
        Page<ProductCategory> page = new Page<ProductCategory>();
        ProductBeanUtil.copyProperties(productCategory, productCategoryVo);
        ProductBeanUtil.copyProperties(page, pageVo);
        List<ProductCategory> productCategoryList = productCategoryLogic.findCategorysByPage(page,productCategory);
        List<ProductCategoryVo> productCategoryVoList = new ArrayList<ProductCategoryVo>();
        ProductCategoryPageVo pcPageVo = new ProductCategoryPageVo();
        if (productCategoryList!=null && productCategoryList.size()>0) {
            ProductCategoryVo vo = null;
            for (ProductCategory po : productCategoryList) {
                vo = new ProductCategoryVo();
                ProductBeanUtil.copyProperties(vo, po);
                productCategoryVoList.add(vo);
            }
        }
        pcPageVo.setCategoryVoList(productCategoryVoList);
        pageVo.setPageCount(page.getPageCount());
        pageVo.setTotalCount(page.getTotalCount());
        pcPageVo.setPage(pageVo);
        return pcPageVo;
    }


    @Override
    public ProductCategoryPageVo queryProductCategorys(
            ProductCategoryVo productCategoryVo, PageVo pageVo)
            throws TException {
        LogCvt.debug("查询ProductCategory by page"+productCategoryVo.toString()+pageVo.toString());
        ProductCategory productCategory = new ProductCategory();
        Page<ProductCategory> page = new Page<ProductCategory>();
        ProductBeanUtil.copyProperties(productCategory, productCategoryVo);
        ProductBeanUtil.copyProperties(page, pageVo);
        List<ProductCategory> productCategoryList = productCategoryLogic.findCategorysByPage(page,productCategory);
        if(productCategoryVo.getParentId()==0 && productCategoryList!=null && productCategoryList.size()==1){
            productCategory.setParentId(productCategoryList.get(0).getId());
            productCategoryList = productCategoryLogic.findCategorysByPage(page,productCategory);
        }
        
        List<ProductCategoryVo> productCategoryVoList = new ArrayList<ProductCategoryVo>();
        ProductCategoryPageVo pcPageVo = new ProductCategoryPageVo();
        if (productCategoryList!=null && productCategoryList.size()>0) {
            ProductCategoryVo vo = null;
            for (ProductCategory po : productCategoryList) {
                vo = new ProductCategoryVo();
                ProductBeanUtil.copyProperties(vo, po);
                productCategoryVoList.add(vo);
            }
        }
        pcPageVo.setCategoryVoList(productCategoryVoList);
        pageVo.setPageCount(page.getPageCount());
        pageVo.setTotalCount(page.getTotalCount());
        pcPageVo.setPage(pageVo);
        return pcPageVo;
    }


    @Override
    public List<ProductCategoryVo> findCategorys(String clientId)
            throws TException {
        LogCvt.info("查询findCategorys列表，clientId:"+clientId);
        List<ProductCategoryVo> productCategoryVoList = new ArrayList<ProductCategoryVo>();
        if(clientId!=null && !"".equals(clientId)){
            List<ProductCategory> productCategoryList = productCategoryLogic.findCategorys(clientId);
            if (productCategoryList!=null && productCategoryList.size()>0) {
                ProductCategoryVo vo = null;
                for (ProductCategory po : productCategoryList) {
                    vo = new ProductCategoryVo();
                    ProductBeanUtil.copyProperties(vo, po);
                    productCategoryVoList.add(vo);
                }
            }
        }
        return productCategoryVoList;
    }


    @Override
    public List<AddProductCategoryVoRes> addProductCategoryBatch(OriginVo originVo, 
            List<ProductCategoryVo> productCategoryVos) throws TException {
        LogCvt.info("OriginVo:"+JSON.toJSONString(originVo)+"批量添加ProductCategory");
        
        //添加操作日志记录
		originVo.setDescription("批量添加商品分类");
		LogUtils.addLog(originVo);
        
        List<ProductCategory> productCategorys = new ArrayList<ProductCategory>();
        ProductCategory productCategory = null;
        for(ProductCategoryVo productCategoryVo :productCategoryVos){
            productCategory = new ProductCategory();
            ProductBeanUtil.copyProperties(productCategory, productCategoryVo);
            productCategorys.add(productCategory);
        }
        return productCategoryLogic.addProductCategoryBatch(productCategorys);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductCategoryVo> queryH5ProductCategorys(String clientId, long parentId, long areaId) throws TException {
		LogCvt.debug("查询商品分类--特惠商品分类首页商品分类查询-H5用户端,clientId:"+clientId+",parentId:"+parentId+",areaId:"+areaId);
		List<ProductCategoryVo> productCategoryVoList = new ArrayList<ProductCategoryVo>();
		if(Checker.isNotEmpty(clientId)){
			Map<String,Object> categorys = productCategoryLogic.queryProductCategorys(clientId, parentId, areaId);
	        List<ProductCategory> productCategoryList = (List<ProductCategory>)categorys.get("categorys");
	        Map<Long,Long> childNum = (Map<Long,Long>)categorys.get("childNum");
			
	        if (productCategoryList!=null && productCategoryList.size()>0) {
	            ProductCategoryVo vo = null;
	            for (ProductCategory po : productCategoryList) {
	                vo = new ProductCategoryVo();
	                ProductBeanUtil.copyProperties(vo, po);
	                if(childNum!=null && childNum.get(po.getId())!=null && childNum.get(po.getId())>0){
	                	vo.setIsHaveChild(true);
	                } else {
	                	vo.setIsHaveChild(false);
	                }
	                productCategoryVoList.add(vo);
	            }
	        }
		}
		return productCategoryVoList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductCategoryVo> queryManageProductCategorys(String clientId, long parentId) throws TException {
		LogCvt.debug("分级查询商品分类--新加特惠商品分类商户管理平台用到,clientId:"+clientId+",parentId:"+parentId);
		List<ProductCategoryVo> productCategoryVoList = new ArrayList<ProductCategoryVo>();
		if(Checker.isNotEmpty(clientId)){
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("clientId", clientId);
			param.put("parentId", parentId);
			param.put("isMall", false);
			Map<String,Object> categorys = productCategoryLogic.getManageProductCategorys(param);
	        List<ProductCategory> productCategoryList = (List<ProductCategory>)categorys.get("categorys");
	        Map<Long,Long> childNum = (Map<Long,Long>)categorys.get("childNum");
			
	        if (productCategoryList!=null && productCategoryList.size()>0) {
	            ProductCategoryVo vo = null;
	            for (ProductCategory po : productCategoryList) {
	                vo = new ProductCategoryVo();
	                ProductBeanUtil.copyProperties(vo, po);
	                if(childNum!=null && childNum.get(po.getId())!=null && childNum.get(po.getId())>0){
	                	vo.setIsHaveChild(true);
	                } else {
	                	vo.setIsHaveChild(false);
	                }
	                productCategoryVoList.add(vo);
	            }
	        }
		}
		return productCategoryVoList;
	}
	
	@Override
	public List<ProductCategoryVo> queryBoutiqueGoodsCategorys(String clientId,
			long parentId) throws TException {
		LogCvt.debug("H5精品商城商品分类查询,clientId:"+clientId+",parentId:"+parentId);
		List<ProductCategoryVo> productCategoryVoList = new ArrayList<ProductCategoryVo>();
		if(Checker.isNotEmpty(clientId)){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("clientId", clientId);
			param.put("parentId", parentId);
			param.put("isMall", true);//是精品商城的分类
			List<ProductCategory> productCategoryList = productCategoryLogic.getGoodsCategorys(param);
	        if (productCategoryList!=null && productCategoryList.size()>0) {
	            ProductCategoryVo vo = null;
	            for (ProductCategory po : productCategoryList) {
	                vo = new ProductCategoryVo();
	                ProductBeanUtil.copyProperties(vo, po);
	                productCategoryVoList.add(vo);
	            }
	        }
		}
		return productCategoryVoList;
	}
	
	@Override
	public List<ProductCategoryVo> queryRecommendProductCategorys(String clientId,
			boolean isMall) throws TException {
		LogCvt.debug("H5推荐类目的商品分类查询,clientId:"+clientId+",isMall:"+isMall);
		List<ProductCategoryVo> productCategoryVoList = new ArrayList<ProductCategoryVo>();
		if(Checker.isNotEmpty(clientId)){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("clientId", clientId);
			param.put("isMall", isMall);//是否是精品商城的分类
			param.put("isMarket", true);//类目推荐的商品分类
			List<ProductCategory> productCategoryList = productCategoryLogic.getGoodsCategorys(param);
	        if (productCategoryList!=null && productCategoryList.size()>0) {
	        	//营销类目商品分类名称映射 餐饮，娱乐，家政，日用->'餐饮行业','休闲娱乐','洗衣家政','日用百货'
	        	//餐饮行业:餐饮,休闲娱乐:娱乐,洗衣家政:家政,日用百货:日用
	        	Map<String,String> cnames = new HashMap<String,String>();
	        	String recommendCategoryNames = GoodsConstants.GOODS_RECOMMEND_CATEGORY_NAME;
	        	if(Checker.isNotEmpty(recommendCategoryNames)){
	        		String[] recommendCategoryNameArray = recommendCategoryNames.split(",");
	        		if(recommendCategoryNameArray!=null && recommendCategoryNameArray.length>0){
	        			for(String cname : recommendCategoryNameArray){
	        				if(Checker.isNotEmpty(cname)){
	        					String[] cnameArray = cname.split(":");
	        					if(cnameArray!=null && cnameArray.length==2){
	        						cnames.put(cnameArray[0], cnameArray[1]);
	        					}
	        				}
	        			}
	        		}
	        	}
	             
	            ProductCategoryVo vo = null;
	            for (ProductCategory po : productCategoryList) {
	                vo = new ProductCategoryVo();
	                ProductBeanUtil.copyProperties(vo, po);
	                if(Checker.isNotEmpty(po.getBigIcoUrl())){
	                	vo.setIcoUrl(po.getBigIcoUrl());
	                }
	                if(cnames.get(po.getName())!=null){
	                	vo.setName(cnames.get(po.getName()));
	                }
	                productCategoryVoList.add(vo);
	            }
	        }
		}
		return productCategoryVoList;
	}

	@Override
	public List<ProductCategoryVo> getProductCategoryByIds(String clientId,
			List<String> categoryIds) throws TException {
		LogCvt.info("查询ProductCategory集合:" + JSON.toJSONString(categoryIds)
				+ ", clientId= " + clientId);
		List<String> ids = new ArrayList<String>();
		// 过滤重复的id
		for (String id : categoryIds) {
			if (ids.contains(id)) {
				continue;
			}
			ids.add(id);
		}
		List<ProductCategory> poList = productCategoryLogic
				.getProductCategoryByIds(clientId, ids);
		List<ProductCategoryVo> categoryList = new ArrayList<ProductCategoryVo>();
		if (poList != null && poList.size() > 0) {
			ProductCategoryVo category = null;
			for (ProductCategory productCategory : poList) {
				category = new ProductCategoryVo();
				ProductBeanUtil.copyProperties(category, productCategory);
				categoryList.add(category);
			}
		}
		return categoryList;
	}

}
