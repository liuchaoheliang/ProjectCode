/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"
include "FFTUserRoleVo.thrift"

namespace java com.froad.thrift.service


/**
* -------------------------------------用户角色管理服务---------------------------------
*/

/**
 * 用户角色管理服务
 * FFTUserRoleService
 */
service FFTUserRoleService extends BizMonitor.BizMonitorService {

  
   /**
	 * 用户角色查询
	 * @param userId 用户Id
	 * @return FFTUserRoleListVoRes
	 */
    FFTUserRoleVo.FFTUserRoleListVoRes getFFTUserRole(1:i64 userId);
  
   /**
	 * 用户角色id查询-去重
	 * @param userId 用户Id
	 * @return FFTUserRoleIdListVoRes
	 */
    FFTUserRoleVo.FFTUserRoleIdListVoRes getFFTUserRoleId(1:i64 userId);
    
    
    /**
     * 新增 用户角色
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param userId 用户Id
     * @param orgIds 组织配置
     * @param roleIds 角色分配
     * @return CommonAddVoRes     
     */
    Common.CommonAddVoRes addFFTUserRole(1:Common.OriginVo originVo,2:i64 userId,3:list<string> orgIds,4:list<i64> roleIds);

   
    /**
     * 修改 用户角色关系
     * @param OriginVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param userId 用户Id
     * @param orgIds 组织配置
     * @param roleIds 角色分配
     * @return ResultVo    
     */
    Common.ResultVo updateFFTUserRole(1:Common.OriginVo originVo,2:i64 userId,3:list<string> orgIds,4:list<i64> roleIds);
  
   /**
     * 删除 用户角色关系
     * @param OriginVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param userId 用户Id
     * @return ResultVo    
     */
    Common.ResultVo deleteFFTUserRole(1:Common.OriginVo originVo,2:i64 userId);
    
    
}