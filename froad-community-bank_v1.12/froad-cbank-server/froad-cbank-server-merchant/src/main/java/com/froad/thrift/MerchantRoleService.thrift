/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入MerchantRoleVo.thrift文件 */
include "MerchantRoleVo.thrift"

/* 引入MerchantRoleServiceVo.thrift文件 */
include "MerchantRoleServiceVo.thrift"

namespace java com.froad.thrift.service


/**
 * MerchantRoleService
 */
service MerchantRoleService extends BizMonitor.BizMonitorService {

    /**
     * 增加 MerchantRole
     * @param merchantRole
     * @return MerchantRoleAddVoRes
     */
    MerchantRoleServiceVo.MerchantRoleAddVoRes addMerchantRole(1:Common.OriginVo originVo, 2:MerchantRoleVo.MerchantRoleVo merchantRoleVo);

    /**
     * 删除 MerchantRole
     * @param merchantRole
     * @return ResultVo    
     */
    Common.ResultVo deleteMerchantRole(1:Common.OriginVo originVo, 2:MerchantRoleVo.MerchantRoleVo merchantRoleVo);

    /**
     * 修改 MerchantRole
     * @param merchantRole
     * @return ResultVo    
     */
    Common.ResultVo updateMerchantRole(1:Common.OriginVo originVo, 2:MerchantRoleVo.MerchantRoleVo merchantRoleVo);

    /**
     * 查询 MerchantRole
     * @param merchantRole
     * @return List<MerchantRoleVo>
     */
    list<MerchantRoleVo.MerchantRoleVo> getMerchantRole(1:MerchantRoleVo.MerchantRoleVo merchantRoleVo);

    /**
     * 分页查询 MerchantRole
     * @param merchantRole
     * @return List<MerchantRoleVo>
     */
    MerchantRoleServiceVo.MerchantRolePageVoRes getMerchantRoleByPage(1:Common.PageVo page, 2:MerchantRoleVo.MerchantRoleVo merchantRoleVo);
    
    
     /**
     * 查询 MerchantRole
     * @param clientId 客户端id
     * @param description Role_Administrators-超级管理员;Role_Outlet_Admin-门店管理员;Role_Outlet_Operator-门店操作员
     */
    MerchantRoleVo.MerchantRoleVo getMerchantRoleByClientIdAndRoleDesc(1:string clientId,2:string description);
}
