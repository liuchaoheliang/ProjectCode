namespace java com.froad.thrift.service

include "Common.thrift"
include "BizMonitor.thrift"
include "ProviderStruct.thrift"

service ProviderService extends BizMonitor.BizMonitorService {
	
	/**
	 * 供应商列表查询
	 */
	ProviderStruct.ProviderListVo findAllProviders();
	
}

