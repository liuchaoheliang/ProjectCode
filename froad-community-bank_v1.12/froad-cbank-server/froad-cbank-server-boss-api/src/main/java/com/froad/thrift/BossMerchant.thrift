include "BizMonitor.thrift"


/* 引入OutletVo.thrift文件 */
include "OutletVo.thrift"

namespace java com.froad.thrift.service



service BossMerchantService extends BizMonitor.BizMonitorService {


    /**
     * 查询提货网点(预售用到)
     * @param outlet
     * @return List<OutletVo>
     */
    list<OutletVo.OutletVo> getSubBankOutletToList(1:string client_id, 2:string org_code);
   
}