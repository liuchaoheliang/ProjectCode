namespace java com.froad.thrift.vo

/* 引入CategoryInfoVo.thrift文件 */
include "CategoryInfoVo.thrift"

/* 引入TypeInfoVo.thrift文件 */
include "TypeInfoVo.thrift"

/**
 * 门店信息
 */
struct OutletVo {

	/** 主键ID */
    1:  optional i64     id;
    /** 创建时间 */
    2:  optional i64     createTime;
    /** 客户端ID */
    3:  optional string clientId;
    /** 商户ID */
    4:  optional string  merchantId;
    /** 门店ID */
    5:  optional string  outletId;
    /** 地区ID */
    6:  optional i64     areaId;
    /** 排序 */
    7:  optional i32     orderValue;
    /** 门店名称 */
    8:  optional string  outletName;
    /** 门店全名 */
    9:  optional string  outletFullname;
    /** 是否银?行机构对应?门店 */
    10: optional bool    outletStatus;
    /** 地址 */
    11: optional string  address;
    /** 营业时间 */
    12: optional string  businessHours;
    /** 邮编 */
    13: optional string  zip;
    /** 传真 */
    14: optional string  fax;
    /** 电话 */
    15: optional string  phone;
    /** 联系人姓名 */
    16: optional string  contactName;
    /** 联系人电话 */
    17: optional string  contactPhone;
    /** 联系人邮箱 */
    18: optional string  contactEmail;
    /** 服务提供商 */
    19: optional string  serviceProvider;
	/** 经度 */
    20: optional string  longitude;
    /** 纬度 */
    21: optional string  latitude;
    /** 是否有效 */
    22: optional bool    isEnable;
    /** 无效状态,0正常;1禁用;2解约 */
    23: optional string disableStatus;
    /** 描述 */
    24: optional string  description;
    /** 优惠详情 */
    25: optional string  preferDetails;
	/** 优惠开始时间 */
    26: optional i64  preferStartPeriod;
	/** 优惠结束时间 */
    27: optional i64  preferEndPeriod;
	/** 折扣 */
    28: optional string  discount;
    
    /**开始创建时间(查询条件使用)*/
    29: optional i64 startCreateTime;
    
     /**结束创建时间(查询条件使用)*/
    30: optional i64 endCreateTime;
    /**无效状态 集合*/
    31: optional list<string> disableStatusList;
    
     /**投诉电话*/
    32: optional string complaintPhone;
    
     /** 分类列表 */
    33:  optional list<CategoryInfoVo.CategoryInfoVo>     categoryInfo; 
    
    /** 类型列表 */
    34:  optional list<TypeInfoVo.TypeInfoVo>     typeInfo;      
    
    /** 优惠折扣码 */
    35: optional string  discountCode;

    /** 优惠折扣比 */
    36: optional string  discountRate;
    
    /** 审核时间 */
    37: optional i64 auditTime;
    
    /** 审核备注 */
    38: optional string auditComment;
    
    /** 审核人 */
    39: optional string auditStaff;
    
    /** 审核状态   0=待审核 ,1=审核通过 , 2=审核不通过 , 3=未提交 , 4=审核通过待同步    */
    40: optional string auditState;
    
    /** 编辑审核状态  0=待审核 ,1=审核通过 ,2=审核不通过 ,3=未提交 ,4=审核通过待同步   */
    41: optional string editAuditState;
    /** 是否为默认门店  */
    42: optional bool isDefault;
    
    /** 优惠开通状态 0=无效 ,1=有效  */
    43: optional bool preferStatus;
    
    /** 门店二维码URL */
    44: optional string qrcodeUrl;

    /** 评论星级 */
    45: optional string starLevel;

}
