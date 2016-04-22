namespace java com.froad.thrift.vo.order

include "BizMonitor.thrift"
include "Common.thrift"

/**
 * 商户商圈标列表签查询req
 */
struct BusinessZoneTagListReq {
	/**客户端号*/
	1:string clientId;

	/**状态：全部0；启用1; 禁用2; 新增审核中3; 编辑审核中4; 禁用审核中5*/
	2:string status;

	/**商户标签名称*/
	3:string tagName;

    /**分页*/
    4:Common.PageVo pageVo;
}


/**
 * 商户商圈标签查询res
 */
struct BusinessZoneTagListRes {
	/**多银行列表数据*/
	1:list<BusinessZoneTagVo> businessZoneTagList;
	
	/**查询结果*/
	2:Common.ResultVo resultVo;	
	
	/**分页数据*/
	3:Common.PageVo pageVo;
}
/**
*商户商圈标签vo
*/
struct BusinessZoneTagVo {
	/**商户商圈标签ID*/
	1:string  id

	/**商户商圈名称*/
	2:string tagName;
	
	/**客户端名称*/
	3:string clientName;
	
	/**所在地区*/
	4:string areaName;

	/**描述*/
	5:string desc;

	/**状态：启用1; 禁用2; 新增审核中3; 编辑审核中4; 禁用审核中5*/
	6:string status;

}


/**
*新增或编辑商圈标签信息req
*/
struct BusinessZoneTagAddReq {
	/**客户端Id*/
	1:string clientId;
	
	/**商圈标签名称*/
	2:string tagName;

	/**是否启用:启用1; 禁用2; 新增审核中3; 编辑审核中4; 禁用审核中5*/
	3:string status;

	/**标签序号*/
	4:string sortValue;

	/**所在区域一级区域Id*/
	5:string fareadId;

	/**所在区域二级区域Id*/
	6:string sareadId;

	/**所在区域三级区域Id*/
	7:string tareadId;

	/**所在区域四级区域Id*/
	8:string oareadId;

	/**描述*/
	9:string desc;

	/**操作标识：1：新增；2：编辑*/
	10:string flag;
	
	/**id*/
	11:string id;

}



/**
*新增或编辑商圈标签信息res
*/
struct BusinessZoneTagAddRes {
	/**相应结果*/
	1:Common.ResultVo resultVo;	
}



/**
*商圈标签详情req
*/
struct BusinessZoneTagDetailReq {
	/**
	*商圈标签Id
	*/
	1:string id;
}

/**
*商圈标签详情res
*/
struct BusinessZoneTagDetailRes {

	/**客户端Id*/
	1:string clientId;
	
	/**商圈标签名称*/
	2:string tagName;

	/**是否启用:启用1; 禁用2; 新增审核中3; 编辑审核中4; 禁用审核中5*/
	3:string status;

	/**标签序号*/
	4:string sortValue;

	/**所在区域一级区域Id*/
	5:string fareadId;

	/**所在区域二级区域Id*/
	6:string sareadId;

	/**所在区域三级区域Id*/
	7:string tareadId;

	/**所在区域四级区域Id*/
	8:string oareadId;

	/**描述*/
	9:string desc;

	/**商户商圈id*/
	10:string id;

	/**响应结果*/
	11:Common.ResultVo resultVo;	

}

/**
*商圈标签禁用req
*/
struct BusinessZoneTagDeleteReq {
	/**
	**商户商圈标签id
	*/
	1:string id
}

/**
*商圈标签禁用res
*/
struct BusinessZoneTagDeleteRes {
	/**响应结果*/
	1:Common.ResultVo resultVo;	
}


/**
*商圈标签门店导出req
*/
struct BusinessZoneTagExportOutletReq {
	/**
	**商户商圈标签id
	*/
	1:string id
}

/**
*商圈标签门店导出res
*/
struct BusinessZoneTagExportOutletRes {
	/**
	**导出文件url
	*/
	1:string url;

	/**响应结果*/
	2:Common.ResultVo resultVo;	
}

struct GenerateActivityNoReq {
	/**
	*客户端id
	*/
	1:string clientId;
	
	/**活动类型:1商户活动；2门店活动；3商品活动*/
	2:string activityType
}

struct GenerateActivityNoRes {
	/**活动编号*/
	1:string activiyNo;
	
	/**响应结果*/
	2:Common.ResultVo resultVo;	
}

/**
 * Boss商户商圈标签
 */
service BusinessZoneTagService extends BizMonitor.BizMonitorService {

	/**
	*商户商圈标签列表查询
	*/
	BusinessZoneTagListRes getBusinessZoneTagList(1:BusinessZoneTagListReq req);
	
	/**
	*查看商户商圈标签详情
	*/
	BusinessZoneTagDetailRes getBusinessZoneTagDetail(1:BusinessZoneTagDetailReq req);
	
	/**
	*新增商户商圈标签信息
	*/
	BusinessZoneTagAddRes addBusinessZoneTag(1:BusinessZoneTagAddReq req);
	
	
	/**
	*商户商圈标签禁用
	*/
	BusinessZoneTagDeleteRes deleteBusinessZoneTag(1:BusinessZoneTagDeleteReq req);

	/**
	*商户商圈标签门店导出
	*/
	BusinessZoneTagExportOutletRes exportBusinessZoneTagOutlet(1:BusinessZoneTagExportOutletReq req);

	
	/**
	*活动编号生成
	*/
	GenerateActivityNoRes generateActivityNo(1:GenerateActivityNoReq req);
}






