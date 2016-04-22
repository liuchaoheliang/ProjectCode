namespace java com.froad.thrift.service

include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"
include "QueryOrderVo.thrift"


service OrderQueryService extends BizMonitor.BizMonitorService {

    /*******************商户H5接口*******************/
    
    /**
     * 查询列表，团购和面对面订单
     */
    QueryOrderVo.QueryOrderByMerchantPhoneVoRes queryOrderByMerchantPhone(1:QueryOrderVo.QueryOrderByMerchantPhoneVoReq req);
    /**
     * 查询详细内容，团购和面对面订单详细
     */
    QueryOrderVo.GetOrderDetailByMerchantPhoneVoRes getOrderDetailByMerchantPhone(1:QueryOrderVo.GetOrderDetailByMerchantPhoneVoReq req);
    
    
    /*******************商户管理平台*******************/
    /**
     * 商户管理平台-订单列表
     */
    QueryOrderVo.QueryOrderByMerchantManageVoRes queryOrderByMerchantManage(1:QueryOrderVo.QueryOrderByMerchantManageVoReq req);

    /**
     * 商户管理平台-订单导出
     */
    Common.ExportResultRes exportOrderByMerchantManage(1:QueryOrderVo.QueryOrderByMerchantManageVoReq req);
    
    /**
     * 商户管理-订单详细
     */
    QueryOrderVo.GetOrderDetailByMerchantManageVoRes getOrderDetailByMerchantManage(1:QueryOrderVo.GetOrderDetailByMerchantManageVoReq req);
    
    /**
     * v1.1分支 迭代1.1.0新增
     * 商户管理-订单详细-优化
     */
    QueryOrderVo.GetOrderDetailByBankManageVoRes getOrderDetailByMerchantManageNew(1:QueryOrderVo.GetOrderDetailByMerchantManageVoReq req);

    /*******************银行管理平台********************/

    /**
     * 银行管理平台-订单列表
     */
     QueryOrderVo.QueryOrderByBankManageVoRes queryOrderByBankManage(1:QueryOrderVo.QueryOrderByBankManageVoReq req);

    /**
     * 银行管理平台-订单导出
     */
     Common.ExportResultRes exportOrderByBankManage(1:QueryOrderVo.QueryOrderByBankManageVoReq req);
     
     
       /**
     * 银行管理平台-精品商城订单列表
     */
     QueryOrderVo.QueryBoutiqueOrderByBankManageVoRes queryBoutiqueOrderByBankManage(1:QueryOrderVo.QueryBoutiqueOrderByBankManageVoReq req);

    /**
     * 银行管理平台-精品商城订单导出
     */
     Common.ExportResultRes exportBoutiqueOrderByBankManage(1:QueryOrderVo.QueryBoutiqueOrderByBankManageVoReq req);
     

     /**
     * 银行管理平台-订单详细
     */
     QueryOrderVo.GetOrderDetailByBankManageVoRes getOrderDetailByBankManage(1:QueryOrderVo.GetOrderDetailByBankManageVoReq req);
     
     /*******************BOSS平台********************/
     
     /**
      * Boss 查询订单列表
      */
     QueryOrderVo.OrderListByBossRes queryOrderListByBoss(1:QueryOrderVo.OrderListByBossReq req);
     
     /**
      * Boss 查看订单详细
      */
     QueryOrderVo.SubOrderByBossRes getSubOrderByBoss(1:QueryOrderVo.SubOrderByBossReq req);
     
     
     /**
      * 根据大订单号-查询全部子订单内容（不包括面对面）
      */
     QueryOrderVo.OrderDetailRes getOrderDetailById(1:string orderId, 2:string clientId);

          
     /**
      * 查询商品送积分订单明细
      */
     QueryOrderVo.QueryGivePointsProductByBossRes queryGivePointsProductByBoss(1:QueryOrderVo.QueryGivePointsProductByBossReq req);

     /**
      * 查询配送商品所有相关订单的发货人信息
      */
     QueryOrderVo.QueryRecvInfoForProductByBossRes queryRecvInfoForProductByBoss(1:QueryOrderVo.QueryRecvInfoForProductByBossReq req);

     /**
      * 查询商户结算情况
      */
     QueryOrderVo.QueryMerchantSettlementRes queryMerchantSettlement(1:QueryOrderVo.QueryMerchantSettlementReq req); 
}