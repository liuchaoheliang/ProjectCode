include "ProductVo.thrift"
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitorVo.thrift"
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"
include "../../../../../../../froad-cbank-server-merchant/src/main/java/com/froad/thrift/OutletVo.thrift"
include "../../../../../../../froad-cbank-server-support/src/main/java/com/froad/thrift/struct.thrift"

namespace java com.froad.thrift.service

/**
 * ProductCategoryService
 */
service ProductCategoryService extends BizMonitor.BizMonitorService {

    /**
     * 增加 ProductCategory
     * @param OriginVo
     * @param ProductCategoryVo
     * @return AddProductCategoryVoRes
     */
    ProductVo.AddProductCategoryVoRes addProductCategory(1:Common.OriginVo originVo,2:ProductVo.ProductCategoryVo productCategoryVo),

    /**
     * 删除 ProductCategory
     * @param OriginVo
     * @param ProductCategoryVo
     * @return ResultVo    
     */
    Common.ResultVo deleteProductCategory(1:Common.OriginVo originVo,2:ProductVo.ProductCategoryVo productCategoryVo),

    /**
     * 修改 ProductCategory
     * @param OriginVo
     * @param ProductCategoryVo
     * @return ResultVo    
     */
    Common.ResultVo updateProductCategory(1:Common.OriginVo originVo,2:ProductVo.ProductCategoryVo productCategoryVo),

    /**
     * 查询 ProductCategory
     * @param productCategory
     * @return ProductCategoryVo
     */
    ProductVo.ProductCategoryVo getProductCategoryById(1:ProductVo.ProductCategoryVo productCategoryVo),
    
    /**
     * 同一个客户端同一个路径下面是否已经存在ProductCategory
     * @param productCategory
     * @return boolean
     */
    bool isProductCategoryExist(1:ProductVo.ProductCategoryVo productCategoryVo),
    
    /**
	 * 分页查询 ProductCategory
	 * @param productCategoryVo
	 * @param pageVo
	 * @return ProductCategoryPageVo
	 */
    ProductVo.ProductCategoryPageVo findCategorysByPage(1:ProductVo.ProductCategoryVo productCategoryVo,2:Common.PageVo pageVo),
    
    /**
	 * 分页查询 ProductCategory 特惠商品分类首页商品分类查询-H5用户端
	 * @param productCategoryVo
	 * @param pageVo
	 * @return ProductCategoryPageVo
	 */
    ProductVo.ProductCategoryPageVo queryProductCategorys(1:ProductVo.ProductCategoryVo productCategoryVo,2:Common.PageVo pageVo),
    
    /**
	 * 查询商品分类列表
	 * @param clientId
	 * @return list<ProductCategoryVo>
	 */
    list<ProductVo.ProductCategoryVo> findCategorys(1:string clientId),
    
    /**
     * 批量增加 ProductCategory
     * @param list<ProductCategoryVo>
     * @return List<AddProductCategoryVoRes>
     */
    list<ProductVo.AddProductCategoryVoRes> addProductCategoryBatch(1:Common.OriginVo originVo,2:list<ProductVo.ProductCategoryVo> productCategoryVos),

    /**
	 * 查询商品分类--特惠商品分类首页商品分类查询-H5用户端
	 * @param clientId 客户端ID
	 * @param parentId 商品分类ID
	 * @param areaId 地区ID
	 * @return list<ProductCategoryVo>
	 */
    list<ProductVo.ProductCategoryVo> queryH5ProductCategorys(1:string clientId,2:i64 parentId,3:i64 areaId),
    
    /**
	 * 分级查询商品分类--新加特惠商品分类商户管理平台用到
	 * @param clientId 客户端ID
	 * @param parentId 商品分类ID
	 * @return list<ProductCategoryVo>
	 */
    list<ProductVo.ProductCategoryVo> queryManageProductCategorys(1:string clientId,2:i64 parentId),
    
    /**
	 * H5精品商城商品分类查询
	 * @param clientId 客户端ID
	 * @param parentId 商品分类ID
	 * @return list<ProductCategoryVo>
	 */
    list<ProductVo.ProductCategoryVo> queryBoutiqueGoodsCategorys(1:string clientId,2:i64 parentId),
    
    /**
	 * H5类目推荐的商品分类查询
	 * @param clientId 客户端ID
	 * @param isMall 是否是精品商城的商品分类
	 * @return list<ProductCategoryVo>
	 */
    list<ProductVo.ProductCategoryVo> queryRecommendProductCategorys(1:string clientId,2:bool isMall),
	
	 /**
	 * 根据id集合查询商品分类集合
	 * @param clientId 客户端ID
	 * @param categoryIds 商品分类id集合
	 * @return list<ProductCategoryVo>
	 */
	list<ProductVo.ProductCategoryVo> getProductCategoryByIds(1:string clientId,2:list<string> categoryIds),
	
    
}

/**商品接口*/

/**
 * ProductService
 */
service ProductService extends BizMonitor.BizMonitorService {

    /**
     * 增加 Product
     * @param OriginVo
     * @param ProductInfoVo
     * @return AddProductVoRes
     */
    ProductVo.AddProductVoRes addProduct(1:Common.OriginVo originVo,2:ProductVo.ProductInfoVo productInfoVo),

    /**
     * 删除 Product
     * @param OriginVo
     * @param ProductOperateVoReq
     * @return ResultVo    
     */
    Common.ResultVo deleteProduct(1:Common.OriginVo originVo,2:ProductVo.ProductOperateVoReq productVoReq),

    /**
     * 修改 Product
     * @param OriginVo
     * @param ProductInfoVo
     * @return ResultVo    
     */
    Common.ResultVo updateProduct(1:Common.OriginVo originVo,2:ProductVo.ProductInfoVo productInfoVo),
    
    /**
     * 修改 Product确定的一些信息
     * @param OriginVo
     * @param ProductBaseVo
     * @return ResultVo    
     */
    Common.ResultVo updateProductInfo(1:Common.OriginVo originVo,2:ProductVo.ProductBaseVo productBaseVo),

    /**
     * 查询 Product详情商品管理商品时修改商品之前查询用到
     * @param ProductOperateVoReq
     * @return ProductInfoVo
     */
    ProductVo.ProductInfoVo getMerchantProductDetail(1:ProductVo.ProductOperateVoReq productVoReq),
    
    /**
     * 同一个客户端同一类型下面是否已经存在Product
     * @param product
     * @return boolean
     */
    bool isProductExist(1:ProductVo.ProductExistVoReq productExistVoReq),
    
    /**
	 * 分页查询 Product (商品管理)
	 * @param ProductFilterVoReq
	 * @param pageVo
	 * @return ProductBriefPageVo
	 */
    ProductVo.ProductBriefPageVo findMerchantProductsByPage(1:ProductVo.ProductFilterVoReq productFilterVoReq,2:Common.PageVo pageVo),
    
    /**
	 * 分页查询 Product (H5用户查看)
	 * @param ProductFilterVoReq
	 * @param pageVo
	 * @return ProductBriefPageVoRes
	 */
    ProductVo.ProductBriefPageVoRes queryProducts(1:ProductVo.ProductFilterVoReq productFilterVoReq,2:Common.PageVo pageVo),
    
    /**
     * 查询 Product详情(H5用户查看)
     * @param ProductOperateVoReq
     * @return ProductInfoVo
     */
    ProductVo.ProductInfoVo queryProductDetail(1:ProductVo.ProductOperateVoReq productVoReq),
    
    /**
     * 上下架 Product
     * @param ProductStatusVoReq
     * @return boolean    
     */
    Common.ResultVo updateProductStatus(1:Common.OriginVo originVo,2:ProductVo.ProductStatusVoReq productStatusVoReq),
    
    /**
     * 批量上下架 Product
     * @param list<ProductStatusVoReq>
     * @return ResultVo    
     */
    Common.ResultVo updateProductStatusBatch(1:Common.OriginVo originVo,2:ProductVo.ProductStatusBatchVoReq productStatusBatchVoReq),
    
    /**
     * 新建面对面商品 返回二维码
     * @param OutletProductVo
     * @return AddOutletProductVoRes
     */
    ProductVo.AddOutletProductVoRes addOutletProduct(1:Common.OriginVo originVo,2:ProductVo.OutletProductVo outletProductVo),
    
   /**
     * H5新建面对面商品 返回二维码
     * @param OutletProductVo
     * @return AddOutletProductVoRes
     */
    ProductVo.AddOutletProductVoRes addH5OutletProduct(1:Common.OriginVo originVo,2:ProductVo.OutletProductDiscountVo outletProductDiscountVo),
    
    /**
     * 扫码后查看面对面商品详情
     * @param OutletProductVoReq
     * @return OutletProductVoRes
     */
    ProductVo.OutletProductVoRes getOutletProduct(1:ProductVo.OutletProductVoReq outletProductVoReq),
    
    /**
     * 删除面对面商品
     * @param OutletProduct
     * @return boolean   
     */
    Common.ResultVo deleteOutletProduct(1:Common.OriginVo originVo,2:ProductVo.OutletProductVo outletProductVo),
    
    /**
     * 分页查询面对面商品
     * @param OutletProduct
     * @return OutletProductPageVo   
     */
    ProductVo.OutletProductPageVo getOutletProductListByPage(1:ProductVo.OutletProductVo outletProductVo,2:Common.PageVo pageVo),
    
    /**
     * 分页查询商品评价列表(商户管理用到)
     * @param ProductCommentFilterReq
	 * @param pageVo
	 * @return ProductCommentPageVo
	 */
    ProductVo.ProductCommentPageVo getProductCommentList(1:ProductVo.ProductCommentFilterReq productCommentFilterReq,2:Common.PageVo pageVo),
    
    /**
     * 商品评价详情查询(商户管理用到)
     * @param ProductCommentVo
     * @return ProductCommentVo  
     */
    ProductVo.ProductCommentVo getProductCommentDetail(1:ProductVo.ProductCommentVo productCommentVo),
    
    /**
	 * 删除商品评价(商户管理用到)
	 * @param ProductCommentVo
	 * @return Boolean
	 */
    Common.ResultVo deleteProductComment(1:ProductVo.ProductCommentVo productCommentVo),
    
    /**
	 * 商户回复商品评价(商户管理用到)
	 * @param ProductCommentVo
	 * @return Boolean
	 */
    Common.ResultVo replayProductComment(1:ProductVo.ProductCommentVo productCommentVo),
    
    /**
	 * 批量商户回复商品评价(商户管理用到)
	 * @param list<ProductCommentVo>
	 * @return String
	 */
    Common.ResultVo replayProductCommentBatch(1:list<ProductVo.ProductCommentVo> productCommentVo),
    
    /**
	 * 用户评价商品(用户H5用到)
	 * @param ProductCommentVo
	 * @return Boolean
	 */
    Common.ResultVo addProductComment(1:ProductVo.ProductCommentVo productCommentVo),
    
    /**
	 * 用户更新商品评价(用户H5用到)
	 * @param ProductCommentVo
	 * @return Boolean
	 */
    Common.ResultVo updateProductComment(1:ProductVo.ProductCommentVo productCommentVo),
    
    /**
     * 商品评价内容查询(H5用到)
     * @param ProductCommentVo
     * @return ProductCommentVo  
     */
    ProductVo.ProductCommentVo queryProductComment(1:ProductVo.ProductCommentVo productCommentVo),
    
    /**
     * 用户分页查看商品评价,用户购买商品时候 查看评价用到
     * @param ProductCommentFilterReq
	 * @param pageVo
	 * @return ProductCommentPageVo
	 */
    ProductVo.ProductCommentPageVo queryProductComments(1:ProductVo.ProductCommentFilterReq productCommentFilterReq,2:Common.PageVo pageVo),
    
    /**
     * 商品关联活动
     * @param ProductRelateActivitiesVo
	 * @return ResultVo(提示信息)
	 */
    Common.ResultVo addProductActivties(1:Common.OriginVo originVo,2:ProductVo.ProductRelateActivitiesVo relateActivities),
    
    /**
     * 商品关联门店
     * @param ProductRelateOutletVo
	 * @return ResultVo(提示信息)
	 */
    Common.ResultVo addProductOutlets(1:Common.OriginVo originVo,2:ProductVo.ProductRelateOutletVo outlets),
    
	 /**
      * 查询商品所属区下面的网店
	  * @param ProductAreaVo
	  * @return list<ProductAreaVo>
	  */
	 list<ProductVo.ProductAreaVo> getProductAreaOutlets(1:ProductVo.ProductAreaVo productAreaVo),
	 
	 /**
	  * 商品批量失效
	  * @param string
	  * @return ResultVo
	  */
	 Common.ResultVo invalidProductBatch(1:Common.OriginVo originVo,2:ProductVo.InvalidProductBatchVo invalidProductBatchVo),
	 
	 /**
	  * 启用商户时 将除团购以外的商品的 该普通商户的未提交状态变成待审核
	  * @param string
	  * @return ResultVo
	  */
	 Common.ResultVo validMerchantProductBatch(1:string merchantId),
	 
	 /**
	  * 商品批量禁用，启用
	  * @param string
	  * @return bool
	  */
	 Common.ResultVo endisableProductBatch(1:Common.OriginVo originVo,2:ProductVo.InvalidProductBatchVo invalidProductBatchVo,3:string flag),
	 
	 /**
     * 查询评论数量
     * @param ProductCommentFilterReq
	 * @return int
	 */
    i32 queryProductCommentCount(1:ProductVo.ProductCommentFilterReq productCommentFilterReq),
    
    /**
     * 批量删除 Product
     * @param ProductStatusBatchVoReq
     * @return ResultVo    
     */
    Common.ResultVo deleteProductBatch(1:Common.OriginVo originVo,2:ProductVo.ProductStatusBatchVoReq productStatusBatchVoReq),
    
     /**
     * 获取 ProductStatus
     * @param ProductStatusVoReq
     * @return list<ProductStatusVoReq>    
     */
    list<ProductVo.ProductStatusVoReq> getProductStatus(),

    /**
     * 批量更改商品审核状态 ProductAuditState
     * @param ProductStatusVoReq
     * @return bool   
     */
    Common.ResultVo updateAuditProductBatch(1:Common.OriginVo originVo,2:list<ProductVo.ProductAuditVo> productAuditVoList),
    
    /**
     * 获取 getProductBaseInfo
     * @param clientId
     * @param list<ProductStoreFilterVo> products
     * @return list<ProductBaseInfoVo>    
     */
    list<ProductVo.ProductBaseInfoVo> getProductBaseInfo(1:string clientId,2:list<ProductVo.ProductStoreFilterVo> products),
    
    /*
	 *是否收藏商品	
	 */
	bool isExitsStoreProductInfo(1:string clientId, 2:i64 memberCode,3:string productId),

    /**
     * 增加 StoreProductInfoVo
     * @param storeProductInfoVo
     * @return long    主键ID
     */
    Common.ResultVo addStoreProductInfoVo(1:string clientId,2:i64 memberCode,3:ProductVo.StoreProductInfoVo storeProductInfoVo),

    /**
     * 取消商品收藏 StoreProductInfoVo
     * @param storeProductInfoVo
     * @return boolean    
     */
    bool deleteStoreProductInfoVo(1:string clientId,2:i64 memberCode,3:string productId),

    /**
     * 商品收藏查询接口 StoreProductInfoVo
     * @param storeProductInfoVo
     * @return List<StoreProductInfoVo>
     */
    list<ProductVo.StoreProductInfoVo> getStoreProductInfoVo(1:string clientId,2:i64 memberCode),

    /**
     * 商品收藏分页查询接口 StoreProductInfoVo
     * @param storeProductInfoVo
     * @return List<StoreProductInfoVo>
     */
    ProductVo.StoreProductInfoPageVoRes getStoreProductInfoByPage(1:Common.PageVo page,2:string clientId,3:i64 memberCode),
    
    /**
     * 批量增加 Product
     * @param list<ProductInfoVo>
     * @return List<AddProductVoRes>
     */
    list<ProductVo.AddProductVoRes> addProductBatch(1:Common.OriginVo originVo,2:list<ProductVo.ProductInfoVo> productInfoVos),
    
     /**
     * 修改 Product模块的敏感词
     * @param opt 1表示新增0表示删除
     * @param word 敏感词
     * @return
     */
    void modifySenseWord(1:i32 opt, 2:string word),
    
    /**
     * 活动批量绑定商品
     * @param originVo 操作人信息
     * @param activityId 活动Id
     * @param productIds 商品Ids
	 * @return ResultVo(提示信息)
	 */
    Common.ResultVo addActivtyToProducts(1:Common.OriginVo originVo,2:i64 activityId,3:list<string> productIds),
    
    /**
     * 活动批量解除绑定商品
     * @param originVo 操作人信息
     * @param activtyId 活动id
     * @param productId 商品id 如果有值代表解除单条绑定 如果没有值解除该活动下所有商品绑定
	 * @return ResultVo(提示信息)
	 */
    Common.ResultVo removeActivtyFromProducts(1:Common.OriginVo originVo,2:i64 activtyId,3:string productId),
    
    /**
	 * 查询活动已经绑定的商品列表
	 * @param activtyId 活动id
	 * @param pageVo
	 * @return ActivityProductPageVo
	 */
    ProductVo.ActivityProductPageVo findActivtyProductsByPage(1:i64 activtyId,2:Common.PageVo pageVo),
    
    /**
	 * 查询可以绑定活动的商品
	 * @param activtyId 活动id
	 * @param name 商品名称
	 * @param pageVo
	 * @return ActivityProductPageVo
	 */
    ProductVo.ActivityProductPageVo findProductsForActivtyByPage(1:i64 activtyId,2:string name,3:Common.PageVo pageVo),
    
    /**
	 * 查询一个商品下面所有可以提货的网点
	 * @param productId 商品id
	 * @param cityId 城市id
	 * @param clientId 客户端id
	 * @param pageVo
	 * @return OutletProductPageVo
	 */
    ProductVo.ProductOutletPageVo findProductsForAreaByPage(1:string productId,2:i64 cityId,3:string clientId,4:Common.PageVo pageVo),
    
    /**
	 * 查询一个商品下面所有可以提货的区
	 * @param productId 商品id
	 * @param cityId 城市id
	 * @param clientId 客户端id
	 * @return list<AreaVo>
	 */
    list<struct.AreaVo> findAreaForCityByList(1:string productId,2:i64 cityId,3:string clientId),
    
    /**
	 * VIP预售商品推荐列表查询
	 * @param ProductFilterVoReq
	 * @param pageVo
	 * @return ProductBriefPageVoRes
	 */
    ProductVo.ProductBriefPageVoRes queryVipPresellProducts(1:ProductVo.ProductFilterVoReq productFilterVoReq,2:Common.PageVo pageVo),
    
    /**
     *根据定位的距离查询特惠商品推荐列表或特惠商品列表查询
     * @param QueryProductFilterVo过滤条件
     * @param filedSorts 排序
	 * @param pageVo 分页
	 * @return ProductBriefPageVoRes
	 */
    ProductVo.GroupProductPageVoRes queryGroupProducts(1:ProductVo.QueryProductFilterVo filterVo,2:list<ProductVo.FiledSort> filedSorts,3:Common.PageVo pageVo),
    
    /** 
     * 根据商户id(定位的距离查询到的商户)查询该商户的特惠商品列表 QueryProductFilterVo过滤条件 filedSorts 排序 pageVo 分页 
     */
    ProductVo.MerchantProductPageVoRes queryMerchantProducts(1:ProductVo.QueryProductFilterVo filterVo,2:list<ProductVo.FiledSort> filedSorts,3:Common.PageVo pageVo),
    
    /**
	 * 批量获取商品二维码信息
	 * @param ProductInfoVo 
	 * @param list
	 * @return list<ProductInfoVo>
	 */
    list<ProductVo.ProductVo> findUrlCodeByList(1:list<ProductVo.ProductVo> productList),
    
    /**
	 * 下载商品信息
	 * @param ProductSearchVo 
	 * @param type
	 * @return ExportResultRes
	 */
    Common.ExportResultRes downProducts(1:ProductVo.ProductSearchVo productSearchVo,2:string type),
    
    /**
     * 根据定位的距离查询类目营销里的特惠商品推荐列表
     * @param QueryProductFilterVo过滤条件
     * @param filedSorts 排序
	 * @param pageVo 分页
	 * @return ProductPageVoRes
	 */
    ProductVo.ProductPageVoRes searchProductByType(1:ProductVo.QueryProductFilterVo filterVo,2:list<ProductVo.FiledSort> filedSorts,3:Common.PageVo pageVo),
    
    /**
     * 真实门店新增 修改 删除 需要修改对应商品关联的地区，门店信息
     * @param clientId客户端id
     * @param type门店操作类型（1:新增;2:修改;3:删除）
	 * @param OutletVo门店信息
	 * @return ResultVo
	 */
    Common.ResultVo updateGroupProductByOutlet(1:string clientId,2:string type,3:OutletVo.OutletVo outletVo),
    
    
    Common.ResultVo updateHisGroupGoodsOutlets(),
    
    /**
     * 销售期到期自动将上架状态变成下架
	 */
    Common.ResultVo autoOffShelfProductSaleEnd(),
    
    /**
	 * boss商品管理平台分页查询商品列表(精品商城商品列表)
	 * @param ProductFilterVo
	 * @param pageVo
	 * @return ProductListPageVo
	 */
    ProductVo.ProductListPageVo findGoodsByPage(1:ProductVo.ProductFilterVo productFilterVo,2:Common.PageVo pageVo),
    
    /**
     * boss商品管理平台新增精品商城商品
     * @param OriginVo
     * @param GoodsInfoVo
     * @return AddProductVoRes
     */
    ProductVo.AddProductVoRes addGoods(1:Common.OriginVo originVo,2:ProductVo.GoodsInfoVo productInfoVo),

    /**
     * boss商品管理平台修改精品商城商品
     * @param OriginVo
     * @param GoodsInfoVo
     * @return ResultVo    
     */
    Common.ResultVo updateGoods(1:Common.OriginVo originVo,2:ProductVo.GoodsInfoVo productInfoVo),
    
    /**
     * boss商品管理平台查询精品商城商品详情修改精品商城商品之前查询用到
     * @param string
     * @return GoodsInfoVo
     */
    ProductVo.GoodsInfoVo getGoodsDetail(1:string productId),
    
    /**
     * boss管理平台上架精品商城商品
     * @param OriginVo
     * @param string
     * @return ResultVo    
     */
    Common.ResultVo updateGoodsStatusOn(1:Common.OriginVo originVo,2:string productId),
    
    /**
     * boss管理平台下架精品商城商品
     * @param OriginVo
     * @param string
     * @return ResultVo    
     */
    Common.ResultVo updateGoodsStatusOff(1:Common.OriginVo originVo,2:string productId),
    
    /**
     * H5获取精品商城的商品列表
     * @param QueryBoutiqueGoodsFilterVo过滤条件
     * @param filedSorts 排序
	 * @param pageVo 分页
	 * @return BoutiqueProductPageVoRes
	 */
    ProductVo.BoutiqueProductPageVoRes queryBoutiqueGoods(1:ProductVo.QueryBoutiqueGoodsFilterVo filterVo,2:ProductVo.FiledSort filedSort,3:Common.PageVo pageVo),
    
     /**
     * H5获取精品商城的商品详情
     * @param productId
     * @return BoutiqueGoodsInfoVo
     */
    ProductVo.BoutiqueGoodsInfoVo queryBoutiqueGoodsDetail(1:string productId),
    
    /**
     * 管理平台商品管理查询商品详情(商户管理平台和银行管理平台)
     * @param productId
     * @return ProductDetailInfo
     */
    ProductVo.ProductDetailInfo getProductDetail(1:string productId),
    
    /**
	 * 管理平台商品管理查询商品列表(商户管理平台和银行管理平台)
	 * @param ProductFilterReqVo
	 * @param filedSorts
	 * @param pageVo
	 * @return ProductPageVo
	 */
    ProductVo.ProductPageVo findProductsByPage(1:ProductVo.ProductFilterReqVo filterVo,2:list<ProductVo.FiledSort> filedSorts,3:Common.PageVo pageVo),
    
    /**
     * 更新商品审核状态，只能是未提交或待审核两种情况
     * @param list<EditProductAuditStateVo>
     * @return ResultVo
     */
    Common.ResultVo updateProductAuditState(1:list<ProductVo.EditProductAuditStateVo> productAuditStates)
    
}

/** 
 * VIPProductService
 */
service VipProductService extends BizMonitor.BizMonitorService {    
    /**
     * 新加vip规则
     * @param OriginVo 操作人信息
     * @param VipProductVo
     * @return AddProductVoRes
     */
    ProductVo.AddProductVoRes addVipProduct(1:Common.OriginVo originVo,2:ProductVo.VipProductVo vipProductVo),
    
    /**
     * 启用或作废vip规则
     * @param OriginVo 操作人信息
     * @param vipId VIP规则id
     * @param status 1启用,2作废
     * @return ResultVo    
     */
    Common.ResultVo updateVipStatus(1:Common.OriginVo originVo,2:string vipId,3:string status),
    
    /**
     * 删除vip规则
     * @param OriginVo 操作人信息
     * @param vipId VIP规则id
     * @return ResultVo    
     */
    Common.ResultVo deleteVipProduct(1:Common.OriginVo originVo,2:string vipId),

    /**
     * 修改vip规则
     * @param OriginVo 操作人信息
     * @param VipProductVo
     * @return ResultVo    
     */
    Common.ResultVo updateVipProduct(1:Common.OriginVo originVo,2:ProductVo.VipProductVo vipProductVo),
    
    /**
     * 根据客户端id查询单独一条启用的vip规则
     * @param clientId 客户端id
     * @return VipProductVo
     */
    ProductVo.VipProductVo getVipProduct(1:string clientId),
    
    /**
     * 根据VIP规则id查询单独一条vip规则详情
     * @param vipId VIP规则id
     * @return VipProductVo
     */
    ProductVo.VipProductVo getVipProductDetail(1:string vipId),
    
    /**
     * 分页查询vip规则列表
     * @param PageVo 分页信息
     * @param VipProductVo
     * @return VipProductPageVoRes
     */
    ProductVo.VipProductPageVoRes getVipProductsByPage(1:Common.PageVo pageVo, 2:ProductVo.VipProductVo vipProductVo),
    
    /**
     * 分页查询vip规则已经绑定的商品列表
     * @param PageVo 分页信息
     * @param vipId VIP规则id
     * @return ProductOfVipPageVo
     */
    ProductVo.ProductOfVipPageVo getProductsOfVipByPage(1:Common.PageVo pageVo, 2:string vipId),
    
    /**
     * VIP规则绑定新商品
     * @param originVo 操作人信息
     * @param vipId VIP规则id
     * @param list<BindVipInfoVo> 绑定信息
	 * @return ResultVo(提示信息)
	 */
    Common.ResultVo addProductsToVipProduct(1:Common.OriginVo originVo,2:string vipId,3:list<ProductVo.BindVipInfoVo> bindInfos),
    
    /**
     * VIP规则解除绑定商品
     * @param originVo 操作人信息
     * @param vipId VIP规则id
     * @param productIds 商品id数组 如果有值代表解除多条绑定 如果没有值解除该VIP规则下所有商品绑定
	 * @return ResultVo(提示信息)
	 */
    Common.ResultVo removeProductsFromVipProduct(1:Common.OriginVo originVo,2:string vipId,3:list<string> productIds),
    
    /**
	 * 分页查询可以绑定VIP规则的商品列表
	 * @param vipId VIP规则id
	 * @param name 商品名称
	 * @param priceStart 商品售价开始范围
	 * @param priceEnd 商品售价结束范围
	 * @param pageVo
	 * @return ProductOfVipPageVo
	 */
    ProductVo.ProductOfVipPageVo findProductsForVipByPage(1:string vipId,2:string name,3:double priceStart,4:double priceEnd,5:Common.PageVo pageVo)
    
}

/** 
 * 商品秒杀配置接口
 */
service ProductSeckillService  extends BizMonitor.BizMonitorService{

    /**
     * 增加 ProductSeckill
     * @param productSeckillInfoVo
     * @return ResultVo
     */
    Common.ResultVo addProductSeckill(1:list<ProductVo.ProductSeckillVo> productSeckillVoList,2:Common.OriginVo originVo);

    /**
     * 删除 ProductSeckill
     * @param productSeckillVo
     * @return ResultVo    
     */
    Common.ResultVo deleteProductSeckill(1:ProductVo.ProductSeckillVo productSeckillVo,2:Common.OriginVo originVo);

    /**
     * 修改 ProductSeckill
     * @param productSeckillVo
     * @return ResultVo    
     */
    Common.ResultVo updateProductSeckill(1:ProductVo.ProductSeckillVo productSeckillVo,2:Common.OriginVo originVo);


    /**
     * 上下架 ProductSeckill
     * @param productSeckillVo
     * @return ResultVo    
     */
    Common.ResultVo updateProductSeckillStatus(1:ProductVo.ProductSeckillVo productSeckillVo,2:Common.OriginVo originVo);

    /**
     * 查询详情 ProductSeckill
     * @param productSeckill
     * @return productSeckillInfoVo
     */
    ProductVo.ProductSeckillInfoVo getProductSeckillDetail(1:ProductVo.ProductSeckillVo productSeckillVo);

    /**
     *分页查询 ProductSeckillVo
     * @param productSeckillVo
     * @param pageVo
     * @return ProductSeckillPageVo
     */
    ProductVo.ProductSeckillPageVo findSeckillByPage(1:ProductVo.ProductFilterVoReq productFilterVoReq,2:Common.PageVo pageVo)


    /**
     *分页查询H5 ProductSeckillVo
     * @param productSeckillVo
     * @param pageVo
     * @return ProductSeckillPageVo
     */
    ProductVo.ProductSeckillInfoPageVo findH5SeckillByPage(1:ProductVo.ProductFilterVoReq productFilterVoReq,2:Common.PageVo pageVo)
    
}