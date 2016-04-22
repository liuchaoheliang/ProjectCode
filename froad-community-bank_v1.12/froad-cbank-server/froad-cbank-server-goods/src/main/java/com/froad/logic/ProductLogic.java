/*   
* Copyright © 2008 F-Road All Rights Reserved.
*  
* This software is the confidential and proprietary information of   
* Founder. You shall not disclose such Confidential Information   
* and shall use it only in accordance with the terms of the agreements   
* you entered into with Founder.   
*   
*/

/**
 * 
 * @Title: ProductLogic.java
 * @Package com.froad.logic
 * @see:   
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;
import java.util.Map;

import com.froad.common.beans.ResultBean;
import com.froad.db.mongo.page.H5MongoPage;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mysql.bean.Page;
import com.froad.po.ActivitiesInfo;
import com.froad.po.OutletProduct;
import com.froad.po.OutletProductInfo;
import com.froad.po.OutletProductQrCode;
import com.froad.po.Product;
import com.froad.po.ProductAreaOutlet;
import com.froad.po.ProductBaseInfo;
import com.froad.po.ProductComment;
import com.froad.po.ProductCommentFilter;
import com.froad.po.ProductDetailSimple;
import com.froad.po.ProductFilter;
import com.froad.po.ProductInfo;
import com.froad.po.ProductListInfo;
import com.froad.po.ProductViewInfo;
import com.froad.po.Result;
import com.froad.po.VipBindProductInfo;
import com.froad.po.mongo.ProductDetail;
import com.froad.po.mongo.StoreProductInfo;
import com.froad.thrift.vo.AddProductVoRes;
import com.froad.thrift.vo.EditProductAuditStateVo;
import com.froad.thrift.vo.FiledSort;
import com.froad.thrift.vo.GoodsInfoVo;
import com.froad.thrift.vo.OutletDetailSimpleVo;
import com.froad.thrift.vo.OutletVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.QueryBoutiqueGoodsFilterVo;
import com.froad.thrift.vo.QueryProductFilterVo;

import java.util.Set;

import com.mongodb.DBObject;

/**
 * 
 * <p>@Title: ProductLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ProductLogic {

    /**
     * 增加 Product
     * @param product
     * @return ResultBean
     */
	public ResultBean addProduct(ProductInfo productInfo);

    /**
     * 删除 Product
     * @param product
     * @return Boolean    是否成功
     */
	public Boolean deleteProduct(Product product);

    /**
     * 修改 Product
     * @param ProductInfo
     * @return Result
     */
	public Result updateProduct(ProductInfo productInfo);
	
    /**
     * 查询 Product
     * @param product
     * @return ProductViewInfo 
     */
	public ProductViewInfo getProduct(Product product);
	
	/**
     * 查询 ProductDetail
	 * @param product
	 * @param keys
	 * @return
	 */
	public ProductDetail getProductDetail(Product product,DBObject keys);
	
	/**
	 * 获取送积分活动信息
	 * @param param
	 * @return
	 * @return List<ActivitiesInfo>
	 */
	public List<ActivitiesInfo> getPointActivities(Map<String, Object> param);
	
	/**
	 * 上下架
	 * @param product
	 * @return Result
	 */
	public Result updateProductStatus(Product product);

	/**
	 * 判断商品是否已经存在
	 * @param product
	 * @return Boolean
	 */
	public Boolean isProductExist(Product product);
	
	/**
     * 查询 Product
     * @param ProductFilter
     * @return List<ProductListInfo>    结果集合 
     */
	public List<ProductListInfo> findProductListByPage(Page<ProductListInfo> page,ProductFilter product);
	
	/**
     * 查询 Product
     * @param ProductFilter
     * @return MongoPage    结果集合 
     */
    public MongoPage findProductsByPage(Page<ProductViewInfo> page,ProductFilter product);

    /**
     * 增加 OutletProduct
     * @param OutletProduct
     * @return OutletProductQrCode  二维码对象
     */
    public OutletProductQrCode addOutletProduct(OutletProduct outletProduct);
    
    /**
     * 个人h5输入价格后新加虚拟商品
     * @param outletProduct
     * @return
     */
    public OutletProductQrCode addH5OutletProduct(OutletProduct outletProduct);
    
    /**
     * 根据二维码查看面对面商品详情.
     * @param outletProductInfo
     * @return OutletProductInfo
     */
    public OutletProductInfo getOutletProduct(OutletProductInfo outletProductInfo);

    /**
     * 删除 OutletProduct
     * @param OutletProduct
     * @return Boolean    是否成功
     */
    public Boolean deleteOutletProduct(OutletProduct outletProduct);

    /**
     * 分页查询mongodb
     * @param page
     * @param outletProduct
     * @return MongoPage
     */
    public MongoPage getOutletProductListByPage(Page<OutletProduct> page,OutletProduct outletProduct);
    
    /**
     * 新加商品评论
     * @param productComment
     * @return Boolean
     */
    public Result addProductComment(ProductComment productComment);
    
    /**
     * 删除 ProductComment
     * @param ProductComment
     * @return Boolean    是否成功
     */
    public ResultBean deleteProductComment(ProductComment productComment);
    
    /**
     * replay ProductComment
     * @param ProductComment
     * @return Result    是否成功
     */
    public Result replayProductComment(ProductComment productComment);
    
    /**
     * 追加 ProductComment
     * @param ProductComment
     * @return Boolean    是否成功
     */
    public ResultBean updateProductComment(ProductComment productComment);
    
    /**
     * 查询ProductComment明细
     * @param ProductComment
     * @return ProductComment
     */
    public ProductComment getProductCommentDetail(ProductComment productComment);
    
    /**
     * 查询商品评论内容
     * @param ProductComment
     * @return ProductComment
     */
    public ProductComment queryProductComment(ProductComment productComment);
    
    /**
     * 分页查询ProductComment
     * @param Page
     * @param ProductCommentFilter
     * @return MongoPage
     */
    public MongoPage getProductCommentList(Page<ProductComment> page, ProductCommentFilter productCommentFilter);
    
    /**
     * 分页查询ProductComment
     * @param Page
     * @param ProductComment
     * @return MongoPage
     */
    public MongoPage queryProductComments(Page<ProductComment> page, ProductCommentFilter productCommentFilter);
    
    /**
      * 商品关联活动更新ProductDetail
      * @Title: updateProductDetail
      * @Description:  
      * @author: Yaren Liang 2015年4月3日
      * @modify: Yaren Liang 2015年4月3日
      * @param @param productDetail
      * @param @return    
      * @return boolean    
      * @throws
     */
    public ResultBean updateProductDetail(ProductDetail productDetail);
    
    /**
     * 批量回复
     * @param recomment 批量回复内容
     * @param merchantUserName 批量回复人姓名
     * @param productComments 批量回复评论id集合
     * @return
     * @return Result
     */
    public Result replayProductCommentBatch(String recomment,String merchantUserName,List<ProductComment> productComments);
    
    /**
     * 查询商品所属区下面的所有提货网点
     * @param productArea
     * @return
     */
    public List<ProductAreaOutlet> getProductAreaOutlets(ProductAreaOutlet productArea);
    
    /**
     * 批量上下架
     * @Title: updateProductStatusBatch
     * @Description:  
     * @author: wangyan
     * @modify: wangyan
     * @param clientId
     * @param status
     * @param productIds
     * @return
     * @return Result
     */
   public Result updateProductStatusBatch(String clientId,String status,List<String> productIds);
   
    /**
     * 商品批量失效
     * @Title: invalidProductBatch
     * @Description:  
     * @author: wangyan
     * @modify: wangyan
     * @param @param product
     * @param @return    
     * @return Boolean    
     * @throws
    */
    public boolean invalidProductBatch(Product product);
    
    /**
     * 商户启用时 把商品除团购以外的商品审核状态 未提交的改回待审核
     * @Title: validMerchantProductBatch
     * @Description:  
     * @author: wangyan
     * @modify: wangyan
     * @param @param merchantId
     * @param @return    
     * @return Boolean    
     * @throws
    */
    public boolean validMerchantProductBatch(String merchantId);
    
    /**
     * 商品批量禁用启用
     * @Title: endisableProductBatch
     * @Description:  
     * @author: wangyan
     * @modify: wangyan
     * @param @param Map
     * @param @return    
     * @return Boolean    
     * @throws
    */
    public boolean endisableProductBatch(Map<String,String> param);
    
    /**
     * 添加商户门店关联
      * @Title: addProductOutlets
      * @Description:  
      * @author: Yaren Liang 2015年4月8日
      * @modify: Yaren Liang 2015年4月8日
      * @param @param outlet
      * @param @return    
      * @return boolean    
      * @throws
     */
    public ResultBean addProductOutlets(ProductDetail productDetail);
    
    /**
     * 批量删除商品
     * @Title: deleteProductBatch
     * @Description:  
     * @author: wangyan
     * @modify: wangyan
     * @param clientId
     * @param productIds
     * @return
     * @return Result
     */
   public Result deleteProductBatch(String clientId,List<String> productIds);
    
    /**
     * 商品评价数量查询接口
      * @Title: queryProductCommentCount
      * @Description:  
      * @author: Yaren Liang 2015年4月9日
      * @modify: Yaren Liang 2015年4月9日
      * @param @param productCommentFilter
      * @param @return    
      * @return int    
      * @throws
     */
    public int queryProductCommentCount(ProductCommentFilter productCommentFilter);
    
    /** 是否收藏商品
	* @Title: countProducts 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param productId
	* @param @return
	* @return int
	* @throws 
	*/
	public int isExitsStoreProductInfo(String clientId, long memberCode,String productId);

	/** 
	 * 增加商品收藏
	* @Title: addStoreProductInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param productId
	* @param @param storeProductInfo
	* @param @return
	* @return int
	* @throws 
	*/
	public ResultBean addStoreProductInfo(String clientId, long memberCode,StoreProductInfo storeProductInfo);
	
	/** 
	 * 取消商品收藏
	* @Title: removeStoreProductInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param productId
	* @param @return
	* @return int
	* @throws 
	*/
	public int removeStoreProductInfo(String clientId, long memberCode, String productId);
	
	/** 获取全部收藏商品
	* @Title: queryStoreProductInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @return
	* @return List<StoreProductInfo>
	* @throws 
	*/
	public List<StoreProductInfo> queryStoreProductInfo(String clientId, long memberCode);
	
	
    /**
     * 分页查询 StoreProductInfo
     * @param page
     * @param storeProductInfo
     * @return Page<StoreProductInfo>    结果集合 
     */

	public Page<StoreProductInfo> findStoreProductInfoByPage(Page<StoreProductInfo> page, String clientId, long memberCode);
    
    /**
      * 商品审核批量更新接口
      * @Title: updateAuditProductBatch
      * @Description:  
      * @author: Yaren Liang 2015年4月16日
      * @modify: Yaren Liang 2015年4月16日
      * @param @param products
      * @param @return    
      * @return String    
      * @throws
     */
    public ResultBean updateAuditProductBatch(List<Product> products);
    
    /**
      * 根据商品id获取基础信息
      * @Title: getProductBaseInfo
      * @Description:  
      * @author: wangyan 2015年4月16日
      * @modify: wangyan 2015年4月16日
      * @param @param String clientId
      * @param List<ProductBaseInfo> products
      * @param @return    
      * @return List<ProductBaseInfo>    
      * @throws
     */
    public List<ProductBaseInfo> getProductBaseInfo(String clientId,List<ProductBaseInfo> products);
    
    /**
     * 批量增加 Product
     * @param List<ProductInfo>
     * @return List<AddProductVoRes>
     */
    public List<AddProductVoRes> addProductBatch(List<ProductInfo> productInfos);
    
    /**
     * 注册服务的ip个port
     * @param host:port
     * @return 
     */
	public void registerProductService(String hostAndPort);
	
	/**
	 * 个人h5查询商品
	 * @Title: findProducts 
	 * @author vania
	 * @version 1.0
	 * @see:  
	 * @param page
	 * @param product
	 * @return
	 * @return H5MongoPage    返回类型 
	 * @throws
	 */
	public H5MongoPage findProducts(Page<ProductViewInfo> page,ProductFilter product);
	
	/**
     * 活动绑定商品
	 * @param activitiesInfo
	 * @param productIds
	 * @return Result
	 */
	public Result addActivtyToProducts(Long activitiesId,List<String> productIds);
	
	/**
     * 活动解除绑定商品
     * @param activtyId
     * @param productId
     * @return Result
     */
	public Result removeActivtyFromProducts(long activtyId, String productId);
	
	/**
     * 查询 Product
     * @param activtyId
     * @param page
     * @return MongoPage    结果集合 
     */
    public MongoPage findActivtyProductsByPage(long activtyId,Page<ProductDetail> page);
    
    /**
     * 查询 Product
     * @param activtyId
     * @param name
     * @param page
     * @return MongoPage    结果集合 
     */
    public MongoPage findProductsForActivtyByPage(long activtyId,String name,Page<ProductDetail> page);
    
    /**
     * 更新团购商品区域信息
     * @param pageNumber
     * @param pageSize
     * @return int
     */
//    public int updateProductDetailArea(int pageNumber,int pageSize);
    
    public String updateProductDetailCityArea(int pageNumber, int pageSize, String type);
    
    /**
     * VIP预售推荐列表
     * <pre>
     *   wangyan <功能详细描述>
     * </pre>
     * @param mongoPage
     * @param product
     * @return MongoPage
     */
    public MongoPage queryVipPresellProducts(MongoPage mongoPage,ProductFilter product);
    
    /**
     * 
     * queryGroupProducts:(根据商户id查询 特惠商品推荐列表 特惠商品列表).
     *
     * @author wangyan
     * 2015-8-7 下午4:23:49
     * @param mPage
     * @param filterVo
     * @param merchantIds
     * @param pageSize
     * @return
     *
     */
    public Map<String,List<ProductDetail>> queryGroupProducts(MongoPage mPage,QueryProductFilterVo filterVo,List<String> merchantIds,int pageSize);
    
    
    /**
     * 未生效VIP规则绑定的VIP价格和VIP限购信息
     * @param productId
     * @return VipBindProductInfo
     */
    public VipBindProductInfo findVipBindProduct(String productId);
    
    /**
     * 生成单个二维码
     * @param productId
     * @return Product
     */
	public Product findUrlCodeById(String productId);
	
	/**
	 * 营销类目里的按距离排序的特惠商品列表
	 * @param pageVo 分页信息
	 * @param filterVo 查询条件
	 * @param fs FiledSort 排序
	 * @return
	 */
    public List<ProductDetailSimple> queryDistanceGroupGoods(PageVo pageVo,QueryProductFilterVo filterVo,FiledSort fs) throws Exception ;
    
    /**
	 * 营销类目里的其他排序的特惠商品列表(销量，价格，折扣排序)
	 * @param pageVo 分页信息
	 * @param filterVo 查询条件
	 * @param fs FiledSort 排序
	 * @return
	 */
    public List<ProductDetailSimple> queryGeneralGroupGoods(PageVo pageVo,QueryProductFilterVo filterVo,FiledSort fs) throws Exception ;
    
    /**
     * 获取营销类目里的特惠商品列表对应的商户门店信息
     * @param productDetailSimples
     * @param filterVo 查询条件
     * @param pageVo 分页信息
     * @return
     * @throws Exception
     */
	public Map<String, OutletDetailSimpleVo> queryGroupGoodMerhantOutlets(
			List<ProductDetailSimple> productDetailSimples, QueryProductFilterVo filterVo, PageVo pageVo)
			throws Exception;
	
	/**
	 * 门店变化时需要更新商品关联的门店区域信息
	 * @param type
	 * @param outletVo
	 * @return
	 */
	public boolean updateGroupProductAreaOutlet(String type, OutletVo outletVo);
	
	/**
     * 更新历史团购商品数据所属商户下门店和门店所在地区的关联关系
     * @return
     */
    public void updateHisGroupGoodsOutlets();

    /**
     * 销售期到期自动将上架状态变成下架
     * @return
     */
    public Result autoOffShelfProductSaleEnd();


    
    /**
     * 查询满足条件的特惠商品所属的商户iD
     * @param pageVo
     * @param filterVo
     * @return
     */
    public Set<String> findProductMerchantIds(PageVo pageVo,QueryProductFilterVo filterVo);


    
    /**
     * 新加精品商城商品
     * @param productVo
     * @param platType
     * @return
     */
	public ResultBean addGoods(GoodsInfoVo productVo, String platType);
	
	/**
     * 修改精品商城商品
     * @param productVo
     * @return
     */
	public Result updateGoods(GoodsInfoVo productVo);
	
	/**
	 * 精品商城商品上架
	 * @param productId
	 * @return
	 */
	public Result updateGoodsStatusOn(String productId);
	
	/**
	 * 精品商城商品下架
	 * @param productId
	 * @return
	 */
	public Result updateGoodsStatusOff(String productId);
	
	/**
	 * boss查询精品商城商品列表
	 * @param page
	 * @param productFilter
	 * @return
	 */
	public List<Product> findGoodsByPage(Page<ProductListInfo> page, ProductFilter productFilter);
	
	/**
	 * boss查询精品商城商品明细
	 * @param productId
	 * @return
	 */
	public Product getGoodsDetail(String productId);
	
	/**
	 * 营销类目里的按距离排序的特惠商品列表
	 * @param pageVo 分页信息
	 * @param filterVo 查询条件
	 * @param fs FiledSort 排序
	 * @param needGoodsNum 需要多少条
	 * @return
	 * @throws Exception
	 */
    public List<ProductDetail> queryGoods(PageVo pageVo,QueryBoutiqueGoodsFilterVo filterVo,FiledSort fs, int needGoodsNum) throws Exception ;
	
    /**
	 * 提交审核系统需要及时通知
	 * @param List<EditProductAuditStateVo>
	 * @return Result
	 */
	public Result updateProductAuditState(List<EditProductAuditStateVo> productAuditStates);
	
	/**
	 * 查询单个商品mysql表和mongo表中的基础信息
	 * @param productId
	 * @return
	 */
	public Map<String,Object> getProductInfo(String productId);
	
	/**
     * 查询 Product
     * @param ProductFilter
     * @return List<ProductListInfo>    结果集合 
     */
	public List<ProductListInfo> getProductsByPage(Page<ProductListInfo> page,ProductFilter product);
	
}