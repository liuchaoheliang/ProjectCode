/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入OutletVo.thrift文件 */
include "OutletVo.thrift"

/* 引入OutletDetailVo.thrift文件 */
include "OutletDetailVo.thrift"

/* 引入OutletDetailSimpleInfoVo.thrift文件 */
include "OutletDetailSimpleInfoVo.thrift"

/* 引入OutletDetailSimpleVo.thrift文件 */
include "OutletDetailSimpleVo.thrift"

/* 引入OutletMongoInfoVo.thrift文件 */
include "OutletMongoInfoVo.thrift"

/* 引入OutletPreferVo.thrift */
include "OutletPreferVo.thrift"

namespace java com.froad.thrift.vo

/**
 * 门店添加的响应
 */
struct OutletAddVoRes {

	/** 响应基础信息 */
	1:Common.ResultVo result;
	
	/** 门店id */
	2:string outletId;
}

/**
 * 门店分页的响应
 */
struct OutletPageVoRes {

	/** 分页基础信息 */
	1:Common.PageVo page;
	/** 门店信息列表 */
	2:list<OutletVo.OutletVo> outletVoList;
}

/**
 * 门店详情分页的响应
 */
struct OutletDetailPageVoRes {

	/** 分页基础信息 */
	1:Common.PageVo page;
	/** 门店详情信息列表 */
	2:list<OutletDetailVo.OutletDetailVo> outletDetailVoList;
}


/**
 * 门店详情概要信息分页的响应
 */
struct OutletDetailSimpleInfoPageVoRes {

	/** 分页基础信息 */
	1:Common.PageVo page;
	/** 门店详情信息列表 */
	2:list<OutletDetailSimpleInfoVo.OutletDetailSimpleInfoVo> outletDetailSimpleInfoVoList;
}

/**
 * 门店详情概要信息分页的响应
 * 商品详情页、券详情页根据商品所属商户展示相应门店信息
 */
struct OutletMongoInfoPageVoRes {

	/** 分页基础信息 */
	1:Common.PageVo page;
	/** 门店详情信息列表 */
	2:list<OutletMongoInfoVo.OutletMongoInfoVo> utletMongoInfoVoList;
}

struct OutletDetailSimplePageVoRes {

	/** 分页基础信息 */
	1:Common.PageVo page;
	/** 门店详情信息列表 */
	2:list<OutletDetailSimpleVo.OutletDetailSimpleVo> outletDetailSimpleVoList;
}	
	
	/**
 * 门店惠付分页的响应
 */
struct OutletPreferPageVoRes {

	/** 分页基础信息 */
	1:Common.PageVo page;
	/** 门店详情信息列表 */
	2:list<OutletPreferVo.OutletPreferVo> outletPreferVoList;
}