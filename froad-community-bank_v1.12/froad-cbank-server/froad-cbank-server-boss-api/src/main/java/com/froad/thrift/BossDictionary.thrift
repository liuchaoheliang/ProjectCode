namespace java com.froad.thrift.vo.dictionary

include "BizMonitor.thrift"
include "Common.thrift"


/**
 * 字典vo
 */
struct DictionaryVo {
	/** 字典id(自增主键) */
    1:optional i64 id; 
      
    /** 字典编号 */
    2:optional string dicCode;
    
    /** 字典名称 */
    3:optional string dicName;
      
    /** 字典描述 */
    4:optional string depiction;
        
    /** 字典类别ID */
    5:optional i64 categoryId;
    
    /**  排序值*/
    6:optional i32 orderValue;
    
    /**是否有效*/
    7:optional bool isEnable;
    
}
 

/**
 * 字典分页VoRes
 * DictionaryPageVoRes
 */
struct DictionaryPageVoRes {
	/** 分页page  */  
	1:Common.PageVo page;
	/**返回结果*/
	2:Common.ResultVo resultVo;
	/** 返回结果list  */  
	3:list<DictionaryVo> dictionaryVoList;
}



/**
 * DictionaryService
 * 字典服务类
 */
service BossDictionaryService extends BizMonitor.BizMonitorService {

  
   /**
     * 新增字典
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param dictionaryVo 字典信息
     * @return ResultVo
     */
    Common.CommonAddVoRes addDictionary(1:Common.OriginVo originVo,2:DictionaryVo dictionaryVo);
  
   /**
     * 修改字典
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param dictionaryVo 字典vo
     * @return ResultVo
     */
    Common.ResultVo updateDictionary(1:Common.OriginVo originVo,2:DictionaryVo dictionaryVo);
    
  
   /**
     * 删除字典
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param id 字典条目id
     * @return ResultVo
     */
    Common.ResultVo deleteDictionaryBatch(1:Common.OriginVo originVo,2:list<i64> dicIds);
  
   /**
	 * 分页查询字典类别列表
	 * @param dictionaryVo 过滤参数
	 * @param type 1-当前类别下字典查询  2-当前类别下所有子类别中字典查询
	 * @return DictionaryPageVoRes
	 */
    DictionaryPageVoRes getDictionaryByPage(1:Common.PageVo page,2:DictionaryVo dictionaryVo,3:i32 type);
  
  
   
}