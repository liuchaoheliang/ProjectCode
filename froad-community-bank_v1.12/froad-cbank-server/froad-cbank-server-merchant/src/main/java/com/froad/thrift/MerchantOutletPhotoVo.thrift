namespace java com.froad.thrift.vo


/**
 * 商户门店相册
 */
struct MerchantOutletPhotoVo {

	/** 主键ID */
    1: optional i64 id;
    /** 商户ID */
    2: optional string merchantId;
    /** 门店ID */
    3: optional string outletId;
    /** 是否默认 */
    4: optional bool isDefault;
    /** 标题 */
    5: optional string title;
    /** 原图片 */
    6: optional string source;
    /** 大图片 */
    7: optional string large;
    /** 中图片 */
    8: optional string medium;
    /** 缩略图 */
    9: optional string thumbnail;
}
