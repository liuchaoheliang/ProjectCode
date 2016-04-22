package com.froad.cbank.coremodule.module.normal.user.vo;

import java.util.List;

import com.froad.cbank.coremodule.module.normal.user.pojo.PagePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.ProductOfFindUseReqPojo;


/**
 * ClassName: FindCanUseRedPacketReqVo
 * Function:查询可用红包对象
 * date: 2015年12月8日 下午6:45:17
 *
 * @author 刘超 liuchao@f-road.com.cn
 * @version 
 */
public class FindCanUseRedPacketReqVo extends PagePojo {

	private Boolean isAvailable;
	private Boolean isEnableQrcode = false;
	private String[] sustainActiveIds;
	private List<ProductOfFindUseReqPojo> list;
	public Boolean getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	public Boolean getIsEnableQrcode() {
		return isEnableQrcode;
	}
	public void setIsEnableQrcode(Boolean isEnableQrcode) {
		this.isEnableQrcode = isEnableQrcode;
	}
	public String[] getSustainActiveIds() {
		return sustainActiveIds;
	}
	public void setSustainActiveIds(String[] sustainActiveIds) {
		this.sustainActiveIds = sustainActiveIds;
	}
	public List<ProductOfFindUseReqPojo> getList() {
		return list;
	}
	public void setList(List<ProductOfFindUseReqPojo> list) {
		this.list = list;
	}
	
}
