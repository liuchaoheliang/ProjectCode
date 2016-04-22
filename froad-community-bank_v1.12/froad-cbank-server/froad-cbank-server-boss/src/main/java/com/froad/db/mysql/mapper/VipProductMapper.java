package com.froad.db.mysql.mapper;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

import com.froad.po.VipBindProductInfo;
import com.froad.po.VipProduct;

public interface VipProductMapper {
    
    /**
     * 根据vipId查询VIP规则
     * @param vipId
     * @return VipProduct
     */
    public VipProduct getVipProduct(String vipId);
    
    
    /**
     * vip规则列表查询
     * @param page
     * @param param
     * @return List<VipProduct>
     */
    public List<VipProduct> findVipProductByList(@Param(value = "vip")VipProduct vipProduct);
    
    
    /**
     * 保存绑定关系中间表
     * @param vipBindProductInfos
     * @return void
     */
    public void addVipBindProducts(List<VipBindProductInfo> vipBindProductInfos);
    
    /**
     * 更新Vip已经绑定商品数量
     * @param vipProduct
     * @return void
     */
    public void updateVipCount(VipProduct vipProduct);
    
    
    /**
     * 未生效VIP规则绑定的VIP价格和VIP限购信息
     * @param productId
     * @return VipBindProductInfo
     */
    public VipBindProductInfo findVipBindProduct(String productId);
    
    
    /**
     * 根据id查询VIP规则
     * @param vipId
     * @return VipProduct
     */
    public VipProduct getVipProductById(Long vipId);
    
    /**
     * 删除绑定关系的中间表
     * @param param
     * @return void
     */
    public void deleteProductFromVip(Map<String,Object> param);

}
