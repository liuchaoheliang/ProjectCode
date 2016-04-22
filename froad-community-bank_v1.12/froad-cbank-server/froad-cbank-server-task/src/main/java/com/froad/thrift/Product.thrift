include "BizMonitor.thrift"

namespace java com.froad.thrift.service


/**
 * 返回结果
 */
struct ResultVo {
     /**
      * 返回码 : 0000:成功 ,失败：xxxx
      */
     1:string resultCode,
     
     /**
      * 返回信息
      */
     2:string resultDesc
}

/**
 * ProductService
 */
service ProductService extends BizMonitor.BizMonitorService {

    
    
	 /**
	  * 启用商户时 将除团购以外的商品的 该普通商户的未提交状态变成待审核
	  * @param string
	  * @return ResultVo
	  */
	 ResultVo validMerchantProductBatch(1:string merchantId),

	   /**
     * 销售期到期自动将上架状态变成下架
	 */
    ResultVo autoOffShelfProductSaleEnd()
	
}

