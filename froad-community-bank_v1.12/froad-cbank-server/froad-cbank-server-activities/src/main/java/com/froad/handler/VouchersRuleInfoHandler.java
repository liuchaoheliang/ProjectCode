package com.froad.handler;

import java.util.List;

import org.apache.thrift.TException;

import com.froad.db.mysql.bean.Page;
import com.froad.po.Vouchers;
import com.froad.po.VouchersInfo;
import com.froad.po.VouchersRuleInfo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.FindVouchersRuleInfoVoResultVo;

 /**
  * @ClassName: VouchersRuleInfoHandler
  * @Description: 代金券信息数据处理接口
  * @author froad-shenshaocheng 2015年11月26日
  * @modify froad-shenshaocheng 2015年11月26日
 */
public interface VouchersRuleInfoHandler {
	 /**
	  * @Title: addVouchersRuleInfo
	  * @Description: 新增代金券规则
	  * @author: shenshaocheng 2015年11月26日
	  * @modify: shenshaocheng 2015年11月26日
	  * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
	  * @param vouchersRuleInfoVo 代金券详细信息
	  * @return 返回 新增结果
	  * @throws TException
	  * @see com.froad.thrift.service.VouchersRuleInfoService.Iface#addVouchersRuleInfo(
	  * com.froad.thrift.vo.OriginVo, com.froad.thrift.vo.active.VouchersRuleInfoVo)
	  */	
	public Long addVouchersRuleInfo(OriginVo originVo,
			VouchersRuleInfo vouchersRuleInfo);
	
	 /**
	  * @Title: addVouchersRuleInfo
	  * @Description: 新增临时代金券券码
	  * @author: shenshaocheng 2015年11月26日
	  * @modify: shenshaocheng 2015年11月26日
	  * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
	  * @param vouchersRuleInfoVo 代金券详细信息
	  * @return 返回 新增结果
	  * @throws TException
	  * @see com.froad.thrift.service.VouchersRuleInfoService.Iface#addVouchersRuleInfo(
	  * com.froad.thrift.vo.OriginVo, com.froad.thrift.vo.active.VouchersRuleInfoVo)
	  */	
	public Long addTemporaryVouchersRuleInfo(OriginVo originVo,
			List<VouchersInfo> vouchersRuleInfo);
	
	 /**
	  * @Title: disableVouchersRuleInfo
	  * @Description: 禁用 代金券规则
	  * @author: shenshaocheng 2015年11月26日
	  * @modify: shenshaocheng 2015年11月26日
	  * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
	  * @param clientId 客户端
	  * @param activeId	活动id
	  * @param operator 操作员
	  * @return 返回操作结果
	  * @throws TException
	  * @see com.froad.thrift.service.VouchersRuleInfoService.Iface#disableVouchersRuleInfo(
	  * com.froad.thrift.vo.OriginVo, java.lang.String, java.lang.String, java.lang.String)
	  */	
	public Integer disableVouchersRuleInfo(OriginVo originVo, String clientId,
			String activeId, String operator);

	 /**
	  * @Title: getActiveRuleInfo
	  * @Description: 查询 代金券列表
	  * @author: shenshaocheng 2015年11月26日
	  * @modify: shenshaocheng 2015年11月26日
	  * @param vouchersRuleInfoVo 代金券查询条件集
	  * @return 返回代金券列表
	  * @throws TException
	  * @see com.froad.thrift.service.VouchersRuleInfoService.Iface#getActiveRuleInfo(
	  * com.froad.thrift.vo.active.VouchersRuleInfoVo)
	  */	
	public List<Vouchers> getActiveRuleInfo(
			VouchersRuleInfo vouchersRuleInfo);

	 /**
	  * @Title: getActiveRuleInfoById
	  * @Description: 查询 单个 代金券信息
	  * @author: shenshaocheng 2015年11月26日
	  * @modify: shenshaocheng 2015年11月26日
	  * @param clientId 客户端ID
	  * @param activeId 活动ID
	  * @return
	  * @throws TException
	  * @see com.froad.thrift.service.VouchersRuleInfoService.Iface#getActiveRuleInfoById(
	  * java.lang.String, java.lang.String)
	  */	
	public FindVouchersRuleInfoVoResultVo getActiveRuleInfoById(String clientId,
			String activeId);

	 /**
	  * @Title: getActiveRuleInfoByPage
	  * @Description: 分页查询代金券列表
	  * @author: shenshaocheng 2015年11月26日
	  * @modify: shenshaocheng 2015年11月26日
	  * @param pageVo 分页信息
	  * @param vouchersRuleInfoVo 代金券查询条件集
	  * @return 返回分页列表结果
	  * @throws TException
	  * @see com.froad.thrift.service.VouchersRuleInfoService.Iface#getActiveRuleInfoByPage(
	  * com.froad.thrift.vo.PageVo, com.froad.thrift.vo.active.VouchersRuleInfoVo)
	  */	
	public Page<VouchersRuleInfo> getActiveRuleInfoByPage(
			Page<VouchersRuleInfo> page, VouchersRuleInfo vouchersRuleInfo);

	 /**
	  * @Title: updateVouchersRuleInfo
	  * @Description: 更新代金券规则
	  * @author: shenshaocheng 2015年11月26日
	  * @modify: shenshaocheng 2015年11月26日
	  * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
	  * @param vouchersRuleInfoVo 代金券信息
	  * @return 返回更新结果
	  * @throws TException
	  * @see com.froad.thrift.service.VouchersRuleInfoService.Iface#updateVouchersRuleInfo(
	  * com.froad.thrift.vo.OriginVo, com.froad.thrift.vo.active.VouchersRuleInfoVo)
	  */	
	public ResultVo updateVouchersRuleInfo(OriginVo originVo,
			VouchersRuleInfo vouchersRuleInfo);
	
	 /**
	  * @Title: findMaxActiveId
	  * @Description: 获取最大活动ID
	  * @author: shenshaocheng 2015年11月27日
	  * @modify: shenshaocheng 2015年11月27日
	  * @param clientId 客户端ID
	  * @return
	 */	
	public String findMaxActiveId(String clientId);
}
