namespace java com.froad.thrift.vo.provider

include "Common.thrift"

struct ProviderVo {
	/** id **/
	1:optional i64 id;
	
	/** 创建时间 **/
	2:optional i64 createTime;
	
	/** 更新时间 **/
	3:optional i64 updateTime;
	
	/** 供应商ID **/
	4:optional string merchantId;
	
	/** 供应商名称 **/
	5:optional string merchantName;
	
	/** 地址 **/
	6:optional string address;
	
	/** 电话 **/
	7:optional string phone;
	
	/** 状态: 0禁用, 1启用 **/
	8:optional string status;
	
	/** 供应商描述 **/
	9:optional string description;
}

struct ProviderListVo {
	/** 返回描述 **/
	1:required Common.ResultVo resultVo;
	
	/** 供应商集合 **/
	2:required list<ProviderVo> providerList;
}

