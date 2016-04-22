namespace java com.froad.thrift.vo.order

include "BizMonitor.thrift"
include "Common.thrift"

/**
 * 多银行列表查询req
 */
struct BankAccessListReq {
	/**客户端号*/
	1:string clientId;
	
    /**分页*/
    2:Common.PageVo pageVo;
}


/**
 * 多银行列表查询res
 */
struct BankAccessListRes {
	/**多银行列表数据*/
	1:list<BankAccessVo> bankAccessList;
	
	/**查询结果*/
	2:Common.ResultVo resultVo;	
	
	/**分页数据*/
	3:Common.PageVo pageVo;
}
/**
*银行信息vo
*/
struct BankAccessVo {
	/**客户端号*/
	1:string clientNo;
	
	/**客户端名称*/
	2:string clientName;
	
	/**功能模块*/
	3:string functionDesc;
	
	/**支付方式*/
	4:string paymentMethodDesc;

	/**配置时间*/
	5:i64 createTime;

	/**更新时间*/
	6:i64 updateTime;
	
	/**编号*/
	7:i64 id;

}

/**
*银行信息详情请求req
*/
struct BankAccessDetailReq {
	/**
	*客户端id
	*/
	1:string clientId;
}
/**
*银行信息详情请求res
*/
struct BankAccessDetailRes {
	/**
	*客户端名称
	*/
	1:string cilentName;
	
	/**
	*功能模块列表
	*/
	2:list<FunctionModuleVo> functionList;
	
	/**
	*支付方式列表
	*/
	3:list<PaymentMethodVo> paymentList;
	
	/**查询结果*/
	4:Common.ResultVo resultVo;	

}



/**
*新增银行信息req
*/
struct BankAccessAddReq {
	/**
	*客户端Id
		*/
	1:string clientId;
	
	/**
	* 功能模块集合
	*/
	2:list<FunctionModuleVo> functionList;
	
	/**
	*支付方式集合：
	*/
	3:list<PaymentMethodVo> paymentList;

}

/**
*功能模块vo
*/
struct FunctionModuleVo {
	
	/**
	*商户模块类型:1:特惠商户;2:特惠商品;3:精品预售;4:扫码支付；5:积分兑换；
	*/
	1:string type;
	
	/**
	*功能模块别名
	*/
	2:string aliasName;
	
	/**
	*图标url
	*/
	3:string iconUrl;
	
	/**
	*排序值
	*/
	4:string sortValue;
}

/**
*支付方式vo
*/
struct PaymentMethodVo {

	/**
	*支付方式类型：1联盟积分；2：银行积分；20：贴膜卡积分；41：银联无卡积分；50：网银积分；51：快捷支付；
	*/
	1:string type;
	
	/**
	*文案
	*/
	2:string aliasName;
	
	/**
	*图标url
	*/
	3:string iconUrl;
}


/**
*银行信息新增res
*/
struct BankAccessAddRes {
	/**相应结果*/
	1:Common.ResultVo resultVo;	
}

/**
*银行信息编辑req
*/
struct BankAccessEditReq {
	/**
	*客户端Id
	*/
	1:string clientId;
	
	/**
	* 功能模块集合
	*/
	2:list<FunctionModuleVo> functionList;
	
	/**
	*支付方式集合：
	*/
	3:list<PaymentMethodVo> paymentList;

}

/**
*银行信息编辑res
*/
struct BankAccessEditRes {
	
	/**响应结果*/
	1:Common.ResultVo resultVo;	

}

/**
*银行信息删除req
*/
struct BankAccessDeleteReq {
	/**
	**客户端id
	*/
	1:string clientId
}

/**
*银行信息删除res
*/
struct BankAccessDeleteRes {
	/**响应结果*/
	1:Common.ResultVo resultVo;	
}


/**
*获取下拉框客户端信息req
*/
struct BankAccessClientListReq {

	/**
	*获取业务类型1:获取查询列表客户端下拉框集合信息；2：获取新增银行信息时客户端下拉框信息
	*/
	1:string type;
}

/**
*获取下拉框客户端信息res
*/
struct BankAccessClientListRes {

	/**响应结果*/
	1:Common.ResultVo resultVo;	
	
	/**
	*客户端集合
	*/
	2:list<BankAccessClientVo> clientList;
}

/**
*客户端信息vo
*/
struct BankAccessClientVo {
	
	/**
	*客户端Id
	*/
	1:string clientId;
	
	/**
	*客户端名称
	*/
	2:string clientName;
	
	/**
	*银行名称
	*/
	3:string bankName;

}




/**
 * Boss多银行接入
 */
service BankAccessService extends BizMonitor.BizMonitorService {

	/**
	*多银行列表查询
	*/
	BankAccessListRes getBankAccessList(1:BankAccessListReq req);
	
	/**
	*查看银行详情
	*/
	BankAccessDetailRes getBankAccessDetail(1:BankAccessDetailReq req);
	
	/**
	*新增银行信息
	*/
	BankAccessAddRes addBankAccess(1:BankAccessAddReq req);
	
	/**
	*编辑银行信息
	*/
	BankAccessEditRes editBankAccess(1:BankAccessEditReq req);
	
	/**
	*银行信息删除
	*/
	BankAccessDeleteRes deleteBankAccess(1:BankAccessDeleteReq req);
	
	/**
	*获取下拉框客户端信息
	*/
	BankAccessClientListRes getClientList(1:BankAccessClientListReq req);
	
	
	
}






