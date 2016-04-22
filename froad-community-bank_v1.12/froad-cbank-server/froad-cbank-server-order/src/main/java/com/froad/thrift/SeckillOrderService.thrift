namespace java com.froad.thrift.service

include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"
include "SeckillOrderVo.thrift"


/**
 * 秒杀订单模块
 */   
service SeckillOrderService extends BizMonitor.BizMonitorService{
     /**
      * 创建订单接口
      */
     SeckillOrderVo.AddSeckillOrderVoRes addOrder(1:SeckillOrderVo.AddSeckillOrderVoReq addSeckillOrderVoReq);

     /**
      * 补全收货、提货信息接口
      */
     SeckillOrderVo.AddDeliveryInfoVoRes updateDeliveryInfo(1:SeckillOrderVo.AddDeliveryInfoVoReq addDeliveryInfoVoReq);

     /**
      * 创建订单接口（版本1.1 - 2015.06.07需求变更）
      */
     SeckillOrderVo.AddOrderForSeckillVoRes addOrderForSeckill(1:SeckillOrderVo.AddOrderForSeckillVoReq addOrderForSeckillVoReq);

}