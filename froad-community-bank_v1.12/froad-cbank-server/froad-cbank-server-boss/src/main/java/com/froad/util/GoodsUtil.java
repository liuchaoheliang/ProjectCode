package com.froad.util;

import java.util.ArrayList;
import java.util.List;


public class GoodsUtil {

    /**
     * 是否满足绑定VIP规则
     * @return boolean
     */
    public static boolean isValidVipContition(String isMarketable,String type,String platType){

          //获取可以绑定VIP规则的指定商品类型
          List<String> types = getProductTypeVip();
          //获取指定平台新加的商品可以绑定VIP规则
          List<String> platTypes = getPlatTypeVip();
          //获取指定商品状态可以绑定VIP规则
          List<String> productStatuss = getProductStatusVip();
          
          if(productStatuss!=null && productStatuss.size()>0){//未上架的才可以绑定活动
              if(!productStatuss.contains(isMarketable)){
                  return false;
              }
          }
          if(types!=null && types.size()>0){//VIP活动只支持精品预售中的商品
              if(!types.contains(type)){
                  return false;
              }
          }
          if(platTypes!=null && platTypes.size()>0){//通过BOSS系统录入或通过银行管理端录入的预售商品均可被展示及绑定
              if(!platTypes.contains(platType)){
                  return false;
              }
          }
          return true;
    }
    
    /**
     * 获取可以绑定VIP规则的指定商品类型(商品类型逗号分隔,比如1,2)2预售
     * @return List<String>
     */
    private static List<String> getProductTypeVip(){
        String productTypeVips = GoodsConstants.GOODS_PRODUCT_TYPE_VIP;
        if(productTypeVips!=null){
            String[] productTypeVipArray = productTypeVips.split(",");
            if(productTypeVipArray.length>0){
                List<String> types = new ArrayList<String>();
                for(String type : productTypeVipArray){
                    if(type!=null && !"".equals(type)){
                        types.add(type);
                    }
                }
                return types;
            }
        }
        return null;
    }
    
    /**
     * 获取指定平台新加的商品可以绑定VIP规则(平台类型逗号分隔,比如1,2) boss 1, 银行端 2
     * @return List<String>
     */
    private static List<String> getPlatTypeVip(){
        String platTypeVips = GoodsConstants.GOODS_PLAT_TYPE_VIP;
        if(platTypeVips!=null){
            String[] platTypeVipArray = platTypeVips.split(",");
            if(platTypeVipArray.length>0){
                List<String> types = new ArrayList<String>();
                for(String type : platTypeVipArray){
                    if(type!=null && !"".equals(type)){
                        types.add(type);
                    }
                }
                return types;
            }
        }
        return null;
    }
    
    /**
     * 获取指定商品状态可以绑定VIP规则(商品上下架状态类型逗号分隔,比如0,1,2) 0未上架
     * @return List<String>
     */
    private static List<String> getProductStatusVip(){
        String productStatusVips = GoodsConstants.GOODS_ISMARKETABLE_VIP;
        if(productStatusVips!=null){
            String[] productStatusVipArray = productStatusVips.split(",");
            if(productStatusVipArray.length>0){
                List<String> statuss = new ArrayList<String>();
                for(String status : productStatusVipArray){
                    if(status!=null && !"".equals(status)){
                        statuss.add(status);
                    }
                }
                return statuss;
            }
        }
        return null;
    }
}
