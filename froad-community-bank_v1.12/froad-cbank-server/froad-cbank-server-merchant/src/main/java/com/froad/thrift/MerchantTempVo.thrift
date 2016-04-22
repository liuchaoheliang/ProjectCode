namespace java com.froad.thrift.vo


/**
 * 商户修改审核变更表
 */
struct MerchantTempVo {

	/** 主键ID */
    1:  optional i64     id;
    /** 创建时间 */
    2:  optional i64     createTime;
    /** 客户端ID */
    3:  optional string clientId;
    /** 审核订单号 */
    4:  optional string  auditId;
    /** 商户ID */
    5:  optional string  merchantId;
    /** 联系人姓名 */
    6:  optional string  contactName;
    /** 商户分类ID */
    7:  optional i64     merchantCategoryId;
    /** 商户分类名称*/
    8:  optional string  merchantCategoryName;
    /** 商户类型ID，用逗号分隔 */
    9:  optional string  merchantTypeId;
    /** 商户类型名称，用逗号分隔 */
    10: optional string    merchantTypeName;
    /** 商户类型值，用逗号分隔*/
    11: optional string  merchantTypeValue;
    /** 法人姓名 */
    12: optional string  legalName;
    /** 法人证件号类型 */
    13: optional string  legalCredentType;
    /** 法人证件号 */
    14: optional string  legalCredentNo;
    /** 开户行机构号 */
    15: optional string  orgCode;
    /** 开户行机构名称 */
    16: optional string  orgName;
    /** 所属分行机构代码*/
    17: optional string  cityOrgCode;
    /** 所属分行机构名称 */
    18: optional string  cityOrgName;
    /** 所属网点机构代码 */
    19: optional string  countyOrgCode;
	/** 所属网点机构名称*/
    20: optional string  countyOrgName;
    /** 收款账户名 */
    21: optional string  accountName;
    /** 收款账户号*/
    22: optional string    acountNo;
    /** 登录账号 */
    23: optional string loginName;
    /** 登录人手机号 */
    24: optional string  loginMobile;
    /** 商户原始值  */
    25: optional string primeval;
     /** 商户名称 */
    26: optional string  merchantName;
    /** 联系人电话*/
    27: optional string    phone;
    /** 联系人手机 */
    28: optional string contactPhone;
    /** 营业执照 */
    29: optional string  license;
    /** 签约人  */
    30: optional string contractStaff;
    /** 是否外包[false=否,true=是] */
    31: optional bool isOutsource;
    /** 外包公司id */
    32: optional i64 companyId;
}
