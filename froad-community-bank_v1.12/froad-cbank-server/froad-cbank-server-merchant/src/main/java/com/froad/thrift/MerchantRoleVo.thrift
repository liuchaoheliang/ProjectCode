namespace java com.froad.thrift.vo


/**
 * 商户角色信息
 */
struct MerchantRoleVo {
    
    /** 主键ID */
    1: optional i64 id;
    /** 角色名称 */
    2: optional string name;
    /** 描述 */
    3: optional string description;
    /** 是否删除 */
    4: optional bool isDelete;
}
