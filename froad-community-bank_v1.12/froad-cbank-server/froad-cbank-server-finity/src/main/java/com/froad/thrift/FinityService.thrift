/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"
include "FinityVo.thrift"

namespace java com.froad.thrift.service

/**
* -------------------------------------资源服务---------------------------------
*/ 
/**
 * 资源服务接口
 * FinityResourceService
 */
service FinityResourceService extends BizMonitor.BizMonitorService{
   
    /**
     * 增加 FinityResource
     * @param FinityResource 资源对象
     * @return long    主键ID(异常及错误返回-1)
     */
	Common.CommonAddVoRes addFinityResource(1:Common.OriginVo originVo,2:FinityVo.FinityResourceVo finityResourceVo);
	
	
	 /**
     * 删除 FinityResource
     * @param FinityResource
     * @return boolean    
     */
    Common.ResultVo deleteFinityResource(1:Common.OriginVo originVo,2:string platform,3:i64 resourceId);
    
    
     /**
     * 修改 FinityResource
     * @param FinityResource
     * @return boolean    
     */
    Common.ResultVo updateFinityResource(1:Common.OriginVo originVo,2:FinityVo.FinityResourceVo finityResourceVo);
    
    
     /**
     * 菜单位移
     * @param list<FinityVo.FinityResourceVo>
     * @return 
     */
    Common.ResultVo moveMenu(1:Common.OriginVo originVo, 2:list<FinityVo.FinityResourceVo> finityResourceVoList);
   
   
    /**
     * 查询 FinityResource
     * @param FinityResource 过滤条件
     * @return List<FinityResourceVo>
     */
    list<FinityVo.FinityResourceVo> getBossFinityResource(1:FinityVo.FinityResourceVo finityResourceVo); 
   
    /**
     * 查询 FinityResource
     * @param FinityResource 过滤条件
     * @return List<FinityResourceVo>
     */
    list<FinityVo.FinityResourceVo> getFinityResource(1:FinityVo.FinityResourceVo finityResourceVo); 

     /**
     * 查询 FinityResource by 用户
     * @param FinityResource 过滤条件
     * @return List<FinityResourceVo>
     */
    list<FinityVo.FinityResourceVo> getFinityResourceByUser(1:FinityVo.FinityResourceVo finityResourceVo,2:i64 userId,3:i32 userType); 

    /**
     * 查询 FinityResource by 角色
     * @param FinityResource 过滤条件
     * @return List<FinityResourceVo>
     */
    list<FinityVo.FinityResourceVo> getFinityResourceByRole(1:FinityVo.FinityResourceVo finityResourceVo,2:list<i64> roles); 
}



/**
*-----------------------角色服务----------------------------
*/
/**
 * 角色服务接口
 * FinityRoleService
 */
service FinityRoleService extends BizMonitor.BizMonitorService{

    /**
     * 增加 FinityRole
     * @param finityRole 角色对象
     * @param resourceIds 资源id集合
     * @return long    主键ID(异常及错误返回-1)
     */
    Common.CommonAddVoRes addFinityRole(1:Common.OriginVo originVo,2:FinityVo.FinityRoleVo finityRoleVo, 3:list<i64> resourceIds);
  
    /**
     * 修改 FinityRole
     * @param finityRole
     * @param resourceIds 资源id集合
     * @return boolean    
     */
    Common.ResultVo updateFinityRole(1:Common.OriginVo originVo,2:FinityVo.FinityRoleVo finityRoleVo, 3:list<i64> resourceIds);

    /**
     * 查询 FinityRole
     * @param finityRole
     * @return List<FinityRoleVo>
     */
    list<FinityVo.FinityRoleVo> getFinityRole(1:FinityVo.FinityRoleVo finityRoleVo);
    
    
     /**
     * 根据UserId、platform获取所能查看范围的角色列表 
     * @param userId
     * @param platform 平台名称(boss、bank、merchant)
     * @return List<FinityRoleVo>
     */
     FinityVo.FinityRoleListVoRes getFinityRoleByUserId(1:i64 userId,2:string platform);
    
    /**
     * 用户登录时获取的所属角色
     * @param userId
     * @return List<FinityRoleVo>
     */
     FinityVo.FinityRoleListVoRes getFinityRoleByUserIdLogin(1:i64 userId);
     
     
     /**
     * 查询 FinityRole
     * @param roleId
     * @return List<FinityRoleVo>
     */
     FinityVo.FinityRoleListVoRes getFinityRoleById(1:i64 roleId);
    
     /**
     * 删除 FinityRole
     * @param finityRole
     * @return boolean    
     */
    Common.ResultVo deleteFinityRole(1:Common.OriginVo originVo,2:string platform,3:i64 roleId); 
}
 
 /**
 *----------------------角色资源关系服务-----------------------
 */

  /**
 * 角色资源关系服务接口
 * FinityRoleResourceService
 */
service FinityRoleResourceService extends BizMonitor.BizMonitorService{
    /**
     * 查询 角色资源关系列表
     * @param clientId 客户端id
     * @param finityRoleId 角色id
     * @param platform 所属平台
     * @return List<FinityResourceVo> 资源列表List
     */
    list<FinityVo.FinityResourceVo> getFinityRoleResource(1:string clientId,2:i64 roleId;3:string platform);    
}