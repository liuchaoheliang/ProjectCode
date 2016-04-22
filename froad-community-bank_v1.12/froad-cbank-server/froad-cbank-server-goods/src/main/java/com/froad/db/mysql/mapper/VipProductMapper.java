package com.froad.db.mysql.mapper;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

import com.froad.db.mysql.bean.Page;
import com.froad.po.Product;
import com.froad.po.VipBindProductInfo;
import com.froad.po.VipProduct;

public interface VipProductMapper {
    
    /**
     * 增加VIP规则
     * @param product
     * @return
     */
    public void addVipProduct(VipProduct vipProduct);
    
    /**
     * 该客户端是否已经存在启用的VIP规则
     * @param clientId
     * @return VipProduct
     */
    public VipProduct isExistVipProduct(String clientId);
    
    /**
     * 根据vipId查询VIP规则
     * @param clientId
     * @return VipProduct
     */
    public VipProduct getVipProduct(String vipId);
    
    /**
     * 更新VIP规则状态
     * @param vipProduct
     * @return void
     */
    public void updateVipStatus(VipProduct vipProduct);
    
    /**
     * 更新商品表的vip_price
     * @param vipProduct
     * @return void
     */
    public void updateProductVipPrice(Map<String,Object> param);
    
    /**
     * 更新Vip已经绑定商品数量
     * @param vipProduct
     * @return void
     */
    public void updateVipCount(VipProduct vipProduct);
    
    /**
     * 重置Vip已经绑定商品数量
     * @param vipProduct
     * @return void
     */
    public void resetVipCount(VipProduct vipProduct);
    
    /**
     * 更新VIP规则
     * @param vipProduct
     * @return void
     */
    public void updateVipProduct(VipProduct vipProduct);
    
    /**
     * 根据客户端id查询单独一条启用的vip规则 
     * @param clientId
     * @return VipProduct
     */
    public VipProduct getClientVipProduct(String clientId);
    
    /**
     * vip规则分页查询
     * @param page
     * @param param
     * @return List<VipProduct>
     */
    public List<VipProduct> findVipProductByPage(Page<VipProduct> page,@Param(value = "vip")VipProduct vipProduct);
    
    /**
     * 查询可以绑定VIP规则的商品列表
     * @param page
     * @param param
     * @return List<Product>
     */
    public List<Product> findProductForVipByPage(Page<Product> page,@Param(value = "p")Map<String,Object> param);
    
    /**
     * 保存绑定关系中间表
     * @param vipBindProductInfos
     * @return void
     */
    public void addVipBindProducts(List<VipBindProductInfo> vipBindProductInfos);
    
    /**
     * 删除绑定关系的中间表
     * @param param
     * @return void
     */
    public void deleteProductsFromVip(Map<String,Object> param);
    
    /**
     * 查询绑定过的vip规则关系的中间表 
     * @param vipId
     * @return List<VipBindProductInfo>
     */
    public List<VipBindProductInfo> findVipBindProducts(String vipId);
    
    /**
     * 分页查询vip规则绑定关系的中间表
     * @param page
     * @param vipId
     * @return List<VipBindProductInfo>
     */
    public List<VipBindProductInfo> findVipBindProductsByPage(Page<VipBindProductInfo> page,@Param(value = "vipId")String vipId);
    
    /**
     * 查询可以绑定vip规则的商品
     * @param param
     * @return List<String>
     */
    public List<String> findValidProductsForVip(Map<String,Object> param);
    
    /**
     * 未生效VIP规则绑定的VIP价格和VIP限购信息
     * @param productId
     * @return VipBindProductInfo
     */
    public VipBindProductInfo findVipBindProduct(String productId);
    
    
    /**
     * 分页查询vip规则绑定关系的中间表
     * @param page
     * @param vipId
     * @return List<VipBindProductInfo>
     */
    public List<Product> findProductsForActivtyByPage(Page<Product> page,@Param(value = "t")Map<String,Object> param);

}
