package com.froad.cbank.coremodule.normal.boss.pojo.ad;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;
import com.froad.cbank.coremodule.framework.common.valid.Regular;

/**
 * 广告位新增对象
 * @author luwanquan@f-road.com.cn
 * @date 2015年9月21日 下午3:05:11
 */
public class AdPositionAddReq {
	@NotEmpty("广告位名称为空")
	@Regular(reg = "^[\\s\\S]{1,20}$", value = "广告位名称限20字内")
	private String name;//名称
	@NotEmpty("终端类型为空")
	private String terminalType;//终端类型（1-pc,2-android,3-ios）
	@NotEmpty("位置为空")
	@Regular(reg = "^[\\s\\S]{1,16}$", value = "位置限16字符内")
	private String positionPage;//页面标识
	private int sizeLimit;//大小限制-单位为K
	private int width;//宽度
	private int height;//高度
	private String paramOneType;//第一附加参数类型（1-地区、2-商户类型、3-商品类型、4-精品商品类型）
	private String paramTwoType;//第二附加参数类型（1-地区、2-商户类型、3-商品类型、4-精品商品类型）
	private String paramThreeType;//第三附加参数类型（1-地区、2-商户类型、3-商品类型、4-精品商品类型）
	@Regular(reg = "^[\\s\\S]{0,100}$", value = "描述限100字内")
	private String description;//描述
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}
	public String getPositionPage() {
		return positionPage;
	}
	public void setPositionPage(String positionPage) {
		this.positionPage = positionPage;
	}
	public int getSizeLimit() {
		return sizeLimit;
	}
	public void setSizeLimit(int sizeLimit) {
		this.sizeLimit = sizeLimit;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getParamOneType() {
		return paramOneType;
	}
	public void setParamOneType(String paramOneType) {
		this.paramOneType = paramOneType;
	}
	public String getParamTwoType() {
		return paramTwoType;
	}
	public void setParamTwoType(String paramTwoType) {
		this.paramTwoType = paramTwoType;
	}
	public String getParamThreeType() {
		return paramThreeType;
	}
	public void setParamThreeType(String paramThreeType) {
		this.paramThreeType = paramThreeType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
