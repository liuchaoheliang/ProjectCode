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
struct OutletDetailSimpleVo {
    
    /** 商户id */
    1:  optional string merchantId;      
    
       /** 商户名称 */
    2: optional string merchantName;      
    
       /** 门店Id */
    3: optional string outletId;                                            
    
    /** 门店名称 */
    4:  optional string  outletName;
    /** 门店默认图片(小图) */
    5:  optional string defaultImage;                                                                   
                                                             
    /** 分类列表 */
    6:  optional list<CategoryInfoVo.CategoryInfoVo>     categoryInfo; 
    
       /** 类型列表 */
    7:  optional list<TypeInfoVo.TypeInfoVo>     typeInfo; 
    
    /** 地址 */
    8: optional string  address;                                                                      
    
    /** 计算出的距离 */
    9: optional double dis;  
    
      /** 星评 */
    10: optional string starLevel;  
    
}
