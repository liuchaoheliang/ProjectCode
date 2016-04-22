package com.froad.logic;

import java.util.List;

import com.froad.common.beans.ResultBean;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mysql.bean.Page;
import com.froad.po.BindVipInfo;
import com.froad.po.Product;
import com.froad.po.Result;
import com.froad.po.VipBindProductInfo;
import com.froad.po.VipProduct;
import com.froad.po.mongo.ProductDetail;

public interface VipProductLogic {
    
    /**
     * 新加VIP规则
     * @param vipProduct
     * @return Result
     */
    public ResultBean addVipProduct(VipProduct vipProduct);
    
    /**
     * 删除VIP规则
     * @param vipId
     * @return Result
     */
    public Result deleteVipProduct(String vipId);
    
    /**
     * 修改VIP规则
     * @param vipProduct
     * @return Result
     */
    public Result updateVipProduct(VipProduct vipProduct);
    
    /**
     * 获取某个客户端的VIP规则
     * @param clientId
     * @return VipProduct
     */
    public VipProduct getVipProduct(String clientId);
    
    /**
     * 获取某条VIP规则详情
     * @param vipId
     * @return VipProduct
     */
    public VipProduct getVipProductDetail(String vipId);
    
    /**
     * 获取VIP规则列表
     * @param page
     * @param vipProduct
     * @return List<VipProduct>
     */
    public List<VipProduct> getVipProductsByPage(Page<VipProduct> page, VipProduct vipProduct) throws Exception;
    
    /**
     * 获取某条VIP已经绑定的商品列表
     * @param page
     * @param vipId
     * @throws Exception
     * @return List<ProductDetail>
     */
    public List<ProductDetail> getProductsOfVipByPage(Page<VipBindProductInfo> page, String vipId) throws Exception;

    /**
     * 为某条VIP规则绑定商品
     * @param vipId
     * @param bindInfos
     * @return Result
     */
    public Result addProductsToVipProduct(String vipId,List<BindVipInfo> bindInfos);

    /**
     * 移除商品与某条VIP规则的绑定关系
     * @param vipId
     * @param productIds
     * @return Result
     */
    public Result removeProductsFromVipProduct(String vipId, List<String> productIds);
    
    /**
     * 查找可以绑定某条VIP规则的商品列表
     * @param vipId
     * @param name
     * @param priceStart
     * @param priceEnd
     * @param page
     * @return List<Product>
     */
    public List<Product> findProductsForVipByPage(String vipId, String name, double priceStart, double priceEnd, Page<Product> page) throws Exception;

    /**
     * 启用或作废vip规则
     * @param vipId
     * @param status 1启用,2作废
     * @return Result
     */
    public Result updateVipStatus(String vipId, String status);
    /**
     * 查找可以绑定的商品
     */
    public List<Product> findProductsForActivtyByPage(Long activtyId,String name, Page<Product> page);

}
