package com.froad.po;

import java.util.List;
/**
 * 
 * ClassName: QueryAuitRes
 * Function: TODO ADD FUNCTION
 * date: 2015年11月3日 下午2:24:18
 *
 * @author mi
 * @version
 */
public class QueryAuitRes {
	
	public Result result; 
	public List<QueryAuditDetailRes> auditDetailList;
	
	
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public List<QueryAuditDetailRes> getAuditDetailList() {
		return auditDetailList;
	}
	public void setAuditDetailList(List<QueryAuditDetailRes> auditDetailList) {
		this.auditDetailList = auditDetailList;
	}
	  
}
