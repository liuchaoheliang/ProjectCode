namespace java com.froad.thrift.service

/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

include "MerchantOutletFavoriteVo.thrift"


/**
 * MerchantOutletFavoriteService
 */
service MerchantOutletFavoriteService extends BizMonitor.BizMonitorService {

	/*
	 *是否收藏门店	
	 */
	bool isExitsStoreOutletInfo(1:string clientId,2:i64 memberCode,3:string outletId);

    /**
     * 增加商户收藏 StoreOutletInfoVo
     * @param storeOutletInfoVo
     * @return long    主键ID
     */
    Common.ResultVo addStoreOutletInfoVo(1:string clientId,2:i64 memberCode,3:MerchantOutletFavoriteVo.StoreOutletInfoVo storeOutletInfoVo);


    /**
     * 取消商户收藏 StoreOutletInfoVo
     * @param storeOutletInfoVo
     * @return boolean    
     */
    bool deleteStoreOutletInfoVo(1:string clientId,2:i64 memberCode,3:string outletId);



    /**
     * 商户收藏查询接口 StoreOutletInfoVo
     * @param storeOutletInfoVo
     * @return List<StoreOutletInfoVo>
     */
    list<MerchantOutletFavoriteVo.StoreOutletInfoVo> getStoreOutletInfoVo(1:string clientId,2:i64 memberCode);

	

       /**
     * 商户收藏分页查询接口 StoreProductInfoVo
     * @param storeProductInfoVo
     * @return List<StoreProductInfoVo>
     */
    MerchantOutletFavoriteVo.StoreOutletInfoPageVoRes getStoreOutletInfoByPage(1:Common.PageVo page,2:string clientId,3:i64 memberCode);


   /*
	*统计用户收藏的商品和门店
	*	
	*/
	map<string,i32> countProductStoreOutletInfo(1:string clientId,2:i64 memberCode);



    /**
     * 收货信息增加 RecvInfoVo
     * @param recvInfoVo
     * @return long    主键ID
     */
     Common.ResultVo addRecvInfoVo(1:string clientId,2:i64 memberCode,3:MerchantOutletFavoriteVo.RecvInfoVo recvInfoVo);


   /**
     * 查询收货信息 RecvInfoVo
     * @param recvInfoVo
     * @return List<RecvInfoVo>
     */
    list<MerchantOutletFavoriteVo.RecvInfoVo> getRecvInfoVo(1:string clientId,2:i64 memberCode,3:string isDefault,4:string type);

    /**
     * 查询收货信息 RecvInfoVo 按主键
     * @param recvId
     * @return List<RecvInfoVo>
     */
    MerchantOutletFavoriteVo.RecvInfoVo getRecvInfoVoById(1:string clientId,2:i64 memberCode,3:string recvId);

    /**
     * 删除收货地址信息 RecvInfoVo
     * @param recvInfoVo
     * @return boolean    
     */
    bool deleteRecvInfoVo(1:string clientId,2:i64 memberCode,3:string recvId);

    /**
     * 修改收货地址信息 RecvInfoVo
     * @param recvInfoVo
     * @return boolean    
     */
     MerchantOutletFavoriteVo.MerchantOutletFavoriteVORes updateRecvInfoVo(1:string clientId,2:i64 memberCode,3:MerchantOutletFavoriteVo.RecvInfoVo recvInfoVo);


 /**
     * 增加提货地址信息 DeliverInfoVo
     * @param deliverInfoVo
     * @return long    主键ID
     */
    Common.ResultVo addDeliverInfoVo(1:string clientId,2:i64 memberCode,3:MerchantOutletFavoriteVo.DeliverInfoVo deliverInfoVo);

	/*
	 *查询提货信息
	 */
   list<MerchantOutletFavoriteVo.DeliverInfoVo> getDeliverInfoVo(1:string clientId,2:i64 memberCode,3:string isDefault);
   
    /*
	 *删除提货信息
	 */
   bool deleteDeliverInfoVo(1:string clientId,2:i64 memberCode,3:string deliveryId);
   
    /*
	 *更新提货信息
	 */
   Common.ResultVo updateDeliverInfoVo(1:string clientId,2:i64 memberCode,3:MerchantOutletFavoriteVo.DeliverInfoVo deliverInfoVo);
   
   
   
   
}