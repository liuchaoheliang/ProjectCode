/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

namespace java com.froad.thrift.vo




struct MerchantOutletFavoriteVORes {

	
	1:Common.ResultVo result;

	2:string id;
}

/**
 * 门店收藏
 */
struct StoreOutletInfoVo{

	/** 商户id */
	 1: optional string merchantId;
	 /** 门店id */
	 2: optional string outletId;
	 /** 门店名称 */
	 3: optional string outletName;
	 /** 门店图片 */
	 4: optional string outletImage;
}




/**
 * 提货信息
 */
struct DeliverInfoVo{

	/** 提货信息编号 */
	 1: optional string deliveryId;
	 /** 提货人 */
	 2: optional string consignee;
	 /** 提货电话 */
	 3: optional string phone;
	 /**是否为默认,0:否 1:是 */
	 4: optional string isDefault;
	   /**地区Id */
	 5: optional i64 areaId;
	  /** 树路径 */
	 6: optional string treePath;    
	  /** 树路径名称 */
	 7: optional string treePathName; 
}


/**
 * 收货信息
 */
struct RecvInfoVo{

	/** 收货信息编号 */
	 1: optional string recvId;
	 /** 收货人 */
	 2: optional string consignee;
	 /** 收货地址 */
	 3: optional string address;
	 /** 电话 */
	 4: optional string phone;
	   /**是否为默认,0:否 1:是 */
	 5: optional string isDefault;
	  /**地区Id */
	 6: optional i64 areaId;
	   /** 树路径 */
	 7: optional string treePath;    
	  /** 树路径名称 */
	 8: optional string treePathName; 
	 /** 是否启用 */
	 9: optional string isEnable;
	  /** 收货类型 */
	 10: optional string type;
	   /** 开始时间 */ 
	 11: optional i64 createTime;
          /** 是否为默认提货地址 */ 
	 12:optional string isDeliverDefault;
}



/**
 * 商户门店收藏
 */
struct MerchantOutletFavoriteVo{

	/** 主键id */
	1:i64 _id;
	/** 门店收藏列表 */
	2:list<StoreOutletInfoVo> storeOutletInfo;
	/** 提货信息 */
	3:DeliverInfoVo deliverInfo;
	/** 收货信息列表 */
	4:list<RecvInfoVo> recvInfo;

}




/**
 * 门店收藏分页的响应结果
 */
struct StoreOutletInfoPageVoRes {

	/** 分页基础信息 */
	1:Common.PageVo page;
	/** 门店收藏信息列表 */
	2:list<StoreOutletInfoVo> storeOutletInfoVoList;
}