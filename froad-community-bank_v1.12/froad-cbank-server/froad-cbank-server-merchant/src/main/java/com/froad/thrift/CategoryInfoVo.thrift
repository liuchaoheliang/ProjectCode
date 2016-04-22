namespace java com.froad.thrift.vo

/**
 * 商户分类
 * 
 * 餐饮、休闲、美容、汽车等
 */
struct CategoryInfoVo {
	/** 类别id */
	1:i64 categoryId;
	/** 名称 */
	2:string name;
}
