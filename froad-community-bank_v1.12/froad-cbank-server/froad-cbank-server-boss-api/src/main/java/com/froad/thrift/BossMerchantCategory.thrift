namespace java com.froad.thrift.vo.merchant

include "BizMonitor.thrift"
include "Common.thrift"


/**
 * 商户分类
 */
struct BossMerchantCategoryVo {
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
    8:bool isEnable
    
}

struct BossParentCategoryListReq {
	/**客户端ID*/
	1:string clientId;
	/**分类ID*/
	2:string categoryId;
	
	/**分类类型:1:商户分类；2:商品分类*/
	3:string type
}

struct BossParentCategoryListRes {
	/** 商户分类集合*/
	1:list<BossParentCategoryVo> voList,
	
	/**返回结果*/
	2:Common.ResultVo resultVo
}

struct BossParentCategoryVo {
	/**分类id*/
	1:string categoryId;
	
	/**分类名称*/
	2:string name;
	
	/**tree path*/
	3:string treePaht;
	
	/**是否禁用:1:禁用；0：启用*/
	4:string isDelete;
	
}

 
struct BossMerchantCategoryListRes {
	/** 商户分类集合*/
	1:list<BossMerchantCategoryVo> voList,
	
	/**返回结果*/
	2:Common.ResultVo resultVo
}

struct BossMerchantCategoryInfoRes {
	/** 商户分类信息*/
	1:BossMerchantCategoryVo vo,
	
	/**返回结果*/
	2:Common.ResultVo resultVo
}

struct MerchantCategoryVo {
	/** 序号 */
    1:optional i64 id; 
      
    /** 所属客户端 */
    2:string clientName;
	
	/** 商户分类 */
	3:string merchantCategory;
	
	/** 商户分类详细 */
	4:string merchantCategryDetail;
	
	/** 商户名称 */
	5:string merchantName;
	
	/** 商户ID */
	6:string merchantId;
	
	/** 营业执照号 */
	7:string license;
}

/**
 * BossMerchantCategoryService
 */
service BossMerchantCategoryService extends BizMonitor.BizMonitorService {

  
   /**
	 * 查询商户分类列表
	 * @param clientId 客户端Id
	 * @param iscludeDisable 是否包含非禁用商户分类
	 * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
	 * @return BossMerchantCategoryResList
	 */
    BossMerchantCategoryListRes findAll(1:string clientId, 2:bool iscludeDisable, 3:Common.OriginVo originVo),
  
    /**
     * 查询商户分类信息
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param id  分类id
     * @param clientId 客户端Id
     * @return BossMerchantCategoryInfoRes
     */
    BossMerchantCategoryInfoRes getBossMerchantCategoryById(1:Common.OriginVo originVo, 2:i64 id, 3:string clientId),
  
    /**
     * 增加商户分类
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param bossMerchantCategoryVo 商户分类信息
     * @return ResultVo
     */
    Common.ResultVo addBossMerchantCategoryVo(1:Common.OriginVo originVo,2:BossMerchantCategoryVo bossMerchantCategoryVo),

   
    /**
     * 修改商户分类
     * @param OriginVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param BossMerchantCategoryVo
     * @return ResultVo    
     */
    Common.ResultVo updateBossMerchantCategoryVo(1:Common.OriginVo originVo,2:BossMerchantCategoryVo bossMerchantCategoryVo),

	
	/**
     * 商户分类的商户导入
     * @param OriginVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param list<BossMerchantCategoryInputVo>
     * @return ExportResultRes    
     */
	Common.ExportResultRes merchantCategoryInput(1:Common.OriginVo originVo, 2:list<MerchantCategoryVo> vos);
	
	
	/**
     * 商户分类的商户明细导出
     * @param OriginVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param clientId
	 * @param categoryId
     * @return ExportResultRes    
     */
	Common.ExportResultRes merchantCategoryDetailExport(1:Common.OriginVo originVo, 2:string clientId, 3:i64 categoryId); 
	
	/**
	*获取当前分类的父分类列表
	*/
	BossParentCategoryListRes getParentCategoryList(1:BossParentCategoryListReq req);
	
	
}