/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入MerchantResourceVo.thrift文件 */
include "MerchantResourceVo.thrift"

/* 引入MerchantResourceServiceVo.thrift文件 */
include "MerchantResourceServiceVo.thrift"

namespace java com.froad.thrift.service


/**
 * MerchantResourceService
 */
service MerchantResourceService extends BizMonitor.BizMonitorService {

    /**
     * 增加 MerchantResource
     * @param merchantResource
     * @return MerchantResourceAddVoRes
     */
    MerchantResourceServiceVo.MerchantResourceAddVoRes addMerchantResource(1:Common.OriginVo originVo, 2:MerchantResourceVo.MerchantResourceVo merchantResourceVo);

    /**
     * 删除 MerchantResource
     * @param merchantResource
     * @return ResultVo    
     */
    Common.ResultVo deleteMerchantResource(1:Common.OriginVo originVo, 2:MerchantResourceVo.MerchantResourceVo merchantResourceVo);

    /**
     * 修改 MerchantResource
     * @param merchantResource
     * @return ResultVo    
     */
    Common.ResultVo updateMerchantResource(1:Common.OriginVo originVo, 2:MerchantResourceVo.MerchantResourceVo merchantResourceVo);
    
    /**
     * 移动 MerchantResource
     * @param originVo
     * @param srcResourceId
     * @param destResourceId
     * @param action  0-移动到某某之前   1-移动到某某之后
     * @return ResultVo    
     */
    Common.ResultVo moveMerchantResourceTo(1:Common.OriginVo originVo, 2:i64 srcResourceId, 3:i64 destResourceId, 4: i32 action);

    /**
     * 查询 MerchantResource
     * @param merchantResource
     * @return List<MerchantResourceVo>
     */
    list<MerchantResourceVo.MerchantResourceVo> getMerchantResource(1:MerchantResourceVo.MerchantResourceVo merchantResourceVo);

    /**
     * 分页查询 MerchantResource
     * @param merchantResource
     * @return List<MerchantResourceVo>
     */
    MerchantResourceServiceVo.MerchantResourcePageVoRes getMerchantResourceByPage(1:Common.PageVo page, 2:MerchantResourceVo.MerchantResourceVo merchantResourceVo);
}
