/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入OutletCommentVo.thrift文件 */
include "MerchantMonthCountVo.thrift"

namespace java com.froad.thrift.service


/**
 * MerchantMonthCountService
 */
service MerchantMonthCountService extends BizMonitor.BizMonitorService {

    /**
     * 查询 MerchantMonthCountVo
     * @param merchantMonthCountVo
     * @return MerchantMonthCountVo    
     */
    MerchantMonthCountVo.MerchantMonthCountVo getMerchantMonthCount(1:MerchantMonthCountVo.MerchantMonthCountVo merchantMonthCountVo);

}


