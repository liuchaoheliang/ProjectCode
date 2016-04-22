namespace java test

include "Common.thrift"

/**
 * 12.操作库存请求
 */
struct StoreVoReq {
     /**客户端号*/
     1:required string clientId;

     /**订单号*/
     2:required string orderId;
     
     /**操作类型:1 加库存（还库存）  0 减库存（扣库存）*/
     3:required string operationType;
}

/**
 * 12.操作库存响应
 */
struct StoreVoRes {
     /** 结果信息 */
     1:Common.ResultVo resultVo;
}

/**
 * 订单管理模块
 */   
service OrderService {

     /**
      * 操作库存
      */
     StoreVoRes storeProcess(1:StoreVoReq storeVoReq);
      
}
