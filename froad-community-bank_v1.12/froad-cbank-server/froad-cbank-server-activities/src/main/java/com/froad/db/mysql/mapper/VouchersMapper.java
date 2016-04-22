package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ActiveBaseRule;
import com.froad.po.Vouchers;
import com.froad.po.VouchersInfo;
import com.froad.po.VouchersRuleInfo;

public interface VouchersMapper {

	/**
	 * @Title: addVouchersRuleInfo
	 * @Description: 红包基础规则
	 * @author: shenshaocheng 2015年11月26日
	 * @modify: shenshaocheng 2015年11月26日
	 * @param vouchersRuleInfo
	 *            代金券规则信息
	 * @return 返回更新状态
	 */
	public Long addVouchersRuleInfo(VouchersRuleInfo vouchersRuleInfo);
	
	 /**
	  * @Title: copyVouchersInfo
	  * @Description: 复制红包券码（新增时从上传券码中获取）
	  * @author: shenshaocheng 2015年12月13日
	  * @modify: shenshaocheng 2015年12月13日
	  * @param vouchersRuleInfo
	  * @return
	 */	
	public Long copyVouchersInfo(VouchersRuleInfo vouchersRuleInfo);
	
	 /**
	  * @Title: updateVouchersInfo
	  * @Description: 更新红包券码信息（从临时表拷贝后修正客户端ID、活动ID）
	  * @author: shenshaocheng 2015年12月13日
	  * @modify: shenshaocheng 2015年12月13日
	  * @param vouchersRuleInfo
	  * @return
	 */	
	public Long updateVouchersInfo(VouchersRuleInfo vouchersRuleInfo);
	
	 /**
	  * @Title: addVouchersDetailInfo
	  * @Description: 新增红包明细规则
	  * @author: shenshaocheng 2015年11月27日
	  * @modify: shenshaocheng 2015年11月27日
	  * @param vouchersRuleInfo
	  * @return
	 */	
	public Long addVouchersDetailInfo(VouchersRuleInfo vouchersRuleInfo);
	
	 /**
	  * @Title: addActiveTagRelationInfo
	  * @Description: 新增活动标签关联
	  * @author: shenshaocheng 2015年11月27日
	  * @modify: shenshaocheng 2015年11月27日
	  * @param vouchersRuleInfo
	  * @return
	 */	
	public Long addActiveTagRelationInfo(VouchersRuleInfo vouchersRuleInfo);
	
	 /**
	  * @Title: addVouchersInfo
	  * @Description: 新增红包券码信息
	  * @author: shenshaocheng 2015年11月27日
	  * @modify: shenshaocheng 2015年11月27日
	  * @param vouchersRuleInfo
	  * @return
	 */	
	public Long addVouchersInfo(VouchersRuleInfo vouchersRuleInfo);
	
	 /**
	  * @Title: addTemporaryVouchersInfo
	  * @Description: 新增临时红包券码信息
	  * @author: shenshaocheng 2015年12月1日
	  * @modify: shenshaocheng 2015年12月1日
	  * @param vouchersRuleInfo
	  * @return
	 */	
	public Long addTemporaryVouchersInfo(@Param("vouchersRuleInfo")List<VouchersInfo> vouchersRuleInfo);
	 /**
	  * @Title: addActiveSustainInfo
	  * @Description: 新增红包支持活动
	  * @author: shenshaocheng 2015年11月27日
	  * @modify: shenshaocheng 2015年11月27日
	  * @param vouchersRuleInfo
	  * @return
	 */	
	public Long addActiveSustainInfo(VouchersRuleInfo vouchersRuleInfo);
	
	 /**
	  * @Title: findvoucherList
	  * @Description: 查找红包券码
	  * @author: shenshaocheng 2015年11月28日
	  * @modify: shenshaocheng 2015年11月28日
	  * @param vouchersRuleInfo
	  * @return
	 */	
	public List<Vouchers> findvoucherList(VouchersRuleInfo vouchersRuleInfo);
	
	 /**
	  * @Title: findvoucherList
	  * @Description: 查找红包券码分页
	  * @author: shenshaocheng 2015年11月28日
	  * @modify: shenshaocheng 2015年11月28日
	  * @param vouchersRuleInfo
	  * @return
	 */	
	@SuppressWarnings("rawtypes")
	public List<Vouchers> findvoucherListByPage(Page page, @Param("activeBaseRule")ActiveBaseRule activeBaseRule);
	
}
