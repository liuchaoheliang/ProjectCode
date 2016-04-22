/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"
  
include "coremodule_struct.thrift"
 

namespace java com.froad.thrift.service
/**
* 指标
*/
service ReportService extends BizMonitor.BizMonitorService{
 
    /**
	 * 
	 * getRespPage:获取报表信息分页
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月2日 下午4:41:05
	 * @param pageVo 分页请求信息
	 * @param reportReqVo 报表请求参数
	 *            
	 *
	 */
    coremodule_struct.ReportRespPageVo getRespPage(1:Common.PageVo pageVo, 2:coremodule_struct.ReportReqVo reportReqVo);
    
    /**
	 * 
	 * getReportByCondition:列表条件查询导出
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月5日 下午4:41:05
	 * @param pageVo 分页请求信息
	 * @param reportReqVo 报表请求参数
	 *            
	 *
	 */
	 Common.ExportResultRes getReportByCondition(1:coremodule_struct.ReportReqVo reportReqVo);
	 
	/**
	 * 
	 * getDefineTaskVo:获取任务列表信息分页
	 * 
	 * @author wufei@froad.com.cn 2015年12月5日 下午4:15:05
	 * @param pageVo 分页请求信息
	 * @param defineTaskReqVo 任务列表请求参数
	 *            
	 *
	 */
     coremodule_struct.DefineTaskRespPageVo getDefineTaskPageVo(1:Common.PageVo pageVo,2:coremodule_struct.DefineTaskReqVo defineTaskReqVo);
}