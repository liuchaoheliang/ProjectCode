include "BizMonitor.thrift"

/* 引入Common.thrift.thrift文件 */
include "Common.thrift"


namespace java com.froad.thrift.service


/**
 * MerchantService
 */
service MerchantService extends BizMonitor.BizMonitorService {

    /**
     * 续约商户
     * @param merchantId 商户id
     * @param contractEndtime 续约到期时间
     * @return boolean   
     */
    Common.ResultVo extensionMerchant(1:Common.OriginVo originVo, 2:string merchantId, 3:i64 contractEndtime);
    
    /**
     * 同步白名单操作时，根据客户端id和商户id同步商户信息.
     * @param merchantId 商户id
     * @param clientId    客户端id
     * @param isSynSucc  0-同步成功，1-同步失败
     * @param synType 0-同步通知，1-审核通知
     * @return Common.ResultVo
     */    
     Common.ResultVo syncMerchantInfo(1:string merchantId,2:string clientId,3:string isSynSucc,4:string synType);    
   }