/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

namespace java com.froad.thrift.vo

struct DictionaryItemVo{

         /** 自增主键ID */
	1:i64 id;
         /** 字典条目编号 */
	2:string itemCode;
	 /** 字典条目名称 */
	3:string itemName;
	 /** 字典条目值 */
	4:string itemValue;
	 /** 字典描述 */
	5:string depiction;
	 /** 字典ID */
	6:i64 dicId;
	 /** 客户端ID */
	7:string clientId;
	/** 排序值 */
	8:i16 orderValue;
	/** 是否有效 */
	9:string isEnable;
}

struct DictionaryVo {
	/** 字典id(自增主键) */
    1:optional i64 id; 
      
    /** 字典编号 */
    2:string dicCode;
    
    /** 字典名称 */
    3:string dicName;
      
    /** 字典描述 */
    4:string depiction;
        
    /** 字典类别ID */
    5:i64 categoryId;
    
    /**  排序值*/
    6:i16 orderValue;
    
    /**是否有效*/
    7:bool isEnable;
    
}



/**
 * 字典类别vo
 */
struct DictionaryCategoryVo {
	/** 字典类别id(自增主键) */
    1:optional i64 id; 
      
    /** 字典类别编号 */
    2:string categoryCode;
    
    /** 字典类别名称 */
    3:string categoryName; 
      
    /** 字典类别描述 */
    4:string depiction;
        
    /** 字典类别级别 */
    5:string categoryLevel;
    
    /** 父字典类别ID*/
    6:i64 parentId;
    
    /**  树路径*/
    7:string treePath;
    
    /**  排序值*/
    8:i16 orderValue;
    
    /**是否有效*/
    9:bool isEnable;
    
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
 * 字典类目分页的响应结果
 *
 */
struct DictionaryitemPageVoRes {
	
	/** 分页基础信息 */
	1:Common.PageVo page;
	/** 商户账户列表 */
	2:list<DictionaryItemVo> dictionaryitemVoList;
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