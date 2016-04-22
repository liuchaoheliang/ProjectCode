package com.froad.cbank.coremodule.normal.boss.pojo.product;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * 
 * @ClassName: CompetiviProUpVo
 * @author chenzhangwei@f-road.com.cn
 * @createTime 2015年12月4日 下午7:25:05 
 * @desc <p>精品商品上架下架请求vo</p>
 */
public class CompetiviProUpVo {
	
	@NotEmpty("id不能为空")
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
