namespace java com.froad.thrift.vo

/**
 * 商户类型
 */
struct TypeInfoVo {

	/** 类型编号 */
	1:i64 merchantTypeId
	/** 类型名称 */
	2:string typeName
	
	/** 0-团购（团）-1直接优惠(折) 2-名优特惠 */
	3:string type
}
