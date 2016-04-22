namespace java com.froad.thrift.vo


/**
 * 账户账户信息Vo
 */
struct MerchantAccountVo {

	/** 主键ID */
    1: optional i64 id;
     /** 创建时间 */
    2: optional i64 createTime;
    /** 客户端ID */
    3: optional string clientId;
    /** 商户ID */
    4: optional string merchantId;
    /** 门店ID */
    5: optional string outletId;
    /** 账户名称 */
    6: optional string acctName;
    /** 账户号 */
    7: optional string acctNumber;
    /** 账户类型 */
    8: optional string acctType;
    /** 开户行 */
    9: optional string openingBank;
    /** 是否删除 */
    10: optional bool isDelete;
}
