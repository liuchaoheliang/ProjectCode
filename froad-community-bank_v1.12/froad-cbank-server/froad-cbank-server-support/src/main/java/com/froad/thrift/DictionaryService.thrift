
/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"



/* 引入DictionaryitemVo.thrift */
include "DictionaryVo.thrift"

namespace java com.froad.thrift.service


service DictionaryService extends BizMonitor.BizMonitorService{


    /**
     * MerchantAccount
     * @param merchantAccount
     * @return List<DictionaryItemVo>
     */
    list<DictionaryVo.DictionaryItemVo> getDictionaryItemList(1:string dicCode,2:string clientId);

       /** 
     * 
     * @param outletId
     * @return OutletVo
     */
    DictionaryVo.DictionaryItemVo getDictionaryitemById(1:i64 dictionaryitemId);
    
}