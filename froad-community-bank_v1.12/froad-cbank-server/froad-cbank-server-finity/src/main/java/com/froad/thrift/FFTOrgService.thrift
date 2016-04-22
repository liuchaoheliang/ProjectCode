/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"
include "FFTOrgVo.thrift"

namespace java com.froad.thrift.service


/**
* -------------------------------------组织管理服务---------------------------------
*/

/**
 * 组织管理服务
 * FFTOrgService
 */
service FFTOrgService extends BizMonitor.BizMonitorService {

  
   /**
	 * 组织查询List
	 * @param fftOrgVo 过滤条件对象
	 * @return FFTOrgListVoRes
	 */
    FFTOrgVo.FFTOrgListVoRes getFFTOrgByList(1:FFTOrgVo.FFTOrgVo fftOrgVo);
  
    /**
     * 组织查询Page
     * @param page 分页对象
     * @param fftOrgVo  组织对象过滤信息
     * @return BossProductCategoryInfoRes
     */
    FFTOrgVo.FFTOrgPageVoRes getFFTOrgByPage(1:Common.PageVo page, 2:FFTOrgVo.FFTOrgVo fftOrgVo);
  
    /**
	 * 组织Id多集合查询
	 * @param orgIds 组织Id集合
	 * @return FFTOrgNameListVoRes
	 */
    FFTOrgVo.FFTOrgNameListVoRes getFFTOrgByOrgIds(1:list<string> orgIds);
  
   /**
	 * 主键id多集合查询
	 * @param ids 主键id集合
	 * @return FFTOrgListVoRes
	 */
    FFTOrgVo.FFTOrgListVoRes getFFTOrgByIds(1:list<i64> ids);
    
    /**
	 * 组织列表拉取-查全部
	 * @param userId 用户Id
	 * @return FFTOrgListVoRes
	 */
    FFTOrgVo.FFTOrgListVoRes getFFTOrgByUserId(1:i64 userId);
  
   /**
	 * 组织列表拉取-查顶级2级
	 * @param userId 用户Id
	 * @return FFTOrgListVoRes
	 */
    FFTOrgVo.FFTOrgListVoRes getFFTOrgByUserIdTwoLevel(1:i64 userId);
    
    /**
	 * 组织列表拉取-查用户下组织id下的子组织
	 * @param userId 用户Id
	 * @param orgId 组织Id
	 * @return FFTOrgListVoRes
	 */
    FFTOrgVo.FFTOrgListVoRes getFFTOrgByUserIdOrgId(1:i64 userId,2:string orgId);
    
    /**
	 * 组织详情
	 * @param id 主键Id
	 * @param orgId 组织Id
	 * @return FFTOrgDetailVo
	 */
    FFTOrgVo.FFTOrgDetailVo getFFTOrgDetail(1:i64 id,2:string orgId);
  
   /**
	 * 验证组织下是否有下级组织
	 * @param id 主键Id
	 * @return isNextFFTOrgVo集合
	 */
    list<FFTOrgVo.isNextFFTOrgVo> isNextFFTOrg(1:i64 userId,2:list<i64> ids);
    
    
    /**
     * 新增 组织
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param fftOrgVo 组织信息对象
     * @param roleIds 角色分配
     * @param roleIds 数据权限
     * @return CommonAddVoRes     
     */
    Common.CommonAddVoRes addFFTOrg(1:Common.OriginVo originVo,2:FFTOrgVo.FFTOrgVo fftOrgVo,3:list<i64> roleIds,4:list<string> reOrgIds);

	/**
     * 删除  组织
     * @param OriginVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param id 主键Id
     * @return ResultVo    
     */
    Common.ResultVo deleteFFTOrg(1:Common.OriginVo originVo,2:i64 id);
    
   
    /**
     * 修改 组织
     * @param OriginVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param fftOrgVo 组织信息对象
     * @param roleIds 角色分配
     * @param roleIds 数据权限
     * @return ResultVo    
     */
    Common.ResultVo updateFFTOrg(1:Common.OriginVo originVo,2:FFTOrgVo.FFTOrgVo fftOrgVo,3:list<i64> roleIds,4:list<string> reOrgIds);

   /**
	 * 组织数据权限查询
	 * @param orgId 组织Id
	 * @return FFTOrgReListVoRes
	 */
    FFTOrgVo.FFTOrgReListVoRes getFFTOrgReByOrgId(1:string orgId);
  
   /**
	 * 组织角色查询
	 * @param orgId 组织Id
	 * @return OrgRoleListVoRes
	 */
    FFTOrgVo.OrgRoleListVoRes getOrgRoleByOrgId(1:string orgId);
    
    /**
	 * 组织Id集合查询角色id-去重
	 * @param orgIds 组织Id集合
	 * @return OrgRoleIdListVoRes
	 */
    FFTOrgVo.OrgRoleIdListVoRes getOrgRoleIds(1:list<string> orgIds);
    
    
    /**
	 * 银行渠道列表(获取银行组织的一级)
	 * @param userId 用户Id
	 * @return FFTOrgListVoRes
	 */
    FFTOrgVo.FFTOrgListVoRes getFFTOrgInOneByBank(1:i64 userId);
    
    /**
	 * 所属组织-数据权限下拉框(获取银行组织非一级组织)
	 * @param userId 用户Id
	 * @param clientId 客户端Id
	 * @return FFTOrgListVoRes
	 */
    FFTOrgVo.FFTOrgListVoRes getFFTOrgByUserIdPlatform(1:i64 userId,2:string clientId);
    
    
    
}