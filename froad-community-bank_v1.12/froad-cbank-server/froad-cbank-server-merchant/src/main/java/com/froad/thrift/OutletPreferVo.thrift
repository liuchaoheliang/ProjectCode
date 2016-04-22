namespace java com.froad.thrift.vo

/**
 * 门店惠付信息
 */
struct OutletPreferVo {

    /** 主键ID */
    1: optional i64 id;
    /** 创建时间 */
    2: optional i64 createTime;
    /** 客户端ID */
    3: optional string clientId;
    /** 商户ID */
    4: optional string merchantId;
    /** 被发展组织机构代码的省级机构 */
    5: optional string proOrgCode;
    /** 被发展组织机构代码的市级机构 */
    6: optional string cityOrgCode;
    /** 被发展组织机构代码的区级机构 */
    7: optional string countyOrgCode;
    /** 被发展组织机构代码 */
    8: optional string orgCode;
    /** 商户名 */
    9: optional string merchantName;
    /** 营业执照号码 */
    10: optional string license;
    /** 门店名称 */
    11: optional string  outletName;     
    /** 门店id */
    12: optional string  outletId;
    /** 审核时间 */
    13: optional i64 auditTime;
    /** 审核状态   0=待审核 ,1=审核通过 , 2=审核不通过 , 3=未提交 , 4=审核通过待同步    */
    14: optional string auditState;
    /**0-更新审核中1-正常(编辑审核通过)2-审核未通过*/
    15: optional string editAuditState;
    /** 门店二维码 */
    16: optional string  qrcodeUrl;
    /**开始创建时间(查询条件使用)*/
    17: optional i64 startCreateTime;
    /**结束创建时间(查询条件使用)*/
    18: optional i64 endCreateTime;
    /**开始审核时间(查询条件使用)*/
    19: optional i64 startAuditTime;
    /**结束审核时间(查询条件使用)*/
    20: optional i64 endAuditTime;
    /** 门店地址 */
    21: optional string  address;
    /** 门店地区 */
    22: optional string  areaName;
    /** 所属机构名称*/
    23: optional string  orgName;
    /** 无效状态,0正常;1禁用;2解约 */
    24: optional string disableStatus;
    /** 是否有效 */
    25: optional bool isEnable;
    /** 是否为默认门店  */
    26: optional bool isDefault;
    /** 区域树路径  */
    27: optional string treePathName;
    /** 优惠开通状态 0=无效 ,1=有效  */
    28: optional bool preferStatus;
    /** 优惠折扣比 */
    29: optional string discountRate;
    /**无效状态 集合*/
    30: optional list<string> disableStatusList;
}