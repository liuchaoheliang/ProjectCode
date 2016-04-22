namespace java com.froad.thrift.vo

/**
 * 门店评论
 */
struct OutletCommentVo {

	/** 主键id */
	1:string id;
	/** 商户id */
	2:string merchantId;
	/** 门店id */
	3:string outletId
	/** 创建id */
	4:i64 createTime;
	/** 门店名称 */
	5:string outletName;
	/** 排序 */
	6:i32 orderValue;
	/** 会员编号 */
	7:string memberCode;
	/** 会员名称 */
	8:string memberName;
	/** 星级 */
	9:i32 starLevel;
	/** 评论描述 */
	10:string commentDescription;
	/** 回复 */
	11:string recomment;
	/** 客户端id */
	12:string clientId;
	
	/** 评论来源类型:0-商户评论  默认为普通评论,1-面对面评论,2-其它**/
	13:i32 commentType;
	
	/** 1 机构码 */
	14:string forgCode;
	/** 2 机构码 */
	15:string sorgCode;
	/** 3 机构码 */
	16:string torgCode;
	/** 4 机构码 */
	17:string lorgCode;
	
	/** 机构码 */
	18:string orgCode;
	
	/** 门店名称 */
	19:string merchantName;
	
	/** 回复时间 */
	20:i64 recommentTime;
	/** 商户用户名称-回复人 */
	21:string merchantUserName;
	
	/** 回复是否为空 - 查询使用 */
	22:RecommentNotEmptyVo recommentNotEmpty;
	/** 星级过滤 - 查询使用 */
	23:StarLevelFilterVo starLevelFilter;
	/** 创建时间过滤 - 查询使用 */
	24:CreateTimeFilterVo createTimeFilter;
	
	/** 门店电话 */
	25:string outletPhone;
	
	/** 门店图片 */
	26:string outletImage;
	
	/** 评论人头像 */
	27:string memberHead;
	/** 回复人头像 */
	28:string recommentHead;
	/** 订单ID */
	29:string orderId;
}

/**
 * 回复是否为空
 * 
 * 查询使用
 */
struct RecommentNotEmptyVo {

	/** 是否空 */
	1:bool notEmpty;
}

/**
 * 星级过滤 - 查询使用
 */
struct StarLevelFilterVo {

	/** 星级 */
	1:i32 starLevel;
}

/**
 * 创建时间过滤 - 查询使用
 */
struct CreateTimeFilterVo {

	/** 开始时间 */
	1:i64 begTime;
	/** 接收时间 */
	2:i64 endTime;
}