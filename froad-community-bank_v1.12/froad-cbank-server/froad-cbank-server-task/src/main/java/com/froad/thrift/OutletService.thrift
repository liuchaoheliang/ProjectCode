include "BizMonitor.thrift"

/* 引入Common.thrift.thrift文件 */
include "Common.thrift"

/* 引入OutletVo.thrift文件 */
include "OutletVo.thrift"




namespace java com.froad.thrift.service


/**
 * OutletService
 */
service OutletService extends BizMonitor.BizMonitorService {



      /**
     * 同步白名单操作时，根据客户端id和商户id同步商户门店信息.
     * @param merchantId 商户id
     * @param merchantId 门店id
     * @param clientId    客户端id
     * @param isSynSucc  0-同步成功，1-同步失败
     * @param synType 0-同步通知，1-审核通知
     * @return Common.ResultVo
     */    
     Common.ResultVo syncOutletInfo(1:OutletVo.OutletVo outletVo,2:string isSynSucc,3:string synType);    
}
