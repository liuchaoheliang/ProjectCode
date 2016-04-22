namespace java com.froad.thrift.service

include "Common.thrift"
include "BizMonitor.thrift"
include "ProviderOrderStruct.thrift"

service ProviderOrderService extends BizMonitor.BizMonitorService {
	
	/**
	 * 供应商订单列表分页查询
	 */
	ProviderOrderStruct.ProviderOrderQueryPageVo queryProviderOrderInfoByPage(1:ProviderOrderStruct.ProviderOrderQueryReq req, 2:Common.PageVo pageVo);
	
	
	/**
	 * 供应商订单列表导出
	 */
	Common.ExportResultRes exportProviderOrderInfo(1:ProviderOrderStruct.ProviderOrderQueryReq req);
	
	
	/**
	 * 导入物流信息
	 */
	Common.ExportResultRes inputShippingInfo(1:list<ProviderOrderStruct.ShippingExcelInfoVo> excelInfoVos);
	
	
	/**
	 * 出库
	 */
	Common.ResultVo shippingByOrderId(1:string orderId);
	
	
	/**
	 * 更新物流信息
	 */
	Common.ResultVo updateShippingInfo(1:ProviderOrderStruct.UpdateShippingInfoReq req);
	
}
















