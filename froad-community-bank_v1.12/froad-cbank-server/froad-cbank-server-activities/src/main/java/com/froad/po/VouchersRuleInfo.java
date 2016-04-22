package com.froad.po;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: VouchersRuleInfo
 * @Description: 代金券规则信息
 * @author froad-shenshaocheng 2015年11月26日
 * @modify froad-shenshaocheng 2015年11月26日
 */
 /**
  * @ClassName: VouchersRuleInfo
  * @Description: TODO
  * @author froad-shenshaocheng 2015年11月27日
  * @modify froad-shenshaocheng 2015年11月27日
 */
public class VouchersRuleInfo {

	/**
	 * 营销活动标签关联
	 */
	private ActiveTagRelation activeTagRelation;
	/**
	 * 营销活动基础规则
	 */
	private ActiveBaseRule activeBaseRule;
	/**
	 * 代金券详细规则
	 */
	private VouchersDetailRule vouchersDetailRule;
	
	List<VouchersInfo> vouchersInfoList;
	List<ActiveSustainRelation> activeSustainRelationList;
	
	@SuppressWarnings("rawtypes")
	private Map objMap;

	public ActiveTagRelation getActiveTagRelation() {
		return activeTagRelation;
	}

	public void setActiveTagRelation(ActiveTagRelation activeTagRelation) {
		this.activeTagRelation = activeTagRelation;
	}

	public ActiveBaseRule getActiveBaseRule() {
		return activeBaseRule;
	}

	public void setActiveBaseRule(ActiveBaseRule activeBaseRule) {
		this.activeBaseRule = activeBaseRule;
	}

	public VouchersDetailRule getVouchersDetailRule() {
		return vouchersDetailRule;
	}

	public void setVouchersDetailRule(VouchersDetailRule vouchersDetailRule) {
		this.vouchersDetailRule = vouchersDetailRule;
	}

	@SuppressWarnings("rawtypes")
	public Map getObjMap() {
		return objMap;
	}

	@SuppressWarnings("rawtypes")
	public void setObjMap(Map objMap) {
		this.objMap = objMap;
	}

	public List<VouchersInfo> getVouchersInfoList() {
		return vouchersInfoList;
	}

	public void setVouchersInfoList(List<VouchersInfo> vouchersInfoList) {
		this.vouchersInfoList = vouchersInfoList;
	}

	public List<ActiveSustainRelation> getActiveSustainRelationList() {
		return activeSustainRelationList;
	}

	public void setActiveSustainRelationList(
			List<ActiveSustainRelation> activeSustainRelationList) {
		this.activeSustainRelationList = activeSustainRelationList;
	}
}
