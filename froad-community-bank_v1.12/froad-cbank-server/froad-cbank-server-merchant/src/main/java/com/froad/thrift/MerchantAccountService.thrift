/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入MerchantAccountVo.thrift文件 */
include "MerchantAccountVo.thrift"

/* 引入MerchantAccountServiceVo.thrift文件 */
include "MerchantAccountServiceVo.thrift"

namespace java com.froad.thrift.service


/**
 * MerchantAccountService
 */
service MerchantAccountService extends BizMonitor.BizMonitorService{

    /**
     * 增加 MerchantAccount
     * @return MerchantAccountAddVoRes
     */
    MerchantAccountServiceVo.MerchantAccountAddVoRes addMerchantAccount(1:Common.OriginVo originVo ,2:MerchantAccountVo.MerchantAccountVo merchantAccountVo);

    /**
     * 删除 MerchantAccount
     * @return ResultVo    
     */
    Common.ResultVo deleteMerchantAccount(1:Common.OriginVo originVo ,2:MerchantAccountVo.MerchantAccountVo merchantAccountVo);
    
    /**
     * 物理删除 MerchantAccount(商户添加时回滚用到)
     * @return ResultVo    
     */
    Common.ResultVo removeMerchantAccount(1:Common.OriginVo originVo ,2:i64 id);

    /**
     * 修改 MerchantAccount
     * @return ResultVo    
     */
    Common.ResultVo updateMerchantAccount(1:Common.OriginVo originVo ,2:MerchantAccountVo.MerchantAccountVo merchantAccountVo);

    /**
     * 查询 MerchantAccount
     * @param merchantAccount
     * @return List<MerchantAccountVo>
     */
    list<MerchantAccountVo.MerchantAccountVo> getMerchantAccount(1:MerchantAccountVo.MerchantAccountVo merchantAccountVo);
    
    /**
     * 根据门店ID查询门店账号,提供给openAPI调用
     * @param key 加密的key
     * @param outletId 加密的门店ID
     */
    string getMerchantAccountByKeyAndOutletId(1:string key,2:string merchantId_outletId);

    /**
     * 分页查询 MerchantAccount
     * @param merchantAccount
     * @return List<MerchantAccountVo>
     */
    MerchantAccountServiceVo.MerchantAccountPageVoRes getMerchantAccountByPage(1:Common.PageVo page, 2:MerchantAccountVo.MerchantAccountVo merchantAccountVo);
}