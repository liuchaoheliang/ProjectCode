namespace java com.froad.thrift.vo.dictionary

include "BizMonitor.thrift"
include "Common.thrift"


/**
 * 字典条目vo
 */
struct DictionaryItemVo {
	/** 字典条目id(自增主键) */
    1:optional i64 id; 
      
    /** 字典条目编号 */
	2:optional string itemCode;
	
	 /** 字典条目名称 */
	3:optional string itemName;
	
	 /** 字典条目值 */
	4:optional string itemValue;
	
	 /** 字典描述 */
	5:optional string depiction;
	
	 /** 字典ID */
	6:optional i64 dicId;
	
	 /** 客户端ID */
	7:optional string clientId;
	
    /**  排序值*/
    8:optional i32 orderValue;
    
    /**是否有效*/
    9:optional bool isEnable;
    
    /**字典名称*/
    10:optional string dicName;
    
}
 
/**
 * 字典条目分页VoRes
 * DictionaryItemPageVoRes
 */
struct DictionaryItemPageVoRes {
	/** 分页page  */  
	1:Common.PageVo page;
	/**返回结果*/
	2:Common.ResultVo resultVo;
	/** 返回结果list  */  
	3:list<DictionaryItemVo> dictionaryItemVoList;
}


/**
 * DictionaryItemService
 * 字典条目服务类
 */
service BossDictionaryItemService extends BizMonitor.BizMonitorService {

  
   /**
     * 新增字典条目
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param dictionaryItemVo 字典条目信息
     * @return ResultVo
     */
    Common.ResultVo addDictionaryItem(1:Common.OriginVo originVo,2:DictionaryItemVo dictionaryItemVo);
  
   /**
     * 修改字典条目
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param dictionaryItemVo 字典条目vo
     * @return ResultVo
     */
    Common.ResultVo updateDictionaryItem(1:Common.OriginVo originVo,2:DictionaryItemVo dictionaryItemVo);
    
    
   /**
     * 删除字典条目
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param id 字典条目id
     * @return ResultVo
     */
    Common.ResultVo deleteDictionaryItemBatch(1:Common.OriginVo originVo,2:list<i64> itemIds);
  
  
   /**
	 * 分页查询字典条目列表
	 * @param dictionaryItemVo 过滤参数
	 * @return DictionaryItemPageVoRes
	 */
    DictionaryItemPageVoRes getDictionaryItemByPage(1:Common.PageVo page,2:DictionaryItemVo dictionaryItemVo);
  
}