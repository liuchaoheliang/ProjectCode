namespace java com.froad.thrift.vo

/* 引入CategoryInfoVo.thrift文件 */
include "CategoryInfoVo.thrift"

/**
 * 商户信息
 */
struct MerchantVo {

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
    /** 商户全名 */
    10: optional string merchantFullname;
    /** 商户商标 */
    11: optional string logo;
    /** 商户地址 */
    12: optional string address;
    /** 商户电话 */
    13: optional string phone;
    /** 所在地区 */
    14: optional i64 areaId;
    /** 是否银⾏行机构对应的商户 */
    15: optional bool merchantStatus;
    /** 是否有效商户 */
    16: optional bool isEnable;
    /** 无效状态,0正常;1禁用;2解约 */
    17: optional string disableStatus;
    /** 是否置顶 */
    18: optional bool isTop;
    /** 简介 */
    19: optional string introduce;
    /** 营业执照号码 */
    20: optional string license;
    /** 税务登记证 */
    21: optional string taxReg;
    /** 签约时间 */
    22: optional i64 contractBegintime;
    /** 签约到期时间 */
    23: optional i64 contractEndtime;
    /** 签约人员 */
    24: optional string contractStaff;
    /** 起始审核机构 */
    25: optional string auditStartOrgCode;
    /** 最终审核机构 */
    26: optional string auditEndOrgCode;
    /** 审核状态 */
    27: optional string auditState;
    /** 待审核机构编号(完成置0) */
    28: optional string auditOrgCode;
    /** 是否需要复核(0/1) - 已删除 */
    29: optional bool needReview;
    /** 审核步骤(0-初审/1-复审) */
    30: optional string auditStage;
    /** 审核时间 */
    31: optional i64 auditTime;
    /** 审核备注 */
    32: optional string auditComment;
    /** 审核人员 */
    33: optional string auditStaff;
    /** 复核人员 */
    34: optional string reviewStaff;
    /** 联系人姓名 */
    35: optional string contactName;
    /** 联系人电话 */
    36: optional string contactPhone;
    /** 联系人邮箱 */
    37: optional string contactEmail;
    /** 法人姓名 */
    38: optional string legalName;
    /** 法人证件号类型 */
    39: optional i32 legalCredentType;
    /** 法人证件号 */
    40: optional string legalCredentNo;
    /** 投诉电话 */
    41: optional string complaintPhone;
    
    /** 分类id列表(查询使用 - 餐饮、休闲、美容、汽车等) */
    42: optional list<i64> categoryInfoList;
    
    /** 类型id列表(查询使用 - 团购、面对面、名优特惠、等) */
    43: optional list<i64> typeInfoList;
    
    /**开始创建时间(查询条件使用)*/
    44: optional i64 startCreateTime;
    
     /**结束创建时间(查询条件使用)*/
    45: optional i64 endCreateTime;
    
    /**开始审核时间(查询条件使用)*/
    46: optional i64 startAuditTime;
    
     /**结束审核时间(查询条件使用)*/
    47: optional i64 endAuditTime;
    
    /**开始签约时间(查询条件使用)*/
    48: optional i64 startContractBegintime;
    
    /**开始签约时间(查询条件使用)*/
    49: optional i64 endContractBegintime;
    
    /**开始签约到期时间(查询条件使用)*/
    50: optional i64 startContractEndtime;
    
    /**结束签约到期时间(查询条件使用)*/
    51: optional i64 endContractEndtime;
    
     /**用户录入机构*/
    52: optional string userOrgCode;
    
     /**商户组织机构代码证*/
    53: optional string companyCredential;
    
     /**0-更新审核中1-正常(编辑审核通过)2-审核未通过*/
    54: optional string editAuditState;
    
    /**操作时间*/
    55: optional i64 operateTime;
    
    /** 操作人 **/    
    56: optional string operateUser;
    
    /** 是否外包[false=否,true=是] */
    57: optional bool isOutsource;
    
    /** 外包公司id */
    58: optional i64 companyId;
}
