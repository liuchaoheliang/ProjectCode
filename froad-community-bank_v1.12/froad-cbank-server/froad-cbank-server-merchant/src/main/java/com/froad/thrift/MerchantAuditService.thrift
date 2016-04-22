/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入MerchantAuditServiceVo.thrift文件 */
include "MerchantAuditServiceVo.thrift"

/* 引入MerchantTempVo.thrift文件 */
include "MerchantTempVo.thrift"

namespace java com.froad.thrift.service


/**
 * MerchantAuditService
 */
service MerchantAuditService extends BizMonitor.BizMonitorService {

    /**
     * 商户再审核
     * originVo 对象 的clientId和operatorId 必须填值，merchantTempVo 对象里面的审核流水号字段可为空
     * @param originVo 操作vo
     * @param merchantTempVoReq 审核信息VO
     * @return MerchantAuditVoRes 审核操作对象
     */
    MerchantAuditServiceVo.MerchantAuditVoRes auditMerchant(1:Common.OriginVo originVo, 2:MerchantAuditServiceVo.MerchantTempVoReq merchantTempVoReq);
    
    
    /**
     * 同步商户编辑后的数据到主表
     * @param merchantTempVo 审核信息VO
     * @return MerchantAuditVoRes 审核操作对象
     */
    MerchantAuditServiceVo.MerchantAuditVoRes synchMerchantEdit(1:string auditId);
    
    
    
    /**
     * 查询商户修改必审信息
     * originVo 对象 的clientId和operatorId 必须填值
     * @param originVo 操作vo
     * @param merchantTempVoReq 审核信息VO
     * @return MerchantTempVoReq 商户修改审核变更信息对象
     */
    MerchantTempVo.MerchantTempVo getAuditMerchant(1:Common.OriginVo originVo, 2:string auditId);
    
    
    
    
    /**
     * 查询 MerchantTempVo 根据该对象查询
     * @param MerchantTempVo
     * @return List<MerchantTempVo>
     */
    list<MerchantTempVo.MerchantTempVo> getMerchantTemp(1:MerchantTempVo.MerchantTempVo merchantTempVo);
    

}