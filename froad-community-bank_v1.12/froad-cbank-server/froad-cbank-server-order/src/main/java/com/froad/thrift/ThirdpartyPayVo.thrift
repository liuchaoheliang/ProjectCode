namespace java com.froad.thrift.vo.payment

include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"


/**合并支付请求体*/
struct CombineSettleReq{
	1:string paymentId;
	2:string payValue;
	3:string merchantAndOutletId;
	4:string clientId;
	5:string paymentOrgNo;
}
/**
 * 返回结果
 */
struct ResultVo {
     /**
      * 返回码 : 0000:成功 ,失败：xxxx
      */
     1:string resultCode;
     
     /**
      * 返回信息
      */
     2:string resultDesc;
     
     3:string reqId;
}