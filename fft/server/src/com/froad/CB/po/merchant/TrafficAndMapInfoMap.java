package com.froad.CB.po.merchant;

import java.io.Serializable;
import java.util.List;


	/**
	 * 类描述：交通信息和地图信息集合
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Dec 13, 2012 5:35:48 PM 
	 */
public class TrafficAndMapInfoMap  implements Serializable {
	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private List<MerchantTraffic> merchantTrafficList;
	private MerchantTrafficMAP merchantTrafficMap;
	public List<MerchantTraffic> getMerchantTrafficList() {
		return merchantTrafficList;
	}
	public void setMerchantTrafficList(List<MerchantTraffic> merchantTrafficList) {
		this.merchantTrafficList = merchantTrafficList;
	}
	public MerchantTrafficMAP getMerchantTrafficMap() {
		return merchantTrafficMap;
	}
	public void setMerchantTrafficMap(MerchantTrafficMAP merchantTrafficMap) {
		this.merchantTrafficMap = merchantTrafficMap;
	}
	
}
