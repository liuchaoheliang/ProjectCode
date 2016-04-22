namespace java com.froad.thrift.vo.product

include "BizMonitor.thrift"
include "Common.thrift"


/**
 * 商品分类
 */
struct BossProductCategoryVo {
	/** 分类id */
    1:optional i64 id, 
      
    /** 客户端id */
    2:string clientId,
    
    /** 分类名称 */
    3:string name, 
      
    /** 树路径 空格间隔 */
    4:string treePath,
        
    /** 父节点id */
    5:i64 parentId,
    
    /** 小图标 */
    6:string icoUrl,
    
    /**  排序*/
    7:i16 orderValue,
    
    /**是否禁用*/
    8:bool isEnable,
    
    /**是否参与类目营销*/
    9:bool isMarket,
    
    /**是否是精品商城商品类型*/
    10:bool isMall,
}
 
struct BossProductCategoryListRes {
	/** 商品分类集合*/
	1:list<BossProductCategoryVo> voList,
	
	/**返回结果*/
	2:Common.ResultVo resultVo
}

struct BossProductCategoryInfoRes {
	/** 商品分类信息*/
	1:BossProductCategoryVo vo,
	
	/**返回结果*/
	2:Common.ResultVo resultVo
}

/**
 *商品分类导入Vo
 */
struct ProductCategoryVo {
	/** 序号 */
    1:optional i64 id; 
    
	/** 所属客户端 */
    2:string clientName;
	
	/**商品分类*/
	3:string productCategory,
	
	/**商品分类详细*/
	4:string productCategryDetail,
	
	/**商品名称*/
	5:string productName,
	
	/**商品Id*/
	6:string productId,
	
	/**商户名称*/
	7:string merchantName,
	
	/**商户Id*/
	8:string merchantId,
	
}


/**
 * 商品分类
 * BossProductCategoryService
 */
service BossProductCategoryService extends BizMonitor.BizMonitorService {

  
   /**
	 * 查询商品分类列表
	 * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
	 * @param clientId 客户端Id
	 * @param iscludeDisable 是否包含非禁用商品分类
	 * @param isMall 是否是精品商城商品类型0-否 1-是
	 * @return BossProductCategoryResList
	 */
    BossProductCategoryListRes findAll(1:Common.OriginVo originVo, 2:string clientId, 3:bool iscludeDisable, 4:bool isMall);
  
    /**
     * 查询商品分类信息
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param id  分类id
     * @return BossProductCategoryInfoRes
     */
    BossProductCategoryInfoRes getBossProductCategoryById(1:Common.OriginVo originVo, 2:i64 id, 3:string clientId);
  
    /**
     * 增加商品分类
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param bossProductCategoryVo 商品分类信息
     * @return ResultVo
     */
    Common.ResultVo addBossProductCategoryVo(1:Common.OriginVo originVo,2:BossProductCategoryVo bossProductCategoryVo);

   
    /**
     * 修改 商品分类
     * @param OriginVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param BossProductCategoryVo
     * @return ResultVo    
     */
    Common.ResultVo updateBossProductCategoryVo(1:Common.OriginVo originVo,2:BossProductCategoryVo bossProductCategoryVo);
	
	
    /**
     * 商品分类 商品导入
     * @param OriginVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param inputType 导入类型  1-商品分类   2-精品商城商品分类(区别于excel内容不一样)
     * @param list
     * @return     
     */
	Common.ExportResultRes productCategoryProductInput(1:Common.OriginVo originVo,2:string inputType,3:list<ProductCategoryVo> vos);
  
  
  	/**
     * 商品分类详细导出
     * @param OriginVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param ExportType 导出类型  1-商品分类   2-精品商城商品分类(区别于excel内容不一样)
     * @param list
     * @return   
     */
  	Common.ExportResultRes productCategoryDetailExport(1:Common.OriginVo originVo,2:string ExportType,3:string clientId, 4:i64 categoryId);
  	
  	
}