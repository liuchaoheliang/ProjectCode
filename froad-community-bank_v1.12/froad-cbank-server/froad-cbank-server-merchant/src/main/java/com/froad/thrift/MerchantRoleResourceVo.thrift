namespace java com.froad.thrift.vo
/* 引入ResourceVo.thrift文件 */
include "ResourceVo.thrift"

/**
 * 商户角色资源信息
 */
struct MerchantRoleResourceVo {

	/** 主键ID 由client_id和role_id组成 中间用下划线连接 */
    1: optional string _id;    /** client_id+role_id */
    /** 菜单资源信息列表 */
    2: optional list<ResourceVo.ResourceVo> resources;    /** list<ResourceVo.Resource> */
}


