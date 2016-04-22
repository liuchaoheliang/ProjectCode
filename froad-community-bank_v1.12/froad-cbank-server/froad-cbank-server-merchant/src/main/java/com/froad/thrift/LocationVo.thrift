namespace java com.froad.thrift.vo

/**
 * 地点
 *
 * 确定经纬度
 */
struct LocationVo {
	/** 经度 */
	1:double longitude;
	/** 纬度 */
	2:double latitude;
}
