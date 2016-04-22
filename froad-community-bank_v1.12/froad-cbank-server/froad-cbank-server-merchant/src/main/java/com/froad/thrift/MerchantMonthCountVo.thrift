namespace java com.froad.thrift.vo

/**
 * 商户销售月底统计
 */
struct MerchantMonthCountVo {

	/** 主键 */
	1:i64     id;                 
	/** 客户端ID */
	2:string clientId;           
	/** 商户ID */
	3:string  merchantId;         
	/** 年 */
	4:string  year;               
	/** 月 */
	5:string  month;              
	/** 月销售额 */
	6:double     monthMoney;         
	/** 月团购订单数 */
	7:i32     groupOrderCount;    
	/** 月名优特惠订单数 */
	8:i32     sellOrderCount;     
	/** 月面对面订单数 */
	9:i32     faceOrderCount;     
	/** 商品ID */
	10:string productId;          
	/** 最高月销售量 */
	11:i32    maxCount;          
	/** 月团购销售额 */
	12:double     groupOrderMoney;    
	/** 月名优特惠销售额 */
	13:double     sellOrderMoney;     
	/** 月面对面销售额 */
	14:double     faceOrderMoney;     
	/** 商品名称 */
	15:string  productName;  
	/** 最高月销售额 */
	16:double    maxMoney;           
}

