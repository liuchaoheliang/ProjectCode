namespace java com.froad.thrift.vo


/**
 * 菜单资源信息
 */
struct ResourceVo {

	/** 主键ID */
	1: optional i64    resource_id;
	/** 资源名称 */
	2: optional string resource_name;
	/** 资源类型 */
	3: optional string resource_type;
	/** 树路径 */
	4: optional string tree_path;
	/** 资源URL */
	5: optional string resource_url;
	/** 上级资源ID */
	6: optional i64    parent_id;
	/** icon */
	7: optional string icon;
	/** 资源接口 */
	8: optional string api;
	/** 排序值 */
	9: optional i32 order_value;
}


