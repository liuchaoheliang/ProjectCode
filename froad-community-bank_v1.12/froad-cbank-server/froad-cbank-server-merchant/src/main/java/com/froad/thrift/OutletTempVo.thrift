namespace java com.froad.thrift.vo

/**
 * 门店临时信息
 */
struct OutletTempVo {

    /** 主键ID */
    1: optional i64 id;
    /** 创建时间 */
    2: optional i64 createTime;
    /** 客户端ID */
    3: optional string clientId;
    /** 审核流水号 */
    4: optional string auditId;
    /** 商户ID */
    5: optional string merchantId;
    /** 门店Id */
    6: optional string outletId;
    /** 地区Id */
    7: optional string areaId;
    /** 门店名称 */
    8: optional string outletName;
    /** 门店全称 */
    9: optional string outletFullName;
    /** 地址 */
    10: optional string address;
    /** 营业时间*/
    11: optional string businessHours;
    /** 邮政编码*/
    12: optional string zip;
    /** 传真*/
    13: optional string fax;
    /** 电话*/
    14: optional string phone;
    /** 联系人姓名*/
    15: optional string contactName;
    /** 联系人电话*/
    16: optional string contactPhone;
    /** 联系人邮箱*/
    17: optional string contactEmail;
    /** 经度 */
    18: optional string  longitude;
    /** 纬度 */
    19: optional string  latitude;
    /** 描述*/
    20: optional string description;
    /** 优惠详情*/
    21: optional string preferDetails;
    /** 折扣*/
    22: optional string discount;
    /** 优惠折扣码*/
    23: optional string discountCode;
    /** 优惠折扣比*/
    24: optional string discountRate;
    /** 账户名*/
    25: optional string acctName;
    /** 账户号*/
    26: optional string acctNumber;
    /** 相册列表*/
    27: optional string photoList;   
    /** 原始门店信息*/
    28: optional string primeval;
    /** 门店分类id */
    29: optional string outletCategoryId;
    /** 门店分类名称 */   
    30: optional string outletCategoryName;
}