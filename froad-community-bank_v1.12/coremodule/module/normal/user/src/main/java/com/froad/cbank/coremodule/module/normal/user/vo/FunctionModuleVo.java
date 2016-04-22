package com.froad.cbank.coremodule.module.normal.user.vo;

/**
 * @Description: 功能模块
 * @Author: liaolibiao@f-road.com.cn
 * @Date: 2015年10月16日 上午11:55:57
 */
public class FunctionModuleVo implements Comparable<FunctionModuleVo>{

	  /**
	   * 功能模块类型1:特惠商户;2:特惠商品;3:精品预售;4:扫码支付；5:积分兑换；
	   */
	  public String type; // optional
	  /**
	   * 模块名称
	   */
	  public String moduleName; // optional
	  /**
	   * 模块别名
	   */
	  public String moduleAlias; // optional
	  /**
	   * 模块图标url
	   */
	  public String iconUrl; // optional
	  
	  public String sortValue;
	  
	  
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getModuleAlias() {
		return moduleAlias;
	}
	public void setModuleAlias(String moduleAlias) {
		this.moduleAlias = moduleAlias;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getSortValue() {
		return sortValue;
	}
	public void setSortValue(String sortValue) {
		this.sortValue = sortValue;
	}
	
	
	
	/**
	 * 
	 * @Description: 功能模块排序
	 * @Author: liaolibiao@f-road.com.cn
	 * @Date: 2015年10月21日 下午1:40:43
	 * @param o
	 * @return
	 */
	@Override
	public int compareTo(FunctionModuleVo vo) {
		int sortValue = Integer.parseInt(this.sortValue);
		int otherSortValue = Integer.parseInt(vo.sortValue);
		if (sortValue < otherSortValue){
			return - 1 ;  
		}else if(sortValue > otherSortValue){
			return 1 ;  
		}
		return 0 ;  
	}
	  
	  
	  
	  
	  
}
