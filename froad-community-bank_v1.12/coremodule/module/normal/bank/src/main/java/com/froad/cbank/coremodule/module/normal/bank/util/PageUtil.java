package com.froad.cbank.coremodule.module.normal.bank.util;

import java.util.Map;

import com.froad.cbank.coremodule.module.normal.bank.vo.BaseVo;
import com.froad.thrift.vo.PageVo;

/**
 * 分页信息处理工具
 * 
 * @author user
 *
 */
public class PageUtil {

	/**
	 * 
	 * setPagesValueToResMap:将返回的分页信息注入Map中
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月31日 上午11:11:14
	 * @param reMap
	 * @param pageVo
	 */
	public static void setPagesValueToResMap(Map<String, Object> reMap,
			PageVo pageVo) {
		if (pageVo != null) {
			reMap.put("pageCount", pageVo.getPageCount());
			reMap.put("totalCount", pageVo.getTotalCount());
			reMap.put("pageNumber", pageVo.getPageNumber());
			reMap.put("lastPageNumber", pageVo.getLastPageNumber());
			reMap.put("firstRecordTime", pageVo.getFirstRecordTime());
			reMap.put("lastRecordTime", pageVo.getLastRecordTime());
		} else {
			reMap.put("pageCount", 0);
			reMap.put("totalCount", 0);
			reMap.put("pageNumber", 1);
			reMap.put("lastPageNumber", 0);
			reMap.put("firstRecordTime", 0);
			reMap.put("lastRecordTime", 0);
		}
	}

	/**
	 * 
	 * getPageVo:封装列表分页请求参数
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月31日 上午11:13:41
	 * @param req
	 * @return
	 */
	public static PageVo getPageVo(BaseVo req) {
		PageVo page = new PageVo();
		page.setPageNumber(
				req.getPageNumber() == null ? 1 : req.getPageNumber());
		page.setPageSize(req.getPageSize() == null ? 10 : req.getPageSize());
		if (req.getLastPageNumber() != null) {
			page.setLastPageNumber(req.getLastPageNumber());
		}
		if (req.getFirstRecordTime() != null) {
			page.setFirstRecordTime(req.getFirstRecordTime());
		}
		if (req.getLastRecordTime() != null) {
			page.setLastRecordTime(req.getLastRecordTime());
		}
		return page;
	}
}
