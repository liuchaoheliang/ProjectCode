/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"
include "FFTUserOrgVo.thrift"

namespace java com.froad.thrift.service


/**
* -------------------------------------用户组织管理服务---------------------------------
*/

/**
 * 用户组织管理服务
 * FFTUserOrgService
 */
service FFTUserOrgService extends BizMonitor.BizMonitorService {

  
   /**
	 * 用户组织查询
	 * @param userId 用户Id
	 * @return FFTUserOrgListVoRes
	 */
    FFTUserOrgVo.FFTUserOrgListVoRes getFFTUserOrg(1:i64 userId);
  
}