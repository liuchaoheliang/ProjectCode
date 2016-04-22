package com.froad.logic;

import java.util.List;
import java.util.Map;

import com.froad.common.beans.AddResultBean;
import com.froad.db.mysql.bean.Page;
import com.froad.po.BossProductFilter;
import com.froad.po.Product;
import com.froad.po.AuditFilter;
import com.froad.po.ProductCategory;
import com.froad.po.ProductFilter;
import com.froad.po.ProductInfo;
import com.froad.po.ProductOperateLog;
import com.froad.po.ProductTemp;
import com.froad.po.Result;
import com.froad.po.VipProduct;
import com.froad.thrift.vo.BossProductInfoVo;

public interface BossProductLogic {
    
    /**
     * boss管理平台查询商品列表
     * @param page
     * @param bossProductFilter
     * @return List<Product>
     */
    public List<Product> findProductsByPage(Page<Product> page,BossProductFilter bossProductFilter);

    /**
     * 分页查询待审核商品
     */
	public List<ProductTemp> findAuditProductsByPage(ProductFilter productFilterVo, Page<ProductTemp> page);
	
	/**
	 * 新加商品
	 * @param productInfo
	 * @param roleId 操作人角色id
	 * @return AddResultBean
	 */
	public AddResultBean addProduct(ProductInfo productInfo,String roleId);
	
	/**
	 * 修改商品
	 * @param productInfoVo
	 * @param roleId 操作人角色id
	 * @return Result
	 */
	public Result updateProduct(BossProductInfoVo productInfoVo,String roleId);
	
	/**
	 * boss查询商品详情
	 * @param productId
	 * @return Map<String,Object>
	 */
	public Map<String,Object> getBossProductDetail(String productId);
	
	/**
	 * 删除商品
	 * @param productId
	 * @return Result
	 */
	public Result deleteProduct(String productId);
	
	/**
	 * 商品上下架
	 * @param productId
	 * @param marketableStatus
	 * @return Result
	 */
	public Result updateProductStatus(String productId, String marketableStatus);
	
    /**
     * 审核商品
     */
	public Result auditProduct(AuditFilter auditFilter);
	
    /**
     * 添加操作日志
     */
	public Result addProductLog(ProductOperateLog log);
    /**
     * 添加操作日志
     */
	public List<VipProduct> findVipProductByList(VipProduct vipProduct);
	
    /**
     * 查询商品分类对象
     * @param Long
     * @return String
     */
    public ProductCategory findProductCategoryById(Long id);
}
