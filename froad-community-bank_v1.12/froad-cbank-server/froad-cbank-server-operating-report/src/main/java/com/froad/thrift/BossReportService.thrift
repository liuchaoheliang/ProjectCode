/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"
include "BossReportVo.thrift"

namespace java com.froad.thrift.service




/**
* -------------------------------------Boss统计报表---------------------------------
*/


/**
 * BossReportService
 * Boss统计服务类
 */
service BossReportService extends BizMonitor.BizMonitorService {

     
   /**
     * 导出
     * @param type 下载报表类型(1-用户活跃统计明细2-商户活跃度明细3-用户活跃度概要统计4-商户活跃度概要统计5-商户门店活跃度明细统计6-购物订单明细7-面对面、惠付、扫码支付明细8-购物券码明细)
     * @param ExportReq 导出请求参数
     * @return ResultVo
     */
    Common.ResultVo export(1:i32 type,2:BossReportVo.ExportReq exportReq);
  
   /**
     * 下载
     * @param filename 报表文件名
     * @return ExportResultRes 
     */
    Common.ExportResultRes download(1:string filename);
  
  
   /**
	 * 分页查询
	 * @param page 分页对象
	 * @param type 下载报表类型(1-用户活跃统计明细2-商户活跃度明细3-用户活跃度概要统计4-商户活跃度概要统计5-商户门店活跃度明细统计6-购物订单明细7-面对面、惠付、扫码支付明细8-购物券码明细)
	 * @return DictionaryItemPageVoRes
	 */
    BossReportVo.DownloadInfoPageVoRes getDownloadInfoByPage(1:Common.PageVo page,2:i32 type);
  
}