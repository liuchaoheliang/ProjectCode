namespace java com.froad.thrift.vo


/**
 * 账户账户信息Vo
 */
struct MerchantBankWhiteVo {
    /** 商户ID、门店ID */
    1: optional string merchantIdOrOutletId;
    /** 商户Name、门店Name */
    2: optional string merchantNameOrOutletName;
    /** 商户账号*/
    3: optional string accountNo;
    /** 客户端ID*/
    4: optional string clientId;
    /** 账户名*/
    5: optional string accountName;
    /** 操作类型*/
    6: optional string optionType;
    /** mac值 */
    7: optional string mac;
}
