namespace java com.froad.thrift.vo
/* 引入LocationVo.thrift文件 */
include "LocationVo.thrift"

/* 引入CategoryInfoVo.thrift文件 */
include "CategoryInfoVo.thrift"

/* 引入TypeInfoVo.thrift文件 */
include "TypeInfoVo.thrift"

/**
 * 门店详情概要信息
 */
struct OutletDetailSimpleInfoVo {
    
    /** 门店id */
    1:  optional string     id;                                                    
    
    /** 门店名称 */
    2:  optional string  outletName;
    /** 门店默认图片(小图) */
    3:  optional string defaultImage;                                                                   
                                                             
    /** 分类列表 */
    4:  optional list<CategoryInfoVo.CategoryInfoVo>     categoryInfo; 
    
       /** 类型列表 */
    5:  optional list<TypeInfoVo.TypeInfoVo>     typeInfo; 
    
    /** 地址 */
    6: optional string  address;                                                                      
    
    /** 计算出的距离 */
    7: optional double dis;  
    
    /** 星评 */
    8: optional string starLevel;  
    
    /** 优惠折扣码 */
    9: optional string  discountCode;

    /** 优惠折扣比 */
    10: optional string  discountRate;
    
}
