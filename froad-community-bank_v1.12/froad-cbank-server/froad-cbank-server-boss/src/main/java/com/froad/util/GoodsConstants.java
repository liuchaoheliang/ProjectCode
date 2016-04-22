package com.froad.util;

import java.util.Date;

/**
 * boss管理平台商品需要的常量类
 * @author wangyan
 *
 */
public class GoodsConstants {

    /**
     *  double int之间转换的倍数
     */
    public static final int DOUBLE_INTGER_CHANGE = 1000;
    
    /**
     * 未来40150425时间
     */
    public static final Date FUTURE_END_DATE= DateUtil.parse(DateUtil.DATE_FORMAT2,"40150425");
    
    /**
     * goods.properties配置文件名称
     */
    public static final String GOODS_PROPERTIES= "goods.properties";
    
    /**
     * 指定商品类型只能在指定平台新加(type1:platType1,platType2;type2:platType1,platType2;)4:1代表4在线积分兑换只能在1boss平台添加
     */
    public static final String GOODS_PLAT_TYPE_PRODUCT_TYPE = BossPropertiesUtil.getGoodsProperty().getProperty("goods.platType.productType");
    
    /**
     * 指定商品类型可以选择的配送方式(0送货上门,1网点自提,2配送或自提)比如1:1;2:0,1,2;3:0;4:0;5:1代表团购只能自提;预售可以自提或配送;名优特惠只能配送;在线积分只能配送;网点礼品只能自提
     */
    public static final String GOODS_PRODUCT_TYPE_DELIVERY_OPTION = BossPropertiesUtil.getGoodsProperty().getProperty("goods.productType.deliveryOption");
    
    /**
     * 指定商品类型可以绑定VIP规则(商品类型逗号分隔,比如1,2)2预售
     */
    public static final String GOODS_PRODUCT_TYPE_VIP = BossPropertiesUtil.getGoodsProperty().getProperty("goods.productType.VIP");
    /**
     * 指定平台新加的商品可以绑定VIP规则(平台类型逗号分隔,比如1,2) boss 1, 银行端 2
     */
    public static final String GOODS_PLAT_TYPE_VIP = BossPropertiesUtil.getGoodsProperty().getProperty("goods.platType.VIP");
    /**
     * 指定商品状态可以绑定VIP规则(商品上下架状态类型逗号分隔,比如0,1,2) 0未上架
     */
    public static final String GOODS_ISMARKETABLE_VIP = BossPropertiesUtil.getGoodsProperty().getProperty("goods.isMarketable.VIP");
    
}
