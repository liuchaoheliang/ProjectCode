package com.froad.util;

import java.util.Date;

/**
 * 常量
 * @author wangyan
 *
 */
public class GoodsConstants {

    //常量
	/**
	 *  double int之间转换的倍数
	 */
	public static final int DOUBLE_INTGER_CHANGE = 1000;
	
    public static final String NORMAL_RESPONSE = "0000";
    
    public static final String ABNORMAL_RESPONSE = "9999";
    
    public static final String SUPPORT = "support";
    
    /**
     * 发送短信url
     */
    public static final String QRCODE_SMSURL = "qrcode.smsurl";
    
    public static final Date FUTURE_END_DATE= DateUtil.parse(DateUtil.DATE_FORMAT2,"40150425");
    
    /**
     * goods.properties配置文件名称
     */
    public static final String GOODS_PROPERTIES= "goods.properties";
    /**
     * goods的thrift.properties配置文件名称
     */
    public static final String THRIFT_PROPERTIES= "thrift.properties";
    /**
     * goods的monitor.properties配置文件名称
     */
    public static final String MONITOR_PROPERTIES= "monitor.properties";
    
    
    //thrift配置文件
    /**
     * 调用外围模块IP
     */
    public static final String THRIFT_SUPPORT_HOST = GoodsPropertiesUtil.getThriftProperty().getProperty("thrift.support.host");
    /**
     * 调用外围模块端口
     */
    public static final Integer THRIFT_SUPPORT_PORT = Integer.valueOf(GoodsPropertiesUtil.getThriftProperty().getProperty("thrift.support.port"));
    
    /**
     * 调用qrcode生成二维码服务的IP
     */
    public static final String THRIFT_QRCODE_HOST = GoodsPropertiesUtil.getThriftProperty().getProperty("thrift.qrcode.host");
    /**
     * 调用qrcode生成二维码服务的端口
     */
    public static final String THRIFT_QRCODE_PORT = GoodsPropertiesUtil.getThriftProperty().getProperty("thrift.qrcode.port");
    
    /**
     * 调用商品商户门店服务的IP
     */
    public static final String THRIFT_MERCHANT_HOST = GoodsPropertiesUtil.getThriftProperty().getProperty("thrift.merchant.host");
    /**
     * 调用商品商户门店服务的端口
     */
    public static final String THRIFT_MERCHANT_PORT = GoodsPropertiesUtil.getThriftProperty().getProperty("thrift.merchant.port");
    
    /**
     * activities模块thrift服务的IP
     */
    public static final String THRIFT_ACTIVITIES_HOST = GoodsPropertiesUtil.getThriftProperty().getProperty("thrift.activities.host");
    /**
     * activities模块thrift服务的端口
     */
    public static final String THRIFT_ACTIVITIES_PORT = GoodsPropertiesUtil.getThriftProperty().getProperty("thrift.activities.port");
    
    
    //监控配置文件
    /**
     * good_f2fgoodadd_面对面商品创建接口每次耗时监控项
     */
    public static final String MONITOR_GOODS_F2FGOOD_ADD_SPEND_TIME_ATTRID = GoodsPropertiesUtil.getMonitorProperty().getProperty("monitor.goods.f2fgoodadd.spendTime.attrId");

    /**
     * good_f2fgoodadd_面对面商品创建接口每次耗时值类型
     */
    public static final String MONITOR_GOODS_F2FGOOD_ADD_SPEND_TIME_TYPE = GoodsPropertiesUtil.getMonitorProperty().getProperty("monitor.goods.f2fgoodadd.spendTime.type");

    /**
     * good_exchangesearch_个人H5线下积分兑换商品每秒查询次数监控项
     */
    public static final String MONITOR_GOODS_EXCHANGESEARCH_SEARCHNUMBER_ATTRID = GoodsPropertiesUtil.getMonitorProperty().getProperty("monitor.goods.exchangesearch.searchNumber.attrId");

    /**
     * good_exchangesearch_个人H5线下积分兑换商品每秒查询次数值类型
     */
    public static final String MONITOR_GOODS_EXCHANGESEARCH_SEARCHNUMBER_TYPE = GoodsPropertiesUtil.getMonitorProperty().getProperty("monitor.goods.exchangesearch.searchNumber.type");

    /**
     * good_exchangesearch_个人H5线下积分兑换商品查询耗时监控项
     */
    public static final String MONITOR_GOODS_EXCHANGESEARCH_SPEND_TIME_ATTRID = GoodsPropertiesUtil.getMonitorProperty().getProperty("monitor.goods.exchangesearch.spendTime.attrId");

    /**
     * good_exchangesearch_个人H5线下积分兑换商品查询耗时值类型
     */
    public static final String MONITOR_GOODS_EXCHANGESEARCH_SPEND_TIME_TYPE = GoodsPropertiesUtil.getMonitorProperty().getProperty("monitor.goods.exchangesearch.spendTime.type");

    /**
     * good_goodssearch_个人H5商品每秒查询次数监控项
     */
    public static final String MONITOR_GOODS_GOODSSEARCH_SEARCHNUMBER_ATTRID = GoodsPropertiesUtil.getMonitorProperty().getProperty("monitor.goods.goodssearch.searchNumber.attrId");

    /**
     * good_goodssearch_个人H5商品每秒查询次数值类型
     */
    public static final String MONITOR_GOODS_GOODSSEARCH_SEARCHNUMBER_TYPE = GoodsPropertiesUtil.getMonitorProperty().getProperty("monitor.goods.goodssearch.searchNumber.type");

    /**
     * good_goodssearch_个人H5商品查询耗时监控项
     */
    public static final String MONITOR_GOODS_GOODSSEARCH_SPEND_TIME_ATTRID = GoodsPropertiesUtil.getMonitorProperty().getProperty("monitor.goods.goodssearch.spendTime.attrId");

    /**
     * good_goodssearch_个人H5商品查询耗时值类型
     */
    public static final String MONITOR_GOODS_GOODSSEARCH_SPEND_TIME_TYPE = GoodsPropertiesUtil.getMonitorProperty().getProperty("monitor.goods.goodssearch.spendTime.type");
    
    
    //goods配置文件
    /**
     * 热销商品 数量标准
     */
    public static final String HOT_SELLl_COUNT = GoodsPropertiesUtil.getGoodsProperty().getProperty("hot.sellCounct");
    /**
     * boss平台添加商品不需要审核平台代码 1:boss(都好分隔)
     */
    public static final String NO_NEED_AUDIT_PLAT_CODES = GoodsPropertiesUtil.getGoodsProperty().getProperty("goods.noNeedAuditPlatCode");
    
    /**
     * 指定商品类型只能在指定平台新加(type1:platType1,platType2;type2:platType1,platType2;)4:1代表4在线积分兑换只能在1boss平台添加
     */
    public static final String GOODS_PLAT_TYPE_PRODUCT_TYPE = GoodsPropertiesUtil.getGoodsProperty().getProperty("goods.platType.productType");
    
//    /**
//     * 指定商品类型有关联活动(商品类型逗号分隔)
//     */
//    public static final String GOODS_PRODUCT_TYPE_ACTIVITIES = GoodsPropertiesUtil.getGoodsProperty().getProperty("goods.productType.activities");
    
//    /**
//     * 指定商品类型可以根据区域查询逗号分隔比如1,2,3,4,5
//     */
//    public static final String GOODS_AREA_PRODUCT_PRODUCT_TYPE = GoodsPropertiesUtil.getGoodsProperty().getProperty("goods.areaProduct.productType");
    
    /**
     * 指定商品类型可以绑定VIP规则(商品类型逗号分隔,比如1,2)2预售
     */
    public static final String GOODS_PRODUCT_TYPE_VIP = GoodsPropertiesUtil.getGoodsProperty().getProperty("goods.productType.VIP");
    /**
     * 指定平台新加的商品可以绑定VIP规则(平台类型逗号分隔,比如1,2) boss 1, 银行端 2
     */
    public static final String GOODS_PLAT_TYPE_VIP = GoodsPropertiesUtil.getGoodsProperty().getProperty("goods.platType.VIP");
    /**
     * 指定商品状态可以绑定VIP规则(商品上下架状态类型逗号分隔,比如0,1,2) 0未上架
     */
    public static final String GOODS_ISMARKETABLE_VIP = GoodsPropertiesUtil.getGoodsProperty().getProperty("goods.isMarketable.VIP");
    
    /**
     * 指定商品类型可以绑定送积分活动(商品类型逗号分隔,比如1,2)2预售
     */
    public static final String GOODS_PRODUCT_TYPE_POINTS = GoodsPropertiesUtil.getGoodsProperty().getProperty("goods.productType.points");
    /**
     * 指定平台新加的商品可以绑定送积分活动(平台类型逗号分隔,比如1,2) boss 1, 银行端 2
     */
    public static final String GOODS_PLAT_TYPE_POINTS = GoodsPropertiesUtil.getGoodsProperty().getProperty("goods.platType.points");
    /**
     * 指定商品状态可以绑定VIP规则(商品上下架状态类型逗号分隔,比如0,1,2) 0未上架
     */
    public static final String GOODS_ISMARKETABLE_POINTS = GoodsPropertiesUtil.getGoodsProperty().getProperty("goods.isMarketable.points");
    
    /**
     * 指定商品类型可以选择的配送方式(0送货上门,1网点自提,2配送或自提)比如1:1;2:0,1,2;3:0;4:0;5:1代表团购只能自提;预售可以自提或配送;名优特惠只能配送;在线积分只能配送;网点礼品只能自提
     */
    public static final String GOODS_PRODUCT_TYPE_DELIVERY_OPTION = GoodsPropertiesUtil.getGoodsProperty().getProperty("goods.productType.deliveryOption");
      
  
    
    
    /**
     * 特惠商品查询根据经纬度条件查询缓存有效时间,2代表2分钟
     */
    public static final String GOODS_GROUP_REDIS_LAT_CON_LOCK_TIME = GoodsPropertiesUtil.getGoodsProperty().getProperty("goods.group.redis.lat.con.lockTime");
    /**
     * 团购商品查询条件经常变缓存有效时间,5代表5分钟
     */
    public static final String GOODS_GROUP_REDIS_CHANGE_LOCK_TIME = GoodsPropertiesUtil.getGoodsProperty().getProperty("goods.group.redis.change.lockTime");
    /**
     * 团购商品查询条件不常变缓存有效时间,30代表30分钟
     */
    public static final String GOODS_GROUP_REDIS_STEADY_LOCK_TIME = GoodsPropertiesUtil.getGoodsProperty().getProperty("goods.group.redis.steady.lockTime");
    
    /**
     * 预售商品查询正在预售缓存有效时间,5代表5分钟
     */
    public static final String GOODS_PRESELL_REDIS_PRESELL1_LOCK_TIME = GoodsPropertiesUtil.getGoodsProperty().getProperty("goods.presell.redis.presell1.lockTime");
    /**
     * 预售商品查询下期预售缓存有效时间,30代表30分钟
     */
    public static final String GOODS_PRESELL_REDIS_PRESELL2_LOCK_TIME = GoodsPropertiesUtil.getGoodsProperty().getProperty("goods.presell.redis.presell2.lockTime");
    
    /**
     * 预售商品查询往期预售缓存有效时间,30代表30分钟
     */
    public static final String GOODS_PRESELL_REDIS_PRESELL3_LOCK_TIME = GoodsPropertiesUtil.getGoodsProperty().getProperty("goods.presell.redis.presell3.lockTime");
    /**
     * 名优特惠商品查询条件经常变缓存有效时间,5代表5分钟
     */
    public static final String GOODS_SPECIAL_REDIS_CHANGE_LOCK_TIME = GoodsPropertiesUtil.getGoodsProperty().getProperty("goods.special.redis.change.lockTime");
    /**
     * 名优特惠商品查询条件不常变缓存有效时间,30代表30分钟
     */
    public static final String GOODS_SPECIAL_REDIS_STEADY_LOCK_TIME = GoodsPropertiesUtil.getGoodsProperty().getProperty("goods.special.redis.steady.lockTime");
    
    /**
     * 在线积分商品查询缓存有效时间,30代表30分钟
     */
    public static final String GOODS_ONLINEPOINT_REDIS_LOCK_TIME = GoodsPropertiesUtil.getGoodsProperty().getProperty("goods.onlinePoint.redis.lockTime");
    
    /**
     * 线下礼品兑换商品查询缓存有效时间,30代表30分钟
     */
    public static final String GOODS_DOTGIFT_REDIS_LOCK_TIME = GoodsPropertiesUtil.getGoodsProperty().getProperty("goods.dotgift.redis.lockTime");
    
    /**
     * 下载商品每个sheet有多少行数 默认5万
     */
    public static final String GOODS_DOWN_ROWNUM = GoodsPropertiesUtil.getGoodsProperty().getProperty("goods.down.rownum");
    
    
    /**
     * 下载商品每个sheet有多少行数 默认5万
     */
    public static final String GOODS_DOWN_SEARCH_COUNT = GoodsPropertiesUtil.getGoodsProperty().getProperty("goods.down.search.count");
    
    /**
     * 指定商品类型销售期到期自动下架(商品类型逗号分隔,比如1,2)1团购,2预售
     */
    public static final String GOODS_SALE_END_OFF_PRODUCT_TYPE = GoodsPropertiesUtil.getGoodsProperty().getProperty("goods.saleEndOff.productType");
    
    /**
     * 商品分类有对应的商品缓存有效时间,2代表2分钟
     */
    public static final String PRODUCT_CATEGORY_GOODS_REDIS_LOCK_TIME = GoodsPropertiesUtil.getGoodsProperty().getProperty("productCategory.goods.redis.lockTime");
    
    /**
     * 营销类目商品分类名称映射 餐饮，娱乐，家政，日用->'餐饮行业','休闲娱乐','洗衣家政','日用百货'
     */
    public static final String GOODS_RECOMMEND_CATEGORY_NAME = GoodsPropertiesUtil.getGoodsProperty().getProperty("goods.recommend.category.name");
    
    /**
     * 指定商品类型可以满减活动(商品类型逗号分隔,比如1,2)1团购
     */
    public static final String GOODS_PRODUCT_TYPE_FULL_REDUCTION = GoodsPropertiesUtil.getGoodsProperty().getProperty("goods.productType.full.reduction");
    
    /**
     * 面对面生成二维码时候 二维码内容得是完整的url：(https://环境mp.ubank365.com/clientId/order/qrcode?type=xxxxxx&id=xxxxxxx)
     */
    public static final String OUTLET_PRODUCT_QRCODE_CONTENT_URL = GoodsPropertiesUtil.getGoodsProperty().getProperty("outletProduct.qrcode.content.url");
	
}
