namespace java com.froad.thrift.vo
/* 引入LocationVo.thrift文件 */
include "LocationVo.thrift"

/* 引入CategoryInfoVo.thrift文件 */
include "CategoryInfoVo.thrift"

/* 引入TypeInfoVo.thrift文件 */
include "TypeInfoVo.thrift"

/**
 * 门店详情
 */
struct OutletDetailVo {
    
    /** 门店id */
    1:  optional string     id;                                                    
    /** 创建时间 */
    2:  optional i64     createTime;                                                                  
    /** 客户端id */
    3:  optional string clientId;                                                               
    /** 商户id */
    4:  optional string  merchantId;
    /** 商户名称 */
    5:  optional string  merchantName;
    /** 区域id */
    6:  optional i64     areaId; 
    /** 上级区域id */
    7:  optional i64     parentAreaId; 
    /** 门店默认图片(小图) */
    8:  optional string defaultImage;                                                                   
    /** 树路径名称 */
    9:  optional string  treePathName;
    /** 经纬度 */
    10:  LocationVo.LocationVo location;
    /** 是否有效 */
    11:   optional bool    isEnable;   
    /** 门店名称 */
    12:  optional string  outletName;                                                                  
    /** 门店全名 */
    13:  optional string  outletFullname;                                                              
    /** 分类列表 */
    14:  optional list<CategoryInfoVo.CategoryInfoVo>     categoryInfo; 
    /** 类型列表 */
    15:  optional list<TypeInfoVo.TypeInfoVo>     typeInfo;                                                       
    /** 地址 */
    16: optional string  address;                                                                      
    /** 联系电话 */
    17: optional string  phone;                                                                        
    /** 描述 */
    18: optional string  description;                                                                        
    /** 优惠详情 */
    19: optional string  preferDetails; 
    /** 收藏总数 */
    20: optional i32 storeCount;
    /** 计算出的距离 */
    21: optional double dis;  
    
    /** 评论星级 */
    22: optional double starLevel;
    /** 1 星级 总数 */
    23: optional i32 oneLevelCount;
    /** 2 星级 总数 */
    24: optional i32 twoLevelCount;
    /** 3 星级 总数 */
    25: optional i32 threeLevelCount;
    /** 4 星级 总数 */
    26: optional i32 fourLevelCount;
    /** 5 星级 总数 */
    27: optional i32 fiveLevelCount;
}
