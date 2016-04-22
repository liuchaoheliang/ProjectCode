package com.froad.cbank.coremodule.normal.boss.support.message;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.froad.thrift.vo.PageVo;

/**
 * 
 * <p>标题: 短信元素</p>
 * <p>说明: 短信元素相关的业务</p>
 * <p>创建时间：2015年5月12日下午1:49:39</p>
 * <p>作者: 陈明灿</p>
 * <p>版本: 1.0</p>
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
@Service
public class MessageElementSupport {
	
	/**
	 * 
	 * <p>功能简述：短信元素列表</p> 
	 * <p>使用说明：查询短信列表逻辑</p> 
	 * <p>创建时间：2015年5月12日下午1:55:29</p>
	 * <p>作者: 陈明灿</p>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> list(String pageNumber, String pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		PageVo pageVo = new PageVo();
		if (null != pageNumber) {
			pageVo.setPageNumber(Integer.parseInt(pageNumber));
		}
		if (null != pageSize) {
			pageVo.setPageSize(Integer.parseInt(pageSize));
		}
		// TODO 迭代延后
		return map;
	}

}
