/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入OutletCommentVo.thrift文件 */
include "OutletCommentVo.thrift"

namespace java com.froad.thrift.vo

/**
 * 门店评论分页的响应
 */
struct OutletCommentPageVoRes {

	/** 分页基础信息 */
	1:Common.PageVo page;
	/** 门店评论信息列表 */
	2:list<OutletCommentVo.OutletCommentVo> outletCommentVoList;
}

/**
 * 门店评论级别统计
 */
struct OutletCommentLevelAmountVo {

	/** 门店id */
	1:string outletId;
	/** 门店名称 */
	2:string outletName;

	/** 星级 1 的数量 */
	3:i32 levelAmountOne;
	/** 星级 2 的数量 */
	4:i32 levelAmountTwo;
	/** 星级 3 的数量 */
	5:i32 levelAmountThree;
	/** 星级 4 的数量 */
	6:i32 levelAmountFour;
	/** 星级 5 的数量 */
	7:i32 levelAmountFive;
}

/**
 * 新增返回bean
 */
struct OutletCommentAddVoRes {
	
	/** 公共响应信息 */
	1:Common.ResultVo result;
	/** 主键id */
	2:string id;
}