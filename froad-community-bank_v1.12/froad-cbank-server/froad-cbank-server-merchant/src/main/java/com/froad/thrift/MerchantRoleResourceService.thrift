/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入MerchantRoleResourceVo.thrift文件 */
include "MerchantRoleResourceVo.thrift"

/* 引入MerchantRoleResourceServiceVo.thrift文件 */
include "MerchantRoleResourceServiceVo.thrift"

namespace java com.froad.thrift.service


/**
 * MerchantRoleResourceService
 */
service MerchantRoleResourceService extends BizMonitor.BizMonitorService {

    /**
     * 增加 MerchantRoleResource
     * @param merchantRoleResource
     * @return MerchantRoleResourceAddVoRes
     */
    MerchantRoleResourceServiceVo.MerchantRoleResourceAddVoRes addMerchantRoleResource(1:Common.OriginVo originVo, 2:MerchantRoleResourceVo.MerchantRoleResourceVo merchantRoleResourceVo);

    /**
     * 删除 MerchantRoleResource
     * @param _id (client_id_role_id)
     * @return ResultVo    
     */
    Common.ResultVo deleteMerchantRoleResource(1:Common.OriginVo originVo, 2:string _id);

    /**
     * 修改 MerchantRoleResource
     * @param merchantRoleResource
     * @return ResultVo    
     */
    Common.ResultVo updateMerchantRoleResource(1:Common.OriginVo originVo, 2:MerchantRoleResourceVo.MerchantRoleResourceVo merchantRoleResourceVo);

    /**
     * 查询 MerchantRoleResource
     * @param _id (client_id_role_id)
     * @return MerchantRoleResourceVo
     */
    MerchantRoleResourceVo.MerchantRoleResourceVo getMerchantRoleResource(1:string _id);

    /**
     * 查询 List<MerchantRoleResource>
     * @param client_id
     * @return List<MerchantRoleResourceVo>
     */
    list<MerchantRoleResourceVo.MerchantRoleResourceVo> getMerchantRoleResourceListByClientId(1:string client_id);
    
    /**
     * 查询 List<MerchantRoleResource> (全部)
     * @return List<MerchantRoleResourceVo>
     */
    list<MerchantRoleResourceVo.MerchantRoleResourceVo> getMerchantRoleResourceList();
}
