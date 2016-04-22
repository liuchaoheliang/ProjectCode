namespace java com.froad.thrift.vo


/**
 * 商户分类信息
 */
struct MerchantCategoryVo {

	/** 主键ID */
    1: optional i64 id;
    /** 客户端ID */
    2: optional string clientId;
    /** 上级分类ID */
    3: optional i64 parentId;
    /** 分类名称 */
    4: optional string name;
    /** 树路径 */
    5: optional string treePath;
    /** 商户分类图标 */
    6: optional string icoUrl;
    /** 是否删除 */
    7: optional bool isDelete;
    /** 排序 */
    8: optional i32 orderValue;
    /** 是否存在下级标志  有=true 无=false */
    9: optional bool nextFlag;
    /** 区域Id */
    10: optional i64 areaId;
}
