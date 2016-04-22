namespace java com.froad.thrift.vo

/**
 * 门店详情概要信息
 */
struct OutletMongoInfoVo {
    
    /** 门店id */
    1:  optional string     id;                                                    
    
    /** 门店名称 */
    2:  optional string  outletName;
    /** 门店默认图片(小图) */
    3:  optional string defaultImage;                                                                   
                                                             
    /** 地址 */
    4: optional string  address;                                                                      
    
    /** 计算出的距离 */
    5: optional double dis;  
    
     /** 星级评价 */
    6: optional double starLevel;  
    
      /** 电话 */
    7: optional string phone;  
    
}
