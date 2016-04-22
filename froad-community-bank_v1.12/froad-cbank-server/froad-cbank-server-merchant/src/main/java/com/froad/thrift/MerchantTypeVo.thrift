namespace java com.froad.thrift.vo


/**
 * 商户类型信息 
 
 * 团购、预售、面对面支付
 */
struct MerchantTypeVo {
    
    /** 主键ID */
    1: optional i64 id;
    /** 类型名称 */
    2: optional string typeName;
    /** 是否删除 */
    3: optional bool isDelete;
    
     /** 0-团购（团）-1直接优惠(折) 2-名优特惠 */
    4: optional string type;
    
    /** 客户端id */
    5: optional string clientId;
}
