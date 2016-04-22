/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入MerchantUserVo.thrift文件 */
include "MerchantUserVo.thrift"

/* 引入FinityVo.thrift文件 */
include "FinityVo.thrift"

namespace java com.froad.thrift.service


/**
 * MerchantUserService
 */
service FinityMerchantUserService extends BizMonitor.BizMonitorService {

    /**
     * 增加 MerchantUser
     * @param merchantUser
     * @return MerchantUserAddVoRes
     */
    Common.CommonAddVoRes addMerchantUser(1:Common.OriginVo originVo, 2:MerchantUserVo.MerchantUserVo merchantUserVo,3:list<i64> resourceIds);


    /**
     * 修改 MerchantUser
     * @param merchantUser
     * @return boolean    
     */
    Common.ResultVo updateMerchantUser(1:Common.OriginVo originVo, 2:MerchantUserVo.MerchantUserVo merchantUserVo,3:list<i64> resourceIds);



    /**
     * 查询 MerchantUser
     * @param merchantUser
     * @return List<MerchantUserVo>
     */
    list<MerchantUserVo.MerchantUserVo> getMerchantUser(1:MerchantUserVo.MerchantUserVo merchantUserVo);

    /**
     * 分页查询 MerchantUser
     * @param merchantUser
     * @return List<MerchantUserVo>
     */
    MerchantUserVo.MerchantUserPageVoRes getMerchantUserByPage(1:Common.PageVo page, 2:MerchantUserVo.MerchantUserVo merchantUserVo);
}


service FinityUserResourceService extends BizMonitor.BizMonitorService{
    /**
     * 批量增加 用户资源关系
     * @param userResourceVoList
     * @return ResultVo
     */
    Common.ResultVo addUserResourceByBatch(1:list<FinityVo.UserResourceVo> userResourceVoList);
}

