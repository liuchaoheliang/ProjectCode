namespace java com.froad.thrift.service

include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"
include "PointSettlementVo.thrift"


/**
* 积分结算统计查询对外接口
*/
service PointSettlementService extends BizMonitor.BizMonitorService {
	/**
	* 积分汇总查询接口
	*/
	PointSettlementVo.PointSettlementResVo queryPointCount(1:PointSettlementVo.PointSettlementReqVo req);
	
	/**
	* 积分结算明细查询接口
	*/
	PointSettlementVo.PointSettlementResVo queryPointDetail(1:PointSettlementVo.PointSettlementReqVo req);
	
	/**
	* 商户汇总查询接口
	*/
	PointSettlementVo.PointSettlementMerchantResVo queryMerchantPointCount(1:PointSettlementVo.PointSettlementReqVo req);
	
	/**
	* 报表导出接口
	*/
    Common.ExportResultRes exportPointSettlement(1:PointSettlementVo.PointSettlementReqVo req);
	
}