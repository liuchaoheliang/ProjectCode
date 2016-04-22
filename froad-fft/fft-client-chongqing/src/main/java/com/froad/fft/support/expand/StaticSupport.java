package com.froad.fft.support.expand;

import java.util.Map;

/**
 * 静态化
 * @author FQ
 *
 */
public interface StaticSupport {
	

	/**
	 * 生成静态
	 * 
	 * @param templatePath 模板文件路径
	 * @param staticPath 静态文件路径
	 * @param model 数据
	 * @return 生成数量
	 */
	int build(String templatePath, String staticPath, Map<String, Object> model);

	/**
	 * 生成静态
	 * 
	 * @param templatePath 模板文件路径
	 * @param staticPath 静态文件路径
	 * @return 生成数量
	 */
	int build(String templatePath, String staticPath);

	/**
	 * 生成首页静态
	 * @return
	 */
	int buildIndex();
	
	/**
	 * 生成Sitemap
	 * 
	 * @return 生成数量
	 */
	int buildSitemap();
	
	/**
	 * 生成其它静态
	 * @return
	 */
	int buildOther();
}
