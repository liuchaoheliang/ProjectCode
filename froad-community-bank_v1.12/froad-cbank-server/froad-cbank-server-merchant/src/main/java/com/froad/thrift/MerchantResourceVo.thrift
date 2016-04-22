namespace java com.froad.thrift.vo


/**
 * 商户菜单资源
 */
struct MerchantResourceVo {

	/** 主键ID */
    1: optional i64 id;    /** id */
	/** 客户端ID */
    2: optional string clientId; /** clientId */
    /** 资源名称 */
    4: optional string name;    /** name */
    /** 上级资源ID */
    5: optional string icon;    /** icon */
    /** 资源URL */
    6: optional string url;    /** url */
    /** 类型 */
    7: optional string type;    /** type */
    /** 上级资源ID */
    8: optional i64 parentId;    /** parentId */
    /** 树路径 */
    9: optional string treePath;    /** treePath */
    /** 是否启用 */
    10: optional bool isEnabled;    /** isEnabled */
    /** 资源接口 */
    11: optional string api;
    
    /** 模块(元素)编号 */
    12: optional string sn;
    /** 权限编号 */
	13: optional string rightSn;
    /** 是否是上级 */
	14: optional bool isParent;
    /** 排序 */
	15: optional i32 orderValue;
    
}
