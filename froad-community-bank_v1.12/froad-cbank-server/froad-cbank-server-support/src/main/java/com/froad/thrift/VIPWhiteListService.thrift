/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* VIPWhiteListServiceVo.thrift文件 */
include "VIPWhiteListServiceVo.thrift"

namespace java com.froad.thrift.service


service VIPWhiteListService extends BizMonitor.BizMonitorService {

	/**
     * checkVIPExistWhiteList 检查VIP是否存在白名单中
     * @param reqVo
     * @return CheckVIPExistWhiteListRespVo
     */
	VIPWhiteListServiceVo.CheckVIPExistWhiteListRespVo checkVIPExistWhiteList(1:VIPWhiteListServiceVo.CheckVIPExistWhiteListReqVo reqVo);
	
	/**
     * addVIPWhiteList 添加VIP白名单
     * @param reqVo
     * @return ResultVo
     */
	Common.ResultVo addVIPWhiteList(1:VIPWhiteListServiceVo.AddVIPWhiteListReqVo reqVo);
	
	/**
     * removeVIPWhiteList 移除VIP白名单
     * @param reqVo
     * @return ResultVo
     */
	Common.ResultVo removeVIPWhiteList(1:VIPWhiteListServiceVo.RemoveVIPWhiteListReqVo reqVo);
}