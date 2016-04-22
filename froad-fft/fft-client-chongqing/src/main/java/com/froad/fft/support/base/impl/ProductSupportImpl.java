
	 /**
  * 文件名：ProductSupportImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年4月15日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.support.base.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.froad.fft.api.service.ProductExportService;
import com.froad.fft.dto.ProductDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.support.base.ProductSupport;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月15日 下午8:04:46 
 */

@Service
public class ProductSupportImpl implements ProductSupport {

	@Resource(name = "productService")
	private ProductExportService productService;
	
	private static Logger logger = Logger.getLogger(ProductSupportImpl.class);
	
	private final ClientAccessType clientAccessType = ClientAccessType.chongqing;
	
	
	@Override
	public ProductDto getById(Long id) {
		if(id==null){
			logger.info("查询ID为空");
			return null;
		}
		return productService.getProductById(clientAccessType, ClientVersion.version_1_0, id);
	}

}
