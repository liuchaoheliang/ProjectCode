/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

namespace java com.froad.thrift.vo.report 

/**
* -------------------------------------Boss统计报表服务Vo---------------------------------
*/

/**
 *  导出请求vo
 *  ExportReq
 */
struct ExportReq {
    /** 所属客户端  */   
    1: optional string clientId;     	 	 
    /** 所属机构，多个用逗号分隔  */  
    2: optional string orgCodes;     
    /** 商户Id  */  
    3: optional string merchantId;    	 
    /** 创建时间开始日期  */  
    4: optional i64 startDate;    
    /** 创建时间结束日期  */  
    5: optional i64 endDate;    		
}


/**
 * 分页查询VoRes
 */
struct DownloadInfoPageVoRes{
	/** 分页page  */  
	1:Common.PageVo page;
	/**返回结果*/
	2:Common.ResultVo resultVo;
	/** 返回结果list  */  
	3:list<DownloadInfoVo> voList;
}

/**
 *  分页查询详情vo
 *  DownloadInfoVo
 */
struct DownloadInfoVo {
    /** 主键id  */   
    1: optional i64 id;     	 	 
    /** 文件名  */  
    2: optional string filename;     
    /** 文件生成时间  */  
    3: optional i64 createTime;    	 
}


