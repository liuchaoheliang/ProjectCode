/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* FallowExecute.thrift文件 */
include "FallowExecuteServiceVo.thrift"

namespace java com.froad.thrift.service


/**
 * FallowExecuteService
 */
service FallowExecuteService extends BizMonitor.BizMonitorService {
	
	/**
	 * 创建审核实例
     * @param req 请求参数
     */
	FallowExecuteServiceVo.CreateInstanceResVo createInstance (1:FallowExecuteServiceVo.CreateInstanceReqVo req);
	
	/**
	 * 执行审核任务
     * @param req 请求参数
	 */
	FallowExecuteServiceVo.ExecuteTaskResVo executeTask (1:FallowExecuteServiceVo.ExecuteTaskReqVo req);
	
	
}
