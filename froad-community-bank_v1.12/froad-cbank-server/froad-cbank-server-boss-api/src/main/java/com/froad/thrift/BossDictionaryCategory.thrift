namespace java com.froad.thrift.vo.dictionary

include "BizMonitor.thrift"
include "Common.thrift"


/**
 * 字典类别vo
 */
struct DictionaryCategoryVo {
	/** 字典类别id(自增主键) */
    1:optional i64 id; 
      
    /** 字典类别编号 */
    2:optional string categoryCode;
    
    /** 字典类别名称 */
    3:optional string categoryName; 
      
    /** 字典类别描述 */
    4:optional string depiction;
        
    /** 字典类别级别0-系统字典 1-业务字典 */
    5:optional string categoryLevel;
    
    /** 父字典类别ID*/
    6:optional i64 parentId;
    
    /**  树路径*/
    7:optional string treePath;
    
    /**  排序值*/
    8:optional i32 orderValue;
    
    /**是否有效*/
    9:optional bool isEnable;
    
}
 
/**
 * 字典类别Listvo
 */
struct DictionaryCategoryListVoRes {
	/**返回结果*/
	1:Common.ResultVo resultVo;
	
	/** 字典类别集合*/
	2:list<DictionaryCategoryVo> dictionaryCategoryVoList;
}


/**
 * DictionaryCategoryService
 * 字典类别服务类
 */
service BossDictionaryCategoryService extends BizMonitor.BizMonitorService {

  
   /**
     * 新增字典类别
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param dictionaryCategoryVo 字典类别信息
     * @return ResultVo
     */
    Common.ResultVo addDictionaryCategory(1:Common.OriginVo originVo,2:DictionaryCategoryVo dictionaryCategoryVo);
  
   /**
     * 修改字典类别
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param dictionaryCategoryVo 类别修改对象
     * @return ResultVo
     */
    Common.ResultVo updateDictionaryCategory(1:Common.OriginVo originVo,2:DictionaryCategoryVo dictionaryCategoryVo);
    
  
   /**
     * 删除字典类别
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param id 字典类别id
     * @return ResultVo
     */
    Common.ResultVo deleteDictionaryCategory(1:Common.OriginVo originVo,2:i64 id);
  
  
   /**
	 * 查询字典类别列表
	 * @param dictionaryCategoryVo 过滤参数
	 * @return DictionaryCategoryListVoRes
	 */
    DictionaryCategoryListVoRes getDictionaryCategory(1:DictionaryCategoryVo dictionaryCategoryVo);
  
   /**
	 * 导出脚本(导出该类别下所有的字典、条目信息)
	 * @param type 1-类别Id 2-字典Id
	 * @param ids 字典类别id或字典id集合
	 */
	Common.ExportResultRes getDictionaryCategoryExportUrl(1:i32 type,2:list<i64> ids);
      
      
   
}