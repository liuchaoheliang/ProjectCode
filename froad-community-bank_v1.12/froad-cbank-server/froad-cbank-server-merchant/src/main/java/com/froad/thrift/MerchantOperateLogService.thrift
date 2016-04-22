/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入MerchantOperateLogVo.thrift文件 */
include "MerchantOperateLogVo.thrift"

namespace java com.froad.thrift.service



/**
 * MerchantOperateLogService
 */
service MerchantOperateLogService extends BizMonitor.BizMonitorService{

    /**
     * 添加日志
     * @param MerchantOperateLogVo
     * @return boolean
     */
    bool addMerchantOperateLog(1:MerchantOperateLogVo.MerchantOperateLogVo MerchantOperateLogVo); 

    /**
     * 获取分页日志
     * @return MerchantOperateLogPageVoRes    
     */
    MerchantOperateLogVo.MerchantOperateLogPageVoRes getMerchantOperateLogByPage(1:Common.PageVo page, 2:MerchantOperateLogVo.MerchantOperateLogVo MerchantOperateLogVo);
    
}
